package competition;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Options.Rename;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Counters;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.NullOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import competition.XMLParser.XmlInputFormat;



public class MeterCompute extends Configured implements Tool {
	
	/**
	 * 请设置你的ZooKeeper集群地址（多个IP之间以逗号分开）
	 */
	//############################### 请设置ZooKeeper集群地址  ####################################
	static String zooKeeper_Path = "192.168.1.2,192.168.1.3,192.168.1.4";
	//##########################################################################################
	static String hdfs_Path;
	
	/**
	 * 定义LastValue表，用于从HBase数据库获取上批数据最后的用电量，参赛者无需更改
	 */
	static TableName lastValueTableName = TableName.valueOf("LastValue");
	static String[] lastValueColumnFamily = {"LastElectricityValue"};

	/**
	 * 在此定义创建HBase表所需的参数（表名、列族名）
	 * 请参赛者创建两张表：
	 * 第一张表存储Map阶段解析后的XML文件的基础信息，要求表名统一为BaseInfo
	 * 第二张表存储Reduce阶段的用电量计算的结果，要求表名统一为ComputeResult
	 * 
	 */	
	//############################### 代码部分：请设置HBase表参数  ##################################
	static TableName baseInfoTableName = TableName.valueOf("BaseInfo");
	static String[] baseInfoColumnFamily = {"CommonInfo","EnergyInfo"};
	static TableName computeResultTableName = TableName.valueOf("ComputeResult");
	static String[] computeResultColumnFamily = {"ElectricityChangeInfo"};
	//###########################################################################################
	
	
	public static void main(String[] args) throws Exception {
		
		hdfs_Path = args[0];
		
		HBaseOps hbaseOps = HBaseOps.connectHBase(zooKeeper_Path);
		//创建LastValue表
		hbaseOps.createTable(lastValueTableName, lastValueColumnFamily);
		/**
		 * 结合上面设置的HBase表参数，创建HBase表
		 */
	//################################### 代码部分：请创建HBase表  ##################################
		hbaseOps.createTable(baseInfoTableName, baseInfoColumnFamily);
		hbaseOps.createTable(computeResultTableName, computeResultColumnFamily);
	//############################################################################################
		hbaseOps.disconnect();
		while (true) {
			ToolRunner.run(new MeterCompute(), args);
		}
	}
	
	/**
	 * run()会在程序启动后循环执行，参赛者无需修改该部分代码
	 */
	public int run(String[] args) throws Exception {
		
		Configuration conf = new Configuration();
		DistributedFileSystem fs = (DistributedFileSystem)FileSystem.get(URI.create(hdfs_Path), conf);

		// 设置XML文件解析的开始和结束位置
		conf.set("xmlinput.start", "<root>");
		conf.set("xmlinput.end", "</root>");

		@SuppressWarnings("deprecation")
		
		//	初始化job
		Job job = new Job(conf);
		//	设置JarByClass
		job.setJarByClass(MeterCompute.class);
		//	设置Map阶段输出的Key和Value的类型
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		//	设置ReduceTask个数
		job.setNumReduceTasks(4);
		//	指定Map类
		job.setMapperClass(CustomerMap.class);		
		//	指定Reduce类
		job.setReducerClass(CustomerReduce.class);
		//	设置input格式的类，在此即使用了XmlParser中的方法类
		job.setInputFormatClass(XmlInputFormat.class);
		//	设置output格式的类
		job.setOutputFormatClass(NullOutputFormat.class);
		
		//	从指定的HDFS目录下（hdfs://<your-host>/input/xml）读取所有文件,并保存该批次的文件信息
		FileInputFormat.addInputPath(job, new Path(hdfs_Path + "/input/xml"));
		FileStatus[] fileStatus = fs.listStatus(new Path(hdfs_Path + "/input/xml"));
		
		//	提交job
		job.waitForCompletion(true);
		
		//	获取文件处理个数
		Counters cs = job.getCounters();
		Counter uc = cs.findCounter("Customer", "file");
		int fileCounter = (int) uc.getValue();
		
		//	判断保存已处理文件的tmp文件夹是否存在，不存在则创建
		if(!(fs.exists(new Path(hdfs_Path + "/tmp/xml")))){
			fs.mkdirs(new Path(hdfs_Path + "/tmp/xml"));
		}
		//	将已经处理过的文件从hdfs://<your-host>/input/xml 移动到 hdfs://<your-host>/tmp/xml
		for (int i = 0; i < fileStatus.length; i++) {
			FileStatus fileStatu = fileStatus[i];
			String filename = fileStatu.getPath().toString().substring(fileStatu.getPath().toString().lastIndexOf("/"), fileStatu.getPath().toString().length());
			String newFilename = hdfs_Path + "/tmp/xml" + filename;
			fs.rename(fileStatu.getPath(), new Path(newFilename), Rename.OVERWRITE);
		}
		
		//	为了观察和调试，将在控制台输出以下信息
		System.out.println("程序已执行第 " + (++EnergyContent.runCounter) + " 次");
		System.out.println("本次处理 " + fileCounter + " 个文件");
		
		return 0;
	}
	
	/**
	 *  在CustomerMap类中完善map程序：
	 *  Map阶段读入能耗数据，获取能耗基础用能数据并存入HBase数据库的BaseInfo表。
	 */
	public static class CustomerMap extends Mapper<LongWritable, Text, Text, Text> {
		//	EnergyContent 保存解析后的能耗数据。API见《大数据应用开发手册》附件二
		EnergyContent ec = new EnergyContent();
		//	HBaseOps 简单封装了HBase操作。API见《大数据应用开发手册》附件一
		HBaseOps hbaseOps = HBaseOps.connectHBase(zooKeeper_Path);
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			context.getCounter("Customer", "file").increment(1);
			String document = value.toString();
			ec.initXml(document);
			//	能耗数据公有部分信息
			HashMap<String,String> commonMap = ec.getCommonMap();
			//	获取采集器id
			String gatewayId = commonMap.get("gateway_id");
			//	获取采集时间
			String sequence =  commonMap.get("sequence");
			//	能耗数据电表信息
			HashMap<String,HashMap<String,String>> meterMap = ec.getMeterMap();
			Iterator<Entry<String, HashMap<String,String>>> meterIter = meterMap.entrySet().iterator();  
			while (meterIter.hasNext()) {  
				Entry<String, HashMap<String,String>> entry = (Entry<String, HashMap<String,String>>) meterIter.next();  
				String meterId = (String)entry.getKey();
				//	获取每个电表的用电量
				String electricity = entry.getValue().get("function_1").toString();  
		//###################################代码部分：请完善map代码将数据存入HBase表 #######################################
				//	1.能耗数据的公共信息存入HBase数据库中的BaseInfo表
				Iterator<Entry<String, String>> commonIter = commonMap.entrySet().iterator(); 
				while(commonIter.hasNext()){
					Entry<String, String> commonEntry = (Entry<String, String>) commonIter.next();  
					String commonKey = commonEntry.getKey().toString();
					String commonValue = commonEntry.getValue().toString();
					try {
						hbaseOps.put("BaseInfo", sequence + "_" + gatewayId + "_" + meterId, "CommonInfo", commonKey, commonValue);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				HashMap<String,String> functionMap = meterMap.get(meterId);
				
				//	2.能耗基础用能数据存入HBase数据库中的BaseInfo表
				Iterator<Entry<String, String>> functionsIter = functionMap.entrySet().iterator(); 
				while(functionsIter.hasNext()){
					Entry<String, String> functionEntry = (Entry<String, String>) functionsIter.next();  
					String energyKey = functionEntry.getKey().toString();
					String energyValue = functionEntry.getValue().toString();
					try {
						hbaseOps.put("BaseInfo", sequence + "_" + gatewayId + "_" + meterId, "EnergyInfo", energyKey, energyValue);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
		//#################################################################################################################
				// context中的数据格式是（采集器编号_电表编号，采集时间_用电量）
				context.write(new Text(gatewayId + "_" + meterId), new Text(sequence + "_" + electricity));
			}
		}
		@Override
		protected void cleanup(Context context) throws IOException, InterruptedException {
			super.cleanup(context);
			hbaseOps.disconnect();
		}
	}
	
	/**
	 * 在CustomerReduce类中完善reduce程序：
	 * Reduce阶段计算出单位用电数据（Kw/10s），并写入HBase ComputeResult表。
	 */
	public static class CustomerReduce extends Reducer<Text, Text, Text, Text> {
		HBaseOps hbaseOps = HBaseOps.connectHBase(zooKeeper_Path);
		public void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {	
			//	valueList是用来记录当前“采集时间_用电量”和上一次“采集时间_用电量”的按时间排序的字符串集合。
			List<String> valueList = new ArrayList<String>();			
			for (Text value : values) {
				valueList.add(value.toString());
			}
			try {
				//	获取上一次电表的用电量
				String temp = hbaseOps.get(lastValueTableName.toString(), key.toString(), "LastElectricityValue", "se");	
				if(temp != null){
					valueList.add(temp);
				}
				//	对valueList集合按照采集时间进行排序
				Collections.sort(valueList);	
			} catch (Exception e) {
				e.printStackTrace();
			}
			//	用电量
			int electricity;
			//	采集时间
			String sequence = "";
			//	计算每个电表的用电量变化值
			for (int i = 0; i < valueList.size() - 1; i++) {
		//########################################### 代码部分：请完善reduce代码  ###########################################
				//	1.获取用电量和采集时间
				sequence = valueList.get(i).split("_")[0];
				electricity = Integer.parseInt(valueList.get(i).split("_")[1]);
				String startTime = sequence;
				String endTime = valueList.get(i + 1).split("_")[0];
				//	2.计算出每个电表每10秒的电量变化值
				int newValue = Integer.parseInt(valueList.get(i + 1).split("_")[1]) - electricity;
				//	3.将电量变化值及相关信息写入ComputeResult表
				try {
					hbaseOps.put(computeResultTableName.toString(), startTime + "_" + key.toString(), "ElectricityChangeInfo", "startTime", startTime);
					hbaseOps.put(computeResultTableName.toString(), startTime + "_" + key.toString(), "ElectricityChangeInfo", "endTime", endTime);
					hbaseOps.put(computeResultTableName.toString(), startTime + "_" + key.toString(), "ElectricityChangeInfo", "change", String.valueOf(newValue));
				} catch (Exception e) {
					e.printStackTrace();
				}
		//#################################################################################################################
			}
			
			try {
				//	记录电表最后一次的用电量值并写入LastValue表中，为下一次计算做准备。
				hbaseOps.put(lastValueTableName.toString(), key.toString(), "LastElectricityValue", "se", valueList.get(valueList.size()-1));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		@Override
		protected void cleanup(Context context) throws IOException, InterruptedException {
			super.cleanup(context);
			hbaseOps.disconnect();
		}
	}
}

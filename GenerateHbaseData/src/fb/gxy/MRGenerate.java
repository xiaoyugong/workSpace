package fb.gxy;

import java.io.IOException;
import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Durability;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

public class MRGenerate {
	public static Configuration configuration;  
    public static Connection connection;  
    public static Admin admin; 
    public static String tableName;
    
    public static String[] cq1 = new String[]{"Id", "DriveChan", "VehicleType1", "VehicleAttribute", "IllegalType1",
    		"IllegalSubType", "PostPicNo", "ChanIndex", "SpeedLimit", "PlateType", "PlateColor", "Bright",
    		"LicenseLen", "Country", "License", "Believe", "Index", "VehicleType2", "ColorDepth", "Color", 
    		"Speed", "Length", "IllegalType2", "VehicleLogoRecog", "VehicleSubLogoRecog", "VehicleModel", 
    		"MonitoringSiteID", "Dir", "DetectType", "PilotSafebelt", "CopilotSafebelt", "PilotSubVisor",
    		"CopilotSubVisor", "PilotCall", "AlarmDataType", "llegalTime", "IllegalTime", "PicNum"};
    public static String[] cq2 = new String[]{"Type", "PicRecogMode", "RedLightTime", "X", "Y", "Width", "Height", "Data"};

	public static class MyMapper extends Mapper<LongWritable, Text, Text, Put> {
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			for (int i = 1; i <= 1000000; i++) {
				int random = (int) (Math.random() * (100000 - 10000)) + 10000;
				// 抓拍时间
				long time = System.currentTimeMillis();
				// 设备编号
				String Id = String.valueOf(random + time);
				Random rand = new Random();
				// 违法类型
				String IllegalType = String.valueOf(rand.nextInt(20));
				// 车型
				String VehicleType = String.valueOf(rand.nextInt(11));
				// 车牌号
				String carNo = RandomStringUtils.randomAlphanumeric(5);
				String rowkey = "(" + Id.charAt(Id.length() - 1) + ")" + Id + "_" + String.valueOf(time) + "(" + i % 2
						+ ")_" + carNo + "_" + VehicleType + "_" + IllegalType;

				Put put = new Put(Bytes.toBytes(rowkey));
				put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[0]), Bytes.toBytes(Id));
				put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[1]),
						Bytes.toBytes(String.valueOf(rand.nextInt(11))));
				put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[2]), Bytes.toBytes(VehicleType));
				put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[3]), Bytes.toBytes(String.valueOf(rand.nextInt(3))));
				put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[4]), Bytes.toBytes(IllegalType));
				put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[5]), Bytes.toBytes(IllegalType));
				put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[6]),
						Bytes.toBytes(String.valueOf(rand.nextInt(3) + 1)));
				put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[7]),
						Bytes.toBytes(String.valueOf(rand.nextInt(11))));
				put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[8]), Bytes.toBytes("120km/h"));
				put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[9]), Bytes.toBytes(String.valueOf(rand.nextInt(9))));
				put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[10]),
						Bytes.toBytes(String.valueOf(rand.nextInt(7))));
				put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[11]), Bytes.toBytes("100nit"));
				put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[12]), Bytes.toBytes("5"));
				put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[13]), Bytes.toBytes("Country"));
				put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[14]), Bytes.toBytes(carNo));
				put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[15]), Bytes.toBytes("believe"));
				put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[16]), Bytes.toBytes("" + i + ""));
				put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[17]),
						Bytes.toBytes(String.valueOf(rand.nextInt(7))));
				put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[18]),
						Bytes.toBytes(String.valueOf(rand.nextInt(2))));
				put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[19]),
						Bytes.toBytes(String.valueOf(rand.nextInt(13))));
				put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[20]), Bytes.toBytes("90km/h"));
				put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[21]), Bytes.toBytes("2m"));
				put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[22]),
						Bytes.toBytes(String.valueOf(rand.nextInt(22))));
				put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[23]), Bytes.toBytes("VehicleLogoRecog"));
				put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[24]), Bytes.toBytes("VehicleSubLogoRecog"));
				put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[25]), Bytes.toBytes("VehicleModel"));
				put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[26]),
						Bytes.toBytes(String.valueOf(rand.nextInt(100000))));
				put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[27]),
						Bytes.toBytes(String.valueOf(rand.nextInt(9))));
				put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[28]),
						Bytes.toBytes(String.valueOf(rand.nextInt(6))));
				put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[29]),
						Bytes.toBytes(String.valueOf(rand.nextInt(3))));
				put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[30]),
						Bytes.toBytes(String.valueOf(rand.nextInt(3))));
				put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[31]),
						Bytes.toBytes(String.valueOf(rand.nextInt(3))));
				put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[32]),
						Bytes.toBytes(String.valueOf(rand.nextInt(3))));
				put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[33]),
						Bytes.toBytes(String.valueOf(rand.nextInt(3))));
				put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[34]),
						Bytes.toBytes(String.valueOf(rand.nextInt(2))));
				put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[35]), Bytes.toBytes("llegalTime"));
				put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[36]), Bytes.toBytes("IllegalTime"));
				put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[37]), Bytes.toBytes("3"));

				put.addColumn(Bytes.toBytes("image"), Bytes.toBytes(cq2[0]),
						Bytes.toBytes(String.valueOf(rand.nextInt(10))));
				put.addColumn(Bytes.toBytes("image"), Bytes.toBytes(cq2[1]),
						Bytes.toBytes(String.valueOf(rand.nextInt(2))));
				put.addColumn(Bytes.toBytes("image"), Bytes.toBytes(cq2[2]), Bytes.toBytes("300s"));
				put.addColumn(Bytes.toBytes("image"), Bytes.toBytes(cq2[3]), Bytes.toBytes("1"));
				put.addColumn(Bytes.toBytes("image"), Bytes.toBytes(cq2[4]), Bytes.toBytes("1"));
				put.addColumn(Bytes.toBytes("image"), Bytes.toBytes(cq2[5]), Bytes.toBytes("1"));
				put.addColumn(Bytes.toBytes("image"), Bytes.toBytes(cq2[6]), Bytes.toBytes("1"));
				put.addColumn(Bytes.toBytes("image"), Bytes.toBytes(cq2[7]), Bytes.toBytes("picData"));
				put.setDurability(Durability.SKIP_WAL);
				context.write(new Text(rowkey), put);
			}
		}
	}
	
	public static class MyReducer extends TableReducer<Text, Put, Writable> {
		public void reduce(Text key, Put put, Context context) throws IOException,InterruptedException {
			context.write(null, put);
		}
	}
	public static void main(String[] args) throws Exception {
		configuration = HBaseConfiguration.create();  
        
		Job job = Job.getInstance(configuration, "MRGenerate");
		job.setJarByClass(MRGenerate.class);
		//job.setJar("/home/gxy/workspace/GenerateHbaseData/MRGHbaseData.jar");
		job.setMapperClass(MyMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Put.class);
		FileInputFormat.setInputPaths(job, "/input/wc");
		
		TableMapReduceUtil.initTableReducerJob("passvehicle2", MyReducer.class, job);
		
		if (!job.waitForCompletion(true))
			return;
	}
	
	/*public static void getPutList() throws IOException {
		Table table = connection.getTable(TableName.valueOf(tableName));
		List<Put> putList = new ArrayList<Put>();
		for (int i = 1; i <= 100000000; i++) {
			int random = (int) (Math.random() * (100000 - 10000)) + 10000;
			// 抓拍时间
			long time = System.currentTimeMillis();
			// 设备编号
			String Id = String.valueOf(random + time);
			Random rand = new Random();
			// 违法类型
			String IllegalType = String.valueOf(rand.nextInt(20));
			// 车型
			String VehicleType = String.valueOf(rand.nextInt(11));
			// 车牌号
			String carNo = RandomStringUtils.randomAlphanumeric(5);
			String rowkey = "(" + Id.charAt(Id.length() - 1) + ")" + Id + "_" + String.valueOf(time) + "(" + i % 2
					+ ")_" + carNo + "_" + VehicleType + "_" + IllegalType;

			Put put = new Put(Bytes.toBytes(rowkey));
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[0]), Bytes.toBytes(Id));
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[1]),
					Bytes.toBytes(String.valueOf(rand.nextInt(11))));
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[2]), Bytes.toBytes(VehicleType));
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[3]), Bytes.toBytes(String.valueOf(rand.nextInt(3))));
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[4]), Bytes.toBytes(IllegalType));
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[5]), Bytes.toBytes(IllegalType));
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[6]),
					Bytes.toBytes(String.valueOf(rand.nextInt(3) + 1)));
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[7]),
					Bytes.toBytes(String.valueOf(rand.nextInt(11))));
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[8]), Bytes.toBytes("120km/h"));
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[9]), Bytes.toBytes(String.valueOf(rand.nextInt(9))));
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[10]),
					Bytes.toBytes(String.valueOf(rand.nextInt(7))));
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[11]), Bytes.toBytes("100nit"));
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[12]), Bytes.toBytes("5"));
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[13]), Bytes.toBytes("Country"));
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[14]), Bytes.toBytes(carNo));
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[15]), Bytes.toBytes("believe"));
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[16]), Bytes.toBytes("" + i + ""));
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[17]),
					Bytes.toBytes(String.valueOf(rand.nextInt(7))));
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[18]),
					Bytes.toBytes(String.valueOf(rand.nextInt(2))));
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[19]),
					Bytes.toBytes(String.valueOf(rand.nextInt(13))));
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[20]), Bytes.toBytes("90km/h"));
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[21]), Bytes.toBytes("2m"));
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[22]),
					Bytes.toBytes(String.valueOf(rand.nextInt(22))));
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[23]), Bytes.toBytes("VehicleLogoRecog"));
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[24]), Bytes.toBytes("VehicleSubLogoRecog"));
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[25]), Bytes.toBytes("VehicleModel"));
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[26]),
					Bytes.toBytes(String.valueOf(rand.nextInt(100000))));
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[27]),
					Bytes.toBytes(String.valueOf(rand.nextInt(9))));
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[28]),
					Bytes.toBytes(String.valueOf(rand.nextInt(6))));
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[29]),
					Bytes.toBytes(String.valueOf(rand.nextInt(3))));
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[30]),
					Bytes.toBytes(String.valueOf(rand.nextInt(3))));
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[31]),
					Bytes.toBytes(String.valueOf(rand.nextInt(3))));
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[32]),
					Bytes.toBytes(String.valueOf(rand.nextInt(3))));
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[33]),
					Bytes.toBytes(String.valueOf(rand.nextInt(3))));
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[34]),
					Bytes.toBytes(String.valueOf(rand.nextInt(2))));
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[35]), Bytes.toBytes("llegalTime"));
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[36]), Bytes.toBytes("IllegalTime"));
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[37]), Bytes.toBytes("3"));

			put.addColumn(Bytes.toBytes("image"), Bytes.toBytes(cq2[0]),
					Bytes.toBytes(String.valueOf(rand.nextInt(10))));
			put.addColumn(Bytes.toBytes("image"), Bytes.toBytes(cq2[1]),
					Bytes.toBytes(String.valueOf(rand.nextInt(2))));
			put.addColumn(Bytes.toBytes("image"), Bytes.toBytes(cq2[2]), Bytes.toBytes("300s"));
			put.addColumn(Bytes.toBytes("image"), Bytes.toBytes(cq2[3]), Bytes.toBytes("1"));
			put.addColumn(Bytes.toBytes("image"), Bytes.toBytes(cq2[4]), Bytes.toBytes("1"));
			put.addColumn(Bytes.toBytes("image"), Bytes.toBytes(cq2[5]), Bytes.toBytes("1"));
			put.addColumn(Bytes.toBytes("image"), Bytes.toBytes(cq2[6]), Bytes.toBytes("1"));
			put.addColumn(Bytes.toBytes("image"), Bytes.toBytes(cq2[7]), Bytes.toBytes("picData"));
			put.setDurability(Durability.SKIP_WAL);

			putList.add(put);

			// 批量插入
			if (i % 500000 == 0) {
				long startTime = System.currentTimeMillis();
            	System.out.println("已生成" + i + "条记录");
            	table.put(putList);
            	System.out.println("已插入第" + i + "条记录");
                long spend = System.currentTimeMillis() - startTime;
                System.out.println("insert data finished in " + spend + "ms");
            	putList.clear();
			}
		}
		table.close();
	}*/
}


package com.wuzhou.traffic;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Durability;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;

import com.kmlc.protobuf.OffenceSnapDataProtos.OffenceSnapData;
import com.kmlc.protobuf.OffenceSnapDataProtos.PicInfoModel;
import com.wuzhou.producer.deserializer.ProtocolDeserializer;

import kafka.serializer.StringDecoder;
import scala.Tuple2;

/**
 * Created by gxy on 16-7-27.
 */
public class ParseToHbase{
	static Logger log = Logger.getLogger("spark2hbase");
	static long MAXTIME = 2524579200000l;
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException, Exception {
		/*System.setProperty("HADOOP_USER_NAME", "hdfs");
    	String[] jars = new String[1];
    	jars[0] = "/home/gxy/traffic-0.2.jar";
        SparkConf conf = new SparkConf().setMaster("yarn-client").setAppName("ParseToHbase").setJars(jars);*/
        SparkConf conf = new SparkConf().setAppName("ParseToHbase");
//        conf.set("spark.kryo.registrator", "com.wuzhou.traffic.MyKryoRegistrator");
        conf.set("spark.streaming.kafka.maxRatePerPartition", "10000");
        conf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer");
        JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(5));
        HashMap<String, String> kafkaParams = new HashMap<String, String>();
        kafkaParams.put("metadata.broker.list", "slave-6:6667,slave-7:6667,slave-8:6667");
        //kafkaParams.put("metadata.broker.list", "localhost:9092");
        kafkaParams.put("group.id", "0");
        kafkaParams.put("auto.offset.reset", "smallest");
        String topics = "trafficdata";
        Set<String> topicsSet = new HashSet<String>(Arrays.asList(topics.split(",")));

        JavaPairDStream<String, OffenceSnapData> messages = KafkaUtils.createDirectStream(
                jssc,
                String.class,
                OffenceSnapData.class,
                StringDecoder.class,
                ProtocolDeserializer.class,
                kafkaParams,
                topicsSet
        );
        //bulk put
        /*Configuration hconf = HBaseConfiguration.create();
        JavaHBaseContext hbaseContext = new JavaHBaseContext(jssc, hconf);
        hbaseContext.streamBulkPut(messages, TableName.valueOf("passvehicle3"), new PutFunction());*/
        
        //直接foreachRDD
        Configuration hconf = HBaseConfiguration.create();
        hconf.set(TableOutputFormat.OUTPUT_TABLE,  "passvehicle3");
        hconf.setInt("hbase.client.write.buffer", 8*1024*1024);
        hconf.setInt("hbase.regionserver.handler.count", 100);
        
        final Configuration jobConf = new Configuration(hconf);
        jobConf.set("mapreduce.outputformat.class", TableOutputFormat.class.getName());
        messages.foreachRDD(new Function<JavaPairRDD<String,OffenceSnapData>, Void>() {

			private static final long serialVersionUID = 1L;

			public Void call(JavaPairRDD<String, OffenceSnapData> pairRDD) throws Exception {
				JavaRDD<OffenceSnapData> offenceSnapData = pairRDD.map(new Function<Tuple2<String, OffenceSnapData>, OffenceSnapData>() {

					private static final long serialVersionUID = 1L;

					public OffenceSnapData call(Tuple2<String, OffenceSnapData> tuple2) throws Exception {
						
						return tuple2._2();
					}
				});
				JavaPairRDD<ImmutableBytesWritable, Put> put = offenceSnapData.mapToPair(new PairFunction<OffenceSnapData, ImmutableBytesWritable, Put>() {
                    
					private static final long serialVersionUID = 1L;

					public Tuple2<ImmutableBytesWritable, Put> call(OffenceSnapData offenceSnapData) throws Exception {
                        return convertToPut(offenceSnapData);
                    }
                });

                put.saveAsNewAPIHadoopDataset(jobConf);
                System.out.println("Insert to Hbase Successfully");
				return null;
			}
		}); 
        
        //先转换再foreachRDD
        /*JavaDStream<OffenceSnapData> lines = messages.map(new Function<Tuple2<String, OffenceSnapData>, OffenceSnapData>() {
        	
			private static final long serialVersionUID = 1L;

			public OffenceSnapData call(Tuple2<String, OffenceSnapData> tuple2) throws Exception {
                return tuple2._2();
            }
        });
        //lines.print();
        lines.foreachRDD(new Function<JavaRDD<OffenceSnapData>, Void>() {
            
			private static final long serialVersionUID = 1L;
			
			public Void call(JavaRDD<OffenceSnapData> rdd) throws Exception {
				
                JavaPairRDD<ImmutableBytesWritable, Put> put = rdd.mapToPair(new PairFunction<OffenceSnapData, ImmutableBytesWritable, Put>() {
                    
					private static final long serialVersionUID = 1L;

					public Tuple2<ImmutableBytesWritable, Put> call(OffenceSnapData offenceSnapData) throws Exception {
                        return convertToPut(offenceSnapData);
                    }
                });
                
                Configuration hconf = HBaseConfiguration.create();
                hconf.set(TableOutputFormat.OUTPUT_TABLE,  "passvehicle3");
                hconf.setInt("hbase.client.write.buffer", 8*1024*1024);
                hconf.setInt("hbase.regionserver.handler.count", 100);
                
                Configuration jobConf = new Configuration(hconf);
                jobConf.set("mapreduce.outputformat.class", TableOutputFormat.class.getName());
                
                put.saveAsNewAPIHadoopDataset(jobConf);
                System.out.println("Insert to Hbase Successfully");
                return null;
            }
        });*/

//        jssc.start();
//        jssc.awaitTermination();
    }

    private static Tuple2<ImmutableBytesWritable, Put> convertToPut(OffenceSnapData offenceSnapData) {
        String[] cq1 = new String[]{"Id", "DriveChan", "VehicleType1", "VehicleAttribute", "IllegalType1",
                "IllegalSubType", "PostPicNo", "ChanIndex", "SpeedLimit", "PlateType", "PlateColor", "Bright",
                "LicenseLen", "Country", "License", "Believe", "Index", "VehicleType2", "ColorDepth", "Color",
                "Speed", "Length", "IllegalType2", "VehicleLogoRecog", "VehicleSubLogoRecog", "VehicleModel",
                "MonitoringSiteID", "Dir", "DetectType", "PilotSafebelt", "CopilotSafebelt", "PilotSubVisor",
                "CopilotSubVisor", "PilotCall", "AlarmDataType", "llegalTime", "IllegalTime", "PicNum"};
        String[] cq2 = new String[]{"Type", "PicRecogMode", "RedLightTime", "X", "Y", "Width", "Height", "Data"};

        Random rand = new Random();
		String Id = offenceSnapData.getId();
		Long time = MAXTIME - Long.valueOf(offenceSnapData.getLlegalTime());
		String license = offenceSnapData.getPlateInfo().getLicense();
		String vehicleType = String.valueOf(offenceSnapData.getVehicleType());
		String illegalType = String.valueOf(offenceSnapData.getIllegalType());
		String rowkey = computeRowkey(time) + "|" + String.valueOf(time) + "(" + rand.nextInt(2) + ")" + license + "_"
				+ vehicleType + "(" + rand.nextInt(10) + ")" + Id + "_" + illegalType;
		Put put = new Put(Bytes.toBytes(rowkey));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[0]), Bytes.toBytes(Id));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[1]), Bytes.toBytes(String.valueOf(offenceSnapData.getDriveChan())));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[2]), Bytes.toBytes(vehicleType));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[3]), Bytes.toBytes(String.valueOf(offenceSnapData.getVehicleAttribute())));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[4]), Bytes.toBytes(illegalType));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[5]), Bytes.toBytes(offenceSnapData.getIllegalSubType()));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[6]), Bytes.toBytes(String.valueOf(offenceSnapData.getPostPicNo())));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[7]), Bytes.toBytes(String.valueOf(offenceSnapData.getChanIndex())));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[8]), Bytes.toBytes(String.valueOf(offenceSnapData.getSpeedLimit())));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[9]), Bytes.toBytes(String.valueOf(offenceSnapData.getPlateInfo().getPlateType())));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[10]), Bytes.toBytes(String.valueOf(offenceSnapData.getPlateInfo().getColor())));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[11]), Bytes.toBytes(String.valueOf(offenceSnapData.getPlateInfo().getBright())));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[12]), Bytes.toBytes(String.valueOf(offenceSnapData.getPlateInfo().getLicenseLen())));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[13]), Bytes.toBytes(String.valueOf(offenceSnapData.getPlateInfo().getCountry())));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[14]), Bytes.toBytes(license));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[15]), Bytes.toBytes(String.valueOf(offenceSnapData.getPlateInfo().getLicense())));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[16]), Bytes.toBytes(String.valueOf(offenceSnapData.getVehicleInfo().getIndex())));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[17]), Bytes.toBytes(String.valueOf(offenceSnapData.getVehicleInfo().getVehicleType())));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[18]), Bytes.toBytes(String.valueOf(offenceSnapData.getVehicleInfo().getColorDepth())));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[19]), Bytes.toBytes(String.valueOf(offenceSnapData.getVehicleInfo().getColor())));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[20]), Bytes.toBytes(String.valueOf(offenceSnapData.getVehicleInfo().getSpeed())));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[21]), Bytes.toBytes(String.valueOf(offenceSnapData.getVehicleInfo().getLength())));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[22]), Bytes.toBytes(String.valueOf(offenceSnapData.getVehicleInfo().getIllegalType())));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[23]), Bytes.toBytes(String.valueOf(offenceSnapData.getVehicleInfo().getVehicleLogoRecog())));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[24]), Bytes.toBytes(String.valueOf(offenceSnapData.getVehicleInfo().getVehicleSubLogoRecog())));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[25]), Bytes.toBytes(String.valueOf(offenceSnapData.getVehicleInfo().getVehicleModel())));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[26]), Bytes.toBytes(offenceSnapData.getMonitoringSiteID()));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[27]), Bytes.toBytes(String.valueOf(offenceSnapData.getDir())));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[28]), Bytes.toBytes(String.valueOf(offenceSnapData.getDetectType())));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[29]), Bytes.toBytes(String.valueOf(offenceSnapData.getPilotSafebelt())));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[30]), Bytes.toBytes(String.valueOf(offenceSnapData.getCopilotSafebelt())));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[31]), Bytes.toBytes(String.valueOf(offenceSnapData.getPilotSubVisor())));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[32]), Bytes.toBytes(String.valueOf(offenceSnapData.getCopilotSubVisor())));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[33]), Bytes.toBytes(String.valueOf(offenceSnapData.getPilotCall())));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[34]), Bytes.toBytes(String.valueOf(offenceSnapData.getAlarmDataType())));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[35]), Bytes.toBytes(offenceSnapData.getLlegalTime()));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[36]), Bytes.toBytes(String.valueOf(offenceSnapData.getIllegalTimer())));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[37]), Bytes.toBytes(String.valueOf(offenceSnapData.getPicNum())));

        for(PicInfoModel picInfo : offenceSnapData.getPicInfoList()) {
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq2[0]), Bytes.toBytes(String.valueOf(picInfo.getType())));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq2[1]), Bytes.toBytes(String.valueOf(picInfo.getPicRecogMode())));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq2[2]), Bytes.toBytes(String.valueOf(picInfo.getRedLightTime())));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq2[3]), Bytes.toBytes(String.valueOf(picInfo.getPlateRect().getX())));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq2[4]), Bytes.toBytes(String.valueOf(picInfo.getPlateRect().getY())));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq2[5]), Bytes.toBytes(String.valueOf(picInfo.getPlateRect().getWidth())));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq2[6]), Bytes.toBytes(String.valueOf(picInfo.getPlateRect().getHeight())));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq2[7]), picInfo.getData().toByteArray());
            /*log.info(String.valueOf(picInfo.getType()));
            log.info(String.valueOf(picInfo.getPicRecogMode()));
            log.info(String.valueOf(picInfo.getRedLightTime()));
            log.info(String.valueOf(picInfo.getPlateRect().getX()));
            log.info(String.valueOf(picInfo.getPlateRect().getY()));
            log.info(String.valueOf(picInfo.getPlateRect().getWidth()));
            log.info(String.valueOf(picInfo.getPlateRect().getHeight()));
            log.info(picInfo.getData());*/
        }
        put.setDurability(Durability.SKIP_WAL);

        Tuple2<ImmutableBytesWritable, Put> t2 = new Tuple2<ImmutableBytesWritable, Put>(new ImmutableBytesWritable(), put);
        return t2;
    }
    
    public static String computeRowkey(long time) {
		String partitionId = String.valueOf(time % 100);
		if (partitionId.length() < 2) {
			partitionId = "0" + partitionId;
		}
		return partitionId;
	}
    
    public static class PutFunction implements Function<Tuple2<String, OffenceSnapData>, Put> {

        private static final long serialVersionUID = 1L;

        public Put call(Tuple2<String, OffenceSnapData> v) throws Exception {
        	OffenceSnapData  offenceSnapData = v._2();
        	
        	String[] cq1 = new String[]{"Id", "DriveChan", "VehicleType1", "VehicleAttribute", "IllegalType1",
                     "IllegalSubType", "PostPicNo", "ChanIndex", "SpeedLimit", "PlateType", "PlateColor", "Bright",
                     "LicenseLen", "Country", "License", "Believe", "Index", "VehicleType2", "ColorDepth", "Color",
                     "Speed", "Length", "IllegalType2", "VehicleLogoRecog", "VehicleSubLogoRecog", "VehicleModel",
                     "MonitoringSiteID", "Dir", "DetectType", "PilotSafebelt", "CopilotSafebelt", "PilotSubVisor",
                     "CopilotSubVisor", "PilotCall", "AlarmDataType", "llegalTime", "IllegalTime", "PicNum"};
            String[] cq2 = new String[]{"Type", "PicRecogMode", "RedLightTime", "X", "Y", "Width", "Height", "Data"};

            Random rand = new Random();
     		String Id = offenceSnapData.getId();
     		Long time = MAXTIME - Long.valueOf(offenceSnapData.getLlegalTime());
     		String license = offenceSnapData.getPlateInfo().getLicense();
     		String vehicleType = String.valueOf(offenceSnapData.getVehicleType());
     		String illegalType = String.valueOf(offenceSnapData.getIllegalType());
     		String rowkey = computeRowkey(time) + "|" + String.valueOf(time) + "(" + rand.nextInt(2) + ")" + license + "_"
     				+ vehicleType + "(" + rand.nextInt(10) + ")" + Id + "_" + illegalType;
     		Put put = new Put(Bytes.toBytes(rowkey));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[0]), Bytes.toBytes(Id));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[1]), Bytes.toBytes(String.valueOf(offenceSnapData.getDriveChan())));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[2]), Bytes.toBytes(vehicleType));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[3]), Bytes.toBytes(String.valueOf(offenceSnapData.getVehicleAttribute())));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[4]), Bytes.toBytes(illegalType));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[5]), Bytes.toBytes(offenceSnapData.getIllegalSubType()));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[6]), Bytes.toBytes(String.valueOf(offenceSnapData.getPostPicNo())));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[7]), Bytes.toBytes(String.valueOf(offenceSnapData.getChanIndex())));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[8]), Bytes.toBytes(String.valueOf(offenceSnapData.getSpeedLimit())));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[9]), Bytes.toBytes(String.valueOf(offenceSnapData.getPlateInfo().getPlateType())));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[10]), Bytes.toBytes(String.valueOf(offenceSnapData.getPlateInfo().getColor())));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[11]), Bytes.toBytes(String.valueOf(offenceSnapData.getPlateInfo().getBright())));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[12]), Bytes.toBytes(String.valueOf(offenceSnapData.getPlateInfo().getLicenseLen())));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[13]), Bytes.toBytes(String.valueOf(offenceSnapData.getPlateInfo().getCountry())));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[14]), Bytes.toBytes(license));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[15]), Bytes.toBytes(String.valueOf(offenceSnapData.getPlateInfo().getLicense())));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[16]), Bytes.toBytes(String.valueOf(offenceSnapData.getVehicleInfo().getIndex())));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[17]), Bytes.toBytes(String.valueOf(offenceSnapData.getVehicleInfo().getVehicleType())));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[18]), Bytes.toBytes(String.valueOf(offenceSnapData.getVehicleInfo().getColorDepth())));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[19]), Bytes.toBytes(String.valueOf(offenceSnapData.getVehicleInfo().getColor())));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[20]), Bytes.toBytes(String.valueOf(offenceSnapData.getVehicleInfo().getSpeed())));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[21]), Bytes.toBytes(String.valueOf(offenceSnapData.getVehicleInfo().getLength())));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[22]), Bytes.toBytes(String.valueOf(offenceSnapData.getVehicleInfo().getIllegalType())));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[23]), Bytes.toBytes(String.valueOf(offenceSnapData.getVehicleInfo().getVehicleLogoRecog())));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[24]), Bytes.toBytes(String.valueOf(offenceSnapData.getVehicleInfo().getVehicleSubLogoRecog())));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[25]), Bytes.toBytes(String.valueOf(offenceSnapData.getVehicleInfo().getVehicleModel())));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[26]), Bytes.toBytes(offenceSnapData.getMonitoringSiteID()));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[27]), Bytes.toBytes(String.valueOf(offenceSnapData.getDir())));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[28]), Bytes.toBytes(String.valueOf(offenceSnapData.getDetectType())));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[29]), Bytes.toBytes(String.valueOf(offenceSnapData.getPilotSafebelt())));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[30]), Bytes.toBytes(String.valueOf(offenceSnapData.getCopilotSafebelt())));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[31]), Bytes.toBytes(String.valueOf(offenceSnapData.getPilotSubVisor())));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[32]), Bytes.toBytes(String.valueOf(offenceSnapData.getCopilotSubVisor())));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[33]), Bytes.toBytes(String.valueOf(offenceSnapData.getPilotCall())));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[34]), Bytes.toBytes(String.valueOf(offenceSnapData.getAlarmDataType())));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[35]), Bytes.toBytes(offenceSnapData.getLlegalTime()));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[36]), Bytes.toBytes(String.valueOf(offenceSnapData.getIllegalTimer())));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq1[37]), Bytes.toBytes(String.valueOf(offenceSnapData.getPicNum())));
            for(PicInfoModel picInfo : offenceSnapData.getPicInfoList()) {
                 put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq2[0]), Bytes.toBytes(String.valueOf(picInfo.getType())));
                 put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq2[1]), Bytes.toBytes(String.valueOf(picInfo.getPicRecogMode())));
                 put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq2[2]), Bytes.toBytes(String.valueOf(picInfo.getRedLightTime())));
                 put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq2[3]), Bytes.toBytes(String.valueOf(picInfo.getPlateRect().getX())));
                 put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq2[4]), Bytes.toBytes(String.valueOf(picInfo.getPlateRect().getY())));
                 put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq2[5]), Bytes.toBytes(String.valueOf(picInfo.getPlateRect().getWidth())));
                 put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq2[6]), Bytes.toBytes(String.valueOf(picInfo.getPlateRect().getHeight())));
                 put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(cq2[7]), picInfo.getData().toByteArray());
                 /*log.info(String.valueOf(picInfo.getType()));
                 log.info(String.valueOf(picInfo.getPicRecogMode()));
                 log.info(String.valueOf(picInfo.getRedLightTime()));
                 log.info(String.valueOf(picInfo.getPlateRect().getX()));
                 log.info(String.valueOf(picInfo.getPlateRect().getY()));
                 log.info(String.valueOf(picInfo.getPlateRect().getWidth()));
                 log.info(String.valueOf(picInfo.getPlateRect().getHeight()));
                 log.info(picInfo.getData());*/
            }
            put.setDurability(Durability.SKIP_WAL);
        	
        	return put;
        }
    }
}

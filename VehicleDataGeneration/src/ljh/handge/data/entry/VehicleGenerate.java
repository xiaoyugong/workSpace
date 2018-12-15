package ljh.handge.data.entry;

import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import ljh.handge.data.util.PropertiesReader;

import org.apache.kafka.clients.producer.KafkaProducer;

public class VehicleGenerate {
	
	static Properties props;
	static String TOPIC;
	static String FNAME = "./src/config.properties";
	static String  BOOTSTRAP_SERVERS;
	static int VEHICLE_NUM = 0;
	static int PROVINCE = -1;
	static double PROVINCE_RATIO = 0;
	static KafkaProducer<byte[], byte[]> kafkaProducer;
	static Random random;
	static{
		props = new Properties();
		try {
			PropertiesReader.init(FNAME);
			BOOTSTRAP_SERVERS = PropertiesReader.getProperty("KAFKA_SERVERS");
			VEHICLE_NUM = Integer.valueOf(PropertiesReader.getProperty("VEHICLE_NUMBER"));
			PROVINCE = Integer.valueOf(PropertiesReader.getProperty("PROVINCE"));
			PROVINCE_RATIO = Double.valueOf(PropertiesReader.getProperty("P_RATIO"));
			TOPIC = PropertiesReader.getProperty("TOPIC_A");
		} catch (Exception e) {
			e.printStackTrace();
		}
		props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
		props.put("key.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
		props.put("request.required.acks", "0");
		props.put("partitioner.class", "ljh.handge.data.util.CostomPartitioner");
	}
	
	public static void main(String[] args) throws InterruptedException {
		System.out.println("------生成程序正在启动------");
		int numThread = 2;
		ExecutorService executor = Executors.newFixedThreadPool(numThread);
		executor.submit(new GenerateThread("normal"));
		executor.submit(new GenerateThread("illegal"));
//		new Thread(new GenerateThread("normal")).start();
//		new Thread(new GenerateThread("illegal")).start();
		executor.shutdown();
		while(!executor.awaitTermination(1, TimeUnit.MILLISECONDS)) {
		}
		System.out.println("------生成程序执行完毕------");
	}
}
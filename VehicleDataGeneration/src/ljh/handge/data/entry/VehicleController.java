package ljh.handge.data.entry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.serializer.Decoder;
import kafka.serializer.StringDecoder;
import kafka.utils.VerifiableProperties;

import org.apache.zookeeper.ZooKeeper;

import kafka.javaapi.consumer.ConsumerConnector;
import ljh.handge.data.bean.Gate;
import ljh.handge.data.bean.GateFactory;
import ljh.handge.data.util.PropertiesReader;
import ljh.handge.data.util.ZookeeperConnector;
import ljh.handge.pool.common.PoolConfig;
import ljh.handge.pool.kafka.KafkaConnectionPool;

public class VehicleController {

	private static Properties props = null;
	private static ConsumerConnector consumer;
	public static Random random;
	public static String FNAME = "src/config.properties";
	public static String BOOTSTRAP_SERVERS, ZOOKEEPER_CONNECT, GROUP_ID = null;
	public static Map<String, Gate> gates;
	public static KafkaConnectionPool pool;
	public static String TOPIC;
	public static String KAFKA_GROUP;
	public static String TOPIC_FINAL;
	public static String IMAGE_FILE;

	static {
		random = new Random();
		props = new Properties();
		GateFactory gf = new GateFactory();
		gates = gf.findAll();

		try {
			PropertiesReader.init(FNAME);
			BOOTSTRAP_SERVERS = PropertiesReader.getProperty("KAFKA_SERVERS");
			ZOOKEEPER_CONNECT = PropertiesReader.getProperty("ZOOKEEPER_CONNECT_C");
			GROUP_ID = PropertiesReader.getProperty("GROUP_ID");
			TOPIC = PropertiesReader.getProperty("TOPIC_A");
			KAFKA_GROUP = PropertiesReader.getProperty("GROUP_ID");
			TOPIC_FINAL = PropertiesReader.getProperty("TOPIC_B");
			IMAGE_FILE = PropertiesReader.getProperty("IMAGE_PATH");
		} catch (Exception e) {
			e.printStackTrace();
		}

		props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
		props.put("key.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
		props.put("request.required.acks", "0");
		props.put("partitioner.class", "ljh.handge.data.util.CostomPartitioner");
		props.put("compression.codec", "snappy");
		props.put("batch.num.messages", "100");
		props.put("max.request.size", "1000973460");

		props.put("zookeeper.connect", ZOOKEEPER_CONNECT);
		props.put("group.id", GROUP_ID);
		props.put("auto.offset.reset", "largest");
		props.put("auto.commit.enable", "true");
		props.put("producer.type", "async");
		props.put("auto.commit.interval.ms", "30000");
		props.put("serializer.class", "kafka.serializer.StringEncoder");

		ConsumerConfig config = new ConsumerConfig(props);
		consumer = Consumer.createJavaConsumerConnector(config);
	}

	public static void main(String[] args) throws InterruptedException {
        PoolConfig poolconfig = new PoolConfig();
        poolconfig.setMaxTotal(30);
        poolconfig.setMaxIdle(20);
        poolconfig.setMaxWaitMillis(10000);
        poolconfig.setTestOnBorrow(true);
        pool = new KafkaConnectionPool(poolconfig, props);
		ZooKeeper zk = ZookeeperConnector.getZkConnector();
		try {
			ZookeeperConnector.rmr(zk, "/consumers/" + KAFKA_GROUP);
		} catch (Exception e) {
			e.printStackTrace();
		}
		zk.close();
		consume("Multiple-thread", Integer.valueOf(args[0]));
	}
	
	public static void consume(String s, int threadNum){
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(TOPIC, threadNum);
		Decoder<String> keyDecoder = new StringDecoder(new VerifiableProperties());
		Decoder<String> valueDecoder = new StringDecoder(new VerifiableProperties());
		ConsumerConfig config = new ConsumerConfig(props);
		consumer = Consumer.createJavaConsumerConnector(config);
		Map<String, List<KafkaStream<String, String>>> createMessageStreams = consumer.createMessageStreams(topicCountMap, keyDecoder, valueDecoder);
		List<KafkaStream<String, String>> streams = createMessageStreams.get(TOPIC);
		ExecutorService executor = Executors.newFixedThreadPool(threadNum);
		for(final KafkaStream<String, String> stream : streams){
			executor.submit(new ControllerThread(stream));
		}
	}
}

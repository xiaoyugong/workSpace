package ljh.handge.data.testing;

import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import ljh.handge.pool.common.PoolConfig;
import ljh.handge.pool.kafka.KafkaConnectionPool;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class ProducerTest {
	
	public static KafkaConnectionPool pool;

	public static void main(String[] args){
		
		Random random = new Random();
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "lmaster:9092,lworker:9092,lslaver:9092");
        props.setProperty("producer.type", "async");
        props.setProperty("key.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
        props.setProperty("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
        props.setProperty("request.required.acks", "0");
        props.setProperty("partitioner.class", "ljh.handge.data.util.CostomPartitioner");
        
//        KafkaProducer<byte[], byte[]> kafkaProducer = new KafkaProducer<byte[], byte[]>(props);	
        
        PoolConfig config = new PoolConfig();
        config.setMaxTotal(10);
        config.setMaxIdle(5);
        config.setMaxWaitMillis(10000);
//        config.setTestOnBorrow(true);
        pool = new KafkaConnectionPool(config, props);
        Producer<byte[], byte[]> producer = pool.getConnection();
        String s = "hello";
        for (int i = 0; i < 1000; i++) {
        	String p = String.valueOf(random.nextInt(70)) + "|" + "plate";
        	ProducerRecord<byte[], byte[]> kafkaRecord = new ProducerRecord<byte[], byte[]>("mytest3", p.getBytes(), s.getBytes());
        	try {
//				RecordMetadata res = kafkaProducer.send(kafkaRecord).get();
        		RecordMetadata res = producer.send(kafkaRecord).get();
        		System.out.println(p);
//				System.out.println(res);
        		
//				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
        }
//        kafkaProducer.close();
	}
}

package ljh.handge.data.testing;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
import kafka.serializer.Decoder;
import kafka.serializer.StringDecoder;
import kafka.utils.VerifiableProperties;
import ljh.handge.data.bean.Vehicle;

import org.apache.kafka.clients.producer.Producer;

public class ConsumerTest {
	
	private static ConsumerConnector consumer;
	
	static{
		Properties props = new Properties();
		props.put("zookeeper.connect", "Dataflow-1:2181,Dataflow-2:2181,Dataflow-3:2181,Dataflow-4:2181,Dataflow-5:2181");
		props.put("group.id", "ljh");
		props.put("auto.offset.reset", "largest");
		props.put("auto.commit.enable", "true");
//		props.put("producer.type", "async");
		props.put("auto.commit.interval.ms", "30000");
		props.put("serializer.class", "kafka.serializer.StringEncoder");
		ConsumerConfig config = new ConsumerConfig(props);
		consumer = Consumer.createJavaConsumerConnector(config);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static void consume() throws InterruptedException {
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put("solar2", 1);

		Decoder<String> keyDecoder = new StringDecoder(new VerifiableProperties());
		Decoder<String> valueDecoder = new StringDecoder(new VerifiableProperties());

		Map<String, List<KafkaStream<String, String>>> createMessageStreams = consumer.createMessageStreams(topicCountMap, keyDecoder, valueDecoder);
		for (Iterator<String> it = createMessageStreams.keySet().iterator(); it.hasNext(); ) {
			String key = it.next();
			System.out.println("key: " + key);
			List<KafkaStream<String, String>> values = createMessageStreams.get(key);
			for (KafkaStream<String, String> value : values) {
				ConsumerIterator<String, String> consumerIt = value.iterator();
				
				while (consumerIt.hasNext()) {
					MessageAndMetadata<String, String> data = consumerIt.next();
					System.out.println(data.message().toString());

//					Thread.sleep(1000);
				}
			}
		}
	}

}

package ljh.handge.data.entry;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import ljh.handge.data.bean.VehicleFactory;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class GenerateThread implements Runnable{
	
	static Properties props = VehicleGenerate.props;
	static String TOPIC = VehicleGenerate.TOPIC;
	static String  BOOTSTRAP_SERVERS = VehicleGenerate.BOOTSTRAP_SERVERS;
	static int VEHICLE_NUM = VehicleGenerate.VEHICLE_NUM;
	static int PROVINCE = VehicleGenerate.PROVINCE;
	static double PROVINCE_RATIO = VehicleGenerate.PROVINCE_RATIO;
	static Random random;
	KafkaProducer<byte[], byte[]> kafkaProducer;
	private String generateType = "";
	static VehicleFactory vf;
	
	public GenerateThread(String s){
		kafkaProducer = new KafkaProducer<byte[], byte[]>(props);
		random = new Random();
		vf = new VehicleFactory();
		this.generateType = s;
	}

	public void run() {
		System.out.println(Thread.currentThread().getName());
		if(generateType.equals("normal")){
			for(int i = 0 ; i < 10 ; i++){
//			for(int i=0 ; i < 1 ; i++){
				generateNormal(kafkaProducer);
				try {
					Thread.sleep(20000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		else if(generateType.equals("illegal")){
			for(int i = 1 ; ; i++) {
				System.out.println("IllegalVehicles Bulk: " + i);
				generateIllegal(kafkaProducer);
				try {
					Thread.sleep(1200000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		else{
			System.out.println("parameter is error");
		}
		kafkaProducer.close();
	}
	
	public static void generateNormal(KafkaProducer<byte[], byte[]> kafkaProducer){		
		String[] vehicles_A = vf.startProduction(VEHICLE_NUM / 10, PROVINCE, PROVINCE_RATIO, "0");
//		String[] vehicles_A = vf.startProduction(VEHICLE_NUM, PROVINCE, PROVINCE_RATIO, "18");
		for(String vehicleInfo : vehicles_A){
			String value = vehicleInfo;
			ProducerRecord<byte[], byte[]> kafkaRecord = new ProducerRecord<byte[], byte[]>(TOPIC, "18".getBytes(), value.getBytes());
			kafkaProducer.send(kafkaRecord);
			System.out.println(vehicleInfo);
		}
	}
	
	public static void generateIllegal(KafkaProducer<byte[], byte[]> kafkaProducer){
		VehicleFactory vf = new VehicleFactory();
		String[] province = {"川","川","京","云","川"};
		String[] zones = {"I","G","H","B","G"};
		String[] p1 = new String[3];
		String[] p2 = new String[2];
		int i = 0;
		// 设置一批次的非法车辆数number(建议设置为5的倍数)
		int number = 5;
		while(i < number){
			//随机生成
//			int code = random.nextInt(5);
			//定向顺序生成（条件：province和zones数组长度=生成数）
			int code = i % 5;
			
			String p = province[code] + zones[code] + String.format("%05d", random.nextInt(100000));
			if(i < (number / 5 * 3)){
				p1[i] = p;
			}
			else{
				p2[i - (number / 5 * 3)] = p;
			}
			i++;
		}
		List<String[]> list = new ArrayList<String[]>();
		list.add(vf.startProduction(p1, "0"));
		list.add(vf.startProduction(p1, "0"));
		list.add(vf.startProduction(p2, "0"));
		list.add(vf.startProduction(p2, "0"));
		for(String[] vehicleInfos : list){
			for(String vehicleInfo : vehicleInfos){
				String value = vehicleInfo;
				ProducerRecord<byte[], byte[]> kafkaRecord = new ProducerRecord<byte[], byte[]>(TOPIC, "0".getBytes(), value.getBytes());
				kafkaProducer.send(kafkaRecord);
				System.out.println(vehicleInfo);
			}
		}
	}
}

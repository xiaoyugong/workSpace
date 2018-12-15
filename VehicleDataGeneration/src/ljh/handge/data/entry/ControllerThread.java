package ljh.handge.data.entry;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.log4j.Logger;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;
import ljh.handge.data.bean.Gate;
import ljh.handge.data.bean.Vehicle;
import ljh.handge.data.bean.VehicleFactory;
import ljh.handge.data.util.SendToFinal;
import ljh.handge.pool.kafka.KafkaConnectionPool;

public class ControllerThread implements Runnable {
	
	private KafkaStream<String, String> stream;
	public static Map<String, Gate> gates = VehicleController.gates;
	public static Random random = VehicleController.random;
	public static String TOPIC = VehicleController.TOPIC;
	public static KafkaConnectionPool pool = VehicleController.pool;
	//	测试用文件流
	public static FileOutputStream fos;
	private static Logger log = Logger.getLogger(ControllerThread.class);
	
    public ControllerThread(KafkaStream<String, String> stream) {  
        this.stream = stream;
        //	测试用文件流
//		try {
//			fos = new FileOutputStream("/home/liujihao/vehicleinfo.txt", false);
//		} catch (FileNotFoundException e1) {
//			e1.printStackTrace();
//		}
    }
    
    public void run(){
    	ConsumerIterator<String, String> it = stream.iterator();  
    	while(it.hasNext()) {
			MessageAndMetadata<String, String> data = it.next();
			Vehicle vehicle = MessageToObject(data.message());

			if(checkTime(vehicle)) {
				sendToSelf(vehicle);
			}
			else {
				if(checkIllegalTimes(vehicle)) {
					sendToTopic(vehicle);
					setNewInfo(vehicle);
					sendToNext(vehicle);
				}
			}
			/**
			 * 连接池预警处理(正式代码删除)
			 */
//			if(pool.getNumActive()>= 2 || pool.getNumIdle()>=2)
//				log.info("error!  " + pool.getNumActive() + " " + pool.getNumIdle());
    	}
    }
    
	public static Vehicle MessageToObject(String info) {
		String[] infos = info.split(",");
		Vehicle vehicle = new Vehicle();
		vehicle.setCurrentGate(infos[0]);
		vehicle.setPreviousGate(infos[1]);
		vehicle.setPlateNumber(infos[2]);
		vehicle.setType(infos[3]);
		vehicle.setColor(infos[4]);
		vehicle.setBrand(infos[5]);
		vehicle.setSpeed(infos[6]);
		vehicle.setDesireTime(infos[7]);
		vehicle.setIllegalTimes(infos[8]);
		return vehicle;
	}
	
	//返回当前卡口和上一个卡口的不同邻接卡口的集合
	public static List<String> compare(String[] t1, String[] t2) {
		List<String> list1 = Arrays.asList(t1);
		List<String> list2 = new ArrayList<String>();
		for (String t : t2) {
			if (!list1.contains(t)) {
				list2.add(t);
			}
		}
		return list2;
	}
	
	//返回选定的下个卡口在卡口数组中的索引值，获取该值是为了获取对应的距离
	public static int getIndex(String[] sa, String s) {
		int i;
		for(i = 0; i < sa.length; i++) {
			if(sa[i].equals(s))
				break;
		}
		return i;
	}

	//计算并设置新的卡口、速度、期望时间
	public static void setNewInfo(Vehicle vehicle) {
		String next_gate_id;
		double distance;
		int g_code = 0;
		String old_gate_id = vehicle.getCurrentGate();
		String prevois_gate = vehicle.getPreviousGate();
		//备用入口集合
		String[] reachs = {"1", "17", "19", "21", "22", "23", "24", "25", "26"};
		if(old_gate_id.equals("0")){
//			next_gate_id = "1";
			//使用备用入口集合
			next_gate_id = reachs[random.nextInt(reachs.length)];
			distance = 1.0 / 100;
		} else {
			String agates = gates.get(old_gate_id).getNextGates();
			String[] current_agates = (old_gate_id + "," + agates).split(",");
			String[] previous_agates = (prevois_gate + "," + gates.get(prevois_gate).getNextGates()).split(",");
			List<String> diff_agates = compare(previous_agates, current_agates);
			if(diff_agates.size() == 0){
				next_gate_id = current_agates[current_agates.length - 1];
			}
			else {
				do {
				next_gate_id = diff_agates.get(random.nextInt(diff_agates.size()));
				}while(old_gate_id.equals(next_gate_id));
			}
			String[] next_distances = gates.get(old_gate_id).getDistances().split(",");
			g_code = getIndex(agates.split(","), next_gate_id);
//			log.info("agates: " + agates + "  next_gate_id: " + next_gate_id);
//			log.info("g_code: " + g_code);
			distance = Double.valueOf(next_distances[g_code]) / 100;
		}
		String speedlimit;
		String v_type = vehicle.getType();
		Gate next_gate = gates.get(next_gate_id);
		Gate old_gate = gates.get(old_gate_id);
		if(next_gate.getType().equals("0") || old_gate.getType().equals("0")) {
			speedlimit =  next_gate.getSpeedlimit().contains(",") ?  old_gate.getSpeedlimit(): next_gate.getSpeedlimit();
		} else {
			speedlimit = next_gate.getSpeedlimit().split(",")[Integer.valueOf(v_type)];
		}
		String newSpeed = VehicleFactory.generateSpeed(v_type, speedlimit);
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currentTime = new Date();
		
		double speed;
		speed = Double.valueOf(newSpeed);
		double needTime;
		needTime = (distance / speed) * 3600000;
		Date desireTime = new Date((long)(currentTime.getTime() + needTime));
//		log.info(" currentTime:" + currentTime + "newSpeed:" + newSpeed + 
//				"\n distance:" + distance + "needTime:" + needTime + 
//				"\n desireTime:" + desireTime + df.format(desireTime));
		vehicle.setSpeed(newSpeed);
		vehicle.setPreviousGate(old_gate_id);
		vehicle.setDesireTime(df.format(desireTime));
		vehicle.setCurrentGate(next_gate_id);
	}

	public static boolean checkTime(Vehicle vehicle) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date desireTime;
		try {
			desireTime = df.parse(vehicle.getDesireTime());
			if(desireTime.getTime() <= new Date().getTime()){
				return false;
			}
			else
				return true;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean checkIllegalTimes(Vehicle vehicle) {
		int illegalTimes = Integer.valueOf(vehicle.getIllegalTimes());
		if(illegalTimes > 0) {
			if(illegalTimes > 10) {
				return false;
			} else {
				vehicle.setIllegalTimes(String.valueOf(illegalTimes + 1));
				return true;
			}
		} else {
			return true;
		}
	}

	public static void sendToSelf(Vehicle vehicle) {
		String value = vehicle.toString();
		String currentGate = vehicle.getCurrentGate();
		String key = currentGate + "|" + vehicle.getPlateNumber();
		ProducerRecord<byte[], byte[]> kafkaRecord = new ProducerRecord<byte[], byte[]>(TOPIC, key.getBytes(), value.getBytes());
		Producer<byte[], byte[]> producer = pool.getConnection();
		try {
			RecordMetadata res = producer.send(kafkaRecord).get(1000, TimeUnit.MILLISECONDS);
//			log.info("self: " + res);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		pool.returnConnection(producer);
	}

	public static void sendToNext(Vehicle vehicle) {
		String value = vehicle.toString();
		String nextGate = vehicle.getCurrentGate();
		String key = nextGate + "|" + vehicle.getPlateNumber();
		ProducerRecord<byte[], byte[]> kafkaRecord = new ProducerRecord<byte[], byte[]>(TOPIC, key.getBytes(), value.getBytes());
		Producer<byte[], byte[]> producer = pool.getConnection();
		try {
			RecordMetadata res = producer.send(kafkaRecord).get(1000, TimeUnit.MILLISECONDS);
//			log.info("next: " + res);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		pool.returnConnection(producer);
	}

	public static void sendToTopic(Vehicle vehicle) {
		/**
		 * 发送到Topic-B（使用ProtocalBuffer）
		 */
		if(!vehicle.getCurrentGate().equals("0")){
			try {
				SendToFinal.getData(vehicle);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
		/**
		 * 本地测试，发送到文件系统：/home/liujihao/vehicleinfo.txt
		 */
//		String value = null;
//		if(!vehicle.getCurrentGate().equals("0")) {
//			value = vehicle.toArchive() + "," + vehicle.getIllegalTimes() + "\n";
//			try {
//				fos.write(value.getBytes());
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
	}
}

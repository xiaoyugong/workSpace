package ljh.handge.data.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.log4j.Logger;

import protocol.OffenceSnapDataProtos;
import ljh.handge.data.bean.Gate;
import ljh.handge.data.bean.Vehicle;
import ljh.handge.data.entry.VehicleController;
import ljh.handge.pool.common.PoolConfig;
import ljh.handge.pool.kafka.KafkaConnectionPool;

public class SendToFinal {

	public static HashMap<String,ArrayList<String>> device = new HashMap<String,ArrayList<String>>();
	public static KafkaConnectionPool pool = VehicleController.pool;
    public static boolean flag = true;
    public static String image = null;
	public static String topic = null;
	public static Map<String, Gate> gates = VehicleController.gates;
	private static Logger log = Logger.getLogger(SendToFinal.class);
	
	//testing for message counter
//	public static int MESSAGE_COUNTER = 0;

    public static void send(Vehicle vehicle, Map<String, Gate> gates) throws IOException {

        ThreadLocalRandom RandomR = ThreadLocalRandom.current();
        Producer<byte[], byte[]> producer = pool.getConnection();
        
        int parttions = producer.partitionsFor(topic).size();
		OffenceSnapDataProtos.OffenceSnapData.Builder offenceSnapData = OffenceSnapDataProtos.OffenceSnapData
				.newBuilder();

//		int picRandom = RandomR.nextInt(0, 4);
		int illegalType = RandomR.nextInt(0, 20);
		int speedRandm = RandomR.nextInt(0, 121);
		int chan = RandomR.nextInt(5);
		int color = RandomR.nextInt(6);
		int bright = RandomR.nextInt(5);

		long time = System.currentTimeMillis() / 1000;

		offenceSnapData.clear();
		offenceSnapData.setId(String.valueOf(vehicle.getCurrentGate()));
		offenceSnapData.setDriveChan(chan);
		offenceSnapData.setIllegalType(illegalType);
		offenceSnapData.setIllegalSubType("0");
		offenceSnapData.setSpeedLimit(speedRandm);
		offenceSnapData.setMonitoringSiteID(gates.get(vehicle.getCurrentGate()).getLonlat());
		offenceSnapData.setDir(1);
		offenceSnapData.setDetectType(2);
		offenceSnapData.setPilotSafebelt(0);
		offenceSnapData.setCopilotSafebelt(0);
		offenceSnapData.setPilotSubVisor(0);
		offenceSnapData.setCopilotSubVisor(0);
		offenceSnapData.setPilotCall(0);
		offenceSnapData.setAlarmDataType(0);
		offenceSnapData.setTime(vehicle.getDesireTime());
		offenceSnapData.setPicNum(1);
		
		OffenceSnapDataProtos.PlateInfoModel.Builder plateInfo = OffenceSnapDataProtos.PlateInfoModel.newBuilder();
		plateInfo.setPlateType(RandomR.nextInt(0, 5) + "");
		plateInfo.setColor(String.valueOf(color));
		plateInfo.setBright(bright);
		plateInfo.setLicenseLen(6);
		plateInfo.setCountry(0);
		plateInfo.setLicense(vehicle.getPlateNumber());
		plateInfo.setBelieve("1");
		offenceSnapData.setPlateInfo(plateInfo);
		
		OffenceSnapDataProtos.VehicleInfoModel.Builder vehicleInfo = OffenceSnapDataProtos.VehicleInfoModel
				.newBuilder();
		vehicleInfo.setIndex(100);
		vehicleInfo.setVehicleType(Integer.valueOf(vehicle.getType()));
		vehicleInfo.setColorDepth(0);
		vehicleInfo.setColor(vehicle.getColor());
		vehicleInfo.setSpeed(Float.valueOf(vehicle.getSpeed()));
		vehicleInfo.setLength(10);
		vehicleInfo.setVehicleLogoRecog(vehicle.getBrand());
		vehicleInfo.setVehicleSubLogoRecog("0");
		vehicleInfo.setVehicleModel("0");
		vehicleInfo.setVehicleAttribute(0);
		vehicleInfo.setChanIndex(chan);
		offenceSnapData.setVehicleInfo(vehicleInfo);

//		while (picRandom >= 0) {
//			--picRandom;
			OffenceSnapDataProtos.PicInfoModel.Builder picInfo = OffenceSnapDataProtos.PicInfoModel.newBuilder();
			picInfo.setType(1);
			picInfo.setPicRecogMode(0);
			picInfo.setRedLightTime(5);
			OffenceSnapDataProtos.PlateRectModel.Builder plateRect = OffenceSnapDataProtos.PlateRectModel.newBuilder();
			plateRect.setX(0);
			plateRect.setY(0);
			plateRect.setWidth(100);
			plateRect.setHeight(100);
			picInfo.setPlateRect(plateRect);
			picInfo.setData(image);
			offenceSnapData.addPicInfo(picInfo);
//		}
		OffenceSnapDataProtos.OffenceSnapData data = offenceSnapData.build();
		ProducerRecord<byte[], byte[]> message = new ProducerRecord<byte[], byte[]>(topic,
				Integer.valueOf(vehicle.getCurrentGate()) % parttions, time,
				String.valueOf(vehicle.getCurrentGate()).getBytes(), data.toByteArray());

		sendToKafka(producer, message);
    }

    public static void sendToKafka(Producer<byte[], byte[]> producer, ProducerRecord<byte[], byte[]> message){
    	try {
    		RecordMetadata res = producer.send(message).get(1000, TimeUnit.MILLISECONDS);
    	} catch (InterruptedException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} catch (ExecutionException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} catch (TimeoutException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
//			log.info("final: " + res);
    	pool.returnConnection(producer);
    }

    public static void getData(Vehicle vehicle) throws Exception {
        if(flag) {        	
//            String BOOTSTRAP_SERVERS = VehicleController.BOOTSTRAP_SERVERS;
            String imageFile = VehicleController.IMAGE_FILE;
            image = new String(Files.readAllBytes(Paths.get(imageFile)));
            topic = VehicleController.TOPIC_FINAL;
            
//            PoolConfig config = new PoolConfig();
//            config.setMaxTotal(30);
//            config.setMaxIdle(20);
//            config.setMaxWaitMillis(10000000);
//            config.setTestOnBorrow(true);
//
//            Properties props = new Properties();
//            props.setProperty("bootstrap.servers", BOOTSTRAP_SERVERS);
//            props.setProperty("producer.type", "async");
//            props.setProperty("key.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
//            props.setProperty("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
//
//            props.setProperty("request.required.acks", "0");
//            props.setProperty("compression.codec", "snappy");
//            props.setProperty("batch.num.messages", "2000");
//            props.setProperty("max.request.size", "1000973460");
//            pool = new KafkaConnectionPool(config, props);
        	flag = false;
        }
        log.info(Thread.currentThread().getName() + "-Message: " + vehicle.toArchive());
        send(vehicle, gates);
    }
}

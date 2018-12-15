package ljh.handge.data.util;

import java.util.Map;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

public class CostomPartitioner implements Partitioner{

	public void configure(Map<String, ?> arg0) {
		// TODO Auto-generated method stub

	}

	public void close() {
		// TODO Auto-generated method stub

	}

	public int partition(String s, Object key, byte[] keybyte, Object value,
			byte[] valuebyte, Cluster cluster) {
		// TODO Auto-generated method stub
		int partNum = 0;
		try{
			String nkey = new String((byte[])key);
			if(nkey.contains("|")){
				partNum = Integer.valueOf(nkey.split("\\|")[0]); 
			}
			else{
				partNum = Integer.valueOf(nkey);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return partNum;
	}
}

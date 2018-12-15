package ljh.handge.data.entry;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ResultValidate {
	
	public static Map<String, List<String>> results = new HashMap<String, List<String>>();
	public static Map<String, Integer> count = new HashMap<String, Integer>();

	public static void main(String[] args) {
		validate();
		printResult();
	}
	
	public static void validate(){
		try {
			FileReader reader = new FileReader("/home/liujihao/vehicleinfo.txt");
			BufferedReader br = new BufferedReader(reader);
			String str = null;
			while((str = br.readLine()) != null) {
				String key = str.split(",")[1];
				String gate = str.split(",")[0];
				if(results.containsKey(key)){
					List<String> infos =  results.get(key);
					infos.add(str);
					results.put(key, infos);
				} else{
					List<String> infos = new ArrayList<String>();
					infos.add(str);
					results.put(key, infos);
				}
				if(count.containsKey(gate)){
					count.put(gate, count.get(gate)+1);
				} else{
					count.put(gate, 1);
				}
			}
			br.close();
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void printResult(){
		for(Entry<String,List<String>> result : results.entrySet()){
			System.out.println("---------------" + result.getKey() + "---------------");
			for(String info : result.getValue()){
				System.out.println(info);
			}
			System.out.println();
		}

		List<Entry<String,Integer>> list = new ArrayList<Entry<String,Integer>>(count.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1,
					Map.Entry<String, Integer> o2) {
				return (o2.getValue() - o1.getValue());
			}
		});
		System.out.println("---------------卡口过车数量总计---------------");
		System.out.println(" 排名 : 编号 : 车辆数");
		int i = 1;
		int total = 0;
		for(Entry<String,Integer> l : list){
			System.out.println(String.format("%4d", i++) + " |" + String.format("%4s", l.getKey()) + " |" + String.format("%4s", l.getValue()));
			total += l.getValue();
		}
		System.out.println("total : " + total);
	}
}

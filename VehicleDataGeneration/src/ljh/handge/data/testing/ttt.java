package ljh.handge.data.testing;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import ljh.handge.data.bean.Gate;



public class ttt {
	
	public static Random random;
	public static Map<String, Gate> gates;
	public static final int MAX_VALUE = Integer.MAX_VALUE;  

	public static void main(String[] args) {
		
		String[] s = {"1","2","3","5"};
		int r = getIndex(s , "2");
		System.out.println(r);
	}
	
	public static int getIndex(String[] sa, String s){
		int i;
		for(i = 0; i < sa.length; i++){
			if(sa[i].equals(s))
				break;
		}
		return i;
	}
	
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
	
	public static void t(Object o){
		byte[] b = (byte[])o;
		String c = new String(b); 
		System.out.println(c);
	}
	
	
	public static void overspeedTest(){
		Random random = new Random();

		double overspeed_ratio = 0.01;
		double rnumber;
		double max = 100;
		double speed = 0;

		int n = 0, m = 0;
		while(m < 3){
			rnumber = random.nextDouble();
			if(rnumber <= overspeed_ratio){
				double overspeed_factor;
				do{
					overspeed_factor = 1.0 + random.nextDouble();
				} while (overspeed_factor > 1.6);
				speed = max * overspeed_factor;
				m++;
			}
			else{
			speed = 80;
			}
			System.out.println(speed);
			n++;
		}
		System.out.println(n + " " + m);
	}
	
	// 泊松分布 
	public static double P_rand(double Lamda){      
		double x = 0, b = 1.0, c = Math.exp(-Lamda), u;   
		do {  
			u = Math.random();  
			b *= u;  
			if(b >= c)  
				x++;  
		} while(b >= c);  
		return x;  
	}
	
}

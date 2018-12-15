package mahout.FP;

import org.apache.mahout.fpm.pfpgrowth.FPGrowthDriver;

import parser.AKVRegex;
import parser.Parser;

public class FP {
		public static void main(String arg[]) throws Exception {
			String[] args= new String[19];
			
			args[0]="-Dmapred.reduce.tasks="+3;
			args[1]="-i";
			args[2]="hdfs://10.18.5.178:9000/input/blogddata1";
			args[3]="-o";
			args[4]="hdfs://10.18.5.178:9000/outOFgxy/outputFP";
			args[5]="-method";
			args[6]="mapreduce";
			args[7]="-regex";
			args[8]=" ";
			args[9]="-g";  // groupNums
			args[10]=String.valueOf(100);
			args[11]="-s";  // 最小支持度
			args[12]=String.valueOf(10000);
			args[13]="-k";
			args[14]=String.valueOf(50); // 考虑到后期的整合 ,默认使用20
			args[15]="--tempDir";
			args[16]="hdfs://10.18.5.178:9000/tmp";
			args[17]="-tc";
			args[18]=String.valueOf(10);
			run(args);
		}

		public static void run(String[] s) throws Exception {
			try {
				//调用fp算法
				FPGrowthDriver.main(s);
//				解析结果，重新写入hdfs
//				boolean flag=Parser.Parser("hdfs://172.18.200.135:8020/outOFgxy/outputFP/fList",
//						"hdfs://172.18.200.135:8020/outOFgxy/outputFP/parsefList", "172.18.200.135", new AKVRegex());
//				System.out.println(flag);		
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}

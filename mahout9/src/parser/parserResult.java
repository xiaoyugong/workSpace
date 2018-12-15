package parser;

import java.io.IOException;

public class parserResult {

	public static void main(String[] args) throws IOException {
//		 TODO Auto-generated method stub
		boolean flag=Parser.Parser("hdfs://172.18.200.135:8020/outOFgxy/outputFP/fList",
				"hdfs://172.18.200.135:8020/outOFgxy/outputFP/fListp", "172.18.200.135", new AKVRegex());
//		boolean flag=Parser.Parser("hdfs://localhost:9000/input/seq/part-m-00000",
//				"hdfs://localhost:9000/input/seq/seq1", "127.0.0.1", new AKVRegex());
		System.out.println(flag);	
	}
}

package parser;

import java.io.IOException;

public class parserResult {

	public static void main(String[] args) throws IOException {
//		 TODO Auto-generated method stub
		boolean flag=Parser.Parser("hdfs://172.18.199.161:9000/output/fp/fList",
				"hdfs://172.18.199.161:9000/output/fp/fListp", "172.18.199.161", new AKVRegex());
//		boolean flag=Parser.Parser("hdfs://localhost:9000/input/seq/part-m-00000",
//				"hdfs://localhost:9000/input/seq/seq1", "127.0.0.1", new AKVRegex());
		System.out.println(flag);	
	}
}

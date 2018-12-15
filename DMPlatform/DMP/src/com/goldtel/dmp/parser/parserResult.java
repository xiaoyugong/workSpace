package com.goldtel.dmp.parser;

import java.io.IOException;

public class parserResult {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		boolean flag=Parser.Parser("hdfs://172.18.200.135:8020/outOFgxy/outputKmeans/clusteredPoints/part-m-00000",
				"hdfs://172.18.200.135:8020/outOFgxy/outputKmeans/result", "172.18.200.135", new AKVRegex());
		System.out.println(flag);	
	}
}

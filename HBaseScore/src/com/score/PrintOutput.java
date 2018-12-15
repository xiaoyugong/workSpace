package com.score;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.PrefixFilter;

public class PrintOutput {
	static String filePath = "";
	static FileOutputStream output = null;

	public static void main(String[] args) {
		filePath = args[0];
		File file = new File(filePath);
		try {
			output = new FileOutputStream(file, false);
			scanBaseInfo();
			scanComputeResult();
			output.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void scanBaseInfo() {
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "master,slave1,slave2");
		conf.set("hbase.zookeeper.property.clientPort", "2181");
		ResultScanner rs = null;
		Scan scan = null;
		double score = 0;
		String firstTime = "2016-11-26-10-05-00";
		String secondsTime = "2016-11-26-10-10-00";
		try {
			Connection conn = ConnectionFactory.createConnection(conf);
			String tableNameInfo = "BaseInfo";
			TableName tablename = TableName.valueOf(tableNameInfo);

			Admin admin = conn.getAdmin();
			boolean isExists = admin.tableExists(tablename);
			if (isExists == true) {
				score += 1.5;
				output.write(("Tablename: " + tableNameInfo + "                   Score: 1.5\n").getBytes());
				System.out.println("Tablename: " + tableNameInfo + "                   Score: 1.5");

				Table table = conn.getTable(tablename);
				scan = new Scan();
				rs = table.getScanner(scan);
				if (rs.next() != null) {
					score += 1;
					output.write(("Data exists:                          Score: 1\n").getBytes());
					System.out.println("Data exists:                          Score: 1");
				} else {
					output.write(("Data exists:                          Score: 0\n").getBytes());
					System.out.println("Data exists:                          Score: 0");
				}

				scan = new Scan();
				Filter filter1 = new PrefixFilter(Bytes.toBytes(firstTime));
				scan.setFilter(filter1);
				rs = table.getScanner(scan);
				if (rs.next() != null) {
					score += 2;
					output.write(("5 minutes of data exists:             Score: 2\n").getBytes());
					System.out.println("5 minutes of data exists:             Score: 2");
				} else {
					output.write(("5 minutes of data exists:             Score: 0\n").getBytes());
					System.out.println("5 minutes of data exists:             Score: 0");
				}

				scan = new Scan();
				Filter filter2 = new PrefixFilter(Bytes.toBytes(secondsTime));
				scan.setFilter(filter2);
				rs = table.getScanner(scan);
				if (rs.next() != null) {
					score += 2;
					output.write(("10 minutes of data exists:            Score: 2\n").getBytes());
					System.out.println("10 minutes of data exists:            Score: 2");
				} else {
					output.write(("10 minutes of data exists:            Score: 0\n").getBytes());
					System.out.println("10 minutes of data exists:            Score: 0");
				}

				System.out.println("BaseInfo Total Score: " + score);
				output.write(("BaseInfo Total Score: " + score+"\n\n").getBytes());
			} else {
				output.write(("Tablename: " + tableNameInfo + " isn't exists:      Score: 0\n\n").getBytes());
				output.write(("BaseInfo Total Score: " + score+"\n").getBytes());
				
				System.out.println("Tablename: " + tableNameInfo + " isn't exists:      Score: 0");
				System.out.println("BaseInfo Total Score: " + score);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void scanComputeResult() {
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "192.168.1.2,192.168.1.3,192.168.1.4");
		conf.set("hbase.zookeeper.property.clientPort", "2181");
		ResultScanner rs = null;
		Scan scan = null;
		double score = 0;
		String firstTime = "2016-11-26-10-05-00";
		String secondsTime = "2016-11-26-10-10-00";
		try {
			Connection conn = ConnectionFactory.createConnection(conf);
			String tableNameResult = "ComputeResult";
			TableName tablename = TableName.valueOf(tableNameResult);

			Admin admin = conn.getAdmin();
			boolean isExists = admin.tableExists(tablename);
			if (isExists == true) {
				score += 1.5;
				output.write(("Tablename: " + tableNameResult + "              Score: 1.5\n").getBytes());
				System.out.println("Tablename: " + tableNameResult + "              Score: 1.5");

				Table table = conn.getTable(tablename);
				scan = new Scan();
				rs = table.getScanner(scan);
				if (rs.next() != null) {
					score += 1;
					output.write(("Data exists:                          Score: 1\n").getBytes());
					System.out.println("Data exists:                          Score: 1");
				} else {
					output.write(("Data exists:                          Score: 0\n").getBytes());
					System.out.println("Data exists:                          Score: 0");
				}

				scan = new Scan();
				Filter filter1 = new PrefixFilter(Bytes.toBytes(firstTime));
				scan.setFilter(filter1);
				rs = table.getScanner(scan);
				if (rs.next() != null) {
					score += 2;
					output.write(("5 minutes of data exists:             Score: 2\n").getBytes());
					System.out.println("5 minutes of data exists:             Score: 2");
				} else {
					output.write(("5 minutes of data exists:             Score: 0\n").getBytes());
					System.out.println("5 minutes of data exists:             Score: 0");
				}

				scan = new Scan();
				Filter filter2 = new PrefixFilter(Bytes.toBytes(secondsTime));
				scan.setFilter(filter2);
				rs = table.getScanner(scan);
				if (rs.next() != null) {
					score += 2;
					output.write(("10 minutes of data exists:            Score: 2\n").getBytes());
					System.out.println("10 minutes of data exists:            Score: 2");
				} else {
					output.write(("10 minutes of data exists:            Score: 0\n").getBytes());
					System.out.println("10 minutes of data exists:            Score: 0");
				}

				System.out.println("ComputeResult Total Score: " + score);
				output.write(("ComputeResult Total Score: " + score).getBytes());
			} else {
				output.write(("Tablename: " + tableNameResult + " isn't exists:    Score: 0\n").getBytes());
				output.write(("ComputeResult Total Score: " + score).getBytes());
				
				System.out.println("Tablename: " + tableNameResult + " isn't exists:    Score: 0");
				System.out.println("ComputeResult Total Score: " + score);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

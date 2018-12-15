package competition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * 
 * 该类实现了对HBase的部分基本操作
 * 
 */
public class HBaseOps {
	private static Configuration conf = null;
	static Connection conn = null;
	static Admin admin = null;
	
	/**
	 * 连接HBase数据库并获得该HBaseOps类的实例化对象
	 * @param hosts ZooKeeper集群地址
	 * @return HBaseOps类的实例化对象，用于后续调用HBase数据库操作的API
	 */
	public static HBaseOps connectHBase(String hosts) {
		conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", hosts);
		conf.set("hbase.zookeeper.property.clientPort", "2181");
		try {
			conn = ConnectionFactory.createConnection(conf);
		} catch (IOException e) {
			e.printStackTrace();
		}
		HBaseOps hbase = new HBaseOps();
		return hbase;
	}
	
	/**
	 * 断开当前与HBase的链接
	 * 若在多次MapReduce的过程中总是只连接不关闭，会造成通讯线程越来越多而崩溃
	 */
	public void disconnect(){
		try{
			if(admin != null){
				admin.close();
			}
			if(conn != null){
				conn.close();
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 创建表
	 * @param tableName 表名
	 * @param columnFamily 列族的数组，应包括至少一个列族
	 * @throws IOException
	 */
	public void createTable(TableName tableName, String[] columnFamily)
			throws IOException {
		admin = conn.getAdmin();
		if (admin.tableExists(tableName)) {
			admin.disableTable(tableName);
			admin.deleteTable(tableName);
		}
		HTableDescriptor tableDesc = new HTableDescriptor(tableName);
		for (int i = 0; i < columnFamily.length; i++) {
			tableDesc.addFamily(new HColumnDescriptor(columnFamily[i]));
		}
		admin.createTable(tableDesc);
		System.out.println("Create Table " + tableName.toString() + " success!");
		admin.close();
	}
	/**
	 * 插入数据
	 * @param tablename 表名
	 * @param row RowKey
	 * @param columnFamily 列族名
	 * @param column 列限定符
	 * @param data 数据值
	 * @throws Exception
	 */
	public void put(String tableName, String row, String columnFamily,
			String column, String data) throws Exception {
		TableName table = TableName.valueOf(tableName);
		Table htable = conn.getTable(table);

		Put put = new Put(Bytes.toBytes(row));

		put.addColumn(columnFamily.getBytes(), column.getBytes(),
				data.getBytes());
		htable.put(put);

	}
	
	/**
	 * 按行键、列族、列限定符获取单元值数据
	 * @param tableName 表名
	 * @param row 行键
	 * @param columnFamily 列族
	 * @param column 列限定符
	 * @return 返回String类型的数据值
	 * @throws Exception
	 */
	public String get(String tableName, String row, String columnFamily, String column) throws Exception {
		TableName table = TableName.valueOf(tableName);
		Table htable = conn.getTable(table);
		Get g = new Get(Bytes.toBytes(row));
		g.addColumn(columnFamily.getBytes(), column.getBytes());
		Result result = htable.get(g);
		byte[] value = result.getValue(Bytes.toBytes(columnFamily),
				Bytes.toBytes(column));
		return Bytes.toString(value);
	}
	
	/**
	 * 查看整个表（比赛中不会要求使用该方法）
	 * @param tablename 表名
	 * @throws Exception
	 */
	public void scan(String tableName) throws Exception {
		TableName table = TableName.valueOf(tableName);
		Table htable = conn.getTable(table);
		Scan s = new Scan();
		ResultScanner rs = htable.getScanner(s);
		for (Result r : rs) {
			byte[] row = r.getRow();
			byte[] value = r.getValue(Bytes.toBytes(""),
					Bytes.toBytes(""));
			System.out.println("Scan:" + Bytes.toString(row) + " value："
					+ Bytes.toString(value));
		}
		rs.close();
	}
	
	/**
	 * 删除表中的某行（比赛中不会要求使用该方法）
	 * @param tablename 表名
	 * @param rowkey 
	 * @throws IOException
	 */
	public void deleteRow(String tableName, String rowkey) throws IOException {
		TableName table = TableName.valueOf(tableName);
		Table htable = conn.getTable(table);
		List<Delete> list = new ArrayList<Delete>();
		Delete d1 = new Delete(rowkey.getBytes());
		list.add(d1);
		htable.delete(list);
		System.out.println("delete row " + rowkey + " success!");
	}
	
	/**
	 * 删除整个表（比赛中不会要求使用该方法）
	 * @param tablename 表名
	 * @throws IOException
	 */
	public void delete(TableName tableName) throws IOException {
		Admin admin = conn.getAdmin();
		try {
			if (!admin.tableExists(tableName)) {
				System.out.println("table :" + tableName + " is not exists!");
			} else {
				admin.disableTable(tableName);
				admin.deleteTable(tableName);
				System.out.println("delete " + tableName + " success!");
			}
		} catch (Exception ex) { 
			ex.printStackTrace();
		} finally {
			try {
				admin.close();
			} catch (Exception ex) {
				ex.printStackTrace();

			}
		}
	}
}

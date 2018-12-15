package util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.util.LinkedList;
import java.util.Properties;

import common.PoolConfig;
import common.es.EsConnectionPool;
import common.hbase.HbaseConnectionPool;
import common.jdbc.JdbcConnectionPool;
import common.kafka.KafkaConnectionPool;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.kafka.clients.producer.Producer;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.Settings.Builder;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import redis.clients.jedis.*;

public class Util {
	
	private static HbaseConnectionPool hbaseConnectionPool = null;
	private static EsConnectionPool esConnectionPool = null;
	private static JdbcConnectionPool jdbcConnectionPool = null;
	private static KafkaConnectionPool kafkaConnectionPool = null;
	private static JedisPool redisPool = null;
	
	private static PoolConfig getPoolConfig() {
		PoolConfig poolConfig = new PoolConfig();

		poolConfig.setMaxTotal(301);
		poolConfig.setMaxIdle(301);
		poolConfig.setMaxWaitMillis(100000);
		poolConfig.setTestOnBorrow(true);
		poolConfig.setTestOnReturn(true);
		poolConfig.setTestOnCreate(true);
		
		return poolConfig;
	}
	
	private static HbaseConnectionPool initHbase(){
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", Constants.HBASE_ZOOKEEPER_QUORUM);
		conf.set("hbase.zookeeper.property.clientPort", Constants.HBASE_ZOOKEEPER_PROPERTY_CLIENTPOINT);
		conf.set("hbase.client.write.buffer", Constants.HBASE_CLIENT_WRITE_BUFFER);
		conf.set("hbase.client.max.total.tasks", Constants.HBASE_CLEINT_MAX_TOTAL_TASKS);
		conf.set("hbase.client.max.perserver.tasks", Constants.HBASE_CLIENT_MAX_PRESERVER_TASKS);
		conf.set("hbase.client.max.perregion.tasks", Constants.HABSE_CLIENT_MAX_PERREGION_TASKS);
		conf.set("zookeeper.znode.parent", Constants.ZOOKEEPER_ZNODE_PARENT);
		HbaseConnectionPool hbasePool = new HbaseConnectionPool(getPoolConfig(), conf);
		System.out.println("_______________________create new hbasePoll________________________");
		
		return hbasePool;
	}
	
	private static EsConnectionPool initEs() {
		Builder settings = Settings.builder();
		settings.put("cluster.name", Constants.ES_CLUSTER_NAME);

		LinkedList<InetSocketTransportAddress> address = new LinkedList<InetSocketTransportAddress>();
		String[] esUrls = Constants.ES_URL.split(",");
		for (String s : esUrls) {
			String[] hp = s.split(":");
			// logInfo(" ES " + hp[0] + " " +hp[1]);
			try {
				address.add(new InetSocketTransportAddress(InetAddress.getByName(hp[0]), Integer.valueOf(hp[1])));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		EsConnectionPool esPool = new EsConnectionPool(getPoolConfig(), settings.build(), address);
		
		return esPool;
	}
	
	private static JdbcConnectionPool initJDBC() {
		JdbcConnectionPool mysqlPool = new JdbcConnectionPool(getPoolConfig(), Constants.MYSQL_DRIVER,
				Constants.MYSQL_JDBC_URL, Constants.MYSQL_USER_NAME, Constants.MYSQL_USER_PASSWORD);
		
		return mysqlPool;
	}
	
	private static KafkaConnectionPool initKafka() {
		Properties kafkaConfig = new Properties();
		kafkaConfig.setProperty("bootstrap.servers", Constants.KAFKA_SERVER);
		kafkaConfig.setProperty("producer.type", Constants.KAFKA_PRODUCER_TYPE);
		kafkaConfig.setProperty("key.serializer", Constants.KAFKA_KEY_SERIALIZER);
		kafkaConfig.setProperty("value.serializer", Constants.KAFKA_VALUE_SERIALIZER);
		kafkaConfig.setProperty("batch.num.messages", Constants.KAFKA_BATCH_NUM_MESSAGES);
		kafkaConfig.setProperty("max.request.size", Constants.KAFKA_MAX_REQUEST_SIZE);
		KafkaConnectionPool kafkaPool = new KafkaConnectionPool(getPoolConfig(), kafkaConfig);
		return kafkaPool;
	}
	
	private static JedisPool initRedisPool() {
		JedisPool pools = new JedisPool(getPoolConfig(), Constants.REDIS_HOST, Constants.REDIS_PORT);
		return pools;
	}
	
	synchronized static Producer<byte[], byte[]> getKafkaConn() {
		if (kafkaConnectionPool == null || kafkaConnectionPool.isClosed()) {
			kafkaConnectionPool = initKafka();
		}

		return kafkaConnectionPool.getConnection();
	}
	
	synchronized static void returnKafkaConn(Producer<byte[], byte[]> producer) {
		kafkaConnectionPool.returnConnection(producer);
	}
	
	synchronized static Connection getMysqlConn() {
		if (jdbcConnectionPool == null || jdbcConnectionPool.isClosed()) {
			jdbcConnectionPool = initJDBC();
		}
		
		return jdbcConnectionPool.getConnection();
	}

	synchronized static void returnMysqlConn(Connection conn) {
		jdbcConnectionPool.returnConnection(conn);
	}
	
	synchronized static org.apache.hadoop.hbase.client.Connection getHbaseConn() {
		if (hbaseConnectionPool == null || hbaseConnectionPool.isClosed()) {
			hbaseConnectionPool = initHbase();
		}

		return hbaseConnectionPool.getConnection();
	}

	synchronized static void returnHbaseConn(org.apache.hadoop.hbase.client.Connection conn) {
		hbaseConnectionPool.returnConnection(conn);
	}
	
	synchronized static TransportClient getEsClient() {
		if (esConnectionPool == null || esConnectionPool.isClosed()) {
			esConnectionPool = initEs();
		}
		
		return esConnectionPool.getConnection();
	}

	synchronized static void returnEsConn(TransportClient client) {
		esConnectionPool.returnConnection(client);
	}
	
	synchronized static Jedis getRedisConn() {
		if (redisPool == null || redisPool.isClosed()) {
			redisPool = initRedisPool();
		}
		
		return redisPool.getResource();
	}

	synchronized static void returnRedisConn(Jedis redis) {
		redisPool.returnResource(redis);
	}

}

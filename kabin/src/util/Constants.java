package util;

public class Constants {
	static String APP_NAME="DRIVER";

	static long DURATION=1000l;

	static String CHECKPOINT="checkpoint";

	static String KAFKA_SERVER="172.20.31.4:6667,172.20.31.5:6667,172.20.31.6:6667";

	static String KAFKA_TOPIC="traffic";

	static String KAFKA_KEY_DESERIALIZER="org.apache.kafka.common.serialization.ByteArrayDeserializer";

	static String KAFKA_VALUE_DESERIALIZER = "org.apache.kafka.common.serialization.ByteArrayDeserializer";

	static String KAFKA_KEY_SERIALIZER = "org.apache.kafka.common.serialization.ByteArraySerializer";

	static String KAFKA_VALUE_SERIALIZER = "org.apache.kafka.common.serialization.ByteArraySerializer";

	static String KAFKA_GROUPID="1";

	static String KAFKA_AUTO_OFFSET_RESET="latest";

	static String KAFKA_ENABLE_AUTO_COMMIT="false";

	static String KAFKA_PRODUCER_TYPE="async";

	static String KAFKA_BATCH_NUM_MESSAGES="100";

	static String KAFKA_MAX_REQUEST_SIZE="1000973460";

	static String KAFKA_ILLEGAL_TOPIC="Illegal";

	static String KAFKA_ALARM_TOPIC="Alarm";

	static String HBASE_ZOOKEEPER_QUORUM="datanode1,datanode2,datanode3";

	static String HBASE_ZOOKEEPER_PROPERTY_CLIENTPOINT="2181";

	static String HBASE_CLIENT_WRITE_BUFFER="12582912";

	static String HBASE_CLEINT_MAX_TOTAL_TASKS="500";

	static String HBASE_CLIENT_MAX_PRESERVER_TASKS="10";

	static String HABSE_CLIENT_MAX_PERREGION_TASKS="2";

	static String ZOOKEEPER_ZNODE_PARENT="/hbase-unsecure";

	static String HBASE_TABLE_TRAFFIC="Traffic";

	static String HBASE_TRAFFIC_COLUMNFAMILY="MetaData";

	static String HBASE_TABLE_TRAFFICIMAGE="TrafficImage";

	static String HBASE_TRAFFICIAMGE_COLUMNFAMILY="Image";

	static String HBASE_TABLE_STATISTICS="TrafficInfo";

	static String STATISTICS_FAMILY_NAME="trafficinfo";

	static String HBASE_TABLE_ILLEGALINFO="IllegalInfo";

	static String HBASE_ILLEGALINFO_COLUMNFAMILY="illegalinfo";

	static String HBASE_TABLE_ALARMINFO="AlarmInfo";

	static String HBASE_ALARMINFO_COLUMNFAMILY="AlarmInfo";

	static String ES_CLUSTER_NAME="handge-cloud";

	static String ES_URL="datanode1:9300,datanode2:9300,datanode3:9300";

	static String ES_INDEX="traffic";

	static String ES_TYPE="traffic";

	static String MYSQL_DRIVER="com.mysql.jdbc.Driver";

	static String MYSQL_USER_NAME="root";

	static String MYSQL_USER_PASSWORD="mysql";

	static String MYSQL_JDBC_URL="jdbc:mysql://172.20.31.127:3306/solar";

	static String REDIS_HOST="172.20.31.127";

	static int REDIS_PORT=6379;
}

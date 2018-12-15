package gxy.flume.sink;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.FlumeException;
import org.apache.flume.conf.ComponentConfiguration;
import org.apache.flume.sink.hbase.HbaseEventSerializer;
import org.apache.hadoop.hbase.client.Increment;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Row;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;

public class MyHbaseEventSerializer implements HbaseEventSerializer {
 // Config vars
    private static final Logger logger = LoggerFactory.getLogger(MyHbaseEventSerializer.class);
    
    /** Comma separated list of column names to place matching groups in. */
    public static final String COL_NAME_CONFIG = "colNames";
    public static final String COLUMN_NAME_DEFAULT = "payload";
    public static final long MAX_TIME = 2524579200L;
    
    /** Placeholder in colNames for row key */
    public static final String ROW_KEY_NAME = "ROW_KEY";

    /** What charset to use when serializing into HBase's byte arrays */
    public static final String CHARSET_CONFIG = "charset";
    public static final String CHARSET_DEFAULT = "UTF-8";

    protected byte[] cf;
    private byte[] payload;
    private List<byte[]> colNames = Lists.newArrayList();
    private Charset charset;

    public void configure(Context context) {
      charset = Charset.forName(context.getString(CHARSET_CONFIG, CHARSET_DEFAULT));

      String colNameStr = context.getString(COL_NAME_CONFIG, COLUMN_NAME_DEFAULT);
      String[] columnNames = colNameStr.split(",");
      logger.info("The colName:" + Arrays.toString(columnNames));
      for (String s: columnNames) {
        colNames.add(s.getBytes(charset));
      }
    }

    public void configure(ComponentConfiguration conf) {
    }

    public void initialize(Event event, byte[] columnFamily) {
      this.payload = event.getBody();
      this.cf = columnFamily;
    }

    @SuppressWarnings("unused")
    public List<Row> getActions() throws FlumeException {
      List<Row> actions = Lists.newArrayList();
      String rowKey;
      //散列字段
      int index = 0;
      
      String body = new String(payload, charset);
      logger.info("The body: " + body);
      
      if (body == null) {
        return Lists.newArrayList();
      }
      //解析成json对象
      JSONObject jo = JSONObject.fromObject(body);
      
      //用一个很大的时间减去time，为了使最新的数据放在最前面
      long time = MAX_TIME - jo.getJSONObject("data").getLong("time");
      JSONArray meterArray = jo.getJSONObject("data").getJSONArray("meter");
      for(int i = 0; i < meterArray.size(); i++) {
          JSONObject meter = (JSONObject)meterArray.get(i);
          String meter_id = meter.getString("@id");
          
          JSONArray functionArray = meter.getJSONArray("function");
          try {
              for(int j = 0; j < functionArray.size(); j++) {
                  index = j + 1;
                  //构造rowkey
                  rowKey = index  + meter_id + Long.toString(time);
                  logger.info("rowKey: " + rowKey);
                  Put put = new Put(Bytes.toBytes(rowKey));
                  
                  JSONObject function = (JSONObject)functionArray.get(j);
                  String function_id = function.getString("@id");
                  String coding = function.getString("@coding");
                  String value = function.getString("#text");
                  
                  logger.info("function_id: " + function_id);
                  logger.info("coding: " + coding);
                  logger.info("value: " + value);
                  
                  put.add(cf, colNames.get(0), function_id.getBytes(Charsets.UTF_8));
                  put.add(cf, colNames.get(1), coding.getBytes(Charsets.UTF_8));
                  put.add(cf, colNames.get(2), value.getBytes(Charsets.UTF_8));
                  
                  actions.add(put);
              }
          } catch (Exception e) {
              throw new FlumeException("Could not get row key!", e);
          }
      }
      return actions;
    }

    public List<Increment> getIncrements() {
      return Lists.newArrayList();
    }

    public void close() {  }
}

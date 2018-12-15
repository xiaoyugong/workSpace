/**
 * @Class: ReadSequenceWritable.java
 * @Description: 解析Cluster文件，可适合于聚类算法结果
 * @Author: Zhaoliheng July 15, 2015
 * @Version: V1.0
 */
package com.goldtel.dmp.clusterwriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.util.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.goldtel.dmp.Const;
import com.goldtel.dmp.Utils;

public class ReadSequenceWritable
{
	private static final Logger log = LoggerFactory.getLogger(ReadSequenceWritable.class);

	/**
	 * 读取random forest分类结果
	 * @param conf			Configuration
	 * @param seqFileDir	输入的序列化文件路径
	 * @param pointsDir		聚类中心点文件路径
	 * @param outputFile	输出的本地文件，带文件名
	 * @throws Exception
	 */
	public static List<String> readSeqFileRF(String ds, String strOutPath, long iStart, long iStop) throws Exception
	{
		log.info("readSeqFileRF: Begin to get[" + ds + "], iStart = " + iStart + ", iStop = " + iStop);

        Configuration conf = new Configuration();
        Map<String, LinkedList<String>> res = readSeqFileValue(strOutPath, conf, iStart, iStop);
        List<String> lst = putMapToList(res);

        log.info("readClusterFile: Get[" + ds + "] ok. iStart = " + iStart + ", iStop = " + iStop);

	    return lst;
	}

	/**
	 * 注入Bayes分类查询结果
	 * @param ds
	 * @param iStart
	 * @param iStop
	 * @throws Exception
	 */
    public static List<String> readSeqFileBayes(String ds, String strOutPath, long iStart, long iStop) throws Exception
    {
        log.info("readSeqFileBayes: Begin to get[" + ds + "], iStart = " + iStart + ", iStop = " + iStop);

        Configuration conf = new Configuration();
        Map<String, LinkedList<String>> res = readSeqKeyFile(strOutPath, conf, iStart, iStop);
        List<String> lst = putMapToList(res);

        log.info("readSeqFileBayes: Get[" + ds + "] ok. iStart = " + iStart + ", iStop = " + iStop);

        return lst;
    }

	/**
	 * 读取序列文件,返回前面numRecords记录，将文件value作为map的key
	 * @param fPath
	 * @param conf
	 * @param iStart
	 * @param iStop
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static Map<String, LinkedList<String>> readSeqFileValue(String fPath, Configuration conf, long iStart, long iStop) throws IOException, Exception
    {
        FileSystem fs = FileSystem.get(URI.create(fPath), conf);
        Path path = new Path(fPath);
        Map<String, LinkedList<String>> map = new LinkedHashMap<String, LinkedList<String>>();
        SequenceFile.Reader reader = null;
        try
        {
            reader = new SequenceFile.Reader(fs, path, conf);
            Writable key = (Writable) ReflectionUtils.newInstance(reader.getKeyClass(), conf);
            Writable value = (Writable) ReflectionUtils.newInstance(reader.getValueClass(), conf);
            @SuppressWarnings("unchecked")
            Class<Writable> writableClassK = (Class<Writable>) reader.getKeyClass();
            @SuppressWarnings("unchecked")
            Class<Writable> writableClassV = (Class<Writable>) reader.getValueClass();

            long iCur = 0L;
            while (reader.next(key, value))
            {
                // Writable的深度复制，格式为: alt.51127:alt.atheism(数据名:类别)
                Writable k = deepCopy(key, writableClassK);
                Writable v = deepCopy(value, writableClassV);   //  这里不用读取属性

                String strKey = k.toString().trim();
                String strV = v.toString().trim();
                String strVal = strV.substring(strV.indexOf(Const.SPLIT_DM_COND) + Const.SPLIT_DM_COND.length(), strV.length());
                try
                {
                    if (iCur >= iStart)
                    {
                        if (0 == iStop)
                        {
                            Utils.putMap(map, strVal.trim(), strKey.trim());
                            //map.put(k, v);
                        }
                        else if (iCur <= iStop)
                        {
                            Utils.putMap(map, strVal.trim(), strKey.trim());
                            //map.put(k, v);
                        }
                    }
                }
                catch (Throwable e)
                {
                    log.error("readSeqFileValue error: key = " + strKey + ", value = " + strV);
                }
                finally
                {
                    iCur++;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("readSeqFileValue error: " + e.getLocalizedMessage());
            throw e;
        }
        finally
        {
            IOUtils.closeStream(reader);
        }
        return map;
    }

	/**
     * 读取序列文件的key,返回前面numRecords记录
     * 
     * @param fPath
     * @param conf
     * @param numRecords
     * @return
     * @throws IOException
     */
    public static Map<String, LinkedList<String>> readSeqKeyFile(String fPath, Configuration conf, long iStart, long iStop) throws IOException, Exception
    {
        FileSystem fs = FileSystem.get(URI.create(fPath), conf);
        Path path = new Path(fPath);
        Map<String, LinkedList<String>> map = new LinkedHashMap<String, LinkedList<String>>();
        SequenceFile.Reader reader = null;
        try
        {
            reader = new SequenceFile.Reader(fs, path, conf);
            Writable key = (Writable) ReflectionUtils.newInstance(
                    reader.getKeyClass(), conf);
            Writable value = (Writable) ReflectionUtils.newInstance(
                    reader.getValueClass(), conf);
            @SuppressWarnings("unchecked")
            Class<Writable> writableClassK = (Class<Writable>) reader
                    .getKeyClass();
            @SuppressWarnings("unchecked")
            Class<Writable> writableClassV = (Class<Writable>) reader
                    .getValueClass();

            long iCur = 0L;
            while (reader.next(key, value))
            {
                // Writable的深度复制，格式为: alt.51127:alt.atheism(数据名:类别)
                Writable k = deepCopy(key, writableClassK);
                //  Writable v = deepCopy(value, writableClassV);   //  这里不用读取属性

                String[] strKey = k.toString().split(Const.SPLIT_DM_COND);
                try
                {
                    if (iCur >= iStart)
                    {
                        if (0 == iStop)
                        {
                            Utils.putMap(map, strKey[1].trim(), strKey[0].trim());
                            //map.put(k, v);
                        }
                        else if (iCur <= iStop)
                        {
                            Utils.putMap(map, strKey[1].trim(), strKey[0].trim());
                            //map.put(k, v);
                        }
                    }
                }
                catch (Throwable e)
                {
                    log.error("readFromFile: error key: " + k.toString());
                }
                finally
                {
                    iCur++;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            IOUtils.closeStream(reader);
        }
        return map;
    }

	/**
     * Writable 的深度复制 引自WritableDeepCopier
     * 
     * @param fPath
     * @return
     * @throws IOException
     */
    public static Writable deepCopy(Writable source, Class<Writable> writableClass) throws Exception
    {
        ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
        DataOutputStream dataOut = new DataOutputStream(byteOutStream);
        Writable copiedValue = null;
        try {
            source.write(dataOut);
            dataOut.flush();
            ByteArrayInputStream byteInStream = new ByteArrayInputStream(
                    byteOutStream.toByteArray());
            DataInput dataInput = new DataInputStream(byteInStream);
            copiedValue = writableClass.newInstance();
            copiedValue.readFields(dataInput);
        } catch (Exception e) {
            throw new Exception("Error while deep copying " + source, e);
        }
        return copiedValue;
    }

	/**
     * 将结果写入List中
     * @param res
     */
    private static List<String> putMapToList(Map<String, LinkedList<String>> map)
    {
        //  选中的结果，格式为：List<类别名称:新数据1    新数据2    ...>，数据之间以Tab分开
        List<String> recommand = new LinkedList<String>();
        recommand.clear();

        for (String key : map.keySet())
        {
            LinkedList<String> value = map.get(key);

            StringBuffer sb = new StringBuffer();
            for (String val : value)
            {
                sb.append(val);
                sb.append(Const.RESULT_KV_SPLIT);
            }

            if (sb.length() > 0)
            {
                sb.deleteCharAt(sb.length() - Const.RESULT_KV_SPLIT.length());//.substring(0, sb.length() - Const.SPLIT_ANALYZE_S.length());
            }

            String val = key + Const.SPLIT_DM_COND + sb.toString();
            recommand.add(val);
        }
        
        return recommand;
    }
}

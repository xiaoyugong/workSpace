package util.hadoop;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import model.Knowledge;

import org.apache.crunch.CrunchRuntimeException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.util.ReflectionUtils;
import org.apache.mahout.common.HadoopUtil;
import org.apache.mahout.common.Pair;
import org.apache.mahout.fpm.pfpgrowth.convertors.string.TopKStringPatterns;

import util.GlobalUtil;

public class HadoopUtils {
	public static final String FLIST = "hdfs://172.18.200.135:8020/outOFgxy/outputFP/fList";
	public static final String FP_OUTPATH = "hdfs://mycluster/output/fp/blogdata1";
	public static final String UPLOAD_PATH = "hdfs://172.18.200.135:8020/input/";
	public static final String FP_INPATH = "hdfs://mycluster/input/blogdata1";

	// JobClient
	private static JobClient jobClient;

	public static JobClient getJobClient() {
		if (jobClient == null) {
			try {
				InetSocketAddress jobTracker = new InetSocketAddress(getHost(),
						getJobtrackerPort());
				jobClient = new JobClient(jobTracker, getConf());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return jobClient;
	}

	private static String JOBTRACKER = null;

	/*
	 * 获得host
	 */
	public static String getHost() {
		if (JOBTRACKER == null) {
			JOBTRACKER = GlobalUtil.getValue("mapreduce.jobtracker.name");
		}
		return JOBTRACKER.split(":")[0];
	}

	/*
	 * 获得 jobtracker port
	 */
	public static int getJobtrackerPort() {
		if (JOBTRACKER == null) {
			JOBTRACKER = GlobalUtil.getValue("mapreduce.jobtracker.name");
		}
		return Integer.parseInt(JOBTRACKER.split(":")[1]);
	}

	private static Configuration conf;

	/*
	 * 获得configuration
	 */
	public static Configuration getConf() {
		if (conf == null) {
			conf = new Configuration();
		}
		return conf;
	}

	/**
	 * 上传数据到云平台
	 * 
	 * @param localFile
	 * @param deltaOrAll
	 * @return 上传路径；
	 */
	public static String upload(String localFile, String deltaOrAll) {
		FileSystem fs = null;
		int beginIndex = localFile.lastIndexOf("/");
		String fileName = localFile.substring(beginIndex+1);
		String uploadFile = UPLOAD_PATH + fileName;
		Path in = new Path(localFile);
		Path out = new Path(uploadFile);
		try {
			fs = FileSystem.get(URI.create(uploadFile), getConf());
			if ("all".equals(deltaOrAll)) {
				HadoopUtil.delete(getConf(), new Path(UPLOAD_PATH));
			}
			fs.copyFromLocalFile(in, out);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			try {
				HadoopUtil.delete(getConf(), out);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			uploadFile = null;
		} finally {
			try {
				if (fs != null) {
					fs.close();
				}
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}
		return uploadFile;
	}

	/**
	 * 删除云平台数据
	 * 
	 * @param path
	 */
	public static void delete(String path) {
		Path out = new Path(path);
		try {
			HadoopUtil.delete(getConf(), out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取序列文件,返回前面numRecords记录
	 * 
	 * @param fPath
	 * @param conf
	 * @param numRecords
	 * @return
	 * @throws IOException
	 */
	public static Map<Writable, Writable> readFromFile(String fPath,
			Configuration conf, long numRecords) throws IOException {
		FileSystem fs = FileSystem.get(URI.create(fPath), conf);
		Path path = new Path(fPath);
		Map<Writable, Writable> map = new LinkedHashMap<Writable, Writable>();
		SequenceFile.Reader reader = null;
		try {
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
			while (reader.next(key, value)) {
				// Writable的深度复制
				Writable k = deepCopy(key, writableClassK);
				Writable v = deepCopy(value, writableClassV);
				map.put(k, v);
				if (map.size() > numRecords) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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
	public static Writable deepCopy(Writable source,
			Class<Writable> writableClass) {
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
			throw new CrunchRuntimeException("Error while deep copying "
					+ source, e);
		}
		return copiedValue;
	}

	/**
	 * 读取知识库
	 * 
	 * @return
	 * @throws IOException
	 */
	public static List<Knowledge> getKnowledgeFromFp() throws IOException {
		List<Knowledge> list = new ArrayList<Knowledge>();
		String fPath = FP_OUTPATH + "/frequentpatterns/part-r-00000";
		FileSystem fs = FileSystem.get(URI.create(fPath), getConf());
		Path path = new Path(fPath);
		SequenceFile.Reader reader = null;
		try {
			reader = new SequenceFile.Reader(fs, path, getConf());
			Writable key = (Writable) ReflectionUtils.newInstance(
					reader.getKeyClass(), getConf());
			Writable value = (Writable) ReflectionUtils.newInstance(
					reader.getValueClass(), getConf());

			while (reader.next(key, value)) {
				Text k = (Text) key;
				TopKStringPatterns v = (TopKStringPatterns) value;
				List<Pair<List<String>, Long>> knowledge = v.getPatterns();
				Map<String, Long> map = new HashMap<String, Long>();
				for (int i = knowledge.size() - 1; i >= 0; i--) {
					Pair<List<String>, Long> pair = knowledge.get(i);
					List<String> plist = pair.getFirst();
					plist.remove(k.toString());
					for (String pl : plist) {
						map.put(pl, pair.getSecond());
					}
				}
				Set<Entry<String, Long>> set = map.entrySet();
				for (Entry<String, Long> s : set) {
					list.add(new Knowledge(Integer.parseInt(k.toString()),
							Integer.parseInt(s.getKey()), s.getValue().intValue()));
				}
			}
		} finally {
			IOUtils.closeStream(reader);
		}
		return list;
	}

}

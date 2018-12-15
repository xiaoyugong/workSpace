package mahout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class FP implements Runnable {

	private int minSupport;
	private int maxHeap;
	private int numGroups;
	private int treeCache;
	private String splitter;
	private String input;

	public FP(int minSupport, int maxHeap, int numGroups, int treeCache,String splitter, int numReducer, String input, String output) {
		this.minSupport = minSupport;
		this.maxHeap = maxHeap;
		this.numGroups = numGroups;
		this.treeCache = treeCache;
		this.splitter = splitter;
		this.input = input;
	}

	public void run() {
		String[] args = new String[14];

		args[0] = "-i";
		// args[1]=HadoopUtils.FP_INPATH;
		args[1] = input;
		// args[2]="-o";
		// args[3]=HadoopUtils.FP_OUTPATH;
		args[2] = "-method";
		args[3] = "mapreduce";
		args[4] = "-regex";
		args[5] = splitter;
		args[6] = "-g"; // groupNums
		args[7] = String.valueOf(numGroups);
		args[8] = "-s"; // 最小支持度
		args[9] = String.valueOf(minSupport);
		args[10] = "-k";
		args[11] = String.valueOf(maxHeap); // 考虑到后期的整合 ,默认使用20
		args[12] = "-tc";
		args[13] = String.valueOf(treeCache);

		// 调用rest接口
		String result = "";
		String url = "http://172.18.200.135:8888/DMP/rest/DMPImplService/dm/fp";
		// "/fpdata&-method:mapreduce,-regex:,,-g:7,-s:3,-k:50,-tc:5";
		url = url + "/" + args[1] + "&";
		for (int i = 2; i < args.length - 1; i++) {
			url = url + args[i] + ":" + args[++i] + ",";
		}
		url = url.substring(0, url.length() - 1);
		System.out.println(url);
		HttpGet get = new HttpGet(url);

		try {
			HttpResponse httpResponse = new DefaultHttpClient().execute(get);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				BufferedReader rd = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
				String line = "";
				while ((line = rd.readLine()) != null) {
					System.out.println(line);
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			result = e.getMessage().toString();
			System.out.println("httpresponse:" + result);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = e.getMessage().toString();
			System.out.println("httpresponse:" + result);
		} catch (IOException e) {
			e.printStackTrace();
			result = e.getMessage().toString();
			System.out.println("httpresponse:" + result);
		}
			
			
//			try {
//				//调用fp算法
//				FPGrowthDriver.main(args);
//				//解析结果，重新写入hdfs
//				boolean flag=Parser.Parser("hdfs://172.18.200.135:8020/outOFgxy/outputFP/frequentpatterns/part-r-00000",
//						"hdfs://172.18.200.135:8020/outOFgxy/outputFP/result", "172.18.200.135", new AKVRegex());
//				System.out.println("解析结果"+flag);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
}
		
		public int getMinSupport() {
			return minSupport;
		}
		public void setMinSupport(int minSupport) {
			this.minSupport = minSupport;
		}
		public int getMaxHeap() {
			return maxHeap;
		}
		public void setMaxHeap(int maxHeap) {
			this.maxHeap = maxHeap;
		}
		public int getNumGroups() {
			return numGroups;
		}
		public void setNumGroups(int numGroups) {
			this.numGroups = numGroups;
		}
		public int getTreeCache() {
			return treeCache;
		}
		public void setTreeCache(int treeCache) {
			this.treeCache = treeCache;
		}
		public String getSplitter() {
			return splitter;
		}
		public void setSplitter(String splitter) {
			this.splitter = splitter;
		}
}

package com.goldtel.dmp.monitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.goldtel.dmp.Const;

/**
 * 集群资源监控
 * @author gxy
 * 2015-12-14
 */
public final class ClusterResourceMonitor {
	
	/**
	 * getClusterMetrics 获取集群资源度量
	 * @return {@link ClusterMetrics}
	 */
	public static ClusterMetrics getClusterMetrics(){
		String url = "http://" + Const.HDFS_IP + ":8088/ws/v1/cluster/metrics";

		JSONObject jsonObject = restRequest(url);

		ClusterMetrics clusterMetrics = (ClusterMetrics) JSONObject.toBean(
				jsonObject.getJSONObject("clusterMetrics"),
				ClusterMetrics.class);

		return clusterMetrics;
	}
	
	/**
	 * getNodes 获取集群节点信息
	 * @return List<Node>
	 */
	public static List<Node> getNodes(){
		String url = "http://" + Const.HDFS_IP + ":8088/ws/v1/cluster/nodes";
		
		JSONObject jsonObject = restRequest(url);

		List<Node> nodes = new ArrayList<Node>();
		JSONArray jsonArray = jsonObject.getJSONObject("nodes").getJSONArray(
				"node");
		for (int i = 0; i < jsonArray.size(); i++) {
			nodes.add((Node) JSONObject.toBean(jsonArray.getJSONObject(i),
					Node.class));
		}

		return nodes;
	}
	
	/**
	 * getApplication 根据application_id参数获取application信息
	 * @param param
	 * @return {@link Application}
	 */
	public static Application getApplication(String param){
		String url = "http://" + Const.HDFS_IP + ":8088/ws/v1/cluster/apps/" + param;
		
		JSONObject jsonObject = restRequest(url);
		
		Application application = (Application) JSONObject.toBean(jsonObject.getJSONObject("app"), Application.class);
		
		return application;
	}
	
	/**
	 * 获取正在运行的application的信息
	 * @return {@link Application}
	 */
	public static List<Application> getRunningApplication(){
		String url = "http://" + Const.HDFS_IP + ":8088/ws/v1/cluster/apps?states=running";
		
		JSONObject jsonObject = restRequest(url);
		
		if (jsonObject.get("apps").toString() != "null") {
			JSONArray jsonArray = jsonObject.getJSONObject("apps").getJSONArray("app");
			List<Application> applications = new ArrayList<Application>();
			for(int i = 0; i < jsonArray.size(); i++){
				applications.add((Application) JSONObject.toBean(
						jsonArray.getJSONObject(i), Application.class));
			}
			
			return applications;
		} else {
			return null;
		}
	}
	
	/**
	 * Rest请求方法  返回请求结果
	 * @param url
	 * @return {@link JSONObject}
	 */
	public static JSONObject restRequest(String url){
		try{
			URL requestUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) requestUrl.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(2000);
			
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException(
						"HTTP GET Request Failed with Error code : "
								+ conn.getResponseCode());
			}
			
			BufferedReader responseBuffer = new BufferedReader(
					new InputStreamReader((conn.getInputStream())));
			String resp = responseBuffer.readLine();
			JSONObject jsonObject = JSONObject.fromObject(resp);
			
			//打印请求结果
			System.out.println(jsonObject.toString());

			conn.disconnect();
			return jsonObject;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static void main(String[] args) {
		getClusterMetrics();
		getNodes();
		getApplication("application_1449200761796_0254");
		getRunningApplication();
	}
}

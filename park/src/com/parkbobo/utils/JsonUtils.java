package com.parkbobo.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtils {
	public static void main(String[] args) {
		String str = "[{\"jcxbh\":\"1\", \"jcjg\":\"0\"},{\"jcxbh\":\"2\", \"jcjg\":\"0\"},{\"jcxbh\":\"3\", \"jcjg\":\"1\"},{\"jcxbh\":\"4\", \"jcjg\":\"0\"},{\"jcxbh\":\"5\", \"jcjg\":\"0\"},{\"jcxbh\":\"6\", \"jcjg\":\"1\"},{\"jcxbh\":\"7\", \"jcjg\":\"0\"},{\"jcxbh\":\"8\", \"jcjg\":\"0\"},{\"jcxbh\":\"9\", \"jcjg\":\"0\"}]";

		JSONArray jsonArray = JSONArray.fromObject(str);
		for(int i = 0; i<jsonArray.size();i++)
		{
			JSONObject obj = jsonArray.getJSONObject(i);
			System.out.println(obj.get("jcxbh"));
			System.out.println(obj.get("jcjg"));
			System.out.println("-------");
		}
	}
}

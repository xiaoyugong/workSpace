package com.weixin.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;


public class HttpUtil {

	public static String  requestGet(String url) {
		String respstring = null;
		HttpClient client = new HttpClient();
		//设置连接超时时间
		client.getHttpConnectionManager().getParams().setConnectionTimeout(2000);
		GetMethod get = new GetMethod(url);
		try {
			int statusCode  =client.executeMethod(get);
			if(statusCode==HttpStatus.SC_OK){
				respstring = get.getResponseBodyAsString();
				System.out.println(respstring);
			};
			
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return respstring;
	}
	
	public static String  requestPost(String url,String jsonParm) {
		HttpClient client = new HttpClient();
		//设置连接超时时间
		client.getHttpConnectionManager().getParams().setConnectionTimeout(2000);
		PostMethod post = new PostMethod(url);
        try {
        	RequestEntity entity = new StringRequestEntity(jsonParm,"text/html","utf-8");
        	post.setRequestEntity(entity);
        	client.executeMethod(post);
        	
			 System.out.println(post.getResponseBodyAsString());
			 return post.getResponseBodyAsString();
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		post.releaseConnection();  
		return null;
		
	}
	
	
	public static String  requestPost(String url,File file) {
		HttpClient client = new HttpClient();
		//设置连接超时时间
		client.getHttpConnectionManager().getParams().setConnectionTimeout(50000);
		PostMethod post = new PostMethod(url);
        try {
        	FilePart filePart = new FilePart("media",file); 
        	Part[] parts = {
        			filePart
        		  };
        	MultipartRequestEntity  multipartRequestEntity = new MultipartRequestEntity
        	(parts,post.getParams());
        	
        	post.setRequestEntity(multipartRequestEntity);   
        	post.getParams().setContentCharset("UTF-8");   
        	client.executeMethod(post);
			 System.out.println(post.getResponseBodyAsString());
			 return post.getResponseBodyAsString();
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		post.releaseConnection();  
		return null;
		
	}
	
}

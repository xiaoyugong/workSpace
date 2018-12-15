package com.weixin.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.sun.xml.internal.bind.v2.model.core.ID;
import com.sun.xml.internal.ws.util.xml.XmlUtil;
import com.weixin.msg.BaseMsg;
import com.weixin.msg.ImageMsg;
import com.weixin.msg.LocationMsg;
import com.weixin.msg.ShortvideoMsg;
import com.weixin.msg.TextMsg;
import com.weixin.msg.VideoMsg;
import com.weixin.msg.VoiceMsg;
import com.weixin.returnMsg.NewsArticle;
import com.weixin.returnMsg.ReturnBaseMsg;
import com.weixin.returnMsg.ReturnNewsMsg;


public class XMLHandler {
	
	
	private static String[] pros ;
	
	private XMLHandler(){}
	
	/**
	 * 
	 * @param in
	 * @return
	 * @throws IOException 
	 */
	public static Map parseXML(InputStream in) throws IOException {
		 
		
		HashMap<String, String> map = new HashMap();
		SAXReader reader = new SAXReader();
		
		 try {
			 Document  doc = reader.read(in);
//			doc = DocumentHelper.parseText(string);
			Element rootElt = doc.getRootElement();
			List<Element> iterator = rootElt.elements();
			
			for(Element element:iterator){
				String name = element.getName();
				String value = element.getStringValue();
				map.put(name, value);
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return map;
	}
	
	

	/*
	 * 
 <xml>
<ToUserName><![CDATA[toUser]]></ToUserName>
<FromUserName><![CDATA[fromUser]]></FromUserName>
<CreateTime>1357290913</CreateTime>
<MsgType><![CDATA[video]]></MsgType>
<Content>这是内容</Content>
<MsgId>1234567890123456</MsgId>
</xml>
	 */
	
	/**
	 * 
	 * 响应的xml
	 * 通过反射创建通用xml生成方法
	 * @param textMsg
	 * @return
	 */
	public static String createXml(ReturnBaseMsg baseMsg){
		if(baseMsg.getMsgType().equals("text")){
			return createReturnTextMsgXml(baseMsg);
		}
		if(baseMsg.getMsgType().equals("news")){
			return createReturnNewsMsgXml((ReturnNewsMsg)baseMsg);
		}
		if(baseMsg.getMsgType().equals("event")){
			return createReturnTextMsgXml(baseMsg);
		}
		
		return null;
	}
	/*
	 * <xml>
<ToUserName><![CDATA[toUser]]></ToUserName>
<FromUserName><![CDATA[fromUser]]></FromUserName>
<CreateTime>12345678</CreateTime>
<MsgType><![CDATA[news]]></MsgType>
<ArticleCount>2</ArticleCount>
<Articles>
<item>
<Title><![CDATA[title1]]></Title> 
<Description><![CDATA[description1]]></Description>
<PicUrl><![CDATA[picurl]]></PicUrl>
<Url><![CDATA[url]]></Url>
</item>
</Articles>
</xml>
	 * 
	 */
	
	/**
	 * 把ReturnNewsMsg创建为 xml
	 * @return
	 */
	public static String createReturnNewsMsgXml(ReturnNewsMsg news){
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		sb.append("<ToUserName><![CDATA["+news.getToUserName()+"]]></ToUserName>");
		sb.append("<FromUserName><![CDATA["+news.getFromUserName()+"]]></FromUserName>");
		sb.append("<CreateTime>"+news.getCreateTime()+"</CreateTime>");
		sb.append("<MsgType><![CDATA["+news.getMsgType()+"]]></MsgType>");
		sb.append("<ArticleCount>"+news.getArticleCount()+"</ArticleCount>");
		sb.append("<Articles>");
		for(NewsArticle actArticle:news.getArticles()){
			sb.append("<item>");
			sb.append("<Title><![CDATA["+StringUtil.removeNull(actArticle.getTitle())+"]]></Title> ");
			sb.append("<Description><![CDATA["+StringUtil.removeNull(actArticle.getDescription())+"]]></Description>");
			sb.append("<PicUrl><![CDATA["+StringUtil.removeNull(actArticle.getPicUrl())+"]]></PicUrl>");
			sb.append("<Url><![CDATA["+StringUtil.removeNull(actArticle.getUrl())+"]]></Url>");
			sb.append("</item>");
		}
		sb.append("</Articles>");
		sb.append("</xml>");
		
		
		return sb.toString();
	}
	
	/**
	 * 
	 * 把ReturnTextMsg创建为 xml
	 * @param baseMsg
	 * @return
	 */
	public static String createReturnTextMsgXml(ReturnBaseMsg baseMsg){
		StringBuffer xml = new StringBuffer("<xml>");
		try {
			
			Class[] classs = new Class[]{baseMsg.getClass().getSuperclass(),baseMsg.getClass()};
			
			for(Class cc:classs){
				Field[] sf =  cc.getDeclaredFields();
				for(Field field:sf){
					String tagName = field.getName().substring(0, 1).toUpperCase()+field.getName().substring(1);
					xml.append("<"+tagName+">");
					if(filterProper(tagName)){
						xml.append(""+cc.getDeclaredMethod("get"+tagName).invoke(baseMsg)+"");
					}
					else {
						xml.append("<![CDATA["+cc.getDeclaredMethod("get"+tagName).invoke(baseMsg)+"]]>");
						
					}
					xml.append("</"+tagName+">");
				}
			}
			
			xml.append("</xml>");
			
			
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return xml.toString();
		
	}
	
	

	
	
	/**
	 * @param map   xml解析后并封装为map
	 * @param basemsg  消息对象
	 * @return
	 */
	public static BaseMsg getMsg(Map<String,String> map,BaseMsg basemsg){
		try {
			Class[] classs=new Class[]{basemsg.getClass(),basemsg.getClass().getSuperclass()};
			for(Class c:classs){
				for(Entry<String, String> entry: map.entrySet()){
					Field[] field = c.getDeclaredFields();
					String tagName = entry.getKey().substring(0, 1).toLowerCase()+entry.getKey().substring(1);
					for(Field f:field){
						f.setAccessible(true);
						if(f.getName().equals(tagName)){
							f.set(basemsg, entry.getValue());
						}
					}
					
				}
			}
			return basemsg;
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	

	
	
	
	
	
	static{
		
		 pros = new String[]{"CreateTime","MsgId",
				"Location_X","Location_Y","Scale"};
	}
	
	/**
	 * 判断属性是否加<![CDATA[]]>
	 * true  不加
	 * false 加
	 * @param pro
	 * @return
	 */
	public static boolean filterProper(String pro){
		for(String s : pros){
			if(s.equals(pro))
				return true;
		}
		return false;
	}

	
}

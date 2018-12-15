package com.test;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class TestJsoup {

	
	
	
	@Test
	public void testJsoup() throws Exception {
	 Connection connection = Jsoup.connect("http://www.eastmoney.com/");
	 Document doc =  connection   // 请求参数  
	 .userAgent("Mozilla")
	  .cookie("auth", "token")
	  .timeout(3000)
	  .get();
	 
	 Elements element = doc.getElementsByTag("a");
	 for(Element e : element){
		 
		 System.out.println("标题："+e.text());
		 System.out.println("链接："+e.attr("href"));
	 }
	}
	
}

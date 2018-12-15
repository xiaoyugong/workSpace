package com.parkbobo.utils;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class SMSSend {
	private static SMSSend sMSSend;
	private static String URL = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";
	
	private SMSSend(){}
	public synchronized static SMSSend getDefaultInstance(){
		if(sMSSend==null){
			sMSSend = new SMSSend();
		}
		return sMSSend;
		
	}
	/**
	 * 短信验证码发送
	 * @return
	 */
	public String iuyiSendSms(String content, String telephone)
	{
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(URL);
		client.getParams().setContentCharset("UTF-8");
		method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=UTF-8");
		NameValuePair[] data = {//提交短信
			    new NameValuePair("account", "cf_weibokeji"), 
			    new NameValuePair("password", "6992005ych"), //密码可以使用明文密码或使用32位MD5加密
			    //new NameValuePair("password", util.StringUtil.MD5Encode("密码")),
			    new NameValuePair("mobile", telephone), 
			    new NameValuePair("content", content),
		};
		method.setRequestBody(data);
		try {
			client.executeMethod(method);	
			
			String SubmitResult =method.getResponseBodyAsString();
					
			System.out.println(SubmitResult);

			Document doc = DocumentHelper.parseText(SubmitResult); 
			Element root = doc.getRootElement();


			String code = root.elementText("code");	
			return code;
//			if(code.equals("2")){
//				System.out.println("短信提交成功");
//				return true;
//			}
//			else
//			{
////				4085 验证码短信每天每个手机号码只能发5条
//				System.out.println("短信提交失败");
//				return false;
//			}
			
		} catch (HttpException e) {
			e.printStackTrace();
			return "";
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		} catch (DocumentException e) {
			e.printStackTrace();
			return "";
		}	
	}
	
	
	public static void main(String[] args) {
		SMSSend.getDefaultInstance().iuyiSendSms("您的验证码是：123456。请不要把验证码泄露给其他人。", "15680673685");
	}
}

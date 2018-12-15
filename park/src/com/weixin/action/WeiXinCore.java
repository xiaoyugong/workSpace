package com.weixin.action;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.util.WebUtils;

import com.opensymphony.xwork2.ActionContext;
import com.parkbobo.model.Users;
import com.parkbobo.service.ServiceLocator;
import com.parkbobo.service.UsersService;
import com.weixin.control.WeiXinAPI;
import com.weixin.control.WeiXinControl;
import com.weixin.msg.BaseMsg;
import com.weixin.msg.EventMsg;
import com.weixin.returnMsg.ReturnBaseMsg;
import com.weixin.util.XMLHandler;

/**
 * 作者:卢鸶
 * 时间:2015-4-14
 * 描述:接收微信的核心方法
 */
public class WeiXinCore extends HttpServlet{

	private WeiXinAPI weiXinAPI;
	private WeiXinControl weiXinControl;
	/**
	 * Constructor of the object.
	 */
	public WeiXinCore() {
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		boolean result = false;
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String signature = request.getParameter("signature");
		String echostr = request.getParameter("echostr");
		try {
			System.out.println(timestamp+"=="+nonce+"=="+signature);
			result = weiXinAPI.checkSignature(timestamp,nonce,signature);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		if(result){
			OutputStream os = response.getOutputStream();
            BufferedWriter resBr = new BufferedWriter(new OutputStreamWriter(os));
            resBr.write(echostr);
            resBr.flush();
            resBr.close();
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			
			
			//接收微信传过来的数据
			BaseMsg msg = weiXinControl.getMsg(request.getInputStream());
			System.out.println(msg);
			if(msg!=null){
				ReturnBaseMsg msg2 = weiXinControl.control(msg);
				String responsexml =XMLHandler.createXml(msg2);
				System.out.println(responsexml);
				 simpleResponse(responsexml,response);	
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
		ServiceLocator serviceLocator = ServiceLocator.getInstance();
		weiXinAPI = (WeiXinAPI) serviceLocator.getService("weiXinAPI");
		weiXinControl = (WeiXinControl) serviceLocator.getService("weiXinControl");
	}
	
	
	public String simpleResponse(String data,HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		try {
			PrintWriter pw =  response.getWriter();
			pw.print(data);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}

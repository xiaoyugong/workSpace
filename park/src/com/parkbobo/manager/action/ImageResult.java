package com.parkbobo.manager.action;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
/**
 * 自定义Struts结果类型
 * @author LH
 *
 */
public class ImageResult implements Result{
	private static final long serialVersionUID = -6086577365177747929L;
	public void execute(ActionInvocation ai) throws Exception {
	       ValidateCodeAction action = (ValidateCodeAction)ai.getAction();
	       HttpServletResponse response = ServletActionContext.getResponse();
	       response.setHeader("Pragma", "No-cache"); 
	       response.setHeader("Cache-Control", "no-cache"); 
	       response.setDateHeader("Expires", 0); 
	       response.setHeader("Cash", "no cash");
	       response.setContentType(action.getContentType());
	       response.setContentLength(action.getContentLength());
	       response.getOutputStream().write(action.getImageBytes());
	       response.getOutputStream().flush();
	}	
}

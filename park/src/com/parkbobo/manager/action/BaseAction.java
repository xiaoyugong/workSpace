package com.parkbobo.manager.action;


import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.ValueStack;
import com.parkbobo.manager.model.Manager;
import com.parkbobo.model.OptLogs;
import com.parkbobo.service.OptLogsService;

public abstract class BaseAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7882607718798235121L;
	private int page;
	private int pageSize;
	private String method;
	private String forwardUrl;
	private String ids;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	@Resource(name="optLogsService")
	private OptLogsService logsService;
	public String downloadFileName;
	protected ValueStack valueStack;
	public BaseAction(){
		valueStack = ActionContext.getContext().getValueStack();
	}
	
	public void saveLog(String description) {
		Manager manager = (Manager) ActionContext.getContext().getSession().get("loginManager");
		OptLogs optLogs = new OptLogs();
		optLogs.setCreateTime(System.currentTimeMillis());
		optLogs.setDescription(description);
		optLogs.setFromModel(this.logModel());
		optLogs.setUserId(manager.getUserId());
		optLogs.setUsername(manager.getUsername());
		try {
			logsService.add(optLogs);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public String responseString(String json) {
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.print(json);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String forward(String url)
	{
		this.forwardUrl = url;
		return SUCCESS;
	}
	/**
	 * 获取session
	 * @return
	 */
	public HttpSession getSession(){
		return ServletActionContext.getRequest().getSession(true);
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPageSize() {
		if(pageSize == 0){
			pageSize = 12;
		}
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getForwardUrl() {
		return forwardUrl;
	}
	public void setForwardUrl(String forwardUrl) {
		this.forwardUrl = forwardUrl;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
		this.response.setContentType("text/html;charset=utf-8");
	}
	
	public abstract  String logModel();
	
}

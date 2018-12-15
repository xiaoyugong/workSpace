package com.parkbobo.port.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.parkbobo.model.BerthOrder;
import com.parkbobo.model.CarparkDevice;
import com.parkbobo.model.CarparkDeviceRequestLog;
import com.parkbobo.service.BerthOrderService;
import com.parkbobo.service.CarparkDeviceRequestLogService;
import com.parkbobo.service.CarparkDeviceService;
import com.parkbobo.service.CarparkOrderService;
import com.parkbobo.service.UsersService;

public class PortBaseAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{
	@Resource(name="carparkOrderService")
	protected CarparkOrderService carparkOrderService;
	@Resource(name="carparkDeviceService")
	protected CarparkDeviceService carparkDeviceService;
	@Resource(name="usersService")
	protected UsersService usersService;
	@Resource(name="berthOrderService")
	protected BerthOrderService berthOrderService;
	@Resource(name="carparkDeviceRequestLogService")
	private CarparkDeviceRequestLogService carparkDeviceRequestLogService;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected String carNum;
	protected String parkid;
	protected String sbbm;
	protected String password;
	
	
	
	public String responseErrorData(String errorcode) throws IOException{
		String json = "{\"status\":\"false\",\"errorcode\":\""+errorcode+"\"}";
		response.getWriter().print(json);
		return null;
	}
	
	
	
	public String responseSuccessData(String... json) throws IOException{
		if(json.length==1)
			response.getWriter().print(json[0]);
		if(json.length==0)
			response.getWriter().print("{\"status\":\"true\"}");
			
		return null;
	}
	
	protected void log(String details,Short optType) {
		CarparkDeviceRequestLog log = new CarparkDeviceRequestLog();
		log.setIsDel((short)1);
		log.setOptType(optType);
		log.setDetails(details);
		log.setCarNumber(carNum);
		log.setLogDateTime(new Date());
		log.setCarparkDevice(carparkDeviceService.getCarparkDeviceByNum(sbbm));
		carparkDeviceRequestLogService.add(log);
	}
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	@Override
	public void setServletResponse(HttpServletResponse response) {
		// TODO Auto-generated method stub
		this.response = response;
		this.response.setContentType("text/html;charset=utf-8");
	}
	public String getCarNum() {
		return carNum;
	}
	public void setCarNum(String carNum) throws UnsupportedEncodingException {
		this.carNum = new String(carNum.getBytes("iso-8859-1"),"UTF-8" );
		this.carNum = carNum;
	}
	public String getParkid() {
		return parkid;
	}
	public void setParkid(String parkid) {
		this.parkid = parkid;
	}
	public String getSbbm() {
		return sbbm;
	}
	public void setSbbm(String sbbm) {
		this.sbbm = sbbm;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}

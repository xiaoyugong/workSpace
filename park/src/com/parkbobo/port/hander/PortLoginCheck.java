package com.parkbobo.port.hander;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.parkbobo.model.CarparkDevice;
import com.parkbobo.model.CarparkDeviceRequestLog;
import com.parkbobo.service.CarparkDeviceService;
import com.parkbobo.utils.MD5;

@Component
@Aspect
public class PortLoginCheck {
	@Resource(name="carparkDeviceService")
	protected CarparkDeviceService carparkDeviceService;
	public static final String EDP = "execution(* " +
			"com.parkbobo.port.action.*.*Port(..))";
	public static final String SERVICE = "execution(* " +
	"com.parkbobo.service.CarparkOrderService.*(..))||" +
	"execution(* com.parkbobo.service.CarparkDeviceService.*(..))";
	@Pointcut(EDP)
	private void assignMethod(){}
	
	@Pointcut(SERVICE)
	private void serviceMethod(){}
	
	
	@Around("assignMethod()")  
	public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable{  
		HttpServletResponse response  = ServletActionContext.getResponse();
		String json = "{\"status\":\"false\",\"errorcode\":01}";
		Object object = null;
		if(checkLogin()){
         object = pjp.proceed();//执行该方法  
         return object;  
		}
		else
			response.getWriter().print(json);
		return null;
    } 
	
	
//	@AfterReturning(pointcut="serviceMethod()",returning="returnValue")
//	public void logRecord(JoinPoint point, Object returnValue) {
//		CarparkDeviceRequestLog log = new CarparkDeviceRequestLog();
//		log.setIsDel((short)0);
//		if(point.getSignature().getName().contains("update")){
//		}
//		if(point.getSignature().getName().contains("get")){}
//		if(point.getSignature().getName().contains("delete")){}
//		
//	}
	
	
	private boolean checkLogin(){
		try {
			HttpServletRequest request  = ServletActionContext.getRequest();
			String password= request.getParameter("password");
			String sbbm=request.getParameter("sbbm");
			Long parkid=Long.parseLong(request.getParameter("parkid"));
			String loginHql="from CarparkDevice where isDel=0 " +
			"and isStop=0" +
			"and password='"+password+"' and deviceNumber='"+sbbm+"' and carpark.carparkid="+parkid;
			List<CarparkDevice> devices = carparkDeviceService.getByHql(loginHql);
			
			if(devices.size()>0){
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return false;
		
	}
	
	
}

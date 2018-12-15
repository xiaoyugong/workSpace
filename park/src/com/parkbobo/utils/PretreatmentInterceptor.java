package com.parkbobo.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.parkbobo.model.WithdrawLog;
import com.parkbobo.service.BerthOrderService;
import com.parkbobo.service.CarparkAuthenticationService;
import com.parkbobo.service.DefaultApplyService;
import com.parkbobo.service.DriverAuthenticationService;
import com.parkbobo.service.WithdrawLogService;


/**
 * @author Administrator
 *对处理申请进行检查
 */
public class PretreatmentInterceptor extends AbstractInterceptor {

	 
	@Override
	public String intercept(ActionInvocation action) throws Exception {
		// TODO Auto-generated method stub
		HttpServletRequest request = ServletActionContext.getRequest();
		String reqURL = request.getRequestURI();
		System.out.println(reqURL);
		//每一次_list请求都获得一次提现未处理数量
		if(reqURL.matches(".*list")||reqURL.matches(".*/main_index")){
			WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
			//提现未处理
			WithdrawLogService wService = context.getBean(WithdrawLogService.class);
			String hqlString = "select count(o) from WithdrawLog o where isDel = 0 and status=0";
			int count =  wService.totalCount(hqlString);
			request.getSession().setAttribute("withdrawDispose", count);
			//违约处理申请
			DefaultApplyService applyService = context.getBean(DefaultApplyService.class);
			String defaultApplyhqlString = "select count(o) from DefaultApply o where status=0";
			int applycount =  applyService.totalCount(defaultApplyhqlString);
			request.getSession().setAttribute("defaultApplyDispose", applycount);
			//车位认证
			CarparkAuthenticationService carparkAuthenticationService = context.getBean(CarparkAuthenticationService.class);
			String carparkAuthenticationhqlString = "select count(o) from CarparkAuthentication o where status=0";
			int carparkAuthentication =  carparkAuthenticationService.totalCount(carparkAuthenticationhqlString);
			request.getSession().setAttribute("carparkAuthenticationDispose", carparkAuthentication);
			//车主认证
			DriverAuthenticationService ds = context.getBean(DriverAuthenticationService.class);
			String driverAuthenticationhqlString = "select count(o) from DriverAuthentication o where status=0";
			int driverAuthenticationcount =  ds.totalCount(driverAuthenticationhqlString);
			request.getSession().setAttribute("driverAuthenticationDispose", driverAuthenticationcount);
		
			//超期停车
			BerthOrderService bs = context.getBean(BerthOrderService.class);
			String berthOrderhqlString = "select count(o) from BerthOrder o where status=0 and isDel=0 and endMillisecond<="+System.currentTimeMillis();
			int driverOrderCqCount =  ds.totalCount(berthOrderhqlString);
			request.getSession().setAttribute("driverOrderCqCount", driverOrderCqCount);
		
		};
		return action.invoke();
	}

	
	
	
	
}

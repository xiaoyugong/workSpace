package com.lqkj.security;

import java.util.Date;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.parkbobo.manager.model.Manager;
import com.parkbobo.manager.service.ManagerService;
import com.parkbobo.model.OptLogs;
import com.parkbobo.service.OptLogsService;
import com.parkbobo.utils.RequestUtils;
import com.parkbobo.utils.WebUtils;


public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	private static final Logger logger = Logger.getLogger(MyUsernamePasswordAuthenticationFilter.class);
	public static final String VALIDATE_CODE = "validateCode";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	@Resource(name="managerService")
	private ManagerService managerService;
	@Resource(name="optLogsService")
	private OptLogsService logsService;
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		if (!request.getMethod().equals("POST")) {
			if (logger.isDebugEnabled()) {
				logger.debug("不支持的提交方式: " + request.getMethod()); //$NON-NLS-1$
			}
			throw new AuthenticationServiceException("不支持的提交方式: " + request.getMethod());
		}
		//验证码校验
		checkValidateCode(request);
		String username = obtainUsername(request).trim();
		String password = obtainPassword(request).trim();
		ShaPasswordEncoder sp = new ShaPasswordEncoder();
		
		//根据username查询出用户信息，第一次查询
		Manager manager = new Manager();
		if(WebUtils.getDefaultInstance().isVaildEmail(username)){
			manager = managerService.getUniqueByProperty("email", username);
		}else{
			manager = managerService.getUniqueByProperty("username", username);
		}
		if(manager == null||!sp.encodePassword(password, manager.getUsername()).equals(manager.getPassword())){
			if (logger.isDebugEnabled()) {
				logger.debug("用户名或密码错误！"); //$NON-NLS-1$
			}
			throw new AuthenticationServiceException("用户名或密码错误！");
		}
		//更新登录信息：登录时间，登录IP，登录次数
		String [] propertyNames = {"lastLoginIp","lastLoginTime","loginCount"};
		Object [] values = {RequestUtils.getDefaultInstance().getRemoteAddr(request),new Date().getTime(),manager.getLoginCount()+1};
		managerService.localUpdateOneFields(manager.getUserId(), propertyNames, values);
		//UsernamePasswordAuthenticationToken实现 Authentication
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(manager.getUsername(), password);
		// Place the last username attempted into HttpSession for views
		HttpSession session = request.getSession();
		session.setAttribute(USERNAME, manager.getUsername());
	
		session.setAttribute("loginManager", manager);
		// 允许子类设置详细属性
		setDetails(request, authRequest);
		
		//保存登陆日志
		logsService.add(new OptLogs(null,"登录",manager.getUserId(),manager.getUsername(),"登录到系统",System.currentTimeMillis(),""));
		
		// 运行UserDetailsService的loadUserByUsername 再次封装Authentication
		return this.getAuthenticationManager().authenticate(authRequest);
	}
	
	
	
	protected void checkValidateCode(HttpServletRequest request) { 
		HttpSession session = request.getSession();
		
	    String sessionValidateCode = obtainSessionValidateCode(session); 
	    //让上一次的验证码失效
	    session.setAttribute(VALIDATE_CODE, null);
	    String validateCodeParameter = obtainValidateCodeParameter(request);  
	    if (StringUtils.isEmpty(validateCodeParameter) || !sessionValidateCode.equalsIgnoreCase(validateCodeParameter)) {  
	    	if (logger.isDebugEnabled()) {
				logger.debug("验证码错误！"); //$NON-NLS-1$
			}
	        throw new AuthenticationServiceException("验证码错误！");  
	    }  
	}
	
	private String obtainValidateCodeParameter(HttpServletRequest request) {
		Object obj = request.getParameter(VALIDATE_CODE);
		return null == obj ? "" : obj.toString();
	}

	protected String obtainSessionValidateCode(HttpSession session) {
		Object obj = session.getAttribute(VALIDATE_CODE);
		return null == obj ? "" : obj.toString();
	}

	@Override
	protected String obtainUsername(HttpServletRequest request) {
		Object obj = request.getParameter(USERNAME);
		return null == obj ? "" : obj.toString();
	}

	@Override
	protected String obtainPassword(HttpServletRequest request) {
		Object obj = request.getParameter(PASSWORD);
		return null == obj ? "" : obj.toString();
	}
	
	
}

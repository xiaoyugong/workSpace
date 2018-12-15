package com.weixin.quartz;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionContext;
import com.parkbobo.service.WeiXinConfigService;
import com.weixin.control.WeiXinAPI;
import com.weixin.model.Config;
import com.weixin.util.HttpUtil;


public class WeiXinQuartz {
	private final static String APPID="wx9c00d8ef6549ea12";
	private final static String APPSECRET="19794d7f6575bb2fc3f39b04409b13a3";
	private final static String TOKEN="6992005Ych";
	@Resource(name="weiXinConfigService")
	private WeiXinConfigService weiXinConfigService;
	public void execute(){
		String str = HttpUtil.requestGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APPID+"&secret="+APPSECRET+"");
		String access_token = (String) JSONObject.fromObject(str).get("access_token");
		System.out.println(str);
		if(access_token!=null){
			Config config = weiXinConfigService.get(1);
			config.setAccess_token(access_token);
			weiXinConfigService.update(config);
		}
	}
	
	//ThreadLocal
}

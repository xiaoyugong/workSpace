package com.parkbobo.manager.action;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("carparkDeviceAction")
@Scope("prototype")
public class CarparkDeviceAction extends BaseAction{

	
	//2.1.	业主身份绑定
	
	
	@Override
	public String logModel() {
		// TODO Auto-generated method stub
		return "";
	}

}

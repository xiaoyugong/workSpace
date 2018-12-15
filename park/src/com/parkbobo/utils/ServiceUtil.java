package com.parkbobo.utils;

import java.util.ArrayList;
import java.util.List;


import com.parkbobo.model.Carpark;
import com.parkbobo.model.Users;

public class ServiceUtil {
	private static ServiceUtil serviceUtil; 
	private ServiceUtil(){
		
	}
	
	public synchronized static ServiceUtil getDefaultInstance(){
		if(serviceUtil==null){
			serviceUtil = new ServiceUtil();
		}
		return serviceUtil;
	}
	public List<String> getClientIdByUserList(List<Users> users) {
		List<String> list = new ArrayList<String>();
		for(Users user:users){
			list.add(user.getClientid());
		}
		return list;
	}
	
	public List<String> getClientIdByCarparkList(List<Carpark> carparks) {
		return null;
	}
	
	
}

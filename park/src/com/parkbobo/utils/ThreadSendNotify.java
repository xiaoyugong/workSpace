package com.parkbobo.utils;

import com.parkbobo.model.Notify;
import com.parkbobo.model.Users;
import com.parkbobo.service.NotifyService;
import com.parkbobo.service.UsersService;

public class ThreadSendNotify implements Runnable{

	private UsersService userservice;
	private Notify notify;
	private Integer notifysendMode;
	private Integer notifyType;
	private String hql;
	private NotifyService notifyService;
	private PageBean<Users> users;
	private Integer page=0;
	private Integer pageSize = 2000;
	public ThreadSendNotify(UsersService userservice, Notify notify,
			Integer notifysendMode, Integer notifyType,String hql,NotifyService notifyService) {
		this.userservice = userservice;
		this.notify = notify;
		this.notifysendMode = notifysendMode;
		this.notifyType = notifyType;
		this.hql = hql;
		this.notifyService = notifyService;
	}

	public void run() {
		// TODO Auto-generated method stub
		send();
	}
	
	public   void send(){
		System.out.println("********************");
		System.out.println(users);
		if(users!=null)
		System.out.println("********************"+(users.isHasNextPage()));
		if(users==null||users.isHasNextPage()){
			users= userservice.loadPage(hql, pageSize, page);
			page = users.getCurrentPage()+1;
			notifyService.add(notify,users.getList(),notifysendMode,notifyType);
			send();
		}
	}

}

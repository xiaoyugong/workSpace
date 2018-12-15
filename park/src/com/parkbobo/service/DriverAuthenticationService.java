package com.parkbobo.service;
import org.apache.commons.collections.map.HashedMap;
import org.hibernate.hql.ast.SqlASTFactory;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.igetui.util.PushMessageUtil;
import com.parkbobo.dao.DriverAuthenticationDao;
import com.parkbobo.dao.NotifyDao;
import com.parkbobo.dao.UsersDao;
import com.parkbobo.model.DriverAuthentication;
import com.parkbobo.model.Notify;
import com.parkbobo.model.Users;
import com.parkbobo.utils.PageBean;
import com.sun.org.apache.bcel.internal.generic.NEW;
@Component("driverAuthenticationService")
public class DriverAuthenticationService {
	@Resource(name="driverAuthenticationDaoImpl")
	private DriverAuthenticationDao driverAuthenticationDao;
	@Resource(name="usersDaoImpl")
	private UsersDao usersDao;
	private static Map<Short, String> statusMap;
	@Resource(name="notifyDaoImpl")
	private NotifyDao notifyDao;
	static{
		statusMap = new HashMap();
		statusMap.put((short)1, "已通过");
		statusMap.put((short)2, "未通过");
		
	}
	public List<DriverAuthentication> getByHql(String hql){
		return this.driverAuthenticationDao.getByHQL(hql);
	}

	public boolean add(DriverAuthentication driverAuthentication) {
		try {
			this.driverAuthenticationDao.merge(driverAuthentication);
			Users users = usersDao.get(driverAuthentication.getUserid());
			users.setDriverStatus((short)1);
			usersDao.merge(users);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


	public DriverAuthentication getByUserid(String userid) {
		return driverAuthenticationDao.getUniqueByProperty("userid", userid);
	}


	public  void merge(DriverAuthentication driverAuthentication) {
		// TODO Auto-generated method stub
		driverAuthenticationDao.merge(driverAuthentication);
		//发送通知
		String notifyString = "您的车主认证"+statusMap.get(driverAuthentication.getStatus())+"。审核意见:" +
		(driverAuthentication.getMemo().trim().equals("")?"无":driverAuthentication.getMemo());
		Notify notify = new Notify(null, driverAuthentication.getUsers(), "车主认证通知", notifyString, 0, new Date().getTime() , 0,0, null);
		notify = notifyDao.add(notify);
		notifyDao.add(notify);
		PushMessageUtil.getDefaultInstance().pushTransmission
		(driverAuthentication.getUsers().getClientid(),"车主认证通知"
				,notifyString,notify.getNotifyid());
		
	}
	public PageBean<DriverAuthentication> loadPage(String hql, int pageSize,
			int page) {
		// TODO Auto-generated method stub
		
		return driverAuthenticationDao.pageQuery(hql, pageSize, page);
	}
	public void bulkDelete(String ids) {
		// TODO Auto-generated method stub
		driverAuthenticationDao.bulkDelete(ids.split(","));
	}
	
	public static void main(String[] args) {
		String string = "";
		String string2 = "";
		System.out.println(string.trim()==string2);
		
	}
	public int totalCount(String driverAuthenticationhqlString) {
		// TODO Auto-generated method stub
		return driverAuthenticationDao.totalCount(driverAuthenticationhqlString);
	}
	
	
	public void update(DriverAuthentication driverAuthentication){
		driverAuthenticationDao.update(driverAuthentication);
	}
}
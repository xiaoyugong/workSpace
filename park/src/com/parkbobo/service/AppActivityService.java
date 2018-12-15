package com.parkbobo.service;


import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.parkbobo.dao.AppActivityDao;
import com.parkbobo.model.AppActivity;
import com.parkbobo.utils.PageBean;

@Component("appActivityService")
public class AppActivityService {
	@Resource(name="appActivityDaoImpl")
	private AppActivityDao appActivityDao;
	
	public PageBean<AppActivity> loadPage(String hql, int pageSize, int page)
	{
		return  this.appActivityDao.pageQuery(hql, pageSize, page);
	}

	public AppActivity getActivityById(Long activityid) {
		// TODO Auto-generated method stub
		return appActivityDao.get(activityid);
	}
	public void updateAppActivity(AppActivity activity) {
		// TODO Auto-generated method stub
		 appActivityDao.update(activity);
	}

	public void add(AppActivity appActivity) {
		// TODO Auto-generated method stub
		appActivityDao.add(appActivity);
	}
	
	public void bulkDelete(String ids) {
		if(ids.length() > 0)
		{
			  String sqlString = "update AppActivity set isDel=1 where activityid in ("+ids+")";
			this.appActivityDao.deleteByHql(sqlString);
		
		}
	}
		
}

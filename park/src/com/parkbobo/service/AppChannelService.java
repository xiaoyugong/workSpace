package com.parkbobo.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.parkbobo.dao.AppChannelDao;
import com.parkbobo.model.AppChannel;
import com.parkbobo.utils.PageBean;

@Component("appChannelService")
public class AppChannelService {
	@Resource(name="appChannelDaoImpl")
	private AppChannelDao appChannelDao; 
	
	public PageBean<AppChannel> loadPage(String hql, int pageSize, int page)
	{
		return this.appChannelDao.pageQuery(hql, pageSize, page);
	}

	public void bulkDelete(String ids) 
	{
		if(ids.length() > 0)
		{
			String[] strs = ids.split(",");
			Long[] idArr = new Long[strs.length];
			for (int i=0; i< strs.length; i++) {
				idArr[i] = Long.valueOf(strs[i]);
			}
			this.appChannelDao.bulkDelete(idArr);
		}
	}

	public AppChannel getById(Long id) 
	{
		return this.appChannelDao.get(id);
	}

	public void add(AppChannel appChannel) 
	{
		this.appChannelDao.merge(appChannel);
	}

	public List<AppChannel> getByHql(String hql) {
		return this.appChannelDao.getByHQL(hql);
	}
}

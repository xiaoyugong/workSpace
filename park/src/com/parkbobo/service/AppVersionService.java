package com.parkbobo.service;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.parkbobo.dao.AppVersionDao;
import com.parkbobo.model.AppVersion;
import com.parkbobo.model.AppVersionId;
import com.parkbobo.utils.PageBean;
@Component("appVersionService")
public class AppVersionService {
	@Resource(name="appVersionDaoImpl")
	private AppVersionDao appVersionDao;

	public List<AppVersion> getByHql(String hql){
		return this.appVersionDao.getByHQL(hql);
	}
	public PageBean<AppVersion> loadPage(String hql,int pageSize, int page)
	{
		return this.appVersionDao.pageQuery(hql, pageSize, page);
	}
	
	public void add(AppVersion appVersion) {
		this.appVersionDao.merge(appVersion);
	}
	public AppVersion getById(AppVersionId id) {
		return this.appVersionDao.get(id);
	}
	public void update(AppVersion appVersion) {
		AppVersion av = this.appVersionDao.get(appVersion.getId());
		av.setAttached(appVersion.getAttached());
		av.setContent(appVersion.getContent());
		av.setNeedUpdate(appVersion.getNeedUpdate());
		this.appVersionDao.merge(av);
	}
	public void bulkDelete(String ids) {
		if(ids.length() > 0)
		{
			String[] idArr = ids.split(",");
			for (String s : idArr) {
				String[] idd = s.split("_");
				AppVersionId id = new AppVersionId(Long.valueOf(idd[0]), Long.valueOf(idd[1]));
				this.appVersionDao.delete(id);
			}
		}
	}
}
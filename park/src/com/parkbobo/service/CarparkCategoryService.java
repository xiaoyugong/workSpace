package com.parkbobo.service;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.List;

import com.parkbobo.dao.CarparkCategoryDao;
import com.parkbobo.model.CarparkCategory;
import com.parkbobo.utils.PageBean;
@Component("carparkCategoryService")
public class CarparkCategoryService {
	@Resource(name="carparkCategoryDaoImpl")
	private CarparkCategoryDao carparkCategoryDao;

	public List<CarparkCategory> getByHql(String hql){
		return this.carparkCategoryDao.getByHQL(hql);
	}

	public PageBean<CarparkCategory> page(String sqlString, int pageSize,
			int page) {
		// TODO Auto-generated method stub
		return carparkCategoryDao.pageQuery(sqlString, pageSize, page);
	}

	public  CarparkCategory get(Long categoryid) {
		// TODO Auto-generated method stub
		return carparkCategoryDao.get(categoryid);
	}

	public void add(CarparkCategory carparkCategory) {
		// TODO Auto-generated method stub
		carparkCategoryDao.add(carparkCategory);
	}
	public void update(CarparkCategory carparkCategory) {
		// TODO Auto-generated method stub
		carparkCategoryDao.update(carparkCategory);
	}
	public void delete(String ids) {
//		String[] strs = ids.split(",");
//		Long[] idArr = new Long[strs.length];
//		for (int i=0; i< strs.length; i++) {
//			idArr[i] = Long.valueOf(strs[i]);
//		}
		String hqlString = "update CarparkCategory set isDel=1 where categoryid in ("+ids+")";
		carparkCategoryDao.deleteByHql(hqlString);
	
	}
}
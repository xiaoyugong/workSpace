package com.parkbobo.service;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.List;

import com.parkbobo.dao.CarparkMarkerCategoryDao;
import com.parkbobo.model.CarparkMarkerCategory;
import com.parkbobo.utils.PageBean;
@Component("carparkMarkerCategoryService")
public class CarparkMarkerCategoryService {
	@Resource(name="carparkMarkerCategoryDaoImpl")
	private CarparkMarkerCategoryDao carparkMarkerCategoryDao;

	public List<CarparkMarkerCategory> getByHql(String hql){
		return this.carparkMarkerCategoryDao.getByHQL(hql);
	}

	public PageBean<CarparkMarkerCategory> page(String string, int pageSize,
			int page) {
		// TODO Auto-generated method stub
		return carparkMarkerCategoryDao.pageQuery(string, pageSize, page);
	}

	public void delete(String ids) {
		String[] strs = ids.split(",");
		Long[] idArr = new Long[strs.length];
		for (int i=0; i< strs.length; i++) {
			idArr[i] = Long.valueOf(strs[i]);
		}
		
		carparkMarkerCategoryDao.bulkDelete(idArr);
	
	}

	public CarparkMarkerCategory get(Long categoryid) {
		// TODO Auto-generated method stub
		return carparkMarkerCategoryDao.get(categoryid);
	}

	public void add(CarparkMarkerCategory carparkMarkerCategory) {
		// TODO Auto-generated method stub
		carparkMarkerCategoryDao.add(carparkMarkerCategory);
	}

	public void update(CarparkMarkerCategory markerCategory) {
		// TODO Auto-generated method stub
		carparkMarkerCategoryDao.update(markerCategory);
	}
}
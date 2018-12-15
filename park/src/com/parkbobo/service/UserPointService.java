package com.parkbobo.service;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.List;

import com.parkbobo.dao.UserPointDao;
import com.parkbobo.model.UserPoint;
import com.parkbobo.utils.PageBean;
@Component("userPointService")
public class UserPointService {
	@Resource(name="userPointDaoImpl")
	private UserPointDao userPointDao;

	public List<UserPoint> getByHql(String hql){
		return this.userPointDao.getByHQL(hql);
	}

	public PageBean<UserPoint> getPage(String hql, int page, int pageSize) {
		return userPointDao.pageQuery(hql, pageSize, page);
	}

	public void delete(String ids) {
		// TODO Auto-generated method stub
		String[] arr = ids.split(",");
		Long[] newArr = new Long[arr.length];
		for(int i = 0;i<arr.length;i++){
			newArr[i] = Long.valueOf(arr[i]);
		}
		
		userPointDao.bulkDelete(newArr);
	}

	public void bulkAdd(List<UserPoint> listPoints) {
		// TODO Auto-generated method stub
		userPointDao.bulksave(listPoints);
	}
	
}
package com.parkbobo.service;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.List;

import com.parkbobo.dao.UserCreditDao;
import com.parkbobo.model.UserCredit;
import com.parkbobo.utils.PageBean;
@Component("userCreditService")
public class UserCreditService {
	@Resource(name="userCreditDaoImpl")
	private UserCreditDao userCreditDao;

	public List<UserCredit> getByHql(String hql){
		return this.userCreditDao.getByHQL(hql);
	}

	public PageBean<UserCredit> getPage(String hql, int page, int pageSize) {
		return userCreditDao.pageQuery(hql, pageSize, page);
	}

	
	public void bulkAdd(List<UserCredit> userCredits){
			userCreditDao.bulksave(userCredits);
	}
	public void delete(String ids) {
		// TODO Auto-generated method stub
		String[] arr = ids.split(",");
		Long[] newArr = new Long[arr.length];
		for(int i = 0;i<arr.length;i++){
			newArr[i] = Long.valueOf(arr[i]);
		}
		
		userCreditDao.bulkDelete(newArr);
	}
}
package com.parkbobo.service;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.List;

import com.parkbobo.dao.UsersConfigureDao;
import com.parkbobo.model.UsersConfigure;
@Component("usersConfigureService")
public class UsersConfigureService {
	@Resource(name="usersConfigureDaoImpl")
	private UsersConfigureDao usersConfigureDao;

	public List<UsersConfigure> getByHql(String hql){
		return this.usersConfigureDao.getByHQL(hql);
	}
}
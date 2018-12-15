package com.parkbobo.manager.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.parkbobo.manager.dao.ManagerRoleDao;
import com.parkbobo.manager.model.ManagerRole;

@Component("managerRoleService")
public class ManagerRoleService {
	@Resource(name="managerRoleDaoImpl")
	private ManagerRoleDao managerRoleDao;
	public List<ManagerRole> getByHql(String hql) {
		return managerRoleDao.getByHQL(hql);
	}

}

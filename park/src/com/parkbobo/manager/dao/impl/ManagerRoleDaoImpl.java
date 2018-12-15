package com.parkbobo.manager.dao.impl;

import org.springframework.stereotype.Component;

import com.parkbobo.dao.impl.BaseDaoSupport;
import com.parkbobo.manager.dao.ManagerRoleDao;
import com.parkbobo.manager.model.ManagerRole;
@Component("managerRoleDaoImpl")
public class ManagerRoleDaoImpl extends BaseDaoSupport<ManagerRole> implements
		ManagerRoleDao {

}

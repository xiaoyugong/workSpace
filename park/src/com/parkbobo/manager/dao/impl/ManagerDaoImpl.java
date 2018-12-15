package com.parkbobo.manager.dao.impl;

import org.springframework.stereotype.Component;

import com.parkbobo.dao.impl.BaseDaoSupport;
import com.parkbobo.manager.dao.ManagerDao;
import com.parkbobo.manager.model.Manager;
@Component("managerDaoImpl")
public class ManagerDaoImpl extends BaseDaoSupport<Manager> implements
		ManagerDao {

}

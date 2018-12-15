package com.parkbobo.manager.dao.impl;

import org.springframework.stereotype.Component;

import com.parkbobo.dao.impl.BaseDaoSupport;
import com.parkbobo.manager.dao.ManagerGroupDao;
import com.parkbobo.manager.model.ManagerGroup;
@Component("managerGroupDaoImpl")
public class ManagerGroupDaoImpl extends BaseDaoSupport<ManagerGroup> implements
		ManagerGroupDao {

}

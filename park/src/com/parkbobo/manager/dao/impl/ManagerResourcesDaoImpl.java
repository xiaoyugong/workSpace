package com.parkbobo.manager.dao.impl;

import org.springframework.stereotype.Component;

import com.parkbobo.dao.impl.BaseDaoSupport;
import com.parkbobo.manager.dao.ManagerResourcesDao;
import com.parkbobo.manager.model.ManagerResources;
@Component("managerResourcesDaoImpl")
public class ManagerResourcesDaoImpl extends BaseDaoSupport<ManagerResources>
		implements ManagerResourcesDao {

}

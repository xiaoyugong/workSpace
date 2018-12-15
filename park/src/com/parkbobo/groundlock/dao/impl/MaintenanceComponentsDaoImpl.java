package com.parkbobo.groundlock.dao.impl;

import org.springframework.stereotype.Component;

import com.parkbobo.dao.impl.BaseDaoSupport;
import com.parkbobo.groundlock.dao.MaintenanceComponentsDao;
import com.parkbobo.groundlock.model.MaintenanceComponents;
@Component("maintenanceComponentsDaoImpl")
public class MaintenanceComponentsDaoImpl extends
		BaseDaoSupport<MaintenanceComponents> implements
		MaintenanceComponentsDao {

}

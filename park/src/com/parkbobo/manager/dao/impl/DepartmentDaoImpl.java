package com.parkbobo.manager.dao.impl;

import org.springframework.stereotype.Component;

import com.parkbobo.dao.impl.BaseDaoSupport;
import com.parkbobo.manager.dao.DepartmentDao;
import com.parkbobo.manager.model.Department;
@Component("departmentDaoImpl")
public class DepartmentDaoImpl extends BaseDaoSupport<Department> implements
		DepartmentDao {

}

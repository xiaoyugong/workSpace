package com.parkbobo.manager.dao.impl;

import org.springframework.stereotype.Component;

import com.parkbobo.dao.impl.BaseDaoSupport;
import com.parkbobo.manager.dao.RoleDao;
import com.parkbobo.manager.model.Role;
@Component("roleDaoImpl")
public class RoleDaoImpl extends BaseDaoSupport<Role> implements RoleDao {

}

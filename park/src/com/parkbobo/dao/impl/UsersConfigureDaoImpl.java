package com.parkbobo.dao.impl;
import org.springframework.stereotype.Component;

import com.parkbobo.dao.UsersConfigureDao;
import com.parkbobo.model.UsersConfigure;
@Component("usersConfigureDaoImpl")
public class UsersConfigureDaoImpl extends BaseDaoSupport<UsersConfigure> implements UsersConfigureDao {
}
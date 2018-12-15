package com.parkbobo.dao.impl;
import org.springframework.stereotype.Component;

import com.parkbobo.dao.DriverAuthenticationDao;
import com.parkbobo.model.DriverAuthentication;
@Component("driverAuthenticationDaoImpl")
public class DriverAuthenticationDaoImpl extends BaseDaoSupport<DriverAuthentication> implements DriverAuthenticationDao {
}
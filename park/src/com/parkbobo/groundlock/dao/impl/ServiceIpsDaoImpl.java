package com.parkbobo.groundlock.dao.impl;

import org.springframework.stereotype.Component;

import com.parkbobo.dao.impl.BaseDaoSupport;
import com.parkbobo.groundlock.dao.ServiceIpsDao;
import com.parkbobo.groundlock.model.ServiceIps;
@Component("serviceIpsDaoImpl")
public class ServiceIpsDaoImpl extends BaseDaoSupport<ServiceIps> implements
		ServiceIpsDao {

}

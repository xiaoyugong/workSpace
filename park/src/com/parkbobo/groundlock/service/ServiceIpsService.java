package com.parkbobo.groundlock.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.parkbobo.groundlock.dao.ServiceIpsDao;
import com.parkbobo.groundlock.model.ServiceIps;

@Component("serviceIpsService")
public class ServiceIpsService {
	@Resource(name="serviceIpsDaoImpl")
	private ServiceIpsDao serviceIpsDao;
	public List<ServiceIps> getByGroundlockid(String mac) {
		return this.serviceIpsDao.getByHQL("from ServiceIps as a where a.groundlock.groundlockid = '" + mac + "' order by a.id");
	}
}

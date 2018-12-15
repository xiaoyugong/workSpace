package com.parkbobo.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.parkbobo.dao.CarparkDeviceRequestLogDao;
import com.parkbobo.model.CarparkDeviceRequestLog;

@Service("carparkDeviceRequestLogService")
public class CarparkDeviceRequestLogService {

	@Resource(name="carparkDeviceRequestLogDaoImpl")
	private CarparkDeviceRequestLogDao dao;
	
	public void add(CarparkDeviceRequestLog carparkDeviceRequestLog){
		dao.add(carparkDeviceRequestLog);
	}
}

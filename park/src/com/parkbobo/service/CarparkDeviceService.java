package com.parkbobo.service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.parkbobo.dao.CarparkDeviceDao;
import com.parkbobo.dao.CarparkOrderDao;
import com.parkbobo.model.CarparkDevice;


@Service("carparkDeviceService")
public class CarparkDeviceService {

	@Resource(name="carparkDeviceDaoImpl")
	private CarparkDeviceDao carparkDeviceDao;
	public List getByHql(String hql){
		return carparkDeviceDao.getByHQL(hql);
	}
	
	public CarparkDevice getCarparkDevice(Long id) {
		return carparkDeviceDao.get(id);
	}
	public CarparkDevice getCarparkDeviceByNum(String num) {
		return carparkDeviceDao.getUniqueByProperty("deviceNumber", num);
	}
}

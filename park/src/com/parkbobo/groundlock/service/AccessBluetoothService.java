package com.parkbobo.groundlock.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.parkbobo.groundlock.dao.AccessBluetoothDao;
import com.parkbobo.groundlock.model.AccessBluetooth;
import com.parkbobo.utils.PageBean;

@Component("accessBluetoothService")
public class AccessBluetoothService {
	@Resource(name="accessBluetoothDaoImpl")
	private AccessBluetoothDao accessBluetoothDao;
	
	public PageBean<AccessBluetooth> getPage(String hql, int pageSize, int page)
	{
		return this.accessBluetoothDao.pageQuery(hql, pageSize, page);
	}
	public void save(AccessBluetooth accessBluetooth)
	{
		this.accessBluetoothDao.merge(accessBluetooth);
	}
	public List<AccessBluetooth> getByGroundlockid(String mac) {
		return this.accessBluetoothDao.getByHQL("from AccessBluetooth as a where a.groundlock.groundlockid = '" + mac + "' order by a.bluetoothid");
	}
	public AccessBluetooth getById(Long bluetoothid) {
		return this.accessBluetoothDao.get(bluetoothid);
	}
	public List<AccessBluetooth> getByHql(String hql) {
		return this.accessBluetoothDao.getByHQL(hql);
	}
}

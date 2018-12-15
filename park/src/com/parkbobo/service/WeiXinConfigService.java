package com.parkbobo.service;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.sql.rowset.serial.SerialArray;

import org.springframework.stereotype.Service;

import com.parkbobo.dao.WeiXinConfigDao;
import com.weixin.model.Config;

@Service("weiXinConfigService")
public class WeiXinConfigService {

	@Resource(name="weiXinConfigDaoImpl")
	private WeiXinConfigDao weiXinConfigDao;

	public Config get(Serializable entityid) {
		// TODO Auto-generated method stub
		return weiXinConfigDao.getById(entityid);
	}

	public void update(Config config) {
		// TODO Auto-generated method stub
		weiXinConfigDao.update(config);
	}
	
	
	
}

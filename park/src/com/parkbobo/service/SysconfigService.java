package com.parkbobo.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.parkbobo.dao.SysconfigDao;
import com.parkbobo.model.Sysconfig;

@Component("sysconfigService")
public class SysconfigService {
	@Resource(name="sysconfigDaoImpl")
	private SysconfigDao sysconfigDao;
	
	public Sysconfig getByVarname(String varname)
	{
		return this.sysconfigDao.getUniqueByProperty("varname", varname);
	}

	public void add(Sysconfig sysconfig) {
		sysconfigDao.merge(sysconfig);
	}
}

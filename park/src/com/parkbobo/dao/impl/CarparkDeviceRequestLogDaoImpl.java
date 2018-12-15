package com.parkbobo.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.parkbobo.dao.CarparkDeviceRequestLogDao;
import com.parkbobo.model.CarparkDeviceRequestLog;
import com.parkbobo.utils.PageBean;

@Repository("carparkDeviceRequestLogDaoImpl")
public class CarparkDeviceRequestLogDaoImpl extends
		BaseDaoSupport<CarparkDeviceRequestLog> implements
		CarparkDeviceRequestLogDao {}

package com.parkbobo.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.dao.support.DaoSupport;
import org.springframework.stereotype.Repository;

import com.parkbobo.dao.CarparkOrderDao;
import com.parkbobo.model.CarparkOrder;
import com.parkbobo.utils.PageBean;

@Repository("carparkOrderDaoImpl")
public class CarparkOrderDaoImpl extends BaseDaoSupport<CarparkOrder> implements CarparkOrderDao {
	
	
}

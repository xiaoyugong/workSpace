package com.parkbobo.dao.impl;

import org.springframework.stereotype.Component;

import com.parkbobo.dao.OrderTaskDao;
import com.parkbobo.model.OrderTask;
@Component("orderTaskDaoImpl")
public class OrderTaskDaoImpl extends BaseDaoSupport<OrderTask> implements
		OrderTaskDao {

}

package com.parkbobo.dao.impl;
import org.springframework.stereotype.Component;

import com.parkbobo.dao.BerthOrderDao;
import com.parkbobo.model.BerthOrder;
@Component("berthOrderDaoImpl")
public class BerthOrderDaoImpl extends BaseDaoSupport<BerthOrder> implements BerthOrderDao {
}
package com.parkbobo.dao.impl;
import org.springframework.stereotype.Component;

import com.parkbobo.dao.WithdrawLogDao;
import com.parkbobo.model.WithdrawLog;
@Component("withdrawLogDaoImpl")
public class WithdrawLogDaoImpl extends BaseDaoSupport<WithdrawLog> implements WithdrawLogDao {
}
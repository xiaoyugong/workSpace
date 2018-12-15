package com.parkbobo.dao.impl;
import org.springframework.stereotype.Component;

import com.parkbobo.dao.UserBalanceDao;
import com.parkbobo.model.UserBalance;
@Component("userBalanceDaoImpl")
public class UserBalanceDaoImpl extends BaseDaoSupport<UserBalance> implements UserBalanceDao {
}
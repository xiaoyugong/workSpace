package com.parkbobo.dao.impl;
import org.springframework.stereotype.Component;

import com.parkbobo.dao.UserCreditDao;
import com.parkbobo.model.UserCredit;
@Component("userCreditDaoImpl")
public class UserCreditDaoImpl extends BaseDaoSupport<UserCredit> implements UserCreditDao {
}
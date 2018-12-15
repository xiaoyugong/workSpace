package com.parkbobo.dao.impl;
import org.springframework.stereotype.Component;

import com.parkbobo.dao.UserPointDao;
import com.parkbobo.model.UserPoint;
@Component("userPointDaoImpl")
public class UserPointDaoImpl extends BaseDaoSupport<UserPoint> implements UserPointDao {
}
package com.parkbobo.dao.impl;

import org.springframework.stereotype.Component;

import com.parkbobo.dao.AppActivityDao;
import com.parkbobo.model.AppActivity;
@Component("appActivityDaoImpl")
public class AppActivityDaoImpl extends BaseDaoSupport<AppActivity> implements
		AppActivityDao {

}

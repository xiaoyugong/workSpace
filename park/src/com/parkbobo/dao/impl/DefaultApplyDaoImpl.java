package com.parkbobo.dao.impl;

import org.springframework.stereotype.Component;

import com.parkbobo.dao.DefaultApplyDao;
import com.parkbobo.model.DefaultApply;
@Component("defaultApplyDaoImpl")
public class DefaultApplyDaoImpl extends BaseDaoSupport<DefaultApply> implements
		DefaultApplyDao {

}

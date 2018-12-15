package com.parkbobo.groundlock.dao.impl;

import org.springframework.stereotype.Component;

import com.parkbobo.dao.impl.BaseDaoSupport;
import com.parkbobo.groundlock.dao.WeakupPhonesDao;
import com.parkbobo.groundlock.model.WeakupPhones;
@Component("weakupPhonesDaoImpl")
public class WeakupPhonesDaoImpl extends BaseDaoSupport<WeakupPhones> implements
		WeakupPhonesDao {

}

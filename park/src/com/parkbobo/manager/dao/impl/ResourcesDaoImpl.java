package com.parkbobo.manager.dao.impl;

import org.springframework.stereotype.Component;

import com.parkbobo.dao.impl.BaseDaoSupport;
import com.parkbobo.manager.dao.ResourcesDao;
import com.parkbobo.manager.model.Resources;
@Component("resourcesDaoImpl")
public class ResourcesDaoImpl extends BaseDaoSupport<Resources> implements
		ResourcesDao {

}

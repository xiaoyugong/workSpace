package com.parkbobo.dao.impl;
import org.springframework.stereotype.Component;

import com.parkbobo.dao.CarparkCategoryDao;
import com.parkbobo.model.CarparkCategory;
@Component("carparkCategoryDaoImpl")
public class CarparkCategoryDaoImpl extends BaseDaoSupport<CarparkCategory> implements CarparkCategoryDao {
}
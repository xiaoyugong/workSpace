package com.parkbobo.dao.impl;
import org.springframework.stereotype.Component;

import com.parkbobo.dao.AppVersionDao;
import com.parkbobo.model.AppVersion;
@Component("appVersionDaoImpl")
public class AppVersionDaoImpl extends BaseDaoSupport<AppVersion> implements AppVersionDao {
}
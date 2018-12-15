package com.parkbobo.dao.impl;

import org.springframework.stereotype.Component;

import com.parkbobo.dao.AppActivityDao;
import com.parkbobo.dao.WeiXinConfigDao;
import com.parkbobo.model.AppActivity;
import com.weixin.model.Config;
@Component("weiXinConfigDaoImpl")
public class WeiXinConfigDaoImpl extends BaseDaoSupport<Config> implements
WeiXinConfigDao {

}

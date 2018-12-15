package com.parkbobo.dao.impl;

import org.springframework.stereotype.Component;

import com.parkbobo.dao.AppActivityDao;
import com.parkbobo.dao.WeiXinNewsDao;
import com.parkbobo.model.AppActivity;
import com.weixin.model.News;
@Component("weiXinNewsDaoImpl")
public class WeiXinNewsDaoImpl extends BaseDaoSupport<News> implements
WeiXinNewsDao {

	
}

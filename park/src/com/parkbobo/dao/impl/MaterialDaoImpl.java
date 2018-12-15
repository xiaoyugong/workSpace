package com.parkbobo.dao.impl;

import org.springframework.stereotype.Component;

import com.parkbobo.dao.AppActivityDao;
import com.parkbobo.dao.WeiXinMaterialDao;
import com.parkbobo.model.AppActivity;
import com.weixin.model.Material;
@Component("materialDaoImpl")
public class MaterialDaoImpl extends BaseDaoSupport<Material> implements
WeiXinMaterialDao {

}

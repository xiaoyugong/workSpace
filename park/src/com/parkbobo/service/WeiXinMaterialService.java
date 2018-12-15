package com.parkbobo.service;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.parkbobo.dao.WeiXinMaterialDao;
import com.parkbobo.utils.PageBean;
import com.weixin.model.Material;

@Service("weiXinMaterialService")
public class WeiXinMaterialService {

	@Resource(name="materialDaoImpl")
	private WeiXinMaterialDao materialDao;

	public PageBean<Material> page(String hql, int page, int pageSize) {
		// TODO Auto-generated method stub
		return materialDao.pageQuery(hql, pageSize, page);
	}

	public void add(Material material) {
		// TODO Auto-generated method stub
		materialDao.add(material);
	}

	public void delete(Serializable mediaId) {
		// TODO Auto-generated method stub
		materialDao.delete(mediaId);
	}
	
	
}

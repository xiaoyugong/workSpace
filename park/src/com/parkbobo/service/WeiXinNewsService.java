package com.parkbobo.service;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.parkbobo.dao.WeiXinNewsDao;
import com.parkbobo.utils.PageBean;
import com.weixin.model.News;


@Service("weiXinNewsService")
public class WeiXinNewsService {

	@Resource(name="weiXinNewsDaoImpl")
	private WeiXinNewsDao weiXinNewsDao;

	public PageBean<News> page(String hql, int page, int pageSize) {
		// TODO Auto-generated method stub
		return weiXinNewsDao.pageQuery(hql, pageSize, page);
	}

	public void add(News news) {
		// TODO Auto-generated method stub
		weiXinNewsDao.add(news);
	}

	public News get(Serializable mediaId) {
		// TODO Auto-generated method stub
		return weiXinNewsDao.getById(mediaId);
	}

	public void update(News news) {
		// TODO Auto-generated method stub
		weiXinNewsDao.merge(news);
	}

	public void delete(String mediaId) {
		// TODO Auto-generated method stub
		weiXinNewsDao.delete(mediaId);
	}
	
}

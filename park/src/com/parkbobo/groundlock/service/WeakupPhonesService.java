package com.parkbobo.groundlock.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.parkbobo.groundlock.dao.WeakupPhonesDao;
import com.parkbobo.groundlock.model.WeakupPhones;
import com.parkbobo.utils.PageBean;

@Component("weakupPhonesService")
public class WeakupPhonesService {
	@Resource(name="weakupPhonesDaoImpl")
	private WeakupPhonesDao weakupPhonesDao;

	public List<WeakupPhones> getByGroundlockid(String mac) {
		return this.weakupPhonesDao.getByHQL("from WeakupPhones as a where a.groundlock.groundlockid = '" + mac + "' order by a.phoneid");
	}

	public PageBean<WeakupPhones> loadPage(String hql, int pageSize, int page) {
		return this.weakupPhonesDao.pageQuery(hql, pageSize, page);
	}
}

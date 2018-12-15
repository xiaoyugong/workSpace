package com.parkbobo.groundlock.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.parkbobo.groundlock.dao.RunningRecordsDao;
import com.parkbobo.groundlock.model.RunningRecords;
import com.parkbobo.utils.PageBean;

@Component("runningRecordsService")
public class RunningRecordsService {
	@Resource(name="runningRecordsDaoImpl")
	private RunningRecordsDao runningRecordsDao;

	public PageBean<RunningRecords> loadPage(String hql, int pageSize, int page) {
		return this.runningRecordsDao.pageQuery(hql, pageSize, page);
	}

	public void bulkDelete(String ids) {
		if(ids.length() > 0)
		{
			String[] strs = ids.split(",");
			Long[] idArr = new Long[strs.length];
			for (int i=0; i< strs.length; i++) {
				idArr[i] = Long.valueOf(strs[i]);
			}
			this.runningRecordsDao.bulkDelete(idArr);
		}
	}
}

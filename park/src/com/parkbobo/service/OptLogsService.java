package com.parkbobo.service;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.List;

import com.parkbobo.dao.OptLogsDao;
import com.parkbobo.model.OptLogs;
import com.parkbobo.utils.PageBean;
@Component("optLogsService")
public class OptLogsService {
	@Resource(name="optLogsDaoImpl")
	private OptLogsDao optLogsDao;

	public List<OptLogs> getByHql(String hql){
		return this.optLogsDao.getByHQL(hql);
	}

	public void add(OptLogs optLogs) {
		// TODO Auto-generated method stub
		optLogsDao.save(optLogs);
	}

	public PageBean<OptLogs> page(String hqlString, int page, int pageSize) {
		// TODO Auto-generated method stub
		return optLogsDao.pageQuery(hqlString, pageSize, page);
	}

	public void delete(String ids) {
		// TODO Auto-generated method stub
		String[] strs = ids.split(",");
		Long[] idArr = new Long[strs.length];
		for (int i=0; i< strs.length; i++) {
			idArr[i] = Long.valueOf(strs[i]);
		}
		
		optLogsDao.bulkDelete(idArr);
	}
}
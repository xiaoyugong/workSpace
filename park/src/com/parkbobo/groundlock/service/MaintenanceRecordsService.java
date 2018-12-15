package com.parkbobo.groundlock.service;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.parkbobo.groundlock.dao.MaintenanceRecordsDao;
import com.parkbobo.groundlock.model.MaintenanceRecords;
import com.parkbobo.utils.PageBean;

@Component("maintenanceRecordsService")
public class MaintenanceRecordsService {
	@Resource(name="maintenanceRecordsDaoImpl")
	private MaintenanceRecordsDao maintenanceRecordsDao;

	public PageBean<MaintenanceRecords> loadPage(String hql, int pageSize,
			int page) {
		return this.maintenanceRecordsDao.pageQuery(hql, pageSize, page);
	}

	public void accept(String recordid,String repairman) {
		MaintenanceRecords maintenanceRecords = this.maintenanceRecordsDao.get(Long.parseLong(recordid));
		//System.out.println(repairman);
		if(repairman!=null&&repairman!=""){
			maintenanceRecords.setRepairman(repairman);
			maintenanceRecords.setIsOperate(1);
			this.maintenanceRecordsDao.merge(maintenanceRecords);
		}else{
//			maintenanceRecords.setRecordstatus(2);
		}
	}
	/**
	 * 维修申报删除
	 * @param ids
	 */
	public void bulkDelete(String ids) {
		if(ids.length() > 0)
		{
			String[] strs = ids.split(",");
			Long[] idArr = new Long[strs.length];
			for (int i=0; i< strs.length; i++) {
				idArr[i] = Long.valueOf(strs[i]);
			}
			this.maintenanceRecordsDao.bulkDelete(idArr);
		}
	}
}

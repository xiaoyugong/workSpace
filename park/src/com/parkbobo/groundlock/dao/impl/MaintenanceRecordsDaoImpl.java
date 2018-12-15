package com.parkbobo.groundlock.dao.impl;

import org.springframework.stereotype.Component;

import com.parkbobo.dao.impl.BaseDaoSupport;
import com.parkbobo.groundlock.dao.MaintenanceRecordsDao;
import com.parkbobo.groundlock.model.MaintenanceRecords;
@Component("maintenanceRecordsDaoImpl")
public class MaintenanceRecordsDaoImpl extends BaseDaoSupport<MaintenanceRecords> implements
MaintenanceRecordsDao {
	/*@Override
	public void save(final String hql,final MaintenanceRecords maintenanceRecords) {
		System.out.println("daoImp add");
		this.getHibernateTemplate().execute(new HibernateCallback<MaintenanceRecords>() {
			@Override
			public MaintenanceRecords doInHibernate(org.hibernate.Session session)throws HibernateException, SQLException {
				session.update(hql, maintenanceRecords);
				return null;
			}
		});
	}*/

}

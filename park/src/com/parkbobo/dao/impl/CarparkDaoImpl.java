package com.parkbobo.dao.impl;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;

import com.parkbobo.dao.CarparkDao;
import com.parkbobo.model.Carpark;
@Component("carparkDaoImpl")
public class CarparkDaoImpl extends BaseDaoSupport<Carpark> implements CarparkDao {

	@SuppressWarnings("unchecked")
	public void executeSql(final String sql) {
		 getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				session.createSQLQuery(sql).setCacheable(true).executeUpdate();
				return null;
			}
		});
	}
}
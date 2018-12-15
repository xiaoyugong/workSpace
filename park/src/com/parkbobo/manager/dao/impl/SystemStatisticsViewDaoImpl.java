package com.parkbobo.manager.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;

import com.parkbobo.dao.impl.BaseDaoSupport;
import com.parkbobo.manager.dao.SystemStatisticsViewDao;
import com.parkbobo.manager.model.SystemStatisticsView;
@Component("systemStatisticsViewDaoImpl")
public class SystemStatisticsViewDaoImpl extends BaseDaoSupport<SystemStatisticsView> implements SystemStatisticsViewDao {
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getBySql(final String sql) {
		List<Object[]> list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public List<Object[]> doInHibernate(Session session) throws HibernateException,
					SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
		return list;
	}
}

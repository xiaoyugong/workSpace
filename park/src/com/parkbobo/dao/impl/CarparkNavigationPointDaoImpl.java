package com.parkbobo.dao.impl;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;

import com.parkbobo.dao.CarparkNavigationPointDao;
import com.parkbobo.model.CarparkNavigationPoint;
@Component("carparkNavigationPointDaoImpl")
public class CarparkNavigationPointDaoImpl extends BaseDaoSupport<CarparkNavigationPoint> implements CarparkNavigationPointDao {
	public void insertOrUpdateBysql(final List<CarparkNavigationPoint> carparkNavigationPoints,final List<String> sqls) {
		this.getHibernateTemplate().execute(new HibernateCallback<Object>(){
			public Object doInHibernate(Session session) throws HibernateException,SQLException{
				int j = 0;
				for (int i = 0; i < carparkNavigationPoints.size(); i++) {
					CarparkNavigationPoint carparkNavigationPoint = carparkNavigationPoints.get(i);
					String sql = sqls.get(i);
					Query sqlQuery = session.createSQLQuery(sql);
					if(carparkNavigationPoint.getGid()!=null){
						sqlQuery.setParameter("gid", carparkNavigationPoint.getGid());
					}
					sqlQuery.setLong("carparkid", carparkNavigationPoint.getCarparkid());
					sqlQuery.setParameter("memo", carparkNavigationPoint.getMemo(),Hibernate.STRING);
					sqlQuery.setParameter("startFloorid", carparkNavigationPoint.getStartFloorid(),Hibernate.LONG);
					sqlQuery.setParameter("endFloorid", carparkNavigationPoint.getEndFloorid(),Hibernate.LONG);
					sqlQuery.setParameter("roadType", carparkNavigationPoint.getRoadType(),Hibernate.INTEGER);
					sqlQuery.setParameter("distance", carparkNavigationPoint.getDistance(),Hibernate.INTEGER);
					sqlQuery.setParameter("description", carparkNavigationPoint.getDescription(),Hibernate.STRING);
					j+=sqlQuery.executeUpdate();
				}
				return j;
			}
		});
		}
}
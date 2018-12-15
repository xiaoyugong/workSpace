package com.parkbobo.dao.impl;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;

import com.parkbobo.dao.CarparkEntrancePointDao;
import com.parkbobo.model.CarparkBerthPolygon;
import com.parkbobo.model.CarparkEntrancePoint;
@Component("carparkEntrancePointDaoImpl")
public class CarparkEntrancePointDaoImpl extends BaseDaoSupport<CarparkEntrancePoint> implements CarparkEntrancePointDao {

	public void insertOrUpdateBysql(final List<CarparkEntrancePoint> carparkEntrancePoints,final List<String> sqls) {
		this.getHibernateTemplate().execute(new HibernateCallback<Object>(){
			public Object doInHibernate(Session session) throws HibernateException,SQLException{
				int j = 0;
				for (int i = 0; i < carparkEntrancePoints.size(); i++) {
					CarparkEntrancePoint carparkEntrancePoint = carparkEntrancePoints.get(i);
					String sql = sqls.get(i);
				
					Query sqlQuery = session.createSQLQuery(sql);
					if(carparkEntrancePoint.getGid()!=null){
						sqlQuery.setParameter("gid", carparkEntrancePoint.getGid());
					}
					sqlQuery.setParameter("carparkid",carparkEntrancePoint.getCarpark().getCarparkid(),Hibernate.LONG);
					sqlQuery.setParameter("floorid",carparkEntrancePoint.getFloorid(),Hibernate.LONG);
					sqlQuery.setParameter("name",carparkEntrancePoint.getName(),Hibernate.STRING);
					sqlQuery.setParameter("type",carparkEntrancePoint.getType(),Hibernate.SHORT);
					sqlQuery.setParameter("gaodeLongitude",carparkEntrancePoint.getGaodeLongitude(),Hibernate.STRING);
					sqlQuery.setParameter("gaodeLatitude",carparkEntrancePoint.getGaodeLatitude(),Hibernate.STRING);
					sqlQuery.setParameter("memo",carparkEntrancePoint.getMemo(),Hibernate.STRING);
					j+=sqlQuery.executeUpdate();
				}
				System.out.println(j);
				return j;
			}
		});
		}
}
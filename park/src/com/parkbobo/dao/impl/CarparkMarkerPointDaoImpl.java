package com.parkbobo.dao.impl;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;

import com.parkbobo.dao.CarparkMarkerPointDao;
import com.parkbobo.model.CarparkEntrancePoint;
import com.parkbobo.model.CarparkMarkerPoint;
@Component("carparkMarkerPointDaoImpl")
public class CarparkMarkerPointDaoImpl extends BaseDaoSupport<CarparkMarkerPoint> implements CarparkMarkerPointDao {
	public void insertOrUpdateBysql(final List<CarparkMarkerPoint> carparkMarkerPoints,final List<String> sqls) {
		this.getHibernateTemplate().execute(new HibernateCallback<Object>(){
			public Object doInHibernate(Session session) throws HibernateException,SQLException{
				int j = 0;
				for (int i = 0; i < carparkMarkerPoints.size(); i++) {
					CarparkMarkerPoint carparkMarkerPoint = carparkMarkerPoints.get(i);
					String sql = sqls.get(i);
				
					Query sqlQuery = session.createSQLQuery(sql);
					if(carparkMarkerPoint.getGid()!=null){
						sqlQuery.setParameter("gid", carparkMarkerPoint.getGid());
					}
					sqlQuery.setParameter("carparkid",carparkMarkerPoint.getCarpark().getCarparkid(),Hibernate.LONG);
					if(carparkMarkerPoint.getCarparkMarkerCategory()!=null)
						sqlQuery.setParameter("categoryid",carparkMarkerPoint.getCarparkMarkerCategory().getCategoryid(),Hibernate.LONG);
					else
						sqlQuery.setParameter("categoryid",null,Hibernate.LONG);
					sqlQuery.setParameter("floorid",carparkMarkerPoint.getFloorid(),Hibernate.LONG);
					sqlQuery.setParameter("name",carparkMarkerPoint.getName(),Hibernate.STRING);
					sqlQuery.setParameter("memo",carparkMarkerPoint.getMemo(),Hibernate.STRING);
					sqlQuery.setParameter("floorid", carparkMarkerPoint.getFloorid(),Hibernate.LONG);
					sqlQuery.setParameter("fontColor", carparkMarkerPoint.getFontColor(),Hibernate.INTEGER);
					sqlQuery.setParameter("fontItalic", carparkMarkerPoint.getFontItalic(),Hibernate.INTEGER);
					sqlQuery.setParameter("fontSize", carparkMarkerPoint.getFontSize(),Hibernate.FLOAT);
					sqlQuery.setParameter("fontWeight", carparkMarkerPoint.getFontWeight(),Hibernate.INTEGER);
					j += sqlQuery.executeUpdate();
				}
				return j;
			}
		});
		}

}
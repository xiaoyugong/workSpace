package com.parkbobo.dao.impl;
import java.sql.SQLException;
import java.util.List;


import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;

import com.parkbobo.dao.CarparkBerthPolygonDao;
import com.parkbobo.model.CarparkBerthPolygon;
@Component("carparkBerthPolygonDaoImpl")
public class CarparkBerthPolygonDaoImpl extends BaseDaoSupport<CarparkBerthPolygon> implements CarparkBerthPolygonDao {

	public void insertOrUpdateBysql(final List<CarparkBerthPolygon>  carparkBerthPolygons,final List<String> sql) {
		this.getHibernateTemplate().execute(new HibernateCallback<Object>(){
			public Object doInHibernate(Session session) throws HibernateException,SQLException{
				int j = 0;
				for (int i = 0; i < carparkBerthPolygons.size(); i++) {
					CarparkBerthPolygon carparkBerthPolygon = carparkBerthPolygons.get(i);
					Query sqlQuery = session.createSQLQuery(sql.get(i));
					if(carparkBerthPolygon.getGid()!=null){
						sqlQuery.setParameter("gid", carparkBerthPolygon.getGid());
					}
					sqlQuery.setLong("carparkid", carparkBerthPolygon.getCarpark().getCarparkid());
					sqlQuery.setParameter("bordercolor", carparkBerthPolygon.getBordercolor(),Hibernate.INTEGER);
					sqlQuery.setParameter("clickBordercolor", carparkBerthPolygon.getClickBordercolor(),Hibernate.INTEGER);
					sqlQuery.setParameter("clickColor", carparkBerthPolygon.getClickColor(),Hibernate.INTEGER);
					sqlQuery.setParameter("color", carparkBerthPolygon.getColor(),Hibernate.INTEGER);
					sqlQuery.setParameter("floorid", carparkBerthPolygon.getFloorid(),Hibernate.LONG);
					sqlQuery.setParameter("fontColor", carparkBerthPolygon.getFontColor(),Hibernate.INTEGER);
					sqlQuery.setParameter("fontItalic", carparkBerthPolygon.getFontItalic(),Hibernate.INTEGER);
					sqlQuery.setParameter("fontSize", carparkBerthPolygon.getFontSize(),Hibernate.FLOAT);
					sqlQuery.setParameter("fontWeight", carparkBerthPolygon.getFontWeight(),Hibernate.INTEGER);
	//				sqlQuery.setString("geom", "st_astext('"+carparkBerthPolygon.getGeometry()+"')");
					sqlQuery.setParameter("height", carparkBerthPolygon.getHeight(),Hibernate.FLOAT);
					sqlQuery.setParameter("width", carparkBerthPolygon.getWidth(),Hibernate.FLOAT);
					sqlQuery.setParameter("name", carparkBerthPolygon.getName(),Hibernate.STRING);
					sqlQuery.setParameter("memo", carparkBerthPolygon.getMemo(),Hibernate.STRING);
					j+=sqlQuery.executeUpdate();
				}
				return	j;
			}
		});
	}

}
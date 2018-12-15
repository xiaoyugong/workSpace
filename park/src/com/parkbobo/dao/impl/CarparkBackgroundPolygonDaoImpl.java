package com.parkbobo.dao.impl;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;

import com.parkbobo.dao.CarparkBackgroundPolygonDao;
import com.parkbobo.model.CarparkBackgroundPolygon;
@Component("carparkBackgroundPolygonDaoImpl")
public class CarparkBackgroundPolygonDaoImpl extends BaseDaoSupport<CarparkBackgroundPolygon> implements CarparkBackgroundPolygonDao {

	@Override
	public void insertOrUpdateBysql(
			final List<CarparkBackgroundPolygon> carparkBackgroundPolygons,final List<String> sqls) {	
		this.getHibernateTemplate().execute(new HibernateCallback<Object>(){
			public Object doInHibernate(Session session) throws HibernateException,SQLException{
				int i = 0;
				for(int j = 0;j<carparkBackgroundPolygons.size();j++){
					CarparkBackgroundPolygon carparkBackgroundPolygon = carparkBackgroundPolygons.get(j);
					Query sqlQuery = session.createSQLQuery(sqls.get(j));
					if(carparkBackgroundPolygon.getGid()!=null){
						sqlQuery.setParameter("gid", carparkBackgroundPolygon.getGid());
					}
					sqlQuery.setLong("carparkid", carparkBackgroundPolygon.getCarpark().getCarparkid());
					sqlQuery.setParameter("bordercolor", carparkBackgroundPolygon.getBordercolor(),Hibernate.INTEGER);
					sqlQuery.setParameter("color", carparkBackgroundPolygon.getColor(),Hibernate.INTEGER);
					sqlQuery.setParameter("floorid", carparkBackgroundPolygon.getFloorid(),Hibernate.LONG);
					sqlQuery.setParameter("fontColor", carparkBackgroundPolygon.getFontColor(),Hibernate.INTEGER);
					sqlQuery.setParameter("fontItalic", carparkBackgroundPolygon.getFontItalic(),Hibernate.INTEGER);
					sqlQuery.setParameter("fontSize", carparkBackgroundPolygon.getFontSize(),Hibernate.FLOAT);
					sqlQuery.setParameter("fontWeight", carparkBackgroundPolygon.getFontWeight(),Hibernate.INTEGER);
	//				sqlQuery.setString("geom", "st_astext('"+carparkBackgroundPolygon.getGeometry()+"')");
					sqlQuery.setParameter("name", carparkBackgroundPolygon.getName(),Hibernate.STRING);
					sqlQuery.setParameter("memo", carparkBackgroundPolygon.getMemo(),Hibernate.STRING);
					i += sqlQuery.executeUpdate();
				}
				return	i;
			}
		});}
}
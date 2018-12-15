package com.parkbobo.dao.impl;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;

import com.parkbobo.dao.CarparkModelPolygonDao;
import com.parkbobo.model.CarparkBerthPolygon;
import com.parkbobo.model.CarparkModelPolygon;
@Component("carparkModelPolygonDaoImpl")
public class CarparkModelPolygonDaoImpl extends BaseDaoSupport<CarparkModelPolygon> implements CarparkModelPolygonDao {
	public void insertOrUpdateBysql(final List<CarparkModelPolygon> carparkModelPolygons,final List<String> sqls) {
		this.getHibernateTemplate().execute(new HibernateCallback<Object>(){
			public Object doInHibernate(Session session) throws HibernateException,SQLException{
				int j =0;
				for (int i = 0; i < carparkModelPolygons.size(); i++) {
					CarparkModelPolygon carparkModelPolygon = carparkModelPolygons.get(i);
					String sql = sqls.get(i);
				
				Query sqlQuery = session.createSQLQuery(sql);
				if(carparkModelPolygon.getGid()!=null){
					sqlQuery.setParameter("gid", carparkModelPolygon.getGid());
				}
				sqlQuery.setLong("carparkid", carparkModelPolygon.getCarpark().getCarparkid());
				sqlQuery.setParameter("bordercolor", carparkModelPolygon.getBordercolor(),Hibernate.INTEGER);
				sqlQuery.setParameter("clickBordercolor", carparkModelPolygon.getClickBordercolor(),Hibernate.INTEGER);
				sqlQuery.setParameter("clickColor", carparkModelPolygon.getClickColor(),Hibernate.INTEGER);
				sqlQuery.setParameter("color", carparkModelPolygon.getColor(),Hibernate.INTEGER);
				sqlQuery.setParameter("floorid", carparkModelPolygon.getFloorid(),Hibernate.LONG);
				sqlQuery.setParameter("fontColor", carparkModelPolygon.getFontColor(),Hibernate.INTEGER);
				sqlQuery.setParameter("fontItalic", carparkModelPolygon.getFontItalic(),Hibernate.INTEGER);
				sqlQuery.setParameter("fontSize", carparkModelPolygon.getFontSize(),Hibernate.FLOAT);
				sqlQuery.setParameter("fontWeight", carparkModelPolygon.getFontWeight(),Hibernate.INTEGER);
//				sqlQuery.setString("geom", "st_astext('"+carparkModelPolygon.getGeometry()+"')");
				sqlQuery.setParameter("height", carparkModelPolygon.getHeight(),Hibernate.FLOAT);
				sqlQuery.setParameter("width", carparkModelPolygon.getWidth(),Hibernate.FLOAT);
				sqlQuery.setParameter("name", carparkModelPolygon.getName(),Hibernate.STRING);
				sqlQuery.setParameter("memo", carparkModelPolygon.getMemo(),Hibernate.STRING);
				j+=sqlQuery.executeUpdate();
				}
				return	j;
			}
		});
	}

}
package com.parkbobo.dao.impl;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;

import com.parkbobo.dao.CarparkShopPolygonDao;
import com.parkbobo.model.CarparkEntrancePoint;
import com.parkbobo.model.CarparkShopPolygon;
@Component("carparkShopPolygonDaoImpl")
public class CarparkShopPolygonDaoImpl extends BaseDaoSupport<CarparkShopPolygon> implements CarparkShopPolygonDao {
	public void insertOrUpdateBysql(final List<CarparkShopPolygon> carparkShopPolygons,final List<String> sqls) {
		this.getHibernateTemplate().execute(new HibernateCallback<Object>(){
			public Object doInHibernate(Session session) throws HibernateException,SQLException{
				int j = 0;
				for (int i = 0; i < carparkShopPolygons.size(); i++) {
					CarparkShopPolygon carparkShopPolygon = carparkShopPolygons.get(i);
					String sql = sqls.get(i);
					Query sqlQuery = session.createSQLQuery(sql);
					if(carparkShopPolygon.getGid()!=null){
						sqlQuery.setParameter("gid", carparkShopPolygon.getGid());
					}
					sqlQuery.setLong("carparkid", carparkShopPolygon.getCarpark().getCarparkid());
					sqlQuery.setParameter("bordercolor", carparkShopPolygon.getBordercolor(),Hibernate.INTEGER);
					sqlQuery.setParameter("clickBordercolor", carparkShopPolygon.getClickBordercolor(),Hibernate.INTEGER);
					sqlQuery.setParameter("clickColor", carparkShopPolygon.getClickColor(),Hibernate.INTEGER);
					sqlQuery.setParameter("color", carparkShopPolygon.getColor(),Hibernate.INTEGER);
					sqlQuery.setParameter("floorid", carparkShopPolygon.getFloorid(),Hibernate.LONG);
					sqlQuery.setParameter("fontColor", carparkShopPolygon.getFontColor(),Hibernate.INTEGER);
					sqlQuery.setParameter("fontItalic", carparkShopPolygon.getFontItalic(),Hibernate.INTEGER);
					sqlQuery.setParameter("fontSize", carparkShopPolygon.getFontSize(),Hibernate.FLOAT);
					sqlQuery.setParameter("fontWeight", carparkShopPolygon.getFontWeight(),Hibernate.INTEGER);
	//				sqlQuery.setString("geom", "st_astext('"+carparkShopPolygon.getGeometry()+"')");
					sqlQuery.setParameter("height", carparkShopPolygon.getHeight(),Hibernate.FLOAT);
					sqlQuery.setParameter("width", carparkShopPolygon.getWidth(),Hibernate.FLOAT);
					sqlQuery.setParameter("name", carparkShopPolygon.getName(),Hibernate.STRING);
					sqlQuery.setParameter("memo", carparkShopPolygon.getMemo(),Hibernate.STRING);
					i += sqlQuery.executeUpdate();
				}
				return	j;
			}
		});
		}
}
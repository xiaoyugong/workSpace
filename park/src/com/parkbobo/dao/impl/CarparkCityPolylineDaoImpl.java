package com.parkbobo.dao.impl;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;

import com.parkbobo.dao.CarparkCityPolylineDao;
import com.parkbobo.model.CarparkCityPolyline;
import com.parkbobo.model.CarparkEntrancePoint;
@Component("carparkCityPolylineDaoImpl")
public class CarparkCityPolylineDaoImpl extends BaseDaoSupport<CarparkCityPolyline> implements CarparkCityPolylineDao {
	public void insertOrUpdateBysql(final List<CarparkCityPolyline> carparkCityPolylines,final List<String> sqls) {
		this.getHibernateTemplate().execute(new HibernateCallback<Object>(){
			public Object doInHibernate(Session session) throws HibernateException,SQLException{
				int j = 0;
				for (int i = 0; i < carparkCityPolylines.size(); i++) {
					CarparkCityPolyline carparkCityPolyline = carparkCityPolylines.get(i);
					String sql = sqls.get(i);
				
				Query sqlQuery = session.createSQLQuery(sql);
				if(carparkCityPolyline.getGid()!=null){
					sqlQuery.setParameter("gid", carparkCityPolyline.getGid());
				}
				sqlQuery.setLong("carparkid", carparkCityPolyline.getCarpark().getCarparkid());
				sqlQuery.setParameter("bordercolor", carparkCityPolyline.getBordercolor(),Hibernate.INTEGER);
				sqlQuery.setParameter("color", carparkCityPolyline.getColor(),Hibernate.INTEGER);
				sqlQuery.setParameter("fontColor", carparkCityPolyline.getFontColor(),Hibernate.INTEGER);
				sqlQuery.setParameter("fontItalic", carparkCityPolyline.getFontItalic(),Hibernate.INTEGER);
				sqlQuery.setParameter("fontSize", carparkCityPolyline.getFontSize(),Hibernate.FLOAT);
				sqlQuery.setParameter("fontWeight", carparkCityPolyline.getFontWeight(),Hibernate.INTEGER);
//				sqlQuery.setString("geom", "st_astext('"+carparkCityPolyline.getGeometry()+"')");
				sqlQuery.setParameter("width", carparkCityPolyline.getWidth(),Hibernate.FLOAT);
				sqlQuery.setParameter("height", carparkCityPolyline.getHeigh(),Hibernate.FLOAT);
				sqlQuery.setParameter("name", carparkCityPolyline.getName(),Hibernate.STRING);
				sqlQuery.setParameter("memo", carparkCityPolyline.getMemo(),Hibernate.STRING);
				j+=sqlQuery.executeUpdate();
				}
				return j;
			}
		});
		}
}
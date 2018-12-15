package com.parkbobo.dao.impl;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;

import com.parkbobo.dao.CarparkRoadPolylineDao;
import com.parkbobo.model.CarparkFuzhuPolygon;
import com.parkbobo.model.CarparkRoadPolyline;
@Component("carparkRoadPolylineDaoImpl")
public class CarparkRoadPolylineDaoImpl extends BaseDaoSupport<CarparkRoadPolyline> implements CarparkRoadPolylineDao {
	public void insertOrUpdateBysql(final List<CarparkRoadPolyline> carparkRoadPolylines,final List<String> sqls) {
		this.getHibernateTemplate().execute(new HibernateCallback<Object>(){
			public Object doInHibernate(Session session) throws HibernateException,SQLException{
				int j = 0;
				for (int i = 0; i < carparkRoadPolylines.size(); i++) {
					CarparkRoadPolyline carparkRoadPolyline = carparkRoadPolylines.get(i);
					String sql = sqls.get(i);
					Query sqlQuery = session.createSQLQuery(sql);
					if(carparkRoadPolyline.getGid()!=null){
						sqlQuery.setParameter("gid", carparkRoadPolyline.getGid());
					}
					sqlQuery.setLong("carparkid", carparkRoadPolyline.getCarpark().getCarparkid());
					sqlQuery.setParameter("bordercolor", carparkRoadPolyline.getBordercolor(),Hibernate.INTEGER);
					sqlQuery.setParameter("color", carparkRoadPolyline.getColor(),Hibernate.INTEGER);
					sqlQuery.setParameter("floorid", carparkRoadPolyline.getFloorid(),Hibernate.LONG);
					sqlQuery.setParameter("fontColor", carparkRoadPolyline.getFontColor(),Hibernate.INTEGER);
					sqlQuery.setParameter("fontItalic", carparkRoadPolyline.getFontItalic(),Hibernate.INTEGER);
					sqlQuery.setParameter("fontSize", carparkRoadPolyline.getFontSize(),Hibernate.FLOAT);
					sqlQuery.setParameter("fontWeight", carparkRoadPolyline.getFontWeight(),Hibernate.INTEGER);
					sqlQuery.setParameter("width", carparkRoadPolyline.getWidth(),Hibernate.FLOAT);
	//				sqlQuery.setString("geom", "st_astext('"+carparkRoadPolyline.getGeometry()+"')");
					sqlQuery.setParameter("name", carparkRoadPolyline.getName(),Hibernate.STRING);
					sqlQuery.setParameter("memo", carparkRoadPolyline.getMemo(),Hibernate.STRING);
					j+=sqlQuery.executeUpdate();
				}
				return j;
			}
		});
		}
}
package com.parkbobo.dao.impl;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;

import com.parkbobo.dao.CarparkNavigationPolylineDao;
import com.parkbobo.model.CarparkNavigationPoint;
import com.parkbobo.model.CarparkNavigationPolyline;
import com.parkbobo.model.CarparkRoadPolyline;
@Component("carparkNavigationPolylineDaoImpl")
public class CarparkNavigationPolylineDaoImpl extends BaseDaoSupport<CarparkNavigationPolyline> implements CarparkNavigationPolylineDao {
	public void insertOrUpdateBysql(final List<CarparkNavigationPolyline> carparkNavigationPolylines,final List<String> sqls) {
		this.getHibernateTemplate().execute(new HibernateCallback<Object>(){
			public Object doInHibernate(Session session) throws HibernateException,SQLException{
				int j =0;
				for(int i =0;i<carparkNavigationPolylines.size();i++){
					String sql = sqls.get(i);
					CarparkNavigationPolyline carparkNavigationPolyline = carparkNavigationPolylines.get(i);
					
					Query sqlQuery = session.createSQLQuery(sql);
					if(carparkNavigationPolyline.getGid()!=null){
						sqlQuery.setParameter("gid", carparkNavigationPolyline.getGid());
					}
					sqlQuery.setLong("carparkid", carparkNavigationPolyline.getCarpark().getCarparkid());
					sqlQuery.setParameter("bordercolor", carparkNavigationPolyline.getBordercolor(),Hibernate.INTEGER);
					sqlQuery.setParameter("color", carparkNavigationPolyline.getColor(),Hibernate.INTEGER);
					sqlQuery.setParameter("floorid", carparkNavigationPolyline.getFloorid(),Hibernate.LONG);
					sqlQuery.setParameter("fontColor", carparkNavigationPolyline.getFontColor(),Hibernate.INTEGER);
					sqlQuery.setParameter("fontItalic", carparkNavigationPolyline.getFontItalic(),Hibernate.INTEGER);
					sqlQuery.setParameter("fontSize", carparkNavigationPolyline.getFontSize(),Hibernate.FLOAT);
					sqlQuery.setParameter("fontWeight", carparkNavigationPolyline.getFontWeight(),Hibernate.INTEGER);
					sqlQuery.setParameter("roadType", carparkNavigationPolyline.getRoadType(),Hibernate.INTEGER);
					sqlQuery.setParameter("direction", carparkNavigationPolyline.getDirection(),Hibernate.INTEGER);
	//				sqlQuery.setString("geom", "st_astext('"+carparkNavigationPolyline.getGeometry()+"')");
					sqlQuery.setParameter("name", carparkNavigationPolyline.getName(),Hibernate.STRING);
					sqlQuery.setParameter("memo", carparkNavigationPolyline.getMemo(),Hibernate.STRING);
					j+=sqlQuery.executeUpdate();
				}
				return j;
			}
		});
		}
}
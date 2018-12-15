package com.parkbobo.dao.impl;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;

import com.parkbobo.dao.CarparkFuzhuPolygonDao;
import com.parkbobo.model.CarparkEntrancePoint;
import com.parkbobo.model.CarparkFuzhuPolygon;
@Component("carparkFuzhuPolygonDaoImpl")
public class CarparkFuzhuPolygonDaoImpl extends BaseDaoSupport<CarparkFuzhuPolygon> implements CarparkFuzhuPolygonDao {
	public void insertOrUpdateBysql(final List<CarparkFuzhuPolygon> carparkFuzhuPolygons,final List<String> sqls) {
		this.getHibernateTemplate().execute(new HibernateCallback<Object>(){
			public Object doInHibernate(Session session) throws HibernateException,SQLException{
//				String sqlString = "insert into lq_carpark_berth_polygon (bordercolor, carparkid, click_bordercolor, click_color, color, floorid, font_color, font_italic, font_size, font_weight, geom, height, memo, name, width)" +
//						" values (12, 12, 12, 12, 12, 12, 12, 12, 2, 12, st_astext('0106000000010000000103000000010000000500000030A5407A91045A4091C33AD567B03E40C0B8321592045A4091C33AD567B03E40C0B8321592045A4036F3D57B66B03E4030A5407A91045A4036F3D57B66B03E4030A5407A91045A4091C33AD567B03E40'), 12, 12, 12, 12)";
				int i = 0;
				for(int j = 0;j<carparkFuzhuPolygons.size();j++){
					CarparkFuzhuPolygon carparkFuzhuPolygon = carparkFuzhuPolygons.get(j);
					Query sqlQuery = session.createSQLQuery(sqls.get(j));
					if(carparkFuzhuPolygon.getGid()!=null){
						sqlQuery.setParameter("gid", carparkFuzhuPolygon.getGid());
					}
					sqlQuery.setLong("carparkid", carparkFuzhuPolygon.getCarpark().getCarparkid());
					sqlQuery.setParameter("bordercolor", carparkFuzhuPolygon.getBordercolor(),Hibernate.INTEGER);
					sqlQuery.setParameter("color", carparkFuzhuPolygon.getColor(),Hibernate.INTEGER);
					sqlQuery.setParameter("floorid", carparkFuzhuPolygon.getFloorid(),Hibernate.LONG);
					sqlQuery.setParameter("fontColor", carparkFuzhuPolygon.getFontColor(),Hibernate.INTEGER);
					sqlQuery.setParameter("fontItalic", carparkFuzhuPolygon.getFontItalic(),Hibernate.INTEGER);
					sqlQuery.setParameter("fontSize", carparkFuzhuPolygon.getFontSize(),Hibernate.FLOAT);
					sqlQuery.setParameter("fontWeight", carparkFuzhuPolygon.getFontWeight(),Hibernate.INTEGER);
	//				sqlQuery.setString("geom", "st_astext('"+carparkFuzhuPolygon.getGeometry()+"')");
					sqlQuery.setParameter("height", carparkFuzhuPolygon.getHeight(),Hibernate.FLOAT);
					sqlQuery.setParameter("name", carparkFuzhuPolygon.getName(),Hibernate.STRING);
					sqlQuery.setParameter("memo", carparkFuzhuPolygon.getMemo(),Hibernate.STRING);
					i+=sqlQuery.executeUpdate();
				}
				return i;
			}
		});
		}
}
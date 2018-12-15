package com.parkbobo.dao;
import java.util.List;

import com.parkbobo.model.CarparkBackgroundPolygon;
import com.parkbobo.model.CarparkCityPolyline;
public interface CarparkCityPolylineDao extends BaseDao<CarparkCityPolyline>{
	public void insertOrUpdateBysql(final List<CarparkCityPolyline> carparkCityPolylines,final List<String> sqls) ;
}
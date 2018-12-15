package com.parkbobo.dao;
import java.util.List;

import com.parkbobo.model.CarparkBerthPolygon;
public interface CarparkBerthPolygonDao extends BaseDao<CarparkBerthPolygon>{
	public void insertOrUpdateBysql(List<CarparkBerthPolygon> carparkBerthPolygons,List<String> sql);
}
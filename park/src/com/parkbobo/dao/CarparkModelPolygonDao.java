package com.parkbobo.dao;

import java.util.List;

import com.parkbobo.model.CarparkModelPolygon;

public interface CarparkModelPolygonDao extends BaseDao<CarparkModelPolygon>{
	public void insertOrUpdateBysql(final List<CarparkModelPolygon> carparkModelPolygons,final List<String> sqls);
}
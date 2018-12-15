package com.parkbobo.dao;
import java.util.List;

import com.parkbobo.model.CarparkMarkerPoint;
import com.parkbobo.model.CarparkShopPolygon;
public interface CarparkShopPolygonDao extends BaseDao<CarparkShopPolygon>{
	public void insertOrUpdateBysql(final List<CarparkShopPolygon> carparkShopPolygons,final List<String> sqls) ;
}
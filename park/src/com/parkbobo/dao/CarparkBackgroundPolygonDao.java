package com.parkbobo.dao;
import java.util.List;

import com.parkbobo.model.CarparkBackgroundPolygon;
import com.parkbobo.model.CarparkShopPolygon;
public interface CarparkBackgroundPolygonDao extends BaseDao<CarparkBackgroundPolygon>{
	public void insertOrUpdateBysql(final List<CarparkBackgroundPolygon> carparkBackgroundPolygons,final List<String> sqls) ;
}
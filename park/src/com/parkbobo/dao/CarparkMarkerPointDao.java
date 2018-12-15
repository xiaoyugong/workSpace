package com.parkbobo.dao;
import java.util.List;

import com.parkbobo.model.CarparkFuzhuPolygon;
import com.parkbobo.model.CarparkMarkerPoint;
public interface CarparkMarkerPointDao extends BaseDao<CarparkMarkerPoint>{
	public void insertOrUpdateBysql(final List<CarparkMarkerPoint> carparkMarkerPoints,final List<String> sqls) ;
}
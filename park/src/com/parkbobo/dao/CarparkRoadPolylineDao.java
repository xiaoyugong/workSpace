package com.parkbobo.dao;
import java.util.List;

import com.parkbobo.model.CarparkRoadPolyline;
public interface CarparkRoadPolylineDao extends BaseDao<CarparkRoadPolyline>{
	public void insertOrUpdateBysql(final List<CarparkRoadPolyline> carparkRoadPolylines,final List<String> sqls) ;
}
package com.parkbobo.dao;
import java.util.List;

import com.parkbobo.model.CarparkBackgroundPolygon;
import com.parkbobo.model.CarparkEntrancePoint;
import com.parkbobo.model.CarparkFuzhuPolygon;
public interface CarparkFuzhuPolygonDao extends BaseDao<CarparkFuzhuPolygon>{
	public void insertOrUpdateBysql(final List<CarparkFuzhuPolygon> carparkFuzhuPolygons,final List<String> sqls) ;
}
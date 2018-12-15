package com.parkbobo.dao;
import java.util.List;

import com.parkbobo.model.CarparkNavigationPolyline;
public interface CarparkNavigationPolylineDao extends BaseDao<CarparkNavigationPolyline>{

	void insertOrUpdateBysql(
			List<CarparkNavigationPolyline> carparkNavigationPolylines,final List<String> sqls);
}
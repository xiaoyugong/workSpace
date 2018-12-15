package com.parkbobo.dao;
import java.util.List;

import com.parkbobo.model.CarparkNavigationPoint;
public interface CarparkNavigationPointDao extends BaseDao<CarparkNavigationPoint>{
	public void insertOrUpdateBysql(final List<CarparkNavigationPoint> carparkNavigationPoints,final List<String> sqls);
}
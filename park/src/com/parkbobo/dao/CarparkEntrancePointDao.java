package com.parkbobo.dao;
import java.util.List;

import com.parkbobo.model.CarparkEntrancePoint;
public interface CarparkEntrancePointDao extends BaseDao<CarparkEntrancePoint>{
	public void insertOrUpdateBysql(final List<CarparkEntrancePoint> carparkEntrancePoints,final List<String> sqls) ;
}
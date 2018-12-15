package com.parkbobo.manager.dao;

import java.util.List;

import com.parkbobo.dao.BaseDao;
import com.parkbobo.manager.model.SystemStatisticsView;

public interface SystemStatisticsViewDao extends BaseDao<SystemStatisticsView> {
	public abstract List<Object[]> getBySql(String sql);
}

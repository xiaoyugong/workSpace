package com.parkbobo.dao;
import com.parkbobo.model.Carpark;
public interface CarparkDao extends BaseDao<Carpark>{
	public abstract void executeSql(String sql);
}
package com.parkbobo.manager.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Component;

import com.parkbobo.manager.dao.SystemStatisticsViewDao;
import com.parkbobo.manager.model.SystemStatisticsView;
import com.parkbobo.model.Carpark;
import com.parkbobo.service.CarparkService;
import com.parkbobo.utils.SystemCarStatistics;
import com.parkbobo.utils.SystemStatistics;

@Component("systemStatisticsViewService")
public class SystemStatisticsViewService {
	@Resource(name="systemStatisticsViewDaoImpl")
	private SystemStatisticsViewDao statisticsViewDao;
	@Resource(name="carparkService")
	private CarparkService carparkService;

	public SystemStatistics getList(SystemStatistics statistics) {
		if(statistics==null){
			statistics = new SystemStatistics();
		}
		StringBuilder sbSql = new StringBuilder();
		sbSql.append("select " +
				"sum(b.stop_total_millisecond) totalTime," +
				"(SELECT count(*) FROM lq_berth_order b WHERE b.stop_overtime_money>0) overTimeNum," +
				"sum(b.stop_total_money) totalMoney," +
				"count(*) totalCarNum," +
				"sum(b.owner_revenue) totalOwnerRevenue," +
				"sum(b.property_revenue) totalPropertyRevenue," +
				"sum(b.company_revenue) totalCompanyRevenue " +
				"FROM lq_system_statistics_view AS b where 1=1");
		if(statistics.getStartTime()!=null){
			sbSql.append(" and b.posttime>"+statistics.getStartTime().getTime());
		}
		if(statistics.getEndTime()!=null){
			sbSql.append(" and b.posttime<"+statistics.getEndTime().getTime());
		}
		if(statistics.getCarparkname()!=null && !"".equals(statistics.getCarparkname())){
			sbSql.append(" and b.carparkname like '%"+statistics.getCarparkname()+"%'");
		}
		if(statistics!=null && statistics.getArea()!=null && !"".equals(statistics.getArea())){
			sbSql.append(" and b.city like '%"+statistics.getArea()+"%'");
		}
		String sql = sbSql.toString();
		List<Object[]> object = statisticsViewDao.getBySql(sql);
		for (Object[] objects : object) {
			statistics.setTotalTime((BigDecimal)objects[0]);
			statistics.setOverTimeNum((BigInteger)objects[1]);
			statistics.setTotalMoney((BigDecimal)objects[2]);
			statistics.setTotalCarNum((BigInteger)objects[3]);
			statistics.setTotalOwnerRevenue((BigDecimal)objects[4]);
			statistics.setTotalPropertyRevenue((BigDecimal)objects[5]);
			statistics.setTotalCompanyRevenue((BigDecimal)objects[6]);
		}
		return statistics;
	}
	public List<SystemCarStatistics> getCarList(SystemStatistics statistics) {
		List<SystemCarStatistics> list = new ArrayList<SystemCarStatistics>();
		StringBuilder sbSql = new StringBuilder();
		sbSql.append("select "+ 
				"s.carparkid,"+
				"s.carparkname,"+
				"s.city,"+
				"count(*) as total_order,"+
				"(SELECT count(*) FROM lq_berth_share as bs WHERE s.carparkid=bs.carparkid) as total_berth_share,"+
				"sum(s.stop_overtime_money) as stop_overtime_money,"+
				"sum(s.stop_total_millisecond) as stop_total_millisecond,"+
				"sum(s.stop_total_money) as stop_total_money,"+
				"sum(s.owner_revenue) as owner_revenue,"+
				"sum(s.company_revenue) as company_revenue,"+
				"sum(s.property_revenue) as property_revenue "+
		"FROM lq_system_statistics_view as s WHERE 1=1");
		if(statistics!=null && statistics.getStartTime()!=null){
			sbSql.append(" and s.posttime>"+statistics.getStartTime().getTime());
		}
		if(statistics!=null && statistics.getEndTime()!=null){
			sbSql.append(" and s.posttime<"+statistics.getEndTime().getTime());
		}
		if(statistics!=null && statistics.getCarparkname()!=null && !"".equals(statistics.getCarparkname())){
			sbSql.append(" and s.carparkname like '%"+statistics.getCarparkname()+"%'");
		}
		if(statistics!=null && statistics.getArea()!=null && !"".equals(statistics.getArea())){
			sbSql.append(" and s.city like '%"+statistics.getArea()+"%'");
		}

		sbSql.append(" GROUP BY s.carparkname,s.carparkid,s.city"+
			" ORDER BY stop_total_money DESC");
		
		String sql = sbSql.toString();
		List<Object[]> object = statisticsViewDao.getBySql(sql);
		for (Object[] objects : object) {
			SystemCarStatistics	carStatistics = new SystemCarStatistics();
			carStatistics.setCarparkid((BigInteger)objects[0]);
			carStatistics.setCarparkname((String)objects[1]);
			carStatistics.setCity((String)objects[2]);
			carStatistics.setCarparkTotalOrder((BigInteger)objects[3]);
			carStatistics.setCarparkBerthShareNum((BigInteger)objects[4]);
			carStatistics.setCarparkTime((BigDecimal)objects[6]);
			carStatistics.setCarparkMoney((BigDecimal)objects[7]);
			carStatistics.setCarparkOwnerRevenue((BigDecimal)objects[8]);
			carStatistics.setCarparkPropertyRevenue((BigDecimal)objects[9]);
			carStatistics.setCarparkCompanyRevenue((BigDecimal)objects[10]);
			list.add(carStatistics);
		}
		return list;
		
	}
	
	
	
	public String getCarparkName(String inputCarparkname) {
		if(inputCarparkname!=null && !"".equals(inputCarparkname)){
			String hql = "select distinct s.carparkname from SystemStatisticsView as s where s.carparkname like '%"+inputCarparkname+"%'";	
			List<SystemStatisticsView> list = statisticsViewDao.getByHQL(hql);
			JSONArray fromObject = JSONArray.fromObject(list);
			return fromObject.toString();		
		}else{
			return null;
		}
	}

}

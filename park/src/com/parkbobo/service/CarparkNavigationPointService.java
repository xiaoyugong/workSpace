package com.parkbobo.service;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.parkbobo.dao.CarparkDao;
import com.parkbobo.dao.CarparkNavigationPointDao;
import com.parkbobo.model.Carpark;
import com.parkbobo.model.CarparkNavigationPoint;
import com.parkbobo.model.OptLogs;
import com.parkbobo.utils.ExcelUtils;
import com.parkbobo.utils.PageBean;
@Component("carparkNavigationPointService")
public class CarparkNavigationPointService {
	@Resource(name="carparkNavigationPointDaoImpl")
	private CarparkNavigationPointDao carparkNavigationPointDao;
	@Resource(name="carparkDaoImpl")
	private CarparkDao carparkDao;
	private Map<String, Carpark> carparkCache = new HashMap<String, Carpark>();
	@Resource(name="optLogsService")
	private OptLogsService optLogsService;
	public List<CarparkNavigationPoint> getByHql(String hql){
		return this.carparkNavigationPointDao.getByHQL(hql);
	}

	public PageBean<CarparkNavigationPoint> page(String hqlString,
			int pageSize, int page) {
		// TODO Auto-generated method stub
		return carparkNavigationPointDao.pageQuery(hqlString, pageSize, page);
	}

	public CarparkNavigationPoint get(Long gid) {
		// TODO Auto-generated method stub
		return carparkNavigationPointDao.get(gid);
	}


	public void update(CarparkNavigationPoint carparkNavigationPoint) {
		// TODO Auto-generated method stub
		List<CarparkNavigationPoint> carparkNavigationPoints = new ArrayList<CarparkNavigationPoint>();
		List<String> sqls = new ArrayList<String>(); 
		String updatesql = " update lq_carpark_navigation_point set carparkid=:carparkid,road_type=:roadType,distance=:distance,"
				+"start_floorid=:startFloorid,end_floorid=:endFloorid,description=:description,memo=:memo";
		if(!carparkNavigationPoint.getStartGeometry().trim().equals("")){
			updatesql += ",start_geom=st_astext('"+carparkNavigationPoint.getStartGeometry().trim()+"')";
		}
		if(!carparkNavigationPoint.getEndGeometry().trim().equals("")){
			updatesql += ",end_geom=st_astext('"+carparkNavigationPoint.getEndGeometry().trim()+"')";
		}
			updatesql += " where gid=:gid";
			carparkNavigationPoints.add(carparkNavigationPoint);
			sqls.add(updatesql);
			carparkNavigationPointDao.insertOrUpdateBysql(carparkNavigationPoints, sqls);
	}

	public void add(CarparkNavigationPoint carparkNavigationPoint) {
		List<CarparkNavigationPoint> carparkNavigationPoints = new ArrayList<CarparkNavigationPoint>();
		List<String> sqls = new ArrayList<String>(); 
		String insertsql = " insert into lq_carpark_navigation_point " +
				"(carparkid,road_type,distance,start_floorid,end_floorid,description," +
				"start_geom,end_geom,memo) values(:carparkid,:roadType,:distance,:startFloorid,:endFloorid,:description," +
				"st_astext("+(carparkNavigationPoint.getStartGeometry().trim().equals("")?null:("'"+carparkNavigationPoint.getStartGeometry().trim())+"'")+")," +
				"st_astext("+(carparkNavigationPoint.getEndGeometry().trim().equals("")?null:("'"+carparkNavigationPoint.getEndGeometry().trim())+"'")+"),:memo)";
		carparkNavigationPoints.add(carparkNavigationPoint);
		System.out.println("insertsql ===" + insertsql);
		sqls.add(insertsql);
		carparkNavigationPointDao.insertOrUpdateBysql(carparkNavigationPoints, sqls);
		
	}
	
	public void bulkAdd(List<CarparkNavigationPoint> carparkNavigationPoints){
		List<String> sqls = new ArrayList<String>();
		for(CarparkNavigationPoint carparkNavigationPoint:carparkNavigationPoints){
			String insertsql = " insert into lq_carpark_navigation_point  " +
			"(carparkid,road_type,distance,start_floorid,end_floorid,description," +
			"start_geom,end_geom,memo) values(:carparkid,:roadType,:distance,:startFloorid,:endFloorid,:description," +
			"st_astext("+(carparkNavigationPoint.getStartGeometry().trim().equals("")?null:("'"+carparkNavigationPoint.getStartGeometry().trim())+"'")+")," +
			"st_astext("+(carparkNavigationPoint.getEndGeometry().trim().equals("")?null:("'"+carparkNavigationPoint.getEndGeometry().trim())+"'")+"),:memo)";
			sqls.add(insertsql);
		}
		carparkNavigationPointDao.insertOrUpdateBysql(carparkNavigationPoints, sqls);
		
	}
	public Carpark getCarpark(String carparkName){
		Carpark carpark = carparkCache.get(carparkName);
		if(carpark!=null){
			return carpark;
		}
		List<Carpark> list  = carparkDao.getByProperty("name", carparkName);
		if(list.size()>0){
			carpark = list.get(0);
			carparkCache.put(carparkName, carpark);
			return carpark;
		}
		return null;
	}
	public  String importExcel(File  file){
		ExcelUtils util = new ExcelUtils();
		List<List<String>> list = util.read(file);
		String messageString = "";
		List<CarparkNavigationPoint> carparkNavigationPoints = new ArrayList<CarparkNavigationPoint>();
		for(int i = 0;i<list.size();i++){
			if(i==0){
				continue;
			}
			CarparkNavigationPoint carparkCityPolyline = new CarparkNavigationPoint();
			for(int j = 0;j < list.get(i).size();j++){
				try {
					
				String titleString = list.get(0).get(j).trim();
				String contentString = list.get(i).get(j);
				boolean isNull = contentString==null||"".equals(contentString.trim());
				if(titleString.equals("停车场")){
					Carpark carpark = getCarpark(contentString);
					if(carpark==null){
						messageString+="没找到停车【"+contentString+"】\t";
						throw new Exception("没找到停车场");
					}
					carparkCityPolyline.setCarparkid(carpark.getCarparkid());
				}
				if(titleString.equals("长度")){
					carparkCityPolyline.setDistance(Integer.valueOf(contentString));		
				}
				if(titleString.equals("描述")){
					carparkCityPolyline.setDescription(contentString);		
				}
				if(titleString.equals("起始点几何信息")){
					carparkCityPolyline.setStartGeometry(contentString);
				}
				if(titleString.equals("结束点几何信息")){
					carparkCityPolyline.setEndGeometry(contentString);
				}
				if(titleString.equals("备注")){
					carparkCityPolyline.setMemo(contentString);		
				}
				
				} catch (Exception e) {
					// TODO: handle exception
					messageString+="错误行:"+(i+1)+";列:"+(j+1)+";\n";
				}
			}
			
			carparkNavigationPoints.add(carparkCityPolyline);
			
		}
		if(messageString.equals("")&&list.size()!=0){
			try {
				bulkAdd(carparkNavigationPoints);
				messageString="成功导入"+(list.size()-1)+"条数据";
				optLogsService.add(new OptLogs(messageString,"跨楼层导航点"));
			} catch (Exception e) {
				// TODO: handle exception
				messageString="导入错误!请检查模板是否正确!";
				e.printStackTrace();
			}
		}
		return messageString;
	}
	public void delete(String ids) {
		String[] strs = ids.split(",");
		Long[] idArr = new Long[strs.length];
		for (int i=0; i< strs.length; i++) {
			idArr[i] = Long.valueOf(strs[i]);
		}
		
		carparkNavigationPointDao.bulkDelete(idArr);
	
	}
}
package com.parkbobo.service;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.parkbobo.dao.CarparkDao;
import com.parkbobo.dao.CarparkEntrancePointDao;
import com.parkbobo.model.Carpark;
import com.parkbobo.model.CarparkEntrancePoint;
import com.parkbobo.model.OptLogs;
import com.parkbobo.utils.ExcelUtils;
import com.parkbobo.utils.PageBean;
@Component("carparkEntrancePointService")
public class CarparkEntrancePointService {
	@Resource(name="carparkEntrancePointDaoImpl")
	private CarparkEntrancePointDao carparkEntrancePointDao;
	@Resource(name="carparkDaoImpl")
	private CarparkDao carparkDao;
	private Map<String, Carpark> carparkCache = new HashMap<String, Carpark>();
	private static Map<String,Short> entranceMap = new HashMap<String, Short>();
	@Resource(name="optLogsService")
	private OptLogsService optLogsService;
	public List<CarparkEntrancePoint> getByHql(String hql){
		return this.carparkEntrancePointDao.getByHQL(hql);
	}
	 static {
		
		entranceMap.put("入口", (short)0);
		entranceMap.put("出口", (short)1);
		entranceMap.put("出入口", (short)2);
		
	}
//	public void add(CarparkEntrancePoint carparkEntrancePoint) {
//		carparkEntrancePointDao.merge(carparkEntrancePoint);
//	}

	public void delete(String ids) {
		String[] strs = ids.split(",");
		Long[] idArr = new Long[strs.length];
		for (int i=0; i< strs.length; i++) {
			idArr[i] = Long.valueOf(strs[i]);
		}
		
		carparkEntrancePointDao.bulkDelete(idArr);
	
	}

	public CarparkEntrancePoint get(Long gid) {
		// TODO Auto-generated method stub
		return carparkEntrancePointDao.get(gid);
	}

	public PageBean<CarparkEntrancePoint> page(String hqlString, int pageSize,
			int page) {
		// TODO Auto-generated method stub
		return carparkEntrancePointDao.pageQuery(hqlString, pageSize, page);
	}
	
	
	public void update(CarparkEntrancePoint carparkEntrancePoint){
		List<String> sqls = new ArrayList<String>();
		List<CarparkEntrancePoint> carparkEntrancePoints = new ArrayList<CarparkEntrancePoint>();
		String sql="update lq_carpark_entrance_point set carparkid=:carparkid,floorid=:floorid," +
				"name=:name,type=:type,gaode_longitude=:gaodeLongitude,gaode_latitude=:gaodeLatitude,";
		if(!carparkEntrancePoint.getGeometry().trim().equals("")){
					sql+="geom=st_astext('"+carparkEntrancePoint.getGeometry().trim()+"'),";
				}
				sql+="memo=:memo where gid=:gid";
				carparkEntrancePoints.add(carparkEntrancePoint);
				sqls.add(sql);
		carparkEntrancePointDao.insertOrUpdateBysql(carparkEntrancePoints, sqls);
		
	}
	public void add(CarparkEntrancePoint carparkEntrancePoint){
		List<String> sqls = new ArrayList<String>();
		List<CarparkEntrancePoint> carparkEntrancePoints = new ArrayList<CarparkEntrancePoint>();
		String sql="insert into lq_carpark_entrance_point (carparkid,floorid,name,type,gaode_longitude" +
				",gaode_latitude,geom,memo)values(:carparkid,:floorid,:name,:type,:gaodeLongitude,:gaodeLatitude," +
				"st_astext("+(carparkEntrancePoint.getGeometry().trim().equals("")?null:("'"+carparkEntrancePoint.getGeometry().trim())+"'")+"),:memo)";
		carparkEntrancePoints.add(carparkEntrancePoint);
		sqls.add(sql);
		carparkEntrancePointDao.insertOrUpdateBysql(carparkEntrancePoints, sqls);
		
	}
	public void bulkAdd(List<CarparkEntrancePoint> carparkEntrancePoints){
		List<String> sqls = new ArrayList<String>();
		for(CarparkEntrancePoint carparkEntrancePoint:carparkEntrancePoints){
			String sql="insert into lq_carpark_entrance_point (carparkid,floorid,name,type,gaode_longitude" +
			",gaode_latitude,geom,memo)values(:carparkid,:floorid,:name,:type,:gaodeLongitude,:gaodeLatitude," +
			"st_astext("+(carparkEntrancePoint.getGeometry().trim().equals("")?null:("'"+carparkEntrancePoint.getGeometry().trim())+"'")+"),:memo)";
			sqls.add(sql);
		}
		carparkEntrancePointDao.insertOrUpdateBysql(carparkEntrancePoints, sqls);
		
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
		List<CarparkEntrancePoint> carparkEntrancePoints = new ArrayList<CarparkEntrancePoint>();
		for(int i = 0;i<list.size();i++){
			if(i==0){
				continue;
			}
			CarparkEntrancePoint carparkEntrancePoint = new CarparkEntrancePoint();
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
					carparkEntrancePoint.setCarpark(carpark);
				}
				if(titleString.equals("类型")){
					Short typeShort = entranceMap.get(contentString);
					if(typeShort==null){
						messageString+="类型【"+contentString+"】只能为：'入口'，'出口'，'出入口'\t";
						throw new Exception("出入口类型有误");
					}
					carparkEntrancePoint.setType(typeShort);			
					}
				if(titleString.equals("名称")){
					carparkEntrancePoint.setName(contentString);			
					}
				if(titleString.equals("高德经度")){
					carparkEntrancePoint.setGaodeLongitude(contentString);			
					}
				if(titleString.equals("高德纬度")){
					carparkEntrancePoint.setGaodeLatitude(contentString);			
					}
				if(titleString.equals("空间经纬度信息")){
					carparkEntrancePoint.setGeometry(contentString);
				}
				if(titleString.equals("备注")){
					carparkEntrancePoint.setMemo(contentString);		
				}
				
				} catch (Exception e) {
					// TODO: handle exception
					messageString+="错误行:"+(i+1)+";列:"+(j+1)+";\n";
				}
			}
			
			carparkEntrancePoints.add(carparkEntrancePoint);
			
		}
		if(messageString.equals("")&&list.size()!=0){
			try {
				bulkAdd(carparkEntrancePoints);
				messageString="成功导入"+(list.size()-1)+"条数据";
				optLogsService.add(new OptLogs(messageString,"停车场出入口"));
			} catch (Exception e) {
				// TODO: handle exception
				messageString="导入错误!请检查模板是否正确!";
				e.printStackTrace();
			}
		}
		return messageString;
	}
}
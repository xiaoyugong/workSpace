package com.parkbobo.service;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.parkbobo.dao.CarparkDao;
import com.parkbobo.dao.CarparkFloorDao;
import com.parkbobo.model.Carpark;
import com.parkbobo.model.CarparkFloor;
import com.parkbobo.model.OptLogs;
import com.parkbobo.utils.ExcelUtils;
import com.parkbobo.utils.PageBean;
@Component("carparkFloorService")
public class CarparkFloorService {
	@Resource(name="carparkFloorDaoImpl")
	private CarparkFloorDao carparkFloorDao;
	@Resource(name="carparkDaoImpl")
	private CarparkDao carparkDao;
	@Resource(name="optLogsService")
	private OptLogsService optLogsService;
	private Map<String, Carpark> carparkCache = new HashMap<String, Carpark>();
	private static Map<String,Integer > isDefaultmap = new HashMap<String,Integer >();
	
	public List<CarparkFloor> getByHql(String hql){
		return this.carparkFloorDao.getByHQL(hql);
	}

	public PageBean<CarparkFloor> page(String sqlString, int pageSize,
			int page) {
		// TODO Auto-generated method stub
		return carparkFloorDao.pageQuery(sqlString, pageSize, page);
	}

	public CarparkFloor get(Long floorid) {
		// TODO Auto-generated method stub
		return carparkFloorDao.get(floorid);
	}

	public void update(CarparkFloor carparkFloor) {
		// TODO Auto-generated method stub
		carparkFloorDao.update(carparkFloor);
	}
	public void add(CarparkFloor carparkFloor) {
		// TODO Auto-generated method stub
		carparkFloorDao.save(carparkFloor);
	}
	public void delete(String ids) {
		String[] strs = ids.split(",");
		Long[] idArr = new Long[strs.length];
		for (int i=0; i< strs.length; i++) {
			idArr[i] = Long.valueOf(strs[i]);
		}
		
		carparkFloorDao.bulkDelete(idArr);
	
	}
	public void bulkAdd(List<CarparkFloor> carparkFloors){
		carparkFloorDao.bulksave(carparkFloors);
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
		List<CarparkFloor> carparkFloors = new ArrayList<CarparkFloor>();
		for(int i = 0;i<list.size();i++){
			if(i==0){
				continue;
			}
			CarparkFloor carparkFloor = new CarparkFloor();
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
					carparkFloor.setCarpark(carpark);
				}
				if(titleString.equals("显示默认楼层")){
					Integer defaultInt = isDefaultmap.get(contentString);
					if(defaultInt==null){
						messageString+="显示默认楼层只能为是或否\t";
						throw new Exception("显示默认楼层只能为是或否");
					}
					carparkFloor.setIsDefault(defaultInt);			
					}
				if(titleString.equals("名称")){
					carparkFloor.setName(contentString);			
					}
				if(titleString.equals("备注")){
					carparkFloor.setMemo(contentString);		
				}
				
				} catch (Exception e) {
					// TODO: handle exception
					messageString+="错误行:"+(i+1)+";列:"+(j+1)+";\n";
				}
			}
			
			carparkFloors.add(carparkFloor);
			
		}
		if(messageString.equals("")&&list.size()!=0){
			try {
				bulkAdd(carparkFloors);
				messageString="成功导入"+(list.size()-1)+"条数据";
				optLogsService.add(new OptLogs(messageString,"停车场楼层"));
			} catch (Exception e) {
				// TODO: handle exception
				messageString="导入错误!请检查模板是否正确!";
				e.printStackTrace();
			}
		}
		return messageString;
	}
	
	static{
		isDefaultmap.put("否",0);
		isDefaultmap.put("是",1);
	}
	
}
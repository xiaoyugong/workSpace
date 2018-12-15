package com.parkbobo.service;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.parkbobo.dao.CarparkDao;
import com.parkbobo.dao.CarparkMarkerPointDao;
import com.parkbobo.model.Carpark;
import com.parkbobo.model.CarparkBerthPolygon;
import com.parkbobo.model.CarparkMarkerPoint;
import com.parkbobo.model.OptLogs;
import com.parkbobo.utils.ExcelUtils;
import com.parkbobo.utils.PageBean;
@Component("carparkMarkerPointService")
public class CarparkMarkerPointService {
	@Resource(name="carparkMarkerPointDaoImpl")
	private CarparkMarkerPointDao carparkMarkerPointDao;
	@Resource(name="carparkDaoImpl")
	private CarparkDao carparkDao;
	private Map<String, Carpark> carparkCache = new HashMap<String, Carpark>();
	@Resource(name="optLogsService")
	private OptLogsService optLogsService;
	public List<CarparkMarkerPoint> getByHql(String hql){
		return this.carparkMarkerPointDao.getByHQL(hql);
	}

	public PageBean<CarparkMarkerPoint> page(String hql, int pageSize, int page) {
		// TODO Auto-generated method stub
		return carparkMarkerPointDao.pageQuery(hql, pageSize, page);
	}

	public CarparkMarkerPoint get(Long gid) {
		// TODO Auto-generated method stub
		return carparkMarkerPointDao.get(gid);
	}

	public void update(CarparkMarkerPoint carparkMarkerPoint) {
		// TODO Auto-generated method stub
		List<String> sqls = new ArrayList<String>();
		List<CarparkMarkerPoint> carparkMarkerPoints = new ArrayList<CarparkMarkerPoint>();
		String updatesql = " update lq_carpark_marker_point set carparkid=:carparkid,categoryid=:categoryid,"
		+"floorid=:floorid,name=:name,font_color=:fontColor," +
				"font_size=:fontSize,font_weight=:fontWeight,font_italic=:fontItalic,memo=:memo";
		if(!carparkMarkerPoint.getGeometry().trim().equals("")){
			updatesql += ",geom=st_astext('"+carparkMarkerPoint.getGeometry().trim()+"')";
		}
			updatesql += " where gid=:gid";
		carparkMarkerPoints.add(carparkMarkerPoint);
		sqls.add(updatesql);
		carparkMarkerPointDao.insertOrUpdateBysql(carparkMarkerPoints, sqls);
	}

	public void add(CarparkMarkerPoint carparkMarkerPoint) {
		List<String> sqls = new ArrayList<String>();
		List<CarparkMarkerPoint> carparkMarkerPoints = new ArrayList<CarparkMarkerPoint>();
		String insertsql = " insert into lq_carpark_marker_point  " +
				"(carparkid,categoryid,floorid,name," +
					"font_color," +
					"font_size,font_weight,font_italic," +
					"geom,memo) values(:carparkid,:categoryid,:floorid,:name" +
					",:fontColor,:fontSize,:fontWeight,:fontItalic," +
					"st_astext("+(carparkMarkerPoint.getGeometry().trim().equals("")?null:("'"+carparkMarkerPoint.getGeometry().trim())+"'")+"),:memo)";
		carparkMarkerPoints.add(carparkMarkerPoint);
		sqls.add(insertsql);
		carparkMarkerPointDao.insertOrUpdateBysql(carparkMarkerPoints, sqls);
	}

	public void delete(String ids) {
		String[] strs = ids.split(",");
		Long[] idArr = new Long[strs.length];
		for (int i=0; i< strs.length; i++) {
			idArr[i] = Long.valueOf(strs[i]);
		}
		
		carparkMarkerPointDao.bulkDelete(idArr);
	
	}
	public void bulkAdd(List<CarparkMarkerPoint> carparkMarkerPoints){
		List<String> sqls = new ArrayList<String>();
		for(CarparkMarkerPoint carparkMarkerPoint:carparkMarkerPoints){
			String insertsql = " insert into lq_carpark_marker_point  " +
			"(carparkid,categoryid,floorid,name," +
				"font_color," +
				"font_size,font_weight,font_italic," +
				"geom,memo) values(:carparkid,:categoryid,:floorid,:name" +
				",:fontColor,:fontSize,:fontWeight,:fontItalic," +
				"st_astext("+(carparkMarkerPoint.getGeometry().trim().equals("")?null:("'"+carparkMarkerPoint.getGeometry().trim())+"'")+"),:memo)";
			sqls.add(insertsql);
		}
		carparkMarkerPointDao.insertOrUpdateBysql(carparkMarkerPoints, sqls);
		
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
		List<CarparkMarkerPoint> carparkMarkerPoints = new ArrayList<CarparkMarkerPoint>();
		for(int i = 0;i<list.size();i++){
			if(i==0){
				continue;
			}
			CarparkMarkerPoint carparkMarkerPoint = new CarparkMarkerPoint();
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
					carparkMarkerPoint.setCarpark(carpark);
				}
				if(titleString.equals("名称")){
					carparkMarkerPoint.setName(contentString);			
								}
				if(titleString.equals("字体颜色")){
					carparkMarkerPoint.setFontColor((int)Double.parseDouble(contentString));
				}
				if(titleString.equals("字体大小")){
					carparkMarkerPoint.setFontSize(Float.valueOf(contentString));
				}
				if(titleString.equals("字体加粗")){
					carparkMarkerPoint.setFontWeight((int)Double.parseDouble(contentString));
				}
				if(titleString.equals("字体倾斜")){
					carparkMarkerPoint.setFontItalic((int)Double.parseDouble(contentString));
				}
				if(titleString.equals("空间经纬度信息")){
					carparkMarkerPoint.setGeometry(contentString);
				}
				if(titleString.equals("备注")){
					carparkMarkerPoint.setMemo(contentString);		
				}
				
				} catch (Exception e) {
					// TODO: handle exception
					messageString+="错误行:"+(i+1)+";列:"+(j+1)+";\n";
				}
			}
			
			carparkMarkerPoints.add(carparkMarkerPoint);
			
		}
		if(messageString.equals("")&&list.size()!=0){
			try {
				bulkAdd(carparkMarkerPoints);
				messageString="成功导入"+(list.size()-1)+"条数据";
				optLogsService.add(new OptLogs(messageString,"停车场标注"));
			} catch (Exception e) {
				// TODO: handle exception
				messageString="导入错误!请检查模板是否正确!";
				e.printStackTrace();
			}
		}
		return messageString;
	}
}
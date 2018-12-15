package com.parkbobo.service;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.parkbobo.dao.CarparkDao;
import com.parkbobo.dao.CarparkModelPolygonDao;
import com.parkbobo.model.Carpark;
import com.parkbobo.model.CarparkBerthPolygon;
import com.parkbobo.model.CarparkModelPolygon;
import com.parkbobo.model.OptLogs;
import com.parkbobo.utils.ExcelUtils;
import com.parkbobo.utils.PageBean;
@Component("carparkModelPolygonService")
public class CarparkModelPolygonService {
	@Resource(name="carparkModelPolygonDaoImpl")
	private CarparkModelPolygonDao carparkModelPolygonDao;
	@Resource(name="carparkDaoImpl")
	private CarparkDao carparkDao;
	private Map<String, Carpark> carparkCache = new HashMap<String, Carpark>();
	@Resource(name="optLogsService")
	private OptLogsService optLogsService;
	public List<CarparkModelPolygon> getByHql(String hql){
		return this.carparkModelPolygonDao.getByHQL(hql);
	}

	public PageBean<CarparkModelPolygon> page(String hqlString, int pageSize,
			int page) {
		// TODO Auto-generated method stub
		return carparkModelPolygonDao.pageQuery(hqlString, pageSize, page);
	}

	public CarparkModelPolygon get(Long gid) {
		// TODO Auto-generated method stub
		return carparkModelPolygonDao.get(gid);
	}

	public void update(CarparkModelPolygon carparkModelPolygon) {
		// TODO Auto-generated method stub
		List<CarparkModelPolygon> carparkModelPolygons = new ArrayList<CarparkModelPolygon>();
		List<String> sqls = new ArrayList<String>(); 
		String updatesql = " update lq_carpark_model_polygon set carparkid=:carparkid"
		+",floorid=:floorid,name=:name,color=:color,bordercolor=:bordercolor," +
				"click_color=:clickColor,click_bordercolor=:clickBordercolor,font_color=:fontColor," +
				"font_size=:fontSize,font_weight=:fontWeight,font_italic=:fontItalic,width=:width," +
				"height=:height,memo=:memo";
		if(!carparkModelPolygon.getGeometry().trim().equals("")){
			updatesql += ",geom=st_astext('"+carparkModelPolygon.getGeometry().trim()+"')";
		}
			updatesql += " where gid=:gid";
			sqls.add(updatesql);
			carparkModelPolygons.add(carparkModelPolygon);
		carparkModelPolygonDao.insertOrUpdateBysql(carparkModelPolygons, sqls);
	}

	public void add(CarparkModelPolygon carparkModelPolygon) {
		List<CarparkModelPolygon> carparkModelPolygons = new ArrayList<CarparkModelPolygon>();
		List<String> sqls = new ArrayList<String>(); 
		String insertsql = " insert into lq_carpark_model_polygon  " +
				"(carparkid,floorid,name,color,bordercolor," +
					"click_color,click_bordercolor,font_color," +
					"font_size,font_weight,font_italic,width," +
					"height,geom,memo) values(:carparkid,:floorid,:name,:color,:bordercolor" +
					",:clickColor,:clickBordercolor,:fontColor,:fontSize,:fontWeight,:fontItalic,:width," +
					":height,st_astext("+(carparkModelPolygon.getGeometry().trim().equals("")?null:("'"+carparkModelPolygon.getGeometry().trim())+"'")+"),:memo)";
		sqls.add(insertsql);
		carparkModelPolygons.add(carparkModelPolygon);
			carparkModelPolygonDao.insertOrUpdateBysql(carparkModelPolygons, sqls);
	}
	public void delete(String ids) {
		String[] strs = ids.split(",");
		Long[] idArr = new Long[strs.length];
		for (int i=0; i< strs.length; i++) {
			idArr[i] = Long.valueOf(strs[i]);
		}
		
		carparkModelPolygonDao.bulkDelete(idArr);
	
	}
	public void bulkAdd(List<CarparkModelPolygon> carparkModelPolygons){
		List<String> sqls = new ArrayList<String>();
		for(CarparkModelPolygon carparkModelPolygon:carparkModelPolygons){
			String insertsql = " insert into lq_carpark_model_polygon  " +
			"(carparkid,floorid,name,color,bordercolor," +
				"click_color,click_bordercolor,font_color," +
				"font_size,font_weight,font_italic,width," +
				"height,geom,memo) values(:carparkid,:floorid,:name,:color,:bordercolor" +
				",:clickColor,:clickBordercolor,:fontColor,:fontSize,:fontWeight,:fontItalic,:width," +
				":height,st_astext("+(carparkModelPolygon.getGeometry().trim().equals("")?null:("'"+carparkModelPolygon.getGeometry().trim())+"'")+"),:memo)";
			sqls.add(insertsql);
		}
		carparkModelPolygonDao.insertOrUpdateBysql(carparkModelPolygons, sqls);
		
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
		List<CarparkModelPolygon> carparkModelPolygons = new ArrayList<CarparkModelPolygon>();
		for(int i = 0;i<list.size();i++){
			if(i==0){
				continue;
			}
			CarparkModelPolygon carparkModelPolygon = new CarparkModelPolygon();
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
					carparkModelPolygon.setCarpark(carpark);
				}
				if(titleString.equals("名称")){
					carparkModelPolygon.setName(contentString);			
								}
				if(titleString.equals("点击后背景色")){
					carparkModelPolygon.setClickColor((int)(Double.parseDouble(contentString)));
				}
				if(titleString.equals("点击后边框色")){
					carparkModelPolygon.setClickBordercolor((int)(Double.parseDouble(contentString)));
				}
				if(titleString.equals("背景色")){
					if(isNull){
						messageString+="背景色不能为空!\t";
						throw new Exception("背景色不能为空!");
					}
					carparkModelPolygon.setColor((int)Double.parseDouble(contentString));
				}
				if(titleString.equals("边框色")){
					if(isNull){
						messageString+="边框色不能为空!\t";
						throw new Exception("边框色不能为空!");
					}

					carparkModelPolygon.setBordercolor((int)(Double.parseDouble(contentString)));
				}
				if(titleString.equals("宽度")){
					carparkModelPolygon.setWidth(Float.valueOf(contentString));
				}
				if(titleString.equals("高度")){
					if(isNull){
						messageString+="高度不能为空!\t";
						throw new Exception("高度不能为空!");
					}
					carparkModelPolygon.setHeight(Float.valueOf(contentString));
				}
				if(titleString.equals("字体颜色")){
					carparkModelPolygon.setFontColor((int)Double.parseDouble(contentString));
				}
				if(titleString.equals("字体大小")){
					carparkModelPolygon.setFontSize(Float.valueOf(contentString));
				}
				if(titleString.equals("字体加粗")){
					carparkModelPolygon.setFontWeight((int)Double.parseDouble(contentString));
				}
				if(titleString.equals("字体倾斜")){
					carparkModelPolygon.setFontItalic((int)Double.parseDouble(contentString));
				}
				if(titleString.equals("空间经纬度信息")){
					carparkModelPolygon.setGeometry(contentString);
				}
				if(titleString.equals("备注")){
					carparkModelPolygon.setMemo(contentString);		
				}
				
				} catch (Exception e) {
					// TODO: handle exception
					messageString+="错误行:"+(i+1)+";列:"+(j+1)+";\n";
				}
			}
			
			carparkModelPolygons.add(carparkModelPolygon);
			
		}
		if(messageString.equals("")&&list.size()!=0){
			try {
				bulkAdd(carparkModelPolygons);
				messageString="成功导入"+(list.size()-1)+"条数据";
				optLogsService.add(new OptLogs(messageString,"停车场高度模型图层"));
			} catch (Exception e) {
				// TODO: handle exception
				messageString="导入错误!请检查模板是否正确!";
				e.printStackTrace();
			}
		}
		return messageString;
	}
}
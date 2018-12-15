package com.parkbobo.service;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.parkbobo.dao.CarparkDao;
import com.parkbobo.dao.CarparkFuzhuPolygonDao;
import com.parkbobo.model.Carpark;
import com.parkbobo.model.CarparkBackgroundPolygon;
import com.parkbobo.model.CarparkCityPolyline;
import com.parkbobo.model.CarparkFuzhuPolygon;
import com.parkbobo.model.CarparkMarkerPoint;
import com.parkbobo.model.OptLogs;
import com.parkbobo.utils.ExcelUtils;
import com.parkbobo.utils.PageBean;
@Component("carparkFuzhuPolygonService")
public class CarparkFuzhuPolygonService {
	@Resource(name="carparkFuzhuPolygonDaoImpl")
	private CarparkFuzhuPolygonDao carparkFuzhuPolygonDao;
	@Resource(name="carparkDaoImpl")
	private CarparkDao carparkDao;
	private Map<String, Carpark> carparkCache = new HashMap<String, Carpark>();
	@Resource(name="optLogsService")
	private OptLogsService optLogsService;
	public List<CarparkFuzhuPolygon> getByHql(String hql){
		return this.carparkFuzhuPolygonDao.getByHQL(hql);
	}

	public PageBean<CarparkFuzhuPolygon> page(String hql, int pageSize, int page) {
		// TODO Auto-generated method stub
		return carparkFuzhuPolygonDao.pageQuery(hql, pageSize, page);
	}

	public CarparkFuzhuPolygon get(Long gid) {
		// TODO Auto-generated method stub
		return carparkFuzhuPolygonDao.get(gid);
	}
	public void update(CarparkFuzhuPolygon carparkFuzhuPolygon) {
		// TODO Auto-generated method stub
//		carparkBerthPolygonDao.merge(carparkBerthPolygon);
		String updatesql = " update lq_carpark_fuzhu_polygon set carparkid=:carparkid,height=:height,"
				+"floorid=:floorid,name=:name,font_color=:fontColor,bordercolor=:bordercolor,color=:color," +
				"font_size=:fontSize,font_weight=:fontWeight,font_italic=:fontItalic,memo=:memo";
		if(!carparkFuzhuPolygon.getGeometry().trim().equals("")){
			updatesql += ",geom=st_astext('"+carparkFuzhuPolygon.getGeometry().trim()+"')";
		}
			updatesql += " where gid=:gid";
			
			List<CarparkFuzhuPolygon> carparkFuzhuPolygons = new ArrayList<CarparkFuzhuPolygon>();
			List<String> sqls = new ArrayList<String>();
			sqls.add(updatesql);
			carparkFuzhuPolygons.add(carparkFuzhuPolygon);
			carparkFuzhuPolygonDao.insertOrUpdateBysql(carparkFuzhuPolygons, sqls);
	}

	public void add(CarparkFuzhuPolygon carparkFuzhuPolygon) {
		String insertsql = " insert into lq_carpark_fuzhu_polygon  " +
				"(carparkid,height,floorid,name,bordercolor,color,font_color," +
					"font_size,font_weight,font_italic," +
					"geom,memo) values(:carparkid,:height,:floorid,:name,:bordercolor,:color" +
					",:fontColor,:fontSize,:fontWeight,:fontItalic," +
					"st_astext("+(carparkFuzhuPolygon.getGeometry().trim().equals("")?null:("'"+carparkFuzhuPolygon.getGeometry().trim())+"'")+"),:memo)";
		List<CarparkFuzhuPolygon> carparkFuzhuPolygons = new ArrayList<CarparkFuzhuPolygon>();
		List<String> sqls = new ArrayList<String>();
		sqls.add(insertsql);
		carparkFuzhuPolygons.add(carparkFuzhuPolygon);
		carparkFuzhuPolygonDao.insertOrUpdateBysql(carparkFuzhuPolygons, sqls);
	}

	public void delete(String ids) {
		String[] strs = ids.split(",");
		Long[] idArr = new Long[strs.length];
		for (int i=0; i< strs.length; i++) {
			idArr[i] = Long.valueOf(strs[i]);
		}
		
		carparkFuzhuPolygonDao.bulkDelete(idArr);
	
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
	public void bulkAdd(List<CarparkFuzhuPolygon> carparkFuzhuPolygons){
		List<String> sqls = new ArrayList<String>();
		for(CarparkFuzhuPolygon carparkFuzhuPolygon:carparkFuzhuPolygons){
			String insertsql = " insert into lq_carpark_fuzhu_polygon  " +
			"(carparkid,height,floorid,name,bordercolor,color,font_color," +
				"font_size,font_weight,font_italic," +
				"geom,memo) values(:carparkid,:height,:floorid,:name,:bordercolor,:color" +
				",:fontColor,:fontSize,:fontWeight,:fontItalic," +
				"st_astext("+(carparkFuzhuPolygon.getGeometry().trim().equals("")?null:("'"+carparkFuzhuPolygon.getGeometry().trim())+"'")+"),:memo)";
			sqls.add(insertsql);
		}
		carparkFuzhuPolygonDao.insertOrUpdateBysql(carparkFuzhuPolygons, sqls);
		
	}
	public  String importExcel(File  file){
		ExcelUtils util = new ExcelUtils();
		List<List<String>> list = util.read(file);
		String messageString = "";
		List<CarparkFuzhuPolygon> carparkFuzhuPolygonss = new ArrayList<CarparkFuzhuPolygon>();
		
		for(int i = 0;i<list.size();i++){
			if(i==0){
				continue;
			}
			CarparkFuzhuPolygon carparkFuzhuPolygons = new CarparkFuzhuPolygon();
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
					carparkFuzhuPolygons.setCarpark(carpark);
				}
				if(titleString.equals("名称")){
					carparkFuzhuPolygons.setName(contentString);			
								}
				if(titleString.equals("背景色")){
					if(isNull){
						messageString+="背景色不能为空!\t";
						throw new Exception("背景色不能为空!");
					}
					carparkFuzhuPolygons.setColor((int)Double.parseDouble(contentString));
				}
				if(titleString.equals("边框色")){
					carparkFuzhuPolygons.setBordercolor((int)(Double.parseDouble(contentString)));
				}
//				if(titleString.equals("点击后背景色")){
//					carparkFuzhuPolygons.set
//				}
//				if(titleString.equals("点击后边框色")){
//					
//				}
//				if(titleString.equals("宽度")){
//					
//				}
				if(titleString.equals("高度")){
					carparkFuzhuPolygons.setHeight(Float.valueOf(contentString));
				}
				if(titleString.equals("字体颜色")){
					carparkFuzhuPolygons.setFontColor((int)Double.parseDouble(contentString));
				}
				if(titleString.equals("字体大小")){
					carparkFuzhuPolygons.setFontSize(Float.valueOf(contentString));
				}
				if(titleString.equals("字体加粗")){
					carparkFuzhuPolygons.setFontWeight((int)Double.parseDouble(contentString));
				}
				if(titleString.equals("字体倾斜")){
					carparkFuzhuPolygons.setFontItalic((int)Double.parseDouble(contentString));
				}
				if(titleString.equals("空间经纬度信息")){
					carparkFuzhuPolygons.setGeometry(contentString);
				}
				if(titleString.equals("备注")){
					carparkFuzhuPolygons.setMemo(contentString);		
				}
				
				} catch (Exception e) {
					// TODO: handle exception
					messageString+="错误行:"+(i+1)+";列:"+(j+1)+";\n";
				}
			}
			
			carparkFuzhuPolygonss.add(carparkFuzhuPolygons);
			
		}
		if(messageString.equals("")&&list.size()!=0){
			try {
				bulkAdd(carparkFuzhuPolygonss);
				messageString="成功导入"+(list.size()-1)+"条数据";
				optLogsService.add(new OptLogs(messageString,"停车场辅助图层"));
			} catch (Exception e) {
				// TODO: handle exception
				messageString="导入错误!请检查模板是否正确!";
				e.printStackTrace();
			}
		}
		return messageString;
	}
	
}
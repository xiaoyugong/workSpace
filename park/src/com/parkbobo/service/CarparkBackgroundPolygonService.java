package com.parkbobo.service;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.parkbobo.dao.CarparkBackgroundPolygonDao;
import com.parkbobo.dao.CarparkDao;
import com.parkbobo.model.Carpark;
import com.parkbobo.model.CarparkBackgroundPolygon;
import com.parkbobo.model.CarparkMarkerPoint;
import com.parkbobo.model.OptLogs;
import com.parkbobo.utils.ExcelUtils;
import com.parkbobo.utils.PageBean;
@Component("carparkBackgroundPolygonService")
public class CarparkBackgroundPolygonService {
	@Resource(name="carparkBackgroundPolygonDaoImpl")
	private CarparkBackgroundPolygonDao carparkBackgroundPolygonDao;
	@Resource(name="carparkDaoImpl")
	private CarparkDao carparkDao;
	private Map<String, Carpark> carparkCache = new HashMap<String, Carpark>();
	@Resource(name="optLogsService")
	private OptLogsService optLogsService;
	public List<CarparkBackgroundPolygon> getByHql(String hql){
		return this.carparkBackgroundPolygonDao.getByHQL(hql);
	}

	public PageBean<CarparkBackgroundPolygon> page(String hql, int pageSize,
			int page) {
		// TODO Auto-generated method stub
		return carparkBackgroundPolygonDao.pageQuery(hql, pageSize, page);
	}

	public CarparkBackgroundPolygon get(Long gid) {
		// TODO Auto-generated method stub
		return carparkBackgroundPolygonDao.get(gid);
	}
	public void update(CarparkBackgroundPolygon carparkBackgroundPolygon) {
		// TODO Auto-generated method stub
//		carparkBerthPolygonDao.merge(carparkBerthPolygon);
		String updatesql = " update lq_carpark_background_polygon set carparkid=:carparkid,color=:color,"
				+"bordercolor=:bordercolor,floorid=:floorid,name=:name,font_color=:fontColor," +
				"font_size=:fontSize,font_weight=:fontWeight,font_italic=:fontItalic,memo=:memo";
		if(!carparkBackgroundPolygon.getGeometry().trim().equals("")){
			updatesql += ",geom=st_astext('"+carparkBackgroundPolygon.getGeometry().trim()+"')";
		}
			updatesql += " where gid=:gid";
			List<CarparkBackgroundPolygon> backgroundPolygons = new ArrayList<CarparkBackgroundPolygon>();
			List<String> sqls = new ArrayList<String>();
			sqls.add(updatesql);
			backgroundPolygons.add(carparkBackgroundPolygon);
			carparkBackgroundPolygonDao.insertOrUpdateBysql(backgroundPolygons, sqls);
	}
	public void bulkAdd(List<CarparkBackgroundPolygon> carparkBackgroundPolygons){
		List<String> sqls = new ArrayList<String>();
		for(CarparkBackgroundPolygon carparkBackgroundPolygon:carparkBackgroundPolygons){
			String insertsql = " insert into lq_carpark_background_polygon  " +
			"(carparkid,floorid,name,bordercolor," +
			"font_color,color," +
			"font_size,font_weight,font_italic," +
			"geom,memo) values(:carparkid,:floorid,:name,:bordercolor" +
			",:fontColor,:color,:fontSize,:fontWeight,:fontItalic," +
			"st_astext("+(carparkBackgroundPolygon.getGeometry().trim().equals("")?null:("'"+carparkBackgroundPolygon.getGeometry().trim())+"'")+"),:memo)";
			sqls.add(insertsql);
		}
		carparkBackgroundPolygonDao.insertOrUpdateBysql(carparkBackgroundPolygons, sqls);
		
	}
	public void add(CarparkBackgroundPolygon carparkBackgroundPolygon) {
		String insertsql = " insert into lq_carpark_background_polygon  " +
				"(carparkid,floorid,name,bordercolor," +
					"font_color,color," +
					"font_size,font_weight,font_italic," +
					"geom,memo) values(:carparkid,:floorid,:name,:bordercolor" +
					",:fontColor,:color,:fontSize,:fontWeight,:fontItalic," +
					"st_astext("+(carparkBackgroundPolygon.getGeometry().trim().equals("")?null:("'"+carparkBackgroundPolygon.getGeometry().trim())+"'")+"),:memo)";
		List<CarparkBackgroundPolygon> backgroundPolygons = new ArrayList<CarparkBackgroundPolygon>();
		backgroundPolygons.add(carparkBackgroundPolygon);
		List<String> sqls = new ArrayList<String>();
		sqls.add(insertsql);
		carparkBackgroundPolygonDao.insertOrUpdateBysql(backgroundPolygons, sqls);
	}

	public void delete(String ids) {
		String[] strs = ids.split(",");
		Long[] idArr = new Long[strs.length];
		for (int i=0; i< strs.length; i++) {
			idArr[i] = Long.valueOf(strs[i]);
		}
		
		carparkBackgroundPolygonDao.bulkDelete(idArr);
	
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
		List<CarparkBackgroundPolygon> carparkBackgroundPolygons = new ArrayList<CarparkBackgroundPolygon>();
		for(int i = 0;i<list.size();i++){
			if(i==0){
				continue;
			}
			CarparkBackgroundPolygon carparkBackgroundPolygon = new CarparkBackgroundPolygon();
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
					carparkBackgroundPolygon.setCarpark(carpark);
				}
				if(titleString.equals("名称")){
					carparkBackgroundPolygon.setName(contentString);			
								}
				if(titleString.equals("背景色")){
					if(isNull){
						messageString+="背景色不能为空\t";
						new Exception("背景色不能为空!");
					}
					carparkBackgroundPolygon.setColor((int)Double.parseDouble(contentString));
				}
				if(titleString.equals("边框色")){
					if(isNull){
						messageString+="边框色不能为空\t";
						new Exception("边框色不能为空!");
					}
					carparkBackgroundPolygon.setBordercolor((int)(Double.parseDouble(contentString)));
				}
//				if(titleString.equals("点击后背景色")){
//					carparkBackgroundPolygon.set
//				}
//				if(titleString.equals("点击后边框色")){
//					
//				}
//				if(titleString.equals("宽度")){
//					
//				}
//				if(titleString.equals("高度")){
//					
//				}
				if(titleString.equals("字体颜色")){
					if(isNull){
						messageString+="字体颜色不能为空\t";
						new Exception("字体颜色不能为空!");
					}
					carparkBackgroundPolygon.setFontColor((int)Double.parseDouble(contentString));
				}
				if(titleString.equals("字体大小")){
					if(isNull){
						messageString+="字体大小不能为空\t";
						new Exception("字体大小不能为空!");
					}
					carparkBackgroundPolygon.setFontSize(Float.valueOf(contentString));
				}
				if(titleString.equals("字体加粗")){
					carparkBackgroundPolygon.setFontWeight((int)Double.parseDouble(contentString));
				}
				if(titleString.equals("字体倾斜")){
					carparkBackgroundPolygon.setFontItalic((int)Double.parseDouble(contentString));
				}
				if(titleString.equals("空间经纬度信息")){
					carparkBackgroundPolygon.setGeometry(contentString);
				}
				if(titleString.equals("备注")){
					carparkBackgroundPolygon.setMemo(contentString);		
				}
				
				} catch (Exception e) {
					// TODO: handle exception
					messageString+="错误行:"+(i+1)+";列:"+(j+1)+";\n";
				}
			}
			
			carparkBackgroundPolygons.add(carparkBackgroundPolygon);
			
		}
		if(messageString.equals("")&&list.size()!=0){
			try {
				bulkAdd(carparkBackgroundPolygons);
				messageString="成功导入"+(list.size()-1)+"条数据";
				optLogsService.add(new OptLogs(messageString,"停车场背景底图"));
			} catch (Exception e) {
				// TODO: handle exception
				messageString="导入错误!请检查模板是否正确!";
				e.printStackTrace();
			}
		}
		return messageString;
	}
	
	public static void main(String[] args) {
		System.out.println((int)Double.parseDouble("12.0"));
	}
}
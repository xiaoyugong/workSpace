package com.parkbobo.service;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.parkbobo.dao.CarparkCityPolylineDao;
import com.parkbobo.dao.CarparkDao;
import com.parkbobo.model.Carpark;
import com.parkbobo.model.CarparkCityPolyline;
import com.parkbobo.model.CarparkFuzhuPolygon;
import com.parkbobo.model.OptLogs;
import com.parkbobo.utils.ExcelUtils;
import com.parkbobo.utils.PageBean;
@Component("carparkCityPolylineService")
public class CarparkCityPolylineService {
	@Resource(name="carparkCityPolylineDaoImpl")
	private CarparkCityPolylineDao carparkCityPolylineDao;

	@Resource(name="carparkDaoImpl")
	private CarparkDao carparkDao;
	@Resource(name="optLogsService")
	private OptLogsService optLogsService;
	private Map<String, Carpark> carparkCache = new HashMap<String, Carpark>();
	public List<CarparkCityPolyline> getByHql(String hql){
		return this.carparkCityPolylineDao.getByHQL(hql);
	}

	public PageBean<CarparkCityPolyline> page(String hql, int pageSize, int page) {
		// TODO Auto-generated method stub
		return carparkCityPolylineDao.pageQuery(hql, pageSize, page);
	}

	public CarparkCityPolyline get(Long carparkid) {
		// TODO Auto-generated method stub
		return carparkCityPolylineDao.get(carparkid);
	}
	public void update(CarparkCityPolyline carparkCityPolyline) {
		// TODO Auto-generated method stub
		List<CarparkCityPolyline> carparkCityPolylines = new ArrayList<CarparkCityPolyline>();
		List<String> sqls = new ArrayList<String>();
		String updatesql = " update lq_carpark_city_polyline set carparkid=:carparkid,heigh=:height,width=:width,"
				+"name=:name,font_color=:fontColor,bordercolor=:bordercolor,color=:color," +
				"font_size=:fontSize,font_weight=:fontWeight,font_italic=:fontItalic,memo=:memo";
		if(!carparkCityPolyline.getGeometry().trim().equals("")){
			updatesql += ",geom=st_astext('"+carparkCityPolyline.getGeometry().trim()+"')";
		}
			updatesql += " where gid=:gid";
			sqls.add(updatesql);
			carparkCityPolylines.add(carparkCityPolyline);
			carparkCityPolylineDao.insertOrUpdateBysql(carparkCityPolylines, sqls);
	}

	public void add(CarparkCityPolyline carparkCityPolyline) {
		List<CarparkCityPolyline> carparkCityPolylines = new ArrayList<CarparkCityPolyline>();
		List<String> sqls = new ArrayList<String>();
		String insertsql = " insert into lq_carpark_city_polyline  " +
				"(carparkid,heigh,width,name,bordercolor,color,font_color," +
					"font_size,font_weight,font_italic," +
					"geom,memo) values(:carparkid,:height,:width,:name,:bordercolor,:color" +
					",:fontColor,:fontSize,:fontWeight,:fontItalic," +
					"st_astext("+(carparkCityPolyline.getGeometry().trim().equals("")?null:("'"+carparkCityPolyline.getGeometry().trim())+"'")+"),:memo)";
		sqls.add(insertsql);
		carparkCityPolylines.add(carparkCityPolyline);
		carparkCityPolylineDao.insertOrUpdateBysql(carparkCityPolylines, sqls);
	}
	public void bulkAdd(List<CarparkCityPolyline> carparkCityPolylines){
		List<String> sqls = new ArrayList<String>();
		for(CarparkCityPolyline carparkCityPolyline:carparkCityPolylines){
			String insertsql = " insert into lq_carpark_city_polyline  " +
			"(carparkid,heigh,width,name,bordercolor,color,font_color," +
				"font_size,font_weight,font_italic," +
				"geom,memo) values(:carparkid,:height,:width,:name,:bordercolor,:color" +
				",:fontColor,:fontSize,:fontWeight,:fontItalic," +
				"st_astext("+(carparkCityPolyline.getGeometry().trim().equals("")?null:("'"+carparkCityPolyline.getGeometry().trim())+"'")+"),:memo)";
			sqls.add(insertsql);
		}
		carparkCityPolylineDao.insertOrUpdateBysql(carparkCityPolylines, sqls);
		
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
		List<CarparkCityPolyline> carparkCityPolylines = new ArrayList<CarparkCityPolyline>();
		for(int i = 0;i<list.size();i++){
			if(i==0){
				continue;
			}
			CarparkCityPolyline carparkCityPolyline = new CarparkCityPolyline();
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
					carparkCityPolyline.setCarpark(carpark);
				}
				if(titleString.equals("名称")){
					carparkCityPolyline.setName(contentString);			
								}
				if(titleString.equals("背景色")){
					if(isNull){
						messageString+="背景色不能为空!\t";
						throw new Exception("背景色不能为空!");
					}
					carparkCityPolyline.setColor((int)Double.parseDouble(contentString));
				}
				if(titleString.equals("边框色")){
					carparkCityPolyline.setBordercolor((int)(Double.parseDouble(contentString)));
				}
				if(titleString.equals("宽度")){
					if(isNull){
						messageString+="宽度不能为空!\t";
						throw new Exception("宽度不能为空!");
					}

					carparkCityPolyline.setWidth(Float.valueOf(contentString));
				}
				if(titleString.equals("高度")){
					carparkCityPolyline.setHeigh(Float.valueOf(contentString));
				}
				if(titleString.equals("字体颜色")){
					carparkCityPolyline.setFontColor((int)Double.parseDouble(contentString));
				}
				if(titleString.equals("字体大小")){
					carparkCityPolyline.setFontSize(Float.valueOf(contentString));
				}
				if(titleString.equals("字体加粗")){
					carparkCityPolyline.setFontWeight((int)Double.parseDouble(contentString));
				}
				if(titleString.equals("字体倾斜")){
					carparkCityPolyline.setFontItalic((int)Double.parseDouble(contentString));
				}
				if(titleString.equals("空间经纬度信息")){
					carparkCityPolyline.setGeometry(contentString);
				}
				if(titleString.equals("备注")){
					carparkCityPolyline.setMemo(contentString);		
				}
				
				} catch (Exception e) {
					// TODO: handle exception
					messageString+="错误行:"+(i+1)+";列:"+(j+1)+";\n";
				}
			}
			
			carparkCityPolylines.add(carparkCityPolyline);
			
		}
		if(messageString.equals("")&&list.size()!=0){
			try {
				bulkAdd(carparkCityPolylines);
				messageString="成功导入"+(list.size()-1)+"条数据";
				optLogsService.add(new OptLogs(messageString,"停车场外部道路"));
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
		
		carparkCityPolylineDao.bulkDelete(idArr);
	
	}
}
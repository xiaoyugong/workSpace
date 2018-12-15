package com.parkbobo.service;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.parkbobo.dao.CarparkDao;
import com.parkbobo.dao.CarparkShopPolygonDao;
import com.parkbobo.model.Carpark;
import com.parkbobo.model.CarparkFuzhuPolygon;
import com.parkbobo.model.CarparkShopPolygon;
import com.parkbobo.model.OptLogs;
import com.parkbobo.utils.ExcelUtils;
import com.parkbobo.utils.PageBean;
@Component("carparkShopPolygonService")
public class CarparkShopPolygonService {
	@Resource(name="carparkShopPolygonDaoImpl")
	private CarparkShopPolygonDao carparkShopPolygonDao;
	@Resource(name="carparkDaoImpl")
	private CarparkDao carparkDao;
	private Map<String, Carpark> carparkCache = new HashMap<String, Carpark>();
	@Resource(name="optLogsService")
	private OptLogsService optLogsService;
	public List<CarparkShopPolygon> getByHql(String hql){
		return this.carparkShopPolygonDao.getByHQL(hql);
	}


	public PageBean<CarparkShopPolygon> page(String hql, int pageSize, int page) {
		// TODO Auto-generated method stub
		return carparkShopPolygonDao.pageQuery(hql, pageSize, page);
	}


	public CarparkShopPolygon get(Long gid) {
		// TODO Auto-generated method stub
		return carparkShopPolygonDao.get(gid);
	}
	public void update(CarparkShopPolygon carparkShopPolygon) {
		// TODO Auto-generated method stub
		List<String> sqls = new ArrayList<String>();
		List<CarparkShopPolygon> carparkShopPolygons = new ArrayList<CarparkShopPolygon>();
		String updatesql = " update lq_carpark_shop_polygon set carparkid=:carparkid"
			+",floorid=:floorid,name=:name,color=:color,bordercolor=:bordercolor," +
					"click_color=:clickColor,click_bordercolor=:clickBordercolor,font_color=:fontColor," +
					"font_size=:fontSize,font_weight=:fontWeight,font_italic=:fontItalic,width=:width," +
					"height=:height,memo=:memo";
		if(!carparkShopPolygon.getGeometry().trim().equals("")){
			updatesql += ",geom=st_astext('"+carparkShopPolygon.getGeometry().trim()+"')";
		}
			updatesql += " where gid=:gid";
			carparkShopPolygons.add(carparkShopPolygon);
			sqls.add(updatesql);
			carparkShopPolygonDao.insertOrUpdateBysql(carparkShopPolygons, sqls);
	}

	public void add(CarparkShopPolygon carparkShopPolygon) {
		List<String> sqls = new ArrayList<String>();
		List<CarparkShopPolygon> carparkShopPolygons = new ArrayList<CarparkShopPolygon>();
		String insertsql = " insert into lq_carpark_shop_polygon  " +
		"(carparkid,floorid,name,color,bordercolor," +
			"click_color,click_bordercolor,font_color," +
			"font_size,font_weight,font_italic,width," +
			"height,geom,memo) values(:carparkid,:floorid,:name,:color,:bordercolor" +
			",:clickColor,:clickBordercolor,:fontColor,:fontSize,:fontWeight,:fontItalic,:width," +
			":height,st_astext("+(carparkShopPolygon.getGeometry().trim().equals("")?null:("'"+carparkShopPolygon.getGeometry().trim())+"'")+"),:memo)";
		carparkShopPolygons.add(carparkShopPolygon);
		sqls.add(insertsql);
		carparkShopPolygonDao.insertOrUpdateBysql(carparkShopPolygons, sqls);
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
	public void bulkAdd(List<CarparkShopPolygon> carparkShopPolygons){
		List<String> sqls = new ArrayList<String>();
		for(CarparkShopPolygon carparkShopPolygon:carparkShopPolygons){
			String insertsql = " insert into lq_carpark_shop_polygon  " +
			"(carparkid,floorid,name,color,bordercolor," +
				"click_color,click_bordercolor,font_color," +
				"font_size,font_weight,font_italic,width," +
				"height,geom,memo) values(:carparkid,:floorid,:name,:color,:bordercolor" +
				",:clickColor,:clickBordercolor,:fontColor,:fontSize,:fontWeight,:fontItalic,:width," +
				":height,st_astext("+(carparkShopPolygon.getGeometry().trim().equals("")?null:("'"+carparkShopPolygon.getGeometry().trim())+"'")+"),:memo)";
			sqls.add(insertsql);
		}
		carparkShopPolygonDao.insertOrUpdateBysql(carparkShopPolygons, sqls);
		
	}
	public  String importExcel(File  file){
		ExcelUtils util = new ExcelUtils();
		List<List<String>> list = util.read(file);
		String messageString = "";
		List<CarparkShopPolygon> carparkShopPolygons = new ArrayList<CarparkShopPolygon>();
		for(int i = 0;i<list.size();i++){
			if(i==0){
				continue;
			}
			CarparkShopPolygon carparkShopPolygon = new CarparkShopPolygon();
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
						carparkShopPolygon.setCarpark(carpark);
					}
					if(titleString.equals("名称")){
						carparkShopPolygon.setName(contentString);			
									}
					if(titleString.equals("背景色")){
						if(isNull){
							messageString+="背景色不能为空!\t";
							throw new Exception("背景色不能为空");
						}
						carparkShopPolygon.setColor((int)Double.parseDouble(contentString));
					}
					if(titleString.equals("边框色")){
						if(isNull){
							messageString+="边框色不能为空!\t";
							throw new Exception("边框色不能为空");
						}
						carparkShopPolygon.setBordercolor((int)(Double.parseDouble(contentString)));
					}
					if(titleString.equals("点击后背景色")){
						if(isNull){
							messageString+="点击后背景色不能为空!\t";
							throw new Exception("点击后背景色");
						}
						carparkShopPolygon.setClickColor((int)Double.parseDouble(contentString));
					}
					if(titleString.equals("点击后边框色")){
						if(isNull){
							messageString+="点击后边框色不能为空!\t";
							throw new Exception("点击后边框色");
						}
						carparkShopPolygon.setClickBordercolor((int)Double.parseDouble(contentString));
					}
					if(titleString.equals("宽度")){
						carparkShopPolygon.setWidth(Float.valueOf(contentString));
					}
					if(titleString.equals("高度")){
						carparkShopPolygon.setHeight(Float.valueOf(contentString));
					}
					if(titleString.equals("字体颜色")){
						carparkShopPolygon.setFontColor((int)Double.parseDouble(contentString));
					}
					if(titleString.equals("字体大小")){
						carparkShopPolygon.setFontSize(Float.valueOf(contentString));
					}
					if(titleString.equals("字体加粗")){
						carparkShopPolygon.setFontWeight((int)Double.parseDouble(contentString));
					}
					if(titleString.equals("字体倾斜")){
						carparkShopPolygon.setFontItalic((int)Double.parseDouble(contentString));
					}
					if(titleString.equals("空间经纬度信息")){
						carparkShopPolygon.setGeometry(contentString);
					}
					if(titleString.equals("备注")){
						carparkShopPolygon.setMemo(contentString);		
					}
					
				} catch (Exception e) {
					// TODO: handle exception
					messageString+="错误行:"+(i+1)+";列:"+(j+1)+";\n";
				}
			}
			
			carparkShopPolygons.add(carparkShopPolygon);
			
		}
		if(messageString.equals("")&&list.size()!=0){
			try {
				bulkAdd(carparkShopPolygons);
				messageString="成功导入"+(list.size()-1)+"条数据";
				optLogsService.add(new OptLogs(messageString,"停车场商场房间"));
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
		
		carparkShopPolygonDao.bulkDelete(idArr);
	
	}

}
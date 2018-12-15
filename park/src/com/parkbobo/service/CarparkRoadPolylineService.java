package com.parkbobo.service;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.parkbobo.dao.CarparkDao;
import com.parkbobo.dao.CarparkRoadPolylineDao;
import com.parkbobo.model.Carpark;
import com.parkbobo.model.CarparkFuzhuPolygon;
import com.parkbobo.model.CarparkRoadPolyline;
import com.parkbobo.model.CarparkShopPolygon;
import com.parkbobo.model.OptLogs;
import com.parkbobo.utils.ExcelUtils;
import com.parkbobo.utils.PageBean;
@Component("carparkRoadPolylineService")
public class CarparkRoadPolylineService {
	@Resource(name="carparkRoadPolylineDaoImpl")
	private CarparkRoadPolylineDao carparkRoadPolylineDao;
	@Resource(name="carparkDaoImpl")
	private CarparkDao carparkDao;
	private Map<String, Carpark> carparkCache = new HashMap<String, Carpark>();
	@Resource(name="optLogsService")
	private OptLogsService optLogsService;
	public List<CarparkRoadPolyline> getByHql(String hql){
		return this.carparkRoadPolylineDao.getByHQL(hql);
	}

	public PageBean<CarparkRoadPolyline> page(String hqlString, int pageSize,
			int page) {
		// TODO Auto-generated method stub
		return carparkRoadPolylineDao.pageQuery(hqlString, pageSize, page);
	}

	public CarparkRoadPolyline get(Long gid) {
		// TODO Auto-generated method stub
		return carparkRoadPolylineDao.get(gid);
	}

	public void update(CarparkRoadPolyline carparkRoadPolyline) {
		// TODO Auto-generated method stub
		List<String> sqls = new ArrayList<String>();
		List<CarparkRoadPolyline> carparkRoadPolylines = new ArrayList<CarparkRoadPolyline>();
		String updatesql = " update lq_carpark_road_polyline set carparkid=:carparkid,width=:width,"
				+"floorid=:floorid,name=:name,font_color=:fontColor,bordercolor=:bordercolor,color=:color," +
				"font_size=:fontSize,font_weight=:fontWeight,font_italic=:fontItalic,memo=:memo";
		if(!carparkRoadPolyline.getGeometry().trim().equals("")){
			updatesql += ",geom=st_astext('"+carparkRoadPolyline.getGeometry().trim()+"')";
		}
			updatesql += " where gid=:gid";
			carparkRoadPolylines.add(carparkRoadPolyline);
			sqls.add(updatesql);
			carparkRoadPolylineDao.insertOrUpdateBysql(carparkRoadPolylines, sqls);
	}

	public void add(CarparkRoadPolyline carparkRoadPolyline) {
		List<String> sqls = new ArrayList<String>();
		List<CarparkRoadPolyline> carparkRoadPolylines = new ArrayList<CarparkRoadPolyline>();
		String insertsql = " insert into lq_carpark_road_polyline  " +
				"(carparkid,width,floorid,name,bordercolor,color,font_color," +
					"font_size,font_weight,font_italic," +
					"geom,memo) values(:carparkid,:width,:floorid,:name,:bordercolor,:color" +
					",:fontColor,:fontSize,:fontWeight,:fontItalic," +
					"st_astext("+(carparkRoadPolyline.getGeometry().trim().equals("")?null:("'"+carparkRoadPolyline.getGeometry().trim())+"'")+"),:memo)";
		carparkRoadPolylines.add(carparkRoadPolyline);
		sqls.add(insertsql);
		carparkRoadPolylineDao.insertOrUpdateBysql(carparkRoadPolylines, sqls);
	}
	public void delete(String ids) {
		String[] strs = ids.split(",");
		Long[] idArr = new Long[strs.length];
		for (int i=0; i< strs.length; i++) {
			idArr[i] = Long.valueOf(strs[i]);
		}
		
		carparkRoadPolylineDao.bulkDelete(idArr);
	
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
	public void bulkAdd(List<CarparkRoadPolyline> carparkRoadPolylines){
		List<String> sqls = new ArrayList<String>();
		for(CarparkRoadPolyline carparkRoadPolyline:carparkRoadPolylines){
			String insertsql = " insert into lq_carpark_road_polyline  " +
			"(carparkid,width,floorid,name,bordercolor,color,font_color," +
				"font_size,font_weight,font_italic," +
				"geom,memo) values(:carparkid,:width,:floorid,:name,:bordercolor,:color" +
				",:fontColor,:fontSize,:fontWeight,:fontItalic," +
				"st_astext("+(carparkRoadPolyline.getGeometry().trim().equals("")?null:("'"+carparkRoadPolyline.getGeometry().trim())+"'")+"),:memo)";
			sqls.add(insertsql);
		}
		carparkRoadPolylineDao.insertOrUpdateBysql(carparkRoadPolylines, sqls);
		
	}
	public  String importExcel(File  file){
		ExcelUtils util = new ExcelUtils();
		List<List<String>> list = util.read(file);
		String messageString = "";
		List<CarparkRoadPolyline> carparkRoadPolylines = new ArrayList<CarparkRoadPolyline>();
		for(int i = 0;i<list.size();i++){
			if(i==0){
				continue;
			}
			CarparkRoadPolyline carparkRoadPolyline = new CarparkRoadPolyline();
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
					carparkRoadPolyline.setCarpark(carpark);
				}
				if(titleString.equals("名称")){
					carparkRoadPolyline.setName(contentString);			
								}
				if(titleString.equals("背景色")){
					if(isNull){
						messageString+="背景色不能为空!\t";
						throw new Exception("背景色不能为空");
					}
					carparkRoadPolyline.setColor((int)Double.parseDouble(contentString));
				}
				if(titleString.equals("边框色")){
					carparkRoadPolyline.setBordercolor((int)(Double.parseDouble(contentString)));
				}
				if(titleString.equals("宽度")){
					if(isNull){
						messageString+="宽度不能为空!\t";
						throw new Exception("点击后边框色不能为空");
					}

					carparkRoadPolyline.setWidth(Float.valueOf(contentString));
				}
				if(titleString.equals("字体颜色")){
					carparkRoadPolyline.setFontColor((int)Double.parseDouble(contentString));
				}
				if(titleString.equals("字体大小")){
					carparkRoadPolyline.setFontSize(Float.valueOf(contentString));
				}
				if(titleString.equals("字体加粗")){
					carparkRoadPolyline.setFontWeight((int)Double.parseDouble(contentString));
				}
				if(titleString.equals("字体倾斜")){
					carparkRoadPolyline.setFontItalic((int)Double.parseDouble(contentString));
				}
				if(titleString.equals("空间经纬度信息")){
					carparkRoadPolyline.setGeometry(contentString);
				}
				if(titleString.equals("备注")){
					carparkRoadPolyline.setMemo(contentString);		
				}
				
				} catch (Exception e) {
					// TODO: handle exception
					messageString+="错误行:"+(i+1)+";列:"+(j+1)+";\n";
				}
			}
			
			carparkRoadPolylines.add(carparkRoadPolyline);
			
		}
		if(messageString.equals("")&&list.size()!=0){
			try {
				bulkAdd(carparkRoadPolylines);
				messageString="成功导入"+(list.size()-1)+"条数据";
				optLogsService.add(new OptLogs(messageString,"停车场内部道路"));
			} catch (Exception e) {
				// TODO: handle exception
				messageString="导入错误!请检查模板是否正确!";
				e.printStackTrace();
			}
		}
		return messageString;
	}
}
package com.parkbobo.service;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.parkbobo.dao.CarparkDao;
import com.parkbobo.dao.CarparkNavigationPolylineDao;
import com.parkbobo.model.Carpark;
import com.parkbobo.model.CarparkNavigationPolyline;
import com.parkbobo.model.CarparkRoadPolyline;
import com.parkbobo.model.OptLogs;
import com.parkbobo.utils.ExcelUtils;
import com.parkbobo.utils.PageBean;
@Component("carparkNavigationPolylineService")
public class CarparkNavigationPolylineService {
	@Resource(name="carparkNavigationPolylineDaoImpl")
	private CarparkNavigationPolylineDao carparkNavigationPolylineDao;
	@Resource(name="carparkDaoImpl")
	private CarparkDao carparkDao;
	private Map<String, Carpark> carparkCache = new HashMap<String, Carpark>();
	@Resource(name="optLogsService")
	private OptLogsService optLogsService;
	public List<CarparkNavigationPolyline> getByHql(String hql){
		return this.carparkNavigationPolylineDao.getByHQL(hql);
	}

	public PageBean<CarparkNavigationPolyline> page(String hqlString,
			int pageSize, int page) {
		// TODO Auto-generated method stub
		return carparkNavigationPolylineDao.pageQuery(hqlString, pageSize, page);
	}

	public void update(CarparkNavigationPolyline carparkNavigationPolyline) {
		// TODO Auto-generated method stub
		List<CarparkNavigationPolyline> carparkNavigationPolylines = new ArrayList<CarparkNavigationPolyline>();
		List<String> sqls = new ArrayList<String>();
		String updatesql = " update lq_carpark_navigation_polyline set carparkid=:carparkid,road_type=:roadType,direction=:direction,"
				+"floorid=:floorid,name=:name,font_color=:fontColor,bordercolor=:bordercolor,color=:color," +
				"font_size=:fontSize,font_weight=:fontWeight,font_italic=:fontItalic,memo=:memo";
		if(!carparkNavigationPolyline.getGeometry().trim().equals("")){
			updatesql += ",geom=st_astext('"+carparkNavigationPolyline.getGeometry().trim()+"')";
		}
			updatesql += " where gid=:gid";
			carparkNavigationPolylines.add(carparkNavigationPolyline);
			sqls.add(updatesql);
			carparkNavigationPolylineDao.insertOrUpdateBysql(carparkNavigationPolylines, sqls);
	}

	public void add(CarparkNavigationPolyline carparkNavigationPolyline) {
		List<CarparkNavigationPolyline> carparkNavigationPolylines = new ArrayList<CarparkNavigationPolyline>();
		List<String> sqls = new ArrayList<String>();
		String insertsql = " insert into lq_carpark_navigation_polyline  " +
				"(carparkid,road_type,direction,floorid,name,bordercolor,color,font_color," +
					"font_size,font_weight,font_italic," +
					"geom,memo) values(:carparkid,:roadType,:direction,:floorid,:name,:bordercolor,:color" +
					",:fontColor,:fontSize,:fontWeight,:fontItalic," +
					"st_astext("+(carparkNavigationPolyline.getGeometry().trim().equals("")?null:("'"+carparkNavigationPolyline.getGeometry().trim())+"'")+"),:memo)";
		carparkNavigationPolylines.add(carparkNavigationPolyline);
		sqls.add(insertsql);
		carparkNavigationPolylineDao.insertOrUpdateBysql(carparkNavigationPolylines, sqls);
	}
	public void bulkAdd(List<CarparkNavigationPolyline> carparkNavigationPolylines){
		List<String> sqls = new ArrayList<String>();
		for(CarparkNavigationPolyline carparkNavigationPolyline:carparkNavigationPolylines){
			String insertsql = " insert into lq_carpark_navigation_polyline  " +
			"(carparkid,road_type,direction,floorid,name,bordercolor,color,font_color," +
				"font_size,font_weight,font_italic," +
				"geom,memo) values(:carparkid,:roadType,:direction,:floorid,:name,:bordercolor,:color" +
				",:fontColor,:fontSize,:fontWeight,:fontItalic," +
				"st_astext("+(carparkNavigationPolyline.getGeometry().trim().equals("")?null:("'"+carparkNavigationPolyline.getGeometry().trim())+"'")+"),:memo)";
			sqls.add(insertsql);
		}
		carparkNavigationPolylineDao.insertOrUpdateBysql(carparkNavigationPolylines, sqls);
		
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
		List<CarparkNavigationPolyline> carparkNavigationPolylines = new ArrayList<CarparkNavigationPolyline>();
		for(int i = 0;i<list.size();i++){
			if(i==0){
				continue;
			}
			CarparkNavigationPolyline carparkNavigationPolyline = new CarparkNavigationPolyline();
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
					carparkNavigationPolyline.setCarpark(carpark);
				}
				if(titleString.equals("名称")){
					carparkNavigationPolyline.setName(contentString);			
					}
				if(titleString.equals("导航类型")){
					Integer typeShort = getType(contentString);
					if(typeShort==null){
						messageString+="导航类型【"+contentString+"】只能为：'人车'，'人'，'车'\t";
						throw new Exception("导航类型有误");
					}
					carparkNavigationPolyline.setRoadType(typeShort);			
					}
				if(titleString.equals("背景色")){
					if(isNull){
						messageString+="背景色不能为空!\t";
						throw new Exception("背景色不能为空!");
					}
					carparkNavigationPolyline.setColor((int)Double.parseDouble(contentString));
				}
				if(titleString.equals("边框色")){
					carparkNavigationPolyline.setBordercolor((int)(Double.parseDouble(contentString)));
				}
				if(titleString.equals("字体颜色")){
					carparkNavigationPolyline.setFontColor((int)Double.parseDouble(contentString));
				}
				if(titleString.equals("字体大小")){
					carparkNavigationPolyline.setFontSize(Float.valueOf(contentString));
				}
				if(titleString.equals("字体加粗")){
					carparkNavigationPolyline.setFontWeight((int)Double.parseDouble(contentString));
				}
				if(titleString.equals("字体倾斜")){
					carparkNavigationPolyline.setFontItalic((int)Double.parseDouble(contentString));
				}
				if(titleString.equals("空间经纬度信息")){
					carparkNavigationPolyline.setGeometry(contentString);
				}
				if(titleString.equals("备注")){
					carparkNavigationPolyline.setMemo(contentString);		
				}
				
				} catch (Exception e) {
					// TODO: handle exception
					messageString+="错误行:"+(i+1)+";列:"+(j+1)+";\n";
				}
			}
			
			carparkNavigationPolylines.add(carparkNavigationPolyline);
			
		}
		if(messageString.equals("")&&list.size()!=0){
			try {
				bulkAdd(carparkNavigationPolylines);
				messageString="成功导入"+(list.size()-1)+"条数据";
				optLogsService.add(new OptLogs(messageString,"停车场导航路线"));
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
		
		carparkNavigationPolylineDao.bulkDelete(idArr);
	
	}
	public Integer getType(String key) {
		Map<String,Integer> roadMap = new HashMap<String, Integer>();
		roadMap.put("人车", 0);
		roadMap.put("人", 1);
		roadMap.put("车", 2);
		return roadMap.get(key);
	}
	public CarparkNavigationPolyline get(Long gid) {
		// TODO Auto-generated method stub
		return carparkNavigationPolylineDao.get(gid);
	}
}
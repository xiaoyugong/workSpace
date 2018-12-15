package com.parkbobo.service;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.parkbobo.dao.BerthShareDao;
import com.parkbobo.dao.CarparkBerthPolygonDao;
import com.parkbobo.dao.CarparkDao;
import com.parkbobo.model.BerthShare;
import com.parkbobo.model.Carpark;
import com.parkbobo.model.CarparkBerthPolygon;
import com.parkbobo.model.CarparkFuzhuPolygon;
import com.parkbobo.model.OptLogs;
import com.parkbobo.utils.ExcelUtils;
import com.parkbobo.utils.PageBean;
@Component("carparkBerthPolygonService")
public class CarparkBerthPolygonService {
	@Resource(name="carparkBerthPolygonDaoImpl")
	private CarparkBerthPolygonDao carparkBerthPolygonDao;
	@Resource(name="berthShareDaoImpl")
	private BerthShareDao berthShareDao;
	@Resource(name="carparkDaoImpl")
	private CarparkDao carparkDao;
	private Map<String, Carpark> carparkCache = new HashMap<String, Carpark>();
	@Resource(name="optLogsService")
	private OptLogsService optLogsService;
	public List<CarparkBerthPolygon> getByHql(String hql){
		return this.carparkBerthPolygonDao.getByHQL(hql);
	}

	public CarparkBerthPolygon getById(Long berthid) {
		return this.carparkBerthPolygonDao.get(berthid);
	}
	/**
	 * 停车场可发布车位列表
	 * @param carparkid
	 * @return
	 */
	public List<CarparkBerthPolygon> getAbleShareBerth(Long carparkid) {
		String berthids = "";
		String hql = "";
		List<BerthShare> berthShares = berthShareDao.getByHQL("from BerthShare as b where b.carparkid = " + carparkid + " and b.berthid is not null");
		for (BerthShare berthShare : berthShares) {
			berthids += berthShare.getBerthid() + ",";
		}
		if(berthids.length() > 0)
		{
			hql = "from CarparkBerthPolygon as c where c.carpark.carparkid = " + carparkid 
				+ " and c.gid not in (" +  berthids.substring(0,berthids.length() - 1)+ ") order by c.floorid, c.name";
		}
		else
		{
			hql = "from CarparkBerthPolygon as c where c.carpark.carparkid = " + carparkid 
				+ " order by c.floorid, c.name";
		}
		
		return carparkBerthPolygonDao.getByHQL(hql);
	}

	public CarparkBerthPolygon get(Long gid) {
		// TODO Auto-generated method stub
		return carparkBerthPolygonDao.get(gid);
	}

	public PageBean<CarparkBerthPolygon> page(String hqlString, int pageSize,
			int page) {
		// TODO Auto-generated method stub
		return carparkBerthPolygonDao.pageQuery(hqlString, pageSize, page);
	}
	
	public void delete(String ids) {
		String[] strs = ids.split(",");
		Long[] idArr = new Long[strs.length];
		for (int i=0; i< strs.length; i++) {
			idArr[i] = Long.valueOf(strs[i]);
		}
		
		carparkBerthPolygonDao.bulkDelete(idArr);
	
	}

	public void update(CarparkBerthPolygon carparkBerthPolygon) {
		// TODO Auto-generated method stub
//		carparkBerthPolygonDao.merge(carparkBerthPolygon);
		String updatesql = " update lq_carpark_berth_polygon set carparkid=:carparkid"
		+",floorid=:floorid,name=:name,color=:color,bordercolor=:bordercolor," +
				"click_color=:clickColor,click_bordercolor=:clickBordercolor,font_color=:fontColor," +
				"font_size=:fontSize,font_weight=:fontWeight,font_italic=:fontItalic,width=:width," +
				"height=:height,memo=:memo";
		if(!carparkBerthPolygon.getGeometry().trim().equals("")){
			updatesql += ",geom=st_astext('"+carparkBerthPolygon.getGeometry().trim()+"')";
		}
			updatesql += " where gid=:gid";
			List<CarparkBerthPolygon> carparkBerthPolygons = new ArrayList<CarparkBerthPolygon>();
			carparkBerthPolygons.add(carparkBerthPolygon);
			List<String> sqls = new ArrayList<String>();
			sqls.add(updatesql);
			carparkBerthPolygonDao.insertOrUpdateBysql(carparkBerthPolygons, sqls);
	}

	public void add(CarparkBerthPolygon carparkBerthPolygon) {
		String insertsql = " insert into lq_carpark_berth_polygon  " +
				"(carparkid,floorid,name,color,bordercolor," +
					"click_color,click_bordercolor,font_color," +
					"font_size,font_weight,font_italic,width," +
					"height,geom,memo) values(:carparkid,:floorid,:name,:color,:bordercolor" +
					",:clickColor,:clickBordercolor,:fontColor,:fontSize,:fontWeight,:fontItalic,:width," +
					":height,st_astext("+(carparkBerthPolygon.getGeometry().trim().equals("")?null:("'"+carparkBerthPolygon.getGeometry().trim())+"'")+"),:memo)";
		List<CarparkBerthPolygon> carparkBerthPolygons = new ArrayList<CarparkBerthPolygon>();
		carparkBerthPolygons.add(carparkBerthPolygon);
		List<String> sqls = new ArrayList<String>();
		sqls.add(insertsql);
		carparkBerthPolygonDao.insertOrUpdateBysql(carparkBerthPolygons, sqls);
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
	public void bulkAdd(List<CarparkBerthPolygon> carparkBerthPolygons){
		List<String> sqls = new ArrayList<String>();
		for(CarparkBerthPolygon carparkBerthPolygon:carparkBerthPolygons){
			String insertsql = " insert into lq_carpark_berth_polygon  " +
			"(carparkid,floorid,name,color,bordercolor," +
				"click_color,click_bordercolor,font_color," +
				"font_size,font_weight,font_italic,width," +
				"height,geom,memo) values(:carparkid,:floorid,:name,:color,:bordercolor" +
				",:clickColor,:clickBordercolor,:fontColor,:fontSize,:fontWeight,:fontItalic,:width," +
				":height,st_astext("+(carparkBerthPolygon.getGeometry().trim().equals("")?null:("'"+carparkBerthPolygon.getGeometry().trim())+"'")+"),:memo)";
			sqls.add(insertsql);
		}
		carparkBerthPolygonDao.insertOrUpdateBysql(carparkBerthPolygons, sqls);
		
	}
	public  String importExcel(File  file){
		ExcelUtils util = new ExcelUtils();
		List<List<String>> list = util.read(file);
		String messageString = "";
		List<CarparkBerthPolygon> carparkBerthPolygons = new ArrayList<CarparkBerthPolygon>();
		for(int i = 0;i<list.size();i++){
			if(i==0){
				continue;
			}
			CarparkBerthPolygon carparkBerthPolygon = new CarparkBerthPolygon();
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
					carparkBerthPolygon.setCarpark(carpark);
				}
				if(titleString.equals("名称")){
					carparkBerthPolygon.setName(contentString);			
								}
				if(titleString.equals("背景色")){
					if(isNull){
						messageString+="背景色不能为空!\t";
						throw new Exception("背景色不能为空!");
					}
					carparkBerthPolygon.setColor((int)Double.parseDouble(contentString));
				}
				if(titleString.equals("边框色")){
					if(isNull){
						messageString+="边框色不能为空!\t";
						throw new Exception("边框色不能为空!");
					}
					carparkBerthPolygon.setBordercolor((int)(Double.parseDouble(contentString)));
				}
				if(titleString.equals("点击后背景色")){
					if(isNull){
						messageString+="点击后背景色不能为空!\t";
						throw new Exception("点击后背景色不能为空!");
					}
					carparkBerthPolygon.setClickColor((int)(Double.parseDouble(contentString)));
				}
				if(titleString.equals("点击后边框色")){
					if(isNull){
						messageString+="点击后边框色不能为空!\t";
						throw new Exception("点击后边框色不能为空!");
					}
					carparkBerthPolygon.setClickBordercolor((int)(Double.parseDouble(contentString)));
				}
				if(titleString.equals("宽度")){
					carparkBerthPolygon.setWidth(Float.valueOf(contentString));
				}
				if(titleString.equals("高度")){
					carparkBerthPolygon.setHeight(Float.valueOf(contentString));
				}
				if(titleString.equals("字体颜色")){
					carparkBerthPolygon.setFontColor((int)Double.parseDouble(contentString));
				}
				if(titleString.equals("字体大小")){
					carparkBerthPolygon.setFontSize(Float.valueOf(contentString));
				}
				if(titleString.equals("字体加粗")){
					carparkBerthPolygon.setFontWeight((int)Double.parseDouble(contentString));
				}
				if(titleString.equals("字体倾斜")){
					carparkBerthPolygon.setFontItalic((int)Double.parseDouble(contentString));
				}
				if(titleString.equals("空间经纬度信息")){
					carparkBerthPolygon.setGeometry(contentString);
				}
				if(titleString.equals("备注")){
					carparkBerthPolygon.setMemo(contentString);		
				}
				
				} catch (Exception e) {
					// TODO: handle exception
					messageString+="错误行:"+(i+1)+";列:"+(j+1)+";\n";
				}
			}
			
			carparkBerthPolygons.add(carparkBerthPolygon);
			
		}
		if(messageString.equals("")&&list.size()!=0){
			try {
				bulkAdd(carparkBerthPolygons);
				messageString="成功导入"+(list.size()-1)+"条数据";
				optLogsService.add(new OptLogs(messageString,"停车场车位"));
			} catch (Exception e) {
				// TODO: handle exception
				messageString="导入错误!请检查模板是否正确!";
				e.printStackTrace();
			}
		}
		return messageString;
	}
	
}
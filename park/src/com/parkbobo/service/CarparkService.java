package com.parkbobo.service;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import com.parkbobo.dao.BerthShareDao;
import com.parkbobo.dao.CarparkDao;
import com.parkbobo.model.Carpark;
import com.parkbobo.model.CarparkBackgroundPolygon;
import com.parkbobo.model.CarparkBerthPolygon;
import com.parkbobo.model.CarparkCityPolyline;
import com.parkbobo.model.CarparkEntrancePoint;
import com.parkbobo.model.CarparkFloor;
import com.parkbobo.model.CarparkFuzhuPolygon;
import com.parkbobo.model.CarparkMarkerPoint;
import com.parkbobo.model.CarparkModelPolygon;
import com.parkbobo.model.CarparkNavigationPolyline;
import com.parkbobo.model.CarparkRoadPolyline;
import com.parkbobo.model.CarparkShopPolygon;
import com.parkbobo.utils.DateUtils;
import com.parkbobo.utils.PageBean;
@Component("carparkService")
public class CarparkService {
	@Resource(name="carparkDaoImpl")
	private CarparkDao carparkDao;
	@Resource(name="berthShareDaoImpl")
	private BerthShareDao berthShareDao;
	public List<Carpark> getByHql(String hql){
		return this.carparkDao.getByHQL(hql);
	}

	public Carpark getById(Long carparkid) {
		return this.carparkDao.get(carparkid);
	}

	public void add(Carpark carpark) {
		this.carparkDao.merge(carpark);
	}

	public PageBean<Carpark> loadPage(String hql, int pageSize, int page) {
		return this.carparkDao.pageQuery(hql, pageSize, page);
	}
	/**
	 * 批量删除
	 * @param ids
	 */
	public void bulkDelete(String ids) {
		if(ids.length() > 0)
		{
			String[] strs = ids.split(",");
			Long[] idArr = new Long[strs.length];
			for (int i=0; i< strs.length; i++) {
				idArr[i] = Long.valueOf(strs[i]);
				this.berthShareDao.deleteByHql("delete from BerthShare as b where  b.carparkid = " + Long.valueOf(strs[i]));
			}
			this.carparkDao.bulkDelete(idArr);
		}		
	}
	/**
	 * 批量添加
	 */
	public void bulkAdd(List<List<String>> list){
		StringBuffer sql = new StringBuffer("insert into lq_carpark(" +
				"carparkid, categoryid, name, picarr, total_berth, province, city, county, " +
				"address, fee_rates, before_mins, before_price, after_mins, after_price, maptype, " +
				"is_allowed, show_level, longitude, latitude, right_top_lon, right_top_lat, " +
				"left_bottom_lon, left_bottom_lat, is_authentication,posttime) values ");
        for (int i = 1; i < list.size(); i++)  
        {  
  
        	List<String> cellList = list.get(i);  
        	sql.append("(" + 
        			cellList.get(0) + "," +  
        			(isEmpty(cellList.get(1))?"null":cellList.get(1)) + ","  +
        			(isEmpty(cellList.get(2))?"null":"'" + cellList.get(2) + "'") + "," +
        			(isEmpty(cellList.get(3))?"null":"'" + cellList.get(3) + "'") + "," +
        			(isEmpty(cellList.get(4))?"null":cellList.get(4)) + "," +
        			(isEmpty(cellList.get(5))?"null":"'" + cellList.get(5) + "'") + "," +
        			(isEmpty(cellList.get(6))?"null":"'" + cellList.get(6) + "'") + "," +
        			(isEmpty(cellList.get(7))?"null":"'" + cellList.get(7) + "'") + "," +
        			(isEmpty(cellList.get(8))?"null":"'" + cellList.get(8) + "'") + "," +
        			(isEmpty(cellList.get(9))?"null":"'" + cellList.get(9) + "'") + "," +
        			(isEmpty(cellList.get(10))?"null":cellList.get(10)) + "," + 
        			(isEmpty(cellList.get(11))?"null":cellList.get(11)) + "," + 
        			(isEmpty(cellList.get(12))?"null":cellList.get(12)) + "," + 
        			(isEmpty(cellList.get(13))?"null":cellList.get(13)) + "," + 
        			(isEmpty(cellList.get(14))?"null":cellList.get(14)) + "," + 
        			(isEmpty(cellList.get(15))?"null":cellList.get(15)) + "," + 
        			(isEmpty(cellList.get(16))?"null":cellList.get(16)) + "," + 
        			(isEmpty(cellList.get(17))?"null":cellList.get(17)) + "," + 
        			(isEmpty(cellList.get(18))?"null":cellList.get(18)) + "," + 
        			(isEmpty(cellList.get(19))?"null":"'" + cellList.get(19) + "'") + "," +
        			(isEmpty(cellList.get(20))?"null":"'" + cellList.get(20) + "'") + "," +
        			(isEmpty(cellList.get(21))?"null":"'" + cellList.get(21) + "'") + "," +
        			(isEmpty(cellList.get(22))?"null":"'" + cellList.get(22) + "'") + "," +
        			"0" +
        			","+System.currentTimeMillis()+"),");
  
        }  
        sql.delete(sql.length()-1, sql.length());
        sql.append(";");
        System.out.println(sql);
        this.carparkDao.executeSql(sql.toString());
	}
	private boolean isEmpty(String str)
	{
		if(str != null && !str.equals(""))
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	public List findCarparkByIds(String ids) {
		// TODO Auto-generated method stub
		String[] arr = ids.split(",");
		Long[] newArr = new Long[arr.length];
		for(int i = 0;i<arr.length;i++){
			newArr[i] = Long.valueOf(arr[i]);
		}
		String hql = "from Carpark where carparkid in(:in)";
		return carparkDao.bulkFind(hql, newArr);
	}

	/**
	 * @param ids
	 * @param path  文件保存路径
	 */
	public File outsql(String ids,String path) {
		// TODO Auto-generated method stub
		List<Carpark> carparks = findCarparkByIds(ids);
		StringBuffer sbBuffer = new StringBuffer();
		for(Carpark carpark:carparks){
			//停车场
			
			sbBuffer.append("\r\n");
			sbBuffer.append("INSERT INTO lq_carpark VALUES ("+carpark.getCarparkid()+", "+carpark.getCarparkCategory().getCategoryid()+", '"+carpark.getName()+"'," +
					" '"+carpark.getPicarr()+"', "+carpark.getTotalBerth()+", "+carpark.getEnableBerth()+", '"+carpark.getProvince()+"', '"+carpark.getCity()+"', " +
					"'"+carpark.getCounty()+"', '"+carpark.getAddress()+"', "+colstring(carpark.getOpentime())+", "+colstring(carpark.getClosetime())+", "+colstring(carpark.getFeeRates())+"," +
					" "+carpark.getBeforeMins()+", "+carpark.getBeforePrice()+", "+carpark.getAfterMins()+", "+carpark.getAfterPrice()+"," +
					" "+carpark.getMaptype()+", "+carpark.getParktype()+", "+carpark.getEnstopnum()+", "+carpark.getIsAllowed()+", "+carpark.getShowLevel()+"," +
					" '"+carpark.getLongitude()+"', '"+carpark.getLatitude()+"', '"+carpark.getRightTopLon()+"', '"+carpark.getRightTopLat()+"'," +
					" '"+carpark.getLeftBottomLon()+"', '"+carpark.getLeftBottomLat()+"', "+carpark.getIsAuthentication()+", "+colstring(carpark.getMemo())+"," +
					" "+carpark.getPosttime()+", "+carpark.getUsername()+");");
			for(CarparkBackgroundPolygon carpackb:carpark.getCarparkBackgroundPolygons()){
			//停车场背景底图
				sbBuffer.append("\r\n");
				sbBuffer.append("INSERT INTO lq_carpark_background_polygon VALUES ("+carpackb.getGid()+", "+carpackb.getCarpark().getCarparkid()+", "+carpackb.getFloorid()+"," +
						" '"+carpackb.getName()+"', "+carpackb.getColor()+", "+carpackb.getBordercolor()+", "+carpackb.getFontColor()+", "+carpackb.getFontSize()+", "+carpackb.getFontWeight()+", "+carpackb.getFontItalic()+"," +
						"st_astext("+colstring(carpackb.getGeometry())+"), "+carpackb.getOrderid()+", '"+carpackb.getMemo()+"');");
			}
			for(CarparkBerthPolygon berth:carpark.getCarparkBerthPolygons()){
				//停车场车位
				sbBuffer.append("\r\n");
				sbBuffer.append("INSERT INTO lq_carpark_berth_polygon VALUES ("+berth.getGid()+", "+berth.getCarpark().getCarparkid()+", "+berth.getFloorid()+", '"+berth.getName()+"', "+berth.getColor()+", "+berth.getBordercolor()+", " +
						""+berth.getClickColor()+", "+berth.getClickBordercolor()+", "+berth.getFontColor()+", "+berth.getFontSize()+", "+berth.getFontWeight()+", "+berth.getFontItalic()+", "+berth.getWidth()+", "+berth.getHeight()+"," +
								"st_astext('"+berth.getGeometry().trim()+"'), '"+berth.getMemo()+"');");
			}
			for(CarparkCityPolyline city:carpark.getCarparkCityPolylines()){
				//停车场外部道路
				sbBuffer.append("\r\n");
				sbBuffer.append("INSERT INTO lq_carpark_city_polyline VALUES ("+city.getGid()+", "+city.getCarpark().getCarparkid()+", '"+city.getName()+"', "+city.getColor()+", "+city.getBordercolor()+", "+city.getFontColor()+"," +
						" "+city.getFontSize()+", "+city.getFontWeight()+", "+city.getFontItalic()+", "+city.getWidth()+", "+city.getHeigh()+", st_astext('"+city.getGeometry().trim()+"'), '"+city.getMemo()+"');");
			}
			for(CarparkEntrancePoint entr:carpark.getCarparkEntrancePoints()){
				//停车场出入口
				
				
				
				sbBuffer.append("\r\n");
				System.out.println(entr.getGeometry());
				sbBuffer.append("INSERT INTO lq_carpark_entrance_point VALUES ("+entr.getGid()+", "+entr.getCarpark().getCarparkid()+", "+entr.getFloorid()+", '"+entr.getName()+"', "+entr.getType()+", '"+entr.getGaodeLatitude()+"'," +
						" '"+entr.getGaodeLongitude()+"', st_astext("+colstring(entr.getGeometry())+"), '"+entr.getMemo()+"');");
			}
			
			for(CarparkFloor floor:carpark.getCarparkFloors()){
				//停车场楼层
				sbBuffer.append("\r\n");
				sbBuffer.append("INSERT INTO lq_carpark_floor VALUES ("+floor.getFloorid()+", "+floor.getCarpark().getCarparkid()+", '"+floor.getName()+"', "+floor.getOrderid()+","+floor.getIsDefault()+", '"+floor.getMemo()+"');");
			}
			for(CarparkFuzhuPolygon fuzhu:carpark.getCarparkFuzhuPolygons()){
				//停车场辅助图层
				sbBuffer.append("\r\n");
				sbBuffer.append("INSERT INTO lq_carpark_fuzhu_polygon VALUES ("+fuzhu.getGid()+", "+fuzhu.getCarpark().getCarparkid()+", "+fuzhu.getFloorid()+", '"+fuzhu.getName()+"', "+fuzhu.getColor()+", "+fuzhu.getBordercolor()+"," +
						" "+fuzhu.getFontColor()+", "+fuzhu.getFontSize()+", "+fuzhu.getFontWeight()+", "+fuzhu.getFontItalic()+", "+fuzhu.getHeight()+", st_astext("+colstring(fuzhu.getGeometry())+"), "+fuzhu.getOrderid()+", '"+fuzhu.getMemo()+"');");
			}
			for(CarparkMarkerPoint marker:carpark.getCarparkMarkerPoints()){
				//停车场标注点
				sbBuffer.append("\r\n");
				sbBuffer.append("INSERT INTO lq_carpark_marker_point VALUES ("+marker.getGid()+", "+marker.getCarpark().getCarparkid()+", "+marker.getCarparkMarkerCategory().getCategoryid()+", "+marker.getFloorid()+", '"+marker.getName()+"'," +
						" "+marker.getFontColor()+", "+marker.getFontSize()+", "+marker.getFontWeight()+", "+marker.getFontItalic()+", st_astext("+colstring(marker.getGeometry())+"), "+marker.getMemo()+");");
			}
			for(CarparkModelPolygon model:carpark.getCarparkModelPolygons()){
				//停车场模型图层
				sbBuffer.append("\r\n");
				sbBuffer.append("INSERT INTO lq_carpark_model_polygon VALUES ("+model.getGid()+", "+model.getCarpark().getCarparkid()+", '"+model.getName()+"', "+model.getFloorid()+", "+model.getColor()+"," +
						" "+model.getBordercolor()+", "+model.getClickColor()+", "+model.getClickBordercolor()+", "+model.getFontColor()+", "+model.getFontSize()+", "+model.getFontWeight()+", "+model.getFontItalic()+", "+model.getWidth()+", "+model.getHeight()+"," +
						"st_astext("+colstring(model.getGeometry())+"), '"+model.getMemo()+"');");
			}
			for(CarparkNavigationPolyline navigation:carpark.getCarparkNavigationPolylines()){
				//导航路线
				sbBuffer.append("\r\n");
				sbBuffer.append("INSERT INTO lq_carpark_navigation_polyline VALUES ("+navigation.getGid()+", "+navigation.getCarpark().getCarparkid()+", "+navigation.getFloorid()+", '"+navigation.getName()+"', "+navigation.getColor()+", "+navigation.getBordercolor()+"," +
						" "+navigation.getFontColor()+", "+navigation.getFontSize()+", "+navigation.getFontWeight()+", "+navigation.getFontItalic()+", st_astext("+colstring(navigation.getGeometry())+"), "+navigation.getRoadType()+", "+navigation.getDirection()+", '"+navigation.getMemo()+"');");
			}
			for(CarparkRoadPolyline road:carpark.getCarparkRoadPolylines()){
				//停车场内部道路
				sbBuffer.append("\r\n");
				sbBuffer.append("INSERT INTO lq_carpark_road_polyline VALUES ("+road.getGid()+", "+road.getCarpark().getCarparkid()+", "+road.getFloorid()+", '"+road.getName()+"', "+road.getColor()+", "+road.getBordercolor()+", "+road.getFontColor()+", "+road.getFontSize()+", " +
						""+road.getFontWeight()+", "+road.getFontItalic()+", "+road.getWidth()+", st_astext("+colstring(road.getGeometry())+"), '"+road.getMemo()+"');");
			}
			for(CarparkShopPolygon shop:carpark.getCarparkShopPolygons()){
				//停车场商场房间
				sbBuffer.append("\r\n");
				sbBuffer.append("INSERT INTO lq_carpark_shop_polygon VALUES ("+shop.getGid()+", "+shop.getCarpark().getCarparkid()+", "+shop.getFloorid()+", '"+shop.getName()+"', "+shop.getColor()+", "+shop.getBordercolor()+", "+shop.getClickColor()+"," +
						" "+shop.getClickBordercolor()+", "+shop.getFontColor()+", "+shop.getFontSize()+", "+shop.getFontWeight()+", "+shop.getFontItalic()+", "+shop.getWidth()+", "+shop.getHeight()+", st_astext("+colstring(shop.getGeometry())+"), '"+shop.getMemo()+"');");
			}
			
			
		}
		return  writeFile(sbBuffer.toString(),path);
	}
	
	public String colstring(Object colString){
		if(colString==null){
			colString = "NULL";
		}else {
			colString="'"+colString+"'";
		}
		return (String)colString;
	}
	
	public  File writeFile(String text,String path) {
		File file = new File(path);
		if(!file.isDirectory()){
			file.mkdirs();
		}
		File sqlFile = new File(path+""+DateUtils.getInstance().formatDate("yyyyMMddHHmmss")+".sql");
		if(!sqlFile.exists()){
			try {
				file.createNewFile();
				FileUtils.writeStringToFile(sqlFile, text);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return sqlFile;
	}

	public String exesql(String sql) {
		// TODO Auto-generated method stub
		carparkDao.executeSql(sql);
		return null;
	}
	public static void main(String[] args) {
	}
}
package com.weixin.msg;

import java.util.Date;

public class LocationMsg extends BaseMsg{

	/**
	 * 经度
	 */
	private String location_X;
	/**
	 * 纬度
	 */
	private String Location_Y;
	
	/**
	 * 地图缩放大小
	 */
	private String scale;
	/**
	 * 地理位置信息
	 */
	private String label;
	
	
	
	public LocationMsg(){
		 super.setMsgType("location");
		 super.setCreateTime(new Date().getTime()+"");
	 }
	public String getLocation_X() {
		return location_X;
	}
	public void setLocation_X(String locationX) {
		location_X = locationX;
	}
	public String getLocation_Y() {
		return Location_Y;
	}
	public void setLocation_Y(String locationY) {
		Location_Y = locationY;
	}
	public String getScale() {
		return scale;
	}
	public void setScale(String scale) {
		this.scale = scale;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	
}

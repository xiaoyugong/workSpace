package com.weixin.menu;

public class LocationSelectMenu extends BaseMenu{
	

	private String key;
	public LocationSelectMenu(String name,String key) {
		super(name, "location_select");
		// TODO Auto-generated constructor stub
		this.key = key;
	}

	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}

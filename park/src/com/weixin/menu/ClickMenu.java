package com.weixin.menu;

public class ClickMenu extends BaseMenu{

	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public ClickMenu(String name,String key) {
		super(name,"click");
		this.key = key;
	}
	
	
	
}

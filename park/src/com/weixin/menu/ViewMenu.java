package com.weixin.menu;

public class ViewMenu extends BaseMenu{

	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ViewMenu(String name,String url) {
		super(name,"view");
		this.url = url;
	}
	
	
}

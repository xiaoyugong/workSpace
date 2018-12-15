package com.weixin.menu;

import java.util.HashSet;
import java.util.Set;

public class BaseMenu {

	private String name;
	private String type;
	/**
	 * 子菜单
	 */
	private Set<BaseMenu> subMenus = new HashSet<BaseMenu>();
	
	/**
	 * 父类菜单
	 */
	private BaseMenu parentMenu;
	
	
	public BaseMenu(String name){
		this.name = name;
		
	}
	public BaseMenu(String name, String type) {
		this.name = name;
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Set<BaseMenu> getSubMenus() {
		return subMenus;
	}
	public void setSubMenus(Set<BaseMenu> subMenus) {
		this.subMenus = subMenus;
	}
	public BaseMenu getParentMenu() {
		return parentMenu;
	}
	public void setParentMenu(BaseMenu parentMenu) {
		this.parentMenu = parentMenu;
	}
	
	
	
}

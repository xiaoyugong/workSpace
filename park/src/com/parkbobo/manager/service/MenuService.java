package com.parkbobo.manager.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.parkbobo.manager.dao.MenuDao;
import com.parkbobo.manager.model.Menu;

@Component("menuService")
public class MenuService {
	@Resource(name="menuDaoImpl")
	private MenuDao menuDao;
	
	public List<Menu> getAll() {
		List<Menu> menus = menuDao.getByHQL("from Menu as m where m.menu.menuId is NULL and m.enable = 1 order by m.orderid");
		for (Menu menu : menus) {
			List<Menu> childrenMenu = menuDao.getByHQL("from Menu as m where m.menu.menuId = " + menu.getMenuId() + " and m.enable = 1  order by m.orderid");
			menu.setChildrenMenu(childrenMenu);
		}
		return menus;
	}

	public void bulkAdd(List menus) {
		// TODO Auto-generated method stub
		menuDao.bulksave(menus);
	}

	public Menu getById(Long entityid) {
		// TODO Auto-generated method stub
		return  menuDao.get(entityid);
	}

	public void add(Menu menu) {
		// TODO Auto-generated method stub
		menuDao.add(menu);
	}

}

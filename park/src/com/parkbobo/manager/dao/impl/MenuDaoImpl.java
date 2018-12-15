package com.parkbobo.manager.dao.impl;

import org.springframework.stereotype.Component;

import com.parkbobo.dao.impl.BaseDaoSupport;
import com.parkbobo.manager.dao.MenuDao;
import com.parkbobo.manager.model.Menu;
@Component("menuDaoImpl")
public class MenuDaoImpl extends BaseDaoSupport<Menu> implements MenuDao {

}

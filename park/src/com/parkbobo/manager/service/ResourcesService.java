package com.parkbobo.manager.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.parkbobo.manager.dao.ManagerResourcesDao;
import com.parkbobo.manager.dao.MenuDao;
import com.parkbobo.manager.dao.ResourcesDao;
import com.parkbobo.manager.dao.RoleResourcesDao;
import com.parkbobo.manager.model.ManagerResources;
import com.parkbobo.manager.model.Menu;
import com.parkbobo.manager.model.Resources;
import com.parkbobo.manager.model.RoleResources;
import com.parkbobo.utils.PageBean;

@Component("resourcesService")
public class ResourcesService {
	@Resource(name="resourcesDaoImpl")
	private ResourcesDao resourcesDao;
	@Resource(name="menuDaoImpl")
	private MenuDao menuDao;
	@Resource(name="roleResourcesDaoImpl")
	private RoleResourcesDao roleResourcesDao;
	@Resource(name="managerResourcesDaoImpl")
	private ManagerResourcesDao managerResourcesDao;
	
	public List<Resources> getByProperty(String propertyName,Object value) {
		return resourcesDao.getByProperty(propertyName, value);
	}
	public void add(Resources resources) {
		this.resourcesDao.merge(resources);
	}
	public Resources getById(Long id) {
		return this.resourcesDao.get(id);
	}
	public PageBean<Resources> loadPage(String hql, int pageSize, int page) {
		return this.resourcesDao.pageQuery(hql, pageSize, page);
	}
	
	public void bulkDelete(String ids) {
		if(ids.length() > 0)
		{
			String[] strs = ids.split(",");
			Long[] idArr = new Long[strs.length];
			for (int i=0; i< strs.length; i++) {
				idArr[i] = Long.valueOf(strs[i]);
			}
			resourcesDao.bulkDelete(idArr);
		}
	}
	
	/**
	 * 获取资源权限树
	 * @return
	 */
	public String loadTree(String type) {
		StringBuffer json = new StringBuffer();
		List<Menu> menuList = menuDao.getByHQL("from Menu as m where (m.menutype = '" + type + "' or m.menutype = '2') and m.enable = 1 order by m.orderid");
		json.append("{id:\"m_0\",pId:\"0\",name:\"系统权限\",drag:false, drop:false,open:true},");
		for (Menu menu : menuList) {
			if(menu.getMenu() == null){
				json.append("{id:\"m_" + menu.getMenuId() + "\",pId:\"m_0\",name:\"" + menu.getName() + "\",drag:false, drop:false,open:true},");
			}else{
				json.append("{id:\"m_" + menu.getMenuId() + "\",pId:\"m_" + menu.getMenu().getMenuId() + "\",name:\"" + menu.getName() + "\",drag:false, drop:false,open:true},");
				List<Resources> resourcesList = resourcesDao.getByHQL("from Resources as rs where rs.menu.menuId = " 
						+ menu.getMenuId() + " and rs.enable = 1 and (rs.resourcetype = '2' or rs.resourcetype = '" + type + "') order by rs.orderid");
				for (Resources resources : resourcesList) {
					json.append("{id:\"" + resources.getResourcesId() + "\",pId:\"m_" + menu.getMenuId() + "\",name:\"" + resources.getName() + "\",drag:false, drop:false,open:true},");
				}
			}
		}
		return json.toString();
	}
	/**
	 * 通过角色ID获取资源权限树
	 * @param id
	 * @return
	 */
	public String loadRoleTree(Long id) {
		Map<Long,Long> idMap = new HashMap<Long, Long>();
		List<RoleResources> roleResourcesList = roleResourcesDao.getByHQL("from RoleResources as r where r.id.roleId = " + id);
		for (RoleResources roleResources : roleResourcesList) {
			idMap.put(roleResources.getResources().getResourcesId(), roleResources.getResources().getResourcesId());
		}
		StringBuffer json = new StringBuffer();
		List<Menu> menuList = menuDao.getByHQL("from Menu as m where (m.menutype = '0' or m.menutype = '2') and m.enable = 1 order by m.orderid");
		json.append("{id:\"m_0\",pId:\"0\",name:\"系统权限\",drag:false, drop:false,open:true},");
		for (Menu menu : menuList) {
			if(menu.getMenu() == null){
				json.append("{id:\"m_" + menu.getMenuId() + "\",pId:\"m_0\",name:\"" + menu.getName() + "\",drag:false, drop:false,open:true},");
			}else{
				json.append("{id:\"m_" + menu.getMenuId() + "\",pId:\"m_" + menu.getMenu().getMenuId() + "\",name:\"" + menu.getName() + "\",drag:false, drop:false,open:true},");
				List<Resources> resourcesList = resourcesDao.getByHQL("from Resources as rs where rs.menu.menuId = " 
						+ menu.getMenuId() + " and rs.enable = 1 and (rs.resourcetype = '2' or rs.resourcetype = '0') order by rs.orderid");
				for (Resources resources : resourcesList) {
					if(idMap.containsKey(resources.getResourcesId())){
						json.append("{id:\"" + resources.getResourcesId() + "\",pId:\"m_" + menu.getMenuId() + "\",name:\"" + resources.getName() + "\",drag:false, drop:false,checked:true,open:true},");
					}else{
						json.append("{id:\"" + resources.getResourcesId() + "\",pId:\"m_" + menu.getMenuId() + "\",name:\"" + resources.getName() + "\",drag:false, drop:false,open:true},");
					}
				}
			}
		}
		return json.toString();
	}
	/**
	 * 通过用户ID获取资源权限树
	 * @param id
	 * @return
	 */
	public String loadUsersTree(Long id, String type) {
		Map<Long, Long> idMap = new HashMap<Long, Long>();
		List<ManagerResources> managerResourcesList = managerResourcesDao.getByHQL("from ManagerResources as ur where ur.id.userId = " + id);
		for (ManagerResources managerResources : managerResourcesList) {
			idMap.put(managerResources.getResources().getResourcesId(), managerResources.getResources().getResourcesId());
		}
		StringBuffer json = new StringBuffer();
		List<Menu> menuList = menuDao.getByHQL("from Menu as m where (m.menutype = '"+type+"' or m.menutype = '2') and m.enable = 1 order by m.orderid");
		json.append("{id:\"m_0\",pId:\"0\",name:\"系统权限\",drag:false, drop:false,open:true},");
		for (Menu menu : menuList) {
			if(menu.getMenu() == null){
				json.append("{id:\"m_" + menu.getMenuId() + "\",pId:\"m_0\",name:\"" + menu.getName() + "\",drag:false, drop:false,open:true},");
			}else{
				json.append("{id:\"m_" + menu.getMenuId() + "\",pId:\"m_" + menu.getMenu().getMenuId() + "\",name:\"" + menu.getName() + "\",drag:false, drop:false,open:true},");
				List<Resources> resourcesList = resourcesDao.getByHQL("from Resources as rs where rs.menu.menuId = " 
						+ menu.getMenuId() + " and rs.enable = 1 and (rs.resourcetype = '2' or rs.resourcetype = '" + type + "') order by rs.orderid");
				for (Resources resources : resourcesList) {
					if(idMap.containsKey(resources.getResourcesId())){
						json.append("{id:\"" + resources.getResourcesId() + "\",pId:\"m_" + menu.getMenuId() + "\",name:\"" + resources.getName() + "\",drag:false, drop:false,checked:true,open:true},");
					}else{
						json.append("{id:\"" + resources.getResourcesId() + "\",pId:\"m_" + menu.getMenuId() + "\",name:\"" + resources.getName() + "\",drag:false, drop:false,open:true},");
					}
				}
			}
		}
		return json.toString();
	}
	public void bulkAdd(List menus) {
		// TODO Auto-generated method stub
		resourcesDao.bulksave(menus);
	}

}

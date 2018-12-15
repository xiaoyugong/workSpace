package com.parkbobo.manager.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.parkbobo.manager.dao.RoleDao;
import com.parkbobo.manager.dao.RoleResourcesDao;
import com.parkbobo.manager.model.Role;
import com.parkbobo.manager.model.RoleResources;
import com.parkbobo.manager.model.RoleResourcesId;
import com.parkbobo.utils.PageBean;

@Component("roleService")
public class RoleService {
	@Resource(name="roleDaoImpl")
	private RoleDao roleDao;
	@Resource(name="roleResourcesDaoImpl")
	private RoleResourcesDao roleResourcesDao;
	
	public void add(Role role, String resourcesIds) {
		role = this.roleDao.add(role);
		if(resourcesIds.length() > 0){
			String[] strs = resourcesIds.split(",");
			for (String str : strs) {
				RoleResources roleResources = new RoleResources();
				RoleResourcesId id = new RoleResourcesId(role.getRoleId(), Long.valueOf(str));
				roleResources.setId(id);
				roleResources.setType((short)1);
				this.roleResourcesDao.merge(roleResources);
			}
		}
	}
	public void update(Role role, String resourcesIds) {
		this.roleDao.merge(role);
		roleResourcesDao.deleteByHql("delete from RoleResources as r where r.id.roleId = " + role.getRoleId());
		if(resourcesIds.length() > 0){
			String[] strs = resourcesIds.split(",");
			for (String str : strs) {
				RoleResources roleResources = new RoleResources();
				RoleResourcesId id = new RoleResourcesId(role.getRoleId(), Long.valueOf(str));
				roleResources.setId(id);
				roleResources.setType((short)1);
				this.roleResourcesDao.merge(roleResources);
			}
		}
	}
	public Role getById(Long id) {
		return this.roleDao.get(id);
	}
	public PageBean<Role> loadPage(String hql, int pageSize, int page) {
		return this.roleDao.pageQuery(hql, pageSize, page);
	}
	public void bulkDelete(String ids) {
		if(ids.length() > 0)
		{
			String[] strs = ids.split(",");
			Long[] idArr = new Long[strs.length];
			for (int i=0; i< strs.length; i++) {
				idArr[i] = Long.valueOf(strs[i]);
			}
			this.roleDao.bulkDelete(idArr);
		}
	}
	public List<Role> getAll() {
		return this.roleDao.getByHQL("from Role as r where r.enable = 1 order by r.createTime");
	}
	

}

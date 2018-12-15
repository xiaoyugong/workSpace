package com.parkbobo.manager.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Component;

import com.parkbobo.manager.dao.ManagerDao;
import com.parkbobo.manager.dao.ManagerResourcesDao;
import com.parkbobo.manager.dao.ManagerRoleDao;
import com.parkbobo.manager.model.Manager;
import com.parkbobo.manager.model.ManagerGroup;
import com.parkbobo.manager.model.ManagerResources;
import com.parkbobo.manager.model.ManagerResourcesId;
import com.parkbobo.manager.model.ManagerRole;
import com.parkbobo.manager.model.ManagerRoleId;
import com.parkbobo.utils.PageBean;

@Component("managerService")
public class ManagerService {
	@Resource(name="managerDaoImpl")
	private ManagerDao managerDao;
	@Resource(name="managerResourcesDaoImpl")
	private ManagerResourcesDao managerResourcesDao;
	@Resource(name="managerRoleDaoImpl")
	private ManagerRoleDao managerRoleDao;
	
	/**
	 * 单个熟悉查询
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public Manager getUniqueByProperty(String propertyName, Object value) {
		return this.managerDao.getUniqueByProperty(propertyName, value);
	}
	/**
	 * 局部更新
	 * @param entityid
	 * @param propertyNames
	 * @param values
	 */
	public void localUpdateOneFields(Serializable entityid,
			String[] propertyNames, Object[] values) {
		this.managerDao.localUpdateOneFields(entityid, propertyNames, values);
		
	}
	public void bulkDelete(String ids) {
		if(ids.length() > 0)
		{
			String[] strs = ids.split(",");
			Long[] idArr = new Long[strs.length];
			for (int i=0; i< strs.length; i++) {
				idArr[i] = Long.valueOf(strs[i]);
			}
			this.managerDao.bulkDelete(idArr);
		}
	}
	public PageBean<Manager> loadPage(String hql, int pageSize, int page) {
		return this.managerDao.pageQuery(hql, pageSize, page);
	}
	public Manager getById(Long id) {
		return this.managerDao.get(id);
	}
	/**
	 * 添加管理用户
	 * @param manager
	 * @param roleIds
	 * @param resourcesIds
	 */
	public Integer add(Manager manager, List<String> roleIds, String resourcesIds) {
		ShaPasswordEncoder sp = new ShaPasswordEncoder();
		manager.setPassword(sp.encodePassword(manager.getPassword(), manager.getUsername()));
		manager.setLoginCount(0l);
		manager.setRegisterTime(new Date().getTime());
		manager.setActivation((short)1);
		manager.setStatus((short)0);
		manager.setManagerGroup(new ManagerGroup(1l));
		if(manager.getEmail().equals(""))
		{
			manager.setEmail(null);
		}
		if(manager.getQq().equals(""))
		{
			manager.setQq(null);
		}
		if(manager.getMobile().equals(""))
		{
			manager.setMobile(null);
		}
		try {
			boolean existsByProperty = this.managerDao.existsByProperty("email", manager.getEmail());
			if(existsByProperty){
				return -1;
			}else if(this.managerDao.existsByProperty("username", manager.getUsername())){
				return -2;
			}else{
				manager = this.managerDao.add(manager);				
			}
			//保存用户角色
			if(roleIds!=null&&roleIds.size()>0)
			{
				for(String roleid : roleIds)
				{
					ManagerRole managerRole = new ManagerRole();
					ManagerRoleId id = new ManagerRoleId(manager.getUserId(), Long.valueOf(roleid));
					managerRole.setId(id);
					managerRoleDao.merge(managerRole);
				}
			}
			//保存用户权限关系
			if(resourcesIds.length() > 0){
				String[] strs = resourcesIds.split(",");
				for (String str : strs) {
					ManagerResources managerResources = new ManagerResources();
					ManagerResourcesId id = new ManagerResourcesId(Long.valueOf(str), manager.getUserId());
					managerResources.setId(id);
					managerResources.setType((short)1);
					managerResourcesDao.merge(managerResources);
				}
			}
			return 0;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
		
	}
	/**
	 * 编辑管理用户
	 * @param manager
	 * @param roleIds
	 * @param resourcesIds
	 */
	public void update(Manager manager, List<String> roleIds,
			String resourcesIds) {
		Manager m = this.managerDao.get(manager.getUserId());
		m.setDepartment(manager.getDepartment());
		if(manager.getPassword()!= null && !manager.getPassword().equals(""))
		{
			ShaPasswordEncoder sp = new ShaPasswordEncoder();
			m.setPassword(sp.encodePassword(manager.getPassword(), manager.getUsername()));
		}
		if(manager.getRealname() == null || manager.getRealname().equals(""))
		{
			m.setRealname(null);
		}
		else
		{
			m.setRealname(manager.getRealname());
		}
		if(manager.getEmail() == null || manager.getEmail().equals(""))
		{
			m.setEmail(null);
		}
		else
		{
			m.setEmail(manager.getEmail());
		}
		if(manager.getQq() == null || manager.getQq().equals(""))
		{
			m.setQq(null);
		}
		else
		{
			m.setQq(manager.getQq());
		}
		if(manager.getMobile() == null || manager.getMobile().equals(""))
		{
			m.setMobile(null);
		}
		else
		{
			m.setMobile(manager.getMobile());
		}
		this.managerDao.merge(m);
		
		//保存用户角色
		this.managerRoleDao.deleteByHql("delete from ManagerRole as mr where mr.id.userId = " + m.getUserId());
		if(roleIds!=null && roleIds.size() > 0)
		{
			for(String roleid : roleIds)
			{
				ManagerRole managerRole = new ManagerRole();
				ManagerRoleId id = new ManagerRoleId(manager.getUserId(), Long.valueOf(roleid));
				managerRole.setId(id);
				managerRoleDao.merge(managerRole);
			}
		}
		//保存用户权限关系
		this.managerResourcesDao.deleteByHql("delete from ManagerResources as mr where mr.id.userId = " + m.getUserId());
		if(resourcesIds.length() > 0){
			String[] strs = resourcesIds.split(",");
			for (String str : strs) {
				ManagerResources managerResources = new ManagerResources();
				ManagerResourcesId id = new ManagerResourcesId(Long.valueOf(str), manager.getUserId());
				managerResources.setId(id);
				managerResources.setType((short)1);
				managerResourcesDao.merge(managerResources);
			}
		}
	}

}

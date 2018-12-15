package com.parkbobo.manager.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.parkbobo.manager.model.Role;
import com.parkbobo.manager.service.ResourcesService;
import com.parkbobo.manager.service.RoleService;
import com.parkbobo.utils.PageBean;
@Controller("roleAction")
@Scope("prototype")
public class RoleAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8352606825536709541L;
	private Role role;
	private PageBean<Role> rolePage;
	private Long id;
	private String resourcesIds;
	private String resourcesJson;
	@Resource(name="roleService")
	private RoleService roleService;
	@Resource(name="resourcesService")
	private ResourcesService resourcesService;
	/**
	 * 角色添加
	 * @return
	 */
	public String add()
	{
		if(getMethod().equals("add"))
		{
			role.setCreateTime(role.getCreateTimeToDate().getTime());
			roleService.add(role,resourcesIds);
			return forward("/role_list");
		}
		else
		{
			resourcesJson = resourcesService.loadTree("0");
			return "add";
		}
	}
	/**
	 * 角色编辑
	 * @return
	 */
	public String edit()
	{
		if(getMethod().equals("edit"))
		{
			role.setCreateTime(role.getCreateTimeToDate().getTime());
			roleService.update(role, resourcesIds);
			return forward("/role_list");
		}
		else
		{
			resourcesJson = resourcesService.loadRoleTree(id);
			role = roleService.getById(id);
			return "edit";
		}
	}
	/**
	 * 角色列表
	 * @return
	 */
	public String list()
	{
		String hql = "from Role as r where 1=1 ";
		if(role != null)
		{
			if(role.getName() != null && !role.getName().equals(""))
			{
				hql += " and r.name like '%" + role.getName() + "%'";
			}
		}
		hql += "order by r.createTime desc";
		rolePage = roleService.loadPage(hql, getPageSize(), getPage());
		return "list";
	}
	/**
	 * 角色删除
	 * @return
	 */
	public String delete()
	{
		roleService.bulkDelete(getIds());
		return forward("/role_list");
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public PageBean<Role> getRolePage() {
		return rolePage;
	}
	public void setRolePage(PageBean<Role> rolePage) {
		this.rolePage = rolePage;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getResourcesJson() {
		return resourcesJson;
	}
	public void setResourcesJson(String resourcesJson) {
		this.resourcesJson = resourcesJson;
	}
	public String getResourcesIds() {
		return resourcesIds;
	}
	public void setResourcesIds(String resourcesIds) {
		this.resourcesIds = resourcesIds;
	}
	@Override
	public String logModel() {
		// TODO Auto-generated method stub
		return null;
	}
}

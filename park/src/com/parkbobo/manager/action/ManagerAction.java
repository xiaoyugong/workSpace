package com.parkbobo.manager.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.parkbobo.manager.model.Department;
import com.parkbobo.manager.model.Manager;
import com.parkbobo.manager.model.ManagerRole;
import com.parkbobo.manager.model.Role;
import com.parkbobo.manager.service.DepartmentService;
import com.parkbobo.manager.service.ManagerRoleService;
import com.parkbobo.manager.service.ManagerService;
import com.parkbobo.manager.service.ResourcesService;
import com.parkbobo.manager.service.RoleService;
import com.parkbobo.utils.PageBean;
@Controller("managerAction")
@Scope("prototype")
public class ManagerAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1220391557132640463L;
	private Manager manager;
	private PageBean<Manager> managerPage;
	private Long id;
	private List<String> roleIds;
	private String resourcesIds;
	private String resourcesJson;
	private List<Role> roles;
	private String method;
	private String errorMsg;
	private List<Department> departments;
	private Map<Long, String> managerRoleMap = new HashMap<Long, String>();
	@Resource(name="managerService")
	private ManagerService managerService;
	@Resource(name="resourcesService")
	private ResourcesService resourcesService;
	@Resource(name="roleService")
	private RoleService roleService;
	@Resource(name="departmentService")
	private DepartmentService departmentService;
	@Resource(name="managerRoleService")
	private ManagerRoleService managerRoleService;
	/**
	 * 管理用户添加
	 * @return
	 */
	public String add()
	{
		if(method!=null && method.equals("add"))
		{
			Integer status = managerService.add(manager, roleIds, resourcesIds);
			if(status==0){
				return forward("/manager_list");				
			}else if(status==-1){
				resourcesJson = resourcesService.loadTree("0");
				departments = departmentService.getAll();
				roles = roleService.getAll();
				errorMsg = "亲，您的邮箱已被注册";
				return "add";
			}else{
				resourcesJson = resourcesService.loadTree("0");
				departments = departmentService.getAll();
				roles = roleService.getAll();
				errorMsg = "亲，您的昵称已被注册";
				return "add";
			}
		}
		else
		{
			resourcesJson = resourcesService.loadTree("0");
			departments = departmentService.getAll();
			roles = roleService.getAll();
			return "add";
		}
	}
	/**
	 * 管理用户编辑
	 * @return
	 */
	public String edit()
	{
		if(method!=null && method.equals("edit"))
		{
			managerService.update(manager, roleIds, resourcesIds);
			return forward("/manager_list");
		}
		else
		{
			resourcesJson = resourcesService.loadUsersTree(id, "0");
			departments = departmentService.getAll();
			roles = roleService.getAll();
			
			manager = managerService.getById(id);
			List<ManagerRole> managerRoles = managerRoleService.getByHql("from ManagerRole as mr where mr.id.userId = " + id);
			for (ManagerRole managerRole : managerRoles) {
				managerRoleMap.put(managerRole.getRole().getRoleId(), managerRole.getRole().getRoleId()+"");
			}
			return "edit";
		}
	}
	/**
	 * 管理用户列表
	 * @return
	 */
	public String list()
	{
		departments = departmentService.getAll();
		String hql = "from Manager as m where 1=1 ";
		if(manager != null)
		{
			if(manager.getUsername() != null && !manager.getUsername().equals(""))
			{
				hql += " and m.username like '%" + manager.getUsername() + "%'";
			}
			if(manager.getDepartment() != null && manager.getDepartment().getDepartmentid() != null)
			{
				hql += " and m.department.departmentid = " +manager.getDepartment().getDepartmentid();
			}
		}
		hql += " order by m.registerTime desc";
		managerPage = managerService.loadPage(hql, getPageSize(), getPage());
		return "list";
	}
	/**
	 * 管理用户删除
	 * @return
	 */
	public String delete()
	{
		managerService.bulkDelete(getIds());
		return forward("/manager_list");
	}
	public Manager getManager() {
		return manager;
	}
	public void setManager(Manager manager) {
		this.manager = manager;
	}
	public PageBean<Manager> getManagerPage() {
		return managerPage;
	}
	public void setManagerPage(PageBean<Manager> managerPage) {
		this.managerPage = managerPage;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getResourcesIds() {
		return resourcesIds;
	}
	public void setResourcesIds(String resourcesIds) {
		this.resourcesIds = resourcesIds;
	}
	public String getResourcesJson() {
		return resourcesJson;
	}
	public void setResourcesJson(String resourcesJson) {
		this.resourcesJson = resourcesJson;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public List<Department> getDepartments() {
		return departments;
	}
	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}
	public List<String> getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
	}
	public Map<Long, String> getManagerRoleMap() {
		return managerRoleMap;
	}
	public void setManagerRoleMap(Map<Long, String> managerRoleMap) {
		this.managerRoleMap = managerRoleMap;
	}
	@Override
	public String logModel() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}

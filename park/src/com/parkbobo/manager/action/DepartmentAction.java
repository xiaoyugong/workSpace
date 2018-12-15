package com.parkbobo.manager.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.parkbobo.manager.model.Department;
import com.parkbobo.manager.service.DepartmentService;
import com.parkbobo.utils.PageBean;
@Controller("departmentAction")
@Scope("prototype")
public class DepartmentAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3231168698922041132L;
	private Department department;
	private PageBean<Department> departmentPage;
	private Long id;
	
	@Resource(name="departmentService")
	private DepartmentService departmentService;
	/**
	 * 部门添加
	 * @return
	 */
	public String add()
	{
		if(getMethod().equals("add"))
		{
			department.setCreateTime(department.getCreateTimeToDate().getTime());
			departmentService.add(department);
			return forward("/department_list");
		}
		else
		{
			return "add";
		}
	}
	/**
	 * 部门编辑
	 * @return
	 */
	public String edit()
	{
		if(getMethod().equals("edit"))
		{
			department.setCreateTime(department.getCreateTimeToDate().getTime());
			departmentService.add(department);
			return forward("/department_list");
		}
		else
		{
			department = departmentService.getById(id);
			return "edit";
		}
	}
	/**
	 * 部门列表
	 * @return
	 */
	public String list()
	{
		String hql = "from Department as d where 1=1 ";
		if(department != null)
		{
			if(department.getName() != null && !department.getName().equals(""))
			{
				hql += " and d.name like '%" + department.getName() + "%'";
			}
		}
		hql += " order by d.createTime desc";
		departmentPage = departmentService.loadPage(hql, getPageSize(), getPage());
		return "list";
	}
	/**
	 * 部门删除
	 * @return
	 */
	public String delete()
	{
		departmentService.bulkDelete(getIds());
		return forward("/department_list");
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public PageBean<Department> getDepartmentPage() {
		return departmentPage;
	}
	public void setDepartmentPage(PageBean<Department> departmentPage) {
		this.departmentPage = departmentPage;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Override
	public String logModel() {
		// TODO Auto-generated method stub
		return null;
	}
}

package com.parkbobo.manager.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.parkbobo.manager.dao.DepartmentDao;
import com.parkbobo.manager.model.Department;
import com.parkbobo.utils.PageBean;

@Component("departmentService")
public class DepartmentService {
	@Resource(name="departmentDaoImpl")
	private DepartmentDao departmentDao;
	public void add(Department department) {
		this.departmentDao.merge(department);
	}
	public Department getById(Long id) {
		return this.departmentDao.get(id);
	}
	public PageBean<Department> loadPage(String hql, int pageSize, int page) {
		return this.departmentDao.pageQuery(hql, pageSize, page);
	}
	public void bulkDelete(String ids) {
		if(ids.length() > 0)
		{
			String[] strs = ids.split(",");
			Long[] idArr = new Long[strs.length];
			for (int i=0; i< strs.length; i++) {
				idArr[i] = Long.valueOf(strs[i]);
			}
			this.departmentDao.bulkDelete(idArr);
		}
	}
	public List<Department> getAll() {
		return this.departmentDao.getByHQL("from Department as d order by d.createTime");
	}

}

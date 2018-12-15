package com.parkbobo.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.parkbobo.dao.OrderTaskDao;
import com.parkbobo.model.OrderTask;
import com.parkbobo.model.Users;
import com.parkbobo.utils.PageBean;

@Component("orderTaskService")
public class OrderTaskService {
	@Resource(name="orderTaskDaoImpl")
	private OrderTaskDao orderTaskDao;
	
	public List<OrderTask> getByHql(String hql)
	{
		return this.orderTaskDao.getByHQL(hql);
	}

	public PageBean<OrderTask> getPage(String hql, int pageSize, int page) {
		return this.orderTaskDao.pageQuery(hql, pageSize, page);
	}

	public boolean add(OrderTask orderTask) {
		try {
			this.orderTaskDao.merge(orderTask);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean delete(Users users, Long taskid) {
		String[] propertyNames = {"users.userid", "taskid"};
		Object[] values = {users.getUserid(), taskid};
		OrderTask orderTask = this.orderTaskDao.getUniqueByPropertys(propertyNames, values);
		if(orderTask != null)
		{
			this.orderTaskDao.delete(orderTask.getTaskid());
			return true;
		}
		else
		{
			return false;
		}
	}

	public OrderTask get(Long taskid) {
		// TODO Auto-generated method stub
		return orderTaskDao.get(taskid);
	}

	public PageBean<OrderTask> page(String hqlString, int pageSize, int page) {
		// TODO Auto-generated method stub
		return orderTaskDao.pageQuery(hqlString, pageSize, page);
	}

		public void delete(String ids) {
			String[] strs = ids.split(",");
			Long[] idArr = new Long[strs.length];
			for (int i=0; i< strs.length; i++) {
				idArr[i] = Long.valueOf(strs[i]);
			}
			
			orderTaskDao.bulkDelete(idArr);
		
	}

}

package com.parkbobo.manager.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.parkbobo.model.CarparkBerthPolygon;
import com.parkbobo.model.CarparkMarkerCategory;
import com.parkbobo.model.OrderTask;
import com.parkbobo.service.CarparkBerthPolygonService;
import com.parkbobo.service.OrderTaskService;
import com.parkbobo.utils.PageBean;

@Controller("orderTaskAction")
@Scope("prototype")
public class OrderTaskAction extends BaseAction {

	private OrderTask orderTask;
	@Resource(name="orderTaskService")
	private OrderTaskService orderTaskService;
	
	
	
	
	public String list() throws Exception {
		String hqlString = "from OrderTask where 1=1 ";
		if(orderTask!=null){
			hqlString+=" and carparkname like '%"+orderTask.getCarparkname().trim()+"%' " +
					"and users.username like '%"+orderTask.getUsers().getUsername().trim()+"%' ";
			if(orderTask.getTaskStatus()!=null&&orderTask.getTaskStatus()!=9){
				hqlString+=" and taskStatus="+orderTask.getTaskStatus();
			}
			if(orderTask.getTasktype()!=null&&orderTask.getTasktype()!=9){
				hqlString+=" and tasktype="+orderTask.getTasktype();
			}
		}
		hqlString+=" order by posttime desc";
		PageBean<OrderTask> page = orderTaskService.page(hqlString,getPageSize(),getPage());
		ActionContext.getContext().getValueStack().set("page", page);
		return "list";
	}
	public String toSave() {
		// TODO Auto-generated method stub
		if(orderTask!=null)
			orderTask = orderTaskService.get(orderTask.getTaskid());
		return "toSave";
	}
	public String delete() {
		// TODO Auto-generated method stub
		orderTaskService.delete(getIds());
		return forward("/orderTask_list");
	}
	@Override
	public String logModel() {
		// TODO Auto-generated method stub
		return "预约停车";
	}
	public OrderTaskService getOrderTaskService() {
		return orderTaskService;
	}
	public void setOrderTaskService(OrderTaskService orderTaskService) {
		this.orderTaskService = orderTaskService;
	}
	public OrderTask getOrderTask() {
		return orderTask;
	}
	public void setOrderTask(OrderTask orderTask) {
		this.orderTask = orderTask;
	}

}

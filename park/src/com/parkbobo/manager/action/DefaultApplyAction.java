package com.parkbobo.manager.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.parkbobo.manager.model.Manager;
import com.parkbobo.model.DefaultApply;
import com.parkbobo.service.DefaultApplyService;
import com.parkbobo.utils.PageBean;

@Controller("defaultApplyAction")
@Scope("prototype")
public class DefaultApplyAction extends BaseAction {

	@Resource(name="defaultApplyService")
	private DefaultApplyService defaultApplyService;
	private DefaultApply defaultApply;
	
	public String list() throws Exception {
		Manager manager = (Manager) request.getSession().getAttribute("loginManager");
		String area = manager.getArea();
		if(area!=null && "总部".equals(area)){
			String hqlString = "from DefaultApply where 1=1 ";
			if(defaultApply!=null){
				if(defaultApply.getStatus()!=null&&defaultApply.getStatus()!=9)
				hqlString += " and status="+defaultApply.getStatus();
				if(defaultApply.getType()!=null&&defaultApply.getType()!=9)
				hqlString += " and type="+defaultApply.getType();
				if(defaultApply.getUsers()!=null)
					hqlString += " and users.username like '%"+defaultApply.getUsers().getUsername().trim()+"%'";
			}
			hqlString+="order by status,posttime desc";
			PageBean<DefaultApply> page = defaultApplyService.getPageBean(hqlString,getPage(),getPageSize());
			ActionContext.getContext().getValueStack().set("page", page);
			return "list";
		}else if(area!=null && !"".equals(area)){
			System.out.println("12345646");
			String hqlString = "from DefaultApply where users.area like '%" + area +"%' ";
			if(defaultApply!=null){
				if(defaultApply.getStatus()!=null&&defaultApply.getStatus()!=9)
				hqlString += " and status="+ defaultApply.getStatus();
				if(defaultApply.getType()!=null&&defaultApply.getType()!=9)
				hqlString += " and type="+defaultApply.getType();
				if(defaultApply.getUsers()!=null)
					hqlString += " and users.username like '%"+defaultApply.getUsers().getUsername().trim()+"%'";
			}
			hqlString+="order by status,posttime desc";
			PageBean<DefaultApply> page = defaultApplyService.getPageBean(hqlString,getPage(),getPageSize());
			ActionContext.getContext().getValueStack().set("page", page);
			return "list";
		}else{
			return "list";			
		}
		
	}
	public String delete() throws Exception {
		defaultApplyService.delete(getIds());
		return forward("/defaultApply_list");
	}
	
	public String view() {
		this.defaultApply = this.defaultApplyService.getById(this.defaultApply.getApplyid());
		
		return "view";
	}
	public String update() {
		DefaultApply apply = this.defaultApplyService.getById(this.defaultApply.getApplyid());
		apply.setStatus(defaultApply.getStatus());
		apply.setMemo(defaultApply.getMemo());
		defaultApplyService.update(apply);
		return forward("/defaultApply_list");
	}
	public DefaultApply getDefaultApply() {
		return defaultApply;
	}
	public void setDefaultApply(DefaultApply defaultApply) {
		this.defaultApply = defaultApply;
	}
	@Override
	public String logModel() {
		// TODO Auto-generated method stub
		return "违约处理申请";
	}
	
	
}

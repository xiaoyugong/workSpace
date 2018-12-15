package com.parkbobo.manager.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.parkbobo.manager.model.Manager;
import com.parkbobo.model.UserCredit;
import com.parkbobo.model.UserPoint;
import com.parkbobo.service.UserCreditService;
import com.parkbobo.utils.PageBean;



@Controller("userCreditAction")
@Scope("prototype")
public class UserCreditAction extends BaseAction {

	@Resource(name="userCreditService")
	private UserCreditService userCreditService;
	private UserCredit userCredit;
	
	
	public String list() throws Exception {
		Manager manager = (Manager) request.getSession().getAttribute("loginManager");
		String area = manager.getArea();
		if(area!=null && "总部".equals(area)){
			String hql = "from UserCredit where 1=1 " ;
			if(userCredit!=null){
				if(userCredit.getUsers()!=null)
					hql+=" and users.username like '%"+userCredit.getUsers().getUsername().trim()+"%' ";
				if(userCredit.getType()!=null&&userCredit.getType()!=9)
					hql+=" and type ="+userCredit.getType();
			}
			hql+=" order by posttime desc";
			System.out.println(hql);
			PageBean<UserCredit> pageBean = userCreditService.getPage(hql, getPage(), getPageSize());
			ActionContext.getContext().getValueStack().set("page", pageBean);
			return "list";
		}else if(area!=null && !"".equals(area)){
			String hql = "from UserCredit where 1=1 and users.area like '%" + area + "%' " ;
			if(userCredit!=null){
				if(userCredit.getUsers()!=null)
					hql+=" and users.username like '%"+userCredit.getUsers().getUsername().trim()+"%' ";
				if(userCredit.getType()!=null&&userCredit.getType()!=9)
					hql+=" and type ="+userCredit.getType();
			}
			hql+=" order by posttime desc";
			System.out.println(hql);
			PageBean<UserCredit> pageBean = userCreditService.getPage(hql, getPage(), getPageSize());
			ActionContext.getContext().getValueStack().set("page", pageBean);
			return "list";
		}else{
			return "list";
		}
	}
	public String delete() throws Exception {
		userCreditService.delete(getIds());
		
		return forward("/userCredit_list");
	}

	public UserCredit getUserCredit() {
		return userCredit;
	}


	public void setUserCredit(UserCredit userCredit) {
		this.userCredit = userCredit;
	}
	@Override
	public String logModel() {
		// TODO Auto-generated method stub
		return "用户信誉";
	}
	
	
}

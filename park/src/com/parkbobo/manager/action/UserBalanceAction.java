package com.parkbobo.manager.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.parkbobo.manager.model.Department;
import com.parkbobo.manager.model.Manager;
import com.parkbobo.manager.service.DepartmentService;
import com.parkbobo.model.Notify;
import com.parkbobo.model.UserBalance;
import com.parkbobo.service.NotifyService;
import com.parkbobo.service.UserBalanceService;
import com.parkbobo.utils.PageBean;
@Controller("userBalanceAction")
@Scope("prototype")
public class UserBalanceAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5157244783570171550L;
	/**
	 * 
	 */
	private UserBalance userBalance;
	private PageBean<UserBalance> userBalancePage;
	@Resource(name = "userBalanceService")
	private UserBalanceService userBalanceService;
	private Integer event=1;
	


	
	public String update(){
		return "update";
	}
	public String list(){
		Manager manager = (Manager) request.getSession().getAttribute("loginManager");
		String area = manager.getArea();
		System.out.println(area);
		if(area!=null && !"".equals(area) && !"总部".equals(area)){
			String hql = "from UserBalance where 1=1 and (paystatus=1 or paystatus is null) and  users.area like '%" + area + "%' " ;
			if(userBalance!=null){
				
				if(userBalance.getEvent()!=null&&userBalance.getEvent()!=9)
					hql+=" and event = "+userBalance.getEvent();
				if(userBalance.getUsers()!=null)
					hql+=" and users.username like '%"+userBalance.getUsers().getUsername().trim()+"%'";
			}
			hql +=  " order by posttime desc";
			userBalancePage = userBalanceService.getPage(hql, getPage(), getPageSize());
				return "list";
		}else if(area!=null && "总部".equals(area)){
			String hql = "from UserBalance where 1=1 and (paystatus=1 or paystatus is null)" ;
			if(userBalance!=null){
				if(userBalance.getEvent()!=null&&userBalance.getEvent()!=9)
					hql+=" and event = "+userBalance.getEvent();
				if(userBalance.getUsers()!=null)
					hql+=" and users.username like '%"+userBalance.getUsers().getUsername().trim()+"%'";
			}
			hql +=  " order by posttime desc";
			userBalancePage = userBalanceService.getPage(hql, getPage(), getPageSize());
			return "list";
		}else{
			return "list";
		}
		
		
	
	}
	
	public String delete() {
		userBalanceService.delete(getIds());
		return forward("/userBalance_list");
	}
	public PageBean<UserBalance> getUserBalancePage() {
		return userBalancePage;
	}
	public void setUserBalancePage(PageBean<UserBalance> userBalancePage) {
		this.userBalancePage = userBalancePage;
	}
	public Integer getEvent() {
		return event;
	}
	public void setEvent(Integer event) {
		this.event = event;
	}
	public UserBalance getUserBalance() {
		return userBalance;
	}
	public void setUserBalance(UserBalance userBalance) {
		this.userBalance = userBalance;
	}
	@Override
	public String logModel() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	}

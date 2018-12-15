package com.parkbobo.manager.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.parkbobo.manager.model.Manager;
import com.parkbobo.model.UserCredit;
import com.parkbobo.model.UserPoint;
import com.parkbobo.service.UserCreditService;
import com.parkbobo.service.UserPointService;
import com.parkbobo.utils.PageBean;

@Controller("userPointAction")
@Scope("prototype")
public class UserPointAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1970851077535177473L;
	@Resource(name="userPointService")
	private UserPointService userPointService;
	private UserPoint userPoint;
	
	
	public String list() throws Exception {
		Manager manager = (Manager) request.getSession().getAttribute("loginManager");
		String area = manager.getArea();
		if(area!=null && "总部".equals(area)){
			String hql = "from UserPoint where 1=1 " ;
			if(userPoint!=null){
				if(userPoint.getUsers()!=null)
					hql+=" and users.username like '%"+userPoint.getUsers().getUsername().trim()+"%' ";
				if(userPoint.getType()!=null&&userPoint.getType()!=9)
					hql+=" and type ="+userPoint.getType();
			}
			hql+=" order by posttime desc";
			System.out.println(hql);
			PageBean<UserPoint> pageBean = userPointService.getPage(hql, getPage(), getPageSize());
			ActionContext.getContext().getValueStack().set("page", pageBean);
			return "list";
		}else if(area!=null && !"".equals(area)){
			System.out.println("asdfs");
			String hql = "from UserPoint where 1=1 and users.area like '%" + area + "%' " ;
			if(userPoint!=null){
				if(userPoint.getUsers()!=null)
					hql+=" and users.username like '%"+userPoint.getUsers().getUsername().trim()+"%' ";
				if(userPoint.getType()!=null&&userPoint.getType()!=9)
					hql+=" and type ="+userPoint.getType();
			}
			hql+=" order by posttime desc";
			System.out.println(hql);
			PageBean<UserPoint> pageBean = userPointService.getPage(hql, getPage(), getPageSize());
			ActionContext.getContext().getValueStack().set("page", pageBean);
			return "list";
		}else{
			return "list";
		}
	}
	public String delete() throws Exception {
		userPointService.delete(getIds());
		
		return forward("/userPoint_list");
	}

	public UserPoint getUserPoint() {
		return userPoint;
	}


	public void setUserPoint(UserPoint userPoint) {
		this.userPoint = userPoint;
	}
	@Override
	public String logModel() {
		// TODO Auto-generated method stub
		return "用户积分";
	}
	
	
}

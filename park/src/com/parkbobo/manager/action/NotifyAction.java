package com.parkbobo.manager.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


import com.baidu.yun.core.model.HttpRestResponse;
import com.igetui.util.PushMessageUtil;
import com.opensymphony.xwork2.ActionContext;
import com.parkbobo.manager.model.Department;
import com.parkbobo.manager.model.Manager;
import com.parkbobo.manager.service.DepartmentService;
import com.parkbobo.model.Carpark;
import com.parkbobo.model.Notify;
import com.parkbobo.model.Users;
import com.parkbobo.service.CarparkService;
import com.parkbobo.service.NotifyService;
import com.parkbobo.service.UsersService;
import com.parkbobo.utils.PageBean;
import com.parkbobo.utils.ThreadSendNotify;
@Controller("notifyAction")
@Scope("prototype")
public class NotifyAction extends BaseAction {
	/**
	 * 
	 */
	private Notify notify;
	@Resource(name = "notifyService")
	private NotifyService notifyService;
	private PageBean<Users> usersPage;
	@Resource(name="usersService")
	private  UsersService userservice;
	@Resource(name="carparkService")
	private CarparkService carparkService;
	private Carpark carpark;
	private String ids;
	private String[] users_selected;
	private Users user;
	
	/**
	 *发送对象  0：停车场，1：用户 2：全部
	 */
	private Integer notifyType;
	/**
	 * 发送方式  0:推送  1:短信 2全部
	 */
	private Integer notifysendMode=0;
	
	
	public String add(){
		if("add".equals(getMethod())){
//			notify.setUsers(userservice.getUsers(users_selected[0]));
			notify.setUsers(null);
			notify.setIsDel(0);
			notify.setType(0);
			notify.setIsRead(0);
			notify.setPosttime(System.currentTimeMillis());
			sendNotifyByNotifyType(notify,notifyType,notifysendMode);
			return this.forward("/notify_list");
		}
		sendInfoPublic();
		 return "add";
	}

	public String toSms(){
		if("add".equals(getMethod())){
//			notify.setUsers(userservice.getUsers(users_selected[0]));
			notify.setUsers(null);
			notify.setIsDel(0);
			notify.setType(0);
			notify.setIsRead(0);
			notify.setPosttime(System.currentTimeMillis());
			sendNotifyByNotifyType(notify,notifyType,notifysendMode);
			return this.forward("/notify_list");
		}
		sendInfoPublic();
		 return "toSms";
	}
	private void sendInfoPublic() {
		
		HttpSession session = request.getSession();
		
		
		//进入添加界面对session清空
		if(notifyType!=null&&notifyType==4){
			session.removeAttribute("notifyUsers");
			session.removeAttribute("notifyCarpark");
		}
		//保存用户
		if("add_usersession".equals(getMethod())&&!"".equals(getIds())){
			Set< Users> users = new HashSet(userservice.findUsersByIds(getIds()));
			Set< Users> exUser = (Set<Users>) session.getAttribute("notifyUsers");
			if(exUser==null)
				session.setAttribute("notifyUsers", users);
			else {
				exUser.addAll(users);
			}
			session.removeAttribute("notifyCarpark");
		}
		//保存停车场
		if("add_carparksession".equals(getMethod())&&!"".equals(getIds())){
			Set< Carpark> users = new HashSet(carparkService.findCarparkByIds(getIds()));
			Set< Carpark> exUser = (Set<Carpark>) session.getAttribute("notifyCarpark");
			if(exUser==null)
				session.setAttribute("notifyCarpark", users);
			else {
				exUser.addAll(users);
			}
			session.removeAttribute("notifyUsers");
		}
	}
	
	
	
	//根据类型发送通知
	private void sendNotifyByNotifyType(Notify notify,Integer notifyType, Integer notifysendMode) {
		//全部用户
		if(notifyType!=null&&notifyType==2){
			String hql = "from Users ";
			Thread thread = new Thread(new ThreadSendNotify(userservice,notify,notifysendMode,notifyType,hql,notifyService));
			thread.start();
		}
		HttpSession session = request.getSession();
			//指定用户
		if(notifyType!=null&&notifyType==1)
		{
			Set< Users> exUser = (Set<Users>) session.getAttribute("notifyUsers");
			notifyService.add(notify,new ArrayList<Users>(exUser),notifysendMode,notifyType);
			
		}
			//指定停车场
		if(notifyType!=null&&notifyType==0){
			Set< Carpark> expark = (Set<Carpark>) session.getAttribute("notifyCarpark");
			notifyService.addCarparkNotify(notify,new ArrayList<Carpark>(expark),notifysendMode);
		}
		
		
	}
	
	public String selectUser()
	{
		String hql = "from Users as u where 1=1";
		if(user!=null)
		hql+=" and u.username like '%"+user.getUsername()+"%' ";
		
		hql += " order by u.registerTime desc";
		usersPage = userservice.loadPage(hql, getPageSize(), getPage());
		return "selectUser";
	}
	
	
	public String selectCarpark()
	{
		String hql = "from Carpark as c where 1=1";
		if(carpark != null)
		{
			if(carpark.getName() != null && !carpark.getName().equals(""))
			{
				hql += " and c.name like '%" + carpark.getName() + "%'";
			}
		}
		hql += " order by c.carparkid desc";
		PageBean<Carpark> carparkPage = carparkService.loadPage(hql, getPageSize(), getPage());
		ActionContext.getContext().getValueStack().set("carparkPage", carparkPage);
		return "selectCarpark";
	}
	public void getUsers() {
		PageBean<Users> userPage = null;
		String hql = "from Users as u where clientid  is not null and clientid!=''";
		hql += " order by u.registerTime desc";
		
		List<Users> tempList = userservice.getByHql(hql);
		
		ActionContext.getContext().getValueStack().set("userList", tempList);
	}
	
	
	public String delete(){
		notifyService.bulkDelete(ids);
//		saveLog("删除通知");
		return forward("/notify_list");
	}
	public String list(){
		Manager manager = (Manager) request.getSession().getAttribute("loginManager");
		String area = manager.getArea();
		if(area!=null && "总部".equals(area)){
			String hqlString = "FROM Notify WHERE isDel=0 AND type=0 ";
			if(notify!=null){
				hqlString+="and title like '%"+notify.getTitle().trim()+"%' ";
			}
			hqlString+="order by posttime desc";
			PageBean<Notify> notifyPage = notifyService.getPage(hqlString, this.getPage(), this.getPageSize());
			ActionContext.getContext().getValueStack().set("page", notifyPage);
			return "list";
			
		}else if(area!=null && !"".equals(area)){
			String hqlString = "FROM Notify WHERE isDel=0 AND type=0 AND users.area like '%" + area +"%' ";
			if(notify!=null){
				hqlString+="and title like '%"+notify.getTitle().trim()+"%' ";
			}
			hqlString+="order by posttime desc";
			PageBean<Notify> notifyPage = notifyService.getPage(hqlString, this.getPage(), this.getPageSize());
			ActionContext.getContext().getValueStack().set("page", notifyPage);
			return "list";
		}else{
			return "list";
		}
	}


	public Notify getNotify() {
		return notify;
	}

	public void setNotify(Notify notify) {
		this.notify = notify;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String[] getUsers_selected() {
		return users_selected;
	}

	public void setUsers_selected(String[] usersSelected) {
		users_selected = usersSelected;
	}


	public PageBean<Users> getUsersPage() {
		return usersPage;
	}


	public void setUsersPage(PageBean<Users> usersPage) {
		this.usersPage = usersPage;
	}


	public Users getUser() {
		return user;
	}


	public void setUser(Users user) {
		this.user = user;
	}


	public Integer getNotifyType() {
		return notifyType;
	}


	public void setNotifyType(Integer notifyType) {
		this.notifyType = notifyType;
	}


	public Carpark getCarpark() {
		return carpark;
	}


	public void setCarpark(Carpark carpark) {
		this.carpark = carpark;
	}

	@Override
	public String logModel() {
		// TODO Auto-generated method stub
		return "通知管理";
	}

	public Integer getNotifysendMode() {
		return notifysendMode;
	}

	public void setNotifysendMode(Integer notifysendMode) {
		this.notifysendMode = notifysendMode;
	}


	
	}

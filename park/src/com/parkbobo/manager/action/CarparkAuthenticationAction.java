package com.parkbobo.manager.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import antlr.collections.List;

import com.parkbobo.manager.model.Manager;
import com.parkbobo.model.CarparkAuthentication;
import com.parkbobo.model.DriverAuthentication;
import com.parkbobo.model.Users;
import com.parkbobo.service.CarparkAuthenticationService;
import com.parkbobo.service.DriverAuthenticationService;
import com.parkbobo.service.UsersService;
import com.parkbobo.utils.CarparkAuthenticationSort;
import com.parkbobo.utils.PageBean;
import com.parkbobo.utils.sort.ApproveTypeSort;
import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * @author Administrator
 *车位认证
 */

@Controller("carparkAuthenticationAction")
@Scope("prototype")
public class CarparkAuthenticationAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4934862660195675960L;
	private PageBean<CarparkAuthentication> carparkAuthenticationPage;
	private CarparkAuthentication carparkAuthentication;
	@Resource(name="carparkAuthenticationService")
	private CarparkAuthenticationService carparkAuthenticationService;
	@Resource(name="usersService")
	private UsersService usersService;
	private Users users;
	private String method;
	private String area;
	private Long authenticationid;
	
	public String list()
	{
		Manager manager = (Manager) request.getSession().getAttribute("loginManager");
		String area = manager.getArea();
		if(area!=null && "总部".equals(area)){
			String username = "";
			if(users!=null){
				username = users.getUsername().trim();
			}
			String hql = "from CarparkAuthentication as u where  u.users.username like '%"+username+"%' ";
			
			if(carparkAuthentication!=null){
				hql += " and u.carparkname  like '%"+carparkAuthentication.getCarparkname().trim()+"%' " ;
				if(carparkAuthentication.getAuthenticationtype()!=null&&carparkAuthentication.getAuthenticationtype()!=3)
					hql += "and  u.authenticationtype="+carparkAuthentication.getAuthenticationtype();
				if(carparkAuthentication.getStatus()!=null&&carparkAuthentication.getStatus()!=3)
					hql+=" and u.status="+carparkAuthentication.getStatus();
			}
			
			hql += " order by u.status , u.posttime desc ";
			carparkAuthenticationPage = carparkAuthenticationService.loadPage(hql, getPageSize(), getPage());
			Collections.sort(carparkAuthenticationPage.getList(), new CarparkAuthenticationSort()); 
			return "list";
		}
		if(area!=null && !"".equals(area)){
			String username = "";
			if(users!=null){
				username = users.getUsername().trim();
			}
			String hql = "from CarparkAuthentication as u where u.users.area like '%"+area+"%' and" +
					" u.users.username like '%"+username+"%' ";
			
			if(carparkAuthentication!=null){
				hql += " and u.carparkname  like '%"+carparkAuthentication.getCarparkname().trim()+"%' " ;
				if(carparkAuthentication.getAuthenticationtype()!=null&&carparkAuthentication.getAuthenticationtype()!=3)
					hql += "and  u.authenticationtype="+carparkAuthentication.getAuthenticationtype();
				if(carparkAuthentication.getStatus()!=null&&carparkAuthentication.getStatus()!=3)
					hql+=" and u.status="+carparkAuthentication.getStatus();
			}
			
			hql += " order by u.status , u.posttime desc ";
			carparkAuthenticationPage = carparkAuthenticationService.loadPage(hql, getPageSize(), getPage());
			Collections.sort(carparkAuthenticationPage.getList(), new CarparkAuthenticationSort()); 
			return "list";

		}else{
			return "list";
			
		}
	}
	
	
	/**
	 * 认证划分
	 * 
	 * */
	/**
	 * 认证划分
	 * @throws IOException 
	 * 
	 * */
	public String updateArea() throws IOException{
		if(method!=null && "save".equals(method)){
			//保存操作
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			CarparkAuthentication authentication = carparkAuthenticationService.getById(authenticationid);
			System.out.println(area);
			if(area!=null && !"".equals(area)){
				Users users2 = authentication.getUsers();
				users2.setArea(area);
				
				try {
					usersService.updateUsers(users2);
					out.print("{\"status\":\"true\"}");					
				} catch (Exception e) {
					out.print("{\"status\":\"false\"}");		
				}
				out.flush();
				out.close();
			}
			return NONE;
		}else{
			return "updateArea";			
		}
	}
	
	
	/**
	 * 跳转到审核界面
	 * @return
	 */
	public String check(){
		
		carparkAuthentication =  carparkAuthenticationService.getById(carparkAuthentication.getAuthenticationid());
		
		return "check";
	}
	
	/**
	 * 审核
	 * @return
	 */
	public String update() {
		CarparkAuthentication carparkAuth = carparkAuthenticationService.getById(this.carparkAuthentication.getAuthenticationid());
		carparkAuth.setStatus(carparkAuthentication.getStatus());
		carparkAuth.setMemo(carparkAuthentication.getMemo());
		carparkAuthenticationService.update(carparkAuth);
		
		this.saveLog("车位"+carparkAuth.getCarparkname()+carparkAuth.getBerthnum()+
				"号',审核为:"+convertStart(carparkAuthentication.getStatus()));
		return forward("/carparkAuthentication_list");
	}
	private String convertStart(Short status){
		HashMap<Short,String> statusMap = new HashMap();
		statusMap.put((short)1,"通过" );
		statusMap.put((short)2,"未通过" );
		return statusMap.get(status);
	}
	public String delete()
	{
			
			carparkAuthenticationService.deleteByHql(getIds());
			this.saveLog("删除：Id:"+getIds());
		
		return forward("/carparkAuthentication_list");
	}

	

	public PageBean<CarparkAuthentication> getCarparkAuthenticationPage() {
		return carparkAuthenticationPage;
	}

	public void setCarparkAuthenticationPage(
			PageBean<CarparkAuthentication> carparkAuthenticationPage) {
		this.carparkAuthenticationPage = carparkAuthenticationPage;
	}

	public CarparkAuthentication getCarparkAuthentication() {
		return carparkAuthentication;
	}

	public void setCarparkAuthentication(CarparkAuthentication carparkAuthentication) {
		this.carparkAuthentication = carparkAuthentication;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	@Override
	public String logModel() {
		// TODO Auto-generated method stub
		return "车位认证";
	}


	public String getMethod() {
		return method;
	}


	public void setMethod(String method) {
		this.method = method;
	}


	public String getArea() {
		return area;
	}


	public void setArea(String area) {
		this.area = area;
	}


	public Long getAuthenticationid() {
		return authenticationid;
	}


	public void setAuthenticationid(Long authenticationid) {
		this.authenticationid = authenticationid;
	}
	
}

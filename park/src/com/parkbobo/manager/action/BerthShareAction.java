package com.parkbobo.manager.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.parkbobo.manager.model.Manager;
import com.parkbobo.model.BerthShare;
import com.parkbobo.model.Users;
import com.parkbobo.service.BerthShareService;
import com.parkbobo.utils.PageBean;

/**
 * @author Administrator
 *车位分享
 */

@Controller("berthShareAction")
@Scope("prototype")
public class BerthShareAction extends BaseAction {

	private PageBean<BerthShare> berthSharePage;
	private BerthShare berthShare;
	@Resource(name="berthShareService")
	private BerthShareService berthShareService;
	private Users users;
	private String publishOrder="desc";
	private String updateOrder="desc";
	private String orderPrecedence="publishOrder";
	public String list()
	{
		Manager manager = (Manager) request.getSession().getAttribute("loginManager");
		String area = manager.getArea();
		if(area!=null && "总部".equals(area)){
			String username = "";
			if(users!=null){
				username = users.getUsername().trim();
			}
			String hql = "from BerthShare as u where isDel=0 " +
			"and u.users.username like '%"+username+"%' ";
			if(berthShare!=null){
				hql +=" and u.carparkname like '%"+berthShare.getCarparkname().trim()+"%' ";
				if(!berthShare.getBerthnum().trim().equals(""))
					hql +=" and u.berthnum like '%"+berthShare.getBerthnum().trim()+"%'" ;
				if(berthShare.getSharetype()!=null&&berthShare.getSharetype()!=9)
					hql += "and  u.sharetype="+berthShare.getSharetype();
				if(berthShare!=null&&berthShare.getIsAuthentication()!=null&&berthShare.getIsAuthentication()!=9)
					hql+=" and u.isAuthentication="+berthShare.getIsAuthentication();
			}
			if(orderPrecedence.equals("publishOrder")){
				hql += " order by u.isAuthentication , u.sharetime "+publishOrder+" ,u.updatetime desc";
			}
			if(orderPrecedence.equals("updateOrder")){
				hql += " order by u.isAuthentication , u.updatetime "+updateOrder+" ,u.sharetime desc";
			}
			berthSharePage = berthShareService.loadPage(hql, getPageSize(), getPage());
		}else if(area!=null && !"".equals(area)){
			String username = "";
			if(users!=null){
				username = users.getUsername().trim();
			}
			String hql = "from BerthShare as u where u.isDel=0 and u.carpark.city like '%" + area + "%' " +
			"and u.users.username like '%"+username+"%' ";
			if(berthShare!=null){
				hql +=" and u.carparkname like '%"+berthShare.getCarparkname().trim()+"%' ";
				if(!berthShare.getBerthnum().trim().equals(""))
					hql +=" and u.berthnum like '%"+berthShare.getBerthnum().trim()+"%'" ;
				if(berthShare.getSharetype()!=null&&berthShare.getSharetype()!=9)
					hql += "and  u.sharetype="+berthShare.getSharetype();
				if(berthShare!=null&&berthShare.getIsAuthentication()!=null&&berthShare.getIsAuthentication()!=9)
					hql+=" and u.isAuthentication="+berthShare.getIsAuthentication();
			}
			if(orderPrecedence.equals("publishOrder")){
				hql += " order by u.isAuthentication , u.sharetime "+publishOrder+" ,u.updatetime desc";
			}
			if(orderPrecedence.equals("updateOrder")){
				hql += " order by u.isAuthentication , u.updatetime "+updateOrder+" ,u.sharetime desc";
			}
			berthSharePage = berthShareService.loadPage(hql, getPageSize(), getPage());
		}
		return "list";
	}
	public String open(){
		berthShare =  berthShareService.getById(berthShare.getShareid());
		berthShare.setIsClose((short)0);
		berthShareService.update(berthShare);
		
		this.saveLog("开启分享，【"+berthShare.getCarparkname()+","+berthShare.getBerthnum()+"号】,id："+berthShare.getShareid());
		return forward("/berthShare_list"); 
	}
	
	public String close(){
		berthShare =  berthShareService.getById(berthShare.getShareid());
		berthShare.setIsClose((short)1);
		berthShareService.update(berthShare);
		
		this.saveLog("关闭分享，【"+berthShare.getCarparkname()+","+berthShare.getBerthnum()+"号】,id："+berthShare.getShareid());
		return forward("/berthShare_list"); 
	}
	/**
	 * 跳转到审核界面
	 * @return
	 */
	public String check(){
		
		berthShare =  berthShareService.getById(berthShare.getShareid());
		
		return "check";
	}
	
	/**
	 * 审核
	 * @return
	 */
	public String update() {
		BerthShare berthShare = berthShareService.getById(this.berthShare.getShareid());
		berthShare.setIsAuthentication(this.berthShare.getIsAuthentication());
		berthShareService.update(berthShare);
		saveLog("审核：【"+berthShare.getCarparkname()+","+berthShare.getBerthnum()+"号】,Id:"+berthShare.getShareid());
		return forward("/berthShare_list");
	}
	public String delete()
	{
		berthShareService.deleteByHql(getIds());
		this.saveLog("删除，id："+getIds());
		return forward("/berthShare_list");
	}

	

 

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public PageBean<BerthShare> getBerthSharePage() {
		return berthSharePage;
	}

	public void setBerthSharePage(PageBean<BerthShare> berthSharePage) {
		this.berthSharePage = berthSharePage;
	}

	public BerthShare getBerthShare() {
		return berthShare;
	}

	public void setBerthShare(BerthShare berthShare) {
		this.berthShare = berthShare;
	}

	@Override
	public String logModel() {
		// TODO Auto-generated method stub
		return "车位分享";
	}


	public String getPublishOrder() {
		return publishOrder;
	}


	public void setPublishOrder(String publishOrder) {
		this.publishOrder = publishOrder;
	}


	public String getUpdateOrder() {
		return updateOrder;
	}


	public void setUpdateOrder(String updateOrder) {
		this.updateOrder = updateOrder;
	}


	public String getOrderPrecedence() {
		return orderPrecedence;
	}


	public void setOrderPrecedence(String orderPrecedence) {
		this.orderPrecedence = orderPrecedence;
	}

}

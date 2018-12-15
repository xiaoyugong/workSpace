package com.parkbobo.manager.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.parkbobo.manager.model.Manager;
import com.parkbobo.model.BerthOrder;
import com.parkbobo.model.UserBalance;
import com.parkbobo.model.Users;
import com.parkbobo.service.BerthOrderService;
import com.parkbobo.service.UserBalanceService;
import com.parkbobo.service.UsersService;
import com.parkbobo.utils.PageBean;

@Controller("berthOrderAction")
@Scope("prototype")
public class BerthOrderAction extends BaseAction {
	@Resource(name="berthOrderService")
	private BerthOrderService berthOrderService;
	@Resource(name="usersService")
	private UsersService usersService;
	@Resource(name="userBalanceService")
	private UserBalanceService userBalanceService;
	/**
	 * 今日订单数
	 */
	private Integer todayOrderCount=0;
	private BerthOrder berthOrder;
	private Long berthorderid;
	private String err;
	
	public String list() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String tod = simpleDateFormat.format(new Date());
		Timestamp s_date = Timestamp.valueOf(tod+" 00:00:00");
		Timestamp e_edate = Timestamp.valueOf(tod+" 23:59:59");
		Manager manager = (Manager) request.getSession().getAttribute("loginManager");
		String area = manager.getArea();
		if(area!=null && !"总部".equals(area)){
			String todayOrderCountHqlString = "select count(o) from BerthOrder as o where "+s_date.getTime()+"<=o.posttime and o.posttime<="+e_edate.getTime()+" and o.status<>3 and o.berthShare.carpark.city like '%"+area+"%'";
			
			String hqlString = "from BerthOrder where isDel=0 and status<>3 and berthShare.carpark.city like '%"+area+"%'";
			if(berthOrder!=null){
				if(this.berthOrder.getDefaultStatus()!=null&&this.berthOrder.getDefaultStatus()<3)
					hqlString += " and defaultStatus="+this.berthOrder.getDefaultStatus();
				if(berthOrder.getBerthShare()!=null)
					hqlString+=" and berthShare.carparkname like '%"+berthOrder.getBerthShare().getCarparkname().trim()+"%'";
				hqlString+=" and upper(carNumber) like '%"+this.berthOrder.getCarNumber().trim().toUpperCase()+"%' " +
						" and telephone like '%"+this.berthOrder.getTelephone().trim()+"%' ";
			}
			hqlString+=" order by posttime desc";
			PageBean<BerthOrder> pageBean = berthOrderService.getPage(hqlString, getPage(), getPageSize());
			todayOrderCount = berthOrderService.dataCount(todayOrderCountHqlString);
			ActionContext.getContext().getValueStack().set("page", pageBean);
			return "list";
		}else if(area!=null && "总部".equals(area)){
			String todayOrderCountHqlString = "select count(o) from BerthOrder o where "+s_date.getTime()+"<=posttime and posttime<="+e_edate.getTime()+" and status<>3";
			
			String hqlString = "from BerthOrder where isDel=0 and status<>3";
			if(berthOrder!=null){
				if(this.berthOrder.getDefaultStatus()!=null&&this.berthOrder.getDefaultStatus()<3)
					hqlString += " and defaultStatus="+this.berthOrder.getDefaultStatus();
				if(berthOrder.getBerthShare()!=null)
					hqlString+=" and berthShare.carparkname like '%"+berthOrder.getBerthShare().getCarparkname().trim()+"%'";
				hqlString+=" and upper(carNumber) like '%"+this.berthOrder.getCarNumber().trim().toUpperCase()+"%' " +
						" and telephone like '%"+this.berthOrder.getTelephone().trim()+"%' ";
			}
			hqlString+=" order by posttime desc";
			PageBean<BerthOrder> pageBean = berthOrderService.getPage(hqlString, getPage(), getPageSize());
			todayOrderCount = berthOrderService.dataCount(todayOrderCountHqlString);
			ActionContext.getContext().getValueStack().set("page", pageBean);
			return "list";
		}else{
			return "list";
		}
		
	}
public String delete() {
	berthOrderService.deleteByHql(getIds());
	this.saveLog("删除订单，id："+getIds());
	return forward("/berthOrder_list");
}

/**
 * 退还保证金
 * @return
 */
public String drawback(){
	BerthOrder berth = berthOrderService.getById(berthorderid);
	//获取违约金
	Long defaultDeposit = berth.getExceedPrice();
	Short isDrawback = berth.getIsDrawback();
	String userid = berth.getUserid();
	if(isDrawback==1){
		err="超期停车费已退";
		return forward("/berthOrder_list");
	}else if(berth.getDefaultStatus()==0){
		err="该订单没有违约";
		return forward("/berthOrder_list");
	}else if(defaultDeposit==0){
		err="该订单没有违约金";
		return forward("/berthOrder_list");
	}else{
		ActionContext actionContext = ActionContext.getContext();
		Map session = actionContext.getSession();
		Manager loginManager = (Manager) session.get("loginManager");	
		berthOrderService.drawback_(defaultDeposit,userid,loginManager,berth);
		return forward("/berthOrder_list");
	}
}

public Integer getTodayOrderCount() {
	return todayOrderCount;
}
public void setTodayOrderCount(Integer todayOrderCount) {
	this.todayOrderCount = todayOrderCount;
}
public String view() {
	this.berthOrder = this.berthOrderService.getById(this.berthOrder.getBerthorderid());
	
	return "view";
}
public BerthOrder getBerthOrder() {
	return berthOrder;
}
public void setBerthOrder(BerthOrder berthOrder) {
	this.berthOrder = berthOrder;
}

public Long getBerthorderid() {
	return berthorderid;
}
public void setBerthorderid(Long berthorderid) {
	this.berthorderid = berthorderid;
}
public String getErr() {
	return err;
}
public void setErr(String err) {
	this.err = err;
}
@Override
public String logModel() {
	// TODO Auto-generated method stub
	return "订单管理";
}

}

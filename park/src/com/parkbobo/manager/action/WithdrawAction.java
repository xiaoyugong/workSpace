package com.parkbobo.manager.action;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.parkbobo.manager.model.Department;
import com.parkbobo.manager.model.Manager;
import com.parkbobo.manager.service.DepartmentService;
import com.parkbobo.model.Notify;
import com.parkbobo.model.UserBalance;
import com.parkbobo.model.WithdrawLog;
import com.parkbobo.service.NotifyService;
import com.parkbobo.service.UserBalanceService;
import com.parkbobo.service.WithdrawLogService;
import com.parkbobo.utils.PageBean;
import com.parkbobo.utils.sort.WithdrawSort;
/**
 * @author Administrator
 *提现记录
 */
@Controller("withdrawAction")
@Scope("prototype")
public class WithdrawAction extends BaseAction {
	/**
	 * 
	 */
	private WithdrawLog withdrawLog;
	private PageBean<WithdrawLog> withdrawPage;
	@Resource(name = "userBalanceService")
	private UserBalanceService userBalanceService;
	@Resource(name="withdrawLogService")
	private WithdrawLogService withdrawLogService;
	
	public String list(){
		Manager manager = (Manager) request.getSession().getAttribute("loginManager");
		String area = manager.getArea();
		if(area!=null && "总部".equals(area)){
			String hqlString = "from WithdrawLog where isDel = 0 ";
			if(withdrawLog != null){
				if(withdrawLog.getStatus()!=null&&withdrawLog.getStatus()!=9)
					hqlString+=" and status = "+withdrawLog.getStatus();
				if(withdrawLog.getUsers()!=null)
					hqlString+=" and users.username like  '%"+withdrawLog.getUsers().getUsername().trim()+"%'";
			}
				hqlString+=" order by posttime desc";
			withdrawPage = withdrawLogService.getPage(hqlString, getPageSize(), getPage());
			Collections.sort(withdrawPage.getList(), new WithdrawSort());
			return "list";
		}else if(area!=null && !"".equals(area)){
			String hqlString = "from WithdrawLog where isDel = 0 and users.area like '%"+ area + "%' ";
			if(withdrawLog != null){
				if(withdrawLog.getStatus()!=null&&withdrawLog.getStatus()!=9)
					hqlString+=" and status = "+withdrawLog.getStatus();
				if(withdrawLog.getUsers()!=null)
					hqlString+=" and users.username like  '%"+withdrawLog.getUsers().getUsername().trim()+"%'";
			}
				hqlString+=" order by posttime desc";
			withdrawPage = withdrawLogService.getPage(hqlString, getPageSize(), getPage());
			Collections.sort(withdrawPage.getList(), new WithdrawSort());
			return "list";
		}else{
			return "list";
		}
	
		}
	
	public String check() {
		withdrawLog = this.withdrawLogService.getById(this.withdrawLog.getWithdrawid());
		
		return "check";
	}
	
	public String update() {
		WithdrawLog wl =  this.withdrawLogService.getById(this.withdrawLog.getWithdrawid());
		wl.setMemo(withdrawLog.getMemo());
		withdrawLogService.update(wl,withdrawLog.getStatus());
		
		this.saveLog("将用户"+wl.getUsers().getUsername()+",审核为:"+convertStart(withdrawLog.getStatus()));
		return forward("/withdraw_list");
	}
	private String convertStart(Integer status){
		HashMap<Integer,String> statusMap = new HashMap();
		statusMap.put(-1, "失败");
		statusMap.put(1, "处理中");
		statusMap.put(2, "成功");
		return statusMap.get(status);
	}
	public String delete() {
		withdrawLogService.delete(getIds());
		saveLog("删除,Id:"+getIds());
		return forward("/withdraw_list");
	}
	public WithdrawLog getWithdrawLog() {
		return withdrawLog;
	}
	public void setWithdrawLog(WithdrawLog withdrawLog) {
		this.withdrawLog = withdrawLog;
	}
	public PageBean<WithdrawLog> getWithdrawPage() {
		return withdrawPage;
	}
	public void setWithdrawPage(PageBean<WithdrawLog> withdrawPage) {
		this.withdrawPage = withdrawPage;
	}

	@Override
	public String logModel() {
		// TODO Auto-generated method stub
		return "提现记录";
	}
	
	
	}

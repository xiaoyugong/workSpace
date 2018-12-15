package com.parkbobo.manager.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.parkbobo.model.OptLogs;
import com.parkbobo.service.OptLogsService;
import com.parkbobo.utils.PageBean;


/**
 * @author Administrator
 *操作日志
 */
@Controller("optLogsAction")
@Scope("prototype")
public class OptLogsAction extends BaseAction {

	@Resource(name="optLogsService")
	private OptLogsService logsService;
	private OptLogs optLogs;
	
	public String list() {
		String hqlString = "from OptLogs where 1 = 1 ";
		if(optLogs!=null){
			hqlString += "and username like '%"+optLogs.getUsername().trim()+"%'" +
					" and fromModel like '%"+optLogs.getFromModel().trim()+"%'";
		}
		hqlString += " order by createTime desc";
		PageBean<OptLogs> page = logsService.page(hqlString,getPage(),getPageSize());
		ActionContext.getContext().getValueStack().set("page", page);
		return "list";
	}
	
	
	
	public String delete() {
		// TODO Auto-generated method stub
		this.logsService.delete(getIds());
		return forward("/optLogs_list");
	}
	
	
	
	
	
	
	
	@Override
	public String logModel() {
		// TODO Auto-generated method stub
		return "操作日志";
	}



	public OptLogs getOptLogs() {
		return optLogs;
	}



	public void setOptLogs(OptLogs optLogs) {
		this.optLogs = optLogs;
	}

}

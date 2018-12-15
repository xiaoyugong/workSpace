package com.parkbobo.groundlock.action;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.parkbobo.groundlock.model.MaintenanceRecords;
import com.parkbobo.groundlock.service.MaintenanceRecordsService;
import com.parkbobo.manager.action.BaseAction;
import com.parkbobo.manager.model.Manager;
import com.parkbobo.utils.MacUtils;
import com.parkbobo.utils.PageBean;

@Controller("maintenanceRecordsAction")
@Scope("prototype")
public class MaintenanceRecordsAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5639754652594985695L;
	private String num;
	private String method;
	private String mac;
	private String worker;
	private String repairman;
	private String recordid;
	private PageBean<MaintenanceRecords> maintenanceRecordsPage;
	
	private MaintenanceRecords maintenanceRecords;
	
	@Resource(name="maintenanceRecordsService")
	private MaintenanceRecordsService maintenanceRecordsService;
	/**
	 * 维修申报列表
	 * @return
	 */
	public String list()
	{
		Manager manager = (Manager) request.getSession().getAttribute("loginManager");
		String area = manager.getArea();
		if(area!=null && "总部".equals(area)){
			String hql = "from MaintenanceRecords as m where 1=1 ";
			if(!StringUtils.isEmpty(mac) && isMac(mac))
			{
				hql += " and m.groundlock.groundlockid = '" + MacUtils.getInstance().macToLong(mac) + "'";
			}
			if(manager.getMemo()!=null){
				hql += " and m.groundlock.carparkname='"+manager.getMemo()+"'";
			}
			hql += " order by m.posttime desc";
			maintenanceRecordsPage = this.maintenanceRecordsService.loadPage(hql,getPageSize(),getPage());
		}else if(area!=null && !"".equals(area)){
			String hql = "from MaintenanceRecords as m where m.groundlock.carpark.city like '%" + area +"%'";;
			if(!StringUtils.isEmpty(mac) && isMac(mac))
			{
				hql += " and m.groundlock.groundlockid = '" + MacUtils.getInstance().macToLong(mac) + "'";
			}
			if(manager.getMemo()!=null){
				hql += " and m.groundlock.carparkname='"+manager.getMemo()+"'";
			}
			hql += " order by m.posttime desc";
			maintenanceRecordsPage = this.maintenanceRecordsService.loadPage(hql,getPageSize(),getPage());
		}
	
		return "list";
	}
	/**
	 * 受理维修
	 * @return
	 * @throws IOException 
	 */
	public String accept() throws Exception
	{
		if(!StringUtils.isEmpty(method) && method.equals("add"))
		{
			Manager manager = (Manager) ActionContext.getContext().getSession().get("loginManager");
			this.maintenanceRecordsService.accept(recordid, repairman);
			HttpServletResponse response = ServletActionContext.getResponse();
			//response.sendRedirect("/park/maintenanceRecords_accept");
			return "{\"status\":\"true\"}";
		}
		if(!StringUtils.isEmpty(num) && num.equals("1")){
			this.maintenanceRecordsService.accept(recordid, repairman);
			return "{\"status\":\"true\"}";
		}
		else
		{
			Manager manager = (Manager) ActionContext.getContext().getSession().get("loginManager");
			//String hql = "update MaintenanceRecords m set m.recordstatus=1,m.repairman=? where m.recordid=?";
			return "accept";
		}
	}
	/**
	 * 维修申报删除
	 * @return
	 */
	public String delete()
	{
		maintenanceRecordsService.bulkDelete(getIds());
		return forward("/maintenanceRecords_list");
	}
	
	private boolean isMac(String macStr)
	{
		String macPattern="[0-9A-F][0-9A-F]:[0-9A-F][0-9A-F]:[0-9A-F][0-9A-F]:[0-9A-F][0-9A-F]:[0-9A-F][0-9A-F]:[0-9A-F][0-9A-F]";
		return Pattern.matches(macPattern, macStr);
	}
	@Override
	public String logModel() {
		return null;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public PageBean<MaintenanceRecords> getMaintenanceRecordsPage() {
		return maintenanceRecordsPage;
	}
	public void setMaintenanceRecordsPage(
			PageBean<MaintenanceRecords> maintenanceRecordsPage) {
		this.maintenanceRecordsPage = maintenanceRecordsPage;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getWorker() {
		return worker;
	}
	public void setWorker(String worker) {
		this.worker = worker;
	}
	public String getRecordid() {
		return recordid;
	}
	public void setRecordid(String recordid) {
		this.recordid = recordid;
	}
	public String getRepairman() {
		return repairman;
	}
	public void setRepairman(String repairman) {
		this.repairman = repairman;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	
}

package com.parkbobo.groundlock.action;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.parkbobo.groundlock.model.RunningRecords;
import com.parkbobo.groundlock.service.RunningRecordsService;
import com.parkbobo.manager.action.BaseAction;
import com.parkbobo.manager.model.Manager;
import com.parkbobo.utils.MacUtils;
import com.parkbobo.utils.PageBean;
@Controller("dynamicRecordsAction")
@Scope("prototype")
public class DynamicRecordsAction extends BaseAction{
	
	private static final long serialVersionUID = 6884989809588966999L;
	private Map<String , String> runActionMap;
	private Date startTime;
	private Date endTime;
	private String mac;
	private String runAction;
	private PageBean<RunningRecords> runningRecordsPage;
	@Resource
	private RunningRecordsService runningRecordsService;
	
	/**
	 * 动态运行记录列表
	 */
	public String list()
	{
		Manager manager = (Manager) request.getSession().getAttribute("loginManager");
		String area = manager.getArea();
		if(area!=null && "总部".equals(area)){
			String hql = "from RunningRecords as r where 1=1 and (r.runAction='200' or r.runAction='201' or r.runAction='115'  or r.runAction='104' or r.runAction='105')";
			if(!StringUtils.isEmpty(mac)  && isMac(mac))
			{
				hql += " and r.groundlock.groundlockid = '" + MacUtils.getInstance().macToLong(mac) + "'";
			}
			if(manager.getMemo()!=null){
				hql += " and r.groundlock.carparkname='"+manager.getMemo()+"'";
			}
			if(manager.getMemo()!=null){
				hql += " and r.groundlock.carparkname='"+manager.getMemo()+"'";
			}
			if(!StringUtils.isEmpty(runAction)  && !runAction.equals(""))
			{
				hql += " and r.runAction = '" + runAction + "'";
			}
			if(startTime != null)
			{
				hql += " and r.recordtime >= '" + startTime.getTime() + "'";
			}
			if(endTime != null)
			{
				hql += " and r.recordtime <= '" + endTime.getTime() + "'";
			}
			hql += " order by r.recordtime desc";
			runningRecordsPage = this.runningRecordsService.loadPage(hql, getPageSize(), getPage());
			createRunActionMap();
		}else if(area!=null && !"".equals(area)){
			String hql = "from RunningRecords as r where r.groundlock.carpark.city like '%" + area +"%' and (r.runAction='200' or r.runAction='201' or r.runAction='115'  or r.runAction='104' or r.runAction='105')";
			if(!StringUtils.isEmpty(mac)  && isMac(mac))
			{
				hql += " and r.groundlock.groundlockid = '" + MacUtils.getInstance().macToLong(mac) + "'";
			}
			if(manager.getMemo()!=null){
				hql += " and r.groundlock.carparkname='"+manager.getMemo()+"'";
			}
			if(manager.getMemo()!=null){
				hql += " and r.groundlock.carparkname='"+manager.getMemo()+"'";
			}
			if(!StringUtils.isEmpty(runAction)  && !runAction.equals(""))
			{
				hql += " and r.runAction = '" + runAction + "'";
			}
			if(startTime != null)
			{
				hql += " and r.recordtime >= '" + startTime.getTime() + "'";
			}
			if(endTime != null)
			{
				hql += " and r.recordtime <= '" + endTime.getTime() + "'";
			}
			hql += " order by r.recordtime desc";
			runningRecordsPage = this.runningRecordsService.loadPage(hql, getPageSize(), getPage());
			createRunActionMap();
		}
		
		return "list";
	}
	/**
	 * 删除动态运行记录
	 * @param macStr
	 * @return
	 */
	public String delete()
	{
		this.runningRecordsService.bulkDelete(getIds());
		return forward("/dynamicRecords_list");
	}
	private boolean isMac(String macStr)
	{
		String macPattern="[0-9A-F][0-9A-F]:[0-9A-F][0-9A-F]:[0-9A-F][0-9A-F]:[0-9A-F][0-9A-F]:[0-9A-F][0-9A-F]:[0-9A-F][0-9A-F]";
		return Pattern.matches(macPattern, macStr);
	}
	private void createRunActionMap()
	{
		runActionMap = new LinkedHashMap<String, String>();
		runActionMap.put("200","服务器升锁");
		runActionMap.put("201","服务器降锁");
		runActionMap.put("104","蓝牙升锁");
		runActionMap.put("105","蓝牙降锁");
		runActionMap.put("115","上传报警");
		runActionMap.put("122","异常搬动报警");
		runActionMap.put("123","地锁冷启动");
	}
	@Override
	public String logModel() {
		return null;
	}
	public PageBean<RunningRecords> getRunningRecordsPage() {
		return runningRecordsPage;
	}
	public void setRunningRecordsPage(PageBean<RunningRecords> runningRecordsPage) {
		this.runningRecordsPage = runningRecordsPage;
	}
	public String getRunAction() {
		return runAction;
	}
	public void setRunAction(String runAction) {
		this.runAction = runAction;
	}
	public Map<String, String> getRunActionMap() {
		return runActionMap;
	}
	public void setRunActionMap(Map<String, String> runActionMap) {
		this.runActionMap = runActionMap;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}

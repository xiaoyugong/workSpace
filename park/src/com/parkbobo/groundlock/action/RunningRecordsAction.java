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
@Controller("runningRecordsAction")
@Scope("prototype")
public class RunningRecordsAction extends BaseAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7375028690634040501L;
	private Map<String , String> runActionMap;
	private Date startTime;
	private Date endTime;
	private String mac;
	private String runAction;
	private PageBean<RunningRecords> runningRecordsPage;
	@Resource
	private RunningRecordsService runningRecordsService;
	
	/**
	 * 运行记录列表
	 */
	public String list()
	{
		Manager manager = (Manager) request.getSession().getAttribute("loginManager");
		String area = manager.getArea();
		if(area!=null && "总部".equals(area)){
			String hql = "from RunningRecords as r where 1=1";
			if(!StringUtils.isEmpty(mac)  && isMac(mac))
			{
				hql += " and r.groundlock.groundlockid = '" + MacUtils.getInstance().macToLong(mac) + "'";
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
			String hql = "from RunningRecords as r where r.groundlock.carpark.city like '%" + area +"%' ";
			if(manager.getMemo()!=null){
				hql += " and r.groundlock.carparkname='"+manager.getMemo()+"'";
			}
			if(!StringUtils.isEmpty(mac)  && isMac(mac))
			{
				hql += " and r.groundlock.groundlockid = '" + MacUtils.getInstance().macToLong(mac) + "'";
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
	 * 删除运行记录
	 * @param macStr
	 * @return
	 */
	public String delete()
	{
		this.runningRecordsService.bulkDelete(getIds());
		return forward("/runningRecords_list");
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
		runActionMap.put("202","修改蓝牙列表");
		runActionMap.put("203","修改蓝牙口令");
		runActionMap.put("204","修改可唤醒地锁电话");
		runActionMap.put("205","修改保护电量");
		runActionMap.put("206","修改IP列表");
		runActionMap.put("207","修改自动唤醒分钟数");
		runActionMap.put("208","修改地锁SIM卡号");
		runActionMap.put("100","地锁上线");
		runActionMap.put("101","地锁下线");
		runActionMap.put("102","地锁心跳");
		runActionMap.put("103","地锁休眠");
		runActionMap.put("104","蓝牙升锁");
		runActionMap.put("105","蓝牙降锁");
		runActionMap.put("106","上传蓝牙列表");
		runActionMap.put("107","上传蓝牙口令");
		runActionMap.put("108","上传电量");
		runActionMap.put("109","上传地锁信息");
		runActionMap.put("110","上传SIM卡号");
		runActionMap.put("111","上传保护电量");
		runActionMap.put("112","上传IP列表");
		runActionMap.put("113","上传当前状态");
		runActionMap.put("114","上传时间");
		runActionMap.put("115","上传报警");
		runActionMap.put("116","上传可唤醒地锁电话");
		runActionMap.put("117","上传地锁唤醒");
		runActionMap.put("118","上传SIM卡型号强度");
		runActionMap.put("119","短信唤醒");
		runActionMap.put("120","电话唤醒");
		runActionMap.put("121","上传自动唤醒分钟数");
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

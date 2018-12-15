package com.parkbobo.groundlock.action;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.parkbobo.groundlock.model.Groundlock;
import com.parkbobo.groundlock.service.GroundlockService;
import com.parkbobo.manager.action.BaseAction;
import com.parkbobo.manager.model.Manager;
import com.parkbobo.model.Users;
import com.parkbobo.service.UsersService;
import com.parkbobo.utils.MacUtils;
import com.parkbobo.utils.PageBean;
@Controller("groundlockAction")
@Scope("prototype")
public class GroundlockAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2766600836575063811L;
	private String mac;
	private String carparkName;
	private String berthName;
	private String telephone;
	private Integer onlineStatus;
	private PageBean<Groundlock> groundlockPage;
	private Groundlock groundlock;
	private String method;
	private String err;
	
	@Resource
	private GroundlockService groundlockService;
	@Resource
	private UsersService usersService;
	/**
	 * 添加地锁
	 * @return
	 */
	public String add()
	{
		if(!StringUtils.isEmpty(method) && method.equals("add"))
		{
			groundlock.setGroundlockid(MacUtils.getInstance().macToLong(groundlock.getGroundlockMac()) + "");
			groundlock.setBatteryPower(100);
			groundlock.setGroundlockStatus(0);
			groundlock.setOnlineStatus(0);
			groundlock.setSimIntensity("-90");
			groundlock.setControlType(1);
			groundlock.setIsDel(0);
			groundlock.setConstructTime(groundlock.getConstructTimeToDate().getTime());
			groundlock.setIsBluetoothLift(1);
			groundlock.setWeakupNum(0);
			groundlock.setPosttime(System.currentTimeMillis());
			groundlock.setAutoWeakupTime(6l);
			this.groundlockService.add(groundlock);
			//添加完成后并绑定车位和用户
			if(StringUtils.isNotBlank(carparkName) && StringUtils.isNotBlank(carparkName)){
				err = this.groundlockService.bindCarpark(carparkName,berthName,groundlock.getGroundlockid()) + "";	
				if(!err.equals("0")){
					return "add";
				}else{
					return forward("/groundlock_list");	
				}
			}else{
				return forward("/groundlock_list");				
			}
		}
		else
		{
			
			return "add";
		}
	}
	/**
	 * 删除地锁
	 * @return
	 */
	public String delete()
	{
		groundlockService.bulkDelete(getIds());
		return forward("/groundlock_list");
	}
	/**
	 * 地锁绑定用户
	 * @throws IOException 
	 */
	public String bindUser() throws IOException
	{
		if(!StringUtils.isEmpty(method) && method.equals("edit"))
		{
			err = this.groundlockService.bindUser(telephone,mac)+"";
		}
		else
		{
			Users users = this.usersService.getUserById(this.groundlockService.getById(mac).getUserid());
			if(users != null)
			{
				telephone = users.getUsername();
			}
		}
		return "bindUser";
	}
	/**
	 * 绑定停车场车位
	 * @return
	 * @throws IOException
	 */
	public String bindCarpark() throws IOException
	{
		if(!StringUtils.isEmpty(method) && method.equals("edit"))
		{
			err = this.groundlockService.bindCarpark(carparkName,berthName,mac) + "";
		}
		else
		{
			Groundlock groundlock = this.groundlockService.getById(mac);
			carparkName = groundlock.getCarparkname();
			berthName = groundlock.getBerthname();
		}
		return "bindCarpark";
	}
	/**
	 * 地锁列表
	 * @return
	 */
	public String list()
	{
		Manager manager = (Manager) request.getSession().getAttribute("loginManager");
		String area = manager.getArea();
		if(manager.getMemo()!=null){
			carparkName = manager.getMemo();
		}
		if(area!=null && "总部".equals(area)){
			String hql = "from Groundlock as g where 1=1";
			if(!StringUtils.isEmpty(mac) && isMac(mac))
			{
				hql += " and g.groundlockid = '" + MacUtils.getInstance().macToLong(mac) + "'";
			}
			if(!StringUtils.isEmpty(carparkName))
			{
				hql += " and g.carparkname like '%" + carparkName + "%'";
			}
			if(!StringUtils.isEmpty(berthName))
			{
				hql += " and g.berthname like '%" + berthName + "%'";
			}
			if(!StringUtils.isEmpty(telephone))
			{
				hql += " and g.simNum like '%" + telephone + "%'";
			}
			if(onlineStatus != null)
			{
				hql += " and g.onlineStatus = " + onlineStatus;
			}
			hql += " order by g.batteryPower asc";
			groundlockPage = this.groundlockService.loadPage(hql, getPageSize(), getPage());
			
		}else if(area!=null && !"".equals(area)){
			String hql = "from Groundlock as g where g.carpark.city like '%" + area +"%' ";
			if(!StringUtils.isEmpty(mac) && isMac(mac))
			{
				hql += " and g.groundlockid = '" + MacUtils.getInstance().macToLong(mac) + "'";
			}
			if(!StringUtils.isEmpty(carparkName))
			{
				hql += " and g.carparkname like '%" + carparkName + "%'";
			}
			if(!StringUtils.isEmpty(berthName))
			{
				hql += " and g.berthname like '%" + berthName + "%'";
			}
			if(!StringUtils.isEmpty(telephone))
			{
				hql += " and g.simNum like '%" + telephone + "%'";
			}
			if(onlineStatus != null)
			{
				hql += " and g.onlineStatus = " + onlineStatus;
			}
			hql += " order by g.batteryPower asc";
			groundlockPage = this.groundlockService.loadPage(hql, getPageSize(), getPage());

		}
		return "list";
	}
	public String setting()
	{
		if(!StringUtils.isEmpty(method) && method.equals("edit"))
		{
			String groundlockNumber = groundlock.getGroundlockNumber();
			System.out.println(groundlockNumber);
			groundlockService.update(groundlock);
			return forward("/groundlock_list");
		}
		else
		{
			groundlock = this.groundlockService.getById(mac);
			return "setting";
		}
	}
	private boolean isMac(String macStr)
	{
		String macPattern="[0-9A-F][0-9A-F]:[0-9A-F][0-9A-F]:[0-9A-F][0-9A-F]:[0-9A-F][0-9A-F]:[0-9A-F][0-9A-F]:[0-9A-F][0-9A-F]";
		return Pattern.matches(macPattern, macStr);
	}
	public PageBean<Groundlock> getGroundlockPage() {
		return groundlockPage;
	}
	public void setGroundlockPage(PageBean<Groundlock> groundlockPage) {
		this.groundlockPage = groundlockPage;
	}

	@Override
	public String logModel() {
		return null;
	}
	public String getCarparkName() {
		return carparkName;
	}
	public void setCarparkName(String carparkName) {
		this.carparkName = carparkName;
	}
	public String getBerthName() {
		return berthName;
	}
	public void setBerthName(String berthName) {
		this.berthName = berthName;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public Integer getOnlineStatus() {
		return onlineStatus;
	}
	public void setOnlineStatus(Integer onlineStatus) {
		this.onlineStatus = onlineStatus;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public Groundlock getGroundlock() {
		return groundlock;
	}
	public void setGroundlock(Groundlock groundlock) {
		this.groundlock = groundlock;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getErr() {
		return err;
	}
	public void setErr(String err) {
		this.err = err;
	}
}

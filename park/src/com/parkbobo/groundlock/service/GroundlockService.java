package com.parkbobo.groundlock.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.LockMode;
import org.springframework.stereotype.Component;

import com.parkbobo.dao.BerthShareDao;
import com.parkbobo.dao.CarparkBerthPolygonDao;
import com.parkbobo.dao.CarparkDao;
import com.parkbobo.dao.CarparkFloorDao;
import com.parkbobo.dao.UsersDao;
import com.parkbobo.groundlock.dao.GroundlockDao;
import com.parkbobo.groundlock.model.Groundlock;
import com.parkbobo.model.BerthShare;
import com.parkbobo.model.Carpark;
import com.parkbobo.model.CarparkBerthPolygon;
import com.parkbobo.model.Users;
import com.parkbobo.utils.PageBean;

@Component("groundlockService")
public class GroundlockService {
	@Resource(name="groundlockDaoImpl")
	private GroundlockDao groundlockDao;
	@Resource(name="usersDaoImpl")
	private UsersDao usersDao;
	@Resource(name="carparkBerthPolygonDaoImpl")
	private CarparkBerthPolygonDao carparkBerthPolygonDao;
	@Resource(name="berthShareDaoImpl")
	private BerthShareDao berthShareDao;
	@Resource(name="carparkFloorDaoImpl")
	private CarparkFloorDao carparkFloorDao;
	@Resource(name="carparkDaoImpl")
	private CarparkDao carparkDao;

	public PageBean<Groundlock> loadPage(String hql, int pageSize, int page) {
		return this.groundlockDao.pageQuery(hql, pageSize, page);
	}

	public Groundlock getById(String mac) {
		return this.groundlockDao.getById(mac);
	}
	

	public void update(Groundlock g) {
		Groundlock groundlock = this.groundlockDao.get(g.getGroundlockid(), LockMode.UPGRADE);
		groundlock.setFirmwareVersion(g.getFirmwareVersion());
		groundlock.setSoftVersion(g.getSoftVersion());
		groundlock.setConstructId(g.getConstructId());
		groundlock.setConstructTime(g.getConstructTimeToDate().getTime());
		groundlock.setBatteryModel(g.getBatteryModel());
		groundlock.setProtectionPower(g.getProtectionPower());
		groundlock.setSimNum(g.getSimNum());
		groundlock.setBluetoothPassword(g.getBluetoothPassword());
		if(g.getGroundlockNumber()!=null){
			groundlock.setGroundlockNumber(g.getGroundlockNumber());
		}	
		groundlock.setLocked_type(g.getLocked_type());
		groundlock.setBrand(g.getBrand());
		this.groundlockDao.update(groundlock);
	}
	/**
	 * 绑定用户
	 * @param telephone
	 * @param mac
	 * @return 0: 绑定成功  -1：用户不存在绑定失败
	 */
	public int bindUser(String telephone, String mac) {
		Users users = this.usersDao.getUniqueByProperty("username", telephone);
		if(users != null)
		{
			Groundlock groundlock = getById(mac);
			groundlock.setUserid(users.getUserid());
			this.groundlockDao.merge(groundlock);
			return 0;
		}
		else
		{
			return -1;
		}
	}
	/**
	 * 绑定停车场
	 * @param carparkName
	 * @param berthName
	 * @param mac
	 * @return 0: 绑定成功  1：车位不存在绑定失败
	 */
	public int bindCarpark(String carparkName, String berthName, String mac) {
		String[] propertyNames = {"carpark.name","name"};
		Object[] values = {carparkName, berthName};       
		CarparkBerthPolygon carparkBerthPolygon = this.carparkBerthPolygonDao.getUniqueByPropertys(propertyNames, values);
		//Long gid = carparkBerthPolygon.getGid();
		String hql = "from Carpark as c where c.name = '"+carparkName+"'";
		List<Carpark> carparks = carparkDao.getByHQL(hql);
		if(carparkBerthPolygon != null)
		{
			Groundlock groundlock = getById(mac);
			if(groundlock.getBerthid() != null && groundlock.getBerthid().equals(carparkBerthPolygon.getGid()) && carparkBerthPolygon.getIsGroundlock() == 1)
			{
				return 0;
			}
			if(groundlock.getBerthid()==null){
				List<BerthShare> berthShares = this.berthShareDao.getByHQL("from BerthShare as b where b.carparkid = " + groundlock.getCarparkid() + " and b.berthnum = '"+groundlock.getBerthname()+"'");
				for (BerthShare berthShare : berthShares) {
					berthShare.setIsGroundlock(0);
					berthShare.setGroundlockid(null);
					this.berthShareDao.merge(berthShare);
				}
			}
			if(groundlock.getBerthid() != null && !groundlock.getBerthid().equals(carparkBerthPolygon.getGid()))
			{
				String[] ps = {"isGroundlock","groundlockid"};
				Object[] vs = {0, null};  
				this.carparkBerthPolygonDao.localUpdateOneFields(groundlock.getBerthid(), ps, vs);
				List<BerthShare> berthShares = this.berthShareDao.getByHQL("from BerthShare as b where b.berthid = " + groundlock.getBerthid());
				for (BerthShare berthShare : berthShares) {
					berthShare.setIsGroundlock(0);
					berthShare.setGroundlockid(null);
					this.berthShareDao.merge(berthShare);
				}
			}
			List<Groundlock> groundlocks = this.groundlockDao.getByHQL("from Groundlock as g where g.berthid = " + carparkBerthPolygon.getGid());
			for (Groundlock g : groundlocks) {
				g.setCarparkid(null);
				g.setCarparkname(null);
				g.setBerthid(null);
				g.setBerthname(null);
				g.setFloorid(null);
				g.setFloorname(null);
				this.groundlockDao.merge(g);
			}
			groundlock.setCarparkid(carparkBerthPolygon.getCarpark().getCarparkid());
			groundlock.setFloorid(carparkBerthPolygon.getFloorid());
			groundlock.setFloorname(carparkFloorDao.get(carparkBerthPolygon.getFloorid()).getName());
			groundlock.setCarparkname(carparkName);
			groundlock.setBerthid(carparkBerthPolygon.getGid());
			groundlock.setBerthname(berthName);
			this.groundlockDao.merge(groundlock);
			
			carparkBerthPolygon.setIsGroundlock(1);
			carparkBerthPolygon.setGroundlockid(mac);
			this.carparkBerthPolygonDao.merge(carparkBerthPolygon);
			
			List<BerthShare> berthShares = this.berthShareDao.getByHQL("from BerthShare as b where b.berthid = " + carparkBerthPolygon.getGid());
			for (BerthShare berthShare : berthShares) {
				berthShare.setIsGroundlock(1);
				berthShare.setGroundlockid(mac);
				this.berthShareDao.merge(berthShare);
			}
			return 0;
		}
		
		else if(carparks!=null && carparks.size()>0 && carparks.get(0).getMaptype()!=1){
			Groundlock groundlock = getById(mac);
			if(groundlock.getBerthname()!=null && groundlock.getBerthname().equals(berthName))
			{
				return 0;
			}
			
			if(groundlock.getBerthname()!=null && !groundlock.getBerthname().equals(berthName));
			{
				String[] ps = {"isGroundlock","groundlockid"};
				Object[] vs = {0, null};  
				this.carparkBerthPolygonDao.localUpdateOneFields(groundlock.getBerthid(), ps, vs);
				List<BerthShare> berthShares = this.berthShareDao.getByHQL("from BerthShare as b where b.carparkid = " + groundlock.getCarparkid() + " and b.berthnum = '"+groundlock.getBerthname()+"'");
				for (BerthShare berthShare : berthShares) {
					berthShare.setIsGroundlock(0);
					berthShare.setGroundlockid(null);
					this.berthShareDao.merge(berthShare);
				}
			}
			List<Groundlock> groundlocks = this.groundlockDao.getByHQL("from Groundlock as g where g.carparkid = " + carparks.get(0).getCarparkid() + " and g.berthname = '" +berthName+ "'");
			for (Groundlock g : groundlocks) {
				g.setCarparkid(null);
				g.setCarparkname(null);
				g.setBerthid(null);
				g.setBerthname(null);
				g.setFloorid(null);
				g.setFloorname(null);
				this.groundlockDao.merge(g);
			}
			groundlock.setCarparkid(carparks.get(0).getCarparkid());
			groundlock.setFloorid(null);
			groundlock.setFloorname(null);
			groundlock.setCarparkname(carparkName);
			groundlock.setBerthid(null);
			groundlock.setBerthname(berthName);
			this.groundlockDao.merge(groundlock);
			
			//carparkBerthPolygon.setIsGroundlock(1);
			//carparkBerthPolygon.setGroundlockid(mac);
			//this.carparkBerthPolygonDao.merge(carparkBerthPolygon);
			
			List<BerthShare> berthShares = this.berthShareDao.getByHQL("from BerthShare as b where b.carparkid = " + carparks.get(0).getCarparkid() + " and b.berthnum = '"+berthName+"'");
			for (BerthShare berthShare : berthShares) {
				berthShare.setIsGroundlock(1);
				berthShare.setGroundlockid(mac);
				this.berthShareDao.merge(berthShare);
			}
			return 0;
		}
		else
		{
			return -1;
		}
		
	}
	/**
	 * 删除地锁
	 * @param ids
	 */
	public void bulkDelete(String ids) {
		if(ids.length() > 0)
		{
			String[] strs = ids.split(",");
			for (String string : strs) {
				if(StringUtils.isNotBlank(string))
				{
					String[] o = {string};
					this.berthShareDao.bulkUpdate("update BerthShare as b set b.isGroundlock = 0 ,b.groundlockid = null where b.groundlockid = ?", o);
					this.carparkBerthPolygonDao.bulkUpdate("update CarparkBerthPolygon as b set b.isGroundlock = 0 ,b.groundlockid = null where b.groundlockid = ?", o);
					this.groundlockDao.delete(string);
				}
			}
//			String[] ps = {"isGroundlock","groundlockid"};
//			Object[] vs = {0, null};  
//			this.carparkBerthPolygonDao.localUpdateOneFields(groundlock.getBerthid(), ps, vs);
//			List<BerthShare> berthShares = this.berthShareDao.getByHQL("from BerthShare as b where b.berthid = " + groundlock.getBerthid());
//			for (BerthShare berthShare : berthShares) {
//				berthShare.setIsGroundlock(0);
//				berthShare.setGroundlockid(null);
//				this.berthShareDao.merge(berthShare);
//			}
			
//			List<BerthShare> berthShares = this.berthShareDao.getByHQL("from BerthShare as b where b.berthid = " + carparkBerthPolygon.getGid());
//			for (BerthShare berthShare : berthShares) {
//				berthShare.setIsGroundlock(1);
//				berthShare.setGroundlockid(mac);
//				this.berthShareDao.merge(berthShare);
//			}
			
//			this.groundlockDao.bulkDelete(strs);
		}
	}

	public void add(Groundlock groundlock) {
		this.groundlockDao.merge(groundlock);
	}
}

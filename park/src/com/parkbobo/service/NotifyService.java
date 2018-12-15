package com.parkbobo.service;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import com.igetui.util.PushMessageUtil;
import com.parkbobo.model.BerthShare;
import com.parkbobo.model.Carpark;
import com.parkbobo.model.Notify;
import com.parkbobo.model.Users;
import com.parkbobo.utils.MD5;
import com.parkbobo.utils.PageBean;
import com.parkbobo.utils.SMSSend;
import com.parkbobo.utils.ServiceUtil;
import com.parkbobo.dao.BerthShareDao;
import com.parkbobo.dao.NotifyDao;
import com.sun.istack.internal.FinalArrayList;
@Component("notifyService")
public class NotifyService {
	@Resource(name="notifyDaoImpl")
	private NotifyDao notifyDao;
	@Resource(name="berthShareDaoImpl")
	private BerthShareDao berthShareDao;
	public List<Notify> getByHql(String hql){
		return this.notifyDao.getByHQL(hql);
	}
	/**
	 * 分页查询
	 * @param hql
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public PageBean<Notify> getPage(String hql, int page, int pageSize)
	{
		return this.notifyDao.pageQuery(hql, pageSize, page);
	}
	
	/**
	 * 根据主键查询
	 * @param notifyid
	 * @return
	 */
	public Notify getById(Long notifyid) {
		return this.notifyDao.get(notifyid);
	}
	
	public void bulkDelete(String ids) {
		if(ids.length() > 0)
		{
//			String[] strs = ids.split(",");
//			Long[] idArr = new Long[strs.length];
//			for (int i=0; i< strs.length; i++) {
//				idArr[i] = Long.valueOf(strs[i]);
//			}
			  String sqlString = "update Notify set isDel=1 where notifyid in ("+ids+")";
			this.notifyDao.deleteByHql(sqlString);
		}
	}
	/**
	 * 根据通知ID和用户ID查询
	 * @param notifyid
	 * @return
	 */
	public Notify getByIdAndUserid(Long notifyid, String userid) {
		String[] propertyNames = {"notifyid","users.userid"};
		Object[] values = {notifyid, userid};
		return this.notifyDao.getUniqueByPropertys(propertyNames, values);
	}
	//向指定用户集合发送通知
	/**
	 * @param notify
	 * @param users  用户集合
	 * @param notifysendMode 1短信 2推送和短信 0推送
	 * @param notifyType   0：停车场，1：用户 2：全部
	 */
	public void add(final Notify notify,final List<Users> users, Integer notifysendMode,Integer notifyType) {
		  List<Notify> notifies = new ArrayList<Notify>();
		  if(notifyType==2){
			  notifyDao.add(notify);
		  }
			for(Users user:users){
				Notify notify2 = new  Notify();
				notify2.setTitle(notify.getTitle());
				notify2.setContent(notify.getContent());
				notify2.setIsDel(0);
				notify2.setType(0);
				notify2.setIsRead(0);
				notify2.setPosttime(System.currentTimeMillis());
				notify2.setUsers(user);
				
				if(notifysendMode!=1){
					if(notifyType!=2){
						add(notify2);
						PushMessageUtil.getDefaultInstance().pushTransmission
						(user.getClientid(),notify.getTitle(),notify.getContent(),notify2.getNotifyid());
					}else {
						PushMessageUtil.getDefaultInstance().pushTransmission
						(user.getClientid(),notify.getTitle(),notify.getContent(),notify.getNotifyid());
					}
				}
				else if(notifyType!=2){
						notifies.add(notify2);
					}
		}
		//如果向全部用户发送通知则只保存一条notify
		if (notifysendMode==1) {
				for(Users u:users){
					SMSSend.getDefaultInstance().iuyiSendSms(notify.getContent(), u.getUsername());
					}
				if(notifyType==2){
					notifyDao.add(notify);
				}else {
					notifyDao.bulksave(notifies);
				}
				
		}
		
	}
	
	//向指定停车场集合发送通知
	public void addCarparkNotify(final Notify notify,List<Carpark> carparks, Integer notifysendMode) {
		
		//得到停车场的id集合
		List<Long> carparkIdList = new ArrayList<Long>();
		 List<Notify> notifies = new ArrayList<Notify>();
		for(Carpark carpark:carparks){
			carparkIdList.add(carpark.getCarparkid());
		}
		//得到停车场分享情况
		String hql = "select distinct b.users from BerthShare b where b.carparkid in (:in) and b.isDel=0 ";
		final List<Users> list = this.berthShareDao.bulkFind(hql, carparkIdList.toArray());
		//根据分享情况得到用户
//		 final List<String> users = new ArrayList<String>();
		for(Users u:list){
//			users.add(u.getClientid());
			
			Notify notify2 = new  Notify();
			notify2.setTitle(notify.getTitle());
			notify2.setContent(notify.getContent());
			notify2.setIsDel(0);
			notify2.setType(0);
			notify2.setIsRead(0);
			notify2.setPosttime(System.currentTimeMillis());
			notify2.setUsers(u);
			if(notifysendMode!=1){
				add(notify2);
				PushMessageUtil.getDefaultInstance().pushTransmission
				(u.getClientid(),notify.getTitle(),notify.getContent(),notify2.getNotifyid());
			}else {
				notifies.add(notify2);
			}
		}
		if (notifysendMode==1) {
			Thread  smsthread1 = new Thread () {
				public void run() {
					
					for(Users u:list){
						SMSSend.getDefaultInstance().iuyiSendSms(notify.getContent(), u.getUsername());
					}
				}
			};
			smsthread1.start();
			notifyDao.bulksave(notifies);
	}
	}
	public void add(Notify notify){
		notifyDao.add(notify);
	}
	
	
}
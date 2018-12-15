package com.parkbobo.service;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import javax.mail.util.SharedByteArrayInputStream;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.igetui.util.PushMessageUtil;
import com.parkbobo.dao.BerthShareDao;
import com.parkbobo.dao.CarparkAuthenticationDao;
import com.parkbobo.dao.NotifyDao;
import com.parkbobo.model.BerthShare;
import com.parkbobo.model.CarparkAuthentication;
import com.parkbobo.model.Notify;
import com.parkbobo.utils.PageBean;
@Component("carparkAuthenticationService")
public class CarparkAuthenticationService {
	
	@Resource(name="carparkAuthenticationDaoImpl")
	private CarparkAuthenticationDao carparkAuthenticationDao;
	@Resource(name="berthShareDaoImpl")
	private BerthShareDao berthShareDao;
	@Resource(name="notifyDaoImpl")
	private NotifyDao notifyDao;
	
	public static Map<Short,String> statusMap;
	static{
		statusMap = new HashMap();
		statusMap.put((short)1,"已通过" );
		statusMap.put((short)2,"未通过" );
	}
	public List<CarparkAuthentication> getByHql(String hql){
		return this.carparkAuthenticationDao.getByHQL(hql);
	}

	public boolean add(CarparkAuthentication carparkAuthentication) {
		try {
			this.carparkAuthenticationDao.merge(carparkAuthentication);
			BerthShare berthShare = berthShareDao.get(carparkAuthentication.getShareid());
			berthShare.setIsAuthentication((short)1);
			berthShareDao.merge(berthShare);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public PageBean<CarparkAuthentication> loadPage(String hql, int pageSize,
			int page) {
		// TODO Auto-generated method stub
		return carparkAuthenticationDao.pageQuery(hql, pageSize, page);
	}

	public void deleteByHql(String ids) {
		// TODO Auto-generated method stub
//		String sqlString = "update CarparkAuthentication set isDel=1 where authenticationid in ("+ids+")";
		if(ids.length()>=1){
			String[] strs = ids.split(",");
			Long[] idArr = new Long[strs.length];
			for (int i=0; i< strs.length; i++) {
				idArr[i] = Long.valueOf(strs[i]);
			}
			
			carparkAuthenticationDao.bulkDelete(idArr);
		}
		
		
	}

	public CarparkAuthentication getById(Long authenticationid) {
		// TODO Auto-generated method stub
		return carparkAuthenticationDao.get(authenticationid);
	}

	public void update(CarparkAuthentication carparkAuth) {
		// TODO Auto-generated method stub
//		Share   2 通过  3未通过
//		carparkAuth  1 通过    2未通过
		BerthShare bsBerthShare = berthShareDao.getById(carparkAuth.getShareid());
		if(bsBerthShare!=null){
			bsBerthShare.setIsAuthentication((short)(carparkAuth.getStatus()+1));
			berthShareDao.update(bsBerthShare);
		}
		carparkAuthenticationDao.update(carparkAuth);
		String notifyString = "您的车位【"+carparkAuth.getCarparkname()+carparkAuth.getBerthnum()+
		"号】认证"+statusMap.get(carparkAuth.getStatus())+"。审核备注:"+(carparkAuth.getMemo().trim().equals("")?"无":carparkAuth.getMemo());
	
		Notify notify = new Notify(null, carparkAuth.getUsers(), "车位认证通知", notifyString, 0, new Date().getTime() , 0,0, null);
		notify = notifyDao.add(notify);
		notifyDao.add(notify);
		//发送通知
		PushMessageUtil.getDefaultInstance()
		.pushTransmission(carparkAuth.getUsers().getClientid(),
				"车位认证通知",notifyString,notify.getNotifyid());
	}
	public int totalCount(String carparkAuthenticationhqlString) {
		// TODO Auto-generated method stub
		return notifyDao.totalCount(carparkAuthenticationhqlString);
	}
	
}
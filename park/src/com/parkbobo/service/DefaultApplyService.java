package com.parkbobo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Component;

import com.igetui.util.PushMessageUtil;
import com.parkbobo.dao.BerthOrderDao;
import com.parkbobo.dao.DefaultApplyDao;
import com.parkbobo.dao.NotifyDao;
import com.parkbobo.model.BerthOrder;
import com.parkbobo.model.BerthShare;
import com.parkbobo.model.Carpark;
import com.parkbobo.model.DefaultApply;
import com.parkbobo.model.Notify;
import com.parkbobo.model.Users;
import com.parkbobo.utils.PageBean;

@Component("defaultApplyService")
public class DefaultApplyService {
	@Resource(name="defaultApplyDaoImpl")
	private DefaultApplyDao defaultApplyDao;
	@Resource(name="notifyDaoImpl")
	private NotifyDao notifyDao;
	@Resource(name="berthOrderDaoImpl")
	private BerthOrderDao berthOrderDao;
	@Resource
	private BerthShareService berthShareService;
	@Resource
	private UsersService usersService;
	public  Map<Short,String> statusMap;
	public  Map<Short,String> typeMap;
	
	public DefaultApplyService(){
		statusMap = new HashedMap();
		statusMap.put((short)1,"不属实被驳回" );
		statusMap.put((short)2,"属实,我们将尽快处理" );
		statusMap.put((short)3,"属实，我们正在协商中" );
		typeMap = new HashedMap();
		typeMap.put((short)0, "车位被恶意入驻");
		typeMap.put((short)1, "无法进去停车");
		typeMap.put((short)2, "超期停车");
		
	}
	public List<DefaultApply> getByHql(String hql)
	{
		return this.defaultApplyDao.getByHQL(hql);
	}
	public boolean add(DefaultApply defaultApply) {
		try {
			this.defaultApplyDao.merge(defaultApply);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	public PageBean<DefaultApply> getPageBean(String hql, int page, int pageSize) {
		// TODO Auto-generated method stub
		return defaultApplyDao.pageQuery(hql, pageSize, page);
	}
	public void delete(String ids) {
		String[] arr = ids.split(",");
		Long[] newArr = new Long[arr.length];
		for(int i = 0;i<arr.length;i++){
			newArr[i] = Long.valueOf(arr[i]);
		}
		defaultApplyDao.bulkDelete(newArr);
	}
	public DefaultApply getById(Long applyid) {
		return defaultApplyDao.getById(applyid);
	}
	public void update(DefaultApply apply) {
		defaultApplyDao.update(apply);
		if(apply.getType() == 2 )
		{
			BerthOrder berthOrder = berthOrderDao.get(apply.getBerthorderid());
			berthOrder.setDefaultStatus((short)2);
			if(apply.getStatus() == 2)
			{
				BerthShare berthShare = this.berthShareService.getById(berthOrder.getBerthShare().getShareid());
				String intro = "车牌号为("+ berthOrder.getCarNumber() + ")的用户违约停车，收取保证金:" + String.format("%.2f", berthOrder.getExceedPrice()/100f) +"元。";
				usersService.updateBalance(berthOrder.getBerthShare().getUsers().getUserid(), (short)0, 1, berthOrder.getExceedPrice()  , intro);
				
				Users users = usersService.getUserById(berthShare.getUsers().getUserid());
				Notify notify = new Notify(null, users, "收取保证金", intro, 0, new Date().getTime() , 0, 0, null);
				notify = notifyDao.add(notify);
				List<String> clientids = new ArrayList<String>();
				clientids.add(users.getClientid());
				PushMessageUtil.getDefaultInstance().pushTransmission(clientids, "收取保证金", "{\"title\":\"收取保证金\"," +
						"\"content\":\"" + intro + "\"," +
						"\"notifyid\":\"" + notify.getNotifyid() + "\"}");
			}
			
		}
		//发送通知
		String notifyString = "您审诉的【"+typeMap.get(apply.getType())+"】经审核"
		+statusMap.get(apply.getStatus())+"。意见备注:"+apply.getMemo();
		Notify notify = new Notify(null, apply.getUsers(), "违约处理反馈通知",notifyString, 0, new Date().getTime() , 0,0, null);
		notify = notifyDao.add(notify);
		notifyDao.add(notify);
		PushMessageUtil.getDefaultInstance()
		.pushTransmission(apply.getUsers().getClientid(),
				"违约处理反馈通知",notifyString,notify.getNotifyid());
	}
	public int totalCount(String defaultApplyhqlString) {
		// TODO Auto-generated method stub
		return defaultApplyDao.totalCount(defaultApplyhqlString);
	}
}

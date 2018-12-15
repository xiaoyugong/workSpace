package com.parkbobo.service;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.igetui.util.PushMessageUtil;
import com.parkbobo.dao.NotifyDao;
import com.parkbobo.dao.UsersDao;
import com.parkbobo.dao.WithdrawLogDao;
import com.parkbobo.model.Notify;
import com.parkbobo.model.Users;
import com.parkbobo.model.WithdrawLog;
import com.parkbobo.utils.PageBean;
@Component("withdrawLogService")
public class WithdrawLogService {
	@Resource(name="withdrawLogDaoImpl")
	private WithdrawLogDao withdrawLogDao;
	
	@Resource(name="notifyDaoImpl")
	private NotifyDao notifyDao;
	@Resource(name="usersDaoImpl")
	private UsersDao usersDao;
	private Map<Integer, String> statusMap;
	
	public WithdrawLogService(){
		statusMap = new HashMap();
		statusMap.put(-1, "审核失败");
		statusMap.put(1, "正在处理中");
		statusMap.put(2, "审核成功");
	}
	
	public List<WithdrawLog> getByHql(String hql){
		return this.withdrawLogDao.getByHQL(hql);
	}

	public PageBean<WithdrawLog> getPage(String hqlString, int page,
			int pageSize) {
		// TODO Auto-generated method stub
		
		return this.withdrawLogDao.pageQuery(hqlString, page, pageSize);
	}

	public WithdrawLog getById(Long withdrawid) {
		// TODO Auto-generated method stub
		return this.withdrawLogDao.get(withdrawid);
	}

	public void update(WithdrawLog wl, Integer status) {
		// TODO Auto-generated method stub
		Users user = wl.getUsers();
		//只有未处理和待处理才执行
		if(wl.getStatus()==1||wl.getStatus()==0){
			if(status==2&&user.getBalance()>=wl.getAmount()){
					//提现成功,
					
			}
			else if(status==-1){
				//提现失败返还金额
				user.setBalance(user.getBalance()+wl.getAmount());
				usersDao.update(user);
			}
		}
			wl.setStatus(status);
		
		if(user.getBalance()<wl.getAmount()){
			wl.setStatus(-1);
		}
			withdrawLogDao.update(wl);
			
			String notifyString = wl.getUsers().getUsername()+"您的提现申请"+statusMap.get(status)
			+"。审核意见:"+(wl.getMemo().trim().equals("")?"无":wl.getMemo());
			
			Notify notify = new Notify(null, wl.getUsers(), "提现审核通知",notifyString , 0, new Date().getTime() , 0,0, null);
			notify = notifyDao.add(notify);
			notifyDao.add(notify);
			PushMessageUtil.getDefaultInstance()
			.pushTransmission(user.getClientid(),
					"提现审核通知",notifyString,notify.getNotifyid()); 
	}

	public void delete(String ids) {
		// TODO Auto-generated method stub
		String sqlString = "update WithdrawLog set isDel=1 where withdrawid in ("+ids+")";
		withdrawLogDao.deleteByHql(sqlString);
	}

	public int totalCount(String hqlString) {
		// TODO Auto-generated method stub
		return withdrawLogDao.totalCount(hqlString);
	}
	
	
//	public static void main(String[] args) {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		System.out.println(sdf.format(new Date(1417339423473l)));;
//	}
}
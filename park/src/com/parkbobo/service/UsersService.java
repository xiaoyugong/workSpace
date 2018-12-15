package com.parkbobo.service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.igetui.util.PushMessageUtil;
import com.parkbobo.dao.BerthOrderDao;
import com.parkbobo.dao.UserBalanceDao;
import com.parkbobo.dao.UserCreditDao;
import com.parkbobo.dao.UserPointDao;
import com.parkbobo.dao.UsersDao;
import com.parkbobo.model.BerthOrder;
import com.parkbobo.model.UserBalance;
import com.parkbobo.model.UserCredit;
import com.parkbobo.model.UserPoint;
import com.parkbobo.model.Users;
import com.parkbobo.utils.PageBean;
@Component("usersService")
public class UsersService {
	@Resource(name="usersDaoImpl")
	private UsersDao usersDao;
	@Resource(name="userPointDaoImpl")
	private UserPointDao userPointDao;
	@Resource(name="userCreditDaoImpl")
	private UserCreditDao userCreditDao;
	@Resource(name="berthOrderDaoImpl")
	private BerthOrderDao berthOrderDao;
	@Resource(name="userBalanceDaoImpl")
	private UserBalanceDao userBalanceDao;
	/**
	 * HQL语句查询
	 * @param hql
	 * @return
	 */
	public List<Users> getByHql(String hql){
		return this.usersDao.getByHQL(hql);
	}
	public PageBean<Users> loadPage(String hql, int pageSize, int page) {
		return this.usersDao.pageQuery(hql, pageSize, page);
	}
	public void bulkDelete(String ids) {
		if(ids.length() > 0)
		{
			String[] strs = ids.split(",");
			this.usersDao.bulkDelete(strs);
		}
	}
	
	public List getUsersByClientArray(String[] array){
		List<String> list = new ArrayList<String>();
		for(int i = 0; i < array.length; i++){
			if(array[i].equals(""))
				continue;
			list.add(array[i]);
			
		}
		return list;
	}
	
	public Users getUsers(String client){
		return usersDao.getByProperty("clientid", client).get(0);
	}
	public Users getUserById(String userid) {
		return usersDao.getUniqueByProperty("userid", userid);
	}
	public void updateUsers(Users user) {
		// TODO Auto-generated method stub
		usersDao.update(user);
	}
		public void upadteApprove(Users user) {
//			driverAuthenticationDao.merge(driverAuthentication);
			
			usersDao.update(user);
		}
		public void updatePoint( UserPoint userPoint) {
			// TODO Auto-generated method stub
			userPointDao.save(userPoint);
		}
		public void updateCredit(UserCredit userCrediit) {
			// TODO Auto-generated method stub
			userCreditDao.save(userCrediit);
		}
		public List findUsersByIds(String ids) {
			// TODO Auto-generated method stub
			String hql = "from Users where userid in(:in)";
			return userCreditDao.bulkFind(hql, ids.split(","));
		}
		public int dataCount(String todayRegCountHqlString) {
			// TODO Auto-generated method stub
			return userCreditDao.totalCount(todayRegCountHqlString);
		}
		public List<Users> getByIds(String ids) {
			// TODO Auto-generated method stub
			StringBuffer sqlIdString = new StringBuffer();
			if(ids.length() > 0)
			{
				String[] strs = ids.split(",");
				for (int i = 0; i < strs.length; i++) {
					sqlIdString.append("'"+strs[i]+"',");
				}
				System.out.println(sqlIdString);
				sqlIdString = sqlIdString.deleteCharAt(sqlIdString.length()-1);
//				this.usersDao.bulkDelete(strs);
			}
			List list = userCreditDao.getByHQL("from Users where userid in ("+sqlIdString+")");
			return list;
		}
		
		/**
		 * 增加或减少余额
		 * type 0增加余额，1减少余额
		 * event  操作类型,
		 * 			1：充值到余额(recharge)
		 * 			2：从余额提现(withdraw)
		 * 			3：从余额支付(pay)
		 * 			4：退款到余额(drawback)
		 * amount 金额
		 * intro 金额变化事由
		 */
		public void updateBalance(String userid, int type, int event, Long amount, String intro)
		{
			Users users = this.usersDao.get(userid);
			UserBalance userBalance = new UserBalance(null, users, null, type, event, amount, users.getBalance() + amount, intro, new Date().getTime(), null, null, null, null);
			userBalanceDao.merge(userBalance);
			users.setBalance(users.getBalance() + amount);
			
			//如果金额大于0先补缴欠款
			if(type == 0)
			{
				List<BerthOrder> berthOrders = berthOrderDao
				.getByHQL("from BerthOrder as b where b.userid = '" + users.getUserid() + "' and b.isArrearage = 1 order by b.berthorderid");
				for (BerthOrder berthOrder : berthOrders) {
					if(amount > 0)
					{
						if(amount > berthOrder.getArrearage())
						{
							String info = "车主补缴订单：" + berthOrder.getBerthorderid() + " " +
									"欠费" + String.format("%.2f", berthOrder.getArrearage()/100f) +"元,欠费已补缴完";
							updateBalance(berthOrder.getBerthShare().getUsers().getUserid(), 0, 1, berthOrder.getArrearage(), info);
							
							berthOrder.setIsArrearage((short)0);
							berthOrderDao.merge(berthOrder);
							amount = amount - berthOrder.getArrearage();
						}
						else
						{
							String info = "车主补缴订单：" + berthOrder.getBerthorderid() + " 欠费" + String.format("%.2f", amount/100f) + "元," +
									"车主还欠费" + String.format("%.2f", (berthOrder.getArrearage()-amount)/100f) + "元";
							updateBalance(berthOrder.getBerthShare().getUsers().getUserid(), 0, 1, berthOrder.getArrearage(), info);
							
							berthOrder.setArrearage(berthOrder.getArrearage()-amount);
							berthOrderDao.merge(berthOrder);
							amount = 0l;
						}
					}
				}
			}
			usersDao.merge(users);
		}
		/**
		 * 增加或减少积分
		 * type 0增加积分，1减少积分
		 * pointvalue  积分变化值
		 * description 积分变化事由
		 */
		public void updatePoint(String userid, short type, int pointvalue, String description)
		{
			Users users = this.usersDao.get(userid);
			UserPoint userPoint = new UserPoint(null, users, type, pointvalue, description, new Date().getTime(), null);
			userPointDao.merge(userPoint);
			users.setPoint(users.getPoint() + pointvalue);
			usersDao.merge(users);
		}
		/**
		 * 增加或减少车主信誉
		 * type 0增加车主信誉，1减少车主信誉
		 * pointvalue  车主信誉变化值
		 * description 车主信誉变化事由
		 */
		public void updateCredit(String userid, short type, int creditvalue, String description){
			Users users = this.usersDao.get(userid);
			UserCredit userCredit = new UserCredit(null, users, type, (short)0, creditvalue, description, new Date().getTime(), null);
			userCreditDao.merge(userCredit);
			users.setCredit(users.getCredit() + creditvalue);
			usersDao.merge(users);
		}
		/**
		 *  增加或减少车位主信誉
		 * type 0增加车位主信誉，1减少车位主信誉
		 * pointvalue  车位主信誉变化值
		 * description 车位主信誉变化事由
		 */
		public void updateShareCredit(String userid, short type,  int creditvalue, String description)
		{
			Users users = this.usersDao.get(userid);
			UserCredit userCredit = new UserCredit(null, users, type, (short)1, creditvalue, description, new Date().getTime(), null);
			userCreditDao.merge(userCredit);
			users.setCredit(users.getCredit() + creditvalue);
			usersDao.merge(users);
		}
		
		
		public Users findByUsername(String username) {
			String userhql ="from Users as u where u.username ='"+username+"'" ;
			List<Users> users = usersDao.getByHQL(userhql);
			if(users.size()!=0){
				return users.get(0);
			}
			return null;
		}
		public Users getUserByOpenId(String fromUserName) {
			// TODO Auto-generated method stub
			String userhql ="from Users as u where u.openId ='"+fromUserName+"'" ;
			List<Users> users = usersDao.getByHQL(userhql);
			if(users.size()!=0){
				return users.get(0);
			}
			return null;
		}
}
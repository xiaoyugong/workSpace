package com.parkbobo.service;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.parkbobo.dao.BerthOrderDao;
import com.parkbobo.dao.UserBalanceDao;
import com.parkbobo.dao.UsersDao;
import com.parkbobo.model.BerthOrder;
import com.parkbobo.model.UserBalance;
import com.parkbobo.model.Users;
import com.parkbobo.utils.PageBean;
@Component("userBalanceService")
public class UserBalanceService {
	@Resource(name="userBalanceDaoImpl")
	private UserBalanceDao userBalanceDao;
	@Resource(name="usersDaoImpl")
	private UsersDao usersDao;
	@Resource(name="berthOrderDaoImpl")
	private BerthOrderDao berthOrderDao;
	public List<UserBalance> getByHql(String hql){
		return this.userBalanceDao.getByHQL(hql);
	}
	/**
	 * 提交充值订单，订单状态为未付款.返回账户明细记录ID
	 * @param users
	 * @param money
	 * @return
	 */
	public String addBalance(Users users, Double money) {
		Long amount = (long)(money * 100);
		Users u = usersDao.get(users.getUserid());
		UserBalance userBalance = new UserBalance(null, u, null, 0, 1, amount, u.getBalance() + amount, "支付宝充值", new Date().getTime(), 0, null, null, null);
		userBalance = userBalanceDao.add(userBalance);
		if(userBalance.getLogid() != null)
		{
			return userBalance.getLogid().toString();
		}
		else
		{
			return null;
		}
	}
	/**
	 * 支付宝充值付款成功，更新记录为已付款状态，同时更新用户账户总金额，支付未付款记录
	 * @param outTradeNo
	 * @param tradeNo
	 */
	public void updateBalance(String outTradeNo, String tradeNo) {
		UserBalance userBalance = userBalanceDao.get(Long.parseLong(outTradeNo));
		userBalance.setPaystatus(1);
		userBalance.setPaytime(new Date().getTime());
		userBalance.setTradeNo(tradeNo);
		userBalanceDao.update(userBalance);
		
		Users u = usersDao.get(userBalance.getUsers().getUserid());
		u.setBalance(u.getBalance() + userBalance.getAmount());
		Long amount = userBalance.getAmount();
		List<BerthOrder> berthOrders = berthOrderDao
			.getByHQL("from BerthOrder as b where b.userid = '" + u.getUserid() + "' and b.isArrearage = 1 order by b.berthorderid");
		for (BerthOrder berthOrder : berthOrders) {
			if(amount > 0)
			{
				if(amount > berthOrder.getArrearage())
				{
					Users _users = usersDao.get(berthOrder.getBerthShare().getUsers().getUserid());
					UserBalance _userBalance = new UserBalance(null, _users, null, 0, 1, 
							berthOrder.getArrearage(), 
							_users.getBalance() + berthOrder.getArrearage(), 
							"你在" + berthOrder.getBerthShare().getCarparkname() + "发布车位,车主补缴欠费" + String.format("%.2f", berthOrder.getArrearage()/100f) +"元,欠费已补缴完",
							new Date().getTime(), 1, null, null, null);
					_users.setBalance(_users.getBalance() +  berthOrder.getArrearage());
					usersDao.merge(_users);
					userBalanceDao.save(_userBalance);
					
					berthOrder.setIsArrearage((short)0);
					berthOrderDao.merge(berthOrder);
					amount = amount - berthOrder.getArrearage();
				}
				else
				{
					Users _users = usersDao.get(berthOrder.getBerthShare().getUsers().getUserid());
					UserBalance _userBalance = new UserBalance(null, _users, null, 0, 1, 
							amount, 
							_users.getBalance() + amount, 
							"你在" + berthOrder.getBerthShare().getCarparkname() + "发布车位,车主补缴欠费" + amount + "元,车主还欠费" + String.format("%.2f", (berthOrder.getArrearage()-amount)/100f) + "元", new Date().getTime(), 1, null, null, null);
					_users.setBalance(_users.getBalance() +  amount);
					usersDao.merge(_users);
					userBalanceDao.save(_userBalance);
					
					berthOrder.setArrearage(berthOrder.getArrearage()-amount);
					berthOrderDao.merge(berthOrder);
					amount = 0l;
				}
			}
		}
		usersDao.update(u);
		
	}
	public PageBean<UserBalance> getPage(String hql, int page, int pageSize) {
		return userBalanceDao.pageQuery(hql, pageSize, page);
	}
	public void delete(String ids) {
		// TODO Auto-generated method stub
		String[] strs = ids.split(",");
		Long[] idArr = new Long[strs.length];
		for (int i=0; i< strs.length; i++) {
			idArr[i] = Long.valueOf(strs[i]);
		}
		userBalanceDao.bulkDelete(idArr);
	}
	public void addBalance(UserBalance balance) {
		// TODO Auto-generated method stub
		userBalanceDao.add(balance);
	}
	public void bulkAdd(List<UserBalance> userBalances) {
		// TODO Auto-generated method stub
		userBalanceDao.bulksave(userBalances);
	}
}
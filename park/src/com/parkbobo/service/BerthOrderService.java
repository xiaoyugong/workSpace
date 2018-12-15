package com.parkbobo.service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.igetui.util.PushMessageUtil;
import com.opensymphony.xwork2.ActionContext;
import com.parkbobo.dao.BerthOrderDao;
import com.parkbobo.dao.BerthShareDao;
import com.parkbobo.dao.NotifyDao;
import com.parkbobo.dao.UserBalanceDao;
import com.parkbobo.dao.UserCreditDao;
import com.parkbobo.dao.UserPointDao;
import com.parkbobo.dao.UsersDao;
import com.parkbobo.manager.model.Manager;
import com.parkbobo.model.BerthOrder;
import com.parkbobo.model.BerthShare;
import com.parkbobo.model.Notify;
import com.parkbobo.model.UserBalance;
import com.parkbobo.model.Users;
import com.parkbobo.utils.PageBean;
@Component("berthOrderService")
public class BerthOrderService {
	@Resource(name="berthOrderDaoImpl")
	private BerthOrderDao berthOrderDao;
	@Resource(name="usersDaoImpl")
	private UsersDao usersDao;
	@Resource(name="berthShareDaoImpl")
	private BerthShareDao berthShareDao;
	@Resource(name="userBalanceDaoImpl")
	private UserBalanceDao userBalanceDao;
	@Resource(name="userCreditDaoImpl")
	private UserCreditDao userCreditDao;
	@Resource(name="notifyDaoImpl")
	private NotifyDao notifyDao;
	@Resource(name="userPointDaoImpl")
	private UserPointDao userPointDao;
	@Resource(name="berthShareService")
	private BerthShareService berthShareService;
	@Resource(name="usersService")
	private UsersService usersService;
	@Resource(name="berthOrderService")
	private BerthOrderService berthOrderService;
	@Resource(name="userBalanceService")
	private UserBalanceService userBalanceService;
	public List<BerthOrder> getByHql(String hql){
		return this.berthOrderDao.getByHQL(hql);
	}
	/**
	 * 分页查询
	 * @param hql
	 * @param pageSize
	 * @param page
	 * @return
	 */
	public PageBean<BerthOrder> getPage(String hql, int page, int pageSize)
	{
		return this.berthOrderDao.pageQuery(hql, pageSize, page);
	}
	/**
	 * 查看订单详情
	 * @param berthorderid
	 * @param userid
	 * @return
	 */
	public BerthOrder getByIdAndUserid(Long berthorderid, String userid) {
		String[] propertyNames = {"berthorderid","userid"};
		Object[] values = {berthorderid, userid};
		return this.berthOrderDao.getUniqueByPropertys(propertyNames, values);
	}
	
	/**
	 *退还保证金
	 * @return
	 */
	public void drawback_(Long defaultDeposit, String userid, Manager loginManager, BerthOrder berthOrder) {
		Users user = usersService.getUserById(userid);
		Long balance = user.getBalance();
		user.setBalance(balance + defaultDeposit);
		usersService.updateUsers(user);
		//在数据库添加一条退款记录信息
		String intro = "退还订单("+ berthOrder.getBerthorderid() + ")违约保证金:" + String.format("%.2f", berthOrder.getExceedPrice()/100d) +"元。";
		UserBalance userBalance = new UserBalance();
		userBalance.setAdminId(loginManager.getUserId());
		userBalance.setUsers(user);
		userBalance.setType(0);
		userBalance.setEvent(4);
		userBalance.setIntro(intro);
		userBalance.setAmountLog(balance+defaultDeposit);
		userBalance.setAmount(defaultDeposit);
		userBalance.setPosttime(new Date().getTime());
		userBalanceService.addBalance(userBalance);
		berthOrder.setIsDrawback((short)1);
		//berthOrder.setDefaultStatus((short) 0);
		berthOrderDao.merge(berthOrder);
		Notify notify = new Notify(user, "退还保证金", intro, 0, new Date().getTime() , 0, 0, null);
		notifyDao.merge(notify);
	}
	
	/**
	 * 结束订单，停车结束扣费
	 * @param userid
	 * @param berthorderid
	 */
	public BerthOrder closeOrder(BerthOrder order) 
	{
		StringBuffer returnStr = new StringBuffer();
		String[] propertyNames = {"berthorderid","userid"};
		Object[] values = {order.getBerthorderid(), order.getUserid()};
		BerthOrder berthOrder = this.berthOrderDao.getUniqueByPropertys(propertyNames, values);
		if(berthOrder != null)
		{
			if(berthOrder.getStatus().equals((short)0))
			{
				BerthShare berthShare = berthShareDao.get(berthOrder.getBerthShare().getShareid());
				Users carUsers = this.usersDao.get(order.getUserid());//车主
				Users berthUsers = this.usersDao.get(berthShare.getUsers().getUserid());//车位主
				
				Date now  = new Date();//系统当前时间及停车结束时间
				Long leaveMillisecond = now.getTime(); //结束订单时间（毫秒）
				
				Long thisStopTotalMillisecond = 0L; //本次停车总时长（毫秒）
				Long thisStopTotalMoney;//本次停车总费用（分）  thisStopBeforeMoney + thisStopBaseMoney + thisStopOvertimeMoney
				Long thisStopBeforeMoney = berthOrder.getBeforePrice();//本次停车首停费用  (分)
				Long thisStopBaseMoney;//本次停车超过首停时间且未超过分享时段停车费用(分)
				Long thisStopOvertimeMoney;//本次停车超过分享时间停车费用（违规停车费用）
				Long thisStopPrepayMoney = thisStopBeforeMoney + berthOrder.getExceedPrice();//本次停车预付费 ,启停费用 + 超期保证金
				Long thisStopPayableMoney;//本次停车应付金额
				Long thisStopPayMoney;//实际付款金额
				Long thisStopArrearageMoney;//欠费金额
				
				
				thisStopTotalMillisecond = leaveMillisecond - berthOrder.getStopMillisecond();
				
				
				/****************结算停车收费*****************/
				
				try {
					if(thisStopTotalMillisecond > 15 * 60 * 1000L) //订单时长超过15分钟
					{
						if(leaveMillisecond <= berthOrder.getEndMillisecond()) //未超期停车
						{
							if((thisStopTotalMillisecond / (1000L * 60)) <= berthOrder.getBeforeMins())
							{
								thisStopBaseMoney = 0L;
							}
							else
							{
								Long a = thisStopTotalMillisecond / (1000L * 60) - berthOrder.getBeforeMins();
								Integer b = berthOrder.getAfterMins();
								thisStopBaseMoney = (a%b==0?a/b:(a/b)+1) * berthOrder.getAfterPrice();
							}
							thisStopTotalMoney = thisStopBeforeMoney + thisStopBaseMoney;
							thisStopPayableMoney = thisStopTotalMoney - thisStopBeforeMoney;
							thisStopOvertimeMoney = 0L;
							if(thisStopPayableMoney > 0 && carUsers.getBalance() < thisStopPayableMoney)//车主余额不足,欠费
							{
								thisStopPayMoney = carUsers.getBalance();
								thisStopArrearageMoney = thisStopPayableMoney - thisStopPayMoney;
								
								//更新车主账户记录、积分记录、信誉记录
								String intro = "你在" + berthShare.getCarparkname() +"停车" + formatMillisecondToHour(thisStopTotalMillisecond ) + "," +
												"预付停车费" +  String.format("%.2f", thisStopPrepayMoney/100f) + "元," +
												"停车总收费" + String.format("%.2f", thisStopTotalMoney/100f) + "元," +
												"超期停车费0.00元," +
												"应付金额" + String.format("%.2f", thisStopPayableMoney/100f) + "元," +
												"实付金额" + String.format("%.2f", thisStopPayMoney/100f) + "元," +
												"欠费金额" + String.format("%.2f", thisStopArrearageMoney/100f) + "元";
								
								usersService.updateBalance(carUsers.getUserid(), 1, 3, - thisStopPayableMoney, intro);

								//更新车位主账户记录、信誉记录、积分记录
								String _intro = "你在" + berthShare.getCarparkname() + "的车位分享了" + formatMillisecondToHour(thisStopTotalMillisecond ) + "," +
										"获得" + String.format("%.2f", thisStopTotalMoney/100f) + "元。" +
										"车主应付金额" + String.format("%.2f", thisStopTotalMoney/100f) + "元," +
										"实付金额" + String.format("%.2f", (thisStopPayMoney + thisStopPrepayMoney)/100f) + "元," +
										"欠费金额" + String.format("%.2f", thisStopArrearageMoney/100f) + "元";
								usersService.updateBalance(berthUsers.getUserid(), 0, 1, thisStopPayMoney + thisStopPrepayMoney, _intro);
								
								usersService.updatePoint(berthUsers.getUserid(), (short)0, Integer.parseInt(String.valueOf(thisStopTotalMoney/100)), 
										"正常完成车位分享，车主消费" + thisStopTotalMoney/100 + "元，积分增加" + thisStopTotalMoney/100);
								
								usersService.updateShareCredit(berthUsers.getUserid(), (short)0, 1, "正常完成停车分享，信誉加1");
								
								
								//更新车位分享状态
								berthShare.setEmptynum(berthShare.getEmptynum() + 1);
								berthShareDao.merge(berthShare);
								//更新订单状态
								berthOrder.setStatus((short)1);
								berthOrder.setDefaultStatus((short)0);
								berthOrder.setIsArrearage((short)1);
								berthOrder.setArrearage(thisStopArrearageMoney);
								berthOrder.setIsReview((short)0);
								berthOrder.setClosetime(leaveMillisecond);
								berthOrder.setStopTotalMillisecond(thisStopTotalMillisecond);
								berthOrder.setStopPrepayMoney(thisStopPrepayMoney);
								berthOrder.setStopTotalMoney(thisStopTotalMoney);
								berthOrder.setStopPayableMoney(thisStopPayableMoney);
								berthOrder.setStopPayMoney(thisStopPayMoney);
								berthOrder.setStopOvertimeMoney(thisStopOvertimeMoney);
								berthOrder.setStopArrearageMoney(thisStopArrearageMoney);
								berthOrderDao.merge(berthOrder);
							}
							else
							{
								thisStopPayMoney = thisStopPayableMoney;
								thisStopArrearageMoney = 0l;
								//更新车主账户记录、积分记录、信誉记录
								String intro = "你在" + berthShare.getCarparkname() +"停车" + formatMillisecondToHour(thisStopTotalMillisecond ) + "," +
											"预付停车费" +  String.format("%.2f", thisStopPrepayMoney/100f) + "元," +
											"停车总收费" + String.format("%.2f", thisStopTotalMoney/100f) + "元," +
											"超期停车费0.00元," +
											"应付金额" + String.format("%.2f", thisStopPayableMoney/100f) + "元," +
											"实付金额" + String.format("%.2f", thisStopPayMoney/100f) + "元," +
											"欠费金额" + String.format("%.2f", thisStopArrearageMoney/100f) + "元";
								usersService.updateBalance(carUsers.getUserid(), 1, 3, - thisStopPayableMoney, intro);
								usersService.updatePoint(carUsers.getUserid(), (short)0, Integer.parseInt(String.valueOf(thisStopTotalMoney/100)), 
										"正常完成停车，消费" + thisStopTotalMoney/100 + "元，积分增加" + thisStopTotalMoney/100);
								
								usersService.updateCredit(carUsers.getUserid(), (short)0, 1, "正常完成停车，信誉增加1");
								
								//更新车位主账户记录、信誉记录
								String _intro = "你在" + berthShare.getCarparkname() + "的车位分享了" + formatMillisecondToHour(thisStopTotalMillisecond) + "," +
												"获得" + String.format("%.2f", thisStopTotalMoney/100f) + "元。" +
												"车主应付金额" + String.format("%.2f", thisStopTotalMoney/100f) + "元," +
												"实付金额" + String.format("%.2f", thisStopTotalMoney/100f) + "元," +
												"欠费金额" + String.format("%.2f", thisStopArrearageMoney/100f) + "元";
								usersService.updateBalance(berthUsers.getUserid(), 0, 1, thisStopTotalMoney, _intro);
								
								usersService.updatePoint(berthUsers.getUserid(), (short)0, Integer.parseInt(String.valueOf(thisStopTotalMoney/100)), 
										"正常完成车位分享，车主消费" + thisStopTotalMoney/100 + "元，积分增加" + thisStopTotalMoney/100);
								
								usersService.updateShareCredit(berthUsers.getUserid(), (short)0, 1, "正常完成停车分享，信誉加1");
								
								//更新车位分享状态
								berthShare.setEmptynum(berthShare.getEmptynum() + 1);
								berthShareDao.merge(berthShare);
								//更新订单状态
								berthOrder.setStatus((short)1);
								berthOrder.setDefaultStatus((short)0);
								berthOrder.setArrearage(thisStopArrearageMoney);
								berthOrder.setIsArrearage((short)0);
								berthOrder.setIsReview((short)0);
								berthOrder.setClosetime(leaveMillisecond);
								berthOrder.setStopTotalMillisecond(thisStopTotalMillisecond);
								berthOrder.setStopPrepayMoney(thisStopPrepayMoney);
								berthOrder.setStopTotalMoney(thisStopTotalMoney);
								berthOrder.setStopPayableMoney(thisStopPayableMoney);
								berthOrder.setStopPayMoney(thisStopPayMoney);
								berthOrder.setStopOvertimeMoney(thisStopOvertimeMoney);
								berthOrder.setStopArrearageMoney(thisStopArrearageMoney);
								berthOrderDao.merge(berthOrder);
							}
							returnStr.append("{\"status\":\"true\"," +
									"\"thisStopMins\":\"" + thisStopTotalMillisecond / (1000L * 60) +"\"," +
									"\"thisStopPrepayMoney\":\"" + String.format("%.2f", thisStopPrepayMoney/100f) + "\"," +
									"\"thisStopTotalMoney\":\"" + String.format("%.2f", thisStopTotalMoney/100f) + "\"," +
									"\"thisStopPayableMoney\":\"" + String.format("%.2f", thisStopPayableMoney/100f) + "\"," +
									"\"thisStopOvertimeMoney\":\"0.00\"," +
									"\"thisStopPayMoney\":\"" + String.format("%.2f", thisStopPayMoney/100f) + "\"," +
									"\"thisStopArrearageMoney\":\"" + String.format("%.2f", thisStopArrearageMoney/100f) + "\"," +
									"\"balance\":\"" + String.format("%.2f", carUsers.getBalance()/100f) + "\"}");
						}
						
						else //超期停车
						{
							Long a = (berthOrder.getEndMillisecond() - berthOrder.getStopMillisecond()) / (1000L * 60) - berthOrder.getBeforeMins();
							Integer b = berthOrder.getAfterMins();
							Long c = (leaveMillisecond - berthOrder.getEndMillisecond()) / (1000L * 60);
							
							thisStopBaseMoney = (a%b==0?a/b:(a/b)+1) * berthOrder.getAfterPrice();
							thisStopOvertimeMoney = (c%60==0?c/60:(c/60) + 1) * berthOrder.getExceedPrice();
							thisStopTotalMoney = thisStopBeforeMoney + thisStopBaseMoney + thisStopOvertimeMoney;
							thisStopPayableMoney = thisStopTotalMoney - thisStopBeforeMoney - berthOrder.getExceedPrice();
							
							
							if(thisStopPayableMoney > 0 && carUsers.getBalance() < thisStopPayableMoney)//余额不足
							{
								thisStopPayMoney = carUsers.getBalance();
								thisStopArrearageMoney = thisStopPayableMoney - thisStopPayMoney;
								//更新车主账户记录、积分记录、信誉记录
								String intro = "你在" + berthShare.getCarparkname() +"停车" + formatMillisecondToHour(thisStopTotalMillisecond) + "," +
												"预付停车费" +  String.format("%.2f", thisStopPrepayMoney/100f) + "元," +
												"停车总收费" + String.format("%.2f", thisStopTotalMoney/100f) + "元," +
												"超期停车费" + String.format("%.2f", thisStopOvertimeMoney/100f) + "元," +
												"应付金额" + String.format("%.2f", thisStopPayableMoney/100f) + "元," +
												"实付金额" + String.format("%.2f", thisStopPayMoney/100f) + "元," +
												"欠费金额" + String.format("%.2f", thisStopArrearageMoney/100f) + "元";
								usersService.updateBalance(carUsers.getUserid(), 1, 3, -thisStopPayableMoney, intro);
								
								usersService.updateCredit(carUsers.getUserid(), (short)1, -1, "超期停车，信誉减少1");
								
								//更新车位主账户记录、信誉记录
								String _intro = "你在" + berthShare.getCarparkname() + "的车位分享了" + formatMillisecondToHour(thisStopTotalMillisecond) + "," +
												"获得" + String.format("%.2f", thisStopTotalMoney/100f) + "元。" +
												"车主应付金额" + String.format("%.2f", thisStopTotalMoney/100f) + "元," +
												"实付金额" + String.format("%.2f", (thisStopPayMoney + thisStopPrepayMoney)/100f) + "元," +
												"欠费金额" + String.format("%.2f", thisStopArrearageMoney/100f) + "元";
								usersService.updateBalance(berthUsers.getUserid(), 0, 1, thisStopTotalMoney - thisStopArrearageMoney, _intro);
								
								usersService.updatePoint(berthUsers.getUserid(), (short)0, Integer.parseInt(String.valueOf(thisStopTotalMoney/100)), 
										"正常完成车位分享，车主消费" + thisStopTotalMoney/100 + "元，积分增加" + thisStopTotalMoney/100);
								
								usersService.updateShareCredit(berthUsers.getUserid(), (short)0, 1, "正常完成停车分享，信誉加1");
								
								//更新车位分享状态
								berthShare.setEmptynum(berthShare.getEmptynum() + 1);
								berthShareDao.merge(berthShare);
								//更新订单状态
								berthOrder.setStatus((short)1);
								berthOrder.setDefaultStatus((short)0);
								berthOrder.setIsArrearage((short)1);
								berthOrder.setArrearage(thisStopArrearageMoney);
								berthOrder.setIsReview((short)0);
								berthOrder.setClosetime(leaveMillisecond);
								berthOrder.setIsDrawback((short)1);
								berthOrder.setStopTotalMillisecond(thisStopTotalMillisecond);
								berthOrder.setStopPrepayMoney(thisStopPrepayMoney);
								berthOrder.setStopTotalMoney(thisStopTotalMoney);
								berthOrder.setStopPayableMoney(thisStopPayableMoney);
								berthOrder.setStopPayMoney(thisStopPayMoney);
								berthOrder.setStopOvertimeMoney(thisStopOvertimeMoney);
								berthOrder.setStopArrearageMoney(thisStopArrearageMoney);
								berthOrderDao.merge(berthOrder);
							}
							else
							{
								thisStopPayMoney = thisStopPayableMoney;
								thisStopArrearageMoney = 0l;
								//更新车主账户记录、积分记录、信誉记录
								String intro = "你在" + berthShare.getCarparkname() +"停车" + formatMillisecondToHour(thisStopTotalMillisecond) + "," +
												"预付停车费" +  String.format("%.2f", thisStopPrepayMoney/100f) + "元," +
												"停车总收费" + String.format("%.2f", thisStopTotalMoney/100f) + "元," +
												"超期停车费" + String.format("%.2f", thisStopOvertimeMoney/100f) + "元," +
												"应付金额" + String.format("%.2f", thisStopPayableMoney/100f) + "元," +
												"实付金额" + String.format("%.2f", thisStopPayableMoney/100f) + "元," +
												"欠费金额" + String.format("%.2f", thisStopArrearageMoney/100f) + "元";
								usersService.updateBalance(carUsers.getUserid(), 1, 3, - thisStopPayableMoney, intro);
								
								usersService.updateCredit(carUsers.getUserid(), (short)1, -1, "超期停车，信誉减少1");
								
								//更新车位主账户记录、信誉记录
								String _intro = "你在" + berthShare.getCarparkname() + "的车位分享了" + formatMillisecondToHour(thisStopTotalMillisecond)  + "," +
												"获得" + String.format("%.2f", thisStopTotalMoney/100f) + "元。" +
												"车主应付金额" + String.format("%.2f", thisStopTotalMoney/100f) + "元," +
												"实付金额" + String.format("%.2f", thisStopTotalMoney/100f) + "元," +
												"欠费金额" + String.format("%.2f", thisStopArrearageMoney/100f) + "元";
								usersService.updateBalance(berthUsers.getUserid(), 0, 1, thisStopTotalMoney, _intro);
								
								usersService.updatePoint(berthUsers.getUserid(), (short)0, Integer.parseInt(String.valueOf(thisStopTotalMoney/100)), 
										"正常完成车位分享，车主消费" + thisStopTotalMoney/100 + "元，积分增加" + thisStopTotalMoney/100);
								
								usersService.updateShareCredit(berthUsers.getUserid(), (short)0, 1, "正常完成停车分享，信誉加1");
								
								//更新车位分享状态
								berthShare.setEmptynum(berthShare.getEmptynum() + 1);
								berthShareDao.merge(berthShare);
								//更新订单状态
								berthOrder.setStatus((short)1);
								berthOrder.setDefaultStatus((short)0);
								berthOrder.setArrearage(thisStopArrearageMoney);
								berthOrder.setIsArrearage((short)0);
								berthOrder.setIsReview((short)0);
								berthOrder.setClosetime(leaveMillisecond);
								berthOrder.setIsDrawback((short)1);
								berthOrder.setStopTotalMillisecond(thisStopTotalMillisecond);
								berthOrder.setStopPrepayMoney(thisStopPrepayMoney);
								berthOrder.setStopTotalMoney(thisStopTotalMoney);
								berthOrder.setStopPayableMoney(thisStopPayableMoney);
								berthOrder.setStopPayMoney(thisStopPayMoney);
								berthOrder.setStopOvertimeMoney(thisStopOvertimeMoney);
								berthOrder.setStopArrearageMoney(thisStopArrearageMoney);
								berthOrderDao.merge(berthOrder);
							}
							returnStr.append("{\"status\":\"true\"," +
									"\"thisStopMins\":\"" + thisStopTotalMillisecond / (1000L * 60) +"\"," +
									"\"thisStopPrepayMoney\":\"" + String.format("%.2f", thisStopPrepayMoney/100f) + "\"," +
									"\"thisStopTotalMoney\":\"" + String.format("%.2f", thisStopTotalMoney/100f) + "\"," +
									"\"thisStopPayableMoney\":\"" + String.format("%.2f", thisStopPayableMoney/100f) + "\"," +
									"\"thisStopOvertimeMoney\":\"" + String.format("%.2f", thisStopOvertimeMoney/100f) + "\"," +
									"\"thisStopPayMoney\":\"" + String.format("%.2f", thisStopPayMoney/100f) + "\"," +
									"\"thisStopArrearageMoney\":\"" + String.format("%.2f", thisStopArrearageMoney/100f) + "\"," +
									"\"balance\":\"" + String.format("%.2f", carUsers.getBalance()/100f) + "\"}");
						}
					}
					//订单时长不足15分钟不计费
					else  
					{
						thisStopTotalMoney = 0L;
						thisStopPayableMoney = 0L;
						thisStopOvertimeMoney = 0L;
						thisStopPayMoney = 0L;
						thisStopArrearageMoney = 0L;
						
						if(thisStopTotalMillisecond < 0)//还未到起始停车时间,保证金立即退
						{
							thisStopTotalMillisecond = 0L;
						}
						//更新车主账户记录、积分记录、信誉记录
						String intro = "你在" + berthShare.getCarparkname() +"停车" + formatMillisecondToHour(thisStopTotalMillisecond)  + "," +
										"预付停车费" +  String.format("%.2f", thisStopPrepayMoney/100f) + "元," +
										"停车总收费" + String.format("%.2f", thisStopTotalMoney/100f) + "元," +
										"超期停车费" + String.format("%.2f", thisStopOvertimeMoney/100f) + "元," +
										"应付金额" + String.format("%.2f", thisStopPayableMoney/100f) + "元," +
										"实付金额" + String.format("%.2f", thisStopPayMoney/100f) + "元," +
										"欠费金额" + String.format("%.2f", thisStopArrearageMoney/100f) + "元";
						usersService.updateBalance(carUsers.getUserid(), 0, 1, thisStopBeforeMoney, intro);
						//更新车位分享状态
						berthShare.setEmptynum(berthShare.getEmptynum() + 1);
						berthShareDao.merge(berthShare);
						//更新订单状态
						berthOrder.setStatus((short)1);
						berthOrder.setDefaultStatus((short)0);
						berthOrder.setArrearage(thisStopArrearageMoney);
						berthOrder.setIsArrearage((short)0);
						berthOrder.setIsReview((short)0);
						berthOrder.setClosetime(leaveMillisecond);
						berthOrder.setStopTotalMillisecond(thisStopTotalMillisecond);
						berthOrder.setStopPrepayMoney(thisStopPrepayMoney);
						berthOrder.setStopTotalMoney(thisStopTotalMoney);
						berthOrder.setStopPayableMoney(thisStopPayableMoney);
						berthOrder.setStopPayMoney(thisStopPayMoney);
						berthOrder.setStopOvertimeMoney(thisStopOvertimeMoney);
						berthOrder.setStopArrearageMoney(thisStopArrearageMoney);
						berthOrderDao.merge(berthOrder);
						
						returnStr.append("{\"status\":\"true\"," +
								"\"thisStopMins\":\"" + thisStopTotalMillisecond / (1000L * 60) +"\"," +
								"\"thisStopPrepayMoney\":\"" + String.format("%.2f", thisStopPrepayMoney/100f) + "\"," +
								"\"thisStopTotalMoney\":\"" + String.format("%.2f", thisStopTotalMoney/100f) + "\"," +
								"\"thisStopPayableMoney\":\"" + String.format("%.2f", thisStopPayableMoney/100f) + "\"," +
								"\"thisStopOvertimeMoney\":\"" + String.format("%.2f", thisStopOvertimeMoney/100f) + "\"," +
								"\"thisStopPayMoney\":\"" + String.format("%.2f", thisStopPayMoney/100f) + "\"," +
								"\"thisStopArrearageMoney\":\"" + String.format("%.2f", thisStopArrearageMoney/100f) + "\"," +
								"\"balance\":\"" + String.format("%.2f", carUsers.getBalance()/100f) + "\"}");
						
					}
					Notify notify = new Notify(null, berthUsers, "车主结束停车通知", "你在【" + berthShare.getCarparkname() 
							+ "】发布的车位，车主已结束停车，车牌号为" + berthOrder.getCarNumber() + "！", 0, new Date().getTime() , 0, 0, null);
					notify = notifyDao.add(notify);
					List<String> clientids = new ArrayList<String>();
					clientids.add(berthUsers.getClientid());
					PushMessageUtil.getDefaultInstance().pushTransmission(clientids, "车主结束停车通知", "{\"title\":\"车主结束停车通知\",\"content\":\"" + "你在【" + berthShare.getCarparkname() 
							+ "】发布的车位，车主已结束停车，车牌号为" + berthOrder.getCarNumber() + "！" + "\",\"notifyid\":\"" + notify.getNotifyid() + "\"}");
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
					returnStr.append("{\"status\":\"false\",\"errorcode\":\"19\"}");//结束停车失败
				}
			}
			else
			{
					
					//订单离场时间-订单结束时间
					Long cqtime = 	order.getLeavetime()-order.getClosetime();
					if(cqtime>60*10*1000){//结束订单10分钟内未出场收取超期停车费
						Long cqmoney = (cqtime%(60*60*1000)!=0?(cqtime/(60*60*1000)+1):cqtime/(60*60*1000))*order.getExceedPrice();
						order.setStopOvertimeMoney(order.getStopOvertimeMoney()+cqmoney);
						berthOrderDao.merge(berthOrder);
					}
			}
		}
		else
		{
			returnStr.append("{\"status\":\"false\",\"errorcode\":\"19\"}");//结束停车失败
		}
		return berthOrder;
	}
	
	
	
	
	
	public BerthOrder orderBalance(BerthOrder order) {
		
		
		
		return order;
	}
	/**
	 * 获取当前年月日是星期几
	 * @param yyyyMMdd
	 * @return
	 * @throws ParseException
	 */
	private int getWeek(String yyyyMMdd) throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(sdf.parse(yyyyMMdd).getTime()));
		return cal.get(Calendar.DAY_OF_WEEK);
	}
	/**
	 * 获取重复星期集合
	 * @param repeatDate
	 * @return
	 */
	private Map<Integer, Integer> getWeekMap(String repeatDate)
	{
		Map<Integer, Integer> weekMap = new HashMap<Integer, Integer>();
		repeatDate = repeatDate.substring(1, repeatDate.length() - 1);
		String[] weekArr = repeatDate.split(",");
		for (String str : weekArr) {
			weekMap.put(Integer.parseInt(str), Integer.parseInt(str));
		}
		return weekMap;
	}
	public void deleteByHql(String ids) {
		// TODO Auto-generated method stub
		String hqlString = "update  BerthOrder set isDel=1 where berthorderid in ("+ids+")";
		this.berthOrderDao.deleteByHql(hqlString);
	}
	
	/**
	 * 将毫秒转换成小时
	 */
	private String formatMillisecondToHour(Long millisecond)
	{
		if((millisecond % (1000L * 60 * 60)) == 0)
		{
			return millisecond / (1000L * 60 * 60) + "小时";
		}
		else
		{
			return millisecond / (1000L * 60 * 60) + "小时" 
					+ (millisecond % (1000L * 60 * 60)) / (1000L * 60) + "分";
		}
	}
	public BerthOrder getById(Long berthorderid) {
		// TODO Auto-generated method stub
		return berthOrderDao.getById(berthorderid);
	}
	public int dataCount(String todayOrderCountHqlString) {
		// TODO Auto-generated method stub
		return berthOrderDao.totalCount(todayOrderCountHqlString);
	}
	public void update(BerthOrder berthOrder) {
		// TODO Auto-generated method stub
		berthOrderDao.update(berthOrder);
	}
	
}
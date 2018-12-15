package com.parkbobo.manager.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import com.mysql.jdbc.BalanceStrategy;
import com.opensymphony.xwork2.ActionContext;
import com.parkbobo.manager.model.Manager;
import com.parkbobo.model.Carpark;
import com.parkbobo.model.Notify;
import com.parkbobo.model.UserBalance;
import com.parkbobo.model.UserCredit;
import com.parkbobo.model.UserPoint;
import com.parkbobo.model.Users;
import com.parkbobo.service.NotifyService;
import com.parkbobo.service.UserBalanceService;
import com.parkbobo.service.UserCreditService;
import com.parkbobo.service.UserPointService;
import com.parkbobo.service.UsersService;
import com.parkbobo.utils.DateUtils;
import com.parkbobo.utils.PageBean;
import com.sun.org.apache.bcel.internal.generic.NEW;

@Component("usersAction")
@Scope("prototype")
public class UsersAction extends BaseAction {

	private static final long serialVersionUID = -115493100563488586L;
	private PageBean<Users> usersPage;
	@Resource(name="usersService")
	private UsersService usersService;
	@Resource(name="userCreditService")
	private UserCreditService userCreditService;
	@Resource(name="userPointService")
	private UserPointService userPointService;
	private UserCredit userCredit;
	private UserPoint userPoint;
	private Users user;
	private String description;
	@Resource(name="userBalanceService")
	private UserBalanceService balanceService;
	@Resource(name="notifyService")
	private NotifyService notifyService;
	private Integer isSendNotify;
	private UserBalance userBalance;
	private Integer couponvalue;
	/**
	 * 今日注册数
	 */
	private int todayRegCount;
	/**
	 * 注册排序
	 */
	private String registerOrder="desc";
	/**
	 * 登陆排序
	 */
	private String loginOrder="desc";
	
	/**
	 * 
	 */
	private String orderPrecedence="registerOrder";
	/**
	 * 用户列表
	 * @return
	 */
	public String list()
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String tod = simpleDateFormat.format(new Date());
		Timestamp s_date = Timestamp.valueOf(tod+" 00:00:00");
		Timestamp e_edate = Timestamp.valueOf(tod+" 23:59:59");
		Manager manager = (Manager) request.getSession().getAttribute("loginManager");
		String area = manager.getArea();
		if(area!=null && !"".equals(area) && !"总部".equals(area)){
			String todayRegCountHqlString = "select count(o) from Users o where "+s_date.getTime()+"<=registerTime and registerTime<="+e_edate.getTime()+" and o.area like '%" + area + "%'";
			String hql = "from Users as u where u.area like '%" + area + "%'";
			if(user!=null){
				hql+=" and u.username like '%"+user.getUsername().trim()+"%' " ;
				if(!user.getInvitecode().trim().equals("")){
					hql+=" and u.invitecode like '%"+user.getInvitecode().trim()+"%' ";
				}
				if(!user.getCarNumber().equals("")){
					hql+=" and upper(u.carNumber) like '%"+user.getCarNumber().trim().toUpperCase()+"%'";
				}
			}
			if(orderPrecedence.equals("registerOrder")){
				hql += " order by u.registerTime "+registerOrder+", u.loginTime "+loginOrder;
			}
			if(orderPrecedence.equals("loginOrder")){
				hql += " order by u.loginTime "+loginOrder+", u.registerTime "+registerOrder;
			}
			todayRegCount = usersService.dataCount(todayRegCountHqlString);
			usersPage = usersService.loadPage(hql, getPageSize(), getPage());
			return "list";
		}else if(area!=null && "总部".equals(area)){
			String todayRegCountHqlString = "select count(o) from Users o where "+s_date.getTime()+"<=registerTime and registerTime<="+e_edate.getTime();
			String hql = "from Users as u where 1=1";
			if(user!=null){
				hql+=" and u.username like '%"+user.getUsername().trim()+"%' " ;
				if(!user.getInvitecode().trim().equals("")){
					hql+=" and u.invitecode like '%"+user.getInvitecode().trim()+"%' ";
				}
				if(!user.getCarNumber().equals("")){
					hql+=" and upper(u.carNumber) like '%"+user.getCarNumber().trim().toUpperCase()+"%'";
				}
			}
			if(orderPrecedence.equals("registerOrder")){
				hql += " order by u.registerTime "+registerOrder+", u.loginTime "+loginOrder;
			}
			if(orderPrecedence.equals("loginOrder")){
				hql += " order by u.loginTime "+loginOrder+", u.registerTime "+registerOrder;
			}
			todayRegCount = usersService.dataCount(todayRegCountHqlString);
			usersPage = usersService.loadPage(hql, getPageSize(), getPage());
			return "list";
		}else{
			return "list";
		}
		
	}
	
	
	/**
	 * 邀请码用户列表
	 * @return
	 */
	public String invitationList()
	{
		String hql = "from Users as u where 1=1 and invitecode is not  null and invitecode!=''";
		if(user!=null&&!user.getUsername().trim().equals("")){
			Users users = usersService.findByUsername(user.getUsername().trim());
			if(users!=null&&users.getInvitecodeDetail()!=null)
			hql+=" and u.invitecode = '"+users.getInvitecodeDetail().getInvitecode()+"' ";
			else{
				usersPage = new PageBean<Users>();
				return "invitationList"; 
			}
		}
		if(orderPrecedence.equals("registerOrder")){
			hql += " order by u.registerTime "+registerOrder+", u.loginTime "+loginOrder;
		}
		if(orderPrecedence.equals("loginOrder")){
			hql += " order by u.loginTime "+loginOrder+", u.registerTime "+registerOrder;
		}
		usersPage = usersService.loadPage(hql, getPageSize(), getPage());
		return "invitationList";
	}
	
	public String getUserJson() throws IOException {
		String hql = "from Users as u where 1=1";
		if(user!=null){
			hql+=" and u.username like '%"+user.getUsername().trim()+"%' " ;
		}
		usersPage = usersService.loadPage(hql, getPageSize(), getPage());
		if(usersPage.getList().size()<=0)
			response.getWriter().print("{\"status\":\"false\"}");
		
		StringBuffer sbBuffer = new StringBuffer("{\"status\":\"true\",\"data\":[");
		for(Users u:usersPage.getList()){
			sbBuffer.append("{\"id\":\""+u.getUserid()+"\",\"name\":\""+u.getUsername()+"\",\"nick\":\""
					+(u.getNickname()==null?"":u.getNickname())+"\",\"real\":\""+(u.getRealname()==null?"":u.getRealname())+"\"},");
		}
		sbBuffer.append("]}");
		sbBuffer = sbBuffer.deleteCharAt(sbBuffer.length()-3);
		response.getWriter().print(sbBuffer);
		return null;
	}
	
	/**
	 * 
	 * 用户删除
	 * @return
	 */
	public String delete()
	{
		usersService.bulkDelete(getIds());
		return forward("/users_list");
	}
//	public String getdata() throws IOException {
//		String hql = "from Users as u where 1=1";
//		if(user!=null)
//		hql+=" and u.username like '%"+user.getUsername()+"%' ";
//		
//		hql += " order by u.registerTime desc";
//		usersPage = usersService.loadPage(hql, getPageSize(), getPage());
//		
//
//		response.getWriter().print(toJson(usersPage));
//		
//		return null;
//	}
	
	private String toJson(PageBean<Users> usersPage) {
		List<Users> list = usersPage.getList();
		StringBuffer json =new StringBuffer( "{\"page\":\""+usersPage.getCurrentPage()+"\",\"rows\":[");
		for(Users users:list){
			json.append("{\"username\":\""+users.getUsername()+"\"," +
					"\"realname\":\""+users.getRealname()+"\"," +
					"\"nickname\":\""+users.getNickname()+"\"," +
							"\"carNumber\":\""+users.getCarNumber()+"\"," +
							"\"registerTimeToDate\":\""+DateUtils.getInstance().parseDate(users.getRegisterTimeToDate())+"\"},");
		}
		if(list.size()!=0)
			json = json.deleteCharAt(json.length()-1);
		json.append("], \"total\":"+usersPage.getTotalPage()+", \"records\":"+usersPage.getAllRow()+"}");
		return json.toString();
	}
	
	
	public String details(){
		Users users = usersService.getUserById(getIds());
		ActionContext.getContext().getValueStack().set("user", users);
		return "details";
	}
	
	
	/**
	 * 跳转到修改余额
	 * @return
	 */
	public String updateBalance() {
//		 user = usersService.getUserById(user.getUserid());
		 List<Users> listuses = usersService.getByIds(getIds());
		 ActionContext.getContext().getValueStack().set("listusers", listuses);
		return "updateBalance";
	}
	/**
	 * 跳转到修改信誉
	 * @return
	 */
	public String updateCredit() {
//		 user = usersService.getUserById(user.getUserid());
		 List<Users> listuses = usersService.getByIds(getIds());
		 ActionContext.getContext().getValueStack().set("listusers", listuses);
		return "updateCredit";
	}
	
	
	/**
	 * 跳转到修改积分
	 * @return
	 */
	public String updatePoint() {
//		 user = usersService.getUserById(user.getUserid());
		 List<Users> listuses = usersService.getByIds(getIds());
		 ActionContext.getContext().getValueStack().set("listusers", listuses);
		return "updatePoint";
	}
	
	
	/**
	 * 跳转到修改停车券
	 * @return
	 */
	public String updateCoupon() {
//		 user = usersService.getUserById(user.getUserid());
		 List<Users> listuses = usersService.getByIds(getIds());
		 ActionContext.getContext().getValueStack().set("listusers", listuses);
		return "updateCoupon";
	}
	
	
	/**
	 * 余额更改
	 * @return
	 */
	public String balanceSave() {
		List<Users> listuses = usersService.getByIds(getIds());
//		Users user_ = usersService.getUserById(user.getUserid());
		List<UserBalance> userBalances = new ArrayList<UserBalance>();
		if(userBalance.getAmount()==0){
			return forward("/users_list"); 
		}
		Notify notify = new Notify();
		if(userBalance.getAmount()!=0){
			for(Users user_:listuses){
				this.saveLog("用户"+user_.getUsername()+",余额变化值："+(userBalance.getAmount())/100+"元");
				Manager manager = (Manager) ActionContext.getContext().getSession().get("loginManager");
				UserBalance balance = null;
				
				if(userBalance.getAmount()>0){//增加
					 balance = new UserBalance(null, user_, manager.getUserId(), 0, 1, userBalance.getAmount(),(user_.getBalance()+userBalance.getAmount()),
							description, System.currentTimeMillis(), 1, null, System.currentTimeMillis(), null);
					 
				}else if(userBalance.getAmount()<0){//减少
					 balance = new UserBalance(null, user_, manager.getUserId(), 1, 3, userBalance.getAmount(), (user_.getBalance()+userBalance.getAmount()),
							description, System.currentTimeMillis(), 1, null, System.currentTimeMillis(), null);
				}
					balance.setUsers(user_);
					userBalances.add(balance);
					 user_.setBalance((user_.getBalance()+userBalance.getAmount()));
//					 usersService.updateUsers(user_);
					//是否发送通知
						if(isSendNotify==1){
							
							notify.setContent(description);
							notify.setTitle("余额变化提醒");
							notify.setIsDel(0);
							notify.setIsRead(0);
							notify.setPosttime(System.currentTimeMillis());
//							notify.setUsers(user_);
							notify.setType(0);
//							List<Users> users = new ArrayList<Users>();
//							users.add(user_);
							
						}
							
				
			}
		}
		if(isSendNotify==1){
			notifyService.add(notify,listuses,0,1);
		}
		balanceService.bulkAdd(userBalances);
		return forward("/users_list"); 
	}
	/**
	 * 用户信誉更改
	 * @return
	 */
	public String creditSave() {
		List<Users> listuses = usersService.getByIds(getIds());
		List<UserCredit> listCredits =  new ArrayList<UserCredit>();
		Notify notify = new Notify();
		for(Users user_:listuses){
			if(userCredit.getCreditvalue()!=0){
				UserCredit userCredit_  = new UserCredit();
				userCredit_.setCreditvalue(userCredit.getCreditvalue());
				userCredit_.setDescription(this.userCredit.getDescription());
				userCredit_.setPosttime(System.currentTimeMillis());
				userCredit_.setCredittype((short)0);
				if(userCredit.getCreditvalue()>0){
					//增加
					userCredit_.setType((short)0);
				}else if(userCredit.getCreditvalue()<0){
					//减少
					userCredit_.setType((short)1);
				}
				
				user_.setCredit(user_.getCredit()+userCredit.getCreditvalue());
				userCredit_.setUsers(user_);
				listCredits.add(userCredit_);
				this.saveLog("用户"+user_.getUsername()+",信誉变化值："+userCredit_.getCreditvalue());
			}
			
			if(isSendNotify==1){
				
				notify.setContent(userCredit.getDescription());
				notify.setTitle("信誉变化提醒");
				notify.setIsDel(0);
				notify.setIsRead(0);
				notify.setPosttime(System.currentTimeMillis());
				notify.setType(0);
				
			}
		}
		if(isSendNotify==1){
			notifyService.add(notify,listuses,0,1);
		}
		userCreditService.bulkAdd(listCredits);
		return forward("/users_list");
		}
	/**
	 * 
	 * @return
	 * 用户积分修改
	 */
	public String pointSave() {
		List<Users> listuses = usersService.getByIds(getIds());
		List<UserPoint> listPoints =  new ArrayList<UserPoint>();
		Notify notify = new Notify();
		for(Users user_:listuses){
			if(userPoint.getPointvalue()!=0){
				UserPoint userPoint_  = new UserPoint();
				userPoint_.setPointvalue(userPoint.getPointvalue());
				userPoint_.setDescription(this.userPoint.getDescription());
				userPoint_.setPosttime(System.currentTimeMillis());
				if(userPoint.getPointvalue()>0){
					//增加
					userPoint_.setType((short)0);
				}else if(userPoint.getPointvalue()<0){
					//减少
					userPoint_.setType((short)1);
				}
				
				user_.setPoint(user_.getPoint()+userPoint.getPointvalue());
				
				userPoint_.setUsers(user_);
//				usersService.updatePoint(userPoint_);
				listPoints.add(userPoint_);
				this.saveLog("用户"+user_.getUsername()+",积分变化值："+userPoint_.getPointvalue());
			}
			if(isSendNotify==1){
				notify.setContent(userPoint.getDescription());
				notify.setTitle("积分变化提醒");
				notify.setIsDel(0);
				notify.setIsRead(0);
				notify.setPosttime(System.currentTimeMillis());
				notify.setType(0);
				
			}
		}
		if(isSendNotify==1){
			notifyService.add(notify,listuses,0,1);
		}
		userPointService.bulkAdd(listPoints);
		return forward("/users_list");
	}
	
	
	/**
	 * 
	 * @return
	 * 用户停车券修改
	 * @throws IOException 
	 */
	public String couponSave() throws IOException {
		System.out.println("1111");
		HttpServletResponse response2 = ServletActionContext.getResponse();
		PrintWriter out = response2.getWriter();
		List<Users> listuses = usersService.getByIds(getIds());
		Users users = listuses.get(0);
		System.out.println(couponvalue);
		if(couponvalue!=0){
			if(users.getCouponBalnce()>=0){
				Long oldcoupon =  users.getCouponBalnce();
				users.setCouponBalnce(oldcoupon+couponvalue*100);
			}
		}
		System.out.println(users.getCouponBalnce());
		usersService.updateUsers(users);
		return forward("/users_list");
	}
	
	
	public PageBean<Users> getUsersPage() {
		return usersPage;
	}
	public void setUsersPage(PageBean<Users> usersPage) {
		this.usersPage = usersPage;
	}
	public UserCredit getUserCredit() {
		return userCredit;
	}
	public void setUserCredit(UserCredit userCredit) {
		this.userCredit = userCredit;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public UserPoint getUserPoint() {
		return userPoint;
	}
	public void setUserPoint(UserPoint userPoint) {
		this.userPoint = userPoint;
	}
	@Override
	public String logModel() {
		// TODO Auto-generated method stub
		return "用户管理";
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getIsSendNotify() {
		return isSendNotify;
	}
	public void setIsSendNotify(Integer isSendNotify) {
		this.isSendNotify = isSendNotify;
	}
	public String getRegisterOrder() {
		return registerOrder;
	}
	public void setRegisterOrder(String registerOrder) {
		this.registerOrder = registerOrder;
	}
	public String getLoginOrder() {
		return loginOrder;
	}
	public void setLoginOrder(String loginOrder) {
		this.loginOrder = loginOrder;
	}
	public String getOrderPrecedence() {
		return orderPrecedence;
	}
	public void setOrderPrecedence(String orderPrecedence) {
		this.orderPrecedence = orderPrecedence;
	}
	public int getTodayRegCount() {
		return todayRegCount;
	}
	public void setTodayRegCount(int todayRegCount) {
		this.todayRegCount = todayRegCount;
	}

	public UserBalance getUserBalance() {
		return userBalance;
	}

	public void setUserBalance(UserBalance userBalance) {
		this.userBalance = userBalance;
	}


	public void setCouponvalue(Integer couponvalue) {
		this.couponvalue = couponvalue;
	}


	public Integer getCouponvalue() {
		return couponvalue;
	}
	
}

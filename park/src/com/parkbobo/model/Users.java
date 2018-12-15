package com.parkbobo.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;



/**
 * 用户表
 * @author LH
 *
 */
@Entity
@Table(name="lq_users")
public class Users  implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9112987784889311856L;
	/**
	 * 用户ID
	 */
	private String userid;
	/**
	 * 用户名：电话号码
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 支付密码
	 */
	private String payPassword;
	/**
	 * 昵称
	 */
	private String nickname;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 姓名
	 */
	private String realname;
	/**
	 * 头像
	 */
	private String userhead;
	/**
	 * 推送ID
	 */
	private String clientid;
	/**
	 * 信誉
	 */
	private Integer credit;
	/**
	 * 分享信誉
	 */
	private Integer shareCredit;
	/**
	 * 积分
	 */
	private Long point;
	/**
	 * 钱包
	 */
	private Long balance;
	/**
	 * 停车券
	 */
	private Long couponBalnce;
	/**
	 * 平台暂扣保证金
	 */
	private Long deposit;
	/**
	 * 车牌号
	 */
	private String carNumber;
	/**
	 * 车主认证状态，0:未认证;1:待审核;2:通过;3:未通过
	 */
	private Short driverStatus;
	/**
	 * 账号状态，0：正常；1：锁定
	 */
	private Short status;
	/**
	 * 注册时间
	 */
	private Long registerTime;
	/**
	 * 最后登录时间
	 */
	private Long loginTime;
	/**
	 * 用户来源渠道
	 */
	private AppChannel appChannel;
	/**
	 * 注册地经度
	 */
	private Double registerLon;
	/**
	 * 注册地纬度
	 */
	private Double registerLat;
	
	/**
	 * 邀请码
	 */
	private String invitecode;
	/**
	 * 备注
	 */
	private String memo;
	/**
	 * 地区
	 */
	private String area;
	/**
	 * 业主
	 */
	private String owner;
	private Date registerTimeToDate;
	private Date loginTimeToDate;
	
	
	/**
	 * 微信号唯一id
	 */
	private String openId;
    private DriverAuthentication driverAuthentication;
    private UsersConfigure usersConfigure;
    private Set<UserPoint> userPoints = new HashSet<UserPoint>(0);
    private Set<WithdrawLog> withdrawLogs = new HashSet<WithdrawLog>(0);
    private Set<BerthFavorite> berthFavorites = new HashSet<BerthFavorite>(0);
    private Set<UserBalance> userBalances = new HashSet<UserBalance>(0);
    private Set<CarparkFavorite> carparkFavorites = new HashSet<CarparkFavorite>(0);
    private Set<BerthShare> berthShares = new HashSet<BerthShare>(0);
    private Set<Feedback> feedbacks = new HashSet<Feedback>(0);
    private Set<UserCredit> userCredits = new HashSet<UserCredit>(0);
    private Set<CarparkAuthentication> carparkAuthentications = new HashSet<CarparkAuthentication>(0);
    private InvitecodeDetail invitecodeDetail;

    public Users() {
    }
    
    

    public Users(String userid) {
        this.userid = userid;
    }
    
    public Users(String userid, String username, String password, String payPassword, 
    		String nickname, String email, String realname, String userhead, String clientid, 
    		Integer credit, Integer shareCredit, Long point, Long balance, Long deposit, 
    		String carNumber, Short driverStatus, Short status, Long registerTime, Long loginTime, 
    		AppChannel appChannel, Double registerLon, Double registerLat, String invitecode, String memo) {
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.payPassword = payPassword;
        this.nickname = nickname;
        this.email = email;
        this.realname = realname;
        this.userhead = userhead;
        this.clientid = clientid;
        this.credit = credit;
        this.shareCredit = shareCredit;
        this.point = point;
        this.balance = balance;
        this.deposit = deposit;
        this.carNumber = carNumber;
        this.driverStatus = driverStatus;
        this.status = status;
        this.registerTime = registerTime;
        this.loginTime = loginTime;
        this.appChannel = appChannel;
        this.registerLon = registerLon;
        this.registerLat = registerLat;
        this.invitecode = invitecode;
        this.memo = memo;
    }

   
    @Id 
    @GeneratedValue(generator = "myIdGenerator")     
	@GenericGenerator(name = "myIdGenerator", strategy = "assigned")  
    @Column(name="userid", unique=true, nullable=false, length=100)

    public String getUserid() {
        return this.userid;
    }
    
    public void setUserid(String userid) {
        this.userid = userid;
    }
    
    @Column(name="username", length=50)

    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    @Column(name="password", length=100)

    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Column(name="pay_password", length=100)

    public String getPayPassword() {
        return this.payPassword;
    }
    
    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }
    @Column(name="nickname", length=50)

    public String getNickname() {
        return this.nickname;
    }
    
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    
    @Column(name="email", length=50)

    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Column(name="realname", length=50)

    public String getRealname() {
        return this.realname;
    }
    
    public void setRealname(String realname) {
        this.realname = realname;
    }
    
    @Column(name="userhead")

    public String getUserhead() {
        return this.userhead;
    }
    
    public void setUserhead(String userhead) {
        this.userhead = userhead;
    }
    
    @Column(name="clientid", length=100)

    public String getClientid() {
        return this.clientid;
    }
    
    public void setClientid(String clientid) {
        this.clientid = clientid;
    }
    
    @Column(name="credit")

    public Integer getCredit() {
        return this.credit;
    }
    
    public void setCredit(Integer credit) {
        this.credit = credit;
    }
    
    @Column(name="share_credit")

    public Integer getShareCredit() {
        return this.shareCredit;
    }
    
    public void setShareCredit(Integer shareCredit) {
        this.shareCredit = shareCredit;
    }
    
    @Column(name="point")
    public Long getPoint() {
		return point;
	}
    
    public void setPoint(Long point) {
        this.point = point;
    }
    
    @Column(name="balance")

    public Long getBalance() {
        return this.balance;
    }
    
    public void setBalance(Long balance) {
        this.balance = balance;
    }
    
    @Column(name="deposit")

    public Long getDeposit() {
        return this.deposit;
    }
    
    public void setDeposit(Long deposit) {
        this.deposit = deposit;
    }
    
    @Column(name="car_number", length=50)

    public String getCarNumber() {
        return this.carNumber;
    }
    
    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }
    
    @Column(name="driver_status")

    public Short getDriverStatus() {
        return this.driverStatus;
    }
    
    public void setDriverStatus(Short driverStatus) {
        this.driverStatus = driverStatus;
    }
    
    @Column(name="status")

    public Short getStatus() {
        return this.status;
    }
    
    public void setStatus(Short status) {
        this.status = status;
    }
    
    @Column(name="memo")

    public String getMemo() {
        return this.memo;
    }
    
    public void setMemo(String memo) {
        this.memo = memo;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="users")

    public Set<UserPoint> getUserPoints() {
        return this.userPoints;
    }
    
    public void setUserPoints(Set<UserPoint> userPoints) {
        this.userPoints = userPoints;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="users")

    public Set<WithdrawLog> getWithdrawLogs() {
        return this.withdrawLogs;
    }
    
    public void setWithdrawLogs(Set<WithdrawLog> withdrawLogs) {
        this.withdrawLogs = withdrawLogs;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="users")

    public Set<BerthFavorite> getBerthFavorites() {
        return this.berthFavorites;
    }
    
    public void setBerthFavorites(Set<BerthFavorite> berthFavorites) {
        this.berthFavorites = berthFavorites;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="users")

    public Set<UserBalance> getUserBalances() {
        return this.userBalances;
    }
    
    public void setUserBalances(Set<UserBalance> userBalances) {
        this.userBalances = userBalances;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="users")

    public Set<CarparkFavorite> getCarparkFavorites() {
        return this.carparkFavorites;
    }
    
    public void setCarparkFavorites(Set<CarparkFavorite> carparkFavorites) {
        this.carparkFavorites = carparkFavorites;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="users")

    public Set<BerthShare> getBerthShares() {
        return this.berthShares;
    }
    
    public void setBerthShares(Set<BerthShare> berthShares) {
        this.berthShares = berthShares;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="users")

    public Set<Feedback> getFeedbacks() {
        return this.feedbacks;
    }
    
    public void setFeedbacks(Set<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="users")

    public Set<UserCredit> getUserCredits() {
        return this.userCredits;
    }
    
    public void setUserCredits(Set<UserCredit> userCredits) {
        this.userCredits = userCredits;
    }
    
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="users")
    public Set<CarparkAuthentication> getCarparkAuthentications() {
        return this.carparkAuthentications;
    }
    
    public void setCarparkAuthentications(Set<CarparkAuthentication> carparkAuthentications) {
        this.carparkAuthentications = carparkAuthentications;
    }

    @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="users")
	public DriverAuthentication getDriverAuthentication() {
		return driverAuthentication;
	}

	public void setDriverAuthentication(DriverAuthentication driverAuthentication) {
		this.driverAuthentication = driverAuthentication;
	}
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="users")
	public UsersConfigure getUsersConfigure() {
		return usersConfigure;
	}

	public void setUsersConfigure(UsersConfigure usersConfigure) {
		this.usersConfigure = usersConfigure;
	}
	@Column(name="register_time")
	public Long getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Long registerTime) {
		this.registerTime = registerTime;
	}
	@Column(name="login_time")
	public Long getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Long loginTime) {
		this.loginTime = loginTime;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="channelid")
	public AppChannel getAppChannel() {
		return appChannel;
	}

	public void setAppChannel(AppChannel appChannel) {
		this.appChannel = appChannel;
	}
	@Column(name="register_lon")
	public Double getRegisterLon() {
		return registerLon;
	}

	public void setRegisterLon(Double registerLon) {
		this.registerLon = registerLon;
	}
	@Column(name="register_lat")
	public Double getRegisterLat() {
		return registerLat;
	}

	public void setRegisterLat(Double registerLat) {
		this.registerLat = registerLat;
	}
	@Column(name="invitecode")
	public String getInvitecode() {
		return invitecode;
	}

	public void setInvitecode(String invitecode) {
		this.invitecode = invitecode;
	}
	
	@Transient
	public Date getRegisterTimeToDate() {
		if(registerTime != null)
		{
			registerTimeToDate = new Date(registerTime);
		}
		return registerTimeToDate;
	}

	public void setRegisterTimeToDate(Date registerTimeToDate) {
		this.registerTimeToDate = registerTimeToDate;
	}

	@Transient
	public Date getLoginTimeToDate() {
		if(loginTime != null)
		{
			loginTimeToDate = new Date(loginTime);
		}
		return loginTimeToDate;
	}

	public void setLoginTimeToDate(Date loginTimeToDate) {
		this.loginTimeToDate = loginTimeToDate;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userid == null) ? 0 : userid.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Users other = (Users) obj;
		if (userid == null) {
			if (other.userid != null)
				return false;
		} else if (!userid.equals(other.userid))
			return false;
		return true;
	}



	public String getOwner() {
		return owner;
	}
	



	public void setOwner(String owner) {
		this.owner = owner;
	}



	
	@OneToOne(mappedBy="users")
	public InvitecodeDetail getInvitecodeDetail() {
		return invitecodeDetail;
	}



	public void setInvitecodeDetail(InvitecodeDetail invitecodeDetail) {
		this.invitecodeDetail = invitecodeDetail;
	}



	@Column(name="open_id")
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	@Column(name="area")
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	
	@Column(name="coupon_balnce")
	public Long getCouponBalnce() {
		return couponBalnce;
	}

	public void setCouponBalnce(Long couponBalnce) {
		this.couponBalnce = couponBalnce;
	}
	
}
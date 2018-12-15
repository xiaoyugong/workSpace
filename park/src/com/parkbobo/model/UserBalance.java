package com.parkbobo.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * 用户账户记录
 * @author LH
 *
 */
@Entity
@Table(name="lq_user_balance")
@SequenceGenerator(name = "generator", sequenceName = "lq_user_balance_logid_seq", allocationSize = 1)
public class UserBalance  implements java.io.Serializable {


	
     
	private static final long serialVersionUID = -2777473543855141075L;
	/**
	 * 记录ID
	 */
	private Long logid;
	/**
	 * 用户ID
	 */
	private Users users;
	/**
	 * 管理员ID
	 */
	private Long adminId;
	/**
	 * 类型，0：增加；1：减少
	 */
	private Integer type;
	/**
	 * 操作类型,
	 * 1：充值到余额(recharge)
	 * 2：从余额提现(withdraw)
	 * 3：从余额支付(pay)
	 * 4：退款到余额(drawback)
	 */
	private Integer event;
	/**
	 * 金额
	 */
	private Long amount;
	/**
	 * 每增增减后的金额
	 */
	private Long amountLog;
	/**
	 * 详情介绍
	 */
	private String intro;
	/**
	 * 记录时间
	 */
	private Long posttime;
	/**
	 * 付款状态，0：未付款；1：已付款
	 */
	private Integer paystatus;
	/**
	 * 支付宝交易流水号
	 */
	private String tradeNo;
	/**
	 * 付款时间
	 */
	private Long paytime;
	/**
	 * 
	 */
	private String memo;

	private String  formatPosttime;

    public UserBalance() {
    }

    public UserBalance(Long logid) {
        this.logid = logid;
    }
    
    public UserBalance(Long logid, Users users, Long adminId, Integer type, Integer event, Long amount, Long amountLog, String intro, Long posttime, Integer paystatus, String tradeNo, Long paytime, String memo) {
        this.logid = logid;
        this.users = users;
        this.adminId = adminId;
        this.type = type;
        this.event = event;
        this.amount = amount;
        this.amountLog = amountLog;
        this.intro = intro;
        this.posttime = posttime;
        this.paystatus = paystatus;
        this.tradeNo = tradeNo;
        this.paytime = paytime;
        this.memo = memo;
    }

   
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
    @Column(name="logid", unique=true, nullable=false)

    public Long getLogid() {
        return this.logid;
    }
    
    public void setLogid(Long logid) {
        this.logid = logid;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="userid")

    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }
    
    @Column(name="admin_id")

    public Long getAdminId() {
        return this.adminId;
    }
    
    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }
    
    @Column(name="type")

    public Integer getType() {
        return this.type;
    }
    
    public void setType(Integer type) {
        this.type = type;
    }
    
    @Column(name="event")

    public Integer getEvent() {
        return this.event;
    }
    
    public void setEvent(Integer event) {
        this.event = event;
    }
    
    @Column(name="amount")

    public Long getAmount() {
        return this.amount;
    }
    
    public void setAmount(Long amount) {
        this.amount = amount;
    }
    
    @Column(name="amount_log")

    public Long getAmountLog() {
        return this.amountLog;
    }
    
    public void setAmountLog(Long amountLog) {
        this.amountLog = amountLog;
    }
    
    @Column(name="intro")

    public String getIntro() {
        return this.intro;
    }
    
    public void setIntro(String intro) {
        this.intro = intro;
    }
    
    @Column(name="posttime")

    public Long getPosttime() {
        return this.posttime;
    }
    
    public void setPosttime(Long posttime) {
        this.posttime = posttime;
    }
    
    @Column(name="memo")

    public String getMemo() {
        return this.memo;
    }
    
    public void setMemo(String memo) {
        this.memo = memo;
    }
    
    @Column(name="paystatus")
	public Integer getPaystatus() {
		return paystatus;
	}

	public void setPaystatus(Integer paystatus) {
		this.paystatus = paystatus;
	}
	@Column(name="trade_no")
	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	@Column(name="paytime")
	public Long getPaytime() {
		return paytime;
	}

	public void setPaytime(Long paytime) {
		this.paytime = paytime;
	}
	@Transient
	public String getFormatPosttime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(posttime != null)
		{
			formatPosttime =  sdf.format(new Date(posttime));
		}
		else
		{
			formatPosttime = "";
		}
		return formatPosttime;
	}
	public void setFormatPosttime(String formatPosttime) {
		this.formatPosttime = formatPosttime;
	}
}
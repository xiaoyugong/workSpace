package com.parkbobo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 车位订单
 * @author LH
 *
 */
@Entity
@Table(name="lq_berth_order")
@SequenceGenerator(name = "generator", sequenceName = "lq_berth_order_berthorderid_seq", allocationSize = 1)
public class BerthOrder  implements java.io.Serializable {

	private static final long serialVersionUID = 1535430336605328917L;
	/**
	 * 订单ID
	 */
	private Long berthorderid;
	/**
	 * 分享ID
	 */
    private BerthShare berthShare;
    /**
	 * 订单用户ID
	 */
    private String userid;
    /**
     * 订单用户昵称
     */
    private String nickname;
    /**
     * 订单用户姓名
     */
    private String realname;
    /**
	 * 提交订单时间
	 */
    private Long posttime;
    /**
	 * 结束订单时间
	 */
    private Long closetime;
    /**
	 * 订单用户电话
	 */
    private String telephone;
    /**
	 * 订单用户车牌号
	 */
    private String carNumber;
    /**
	 * 订单状态，0：预约开始，1：预约结束，2：待付款
	 */
    private Short status;
    /**
	 * 是否欠费，0：否；1：是
	 */
    private Short isArrearage;
    /**
     * 欠费金额
     */
    private Long arrearage;
    /**
	 * 违约状态，0：没有违约；1：违约处理中；2：违约处理结束
	 */
    private Short defaultStatus;
    /**
	 * 可停保证车金
	 */
    private Long enstopDeposit;
    /**
	 * 违约保证金
	 */
    private Long defaultDeposit;
    /**
	 * 是否已评论
	 */
    private Short isReview;
    /**
     * 是否删除
     */
    private Short isDel;
    /**
     * 起始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 首停分钟
     */
    private Integer beforeMins;
    /**
     * 首停价格
     */
    private Long beforePrice;
    /**
     * 每增分钟
     */
    private Integer afterMins;
    /**
     * 每增价格
     */
    private Long afterPrice;
    /**
     * 超期停车价格元/小时
     */
    private Long exceedPrice;
    /**
     * 重复日期
     */
    private String repeatDate;
    /**
     * 入场须知
     */
    private String description;
    /**
     * 车位分享信息是否更改,0:否；1：是
     */
    private Short isChange;
    /**
     * 提交订单经度
     */
    private Double postLon;
    /**
     * 提交订单纬度
     */
    private Double postLat;
    /**
     * 结束订单经度
     */
    private Double closeLon;
    /**
     * 结束订单纬度
     */
    private Double closeLat;
    
    /**
	 * 是否包含出入管理费，0：否；1：是
	 */
	private Short sfbhglf;
	
	/**
     * 是否离场
     */
    private Short isLeave;
    /**
     * 离场时间
     */
    private Long leavetime;
    /**
     * 是否入场
     */
    private Short isEnter;
    /**
     * 入场时间
     */
    private Long entertime;
	
	/**
	 * 本次停车总时长
	 */
	private Long stopTotalMillisecond;
	
	/**
	 * 本次停车预付费
	 */
	private Long stopPrepayMoney;
	
	/**
	 * 本次停车总付费
	 */
	private Long stopTotalMoney;
	
	/**
	 * 本次停车应付费
	 */
	private Long stopPayableMoney;
	
	/**
	 * 本次停车实付费
	 */
	private Long stopPayMoney;
	
	/**
	 * 本次停车超期费
	 */
	private Long stopOvertimeMoney;
	
	/**
	 * 本次停车欠费
	 */
	private Long stopArrearageMoney;
	
	/**
	 * 分享开始时间
	 */
	private Long startMillisecond;
	
	/**
	 * 分享结束时间
	 */
	private Long endMillisecond;
	
	/**
	 * 停车计费开始时间
	 */
	private Long stopMillisecond;
	
	/**
	 * 超期停车费是否已退,0:否；1：是
	 */
	private Short isDrawback;
	
    private String memo;
    /**
	 * 预付金额
	 */
    private Long prepayment;
    /**
	 * 预定起始时间
	 */
    private Long preStartTime;
    /**
	 * 预定结束时间
	 */
    private Long preEndTime;
    /**
	 * 入场入口名
	 */
    private String entranceName;
    /**
	 * 入场入口id
	 */
    private Long entranceDoorid;
    /**
	 * 出场出口名
	 */
    private String exitName;
    /**
	 * 出场出口id
	 */
    private Long exitDoorid;
    /**
     * 补缴现金
     */
    private Long payCash;
    /**
     * 车位主收益
     */
    private Long ownerRevenue;
    /**
     * 物业收益
     */
    private Long propertyRevenue;
    /**
     * 平台收益
     */
    private Long companyRevenue;
    
    /**
     * 订单到期是否提醒  0：未提醒；1：已提醒 
     */
    private Integer isRemind;
    /**
     * 地锁控制模式变更
     * 	 0:无地锁
     *   1：待变更为订单模式；2：已变更为订单模式；
     *   3：待变更为蓝牙模式；4：已变更为蓝牙模式；
     * 
     */
    private Integer groundlockControlType;
    /**
     * 结束订单后是否升锁 0：否；1：是
     */
    private Integer isUplock;
    /**
     * 尝试升锁次数
     */
    private Integer uplockNum;
    private Set<BerthReview> berthReviews = new HashSet<BerthReview>(0);

    public BerthOrder() {
    	
    }

    public BerthOrder(Long berthorderid) {
        this.berthorderid = berthorderid;
    }
    
    public BerthOrder(Long berthorderid, BerthShare berthShare, String userid, 
    		Long posttime, Long closetime, String telephone, 
    		String carNumber, Short status, Short isArrearage, 
    		Long arrearage, Short defaultStatus, Long enstopDeposit, 
    		Long defaultDeposit, Short isReview, Short isDel,
    		Date startTime, Date endTime, Integer beforeMins, 
            Long beforePrice, Integer afterMins, Long afterPrice, 
            Long exceedPrice, String repeatDate, String description, 
            Short isChange, Double postLon, Double postLat,
            Double closeLon, Double closeLat, Short sfbhglf, 
            Short isLeave, Long leavetime, Short isEnter, Long entertime,
            Long stopTotalMillisecond, Long stopPrepayMoney, Long stopTotalMoney,
            Long stopPayableMoney, Long stopPayMoney, Long stopOvertimeMoney,Long stopArrearageMoney,
            Long startMillisecond, Long endMillisecond, Long stopMillisecond, 
            Short isDrawback, String memo, Long prepayment, Long preStartTime,Long preEndTime,
            String entranceName,Long entranceDoorid,String exitName,Long exitDoorid,
            Long payCash, Long ownerRevenue, Long propertyRevenue, Long companyRevenue,
            Integer isRemind, Integer groundlockControlType, 
            Integer isUplock, Integer uplockNum,
            String nickname, String realname) {
        this.berthorderid = berthorderid;
        this.berthShare = berthShare;
        this.userid = userid;
        this.posttime = posttime;
        this.closetime = closetime;
        this.telephone = telephone;
        this.carNumber = carNumber;
        this.status = status;
        this.isArrearage = isArrearage;
        this.arrearage = arrearage;
        this.defaultStatus = defaultStatus;
        this.enstopDeposit = enstopDeposit;
        this.defaultDeposit = defaultDeposit;
        this.isReview = isReview;
        this.isDel = isDel;
        this.memo = memo;
        this.startTime = startTime;
        this.endTime = endTime;
        this.beforeMins = beforeMins;
        this.beforePrice = beforePrice;
        this.afterMins = afterMins;
        this.afterPrice = afterPrice;
        this.exceedPrice = exceedPrice;
        this.repeatDate = repeatDate;
        this.description = description;
        this.isChange = isChange;
        this.postLon = postLon;
        this.postLat = postLat;
        this.closeLon = closeLon;
        this.closeLat = closeLat;
        this.sfbhglf = sfbhglf;
        this.isLeave = isLeave;
        this.leavetime = leavetime;
        this.isEnter = isEnter;
        this.entertime = entertime;
        this.stopTotalMillisecond = stopTotalMillisecond;
        this.stopArrearageMoney = stopArrearageMoney;
        this.stopOvertimeMoney = stopOvertimeMoney;
        this.stopPayableMoney = stopPayableMoney;
        this.stopPayMoney = stopPayMoney;
        this.stopPrepayMoney = stopPrepayMoney;
        this.stopTotalMoney = stopTotalMoney;
        this.startMillisecond = startMillisecond;
        this.endMillisecond = endMillisecond;
        this.stopMillisecond = stopMillisecond;
        this.isDrawback = isDrawback;
        
        this.prepayment = prepayment;
        this.preStartTime = preStartTime;
        this.preEndTime = preEndTime;
        this.entranceName = entranceName;
        this.entranceDoorid = entranceDoorid;
        this.exitName = exitName;
        this.exitDoorid = exitDoorid;
        this.payCash = payCash;
        this.ownerRevenue = ownerRevenue;
        this.propertyRevenue = propertyRevenue;
        this.companyRevenue = companyRevenue;
        this.isRemind = isRemind;
        this.groundlockControlType = groundlockControlType;
        this.isUplock = isUplock;
        this.uplockNum = uplockNum;
        this.nickname = nickname;
        this.realname = realname;
    }

   
    @Id 
    @Column(name="berthorderid", unique=true, nullable=false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
    public Long getBerthorderid() {
        return this.berthorderid;
    }
    
    public void setBerthorderid(Long berthorderid) {
        this.berthorderid = berthorderid;
    }
	
    
   
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="shareid")

    public BerthShare getBerthShare() {
        return this.berthShare;
    }
    
    public void setBerthShare(BerthShare berthShare) {
        this.berthShare = berthShare;
    }
    
    @Column(name="userid", length=100)
    public String getUserid() {
        return this.userid;
    }
    
    public void setUserid(String userid) {
        this.userid = userid;
    }
    @Column(name="nickname")
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	@Column(name="realname")
	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}
	
    
    @Column(name="posttime", length=29)

    public Long getPosttime() {
        return this.posttime;
    }
    
    public void setPosttime(Long posttime) {
        this.posttime = posttime;
    }
    
    @Column(name="closetime", length=29)

    public Long getClosetime() {
        return this.closetime;
    }
    
    public void setClosetime(Long closetime) {
        this.closetime = closetime;
    }
    
    @Column(name="telephone", length=20)

    public String getTelephone() {
        return this.telephone;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    @Column(name="car_number", length=50)

    public String getCarNumber() {
        return this.carNumber;
    }
    
    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }
    
    @Column(name="status")

    public Short getStatus() {
        return this.status;
    }
    
    public void setStatus(Short status) {
        this.status = status;
    }
    
    @Column(name="is_arrearage")

    public Short getIsArrearage() {
        return this.isArrearage;
    }
    
    public void setIsArrearage(Short isArrearage) {
        this.isArrearage = isArrearage;
    }
    
    @Column(name="default_status")

    public Short getDefaultStatus() {
        return this.defaultStatus;
    }
    
    public void setDefaultStatus(Short defaultStatus) {
        this.defaultStatus = defaultStatus;
    }
    
    @Column(name="enstop_deposit")

    public Long getEnstopDeposit() {
        return this.enstopDeposit;
    }
    
    public void setEnstopDeposit(Long enstopDeposit) {
        this.enstopDeposit = enstopDeposit;
    }
    
    @Column(name="default_deposit")

    public Long getDefaultDeposit() {
        return this.defaultDeposit;
    }
    
    public void setDefaultDeposit(Long defaultDeposit) {
        this.defaultDeposit = defaultDeposit;
    }
    
    @Column(name="is_review")

    public Short getIsReview() {
        return this.isReview;
    }
    
    public void setIsReview(Short isReview) {
        this.isReview = isReview;
    }
    
    @Column(name="memo")

    public String getMemo() {
        return this.memo;
    }
    
    public void setMemo(String memo) {
        this.memo = memo;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="berthOrder")

    public Set<BerthReview> getBerthReviews() {
        return this.berthReviews;
    }
    
    public void setBerthReviews(Set<BerthReview> berthReviews) {
        this.berthReviews = berthReviews;
    }
    @Column(name="is_del")
	public Short getIsDel() {
		return isDel;
	}

	public void setIsDel(Short isDel) {
		this.isDel = isDel;
	}
	 @Column(name="arrearage")
	public Long getArrearage() {
		return arrearage;
	}

	public void setArrearage(Long arrearage) {
		this.arrearage = arrearage;
	}
	@Column(name = "start_time", length = 15)
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	@Column(name = "end_time", length = 15)
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	@Column(name = "before_mins")
	public Integer getBeforeMins() {
		return beforeMins;
	}

	public void setBeforeMins(Integer beforeMins) {
		this.beforeMins = beforeMins;
	}
	@Column(name = "before_price")
	public Long getBeforePrice() {
		return beforePrice;
	}

	public void setBeforePrice(Long beforePrice) {
		this.beforePrice = beforePrice;
	}
	@Column(name = "after_mins")
	public Integer getAfterMins() {
		return afterMins;
	}

	public void setAfterMins(Integer afterMins) {
		this.afterMins = afterMins;
	}
	@Column(name = "after_price")
	public Long getAfterPrice() {
		return afterPrice;
	}

	public void setAfterPrice(Long afterPrice) {
		this.afterPrice = afterPrice;
	}
	@Column(name = "exceed_price")
	public Long getExceedPrice() {
		return exceedPrice;
	}

	public void setExceedPrice(Long exceedPrice) {
		this.exceedPrice = exceedPrice;
	}
	@Column(name = "repeat_date")
	public String getRepeatDate() {
		return repeatDate;
	}

	public void setRepeatDate(String repeatDate) {
		this.repeatDate = repeatDate;
	}
	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	@Column(name = "is_change")
	public Short getIsChange() {
		return isChange;
	}

	public void setIsChange(Short isChange) {
		this.isChange = isChange;
	}
	@Column(name = "post_lon")
	public Double getPostLon() {
		return postLon;
	}

	public void setPostLon(Double postLon) {
		this.postLon = postLon;
	}
	@Column(name = "post_lat")
	public Double getPostLat() {
		return postLat;
	}

	public void setPostLat(Double postLat) {
		this.postLat = postLat;
	}
	@Column(name = "close_lon")
	public Double getCloseLon() {
		return closeLon;
	}

	public void setCloseLon(Double closeLon) {
		this.closeLon = closeLon;
	}
	@Column(name = "close_lat")
	public Double getCloseLat() {
		return closeLat;
	}

	public void setCloseLat(Double closeLat) {
		this.closeLat = closeLat;
	}
	@Column(name = "sfbhglf")
	public Short getSfbhglf() {
		return sfbhglf;
	}

	public void setSfbhglf(Short sfbhglf) {
		this.sfbhglf = sfbhglf;
	}

	
	@Column(name = "stop_prepay_money")
	public Long getStopPrepayMoney() {
		return stopPrepayMoney;
	}

	public void setStopPrepayMoney(Long stopPrepayMoney) {
		this.stopPrepayMoney = stopPrepayMoney;
	}
	
	@Column(name = "stop_total_money")
	public Long getStopTotalMoney() {
		return stopTotalMoney;
	}

	public void setStopTotalMoney(Long stopTotalMoney) {
		this.stopTotalMoney = stopTotalMoney;
	}
	
	@Column(name = "stop_payable_money")
	public Long getStopPayableMoney() {
		return stopPayableMoney;
	}

	public void setStopPayableMoney(Long stopPayableMoney) {
		this.stopPayableMoney = stopPayableMoney;
	}
	
	@Column(name = "stop_pay_money")
	public Long getStopPayMoney() {
		return stopPayMoney;
	}

	public void setStopPayMoney(Long stopPayMoney) {
		this.stopPayMoney = stopPayMoney;
	}
	
	@Column(name = "stop_overtime_money")
	public Long getStopOvertimeMoney() {
		return stopOvertimeMoney;
	}

	public void setStopOvertimeMoney(Long stopOvertimeMoney) {
		this.stopOvertimeMoney = stopOvertimeMoney;
	}
	
	@Column(name = "stop_arrearage_money")
	public Long getStopArrearageMoney() {
		return stopArrearageMoney;
	}

	public void setStopArrearageMoney(Long stopArrearageMoney) {
		this.stopArrearageMoney = stopArrearageMoney;
	}
	
	@Column(name="is_leave")
	public Short getIsLeave() {
		return isLeave;
	}

	public void setIsLeave(Short isLeave) {
		this.isLeave = isLeave;
	}
	@Column(name="leavetime")
	public Long getLeavetime() {
		return leavetime;
	}

	public void setLeavetime(Long leavetime) {
		this.leavetime = leavetime;
	}
	@Column(name="is_enter")
	public Short getIsEnter() {
		return isEnter;
	}

	public void setIsEnter(Short isEnter) {
		this.isEnter = isEnter;
	}
	@Column(name="entertime")
	public Long getEntertime() {
		return entertime;
	}

	public void setEntertime(Long entertime) {
		this.entertime = entertime;
	}

	@Column(name="start_millisecond")
	public Long getStartMillisecond() {
		return startMillisecond;
	}

	public void setStartMillisecond(Long startMillisecond) {
		this.startMillisecond = startMillisecond;
	}

	@Column(name="end_millisecond")
	public Long getEndMillisecond() {
		return endMillisecond;
	}

	public void setEndMillisecond(Long endMillisecond) {
		this.endMillisecond = endMillisecond;
	}

	@Column(name="stop_millisecond")
	public Long getStopMillisecond() {
		return stopMillisecond;
	}

	public void setStopMillisecond(Long stopMillisecond) {
		this.stopMillisecond = stopMillisecond;
	}
	@Column(name="is_drawback")
	public Short getIsDrawback() {
		return isDrawback;
	}

	public void setIsDrawback(Short isDrawback) {
		this.isDrawback = isDrawback;
	}
	@Column(name="stop_total_millisecond")
	public Long getStopTotalMillisecond() {
		return stopTotalMillisecond;
	}

	public void setStopTotalMillisecond(Long stopTotalMillisecond) {
		this.stopTotalMillisecond = stopTotalMillisecond;
	}
	@Column(name="prepayment")
	public Long getPrepayment() {
		return prepayment;
	}

	public void setPrepayment(Long prepayment) {
		this.prepayment = prepayment;
	}
	@Column(name="pre_start_time")
	public Long getPreStartTime() {
		return preStartTime;
	}

	public void setPreStartTime(Long preStartTime) {
		this.preStartTime = preStartTime;
	}
	@Column(name="pre_end_time")
	public Long getPreEndTime() {
		return preEndTime;
	}

	public void setPreEndTime(Long preEndTime) {
		this.preEndTime = preEndTime;
	}
	@Column(name="entrance_name")
	public String getEntranceName() {
		return entranceName;
	}

	public void setEntranceName(String entranceName) {
		this.entranceName = entranceName;
	}
	@Column(name="entrance_doorid")
	public Long getEntranceDoorid() {
		return entranceDoorid;
	}

	public void setEntranceDoorid(Long entranceDoorid) {
		this.entranceDoorid = entranceDoorid;
	}
	@Column(name="exit_name")
	public String getExitName() {
		return exitName;
	}

	public void setExitName(String exitName) {
		this.exitName = exitName;
	}
	@Column(name="exit_doorid")
	public Long getExitDoorid() {
		return exitDoorid;
	}

	public void setExitDoorid(Long exitDoorid) {
		this.exitDoorid = exitDoorid;
	}
	@Column(name="pay_cash")
	public Long getPayCash() {
		return payCash;
	}

	public void setPayCash(Long payCash) {
		this.payCash = payCash;
	}
	@Column(name="owner_revenue")
	public Long getOwnerRevenue() {
		return ownerRevenue;
	}

	public void setOwnerRevenue(Long ownerRevenue) {
		this.ownerRevenue = ownerRevenue;
	}
	@Column(name="company_revenue")
	public Long getCompanyRevenue() {
		return companyRevenue;
	}

	public void setCompanyRevenue(Long companyRevenue) {
		this.companyRevenue = companyRevenue;
	}
	@Column(name="property_revenue")
	public Long getPropertyRevenue() {
		return propertyRevenue;
	}

	public void setPropertyRevenue(Long propertyRevenue) {
		this.propertyRevenue = propertyRevenue;
	}
	@Column(name="is_remind")
	public Integer getIsRemind() {
		return isRemind;
	}

	public void setIsRemind(Integer isRemind) {
		this.isRemind = isRemind;
	}
	@Column(name="groundlock_control_type")
	public Integer getGroundlockControlType() {
		return groundlockControlType;
	}

	public void setGroundlockControlType(Integer groundlockControlType) {
		this.groundlockControlType = groundlockControlType;
	}
	@Column(name="is_uplock")
	public Integer getIsUplock() {
		return isUplock;
	}

	public void setIsUplock(Integer isUplock) {
		this.isUplock = isUplock;
	}
	@Column(name="uplock_num")
	public Integer getUplockNum() {
		return uplockNum;
	}

	public void setUplockNum(Integer uplockNum) {
		this.uplockNum = uplockNum;
	}
	
	@Transient
	public String getFormatRepeatDate(){
		//把分享星期格式化成数字,并排序
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("1", 2);
		map.put("2", 3);
		map.put("3", 4);
		map.put("4", 5);
		map.put("5", 6);
		map.put("6", 7);
		map.put("7", 1);
	
		String[] strarr = this.repeatDate.split(",");
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		for(String str:strarr){
			if(!str.equals(""))
				arrayList.add(map.get(str));
			
		}
		Collections.sort(arrayList);
		
		//把排序后的数字格式化成字符串
		Map<Integer,String> newmap = new HashMap<Integer,String>();
		newmap.put(1, "一");
		newmap.put(2, "二");
		newmap.put(3, "三");
		newmap.put(4, "四");
		newmap.put(5, "五");
		newmap.put(6, "六");
		newmap.put(7, "日");
		StringBuffer sbBuffer = new StringBuffer(); 
		for(Integer str:arrayList){
				sbBuffer.append(newmap.get(str)+",");
		}
		return sbBuffer.toString();

	}

	
	@Transient
	public String getIscq() {
		if (status==0) {
			if(System.currentTimeMillis()<endMillisecond){
				return "否";
			}
			return "是";
		}
		return "否";
	}

	

}
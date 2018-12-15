package com.parkbobo.model;

// default package

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
 * 车位分享
 * 
 * @author LH
 * 
 */
@Entity
@Table(name = "lq_berth_share")
@SequenceGenerator(name = "generator", sequenceName = "lq_berth_share_shareid_seq", allocationSize = 1)
public class BerthShare implements java.io.Serializable {
	private static final long serialVersionUID = 259306152472342659L;
	/**
	 * 分享ID
	 */
	private Long shareid;
	/**
	 * 分享用户ID
	 */
	private Users users;
	/**
	 * 停车场ID
	 */
	private Long carparkid;
	private Carpark carpark;
	/**
	 * 楼层ID
	 */
	public Long floorid;
	/**
	 * 车位ID
	 */
	private Long berthid;
	/**
	 * 停车场名称
	 */
	private String carparkname;
	/**
	 * 车位号
	 */
	private String berthnum;
	/**
	 * 分享类型，0：有微地图；1：为微地图
	 */
	private Short sharetype;
	/**
	 * 分享数量
	 */
	private Integer sharenum;
	/**
	 * 空余数量
	 */
	private Integer emptynum;
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
	 * 可停保证金
	 */
	private Long enstopDeposit;
	/**
	 * 违约保证金
	 */
	private Long defaultDeposit;
	/**
	 * 超期停车价格
	 */
	private Long exceedPrice;
	/**
	 * 重复日期
	 */
	private String repeatDate;
	/**
	 * 是否关闭，0：否；1：是
	 */
	private Short isClose;
	/**
	 * 是否删除，0：否；1：是
	 */
	private Short isDel;
	/**
	 * 是否包含出入管理费，0：否；1：是
	 */
	private Short sfbhglf;
	/**
	 * 是否已被预约，0：否；1：是
	 */
	private Integer isReserve;
	/**
	 * 入场须知
	 */
	private String description;
	/**
	 * 是否已认证，0：未审核；1：认证审核中；2：通过认证；3：未通过认证
	 */
	private Short isAuthentication;
	/**
	 * 分享时间
	 */
	private Long sharetime;
	/**
	 * 更新数据
	 */
	private Long updatetime;
	/**
     * 是否安装地锁，0：否；1：是
     */
    private Integer isGroundlock;
    /**
     * 地锁标识号
     */
    private String groundlockid;
	
	private String memo;
	private Set<BerthReview> berthReviews = new HashSet<BerthReview>(0);
	private Set<BerthOrder> berthOrders = new HashSet<BerthOrder>(0);

	public BerthShare() {
	}

	public BerthShare(Long shareid) {
		this.shareid = shareid;
	}

	public BerthShare(Long shareid, Users users, Long carparkid, Long floorid,
			String carparkname, Long berthid, String berthnum, Short sharetype,
			Integer sharenum, Integer emptynum, Date startTime, Date endTime,
			Integer beforeMins, Long beforePrice, Integer afterMins,
			Long afterPrice, Long enstopDeposit, Long defaultDeposit,
			Long exceedPrice, String repeatDate, Short isClose, Short isDel,
			Short sfbhglf, Integer isReserve, Short isAuthentication,
			String description, Long sharetime, Long updatetime, String memo) {
		this.shareid = shareid;
		this.users = users;
		this.carparkid = carparkid;
		this.floorid = floorid;
		this.carparkname = carparkname;
		this.berthid = berthid;
		this.berthnum = berthnum;
		this.sharetype = sharetype;
		this.sharenum = sharenum;
		this.emptynum = emptynum;
		this.startTime = startTime;
		this.endTime = endTime;
		this.beforeMins = beforeMins;
		this.beforePrice = beforePrice;
		this.afterMins = afterMins;
		this.afterPrice = afterPrice;
		this.enstopDeposit = enstopDeposit;
		this.defaultDeposit = defaultDeposit;
		this.exceedPrice = exceedPrice;
		this.repeatDate = repeatDate;
		this.isClose = isClose;
		this.isDel = isDel;
		this.sfbhglf = sfbhglf;
		this.isReserve = isReserve;
		this.isAuthentication = isAuthentication;
		this.description = description;
		this.sharetime = sharetime;
		this.updatetime = updatetime;
		this.memo = memo;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
	@Column(name = "shareid", unique = true, nullable = false)
	public Long getShareid() {
		return this.shareid;
	}

	public void setShareid(Long shareid) {
		this.shareid = shareid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid")
	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	@Column(name = "carparkid")
	public Long getCarparkid() {
		return this.carparkid;
	}

	public void setCarparkid(Long carparkid) {
		this.carparkid = carparkid;
	}
	
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "carparkid", unique = true, nullable = false, insertable = false, updatable = false)
	
	public Carpark getCarpark() {
		return carpark;
	}

	public void setCarpark(Carpark carpark) {
		this.carpark = carpark;
	}

	@Column(name = "berthid")
	public Long getBerthid() {
		return this.berthid;
	}

	public void setBerthid(Long berthid) {
		this.berthid = berthid;
	}

	@Column(name = "berthnum", length = 100)
	public String getBerthnum() {
		return this.berthnum;
	}

	public void setBerthnum(String berthnum) {
		this.berthnum = berthnum;
	}

	@Column(name = "sharetype")
	public Short getSharetype() {
		return this.sharetype;
	}

	public void setSharetype(Short sharetype) {
		this.sharetype = sharetype;
	}

	@Column(name = "sharenum")
	public Integer getSharenum() {
		return this.sharenum;
	}

	public void setSharenum(Integer sharenum) {
		this.sharenum = sharenum;
	}

	@Column(name = "emptynum")
	public Integer getEmptynum() {
		return this.emptynum;
	}

	public void setEmptynum(Integer emptynum) {
		this.emptynum = emptynum;
	}

	@Column(name = "start_time", length = 15)
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name = "end_time", length = 15)
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "before_mins")
	public Integer getBeforeMins() {
		return this.beforeMins;
	}

	public void setBeforeMins(Integer beforeMins) {
		this.beforeMins = beforeMins;
	}

	@Column(name = "before_price")
	public Long getBeforePrice() {
		return this.beforePrice;
	}

	public void setBeforePrice(Long beforePrice) {
		this.beforePrice = beforePrice;
	}

	@Column(name = "after_mins")
	public Integer getAfterMins() {
		return this.afterMins;
	}

	public void setAfterMins(Integer afterMins) {
		this.afterMins = afterMins;
	}

	@Column(name = "after_price")
	public Long getAfterPrice() {
		return this.afterPrice;
	}

	public void setAfterPrice(Long afterPrice) {
		this.afterPrice = afterPrice;
	}

	@Column(name = "enstop_deposit")
	public Long getEnstopDeposit() {
		return this.enstopDeposit;
	}

	public void setEnstopDeposit(Long enstopDeposit) {
		this.enstopDeposit = enstopDeposit;
	}

	@Column(name = "default_deposit")
	public Long getDefaultDeposit() {
		return this.defaultDeposit;
	}

	public void setDefaultDeposit(Long defaultDeposit) {
		this.defaultDeposit = defaultDeposit;
	}

	@Column(name = "exceed_price")
	public Long getExceedPrice() {
		return this.exceedPrice;
	}

	public void setExceedPrice(Long exceedPrice) {
		this.exceedPrice = exceedPrice;
	}

	@Column(name = "repeat_date", length = 50)
	public String getRepeatDate() {
		return this.repeatDate;
	}

	public void setRepeatDate(String repeatDate) {
		this.repeatDate = repeatDate;
	}

	@Column(name = "is_close")
	public Short getIsClose() {
		return this.isClose;
	}

	public void setIsClose(Short isClose) {
		this.isClose = isClose;
	}

	@Column(name = "is_del")
	public Short getIsDel() {
		return this.isDel;
	}

	public void setIsDel(Short isDel) {
		this.isDel = isDel;
	}

	@Column(name = "sfbhglf")
	public Short getSfbhglf() {
		return this.sfbhglf;
	}

	public void setSfbhglf(Short sfbhglf) {
		this.sfbhglf = sfbhglf;
	}

	@Column(name = "is_reserve")
	public Integer getIsReserve() {
		return this.isReserve;
	}

	public void setIsReserve(Integer isReserve) {
		this.isReserve = isReserve;
	}

	@Column(name = "description")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "is_authentication")
	public Short getIsAuthentication() {
		return isAuthentication;
	}

	public void setIsAuthentication(Short isAuthentication) {
		this.isAuthentication = isAuthentication;
	}

	@Column(name = "memo")
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "berthShare")
	public Set<BerthReview> getBerthReviews() {
		return this.berthReviews;
	}

	public void setBerthReviews(Set<BerthReview> berthReviews) {
		this.berthReviews = berthReviews;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "berthShare")
	public Set<BerthOrder> getBerthOrders() {
		return this.berthOrders;
	}

	public void setBerthOrders(Set<BerthOrder> berthOrders) {
		this.berthOrders = berthOrders;
	}

	@Column(name = "carparkname")
	public String getCarparkname() {
		return carparkname;
	}

	public void setCarparkname(String carparkname) {
		this.carparkname = carparkname;
	}

	@Column(name = "floorid")
	public Long getFloorid() {
		return floorid;
	}

	public void setFloorid(Long floorid) {
		this.floorid = floorid;
	}
	@Column(name = "sharetime")
	public Long getSharetime() {
		return sharetime;
	}

	public void setSharetime(Long sharetime) {
		this.sharetime = sharetime;
	}
	@Column(name = "updatetime")
	public Long getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Long updatetime) {
		this.updatetime = updatetime;
	}
	@Column(name="is_groundlock")
	public Integer getIsGroundlock() {
		return isGroundlock;
	}

	public void setIsGroundlock(Integer isGroundlock) {
		this.isGroundlock = isGroundlock;
	}
	@Column(name="groundlockid")
	public String getGroundlockid() {
		return groundlockid;
	}

	public void setGroundlockid(String groundlockid) {
		this.groundlockid = groundlockid;
	}
	@Transient
	public String getFormatRepeatDate(){
		//把分享星期格式化成数字,并排序
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("1", 7);
		map.put("2", 1);
		map.put("3", 2);
		map.put("4", 3);
		map.put("5", 4);
		map.put("6", 5);
		map.put("7", 6);
	
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
}
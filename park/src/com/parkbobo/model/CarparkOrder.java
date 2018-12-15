package com.parkbobo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name="lq_carpark_order")
public class CarparkOrder implements Serializable{

	
	private String orderid;
	
	  /**
	 * 订单用户ID
	 */
    private String userid;
    private String carparkname;
    private Long carparkid;
    
    /**
     * 订单用户昵称
     */
    private String nickname;
    /**
     * 订单用户姓名
     */
    private String realname;
    /**
	 * 订单用户电话
	 */
    private String telephone;
    /**
	 * 订单用户车牌号
	 */
    private String carNumber;
    /**
     * 是否删除
     */
    private Short isDel;
    
    
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
     * 入场须知
     */
    private String description;
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
     * 是否离场  0:否，1：是
     */
    private Short isLeave;
    /**
     * 离场时间
     */
    private Long leavetime;
    /**
     * 是否入场   0:否，1：是
     */
    private Short isEnter;
    /**
     * 入场时间
     */
    private Long entertime;
    /**
     * 下单时间
     */
    private Long posttime;
	
	/**
	 * 本次停车总时长
	 */
	private Long stopTotalMillisecond;
	
	/**
	 * 本次停车应付费
	 */
	private Long stopTotalMoney;
	
	
	/**
	 * 本次停车实付费
	 */
	private Long stopPayMoney;
	
	/**
	 * 本次停车超期费
	 */
	private Long stopOvertimeMoney;
	
	
	/**
	 * 付费时间
	 */
	private Long payTime;
    private String memo;
    /**
     * 1：结束，0:未结束
     */
    private  Short initiativeOrder;
	/**
	 * 
	 */
	private String tempCardNumber;
	
	@Id
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getCarparkname() {
		return carparkname;
	}
	public void setCarparkname(String carparkname) {
		this.carparkname = carparkname;
	}
	public Long getCarparkid() {
		return carparkid;
	}
	public void setCarparkid(Long carparkid) {
		this.carparkid = carparkid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	@Column(name="car_number")
	public String getCarNumber() {
		return carNumber;
	}
	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	@Column(name="is_del")
	public Short getIsDel() {
		return isDel;
	}
	public void setIsDel(Short isDel) {
		this.isDel = isDel;
	}
	@Column(name="before_mins")
	public Integer getBeforeMins() {
		return beforeMins;
	}
	public void setBeforeMins(Integer beforeMins) {
		this.beforeMins = beforeMins;
	}
	@Column(name="before_price")
	public Long getBeforePrice() {
		return beforePrice;
	}
	public void setBeforePrice(Long beforePrice) {
		this.beforePrice = beforePrice;
	}
	@Column(name="after_mins")
	public Integer getAfterMins() {
		return afterMins;
	}
	public void setAfterMins(Integer afterMins) {
		this.afterMins = afterMins;
	}
	@Column(name="after_price")
	public Long getAfterPrice() {
		return afterPrice;
	}
	public void setAfterPrice(Long afterPrice) {
		this.afterPrice = afterPrice;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Column(name="post_lon")
	public Double getPostLon() {
		return postLon;
	}
	public void setPostLon(Double postLon) {
		this.postLon = postLon;
	}
	@Column(name="post_lat")
	public Double getPostLat() {
		return postLat;
	}
	public void setPostLat(Double postLat) {
		this.postLat = postLat;
	}
	@Column(name="close_lon")
	public Double getCloseLon() {
		return closeLon;
	}
	public void setCloseLon(Double closeLon) {
		this.closeLon = closeLon;
	}
	@Column(name="close_lat")
	public Double getCloseLat() {
		return closeLat;
	}
	public void setCloseLat(Double closeLat) {
		this.closeLat = closeLat;
	}
	
	@Column(name="is_leave")
	public Short getIsLeave() {
		return isLeave;
	}
	public void setIsLeave(Short isLeave) {
		this.isLeave = isLeave;
	}
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
	public Long getEntertime() {
		return entertime;
	}
	public void setEntertime(Long entertime) {
		this.entertime = entertime;
	}
	@Column(name="stop_mins")
	public Long getStopTotalMillisecond() {
		return stopTotalMillisecond;
	}
	public void setStopTotalMillisecond(Long stopTotalMillisecond) {
		this.stopTotalMillisecond = stopTotalMillisecond;
	}
	@Column(name="stop_total_money")
	public Long getStopTotalMoney() {
		return stopTotalMoney;
	}
	public void setStopTotalMoney(Long stopTotalMoney) {
		this.stopTotalMoney = stopTotalMoney;
	}
	@Column(name="stop_pay_money")
	public Long getStopPayMoney() {
		return stopPayMoney;
	}
	public void setStopPayMoney(Long stopPayMoney) {
		this.stopPayMoney = stopPayMoney;
	}
	@Column(name="stop_overtime_money")
	public Long getStopOvertimeMoney() {
		return stopOvertimeMoney;
	}
	public void setStopOvertimeMoney(Long stopOvertimeMoney) {
		this.stopOvertimeMoney = stopOvertimeMoney;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	@Column(name="initiative_order")
	public Short getInitiativeOrder() {
		return initiativeOrder;
	}
	public void setInitiativeOrder(Short initiativeOrder) {
		this.initiativeOrder = initiativeOrder;
	}
	
	@Column(name="temp_card_number")
	public String getTempCardNumber() {
		return tempCardNumber;
	}
	public void setTempCardNumber(String tempCardNumber) {
		this.tempCardNumber = tempCardNumber;
	}
	public Long getPosttime() {
		return posttime;
	}
	public void setPosttime(Long posttime) {
		this.posttime = posttime;
	}
	@Column(name="paytime")
	public Long getPayTime() {
		return payTime;
	}
	public void setPayTime(Long payTime) {
		this.payTime = payTime;
	}
	@Override
	public String toString() {
		return "CarparkOrder [stopOvertimeMoney=" + stopOvertimeMoney
				+ ", stopPayMoney=" + stopPayMoney + ", stopTotalMillisecond="
				+ stopTotalMillisecond + ", stopTotalMoney=" + stopTotalMoney
				+ "]";
	}
	
	
	
}

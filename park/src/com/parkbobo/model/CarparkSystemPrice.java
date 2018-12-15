package com.parkbobo.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "lq_carpark_system_price")
public class CarparkSystemPrice implements Serializable{
	/**
	 * 系统定价
	 */
	private static final long serialVersionUID = -4211783809288838848L;
	/**
	 * 停车场ID;
	 * */
	private Long carparkid;
	/**
	 * 
	 * */
	private Carpark carpark;
	/**
	 * 是否开启系统定价
	 * */
	private Integer isSystemPrice;
	/**
	 * 临停首停时间
	 * */
	private Integer beforeMinsTemporary;
	/**
	 * 临停首停价格
	 * */
	private Double beforePriceTemporary;
	/**
	 * 预约首停时间
	 * */
	private Integer beforeMinsReserve;
	/**
	 * 预约首停价格
	 * */
	private Double beforePriceReserve;
	/**
	 * 临停每增时间
	 * */
	private Integer afterMinsTemporary;
	/**
	 * 临停每增价格
	 * */
	private Double afterPriceTemporary;
	/**
	 * 预约每增时间
	 * */
	private Integer afterMinsReserve; 
	/**
	 * 预约每增价格
	 * */
	private Double afterPriceReserve;
	/**
	 * 临停保证金
	 * */
	private Double violatePriceTemporary;
	/**
	 * 预约保证金
	 * */
	private Double violatePriceReserve;
	/**
	 * 分享开始时间
	 * */
	private Date shareStartTime;
	/**
	 * 分享结束时间
	 * */
	private Date shareEndTime;
	/**
	 * 分享周期
	 * */
	private String shareRepeatDate;
	/**
	 * 是否包含出入场费用
	 * */
	private Integer isContainExitPrice;
	/**
	 * 离场时间门槛
	 * */
	private Integer systemLeaveTime;
	/**
	 * 入场时间门槛
	 * */
	private Integer systemEnterTime;
	/**
	 * 入场须知
	 * */
	private String description;
	/**
	 *  定价类型  1:强制定价  2：参考定价 3：默认定价
	 **/
	private Integer priceType;
	
	@Id
    @Column(name="carparkid")
	public Long getCarparkid() {
		return carparkid;
	}
	public void setCarparkid(Long carparkid) {
		this.carparkid = carparkid;
	}
	@Column(name="is_system_price")
	public Integer getIsSystemPrice() {
		return isSystemPrice;
	}
	public void setIsSystemPrice(Integer isSystemPrice) {
		this.isSystemPrice = isSystemPrice;
	}
	@Column(name="before_mins_temporary")
	public Integer getBeforeMinsTemporary() {
		return beforeMinsTemporary;
	}
	public void setBeforeMinsTemporary(Integer beforeMinsTemporary) {
		this.beforeMinsTemporary = beforeMinsTemporary;
	}
	@Column(name="before_price_temporary")
	public Double getBeforePriceTemporary() {
		return beforePriceTemporary;
	}
	public void setBeforePriceTemporary(Double beforePriceTemporary) {
		this.beforePriceTemporary = beforePriceTemporary;
	}
	@Column(name="before_mins_reserve")
	public Integer getBeforeMinsReserve() {
		return beforeMinsReserve;
	}
	public void setBeforeMinsReserve(Integer beforeMinsReserve) {
		this.beforeMinsReserve = beforeMinsReserve;
	}
	@Column(name="before_price_reserve")
	public Double getBeforePriceReserve() {
		return beforePriceReserve;
	}
	public void setBeforePriceReserve(Double beforePriceReserve) {
		this.beforePriceReserve = beforePriceReserve;
	}
	@Column(name="after_mins_temporary")
	public Integer getAfterMinsTemporary() {
		return afterMinsTemporary;
	}
	public void setAfterMinsTemporary(Integer afterMinsTemporary) {
		this.afterMinsTemporary = afterMinsTemporary;
	}
	@Column(name="after_price_temporary")
	public Double getAfterPriceTemporary() {
		return afterPriceTemporary;
	}
	public void setAfterPriceTemporary(Double afterPriceTemporary) {
		this.afterPriceTemporary = afterPriceTemporary;
	}
	@Column(name="after_mins_reserve")
	public Integer getAfterMinsReserve() {
		return afterMinsReserve;
	}
	public void setAfterMinsReserve(Integer afterMinsReserve) {
		this.afterMinsReserve = afterMinsReserve;
	}
	@Column(name="after_price_reserve")
	public Double getAfterPriceReserve() {
		return afterPriceReserve;
	}
	public void setAfterPriceReserve(Double afterPriceReserve) {
		this.afterPriceReserve = afterPriceReserve;
	}
	@Column(name="violate_price_temporary")
	public Double getViolatePriceTemporary() {
		return violatePriceTemporary;
	}
	public void setViolatePriceTemporary(Double violatePriceTemporary) {
		this.violatePriceTemporary = violatePriceTemporary;
	}
	@Column(name="violate_price_reserve")
	public Double getViolatePriceReserve() {
		return violatePriceReserve;
	}
	public void setViolatePriceReserve(Double violatePriceReserve) {
		this.violatePriceReserve = violatePriceReserve;
	}
	@Column(name="share_start_time")
	public Date getShareStartTime() {
		return shareStartTime;
	}
	public void setShareStartTime(Date shareStartTime) {
		this.shareStartTime = shareStartTime;
	}
	@Column(name="share_end_time")
	public Date getShareEndTime() {
		return shareEndTime;
	}
	public void setShareEndTime(Date shareEndTime) {
		this.shareEndTime = shareEndTime;
	}
	@Column(name="share_repeat_date")
	public String getShareRepeatDate() {
		return shareRepeatDate;
	}
	public void setShareRepeatDate(String shareRepeatDate) {
		this.shareRepeatDate = shareRepeatDate;
	}
	@Column(name="is_contain_exit_price")
	public Integer getIsContainExitPrice() {
		return isContainExitPrice;
	}
	public void setIsContainExitPrice(Integer isContainExitPrice) {
		this.isContainExitPrice = isContainExitPrice;
	}
	@Column(name="system_leave_time")
	public Integer getSystemLeaveTime() {
		return systemLeaveTime;
	}
	public void setSystemLeaveTime(Integer systemLeaveTime) {
		this.systemLeaveTime = systemLeaveTime;
	}
	@Column(name="description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Column(name="price_type")
	public Integer getPriceType() {
		return priceType;
	}
	public void setPriceType(Integer priceType) {
		this.priceType = priceType;
	}
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "carparkid", unique = true, nullable = false, insertable = false, updatable = false)
	public Carpark getCarpark() {
		return carpark;
	}
	public void setCarpark(Carpark carpark) {
		this.carpark = carpark;
	}
	public void setSystemEnterTime(Integer systemEnterTime) {
		this.systemEnterTime = systemEnterTime;
	}
	@Column(name="system_enter_time")
	public Integer getSystemEnterTime() {
		return systemEnterTime;
	}
	@Override
	public String toString() {
		return "carparkSystemPrice [afterMinsReserve=" + afterMinsReserve
				+ ", afterMinsTemporary=" + afterMinsTemporary
				+ ", afterPriceReserve=" + afterPriceReserve
				+ ", afterPriceTemporary=" + afterPriceTemporary
				+ ", beforeMinsReserve=" + beforeMinsReserve
				+ ", beforeMinsTemporary=" + beforeMinsTemporary
				+ ", beforePriceReserve=" + beforePriceReserve
				+ ", beforePriceTemporary=" + beforePriceTemporary
				+ ", carparkid=" + carparkid + ", description=" + description
				+ ", isContainExitPrice=" + isContainExitPrice
				+ ", isSystemPrice=" + isSystemPrice + ", priceType="
				+ priceType + ", shareEndTime=" + shareEndTime
				+ ", shareRepeatDate=" + shareRepeatDate + ", shareStartTime="
				+ shareStartTime + ", systemLeaveTime=" + systemLeaveTime
				+ ", violatePriceReserve=" + violatePriceReserve
				+ ", violatePriceTemporary=" + violatePriceTemporary + "]";
	}
	
	@Transient
	public String getStrShareStartTime() {
		if(this.shareStartTime!=null){
			SimpleDateFormat time = new SimpleDateFormat("HH:mm");
			return time.format(this.shareStartTime);
		}else{
			return "";			
		}
	}
	@Transient
	public String getStrShareEndTime() {
		if(this.shareEndTime!=null){
			SimpleDateFormat time = new SimpleDateFormat("HH:mm");
			return time.format(this.shareEndTime);
		}else{
			return "";			
		}
	}

	
}

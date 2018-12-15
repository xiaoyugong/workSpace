package com.parkbobo.manager.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "lq_system_statistics_view")
public class SystemStatisticsView implements Serializable{
	/**
	 * 数据统计
	 */
	private static final long serialVersionUID = -5419566041636246580L;

	/**
	 * 订单ID
	 */
	private Long berthorderid;
	
	/**
	 * 停车场ID
	 */
    private Long carparkid;
    /**
     * 停车场名称
     * */
    private String carparkname;
    /**
	 * 提交订单时间
	 */
    private Long posttime;
    /**
	 * 本次停车总时长
	 */
	private Long stopTotalMillisecond;
	/**
	 * 本次停车总付费
	 */
	private Long stopTotalMoney;
	/**
	 * 本次停车超期费
	 */
	private Long stopOvertimeMoney;
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
	@Id
	@Column(name = "berthorderid", unique = true, nullable = false)
	public Long getBerthorderid() {
		return berthorderid;
	}
	public void setBerthorderid(Long berthorderid) {
		this.berthorderid = berthorderid;
	}
	@Column(name = "owner_revenue")
	public Long getOwnerRevenue() {
		return ownerRevenue;
	}
	public void setOwnerRevenue(Long ownerRevenue) {
		this.ownerRevenue = ownerRevenue;
	}
	@Column(name = "property_revenue")
	public Long getPropertyRevenue() {
		return propertyRevenue;
	}
	public void setPropertyRevenue(Long propertyRevenue) {
		this.propertyRevenue = propertyRevenue;
	}
	@Column(name = "company_revenue")
	public Long getCompanyRevenue() {
		return companyRevenue;
	}
	public void setCompanyRevenue(Long companyRevenue) {
		this.companyRevenue = companyRevenue;
	}	
	@Column(name = "carparkid")
	public Long getCarparkid() {
		return carparkid;
	}
	public void setCarparkid(Long carparkid) {
		this.carparkid = carparkid;
	}
	@Column(name = "carparkname")
	public String getCarparkname() {
		return carparkname;
	}
	
	public void setCarparkname(String carparkname) {
		this.carparkname = carparkname;
	}
	@Column(name = "posttime")
	public Long getPosttime() {
		return posttime;
	}
	public void setPosttime(Long posttime) {
		this.posttime = posttime;
	}
	@Column(name = "stop_total_millisecond")
	public Long getStopTotalMillisecond() {
		return stopTotalMillisecond;
	}
	public void setStopTotalMillisecond(Long stopTotalMillisecond) {
		this.stopTotalMillisecond = stopTotalMillisecond;
	}
	@Column(name = "stop_total_money")
	public Long getStopTotalMoney() {
		return stopTotalMoney;
	}
	public void setStopTotalMoney(Long stopTotalMoney) {
		this.stopTotalMoney = stopTotalMoney;
	}
	@Column(name = "stop_overtime_money")
	public Long getStopOvertimeMoney() {
		return stopOvertimeMoney;
	}
	public void setStopOvertimeMoney(Long stopOvertimeMoney) {
		this.stopOvertimeMoney = stopOvertimeMoney;
	}
	

}

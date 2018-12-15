package com.parkbobo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="lq_carpark_device_request_log")
@SequenceGenerator(initialValue=0,name="seq",sequenceName="carpark_device_request_seq")
public class CarparkDeviceRequestLog {

	private Long logid;
	private CarparkDevice carparkDevice;
	/**
	 * 操作类型1车主入场.2车主出场3车主绑定
	 */
	private Short optType;
	/**
	 * 详情
	 */
	private String details;
	/**
	 * 
	 */
	private Short isDel;
	private String carNumber;
	private Date logDateTime;
	@Id
	@GeneratedValue(generator="seq",strategy=GenerationType.SEQUENCE)
	public Long getLogid() {
		return logid;
	}
	public void setLogid(Long logid) {
		this.logid = logid;
	}
	@ManyToOne
	@JoinColumn(name="deviceid")
	public CarparkDevice getCarparkDevice() {
		return carparkDevice;
	}
	public void setCarparkDevice(CarparkDevice carparkDevice) {
		this.carparkDevice = carparkDevice;
	}
	@Column(name="opt_type")
	public Short getOptType() {
		return optType;
	}
	public void setOptType(Short optType) {
		this.optType = optType;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	@Column(name="isdel")
	public Short getIsDel() {
		return isDel;
	}
	public void setIsDel(Short isDel) {
		this.isDel = isDel;
	}
	@Column(name="car_number")
	public String getCarNumber() {
		return carNumber;
	}
	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="log_datetime")
	public Date getLogDateTime() {
		return logDateTime;
	}
	public void setLogDateTime(Date logDateTime) {
		this.logDateTime = logDateTime;
	}
	
	
}

package com.parkbobo.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="lq_carpark_device")
@SequenceGenerator(initialValue=0,sequenceName="lq_carpark_device_seq",name="seq")
public class CarparkDevice implements Serializable{

	private Long deviceid;
	/**
	 * 
	 */
	private Carpark carpark;
	/**
	 * 设备编号
	 */
	private String deviceNumber;
	/**
	 * 1:出口 2：入口3：出入口
	 */
	private Short  entrance;
	/**
	 * 是否禁用
	 */
	private Short isStop;
	/**
	 * 是否删除1
	 */
	private Short isDel;
	/**
	 * 
	 */
	private String memo;
	/**
	 * 登陆密码
	 */
	private String password;
	private Set<CarparkDeviceRequestLog> carparkDeviceRequestLogs=new HashSet<CarparkDeviceRequestLog>();
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="seq")
	public Long getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(Long deviceid) {
		this.deviceid = deviceid;
	}
	@ManyToOne
	@JoinColumn(name="carparkid")
	public Carpark getCarpark() {
		return carpark;
	}
	public void setCarpark(Carpark carpark) {
		this.carpark = carpark;
	}
	@Column(name="device_number")
	public String getDeviceNumber() {
		return deviceNumber;
	}
	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}
	public Short getEntrance() {
		return entrance;
	}
	public void setEntrance(Short entrance) {
		this.entrance = entrance;
	}
	@Column(name="isstop")
	public Short getIsStop() {
		return isStop;
	}
	public void setIsStop(Short isStop) {
		this.isStop = isStop;
	}
	@Column(name="isdel")
	public Short getIsDel() {
		return isDel;
	}
	public void setIsDel(Short isDel) {
		this.isDel = isDel;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@OneToMany(mappedBy="carparkDevice",cascade=CascadeType.ALL)
	public Set<CarparkDeviceRequestLog> getCarparkDeviceRequestLogs() {
		return carparkDeviceRequestLogs;
	}
	public void setCarparkDeviceRequestLogs(
			Set<CarparkDeviceRequestLog> carparkDeviceRequestLogs) {
		this.carparkDeviceRequestLogs = carparkDeviceRequestLogs;
	}
	
	
	
	
}

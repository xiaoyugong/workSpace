package com.parkbobo.groundlock.model;

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

/**
 * 地锁服务器IP列表
 * @author Administrator
 *
 */
@Entity
@Table(name="lq_service_ips")
@SequenceGenerator(name = "generator", sequenceName = "lq_service_ips_id_seq", allocationSize = 1)
public class ServiceIps implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6886260695686395033L;
	/**
	 * 主键ID
	 */
	private Long id;
	/**
	 * 地锁ID
	 */
	private Groundlock groundlock;
	/**
	 * 服务器IP地址
	 */
	private String ipaddress;
	/**
	 * 排序
	 */
	private Long orderid;
	private String memo;
	public ServiceIps(){}
	public ServiceIps(Long id , Groundlock groundlock, String ipaddress, Long orderid, String memo)
	{
		this.id = id;
		this.groundlock = groundlock;
		this.ipaddress = ipaddress;
		this.orderid = orderid;
		this.memo = memo;
	}
	@Id 
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
    @Column(name="id", unique=true, nullable=false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="groundlockid")
	public Groundlock getGroundlock() {
		return groundlock;
	}
	public void setGroundlock(Groundlock groundlock) {
		this.groundlock = groundlock;
	}
	@Column(name="orderid")
	public Long getOrderid() {
		return orderid;
	}
	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}
	@Column(name="memo")
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	@Column(name="ipaddress")
	public String getIpaddress() {
		return ipaddress;
	}
	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}
}

package com.parkbobo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
/**
 * APP渠道
 * @author LH
 *
 */
@Entity
@Table(name="lq_app_channel")
@SequenceGenerator(name = "generator", sequenceName = "lq_app_channel_channelid_seq", allocationSize = 1)
public class AppChannel implements Serializable {
	private static final long serialVersionUID = -965065357101851133L;
	/**
	 * 渠道ID
	 */
	private Long channelid;
	/**
	 * 渠道名称
	 */
	private String name;
	/**
	 * 排序
	 */
	private Long orderid;
	/**
	 * 备注
	 */
	private String memo;
	
	public AppChannel()
	{
		
	}
	public AppChannel(Long channelid)
	{
		this.channelid = channelid;
	}
	public AppChannel(Long channelid, String name, Long orderid, String memo)
	{
		this.channelid = channelid;
		this.name = name;
		this.orderid = orderid;
		this.memo = memo;
	}
	
	@Id 
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
    @Column(name="channelid", unique=true, nullable=false)
	public Long getChannelid() {
		return channelid;
	}
	public void setChannelid(Long channelid) {
		this.channelid = channelid;
	}
	
	@Column(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
}

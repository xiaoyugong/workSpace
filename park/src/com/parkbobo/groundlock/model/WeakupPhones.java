package com.parkbobo.groundlock.model;

import java.io.Serializable;

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
 * 可唤醒地锁电话
 * @author Administrator
 *
 */
@Entity
@Table(name="lq_weakup_phones")
@SequenceGenerator(name = "generator", sequenceName = "lq_weakup_phones_phoneid_seq", allocationSize = 1)
public class WeakupPhones implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6739664784778022068L;
	/**
	 * 电话ID
	 */
	private Long phoneid;
	/**
	 * 地锁ID
	 */
	private Groundlock groundlock;
	/**
	 * 电话号码
	 */
	private String telephone;
	/**
	 * 提交时间
	 */
	private Long posttime;
	/**
	 * 备注
	 */
	private String memo;
	public WeakupPhones(){}
	public WeakupPhones(Long phoneid, Groundlock groundlock, String telephone, Long posttime, String memo)
	{
		this.phoneid = phoneid;
		this.groundlock = groundlock;
		this.telephone = telephone;
		this.posttime = posttime;
		this.memo = memo;
	}
	@Id 
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
    @Column(name="phoneid", unique=true, nullable=false)
	public Long getPhoneid() {
		return phoneid;
	}
	public void setPhoneid(Long phoneid) {
		this.phoneid = phoneid;
	}
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="groundlockid")
	public Groundlock getGroundlock() {
		return groundlock;
	}
	public void setGroundlock(Groundlock groundlock) {
		this.groundlock = groundlock;
	}
	@Column(name="telephone")
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	@Column(name="posttime")
	public Long getPosttime() {
		return posttime;
	}
	public void setPosttime(Long posttime) {
		this.posttime = posttime;
	}
	@Column(name="memo")
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
}

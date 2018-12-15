package com.parkbobo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 邀请码
 * @author LH
 *
 */
@Entity
@Table(name="lq_invitecode_detail")
public class InvitecodeDetail implements Serializable {
	 /**
     * 用户ID
     */
	private String userid;
	/**
	 * 
	 */
	private static final long serialVersionUID = -5388735839286847195L;
	/**
	 * 邀请码
	 */
	private String invitecode;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 备注
	 */
	private String memo;
	
	private Users users;
	public InvitecodeDetail()
	{
		
	}
	public InvitecodeDetail(String invitecode)
	{
		this.invitecode = invitecode;
	}
	public InvitecodeDetail(String invitecode, String description, String memo)
	{
		this.invitecode = invitecode;
		this.description = description;
		this.memo = memo;
	}
	
    @Column(name="invitecode", unique=true, nullable=false, length=100)
	public String getInvitecode() {
		return invitecode;
	}
	public void setInvitecode(String invitecode) {
		this.invitecode = invitecode;
	}
	
	@Column(name="description", length=255)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name="memo", length=255)
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	
	@OneToOne
	@JoinColumn(name = "userid")
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
	}
	
	@Id
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}

}

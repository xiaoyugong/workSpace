package com.parkbobo.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * 车位认证
 * @author LH
 *
 */
@Entity
@Table(name="lq_driver_authentication")
public class DriverAuthentication  implements java.io.Serializable {

	private static final long serialVersionUID = -8185266249236410705L;
    /**
     * 用户ID
     */
	private String userid;
	/**
     *  
     */
	private Users users;
	/**
     * 附件类型，1：行驶证；2：驾驶证 
     */
	private Short identityType;
	/**
     *  车主附件
     */
	private String attached;
	/**
     *  审核状态，1：通过；2：未通过
     */
	private Short status;
	/**
     *  提交时间
     */
	private Long posttime;
	
    private String memo;
    private String formatPosttime;
    public DriverAuthentication() {
    }

    public DriverAuthentication(String userid, Users users) {
    	this.userid = userid;
        this.users = users;
    }
    
    public DriverAuthentication(String userid, Short identityType, String attached, Short status, Long posttime, String memo) {
        this.userid = userid;
        this.identityType = identityType;
        this.attached = attached;
        this.status = status;
        this.posttime = posttime;
        this.memo = memo;
    }
    
    @Id
    @Column(name="userid", length=100)
    public String getUserid() {
        return this.userid;
    }
    
    public void setUserid(String userid) {
        this.userid = userid;
    }
    @OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid", unique = true, nullable = false, insertable = false, updatable = false)
    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }

    @Column(name="identity_type")

    public Short getIdentityType() {
        return this.identityType;
    }
    
    public void setIdentityType(Short identityType) {
        this.identityType = identityType;
    }

    @Column(name="attached")

    public String getAttached() {
        return this.attached;
    }
    
    public void setAttached(String attached) {
        this.attached = attached;
    }

    @Column(name="status")

    public Short getStatus() {
        return this.status;
    }
    
    public void setStatus(Short status) {
        this.status = status;
    }

    @Column(name="posttime")

    public Long getPosttime() {
        return this.posttime;
    }
    
    public void setPosttime(Long posttime) {
        this.posttime = posttime;
    }

    @Column(name="memo")

    public String getMemo() {
        return this.memo;
    }
    
    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Transient
	public String getFormatPosttime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(posttime != null)
		{
			formatPosttime =  sdf.format(new Date(posttime));
		}
		else
		{
			formatPosttime = "";
		}
		return formatPosttime;
	}
	public void setFormatPosttime(String formatPosttime) {
		this.formatPosttime = formatPosttime;
	}

	@Override
	public String toString() {
		return "DriverAuthentication [attached=" + attached
				+ ", formatPosttime=" + formatPosttime + ", identityType="
				+ identityType + ", memo=" + memo + ", posttime=" + posttime
				+ ", status=" + status + ", userid=" + userid + ", users="
				+ users + "]";
	}
	
}
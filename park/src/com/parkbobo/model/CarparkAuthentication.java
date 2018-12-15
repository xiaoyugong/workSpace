package com.parkbobo.model;

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
 * 停车场或车位认证
 * @author LH
 *
 */
@Entity
@Table(name="lq_carpark_authentication")
@SequenceGenerator(name = "generator", sequenceName = "lq_carpark_authentication_authenticationid_seq", allocationSize = 1)
public class CarparkAuthentication  implements java.io.Serializable {

     
	private static final long serialVersionUID = -4801269600532700850L;
	/**
	 * 认证申请ID
	 */
	private Long authenticationid;
	/**
	 * 用户ID
	 */
	private Users users;
	/**
	 * 停车场ID
	 */
	private Long carparkid;
	/**
	 * 停车场名称
	 */
	private String carparkname;
	/**
	 * 分享ID
	 */
	private Long shareid;
	/**
	 * 车位ID
	 */
	private Long berthid;
	/**
	 * 车位号
	 */
	private String berthnum;
	/**
	 * 认证类型，0：停车场；1：微地图车位；2：无微地图车位
	 */
	private Short authenticationtype;
	/**
	 * 提交认证时间
	 */
	private Long posttime;
	/**
	 * 认证材料
	 */
	private String attached;
	/**
	 * 审核状态，0：待审；1：通过；2：未通过
	 */
	private Short status;
	/**
	 * 
	 */
	private String content;
	private String memo;


    public CarparkAuthentication() {
    }

    public CarparkAuthentication(Long authenticationid) {
        this.authenticationid = authenticationid;
    }
    
    public CarparkAuthentication(Long authenticationid, Users users, Long carparkid, String carparkname, Long shareid, Long berthid, String berthnum, Short authenticationtype, Long posttime, String attached, Short status, String content, String memo) {
        this.authenticationid = authenticationid;
        this.users = users;
        this.carparkid = carparkid;
        this.carparkname = carparkname;
        this.berthid = berthid;
        this.shareid = shareid;
        this.berthnum = berthnum;
        this.authenticationtype = authenticationtype;
        this.posttime = posttime;
        this.attached = attached;
        this.status = status;
        this.content = content;
        this.memo = memo;
    }

   
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
    @Column(name="authenticationid", unique=true, nullable=false)

    public Long getAuthenticationid() {
        return this.authenticationid;
    }
    
    public void setAuthenticationid(Long authenticationid) {
        this.authenticationid = authenticationid;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="userid")

    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }
    
    @Column(name="carparkid")

    public Long getCarparkid() {
        return this.carparkid;
    }
    
    public void setCarparkid(Long carparkid) {
        this.carparkid = carparkid;
    }
    
    @Column(name="berthid")

    public Long getBerthid() {
        return this.berthid;
    }
    
    public void setBerthid(Long berthid) {
        this.berthid = berthid;
    }
    
    @Column(name="berthnum", length=100)

    public String getBerthnum() {
        return this.berthnum;
    }
    
    public void setBerthnum(String berthnum) {
        this.berthnum = berthnum;
    }
    
    @Column(name="authenticationtype")

    public Short getAuthenticationtype() {
        return this.authenticationtype;
    }
    
    public void setAuthenticationtype(Short authenticationtype) {
        this.authenticationtype = authenticationtype;
    }
    
    @Column(name="posttime")

    public Long getPosttime() {
        return this.posttime;
    }
    
    public void setPosttime(Long posttime) {
        this.posttime = posttime;
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
    
    @Column(name="content")

    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    @Column(name="memo")

    public String getMemo() {
        return this.memo;
    }
    
    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Column(name="carparkname")
	public String getCarparkname() {
		return carparkname;
	}

	public void setCarparkname(String carparkname) {
		this.carparkname = carparkname;
	}
	@Column(name="shareid")
	public Long getShareid() {
		return shareid;
	}

	public void setShareid(Long shareid) {
		this.shareid = shareid;
	}
   








}
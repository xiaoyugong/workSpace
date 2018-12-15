package com.parkbobo.model;

import java.text.SimpleDateFormat;
import java.util.Date;

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
import javax.persistence.Transient;
/**
 * 违约处理申请
 * @author LH
 *
 */
@Entity
@Table(name="lq_default_apply")
@SequenceGenerator(name = "generator", sequenceName = "lq_default_apply_applyid_seq", allocationSize = 1)
public class DefaultApply implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5731959969365391734L;
	/**
	 * 申请单号
	 */
	private Long applyid;
	/**
	 * 用户ID
	 */
	private Users users;
	/**
	 * 申述类型，0：车位被恶意入驻，1：无法进去停车，2：超期停车；3：其他
	 */
	private Short type;
	/**
	 * 车位分享ID
	 */
	private Long shareid;
	/**
	 * 订单ID
	 */
	private Long berthorderid;
	/**
	 * 申述内容
	 */
	private String content;
	/**
	 * 申述附件
	 */
	private String attached;
	/**
	 * 处理状态，0：待审，1：驳回，2：成功，3：协商中，4：撤销
	 */
	private Short status;
	/**
	 * 提交时间
	 */
	private Long posttime;
	/**
	 * 备注
	 */
	private String memo;
	
	private String formatPosttime;
	
	public DefaultApply()
	{
		
	}
	public DefaultApply(Long applyid, Users users, Short type, Long shareid, Long berthorderid, String content, String attached, Short status, Long posttime ,String memo)
	{
		this.applyid = applyid;
		this.users = users;
		this.type = type;
		this.shareid = shareid;
		this.berthorderid = berthorderid;
		this.content = content;
		this.attached = attached;
		this.status = status;
		this.posttime = posttime;
		this.memo = memo;
	}
	
	@Id 
    @Column(name="applyid", unique=true, nullable=false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
	public Long getApplyid() {
		return applyid;
	}
	public void setApplyid(Long applyid) {
		this.applyid = applyid;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="userid")
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
	}
	
	@Column(name="type")
	public Short getType() {
		return type;
	}
	public void setType(Short type) {
		this.type = type;
	}
	
	@Column(name="shareid")
	public Long getShareid() {
		return shareid;
	}
	public void setShareid(Long shareid) {
		this.shareid = shareid;
	}
	
	@Column(name="berthorderid")
	public Long getBerthorderid() {
		return berthorderid;
	}
	public void setBerthorderid(Long berthorderid) {
		this.berthorderid = berthorderid;
	}
	
	@Column(name="content")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(name="attached")
	public String getAttached() {
		return attached;
	}
	public void setAttached(String attached) {
		this.attached = attached;
	}
	
	@Column(name="status")
	public Short getStatus() {
		return status;
	}
	public void setStatus(Short status) {
		this.status = status;
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
	@Transient
	public String getFormatPosttime(){
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
}

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
 * 系统通知
 * @author LH
 *
 */
@Entity
@Table(name="lq_notify")
@SequenceGenerator(name = "generator", sequenceName = "lq_notify_notifyid_seq", allocationSize = 1)
public class Notify  implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6754587747943264835L;
	/**
	 * 通知ID
	 */
	private Long notifyid;
	/**
	 * 接收用户,为空为所有用户
	 */
	private Users users;
	/**
	 * 通知标题
	 */
	private String title;
	/**
	 * 通知内容
	 */
	private String content;
	/**
	 * 通知类型，0：系统通知；1：订单通知
	 */
	private Integer type;
	/**
	 * 通知时间
	 */
	private Long posttime;
	/**
	 * 是否删除，0：否；1：是
	 */
	private Integer isDel;
	/**
	 * 是否已读，0：否；1：是
	 */
	private Integer isRead;
	/**
	 * 备注
	 */
	private String memo;
	/**
	 * 格式化日期 yyyy-MM-dd HH:mm:ss
	 */
	private String  formatPosttime;
	public Notify()
	{
	}
	public Notify(Long notifyid)
	{
		this.notifyid = notifyid;
	}
	public Notify(Long notifyid, Users users, String title, String content, Integer type, Long posttime, Integer isDel, Integer isRead, String memo)
	{
		this.notifyid = notifyid;
		this.users = users;
		this.content = content;
		this.title = title;
		this.type = type;
		this.posttime = posttime;
		this.isDel = isDel;
		this.isRead = isRead;
		this.memo = memo;
	}
	
	public Notify(Users users, String title, String content, Integer type, Long posttime, Integer isDel, Integer isRead, String memo)
	{
		this.users = users;
		this.content = content;
		this.title = title;
		this.type = type;
		this.posttime = posttime;
		this.isDel = isDel;
		this.isRead = isRead;
		this.memo = memo;
	}
	@Id 
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
    @Column(name="notifyid", unique=true, nullable=false)
	public Long getNotifyid() {
		return notifyid;
	}
	public void setNotifyid(Long notifyid) {
		this.notifyid = notifyid;
	}
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="userid")
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
	}
	@Column(name="content")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column(name="type")
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	@Column(name="posttime")
	public Long getPosttime() {
		return posttime;
	}
	public void setPosttime(Long posttime) {
		this.posttime = posttime;
	}
	@Column(name="is_del")
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	@Column(name="memo")
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	@Column(name="title")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Column(name="is_read")
	public Integer getIsRead() {
		return isRead;
	}
	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
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
	
}

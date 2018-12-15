package com.parkbobo.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
/**
 * APP活动
 * @author LH
 *
 */
@Entity
@Table(name="lq_app_activity") 
@SequenceGenerator(name = "generator", sequenceName = "lq_app_activity_activityid_seq", allocationSize = 1)
public class AppActivity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3552597480439061309L;
	/**
	 * 活动ID
	 */
	private Long activityid;
	/**
	 * 活动标题
	 */
	private String title;
	/**
	 * 活动图片
	 */
	private String imgurl;
	/**
	 * 详情
	 */
	private String description;
	/**
	 * 提交时间
	 */
	private Long posttime;
	/**
	 * 是否删除，0：否；1：是
	 */
	private Short isDel;
	/**
	 * 备注
	 */
	private String memo;
	/**
	 * 格式化日期 yyyy-MM-dd HH:mm:ss
	 */
	private String  formatPosttime;
	
	public AppActivity()
	{
		
	}
	public AppActivity(Long activityid)
	{
		this.activityid = activityid;
	}
	public AppActivity(Long activityid, String title, String imgurl, String description, Long posttime, Short isDel, String memo)
	{
		this.activityid = activityid;
		this.title = title;
		this.imgurl = imgurl;
		this.description = description;
		this.posttime = posttime;
		this.isDel = isDel;
		this.memo = memo;
	}
	
	@Id 
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
    @Column(name="activityid", unique=true, nullable=false)
	public Long getActivityid() {
		return activityid;
	}
	public void setActivityid(Long activityid) {
		this.activityid = activityid;
	}
	
	@Column(name="title")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name="imgurl")
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	
	@Column(name="description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name="posttime")
	public Long getPosttime() {
		return posttime;
	}
	public void setPosttime(Long posttime) {
		this.posttime = posttime;
	}
	
	@Column(name="is_del")
	public Short getIsDel() {
		return isDel;
	}
	public void setIsDel(Short isDel) {
		this.isDel = isDel;
	}
	
	@Column(name="memo")
	public String getMemo() {
		return memo;
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
}

package com.parkbobo.model;
// default package

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


/**
 * 意见反馈
 * @author LH
 *
 */
@Entity
@Table(name="lq_feedback")
@SequenceGenerator(name = "generator", sequenceName = "lq_feedback_feedbackid_seq", allocationSize = 1)

public class Feedback  implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -5698299887281562733L;
	/**
	 * 主键ID
	 */
	private Long feedbackid;
	/**
	 * 用户ID
	 */
    private Users users;
    /**
     * 联系方式
     */
    private String contact;
    /**
     * 内容
     */
    private String content;
    /**
     * 用户手机型号或系统信息
     */
    private String systemInfo;
    /**
     * 提交时间
     */
    private Date posttime;
    /**
     * 备注
     */
    private String memo;



    public Feedback() {
    }

    public Feedback(Long feedbackid) {
        this.feedbackid = feedbackid;
    }
    
    public Feedback(Long feedbackid, Users users, String contact, String content, String systemInfo, Date posttime, String memo) {
        this.feedbackid = feedbackid;
        this.users = users;
        this.contact = contact;
        this.content = content;
        this.systemInfo = systemInfo;
        this.posttime = posttime;
        this.memo = memo;
    }

   
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
    @Column(name="feedbackid", unique=true, nullable=false)

    public Long getFeedbackid() {
        return this.feedbackid;
    }
    
    public void setFeedbackid(Long feedbackid) {
        this.feedbackid = feedbackid;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="userid")

    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }
    
    @Column(name="contact")

    public String getContact() {
        return this.contact;
    }
    
    public void setContact(String contact) {
        this.contact = contact;
    }
    
    @Column(name="content")

    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    @Column(name="system_info")

    public String getSystemInfo() {
        return this.systemInfo;
    }
    
    public void setSystemInfo(String systemInfo) {
        this.systemInfo = systemInfo;
    }
    
    @Column(name="posttime")

    public Date getPosttime() {
        return this.posttime;
    }
    
    public void setPosttime(Date posttime) {
        this.posttime = posttime;
    }
    
    @Column(name="memo")

    public String getMemo() {
        return this.memo;
    }
    
    public void setMemo(String memo) {
        this.memo = memo;
    }
   








}
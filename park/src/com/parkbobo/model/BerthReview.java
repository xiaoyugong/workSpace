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
 * 车位评论
 * @author LH
 *
 */
@Entity
@Table(name="lq_berth_review")
@SequenceGenerator(name = "generator", sequenceName = "lq_berth_review_reviewid_seq", allocationSize = 1)
public class BerthReview  implements java.io.Serializable {
	private static final long serialVersionUID = -2412456771474765246L;
	/**
	 *  评论ID
	 */
	private Long reviewid;
	/**
	 * 车位订单ID
	 */
    private BerthOrder berthOrder;
    /**
     * 分享ID
     */
    private BerthShare berthShare;
    /**
     * 评论用户ID
     */
    private String userid;
    /**
     * 评论用户昵称
     */
    private String nickname;
    /**
	 * 用户头像
	 */
	private String userhead;
    /**
     * 评价类型，0：好评，1：中评；2：差评
     */
    private Short rates;
    /**
     * 评价内容
     */
    private String content;
    /**
     * 评价日期
     */
    private Long reviewTime;
    private String memo;


    public BerthReview() {
    }

    public BerthReview(Long reviewid) {
        this.reviewid = reviewid;
    }
    
    public BerthReview(Long reviewid, BerthOrder berthOrder, BerthShare berthShare, String userid, String nickname, String userhead, Short rates, String content, Long reviewTime, String memo) {
        this.reviewid = reviewid;
        this.berthOrder = berthOrder;
        this.berthShare = berthShare;
        this.userid = userid;
        this.nickname = nickname;
        this.rates = rates;
        this.content = content;
        this.userhead = userhead;
        this.reviewTime = reviewTime;
        this.memo = memo;
    }

   
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
    @Column(name="reviewid", unique=true, nullable=false)

    public Long getReviewid() {
        return this.reviewid;
    }
    
    public void setReviewid(Long reviewid) {
        this.reviewid = reviewid;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="berthorderid")

    public BerthOrder getBerthOrder() {
        return this.berthOrder;
    }
    
    public void setBerthOrder(BerthOrder berthOrder) {
        this.berthOrder = berthOrder;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="shareid")

    public BerthShare getBerthShare() {
        return this.berthShare;
    }
    
    public void setBerthShare(BerthShare berthShare) {
        this.berthShare = berthShare;
    }
    
    @Column(name="userid", length=100)

    public String getUserid() {
        return this.userid;
    }
    
    public void setUserid(String userid) {
        this.userid = userid;
    }
    
    @Column(name="nickname", length=100)

    public String getNickname() {
        return this.nickname;
    }
    
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    
    @Column(name="rates")

    public Short getRates() {
        return this.rates;
    }
    
    public void setRates(Short rates) {
        this.rates = rates;
    }
    
    @Column(name="content")

    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    @Column(name="review_time")

    public Long getReviewTime() {
        return this.reviewTime;
    }
    
    public void setReviewTime(Long reviewTime) {
        this.reviewTime = reviewTime;
    }
    
    @Column(name="memo")

    public String getMemo() {
        return this.memo;
    }
    
    public void setMemo(String memo) {
        this.memo = memo;
    }
    @Column(name="userhead")
	public String getUserhead() {
		return userhead;
	}

	public void setUserhead(String userhead) {
		this.userhead = userhead;
	}
   







}
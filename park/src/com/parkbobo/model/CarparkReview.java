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
 * 停车场评论
 * @author LH
 *
 */
@Entity
@Table(name="lq_carpark_review")
@SequenceGenerator(name = "generator", sequenceName = "lq_carpark_review_reviewid_seq", allocationSize = 1)
public class CarparkReview  implements java.io.Serializable {

     /**
	 * 
	 */
	private static final long serialVersionUID = 3906765108616963559L;
	/**
	 * 评论ID
	 */
	private Long reviewid;
	/**
	 * 停车场ID
	 */
	private Carpark carpark;
	/**
	 * 用户ID
	 */
	private String userid;
	/**
	 * 用户昵称
	 */
	private String nickname;
	/**
	 * 用户头像
	 */
	private String userhead;
	/**
	 * 评论内容
	 */
	private String content;
	/**
	 * 评论时间
	 */
	private Long reviewTime;
	/**
	 * 点赞个数
	 */
	private Long goodNum;
	/**
	 * 备注
	 */
	private String memo;


    public CarparkReview() {
    }

    public CarparkReview(Long reviewid) {
        this.reviewid = reviewid;
    }
    
    public CarparkReview(Long reviewid, Carpark carpark, String userid, String nickname, String userhead, String content, Long reviewTime, Long goodNum, String memo) {
        this.reviewid = reviewid;
        this.carpark = carpark;
        this.userid = userid;
        this.nickname = nickname;
        this.content = content;
        this.reviewTime = reviewTime;
        this.userhead = userhead;
        this.goodNum = goodNum;
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
        @JoinColumn(name="carparkid")

    public Carpark getCarpark() {
        return this.carpark;
    }
    
    public void setCarpark(Carpark carpark) {
        this.carpark = carpark;
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
    
    @Column(name="good_num")

    public Long getGoodNum() {
        return this.goodNum;
    }
    
    public void setGoodNum(Long goodNum) {
        this.goodNum = goodNum;
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
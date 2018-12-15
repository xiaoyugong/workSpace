package com.parkbobo.model;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * 用户参数配置
 * @author LH
 *
 */
@Entity
@Table(name="lq_users_configure")
public class UsersConfigure  implements java.io.Serializable {



	private static final long serialVersionUID = 5600220769562047753L;
	/**
	 * 用户ID
	 */
	private String userid;
    
	private Users users;
	/**
     *  是否接收消息，0：否；1：是
     */
	private Integer isReceive;
	/**
     *  是否开启声音提示，0：否；1：是
     */
	private Integer isSound;
	/**
     *  是否开启震动提示，0：否；1：是
     */
	private Integer isVibrated;
	/**
     *  是否全天提醒，0：否；1：是
     */
	private Integer isAllTime;
	/**
     *  起始提醒时间
     */
	private String startTime;
	/**
     *  结束提醒时间
     */
	private String endTime;
	/**
     *  
     */
	private String memo;



    public UsersConfigure() {
    }

    public UsersConfigure(String userid, Users users) {
        this.userid = userid;
        this.users = users;
    }
    
    public UsersConfigure(String userid, Users users, Integer isReceive, Integer isSound, Integer isVibrated, Integer isAllTime, String startTime, String endTime, String memo) {
        this.userid = userid;
        this.users = users;
        this.isReceive = isReceive;
        this.isSound = isSound;
        this.isVibrated = isVibrated;
        this.isAllTime = isAllTime;
        this.startTime = startTime;
        this.endTime = endTime;
        this.memo = memo;
    }

   
    @Id 
    
    @Column(name="userid", unique=true, nullable=false, length=100)

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
    
    @Column(name="is_receive")

    public Integer getIsReceive() {
        return this.isReceive;
    }
    
    public void setIsReceive(Integer isReceive) {
        this.isReceive = isReceive;
    }
    
    @Column(name="is_sound")

    public Integer getIsSound() {
        return this.isSound;
    }
    
    public void setIsSound(Integer isSound) {
        this.isSound = isSound;
    }
    
    @Column(name="is_vibrated")

    public Integer getIsVibrated() {
        return this.isVibrated;
    }
    
    public void setIsVibrated(Integer isVibrated) {
        this.isVibrated = isVibrated;
    }
    
    @Column(name="is_all_time")

    public Integer getIsAllTime() {
        return this.isAllTime;
    }
    
    public void setIsAllTime(Integer isAllTime) {
        this.isAllTime = isAllTime;
    }
    
    @Column(name="start_time", length=20)

    public String getStartTime() {
        return this.startTime;
    }
    
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    
    @Column(name="end_time", length=20)

    public String getEndTime() {
        return this.endTime;
    }
    
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    
    @Column(name="memo")

    public String getMemo() {
        return this.memo;
    }
    
    public void setMemo(String memo) {
        this.memo = memo;
    }
   








}
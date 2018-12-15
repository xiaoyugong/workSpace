package com.parkbobo.model;

import java.io.Serializable;

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
 * 预约停车
 * @author LH
 *
 */
@Entity
@Table(name="lq_order_task")
@SequenceGenerator(name = "generator", sequenceName = "lq_order_task_taskid_seq", allocationSize = 1)
public class OrderTask implements Serializable {
	private static final long serialVersionUID = -6634466419462730056L;
	/**
	 * 预约ID
	 */
	private Long taskid;
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
	 * 车位分享ID
	 */
	private BerthShare berthShare;
	/**
	 * 预约类型 0:面向停车场预约,1:面向车位预约
	 */
	private Short tasktype;
	/**
	 * 起始时间
	 */
	private Long startTime;
	/**
	 * 结束时间
	 */
	private Long endTime;
	/**
	 * 车牌号
	 */
	private String carNumber;
	/**
	 * 手机号
	 */
	private String telephone;
	/**
	 * 预约状态，-1:已取消,0:待预约,1:成功,2:失败
	 */
	private Short taskStatus;
	/**
	 * 预约提交时间
	 */
	private Long posttime;
	/**
	 * 预约结束时间
	 */
	private Long finishtime;
	private String memo;
	public OrderTask(){}
	public OrderTask(Long taskid)
	{
		this.taskid = taskid;
	}
	public OrderTask(Long taskid, Users users, Long carparkid, String carparkname, BerthShare berthShare, Short tasktype, 
			Long startTime, Long endTime, String carNumber, String telephone, Short taskStatus, Long posttime, Long finishtime, String memo)
	{
		this.taskid = taskid;
		this.users = users;
		this.carparkid = carparkid;
		this.carparkname = carparkname;
		this.berthShare = berthShare;
		this.tasktype = tasktype;
		this.startTime = startTime;
		this.endTime = endTime;
		this.carNumber = carNumber;
		this.telephone = telephone;
		this.taskStatus = taskStatus;
		this.posttime = posttime;
		this.finishtime = finishtime;
		this.memo = memo;
	}
	
	@Id 
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
    @Column(name="taskid", unique=true, nullable=false)
	public Long getTaskid() {
		return taskid;
	}
	public void setTaskid(Long taskid) {
		this.taskid = taskid;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="userid")
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
	}
	
	@Column(name="carparkid")
	public Long getCarparkid() {
		return carparkid;
	}
	public void setCarparkid(Long carparkid) {
		this.carparkid = carparkid;
	}
	
	@Column(name="carparkname")
	public String getCarparkname() {
		return carparkname;
	}
	public void setCarparkname(String carparkname) {
		this.carparkname = carparkname;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="shareid")
	public BerthShare getBerthShare() {
		return berthShare;
	}
	public void setBerthShare(BerthShare berthShare) {
		this.berthShare = berthShare;
	}
	
	@Column(name="tasktype")
	public Short getTasktype() {
		return tasktype;
	}
	public void setTasktype(Short tasktype) {
		this.tasktype = tasktype;
	}
	
	@Column(name="start_time")
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	
	@Column(name="end_time")
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	
	@Column(name="car_number")
	public String getCarNumber() {
		return carNumber;
	}
	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	
	@Column(name="telephone")
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	@Column(name="task_status")
	public Short getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(Short taskStatus) {
		this.taskStatus = taskStatus;
	}
	
	@Column(name="posttime")
	public Long getPosttime() {
		return posttime;
	}
	public void setPosttime(Long posttime) {
		this.posttime = posttime;
	}
	
	@Column(name="finishtime")
	public Long getFinishtime() {
		return finishtime;
	}
	public void setFinishtime(Long finishtime) {
		this.finishtime = finishtime;
	}
	
	@Column(name="memo")
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
}

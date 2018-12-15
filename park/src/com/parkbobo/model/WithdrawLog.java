package com.parkbobo.model;
// default package

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
 * 提现记录
 * @author LH
 *
 */
@Entity
@Table(name="lq_withdraw_log")
@SequenceGenerator(name = "generator", sequenceName = "lq_withdraw_log_withdrawid_seq", allocationSize = 1)

public class WithdrawLog  implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1382367649846874191L;
	/**
	 * 记录ID
	 */
	private Long withdrawid;
	/**
	 * 用户ID
	 */
    private Users users;
    /**
     * 金额
     */
    private Long amount;
    /**
     * 开户名
     */
    private String name;
    /**
     * 卡号或帐号
     */
    private String cardNum;
    /**
     * 状态
     * -1：失败
     * 0：待处理（默认）
     * 1：处理中
     * 2：成功
     */
    private Integer status;
    /**
     * 用户备注信息
     */
    private String note;
    /**
     * 回复备注信息
     */
    private String reNote;
    /**
     * 是否删除
     * 0:否（默认）
     * 1：是
     */
    private Integer isDel;
    /**
     * 提交时间
     */
    private Long posttime;
    private String memo;

    private String formatPosttime;

    public WithdrawLog() {
    }

    public WithdrawLog(Long withdrawid) {
        this.withdrawid = withdrawid;
    }
    
    public WithdrawLog(Long withdrawid, Users users, Long amount, String name, String cardNum, Integer status, String note, String reNote, Integer isDel, Long posttime, String memo) {
        this.withdrawid = withdrawid;
        this.users = users;
        this.amount = amount;
        this.name = name;
        this.cardNum = cardNum;
        this.status = status;
        this.note = note;
        this.reNote = reNote;
        this.isDel = isDel;
        this.posttime = posttime;
        this.memo = memo;
    }

   
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
    @Column(name="withdrawid", unique=true, nullable=false)

    public Long getWithdrawid() {
        return this.withdrawid;
    }
    
    public void setWithdrawid(Long withdrawid) {
        this.withdrawid = withdrawid;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="userid")

    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }
    
    @Column(name="amount")

    public Long getAmount() {
        return this.amount;
    }
    
    public void setAmount(Long amount) {
        this.amount = amount;
    }
    
    @Column(name="name")

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="status")

    public Integer getStatus() {
        return this.status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    @Column(name="note")

    public String getNote() {
        return this.note;
    }
    
    public void setNote(String note) {
        this.note = note;
    }
    
    @Column(name="re_note")

    public String getReNote() {
        return this.reNote;
    }
    
    public void setReNote(String reNote) {
        this.reNote = reNote;
    }
    
    @Column(name="is_del")

    public Integer getIsDel() {
        return this.isDel;
    }
    
    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
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
    @Column(name="card_num")
	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
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
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
 * 积分记录明细
 * @author LH
 *
 */
@Entity
@Table(name="lq_user_point")
@SequenceGenerator(name = "generator", sequenceName = "lq_user_point_pointid_seq", allocationSize = 1)
public class UserPoint  implements java.io.Serializable {



     
	private static final long serialVersionUID = 6501825374469440204L;
	/**
	 * 记录ID
	 */
	private Long pointid;
	/**
	 * 用户ID
	 */
	private Users users;
	/**
	 * 类型，0：增加；1：减少
	 */
	private Short type;
	/**
	 * 积分变化值
	 */
	private Integer pointvalue;
	/**
	 * 积分变化事由
	 */
	private String description;
	/**
	 * 记录时间 
	 */
	private Long posttime;
    private String memo;
    private String formatPosttime;


    public UserPoint() {
    }

    public UserPoint(Long pointid) {
        this.pointid = pointid;
    }
    
    public UserPoint(Long pointid, Users users, Short type, Integer pointvalue, String description, Long posttime, String memo) {
        this.pointid = pointid;
        this.users = users;
        this.type = type;
        this.pointvalue = pointvalue;
        this.description = description;
        this.posttime = posttime;
        this.memo = memo;
    }

   
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
    @Column(name="pointid", unique=true, nullable=false)

    public Long getPointid() {
        return this.pointid;
    }
    
    public void setPointid(Long pointid) {
        this.pointid = pointid;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="userid")

    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }
    
    @Column(name="type")

    public Short getType() {
        return this.type;
    }
    
    public void setType(Short type) {
        this.type = type;
    }
    
    @Column(name="pointvalue")

    public Integer getPointvalue() {
        return this.pointvalue;
    }
    
    public void setPointvalue(Integer pointvalue) {
        this.pointvalue = pointvalue;
    }
    
    @Column(name="description")

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
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
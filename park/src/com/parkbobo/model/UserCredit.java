package com.parkbobo.model;
// default package

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
 * 用户信誉记录
 * @author LH
 *
 */
@Entity
@Table(name="lq_user_credit")
@SequenceGenerator(name = "generator", sequenceName = "lq_user_credit_creditid_seq", allocationSize = 1)
public class UserCredit  implements java.io.Serializable {



     
	private static final long serialVersionUID = -3005094843865480513L;
	/**
	 * 记录ID
	 */
	private Long creditid;
	/**
	 * 用户ID
	 */
	private Users users;
	/**
	 * 类型，0：增加；1：减少
	 */
	private Short type;
	/**
	 * 信誉类型，0：车主；1：车位主
	 */
	private Short credittype;
	/**
	 * 信誉变化值
	 */
	private Integer creditvalue;
	/**
	 * 信誉变化事由
	 */
	private String description;
	/**
	 * 记录时间
	 */
	private Long posttime;
    private String memo;



    public UserCredit() {
    }

    public UserCredit(Long creditid) {
        this.creditid = creditid;
    }
    
    public UserCredit(Long creditid, Users users, Short type, Short credittype, Integer creditvalue, String description, Long posttime, String memo) {
        this.creditid = creditid;
        this.users = users;
        this.type = type;
        this.credittype = credittype;
        this.creditvalue = creditvalue;
        this.description = description;
        this.posttime = posttime;
        this.memo = memo;
    }

   
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
    @Column(name="creditid", unique=true, nullable=false)

    public Long getCreditid() {
        return this.creditid;
    }
    
    public void setCreditid(Long creditid) {
        this.creditid = creditid;
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
    
    @Column(name="credittype")

    public Short getCredittype() {
        return this.credittype;
    }
    
    public void setCredittype(Short credittype) {
        this.credittype = credittype;
    }
    
    @Column(name="creditvalue")

    public Integer getCreditvalue() {
        return this.creditvalue;
    }
    
    public void setCreditvalue(Integer creditvalue) {
        this.creditvalue = creditvalue;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((creditid == null) ? 0 : creditid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserCredit other = (UserCredit) obj;
		if (creditid == null) {
			if (other.creditid != null)
				return false;
		} else if (!creditid.equals(other.creditid))
			return false;
		return true;
	}
   








}
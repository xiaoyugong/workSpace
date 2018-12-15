package com.parkbobo.model;
// default package

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * 车场收藏
 * @author LH
 *
 */
@Entity
@Table(name="lq_carpark_favorite")
public class CarparkFavorite  implements java.io.Serializable {
     
	private static final long serialVersionUID = 4839507090815730328L;
	/**
	 * 联合主键：停车场ID+用户ID
	 */
	private CarparkFavoriteId id;
	/**
	 * 停车场ID
	 */
	private Carpark carpark;
	/**
	 * 用户ID
	 */
	private Users users;
	/**
	 * 收藏时间
	 */
	private Long posttime;
	/**
	 * 
	 */
	private String memo;


    public CarparkFavorite() {
    }

    public CarparkFavorite(CarparkFavoriteId id, Carpark carpark, Users users) {
        this.id = id;
        this.carpark = carpark;
        this.users = users;
    }
    public CarparkFavorite(CarparkFavoriteId id, Long posttime)
    {
    	this.id = id;
    	this.posttime = posttime;
    }
    public CarparkFavorite(CarparkFavoriteId id, Carpark carpark, Users users, Long posttime, String memo) {
        this.id = id;
        this.carpark = carpark;
        this.users = users;
        this.posttime = posttime;
        this.memo = memo;
    }

   
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="userid", column=@Column(name="userid", nullable=false, length=100) ), 
        @AttributeOverride(name="carparkid", column=@Column(name="carparkid", nullable=false) ) } )

    public CarparkFavoriteId getId() {
        return this.id;
    }
    
    public void setId(CarparkFavoriteId id) {
        this.id = id;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="carparkid", nullable=false, insertable=false, updatable=false)

    public Carpark getCarpark() {
        return this.carpark;
    }
    
    public void setCarpark(Carpark carpark) {
        this.carpark = carpark;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="userid", nullable=false, insertable=false, updatable=false)

    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
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
   








}
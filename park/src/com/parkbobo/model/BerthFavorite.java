package com.parkbobo.model;

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
 * 车位收藏
 * @author LH
 *
 */
@Entity
@Table(name="lq_berth_favorite")
public class BerthFavorite  implements java.io.Serializable {
	private static final long serialVersionUID = -6427348884579313939L;
	/**
	 * 联合主键：车位ID+用户ID
	 */
	private BerthFavoriteId id;
	/**
	 * 车位ID
	 */
    private CarparkBerthPolygon carparkBerthPolygon;
    /**
     * 用户ID
     */
    private Users users;
    /**
     * 收藏时间
     */
    private Long posttime;
    private String memo;

    public BerthFavorite() {
    }

    public BerthFavorite(BerthFavoriteId id, CarparkBerthPolygon carparkBerthPolygon, Users users) {
        this.id = id;
        this.carparkBerthPolygon = carparkBerthPolygon;
        this.users = users;
    }
    public BerthFavorite(BerthFavoriteId id, Long posttime)
    {
    	this.id = id;
    	this.posttime = posttime;
    }
    public BerthFavorite(BerthFavoriteId id, CarparkBerthPolygon carparkBerthPolygon, Users users, Long posttime, String memo) {
        this.id = id;
        this.carparkBerthPolygon = carparkBerthPolygon;
        this.users = users;
        this.posttime = posttime;
        this.memo = memo;
    }

   
    @EmbeddedId
    @AttributeOverrides( {
        @AttributeOverride(name="gid", column=@Column(name="gid", nullable=false) ), 
        @AttributeOverride(name="userid", column=@Column(name="userid", nullable=false, length=100) ) } )

    public BerthFavoriteId getId() {
        return this.id;
    }
    
    public void setId(BerthFavoriteId id) {
        this.id = id;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="gid", nullable=false, insertable=false, updatable=false)

    public CarparkBerthPolygon getCarparkBerthPolygon() {
        return this.carparkBerthPolygon;
    }
    
    public void setCarparkBerthPolygon(CarparkBerthPolygon carparkBerthPolygon) {
        this.carparkBerthPolygon = carparkBerthPolygon;
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
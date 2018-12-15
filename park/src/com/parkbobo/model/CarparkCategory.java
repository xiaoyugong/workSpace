package com.parkbobo.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * 停车场分类
 * @author LH
 *
 */
@Entity
@Table(name="lq_carpark_category")
public class CarparkCategory  implements java.io.Serializable {



	private static final long serialVersionUID = -2821014590386910525L;
	/**
	 * 分类ID
	 */
	private Long categoryid;
	/**
	 * 分类名称
	 */
    private String name;
    /**
     * 排序
     */
    private Integer orderid;
    /**
     * 是否删除
     * 0：否（默认）
     * 1：是
     */
    private Integer isDel;
    /**
     * 备注
     */
    private String memo;
    private Set<Carpark> carparks = new HashSet<Carpark>(0);


    public CarparkCategory() {
    }

    public CarparkCategory(Long categoryid) {
        this.categoryid = categoryid;
    }
    
    public CarparkCategory(Long categoryid, String name, Integer orderid, Integer isDel, String memo) {
        this.categoryid = categoryid;
        this.name = name;
        this.orderid = orderid;
        this.isDel = isDel;
        this.memo = memo;
    }

   
    @Id 
    @GeneratedValue
    @Column(name="categoryid", unique=true, nullable=false)

    public Long getCategoryid() {
        return this.categoryid;
    }
    
    public void setCategoryid(Long categoryid) {
        this.categoryid = categoryid;
    }
    
    @Column(name="name", length=100)

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="orderid")

    public Integer getOrderid() {
        return this.orderid;
    }
    
    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }
    
    @Column(name="is_del")

    public Integer getIsDel() {
        return this.isDel;
    }
    
    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
    
    @Column(name="memo")

    public String getMemo() {
        return this.memo;
    }
    
    public void setMemo(String memo) {
        this.memo = memo;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="carparkCategory")

    public Set<Carpark> getCarparks() {
        return this.carparks;
    }
    
    public void setCarparks(Set<Carpark> carparks) {
        this.carparks = carparks;
    }
}
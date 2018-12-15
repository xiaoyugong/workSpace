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
 * 停车场楼层
 * @author LH
 *
 */
@Entity
@Table(name="lq_carpark_floor")
@SequenceGenerator(name = "generator", sequenceName = "lq_carpark_floor_floorid_seq", allocationSize = 1)
public class CarparkFloor  implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5009683433217110703L;
	/**
	 * 楼层ID
	 */
	private Long floorid;
	/**
	 * 所属停车场ID
	 */
    private Carpark carpark;
    /**
     * 名称
     */
    private String name;
    /**
     * 排序
     */
    private Integer orderid;
    /**
     * 是否默认显示楼层
     * 0：否（默认）
     * 1：是
     */
    private Integer isDefault;
    /**
     * 备注
     */
    private String memo;


    public CarparkFloor() {
    }

    public CarparkFloor(Long floorid) {
        this.floorid = floorid;
    }
    
    public CarparkFloor(Long floorid, Carpark carpark, String name, Integer orderid, Integer isDefault, String memo) {
        this.floorid = floorid;
        this.carpark = carpark;
        this.name = name;
        this.orderid = orderid;
        this.isDefault = isDefault;
        this.memo = memo;
    }

   
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
    @Column(name="floorid", unique=true, nullable=false)

    public Long getFloorid() {
        return this.floorid;
    }
    
    public void setFloorid(Long floorid) {
        this.floorid = floorid;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="carparkid")

    public Carpark getCarpark() {
        return this.carpark;
    }
    
    public void setCarpark(Carpark carpark) {
        this.carpark = carpark;
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
    
    @Column(name="is_default")

    public Integer getIsDefault() {
        return this.isDefault;
    }
    
    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }
    
    @Column(name="memo")

    public String getMemo() {
        return this.memo;
    }
    
    public void setMemo(String memo) {
        this.memo = memo;
    }
   








}
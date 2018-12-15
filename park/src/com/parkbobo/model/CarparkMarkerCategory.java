package com.parkbobo.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * 停车场标注分类
 * @author LH
 *
 */
@Entity
@Table(name="lq_carpark_marker_category")
@SequenceGenerator(name = "generator", sequenceName = "lq_carpark_marker_category_categoryid_seq", allocationSize = 1)
public class CarparkMarkerCategory  implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2902412510410559382L;
	/**
	 * 标注分类ID
	 */
	private Long categoryid;
	/**
	 * 分类名称
	 */
    private String name;
    /**
     * 显示图片路径
     */
    private String imgurl;
    /**
     * 备注
     */
    private String memo;
    private Set<CarparkMarkerPoint> carparkMarkerPoints = new HashSet<CarparkMarkerPoint>(0);

    public CarparkMarkerCategory() {
    }

    public CarparkMarkerCategory(Long categoryid) {
        this.categoryid = categoryid;
    }
    
    public CarparkMarkerCategory(Long categoryid, String name, String imgurl, String memo, Set<CarparkMarkerPoint> carparkMarkerPoints) {
        this.categoryid = categoryid;
        this.name = name;
        this.imgurl = imgurl;
        this.memo = memo;
        this.carparkMarkerPoints = carparkMarkerPoints;
    }

   
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
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
    
    @Column(name="imgurl")

    public String getImgurl() {
        return this.imgurl;
    }
    
    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
    
    @Column(name="memo")

    public String getMemo() {
        return this.memo;
    }
    
    public void setMemo(String memo) {
        this.memo = memo;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="carparkMarkerCategory")

    public Set<CarparkMarkerPoint> getCarparkMarkerPoints() {
        return this.carparkMarkerPoints;
    }
    
    public void setCarparkMarkerPoints(Set<CarparkMarkerPoint> carparkMarkerPoints) {
        this.carparkMarkerPoints = carparkMarkerPoints;
    }
   








}
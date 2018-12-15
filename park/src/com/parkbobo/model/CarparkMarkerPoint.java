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
import javax.persistence.Transient;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.WKTWriter;


/**
 * 停车场标注点
 * @author LH
 *
 */
@Entity
@Table(name="lq_carpark_marker_point")
@SequenceGenerator(name = "generator", sequenceName = "lq_carpark_marker_point_gid_seq", allocationSize = 1)

public class CarparkMarkerPoint  implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -9082357028489049567L;
	/**
	 * 主键ID
	 */
	private Long gid;
	/**
	 * 所属停车场ID
	 */
    private Carpark carpark;
    /**
     * 所属标注分类ID
     */
    private CarparkMarkerCategory carparkMarkerCategory;
    /**
     * 楼层ID
     */
    private Long floorid;
    /**
     * 名称
     */
    private String name;
    /**
	 * 字体颜色
	 */
	private Integer fontColor;
	/**
	 * 字体大小
	 */
	private Float fontSize;
	/**
	 * 字体加粗
	 */
	private Integer fontWeight;
	/**
	 * 字体倾斜
	 */
	private Integer fontItalic;
    /**
     * 空间几何信息
     */
    private Point geom;
    /**
     * 备注
     */
    private String memo;
    /**
	 * 字符串类型的空间经纬度信息
	 */
	private String geometry;

    public CarparkMarkerPoint() {
    }

    public CarparkMarkerPoint(Long gid) {
        this.gid = gid;
    }
    
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
    @Column(name="gid", unique=true, nullable=false)

    public Long getGid() {
        return this.gid;
    }
    
    public void setGid(Long gid) {
        this.gid = gid;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="carparkid")

    public Carpark getCarpark() {
        return this.carpark;
    }
    
    public void setCarpark(Carpark carpark) {
        this.carpark = carpark;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="categoryid")

    public CarparkMarkerCategory getCarparkMarkerCategory() {
        return this.carparkMarkerCategory;
    }
    
    public void setCarparkMarkerCategory(CarparkMarkerCategory carparkMarkerCategory) {
        this.carparkMarkerCategory = carparkMarkerCategory;
    }
    
    @Column(name="floorid")

    public Long getFloorid() {
        return this.floorid;
    }
    
    public void setFloorid(Long floorid) {
        this.floorid = floorid;
    }
    
    @Column(name="name", length=100)

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="font_color")

    public Integer getFontColor() {
        return this.fontColor;
    }
    
    public void setFontColor(Integer fontColor) {
        this.fontColor = fontColor;
    }
    
    @Column(name="font_size")

    public Float getFontSize() {
        return this.fontSize;
    }
    
    public void setFontSize(Float fontSize) {
        this.fontSize = fontSize;
    }
    
    @Column(name="font_weight")

    public Integer getFontWeight() {
        return this.fontWeight;
    }
    
    public void setFontWeight(Integer fontWeight) {
        this.fontWeight = fontWeight;
    }
    
    @Column(name="font_italic")

    public Integer getFontItalic() {
        return this.fontItalic;
    }
    
    public void setFontItalic(Integer fontItalic) {
        this.fontItalic = fontItalic;
    }
    
    @Column(name="geom")
    @Type(type="org.hibernatespatial.GeometryUserType",parameters ={
    		@Parameter(name="dialect",value="postgis")
    })
    public Point getGeom() {
        return this.geom;
    }
    
    public void setGeom(Point geom) {
        this.geom = geom;
    }
    
    
    @Column(name="memo")

    public String getMemo() {
        return this.memo;
    }
    
    public void setMemo(String memo) {
        this.memo = memo;
    }
   

    @Transient
	public String getGeometry() {
    	if(geom==null){
    		return geometry;
    	}
		WKTWriter wr = new WKTWriter();
   		geometry =  wr.write(this.geom);
   		if(geometry.length() > 0)
   		{
   			geometry = geometry.replaceAll(", ", ",");
   		}
		return geometry;
	}

	public void setGeometry(String geometry) {
		this.geometry = geometry;
	}






}
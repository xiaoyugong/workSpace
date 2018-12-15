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
import javax.persistence.Transient;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.io.WKTWriter;


/**
 * 停车场外部道路
 * @author LH
 *
 */
@Entity
@Table(name="lq_carpark_city_polyline")
@SequenceGenerator(name = "generator", sequenceName = "lq_carpark_city_polyline_gid_seq", allocationSize = 1)
public class CarparkCityPolyline  implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -3972911170433508225L;
	/**
	 * 主键ID
	 */
	private Long gid;
	/**
	 * 所属停车场ID
	 */
    private Carpark carpark;
    /**
     * 道路名称
     */
    private String name;
    /**
     * 背景色
     */
    private Integer color;
    /**
     * 边框色
     */
    private Integer bordercolor;
    /**
     * 宽度
     */
    private Float width;
    /**
     * 高度
     */
    private Float heigh;
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
    private MultiLineString geom;
    /**
     * 备注
     */
    private String memo;
    
    /**
	 * 字符串类型的空间经纬度信息
	 */
	private String geometry;
	
    public CarparkCityPolyline() {
    }

    public CarparkCityPolyline(Long gid) {
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
    
    @Column(name="name")

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="color")

    public Integer getColor() {
        return this.color;
    }
    
    public void setColor(Integer color) {
        this.color = color;
    }
    
    @Column(name="bordercolor")

    public Integer getBordercolor() {
        return this.bordercolor;
    }
    
    public void setBordercolor(Integer bordercolor) {
        this.bordercolor = bordercolor;
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
    
    @Column(name="width", precision=17, scale=17)

    public Float getWidth() {
        return this.width;
    }
    
    public void setWidth(Float width) {
        this.width = width;
    }
    
    @Column(name="heigh")

    public Float getHeigh() {
        return this.heigh;
    }
    
    public void setHeigh(Float heigh) {
        this.heigh = heigh;
    }
    
    @Column(name="geom")
    @Type(type="org.hibernatespatial.GeometryUserType",parameters ={
    		@Parameter(name="dialect",value="postgis")
    })
    public MultiLineString getGeom() {
        return this.geom;
    }
    
    public void setGeom(MultiLineString geom) {
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
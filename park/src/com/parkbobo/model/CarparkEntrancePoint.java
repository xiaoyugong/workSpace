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

import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.WKTWriter;


/**
 * 停车场进出口
 * @author LH
 *
 */
@Entity
@Table(name="lq_carpark_entrance_point")
@SequenceGenerator(name = "generator", sequenceName = "lq_carpark_entrance_point_gid_seq", allocationSize = 1)
public class CarparkEntrancePoint  implements java.io.Serializable {



     
	private static final long serialVersionUID = 3082796718676648697L;
	/**
	 * 主键ID
	 */
	private Long gid;
	/**
	 * 停车场ID
	 */
	private Carpark carpark;
	/**
	 * 楼层ID
	 */
	private Long floorid;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 类型,0:入口，1：出口，2：出入口
	 */
	private Short type;
	/**
	 * 高德经度
	 */
	private String gaodeLongitude;
	/**
	 * 高德纬度
	 */
	private String gaodeLatitude;
	/**
     * 空间几何信息
     */
    private Point geom;
    /**
	 * 字符串类型的空间经纬度信息
	 */
	private String geometry;
	private String memo;


    public CarparkEntrancePoint() {
    }

    public CarparkEntrancePoint(Long gid) {
        this.gid = gid;
    }
    public CarparkEntrancePoint(Long gid, Carpark carpark, Long floorid, String name, Short type, String gaodeLongitude, String gaodeLatitude)
    {
    	this.gid = gid;
    	this.carpark = carpark;
    	this.floorid = floorid;
    	this.name = name;
    	this.type = type;
    	this.gaodeLongitude = gaodeLongitude;
    	this.gaodeLatitude =gaodeLatitude;
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
    
    @Column(name="type")

    public Short getType() {
        return this.type;
    }
    
    public void setType(Short type) {
        this.type = type;
    }
    
    @Column(name="gaode_longitude", length=100)

    public String getGaodeLongitude() {
        return this.gaodeLongitude;
    }
    
    public void setGaodeLongitude(String gaodeLongitude) {
        this.gaodeLongitude = gaodeLongitude;
    }
    
    @Column(name="gaode_latitude", length=100)

    public String getGaodeLatitude() {
        return this.gaodeLatitude;
    }
    
    public void setGaodeLatitude(String gaodeLatitude) {
        this.gaodeLatitude = gaodeLatitude;
    }
    
    @Column(name="geom")
    @Type(type="org.hibernatespatial.GeometryUserType",parameters ={
    		@Parameter(name="dialect",value="postgis")
    })
	public Point getGeom() {
		return geom;
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
    	if(geom != null)
    	{
    		WKTWriter wr = new WKTWriter();
    		geometry =  wr.write(this.geom);
    		geometry = geometry.substring(geometry.indexOf("(")+1, geometry.indexOf(")")).replace(" ",",");
    		return geometry;
    	}
    	else
    	{
    		return geometry;
    	}
	}
	
	@Transient
	public String getLon(){
		if(geom != null)
		{
			WKTWriter wr = new WKTWriter();
			geometry =  wr.write(this.geom);
			geometry = geometry.substring(geometry.indexOf("(")+1, geometry.indexOf(")")).replace(" ",",");
			String[] arr = geometry.split(",");
			return arr[0];
		}
		else
		{
			return "";
		}
	}
	@Transient
	public String getLat(){
		if(geom != null)
		{
			WKTWriter wr = new WKTWriter();
			geometry =  wr.write(this.geom);
			geometry = geometry.substring(geometry.indexOf("(")+1, geometry.indexOf(")")).replace(" ",",");
			String[] arr = geometry.split(",");
			return arr[1];
		}
		else
		{
			return "";
		}
	}

	public void setGeometry(String geometry) {
		this.geometry = geometry;
	}








}
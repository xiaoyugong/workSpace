package com.parkbobo.model;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.WKTWriter;


/**
 * 停车场内路径规划跨楼层点位
 * @author LH
 *
 */
@Entity
@Table(name="lq_carpark_navigation_point")
@SequenceGenerator(name = "generator", sequenceName = "lq_carpark_navigation_point_gid_seq", allocationSize = 1)
public class CarparkNavigationPoint  implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 7923196428947983415L;
	/**
	 * 主键ID
	 */
	private Long gid;
	/**
	 * 停车场ID;
	 * */
	private Long carparkid;
	/**
	 * 
	 * */
	private Carpark carpark;
	/**
	 * 起始楼层ID
	 */
	private Long startFloorid;
	/**
	 * 结束楼层ID
	 */
	private Long endFloorid;
	/**
	 * 起始点空间几何信息
	 */
	private Point startGeom;
	/**
	 * 结束点空间几何信息
	 */
	private Point endGeom;
	private String startGeometry;
	private String endGeometry;
	/**
	 * 人或车走，0：人车；1;人；2：车
	 */
	private Integer roadType;
	/**
	 * 长度
	 */
	private Integer distance;
	/**
	 * 描述
	 */
	private String description;
	
	private String memo;


    public CarparkNavigationPoint() {
    }

    public CarparkNavigationPoint(Long gid) {
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
    
    @Column(name="carparkid")

    public Long getCarparkid() {
        return this.carparkid;
    }
    
    public void setCarparkid(Long carparkid) {
        this.carparkid = carparkid;
    }
    
    @Column(name="start_floorid")

    public Long getStartFloorid() {
        return this.startFloorid;
    }
    
    public void setStartFloorid(Long startFloorid) {
        this.startFloorid = startFloorid;
    }
    
    @Column(name="end_floorid")

    public Long getEndFloorid() {
        return this.endFloorid;
    }
    
    public void setEndFloorid(Long endFloorid) {
        this.endFloorid = endFloorid;
    }
    
    @Column(name="start_geom")
    @Type(type="org.hibernatespatial.GeometryUserType",parameters ={
    		@Parameter(name="dialect",value="postgis")
    })
	public Point getStartGeom() {
		return startGeom;
	}
	public void setStartGeom(Point startGeom) {
		this.startGeom = startGeom;
	}
	@Column(name="end_geom")
    @Type(type="org.hibernatespatial.GeometryUserType",parameters ={
    		@Parameter(name="dialect",value="postgis")
    })
	public Point getEndGeom() {
		return endGeom;
	}
	public void setEndGeom(Point endGeom) {
		this.endGeom = endGeom;
	}
	@Transient
	public String getStartGeometry() {
		if(startGeom==null){
			return startGeometry;
		}
		WKTWriter wr = new WKTWriter();
		startGeometry =  wr.write(this.startGeom);
   		if(startGeometry.length() > 0)
   		{
   			startGeometry = startGeometry.replaceAll(", ", ",");
   		}
		return startGeometry;
	}
	public void setStartGeometry(String startGeometry) {
		this.startGeometry = startGeometry;
	}
	@Transient
	public String getEndGeometry() {
		if(endGeom==null){
			return endGeometry;
		}
		WKTWriter wr = new WKTWriter();
		endGeometry =  wr.write(this.endGeom);
   		if(endGeometry.length() > 0)
   		{
   			endGeometry = endGeometry.replaceAll(", ", ",");
   		}
		return endGeometry;
	}
	public void setEndGeometry(String endGeometry) {
		this.endGeometry = endGeometry;
	}
    
    @Column(name="road_type")

    public Integer getRoadType() {
        return this.roadType;
    }
    
    public void setRoadType(Integer roadType) {
        this.roadType = roadType;
    }
    
    @Column(name="distance")

    public Integer getDistance() {
        return this.distance;
    }
    
    public void setDistance(Integer distance) {
        this.distance = distance;
    }
    
    @Column(name="description")

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Column(name="memo")

    public String getMemo() {
        return this.memo;
    }
    
    public void setMemo(String memo) {
        this.memo = memo;
    }

    @OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "carparkid", unique = true, nullable = false, insertable = false, updatable = false)
	public Carpark getCarpark() {
		return carpark;
	}
	public void setCarpark(Carpark carpark) {
		this.carpark = carpark;
	}







}
package com.parkbobo.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 停车场
 * @author LH
 *
 */
@Entity
@Table(name="lq_carpark")
@SequenceGenerator(name = "generator", sequenceName = "lq_carpark_carparkid_seq", allocationSize = 1)
public class Carpark  implements java.io.Serializable {
	private static final long serialVersionUID = -2489073582608914038L;
	/**
	 * 停车场ID
	 */
	private Long carparkid;
    /**
     *  分类ID
     */
	private CarparkCategory carparkCategory;
	/**
     *  名称
     */
	private String name;
	/**
     *  组图
     */
	private String picarr;
	/**
     *  车位总数
     */
	private Integer totalBerth;
	/**
     *  可预定车位数
     */
	private Integer enableBerth;
	/**
     *  省
     */
	private String province;
	/**
     *  市
     */
	private String city;
	/**
     *  区/县
     */
	private String county;
	/**
     *  地址
     */
	private String address;
	/**
     * 开门时间 
     */
	private Date opentime;
	/**
     *  关门时间
     */
	private Date closetime;
	/**
     *  收费标准
     */
	private String feeRates;
	/**
     *  首停时间
     */
	private Integer beforeMins;
	/**
     *  首停费用
     */
	private Double beforePrice;
	/**
     *  每增时间
     */
	private Integer afterMins;
	/**
     *  每增费用
     */
	private Double afterPrice;
	/**
     *  停车场地图类型，1：微地图；2：精确入口导航；3：其他
     */
	private Short maptype;
	/**
     *  停车场性质，1：路边/面（占道）；2：小区停车场；3：公共停车（免费）；4：商业停车场
     */
	private Short parktype;
	/**
     *  可停车位数
     */
	private Integer enstopnum;
	/**
     *  是否允许外来停车，0：不允许；1：允许
     */
	private Short isAllowed;
	/**
     *  显示级数
     */
	private Integer showLevel;
	/**
     *  高德经度
     */
	private Double longitude;
	/**
     * 高德纬度 
     */
	private Double latitude;
	/**
     *  显示范围（右上经度）
     */
	private String rightTopLon;
	/**
     *  显示范围（右上纬度）
     */
	private String rightTopLat;
	/**
     *  显示范围（左下经度）
     */
	private String leftBottomLon;
	/**
     *  显示范围（左下纬度）
     */
	private String leftBottomLat;
	/**
	 * 是否已认证，0：否；1：是
	 */
	private Short isAuthentication;
	/**
	 * 高德停车场分类
	 * */
	private Short amapCarparkType;
	private String memo;
	private Long posttime;
	private String username;
	/**
	 * 系统定价
	 * */
	private CarparkSystemPrice carparkSystemPrice;
	
	
    private Set<CarparkFuzhuPolygon> carparkFuzhuPolygons = new HashSet<CarparkFuzhuPolygon>(0);
    private Set<CarparkMarkerPoint> carparkMarkerPoints = new HashSet<CarparkMarkerPoint>(0);
    private Set<CarparkModelPolygon> carparkModelPolygons = new HashSet<CarparkModelPolygon>(0);
    private Set<CarparkEntrancePoint> carparkEntrancePoints = new HashSet<CarparkEntrancePoint>(0);
    private Set<CarparkReview> carparkReviews = new HashSet<CarparkReview>(0);
    private Set<CarparkNavigationPolyline> carparkNavigationPolylines = new HashSet<CarparkNavigationPolyline>(0);
    private Set<CarparkFloor> carparkFloors = new HashSet<CarparkFloor>(0);
    private Set<CarparkRoadPolyline> carparkRoadPolylines = new HashSet<CarparkRoadPolyline>(0);
    private Set<CarparkFavorite> carparkFavorites = new HashSet<CarparkFavorite>(0);
    private Set<CarparkBackgroundPolygon> carparkBackgroundPolygons = new HashSet<CarparkBackgroundPolygon>(0);
    private Set<CarparkCityPolyline> carparkCityPolylines = new HashSet<CarparkCityPolyline>(0);
    private Set<CarparkBerthPolygon> carparkBerthPolygons = new HashSet<CarparkBerthPolygon>(0);
    private Set<CarparkShopPolygon> carparkShopPolygons = new HashSet<CarparkShopPolygon>(0);
    private Set<CarparkDevice> carparkDevices = new HashSet<CarparkDevice>();
    public Carpark() {
    }

    public Carpark(Long carparkid) {
        this.carparkid = carparkid;
    }
    
    public Carpark(Long carparkid, CarparkCategory carparkCategory, String name, String picarr, Integer totalBerth, Integer enableBerth, String province, String city, String county, String address, Date opentime, Date closetime, String feeRates, Integer beforeMins, Double beforePrice, Integer afterMins, Double afterPrice, Short maptype, Short parktype, Integer enstopnum, Short isAllowed, Integer showLevel, Double longitude, Double latitude, String rightTopLon, String rightTopLat, String leftBottomLon, String leftBottomLat, Short isAuthentication, String memo) {
        this.carparkid = carparkid;
        this.carparkCategory = carparkCategory;
        this.name = name;
        this.picarr = picarr;
        this.totalBerth = totalBerth;
        this.enableBerth = enableBerth;
        this.province = province;
        this.city = city;
        this.county = county;
        this.address = address;
        this.opentime = opentime;
        this.closetime = closetime;
        this.feeRates = feeRates;
        this.beforeMins = beforeMins;
        this.beforePrice = beforePrice;
        this.afterMins = afterMins;
        this.afterPrice = afterPrice;
        this.maptype = maptype;
        this.parktype = parktype;
        this.enstopnum = enstopnum;
        this.isAllowed = isAllowed;
        this.showLevel = showLevel;
        this.longitude = longitude;
        this.latitude = latitude;
        this.rightTopLon = rightTopLon;
        this.rightTopLat = rightTopLat;
        this.leftBottomLon = leftBottomLon;
        this.leftBottomLat = leftBottomLat;
        this.isAuthentication = isAuthentication;
        this.memo = memo;
    }

   
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
    @Column(name="carparkid", unique=true, nullable=false)

    public Long getCarparkid() {
        return this.carparkid;
    }
    
    public void setCarparkid(Long carparkid) {
        this.carparkid = carparkid;
    }
	@ManyToOne
        @JoinColumn(name="categoryid")

    public CarparkCategory getCarparkCategory() {
        return this.carparkCategory;
    }
    
    public void setCarparkCategory(CarparkCategory carparkCategory) {
        this.carparkCategory = carparkCategory;
    }
    
    @Column(name="name", length=200)

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="picarr")

    public String getPicarr() {
        return this.picarr;
    }
    
    public void setPicarr(String picarr) {
        this.picarr = picarr;
    }
    
    @Column(name="total_berth")

    public Integer getTotalBerth() {
        return this.totalBerth;
    }
    
    public void setTotalBerth(Integer totalBerth) {
        this.totalBerth = totalBerth;
    }
    
    @Column(name="enable_berth")

    public Integer getEnableBerth() {
        return this.enableBerth;
    }
    
    public void setEnableBerth(Integer enableBerth) {
        this.enableBerth = enableBerth;
    }
    
    @Column(name="province", length=50)

    public String getProvince() {
        return this.province;
    }
    
    public void setProvince(String province) {
        this.province = province;
    }
    
    @Column(name="city", length=50)

    public String getCity() {
        return this.city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    @Column(name="county", length=50)

    public String getCounty() {
        return this.county;
    }
    
    public void setCounty(String county) {
        this.county = county;
    }
    
    @Column(name="address")

    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    @Column(name="opentime", length=29)

    public Date getOpentime() {
        return this.opentime;
    }
    
    public void setOpentime(Date opentime) {
        this.opentime = opentime;
    }
    
    @Column(name="closetime", length=29)

    public Date getClosetime() {
        return this.closetime;
    }
    
    public void setClosetime(Date closetime) {
        this.closetime = closetime;
    }
    
    @Column(name="fee_rates")

    public String getFeeRates() {
        return this.feeRates;
    }
    
    public void setFeeRates(String feeRates) {
        this.feeRates = feeRates;
    }
    
    @Column(name="before_mins")

    public Integer getBeforeMins() {
        return this.beforeMins;
    }
    
    public void setBeforeMins(Integer beforeMins) {
        this.beforeMins = beforeMins;
    }
    
    @Column(name="before_price", precision=12)

    public Double getBeforePrice() {
        return this.beforePrice;
    }
    
    public void setBeforePrice(Double beforePrice) {
        this.beforePrice = beforePrice;
    }
    
    @Column(name="after_mins")

    public Integer getAfterMins() {
        return this.afterMins;
    }
    
    public void setAfterMins(Integer afterMins) {
        this.afterMins = afterMins;
    }
    
    @Column(name="after_price", precision=12)

    public Double getAfterPrice() {
        return this.afterPrice;
    }
    
    public void setAfterPrice(Double afterPrice) {
        this.afterPrice = afterPrice;
    }
    
    @Column(name="maptype")

    public Short getMaptype() {
        return this.maptype;
    }
    
    public void setMaptype(Short maptype) {
        this.maptype = maptype;
    }
    
    @Column(name="parktype")

    public Short getParktype() {
        return this.parktype;
    }
    
    public void setParktype(Short parktype) {
        this.parktype = parktype;
    }
    
    @Column(name="enstopnum")

    public Integer getEnstopnum() {
        return this.enstopnum;
    }
    
    public void setEnstopnum(Integer enstopnum) {
        this.enstopnum = enstopnum;
    }
    
    @Column(name="is_allowed")

    public Short getIsAllowed() {
        return this.isAllowed;
    }
    
    public void setIsAllowed(Short isAllowed) {
        this.isAllowed = isAllowed;
    }
    
    @Column(name="show_level")

    public Integer getShowLevel() {
        return this.showLevel;
    }
    
    public void setShowLevel(Integer showLevel) {
        this.showLevel = showLevel;
    }
    
    @Column(name="longitude", precision=30, scale=20)

    public Double getLongitude() {
        return this.longitude;
    }
    
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    
    @Column(name="latitude", precision=30, scale=20)

    public Double getLatitude() {
        return this.latitude;
    }
    
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    
    @Column(name="right_top_lon", length=200)

    public String getRightTopLon() {
        return this.rightTopLon;
    }
    
    public void setRightTopLon(String rightTopLon) {
        this.rightTopLon = rightTopLon;
    }
    
    @Column(name="right_top_lat", length=200)

    public String getRightTopLat() {
        return this.rightTopLat;
    }
    
    public void setRightTopLat(String rightTopLat) {
        this.rightTopLat = rightTopLat;
    }
    
    @Column(name="left_bottom_lon", length=200)

    public String getLeftBottomLon() {
        return this.leftBottomLon;
    }
    
    public void setLeftBottomLon(String leftBottomLon) {
        this.leftBottomLon = leftBottomLon;
    }
    
    @Column(name="left_bottom_lat", length=200)

    public String getLeftBottomLat() {
        return this.leftBottomLat;
    }
    
    public void setLeftBottomLat(String leftBottomLat) {
        this.leftBottomLat = leftBottomLat;
    }
    
    @Column(name="is_authentication")
    public Short getIsAuthentication() {
		return isAuthentication;
	}

	public void setIsAuthentication(Short isAuthentication) {
		this.isAuthentication = isAuthentication;
	}
	
	
    @Column(name="memo")
    public String getMemo() {
        return this.memo;
    }
    
    public void setMemo(String memo) {
        this.memo = memo;
    }
    
    
    @Column(name="amap_carpark_type")
    public Short getAmapCarparkType() {
		return amapCarparkType;
	}

	public void setAmapCarparkType(Short amapCarparkType) {
		this.amapCarparkType = amapCarparkType;
	}

@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="carpark")
    public Set<CarparkFuzhuPolygon> getCarparkFuzhuPolygons() {
        return this.carparkFuzhuPolygons;
    }
    
    public void setCarparkFuzhuPolygons(Set<CarparkFuzhuPolygon> carparkFuzhuPolygons) {
        this.carparkFuzhuPolygons = carparkFuzhuPolygons;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="carpark")

    public Set<CarparkMarkerPoint> getCarparkMarkerPoints() {
        return this.carparkMarkerPoints;
    }
    
    public void setCarparkMarkerPoints(Set<CarparkMarkerPoint> carparkMarkerPoints) {
        this.carparkMarkerPoints = carparkMarkerPoints;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="carpark")

    public Set<CarparkModelPolygon> getCarparkModelPolygons() {
        return this.carparkModelPolygons;
    }
    
    public void setCarparkModelPolygons(Set<CarparkModelPolygon> carparkModelPolygons) {
        this.carparkModelPolygons = carparkModelPolygons;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="carpark")

    public Set<CarparkEntrancePoint> getCarparkEntrancePoints() {
        return this.carparkEntrancePoints;
    }
    
    public void setCarparkEntrancePoints(Set<CarparkEntrancePoint> carparkEntrancePoints) {
        this.carparkEntrancePoints = carparkEntrancePoints;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="carpark")

    public Set<CarparkReview> getCarparkReviews() {
        return this.carparkReviews;
    }
    
    public void setCarparkReviews(Set<CarparkReview> carparkReviews) {
        this.carparkReviews = carparkReviews;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="carpark")

    public Set<CarparkNavigationPolyline> getCarparkNavigationPolylines() {
        return this.carparkNavigationPolylines;
    }
    
    public void setCarparkNavigationPolylines(Set<CarparkNavigationPolyline> carparkNavigationPolylines) {
        this.carparkNavigationPolylines = carparkNavigationPolylines;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="carpark")

    public Set<CarparkFloor> getCarparkFloors() {
        return this.carparkFloors;
    }
    
    public void setCarparkFloors(Set<CarparkFloor> carparkFloors) {
        this.carparkFloors = carparkFloors;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="carpark")

    public Set<CarparkRoadPolyline> getCarparkRoadPolylines() {
        return this.carparkRoadPolylines;
    }
    
    public void setCarparkRoadPolylines(Set<CarparkRoadPolyline> carparkRoadPolylines) {
        this.carparkRoadPolylines = carparkRoadPolylines;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="carpark")

    public Set<CarparkFavorite> getCarparkFavorites() {
        return this.carparkFavorites;
    }
    
    public void setCarparkFavorites(Set<CarparkFavorite> carparkFavorites) {
        this.carparkFavorites = carparkFavorites;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="carpark")

    public Set<CarparkBackgroundPolygon> getCarparkBackgroundPolygons() {
        return this.carparkBackgroundPolygons;
    }
    
    public void setCarparkBackgroundPolygons(Set<CarparkBackgroundPolygon> carparkBackgroundPolygons) {
        this.carparkBackgroundPolygons = carparkBackgroundPolygons;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="carpark")

    public Set<CarparkCityPolyline> getCarparkCityPolylines() {
        return this.carparkCityPolylines;
    }
    
    public void setCarparkCityPolylines(Set<CarparkCityPolyline> carparkCityPolylines) {
        this.carparkCityPolylines = carparkCityPolylines;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="carpark")

    public Set<CarparkBerthPolygon> getCarparkBerthPolygons() {
        return this.carparkBerthPolygons;
    }
    
    public void setCarparkBerthPolygons(Set<CarparkBerthPolygon> carparkBerthPolygons) {
        this.carparkBerthPolygons = carparkBerthPolygons;
    }
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="carpark")
    
    public Set<CarparkShopPolygon> getCarparkShopPolygons() {
    	return this.carparkShopPolygons;
    }
    
    public void setCarparkShopPolygons(Set<CarparkShopPolygon> carparkShopPolygons) {
    	this.carparkShopPolygons = carparkShopPolygons;
    }
    
    @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="carpark")
    public CarparkSystemPrice getCarparkSystemPrice() {
		return carparkSystemPrice;
	}

	public void setCarparkSystemPrice(CarparkSystemPrice carparkSystemPrice) {
		this.carparkSystemPrice = carparkSystemPrice;
	}

	@Transient
	public String getExitData() {
    	StringBuffer exitStr = new StringBuffer();
    	for(CarparkEntrancePoint c : carparkEntrancePoints )
    	{
    		if(c.getType() == 1 || c.getType() == 2)
    		exitStr.append("{\"name\":\"" + c.getName() + "\"," +
					"\"lon\":\"" + c.getGaodeLongitude() + "\"," +
					"\"lat\":\"" + c.getGaodeLatitude() + "\"," +
					"\"carparkid\":\"" + carparkid + "\"," +
					"\"floorid\":\"" + c.getFloorid() + "\"," +
					"\"weiditulon\":\"" + c.getLon() + "\"," +
					"\"weiditulat\":\"" + c.getLat() + "\"},");
    	}
    	if(exitStr.length() > 0) exitStr.deleteCharAt(exitStr.length() - 1);
		return exitStr.toString();
	}

	@Transient
	public String getEntranceData() {
		StringBuffer entranceStr = new StringBuffer();
    	for(CarparkEntrancePoint c : carparkEntrancePoints )
    	{
    		if(c.getType() == 0 || c.getType() == 2)
    			entranceStr.append("{\"name\":\"" + c.getName() + "\"," +
					"\"lon\":\"" + c.getGaodeLongitude() + "\"," +
					"\"lat\":\"" + c.getGaodeLatitude() + "\"," +
					"\"parkid\":\"" + carparkid + "\"," +
					"\"floorid\":\"" + c.getFloorid() + "\"," +
					"\"weiditulon\":\"" + c.getLon() + "\"," +
					"\"weiditulat\":\"" + c.getLat() + "\"},");
    	}
    	if(entranceStr.length() > 0) entranceStr.deleteCharAt(entranceStr.length() - 1);
		return entranceStr.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((carparkid == null) ? 0 : carparkid.hashCode());
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
		Carpark other = (Carpark) obj;
		if (carparkid == null) {
			if (other.carparkid != null)
				return false;
		} else if (!carparkid.equals(other.carparkid))
			return false;
		return true;
	}

	@OneToMany(mappedBy="carpark",cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	public Set<CarparkDevice> getCarparkDevices() {
		return carparkDevices;
	}

	public void setCarparkDevices(Set<CarparkDevice> carparkDevices) {
		this.carparkDevices = carparkDevices;
	}

	public Long getPosttime() {
		return posttime;
	}

	public void setPosttime(Long posttime) {
		this.posttime = posttime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}
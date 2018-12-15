package com.parkbobo.groundlock.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.parkbobo.model.Carpark;
import com.parkbobo.utils.MacUtils;


/**
 * 地锁
 */
@Entity
@Table(name="lq_groundlock")

public class Groundlock  implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2279606416908599101L;
	/**
	 * 地锁标识号
	 */
	private String groundlockid;
	/**
	 * 停车场ID;
	 * */
	private Long carparkid;
	/**
	 * 
	 * */
	private Carpark carpark;
	/**
	 * 停车场名称
	 */
    private String carparkname;
    /**
     * 楼层ID
     */
    private Long floorid;
    /**
     * 楼层名称
     */
    private String floorname;
    /**
	 * 车位ID
	 */
    private Long berthid;
    /**
	 * 车位号
	 */
    private String berthname;
    /**
	 * 车载蓝牙个数
	 */
    private Integer carbluetoothNum;
    /**
	 * 固件版本号
	 */
    private String firmwareVersion;
    /**
	 * 软件版本号
	 */
    private String softVersion;
    /**
     * 厂家ID
     */
    private String constructId;
    /**
     * 出厂时间
     */
    private Long constructTime;
    /**
	 * 电池型号
	 */
    private String batteryModel;
    /**
	 * 电池电量
	 */
    private Integer batteryPower;
    /**
	 * 保护电量
	 */
    private Integer protectionPower;
    /**
	 * 地锁状态，0：升起，1：降下
	 */
    private Integer groundlockStatus;
    /**
     * 地锁网络状态，0：在线；1：掉线;2:休眠
     */
    private Integer onlineStatus;
    /**
	 * 蓝牙口令
	 */
    private String bluetoothPassword;
    /**
	 * SIM卡号
	 */
    private String simNum;
    /**
     * SIM卡信号强度
     */
    private String simIntensity;
    /**
     * 最后一次与服务器通讯时间
     */
    private Long lastConnectTime;
    /**
	 * 是否删除，0：否；1：是
	 */
    private Integer isDel;
    /**
     * 地锁时间
     */
    private Long groundlockTime;
    /**
     * 地锁控制模式，0：订单模式；1：蓝牙控制
     */
    private Integer controlType;
    /**
     * 所属用户ID
     */
    private String userid;
    /**
     * 是否蓝牙引起的升降，0：否；1：是
     */
    private Integer isBluetoothLift;
    /**
     * 休眠后短信唤醒次数
     */
    private Integer weakupNum;
    /**
     * 最后一次报警时间
     */
    private Long lastWarnTime;
    /**
     * 地锁初始时间
     */
    private Long posttime;
    /**
     * 自动唤醒分钟数
     */
    private Long autoWeakupTime;
    private String memo;
    
    private Date constructTimeToDate;
    /**
     * 真实MAC地址
     */
    private String groundlockMac;
    /**
     * 地锁类型   1：联网    2 蓝牙  3 联网、蓝牙
     * */
    private Integer locked_type;
    /**
     * 品牌  1 泊泊   2 泊享
     * */
    private Integer brand;
    /**
     * 地锁编码
     * */
    private String groundlockNumber;
    private Set<AccessBluetooth> accessBluetooths = new HashSet<AccessBluetooth>(0);
    private Set<RunningRecords> runningRecordses = new HashSet<RunningRecords>(0);
    private Set<MaintenanceRecords> maintenanceRecordses = new HashSet<MaintenanceRecords>(0);
    

    public Groundlock() {
    }

    public Groundlock(String groundlockid) {
        this.groundlockid = groundlockid;
    }
    
    public Groundlock(String groundlockid, Long carparkid, String carparkname, Long berthid, String berthname, 
    		Integer carbluetoothNum, String firmwareVersion, String softVersion, String constructId,
    		Long constructTime, String batteryModel, Integer batteryPower, Integer protectionPower,
    		Integer  groundlockStatus, Integer onlineStatus, String bluetoothPassword, String simNum,
    		String simIntensity, Integer isDel, Long lastConnectTime, Long groundlockTime,
    		Integer controlType, String userid, String memo,
    		Long floorid, String floorname, Long lastWarnTime,Long posttime) {
        this.groundlockid = groundlockid;
        this.carparkid = carparkid;
        this.carparkname = carparkname;
        this.berthid = berthid;
        this.berthname = berthname;
        this.carbluetoothNum = carbluetoothNum;
        this.firmwareVersion = firmwareVersion;
        this.softVersion = softVersion;
        this.constructId = constructId;
        this.constructTime = constructTime;
        this.batteryModel = batteryModel;
        this.batteryPower = batteryPower;
        this.protectionPower = protectionPower;
        this.groundlockStatus = groundlockStatus;
        this.onlineStatus = onlineStatus;
        this.bluetoothPassword = bluetoothPassword;
        this.simNum = simNum;
        this.simIntensity = simIntensity;
        this.isDel = isDel;
        this.memo = memo;
        this.lastConnectTime = lastConnectTime;
        this.groundlockTime = groundlockTime;
        this.controlType = controlType;
        this.userid = userid;
        this.floorid = floorid;
        this.floorname = floorname;
        this.lastWarnTime = lastWarnTime;
        this.posttime = posttime;
    }

   
    @Id 
    @GeneratedValue(generator = "myIdGenerator")     
	@GenericGenerator(name = "myIdGenerator", strategy = "assigned")  
    @Column(name="groundlockid", unique=true, nullable=false, length=64)

    public String getGroundlockid() {
        return this.groundlockid;
    }
    
    public void setGroundlockid(String groundlockid) {
        this.groundlockid = groundlockid;
    }
    
    @Column(name="carparkname", length=50)

    public String getCarparkname() {
        return this.carparkname;
    }
    
    public void setCarparkname(String carparkname) {
        this.carparkname = carparkname;
    }
    
    @Column(name="berthid")

    public Long getBerthid() {
        return this.berthid;
    }
    
    public void setBerthid(Long berthid) {
        this.berthid = berthid;
    }
    
    @Column(name="berthname", length=50)

    public String getBerthname() {
        return this.berthname;
    }
    
    public void setBerthname(String berthname) {
        this.berthname = berthname;
    }
    
    @Column(name="carbluetooth_num")

    public Integer getCarbluetoothNum() {
        return this.carbluetoothNum;
    }
    
    public void setCarbluetoothNum(Integer carbluetoothNum) {
        this.carbluetoothNum = carbluetoothNum;
    }
    
    @Column(name="firmware_version", length=50)

    public String getFirmwareVersion() {
        return this.firmwareVersion;
    }
    
    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }
    
    @Column(name="soft_version", length=50)

    public String getSoftVersion() {
        return this.softVersion;
    }
    
    public void setSoftVersion(String softVersion) {
        this.softVersion = softVersion;
    }
    
    @Column(name="battery_model", length=50)

    public String getBatteryModel() {
        return this.batteryModel;
    }
    
    public void setBatteryModel(String batteryModel) {
        this.batteryModel = batteryModel;
    }
    
    @Column(name="battery_power")

    public Integer getBatteryPower() {
        return this.batteryPower;
    }
    
    public void setBatteryPower(Integer batteryPower) {
        this.batteryPower = batteryPower;
    }
    
    @Column(name="protection_power")

    public Integer getProtectionPower() {
        return this.protectionPower;
    }
    
    public void setProtectionPower(Integer protectionPower) {
        this.protectionPower = protectionPower;
    }
    
    @Column(name="groundlock_status")

    public Integer getGroundlockStatus() {
        return this.groundlockStatus;
    }
    
    public void setGroundlockStatus(Integer groundlockStatus) {
        this.groundlockStatus = groundlockStatus;
    }
    
    @Column(name="online_status") 
    public Integer getOnlineStatus() {                          
   		return onlineStatus;                                     
    }                                                            
                                                                
    public void setOnlineStatus(Integer onlineStatus) {          
    	this.onlineStatus = onlineStatus;                        
    }                                                            
    
    @Column(name="bluetooth_password", length=32)

    public String getBluetoothPassword() {
        return this.bluetoothPassword;
    }
    
    public void setBluetoothPassword(String bluetoothPassword) {
        this.bluetoothPassword = bluetoothPassword;
    }
    
    @Column(name="sim_num", length=50)

    public String getSimNum() {
        return this.simNum;
    }
    
    public void setSimNum(String simNum) {
        this.simNum = simNum;
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
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="groundlock")

    public Set<AccessBluetooth> getAccessBluetooths() {
        return this.accessBluetooths;
    }
    
    public void setAccessBluetooths(Set<AccessBluetooth> accessBluetooths) {
        this.accessBluetooths = accessBluetooths;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="groundlock")

    public Set<RunningRecords> getRunningRecordses() {
        return this.runningRecordses;
    }
    
    public void setRunningRecordses(Set<RunningRecords> runningRecordses) {
        this.runningRecordses = runningRecordses;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="groundlock")

    public Set<MaintenanceRecords> getMaintenanceRecordses() {
        return this.maintenanceRecordses;
    }
    
    public void setMaintenanceRecordses(Set<MaintenanceRecords> maintenanceRecordses) {
        this.maintenanceRecordses = maintenanceRecordses;
    }
    @Column(name="last_connect_time")
	public Long getLastConnectTime() {
		return lastConnectTime;
	}

	public void setLastConnectTime(Long lastConnectTime) {
		this.lastConnectTime = lastConnectTime;
	}
	@Column(name="construct_id")
	public String getConstructId() {
		return constructId;
	}

	public void setConstructId(String constructId) {
		this.constructId = constructId;
	}
	@Column(name="construct_time")
	public Long getConstructTime() {
		return constructTime;
	}

	public void setConstructTime(Long constructTime) {
		this.constructTime = constructTime;
	}
	@Column(name="sim_intensity")
	public String getSimIntensity() {
		return simIntensity;
	}

	public void setSimIntensity(String simIntensity) {
		this.simIntensity = simIntensity;
	}
	@Column(name="groundlock_time")
	public Long getGroundlockTime() {
		return groundlockTime;
	}

	public void setGroundlockTime(Long groundlockTime) {
		this.groundlockTime = groundlockTime;
	}
	@Column(name="control_type")
	public Integer getControlType() {
		return controlType;
	}

	public void setControlType(Integer controlType) {
		this.controlType = controlType;
	}
	@Column(name="userid")
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	@Transient
	public String getGroundlockMac() {
		if(groundlockid != null)
		{
			groundlockMac = MacUtils.getInstance().longToMac(Long.parseLong(groundlockid));
		}
		return groundlockMac;
	}
	@Transient
	public Date getConstructTimeToDate() {
		if(constructTime != null)
    	{
			constructTimeToDate = new Date(constructTime);
    	}
		return constructTimeToDate;
	}

	public void setConstructTimeToDate(Date constructTimeToDate) {
		this.constructTimeToDate = constructTimeToDate;
	}
	@Column(name="carparkid")
	public Long getCarparkid() {
		return carparkid;
	}

	public void setCarparkid(Long carparkid) {
		this.carparkid = carparkid;
	}
	@Column(name="is_bluetooth_lift")
	public Integer getIsBluetoothLift() {
		return isBluetoothLift;
	}

	public void setIsBluetoothLift(Integer isBluetoothLift) {
		this.isBluetoothLift = isBluetoothLift;
	}
	
	@Column(name="weakup_num")
	public Integer getWeakupNum() {
		return weakupNum;
	}

	public void setWeakupNum(Integer weakupNum) {
		this.weakupNum = weakupNum;
	}

	public void setGroundlockMac(String groundlockMac) {
		this.groundlockMac = groundlockMac;
	}
	@Column(name="floorid")
	public Long getFloorid() {
		return floorid;
	}

	public void setFloorid(Long floorid) {
		this.floorid = floorid;
	}
	@Column(name="floorname")
	public String getFloorname() {
		return floorname;
	}

	public void setFloorname(String floorname) {
		this.floorname = floorname;
	}
	@Column(name="last_warn_time")
	public Long getLastWarnTime() {
		return lastWarnTime;
	}

	public void setLastWarnTime(Long lastWarnTime) {
		this.lastWarnTime = lastWarnTime;
	}
	@Column(name="posttime")
	public Long getPosttime() {
		return posttime;
	}

	public void setPosttime(Long posttime) {
		this.posttime = posttime;
	}
	@Column(name="auto_weakup_time")
	public Long getAutoWeakupTime() {
		return autoWeakupTime;
	}

	public void setAutoWeakupTime(Long autoWeakupTime) {
		this.autoWeakupTime = autoWeakupTime;
	}
	@Column(name="locked_type")
	public Integer getLocked_type() {
		return locked_type;
	}

	public void setLocked_type(Integer lockedType) {
		locked_type = lockedType;
	}
	@Column(name="brand")
	public Integer getBrand() {
		return brand;
	}

	public void setBrand(Integer brand) {
		this.brand = brand;
	}
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "carparkid", unique = true, nullable = false, insertable = false, updatable = false)
	public Carpark getCarpark() {
		return carpark;
	}
	public void setCarpark(Carpark carpark) {
		this.carpark = carpark;
	}
	
	public void setGroundlockNumber(String groundlockNumber) {
		this.groundlockNumber = groundlockNumber;
	}

	@Column(name="groundlock_number")
	public String getGroundlockNumber() {
		return groundlockNumber;
	}
	
}
package com.parkbobo.groundlock.model;

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

import com.parkbobo.utils.MacUtils;

/**
 * 地锁授权蓝牙列表
 * @author LH
 *
 */
@Entity
@Table(name="lq_access_bluetooth")
@SequenceGenerator(name = "generator", sequenceName = "lq_access_bluetooth_bluetoothid_seq", allocationSize = 1)
public class AccessBluetooth  implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8023647387119600372L;
	/**
	 * 蓝牙标识号
	 */
	private Long bluetoothid;
	/**
	 * 地锁标识号
	 */
	private Groundlock groundlock;
	/**
	 * 蓝牙地址
	 */
	private String bluetoothmac;
	/**
	 * 蓝牙类型，0：车载蓝牙；1：手机蓝牙
	 */
	private Integer bluetoothtype;
	/**
	 * 蓝牙使能状态，0：否；1：是
	 */
	private Integer bluetoothEnable;
	/**
	 * 车牌号
	 */
	private String carNumber;
	/**
	 * 手机号
	 */
	private String telephone;
	/**
	 * 是否停车车位上,0:否；1：是
	 */
	private Integer isStop;
	private String memo;
	private String bluetoothmacStr;
    public AccessBluetooth() {
    }

    public AccessBluetooth(Long bluetoothid) {
        this.bluetoothid = bluetoothid;
    }
    
    public AccessBluetooth(Long bluetoothid, Groundlock groundlock, String bluetoothmac, Integer bluetoothtype, Integer bluetoothEnable, String carNumber, String telephone, Integer isStop, String memo) {
        this.bluetoothid = bluetoothid;
        this.groundlock = groundlock;
        this.bluetoothmac = bluetoothmac;
        this.bluetoothtype = bluetoothtype;
        this.bluetoothEnable = bluetoothEnable;
        this.carNumber = carNumber;
        this.telephone = telephone;
        this.isStop = isStop;
        this.memo = memo;
    }

   
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
    @Column(name="bluetoothid", unique=true, nullable=false)

    public Long getBluetoothid() {
        return this.bluetoothid;
    }
    
    public void setBluetoothid(Long bluetoothid) {
        this.bluetoothid = bluetoothid;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="groundlockid")

    public Groundlock getGroundlock() {
        return this.groundlock;
    }
    
    public void setGroundlock(Groundlock groundlock) {
        this.groundlock = groundlock;
    }
    
    @Column(name="bluetoothmac", length=100)

    public String getBluetoothmac() {
        return this.bluetoothmac;
    }
    
    public void setBluetoothmac(String bluetoothmac) {
        this.bluetoothmac = bluetoothmac;
    }
    
    @Column(name="bluetoothtype")

    public Integer getBluetoothtype() {
        return this.bluetoothtype;
    }
    
    public void setBluetoothtype(Integer bluetoothtype) {
        this.bluetoothtype = bluetoothtype;
    }
    
    @Column(name="bluetooth_enable")

    public Integer getBluetoothEnable() {
        return this.bluetoothEnable;
    }
    
    public void setBluetoothEnable(Integer bluetoothEnable) {
        this.bluetoothEnable = bluetoothEnable;
    }
    
    @Column(name="memo")

    public String getMemo() {
        return this.memo;
    }
    
    public void setMemo(String memo) {
        this.memo = memo;
    }
    @Column(name="car_number")
	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	@Column(name="telephone")
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	@Column(name="is_stop")
	public Integer getIsStop() {
		return isStop;
	}

	public void setIsStop(Integer isStop) {
		this.isStop = isStop;
	}
	@Transient
	public String getBluetoothmacStr() {
		if(bluetoothmacStr == null){
			bluetoothmacStr = MacUtils.getInstance().longToMac(Long.parseLong(bluetoothmac));
		}
		return bluetoothmacStr;
	}

	public void setBluetoothmacStr(String bluetoothmacStr) {
		this.bluetoothmacStr = bluetoothmacStr;
	}

}
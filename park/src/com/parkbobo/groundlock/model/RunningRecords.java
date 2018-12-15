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


/**
 * 地锁运行记录
 */
@Entity
@Table(name="lq_running_records")
@SequenceGenerator(name = "generator", sequenceName = "lq_running_records_recordid_seq", allocationSize = 1)
public class RunningRecords  implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1751753970134513434L;
	/**
	 * 记录ID
	 */
	private Long recordid;
	/**
	 * 地锁标识号
	 */
	private Groundlock groundlock;
	/**
	 * 运行动作
	 * 100:地锁上线；
	 * 101:地锁下线;
	 * 102:地锁心跳；
	 * 103:地锁休眠；
	 * 104:蓝牙升锁；
	 * 105:蓝牙降锁
	 * 106:上传蓝牙列表
	 * 107:上传蓝牙口令
	 * 108:上传电量
	 * 109：上传地锁信息
	 * 110:上传SIM卡号
	 * 111:上传保护电量
	 * 112:上传IP列表
	 * 113:上传当前状态
	 * 114:上传时间
	 * 115:上传报警
	 * 116:上传可唤醒地锁电话
	 * 117:上传地锁唤醒
	 * 118:上传SIM卡型号强度
	 * 119：地锁短信唤醒
	 * 120:地锁电话唤醒
	 * 
	 * 
	 * 200:服务器升锁
	 * 201:服务器降锁
	 * 202:修改蓝牙列表
	 * 203:修改蓝牙口令
	 * 204:修改可唤醒地锁电话
	 * 205:修改保护电量
	 * 206:修改IP列表
	 */
	private String runAction;
	/**
	 * 记录时间
	 */
	private Long recordtime;
	/**
	 * 来源设备,0:地锁；1：服务器 
	 */
	private String fromdevice;
	/**
	 * 详细信息
	 */
	private String description;
	/**
	 * 是否收到回执，0：否；1：是
	 */
	private Integer isBack;
	/**
	 * 收到回执时间
	 */
	private Long backtime;
	/**
	 * 回执信息
	 */
	private String backInfo;
	
	private String memo;


    public RunningRecords() {
    }

    public RunningRecords(Long recordid) {
        this.recordid = recordid;
    }
    
    public RunningRecords(Long recordid, Groundlock groundlock, String runAction, Long recordtime, String fromdevice, String description, Integer isBack, Long backtime, String backInfo, String memo) {
        this.recordid = recordid;
        this.groundlock = groundlock;
        this.runAction = runAction;
        this.recordtime = recordtime;
        this.fromdevice = fromdevice;
        this.description = description;
        this.isBack = isBack;
        this.backtime = backtime;
        this.backInfo = backInfo;
        this.memo = memo;
    }

   
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
    @Column(name="recordid", unique=true, nullable=false)

    public Long getRecordid() {
        return this.recordid;
    }
    
    public void setRecordid(Long recordid) {
        this.recordid = recordid;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="groundlockid")

    public Groundlock getGroundlock() {
        return this.groundlock;
    }
    
    public void setGroundlock(Groundlock groundlock) {
        this.groundlock = groundlock;
    }
    
    @Column(name="run_action")

    public String getRunAction() {
        return this.runAction;
    }
    
    public void setRunAction(String runAction) {
        this.runAction = runAction;
    }
    
    @Column(name="recordtime")

    public Long getRecordtime() {
        return this.recordtime;
    }
    
    public void setRecordtime(Long recordtime) {
        this.recordtime = recordtime;
    }
    
    @Column(name="fromdevice", length=50)

    public String getFromdevice() {
        return this.fromdevice;
    }
    
    public void setFromdevice(String fromdevice) {
        this.fromdevice = fromdevice;
    }
    
    @Column(name="memo")

    public String getMemo() {
        return this.memo;
    }
    
    public void setMemo(String memo) {
        this.memo = memo;
    }
    @Column(name="description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	@Column(name="is_back")
	public Integer getIsBack() {
		return isBack;
	}

	public void setIsBack(Integer isBack) {
		this.isBack = isBack;
	}
	@Column(name="backtime")
	public Long getBacktime() {
		return backtime;
	}

	public void setBacktime(Long backtime) {
		this.backtime = backtime;
	}
	@Column(name="back_info")
	public String getBackInfo() {
		return backInfo;
	}

	public void setBackInfo(String backInfo) {
		this.backInfo = backInfo;
	}
}
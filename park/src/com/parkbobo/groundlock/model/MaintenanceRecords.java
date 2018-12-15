package com.parkbobo.groundlock.model;

import java.io.Writer;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.scheduling.quartz.AdaptableJobFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 地锁维护记录
 * @author LH
 *
 */
@Entity
@Table(name="lq_maintenance_records")
@SequenceGenerator(name = "generator", sequenceName = "lq_maintenance_records_recordid_seq", allocationSize = 1)
public class MaintenanceRecords  implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6665004244182887150L;
	/**
	 * 记录ID
	 */
	private Long recordid;
	/**
	 * 地锁标识号
	 */
    private Groundlock groundlock;
    /**
	 * 故障申请时间
	 */
    private Long posttime;
    /**
	 * 故障原因
	 */
    private String faultCause;
    /**
     * 报修客户
     */
    private String customer;
    /**
     * 报修客户电话
     */
    private String customerTelephone;
    /**
     * 是否接报 0：否；1：是
     */
    private Integer isOperate;
    /**
     * 接报人
     */
    private String customerService;
    /**
     * 接报时间
     */
    private Long operateTime;
    /**
     * 紧急度 0:非常紧急 1：紧急 2：一般
     */
    private Integer emergency;
    /**
     * 接报人转接单人方式：inform_way，0：短信；1：电话；2：口述
     */
    private Integer informWay;
    /**
     * 是否接单：is_accept，0：否；1：是
     */
    private Integer isAccept;
    /**
     * 接单人：repairman
     */
    private String repairman;
    /**
     * 接单时间：accept_time
     */
    private Long acceptTime;
    /**
     * 达到时间： arrive_time
     */
    private Long arriveTime;
    /**
     * 完成时间： finish_time
     */
    private Long finishTime;
    /**
     * 维修内容及描述：repair_description
     */
    private String repairDescription;
    /**
     * 处理过程及完成情况：process_description
     */
    private String processDescription;
    /**
     * 是否维修完成：is_repair_finish，0：否；1：是
     */
    private Integer isRepairFinish;
    /**
     * 是否确认完成：is_confirm_finish，0：否；1：是
     */
    private Integer isConfirmFinish;
    /**
     * 备注
     */
    private String memo;
    
    private Set<MaintenanceComponents> maintenanceComponentses = new HashSet<MaintenanceComponents>(
			0);
	
    public MaintenanceRecords() {
    }

    public MaintenanceRecords(Long recordid) {
        this.recordid = recordid;
    }
    
    public MaintenanceRecords(Long recordid, Groundlock groundlock, Long posttime, String faultCause, String customService, 
    		Long operatetime,  String worker, Long workstarttime, Long workendtime, Double workHours, 
    		String customFeedback, String customer,String customerTelephone,
    		Integer isOperate,String customerService,Long operateTime,Integer emergency,
    		Integer informWay,Integer isAccept,String repairman,Long acceptTime,
    		Long arriveTime,Long finishTime,String repairDescription,
    		String processDescription,Integer isRepairFinish,Integer isConfirmFinish,String memo) {
        this.recordid = recordid;
        this.groundlock = groundlock;
        this.posttime = posttime;
        this.faultCause = faultCause;
        this.customer = customer;
        this.customerTelephone = customerTelephone;
        this.isOperate = isOperate;
        this.customerService = customerService;
        this.operateTime = operateTime;
        this.emergency = emergency;
        this.informWay = informWay;
        this.isAccept = isAccept;
        this.repairman = repairman;
        this.acceptTime = acceptTime;
        this.arriveTime = arriveTime;
        this.finishTime = finishTime;
        this.repairDescription = repairDescription;
        this.processDescription = processDescription;
        this.isRepairFinish = isRepairFinish;
        this.isConfirmFinish = isConfirmFinish;
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
    
    @Column(name="posttime")

    public Long getPosttime() {
        return this.posttime;
    }
    
    public void setPosttime(Long posttime) {
        this.posttime = posttime;
    }
    
    @Column(name="fault_cause")

    public String getFaultCause() {
        return this.faultCause;
    }
    
    public void setFaultCause(String faultCause) {
        this.faultCause = faultCause;
    }
    
    @Column(name="memo")

    public String getMemo() {
        return this.memo;
    }
    
    public void setMemo(String memo) {
        this.memo = memo;
    }
    
    @Column(name="customer")
	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}
	@Column(name="customer_telephone")
	public String getCustomerTelephone() {
		return customerTelephone;
	}

	public void setCustomerTelephone(String customerTelephone) {
		this.customerTelephone = customerTelephone;
	}
	@Column(name="is_operate")
	public Integer getIsOperate() {
		return isOperate;
	}

	public void setIsOperate(Integer isOperate) {
		this.isOperate = isOperate;
	}
	@Column(name="customer_service")
	public String getCustomerService() {
		return customerService;
	}

	public void setCustomerService(String customerService) {
		this.customerService = customerService;
	}
	@Column(name="operate_time")
	public Long getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Long operateTime) {
		this.operateTime = operateTime;
	}
	@Column(name="emergency")
	public Integer getEmergency() {
		return emergency;
	}

	public void setEmergency(Integer emergency) {
		this.emergency = emergency;
	}
	@Column(name="inform_way")
	public Integer getInformWay() {
		return informWay;
	}

	public void setInformWay(Integer informWay) {
		this.informWay = informWay;
	}
	@Column(name="is_accept")
	public Integer getIsAccept() {
		return isAccept;
	}

	public void setIsAccept(Integer isAccept) {
		this.isAccept = isAccept;
	}
	@Column(name="repairman")
	public String getRepairman() {
		return repairman;
	}

	public void setRepairman(String repairman) {
		this.repairman = repairman;
	}
	@Column(name="accept_time")
	public Long getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(Long acceptTime) {
		this.acceptTime = acceptTime;
	}
	@Column(name="arrive_time")
	public Long getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(Long arriveTime) {
		this.arriveTime = arriveTime;
	}
	@Column(name="finish_time")
	public Long getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Long finishTime) {
		this.finishTime = finishTime;
	}
	@Column(name="repair_description")
	public String getRepairDescription() {
		return repairDescription;
	}

	public void setRepairDescription(String repairDescription) {
		this.repairDescription = repairDescription;
	}
	@Column(name="process_description")
	public String getProcessDescription() {
		return processDescription;
	}

	public void setProcessDescription(String processDescription) {
		this.processDescription = processDescription;
	}
	@Column(name="is_repair_finish")
	public Integer getIsRepairFinish() {
		return isRepairFinish;
	}

	public void setIsRepairFinish(Integer isRepairFinish) {
		this.isRepairFinish = isRepairFinish;
	}
	@Column(name="is_confirm_finish")
	public Integer getIsConfirmFinish() {
		return isConfirmFinish;
	}

	public void setIsConfirmFinish(Integer isConfirmFinish) {
		this.isConfirmFinish = isConfirmFinish;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "maintenanceRecords")
	public Set<MaintenanceComponents> getMaintenanceComponentses() {
		return maintenanceComponentses;
	}

	public void setMaintenanceComponentses(
			Set<MaintenanceComponents> maintenanceComponentses) {
		this.maintenanceComponentses = maintenanceComponentses;
	}

}

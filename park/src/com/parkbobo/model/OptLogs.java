package com.parkbobo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.opensymphony.xwork2.ActionContext;
import com.parkbobo.manager.model.Manager;


/**
 * 系统操作日志
 * @author LH
 *
 */
@Entity
@Table(name="lq_opt_logs")
@SequenceGenerator(name = "generator", sequenceName = "lq_opt_logs_log_id_seq", allocationSize = 1)

public class OptLogs  implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -92330026853022836L;
	/**
	 * 日志ID
	 */
	private Long logId;
	/**
	 * 来源模块
	 */
    private String fromModel;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 操作描述
     */
    private String description;
    /**
     * 创建时间
     */
    private Long createTime;
    private String memo;



    public OptLogs() {
    }

    public OptLogs(Long logId, String fromModel, Long userId, String username, Long createTime) {
        this.logId = logId;
        this.fromModel = fromModel;
        this.userId = userId;
        this.username = username;
        this.createTime = createTime;
    }
    
    public OptLogs(Long logId, String fromModel, Long userId, String username, String description, Long createTime, String memo) {
        this.logId = logId;
        this.fromModel = fromModel;
        this.userId = userId;
        this.username = username;
        this.description = description;
        this.createTime = createTime;
        this.memo = memo;
    }

    /**
     * 快速创建日志对象
     * @param description   操作描述
     * @param logModel   对应模块
     */
    public  OptLogs(String description,String logModel) {
		Manager manager = (Manager) ActionContext.getContext().getSession().get("loginManager");
		this.createTime = System.currentTimeMillis();
		this.description =description;
		 this.fromModel = logModel;
		 this.userId = manager.getUserId();
		 this.username = manager.getUsername();
	}
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
    @Column(name="log_id", unique=true, nullable=false)

    public Long getLogId() {
        return this.logId;
    }
    
    public void setLogId(Long logId) {
        this.logId = logId;
    }
    
    @Column(name="from_model", nullable=false, length=200)

    public String getFromModel() {
        return this.fromModel;
    }
    
    public void setFromModel(String fromModel) {
        this.fromModel = fromModel;
    }
    
    @Column(name="user_id", nullable=false)

    public Long getUserId() {
        return this.userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    @Column(name="username", nullable=false, length=100)

    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    @Column(name="description")

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Column(name="create_time", nullable=false)

    public Long getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
    
    @Column(name="memo")
    public String getMemo() {
        return this.memo;
    }
    
    public void setMemo(String memo) {
        this.memo = memo;
    }
   




}
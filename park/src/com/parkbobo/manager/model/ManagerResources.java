package com.parkbobo.manager.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * 用户资源权限
 */
@Entity
@Table(name="lq_manager_resources")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ManagerResources  implements java.io.Serializable {
	private static final long serialVersionUID = -6638035845002198719L;
	/**
	 * 主键
	 */
	private ManagerResourcesId id;
	/**
	 * 用户
	 */
    private Manager manager;
    /**
     * 资源
     */
    private Resources resources;
    /**
     * 资源类型，0：可访问；1：可授权
     */
    private Short type;
    private String memo;


    public ManagerResources() {
    }

    public ManagerResources(ManagerResourcesId id, Manager manager, Resources resources, Short type) {
        this.id = id;
        this.manager = manager;
        this.resources = resources;
        this.type = type;
    }
    
    public ManagerResources(ManagerResourcesId id, Manager manager, Resources resources, Short type, String memo) {
        this.id = id;
        this.manager = manager;
        this.resources = resources;
        this.type = type;
        this.memo = memo;
    }

   
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="resourcesId", column=@Column(name="resources_id", nullable=false) ), 
        @AttributeOverride(name="userId", column=@Column(name="user_id", nullable=false) ) } )

    public ManagerResourcesId getId() {
        return this.id;
    }
    
    public void setId(ManagerResourcesId id) {
        this.id = id;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="user_id", nullable=false, insertable=false, updatable=false)

    public Manager getManager() {
        return this.manager;
    }
    
    public void setManager(Manager manager) {
        this.manager = manager;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="resources_id", nullable=false, insertable=false, updatable=false)

    public Resources getResources() {
        return this.resources;
    }
    
    public void setResources(Resources resources) {
        this.resources = resources;
    }
    
    @Column(name="type", nullable=false)

    public Short getType() {
        return this.type;
    }
    
    public void setType(Short type) {
        this.type = type;
    }
    
    @Column(name="memo")

    public String getMemo() {
        return this.memo;
    }
    
    public void setMemo(String memo) {
        this.memo = memo;
    }
   








}
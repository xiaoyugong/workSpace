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
 * 角色资源权限
 */
@Entity
@Table(name="lq_role_resources")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RoleResources  implements java.io.Serializable {
	private static final long serialVersionUID = 4842105589869894261L;
	private RoleResourcesId id;
     private Role role;
     private Resources resources;
     private Short type;
     private String memo;

    public RoleResources() {
    }

    public RoleResources(RoleResourcesId id, Role role, Resources resources, Short type) {
        this.id = id;
        this.role = role;
        this.resources = resources;
        this.type = type;
    }
    
    public RoleResources(RoleResourcesId id, Role role, Resources resources, Short type, String memo) {
        this.id = id;
        this.role = role;
        this.resources = resources;
        this.type = type;
        this.memo = memo;
    }

   
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="roleId", column=@Column(name="role_id", nullable=false) ), 
        @AttributeOverride(name="resourcesId", column=@Column(name="resources_id", nullable=false) ) } )

    public RoleResourcesId getId() {
        return this.id;
    }
    
    public void setId(RoleResourcesId id) {
        this.id = id;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="role_id", nullable=false, insertable=false, updatable=false)

    public Role getRole() {
        return this.role;
    }
    
    public void setRole(Role role) {
        this.role = role;
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
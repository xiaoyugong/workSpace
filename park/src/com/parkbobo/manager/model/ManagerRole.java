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
 * 用户角色
 */
@Entity
@Table(name="lq_manager_role")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ManagerRole  implements java.io.Serializable {


     /**
	 * 
	 */
	private static final long serialVersionUID = 5123934932876486069L;
	private ManagerRoleId id;
     private Role role;
     private Manager manager;
     private String memo;


    public ManagerRole() {
    }

    public ManagerRole(ManagerRoleId id, Role role, Manager manager) {
        this.id = id;
        this.role = role;
        this.manager = manager;
    }
    
    public ManagerRole(ManagerRoleId id, Role role, Manager manager, String memo) {
        this.id = id;
        this.role = role;
        this.manager = manager;
        this.memo = memo;
    }

   
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="userId", column=@Column(name="user_id", nullable=false) ), 
        @AttributeOverride(name="roleId", column=@Column(name="role_id", nullable=false) ) } )

    public ManagerRoleId getId() {
        return this.id;
    }
    
    public void setId(ManagerRoleId id) {
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
        @JoinColumn(name="user_id", nullable=false, insertable=false, updatable=false)

    public Manager getManager() {
        return this.manager;
    }
    
    public void setManager(Manager manager) {
        this.manager = manager;
    }
    
    @Column(name="memo")

    public String getMemo() {
        return this.memo;
    }
    
    public void setMemo(String memo) {
        this.memo = memo;
    }
   








}
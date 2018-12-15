package com.parkbobo.manager.model;

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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * 管理用户角色
 */
@Entity
@Table(name="lq_role")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@SequenceGenerator(name = "generator", sequenceName = "lq_role_role_id_seq", allocationSize = 1)
public class Role  implements java.io.Serializable {

	private static final long serialVersionUID = 7638767917884283249L;
	/**
	 * 角色ID
	 */
	private Long roleId;
	/**
	 * 角色名称
	 */
    private String name;
    /**
     * 角色英文名
     */
    private String enname;
    /**
     * 是否可用，0：否；1：是
     */
    private Short enable;
    /**
     * 是否系统核心模块，0：否；1：是
     */
    private Short iscore;
    /**
     * 排序
     */
    private Long orderid;
    /**
     * 创建时间
     */
    private Long createTime;
    
    private String memo;
    private Set<RoleResources> roleResourceses = new HashSet<RoleResources>(0);
    private Set<ManagerRole> managerRoles = new HashSet<ManagerRole>(0);

    private Date createTimeToDate;
    
    public Role() {
    }

    public Role(Long roleId) {
        this.roleId = roleId;
    }
    
    public Role(Long roleId, String name, String enname, Short enable, Short iscore, Long orderid, Long createTime, String memo, Set<RoleResources> roleResourceses, Set<ManagerRole> managerRoles) {
        this.roleId = roleId;
        this.name = name;
        this.enname = enname;
        this.enable = enable;
        this.iscore = iscore;
        this.orderid = orderid;
        this.createTime = createTime;
        this.memo = memo;
        this.roleResourceses = roleResourceses;
        this.managerRoles = managerRoles;
    }

   
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
    @Column(name="role_id", unique=true, nullable=false)

    public Long getRoleId() {
        return this.roleId;
    }
    
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
    
    @Column(name="name", nullable=false, length=50)

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="enname", length=50)

    public String getEnname() {
        return this.enname;
    }
    
    public void setEnname(String enname) {
        this.enname = enname;
    }
    
    @Column(name="enable", nullable=false)

    public Short getEnable() {
        return this.enable;
    }
    
    public void setEnable(Short enable) {
        this.enable = enable;
    }
    
    @Column(name="iscore", nullable=false)

    public Short getIscore() {
        return this.iscore;
    }
    
    public void setIscore(Short iscore) {
        this.iscore = iscore;
    }
    
    @Column(name="orderid")

    public Long getOrderid() {
        return this.orderid;
    }
    
    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }
    
    @Column(name="create_time")

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
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="role")

    public Set<RoleResources> getRoleResourceses() {
        return this.roleResourceses;
    }
    
    public void setRoleResourceses(Set<RoleResources> roleResourceses) {
        this.roleResourceses = roleResourceses;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="role")

    public Set<ManagerRole> getManagerRoles() {
        return this.managerRoles;
    }
    
    public void setManagerRoles(Set<ManagerRole> managerRoles) {
        this.managerRoles = managerRoles;
    }
    @Transient
	public Date getCreateTimeToDate() {
    	if(createTime != null)
    	{
    		createTimeToDate = new Date(createTime);
    	}
		return createTimeToDate;
	}

	public void setCreateTimeToDate(Date createTimeToDate) {
		this.createTimeToDate = createTimeToDate;
	}

}
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * 系统资源权限
 */
@Entity
@Table(name="lq_resources"
, uniqueConstraints = @UniqueConstraint(columnNames="enname")
)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@SequenceGenerator(name = "generator", sequenceName = "lq_resources_resources_id_seq", allocationSize = 1)
public class Resources  implements java.io.Serializable {

	private static final long serialVersionUID = 6825239521883223546L;
	/**
	 * 资源ID
	 */
	private Long resourcesId;
	/**
	 * 菜单
	 */
    private Menu menu;
    /**
	 * 父资源
	 */
    private Resources resources;
    /**
	 * 资源名称
	 */
    private String name;
    /**
	 * 资源英文名
	 */
    private String enname;
    /**
	 * 资源类型
	 */
    private String resourcetype;
    /**
	 * 资源Action
	 */
    private String target;
    /**
	 * 是否展开，0：否；1：是
	 */
    private Short isopen;
    /**
	 * 是否叶子节点，0：否；1：是
	 */
    private Short isleaf;
    /**
	 * 是否系统核心模块，0：否；1：是
	 */
    private Short iscore;
    /**
	 * 是否可用，0：否；1：是
	 */
    private Short enable;
    /**
	 * 创建时间
	 */
    private Long createTime;
    /**
	 * 排序
	 */
    private Long orderid;
    
    private String memo;
    
    private Set<RoleResources> roleResourceses = new HashSet<RoleResources>(0);
    private Set<Resources> resourceses = new HashSet<Resources>(0);
    private Set<ManagerResources> managerResourceses = new HashSet<ManagerResources>(0);
    
    private Date createTimeToDate;

    public Resources() {
    }

    public Resources(Menu menu, String name, String enname,
			 String target) {
		this.menu = menu;
		this.name = name;
		this.enname = enname;
		this.resourcetype ="0";
		this.target = target;
		this.isopen = 1;
		this.isleaf = 1;
		this.iscore = 1;
		this.enable = 1;
		this.createTime = System.currentTimeMillis();
	}

	public Resources(Long resourcesId) {
        this.resourcesId = resourcesId;
    }
    
    public Resources(Long resourcesId, Menu menu, Resources resources, String name, String enname, String resourcetype, String target, Short isopen, Short isleaf, Short iscore, Short enable, Long createTime, Long orderid, String memo, Set<RoleResources> roleResourceses, Set<Resources> resourceses, Set<ManagerResources> managerResourceses) {
        this.resourcesId = resourcesId;
        this.menu = menu;
        this.resources = resources;
        this.name = name;
        this.enname = enname;
        this.resourcetype = resourcetype;
        this.target = target;
        this.isopen = isopen;
        this.isleaf = isleaf;
        this.iscore = iscore;
        this.enable = enable;
        this.createTime = createTime;
        this.orderid = orderid;
        this.memo = memo;
        this.roleResourceses = roleResourceses;
        this.resourceses = resourceses;
        this.managerResourceses = managerResourceses;
    }

   
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
    @Column(name="resources_id", unique=true, nullable=false)

    public Long getResourcesId() {
        return this.resourcesId;
    }
    
    public void setResourcesId(Long resourcesId) {
        this.resourcesId = resourcesId;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="menu_id")

    public Menu getMenu() {
        return this.menu;
    }
    
    public void setMenu(Menu menu) {
        this.menu = menu;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="parentid")

    public Resources getResources() {
        return this.resources;
    }
    
    public void setResources(Resources resources) {
        this.resources = resources;
    }
    
    @Column(name="name", nullable=false, length=100)

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="enname", unique=true, nullable=false, length=100)

    public String getEnname() {
        return this.enname;
    }
    
    public void setEnname(String enname) {
        this.enname = enname;
    }
    
    @Column(name="resourcetype", length=2)

    public String getResourcetype() {
        return this.resourcetype;
    }
    
    public void setResourcetype(String resourcetype) {
        this.resourcetype = resourcetype;
    }
    
    @Column(name="target", length=100)

    public String getTarget() {
        return this.target;
    }
    
    public void setTarget(String target) {
        this.target = target;
    }
    
    @Column(name="isopen", nullable=false)

    public Short getIsopen() {
        return this.isopen;
    }
    
    public void setIsopen(Short isopen) {
        this.isopen = isopen;
    }
    
    @Column(name="isleaf", nullable=false)

    public Short getIsleaf() {
        return this.isleaf;
    }
    
    public void setIsleaf(Short isleaf) {
        this.isleaf = isleaf;
    }
    
    @Column(name="iscore", nullable=false)

    public Short getIscore() {
        return this.iscore;
    }
    
    public void setIscore(Short iscore) {
        this.iscore = iscore;
    }
    
    @Column(name="enable", nullable=false)

    public Short getEnable() {
        return this.enable;
    }
    
    public void setEnable(Short enable) {
        this.enable = enable;
    }
    
    @Column(name="create_time")

    public Long getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
    
    @Column(name="orderid")

    public Long getOrderid() {
        return this.orderid;
    }
    
    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }
    
    @Column(name="memo")

    public String getMemo() {
        return this.memo;
    }
    
    public void setMemo(String memo) {
        this.memo = memo;
    }
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="resources")

    public Set<RoleResources> getRoleResourceses() {
        return this.roleResourceses;
    }
    
    public void setRoleResourceses(Set<RoleResources> roleResourceses) {
        this.roleResourceses = roleResourceses;
    }
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="resources")

    public Set<Resources> getResourceses() {
        return this.resourceses;
    }
    
    public void setResourceses(Set<Resources> resourceses) {
        this.resourceses = resourceses;
    }
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="resources")

    public Set<ManagerResources> getManagerResourceses() {
        return this.managerResourceses;
    }
    
    public void setManagerResourceses(Set<ManagerResources> managerResourceses) {
        this.managerResourceses = managerResourceses;
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
package com.parkbobo.manager.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * 系统菜单
 */
@Entity
@Table(name="lq_menu")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@SequenceGenerator(name = "generator", sequenceName = "lq_menu_menu_id_seq", allocationSize = 1)
public class Menu  implements java.io.Serializable {
	private static final long serialVersionUID = 1426814830205911868L;
	/**
	 * 菜单项ID
	 */
	private Long menuId;
	/**
	 * 父菜单项ID
	 */
    private Menu menu;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单英文名称
     */
    private String enname;
    /**
     * 菜单类型，0：管理用户
     */
    private String menutype;
    /**
     * 菜单路径
     */
    private String target;
    /**
     * 节点图标
     */
    private String icon;
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
    private Set<Menu> menus = new HashSet<Menu>(0);
    private Set<Resources> resourceses = new HashSet<Resources>(0);
    private List<Menu> childrenMenu  = new ArrayList<Menu>(0);

    public Menu() {
    }
    
    public Menu(Long menuId) {
    	this.menuId = menuId;
    }

    
    public Menu(Menu menu, String name,  
			Short isleaf) {
		this.menu = menu;
		this.name = name;
		this.menutype= 0+"";
		this.isopen = 0;
		this.isleaf = isleaf;
		this.iscore = 1;
		this.enable = 1;
	}

	public Menu(Long menuId, Menu menu, String name, String enname, String menutype, String target, String icon, Short isopen, Short isleaf, Short iscore, Short enable, Long createTime, Long orderid, String memo, Set<Menu> menus, Set<Resources> resourceses) {
        this.menuId = menuId;
        this.menu = menu;
        this.name = name;
        this.enname = enname;
        this.menutype = menutype;
        this.target = target;
        this.icon = icon;
        this.isopen = isopen;
        this.isleaf = isleaf;
        this.iscore = iscore;
        this.enable = enable;
        this.createTime = createTime;
        this.orderid = orderid;
        this.memo = memo;
        this.menus = menus;
        this.resourceses = resourceses;
    }

   
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
    @Column(name="menu_id", unique=true, nullable=false)

    public Long getMenuId() {
        return this.menuId;
    }
    
    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="parentid")

    public Menu getMenu() {
        return this.menu;
    }
    
    public void setMenu(Menu menu) {
        this.menu = menu;
    }
    
    @Column(name="name", length=50)

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
    
    @Column(name="menutype", length=2)

    public String getMenutype() {
        return this.menutype;
    }
    
    public void setMenutype(String menutype) {
        this.menutype = menutype;
    }
    
    @Column(name="target")

    public String getTarget() {
        return this.target;
    }
    
    public void setTarget(String target) {
        this.target = target;
    }
    
    @Column(name="icon")

    public String getIcon() {
        return this.icon;
    }
    
    public void setIcon(String icon) {
        this.icon = icon;
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
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="menu")

    public Set<Menu> getMenus() {
        return this.menus;
    }
    
    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="menu")

    public Set<Resources> getResourceses() {
        return this.resourceses;
    }
    
    public void setResourceses(Set<Resources> resourceses) {
        this.resourceses = resourceses;
    }
    @Transient
	public List<Menu> getChildrenMenu() {
		return childrenMenu;
	}

	public void setChildrenMenu(List<Menu> childrenMenu) {
		this.childrenMenu = childrenMenu;
	}
   
}
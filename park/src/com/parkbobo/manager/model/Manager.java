package com.parkbobo.manager.model;

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
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * 管理用户
 */
@Entity
@Table(name="lq_manager", uniqueConstraints = {@UniqueConstraint(columnNames="email"), @UniqueConstraint(columnNames="username")})
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@SequenceGenerator(name = "generator", sequenceName = "lq_manager_user_id_seq", allocationSize = 1)
public class Manager  implements java.io.Serializable {

	private static final long serialVersionUID = 8732493787571237425L;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 用户组
	 */
    private ManagerGroup managerGroup;
    /**
     * 部门
     */
    private Department department;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 用户名
     */
    private String username;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 密码
     */
    private String password;
    /**
     * 最后登录时间
     */
    private Long lastLoginTime;
    /**
     * 最后登录IP
     */
    private String lastLoginIp;
    /**
     * 登录次数
     */
    private Long loginCount;
    /**
     * 重置密码KEY
     */
    private String resetKey;
    /**
     * 注册时间
     */
    private Long registerTime;
    /**
     * 注册IP
     */
    private String registerIp;
    /**
     * 激活状态，0：未激活；1：激活
     */
    private Short activation;
    /**
     * 激活码
     */
    private String activationCode;
    /**
     * 真实姓名
     */
    private String realname;
    /**
     * QQ
     */
    private String qq;
    /**
     * 电话
     */
    private String phone;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 头像
     */
    private String userImg;
    /**
     * 状态，0：正常；1：锁定
     */
    private Short status;
    private String memo;
    /**
     * 地区
     * */
    private String area;
    private Set<ManagerResources> managerResourceses = new HashSet<ManagerResources>(0);
    private Set<ManagerRole> managerRoles = new HashSet<ManagerRole>(0);

    public Manager() {
    }

    public Manager(Integer userId) {
    }
    
    public Manager(Long userId, ManagerGroup managerGroup, Department department, String email, String username, String nickname, String password, Long lastLoginTime, String lastLoginIp, Long loginCount, String resetKey, Long registerTime, String registerIp, Short activation, String activationCode, String realname, String qq, String phone, String mobile, String userImg, Short status, String memo, Set<ManagerResources> managerResourceses, Set<ManagerRole> managerRoles) {
        this.userId = userId;
        this.managerGroup = managerGroup;
        this.department = department;
        this.email = email;
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.lastLoginTime = lastLoginTime;
        this.lastLoginIp = lastLoginIp;
        this.loginCount = loginCount;
        this.resetKey = resetKey;
        this.registerTime = registerTime;
        this.registerIp = registerIp;
        this.activation = activation;
        this.activationCode = activationCode;
        this.realname = realname;
        this.qq = qq;
        this.phone = phone;
        this.mobile = mobile;
        this.userImg = userImg;
        this.status = status;
        this.memo = memo;
        this.managerResourceses = managerResourceses;
        this.managerRoles = managerRoles;
    }

   
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
    @Column(name="user_id", unique=true, nullable=false)

    public Long getUserId() {
        return this.userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="groupid")

    public ManagerGroup getManagerGroup() {
        return this.managerGroup;
    }
    
    public void setManagerGroup(ManagerGroup managerGroup) {
        this.managerGroup = managerGroup;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="departmentid")

    public Department getDepartment() {
        return this.department;
    }
    
    public void setDepartment(Department department) {
        this.department = department;
    }
    
    @Column(name="email", unique=true, nullable=false, length=50)

    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Column(name="username", unique=true, nullable=false, length=50)

    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    @Column(name="nickname", length=50)

    public String getNickname() {
        return this.nickname;
    }
    
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    
    @Column(name="password", nullable=false, length=100)

    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Column(name="last_login_time")

    public Long getLastLoginTime() {
        return this.lastLoginTime;
    }
    
    public void setLastLoginTime(Long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
    
    @Column(name="last_login_ip", length=50)

    public String getLastLoginIp() {
        return this.lastLoginIp;
    }
    
    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }
    
    @Column(name="login_count")

    public Long getLoginCount() {
        return this.loginCount;
    }
    
    public void setLoginCount(Long loginCount) {
        this.loginCount = loginCount;
    }
    
    @Column(name="reset_key", length=32)

    public String getResetKey() {
        return this.resetKey;
    }
    
    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }
    
    @Column(name="register_time")

    public Long getRegisterTime() {
        return this.registerTime;
    }
    
    public void setRegisterTime(Long registerTime) {
        this.registerTime = registerTime;
    }
    
    @Column(name="register_ip", length=50)

    public String getRegisterIp() {
        return this.registerIp;
    }
    
    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp;
    }
    
    @Column(name="activation", nullable=false)

    public Short getActivation() {
        return this.activation;
    }
    
    public void setActivation(Short activation) {
        this.activation = activation;
    }
    
    @Column(name="activation_code", length=32)

    public String getActivationCode() {
        return this.activationCode;
    }
    
    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }
    
    @Column(name="realname", length=50)

    public String getRealname() {
        return this.realname;
    }
    
    public void setRealname(String realname) {
        this.realname = realname;
    }
    
    @Column(name="qq", length=20)

    public String getQq() {
        return this.qq;
    }
    
    public void setQq(String qq) {
        this.qq = qq;
    }
    
    @Column(name="phone", length=20)

    public String getPhone() {
        return this.phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    @Column(name="mobile", length=20)

    public String getMobile() {
        return this.mobile;
    }
    
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    
    @Column(name="user_img")

    public String getUserImg() {
        return this.userImg;
    }
    
    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }
    
    @Column(name="status", nullable=false)

    public Short getStatus() {
        return this.status;
    }
    
    public void setStatus(Short status) {
        this.status = status;
    }
    
    @Column(name="memo")

    public String getMemo() {
        return this.memo;
    }
    
    public void setMemo(String memo) {
        this.memo = memo;
    }
    
    @Column(name="area")
    public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="manager")

    public Set<ManagerResources> getManagerResourceses() {
        return this.managerResourceses;
    }
    
    public void setManagerResourceses(Set<ManagerResources> managerResourceses) {
        this.managerResourceses = managerResourceses;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="manager")

    public Set<ManagerRole> getManagerRoles() {
        return this.managerRoles;
    }
    
    public void setManagerRoles(Set<ManagerRole> managerRoles) {
        this.managerRoles = managerRoles;
    }
   








}
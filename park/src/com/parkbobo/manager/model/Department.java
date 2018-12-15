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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * 部门表
 * @author LH
 *
 */
@Entity
@Table(name="lq_department")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@SequenceGenerator(name = "generator", sequenceName = "lq_department_departmentid_seq", allocationSize = 1)
public class Department  implements java.io.Serializable {

	private static final long serialVersionUID = -8540170925428621755L;
	/**
	 * 部分ID
	 */
	private Long departmentid;
	/**
	 * 父部门ID
	 */
    private Department department;
    /**
	 * 部门名称
	 */
    private String name;
    /**
	 * 创建时间
	 */
    private Long createTime;
    /**
	 * 部门描述
	 */
    private String description;
    /**
	 * 是否叶子节点；0：否；1：是
	 */
    private Short isleaf;
    /**
	 * 排序
	 */
    private Long orderid;
    private String memo;
    private Set<Manager> managers = new HashSet<Manager>(0);
    private Set<Department> departments = new HashSet<Department>(0);

    private Date createTimeToDate;
    
    public Department() {
    }

    public Department(Long departmentid) {
        this.departmentid = departmentid;
    }
    
    public Department(Long departmentid, Department department, String name, Long createTime, String description, Short isleaf, Long orderid, String memo, Set<Manager> managers, Set<Department> departments) {
        this.departmentid = departmentid;
        this.department = department;
        this.name = name;
        this.createTime = createTime;
        this.description = description;
        this.isleaf = isleaf;
        this.orderid = orderid;
        this.memo = memo;
        this.managers = managers;
        this.departments = departments;
    }

   
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
    @Column(name="departmentid", unique=true, nullable=false)

    public Long getDepartmentid() {
        return this.departmentid;
    }
    
    public void setDepartmentid(Long departmentid) {
        this.departmentid = departmentid;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="parentid")

    public Department getDepartment() {
        return this.department;
    }
    
    public void setDepartment(Department department) {
        this.department = department;
    }
    
    @Column(name="name", length=100)

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="create_time")

    public Long getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
    
    @Column(name="description")

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Column(name="isleaf", nullable=false)

    public Short getIsleaf() {
        return this.isleaf;
    }
    
    public void setIsleaf(Short isleaf) {
        this.isleaf = isleaf;
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
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="department")

    public Set<Manager> getManagers() {
        return this.managers;
    }
    
    public void setManagers(Set<Manager> managers) {
        this.managers = managers;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="department")

    public Set<Department> getDepartments() {
        return this.departments;
    }
    
    public void setDepartments(Set<Department> departments) {
        this.departments = departments;
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
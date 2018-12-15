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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * 用户分组
 */
@Entity
@Table(name="lq_manager_group")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@SequenceGenerator(name = "generator", sequenceName = "lq_manager_group_groupid_seq", allocationSize = 1)
public class ManagerGroup  implements java.io.Serializable {
	private static final long serialVersionUID = -3667926718051933423L;
	/**
	 * 分组ID
	 */
	private Long groupid;
	/**
	 * 名称
	 */
	private String name;
    private String memo;
    private Set<Manager> managers = new HashSet<Manager>(0);

    public ManagerGroup() {
    }

    public ManagerGroup(Long groupid) {
        this.groupid = groupid;
    }
    
    public ManagerGroup(Long groupid, String name, String memo, Set<Manager> managers) {
        this.groupid = groupid;
        this.name = name;
        this.memo = memo;
        this.managers = managers;
    }

   
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
    @Column(name="groupid", unique=true, nullable=false)

    public Long getGroupid() {
        return this.groupid;
    }
    
    public void setGroupid(Long groupid) {
        this.groupid = groupid;
    }
    
    @Column(name="name", length=100)

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="memo")

    public String getMemo() {
        return this.memo;
    }
    
    public void setMemo(String memo) {
        this.memo = memo;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="managerGroup")

    public Set<Manager> getManagers() {
        return this.managers;
    }
    
    public void setManagers(Set<Manager> managers) {
        this.managers = managers;
    }
   








}
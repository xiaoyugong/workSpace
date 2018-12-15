package com.parkbobo.groundlock.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Entity
@Table(name = "lq_maintenance_components")
@SequenceGenerator(name = "generator", sequenceName = "lq_maintenance_components_componentid_seq", allocationSize = 1)
public class MaintenanceComponents implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1666158922749234699L;
	private Long componentid;
	private MaintenanceRecords maintenanceRecords;
	private String name;
	private String model;
	private Integer number;
	private String memo;


	public MaintenanceComponents() {
	}

	public MaintenanceComponents(Long componentid) {
		this.componentid = componentid;
	}

	public MaintenanceComponents(Long componentid,
			MaintenanceRecords maintenanceRecords, String name,
			String model, Integer number, String memo) {
		this.componentid = componentid;
		this.maintenanceRecords = maintenanceRecords;
		this.name = name;
		this.model = model;
		this.number = number;
		this.memo = memo;
	}

	@Id 
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
	@Column(name = "componentid", unique = true, nullable = false)
	public Long getComponentid() {
		return this.componentid;
	}

	public void setComponentid(Long componentid) {
		this.componentid = componentid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recordid")
	public MaintenanceRecords getMaintenanceRecords() {
		return maintenanceRecords;
	}

	public void setMaintenanceRecords(MaintenanceRecords maintenanceRecords) {
		this.maintenanceRecords = maintenanceRecords;
	}

	@Column(name = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "model")
	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Column(name = "number")
	public Integer getNumber() {
		return this.number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	@Column(name = "memo")
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	

}

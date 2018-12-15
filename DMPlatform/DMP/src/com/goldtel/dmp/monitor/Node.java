package com.goldtel.dmp.monitor;

/**
 * Node 节点实体类，用于保存node信息
 * @author gxy
 * 2015-12-16
 */
public class Node {
	
	String rack;
    String state;
    String id;
    String nodeHostName;
    String nodeHTTPAddress;
    long lastHealthUpdate;
    String version;
    String healthReport;
    int numContainers;
    long usedMemoryMB;
    long availMemoryMB;
    int usedVirtualCores;
    int availableVirtualCores;
    
	public String getRack() {
		return rack;
	}
	public void setRack(String rack) {
		this.rack = rack;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNodeHostName() {
		return nodeHostName;
	}
	public void setNodeHostName(String nodeHostName) {
		this.nodeHostName = nodeHostName;
	}
	public String getNodeHTTPAddress() {
		return nodeHTTPAddress;
	}
	public void setNodeHTTPAddress(String nodeHTTPAddress) {
		this.nodeHTTPAddress = nodeHTTPAddress;
	}
	public long getLastHealthUpdate() {
		return lastHealthUpdate;
	}
	public void setLastHealthUpdate(long lastHealthUpdate) {
		this.lastHealthUpdate = lastHealthUpdate;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getHealthReport() {
		return healthReport;
	}
	public void setHealthReport(String healthReport) {
		this.healthReport = healthReport;
	}
	public int getNumContainers() {
		return numContainers;
	}
	public void setNumContainers(int numContainers) {
		this.numContainers = numContainers;
	}
	public long getUsedMemoryMB() {
		return usedMemoryMB;
	}
	public void setUsedMemoryMB(long usedMemoryMB) {
		this.usedMemoryMB = usedMemoryMB;
	}
	public long getAvailMemoryMB() {
		return availMemoryMB;
	}
	public void setAvailMemoryMB(long availMemoryMB) {
		this.availMemoryMB = availMemoryMB;
	}
	public int getUsedVirtualCores() {
		return usedVirtualCores;
	}
	public void setUsedVirtualCores(int usedVirtualCores) {
		this.usedVirtualCores = usedVirtualCores;
	}
	public int getAvailableVirtualCores() {
		return availableVirtualCores;
	}
	public void setAvailableVirtualCores(int availableVirtualCores) {
		this.availableVirtualCores = availableVirtualCores;
	}
}

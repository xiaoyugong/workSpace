package com.goldtel.dmp.monitor;

/**
 * 集群资源度量实体类，用于保存从集群获得的资源度量数据
 * @author gxy
 * 2015-12-14
 */
public class ClusterMetrics {

	private int appsSubmitted;
	private int appsCompleted;
	private int appsPending;
	private int appsRunning;
	private int appsFailed;
	private int appsKilled;
	private long reservedMB;
	private long availableMB;
	private long allocatedMB;
	private int reservedVirtualCores;
	private int availableVirtualCores;
	private int allocatedVirtualCores;
	private int containersAllocated;
	private int containersReserved;
	private int containersPending;
	private long totalMB;
    private int totalVirtualCores;
    private int totalNodes;
    private int lostNodes;
    private int unhealthyNodes;
    private int decommissionedNodes;
    private int rebootedNodes;
    private int activeNodes;
    
	public int getAppsSubmitted() {
		return appsSubmitted;
	}
	public void setAppsSubmitted(int appsSubmitted) {
		this.appsSubmitted = appsSubmitted;
	}
	public int getAppsCompleted() {
		return appsCompleted;
	}
	public void setAppsCompleted(int appsCompleted) {
		this.appsCompleted = appsCompleted;
	}
	public int getAppsPending() {
		return appsPending;
	}
	public void setAppsPending(int appsPending) {
		this.appsPending = appsPending;
	}
	public int getAppsRunning() {
		return appsRunning;
	}
	public void setAppsRunning(int appsRunning) {
		this.appsRunning = appsRunning;
	}
	public int getAppsFailed() {
		return appsFailed;
	}
	public void setAppsFailed(int appsFailed) {
		this.appsFailed = appsFailed;
	}
	public int getAppsKilled() {
		return appsKilled;
	}
	public void setAppsKilled(int appsKilled) {
		this.appsKilled = appsKilled;
	}
	public long getReservedMB() {
		return reservedMB;
	}
	public void setReservedMB(long reservedMB) {
		this.reservedMB = reservedMB;
	}
	public long getAvailableMB() {
		return availableMB;
	}
	public void setAvailableMB(long availableMB) {
		this.availableMB = availableMB;
	}
	public long getAllocatedMB() {
		return allocatedMB;
	}
	public void setAllocatedMB(long allocatedMB) {
		this.allocatedMB = allocatedMB;
	}
	public int getReservedVirtualCores() {
		return reservedVirtualCores;
	}
	public void setReservedVirtualCores(int reservedVirtualCores) {
		this.reservedVirtualCores = reservedVirtualCores;
	}
	public int getAvailableVirtualCores() {
		return availableVirtualCores;
	}
	public void setAvailableVirtualCores(int availableVirtualCores) {
		this.availableVirtualCores = availableVirtualCores;
	}
	public int getAllocatedVirtualCores() {
		return allocatedVirtualCores;
	}
	public void setAllocatedVirtualCores(int allocatedVirtualCores) {
		this.allocatedVirtualCores = allocatedVirtualCores;
	}
	public int getContainersAllocated() {
		return containersAllocated;
	}
	public void setContainersAllocated(int containersAllocated) {
		this.containersAllocated = containersAllocated;
	}
	public int getContainersReserved() {
		return containersReserved;
	}
	public void setContainersReserved(int containersReserved) {
		this.containersReserved = containersReserved;
	}
	public int getContainersPending() {
		return containersPending;
	}
	public void setContainersPending(int containersPending) {
		this.containersPending = containersPending;
	}
	public long getTotalMB() {
		return totalMB;
	}
	public void setTotalMB(long totalMB) {
		this.totalMB = totalMB;
	}
	public int getTotalVirtualCores() {
		return totalVirtualCores;
	}
	public void setTotalVirtualCores(int totalVirtualCores) {
		this.totalVirtualCores = totalVirtualCores;
	}
	public int getTotalNodes() {
		return totalNodes;
	}
	public void setTotalNodes(int totalNodes) {
		this.totalNodes = totalNodes;
	}
	public int getLostNodes() {
		return lostNodes;
	}
	public void setLostNodes(int lostNodes) {
		this.lostNodes = lostNodes;
	}
	public int getUnhealthyNodes() {
		return unhealthyNodes;
	}
	public void setUnhealthyNodes(int unhealthyNodes) {
		this.unhealthyNodes = unhealthyNodes;
	}
	public int getDecommissionedNodes() {
		return decommissionedNodes;
	}
	public void setDecommissionedNodes(int decommissionedNodes) {
		this.decommissionedNodes = decommissionedNodes;
	}
	public int getRebootedNodes() {
		return rebootedNodes;
	}
	public void setRebootedNodes(int rebootedNodes) {
		this.rebootedNodes = rebootedNodes;
	}
	public int getActiveNodes() {
		return activeNodes;
	}
	public void setActiveNodes(int activeNodes) {
		this.activeNodes = activeNodes;
	}
}

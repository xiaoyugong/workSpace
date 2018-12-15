package com.goldtel.dmp.monitor;

/**
 * Application 集群中的应用实体类，用于保存application信息
 * @author gxy
 * 2015-12-15
 */
public class Application {
	
	String id;
    String user;
    String name;
    String queue;
    String state;
    String finalStatus;
    float progress;
    String trackingUI;
    String trackingUrl;
    String diagnostics;
    long clusterId;
    String applicationType;
    String applicationTags;
    long startedTime;
    long finishedTime;
    long elapsedTime;
    String amContainerLogs;
    String amHostHttpAddress;
    long allocatedMB;
    int allocatedVCores;
    int runningContainers;
    long memorySeconds;
    long vcoreSeconds;
    long preemptedResourceMB;
    int preemptedResourceVCores;
    int numNonAMContainerPreempted;
    int numAMContainerPreempted;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getQueue() {
		return queue;
	}
	public void setQueue(String queue) {
		this.queue = queue;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getFinalStatus() {
		return finalStatus;
	}
	public void setFinalStatus(String finalStatus) {
		this.finalStatus = finalStatus;
	}
	public float getProgress() {
		return progress;
	}
	public void setProgress(float progress) {
		this.progress = progress;
	}
	public String getTrackingUI() {
		return trackingUI;
	}
	public void setTrackingUI(String trackingUI) {
		this.trackingUI = trackingUI;
	}
	public String getTrackingUrl() {
		return trackingUrl;
	}
	public void setTrackingUrl(String trackingUrl) {
		this.trackingUrl = trackingUrl;
	}
	public String getDiagnostics() {
		return diagnostics;
	}
	public void setDiagnostics(String diagnostics) {
		this.diagnostics = diagnostics;
	}
	public long getClusterId() {
		return clusterId;
	}
	public void setClusterId(long clusterId) {
		this.clusterId = clusterId;
	}
	public String getApplicationType() {
		return applicationType;
	}
	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}
	public String getApplicationTags() {
		return applicationTags;
	}
	public void setApplicationTags(String applicationTags) {
		this.applicationTags = applicationTags;
	}
	public long getStartedTime() {
		return startedTime;
	}
	public void setStartedTime(long startedTime) {
		this.startedTime = startedTime;
	}
	public long getFinishedTime() {
		return finishedTime;
	}
	public void setFinishedTime(long finishedTime) {
		this.finishedTime = finishedTime;
	}
	public long getElapsedTime() {
		return elapsedTime;
	}
	public void setElapsedTime(long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}
	public String getAmContainerLogs() {
		return amContainerLogs;
	}
	public void setAmContainerLogs(String amContainerLogs) {
		this.amContainerLogs = amContainerLogs;
	}
	public String getAmHostHttpAddress() {
		return amHostHttpAddress;
	}
	public void setAmHostHttpAddress(String amHostHttpAddress) {
		this.amHostHttpAddress = amHostHttpAddress;
	}
	public long getAllocatedMB() {
		return allocatedMB;
	}
	public void setAllocatedMB(long allocatedMB) {
		this.allocatedMB = allocatedMB;
	}
	public int getAllocatedVCores() {
		return allocatedVCores;
	}
	public void setAllocatedVCores(int allocatedVCores) {
		this.allocatedVCores = allocatedVCores;
	}
	public int getRunningContainers() {
		return runningContainers;
	}
	public void setRunningContainers(int runningContainers) {
		this.runningContainers = runningContainers;
	}
	public long getMemorySeconds() {
		return memorySeconds;
	}
	public void setMemorySeconds(long memorySeconds) {
		this.memorySeconds = memorySeconds;
	}
	public long getVcoreSeconds() {
		return vcoreSeconds;
	}
	public void setVcoreSeconds(long vcoreSeconds) {
		this.vcoreSeconds = vcoreSeconds;
	}
	public long getPreemptedResourceMB() {
		return preemptedResourceMB;
	}
	public void setPreemptedResourceMB(long preemptedResourceMB) {
		this.preemptedResourceMB = preemptedResourceMB;
	}
	public int getPreemptedResourceVCores() {
		return preemptedResourceVCores;
	}
	public void setPreemptedResourceVCores(int preemptedResourceVCores) {
		this.preemptedResourceVCores = preemptedResourceVCores;
	}
	public int getNumNonAMContainerPreempted() {
		return numNonAMContainerPreempted;
	}
	public void setNumNonAMContainerPreempted(int numNonAMContainerPreempted) {
		this.numNonAMContainerPreempted = numNonAMContainerPreempted;
	}
	public int getNumAMContainerPreempted() {
		return numAMContainerPreempted;
	}
	public void setNumAMContainerPreempted(int numAMContainerPreempted) {
		this.numAMContainerPreempted = numAMContainerPreempted;
	}
}

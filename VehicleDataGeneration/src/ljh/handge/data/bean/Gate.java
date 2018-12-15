package ljh.handge.data.bean;

public class Gate {
	private String gateId;
	private String nextGates;
	private String distances;
	private String type;
	private String lonlat;
	private String speedlimit;
	
	public String getSpeedlimit() {
		return speedlimit;
	}
	public void setSpeedlimit(String speedlimit) {
		this.speedlimit = speedlimit;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLonlat() {
		return lonlat;
	}
	public void setLonlat(String lonlat) {
		this.lonlat = lonlat;
	}
	public String getDistances() {
		return distances;
	}
	public void setDistances(String distances) {
		this.distances = distances;
	}
	public String getGateId() {
		return gateId;
	}
	public void setGateId(String gateId) {
		this.gateId = gateId;
	}
	public String getNextGates() {
		return nextGates;
	}
	public void setNextGates(String nextGates) {
		this.nextGates = nextGates;
	}
	
	
}

package ljh.handge.data.bean;

public class Vehicle {
	
	private String plateNumber;
	private String type;
	private String color;
	private String brand;
	private String speed;
	private String currentGate;
	private String previousGate;
	private String desireTime;
	private String illegalTimes;
	
	public String getPreviousGate() {
		return previousGate;
	}

	public void setPreviousGate(String previousGate) {
		this.previousGate = previousGate;
	}
	
	public String getDesireTime() {
		return desireTime;
	}


	public void setDesireTime(String desireTime) {
		this.desireTime = desireTime;
	}

	public String getIllegalTimes() {
		return illegalTimes;
	}


	public void setIllegalTimes(String illegalTimes) {
		this.illegalTimes = illegalTimes;
	}


	public String getPlateNumber() {
		return plateNumber;
	}


	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getColor() {
		return color;
	}


	public void setColor(String color) {
		this.color = color;
	}


	public String getBrand() {
		return brand;
	}


	public void setBrand(String brand) {
		this.brand = brand;
	}


	public String getSpeed() {
		return speed;
	}


	public void setSpeed(String speed) {
		this.speed = speed;
	}


	public String getCurrentGate() {
		return currentGate;
	}


	public void setCurrentGate(String gate) {
		this.currentGate = gate;
	}


	@Override
	public String toString() {
		return (this.currentGate + "," + this.previousGate + "," + this.plateNumber + "," + this.type + "," + this.color + "," + this.brand + "," + this.speed + "," + this.desireTime + "," + this.illegalTimes);
	}
	
	public String toArchive() {
		return (this.currentGate + "," + this.plateNumber + "," + this.type + "," + this.color + "," + this.brand + "," + this.speed + "," + this.desireTime);
		
	}
}

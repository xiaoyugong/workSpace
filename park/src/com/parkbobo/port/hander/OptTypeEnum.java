package com.parkbobo.port.hander;

public enum OptTypeEnum {

	
	AUTHENTER("车主入场",(short)1),AUTHLEAVE("车主入场",(short)2),AUTHBIND("车主绑定",(short)3);
	private String value;
	private Short key;
	
	OptTypeEnum(String value,Short key){
		this.value = value;
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Short getKey() {
		return key;
	}

	public void setKey(Short key) {
		this.key = key;
	}
	
	
	
	
}

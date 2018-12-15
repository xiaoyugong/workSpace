package com.weixin.msg;

import java.util.Date;


public class EventMsg extends BaseMsg {

	private String event;
	private String eventKey;
	/**
	 * 如果用户还未关注公众号，则用户可以关注公众号，关注后微信会将带场景值关注事件推送给开发者。
如果用户已经关注公众号，则微信会将带场景值扫描事件推送给开发者
	 */
	private String ticket;
	private String latitude;
	private String longitude;
	private String precision;
	public EventMsg(){
		 super.setMsgType("event");
		 super.setCreateTime(new Date().getTime()+"");
	 }
	
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getEventKey() {
		return eventKey;
	}
	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}


	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getPrecision() {
		return precision;
	}

	public void setPrecision(String precision) {
		this.precision = precision;
	}

	@Override
	public String toString() {
		return "EventMsg [event=" + event + ", eventKey=" + eventKey
				+ ", latitude=" + latitude + ", longitude=" + longitude
				+ ", precision=" + precision + ", ticket=" + ticket
				+ ", getCreateTime()=" + getCreateTime()
				+ ", getFromUserName()=" + getFromUserName() + ", getMsgId()="
				+ getMsgId() + ", getMsgType()=" + getMsgType()
				+ ", getToUserName()=" + getToUserName() + "]";
	}
	
	
}

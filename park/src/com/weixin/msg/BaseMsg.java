package com.weixin.msg;

public class BaseMsg {
	
	 /**
	 * 
	 */
	private String toUserName;
	 /**
	 * 发送方帐号
	 */
	private String fromUserName;
	 /**
	 * 消息创建时间(整形)
	 */
	private String createTime;
	 private String msgType;
	 private String  msgId;
	 
	 
	
	public String getToUserName() {
		return toUserName;
	}
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	 
	 
}

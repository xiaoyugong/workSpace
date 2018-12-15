package com.weixin.returnMsg;

import java.util.Date;

public class ReturnTextMsg extends ReturnBaseMsg{

	private String content;

	
	
	
	public ReturnTextMsg(){
		 super.setMsgType("text");
		 super.setCreateTime(new Date().getTime()+"");
	 }
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "TextMsg [content=" + content + ", getCreateTime()="
				+ getCreateTime() + ", getFromUserName()=" + getFromUserName()
				+ ", getMsgId()=" + getMsgId() + ", getMsgType()="
				+ getMsgType() + ", getToUserName()=" + getToUserName()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
	
}

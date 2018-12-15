package com.weixin.msg;

import java.util.Date;

public class TextMsg extends BaseMsg{

	private String content;

	
	
	
	public TextMsg(){
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

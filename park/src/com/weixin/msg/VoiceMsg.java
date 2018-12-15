package com.weixin.msg;

import java.util.Date;

public class VoiceMsg extends BaseMsg{

	private String mediaId;
	private String format;
	
	public VoiceMsg(){
		 super.setMsgType("voice");
		 super.setCreateTime(new Date().getTime()+"");
	 }
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	
}

package com.weixin.msg;

import java.util.Date;

public class VideoMsg extends BaseMsg{

	private String mediaId;
	private String thumbMediaId;
	
	public VideoMsg(){
		 super.setMsgType("video");
		 super.setCreateTime(new Date().getTime()+"");
	 }
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public String getThumbMediaId() {
		return thumbMediaId;
	}
	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}
	@Override
	public String toString() {
		return "VideoMsg [mediaId=" + mediaId + ", thumbMediaId="
				+ thumbMediaId + ", getCreateTime()=" + getCreateTime()
				+ ", getFromUserName()=" + getFromUserName() + ", getMsgId()="
				+ getMsgId() + ", getMsgType()=" + getMsgType()
				+ ", getToUserName()=" + getToUserName() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
}

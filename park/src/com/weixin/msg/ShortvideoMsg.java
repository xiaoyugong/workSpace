package com.weixin.msg;

import java.util.Date;

public class ShortvideoMsg extends BaseMsg{

	
	private String mediaId;
	private String thumbMediaId;
	
	
	public ShortvideoMsg(){
		 super.setMsgType("shortvideo");
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
}

package com.weixin.msg;

import java.util.Date;

public class ImageMsg extends BaseMsg{

	private String picUrl;
	private String mediaId;
	
	
	public ImageMsg(){
		 super.setMsgType("image");
		 super.setCreateTime(new Date().getTime()+"");
	 }
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
	
}

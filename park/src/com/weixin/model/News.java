package com.weixin.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 作者:卢鸶
 * 时间:2015-4-11
 * 描述:上传图文消息素材
 */
@Entity
@Table(name="weixin_news")
public class News implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 112323;
	private String media_id;
	/**
	 * 图文消息缩略图的media_id
	 */
	private String thumb_media_id;
	/**
	 * 图文消息的作者
	 */
	private String author;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 在图文消息页面点击“阅读原文”后的页面
	 */
	private String content_source_url;
	/**
	 * 图文消息页面的内容，支持HTML标签
	 */
	private String content;
	/**
	 * 图文消息的描述
	 */
	private String digest;
	/**
	 * 是否显示封面，1为显示，0为不显示
	 */
	private String show_cover_pic;
	
	
	/**
	 *是否发送 
	 */
	private String isSend;
	
	
	
	public String getThumb_media_id() {
		return thumb_media_id;
	}
	@Id
	public String getMedia_id() {
		return media_id;
	}
	public void setMedia_id(String mediaId) {
		media_id = mediaId;
	}
	public void setThumb_media_id(String thumbMediaId) {
		thumb_media_id = thumbMediaId;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent_source_url() {
		return content_source_url;
	}
	public void setContent_source_url(String contentSourceUrl) {
		content_source_url = contentSourceUrl;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDigest() {
		return digest;
	}
	public void setDigest(String digest) {
		this.digest = digest;
	}
	public String getShow_cover_pic() {
		return show_cover_pic;
	}
	public void setShow_cover_pic(String showCoverPic) {
		show_cover_pic = showCoverPic;
	}
	@Override
	public String toString() {
		return "UpdateNews [author=" + author + ", content=" + content
				+ ", content_source_url=" + content_source_url + ", digest="
				+ digest + ", media_id=" + media_id + ", show_cover_pic=" + show_cover_pic
				+ ", thumb_media_id=" + thumb_media_id + ", title=" + title
				+ "]";
	}
	@Column(name="is_send")
	public String getIsSend() {
		return isSend;
	}
	public void setIsSend(String isSend) {
		this.isSend = isSend;
	}
	
	
	
	
}

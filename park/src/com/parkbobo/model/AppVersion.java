package com.parkbobo.model;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * APP版本管理
 * @author LH
 *
 */
@Entity
@Table(name="lq_app_version")
public class AppVersion  implements java.io.Serializable {

	private static final long serialVersionUID = 5012117233534833486L;
	
	private AppVersionId id;
	/**
	 * 渠道
	 */
	private AppChannel appChannel;
	/**
	 * 文件绝对路径
	 */
    private String attached;
    /**
	 * 上传时间
	 */
    private Long posttime;
    /**
	 * 下载次数
	 */
    private Long downloadcount;
    /**
	 * 更新内容说明
	 */
    private String content;
    /**
	 * 是否删除，0：否，1：是
	 */
    private Short isDel;
    /**
     * 是否需要强制升级
     */
    private String needUpdate;
    
    private String memo;
    /**
     * APP类型，0：车主APP;1:物业APP
     */
    private Integer category;

    private Date posttimeToDate;
    
    public AppVersion() {
    }

    public AppVersion(AppVersionId id, AppChannel appChannel) {
        this.id = id;
        this.appChannel = appChannel;
    }
    
    public AppVersion(AppVersionId id, AppChannel appChannel, String attached, Long posttime, Long downloadcount, String content, Short isDel, String needUpdate, String memo,Integer category) {
        this.id = id;
        this.appChannel = appChannel;
        this.attached = attached;
        this.posttime = posttime;
        this.downloadcount = downloadcount;
        this.content = content;
        this.isDel = isDel;
        this.needUpdate = needUpdate;
        this.memo = memo;
        this.category = category;
    }
    @EmbeddedId
    @AttributeOverrides( {
        @AttributeOverride(name="versioncode", column=@Column(name="versioncode", nullable=false) ), 
        @AttributeOverride(name="channelid", column=@Column(name="channelid", nullable=false) ) } )
    public AppVersionId getId() {
        return this.id;
    }
    
    public void setId(AppVersionId id) {
        this.id = id;
    }
    @ManyToOne(fetch=FetchType.LAZY)
		@JoinColumn(name="channelid", nullable=false, insertable=false, updatable=false)
	public AppChannel getAppChannel() {
		return appChannel;
	}
	
	public void setAppChannel(AppChannel appChannel) {
		this.appChannel = appChannel;
	}
    
    @Column(name="attached")

    public String getAttached() {
        return this.attached;
    }
    
    public void setAttached(String attached) {
        this.attached = attached;
    }
    
    @Column(name="posttime")

    public Long getPosttime() {
        return this.posttime;
    }
    
    public void setPosttime(Long posttime) {
        this.posttime = posttime;
    }
    
    @Column(name="downloadcount")

    public Long getDownloadcount() {
        return this.downloadcount;
    }
    
    public void setDownloadcount(Long downloadcount) {
        this.downloadcount = downloadcount;
    }
    
    @Column(name="content")

    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    @Column(name="is_del")

    public Short getIsDel() {
        return this.isDel;
    }
    
    public void setIsDel(Short isDel) {
        this.isDel = isDel;
    }
    
    @Column(name="memo")

    public String getMemo() {
        return this.memo;
    }
    
    public void setMemo(String memo) {
        this.memo = memo;
    }
    
    
	@Column(name="need_update")
	public String getNeedUpdate() {
		return needUpdate;
	}

	public void setNeedUpdate(String needUpdate) {
		this.needUpdate = needUpdate;
	}
	
	@Transient
	public Date getPosttimeToDate() {
		if(posttime != null)
		{
			posttimeToDate = new Date(posttime);
		}
		return posttimeToDate;
	}

	public void setPosttimeToDate(Date posttimeToDate) {
		this.posttimeToDate = posttimeToDate;
	}
	@Column(name="category")
	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}
}
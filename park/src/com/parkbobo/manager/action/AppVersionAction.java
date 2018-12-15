package com.parkbobo.manager.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.parkbobo.model.AppChannel;
import com.parkbobo.model.AppVersion;
import com.parkbobo.model.AppVersionId;
import com.parkbobo.service.AppChannelService;
import com.parkbobo.service.AppVersionService;
import com.parkbobo.utils.Configuration;
import com.parkbobo.utils.MyPrintUtil;
import com.parkbobo.utils.PageBean;

@Controller("appVersionAction")
@Scope("prototype")
public class AppVersionAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 939063388467002103L;
	private AppVersion appVersion;
	private PageBean<AppVersion> appVersionPage;
	private File upload;
	private String uploadFileName;
	private String content;
	private String needUpdate;
	private Long channelid;
	private Long versioncode; 

	private List<AppChannel> appChannels;
	
	@Resource(name="appVersionService")
	private AppVersionService appVersionService;
	@Resource(name="appChannelService")
	private AppChannelService appChannelService;
	private Integer category;
	
	/**
	 * APP添加
	 * @return
	 */
	public String add()
	{
		if(getMethod().equals("add"))
		{
			FileOutputStream fos = null;
	        FileInputStream fis = null;
	        try {
	        	String savePath = Configuration.getInstance().getValue("appDir")
	        			+ "app" + System.getProperty("file.separator"); //避免在windows和Linux下文件路径分隔斜杠不同，统一采用System.getProperty("file.separator")写法
	        	String newFileName = UUID.randomUUID().toString() + "." + getUploadFileName().substring(getUploadFileName().indexOf(".")+1).toLowerCase();
	    		File saveDirFile = new File(savePath);
	    		if (!saveDirFile.exists()) 
	    		{
	    			saveDirFile.setWritable(true, false); //设置文件夹权限，避免在Linux下不能写入文件
	    			saveDirFile.mkdirs();
	    		}
				fos = new FileOutputStream(savePath + newFileName);
				fis = new FileInputStream(getUpload());
				byte[] buffer = new byte[1024];
	            int len = 0;
	            while ((len = fis.read(buffer)) != -1) {
	                fos.write(buffer, 0, len);
	            }
	            AppVersionId id = new AppVersionId(versioncode, channelid);
	            appVersion = new AppVersion(id, new AppChannel(channelid), savePath + newFileName, new Date().getTime(), 0l, content, (short)0, needUpdate, null,category);
	            appVersionService.add(appVersion);
	        } 
	        catch (Exception e) {
				e.printStackTrace();
			} 
	        finally {
	            close(fos, fis);
	        }
			return forward("/appVersion_list");
		}
		else
		{
			appChannels = this.appChannelService.getByHql("from AppChannel as a order by a.orderid");
			return "add";
		}
	}
	/**
	 * APP编辑
	 * @return
	 */
	public String edit()
	{
		if(getMethod().equals("edit"))
		{
			FileOutputStream fos = null;
	        FileInputStream fis = null;
	        try {
	        	String savePath = Configuration.getInstance().getValue("appDir")
	        			+ "app" + System.getProperty("file.separator") ; //避免在windows和Linux下文件路径分隔斜杠不同，统一采用System.getProperty("file.separator")写法
	        	String newFileName = UUID.randomUUID().toString() + "." + getUploadFileName().substring(getUploadFileName().indexOf(".")+1).toLowerCase();
	    		File saveDirFile = new File(savePath);
	    		if (!saveDirFile.exists()) 
	    		{
	    			saveDirFile.setWritable(true, false); //设置文件夹权限，避免在Linux下不能写入文件
	    			saveDirFile.mkdirs();
	    		}
				fos = new FileOutputStream(savePath + newFileName);
				fis = new FileInputStream(getUpload());
				byte[] buffer = new byte[1024];
	            int len = 0;
	            while ((len = fis.read(buffer)) != -1) {
	                fos.write(buffer, 0, len);
	            }
	            AppVersionId id = new AppVersionId(versioncode, channelid);
	            appVersion = new AppVersion(id, new AppChannel(channelid), savePath + newFileName, null, null, content, null, needUpdate, null,category);
	            appVersionService.update(appVersion);
	        } 
	        catch (Exception e) {
				e.printStackTrace();
			} 
	        finally {
	            close(fos, fis);
	        }
			return forward("/appVersion_list");
		}
		else
		{
			AppVersionId id = new AppVersionId(versioncode, channelid);
			appVersion = appVersionService.getById(id);
			appChannels = this.appChannelService.getByHql("from AppChannel as a order by a.orderid");
			return "edit";
		}
	}
	/**
	 * APP列表
	 * @return
	 */
	public String list()
	{
		appChannels = this.appChannelService.getByHql("from AppChannel as a order by a.orderid");
		AppVersionId id = new AppVersionId(versioncode, channelid);
		String hql = "from AppVersion as a where a.isDel = 0";
		if(channelid != null)
		{
			hql += " and a.id.channelid = " + channelid;
		}
		if(versioncode != null)
		{
			//hql += " and a.id.versioncode ="+ versioncode;
			hql += " and cast(a.id.versioncode as string) like '%" + getVersioncode() + "%'";
		}
		hql += " order by a.posttime desc";
		appVersionPage = appVersionService.loadPage(hql, getPageSize(), getPage());
		return "list";
	}
	/**
	 * APP删除
	 * @return
	 */
	public String delete()
	{
		appVersionService.bulkDelete(getIds());
		return forward("/appVersion_list");
	}
	
	/**
	 * 关闭输入输出流
	 * @param fos
	 * @param fis
	 */
	private void close(FileOutputStream fos, FileInputStream fis) {
        if (fis != null) {
            try {
                fis.close();
                fis=null;
            } catch (IOException e) {
                System.out.println();
                MyPrintUtil.getDefaultInstance().out("FileInputStream关闭失败");
                e.printStackTrace();
            }
        }
        if (fos != null) {
            try {
                fos.close();
                fis=null;
            } catch (IOException e) {
                MyPrintUtil.getDefaultInstance().out("FileOutputStream关闭失败");
                e.printStackTrace();
            }
        }
    }
	public PageBean<AppVersion> getAppVersionPage() {
		return appVersionPage;
	}
	public void setAppVersionPage(PageBean<AppVersion> appVersionPage) {
		this.appVersionPage = appVersionPage;
	}
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getNeedUpdate() {
		return needUpdate;
	}
	public void setNeedUpdate(String needUpdate) {
		this.needUpdate = needUpdate;
	}
	public Long getChannelid() {
		return channelid;
	}
	public void setChannelid(Long channelid) {
		this.channelid = channelid;
	}
	public List<AppChannel> getAppChannels() {
		return appChannels;
	}
	public void setAppChannels(List<AppChannel> appChannels) {
		this.appChannels = appChannels;
	}
	public AppVersion getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(AppVersion appVersion) {
		this.appVersion = appVersion;
	}
	public Long getVersioncode() {
		return versioncode;
	}
	public void setVersioncode(Long versioncode) {
		this.versioncode = versioncode;
	}
	@Override
	public String logModel() {
		// TODO Auto-generated method stub
		return "app";
	}
	public Integer getCategory() {
		return category;
	}
	public void setCategory(Integer category) {
		this.category = category;
	}
}

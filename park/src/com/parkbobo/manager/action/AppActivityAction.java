package com.parkbobo.manager.action;

import java.io.File;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.parkbobo.model.AppActivity;
import com.parkbobo.service.AppActivityService;
import com.parkbobo.utils.Configuration;
import com.parkbobo.utils.PageBean;

/**
 * @author Administrator
 *app活动
 */

@Controller("appActivityAction")
@Scope("prototype")
public class AppActivityAction extends BaseAction {

	private PageBean<AppActivity> appActivityPage;
	@Resource(name="appActivityService")
	private AppActivityService appActivityService;
	private AppActivity appActivity;
	private static final String imgFilePathString="/appActivity/";
	private File upimg;
	private String upimgFileName;
	private Long activityid;
	public String list()
	{
		System.out.println(request.getParameter("url"));
		String hql = "from AppActivity as a where isDel=0 " ;
		if(appActivity != null)
		{
			if(appActivity.getTitle() != null && !appActivity.getTitle().equals(""))
			{
				hql += " and a.title like '%" + appActivity.getTitle() + "%'";
			}
		}
		hql += "order by posttime desc ";
		appActivityPage = appActivityService.loadPage(hql, getPageSize(), getPage());
		return "list";
	}

	
	/**
	 * 添加，修改共用页面
	 * @return
	 */
	public String toSave(){
		
		if(appActivity!=null){
			appActivity =  appActivityService.getActivityById(appActivity.getActivityid());
		}
		
		return "toSave"; 
	}
	public String update() throws Exception {
		AppActivity activity =  appActivityService.getActivityById(appActivity.getActivityid());
		activity.setTitle(this.appActivity.getTitle());
		activity.setDescription(this.appActivity.getDescription());
		activity.setMemo(appActivity.getMemo());
		if(upimg!=null)
		activity.setImgurl(uploadImg());
		appActivityService.updateAppActivity(activity);
		return forward("/appActivity_list");
	}
	
	
	public String uploadImg() throws Exception{
		String path ="";
		String newFileName = "";
		if(upimg!=null){
			 newFileName = System.nanoTime()+this.upimgFileName;
			 path = Configuration.getInstance().getValue("parkimg")
					+imgFilePathString;
			File file =  new File(path);
			 if(!file.isDirectory()){
				 file.mkdirs();
			 }
			FileUtils.copyFile(upimg, new File(path+"/"+newFileName));
			return "images"+imgFilePathString+newFileName;
		}
		return null;
	}
	public String add() throws Exception {
		appActivity.setPosttime(System.currentTimeMillis());
		appActivity.setIsDel((short)0);
		appActivity.setImgurl(uploadImg());
		appActivityService.add(appActivity);
		return forward("/appActivity_list");
	}
	public String delete()
	{
		 appActivityService.bulkDelete(getIds());
		return forward("/appActivity_list");
	}
	
	
	public String toPreview(){
		appActivity =  appActivityService.getActivityById(appActivity.getActivityid());
		
		return "toPreview";
	}
	public PageBean<AppActivity> getAppActivityPage() {
		return appActivityPage;
	}



	public void setAppActivityPage(PageBean<AppActivity> appActivityPage) {
		this.appActivityPage = appActivityPage;
	}



	public AppActivity getAppActivity() {
		return appActivity;
	}
	public void setAppActivity(AppActivity appActivity) {
		this.appActivity = appActivity;
	}



	public File getUpimg() {
		return upimg;
	}



	public void setUpimg(File upimg) {
		this.upimg = upimg;
	}



	public String getUpimgFileName() {
		return upimgFileName;
	}



	public void setUpimgFileName(String upimgFileName) {
		this.upimgFileName = upimgFileName;
	}



	@Override
	public String logModel() {
		// TODO Auto-generated method stub
		return "app活动";
	}


	public Long getActivityid() {
		return activityid;
	}


	public void setActivityid(Long activityid) {
		this.activityid = activityid;
	}
	
}

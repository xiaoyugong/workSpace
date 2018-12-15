package com.parkbobo.manager.action;

import java.io.File;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.parkbobo.model.CarparkMarkerCategory;
import com.parkbobo.model.OrderTask;
import com.parkbobo.service.CarparkMarkerCategoryService;
import com.parkbobo.service.OrderTaskService;
import com.parkbobo.utils.Configuration;
import com.parkbobo.utils.PageBean;
import com.parkbobo.utils.StringUtils;

@Controller("carparkMarkerCategoryAction")
@Scope("prototype")
public class CarparkMarkerCategoryAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CarparkMarkerCategory carparkMarkerCategory;
	@Resource(name="carparkMarkerCategoryService")
	private CarparkMarkerCategoryService carparkMarkerCategoryService;
	private static final String imgFilePathString="/carparkMarkerCategory/";
	private File upload;
	private String uploadFileName;
	
	public String list() throws Exception {
		// TODO Auto-generated method stub
		String hqlString = "from CarparkMarkerCategory where 1=1 ";
		if(carparkMarkerCategory!=null){
			hqlString+=" and name like '%"+carparkMarkerCategory.getName().trim()+"%'";
			
		}
		hqlString+=" order by categoryid desc";
		PageBean<CarparkMarkerCategory> page = carparkMarkerCategoryService.page(hqlString,getPageSize(),getPage());
		ActionContext.getContext().getValueStack().set("page", page);
		return "list";
	}
	public String toSave() {
		// TODO Auto-generated method stub
		if(carparkMarkerCategory!=null)
		carparkMarkerCategory = carparkMarkerCategoryService.get(this.carparkMarkerCategory.getCategoryid());
		return "toSave";
	}
	
	public String add() throws Exception{
		carparkMarkerCategory.setImgurl(uploadImg());
		carparkMarkerCategoryService.add(this.carparkMarkerCategory);
		return forward("/carparkMarkerCategory_list");
	}
	public String update() throws Exception{
		CarparkMarkerCategory markerCategory = carparkMarkerCategoryService.get(this.carparkMarkerCategory.getCategoryid());
		if(upload!=null){
			markerCategory.setImgurl(uploadImg());
		}
		markerCategory.setName(this.carparkMarkerCategory.getName());
		markerCategory.setMemo(this.carparkMarkerCategory.getMemo());
		carparkMarkerCategoryService.update(markerCategory);
		saveLog("修改："+markerCategory.getName()+",Id:"+markerCategory.getCategoryid());
		return forward("/carparkMarkerCategory_list");
	}
	
	public String uploadImg() throws Exception{
		String path ="";
		String newFileName = "";
		if(upload!=null){
			 newFileName = StringUtils.getNewFileName(this.uploadFileName);
			 path = Configuration.getInstance().getValue("parkimg")
					+imgFilePathString;
			File file =  new File(path);
			 if(!file.isDirectory()){
				 file.mkdirs();
			 }
			FileUtils.copyFile(upload, new File(path+"/"+newFileName));
			return Configuration.getInstance().getValue("clienturl")+"images"+imgFilePathString+newFileName;
		}
		return null;
	}
	public String delete() throws Exception {
		// TODO Auto-generated method stub
		carparkMarkerCategoryService.delete(getIds());
		saveLog("删除："+getIds());
		return forward("/carparkMarkerCategory_list");
	}
	@Override
	public String logModel() {
		// TODO Auto-generated method stub
		return "标注分类";
	}


	public CarparkMarkerCategory getCarparkMarkerCategory() {
		return carparkMarkerCategory;
	}


	public void setCarparkMarkerCategory(CarparkMarkerCategory carparkMarkerCategory) {
		this.carparkMarkerCategory = carparkMarkerCategory;
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

}

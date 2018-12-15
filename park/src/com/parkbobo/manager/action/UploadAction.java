package com.parkbobo.manager.action;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.parkbobo.utils.StringUtils;





@Controller("uploadAction")
@Scope("prototype")
public class UploadAction extends BaseAction{

	private File upload;
	private String uploadFileName;
	public String CKEditorFuncNum;
	public void uploadImg() throws Exception{
		String path ="";
		String newFileName = "";
		if(upload!=null){
			newFileName = StringUtils.getNewFileName(this.uploadFileName);
			 path = ServletActionContext.getServletContext()
			.getRealPath("/upload/image")+File.separator+newFileName+"";
			FileUtils.copyFile(upload, new File(path));
			String contextPath = ServletActionContext.getRequest().getContextPath();
			String jsString =" <script type=\"text/javascript\">window.parent.CKEDITOR.tools.callFunction("+CKEditorFuncNum+", '"+contextPath+"/upload/image/"+newFileName+"', '');</script>";
			ServletActionContext.getResponse().getWriter().print(jsString);
			
		}
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


	@Override
	public String logModel() {
		// TODO Auto-generated method stub
		return null;
	}


	
	
	
}

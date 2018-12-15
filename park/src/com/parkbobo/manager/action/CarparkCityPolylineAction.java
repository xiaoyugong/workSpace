package com.parkbobo.manager.action;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.parkbobo.manager.model.Manager;
import com.parkbobo.model.CarparkBackgroundPolygon;
import com.parkbobo.model.CarparkCityPolyline;
import com.parkbobo.model.CarparkFloor;
import com.parkbobo.model.CarparkFuzhuPolygon;
import com.parkbobo.service.CarparkBackgroundPolygonService;
import com.parkbobo.service.CarparkCityPolylineService;
import com.parkbobo.service.CarparkFloorService;
import com.parkbobo.utils.Configuration;
import com.parkbobo.utils.PageBean;

@Controller("carparkCityPolylineAction")
@Scope("prototype")
public class CarparkCityPolylineAction extends BaseAction{

	@Resource(name="carparkCityPolylineService")
	private CarparkCityPolylineService carparkCityPolylineService;
	private CarparkCityPolyline carparkCityPolyline;
	@Resource(name="carparkFloorService")
	private CarparkFloorService carparkFloorService;
	private String formType;
	private File upload;
	private String uploadFileName;
	public String list() throws Exception {
		Manager manager = (Manager) request.getSession().getAttribute("loginManager");
		String area = manager.getArea();
		if(area!=null && "总部".equals(area)){
			String hqlString = "from CarparkCityPolyline where 1=1 ";
			if(formType!=null&&formType.equals("carpark")){
				String carparkString =new String(carparkCityPolyline.getCarpark().getName().getBytes("iso-8859-1"), "UTF-8"); 
				hqlString+=" and carpark.name like '%"+carparkString+"%'";
				carparkCityPolyline.getCarpark().setName(carparkString);
			}else{
				if(carparkCityPolyline!=null){
					hqlString+=" and upper(name) like '%"+carparkCityPolyline.getName().trim().toUpperCase()+"%'" +
					" and carpark.name like '%"+carparkCityPolyline.getCarpark().getName().trim()+"%'";
				}
			}
			hqlString+="order by gid desc";
			PageBean<CarparkCityPolyline> pageBean  = carparkCityPolylineService.page(hqlString,getPageSize(),getPage());
			ActionContext.getContext().getValueStack().set("page", pageBean);
			
		}else if(area!=null && !"".equals(area)){
			String hqlString = "from CarparkCityPolyline where carpark.city like '%" + area +"%' ";
			if(formType!=null&&formType.equals("carpark")){
				String carparkString =new String(carparkCityPolyline.getCarpark().getName().getBytes("iso-8859-1"), "UTF-8"); 
				hqlString+=" and carpark.name like '%"+carparkString+"%'";
				carparkCityPolyline.getCarpark().setName(carparkString);
			}else{
				if(carparkCityPolyline!=null){
					hqlString+=" and upper(name) like '%"+carparkCityPolyline.getName().trim().toUpperCase()+"%'" +
					" and carpark.name like '%"+carparkCityPolyline.getCarpark().getName().trim()+"%'";
				}
			}
			hqlString+="order by gid desc";
			PageBean<CarparkCityPolyline> pageBean  = carparkCityPolylineService.page(hqlString,getPageSize(),getPage());
			ActionContext.getContext().getValueStack().set("page", pageBean);
		}
		return "list";
	}
	public String toImportExcel(){
		return "toImportExcel";
	}
	public String importExcel() throws IOException{
		
		String savePath = Configuration.getInstance().getValue("attachedPath")
		+ "exceltemp" + System.getProperty("file.separator") ;
		String newFileName = UUID.randomUUID().toString() + "." + getUploadFileName().substring(getUploadFileName().indexOf(".")+1).toLowerCase();
		File excelFile = new File(savePath+newFileName);
		FileUtils.copyFile(upload, excelFile);
		String messageString = carparkCityPolylineService.importExcel(excelFile);
		
		response.getWriter().print(messageString);
		return null;
	}
	public String toAdd() throws Exception {
		// TODO Auto-generated method stub
		return "toAdd";
	}
	public String toUpdate() throws Exception {
		// TODO Auto-generated method stub
		carparkCityPolyline = carparkCityPolylineService.get(carparkCityPolyline.getGid());
		return "toUpdate";
	}
	public String add() throws Exception {
		// TODO Auto-generated method stub
		carparkCityPolylineService.add(carparkCityPolyline);
		saveLog("添加：【"+carparkCityPolyline.getCarpark().getName()+"】ID:"+carparkCityPolyline.getGid());
		return forward("/carparkCityPolyline_list");
	}
	public String update() throws Exception {
		// TODO Auto-generated method stub
		carparkCityPolylineService.update(carparkCityPolyline);
		saveLog("修改：【"+carparkCityPolyline.getCarpark().getName()+"】ID:"+carparkCityPolyline.getGid());
		return forward("/carparkCityPolyline_list");
	}
	public String delete(){
		carparkCityPolylineService.delete(getIds());
		saveLog("删除ID:"+getIds());
		return forward("/carparkCityPolyline_list");
	}
	
	@Override
	public String logModel() {
		// TODO Auto-generated method stub
		return "停车场外部道路";
	}

	public CarparkCityPolyline getCarparkCityPolyline() {
		return carparkCityPolyline;
	}

	public void setCarparkCityPolyline(CarparkCityPolyline carparkCityPolyline) {
		this.carparkCityPolyline = carparkCityPolyline;
	}

	public String getFormType() {
		return formType;
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
	public void setFormType(String formType) {
		this.formType = formType;
	}

}

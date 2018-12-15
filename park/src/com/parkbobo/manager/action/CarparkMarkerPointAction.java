package com.parkbobo.manager.action;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.parkbobo.manager.model.Manager;
import com.parkbobo.model.CarparkCategory;
import com.parkbobo.model.CarparkFloor;
import com.parkbobo.model.CarparkMarkerCategory;
import com.parkbobo.model.CarparkMarkerPoint;
import com.parkbobo.service.CarparkFloorService;
import com.parkbobo.service.CarparkMarkerCategoryService;
import com.parkbobo.service.CarparkMarkerPointService;
import com.parkbobo.service.CarparkService;
import com.parkbobo.utils.Configuration;
import com.parkbobo.utils.PageBean;

@Controller("carparkMarkerPointAction")
@Scope("prototype")
public class CarparkMarkerPointAction extends BaseAction {

	@Resource(name="carparkMarkerPointService")
	private CarparkMarkerPointService carparkMarkerPointService;
	private CarparkMarkerPoint carparkMarkerPoint;
	@Resource(name="carparkFloorService")
	private CarparkFloorService carparkFloorService;
	private String formType;
	@Resource(name="carparkService")
	private CarparkService carparkService;
	@Resource(name="carparkMarkerCategoryService")
	private CarparkMarkerCategoryService carparkMarkerCategoryService;
	private File upload;
	private String uploadFileName;
	public String list() throws UnsupportedEncodingException{
		Manager manager = (Manager) request.getSession().getAttribute("loginManager");
		String area = manager.getArea();
		if(area!=null && "总部".equals(area)){
			String hql = "from CarparkMarkerPoint where 1=1 ";
			if(formType!=null&&formType.equals("carpark")){
				String carparkString = new String(carparkMarkerPoint.getCarpark().getName().getBytes("iso-8859-1"), "UTF-8"); 
				hql+=" and carpark.name like '%"+carparkString+"%'";
				carparkMarkerPoint.getCarpark().setName(carparkString);
			}else{
				if(carparkMarkerPoint!=null){
					hql+=" and upper(name) like '%"+carparkMarkerPoint.getName().trim().toUpperCase()+"%'" +
					" and carpark.name like '%"+carparkMarkerPoint.getCarpark().getName().trim()+"%'";
				}
			}
			
			hql+="order by gid desc";
			PageBean<CarparkMarkerPoint> pageBean  = carparkMarkerPointService.page(hql,getPageSize(),getPage());
			ActionContext.getContext().getValueStack().set("page", pageBean);
		}else if(area!=null && !"".equals(area)){
			String hql = "from CarparkMarkerPoint where carpark.city like '%" + area +"%' ";
			if(formType!=null&&formType.equals("carpark")){
				String carparkString = new String(carparkMarkerPoint.getCarpark().getName().getBytes("iso-8859-1"), "UTF-8"); 
				hql+=" and carpark.name like '%"+carparkString+"%'";
				carparkMarkerPoint.getCarpark().setName(carparkString);
			}else{
				if(carparkMarkerPoint!=null){
					hql+=" and upper(name) like '%"+carparkMarkerPoint.getName().trim().toUpperCase()+"%'" +
					" and carpark.name like '%"+carparkMarkerPoint.getCarpark().getName().trim()+"%'";
				}
			}
			
			hql+="order by gid desc";
			PageBean<CarparkMarkerPoint> pageBean  = carparkMarkerPointService.page(hql,getPageSize(),getPage());
			ActionContext.getContext().getValueStack().set("page", pageBean);
		}
		return "list";
	}
	
	public String toAdd() throws Exception {
		// TODO Auto-generated method stub
		//分类
		List<CarparkMarkerCategory> carparkMarkerCategorys  = carparkMarkerCategoryService.getByHql("from CarparkMarkerCategory order by categoryid desc");
		ActionContext.getContext().getValueStack().set("carparkMarkerCategorys",carparkMarkerCategorys );
		//楼层
		return "toAdd";
	}
	public String toUpdate() throws Exception {
		carparkMarkerPoint = carparkMarkerPointService.get(carparkMarkerPoint.getGid());
		//标注分类
		List<CarparkMarkerCategory> carparkMarkerCategorys  = carparkMarkerCategoryService.getByHql("from CarparkMarkerCategory order by categoryid desc");
		ActionContext.getContext().getValueStack().set("carparkMarkerCategorys",carparkMarkerCategorys );
		
		//楼层
		String hql = "from CarparkFloor where carpark.carparkid="+carparkMarkerPoint.getCarpark().getCarparkid();
		List<CarparkFloor> floors = carparkFloorService.getByHql(hql);
		ActionContext.getContext().getValueStack().set("floors",floors );
		return "toUpdate";
	}
	public String add() throws Exception {
		// TODO Auto-generated method stub
		carparkMarkerPointService.add(carparkMarkerPoint);
		saveLog("添加：【"+carparkMarkerPoint.getCarpark().getName()+"】ID:"+carparkMarkerPoint.getGid());
		return forward("/carparkMarkerPoint_list");
	}
	public String update() throws Exception {
		// TODO Auto-generated method stub
		carparkMarkerPointService.update(carparkMarkerPoint);
		saveLog("修改：【"+carparkMarkerPoint.getCarpark().getName()+"】ID:"+carparkMarkerPoint.getGid());
		return forward("/carparkMarkerPoint_list");
	}
	public String delete(){
		carparkMarkerPointService.delete(getIds());
		saveLog("删除ID:"+getIds());
		return forward("/carparkMarkerPoint_list");
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
		String messageString = carparkMarkerPointService.importExcel(excelFile);
		
		response.getWriter().print(messageString);
		return null;
	}
	public CarparkMarkerPoint getCarparkMarkerPoint() {
		return carparkMarkerPoint;
	}

	public void setCarparkMarkerPoint(CarparkMarkerPoint carparkMarkerPoint) {
		this.carparkMarkerPoint = carparkMarkerPoint;
	}

	@Override
	public String logModel() {
		// TODO Auto-generated method stub
		return "停车场标注";
	}

	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
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

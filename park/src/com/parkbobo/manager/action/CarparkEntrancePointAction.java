package com.parkbobo.manager.action;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.parkbobo.manager.model.Manager;
import com.parkbobo.model.CarparkEntrancePoint;
import com.parkbobo.model.CarparkFloor;
import com.parkbobo.service.CarparkEntrancePointService;
import com.parkbobo.service.CarparkFloorService;
import com.parkbobo.service.CarparkService;
import com.parkbobo.utils.Configuration;
import com.parkbobo.utils.PageBean;

@Controller("carparkEntrancePointAction")
@Scope("prototype")
public class CarparkEntrancePointAction extends BaseAction {

	
	
	@Resource(name="carparkEntrancePointService")
	private CarparkEntrancePointService carparkEntrancePointService;
	private CarparkEntrancePoint carparkEntrancePoint;
	@Resource(name="carparkService")
	private CarparkService carparkService;
	@Resource(name="carparkFloorService")
	private CarparkFloorService carparkFloorService;
	private String formType;
	private File upload;
	private String uploadFileName;
	public String list() throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		Manager manager = (Manager) request.getSession().getAttribute("loginManager");
		String area = manager.getArea();
		if(area!=null && "总部".equals(area)){
			String hqlString = "from CarparkEntrancePoint where 1=1 ";
			if(formType!=null&&formType.equals("carpark")){
				String carparkname = new String(carparkEntrancePoint.getCarpark().getName().getBytes( "iso-8859-1" ), "UTF-8" );
				carparkEntrancePoint.getCarpark().setName(carparkname);	
				hqlString+=" and carpark.name like '%"+carparkname+"%'";
			}else {
				if(carparkEntrancePoint!=null){
					hqlString+=" and upper(name) like '%"+carparkEntrancePoint.getName().trim().toUpperCase()+"%'";
					hqlString+=" and carpark.name like '%"+carparkEntrancePoint.getCarpark().getName()+"%'";
				}
			}
			hqlString+=" order by gid desc";
			PageBean<CarparkEntrancePoint> pageBean = carparkEntrancePointService.page(hqlString,getPageSize(),getPage());
			ActionContext.getContext().getValueStack().set("page", pageBean);
		}else if(area!=null && !"".equals(area)){
			String hqlString = "from CarparkEntrancePoint where carpark.city like '%" + area +"%' ";
			if(formType!=null&&formType.equals("carpark")){
				String carparkname = new String(carparkEntrancePoint.getCarpark().getName().getBytes( "iso-8859-1" ), "UTF-8" );
				carparkEntrancePoint.getCarpark().setName(carparkname);	
				hqlString+=" and carpark.name like '%"+carparkname+"%'";
			}else {
				if(carparkEntrancePoint!=null){
					hqlString+=" and upper(name) like '%"+carparkEntrancePoint.getName().trim().toUpperCase()+"%'";
					hqlString+=" and carpark.name like '%"+carparkEntrancePoint.getCarpark().getName()+"%'";
				}
			}
			hqlString+=" order by gid desc";
			PageBean<CarparkEntrancePoint> pageBean = carparkEntrancePointService.page(hqlString,getPageSize(),getPage());
			ActionContext.getContext().getValueStack().set("page", pageBean);
		}
		return "list";
	}
	
	public String toAdd() {
		// TODO Auto-generated method stub
		return "toAdd";
	}
	public String toUpdate() {
		// TODO Auto-generated method stub
		carparkEntrancePoint = carparkEntrancePointService.get(carparkEntrancePoint.getGid());
		String hql = "from CarparkFloor where carpark.carparkid="+carparkEntrancePoint.getCarpark().getCarparkid();
		List<CarparkFloor> floors = carparkFloorService.getByHql(hql);
		ActionContext.getContext().getValueStack().set("floors",floors );
		
		return "toUpdate";
	}
	public String add(){
		carparkEntrancePoint.setCarpark(carparkService.getById(carparkEntrancePoint.getCarpark().getCarparkid()));
		carparkEntrancePointService.add(carparkEntrancePoint);
		saveLog("添加：【"+carparkEntrancePoint.getCarpark().getName()+"】ID:"+carparkEntrancePoint.getGid());
		return forward("/carparkEntrancePoint_list");
	}
	public String update(){
		carparkEntrancePoint.setCarpark(carparkService.getById(carparkEntrancePoint.getCarpark().getCarparkid()));
		carparkEntrancePointService.update(carparkEntrancePoint);
		saveLog("修改:"+carparkEntrancePoint.getCarpark().getName()+",Id:"+carparkEntrancePoint.getGid());
		return forward("/carparkEntrancePoint_list");
	}
	
	public String delete(){
		carparkEntrancePointService.delete(getIds());
		saveLog("删除:,Id:"+getIds());
		return forward("/carparkEntrancePoint_list");
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
		String messageString = carparkEntrancePointService.importExcel(excelFile);
		
		response.getWriter().print(messageString);
		return null;
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
		return "停车场出入口";
	}

	public CarparkEntrancePoint getCarparkEntrancePoint() {
		return carparkEntrancePoint;
	}

	public void setCarparkEntrancePoint(CarparkEntrancePoint carparkEntrancePoint) {
		this.carparkEntrancePoint = carparkEntrancePoint;
	}

	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

}

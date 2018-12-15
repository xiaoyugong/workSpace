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
import com.parkbobo.model.CarparkFloor;
import com.parkbobo.model.CarparkFuzhuPolygon;
import com.parkbobo.model.CarparkMarkerPoint;
import com.parkbobo.service.CarparkBackgroundPolygonService;
import com.parkbobo.service.CarparkFloorService;
import com.parkbobo.utils.Configuration;
import com.parkbobo.utils.PageBean;

@Controller("carparkBackgroundPolygonAction")
@Scope("prototype")
public class CarparkBackgroundPolygonAction extends BaseAction {
	@Resource(name="carparkBackgroundPolygonService")
	private CarparkBackgroundPolygonService backgroundPolygonService;
	private CarparkBackgroundPolygon carparkBackgroundPolygon;
	@Resource(name="carparkFloorService")
	private CarparkFloorService carparkFloorService;
	private String formType;
	private File upload;
	private String uploadFileName;
	public String list() throws Exception {
		Manager manager = (Manager) request.getSession().getAttribute("loginManager");
		String area = manager.getArea();
		if(area!=null && "总部".equals(area)){
			String hqlString = "from CarparkBackgroundPolygon where 1=1 ";
			if(formType!=null&&formType.equals("carpark")){
				String carparkString =new String(carparkBackgroundPolygon.getCarpark().getName().getBytes("iso-8859-1"), "UTF-8"); 
				hqlString+=" and carpark.name like '%"+carparkString+"%'";
				carparkBackgroundPolygon.getCarpark().setName(carparkString);
			}else{
				if(carparkBackgroundPolygon!=null){
					hqlString+=" and upper(name) like '%"+carparkBackgroundPolygon.getName().trim().toUpperCase()+"%'" +
					" and carpark.name like '%"+carparkBackgroundPolygon.getCarpark().getName().trim()+"%'";
				}
			}
			hqlString+="order by gid desc";
			PageBean<CarparkBackgroundPolygon> pageBean  = backgroundPolygonService.page(hqlString,getPageSize(),getPage());
			ActionContext.getContext().getValueStack().set("page", pageBean);
		}else if(area!=null && !"".equals(area)){
			System.out.println("111");
			String hqlString = "from CarparkBackgroundPolygon where carpark.city like '%" + area +"%' ";
			if(formType!=null&&formType.equals("carpark")){
				String carparkString =new String(carparkBackgroundPolygon.getCarpark().getName().getBytes("iso-8859-1"), "UTF-8"); 
				hqlString+=" and carpark.name like '%"+carparkString+"%'";
				carparkBackgroundPolygon.getCarpark().setName(carparkString);
			}else{
				if(carparkBackgroundPolygon!=null){
					hqlString+=" and upper(name) like '%"+carparkBackgroundPolygon.getName().trim().toUpperCase()+"%'" +
					" and carpark.name like '%"+carparkBackgroundPolygon.getCarpark().getName().trim()+"%'";
				}
			}
			hqlString+="order by gid desc";
			PageBean<CarparkBackgroundPolygon> pageBean  = backgroundPolygonService.page(hqlString,getPageSize(),getPage());
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
		String messageString = backgroundPolygonService.importExcel(excelFile);
		
		response.getWriter().print(messageString);
		return null;
	}
	public String toAdd() throws Exception {
		// TODO Auto-generated method stub
		return "toAdd";
	}
	public String toUpdate() throws Exception {
		// TODO Auto-generated method stub
		carparkBackgroundPolygon = backgroundPolygonService.get(this.carparkBackgroundPolygon.getGid());
		String hql = "from CarparkFloor where carpark.carparkid="+carparkBackgroundPolygon.getCarpark().getCarparkid();
		List<CarparkFloor> floors = carparkFloorService.getByHql(hql);
		ActionContext.getContext().getValueStack().set("floors",floors );
		return "toUpdate";
	}
	public String add() throws Exception {
		// TODO Auto-generated method stub
		backgroundPolygonService.add(carparkBackgroundPolygon);
		saveLog("添加：【"+carparkBackgroundPolygon.getCarpark().getName()+"】ID:"+carparkBackgroundPolygon.getGid());
		return forward("/carparkBackgroundPolygon_list");
	}
	public String update() throws Exception {
		// TODO Auto-generated method stub
		backgroundPolygonService.update(carparkBackgroundPolygon);
		saveLog("修改：【"+carparkBackgroundPolygon.getCarpark().getName()+"】ID:"+carparkBackgroundPolygon.getGid());
		return forward("/carparkBackgroundPolygon_list");
	}
	public String delete(){
		backgroundPolygonService.delete(getIds());
		saveLog("删除ID："+getIds());
		return forward("/carparkBackgroundPolygon_list");
	}
	
	
	
	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

	public String logModel() {
		// TODO Auto-generated method stub
		return "停车场背景底图";
	}

	public CarparkBackgroundPolygon getCarparkBackgroundPolygon() {
		return carparkBackgroundPolygon;
	}

	public void setCarparkBackgroundPolygon(
			CarparkBackgroundPolygon carparkBackgroundPolygon) {
		this.carparkBackgroundPolygon = carparkBackgroundPolygon;
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

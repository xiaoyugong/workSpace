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
import com.parkbobo.model.CarparkBackgroundPolygon;
import com.parkbobo.model.CarparkFloor;
import com.parkbobo.model.CarparkModelPolygon;
import com.parkbobo.service.CarparkFloorService;
import com.parkbobo.service.CarparkModelPolygonService;
import com.parkbobo.utils.Configuration;
import com.parkbobo.utils.PageBean;

/**
 * @author Administrator
 *停车场有高度模型图层
 */
@Controller("carparkModelPolygonAction")
@Scope("prototype")
public class CarparkModelPolygonAction extends BaseAction { 
	private CarparkModelPolygon carparkModelPolygon;
	@Resource(name="carparkModelPolygonService")
	private CarparkModelPolygonService carparkModelPolygonService;
	@Resource(name="carparkFloorService")
	private CarparkFloorService carparkFloorService;
	private String formType;
	private File upload;
	private String uploadFileName;
	public String list() throws Exception {
		String hqlString = "from CarparkModelPolygon where 1=1 ";
		if(formType!=null&&formType.equals("carpark")){
			String carparkString =new String(carparkModelPolygon.getCarpark().getName().getBytes("iso-8859-1"), "UTF-8"); 
			hqlString+=" and carpark.name like '%"+carparkString+"%'";
			carparkModelPolygon.getCarpark().setName(carparkString);
		}else{
			if(carparkModelPolygon!=null){
				hqlString+=" and upper(name) like '%"+carparkModelPolygon.getName().trim().toUpperCase()+"%'" +
				" and carpark.name like '%"+carparkModelPolygon.getCarpark().getName().trim()+"%'";
			}
		}
		hqlString+="order by gid desc";
		PageBean<CarparkModelPolygon> pageBean  = carparkModelPolygonService.page(hqlString,getPageSize(),getPage());
		ActionContext.getContext().getValueStack().set("page", pageBean);
		return "list";
	}
	
	public String toAdd() throws Exception {
		// TODO Auto-generated method stub
		return "toAdd";
	}
	public String toUpdate() throws Exception {
		// TODO Auto-generated method stub
		carparkModelPolygon = carparkModelPolygonService.get(this.carparkModelPolygon.getGid());
		String hql = "from CarparkFloor where carpark.carparkid="+carparkModelPolygon.getCarpark().getCarparkid();
		List<CarparkFloor> floors = carparkFloorService.getByHql(hql);
		ActionContext.getContext().getValueStack().set("floors",floors );
		return "toUpdate";
	}
	public String add() throws Exception {
		// TODO Auto-generated method stub
		carparkModelPolygonService.add(carparkModelPolygon);
		saveLog("添加：【"+carparkModelPolygon.getCarpark().getName()+"】ID:"+carparkModelPolygon.getGid());
		return forward("/carparkModelPolygon_list");
	}
	public String update() throws Exception {
		// TODO Auto-generated method stub
		carparkModelPolygonService.update(carparkModelPolygon);
		saveLog("修改：【"+carparkModelPolygon.getCarpark().getName()+"】ID:"+carparkModelPolygon.getGid());
		return forward("/carparkModelPolygon_list");
		
	}
	public String delete(){
		carparkModelPolygonService.delete(getIds());
		saveLog("删除ID："+getIds());
		return forward("/carparkModelPolygon_list");
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
		String messageString = carparkModelPolygonService.importExcel(excelFile);
		
		response.getWriter().print(messageString);
		return null;
	}
	
	
	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}
	@Override
	public String logModel() {
		// TODO Auto-generated method stub
		return "停车场有高度模型图层";
	}

	public CarparkModelPolygon getCarparkModelPolygon() {
		return carparkModelPolygon;
	}

	public void setCarparkModelPolygon(CarparkModelPolygon carparkModelPolygon) {
		this.carparkModelPolygon = carparkModelPolygon;
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

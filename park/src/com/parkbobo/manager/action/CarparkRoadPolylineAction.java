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
import com.parkbobo.model.CarparkFloor;
import com.parkbobo.model.CarparkModelPolygon;
import com.parkbobo.model.CarparkRoadPolyline;
import com.parkbobo.service.CarparkFloorService;
import com.parkbobo.service.CarparkModelPolygonService;
import com.parkbobo.service.CarparkRoadPolylineService;
import com.parkbobo.utils.Configuration;
import com.parkbobo.utils.PageBean;

/**
 * @author Administrator
 *停车场内部道路
 */
@Controller("carparkRoadPolylineAction")
@Scope("prototype")
public class CarparkRoadPolylineAction extends BaseAction{
	private CarparkRoadPolyline carparkRoadPolyline;
	@Resource(name="carparkRoadPolylineService")
	private CarparkRoadPolylineService carparkRoadPolylineService;
	@Resource(name="carparkFloorService")
	private CarparkFloorService carparkFloorService;
	private String formType;
	private File upload;
	private String uploadFileName;
	public String list() throws Exception {
		Manager manager = (Manager) request.getSession().getAttribute("loginManager");
		String area = manager.getArea();
		if(area!=null && "总部".equals(area)){
			String hqlString = "from CarparkRoadPolyline where 1=1 ";
			if(formType!=null&&formType.equals("carpark")){
				String carparkString =new String(carparkRoadPolyline.getCarpark().getName().getBytes("iso-8859-1"), "UTF-8"); 
				hqlString+=" and carpark.name like '%"+carparkString+"%'";
				carparkRoadPolyline.getCarpark().setName(carparkString);
			}else{
				if(carparkRoadPolyline!=null){
					hqlString+=" and upper(name) like '%"+carparkRoadPolyline.getName().trim().toUpperCase()+"%'" +
					" and carpark.name like '%"+carparkRoadPolyline.getCarpark().getName().trim()+"%'";
				}
			}
			hqlString+="order by gid desc";
			PageBean<CarparkRoadPolyline> pageBean  = carparkRoadPolylineService.page(hqlString,getPageSize(),getPage());
			ActionContext.getContext().getValueStack().set("page", pageBean);
		}else if(area!=null && !"".equals(area)){
			String hqlString = "from CarparkRoadPolyline where carpark.city like '%" + area +"%' ";
			if(formType!=null&&formType.equals("carpark")){
				String carparkString =new String(carparkRoadPolyline.getCarpark().getName().getBytes("iso-8859-1"), "UTF-8"); 
				hqlString+=" and carpark.name like '%"+carparkString+"%'";
				carparkRoadPolyline.getCarpark().setName(carparkString);
			}else{
				if(carparkRoadPolyline!=null){
					hqlString+=" and upper(name) like '%"+carparkRoadPolyline.getName().trim().toUpperCase()+"%'" +
					" and carpark.name like '%"+carparkRoadPolyline.getCarpark().getName().trim()+"%'";
				}
			}
			hqlString+="order by gid desc";
			PageBean<CarparkRoadPolyline> pageBean  = carparkRoadPolylineService.page(hqlString,getPageSize(),getPage());
			ActionContext.getContext().getValueStack().set("page", pageBean);
		}
		return "list";
	}
	
	public String toAdd() throws Exception {
		// TODO Auto-generated method stub
		return "toAdd";
	}
	public String toUpdate() throws Exception {
		// TODO Auto-generated method stub
		carparkRoadPolyline = carparkRoadPolylineService.get(this.carparkRoadPolyline.getGid());
		String hql = "from CarparkFloor where carpark.carparkid="+carparkRoadPolyline.getCarpark().getCarparkid();
		List<CarparkFloor> floors = carparkFloorService.getByHql(hql);
		ActionContext.getContext().getValueStack().set("floors",floors );
		return "toUpdate";
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
		String messageString = carparkRoadPolylineService.importExcel(excelFile);
		
		response.getWriter().print(messageString);
		return null;
	}
	public String add() throws Exception {
		// TODO Auto-generated method stub
		carparkRoadPolylineService.add(carparkRoadPolyline);
		saveLog("添加：【"+carparkRoadPolyline.getCarpark().getName()+"】ID:"+carparkRoadPolyline.getGid());
		return forward("/carparkRoadPolyline_list");
	}
	public String update() throws Exception {
		// TODO Auto-generated method stub
		carparkRoadPolylineService.update(carparkRoadPolyline);
		saveLog("修改：【"+carparkRoadPolyline.getCarpark().getName()+"】ID:"+carparkRoadPolyline.getGid());
		return forward("/carparkRoadPolyline_list");
	}
	public String delete(){
		carparkRoadPolylineService.delete(getIds());
		saveLog("删除ID:"+getIds());
		return forward("/carparkRoadPolyline_list");
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

	public CarparkRoadPolyline getCarparkRoadPolyline() {
		return carparkRoadPolyline;
	}

	public void setCarparkRoadPolyline(CarparkRoadPolyline carparkRoadPolyline) {
		this.carparkRoadPolyline = carparkRoadPolyline;
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
		return "停车场内部道路";
	}

}

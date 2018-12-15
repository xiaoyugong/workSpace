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
import com.parkbobo.model.CarparkNavigationPolyline;
import com.parkbobo.service.CarparkBackgroundPolygonService;
import com.parkbobo.service.CarparkFloorService;
import com.parkbobo.service.CarparkNavigationPolylineService;
import com.parkbobo.utils.Configuration;
import com.parkbobo.utils.PageBean;

/**
 * @author Administrator
 *停车场导航路线
 */
@Controller("carparkNavigationPolylineAction")
@Scope("prototype")
public class CarparkNavigationPolylineAction extends BaseAction {
	@Resource(name="carparkNavigationPolylineService")
	private CarparkNavigationPolylineService carparkNavigationPolylineService;
	private CarparkNavigationPolyline carparkNavigationPolyline;
	@Resource(name="carparkFloorService")
	private CarparkFloorService carparkFloorService;
	private String formType;
	private File upload;
	private String uploadFileName;
	public String list() throws Exception {
		Manager manager = (Manager) request.getSession().getAttribute("loginManager");
		String area = manager.getArea();
		if(area!=null && "总部".equals(area)){
			String hqlString = "from CarparkNavigationPolyline where 1=1 ";
			if(formType!=null&&formType.equals("carpark")){
				String carparkString =new String(carparkNavigationPolyline.getCarpark().getName().getBytes("iso-8859-1"), "UTF-8"); 
				hqlString+=" and carpark.name like '%"+carparkString+"%'";
				carparkNavigationPolyline.getCarpark().setName(carparkString);
			}else{
				if(carparkNavigationPolyline!=null){
					hqlString+=" and upper(name) like '%"+carparkNavigationPolyline.getName().trim().toUpperCase()+"%'" +
					" and carpark.name like '%"+carparkNavigationPolyline.getCarpark().getName().trim()+"%'";
				}
			}
			hqlString+="order by gid desc";
			PageBean<CarparkNavigationPolyline> pageBean  = carparkNavigationPolylineService.page(hqlString,getPageSize(),getPage());
			ActionContext.getContext().getValueStack().set("page", pageBean);
			
		}else if(area!=null && !"".equals(area)){
			String hqlString = "from CarparkNavigationPolyline where carpark.city like '%" + area +"%' ";
			if(formType!=null&&formType.equals("carpark")){
				String carparkString =new String(carparkNavigationPolyline.getCarpark().getName().getBytes("iso-8859-1"), "UTF-8"); 
				hqlString+=" and carpark.name like '%"+carparkString+"%'";
				carparkNavigationPolyline.getCarpark().setName(carparkString);
			}else{
				if(carparkNavigationPolyline!=null){
					hqlString+=" and upper(name) like '%"+carparkNavigationPolyline.getName().trim().toUpperCase()+"%'" +
					" and carpark.name like '%"+carparkNavigationPolyline.getCarpark().getName().trim()+"%'";
				}
			}
			hqlString+="order by gid desc";
			PageBean<CarparkNavigationPolyline> pageBean  = carparkNavigationPolylineService.page(hqlString,getPageSize(),getPage());
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
		carparkNavigationPolyline = carparkNavigationPolylineService.get(this.carparkNavigationPolyline.getGid());
		String hql = "from CarparkFloor where carpark.carparkid="+carparkNavigationPolyline.getCarpark().getCarparkid();
		List<CarparkFloor> floors = carparkFloorService.getByHql(hql);
		ActionContext.getContext().getValueStack().set("floors",floors );
		return "toUpdate";
	}
	public String add() throws Exception {
		// TODO Auto-generated method stub
		carparkNavigationPolylineService.add(carparkNavigationPolyline);
		saveLog("添加：【"+carparkNavigationPolyline.getCarpark().getName()+"】ID:"+carparkNavigationPolyline.getGid());
		return forward("/carparkNavigationPolyline_list");
	}
	public String update() throws Exception {
		// TODO Auto-generated method stub
		carparkNavigationPolylineService.update(carparkNavigationPolyline);
		saveLog("修改：【"+carparkNavigationPolyline.getCarpark().getName()+"】ID:"+carparkNavigationPolyline.getGid());
		return forward("/carparkNavigationPolyline_list");
	}
	public String delete(){
		carparkNavigationPolylineService.delete(getIds());
		saveLog("删除ID："+getIds());
		return forward("/carparkNavigationPolyline_list");
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
		String messageString = carparkNavigationPolylineService.importExcel(excelFile);
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
		return "停车场导航路线";
	}

	public CarparkNavigationPolyline getCarparkNavigationPolyline() {
		return carparkNavigationPolyline;
	}

	public void setCarparkNavigationPolyline(
			CarparkNavigationPolyline carparkNavigationPolyline) {
		this.carparkNavigationPolyline = carparkNavigationPolyline;
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

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
import com.parkbobo.model.CarparkCityPolyline;
import com.parkbobo.model.CarparkFloor;
import com.parkbobo.model.CarparkMarkerPoint;
import com.parkbobo.model.CarparkShopPolygon;
import com.parkbobo.service.CarparkFloorService;
import com.parkbobo.service.CarparkMarkerPointService;
import com.parkbobo.service.CarparkShopPolygonService;
import com.parkbobo.utils.Configuration;
import com.parkbobo.utils.PageBean;

@Controller("carparkShopPolygonAction")
@Scope("prototype")
public class CarparkShopPolygonAction extends BaseAction {
	@Resource(name="carparkShopPolygonService")
	private CarparkShopPolygonService carparkShopPolygonService;
	private CarparkShopPolygon carparkShopPolygon;
	@Resource(name="carparkFloorService")
	private CarparkFloorService carparkFloorService;
	private String formType;
	private File upload;
	private String uploadFileName;
	public String list() throws Exception {
		Manager manager = (Manager) request.getSession().getAttribute("loginManager");
		String area = manager.getArea();
		if(area!=null && "总部".equals(area)){
			String hqlString = "from CarparkShopPolygon where 1=1 ";
			if(formType!=null&&formType.equals("carpark")){
				String carparkString =new String(carparkShopPolygon.getCarpark().getName().getBytes("iso-8859-1"), "UTF-8"); 
				hqlString+=" and carpark.name like '%"+carparkString+"%'";
				carparkShopPolygon.getCarpark().setName(carparkString);
			}else{
				if(carparkShopPolygon!=null){
					hqlString+=" and upper(name) like '%"+carparkShopPolygon.getName().trim().toUpperCase()+"%'" +
					" and carpark.name like '%"+carparkShopPolygon.getCarpark().getName().trim()+"%'";
				}
			}
			hqlString+="order by gid desc";
			PageBean<CarparkShopPolygon> pageBean  = carparkShopPolygonService.page(hqlString,getPageSize(),getPage());
			ActionContext.getContext().getValueStack().set("page", pageBean);
			
		}else if(area!=null && !"".equals(area)){
			String hqlString = "from CarparkShopPolygon where carpark.city like '%" + area +"%' ";
			if(formType!=null&&formType.equals("carpark")){
				String carparkString =new String(carparkShopPolygon.getCarpark().getName().getBytes("iso-8859-1"), "UTF-8"); 
				hqlString+=" and carpark.name like '%"+carparkString+"%'";
				carparkShopPolygon.getCarpark().setName(carparkString);
			}else{
				if(carparkShopPolygon!=null){
					hqlString+=" and upper(name) like '%"+carparkShopPolygon.getName().trim().toUpperCase()+"%'" +
					" and carpark.name like '%"+carparkShopPolygon.getCarpark().getName().trim()+"%'";
				}
			}
			hqlString+="order by gid desc";
			PageBean<CarparkShopPolygon> pageBean  = carparkShopPolygonService.page(hqlString,getPageSize(),getPage());
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
		carparkShopPolygon = carparkShopPolygonService.get(carparkShopPolygon.getGid());
		String hql = "from CarparkFloor where carpark.carparkid="+carparkShopPolygon.getCarpark().getCarparkid();
		List<CarparkFloor> floors = carparkFloorService.getByHql(hql);
		ActionContext.getContext().getValueStack().set("floors",floors );
		return "toUpdate";
	}
	public String add() throws Exception {
		// TODO Auto-generated method stub
		carparkShopPolygonService.add(carparkShopPolygon);
		saveLog("添加：【"+carparkShopPolygon.getCarpark().getName()+"】ID:"+carparkShopPolygon.getGid());
		return forward("/carparkShopPolygon_list");
	}
	public String update() throws Exception {
		// TODO Auto-generated method stub
		carparkShopPolygonService.update(carparkShopPolygon);
		saveLog("修改：【"+carparkShopPolygon.getCarpark().getName()+"】ID:"+carparkShopPolygon.getGid());
		return forward("/carparkShopPolygon_list");
	}
	public String delete(){
		carparkShopPolygonService.delete(getIds());
		saveLog("删除ID："+getIds());
		return forward("/carparkShopPolygon_list");
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
		String messageString = carparkShopPolygonService.importExcel(excelFile);
		
		response.getWriter().print(messageString);
		return null;
	}
	@Override
	public String logModel() {
		// TODO Auto-generated method stub
		return "停车场商场房间";
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

	public CarparkShopPolygon getCarparkShopPolygon() {
		return carparkShopPolygon;
	}

	public void setCarparkShopPolygon(CarparkShopPolygon carparkShopPolygon) {
		this.carparkShopPolygon = carparkShopPolygon;
	}

	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

}

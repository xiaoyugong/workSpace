package com.parkbobo.manager.action;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.tools.ant.taskdefs.Delete;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.parkbobo.manager.model.Manager;
import com.parkbobo.model.Carpark;
import com.parkbobo.model.CarparkBerthPolygon;
import com.parkbobo.model.CarparkFloor;
import com.parkbobo.model.CarparkMarkerCategory;
import com.parkbobo.service.CarparkBerthPolygonService;
import com.parkbobo.service.CarparkFloorService;
import com.parkbobo.service.CarparkService;
import com.parkbobo.utils.Configuration;
import com.parkbobo.utils.PageBean;
import com.vividsolutions.jts.geom.MultiPolygon;

@Controller("carparkBerthPolygonAction")
@Scope("prototype")
public class CarparkBerthPolygonAction extends BaseAction {

	private CarparkBerthPolygon carparkBerthPolygon;
	@Resource(name="carparkBerthPolygonService")
	private CarparkBerthPolygonService berthPolygonService;
	@Resource(name="carparkService")
	private CarparkService carparkService;
	@Resource(name="carparkFloorService")
	private CarparkFloorService carparkFloorService;
	private String formType;
	private File upload;
	private String uploadFileName;
	public String list() throws Exception {
		Manager manager = (Manager) request.getSession().getAttribute("loginManager");
		String area = manager.getArea();
		System.out.println(area);
		if(area!=null && "总部".equals(area)){
			String hqlString = "from CarparkBerthPolygon where 1=1 ";
			System.out.println(formType);
			if(formType!=null&&formType.equals("carpark")){
				String carparkString =new String(carparkBerthPolygon.getCarpark().getName().getBytes("iso-8859-1"), "UTF-8"); 
				hqlString+=" and carpark.name like '%"+carparkString+"%'";
				carparkBerthPolygon.getCarpark().setName(carparkString);
			}else{
				if(carparkBerthPolygon!=null){
					hqlString+=" and upper(name) like '%"+carparkBerthPolygon.getName().trim().toUpperCase()+"%'" +
					" and carpark.name like '%"+carparkBerthPolygon.getCarpark().getName().trim()+"%'";
				}
			}
			hqlString+=" order by gid desc";
			PageBean<CarparkBerthPolygon> page = berthPolygonService.page(hqlString,getPageSize(),getPage());
			ActionContext.getContext().getValueStack().set("page", page);
		}else if(area!=null && !"".equals(area)){
			String hqlString = "from CarparkBerthPolygon where carpark.city like '%" + area +"%' ";
			if(formType!=null&&formType.equals("carpark")){
				String carparkString =new String(carparkBerthPolygon.getCarpark().getName().getBytes("iso-8859-1"), "UTF-8"); 
				hqlString+=" and carpark.name like '%"+carparkString+"%'";
				carparkBerthPolygon.getCarpark().setName(carparkString);
			}else{
				if(carparkBerthPolygon!=null){
					hqlString+=" and upper(name) like '%"+carparkBerthPolygon.getName().trim().toUpperCase()+"%'" +
					" and carpark.name like '%"+carparkBerthPolygon.getCarpark().getName().trim()+"%'";
				}
			}
			hqlString+=" order by gid desc";
			PageBean<CarparkBerthPolygon> page = berthPolygonService.page(hqlString,getPageSize(),getPage());
			ActionContext.getContext().getValueStack().set("page", page);
		}
		return "list";
	}
	
	public String toAdd(){
		
		return "toAdd";
	}

	public String toUpdate() {
		// TODO Auto-generated method stub
		carparkBerthPolygon = berthPolygonService.get(this.carparkBerthPolygon.getGid());
		String hql = "from CarparkFloor where carpark.carparkid="+carparkBerthPolygon.getCarpark().getCarparkid();
		List<CarparkFloor> floors = carparkFloorService.getByHql(hql);
		ActionContext.getContext().getValueStack().set("floors",floors );
		return "toUpdate";
	}
	public String add() {
		carparkBerthPolygon.setCarpark(carparkService.getById(carparkBerthPolygon.getCarpark().getCarparkid()));
		berthPolygonService.add(carparkBerthPolygon);
		return forward("/carparkBerthPolygon_list");
	}
	public String update() {
		CarparkBerthPolygon berthPolygon = berthPolygonService.get(this.carparkBerthPolygon.getGid());
		berthPolygon.setGeom(null);
		berthPolygon.setBordercolor(this.carparkBerthPolygon.getBordercolor());
		berthPolygon.setClickBordercolor(this.carparkBerthPolygon.getClickBordercolor());
		berthPolygon.setClickColor(this.carparkBerthPolygon.getClickColor());
		berthPolygon.setColor(this.carparkBerthPolygon.getColor());
		berthPolygon.setFontColor(this.carparkBerthPolygon.getFontColor());
		berthPolygon.setFontItalic(this.carparkBerthPolygon.getFontItalic());
		berthPolygon.setFontSize(this.carparkBerthPolygon.getFontSize());
		berthPolygon.setFontWeight(this.carparkBerthPolygon.getFontWeight());
		berthPolygon.setHeight(this.carparkBerthPolygon.getHeight());
		berthPolygon.setWidth(this.carparkBerthPolygon.getWidth());
		berthPolygon.setMemo(this.carparkBerthPolygon.getMemo());
		berthPolygon.setName(this.carparkBerthPolygon.getName());
		berthPolygon.setFloorid(this.carparkBerthPolygon.getFloorid());
		berthPolygon.setGeometry(this.carparkBerthPolygon.getGeometry());
		berthPolygonService.update(berthPolygon);
		
		saveLog("修改:"+berthPolygon.getCarpark().getName()+",Id:"+berthPolygon.getGid());
		return forward("/carparkBerthPolygon_list");
	}
	public String delete() {
		
		berthPolygonService.delete(getIds());
		saveLog("删除,Id:"+getIds());
		return forward("/carparkBerthPolygon_list");
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
		String messageString = berthPolygonService.importExcel(excelFile);
		
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

	public CarparkBerthPolygon getCarparkBerthPolygon() {
		return carparkBerthPolygon;
	}



	public void setCarparkBerthPolygon(CarparkBerthPolygon carparkBerthPolygon) {
		this.carparkBerthPolygon = carparkBerthPolygon;
	}
	@Override
	public String logModel() {
		// TODO Auto-generated method stub
		return "停车场车位";
	}

	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}






}

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
import com.parkbobo.model.CarparkCityPolyline;
import com.parkbobo.model.CarparkFloor;
import com.parkbobo.model.CarparkFuzhuPolygon;
import com.parkbobo.service.CarparkCityPolylineService;
import com.parkbobo.service.CarparkFloorService;
import com.parkbobo.service.CarparkFuzhuPolygonService;
import com.parkbobo.utils.Configuration;
import com.parkbobo.utils.PageBean;

@Controller("carparkFuzhuPolygonAction")
@Scope("prototype")
public class CarparkFuzhuPolygonAction extends BaseAction{
	@Resource(name="carparkFuzhuPolygonService")
	private CarparkFuzhuPolygonService carparkFuzhuPolygonService;
	private CarparkFuzhuPolygon carparkFuzhuPolygon;
	@Resource(name="carparkFloorService")
	private CarparkFloorService carparkFloorService;
	private String formType;
	private File upload;
	private String uploadFileName;
	public String list() throws UnsupportedEncodingException  {
		Manager manager = (Manager) request.getSession().getAttribute("loginManager");
		String area = manager.getArea();
		if(area!=null && "总部".equals(area)){
			String hqlString = "from CarparkFuzhuPolygon where 1=1 ";
			if(formType!=null&&formType.equals("carpark")){
				String carparkString =new String(carparkFuzhuPolygon.getCarpark().getName().getBytes("iso-8859-1"), "UTF-8"); 
				hqlString+=" and carpark.name like '%"+carparkString+"%'";
				carparkFuzhuPolygon.getCarpark().setName(carparkString);
			}else{
				if(carparkFuzhuPolygon!=null){
					hqlString+=" and upper(name) like '%"+carparkFuzhuPolygon.getName().trim().toUpperCase()+"%'" +
					" and carpark.name like '%"+carparkFuzhuPolygon.getCarpark().getName().trim()+"%'";
				}
			}
			hqlString+="order by gid desc";
			PageBean<CarparkFuzhuPolygon> pageBean  = carparkFuzhuPolygonService.page(hqlString,getPageSize(),getPage());
			ActionContext.getContext().getValueStack().set("page", pageBean);
		}else if(area!=null && !"".equals(area)){
			String hqlString = "from CarparkFuzhuPolygon where carpark.city like '%" + area +"%' ";
			if(formType!=null&&formType.equals("carpark")){
				String carparkString =new String(carparkFuzhuPolygon.getCarpark().getName().getBytes("iso-8859-1"), "UTF-8"); 
				hqlString+=" and carpark.name like '%"+carparkString+"%'";
				carparkFuzhuPolygon.getCarpark().setName(carparkString);
			}else{
				if(carparkFuzhuPolygon!=null){
					hqlString+=" and upper(name) like '%"+carparkFuzhuPolygon.getName().trim().toUpperCase()+"%'" +
					" and carpark.name like '%"+carparkFuzhuPolygon.getCarpark().getName().trim()+"%'";
				}
			}
			hqlString+="order by gid desc";
			PageBean<CarparkFuzhuPolygon> pageBean  = carparkFuzhuPolygonService.page(hqlString,getPageSize(),getPage());
			ActionContext.getContext().getValueStack().set("page", pageBean);
		}
		return "list";
	}
	
	public String toAdd() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("1123456");
		return "toAdd";
	}
	public String toUpdate() throws Exception {
		// TODO Auto-generated method stub
		carparkFuzhuPolygon = carparkFuzhuPolygonService.get(carparkFuzhuPolygon.getGid());
		String hql = "from CarparkFloor where carpark.carparkid="+carparkFuzhuPolygon.getCarpark().getCarparkid();
		List<CarparkFloor> floors = carparkFloorService.getByHql(hql);
		ActionContext.getContext().getValueStack().set("floors",floors );
		return "toUpdate";
	}
	public String add() throws Exception {
		// TODO Auto-generated method stub
		carparkFuzhuPolygonService.add(carparkFuzhuPolygon);
		saveLog("添加：【"+carparkFuzhuPolygon.getCarpark().getName()+"】ID:"+carparkFuzhuPolygon.getGid());

		return forward("/carparkFuzhuPolygon_list");
	}
	public String update() throws Exception {
		// TODO Auto-generated method stub
		carparkFuzhuPolygonService.update(carparkFuzhuPolygon);
		saveLog("修改：【"+carparkFuzhuPolygon.getCarpark().getName()+"】ID:"+carparkFuzhuPolygon.getGid());
		return forward("/carparkFuzhuPolygon_list");
	}
	public String delete(){
		carparkFuzhuPolygonService.delete(getIds());
		saveLog("删除ID：【"+getIds()+"】");
		return forward("/carparkFuzhuPolygon_list");
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
		String messageString = carparkFuzhuPolygonService.importExcel(excelFile);
		
		response.getWriter().print(messageString);
		return null;
	}
	
	@Override
	public String logModel() {
		// TODO Auto-generated method stub
		return "停车场辅助图层";
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

	public CarparkFuzhuPolygon getCarparkFuzhuPolygon() {
		return carparkFuzhuPolygon;
	}

	public void setCarparkFuzhuPolygon(CarparkFuzhuPolygon carparkFuzhuPolygon) {
		this.carparkFuzhuPolygon = carparkFuzhuPolygon;
	}

	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

}

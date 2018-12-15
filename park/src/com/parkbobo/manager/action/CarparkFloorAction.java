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
import com.parkbobo.model.CarparkCategory;
import com.parkbobo.model.CarparkFloor;
import com.parkbobo.service.CarparkFloorService;
import com.parkbobo.utils.Configuration;
import com.parkbobo.utils.PageBean;


@Controller("carparkFloorAction")
@Scope("prototype")
public class CarparkFloorAction extends BaseAction {

	private CarparkFloor carparkFloor;
	@Resource(name="carparkFloorService")
	private CarparkFloorService carparkFloorService;
	private File upload;
	private String uploadFileName;
	public String list() throws Exception {
		// TODO Auto-generated method stub
		Manager manager = (Manager) request.getSession().getAttribute("loginManager");
		String area = manager.getArea();
		if(area!=null && "总部".equals(area)){
			String sqlString = "from CarparkFloor where 0=0 ";
			if(carparkFloor!=null&&carparkFloor.getCarpark()!=null)
				sqlString+=" and carpark.name like '%"+carparkFloor.getCarpark().getName().trim()+"%'";
			if(carparkFloor!=null)
				sqlString+=" and Lower(name) like '%"+carparkFloor.getName().trim().toLowerCase()+"%'";
			sqlString+=" order by floorid desc";
			PageBean<CarparkFloor> pageBean = carparkFloorService.page(sqlString,getPageSize(),getPage());
			ActionContext.getContext().getValueStack().set("page", pageBean);
			
		}else if(area!=null && !"".equals(area)){
			String sqlString = "from CarparkFloor where carpark.city like '%" + area +"%' ";
			if(carparkFloor!=null&&carparkFloor.getCarpark()!=null)
				sqlString+=" and carpark.name like '%"+carparkFloor.getCarpark().getName().trim()+"%'";
			if(carparkFloor!=null)
				sqlString+=" and Lower(name) like '%"+carparkFloor.getName().trim().toLowerCase()+"%'";
			sqlString+=" order by floorid desc";
			PageBean<CarparkFloor> pageBean = carparkFloorService.page(sqlString,getPageSize(),getPage());
			ActionContext.getContext().getValueStack().set("page", pageBean);

		}
		return "list";
	}
	public String getFloorByCarparkId() throws IOException{
		response.setContentType("text/javascript;charset=utf-8");
		String hql = "from CarparkFloor where carpark.carparkid="+carparkFloor.getCarpark().getCarparkid();
		List<CarparkFloor> list = carparkFloorService.getByHql(hql);
		if(list.size()==0){
			response.getWriter().print("[]");
			return null;
		}
		StringBuffer sbBuffer = new StringBuffer("[");
		for(CarparkFloor floor:list){
			sbBuffer.append("{\"id\":"+floor.getFloorid()+",\"name\":\""+floor.getName()+"\"},");
		}
		sbBuffer = sbBuffer.deleteCharAt(sbBuffer.length()-1);
		sbBuffer.append("]");
		response.getWriter().print(sbBuffer);
		return null;
	}
	public String toImportExcel(){
		return "toImportExcel";
	}
	public String importExcel() throws IOException{
		
		String savePath = Configuration.getInstance().getValue("attachedPath")
		+ "exceltemp" + System.getProperty("file.separator") ;
		String newFileName = UUID.randomUUID().toString() + "." + getUploadFileName().substring(getUploadFileName().indexOf(".")+1).toLowerCase();
		File excelFile = new File(newFileName);
		FileUtils.copyFile(upload, excelFile);
		String messageString = carparkFloorService.importExcel(excelFile);
		
		response.getWriter().print(messageString);
		return null;
	}
	public String toSave(){
		if(carparkFloor!=null){
			carparkFloor = carparkFloorService.get(carparkFloor.getFloorid());
		}
		return "toSave";
	}
	
	public String update() {
		// TODO Auto-generated method stub
		
		carparkFloorService.update(carparkFloor);
		saveLog("修改：【"+carparkFloor.getCarpark().getName()+"】ID:"+carparkFloor.getFloorid());
		return forward("/carparkFloor_list");
	}
	public String add(){
		carparkFloorService.add(carparkFloor);
		saveLog("添加：【"+carparkFloor.getCarpark().getName()+"】ID:"+carparkFloor.getFloorid());
		return forward("/carparkFloor_list");
	}
	public String delete(){
		carparkFloorService.delete(getIds());
		saveLog("删除ID:"+getIds());
		return forward("/carparkFloor_list");
	}
	@Override
	public String logModel() {
		// TODO Auto-generated method stub
		return "停车场楼层";
	}


	public CarparkFloor getCarparkFloor() {
		return carparkFloor;
	}

	public void setCarparkFloor(CarparkFloor carparkFloor) {
		this.carparkFloor = carparkFloor;
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

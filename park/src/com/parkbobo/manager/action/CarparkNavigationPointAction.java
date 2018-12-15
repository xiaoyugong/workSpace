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
import com.parkbobo.model.Carpark;
import com.parkbobo.model.CarparkFloor;
import com.parkbobo.model.CarparkNavigationPoint;
import com.parkbobo.service.CarparkFloorService;
import com.parkbobo.service.CarparkNavigationPointService;
import com.parkbobo.service.CarparkService;
import com.parkbobo.utils.Configuration;
import com.parkbobo.utils.PageBean;

@Controller("carparkNavigationPointAction")
@Scope("prototype")
public class CarparkNavigationPointAction extends BaseAction {
	private PageBean<CarparkNavigationPoint> carparkNavigationPointPage;
	private CarparkNavigationPoint carparkNavigationPoint;
	@Resource(name="carparkFloorService")
	private CarparkFloorService carparkFloorService;
	@Resource(name="carparkNavigationPointService")
	private CarparkNavigationPointService carparkNavigationPointService;
	@Resource(name="carparkService")
	private CarparkService carparkService;
	private Carpark carpark;
	private String formType;
	private File upload;
	private String uploadFileName;
	public String list()
	{
		Manager manager = (Manager) request.getSession().getAttribute("loginManager");
		String area = manager.getArea();
		if(area!=null && "总部".equals(area)){
			String hqlString = "from CarparkNavigationPoint as a where 1=1 " ;
			if(carparkNavigationPoint != null)
			{
				if(carparkNavigationPoint.getCarparkid() != null && !carparkNavigationPoint.getCarparkid().equals(""))
				{
					hqlString += " and a.carparkid =" + carparkNavigationPoint.getCarparkid();
				}
			}
			hqlString += "order by gid desc ";
			carparkNavigationPointPage = carparkNavigationPointService.page(hqlString, getPageSize(), getPage());
			
		}else if(area!=null && !"".equals(area)){
			String hqlString = "from CarparkNavigationPoint as a where a.carpark.city like '%" + area +"%' ";
			if(carparkNavigationPoint != null)
			{
				if(carparkNavigationPoint.getCarparkid() != null && !carparkNavigationPoint.getCarparkid().equals(""))
				{
					hqlString += " and a.carparkid =" + carparkNavigationPoint.getCarparkid();
				}
			}
			hqlString += "order by gid desc ";
			carparkNavigationPointPage = carparkNavigationPointService.page(hqlString, getPageSize(), getPage());

		}
		return "list";
	}
	
	public String toAdd() throws Exception {
		// TODO Auto-generated method stub
		return "toAdd";
	}
	public String toUpdate() throws Exception {
		// TODO Auto-generated method stub
		carparkNavigationPoint = carparkNavigationPointService.get(this.carparkNavigationPoint.getGid());
		String hql = "from CarparkFloor where carpark.carparkid="+carparkNavigationPoint.getCarparkid();
		carpark = carparkService.getById(carparkNavigationPoint.getCarparkid());
		List<CarparkFloor> floors = carparkFloorService.getByHql(hql);
		ActionContext.getContext().getValueStack().set("floors",floors );
		return "toUpdate";
	}
	public String add() throws Exception {
		// TODO Auto-generated method stub
		System.out.println(carparkNavigationPoint.getGid());
		carparkNavigationPointService.add(carparkNavigationPoint);
		saveLog("添加：【车场id，"+carparkNavigationPoint.getCarparkid()+"】ID:"+carparkNavigationPoint.getGid());
		return forward("/carparkNavigationPoint_list");
	}
	public String update() throws Exception {
		// TODO Auto-generated method stub
		carparkNavigationPointService.update(carparkNavigationPoint);
		saveLog("修改：【车场"+carpark.getName()+"】ID:"+carparkNavigationPoint.getGid());
		return forward("/carparkNavigationPoint_list");
	}
	public String delete(){
		carparkNavigationPointService.delete(getIds());
		saveLog("删除ID："+getIds());
		return forward("/carparkNavigationPoint_list");
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
		String messageString = carparkNavigationPointService.importExcel(excelFile);
		
		response.getWriter().print(messageString);
		return null;
	}
	@Override
	public String logModel() {
		// TODO Auto-generated method stub
		return "跨楼层导航点";
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

	public CarparkNavigationPoint getCarparkNavigationPoint() {
		return carparkNavigationPoint;
	}

	public void setCarparkNavigationPoint(
			CarparkNavigationPoint carparkNavigationPoint) {
		this.carparkNavigationPoint = carparkNavigationPoint;
	}

	public Carpark getCarpark() {
		return carpark;
	}

	public void setCarpark(Carpark carpark) {
		this.carpark = carpark;
	}


	public PageBean<CarparkNavigationPoint> getCarparkNavigationPointPage() {
		return carparkNavigationPointPage;
	}


	public void setCarparkNavigationPointPage(
			PageBean<CarparkNavigationPoint> carparkNavigationPointPage) {
		this.carparkNavigationPointPage = carparkNavigationPointPage;
	}


}

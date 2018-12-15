package com.parkbobo.manager.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.naming.directory.DirContext;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


@Controller("downloadAction")
@Scope("prototype")
public class DownloadAction extends BaseAction{

	private String importTemplateType;
	private String downloadFileName;
	
	public InputStream getInputStream() throws FileNotFoundException, UnsupportedEncodingException{
		
		if(importTemplateType.equals("carpark")){
			downloadFileName="停车场.xlsx";
		}if(importTemplateType.equals("carparkBackgroundPolygon")){//停车场背景底图
			downloadFileName="背景底图.xlsx";
		}if(importTemplateType.equals("carparkFuzhuPolygon")){//停车场辅助图层
			downloadFileName="辅助图层.xlsx";
		}if(importTemplateType.equals("carparkNavigationPoint")){//停车场跨楼层导航点
			downloadFileName="跨楼层导航点 .xlsx";
		}if(importTemplateType.equals("carparkMarkerPoint")){//停车场标注
			downloadFileName="停车场标注.xlsx";
		}if(importTemplateType.equals("carparkNavigationPolyline")){//停车场导航路线
			downloadFileName="停车场导航路线.xlsx";
		}if(importTemplateType.equals("carparkRoadPolyline")){//停车场内部道路
			downloadFileName="停车场内部道路.xlsx";
		}if(importTemplateType.equals("carparkShopPolygon")){//停车场商场房间
			downloadFileName="停车场商场房间.xlsx";
		}if(importTemplateType.equals("carparkModelPolygon")){//停车场模型高度层
			downloadFileName="停车场高度模型.xlsx";
		}if(importTemplateType.equals("carparkEntrancePoint")){//停车场出入口
			downloadFileName="停车场出入口.xlsx";
		}if(importTemplateType.equals("carparkCityPolyline")){//停车场外部道路
			downloadFileName="停车场外部道路.xlsx";
		}if(importTemplateType.equals("carparkBerthPolygon")){//停车场车位
			downloadFileName="停车场车位.xlsx";
		}if(importTemplateType.equals("carparkFloor")){//停车场车位
			downloadFileName="停车场楼层.xlsx";;
		}
		
		InputStream inputStream = ServletActionContext.getServletContext().getResourceAsStream("importTemplate"+File.separator+downloadFileName);
		downloadFileName = new String(downloadFileName.getBytes(), "ISO8859-1");
		return inputStream;
	}
	
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		return "success";
	}
	public String getImportTemplateType() {
		return importTemplateType;
	}
	public void setImportTemplateType(String importTemplateType) {
		this.importTemplateType = importTemplateType;
	}
	@Override
	public String logModel() {
		// TODO Auto-generated method stub
		return "下载模块";
	}
	public String getDownloadFileName() {
		return downloadFileName;
	}
	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}
	
	
	
}

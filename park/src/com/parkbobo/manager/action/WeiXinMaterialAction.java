package com.parkbobo.manager.action;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.parkbobo.service.WeiXinMaterialService;
import com.parkbobo.utils.MyStringUtils;
import com.parkbobo.utils.PageBean;
import com.weixin.control.WeiXinAPI;
import com.weixin.model.Material;

/**
 * 作者:卢鸶
 * 时间:2015-4-14
 * 描述:微信素材
 */
@Controller("weiXinMaterialAction")
@Scope("prototype")
public class WeiXinMaterialAction extends BaseAction{

	private Material material ;
	@Resource(name="weiXinAPI")
	private WeiXinAPI weiXinAPI;
	@Resource(name="weiXinMaterialService")
	private WeiXinMaterialService weiXinMaterialService;
	private File upload;
	private String uploadFileName;
	private final static String MATERIALPATH=File.separator+"upload"+File.separator+"material"+File.separator;
	
	public String add() {
		String pathString = ServletActionContext.getServletContext().getRealPath(MATERIALPATH);
		File dirFile = new File(pathString);
		if(!dirFile.isDirectory()){
			dirFile.mkdirs();
		}
		try {
			String finalPathString = pathString+File.separator+MyStringUtils.getDefaultInstance().getNewFileName(uploadFileName);
			File newsFile = new File(finalPathString);
			FileUtils.copyFile(upload, newsFile);
			//上传附件到微信服务器
			Material material = weiXinAPI.addMaterial(newsFile, this.material.getType());
			
			if(material!=null){
				material.setPath(MATERIALPATH+""+newsFile.getName());
				weiXinMaterialService.add(material);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return forward("/weiXinMaterial_list");
	}
	
	public String addThumb() {
		String pathString = ServletActionContext.getServletContext().getRealPath(MATERIALPATH);
		File dirFile = new File(pathString);
		if(!dirFile.isDirectory()){
			dirFile.mkdirs();
		}
		try {
			String finalPathString = pathString+File.separator+MyStringUtils.getDefaultInstance().getNewFileName(uploadFileName);
			File newsFile = new File(finalPathString);
			FileUtils.copyFile(upload, newsFile);
			//上传附件到微信服务器
			Material material = weiXinAPI.upload(newsFile, this.material.getType());
			
			if(material!=null){
				return	responseString("{\"status\":true,\"media\":\""+material.getMedia_id()+"\"}");
			}
			return	responseString("{\"status\":false}");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String delete() {
		if(weiXinAPI.deleteMaterial(getIds())){
			
			weiXinMaterialService.delete(getIds());
			
		};
		
		return forward("/weiXinMaterial_list");
	}
	public String toAdd() {
		
		return "toAdd";
	}
	
	public String list() {
		String sql="from Material";
		PageBean<Material> materialBean = weiXinMaterialService.page(sql,getPage(),getPageSize());
		ActionContext.getContext().getValueStack().set("weiXinMaterialPage", materialBean);
		return "list";
	}
	
	
	
	
	
	
	
	
	
	public Material getMaterial() {
		return material;
	}


	public void setMaterial(Material material) {
		this.material = material;
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


	@Override
	public String logModel() {
		// TODO Auto-generated method stub
		
		
		return "微信管理";
	}

}

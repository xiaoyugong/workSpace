package com.parkbobo.manager.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.parkbobo.model.CarparkCategory;
import com.parkbobo.service.CarparkCategoryService;
import com.parkbobo.utils.PageBean;

@Controller("carparkCategoryAction")
@Scope("prototype")
public class CarparkCategoryAction extends BaseAction {

	@Resource(name="carparkCategoryService")
	private CarparkCategoryService carparkCategoryService;
	private CarparkCategory carparkCategory;
	private String formType;
	public String list() throws Exception {
		// TODO Auto-generated method stub
		String sqlString = "from CarparkCategory where isDel=0 ";
		if(carparkCategory!=null)
		sqlString+=" and name like '%"+carparkCategory.getName()+"%'";
		sqlString+=" order by categoryid desc";
		PageBean<CarparkCategory> pageBean = carparkCategoryService.page(sqlString,getPageSize(),getPage());
		ActionContext.getContext().getValueStack().set("page", pageBean);
		return "list";
	}
	
	public String toSave(){
		if(carparkCategory!=null)
			carparkCategory = carparkCategoryService.get(carparkCategory.getCategoryid());
		
		return "toSave";
	}
	
	
	public String add() {
		// TODO Auto-generated method stub
		carparkCategory.setIsDel(0);
		carparkCategoryService.add(carparkCategory);
		return forward("/carparkCategory_list");
	}
	public String delete() {
		// TODO Auto-generated method stub
		carparkCategoryService.delete(getIds());
		return forward("/carparkCategory_list");
	}
	public String update() {
		// TODO Auto-generated method stub
		carparkCategory.setIsDel(0);
		carparkCategoryService.update(carparkCategory);
		return forward("/carparkCategory_list");
	}
	@Override
	public String logModel() {
		// TODO Auto-generated method stub
		return "停车场分类";
	}
	public CarparkCategory getCarparkCategory() {
		return carparkCategory;
	}
	public void setCarparkCategory(CarparkCategory carparkCategory) {
		this.carparkCategory = carparkCategory;
	}
	public String getFormType() {
		return formType;
	}
	public void setFormType(String formType) {
		this.formType = formType;
	}

}

package com.parkbobo.manager.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.parkbobo.model.CarparkReview;
import com.parkbobo.service.CarparkReviewService;
import com.parkbobo.utils.PageBean;

@Controller("carparkReviewAction")
@Scope("prototype")
public class CarparkReviewAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7099146514213494425L;
	
	private Long id;
	
	
	@Resource(name="carparkReviewService")
	private CarparkReviewService carparkReviewService;
	
	
	public String list() {
		String hqlString = "from CarparkReview where carpark.carparkid="+id+" order by reviewTime desc";
		PageBean<CarparkReview> pageBean = carparkReviewService.getPage(hqlString, getPage(), getPageSize());
		ActionContext.getContext().getValueStack().set("page", pageBean);
		System.out.println(pageBean.getAllRow());
		return "list";
	}
public String delete() {
	carparkReviewService.delete(getIds());
	
	return forward("/carparkReview_list?id="+id);
}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}
	@Override
	public String logModel() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}

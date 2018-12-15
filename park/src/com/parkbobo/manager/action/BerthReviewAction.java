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
import com.parkbobo.model.BerthReview;
import com.parkbobo.model.CarparkReview;
import com.parkbobo.service.BerthReviewService;
import com.parkbobo.service.CarparkReviewService;
import com.parkbobo.utils.PageBean;

/**
 * @author Administrator
 *车位评论
 */
@Controller("berthReviewAction")
@Scope("prototype")
public class BerthReviewAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7099146514213494425L;
	
	private Long id;
	
	
	@Resource(name="berthReviewService")
	private BerthReviewService berthReviewService;
	
	
	public String list() {
		String hqlString = "from BerthReview where berthShare.shareid="+id+" order by reviewTime desc";
		PageBean<BerthReview> pageBean = berthReviewService.getPage(hqlString, getPage(), getPageSize());
		ActionContext.getContext().getValueStack().set("page", pageBean);
		return "list";
	}
public String delete() {
	berthReviewService.delete(getIds());
	
	return forward("/berthReview_list?id="+id);
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
		return "车位评论";
	}
	
	
}

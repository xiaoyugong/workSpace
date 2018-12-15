package com.parkbobo.manager.action;

import java.io.File;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.parkbobo.service.WeiXinNewsService;
import com.parkbobo.utils.PageBean;
import com.parkbobo.utils.WebUtils;
import com.weixin.control.WeiXinAPI;
import com.weixin.model.Material;
import com.weixin.model.News;

@Controller("weiXinNewsAction")
@Scope("prototype")
public class WeiXinNewsAction extends BaseAction{

	@Resource(name="weiXinNewsService")
	private WeiXinNewsService weiXinNewsService;
	private News news;
	@Resource(name="weiXinAPI")
	private WeiXinAPI weiXinAPI;
	private String mediaType;
	public String toAdd() {
		
		return "toAdd";
	}
	public String toUpdate() {
		news = weiXinNewsService.get(news.getMedia_id());
		return "toUpdate";
	}
	
	public String update() {
		news.setIsSend("0");
		news.setContent_source_url(WebUtils.getDefaultInstance().getDeployPath()+
				"/weiXinNews_view?news.media_id="+news.getMedia_id());
		
		weiXinNewsService.update(news);
		return forward("/weiXinNews_list");
	}
	public String view() {
		news = weiXinNewsService.get(news.getMedia_id());
		return "view";
	}
	
	public String add() {
		String media = weiXinAPI.uploadNews(news);
		news.setIsSend("0");
		news.setContent_source_url(WebUtils.getDefaultInstance().getDeployPath()+
				"/weiXinNews_view?news.media_id="+media);
		if(media!=null){
			news.setMedia_id(media);
			weiXinNewsService.add(news);
			
		}
		return forward("/weiXinNews_list");
	}
	
	
	public String list() {
		String sql="from News";
		PageBean<News> materialBean = weiXinNewsService.page(sql,getPage(),getPageSize());
		ActionContext.getContext().getValueStack().set("weiXinNewsPage", materialBean);
		return "list";
	}
	
	public String send() {
		News news = weiXinNewsService.get(this.news.getMedia_id());
		news.setIsSend("1");
		
		weiXinNewsService.update(news);
		boolean b=  weiXinAPI.sendNewsToAll(news);
		if(b){
			return responseString("{\"status\":true}");
		}
		return responseString("{\"status\":false}");
	}
	
	public String delete() {
		
		weiXinNewsService.delete(getIds());
		return forward("/weiXinNews_list");
	}
	
	@Override
	public String logModel() {
		// TODO Auto-generated method stub
		return "微信图文";
	}

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}
	public String getMediaType() {
		return mediaType;
	}
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	
	
	
}

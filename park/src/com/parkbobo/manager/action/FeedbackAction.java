package com.parkbobo.manager.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.parkbobo.model.Feedback;
import com.parkbobo.service.FeedbackService;
import com.parkbobo.utils.PageBean;

@Component("feedbackAction")
@Scope("prototype")
public class FeedbackAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 829958202994685099L;
	private PageBean<Feedback> feedbackPage;
	private Long id;
	@Resource(name="feedbackService")
	private FeedbackService feedbackService;
	/**
	 * 意见反馈列表
	 * @return
	 */
	public String list()
	{
		String hql = "from Feedback as f where 1=1";
		
		hql += " order by f.posttime desc";
		feedbackPage = feedbackService.loadPage(hql, getPageSize(), getPage());
		return "list";
	}
	/**
	 * 意见反馈删除
	 * @return
	 */
	public String delete()
	{
		feedbackService.bulkDelete(getIds());
		return forward("/feedback_list");
	}
	public PageBean<Feedback> getFeedbackPage() {
		return feedbackPage;
	}

	public void setFeedbackPage(PageBean<Feedback> feedbackPage) {
		this.feedbackPage = feedbackPage;
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

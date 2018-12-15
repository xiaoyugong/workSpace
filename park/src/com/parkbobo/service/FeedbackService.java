package com.parkbobo.service;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.List;

import com.parkbobo.dao.FeedbackDao;
import com.parkbobo.model.Feedback;
import com.parkbobo.utils.PageBean;
@Component("feedbackService")
public class FeedbackService {
	@Resource(name="feedbackDaoImpl")
	private FeedbackDao feedbackDao;

	public List<Feedback> getByHql(String hql){
		return this.feedbackDao.getByHQL(hql);
	}
	public PageBean<Feedback> loadPage(String hql, int pageSize, int page) {
		return this.feedbackDao.pageQuery(hql, pageSize, page);
	}
	public void bulkDelete(String ids) {
		if(ids.length() > 0)
		{
			String[] strs = ids.split(",");
			Long[] idArr = new Long[strs.length];
			for (int i=0; i< strs.length; i++) {
				idArr[i] = Long.valueOf(strs[i]);
			}
			this.feedbackDao.bulkDelete(idArr);
		}
	}
}
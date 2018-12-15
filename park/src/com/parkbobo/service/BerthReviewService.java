package com.parkbobo.service;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.parkbobo.dao.BerthOrderDao;
import com.parkbobo.dao.BerthReviewDao;
import com.parkbobo.dao.UsersDao;
import com.parkbobo.model.BerthOrder;
import com.parkbobo.model.BerthReview;
import com.parkbobo.model.Users;
import com.parkbobo.utils.PageBean;
@Component("berthReviewService")
public class BerthReviewService {
	@Resource(name="berthReviewDaoImpl")
	private BerthReviewDao berthReviewDao;
	@Resource(name="berthOrderDaoImpl")
	private BerthOrderDao berthOrderDao;
	@Resource(name="usersDaoImpl")
	private UsersDao usersDao;

	public List<BerthReview> getByHql(String hql){
		return this.berthReviewDao.getByHQL(hql);
	}
	/**
	 * 停车场评价
	 * @param berthorderid
	 * @param userid
	 * @param rates
	 * @param decode
	 * @return
	 */
	public boolean add(Long berthorderid, String userid, Short rates,
			String content) {
		try {
			Users users = usersDao.get(userid);
			BerthOrder berthOrder = berthOrderDao.get(berthorderid);
			berthOrder.setIsReview((short) 1);
			BerthReview berthReview = new BerthReview(null, berthOrder,
					berthOrder.getBerthShare(), userid, users.getNickname(),users.getUserhead(),
					rates, content, new Date().getTime(), null);
			berthOrderDao.merge(berthOrder);
			berthReviewDao.merge(berthReview);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public PageBean<BerthReview> getPage(String hql, int page, int pageSize) {
		return berthReviewDao.pageQuery(hql, pageSize, page);
	}
	public String getByOrderid(Long berthorderid) {
		BerthReview berthReview = berthReviewDao.getUniqueByProperty("berthOrder.berthorderid", berthorderid);
		if(berthReview != null)
		{
			return berthReview.getContent();
		}
		return "";
	}
	public void delete(String ids) {
		// TODO Auto-generated method stub
		String[] arr = ids.split(",");
		Long[] newArr = new Long[arr.length];
		for(int i = 0;i<arr.length;i++){
			newArr[i] = Long.valueOf(arr[i]);
		}
		
		berthReviewDao.bulkDelete(newArr);
	}
}
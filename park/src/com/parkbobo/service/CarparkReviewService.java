package com.parkbobo.service;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.parkbobo.dao.CarparkReviewDao;
import com.parkbobo.dao.UsersDao;
import com.parkbobo.model.Carpark;
import com.parkbobo.model.CarparkReview;
import com.parkbobo.model.Users;
import com.parkbobo.utils.PageBean;
@Component("carparkReviewService")
public class CarparkReviewService {
	@Resource(name="carparkReviewDaoImpl")
	private CarparkReviewDao carparkReviewDao;
	@Resource(name="usersDaoImpl")
	private UsersDao usersDao;

	public List<CarparkReview> getByHql(String hql){
		return this.carparkReviewDao.getByHQL(hql);
	}
	/**
	 * 停车场评论
	 * @param carparkid
	 * @param userid
	 * @param decode
	 * @return
	 */
	public boolean add(Long carparkid, String userid, String content) {
		try {
			Users users = usersDao.get(userid);
			Carpark carpark = new Carpark(carparkid);
			CarparkReview carparkReview = new CarparkReview(null, carpark,
					userid, users.getNickname(),users.getUserhead(), content, new Date().getTime(),
					0L, null);
			carparkReviewDao.merge(carparkReview);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	public PageBean<CarparkReview> getPage(String hql, int page, int pageSize) {
		return carparkReviewDao.pageQuery(hql, pageSize, page);
	}
	/**
	 * 停车场评论数
	 * @param carparkid
	 * @return
	 */
	public int getReviewNum(Long carparkid) {
		List<CarparkReview> carparkReviews = carparkReviewDao.getByHQL("from CarparkReview as c where c.carpark.carparkid = " + carparkid);
		return carparkReviews.size();
	}
	public void delete(String ids) {
		// TODO Auto-generated method stub
		String[] arr = ids.split(",");
		Long[] newArr = new Long[arr.length];
		for(int i = 0;i<arr.length;i++){
			newArr[i] = Long.valueOf(arr[i]);
		}
		
		carparkReviewDao.bulkDelete(newArr);
	}
}
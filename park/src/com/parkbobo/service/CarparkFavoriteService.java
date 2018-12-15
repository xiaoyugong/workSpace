package com.parkbobo.service;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.parkbobo.dao.CarparkFavoriteDao;
import com.parkbobo.model.CarparkFavorite;
import com.parkbobo.model.CarparkFavoriteId;
import com.parkbobo.utils.PageBean;
@Component("carparkFavoriteService")
public class CarparkFavoriteService {
	@Resource(name="carparkFavoriteDaoImpl")
	private CarparkFavoriteDao carparkFavoriteDao;

	public List<CarparkFavorite> getByHql(String hql){
		return this.carparkFavoriteDao.getByHQL(hql);
	}
	/**
	 * 收藏停车场
	 * @param userid
	 * @param carparkid
	 * @return
	 */
	public boolean add(String userid, Long carparkid) {
		try {
			CarparkFavoriteId id = new CarparkFavoriteId(userid, carparkid);
			CarparkFavorite carparkFavorite = new CarparkFavorite(id,
					new Date().getTime());
			this.carparkFavoriteDao.merge(carparkFavorite);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	/**
	 * 分页查询
	 * @param hql
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public PageBean<CarparkFavorite> getPage(String hql , int page, int pageSize)
	{
		return this.carparkFavoriteDao.pageQuery(hql, pageSize, page);
	}
	/**
	 * 删除收藏
	 * @param userid
	 * @param carparkid
	 * @return
	 */
	public boolean delete(String userid, Long carparkid)
	{
		try {
			CarparkFavoriteId id = new CarparkFavoriteId(userid, carparkid);
			this.carparkFavoriteDao.delete(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	/**
	 * 停车场是否已被收藏
	 * @param userid
	 * @param carparkid
	 * @return
	 */
	public boolean isFavorite(String userid, Long carparkid) {
		String[] propertyNames = {"id.userid","id.carparkid"};
		Object[] values = {userid, carparkid};
		if(carparkFavoriteDao.existsByPropertys(propertyNames, values))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
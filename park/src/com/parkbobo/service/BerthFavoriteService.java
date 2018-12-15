package com.parkbobo.service;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.parkbobo.dao.BerthFavoriteDao;
import com.parkbobo.model.BerthFavorite;
import com.parkbobo.model.BerthFavoriteId;
import com.parkbobo.utils.PageBean;
@Component("berthFavoriteService")
public class BerthFavoriteService {
	@Resource(name="berthFavoriteDaoImpl")
	private BerthFavoriteDao berthFavoriteDao;

	public List<BerthFavorite> getByHql(String hql){
		return this.berthFavoriteDao.getByHQL(hql);
	}
	/**
	 * 删除车位收藏 
	 */
	public boolean delete(String userid, Long berthid) {
		try {
			BerthFavoriteId id = new BerthFavoriteId(berthid, userid);
			this.berthFavoriteDao.delete(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	/**
	 * 分页查询
	 */
	public PageBean<BerthFavorite> getPage(String hql, int page, int pageSize)
	{
		return this.berthFavoriteDao.pageQuery(hql, pageSize, page);
	}
	/**
	 * 收藏车位
	 * @param userid
	 * @param carparkid
	 * @return
	 */
	public boolean add(String userid, Long berthid) {
		try {
			BerthFavoriteId id = new BerthFavoriteId(berthid ,userid );
			BerthFavorite berthFavorite = new BerthFavorite(id,
					new Date().getTime());
			this.berthFavoriteDao.merge(berthFavorite);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	/**
	 * 当前用户是否已收藏该车位
	 * @param userid
	 * @param berthid
	 * @return
	 */
	public boolean isFavorite(String userid, Long berthid) {
		String[] propertyNames = {"id.userid","id.gid"};
		Object[] values = {userid, berthid};
		if(berthFavoriteDao.existsByPropertys(propertyNames, values))
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
}
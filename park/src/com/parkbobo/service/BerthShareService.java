package com.parkbobo.service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.parkbobo.dao.BerthShareDao;
import com.parkbobo.dao.UserBalanceDao;
import com.parkbobo.dao.UsersDao;
import com.parkbobo.model.BerthShare;
import com.parkbobo.model.UserBalance;
import com.parkbobo.model.Users;
import com.parkbobo.utils.PageBean;
@Component("berthShareService")
public class BerthShareService {
	@Resource(name="berthShareDaoImpl")
	private BerthShareDao berthShareDao;
	@Resource(name="usersDaoImpl")
	private UsersDao usersDao;
	@Resource(name="userBalanceDaoImpl")
	private UserBalanceDao userBalanceDao;
	public List<BerthShare> getByHql(String hql){
		return this.berthShareDao.getByHQL(hql);
	}
	/**
	 * 分页查询
	 * @param string
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public PageBean<BerthShare> getPage(String hql, int page, int pageSize) {
		return this.berthShareDao.pageQuery(hql, pageSize, page);
	}
	/**
	 * 取消车位发布
	 * @param shareid
	 * @param userid
	 * @return
	 */
	public boolean close(Long shareid, String userid) {
		String[] propertyNames = {"shareid", "users.userid"};
		Object[] values = {shareid, userid};
		BerthShare berthShare = this.berthShareDao.getUniqueByPropertys(propertyNames, values);
		Users users = usersDao.get(userid);
		if(berthShare != null && berthShare.getSharenum().equals(berthShare.getEmptynum()))
		{
			berthShare.setIsClose((short)1);
			UserBalance userBalance = new UserBalance(null, users, null, 0, 1, 
					berthShare.getEnstopDeposit(),
					users.getBalance() + berthShare.getEnstopDeposit(), 
					"平台退还暂扣保证金：" + String.format("%.2f", berthShare.getEnstopDeposit()/100f) + "元",
					new Date().getTime(), 1, null, null, null);
			users.setBalance(users.getBalance() + berthShare.getEnstopDeposit());
			users.setDeposit(users.getDeposit() - berthShare.getEnstopDeposit());
			usersDao.merge(users);
			userBalanceDao.save(userBalance);
			this.berthShareDao.merge(berthShare);
			return true;
		}
		else
		{
			return false;
		}
	}
	/**
	 * 车主查看车位分享详情
	 * @param shareid
	 * @return
	 */
	public BerthShare getById(Long shareid) {
		return this.berthShareDao.get(shareid);
	}
	/**
	 * 查询可预定分页
	 * @param carparkid
	 * @param page
	 * @param pageSize
	 * @return
	 *  分享起始时间 A startTime
	 *  分享结束时间 B endTime
	 *  重复日期 C
	 *  当前时间 D  HH:mm:ss
	 *  当前时间后1小时 E HH:mm:ss
	 *  服务器当前星期 F  2 3 4 5 6 7 1 对应周一到周日
	 *  当前星期前一天 G
	 *  if( F == 1)G=7
	 *  else G = F-1
		
		//查询当前可预定车位分享信息，可提前一小时
		select * from table where ((重复日期 like '%," + F + ",%' and A < B  and E >= A and D <= B) //正常时间段
			or (重复日期 like '%," + F + ",%' and A > B and E >= A)//跨夜切分享重复日期问当前星期
			or (重复日期 like '%," + G + ",%' and A > B and D <= B))//跨夜的重复日期是前一天
		        an 空闲数量 > 0 order by beforePrice asc;
	 * @throws ParseException 
	 */
	public PageBean<BerthShare> getTodayShare(Long carparkid, int page,
			int pageSize) throws ParseException {
		String D, E, hql;
		int F, G;
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		Date date = new Date();
		D = sdf.format(date);
		if(sdf.parse(D).getTime() > 54000000)//当前时间大于23:00:00
		{
			E = "24:00:00";
		}
		else
		{
			E = sdf.format(new Date(date.getTime() + 1000L * 60 * 60));
		}
		F = cal.get(Calendar.DAY_OF_WEEK);
		if( F == 1)G = 7;
		else G = F -1;
        
		hql = "from BerthShare as b where ((b.repeatDate like '%," + F + ",%' and b.startTime = b.endTime) " + // 全天
				" or (b.repeatDate like '%," + F + ",%' and b.startTime < b.endTime and '" + E + "' >= b.startTime and '" + D + "' <= b.endTime) " + //当天没有跨夜
				" or (b.repeatDate like '%," + F + ",%' and b.startTime > b.endTime and '" + E + "' >= b.startTime) " + //跨夜的头天
				" or (b.repeatDate like '%," + G + ",%' and b.startTime > b.endTime and '" + D + "' <= b.endTime)) " + //跨夜的第二天
				" and b.emptynum > 0 and b.carparkid = " + carparkid + " and b.isDel = 0 and b.isClose = 0 order by b.beforePrice asc";
		return this.berthShareDao.pageQuery(hql, pageSize, page);
	}
	/**
	 * 车位主删除发布
	 * @param shareid
	 * @param userid
	 * @return
	 */
	public boolean delete(Long shareid, String userid) {
		String[] propertyNames = {"shareid", "users.userid"};
		Object[] values = {shareid, userid};
		BerthShare berthShare = this.berthShareDao.getUniqueByPropertys(propertyNames, values);
		if(berthShare != null)
		{
			berthShare.setIsDel((short) 1);
			this.berthShareDao.merge(berthShare);
			return true;
		}
		else
		{
			return false;
		}
	}
	/**
	 * 微地图已被入驻分享车位
	 * @param carparkid 停车场ID
	 * @param floorid 楼层ID
	 * @return
	 */
	public List<BerthShare> getMapAlreadyShare(Long carparkid, Long floorid)
	{
		String hql = "from BerthShare as b where b.carparkid = " + carparkid + 
				" and b.berthid is not null and b.floorid = " + floorid + " and b.isDel = 0 order by b.beforePrice asc";
		return  this.berthShareDao.getByHQL(hql);
	}
	/**
	 * 微地图当前时间可预定车位
	 * @param carparkid 停车场ID
	 * @param floorid 楼层ID
	 * @return
	 * @throws ParseException 
	 */
	public List<BerthShare> getMapNowShare(Long carparkid, Long floorid) throws ParseException {
		String D, E, hql;
		int F, G;
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		Date date = new Date();
		D = sdf.format(date);
		if(sdf.parse(D).getTime() > 54000000)//当前时间大于23:00:00
		{
			E = "24:00:00";
		}
		else
		{
			E = sdf.format(new Date(date.getTime() + 1000L * 60 * 60));
		}
		F = cal.get(Calendar.DAY_OF_WEEK);
		if( F == 1)G = 7;
		else G = F -1;
        hql = "from BerthShare as b where ((b.repeatDate like '%," + F + ",%' and b.startTime = b.endTime) " + // 全天
        		" or (b.repeatDate like '%," + F + ",%' and b.startTime < b.endTime and '" + E + "' >= b.startTime and '" + D + "' <= b.endTime) " + //当天没有跨夜
        		" or (b.repeatDate like '%," + F + ",%' and b.startTime > b.endTime and '" + E + "' >= b.startTime) " + //跨夜的头天
        		" or (b.repeatDate like '%," + G + ",%' and b.startTime > b.endTime and '" + D + "' <= b.endTime)) " + //跨夜的第二天
        		" and b.carparkid = " + carparkid + " and b.berthid is not null and b.emptynum  > 0 and b.floorid = " + floorid + " and b.isDel = 0 and b.isClose = 0 order by b.beforePrice asc";
		return this.berthShareDao.getByHQL(hql);
	}
	/**
	 * 车位分享时间是否在当前时间段内且能被预订
	 * @param shareid
	 * @return
	 * @throws ParseException 
	 */
	public boolean getEnable(Long shareid) throws ParseException
	{
		String D, E, hql;
		int F, G;
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		Date date = new Date();
		D = sdf.format(date);
		if(sdf.parse(D).getTime() > 54000000)//当前时间大于23:00:00
		{
			E = "24:00:00";
		}
		else
		{
			E = sdf.format(new Date(date.getTime() + 1000L * 60 * 60));
		}
		F = cal.get(Calendar.DAY_OF_WEEK);
		if( F == 1)G = 7;
		else G = F -1;
		hql = "from BerthShare as b where ((b.repeatDate like '%," + F + ",%' and b.startTime = b.endTime) " + // 全天
	 		" or (b.repeatDate like '%," + F + ",%' and b.startTime < b.endTime and '" + E + "' >= b.startTime and '" + D + "' <= b.endTime) " + //当天没有跨夜
	 		" or (b.repeatDate like '%," + F + ",%' and b.startTime > b.endTime and '" + E + "' >= b.startTime) " + //跨夜的头天
	 		" or (b.repeatDate like '%," + G + ",%' and b.startTime > b.endTime and '" + D + "' <= b.endTime)) " + //跨夜的第二天
	 		" and b.shareid = " + shareid + " and b.emptynum  > 0 and b.isDel = 0 and b.isClose = 0 ";
		if(berthShareDao.getByHQL(hql).size() > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
	/**
	 *  获取停车场可预定数
	 * @param carparkid
	 * @return
	 * @throws ParseException 
	 */
	public int getEnableOrderNum(Long carparkid) throws ParseException {
		int b = 0;
		String D, E, hql;
		int F, G;
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		Date date = new Date();
		D = sdf.format(date);
		if(sdf.parse(D).getTime() > 54000000)//当前时间大于23:00:00
		{
			E = "24:00:00";
		}
		else
		{
			E = sdf.format(new Date(date.getTime() + 1000L * 60 * 60));
		}
		F = cal.get(Calendar.DAY_OF_WEEK);
		if( F == 1)G = 7;
		else G = F -1;
		hql = "from BerthShare as b where ((b.repeatDate like '%," + F + ",%' and b.startTime = b.endTime) " + // 全天
				" or (b.repeatDate like '%," + F + ",%' and b.startTime < b.endTime and '" + E + "' >= b.startTime and '" + D + "' <= b.endTime) " + //当天没有跨夜
				" or (b.repeatDate like '%," + F + ",%' and b.startTime > b.endTime and '" + E + "' >= b.startTime) " + //跨夜的头天
				" or (b.repeatDate like '%," + G + ",%' and b.startTime > b.endTime and '" + D + "' <= b.endTime)) " + //跨夜的第二天
				" and b.carparkid = " + carparkid + " and b.emptynum  > 0 and b.isDel = 0 and b.isClose = 0 order by b.beforePrice asc";
		List<BerthShare> berthShares = berthShareDao.getByHQL(hql);
		for (BerthShare berthShare : berthShares) {
			b += berthShare.getEmptynum();
		}
		return b;
	}
	public PageBean<BerthShare> loadPage(String hql, int pageSize, int page) {
		// TODO Auto-generated method stub
		return this.getPage(hql, page, pageSize);
	}
	public void update(BerthShare berthShare) {
		// TODO Auto-generated method stub
		this.berthShareDao.update(berthShare);
	}
	public void deleteByHql(String ids) {
		// TODO Auto-generated method stub
		if(!"".equals(ids.trim())){
			String sqlString = "update BerthShare set isDel=1 where shareid in ("+ids+")";
			this.berthShareDao.deleteByHql(sqlString);
		}
		
	}
}
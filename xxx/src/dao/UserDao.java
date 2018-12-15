package dao;

import java.util.List;
import java.util.Map;

import model.User;

public interface UserDao {
	/**
	 *  添加单个用户
	 * @param user
	 */
	public void add(User user);
	/**
	 * 删除单个用户
	 * @param user
	 */
	public void delete(User user);
	
	/**
	 * 检查是否含有用户
	 * @param userName
	 * @return
	 */
	public boolean check(String userName);

	/**
	 * 获得用户列表
	 * @param pageSize 
	 * @param currentPage 
	 * @return
	 */
	public List<User> getUsers(int currentPage, int pageSize);
	/**
	 * 用户列表所有记录数
	 * @return
	 */
	public int getTotalPage();
	/**
	 * 根据用户名修改密码
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean modifyPasswordByUsername(String username,String password);
	/**
	 * 根据用户名获得用户密码
	 * @param username
	 * @return
	 */
	public String getPasswordByUsername(String username);
	/**
	 * 更加用户名获得用户权限
	 * @param username
	 * @return
	 */
	public int getLevelByUsername(String username);
	/**
	 * 根据用户名修改权限
	 * @param username
	 * @param level
	 * @return
	 */
	public boolean modifyLevelByUsername(String username,int level);
	/**
	 * 根据用户名获得用户实体
	 * @param username
	 * @return
	 */
	public User getUser(String username);
	/**
	 * 获得用户订阅博客列表
	 * @param username
	 * @return
	 */
	public Map<String,List<String>> getOrder(String username);
	/**
	 * 添加订阅博客
	 * @param blogId
	 * @return
	 */
	public boolean addOrder(String username,String blogId);
	/**
	 * 删除订阅博客
	 * @param blogId
	 * @return
	 */
	public boolean deleteOrder(String username,String blogId);
	/**
	 * 添加用户推荐博客
	 * @param username
	 * @param recommend
	 * @return
	 */
	public boolean insertRecommend(String username,String recommend);
	/**
	 * 获得用户推荐博客
	 * @param username
	 * @return
	 */
	public String[] getRecommend(String username);
	/**
	 * 更新推荐
	 * @param username
	 * @param recommend
	 * @return
	 */
	public boolean updateRecommend(String username,String recommend);
}

package service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import model.User;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import dao.UserDao;
import dao.impl.UserDaoImpl;
@Component("userService")
public class UserService {
	private UserDao userDao=new UserDaoImpl();
	
	@Transactional
	public Map<String,List<String>> getOrderBlogs(String username){
		return userDao.getOrder(username);
	}
	
	@Transactional
	public boolean addOrderBlog(String username,String blogId){
		return userDao.addOrder(username, blogId);
	}
	
	@Transactional
	public boolean deleteOrderBlog(String username,String blogId){
		return userDao.deleteOrder(username, blogId);
	}
	
	@Transactional
	public boolean save(User user){
		if(this.userDao.check(user.getUsername())){
			return false;
		}
		this.userDao.add(user);
		
		return true;
	}
	
	public void delete(User user){
		this.userDao.delete(user);
	}
	public UserDao getUserDao() {
		return userDao;
	}

	@Resource(name="userDao")
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@Transactional
	public List<User> getUsers(int currentPage, int pageSize) {
		
		return this.userDao.getUsers(currentPage,pageSize);
	}
	
	public int getTotalPage(){
		return this.userDao.getTotalPage();
	}
	
	@Transactional
	public int checkLogin(String username,String password){
		String currPasswordReal=this.userDao.getPasswordByUsername(username);
		if(currPasswordReal!=null){
			if(currPasswordReal.equals(password)){
				
				return this.userDao.getLevelByUsername(username);
			}
		}
		return -1;
	}
	
	@Transactional
	public  boolean updatePassword(String currPassword,String username,String newPassword){
		String currPasswordReal=this.userDao.getPasswordByUsername(username);
		if(currPasswordReal!=null){
			if(!currPasswordReal.equals(currPassword)){
				return false;
			}
		}else{
			return false;
		}
		return this.userDao.modifyPasswordByUsername(username, newPassword);
	}
	
	@Transactional
	public boolean updateLevel(String username,int level){
		return this.userDao.modifyLevelByUsername(username, level);
	}
	@Transactional
	public User getUser(String username){
		return this.userDao.getUser(username);
	}
}

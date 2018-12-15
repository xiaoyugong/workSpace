package action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import service.UserService;
import model.User;

import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Component("user")
@Scope("prototype")
public class UserAction extends ActionSupport implements SessionAware{
	private String username;
	private String password; // 密码
	
	// 修改密码
	private String currentpassword;
	private String newpassword;
	private String confirmpassword;
	
	// 订阅查看 
	private Map<String,List<String>> orderedBlogs;
	
	private int level;    // 权限值
	private int modifiedlevel;
	
	private String info;  // 错误信息
	
	static Map<String ,Object> session;
	
	private UserService userService=new UserService();
	
	/**
	 * 更新password
	 * @return
	 */
	public String updatePassword(){
		String username=(String)session.get("username");
		boolean flag=userService.updatePassword(currentpassword,username, newpassword);
		if(flag){
			info="成功修改密码";
		}else{
			info="修改密码失败";
		}
		return "updatePassword";
	}
	/**
	 * 注册
	 * @return
	 */
	public String register(){
		User user =new User(username,password,1);
		boolean flag=userService.save(user);
		if(flag){
			info="注册成功";
		}else{
			info="注册失败";
		}
		return "register";
	}
	/**
	 *  登录
	 * @return
	 */
	public String login(){
		int level =userService.checkLogin(username, password);
		if(-1!=level){
			session.put("username",username);
			session.put("level", level);
			return "home";
		}
		info="用户名或密码错误!";
		return "login";
	}
	/**
	 * 订阅查看
	 * @return
	 */
	public String subscribe(){
		String username=(String)session.get("username");
		orderedBlogs = userService.getOrderBlogs(username);
		if(orderedBlogs!=null&&orderedBlogs.size()>0){
			System.out.println(orderedBlogs.values().toString());
		}else{
			info="null";
		}
		return "subscribe";
	}
	/**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * @param userService the userService to set
	 */
	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}
	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	public static Map<String, Object> getSession() {
		return session;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	/**
	 * @return the info
	 */
	public String getInfo() {
		return info;
	}
	/**
	 * @param info the info to set
	 */
	public void setInfo(String info) {
		this.info = info;
	}
	/**
	 * @return the modifiedlevel
	 */
	public int getModifiedlevel() {
		return modifiedlevel;
	}
	/**
	 * @param modifiedlevel the modifiedlevel to set
	 */
	public void setModifiedlevel(int modifiedlevel) {
		this.modifiedlevel = modifiedlevel;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCurrentpassword() {
		return currentpassword;
	}
	public void setCurrentpassword(String currentpassword) {
		this.currentpassword = currentpassword;
	}
	public String getNewpassword() {
		return newpassword;
	}
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	public String getConfirmpassword() {
		return confirmpassword;
	}
	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}
	/**
	 * @return the orderedBlogs
	 */
	public  Map<String, List<String>>  getOrderedBlogs() {
		return orderedBlogs;
	}
	/**
	 * @param orderedBlogs the orderedBlogs to set
	 */
	public void setOrderedBlogs(Map<String, List<String>>  orderedBlogs) {
		this.orderedBlogs = orderedBlogs;
	}
}
package action;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;

import service.BlogService;

@SuppressWarnings("serial")
@Component("blog")
@Scope("prototype")
public class BlogAction extends ActionSupport implements SessionAware{
	private String blogIdAdd;
	private String blogIdDel;
	private String recommendType;
	private Map<String, List<String>> recommendBlogs = new LinkedHashMap<String, List<String>>();
	private static Map<String, List<String>> recommendBlogsTmp = new LinkedHashMap<String, List<String>>();
	private Map<String, List<String>> recommendBlogsCopy = new LinkedHashMap<String, List<String>>();
	private String info;
	private Map<String ,Object> session=new HashMap<String, Object>(UserAction.getSession());
	private BlogService blogService=new BlogService();
	
	/**
	 * 增加订阅
	 * @return
	 */
	public String subscribe(){
		String username=(String)session.get("username");
		boolean flag=blogService.subscribe(username, blogIdAdd);
		if(flag){
			info="成功订阅ID为"+blogIdAdd+"的博客";
		}else{
			info="订阅失败或者ID为"+blogIdAdd+"的博客不存在";
		}
		return "blog";
	}
	
	/**
	 * 取消订阅
	 * @return
	 */
	public String unsubscribe(){
		String username=(String)session.get("username");
		boolean flag=blogService.unsubscribe(username, blogIdDel);
		if(flag){
			info="成功取消博客ID为"+blogIdDel+"的订阅";
		}else{
			info="取消博客ID为"+blogIdDel+"的订阅时发生错误";
		}
		return "blog";
	}
	/**
	 * 推荐
	 * @return
	 */
	public String recommend(){
		String username=(String)session.get("username");
		recommendBlogs = blogService.recommend(username, recommendType);
		if(recommendBlogs!=null && !recommendBlogs.isEmpty()){
			info="ok";
			recommendBlogsTmp = recommendBlogs;
			String[] keys  = recommendBlogs.keySet().toArray(new String[0]);
			for(int i = 0 ; i <  10; i++){
				String key = keys[i];
				List<String> value = recommendBlogs.get(keys[i]);
				recommendBlogsCopy.put(key,value);
			}
		}else{
			info="null";
		}
		return "blogrecommend";
	}
	
	public String nextpage(){
		if(recommendBlogsTmp!=null && !recommendBlogsTmp.isEmpty()){
			info="ok";
			String[] keys  = recommendBlogsTmp.keySet().toArray(new String[0]);
			Random random = new Random();
			for(int i = 0 ; i <  10; i++){
				int j = random.nextInt(keys.length);
				String key = keys[j];
				List<String> value = recommendBlogsTmp.get(keys[j]);
				recommendBlogsCopy.put(key,value);
			}
		}else{
			info="null";
		}
		System.out.println(recommendBlogsTmp);
		return "blogrecommend";
	}
	
	public String getBlogIdAdd() {
		return blogIdAdd;
	}
	public void setBlogIdAdd(String blogIdAdd) {
		this.blogIdAdd = blogIdAdd;
	}
	public String getBlogIdDel() {
		return blogIdDel;
	}
	public void setBlogIdDel(String blogIdDel) {
		this.blogIdDel = blogIdDel;
	}
	public String getRecommendType() {
		return recommendType;
	}
	public void setRecommendType(String recommendType) {
		this.recommendType = recommendType;
	}
	public Map<String, List<String>> getRecommendBlogs() {
		return recommendBlogs;
	}
	public void setRecommendBlogs(Map<String, List<String>> recommendBlogs) {
		this.recommendBlogs = recommendBlogs;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Map<String, Object> getSession() {
		return session;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	public BlogService getBlogService() {
		return blogService;
	}
	@Resource
	public void setBlogService(BlogService blogService) {
		this.blogService = blogService;
	}

	public Map<String, List<String>> getRecommendBlogsCopy() {
		return recommendBlogsCopy;
	}

	public void setRecommendBlogsCopy(Map<String, List<String>> recommendBlogsCopy) {
		this.recommendBlogsCopy = recommendBlogsCopy;
	}
	
	
}

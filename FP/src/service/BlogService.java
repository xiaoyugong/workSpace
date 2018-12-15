package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import model.Knowledge;
import model.User;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import util.hadoop.HadoopUtils;
import dao.BlogsDao;
import dao.KnowledgeDao;
import dao.UserDao;
import dao.impl.BlogsDaoImpl;
import dao.impl.KnowledgeDaoImpl;
import dao.impl.UserDaoImpl;
@Component("blogService")
public class BlogService {
	
	private UserDao userDao = new UserDaoImpl();
	private KnowledgeDao knowledgeDao = new KnowledgeDaoImpl();
	private BlogsDao blogsDao = new BlogsDaoImpl();;
	
	@Transactional
	public boolean subscribe(String username,String blogId){
		return userDao.addOrder(username, blogId);
	}
	@Transactional
	public boolean unsubscribe(String username,String blogId){
		return userDao.deleteOrder(username, blogId);
	}
	/**
	 * 三种方式获得推荐博客
	 * @param username
	 * @param recommendType
	 * @return
	 */
	@Transactional
	public Map<String,List<String>> recommend(String username,String recommendType){
		if("union".equals(recommendType)){
			return recommendUnion(username);
		}else if("last".equals(recommendType)){
			return recommendLast(username);
		}
		try {
			return recommendTop(username);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * top 推荐 
	 * @param username
	 * @return
	 * @throws IOException 
	 */
	private Map<String,List<String>> recommendTop(String username) throws IOException {
		Map<Writable,Writable> flist = HadoopUtils.readFromFile(HadoopUtils.FLIST, HadoopUtils.getConf(), 17L);
		Map<String,List<String>> blogs = new LinkedHashMap<String, List<String>>();
		int i = 0;
		int k = 0;
		String[] blogIds = new String[18];
		String[] relatedVotes = new String[18];
		for(Writable m:flist.keySet()){
			Text item = (Text) m;
			blogIds[i++] = item.toString();
			relatedVotes[k++] = flist.get(m).toString();
		}
		//（blogid，blog信息）
		blogs = blogsDao.getBlogs(blogIds);
		Map<String,List<String>> blogsAndV = new LinkedHashMap<String,List<String>>();
		int j = 0;
		for(String m : blogs.keySet()) {
			blogs.get(m).add(relatedVotes[j++]);
			blogsAndV.put(m, blogs.get(m));
		}
		
		return blogsAndV;
	}
	/**
	 * 上次推荐
	 * @param username
	 * @return
	 */
	private Map<String, List<String>> recommendLast(String username) {
		User user= userDao.getUser(username);
		String recommend= user.getRecommend();
		if(recommend!=null&&recommend.length()>0){
			String[]recommends= recommend.split(" ");
			if(recommends!=null&&recommends.length>0){
				String[] blogIds = new String[recommends.length/2];
				Map<String, List<String>> blogs = new LinkedHashMap<String, List<String>>();
				for (int i = 0 , j = 0; i < recommends.length;j++, i = i + 2) {
					blogIds[j] = recommends[i]; 
				}
				blogs = blogsDao.getBlogs(blogIds);
				Map<String, List<String>> blogsAndV = new LinkedHashMap<String,List< String>>();
				int k = 1;
				for(String m : blogs.keySet()) {
					blogs.get(m).add(recommends[k]);
					blogsAndV.put(m, blogs.get(m));
					k = k + 2;
				}
				return blogsAndV;
			}
		}
		return null;
	}
	/**
	 * 联合推荐
	 * @param username
	 * @return
	 */
	private Map<String, List<String>> recommendUnion(String username) {
		User user = userDao.getUser(username);
		String order= user.getOrder();
		if(order==null||order.equals("")){
			return null;
		}
		String[] orders = order.split(" ");
		if(orders==null|| orders.length<1){
			return null;
		}
		// 整合推荐map
		Map<String ,Integer> recommendMap= new HashMap<String ,Integer>();
		for(String o:orders){
			List<Knowledge> knowledge = knowledgeDao.getKnowledge(Integer.parseInt(o));
			for(Knowledge k:knowledge){
				for (int j =0; j < orders.length; j++) {
					if (!orders[j].equals(k.getRelatedBlog()+"")) {
						if (j != orders.length-1){
							continue;
						}
					} else {
						break;
					}
					if(recommendMap.containsKey(k.getRelatedBlog())){
						int currentVotes=recommendMap.get(k.getRelatedBlog());
						recommendMap.put(String.valueOf(k.getRelatedBlog()), currentVotes+k.getRelatedVotes());
					}else{
						recommendMap.put(String.valueOf(k.getRelatedBlog()), k.getRelatedVotes());
					}
				}
			}
		}
		// 排序map
		ArrayList<Entry<String,Integer>> list= sortByValue(recommendMap);
		if(list.size()<1){
			return null;
		}
		//获取关联度最高的前n个，剔除已经订阅的
		Map<String,String> recommends = new LinkedHashMap<String, String>();
		StringBuffer buff= new StringBuffer();
		for (Entry<String,Integer> l : list) {
			recommends.put( l.getKey(),l.getValue().toString());
			buff.append(l.getKey()+" "+l.getValue().toString()+" ");
			
			System.out.println(buff);
		}
		// 存入更新推荐，为上次推荐算法做准备
		userDao.updateRecommend(username, buff.substring(0,buff.length()-1));
		String[] blogIds = new String[recommends.size()-1];
		Map<String, List<String>> blogs = new LinkedHashMap<String, List<String>>();
		blogIds = (String[]) recommends.keySet().toArray(new String[0]);
		blogs = blogsDao.getBlogs(blogIds);
		Map<String, List<String>> blogsAndV = new LinkedHashMap<String, List<String>>();
		for(String m : blogs.keySet()) {
			blogs.get(m).add(recommends.get(m)) ;
			blogsAndV.put(m, blogs.get(m));
		}
		
		return blogsAndV;
	}
	
	
	/**
	 * 按照map的值排序
	 * @param keyfreqs
	 * @return
	 */
	private  ArrayList<Entry<String,Integer>> sortByValue(Map<String ,Integer> map){

		ArrayList<Entry<String,Integer>> list = new ArrayList<Entry<String,Integer>>(map.entrySet());  
				
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {  
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				double result=o2.getValue() - o1.getValue();
				 if(result==0.0){
					 return 0;
				 }else if(result >0.0){
					 return 1;
				 } 
				 return -1;
			}  
		});
		return list;
	}

	public UserDao getUserDao() {
		return userDao;
	}
	@Resource(name="userDao")
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public KnowledgeDao getKnowledgeDao() {
		return knowledgeDao;
	}
	@Resource(name="knowledgeDao")
	public void setKnowledgeDao(KnowledgeDao knowledgeDao) {
		this.knowledgeDao = knowledgeDao;
	}
	public BlogsDao getBlogsDao() {
		return blogsDao;
	}
	@Resource(name="blogsDao")
	public void setBlogsDao(BlogsDao blogsDao) {
		this.blogsDao = blogsDao;
	}
	
}

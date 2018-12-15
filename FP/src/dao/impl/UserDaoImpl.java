package dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import model.Blogs;
import model.User;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Component;

import dao.UserDao;
@Component("userDao")
public class UserDaoImpl implements UserDao {
	
	private HibernateTemplate hibernateTemplate;

	public void add(User user) {
		
		hibernateTemplate.save(user);
	}

	public void delete(User user) {
		hibernateTemplate.delete(user);
	}

	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@SuppressWarnings("unchecked")
	public boolean check(String userName) {
		List<User> users=(List<User>) hibernateTemplate.find("from User u where u.username='"+userName+"'");
		if(users!=null&&users.size()>0){
			return true;
		}
		return false;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<User> getUsers(int currentPage, final int pageSize) {
		int maxPage=(int)Math.ceil(getTotalPage()*1.0/pageSize);
		final int offset=currentPage<=maxPage?(currentPage-1)*pageSize:(maxPage-1)*pageSize;
		final String hql="from User";
		@SuppressWarnings("deprecation")
		List list = (List) hibernateTemplate.execute(new HibernateCallback() {     
		    public Object doInHibernate(Session session)     
		      throws HibernateException {     
		     Query query = session.createQuery(hql);     
		     query.setFirstResult(offset);     
		     query.setMaxResults(pageSize);     
		     List list = query.list();     
		     return list;     
		    }     
		   });     
		return list;
	}
	/**
	 * 获取所有记录行数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getTotalPage(){
		final String hql = "select count(*) from User su";     
        Long result = null;     
        result = (Long)getHibernateTemplate().execute(new HibernateCallback() {     
    
            public Object doInHibernate(Session arg0)     
                    throws HibernateException {     
                Query query = arg0.createQuery(hql);     
                return query.uniqueResult();     
            }     
    
        });     
        return result.intValue();  
	}

	public boolean modifyPasswordByUsername(String username, String password) {
		User user=getUser(username);
		if(user!=null){
			user.setPassword(password);
			hibernateTemplate.update(user);
			return true;
		}
		return false;
	}

	public boolean modifyLevelByUsername(String username, int level) {
		User user=getUser(username);
		if(user!=null){
			user.setLevel(level);
			hibernateTemplate.update(user);
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public User getUser(String username) {
		List<User> users=(List<User>) hibernateTemplate.find("from User u where u.username='"+username+"'");
		if(users!=null&&users.size()==1){
			
			return users.get(0);
		}
		return null;
	}

	public String getPasswordByUsername(String username) {
		User user=getUser(username);
		if(user!=null){
			
			return user.getPassword();
		}
		return null;
	}
	
    //订阅查看
	@SuppressWarnings("unchecked")
	public Map<String,List<String>> getOrder(String username) {
		User user=getUser(username);
		if(user!=null){
			String order= user.getOrder();
			if(order!=null&&!"".equals(order)){
				String[] orderIds = order.split(" ");
				int orderId = 0;
				Map<String,List<String>> orderBlog = new HashMap<String,List<String>>();
				for (int i = 0; i < orderIds.length; i++ ) {
					orderId = Integer.parseInt(orderIds[i]);
					List<Blogs> blog = (List<Blogs>) hibernateTemplate.find("from Blogs blog where blog.blogId='"+orderId+"'");
					orderBlog.put(orderIds[i], blog.get(0).BlogMessage());
				}
				return orderBlog;
			}
		}
		return null;
	}
	
    //增加订阅
	@SuppressWarnings("unchecked")
	public boolean addOrder(String username,String blogId) {
		User user = getUser(username);
		int blogID = 0;
		try {
			blogID =  Integer.parseInt(blogId);
		} catch (Exception e) {
			return false;
		} 
		//int blogID =  Integer.parseInt(blogId);
		List<Blogs> isblogId=(List<Blogs>) hibernateTemplate.find("from Blogs blog where blog.blogId='"+blogID+"'");
		if(user != null && !isblogId.isEmpty()) {
			String order= user.getOrder();
			if(order != null&&!"".equals(order)) {
				if (order.contains(" "+blogId+" ")) {
					return true;
				}
				order+=" "+blogId;
				user.setOrder(order);	
			}else{
				user.setOrder(blogId);
			}
            hibernateTemplate.update(user);
			return true;
		}
		return false;
	}
	
    //  取消订阅
	public boolean deleteOrder(String username,String blogId) {
		User user=getUser(username);
		if(user!=null){
			String order= user.getOrder();
			if(order!=null&&!"".equals(order)){
				if(order.contains(blogId)){
					if(order.startsWith(blogId)){
						user.setOrder(order.replaceAll(blogId+" ", ""));
					}else {
						user.setOrder(order.replaceAll(" "+blogId, ""));
					}
					hibernateTemplate.update(user);
					return true;
				}else{
					return true;
				}
			}
		}
		return false;
	}

	public boolean insertRecommend(String username, String recommend) {
		User user=getUser(username);
		if(user!=null){
			user.setRecommend(recommend);
			hibernateTemplate.update(user);
			return true;
		}
		return false;
	}

	public String[] getRecommend(String username) {
		User user=getUser(username);
		if(user!=null){
			String recommend= user.getRecommend();
			if(recommend!=null&&!"".equals(recommend)){
				return recommend.split(" ");
			}
		}
		return null;
	}

	public int getLevelByUsername(String username) {
		User user=getUser(username);
		if(user!=null){
			return user.getLevel();
		}
		return 0;
	}

	public boolean updateRecommend(String username, String recommend) {
		if(recommend==null||"".equals(recommend)){
			return false;
		}
		User user = getUser(username);
		if(user!=null){
			user.setRecommend(recommend);
			
			try{
				hibernateTemplate.update(recommend);
			}catch(Exception e){
				return false;
			}
		}
		return true;
	}
	
	

}

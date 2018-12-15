package dao.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import model.Blogs;

import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Component;

import dao.BlogsDao;

@Component("blogsDao")
public class BlogsDaoImpl implements BlogsDao {

	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String,List<String>> getBlogs(String[] blogIds) {
		Map<String,List<String>> blogs = new LinkedHashMap<String, List<String>>();
		for (int i = 0; i < blogIds.length; i++ ) {
			int blogId = Integer.parseInt(blogIds[i]);
			List<Blogs> blog = (List<Blogs>) hibernateTemplate.find("from Blogs blog where blog.blogId='"+blogId+"'");
			blogs.put(blogIds[i], blog.get(0).BlogMessage());
		}
		return blogs;
	}
	
}

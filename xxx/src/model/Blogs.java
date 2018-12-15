package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mysql.fabric.xmlrpc.base.Array;

/**
 * blogs表 实体类
 * 
 * @author root 2015-6-5
 */
@Entity
@Table(name = "blogs")
public class Blogs {

	private int blogId;
	private String email;
	private String blogName;

	@Id
	@Column(name = "blogid")
	public int getBlogId() {
		return blogId;
	}

	public void setBlogId(int blogId) {
		this.blogId = blogId;
	}

	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name = "blogname")
	public String getBlogName() {
		return blogName;
	}

	public void setBlogName(String blogName) {
		this.blogName = blogName;
	}

	public List<String> BlogMessage() {
		List<String> blogMessage = new ArrayList<String>();
		blogMessage.add(this.blogName);
		blogMessage.add(this.email);
		return blogMessage;
	}
}

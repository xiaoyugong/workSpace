package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_user")
public class User {
	private int id;
	// 用户名
	private String username;
	// 用户密码
	private String password;
	// 用户权限，0：超级管理员，1：普通用户；
	private int level;
	// 用户订购博客 ,空格分隔
	private String order;
	// 用户推荐博客 ,空格分隔
	private String recommend;
	
	public User(){}
	
	public User(String username,String password,int level){
		this.username=username;
		this.password=password;
		this.level=level;
	}
	
	public User(String username,String password,int level,String order){
		this(username,password,level);
		this.order=order;
	}
	@Override
	public String toString(){
		return this.id+","+this.username+","+this.password+","+this.level+","+this.order+","+this.recommend;
	}
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	@Column(name="orderBlog")
	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getRecommend() {
		return recommend;
	}

	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}
}

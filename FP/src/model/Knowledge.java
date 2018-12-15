package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="knowledge")
public class Knowledge {
	private int id;
	// 博客id
	private int blogId;
	// 相关博客id
	private int relatedBlog ;
	// 相关博客id 相关度
	private int relatedVotes;
	
	public Knowledge(){}
	
	public Knowledge(int blogId,int relatedBlog,int relatedVotes){
		this.blogId=blogId;
		this.relatedBlog=relatedBlog;
		this.relatedVotes=relatedVotes;
	}
	@Override
	public String toString(){
		return this.blogId+","+this.relatedBlog+","+this.relatedVotes;
	}
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBlogId() {
		return blogId;
	}

	public void setBlogId(int blogId) {
		this.blogId = blogId;
	}

	public int getRelatedBlog() {
		return relatedBlog;
	}

	public void setRelatedBlog(int relatedBlog) {
		this.relatedBlog = relatedBlog;
	}

	public int getRelatedVotes() {
		return relatedVotes;
	}

	public void setRelatedVotes(int relatedVotes) {
		this.relatedVotes = relatedVotes;
	}
}

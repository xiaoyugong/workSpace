package com.weixin.returnMsg;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ReturnNewsMsg extends ReturnBaseMsg{

	private String ArticleCount;
	
	private Set<NewsArticle> Articles = new HashSet<NewsArticle>();

	
	public ReturnNewsMsg(){
		 super.setMsgType("news");
		 super.setCreateTime(new Date().getTime()+"");
	 }
	public String getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(String articleCount) {
		ArticleCount = articleCount;
	}

	public Set<NewsArticle> getArticles() {
		return Articles;
	}

	public void setArticles(Set<NewsArticle> articles) {
		Articles = articles;
	}
	
	
	
	
}

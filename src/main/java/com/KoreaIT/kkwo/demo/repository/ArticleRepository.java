package com.KoreaIT.kkwo.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.KoreaIT.kkwo.demo.vo.Article;

@Mapper
public interface ArticleRepository {
	
	public Article writeArticle(String title, String body);
	
	public List<Article> getArticles();

	public Article getArticleByInputedId(int id);
	
	public void deleteArticle(int id);

	public void modifyArticle(int id, String title, String body);

	public int getLastInsertId();
}


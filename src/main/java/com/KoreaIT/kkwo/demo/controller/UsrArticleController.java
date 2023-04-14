package com.KoreaIT.kkwo.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.kkwo.demo.vo.Article;

@Controller
public class UsrArticleController {
	int lastArticleId;
	List<Article> articles;

	public UsrArticleController() {
		this.lastArticleId = 0;
		this.articles = new ArrayList<>();

		makeTestData();
	}
	
	// 서비스 메서드
	private void makeTestData() {
		for (int i = 1; i <= 10; i++) {
			String title = "제목" + i;
			String body = "내용" + i;

			writeArticle(title, body);
		}
	}
	
	// 서비스 메서드
	public Article writeArticle(String title, String body) {
		int id = lastArticleId + 1;

		Article article = new Article(id, title, body);
		articles.add(article);
		lastArticleId++;

		return article;
	}
	
	// 액션 메서드
	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public Article doAdd(String title, String body) {
		int id = lastArticleId + 1;
		Article article = new Article(id, title, body);
		articles.add(article);
		lastArticleId++;
		return article;
	}
	
	// 액션 메서드
	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public List<Article> getArticles() {
		return articles;
	}

	
	public Article getArticleByInputedId(int id) {
		for (Article article : articles) {
			if(article.getId() == id) {
				return article;
			}
		}
		return null;
	}
	
	public void deleteArticle(int id) {
		Article article = getArticleByInputedId(id);
		articles.remove(article);
	}
	public void modifyArticle(int id, String title, String body) {
		Article article = getArticleByInputedId(id);
		article.setTitle(title);
		article.setBody(body);
	}
	
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {
		Article article = getArticleByInputedId(id);
		if(article == null) {
			return String.format("%d번 글은 존재하지 않습니다", id);
		}
		deleteArticle(id);
		return String.format("%d번 글이 삭제되었습니다", id);
	}
	
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public String doModify(int id, String title, String body) {
		Article article = getArticleByInputedId(id);
		if(article == null) {
			return String.format("%d번 글은 존재하지 않습니다", id);
		}
		modifyArticle(id, title, body);
		return String.format("%d번 글이 수정되었습니다", id);
	}
}

package com.KoreaIT.kkwo.demo.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.KoreaIT.kkwo.demo.vo.Article;

@Component
public class ArticleRepository {
	private List<Article> articles;
	private int lastArticleId;

	public ArticleRepository() {
		articles = new ArrayList<>();
		lastArticleId = 0;
	}

	public int getId() {
		return lastArticleId + 1;
	}

	public void makeTestData() {
		for (int i = 1; i <= 10; i++) {
			String title = "제목" + i;
			String body = "내용" + i;

			writeArticle(title, body);
		}
	}

	public Article writeArticle(String title, String body) {
		int id = getId();
		Article article = new Article(id, title, body);
		articles.add(article);
		lastArticleId++;
		return article;
	}
	
	public Article getArticleByInputedId(int id) {
		for (Article article : articles) {
			if (article.getId() == id) {
				return article;
			}
		}
		return null;
	}

	public List<Article> getArticles() {
		return articles;
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
}

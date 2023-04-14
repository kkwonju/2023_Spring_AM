package com.KoreaIT.kkwo.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.KoreaIT.kkwo.demo.repository.ArticleRepository;
import com.KoreaIT.kkwo.demo.vo.Article;

@Service
public class ArticleService {
	// @Autowired
	private ArticleRepository articleRepository;
	
	/* @Autowired 보다 생성자가 먼저 실행 */
	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
		articleRepository.makeTestData();
	}
	
	public Article writeArticle(String title, String body) {
		return articleRepository.writeArticle(title, body);
	}

	public List<Article> getArticles() {
		return articleRepository.getArticles();
	}

	public Article getArticleByInputedId(int id) {
		return articleRepository.getArticleByInputedId(id);
	}

	public void deleteArticle(int id) {
		articleRepository.deleteArticle(id);
	}

	public void modifyArticle(int id, String title, String body) {
		articleRepository.modifyArticle(id, title, body);
	}
}

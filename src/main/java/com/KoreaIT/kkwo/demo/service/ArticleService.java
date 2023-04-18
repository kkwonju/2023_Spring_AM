package com.KoreaIT.kkwo.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.KoreaIT.kkwo.demo.repository.ArticleRepository;
import com.KoreaIT.kkwo.demo.util.Ut;
import com.KoreaIT.kkwo.demo.vo.Article;
import com.KoreaIT.kkwo.demo.vo.ResultData;

@Service
public class ArticleService {
	// @Autowired
	private ArticleRepository articleRepository;

	/* @Autowired 보다 생성자가 먼저 실행 */
	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}

	public ResultData writeArticle(String title, String body) {
		articleRepository.writeArticle(title, body);
		int id = articleRepository.getLastInsertId();
		return ResultData.from("S-1", Ut.f("%d번 글 생성", id), id);
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

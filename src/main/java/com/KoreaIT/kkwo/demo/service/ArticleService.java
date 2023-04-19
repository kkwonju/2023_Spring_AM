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

	public ResultData<Integer> writeArticle(String title, String body, int memberId) {
		articleRepository.writeArticle(title, body, memberId);
		int id = articleRepository.getLastInsertId();
		return ResultData.from("S-1", Ut.f("%d번 글 생성", id), id);
	}

	public List<Article> getArticles() {
		return articleRepository.getArticles();
	}

	public Article getArticleById(int id) {
		return articleRepository.getArticleById(id);
	}

	public void deleteArticle(int id) {
		articleRepository.deleteArticle(id);
	}

	public ResultData<Article> modifyArticle(int id, String title, String body) {
		articleRepository.modifyArticle(id, title, body);
		Article article = getArticleById(id);
		return ResultData.from("S-1", Ut.f("%d번 글이 수정되었습니다", id), article);
	}

	public ResultData actorCanModify(int loginedMemberId, Article article) {
		if (article.getMemberId() != loginedMemberId) {
			return ResultData.from("F-0", "수정 권한이 없습니다");
		}
		return ResultData.from("S-1", "수정 가능");
	}
}

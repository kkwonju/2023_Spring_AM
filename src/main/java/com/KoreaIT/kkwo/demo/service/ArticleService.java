package com.KoreaIT.kkwo.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.kkwo.demo.repository.ArticleRepository;
import com.KoreaIT.kkwo.demo.util.Ut;
import com.KoreaIT.kkwo.demo.vo.Article;
import com.KoreaIT.kkwo.demo.vo.ResultData;

@Service
public class ArticleService {
	@Autowired
	private ArticleRepository articleRepository;

	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}

	/* 게시글 작성 */
	public ResultData<Integer> writeArticle(int memberId, String title, String body) {
		articleRepository.writeArticle(memberId, title, body);
		int id = articleRepository.getLastInsertId();
		return ResultData.from("S-1", Ut.f("%d번 글 생성", id), "id", id);
	}

	/* 게시물 가져오기 */
	public Article getArticle(int id) {
		return articleRepository.getArticle(id);
	}

	/* 출력용 게시물 가져오기 */
	public Article getForPrintArticle(int actorId, int id) {
		Article article = articleRepository.getForPrintArticle(id);

		controlForPrintData(actorId, article);

		return article;
	}
	
	// 손 댈 수 있는지 여부
	private void controlForPrintData(int actorId, Article article) {
		if (article == null) {
			return;
		}

		ResultData actorCanModifyRd = actorCanModify(actorId, article);
		article.setActorCanModify(actorCanModifyRd.isSuccess());
		
		ResultData actorCanDeleteRd = actorCanDelete(actorId, article);
		article.setActorCanDelete(actorCanDeleteRd.isSuccess());
	}
	
	/* 삭제 권한 체크 */
	private ResultData actorCanDelete(int actorId, Article article) {
		if (article == null) {
			return ResultData.from("F-1", "게시물이 존재하지 않습니다");
		}
		if (article.getMemberId() != actorId) {
			return ResultData.from("F-2", "해당 게시물에 대한 권한이 없습니다");
		}

		return ResultData.from("S-1", "삭제 가능");
	}

	/* 수정 권한 체크 */
	public ResultData actorCanModify(int actorId, Article article) {
		if (article.getMemberId() != actorId) {
			return ResultData.from("F-3", "해당 글에 대한 권한이 없습니다");
		}
		return ResultData.from("S-1", "수정 가능");
	}
	
	/* 출력용 게시글 목록 가져오기 */
	public List<Article> getForPrintArticles(int boardId) {
		return articleRepository.getForPrintArticles(boardId);
	}

	/* 게시글 수정 */
	public ResultData modifyArticle(int id, String title, String body) {
		articleRepository.modifyArticle(id, title, body);

		Article article = getArticle(id);

		return ResultData.from("S-1", Ut.f("%d번 글을 수정했습니다", id), "Article", article);
	}

	/* 게시글 삭제 */
	public void deleteArticle(int id) {
		articleRepository.deleteArticle(id);
	}

}

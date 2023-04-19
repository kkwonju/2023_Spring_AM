package com.KoreaIT.kkwo.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.kkwo.demo.service.ArticleService;
import com.KoreaIT.kkwo.demo.util.Ut;
import com.KoreaIT.kkwo.demo.vo.Article;
import com.KoreaIT.kkwo.demo.vo.ResultData;

import ch.qos.logback.core.joran.conditional.IfAction;

@Controller
public class UsrArticleController {
	@Autowired // 자동 연결
	private ArticleService articleService;

	// 액션 메서드
	@RequestMapping("/usr/article/doWrite")
	@ResponseBody
	public ResultData<Article> doWrite(HttpSession httpSession, String title, String body) {
		boolean isLogined = false;
		
		if(httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
		}		
		
		if(!isLogined) {
			return ResultData.from("F-0", "로그인 후 이용해주세요");
		}
		if (Ut.empty(title)) {
			return ResultData.from("F-1", "제목을 입력해주세요");
		}
		if (Ut.empty(body)) {
			return ResultData.from("F-2", "내용을 입력해주세요");
		}
		int memberId = (int) httpSession.getAttribute("loginedMemberId");
		ResultData writeArticleRd = articleService.writeArticle(title, body, memberId);
		int id = (int) writeArticleRd.getData1();
		Article article = articleService.getArticleById(id);
		return ResultData.newData(writeArticleRd, article);
	}

	@RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public ResultData<Article> getArticle(int id) {
		Article article = articleService.getArticleById(id);
		if (article == null) {
			return ResultData.from("F-4", Ut.f("%d번 글은 존재하지 않습니다", id));
		}
		return ResultData.from("S-1", Ut.f("%d번 글입니다", id), article);
	}

	@RequestMapping("/usr/article/list")
	@ResponseBody
	public ResultData<List<Article>> getArticles() {
		List<Article> articles = articleService.getArticles();
		if (articles.isEmpty()) {
			return ResultData.from("F-3", "게시글이 없습니다");
		}
		return ResultData.from("S-1", "Article List", articles);
	}

	@RequestMapping("/usr/article/delete")
	@ResponseBody
	public ResultData<Integer> doDelete(HttpSession httpSession, int id) {
		
		boolean isLogined = false;
		int loginedMemberId = 0;
		
		if(httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");
		}		
		
		if(!isLogined) {
			return ResultData.from("F-0", "로그인 후 이용해주세요");
		}
		
		Article article = articleService.getArticleById(id);
		
		if (article == null) {
			return ResultData.from("F-7", Ut.f("%d번 글은 존재하지 않습니다", id), id);
		}
		
		if(article.getMemberId() != loginedMemberId) {
			return ResultData.from("F-0", "삭제 권한이 없습니다");
		}
		
		articleService.deleteArticle(id);
		return ResultData.from("S-1", Ut.f("%d번 글이 삭제되었습니다", id), id);
	}

	@RequestMapping("/usr/article/modify")
	@ResponseBody
	public ResultData<Article> doModify(HttpSession httpSession, int id, String title, String body) {
		
		boolean isLogined = false;
		int loginedMemberId = 0;
		
		if(httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");
		}
		
		if(!isLogined) {
			return ResultData.from("F-0", "로그인 후 이용해주세요");
		}
		
		Article article = articleService.getArticleById(id);
		
		if (article == null) {
			return ResultData.from("F-5", Ut.f("%d번 글은 존재하지 않습니다", id));
		}
		
		if(article.getMemberId() != loginedMemberId) {
			return ResultData.from("F-0", "수정 권한이 없습니다");
		}
		
		
		if (Ut.empty(title) && Ut.empty(body)) {
			return ResultData.from("F-6", "변경사항이 없습니다", article);
		}
		
		articleService.modifyArticle(id, title, body);
		article = articleService.getArticleById(id);
		return ResultData.from("S-1", Ut.f("%d번 글이 수정되었습니다", id), article);
	}

}

package com.KoreaIT.kkwo.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.kkwo.demo.service.ArticleService;
import com.KoreaIT.kkwo.demo.util.Ut;
import com.KoreaIT.kkwo.demo.vo.Article;
import com.KoreaIT.kkwo.demo.vo.ResultData;
import com.KoreaIT.kkwo.demo.vo.Rq;

@Controller
public class UsrArticleController {
	@Autowired
	private ArticleService articleService;

	@RequestMapping("/usr/article/write")
	public String showWrite() {
		return "usr/article/write";
	}
	
	@RequestMapping("/usr/article/doWrite")
	@ResponseBody
	public String doWrite(HttpServletRequest req, String title, String body) {

		Rq rq = (Rq) req.getAttribute("rq");

		if (Ut.empty(title)) {
			return Ut.jsHistoryBack("F-1", "제목을 입력해주세요");
		}

		if (Ut.empty(body)) {
			return Ut.jsHistoryBack("F-2", "내용을 입력해주세요");
		}

		ResultData writeRd = articleService.writeArticle(rq.getLoginedMemberId(), title, body);

		int id = (int) writeRd.getData1();

		Article article = articleService.getArticle(id);

		return Ut.jsReplace("S-1", Ut.f("%d번 글이 생성되었습니다", id), "../article/list");
	}

	@RequestMapping("/usr/article/detail")
	public String showDetail(HttpServletRequest req, Model model, int id) {

		Rq rq = (Rq) req.getAttribute("rq");

		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
		model.addAttribute(article);
		return "usr/article/detail";
	}

	@RequestMapping("/usr/article/list")
	public String showList(Model model) {
		List<Article> articles = articleService.getForPrintArticles();
		model.addAttribute("articles", articles);
		return "usr/article/list";
	}

	/* 게시글 수정 페이지 */
	@RequestMapping("/usr/article/modify")
	public String showModify(HttpServletRequest req, Model model, int id) {

		Rq rq = (Rq) req.getAttribute("rq");

		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		if (article == null) {
			return Ut.jsHistoryBackOnView(req, Ut.f("%d번 글은 존재하지 않습니다", id));
		}

		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);

		if (actorCanModifyRd.isFail()) {
			return Ut.jsHistoryBackOnView(req, actorCanModifyRd.getMsg());
		}

		model.addAttribute("article", article);
		return "usr/article/modify";
	}
	
	/* 게시글 수정 */
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public String doModify(HttpServletRequest req , int id, String title, String body) {

		Rq rq = (Rq) req.getAttribute("rq");

		Article article = articleService.getArticle(id);

		if (article == null) {
			return Ut.jsHistoryBack("F-1", Ut.f("%d번 글은 존재하지 않습니다", id));
		}

		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);

		if (actorCanModifyRd.isFail()) {
			return Ut.jsHistoryBack("F-2", actorCanModifyRd.getMsg());
		}
		
		articleService.modifyArticle(id, title, body);

		return Ut.jsReplace("S-1", Ut.f("%d번 글을 수정했습니다", id), Ut.f("../article/detail?id=%d", id));
	}

	/* 게시글 삭제 */
	@RequestMapping("/usr/article/delete")
	@ResponseBody
	public String doDelete(HttpServletRequest req, int id) {

		Rq rq = (Rq) req.getAttribute("rq");

		Article article = articleService.getArticle(id);

		if (article == null) {
			return Ut.jsHistoryBack("F-4", Ut.f("%d번 글은 존재하지 않습니다", id));
		}

		if (article.getMemberId() != rq.getLoginedMemberId()) {
			return Ut.jsHistoryBack("F-4", Ut.f("%d번 글에 대한 권한이 없습니다", id));
		}

		articleService.deleteArticle(id);

		return Ut.jsReplace("S-1", Ut.f("%d번 글을 삭제했습니다", id), "../article/list");
	}
}

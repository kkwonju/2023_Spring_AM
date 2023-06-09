package com.KoreaIT.kkwo.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.kkwo.demo.service.ArticleService;
import com.KoreaIT.kkwo.demo.service.BoardService;
import com.KoreaIT.kkwo.demo.service.ReactionPointService;
import com.KoreaIT.kkwo.demo.service.ReplyService;
import com.KoreaIT.kkwo.demo.util.Ut;
import com.KoreaIT.kkwo.demo.vo.Article;
import com.KoreaIT.kkwo.demo.vo.Board;
import com.KoreaIT.kkwo.demo.vo.Reply;
import com.KoreaIT.kkwo.demo.vo.ResultData;
import com.KoreaIT.kkwo.demo.vo.Rq;

@Controller
public class UsrArticleController {
	@Autowired
	private ArticleService articleService;
	@Autowired
	private BoardService boardService;
	@Autowired
	private Rq rq;
	@Autowired
	private ReactionPointService reactionPointService;
	@Autowired
	private ReplyService replyService;

	@RequestMapping("/usr/article/write")
	public String showWrite() {
		return "usr/article/write";
	}

	@RequestMapping("/usr/article/doWrite")
	@ResponseBody
	public String doWrite(String title, String body, int boardId) {

		if (Ut.empty(title)) {
			return Ut.jsHistoryBack("F-1", "제목을 입력해주세요");
		}

		if (Ut.empty(body)) {
			return Ut.jsHistoryBack("F-2", "내용을 입력해주세요");
		}

		ResultData writeRd = articleService.writeArticle(title, body, rq.getLoginedMemberId(), boardId);

		int id = (int) writeRd.getData1();

		return Ut.jsReplace("S-1", Ut.f("%d번 글이 생성되었습니다", id), Ut.f("../article/list?boardId=%d", boardId));
	}

	@RequestMapping("/usr/article/detail")
	public String showDetail(Model model, int id) {

		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
		List<Reply> replies = replyService.getForPrintReplies(rq.getLoginedMemberId(), "article", id);
		int repliesCount = replies.size();
		ResultData actorCanMakeReactionRd = reactionPointService.actorCanMakeReaction(rq.getLoginedMemberId(),
				"article", id);

		model.addAttribute("article", article);
		model.addAttribute("replies", replies);
		model.addAttribute("repliesCount", repliesCount);
		model.addAttribute("actorCanMakeReactionRd", actorCanMakeReactionRd);
		model.addAttribute("actorCanMakeReaction", actorCanMakeReactionRd.isSuccess());

		if (actorCanMakeReactionRd.getResultCode().equals("F-2")) {
			int sumReactionPointByMemberId = (int) actorCanMakeReactionRd.getData1();

			if (sumReactionPointByMemberId > 0) {
				model.addAttribute("actorCanCancelGoodReaction", true);
			} else {
				model.addAttribute("actorCanCancelBadReaction", true);
			}
		}

		return "usr/article/detail";
	}

	@RequestMapping("/usr/article/increaseHitCountRd")
	@ResponseBody
	public ResultData doIncreaseHitCountRd(Model model, int id) {

		ResultData increaseHitCountRd = articleService.increaseHitCount(id);

		if (increaseHitCountRd.isFail()) {
			return increaseHitCountRd;
		}

		ResultData rd = ResultData.newData(increaseHitCountRd, "hitCount", articleService.getArticleHitCount(id));

		rd.setData2("id", id);

		return rd;
	}

	@RequestMapping("/usr/article/list")
	public String showList(Model model, @RequestParam(defaultValue = "1") int boardId,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "title, body, nickname") String searchKeywordTypeCode,
			@RequestParam(defaultValue = "") String searchKeyword) {

		Board board = boardService.getBoardById(boardId);

		if (board == null) {
			return rq.jsHistoryBackOnView("None");
		}

		int articlesCount = articleService.getArticlesCount(boardId, searchKeywordTypeCode, searchKeyword);

		int itemsInAPage = 10;
		int totalPage = (int) Math.ceil((double) articlesCount / itemsInAPage);
		
		List<Article> articles = articleService.getForPrintArticles(page, itemsInAPage, articlesCount, boardId,
				searchKeywordTypeCode, searchKeyword);

		model.addAttribute("page", page);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("articlesCount", articlesCount);
		model.addAttribute("board", board);
		model.addAttribute("articles", articles);
		model.addAttribute("searchKeywordTypeCode", searchKeywordTypeCode);
		model.addAttribute("searchKeyword", searchKeyword);
		return "usr/article/list";
	}

	/* 게시글 수정 페이지 */
	@RequestMapping("/usr/article/modify")
	public String showModify(Model model, int id) {

		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		if (article == null) {
			return rq.jsHistoryBackOnView(Ut.f("%d번 글은 존재하지 않습니다", id));
		}

		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);

		if (actorCanModifyRd.isFail()) {
			return rq.jsHistoryBackOnView(actorCanModifyRd.getMsg());
		}

		model.addAttribute("article", article);
		return "usr/article/modify";
	}

	/* 게시글 수정 */
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public String doModify(int id, String title, String body) {

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
	public String doDelete(int id, int boardId) {

		Article article = articleService.getArticle(id);

		if (article == null) {
			return Ut.jsHistoryBack("F-4", Ut.f("%d번 글은 존재하지 않습니다", id));
		}

		if (article.getMemberId() != rq.getLoginedMemberId()) {
			return Ut.jsHistoryBack("F-4", Ut.f("%d번 글에 대한 권한이 없습니다", id));
		}

		articleService.deleteArticle(id);

		return Ut.jsReplace("S-1", Ut.f("%d번 글을 삭제했습니다", id), Ut.f("../article/list?boardId=%d", boardId));
	}
}

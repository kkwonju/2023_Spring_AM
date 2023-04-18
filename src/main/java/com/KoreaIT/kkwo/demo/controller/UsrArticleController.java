package com.KoreaIT.kkwo.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.kkwo.demo.service.ArticleService;
import com.KoreaIT.kkwo.demo.util.Ut;
import com.KoreaIT.kkwo.demo.vo.Article;
import com.KoreaIT.kkwo.demo.vo.ResultData;

@Controller
public class UsrArticleController {
	@Autowired // 자동 연결
	private ArticleService articleService;

//	public UsrArticleController() {
//	}

	/*
	 * 위의 @Autowired 어노테이션은 Spring 프레임워크에서 제공하는 의존성 주입(Dependency Injection) 기능을
	 * 사용하기 위한 것입니다.
	 * 
	 * @Autowired가 붙은 필드에 대한 의존성 주입은 해당 클래스의 인스턴스 생성 후, 스프링 컨테이너가 해당 클래스의 객체를 생성할 때
	 * 실행됩니다.
	 * 
	 * 따라서, @Autowired로 주입되는 ArticleService 객체는 UsrArticleController 객체가 생성된 이후에
	 * 주입됩니다. 그리고 생성자는 객체 생성 시에 실행되므로, UsrArticleController의 생성자가 먼저 실행됩니다.
	 */

	@RequestMapping("/usr/article/delete")
	@ResponseBody
	public String doDelete(int id) {
		Article article = articleService.getArticleByInputedId(id);
		if (article == null) {
			return String.format("%d번 글은 존재하지 않습니다", id);
		}
		articleService.deleteArticle(id);
		return String.format("%d번 글이 삭제되었습니다", id);
	}

	@RequestMapping("/usr/article/modify")
	@ResponseBody
	public Object doModify(int id, String title, String body) {
		Article article = articleService.getArticleByInputedId(id);
		if (article == null) {
			return String.format("%d번 글은 존재하지 않습니다", id);
		}
		articleService.modifyArticle(id, title, body);
		article = articleService.getArticleByInputedId(id);
		return article;
	}

	// 액션 메서드
	@RequestMapping("/usr/article/doWrite")
	@ResponseBody
	public ResultData doWrite(String title, String body) {
		if(Ut.empty(title)) {
			return ResultData.from("F-1", "제목을 입력해주세요");
		}
		if(Ut.empty(body)) {
			return ResultData.from("F-1", "내용을 입력해주세요");
		}
		ResultData writeArticleRd = articleService.writeArticle(title, body);
		int id = (int) writeArticleRd.getData1();
		Article article = articleService.getArticleByInputedId(id);
		return ResultData.from(writeArticleRd.getResultCode(), writeArticleRd.getMsg(), article);
	}

	@RequestMapping("/usr/article/list")
	@ResponseBody
	public ResultData getArticles() {
		List<Article> articles =  articleService.getArticles();
		return ResultData.from("S-1", Ut.f("게시글 목록"), articles); 
	}

	@RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public ResultData getArticle(int id) {
		Article article = articleService.getArticleByInputedId(id);
		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 글은 존재하지 않습니다", id));
		}
		return ResultData.from("S-1", Ut.f("%d번 글입니다", id), article);
	}
}

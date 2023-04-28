package com.KoreaIT.kkwo.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.kkwo.demo.service.ReactionService;
import com.KoreaIT.kkwo.demo.util.Ut;
import com.KoreaIT.kkwo.demo.vo.ResultData;
import com.KoreaIT.kkwo.demo.vo.Rq;

@Controller
public class UsrReactionController {
	@Autowired
	private ReactionService reactionService;
	@Autowired
	private Rq rq;

	@RequestMapping("/usr/reactionPoint/doGoodReaction")
	@ResponseBody
	public String doGoodReaction(String relTypeCode, int relId, String replaceUri) {
		boolean actorCanMakeReaction = reactionService.actorCanMakeReaction(rq.getLoginedMemberId(), relTypeCode,
				relId);
		
		if (actorCanMakeReaction == false) {
			return Ut.jsHistoryBack("F-1" , "이미 누름");
		}

		ResultData rd = reactionService.addGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);

		if(rd.isFail()) {
			return Ut.jsHistoryBack(rd.getResultCode(), rd.getMsg());
		}
		
		return Ut.jsReplace("S-1", "좋아요!", replaceUri);
	}

	@RequestMapping("/usr/reactionPoint/doBadReaction")
	@ResponseBody
	public String doBadReaction(String relTypeCode, int relId, String replaceUri) {
		boolean actorCanMakeReaction = reactionService.actorCanMakeReaction(rq.getLoginedMemberId(), relTypeCode,
				relId);
		
		if (actorCanMakeReaction == false) {
			return Ut.jsHistoryBack("F-1", "이미 누름");
		}

		ResultData rd = reactionService.addBadReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
		
		if(rd.isFail()) {
			return Ut.jsHistoryBack(rd.getResultCode(), rd.getMsg());
		}

		return Ut.jsReplace("S-1", "싫어요!", replaceUri);
	}
}

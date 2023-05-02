package com.KoreaIT.kkwo.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.kkwo.demo.service.ReactionPointService;
import com.KoreaIT.kkwo.demo.util.Ut;
import com.KoreaIT.kkwo.demo.vo.ResultData;
import com.KoreaIT.kkwo.demo.vo.Rq;

@Controller
public class UsrReactionController {
	@Autowired
	private ReactionPointService reactionPointService;
	@Autowired
	private Rq rq;

	@RequestMapping("/usr/reactionPoint/doGoodReaction")
	@ResponseBody
	public String doGoodReaction(String relTypeCode, int relId, String replaceUri) {
		ResultData actorCanMakeReaction = reactionPointService.actorCanMakeReaction(rq.getLoginedMemberId(),
				relTypeCode, relId);

		if (actorCanMakeReaction.isFail()) {
			return Ut.jsHistoryBack("F-1", "이미 누름");
		}

		ResultData rd = reactionPointService.addGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);

		if (rd.isFail()) {
			return Ut.jsHistoryBack(rd.getResultCode(), rd.getMsg());
		}

		return Ut.jsReplace("S-1", "좋아요!", replaceUri);
	}

	@RequestMapping("/usr/reactionPoint/doBadReaction")
	@ResponseBody
	public String doBadReaction(String relTypeCode, int relId, String replaceUri) {
		ResultData actorCanMakeReaction = reactionPointService.actorCanMakeReaction(rq.getLoginedMemberId(),
				relTypeCode, relId);

		if (actorCanMakeReaction.isFail()) {
			return Ut.jsHistoryBack("F-1", "이미 누름");
		}

		ResultData rd = reactionPointService.addBadReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);

		if (rd.isFail()) {
			return Ut.jsHistoryBack(rd.getResultCode(), rd.getMsg());
		}

		return Ut.jsReplace("S-1", "싫어요!", replaceUri);
	}

	@RequestMapping("/usr/reactionPoint/doCancelGoodReaction")
	@ResponseBody
	public String doCancelGoodReaction(String relTypeCode, int relId, String replaceUri) {
		ResultData actorCanMakeReaction = reactionPointService.actorCanMakeReaction(rq.getLoginedMemberId(),
				relTypeCode, relId);

		if (actorCanMakeReaction.isSuccess()) {
			return Ut.jsHistoryBack("F-1", "이미 누름");
		}

		ResultData rd = reactionPointService.removeGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);


		return Ut.jsReplace("S-1", "좋아요 취소!", replaceUri);
	}

	@RequestMapping("/usr/reactionPoint/doCancelBadReaction")
	@ResponseBody
	public String doCancelBadReaction(String relTypeCode, int relId, String replaceUri) {
		ResultData actorCanMakeReaction = reactionPointService.actorCanMakeReaction(rq.getLoginedMemberId(),
				relTypeCode, relId);

		if (actorCanMakeReaction.isSuccess()) {
			return Ut.jsHistoryBack("F-1", "이미 누름");
		}

		ResultData rd = reactionPointService.removeBadReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);

		return Ut.jsReplace("S-1", "싫어요 취소!", replaceUri);
	}
}

package com.KoreaIT.kkwo.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.JdbcUpdateAffectedIncorrectNumberOfRowsException;
import org.springframework.stereotype.Service;

import com.KoreaIT.kkwo.demo.repository.ReactionPointRepository;
import com.KoreaIT.kkwo.demo.vo.ResultData;

@Service
public class ReactionPointService {
	@Autowired
	private ReactionPointRepository reactionPointRepository;
	@Autowired
	private ArticleService articleService;

	public ResultData actorCanMakeReaction(int actorId, String relTypeCode, int relId) {
		if (actorId == 0) {
			return ResultData.from("F-1", "로그인먼저");
		}
		int sumReactionPointByMemberId = reactionPointRepository.getSumReactionPointByMemberId(actorId, relTypeCode,
				relId);

		if (sumReactionPointByMemberId != 0) {
			return ResultData.from("F-2", "추천 불가", "sumReactionPointByMemberId", sumReactionPointByMemberId);
		}
		return ResultData.from("S-1", "추천 가능", "sumReactionPointByMemberId", sumReactionPointByMemberId);
	}

	public ResultData addGoodReactionPoint(int actorId, String relTypeCode, int relId) {
		int affectedRow = reactionPointRepository.addGoodReactionPoint(actorId, relTypeCode, relId);

		if (affectedRow == 0) {
			return ResultData.from("F-1", "좋아요 처리 실패");
		}

		switch (relTypeCode) {
		case "article":
			articleService.increaseGoodReactionPoint(relId);
			break;
//		case "reply":
//			replyService.
		}

		return ResultData.from("S-1", "좋아요 처리됌");
	}

	public ResultData addBadReactionPoint(int actorId, String relTypeCode, int relId) {
		int affectedRow = reactionPointRepository.addBadReactionPoint(actorId, relTypeCode, relId);

		if (affectedRow == 0) {
			return ResultData.from("F-1", "싫어요 처리 실패");
		}

		switch (relTypeCode) {
		case "article":
			articleService.increaseBadReactionPoint(relId);
			break;
//		case "reply":
//			replyService.
		}

		return ResultData.from("S-1", "싫어요 처리됌");
	}

	public ResultData removeGoodReactionPoint(int loginedMemberId, String relTypeCode, int relId) {
		reactionPointRepository.removeReactionPoint(loginedMemberId, relTypeCode, relId);

		switch (relTypeCode) {
		case "article":
			articleService.decreaseGoodReactionPoint(relId);
			break;
//		case "reply":
//			replyService.
		}

		return ResultData.from("S-1", "좋아요 취소 처리됌");
	}

	public ResultData removeBadReactionPoint(int loginedMemberId, String relTypeCode, int relId) {
		reactionPointRepository.removeReactionPoint(loginedMemberId, relTypeCode, relId);

		switch (relTypeCode) {
		case "article":
			articleService.decreaseBadReactionPoint(relId);
			break;
//		case "reply":
//			replyService.
		}

		return ResultData.from("S-1", "싫어요 취소 처리됌");
	}

}
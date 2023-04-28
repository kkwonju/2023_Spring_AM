package com.KoreaIT.kkwo.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.kkwo.demo.repository.ReactionRepository;
import com.KoreaIT.kkwo.demo.vo.ReactionPoint;
import com.KoreaIT.kkwo.demo.vo.ResultData;

@Service
public class ReactionService {
	@Autowired
	private ReactionRepository reactionRepository;
	@Autowired
	private ArticleService articleService;

	public boolean actorCanMakeReaction(int actorId, String relTypeCode, int relId) {
		return reactionRepository.getSumReactionPointByMemberId(actorId, relTypeCode, relId) == 0;
	}

	public ResultData addGoodReactionPoint(int actorId, String relTypeCode, int relId) {
		int affectedRow = reactionRepository.addGoodReactionPoint(actorId, relTypeCode, relId);
		
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
		int affectedRow = reactionRepository.addBadReactionPoint(actorId, relTypeCode, relId);
		
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

}
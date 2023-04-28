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

	public ResultData increaseReactionPoint(int memberId, int relId) {
		int affectRowCount = reactionRepository.increaseReactionPoint(memberId, relId);

		if (affectRowCount == 0) {
			ResultData.from("F-1", "반응 추가 실패", "affectRowRd", affectRowCount);
		}
		return ResultData.from("S-1", "반응 추가 성공", "affectRowRd", affectRowCount);
	}

	public ResultData decreaseReactionPoint(int memberId, int relId) {
		int affectRowCount = reactionRepository.decreaseReactionPoint(memberId, relId);

		if (affectRowCount == 0) {
			ResultData.from("F-1", "반응 삭제 실패", "affectRowRd", affectRowCount);
		}
		return ResultData.from("S-1", "반응 삭제 성공", "affectRowRd", affectRowCount);
	}

	public ReactionPoint getReactionPoint(int relId) {
		return reactionRepository.getReactionPoint(relId);
	}
}

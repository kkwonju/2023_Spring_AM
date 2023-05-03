package com.KoreaIT.kkwo.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.kkwo.demo.repository.ReplyRepository;
import com.KoreaIT.kkwo.demo.util.Ut;
import com.KoreaIT.kkwo.demo.vo.Reply;
import com.KoreaIT.kkwo.demo.vo.ResultData;

@Service
public class ReplyService {
	@Autowired
	private ReplyRepository replyRepository;

	public ReplyService(ReplyRepository replyRepository) {
		this.replyRepository = replyRepository;
	}

	public List<Reply> getForPrintReplies(int actorId, String relTypeCode, int relId) {
		List<Reply> replies = replyRepository.getForPrintReplies(actorId, relTypeCode, relId);
		
		for(Reply reply : replies) {
			controlForPrintData(actorId, reply);
		}
		return replies;
	}

	public ResultData writeReply(String relTypeCode, int relId, String body, int actorId) {
		replyRepository.writeReply(relTypeCode, relId, body, actorId);
		int id = replyRepository.getLastInsertId();
		return ResultData.from("S-1", Ut.f("%d번 댓글 생성", id), "id", id);
	}

	// 손 댈 수 있는지 여부
	private void controlForPrintData(int actorId, Reply reply) {
		if (reply == null) {
			return;
		}

		ResultData actorCanModifyRd = actorCanModify(actorId, reply);
		reply.setActorCanModify(actorCanModifyRd.isSuccess());

		ResultData actorCanDeleteRd = actorCanDelete(actorId, reply);
		reply.setActorCanDelete(actorCanDeleteRd.isSuccess());
	}

	/* 삭제 권한 체크 */
	private ResultData actorCanDelete(int actorId, Reply reply) {
		if (reply == null) {
			return ResultData.from("F-1", "게시물이 존재하지 않습니다");
		}
		if (reply.getMemberId() != actorId) {
			return ResultData.from("F-2", "해당 게시물에 대한 권한이 없습니다");
		}

		return ResultData.from("S-1", "삭제 가능");
	}

	/* 수정 권한 체크 */
	public ResultData actorCanModify(int actorId, Reply reply) {
		if (reply.getMemberId() != actorId) {
			return ResultData.from("F-3", "해당 글에 대한 권한이 없습니다");
		}
		return ResultData.from("S-1", "수정 가능");
	}

	public Reply getReply(int id) {
		return replyRepository.getReply(id);
	}

	public ResultData deleteReply(int id) {
		replyRepository.deleteReply(id);
		return ResultData.from("S-1", Ut.f("%d번 댓글 삭제", id), "id", id);
	}

}

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
	
	public List<Reply> getForPrintReplies(int actorId , String relTypeCode, int relId) {
		return replyRepository.getForPrintReplies(actorId, relTypeCode, relId);
	}

	public ResultData writeReply(String relTypeCode, int relId, String body, int actorId) {
		replyRepository.writeReply(relTypeCode, relId, body, actorId);
		int id = replyRepository.getLastInsertId();
		return ResultData.from("S-1", Ut.f("%d번 댓글 생성", id), "id", id);
	}
	
	
}

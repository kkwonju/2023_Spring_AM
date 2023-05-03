package com.KoreaIT.kkwo.demo.controller;

import javax.swing.UIClientPropertyKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.kkwo.demo.service.ReplyService;
import com.KoreaIT.kkwo.demo.util.Ut;
import com.KoreaIT.kkwo.demo.vo.Reply;
import com.KoreaIT.kkwo.demo.vo.ResultData;
import com.KoreaIT.kkwo.demo.vo.Rq;

@Controller
public class UsrReplyController {
	@Autowired
	private Rq rq;
	@Autowired
	private ReplyService replyService;

	public UsrReplyController(ReplyService replyService) {
		this.replyService = replyService;
	}

	@RequestMapping("/usr/reply/doWrite")
	@ResponseBody
	public String doWrite(String relTypeCode, int relId, String body, String replaceUri) {

		if (Ut.empty(relTypeCode)) {
			return Ut.jsHistoryBack("F-2", "relTypeCode을(를) 입력해주세요");
		}

		if (Ut.empty(relId)) {
			return Ut.jsHistoryBack("F-2", "relId을(를) 입력해주세요");
		}

		if (Ut.empty(body)) {
			return Ut.jsHistoryBack("F-2", "내용을 입력해주세요");
		}

		ResultData writeReplyRd = replyService.writeReply(relTypeCode, relId, body, rq.getLoginedMemberId());

		int id = (int) writeReplyRd.getData1();

		if (Ut.empty(replaceUri)) {
			replaceUri = Ut.f("../article/detail?id=%d", relId);
		}

		return Ut.jsReplace("S-1", Ut.f("%d번 댓글이 생성되었습니다", id), replaceUri);
	}
	
	@RequestMapping("/usr/reply/delete")
	@ResponseBody
	public String doDelete(int id, String replaceUri) {

		Reply reply = replyService.getReply(id);

		if (reply == null) {
			return Ut.jsHistoryBack("F-4", Ut.f("%d번 댓글은 존재하지 않습니다", id));
		}

		if (reply.getMemberId() != rq.getLoginedMemberId()) {
			return Ut.jsHistoryBack("F-4", Ut.f("%d번 댓글에 대한 권한이 없습니다", id));
		}

		ResultData deleteReplyRd = replyService.deleteReply(id);
		
		if(Ut.empty(replaceUri)) {
			switch(reply.getRelTypeCode()) {
			case "article":
				replaceUri = Ut.f("../article/detail?id=%d", reply.getRelId());
				break;
			}
		}
		
		return Ut.jsReplace("S-1", Ut.f("%d번 댓글을 삭제했습니다", id), "../article/list?boardId=1");
	}
}

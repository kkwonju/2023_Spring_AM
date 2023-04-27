package com.KoreaIT.kkwo.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.kkwo.demo.service.ReactionService;
import com.KoreaIT.kkwo.demo.vo.ResultData;
import com.KoreaIT.kkwo.demo.vo.Rq;

@Controller
public class UsrReactionController {
	@Autowired
	private ReactionService reactionService;
	@Autowired
	private Rq rq;

	@RequestMapping("/usr/reactionPoint/increaseReactionPointRd")
	@ResponseBody
	public ResultData increaseReactionPointRd(int memberId, int id) {
		
		ResultData increaseReactionPointRd = reactionService.increaseReactionPoint(memberId, id);
		
		if(increaseReactionPointRd.isFail()) {
			return increaseReactionPointRd;
		}
		
		ResultData rd = ResultData.newData(increaseReactionPointRd, "reactionPoint", reactionService.getReactionPoint(id));
		
		rd.setData2("id", id);
		
		return rd;
	}
	
	@RequestMapping("/usr/reactionPoint/decreaseReactionPointRd")
	@ResponseBody
	public ResultData decreaseReactionPointRd(int memberId, int id) {
		
		ResultData decreaseReactionPointRd = reactionService.decreaseReactionPoint(memberId, id);
		
		if(decreaseReactionPointRd.isFail()) {
			return decreaseReactionPointRd;
		}
		
		ResultData rd = ResultData.newData(decreaseReactionPointRd, "reactionPoint", reactionService.getReactionPoint(id));
		
		return rd;
	}
}

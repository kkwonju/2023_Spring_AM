package com.KoreaIT.kkwo.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.kkwo.demo.service.MemberService;
import com.KoreaIT.kkwo.demo.util.Ut;
import com.KoreaIT.kkwo.demo.vo.Member;
import com.KoreaIT.kkwo.demo.vo.ResultData;
import com.fasterxml.jackson.core.io.UTF32Reader;

@Controller
public class UsrMemberController {
	@Autowired
	private MemberService memberService;

	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public ResultData doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum,
			String email) {

		if (Ut.empty(loginId)) {
			return ResultData.from("F-1", Ut.f("아이디를 입력해주세요"));
		}
		if (Ut.empty(loginPw)) {
			return ResultData.from("F-2", Ut.f("비밀번호를 입력해주세요"));
		}
		if (Ut.empty(name)) {
			return ResultData.from("F-3", Ut.f("이름을 입력해주세요"));
		}
		if (Ut.empty(nickname)) {
			return ResultData.from("F-4", Ut.f("닉네임을 입력해주세요"));
		}
		if (Ut.empty(cellphoneNum)) {
			return ResultData.from("F-5", Ut.f("전화번호를 입력해주세요"));
		}
		if (Ut.empty(email)) {
			return ResultData.from("F-6", Ut.f("이메일을 입력해주세요"));
		}

		ResultData joinRd = memberService.doJoin(loginId, loginPw, name, nickname, cellphoneNum, email);

		if (joinRd.isFail()) {
			return joinRd;
		}
		Member member = memberService.getMemberById((int) joinRd.getData1());
		return ResultData.newData(joinRd, member);
	}
}

package com.KoreaIT.kkwo.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.kkwo.demo.service.MemberService;
import com.KoreaIT.kkwo.demo.vo.Member;

@Controller
public class UsrMemberController {
	@Autowired
	private MemberService memberService;

	@RequestMapping("/usr/member/join")
	@ResponseBody
	public Object doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum,
			String email) {
		boolean isDuplicateId = memberService.isDuplicateId(loginId);
		if(!isDuplicateId) {
			return "이미 사용중인 아이디입니다";
		}
		int id =  memberService.doJoin(loginId, loginPw, name, nickname, cellphoneNum, email);
		Member member = memberService.getMemberById(id);
		return member;
	}
}

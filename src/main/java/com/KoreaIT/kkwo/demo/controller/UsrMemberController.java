package com.KoreaIT.kkwo.demo.controller;

import javax.servlet.http.HttpSession;

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
	public ResultData<Member> doJoin(HttpSession httpSession, String loginId, String loginPw, String name, String nickname, String cellphoneNum,
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

		ResultData<Integer> joinRd = memberService.doJoin(loginId, loginPw, name, nickname, cellphoneNum, email);

		if (joinRd.isFail()) {
			return (ResultData) joinRd;
		}
		Member member = memberService.getMemberById(joinRd.getData1());
		return ResultData.newData(joinRd, member);
	}

	@RequestMapping("/usr/member/login")
	@ResponseBody
	public ResultData doLogin(HttpSession httpSession, String loginId, String loginPw) {
		boolean isLogined = httpSession.getAttribute("loginedMemberId") != null;

		if (isLogined) {
			return ResultData.from("F9", "이미 로그인 중입니다");
		}

		if (Ut.empty(loginId)) {
			return ResultData.from("F-10", "아이디를 입력해주세요");
		}
		
		if (Ut.empty(loginPw)) {
			return ResultData.from("F-11", "비밀번호를 입력해주세요");
		}

		Member member = memberService.getMemberByLoginId(loginId);

		if (member == null) {
			return ResultData.from("F-12", "일치하는 회원이 없습니다");
		}
		
		if (!member.getLoginPw().equals(loginPw)) {
			return ResultData.from("F-13", "비밀번호가 틀렸습니다");
		}

		httpSession.setAttribute("loginedMemberId", member.getId());
		return ResultData.from("S-1", Ut.f("%s님 환영합니다", member.getNickname()));
	}

	@RequestMapping("/usr/member/logout")
	@ResponseBody
	public ResultData doLogout(HttpSession httpSession) {
		boolean isLogined = httpSession.getAttribute("loginedMemberId") != null;
		if(!isLogined) {
			return ResultData.from("F-14", "로그아웃 상태입니다");
		}
		httpSession.removeAttribute("loginedMemberId");
		return ResultData.from("S-1", "로그아웃되었습니다");
		
		
	}
}

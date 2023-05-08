package com.KoreaIT.kkwo.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.kkwo.demo.service.MemberService;
import com.KoreaIT.kkwo.demo.util.Ut;
import com.KoreaIT.kkwo.demo.vo.Member;
import com.KoreaIT.kkwo.demo.vo.Reply;
import com.KoreaIT.kkwo.demo.vo.ResultData;
import com.KoreaIT.kkwo.demo.vo.Rq;

@Controller
public class UsrMemberController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private Rq rq;

	@RequestMapping("/usr/member/join")
	public String showJoinForm() {
		return "usr/member/join";
	}
	
	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public String doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum,
			String email, @RequestParam(defaultValue = "/") String replaceUri) {

		if (rq.isLogined()) {
			return Ut.jsHistoryBack("F-0", "로그아웃 후 이용해주세요");
		}
		if (Ut.empty(loginId)) {
			return Ut.jsHistoryBack("F-1", "아이디를 입력해주세요");
		}
		if (Ut.empty(loginPw)) {
			return Ut.jsHistoryBack("F-2", "비밀번호를 입력해주세요");
		}
		if (Ut.empty(name)) {
			return Ut.jsHistoryBack("F-3", "이름을 입력해주세요");
		}
		if (Ut.empty(nickname)) {
			return Ut.jsHistoryBack("F-4", "닉네임을 입력해주세요");
		}
		if (Ut.empty(cellphoneNum)) {
			return Ut.jsHistoryBack("F-5", "전화번호를 입력해주세요");
		}
		if (Ut.empty(email)) {
			return Ut.jsHistoryBack("F-6", "이메일을 입력해주세요");
		}

		ResultData<Integer> joinRd = memberService.doJoin(loginId, loginPw, name, nickname, cellphoneNum, email);

		if (joinRd.isFail()) {
			return Ut.jsHistoryBack(joinRd.getResultCode(), joinRd.getMsg());
		}

		String afterJoinUri = "../member/login?afterLoginUri=" + Ut.getEncodedUri(replaceUri);

		return Ut.jsReplace("S-1", Ut.f("회원가입이 완료되었습니다"), afterJoinUri);
	}

	@RequestMapping("/usr/member/login")
	public String showLoginForm(Model model) {
		return "usr/member/login";
	}

	@RequestMapping("/usr/member/doLogin")
	@ResponseBody
	public String doLogin(HttpSession httpSession, String loginId, String loginPw, @RequestParam(defaultValue = "/") String replaceUri) {
		if (rq.isLogined()) {
			return Ut.jsHistoryBack("F-0", "이미 로그인 상태입니다");
		}

		if (Ut.empty(loginId)) {
			return Ut.jsHistoryBack("F-1", "아이디를 입력해주세요");
		}

		if (Ut.empty(loginPw)) {
			return Ut.jsHistoryBack("F-2", "비밀번호를 입력해주세요");
		}

		Member member = memberService.getMemberByLoginId(loginId);

		if (member == null) {
			return Ut.jsHistoryBack("F-3", Ut.f("%s는 존재하지 않는 아이디입니다", loginId));
		}

		if (!member.getLoginPw().equals(loginPw)) {
			return Ut.jsHistoryBack("F-4", "비밀번호가 일치하지 않습니다");
		}

		rq.login(member);
		
		if(!replaceUri.startsWith("%")) {
			replaceUri = "/";
		}

		return Ut.jsReplace("S-1", Ut.f("%s님 환영합니다", member.getNickname()), replaceUri);
	}

	@RequestMapping("/usr/member/logout")
	@ResponseBody
	public String doLogout(@RequestParam(defaultValue = "/") String replaceUri) {

		rq.logout();

		return Ut.jsReplace("S-1", "로그아웃 되었습니다", replaceUri);
	}

	@RequestMapping("/usr/member/myPage")
	public String showMyPage(Model model) {
		Member member = rq.getLoginedMember();
		model.addAttribute("member", member);
		return "usr/member/myPage";
	}

	@RequestMapping("/usr/member/checkPw")
	public String showCheckPw(Model model) {
		return "usr/member/checkPw";
	}

	@RequestMapping("/usr/member/doCheckPw")
	@ResponseBody
	public String checkPw(String loginPw, String replaceUri) {

		if (Ut.empty(loginPw)) {
			return Ut.jsHistoryBack("F-1", "비밀번호 입력해");
		}

		if (!loginPw.equals(rq.getLoginedMember().getLoginPw())) {
			return Ut.jsHistoryBack("F-1", "비밀번호가 틀립니다");
		}

		return Ut.jsReplace("S-1", "비밀번호 일치", replaceUri);
	}

	@RequestMapping("/usr/member/modify")
	public String showModifyForm() {
		return "usr/member/modify";
	}

	@RequestMapping("/usr/member/doModify")
	@ResponseBody
	public String doModify(String loginPw, String name, String nickname, String cellphoneNum, String email) {

		if (Ut.empty(loginPw)) {
			loginPw = null;
		}
		if (Ut.empty(name)) {
			return Ut.jsHistoryBack("F-1", "이름을 입력해주세요");
		}
		if (Ut.empty(nickname)) {
			return Ut.jsHistoryBack("F-1", "닉네임을 입력해주세요");
		}
		if (Ut.empty(cellphoneNum)) {
			return Ut.jsHistoryBack("F-1", "전화번호를 입력해주세요");
		}
		if (Ut.empty(email)) {
			return Ut.jsHistoryBack("F-1", "이메일을 입력해주세요");
		}

		Member member = memberService.getMemberById(rq.getLoginedMemberId());

		ResultData memberModifyRd = memberService.modifyMember(rq.getLoginedMemberId(), loginPw, name, nickname, cellphoneNum, email);

		return Ut.jsReplace(memberModifyRd.getResultCode(), memberModifyRd.getMsg(), "../member/myPage");
	}
}
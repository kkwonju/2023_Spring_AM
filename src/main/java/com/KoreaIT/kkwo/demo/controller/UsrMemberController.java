package com.KoreaIT.kkwo.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
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
	@ResponseBody
	public ResultData<Member> doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum,
			String email) {

		if (rq.isLogined()) {
			return ResultData.from("F-0", "로그아웃 후 이용해주세요");
		}

		if (Ut.empty(loginId)) {
			return ResultData.from("F-1", "아이디를 입력해주세요");
		}
		if (Ut.empty(loginPw)) {
			return ResultData.from("F-2", "비밀번호를 입력해주세요");
		}
		if (Ut.empty(name)) {
			return ResultData.from("F-3", "이름을 입력해주세요");
		}
		if (Ut.empty(nickname)) {
			return ResultData.from("F-4", "닉네임을 입력해주세요");
		}
		if (Ut.empty(cellphoneNum)) {
			return ResultData.from("F-5", "전화번호를 입력해주세요");
		}
		if (Ut.empty(email)) {
			return ResultData.from("F-6", "이메일을 입력해주세요");
		}

		ResultData<Integer> joinRd = memberService.doJoin(loginId, loginPw, name, nickname, cellphoneNum, email);

		if (joinRd.isFail()) {
			return (ResultData) joinRd;
		}

		Member member = memberService.getMemberById(joinRd.getData1());

		return ResultData.newData(joinRd, "Member", member);
	}

	@RequestMapping("/usr/member/login")
	public String showLoginForm() {
		return "usr/member/login";
	}

	@RequestMapping("/usr/member/doLogin")
	@ResponseBody
	public String doLogin(HttpSession httpSession, String loginId, String loginPw) {

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

		return Ut.jsReplace("S-1", Ut.f("%s님 환영합니다", member.getNickname()), "/");
	}

	@RequestMapping("/usr/member/logout")
	@ResponseBody
	public String doLogout() {

		rq.logout();

		return Ut.jsReplace("S-1", "로그아웃 되었습니다", "../home/main");
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
	public String checkPw(Model model, String loginId, String loginPw) {
//		if(Ut.empty(replaceUri)) {
//			replaceUri = "/usr/member/checkPw";
//		}
		if(!loginPw.equals(rq.getLoginedMember().getLoginPw())) {
			return rq.jsHistoryBackOnView("비밀번호가 틀립니다");
		}
		Member member = rq.getLoginedMember();
		model.addAttribute("member", member);
		return "usr/member/modify";
	}
	
	@RequestMapping("/usr/member/modify")
	public String showModifyForm() {
		return "usr/member/modify";
	}
	
	@RequestMapping("/usr/member/doModify")
	@ResponseBody
	public String doModify(int id, String loginPw, String name, String nickname, String cellphoneNum, String email, String replaceUri) {

		Member member = memberService.getMemberById(id);

		if (member == null) {
			return Ut.jsHistoryBack("F-1", Ut.f("해당 회원은 존재하지 않습니다", id));
		}

		ResultData actorCanModifyRd = memberService.actorCanModify(rq.getLoginedMemberId(), member);

		if (actorCanModifyRd.isFail()) {
			return Ut.jsHistoryBack("F-2", actorCanModifyRd.getMsg());
		}

		ResultData memberModifyRd = memberService.modifyMember(id, loginPw, name, nickname, cellphoneNum, email);
		
		if (Ut.empty(replaceUri)) {
			replaceUri = "../member/myPage";
		}

		return Ut.jsReplace(memberModifyRd.getResultCode(), memberModifyRd.getMsg(), replaceUri);
	}
}
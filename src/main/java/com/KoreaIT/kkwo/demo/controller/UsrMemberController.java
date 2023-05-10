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
	
	@RequestMapping("/usr/member/getLoginIdDup")
	@ResponseBody
	public ResultData getLoginIdDup(String loginId) {
		
		if(Ut.empty(loginId)) {
			return ResultData.from("F-1", "필수 정보입니다");
		}
		
		if(loginId.length() < 5) {
			return ResultData.from("F-2", "5 ~ 20자 이내로 입력해주세요");
		}
		
		Member existsMember = memberService.getMemberByLoginId(loginId);
		
		if (existsMember != null) {
			return	ResultData.from("F-3", "이미 사용 중인 아이디입니다");
		}
		
		return ResultData.from("S-1", "이용 가능한 아이디입니다", "loginId", loginId);
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
	
	@RequestMapping("/usr/member/findLoginId")
	public String showFindLoginIdForm() {
		return "usr/member/findLoginId";
	}
	
	@RequestMapping("/usr/member/doFindLoginId")
	public String doFindLoginId(String email) {
		if (Ut.empty(email)) {
			return rq.jsHistoryBackOnView("이메일을 입력해주세요");
		}
		
		Member member = memberService.getMemberByEamil(email);

		if (member == null) {
			return rq.jsHistoryBackOnView(Ut.f("일치하는 이메일이 없습니다"));
		}
		
		return rq.jsReplaceOnView( Ut.f("LoginId : %s", member.getLoginId()), "/usr/member/login");
	}
	
	@RequestMapping("/usr/member/findLoginPw")
	public String showFindLoginPwForm() {
		return "usr/member/findLoginPw";
	}
	
	@RequestMapping("/usr/member/doFindLoginPw")
	public String doFindLoginPw(String loginId, String email) {
		if (Ut.empty(loginId)) {
			return Ut.jsHistoryBack("F-0", "아이디를 입력해주세요");
		}
		if (Ut.empty(email)) {
			return Ut.jsHistoryBack("F-0", "이메일을 입력해주세요");
		}
		
		Member member = memberService.getMemberByLoginId(loginId);
		
		if (member == null) {
			return rq.jsHistoryBackOnView(Ut.f("일치하는 아이디가 없습니다"));
		}

		if (!member.getEmail().equals(email)) {
			return rq.jsHistoryBackOnView(Ut.f("일치하는 이메일이 없습니다"));
		}
		
		return rq.jsReplaceOnView( Ut.f("LoginPw : %s", member.getLoginPw()), "/usr/member/login");
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

		if (!member.getLoginPw().equals(Ut.sha256(loginPw))) {
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
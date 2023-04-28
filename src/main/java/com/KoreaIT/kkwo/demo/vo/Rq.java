package com.KoreaIT.kkwo.demo.vo;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.taglibs.standard.tag.common.sql.QueryTagSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.KoreaIT.kkwo.demo.service.MemberService;
import com.KoreaIT.kkwo.demo.util.Ut;

import lombok.Getter;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Rq {
	private MemberService memberService;
	@Getter
	private boolean isLogined;
	@Getter
	private Member loginedMember;
	@Getter
	private int loginedMemberId;
	@Getter
	private int loginedMemberAuthLevel;

	private HttpServletRequest req;
	private HttpServletResponse resp;
	private HttpSession session;

	public Rq(HttpServletRequest req, HttpServletResponse resp, MemberService memberService) {
		this.req = req;
		this.resp = resp;
		this.session = req.getSession();
		this.memberService = memberService;

		int loginedMemberId = 0;
		int loginedMemberAuthLevel = 0;
		boolean isLogined = false;
		Member loginedMember = null;

		if (session.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) session.getAttribute("loginedMemberId");
			loginedMember = memberService.getMemberById(loginedMemberId);
			loginedMemberAuthLevel = loginedMember.getAuthLevel();
		}

		this.isLogined = isLogined;
		this.loginedMemberId = loginedMemberId;
		this.loginedMember = loginedMember;
		this.loginedMemberAuthLevel = loginedMemberAuthLevel;
		
		this.req.setAttribute("rq", this);
	}

	public String jsHistoryBackOnView(String msg) {
		req.setAttribute("msg", msg);
		req.setAttribute("historyBack", true);
		return "usr/common/js";
	}

	public void printHistoryBackJs(String msg) throws IOException {
		resp.setContentType("text/html; charset=UTF-8;");
		print(Ut.jsHistoryBack("F-B", msg));
	}

	public void printReplaceJs(String msg, String uri) throws IOException {
		resp.setContentType("text/html; charset=UTF-8;");
		print(Ut.jsReplace("F-B", msg, uri));
	}

	public void print(String str) {
		try {
			resp.getWriter().append(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//	public void println(String str) {
//		print(str + "\n");
//	}

	public void login(Member member) {
		session.setAttribute("loginedMemberId", member.getId());
	}

	public void logout() {
		session.removeAttribute("loginedMemberId");
	}
	
	// uri 가져오기
	public String getCurrentUri() {
		String currentUri = req.getRequestURI();
		String queryString = req.getQueryString();
		
		if(queryString != null && queryString.length() > 0) {
			currentUri += "?" + queryString;
		}
		
		return currentUri;
	}
	
	public String getEncodedCurrentUri() {
		return Ut.getEncodedCurrentUri(getCurrentUri());
	}
	
	// Rq 객체 유도
	// 삭제 x, BeforeActionInterceptor에서 강제 호출
	public void initOnBeforeActionInterceptor() {
	}

}

package com.KoreaIT.kkwo.demo.vo;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Mapper;
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
	@Getter
	private String encodedUri;

	private HttpServletRequest req;
	private HttpServletResponse resp;
	private HttpSession session;

	private Map<String, String> paramMap;

	public Rq(HttpServletRequest req, HttpServletResponse resp, MemberService memberService) {
		this.req = req;
		this.resp = resp;

		this.session = req.getSession();

		paramMap = Ut.getParamMap(req);

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

	public String jsLocationReloadOnView(String msg) {
		req.setAttribute("msg", msg);
		req.setAttribute("locationReload", true);
		return "usr/common/js";
	}

	public String jsReplaceOnView(String msg, String replaceUri) {
		req.setAttribute("msg", msg);
		req.setAttribute("replaceUri", replaceUri);
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

		System.out.println(currentUri);
		System.out.println(queryString);

		if (queryString != null && queryString.length() > 0) {
			currentUri += "?" + queryString;
		}

		System.out.println(currentUri);
		return currentUri;
	}

	// Rq 객체 유도
	// 삭제 x, BeforeActionInterceptor에서 강제 호출
	public void initOnBeforeActionInterceptor() {
	}

	public boolean isNotLogined() {
		return !isLogined;
	}

	public void run() {
		System.out.println("===========================run A");
	}

	
	public String getLoginUri() {
		return "../member/login?afterLoginUri=" + getAfterLoginUri();
	}

	public String getLogoutUri() {
		String requestUri = req.getRequestURI();

		switch (requestUri) {
		case "/usr/article/write":
			return "../member/doLogout?afterLogoutUri=" + "/";
		}

		return "../member/doLogout?afterLogoutUri=" + getAfterLogoutUri();
	}

	public String getAfterLogoutUri() {
		return getEncodedCurrentUri();
	}

	private String getAfterLoginUri() {
//		로그인 후 접근 불가 페이지

		String requestUri = req.getRequestURI();

		switch (requestUri) {
		case "/usr/member/login":
		case "/usr/member/join":
			return Ut.getEncodedUri(Ut.getAttr(paramMap, "afterLoginUri", ""));
		}

		return getEncodedCurrentUri();
	}
	
	public String getEncodedCurrentUri() {
		return Ut.getEncodedCurrentUri(getCurrentUri());
	}
}

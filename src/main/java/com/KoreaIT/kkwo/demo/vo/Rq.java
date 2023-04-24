package com.KoreaIT.kkwo.demo.vo;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.KoreaIT.kkwo.demo.util.Ut;

import lombok.Getter;

public class Rq {
	@Getter
	private boolean isLogined;
	@Getter
	private int loginedMemberId;

	private HttpServletRequest req;
	private HttpServletResponse resp;
	private HttpSession session;

	public Rq(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
		this.session = req.getSession();

		boolean isLogined = false;
		int loginedMemberId = 0;

		if (session.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) session.getAttribute("loginedMemberId");
		}

		this.isLogined = isLogined;
		this.loginedMemberId = loginedMemberId;
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
}

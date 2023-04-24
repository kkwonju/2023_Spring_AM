package com.KoreaIT.kkwo.demo.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Ut {
	private HttpServletRequest req;
	private HttpServletResponse resp;

	public static boolean empty(Object obj) {

		if (obj == null) {
			return true;
		}

		if (obj instanceof String == false) {
			return true;
		}

		String str = (String) obj;

		return str.trim().length() == 0;
	}

	public static String f(String format, Object... args) {
		return String.format(format, args);
	}

	/* 접근 시 되돌려보내기 , javascript 문법으로 알림창 띄우기 */
	public static String jsHistoryBack(String resultCode, String msg) {
		if (msg == null) {
			msg = "";
		}
		
		return Ut.f("""
				<script>
					const msg = '%s'.trim();
					if( msg.length > 0 ){
						alert(msg);
					}
					history.back();
				</script>
				""", msg);
	}

	/* 접근 시 특정 페이지로 , */
	public static String jsReplace(String resultCode, String msg, String uri) {
		if (msg == null) {
			msg = "";
		}
		if (uri == null) {
			uri = "/";
		}

		return Ut.f("""
					<script>
					const msg = '%s'.trim();
					if( msg.length > 0 ){
						alert(msg);
					}
					location.replace('%s');
				</script>
				""", msg, uri);
	}

	public static String jsHistoryBackOnView(HttpServletRequest req, String msg) {
		req.setAttribute("msg", msg);
		req.setAttribute("historyBack", true);
		return "usr/common/js";
	}
}

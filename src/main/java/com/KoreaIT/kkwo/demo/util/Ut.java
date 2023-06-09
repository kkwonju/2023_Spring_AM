package com.KoreaIT.kkwo.demo.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Ut {
	private HttpServletRequest req;
	private HttpServletResponse resp;

	public static boolean empty(Object obj) {

		if (obj == null) {
			return true;
		}

		if (obj instanceof Integer) {
			return (int) obj == 0;
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

	public static String jsLocationReload(String msg) {
		if (msg == null) {
			msg = "";
		}

		return Ut.f("""
				<script>
					const msg = '%s'.trim();
					if( msg.length > 0 ){
						alert(msg);
					}
					location.reload();
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

	public static String getEncodedCurrentUri(String currentUri) {
		try {
			return URLEncoder.encode(currentUri, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return currentUri;
		}
	}

	public static String getEncodedUri(String uri) {
		try {
			return URLEncoder.encode(uri, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return uri;
		}
	}

	public static Map<String, String> getParamMap(HttpServletRequest req) {
		Map<String, String> param = new HashMap<>(); // HashMap 생성

		Enumeration<String> parameterNames = req.getParameterNames(); // 열거 , 파라미터 이름 열거

		while (parameterNames.hasMoreElements()) { // hasMoreElements() : 토큰 개념, 다음 것이 없으면 그만 , 있으면 계속
			String paramName = parameterNames.nextElement();
			String paramValue = req.getParameter(paramName);

			param.put(paramName, paramValue);
		}
		return param;
	}

	public static String getAttr(Map map, String attrName, String defaultValue) {

		if (map.containsKey(attrName)) {
			return (String) map.get(attrName);
		}

		return defaultValue;
	}
	
	// sha256
	public static String sha256(String password) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			byte[] hash = messageDigest.digest(password.getBytes());
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
}

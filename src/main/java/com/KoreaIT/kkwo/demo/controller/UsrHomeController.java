//package com.KoreaIT.kkwo.demo.controller;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//@Controller
//public class UsrHomeController {
//
//	private int count;
//	private Article article;
//
//	public UsrHomeController() {
//		count = 0;
//	}
//
//	@RequestMapping("/usr/home/getCount")
//	@ResponseBody
//	public int getCount() {
//		return count++;
//	}
//
//	@RequestMapping("/usr/home/setCount")
//	@ResponseBody
//	public String setCount(int count) {
//		this.count = count;
//		return "count의 값이 " + this.count + "으로 초기화 됨";
//	}
//
//	@RequestMapping("/usr/home/getInt")
//	@ResponseBody
//	public int getInt() {
//		return 10;
//	}
//
//	@RequestMapping("/usr/home/getChar")
//	@ResponseBody
//	public char getChar() {
//		return '가';
//	}
//
//	@RequestMapping("/usr/home/getString")
//	@ResponseBody
//	public String getString() {
//		return "십일";
//	}
//
//	@RequestMapping("/usr/home/getFloat")
//	@ResponseBody
//	public float getFloat() {
//		return 123.456f;
//	}
//
//	@RequestMapping("/usr/home/getDouble")
//	@ResponseBody
//	public double getDouble() {
//		return 123.456; // 123.456
//	}
//
//	@RequestMapping("/usr/home/getBoolean")
//	@ResponseBody
//	public boolean getBoolean() {
//		return false; // false
//	}
//
//	@RequestMapping("/usr/home/getArray")
//	@ResponseBody
//	public String[] getArray() {
//		String[] arr = new String[3];
//		arr[0] = "일";
//		arr[1] = "이";
//		arr[2] = "삼";
//		return arr; // ["일","이","삼"]
//	}
//
//	@RequestMapping("/usr/home/getList")
//	@ResponseBody
//	public List<String> getList() {
//		List<String> strList = new ArrayList<>();
//		strList.add("일");
//		strList.add("이");
//		strList.add("삼");
//		return strList; // ["일","이","삼"]
//	}
//
//	@RequestMapping("/usr/home/getMap")
//	@ResponseBody
//	public Map<String, Object> getMap() {
//		Map<String, Object> map = new HashMap<>();
//		map.put("철수", 14);
//		map.put("영희", 16);
//		return map; // {"철수":14,"영희":16}
//	}
//
//	@RequestMapping("/usr/home/getArticle")
//	@ResponseBody
//	public Article getArticle() {
//		Article article = new Article();
//		return article;
//	}
//	
//	@RequestMapping("/usr/home/getArticles")
//	@ResponseBody
//	public List<Article> getArticles() {
//		List<Article> articles = new ArrayList<>();
//		articles.add(new Article());
//		return articles;
//	}
//
//}
// @Data
// @AllArgsConstructor
// @NoArgsConstructor // 매개변수가 없는 생성자
//class Article {
//	@Getter
//	private int id;
//	@Getter
//	private String regDate;
//	@Getter
//	private String title;
//	@Getter
//	private String body;
//	
//
//	public Article() {
//		id = 1;
//		regDate = "12-12-12 12:12:12";
//		title = "제목";
//		body = "내용";
//	}
//}
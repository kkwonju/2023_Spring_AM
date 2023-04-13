package com.KoreaIT.kkwo.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UsrHomeController {
	private int n;
	public UsrHomeController() {
		n = 0;
	}
	@RequestMapping("/usr/home/main")
	@ResponseBody
	public String showMain() {
		return "안녕";
	}
	@RequestMapping("/usr/home/main4")
	@ResponseBody
	public int increaseNum() {
		return n++;
	}

}

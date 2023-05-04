package com.KoreaIT.kkwo.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.KoreaIT.kkwo.demo.vo.Rq;

@Controller
public class UsrHomeController {
	private Rq rq;
	
	public UsrHomeController(Rq rq) {
		this.rq = rq;
	}
	
	@RequestMapping("/usr/home/main")
	public String showMain() {
		rq.run();
		return "usr/home/main";
	}
	
	@RequestMapping("/")
	public String showRoo() {
		return "redirect:/usr/home/main";
	}
}
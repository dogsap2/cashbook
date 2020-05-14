package com.gdu.ditestweb.cashbook1.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	@GetMapping({"/","/index"})
	public String index(){
		return "index";
	}

	@GetMapping("/home") // 로그인 되었을때만 올 수 있는(로그인 되었을때만 들어올 수 있는 것들은 매개변수로 세션을 받아야합니다.) 
	public String home(HttpSession session) {
		if(session.getAttribute("loginMember")==null) {
			return "redirect:/login"; //redirect가 있으면? response.sendRedirect 없으면? 뷰의 이름. 저절로 포워딩 됩니다.
		}
		return "home";
	}
}

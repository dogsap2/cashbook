package com.gdu.ditestweb.cashbook1.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.gdu.ditestweb.cashbook1.service.MemberService;
import com.gdu.ditestweb.cashbook1.vo.LoginMember;
import com.gdu.ditestweb.cashbook1.vo.Member;

@Controller
public class MemberController {
	@Autowired
	MemberService memberService;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@PostMapping("/login")
	public String login(LoginMember loginMember, HttpSession session) {// HttpSession session = request.getSession();
		System.out.println(loginMember+"<---loginMember");
		LoginMember returnLoginMember = memberService.login(loginMember);
		System.out.println("returnLoginMember:"+returnLoginMember);	
		if(returnLoginMember == null) { //로그인 실패시 (검색시 맞는 결과가 없으니까 실패)
			return "redirect:/login";
		}else{ //로그인 성공시 (db서치 결과가 있으면 회원이니 로그인 성공)
			session.setAttribute("loginMember", returnLoginMember);
			return "redirect:/";
		}
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) { //HttpSession session을 매개변수로 받으면 모델2랑 똑같이 사용가능.
		session.invalidate();
		return "redirect:/";
	}
	
	@GetMapping("/addMember")
	public String addMember() {
		return "addMember";
	}
	
	@PostMapping("/addMember")
	public String addMember(Member member) {//Commend 객체 , 도메인 객체
		System.out.println(member.toString());
		memberService.addMember(member);
		return "redirect:/index";
	}
	/*
	 @PostMapping("/addMember")
	 public String addMember(@RequestParam("memberId") String memberId, 
	 						@RequestParam("memberId") String memberPw,
	 						@RequestParam("memberId") String memberName,
	 						@RequestParam("memberId") String memberAddr,
	 						@RequestParam("memberId") String memberPhone,
	 						@RequestParam("memberId") String memberEmail){
	 	return "redirect:/";
	 }
	 * 
	 * 
	 */
}

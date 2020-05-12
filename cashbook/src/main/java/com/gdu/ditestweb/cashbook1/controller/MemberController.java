package com.gdu.ditestweb.cashbook1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.gdu.ditestweb.cashbook1.service.MemberService;
import com.gdu.ditestweb.cashbook1.vo.Member;

@Controller
public class MemberController {
	@Autowired
	MemberService memberService;
	
	@GetMapping("/addMember")
	public String addMember() {
		return "addMember";
	}
	
	@PostMapping("/addMember")
	public String addMember(Member member) {//Commend 객체 , 도메인 객체
		System.out.println(member.toString());
		memberService.AddMember(member);
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

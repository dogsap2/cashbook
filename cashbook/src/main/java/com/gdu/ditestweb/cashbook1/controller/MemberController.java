package com.gdu.ditestweb.cashbook1.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.ditestweb.cashbook1.service.MemberService;
import com.gdu.ditestweb.cashbook1.vo.LoginMember;
import com.gdu.ditestweb.cashbook1.vo.Member;

@Controller
public class MemberController {
	@Autowired
	MemberService memberService;
	
	
	
	@PostMapping("/checkMemberId")
	public String checkMemberId(HttpSession session, Model model,@RequestParam("memberIdCheck") String memberIdCheck){
		//로그인 중일때
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		
		String confirmMemberId = memberService.checkMemberId(memberIdCheck);
		//select member_id from member where member_id="내가 입력한 id' 입력한 아이디가 없으면 null값이 나옴
		if(confirmMemberId == null) {	
			//아이디를 사용할 수 있다.
			System.out.println("아이디를 사용할 수 있습니다");
			model.addAttribute("memberIdCheck", memberIdCheck); //모델에 id값 추가해서 보낼 것.
		}else{
			//아이디를 사용할 수 없다.
			System.out.println("아이디를 사용할 수 없다.");
			model.addAttribute("msg", "사용중인 아이디입니다.");
	
		}
		return "addMember";
	}
	
	
	@GetMapping("/login") //login form
	public String login(HttpSession session) {
		//로그인 중일때
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		//로그인 아닐때
		return "login";
	}
	
	@PostMapping("/login") //login action
	public String login(Model model,LoginMember loginMember, HttpSession session) {// HttpSession session = request.getSession();
		
		//로그인 중일때
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		System.out.println(loginMember+"<---loginMember");
		LoginMember returnLoginMember = memberService.login(loginMember);
		System.out.println("returnLoginMember:"+returnLoginMember);	
		
		if(returnLoginMember == null) { //로그인 실패시 (검색시 맞는 결과가 없으니까 실패)
			model.addAttribute("msg", "아이디랑 비밀번호를 확인하세요");
			return "login";
		}else{ //로그인 성공시 (db서치 결과가 있으면 회원이니 로그인 성공)
			session.setAttribute("loginMember", returnLoginMember);
			return "redirect:/";
		}
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) { //HttpSession session을 매개변수로 받으면 모델2랑 똑같이 사용가능.
		//로그인 아닐 때
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		
		session.invalidate();
		return "redirect:/";
	}
	
	@GetMapping("/addMember")
	public String addMember(HttpSession session) {
		//로그인 중일때
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		
		return "addMember";
	}
	
	@PostMapping("/addMember")
	public String addMember(Member member,HttpSession session) {//Commend 객체 , 도메인 객체
		//로그인 중일때
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		
		System.out.println(member.toString());
		memberService.addMember(member);
		return "redirect:/index";
	}
	/*
	 * @PostMapping("/addMember") public String addMember(@RequestParam("memberId")
	 * String memberId,
	 * 
	 * @RequestParam("memberId") String memberPw,
	 * 
	 * @RequestParam("memberId") String memberName,
	 * 
	 * @RequestParam("memberId") String memberAddr,
	 * 
	 * @RequestParam("memberId") String memberPhone,
	 * 
	 * @RequestParam("memberId") String memberEmail){ return "redirect:/"; }
	 * 
	 * 
	 */
}

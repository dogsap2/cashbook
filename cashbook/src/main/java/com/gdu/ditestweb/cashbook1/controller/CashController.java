package com.gdu.ditestweb.cashbook1.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gdu.ditestweb.cashbook1.service.CashService;
import com.gdu.ditestweb.cashbook1.vo.Cash;
import com.gdu.ditestweb.cashbook1.vo.LoginMember;

@Controller
public class CashController {
	@Autowired private CashService cashService;
	
	@GetMapping("getCashListByDate")
	public String getCashListByDate(HttpSession session, Model model) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		//로그인 아이디
		String loginMemberId = ((LoginMember)session.getAttribute("loginMember")).getMemberId(); //세션에서 getMemberId();만빼옴
		//오늘 날짜 받아오는 변수
		Date today = new Date();//오늘 날짜
		//Calendar today = Calendar.getInstance();//이 날짜 타입을 yyyy-mm-dd로 바꿔야함
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //오늘 날짜를 구해서 원하는 문자열 형태로 변경
		String strToday = sdf.format(today); // 2020-05-19 문자열
		System.out.println(strToday+"<----------strToday");
		
		Cash cash = new Cash(); // 아이디와 데이터가 채워져야함 
		cash.setMemberId(loginMemberId);
		cash.setCashDate(strToday);
		
		List<Cash> cashList = cashService.getCashListByDate(cash);
		model.addAttribute("cashList",cashList);
		model.addAttribute("today", today);	
		
		for(Cash c : cashList) {
			System.out.println(c);
		}
		return "getCashListByDate";
	}
}

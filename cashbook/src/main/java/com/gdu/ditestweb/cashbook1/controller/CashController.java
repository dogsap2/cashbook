package com.gdu.ditestweb.cashbook1.controller;


import java.time.LocalDate;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.ditestweb.cashbook1.service.CashService;
import com.gdu.ditestweb.cashbook1.vo.Cash;
import com.gdu.ditestweb.cashbook1.vo.LoginMember;

@Controller
public class CashController {
	@Autowired
	private CashService cashService;
	
	
	
	@GetMapping("removeCash")
	public String removeCash() {
		return null;
	}
	
	

	@GetMapping("getCashListByDate")
	public String getCashListByDate(HttpSession session, Model model,
			@RequestParam(value = "day", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate day) {
		if (day == null) {
			day = LocalDate.now();
		}
		if (session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		String loginMemberId = ((LoginMember) session.getAttribute("loginMember")).getMemberId(); // 세션에서
																									// getMemberId();만빼옴
		Cash cash = new Cash(); // 아이디와 데이터가 채워져야함
		cash.setMemberId(loginMemberId);
		cash.setCashDate(day.toString());
		
		Map<String, Object> map = cashService.getCashListByDate(cash);
		model.addAttribute("day", day);
		model.addAttribute("cashKindSum",map.get("cashKindSum"));
		model.addAttribute("cashList",map.get("cashList"));
		
		return "getCashListByDate";
	}
}

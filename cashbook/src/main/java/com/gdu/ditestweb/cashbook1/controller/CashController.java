package com.gdu.ditestweb.cashbook1.controller;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
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
import com.gdu.ditestweb.cashbook1.vo.DayAndPrice;
import com.gdu.ditestweb.cashbook1.vo.LoginMember;

@Controller
public class CashController {
	@Autowired
	private CashService cashService;

	@GetMapping("removeCash")
	public String removeCash() {
		return null;
	}

	// 달별로
	@GetMapping("getCashListByMonth")
	public String getCashListByMonth(HttpSession session, Model model, // 문자열로 넘어오면 LocalDate형으로 바꿈
			@RequestParam(value = "day", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate day) {
		// 로그인 하면 리다이렉트..
		// if(session.getAttribute("loginMember")==null) {
		// return "redirect:/";
		// }

		// 오늘날짜 cDay~!
		Calendar cDay = Calendar.getInstance();// 오늘날짜가 들어가게됨
		// System.out.println(cDay.get(Calendar.MONTH)+1+"<-----달출력"); //달 출력해보기 5
		if (day == null) {
			day = LocalDate.now();
		} else {
			// day를 cDay로형 변환 \
			cDay.set(day.getYear(), day.getMonthValue() - 1, day.getDayOfMonth()); // 오늘날짜에서 day값과 동일한 값으로...
			/*
			 * LocalDate -> calendar LocalDate -> Date -> calendar LocalDate -> String ->
			 * calendar
			 */
		}

		/*
		 * 1.오늘이 무슨달 2.이번달의 마지막 일 3.이번달 1일의 요일
		 */
		
		//
		String memberId = ((LoginMember)session.getAttribute("loginMember")).getMemberId();
		int year = cDay.get(Calendar.YEAR);
		int month = cDay.get(Calendar.MONTH);
		List<DayAndPrice> dayAndPriceList = cashService.getCashAndPriceList(memberId, year, month);
		
		model.addAttribute("dayAndPriceList", dayAndPriceList);
		model.addAttribute("day", day);
		model.addAttribute("month", cDay.get(Calendar.MONTH) + 1); // 월
		model.addAttribute("lastDay", cDay.getActualMaximum(Calendar.DATE)); // 마지막일, 오늘날짜의 제일 큰값

		Calendar firstDay = cDay;
		firstDay.set(Calendar.DATE, 1); // cDay에서 일만 1일로 변경
		System.out.println(firstDay.get(Calendar.YEAR)+","+(firstDay.get(Calendar.MONTH)+1)+","+firstDay.get(Calendar.DATE));
		// 요일구하기
		// firstDay.get(Calendar.DAY_OF_WEEK); //값이 0이면 일요일, 1 월요일,..6이면 토요일 ,리턴값이 숫자기
		// 때문에 저렇게 알아야돼 특정날짜의 요일을 구하는 함수!
		System.out.println(firstDay.get(Calendar.YEAR) + "," + (firstDay.get(Calendar.MONTH) + 1) + ","
				+ firstDay.get(Calendar.DATE));
		System.out.println("firstDay.get(Calendar.DAY_OF_WEEK):" + firstDay.get(Calendar.DAY_OF_WEEK));// 1 일요일, 2월요일,
																										// ....7 토요일
		model.addAttribute("firstDayOfWeek", firstDay.get(Calendar.DAY_OF_WEEK));

		return "getCashListByMonth";
	}
	
	//월별
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
		model.addAttribute("cashKindSum", map.get("cashKindSum"));
		model.addAttribute("cashList", map.get("cashList"));

		return "getCashListByDate";
	}
}

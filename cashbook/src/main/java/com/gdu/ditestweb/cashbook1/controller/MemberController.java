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
import com.gdu.ditestweb.cashbook1.vo.MemberForm;

@Controller
public class MemberController {
	@Autowired
	MemberService memberService;

	// findMemberPw
	@GetMapping("/findMemberPw")
	public String findMemberPw(HttpSession session) {
		if (session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		return "findMemberPw";
	}

	@PostMapping("/findMemberPw")
	public String findMemberPw(HttpSession session, Model model, Member member) {
		int row = memberService.getMemberPw(member);
		String msg = "아이디와 메일을 확인하세요.";
		if (row == 1) {
			msg = "비밀번호를 입력한 메일로 전송하겠습니다.";
		}
		model.addAttribute("msg", msg);
		return "memberPwView";
	}

	// findMemberId
	@GetMapping("/findMemberId")
	public String findMemberId(HttpSession session) {
		if (session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		return "findMemberId";
	}

	@PostMapping("/findMemberId")
	public String findMemberId(HttpSession session, Model model, Member member) {
		if (session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		String memberIdPart = memberService.getMemberIdByMember(member);
		System.out.println(memberIdPart + "<--memberIdPart");
		model.addAttribute("memberIdPart", memberIdPart);
		return "memberIdView";
	}

	// 회원 수정
	@GetMapping("/modifyMember")
	public String modifyMember(HttpSession session, Model model) {
		// 로그인이 아닐때!
		if (session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		Member member = memberService.getMemberOne((LoginMember) (session.getAttribute("loginMember")));
		System.out.println(member + "<----member");
		model.addAttribute("member", member);
		return "modifyMember";
	}

	@PostMapping("/modifyMember")
	public String modifyMember(HttpSession session, MemberForm memberForm) {
		// 로그인이 아닐때!
		if (session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		memberService.modifyMember(memberForm);
		System.out.println(memberForm + "<====update member");
		return "redirect:/";
	}

	// 회원 탈퇴
	@GetMapping("/removeMember")
	public String memberDelete(HttpSession session) {
		// 로그인이 아닐때!
		if (session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		return "removeMember";// input type = "password" 입력하도록.
	}

	@PostMapping("/removeMember")
	public String memberDelete(HttpSession session, @RequestParam("memberPw") String memberPw) {
		// 로그인이 아닐때!
		if (session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		// 로컬변수(임시변수)선언 이메소드를 실행하기 위해서만 사용하기위한 변수
		LoginMember loginMember = (LoginMember) (session.getAttribute("loginMember"));
		loginMember.setMemberPw(memberPw);
		System.out.println(loginMember + "<=======remove loginMember");
		int row = memberService.removeMember(loginMember);
		if (row == 1) {
			session.invalidate();
			return "redirect:/";
		}
		return "removeMember";
	}

	@GetMapping("/memberInfo")
	public String memberInfo(HttpSession session, Model model) {
		// 로그인이 아닐때!
		if (session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		// session엔 오브젝트 타입이 들어가있어서 뭐든 다담을 수 있어요. 참조형은 다 가능. 사실 기본형은 불가인데 *오토박싱으로 가능
		Member member = memberService.getMemberOne((LoginMember) (session.getAttribute("loginMember")));// 오브젝트 타입을
																										// loginMember로
																										// 형변환.
		System.out.println(member);
		model.addAttribute("member", member);
		return "memberInfo";
	}

	@PostMapping("/checkMemberId")
	public String checkMemberId(HttpSession session, Model model, @RequestParam("memberIdCheck") String memberIdCheck) {
		// 로그인 중일때
		if (session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}

		String confirmMemberId = memberService.checkMemberId(memberIdCheck);
		// select member_id from member where member_id="내가 입력한 id' 입력한 아이디가 없으면 null값이
		// 나옴
		if (confirmMemberId == null) {
			// 아이디를 사용할 수 있다.
			System.out.println("아이디를 사용할 수 있습니다");
			model.addAttribute("memberIdCheck", memberIdCheck); // 모델에 id값 추가해서 보낼 것.
		} else {
			// 아이디를 사용할 수 없다.
			System.out.println("아이디를 사용할 수 없다.");
			model.addAttribute("msg", "아이디를 사용할 수 없습니다.");

		}
		return "addMember";
	}

	@GetMapping("/login") // login form
	public String login(HttpSession session) {
		// 로그인 중일때
		if (session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		// 로그인 아닐때
		return "login";
	}

	@PostMapping("/login") // login action
	public String login(Model model, LoginMember loginMember, HttpSession session) {// HttpSession session =
																					// request.getSession();

		// 로그인 중일때
		if (session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		System.out.println(loginMember + "<---loginMember");
		LoginMember returnLoginMember = memberService.login(loginMember);
		System.out.println("returnLoginMember:" + returnLoginMember);

		if (returnLoginMember == null) { // 로그인 실패시 (검색시 맞는 결과가 없으니까 실패)
			model.addAttribute("msg", "아이디랑 비밀번호를 확인하세요");
			return "login";
		} else { // 로그인 성공시 (db서치 결과가 있으면 회원이니 로그인 성공)
			session.setAttribute("loginMember", returnLoginMember);
			return "redirect:/home";
		}
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) { // HttpSession session을 매개변수로 받으면 모델2랑 똑같이 사용가능.
		// 로그인 아닐 때
		if (session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}

		session.invalidate();
		return "redirect:/";
	}

	@GetMapping("/addMember")
	public String addMember(HttpSession session) {
		// 로그인 중일때
		if (session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		return "addMember";
	}

	@PostMapping("/addMember")
	public String addMember(MemberForm memberForm, HttpSession session) {
		//// Command 객체, 도메인 객체 , 전부다 받아서 Member 타입으로 바꿔줌, (뷰)폼의 name이 vo안 이름과 같아야됨
		// 로그인 중일때
		if (session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		System.out.println(memberForm + "<-memberForm");

		// 파일은 png,jpg,gif만 업로드 가능
		if (memberForm.getMemberPic() != null) {
			if (!memberForm.getMemberPic().getContentType().equals("image/png")
					&& !memberForm.getMemberPic().getContentType().equals("image/jpeg")
					&& !memberForm.getMemberPic().getContentType().equals("image/gif")) {
				return "redirect:/addMember";
			}
			memberService.addMember(memberForm);
			// 서비스 : 멤버폼->멤버+폴더 에 파일도 저장
		}
		return "redirect:/index";
	}
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

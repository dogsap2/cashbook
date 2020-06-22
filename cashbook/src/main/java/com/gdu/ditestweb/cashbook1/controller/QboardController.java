package com.gdu.ditestweb.cashbook1.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.ditestweb.cashbook1.service.QboardService;
import com.gdu.ditestweb.cashbook1.vo.LoginMember;
import com.gdu.ditestweb.cashbook1.vo.Qboard;
import com.gdu.ditestweb.cashbook1.vo.Qcomment;

@Controller
public class QboardController {
	@Autowired
	public QboardService qboardService;

	
	//덧글 수정
	@PostMapping("/modifyQcomment")
	public String modifyQcomment(HttpSession session,Qcomment qcomment){
		if(session.getAttribute("loginMember")==null) {
			return "redirect:/";
		}
		System.out.println(qcomment+"<---qcomment");
		qboardService.updateQcommentOne(qcomment);
		return "redirect:/detailBoardList?boardNo="+qcomment.getBoardNo();
	}
		
	
	//덧글 하나만 가져오기(수정용)
	@GetMapping("/modifyQcomment")
	public String modifyQcomment(HttpSession session,@RequestParam("commentNo") int commentNo,Model model) {
		if(session.getAttribute("loginMember")==null) {
			return "redirect:/";
		}
		
		Qcomment qcomment = qboardService.selectQcommentOne(commentNo);
		model.addAttribute("qcomment",qcomment);
		return "modifyQcommentList";
		
	}

	
	//포스트 수정하기 링크
	@GetMapping("/modifyQboardList")
	public String modifyQboard(HttpSession session,Model model,@RequestParam("boardNo") int boardNo) {
		if(session.getAttribute("loginMember")==null) {
			return "redirect:/";
		}
		
		HashMap<String, Object> map = qboardService.selectQboardOne(boardNo);
		model.addAttribute("qboard", map.get("qboard"));
		return "modifyQboardList";
	}
	
	//포스트 수정하기 포스트
	@PostMapping("/modifyQboardList")
	public String modifyQboard(Qboard qboard) {
		
		//업데이트 서비스 실행
		qboardService.updateQboardone(qboard);
		return "redirect:/detailBoardList?boardNo="+qboard.getBoardNo();
	}
	
	//포스트 삭제하기
	@GetMapping("/deleteQboardList")
	public String deleteQboard(@RequestParam("boardNo")int boardNo) {
		qboardService.deleteQboard(boardNo);
		return "redirect:/qboardList";
	}
	
	//덧글 삭제하기
	@GetMapping("/deleteComment")
	public String deleteComment(@RequestParam("boardNo")int boardNo,@RequestParam("commentNo")int commentNo) {
		qboardService.deleteQcomment(commentNo);
		return "redirect:/detailBoardList?boardNo="+boardNo;
	}
	
	
	// 덧글 추가하기
	@PostMapping("/insertComment")
	public String insertComment(HttpSession session,Qcomment qcomment,@RequestParam("boardNo")int boardNo) {
		if(session.getAttribute("loginMember")==null) {
			return "redirect:/";
		}
		String memberId = ((LoginMember) session.getAttribute("loginMember")).getMemberId();
		qcomment.setBoardNo(boardNo);
		qcomment.setMemberId(memberId);
		System.out.println(qcomment+"<----qcomment");
		//서비스 호출
		qboardService.insertQcomment(qcomment);
		
		return "redirect:/detailBoardList?boardNo="+boardNo;
	}

	// 게시글 추가하기 워프
	@GetMapping("/insertQboard")
	public String insertQboard(HttpSession session, Model model) {
		if (session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		return "insertQboard";
	}

	// 게시글 추가 포스트 입력
	@PostMapping("/insertQboard")
	public String insertQboard(HttpSession session, Qboard qboard) {
		if (session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		String memberId = ((LoginMember) session.getAttribute("loginMember")).getMemberId();
		qboard.setMemberId(memberId);

		System.out.println(qboard + "<-----qboard");

		// 인설트호출해
		qboardService.insertQboard(qboard);
		return "redirect:/qboardList";
	}

	// 큐리스트 전체 보기
	@GetMapping("/qboardList")
	//currentPage랑 searchWord requestParam 으로 가져오기
	public String selectQboardList(HttpSession session, Model model,@RequestParam(value="currentPage",defaultValue="1")int currentPage,@RequestParam(value="seachWord",defaultValue ="")String searchWord) {
		if (session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		//디버깅
		System.out.println(currentPage+"<----Qboard currentPage");
		// 리스트 불러오기
		List<Qboard> list = qboardService.selectQboardList();
		System.out.println(list + "<---Qboardlist");
		model.addAttribute("list", list);
		model.addAttribute("currentPage",currentPage);

		return "qboardList";
	}

	// 큐 리스트 하나 상세보기,덧글 리스트 뽑아오기일단 ㄱㄱ
	@GetMapping("/detailBoardList")
	public String selectQboardListOne(HttpSession session, Model model, @RequestParam("boardNo") int boardNo) {
		if (session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}

		// boardNo에 해당하는 리스트 불러오고
		HashMap<String, Object> map = qboardService.selectQboardOne(boardNo);
		model.addAttribute("qboard", map.get("qboard"));
		model.addAttribute("commentList", map.get("commentList"));
		// 덧글 리스트도 불러오고
		return "detailBoardList";
	}

}

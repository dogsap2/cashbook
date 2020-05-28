package com.gdu.ditestweb.cashbook1.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.ditestweb.cashbook1.mapper.QboardMapper;
import com.gdu.ditestweb.cashbook1.vo.Qboard;
import com.gdu.ditestweb.cashbook1.vo.Qcomment;

@Service
@Transactional
public class QboardService {
	@Autowired
	public QboardMapper qboardMapper;
	
	//포스트 수정
	public int  updateQboardone(Qboard qboard) {
		return qboardMapper.updateQboardone(qboard);
	}
	
	//포스트 삭제
	public void deleteQboard(int boardNo) {
		qboardMapper.deleteQcomments2(boardNo);
		qboardMapper.deleteQboard(boardNo);
		return;
	}
	
	//덧글 삭제
	public int deleteQcomment(int commentNo) {
		return qboardMapper.deleteQcomments(commentNo);
	}
	
	//덧글 추가
	public int insertQcomment(Qcomment qcomment) {
		return qboardMapper.insertQcomments(qcomment);
	}
	
	//게시글 추가
	public int insertQboard(Qboard qboard) {
		System.out.println(qboard+"<-----qboard");
		return qboardMapper.insertQboard(qboard);
	}
	
	//게시판 상세보기(리스트 하나만 뽑아오기),덧글 리스트 불러오기
	public HashMap<String,Object> selectQboardOne(int boardNo) {
		Qboard qboard = qboardMapper.selectQboardOne(boardNo);
		List<Qcomment> commentList = qboardMapper.selectQcommentList(boardNo);
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("qboard", qboard);
		map.put("commentList", commentList);
		return map;
	}
	
	//게시판 리스트 뽑아오기
	public List<Qboard> selectQboardList(){
		return qboardMapper.selectQboardList();
		
	}
}

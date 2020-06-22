package com.gdu.ditestweb.cashbook1.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.ditestweb.cashbook1.vo.Qboard;
import com.gdu.ditestweb.cashbook1.vo.Qcomment;

@Mapper
public interface QboardMapper {

	//덧글 하나만 뽑아오기
	public Qcomment selectQcommentOne(int commentNo);
	//덧글 수정
	public int updateQcommentOne(Qcomment qcomment);
	//포스트 수정
	public int updateQboardone(Qboard qboard);
	//포스트 삭제
	public void deleteQboard(int boardNo);
	
	//덧글 전체 삭제
	public void deleteQcomments2(int boardNo);
	//덧글 삭제
	public int deleteQcomments(int commentNo);
	//덧글 추가
	public int insertQcomments(Qcomment qcomment);
	//게시글 추가
	public int insertQboard(Qboard qboard);
	
	//해당 게시글의 덧글 리스트 가져오기
	public List<Qcomment> selectQcommentList(int boardNo);
	
	//리스트 하나만 상세보기
	public Qboard selectQboardOne(int boardNo);
	
	//리스트 전체 출력
	public List<Qboard> selectQboardList();
}

package com.gdu.ditestweb.cashbook1.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.ditestweb.cashbook1.vo.LoginMember;
import com.gdu.ditestweb.cashbook1.vo.Member;

@Mapper //@Mapper는 Component를 상속받았대요 
public interface MemberMapper {
	
	//d아이디를 입력하면 pic을 뱉는
	public String selectMemberPic(String memberId);
	//비밀번호 찾기
	public int updateMemberPw(Member member);
	//아이디 찾기
	public String selectMemberIdByMember(Member member);
	//회원 삭제
	public int deleteMemberOne(LoginMember loginMember);
	//회원 수정
	public int updateMember(Member member);
	//회원 상세 정보
	public Member selectMemberOne(LoginMember loginMember);
	//아이디 중복 유효성 체크
	public String selectMemberId(String memberIdCheck);
	//회원 추가
	public int insertMember(Member member);
	//로그인 계정 유효성 체크
	public LoginMember selectLoginMember(LoginMember loginMember);
}

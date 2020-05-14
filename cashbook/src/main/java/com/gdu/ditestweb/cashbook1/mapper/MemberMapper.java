package com.gdu.ditestweb.cashbook1.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.ditestweb.cashbook1.vo.LoginMember;
import com.gdu.ditestweb.cashbook1.vo.Member;

@Mapper //@Mapper는 Component를 상속받았대요 
public interface MemberMapper {
	public Member selectMemberOne(LoginMember loginMember);
	public String selectMemberId(String memberIdCheck);
	public int insertMember(Member member);
	public LoginMember selectLoginMember(LoginMember loginMember);
}

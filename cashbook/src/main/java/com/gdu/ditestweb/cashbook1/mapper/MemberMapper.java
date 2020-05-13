package com.gdu.ditestweb.cashbook1.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.ditestweb.cashbook1.vo.LoginMember;
import com.gdu.ditestweb.cashbook1.vo.Member;

@Mapper
public interface MemberMapper {
	
	public String selectMemberId(String memberIdCheck);
	public int insertMember(Member member);
	public LoginMember selectLoginMember(LoginMember loginMember);
}

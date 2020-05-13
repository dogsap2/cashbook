package com.gdu.ditestweb.cashbook1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.ditestweb.cashbook1.mapper.MemberMapper;
import com.gdu.ditestweb.cashbook1.vo.LoginMember;
import com.gdu.ditestweb.cashbook1.vo.Member;

@Service
@Transactional //클래스에 붙어있을시 클래스안의 메소드를 실행시키다 하나라도 에러가나면 실행취소. (메소드안에 붙어있으면 이 메소드 한정으로 트랜잭션 에러시 롤백)
public class MemberService {
	@Autowired
	private MemberMapper memberMapper;
	
	public LoginMember login(LoginMember loginMember) {
		return memberMapper.selectLoginMember(loginMember);
	}
	public int addMember(Member member) {
		return memberMapper.insertMember(member);	
	}
}

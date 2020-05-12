package com.gdu.ditestweb.cashbook1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.ditestweb.cashbook1.mapper.MemberMapper;
import com.gdu.ditestweb.cashbook1.vo.Member;

@Service
public class MemberService {
	@Autowired
	private MemberMapper memberMapper;
	
	public int AddMember(Member member) {
		return memberMapper.insertMember(member);	
	}
}

package com.gdu.ditestweb.cashbook1.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.ditestweb.cashbook1.vo.Member;

@Mapper
public interface MemberMapper {
	public int insertMember(Member member);
}

package com.gdu.ditestweb.cashbook1.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.ditestweb.cashbook1.vo.Memberid;

@Mapper
public interface MemberidMapper {
	void insertMemberid(Memberid memberid);
}

package com.gdu.ditestweb.cashbook1.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.ditestweb.cashbook1.vo.Cash;

@Mapper
public interface CashMapper {
	// 로그인 사용자의 오늘날짜 cash 목록
	public List<Cash> selectCashListByDate(Cash cash);
}

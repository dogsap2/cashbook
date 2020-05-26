package com.gdu.ditestweb.cashbook1.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.ditestweb.cashbook1.vo.Cash;
import com.gdu.ditestweb.cashbook1.vo.Category;
import com.gdu.ditestweb.cashbook1.vo.DayAndPrice;

@Mapper
public interface CashMapper {
	
	//카테고리 네임 불러오기
	public List<Category> selectCategoryList();
	//하나는 년도고 하나는 달
	public List<DayAndPrice> selectDayAndPriceList(Map<String, Object> map);
	// 로그인 사용자의 오늘날짜 cash 목록
	public List<Cash> selectCashListByDate(Cash cash);
	// 금액 합계
	public int selectCashKindSum(Cash cash);
}

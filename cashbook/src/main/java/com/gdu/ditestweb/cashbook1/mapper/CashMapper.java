package com.gdu.ditestweb.cashbook1.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.ditestweb.cashbook1.vo.Cash;
import com.gdu.ditestweb.cashbook1.vo.Category;
import com.gdu.ditestweb.cashbook1.vo.DayAndPrice;

@Mapper
public interface CashMapper {
	
	//캐쉬 수정
	public int updateCash(Cash cash);
	//캐쉬넘버에 해당하는 캐시리스트 뽑아오기
	public Cash selectCashListByOne(int cashNo);
	//캐쉬 삭제하기
	public int deleteCash(Cash cashNo);
	//캐쉬 추가하기
	public int insertCategoryList(Map<String, Object> map);
	//카테고리 네임 불러오기
	public List<Category> selectCategoryList();
	//하나는 년도고 하나는 달
	public List<DayAndPrice> selectDayAndPriceList(Map<String, Object> map);
	// 로그인 사용자의 오늘날짜 cash 목록
	public List<Cash> selectCashListByDate(Cash cash);
	// 금액 합계
	public int selectCashKindSum(Cash cash);
}

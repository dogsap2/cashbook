package com.gdu.ditestweb.cashbook1.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.ditestweb.cashbook1.mapper.CashMapper;
import com.gdu.ditestweb.cashbook1.vo.Cash;
import com.gdu.ditestweb.cashbook1.vo.Category;
import com.gdu.ditestweb.cashbook1.vo.DayAndPrice;

@Service
@Transactional
public class CashService {
	@Autowired private CashMapper cashMapper;
	
	//캐쉬 넘버에 해당하는 리스트 뽑아오기
	public List<Cash> selectCashListOne(Cash cashNo){
		List<Cash> list = cashMapper.selectCashListByOne(cashNo);
		System.out.println(list+"<------list");
		return list;
	}
	
	
	//캐쉬(가계부) 추가하기
	public int insertCash(Map<String, Object> map) {
		int row = cashMapper.insertCategoryList(map);
		return row;
	}
	
	//캐쉬(가계부) 삭제하기
	public int removeCash(Cash cashNo) {
		return cashMapper.deleteCash(cashNo);
	}
	
	
	//카테고리 목록만 뽑아내기
	public List<Category> selectCategoryList(){
		List<Category> list = new ArrayList<Category>();
		list= cashMapper.selectCategoryList();
		System.out.println(list+"<-----list");
		return list;
	}
	
	public List<DayAndPrice> getCashAndPriceList(String memberId, int year,int month){
		Map<String, Object> map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("year",year);
		map.put("month",month);
		return cashMapper.selectDayAndPriceList(map);
	}
	
	public Map<String,Object> getCashListByDate(Cash cash){
		List<Cash>cashList = cashMapper.selectCashListByDate(cash);
		int cashKindSum = cashMapper.selectCashKindSum(cash);
		System.out.println(cashKindSum+"<----cashKindSum");
		
		Map<String,Object> map = new HashMap<>();
		map.put("cashKindSum",cashKindSum);
		map.put("cashList",cashList);
		return map;
	}
}

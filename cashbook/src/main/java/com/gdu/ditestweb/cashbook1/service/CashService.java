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

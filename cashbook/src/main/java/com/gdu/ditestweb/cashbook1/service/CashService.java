package com.gdu.ditestweb.cashbook1.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.ditestweb.cashbook1.mapper.CashMapper;
import com.gdu.ditestweb.cashbook1.vo.Cash;

@Service
@Transactional
public class CashService {
	@Autowired private CashMapper cashMapper;
	
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

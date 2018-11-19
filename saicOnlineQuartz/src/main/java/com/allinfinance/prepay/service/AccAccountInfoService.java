package com.allinfinance.prepay.service;

import java.util.List;

import com.allinfinance.prepay.model.AccAccountInfo;
import com.allinfinance.prepay.model.AccAccountInfoExample;

public interface AccAccountInfoService {
	
	public List<AccAccountInfo> selectByExample(AccAccountInfoExample example);
	
	public AccAccountInfo selectByCardNo(String cardNO);
	

}

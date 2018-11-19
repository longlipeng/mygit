package com.allinfinance.prepay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allinfinance.prepay.mapper.svc_mng.AccAccountInfoMapper;
import com.allinfinance.prepay.mapper.svc_mng.AccRelCardAccountMapper;
import com.allinfinance.prepay.model.AccAccountInfo;
import com.allinfinance.prepay.model.AccAccountInfoExample;
import com.allinfinance.prepay.model.AccRelCardAccount;
import com.allinfinance.prepay.model.AccRelCardAccountExample;

@Service
public class AccAccountInfoServiceImpl implements AccAccountInfoService{
	@Autowired
	private AccAccountInfoMapper accAccountInfoMapper;
	@Autowired
	private AccRelCardAccountMapper accRelCardAccountMapper;
	
	@Override
	public List<AccAccountInfo> selectByExample(AccAccountInfoExample example) {
		return accAccountInfoMapper.selectByExample(example);
	}

	@Override
	public AccAccountInfo selectByCardNo(String cardNO) {
		AccRelCardAccountExample accRelCardAccountExample = new AccRelCardAccountExample();
		accRelCardAccountExample.createCriteria().andCardNoEqualTo(cardNO);
		List<AccRelCardAccount> accRelCardAccounts= accRelCardAccountMapper.selectByExample(accRelCardAccountExample);
		
		AccAccountInfoExample accAccountInfoExample = new AccAccountInfoExample();
		accAccountInfoExample.createCriteria().andAccountNoEqualTo(accRelCardAccounts.get(0).getAccountNo());
		List<AccAccountInfo> accAccountInfos = accAccountInfoMapper.selectByExample(accAccountInfoExample);
		
		return accAccountInfos.get(0);
		
		
	}
	
	
	
	
}

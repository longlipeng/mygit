package com.allinfinance.prepay.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allinfinance.prepay.mapper.svc_mng.SellerMapper;
import com.allinfinance.prepay.model.Seller;
import com.allinfinance.prepay.model.SellerExample;
@Service
public class CheckDataMethod {
	@Autowired
	private SellerMapper sellerMapper;
	
	public boolean checkIssueId(String id){
		SellerExample ex=new SellerExample();
		ex.createCriteria().andEntityIdEqualTo(id).andDataStateEqualTo("1").andSellerStateEqualTo("1");
		List<Seller> list=sellerMapper.selectByExample(ex);
		
		if(list.size()>0){
			return true;
		}else{
			return false;
		}
		
	}
}

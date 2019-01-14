package com.allinfinance.prepay.mapper.svc_mng;

import java.util.Map;


public interface CommonsMapper {
	
	long getNextValueOfSequence(Map sequenceName);
//	long getNextValueOfSequence(String sequenceName);
	
}

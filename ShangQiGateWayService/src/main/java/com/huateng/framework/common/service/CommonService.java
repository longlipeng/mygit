package com.huateng.framework.common.service;

import com.huateng.framework.exception.BizServiceException;

public interface CommonService {
	
	String getNextValueOfSequence(String seqName) throws BizServiceException;

}

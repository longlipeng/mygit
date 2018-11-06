package com.allinfinance.prepay.batch.service;

import com.allinfinance.prepay.model.SaicOnlineBatchInfo;

public interface BacthIssueCardService {
	
	public void issueCard(SaicOnlineBatchInfo info) throws Exception;
	
}

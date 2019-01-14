package com.huateng.univer.businessbatch;


import java.util.List;

import com.allinfinance.service.BatchParamInterface;
import com.allinfinance.univer.businessbatch.dto.BatchFileDTO;
import com.allinfinance.univer.businessbatch.dto.BatchFileDetailDTO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.listener.batch.BatchListener;


public interface BusinessBatchService extends BatchListener{
//	BatchFileDetailDTO businessBatch(Object batch, BatchFileDTO batchFileDTO) throws BizServiceException;
	
	void insertBatch(List<? extends BatchParamInterface> businessList) throws BizServiceException;
	
	void commMessage(BatchFileDTO batchFileDTO) throws BizServiceException;
	
	/**
	 * 如果需要进行约束，则返回true；
	 * 如果不需要进行约束，则返回false;
	 * 默认返回true;
	 * @return
	 */
	boolean isContrains();
}

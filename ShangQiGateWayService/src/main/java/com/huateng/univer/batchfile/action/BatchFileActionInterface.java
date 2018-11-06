package com.huateng.univer.batchfile.action;

import java.util.List;

import com.allinfinance.service.BatchParamInterface;
import com.allinfinance.univer.businessbatch.dto.BatchFileDTO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.univer.businessbatch.BusinessBatchService;

public interface BatchFileActionInterface {
	String fileBatch(String serviceName, String orderId,
			List<? extends BatchParamInterface> batchList) throws BizServiceException;
	/**
	 * 
	 * 功能描述: <br>
	 * JOB任务调用的批量文件处理任务
	 *
	 * @param serviceName
	 * @param orderId
	 * @param batchList
	 * @return
	 */
    String fileBatchTask(String serviceName, String orderId,
            List<? extends BatchParamInterface> batchList);	
}

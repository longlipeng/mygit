package com.huateng.univer.batchfile;

import java.util.List;

import com.allinfinance.service.BatchParamInterface;
import com.allinfinance.univer.businessbatch.dto.BatchFileDTO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.univer.businessbatch.BusinessBatchService;

/**
 * 用来配置aop的事务接口
 * 
 * @author flypal
 * 
 */
public interface BatchFileTransactionServiceInterface {

	/**
	 * 批量插入数据库
	 * @param orderId
	 * @param batchList
	 * @param business
	 * @return
	 * @throws BizServiceException
	 */
	BatchFileDTO insertBatchInfo(String orderId,
			List<? extends BatchParamInterface> batchList,
			BusinessBatchService business) throws BizServiceException;

}

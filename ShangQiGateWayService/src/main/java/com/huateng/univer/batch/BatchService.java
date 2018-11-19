package com.huateng.univer.batch;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.businessbatch.dto.BatchFileDetailDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderQueryDTO;
import com.huateng.framework.exception.BizServiceException;

public interface BatchService {
	public String getOrderState(SellOrderDTO sellOrderDTO) throws BizServiceException;
	public PageDataDTO rechargeActBatchDetailGet(BatchFileDetailDTO batchFileDetailDTO) throws BizServiceException;
	public PageDataDTO rechargeBatchDetailGet(BatchFileDetailDTO batchFileDetailDTO) throws BizServiceException;
	public PageDataDTO makeCardBatchDetailGet(BatchFileDetailDTO batchFileDetailDTO) throws BizServiceException;
	public PageDataDTO ransomBatchDetailGet(
			BatchFileDetailDTO batchFileDetailDTO) throws BizServiceException;
	public int changeCardNo(SellOrderQueryDTO sellOrderQueryDTO) throws BizServiceException;
}

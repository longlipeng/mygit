package com.huateng.univer.batch;

import com.allinfinance.framework.dto.BatchActivateDTO;
import com.allinfinance.framework.dto.BatchRechargeDTO;
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
	public PageDataDTO getBatchRechargeInfo(BatchRechargeDTO batchRechargeDTO)throws BizServiceException;
	public void toBatchRechargeInfo(BatchRechargeDTO batchRechargeDTO)throws BizServiceException;
	
	//查询批量激活列表
	public PageDataDTO getBatchActivateList(BatchActivateDTO batchActivateDTO)throws BizServiceException;
	//进行批量激活
	public void toBatchActivate(BatchActivateDTO batchActivateDTO)throws BizServiceException;
	//查询具体批次下所有卡
	public PageDataDTO getBatchActivateDetail(BatchActivateDTO batchActivateDTO)throws BizServiceException;
	//查询激活批次下的卡
	public PageDataDTO getBatchActivateCardDetail(BatchActivateDTO batchActivateDTO)throws BizServiceException;
}

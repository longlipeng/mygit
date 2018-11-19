package com.webservice.service;

import com.allinfinance.shangqi.gateway.dto.AccountConsumDTO;
import com.allinfinance.shangqi.gateway.dto.AccountQueryDTO;
import com.allinfinance.shangqi.gateway.dto.RecordConsumeDTO;
import com.huateng.framework.exception.BizServiceException;

public interface SendToPospService {
	
	//余额查询
	public AccountQueryDTO accountBalanceQuery(AccountConsumDTO stockCountDTO)throws Exception;
	
	//账户转充值
	public void accountConsum(AccountConsumDTO stockCountDTO)throws Exception ;
	
	//消费记录查询
	public RecordConsumeDTO queryConsume(RecordConsumeDTO recordConsumeDTO)throws Exception ;
	//食堂充值
//	public AccountConsumDTO canteenRecharge(AccountConsumDTO stockCountDTO) throws Exception;
	
	//商城消费
	public AccountConsumDTO consumeMall(AccountConsumDTO stockCountDTO)
			throws Exception;
	//商城退货申请
	public AccountConsumDTO consumeMallCancel(AccountConsumDTO stockCountDTO)
			throws Exception;
	//商城退货申请状态查询
	public AccountConsumDTO queryConsumeMallCancelState(AccountConsumDTO stockCountDTO)
			throws Exception ;
}

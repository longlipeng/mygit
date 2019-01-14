package com.huateng.univer.consumer.consumercontractbase.biz.service;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.consumercontract.dto.ConsumerContractDTO;
import com.allinfinance.univer.consumercontract.dto.ConsumerContractQueryDTO;
import com.huateng.framework.exception.BizServiceException;

public interface ConsumerContractBaseService {
	public ConsumerContractDTO insertContract(ConsumerContractDTO dto)
			throws BizServiceException;

	public PageDataDTO selectContract(ConsumerContractQueryDTO dto)
			throws BizServiceException;

	public ConsumerContractDTO viewMchntConsumerContractDTO(
			ConsumerContractDTO dto) throws BizServiceException;

	public void updateConsumerContract(ConsumerContractDTO dto)
			throws BizServiceException;

	public void deleteConsumerContract(ConsumerContractDTO dto)
			throws BizServiceException;

	public ConsumerContractDTO viewConsumerContractDTO(ConsumerContractDTO dto)
			throws BizServiceException;

}

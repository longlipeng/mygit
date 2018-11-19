package com.huateng.univer.consumer.merchantacctypecontract.biz.service;

import com.allinfinance.univer.consumercontract.dto.ConsumerContractDTO;
import com.huateng.framework.exception.BizServiceException;

public interface MchantAccTypeContractService {
	public ConsumerContractDTO selectAccTypeContractDTO(ConsumerContractDTO dto)
			throws BizServiceException;

	public void getServiceDTOList(ConsumerContractDTO dto)
			throws BizServiceException;

	public ConsumerContractDTO selectAccTypeContDTOInVer(ConsumerContractDTO dto)
			throws BizServiceException;

}

package com.huateng.univer.consumer.mchntcontract.biz.service;

import java.util.List;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.consumercontract.dto.ConsumerContractDTO;
import com.allinfinance.univer.consumercontract.dto.ConsumerContractQueryDTO;
import com.huateng.framework.exception.BizServiceException;

/**
 * 
 * @author zengfenghuakkkkkk
 */
public interface MchntContractService {
	/**
	 * 
	 * @param dto
	 * @return
	 * @throws BizServiceException
	 */
	public PageDataDTO inquiryMchntContract(ConsumerContractQueryDTO dto)
			throws BizServiceException;

	public List<ConsumerContractDTO> inqueryContractById(String entityId)
			throws BizServiceException;
}
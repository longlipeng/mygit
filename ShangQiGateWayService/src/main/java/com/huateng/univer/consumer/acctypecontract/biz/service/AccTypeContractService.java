package com.huateng.univer.consumer.acctypecontract.biz.service;

import java.util.List;

import com.allinfinance.univer.consumercontract.dto.AccTypeContractDTO;
import com.huateng.framework.exception.BizServiceException;

public interface AccTypeContractService {
	public AccTypeContractDTO addAccTypeContractDTO(AccTypeContractDTO dto)
			throws BizServiceException;

	public void insertAccTypeContractDTO(AccTypeContractDTO dto)
			throws BizServiceException;

	public AccTypeContractDTO viewAccTypeContractDTO(AccTypeContractDTO dto)
			throws BizServiceException;

	public void updateAccTypeContract(AccTypeContractDTO dto)
			throws BizServiceException;

	public void deleteAccTypeContract(AccTypeContractDTO dto)
			throws BizServiceException;

	public List<AccTypeContractDTO> inquery(String Id)
			throws BizServiceException;
}

package com.huateng.univer.issuer.issuercontract.service;

import java.util.List;

import com.allinfinance.univer.issuer.dto.issuerContract.LoyaltyProdContractDTO;
import com.huateng.framework.exception.BizServiceException;

public interface LoyaltyProdContractService {

	public List<LoyaltyProdContractDTO> inquery(String issuerContractId)
			throws BizServiceException;

}

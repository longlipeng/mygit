package com.huateng.univer.issuer.issuercontract.dao;

import com.allinfinance.univer.issuer.dto.issuerContract.LoyaltyContractDTO;
import com.allinfinance.univer.seller.sellercontract.dto.SellerContractDTO;
import com.huateng.framework.exception.BizServiceException;

public interface LoyaltyContractServiceDAO {
	public LoyaltyContractDTO inqueryLoyaltyContractInfo(
			String loyaltyContractId) throws BizServiceException;

	public SellerContractDTO inquerySellerContractInfo(String sellerContractId)
			throws BizServiceException;
}

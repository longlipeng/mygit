package com.huateng.univer.issuer.issuercontract.dao.impl;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.allinfinance.univer.issuer.dto.issuerContract.LoyaltyContractDTO;
import com.allinfinance.univer.seller.sellercontract.dto.SellerContractDTO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.univer.issuer.issuercontract.dao.LoyaltyContractServiceDAO;

public class LoyaltyContractServiceDAOImpl extends SqlMapClientDaoSupport
		implements LoyaltyContractServiceDAO {

	public LoyaltyContractDTO inqueryLoyaltyContractInfo(
			String loyaltyContractId) throws BizServiceException {
		LoyaltyContractDTO loyaltyContractDTO = (LoyaltyContractDTO) getSqlMapClientTemplate()
				.queryForObject("LOYALTYCONTRACT.selectLoyaltyContractInfo",
						loyaltyContractId);
		return loyaltyContractDTO;
	}

	public SellerContractDTO inquerySellerContractInfo(String sellerContractId)
			throws BizServiceException {
		SellerContractDTO contractDTO = (SellerContractDTO) getSqlMapClientTemplate()
				.queryForObject("LOYALTYCONTRACT.selectSellerContractInfo",
						sellerContractId);
		return contractDTO;
	}

}

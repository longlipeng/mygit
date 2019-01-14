package com.allinfinance.prepay.service;

import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.univer.seller.sellercontract.dto.SellerContractDTO;

public interface SellerContractService {
	public SellerContractDTO insert(SellerContractDTO sellerContractDTO)
			throws BizServiceException;

}

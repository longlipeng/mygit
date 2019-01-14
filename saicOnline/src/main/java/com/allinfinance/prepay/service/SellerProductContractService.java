package com.allinfinance.prepay.service;

import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.univer.seller.sellercontract.dto.SellerProductContractDTO;

public interface SellerProductContractService {
	public void insert(SellerProductContractDTO sellerProductContractDTO)
			throws BizServiceException ;

}

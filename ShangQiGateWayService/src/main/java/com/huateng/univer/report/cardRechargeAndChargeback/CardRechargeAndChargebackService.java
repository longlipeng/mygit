package com.huateng.univer.report.cardRechargeAndChargeback;

import java.util.List;

import com.allinfinance.univer.seller.seller.dto.SellerDTO;
import com.allinfinance.univer.system.user.dto.UserDTO;
import com.huateng.framework.exception.BizServiceException;

public interface CardRechargeAndChargebackService {
	public List<SellerDTO> getAllSeller(SellerDTO sellerDTO)throws BizServiceException;
	public List<UserDTO> getSaleManOfSeller(SellerDTO sellerDTO)throws BizServiceException;
}

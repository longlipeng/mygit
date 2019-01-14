package com.allinfinance.shangqi.gateway.service;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.shangqi.gateway.dto.TxnqueryDTO;
import com.allinfinance.univer.consumer.merchant.dto.MerchantQueryDTO;
import com.allinfinance.univer.seller.customer.CustomerQueryDTO;
import com.huateng.framework.exception.BizServiceException;

public interface TxnqueryService {
	public PageDataDTO inqueryTxnRecord(TxnqueryDTO txnQueryDTO)
			throws BizServiceException;
}

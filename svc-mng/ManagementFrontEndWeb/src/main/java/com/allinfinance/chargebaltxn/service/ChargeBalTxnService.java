package com.allinfinance.chargebaltxn.service;

import com.allinfinance.charagebalTxn.dto.ChargeTxnDTO;
import com.allinfinance.framework.dto.PageDataDTO;
import com.huateng.framework.exception.BizServiceException;

public interface ChargeBalTxnService {
	public void insertChargeBal(ChargeTxnDTO dto) throws BizServiceException;
	public PageDataDTO queryChargeBalTxn(ChargeTxnDTO dto) throws BizServiceException;
	
}

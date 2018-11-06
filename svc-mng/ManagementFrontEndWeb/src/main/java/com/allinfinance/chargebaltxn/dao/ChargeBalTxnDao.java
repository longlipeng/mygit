package com.allinfinance.chargebaltxn.dao;

import com.allinfinance.charagebalTxn.dto.ChargeTxnDTO;
import com.huateng.framework.exception.BizServiceException;

public interface ChargeBalTxnDao {
	public void insert(ChargeTxnDTO dto) throws BizServiceException;
}

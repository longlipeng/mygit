package com.allinfinance.shangqi.gateway.dao;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.shangqi.gateway.dto.TxnqueryDTO;

public interface TxnqueryDao {
	PageDataDTO query(String statement, TxnqueryDTO dto);
}

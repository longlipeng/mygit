package com.huateng.univer.consumer.acctypecontract.integration.dao;

import java.util.List;

import com.allinfinance.univer.servicefeerule.dto.ServiceFeeRuleDTO;
import com.allinfinance.univer.servicefeerule.dto.ServiceFeeRuleQueryDTO;
import com.huateng.framework.ibatis.dao.AcctypeContractDAO;

public interface AccTypeContractServiceDAO extends AcctypeContractDAO {
	public List<ServiceFeeRuleDTO> queryDTOList(ServiceFeeRuleQueryDTO dto);
}

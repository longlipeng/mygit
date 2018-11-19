package com.huateng.univer.consumer.acctypecontract.integration.dao.impl;

import com.allinfinance.univer.servicefeerule.dto.ServiceFeeRuleDTO;
import com.allinfinance.univer.servicefeerule.dto.ServiceFeeRuleQueryDTO;
import com.huateng.framework.ibatis.dao.AcctypeContractDAOImpl;
import com.huateng.univer.consumer.acctypecontract.integration.dao.AccTypeContractServiceDAO;

import java.util.List;

public class AccTypeContractServiceDAOImpl extends AcctypeContractDAOImpl
		implements AccTypeContractServiceDAO {
	public List<ServiceFeeRuleDTO> queryDTOList(ServiceFeeRuleQueryDTO dto) {
		List<ServiceFeeRuleDTO> dtoList = (List<ServiceFeeRuleDTO>) getSqlMapClientTemplate()
				.queryForList("ServiceFeeRule.selectRulesByEntityId", dto);
		return dtoList;
	}
}

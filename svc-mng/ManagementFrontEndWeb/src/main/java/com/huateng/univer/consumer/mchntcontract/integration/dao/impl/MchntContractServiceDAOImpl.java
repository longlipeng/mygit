package com.huateng.univer.consumer.mchntcontract.integration.dao.impl;

import java.util.List;

import com.allinfinance.univer.consumercontract.dto.ConsumerContractDTO;
import com.huateng.framework.ibatis.dao.ConsumerContractDAOImpl;
import com.huateng.univer.consumer.mchntcontract.integration.dao.MchntContractServiceDAO;

public class MchntContractServiceDAOImpl extends ConsumerContractDAOImpl
		implements MchntContractServiceDAO {

	@SuppressWarnings("unchecked")
	public List<ConsumerContractDTO> selectById(String entityId) {
		List<ConsumerContractDTO> list = (List<ConsumerContractDTO>) getSqlMapClientTemplate()
				.queryForList("CONSUMER_CONTRACT.selectById", entityId);
		return list;
	}
}
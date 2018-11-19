package com.huateng.univer.consumer.mchntcontract.integration.dao;

import java.util.List;

import com.allinfinance.univer.consumercontract.dto.ConsumerContractDTO;
import com.huateng.framework.ibatis.dao.ConsumerContractDAO;

public interface MchntContractServiceDAO extends ConsumerContractDAO {

	public List<ConsumerContractDTO> selectById(String entityId);
}

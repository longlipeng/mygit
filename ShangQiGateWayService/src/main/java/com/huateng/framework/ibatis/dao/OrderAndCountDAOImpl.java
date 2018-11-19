package com.huateng.framework.ibatis.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.allinfinance.univer.cardmanagement.dto.CardManagementDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.huateng.framework.exception.BizServiceException;

public class OrderAndCountDAOImpl extends SqlMapClientDaoSupport implements OrderAndCountDAO{

	@Override
	public List<SellOrderDTO> orderIdAndCount(CardManagementDTO cardManagementDTO)throws BizServiceException {
		
		List<SellOrderDTO> list=(List<SellOrderDTO>)		
		this.getSqlMapClientTemplate().queryForList("CARDORDER.selectOrderIdAndCount", cardManagementDTO);
		return list;
	}

}

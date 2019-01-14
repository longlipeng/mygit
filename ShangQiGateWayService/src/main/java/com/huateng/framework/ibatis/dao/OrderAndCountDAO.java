package com.huateng.framework.ibatis.dao;

import java.util.List;

import com.allinfinance.univer.cardmanagement.dto.CardManagementDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.huateng.framework.exception.BizServiceException;

public interface OrderAndCountDAO {
	public List<SellOrderDTO> orderIdAndCount(CardManagementDTO cardManagementDTO)throws BizServiceException;
}

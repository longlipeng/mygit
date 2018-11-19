package com.huateng.univer.cardmanage.integration.dao;

import java.util.List;
import java.util.Map;

import com.allinfinance.univer.cardmanagement.dto.AccountDTO;
import com.allinfinance.univer.cardmanagement.dto.CardManagementDTO;
import com.allinfinance.univer.cardmanagement.dto.RiskDTO;
import com.allinfinance.univer.cardmanagement.dto.RiskSvcCtrlDTO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.CardManagementDAO;
import com.huateng.framework.ibatis.dao.CardManagementDAO;
import com.huateng.framework.ibatis.dao.CardholderCardlistDAO;

public interface CardManageServiceDAO extends CardManagementDAO {
	public Map selectCardHolderCardList(CardManagementDTO dto);

	public CardManagementDTO viewOrderByCardNo(CardManagementDTO dto) throws BizServiceException;
}

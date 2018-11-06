package com.huateng.univer.cardmanage.integration.dao.impl;

import java.util.Map;

import com.allinfinance.univer.cardmanagement.dto.CardManagementDTO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.CardManagementDAOImpl;
import com.huateng.univer.cardmanage.integration.dao.CardManageServiceDAO;

public class CardmanageServiceDAOImpl extends CardManagementDAOImpl implements
		CardManageServiceDAO {
	public Map selectCardHolderCardList(CardManagementDTO dto) {
		Map map = getSqlMapClientTemplate().queryForMap(
				"CARDHOLDERCARDLIST.selectCardholderCardlist", dto, null);
		return map;
	}

	public CardManagementDTO viewOrderByCardNo(CardManagementDTO dto) throws BizServiceException {
		CardManagementDTO cardManagementDTO = new CardManagementDTO();
		cardManagementDTO.setAgentrCertType(dto.getAgentrCertType());
		cardManagementDTO.setAgentrCertTypeNo(dto.getAgentrCertTypeNo());
		cardManagementDTO.setAgentrName(dto.getAgentrName());
		cardManagementDTO.setStartDate(dto.getStartDate());
		cardManagementDTO.setEndDate(dto.getEndDate());

	
		dto = (CardManagementDTO) getSqlMapClientTemplate().queryForObject(
				"CARDMANAGEMENT.viewOrderByCardNo", dto);
		if(null==dto){
			throw new BizServiceException("该卡未入库！");
		}
		dto.setAgentrCertType(cardManagementDTO.getAgentrCertType());
		dto.setAgentrCertTypeNo(cardManagementDTO.getAgentrCertTypeNo());
		dto.setAgentrName(cardManagementDTO.getAgentrName());
		dto.setStartDate(cardManagementDTO.getStartDate());
		dto.setEndDate(cardManagementDTO.getEndDate());
		return dto;
	}
}

package com.huateng.univer.report.cardBalanceDatail.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.allinfinance.univer.report.dto.CardBalanceDatailDTO;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.TxnType;
import com.huateng.univer.report.cardBalanceDatail.CardBalanceDetailService;

@SuppressWarnings("unchecked")
public class CardBalanceDetailServiceImpl implements CardBalanceDetailService {
	private Logger logger = Logger.getLogger(CardBalanceDetailServiceImpl.class);
	private BaseDAO baseDAO;
	private List<CardBalanceDatailDTO> hisList;
	private Map<String,String> map = new HashMap<String, String>();
	
	/**
	 * 卡余额表动明细表
	 */
	public List<CardBalanceDatailDTO> getAccCardInfo(
			CardBalanceDatailDTO cardBalanceDatailDTO)
			throws BizServiceException {
		hisList = new ArrayList<CardBalanceDatailDTO>();
		map = TxnType.getMap();
		Set<String> keys = map.keySet();
		try {
			// 获取历史交易
		    cardBalanceDatailDTO.setStartDate(DateUtil.StringDate(cardBalanceDatailDTO.getStartDate()));
		    cardBalanceDatailDTO.setEndDate(DateUtil.StringDate(cardBalanceDatailDTO.getEndDate()));
			hisList = baseDAO.queryForList("CARD_BALANCE_DATAIL",
					"GET_ACC_CARD_DATAIL_HIS", cardBalanceDatailDTO);
			for (CardBalanceDatailDTO c : hisList) {
				c.getTxnDate().replace(".0", "");
				for (String key : keys) {
					if (key.equals(c.getTxnType())) {
						c.setTxnType(map.get(key));
						continue;
					}
				}
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询卡余额失败");
		}
		return hisList;
	}

	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public List<CardBalanceDatailDTO> getHisList() {
		return hisList;
	}

	public void setHisList(List<CardBalanceDatailDTO> hisList) {
		this.hisList = hisList;
	}

}

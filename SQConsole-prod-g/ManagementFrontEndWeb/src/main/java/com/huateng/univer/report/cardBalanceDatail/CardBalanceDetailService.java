package com.huateng.univer.report.cardBalanceDatail;

import java.util.List;

import com.allinfinance.univer.report.dto.CardBalanceDatailDTO;
import com.huateng.framework.exception.BizServiceException;

/**
 *	卡余额变动明细表
 */
public interface CardBalanceDetailService {
	public List<CardBalanceDatailDTO> getAccCardInfo(CardBalanceDatailDTO cardBalanceDatailDTO) throws BizServiceException;
}

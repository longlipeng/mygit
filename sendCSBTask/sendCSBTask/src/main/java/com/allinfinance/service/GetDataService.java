package com.allinfinance.service;

import java.util.List;
import java.util.Map;

import com.allinfinance.model.GetCardInfo;
import com.allinfinance.model.SingleTransactionInfo;

/**
 * 从数据库获取数据service
 * @author taojiajun
 *
 */
public interface GetDataService {
	/**
	 * 获取记名卡领卡信息
	 * @param condition
	 * @return
	 */
	String selectGetIsRegisterCardInfo(Map<String, Object> condition);
	/**
	 * 获取非记名卡领卡信息
	 * @param condition
	 * @return
	 */
	String selectGetNotIsRegisterCardInfo(Map<String, Object> condition);
	/**
	 * 充值交易信息查询
	 * @param condition
	 * @return
	 */
	String selectSingleTxnInfo(Map<String, Object> condition);
	/**
	 * 交易 消费 调账等
	 * @param condition
	 * @return
	 */
	String selectTransaction(Map<String, Object> condition);
	/**
	 * 获取历史卡信息
	 * @return
	 */
	String getHistoryCard();

}

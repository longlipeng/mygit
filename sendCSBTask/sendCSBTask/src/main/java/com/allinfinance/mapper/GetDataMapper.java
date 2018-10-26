package com.allinfinance.mapper;

import java.util.List;
import java.util.Map;

import com.allinfinance.model.GetCardInfo;
import com.allinfinance.model.HistoryCardInfo;
import com.allinfinance.model.RechargeTxnInfo;
import com.allinfinance.model.SingleTransactionInfo;

/**
 * 从数据库取得数据Mapper
 * @author taojiajun
 *
 */
public interface GetDataMapper {
	/**
	 * 获取领卡信息
	 * @param condition
	 * @return
	 */
	List<GetCardInfo> selectGetCardInfo(Map<String, Object> condition);
	/**
	 * 单笔充值交易信息查询
	 * @param condition
	 * @return
	 */
	List<RechargeTxnInfo> selectSingleTxnInfo(Map<String, Object> condition);
	/**
	 * 交易查询 消费 调账等
	 * @param condition
	 * @return
	 */
	List<SingleTransactionInfo> selectTransaction(Map<String, Object> condition);
	/**
	 * 历史交易卡信息
	 * @param condition
	 * @return
	 */
	List<HistoryCardInfo> selectHistory(Map<String, Object> condition);
}

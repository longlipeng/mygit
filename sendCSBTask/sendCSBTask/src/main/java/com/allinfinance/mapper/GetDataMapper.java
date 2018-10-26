package com.allinfinance.mapper;

import java.util.List;
import java.util.Map;

import com.allinfinance.model.GetCardInfo;
import com.allinfinance.model.HistoryCardInfo;
import com.allinfinance.model.RechargeTxnInfo;
import com.allinfinance.model.SingleTransactionInfo;

/**
 * �����ݿ�ȡ������Mapper
 * @author taojiajun
 *
 */
public interface GetDataMapper {
	/**
	 * ��ȡ�쿨��Ϣ
	 * @param condition
	 * @return
	 */
	List<GetCardInfo> selectGetCardInfo(Map<String, Object> condition);
	/**
	 * ���ʳ�ֵ������Ϣ��ѯ
	 * @param condition
	 * @return
	 */
	List<RechargeTxnInfo> selectSingleTxnInfo(Map<String, Object> condition);
	/**
	 * ���ײ�ѯ ���� ���˵�
	 * @param condition
	 * @return
	 */
	List<SingleTransactionInfo> selectTransaction(Map<String, Object> condition);
	/**
	 * ��ʷ���׿���Ϣ
	 * @param condition
	 * @return
	 */
	List<HistoryCardInfo> selectHistory(Map<String, Object> condition);
}

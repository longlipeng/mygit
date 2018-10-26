package com.allinfinance.service;

import java.util.List;
import java.util.Map;

import com.allinfinance.model.GetCardInfo;
import com.allinfinance.model.SingleTransactionInfo;

/**
 * �����ݿ��ȡ����service
 * @author taojiajun
 *
 */
public interface GetDataService {
	/**
	 * ��ȡ�������쿨��Ϣ
	 * @param condition
	 * @return
	 */
	String selectGetIsRegisterCardInfo(Map<String, Object> condition);
	/**
	 * ��ȡ�Ǽ������쿨��Ϣ
	 * @param condition
	 * @return
	 */
	String selectGetNotIsRegisterCardInfo(Map<String, Object> condition);
	/**
	 * ��ֵ������Ϣ��ѯ
	 * @param condition
	 * @return
	 */
	String selectSingleTxnInfo(Map<String, Object> condition);
	/**
	 * ���� ���� ���˵�
	 * @param condition
	 * @return
	 */
	String selectTransaction(Map<String, Object> condition);
	/**
	 * ��ȡ��ʷ����Ϣ
	 * @return
	 */
	String getHistoryCard();

}

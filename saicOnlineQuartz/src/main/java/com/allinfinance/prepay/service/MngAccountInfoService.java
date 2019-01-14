package com.allinfinance.prepay.service;

import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.prepay.model.CardholderRelAcc;
import com.allinfinance.univer.seller.cardholder.dto.CardholderDTO;

public interface MngAccountInfoService {
	
	public void insertAccInfoAndBinding(String id,String type) throws BizServiceException;
	//�����˻�
	public String insertAccInfo(String accType,String cardNo) throws BizServiceException;
	//���ɵ��ӿ���
	public String MakeVirtualCardNo(String id) throws BizServiceException;

}

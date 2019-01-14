package com.huateng.univer.entitybaseinfo.deliverycontact.biz.service;

import java.util.List;

import com.allinfinance.univer.entity.dto.DeliveryRecipientDTO;
import com.huateng.framework.exception.BizServiceException;

public interface DeliveryContactService {

	public List<DeliveryRecipientDTO> inquery(String deliveryPointId)
			throws BizServiceException;

	DeliveryRecipientDTO view(DeliveryRecipientDTO deliveryRecipientDTO)
			throws BizServiceException;

	public void insert(DeliveryRecipientDTO deliveryRecipientDTO)
			throws BizServiceException;

	public void delete(DeliveryRecipientDTO deliveryRecipientDTO)
			throws BizServiceException;
}

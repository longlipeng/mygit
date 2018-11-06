package com.allinfinance.prepay.service;

import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.univer.entity.dto.DeliveryRecipientDTO;

public interface DeliveryContactService {
	
	public void insert(DeliveryRecipientDTO deliveryRecipientDTO)
			throws BizServiceException;

}

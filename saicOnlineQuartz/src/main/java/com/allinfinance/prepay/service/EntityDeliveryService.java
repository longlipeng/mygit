package com.allinfinance.prepay.service;

import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.univer.entity.dto.DeliveryPointDTO;

public interface EntityDeliveryService {
	public DeliveryPointDTO insert(DeliveryPointDTO deliveryPointDTO)
			throws BizServiceException;
	

}

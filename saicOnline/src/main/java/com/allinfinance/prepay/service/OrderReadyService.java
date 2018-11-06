package com.allinfinance.prepay.service;

import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.univer.seller.order.dto.OrderReadyDTO;

public interface OrderReadyService {
	public void cardReady(OrderReadyDTO orderReadyDTO)
			throws BizServiceException;

}

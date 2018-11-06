package com.allinfinance.prepay.service;

import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.univer.seller.order.dto.SellOrderPaymentDTO;

public interface SellOrderPaymentService {
	 public void insertOrderPayment(SellOrderPaymentDTO dto) throws BizServiceException;

}

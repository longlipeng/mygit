package com.allinfinance.prepay.service;

import java.util.List;

import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.univer.entity.dto.BankDTO;

public interface BankService {
	public List<BankDTO> inquery(String entityId) throws BizServiceException ;
	public List<BankDTO> inquery(String entityId, String type)
			throws BizServiceException;

}

package com.huateng.univer.entitybaseinfo.bank.biz.service;

import java.util.List;

import com.allinfinance.univer.entity.dto.BankDTO;
import com.huateng.framework.exception.BizServiceException;

public interface BankService {
	public List<BankDTO> inquery(String entityId, String type)
			throws BizServiceException;

	public void insert(BankDTO bankDTO) throws BizServiceException;

	public void update(BankDTO bankDTO) throws BizServiceException;

	public void delete(BankDTO bankDTO) throws BizServiceException;

	public BankDTO view(BankDTO bankDTO) throws BizServiceException;

	public List<BankDTO> inquery(String entityId) throws BizServiceException;
}

package com.huateng.univer.issuer.consumer.dao;

import com.allinfinance.univer.issuer.dto.consumer.AcqPayDTO;
import com.huateng.framework.exception.BizServiceException;

public interface AcqPayDAO {
	public void delBank(AcqPayDTO acqPayDTO) throws BizServiceException;

	public void delete(AcqPayDTO acqPayDTO) throws BizServiceException;

	public void deleteCardProduct(AcqPayDTO acqPayDTO)
			throws BizServiceException;
}

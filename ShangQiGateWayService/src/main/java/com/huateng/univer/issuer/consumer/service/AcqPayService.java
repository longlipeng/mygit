package com.huateng.univer.issuer.consumer.service;

import java.util.List;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.entity.dto.BankDTO;
import com.allinfinance.univer.issuer.dto.consumer.AcqPayDTO;
import com.allinfinance.univer.issuer.dto.consumer.ConsumerQueryDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.huateng.framework.exception.BizServiceException;

public interface AcqPayService {
	public PageDataDTO inquery(ConsumerQueryDTO consumerQueryDTO)
			throws BizServiceException;

	public PageDataDTO choose(ConsumerQueryDTO consumerQueryDTO)
			throws BizServiceException;

	public List<ProductDTO> selectProd(AcqPayDTO acqPayDTO)
			throws BizServiceException;

	public void insert(AcqPayDTO acqPayDTO) throws BizServiceException;

	public AcqPayDTO reEdit(AcqPayDTO acqPayDTo) throws BizServiceException;

	public void edit(AcqPayDTO acqPayDTo) throws BizServiceException;

	public AcqPayDTO view(AcqPayDTO acqPayDTo) throws BizServiceException;

	public void delete(AcqPayDTO acqPayDTo) throws BizServiceException;

	public List<BankDTO> selectBank(AcqPayDTO acqPayDTO)
			throws BizServiceException;

	public PageDataDTO chooseBank(ConsumerQueryDTO consumerQueryDTO)
			throws BizServiceException;

	public void addBank(AcqPayDTO acqPayDTO) throws BizServiceException;

	public void delBank(AcqPayDTO acqPayDTO) throws BizServiceException;
}

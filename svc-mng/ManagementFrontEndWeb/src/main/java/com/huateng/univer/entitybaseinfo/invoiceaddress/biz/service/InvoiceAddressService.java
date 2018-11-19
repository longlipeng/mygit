package com.huateng.univer.entitybaseinfo.invoiceaddress.biz.service;

import java.util.List;

import com.allinfinance.univer.entity.dto.InvoiceAddressDTO;
import com.huateng.framework.exception.BizServiceException;

public interface InvoiceAddressService {

	public List<InvoiceAddressDTO> inquery(String entityId)
			throws BizServiceException;

	public void insert(InvoiceAddressDTO invoiceAddressDTO)
			throws BizServiceException;

	public void delete(InvoiceAddressDTO invoiceAddressDTO)
			throws BizServiceException;
	public void updateByPrimaryKey(InvoiceAddressDTO invoiceAddressDTO)
			throws BizServiceException;
}

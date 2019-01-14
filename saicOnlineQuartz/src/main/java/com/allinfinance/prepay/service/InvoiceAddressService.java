package com.allinfinance.prepay.service;

import java.util.List;

import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.univer.entity.dto.InvoiceAddressDTO;

public interface InvoiceAddressService {
	public List<InvoiceAddressDTO> inquery(String entityId)
			throws BizServiceException;
	public void insert(InvoiceAddressDTO invoiceAddressDTO)
			throws BizServiceException;
	
	public void updateByPrimaryKey(InvoiceAddressDTO invoiceAddressDTO)
			throws BizServiceException;

}

package com.allinfinance.prepay.service;

import java.util.List;

import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.prepay.model.InvoiceCompany;
import com.allinfinance.univer.entity.dto.InvoiceCompanyDTO;

public interface InvoiceCompanyService {
	
	public List<InvoiceCompanyDTO> inquery(String entityId)
			throws BizServiceException;

	public void insert(InvoiceCompanyDTO invoiceCompanyDTO)
			throws BizServiceException;
	
	public void updateByPrimaryKey(InvoiceCompany invoiceCompany)
			throws BizServiceException;
	
	public List<InvoiceCompany> selectByEntityId(String entityId)
			throws BizServiceException ;

}

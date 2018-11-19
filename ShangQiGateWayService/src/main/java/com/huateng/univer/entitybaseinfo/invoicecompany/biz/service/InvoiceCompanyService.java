package com.huateng.univer.entitybaseinfo.invoicecompany.biz.service;

import java.util.List;

import com.allinfinance.univer.entity.dto.InvoiceCompanyDTO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.model.InvoiceCompany;

public interface InvoiceCompanyService {

	public List<InvoiceCompanyDTO> inquery(String entityId)
			throws BizServiceException;

	public void insert(InvoiceCompanyDTO invoiceCompanyDTO)
			throws BizServiceException;

	public void delete(InvoiceCompanyDTO invoiceCompanyDTO)
			throws BizServiceException;
	
	public void updateByPrimaryKey(InvoiceCompany invoiceCompany)
			throws BizServiceException;
	
	public void updateByPrimaryKeySelective(InvoiceCompany invoiceCompany)
			throws BizServiceException;
}

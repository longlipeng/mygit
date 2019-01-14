package com.huateng.univer.servicefeerule.biz.service;

import java.util.List;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.servicefeerule.dto.ServiceFeeRuleDTO;
import com.allinfinance.univer.servicefeerule.dto.ServiceFeeRuleQueryDTO;
import com.huateng.framework.exception.BizServiceException;

public interface ServiceFeeRuleService {
	public PageDataDTO query(ServiceFeeRuleQueryDTO dto)
			throws BizServiceException;

	public void insert(ServiceFeeRuleDTO dto) throws BizServiceException;

	public List<ServiceFeeRuleDTO> edit(ServiceFeeRuleDTO dto)
			throws BizServiceException;

	public void update(ServiceFeeRuleDTO dto) throws BizServiceException;

	public void modify(ServiceFeeRuleDTO dto) throws BizServiceException;

	public void delete(ServiceFeeRuleDTO dto) throws BizServiceException;
}

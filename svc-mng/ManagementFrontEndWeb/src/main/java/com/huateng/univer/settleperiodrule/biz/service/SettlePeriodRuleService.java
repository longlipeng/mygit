package com.huateng.univer.settleperiodrule.biz.service;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.settleperiodrule.dto.SettlePeriodRuleDTO;
import com.allinfinance.univer.settleperiodrule.dto.SettlePeriodRuleQueryDTO;
import com.huateng.framework.exception.BizServiceException;

public interface SettlePeriodRuleService {
	public void insert(SettlePeriodRuleDTO dto) throws BizServiceException;

	public PageDataDTO query(SettlePeriodRuleQueryDTO dto)
			throws BizServiceException;

	public SettlePeriodRuleDTO editInit(SettlePeriodRuleDTO dto)
			throws BizServiceException;

	public void update(SettlePeriodRuleDTO dto) throws BizServiceException;

	public void delete(SettlePeriodRuleDTO dto) throws BizServiceException;

	public void modify(SettlePeriodRuleDTO dto) throws BizServiceException;

	public SettlePeriodRuleDTO view(SettlePeriodRuleDTO dto)
			throws BizServiceException;

	public String getSettleDateNext(String contractStartDate, String ruleNo)
			throws BizServiceException;

	public void initInsert(String entityId, String userId)
			throws BizServiceException;
}

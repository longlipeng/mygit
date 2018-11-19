package com.huateng.univer.issuer.validPeriodRule.service;

import java.util.List;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.product.ValidPeriodRuleDspDTO;
import com.allinfinance.univer.issuer.dto.product.ValidPeriodRuleQueryDTO;
import com.huateng.framework.exception.BizServiceException;

public interface ValidPeriodRuleService {
	/**
	 * 查询有效期
	 */
	public PageDataDTO queryValidPeriodRule(
			ValidPeriodRuleQueryDTO validPeriodRuleQueryDTO)
			throws BizServiceException;

	/**
	 * 添加有效期
	 */
	public void insert(ValidPeriodRuleDspDTO validPeriodRuleDspDTO)
			throws BizServiceException;

	/**
	 * 编辑有效期
	 */
	public void update(ValidPeriodRuleDspDTO validPeriodRuleDspDTO)
			throws BizServiceException;

	/**
	 * 查看有效期
	 */
	public ValidPeriodRuleQueryDTO view(ValidPeriodRuleDspDTO dsp)
			throws BizServiceException;

	/**
	 * 操作有效期规则
	 */
	public void operate(List<ValidPeriodRuleDspDTO> ruleDspList)
			throws BizServiceException;
}

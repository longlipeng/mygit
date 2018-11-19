package com.huateng.univer.issuer.validPeriodRule.dao;

import java.util.List;

import com.allinfinance.univer.issuer.dto.product.ProductQueryDTO;
import com.allinfinance.univer.issuer.dto.product.ValidPeriodRuleDspDTO;

public interface ValidPeriodRuleServiceDAO {
	/**
	 * 查询有效期规则
	 * 
	 * @param ruleDspId
	 * @return
	 */
	public ValidPeriodRuleDspDTO getValidPeriodRuleDspDTO(
			ValidPeriodRuleDspDTO dsp);

	public List<ValidPeriodRuleDspDTO> getList(ProductQueryDTO productQueryDTO);

}

package com.huateng.univer.issuer.validPeriodRule.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.allinfinance.univer.issuer.dto.product.ProductQueryDTO;
import com.allinfinance.univer.issuer.dto.product.ValidPeriodRuleDspDTO;
import com.huateng.univer.issuer.validPeriodRule.dao.ValidPeriodRuleServiceDAO;

public class ValidPeriodRuleServiceDAOImpl extends SqlMapClientDaoSupport
		implements ValidPeriodRuleServiceDAO {

	public ValidPeriodRuleDspDTO getValidPeriodRuleDspDTO(
			ValidPeriodRuleDspDTO dsp) {
		ValidPeriodRuleDspDTO validPeriodRuleDspDTO = (ValidPeriodRuleDspDTO) getSqlMapClientTemplate()
				.queryForObject("VALIDRULEDSP.selectValidRuleDsp", dsp);
		return validPeriodRuleDspDTO;
	}

	public List<ValidPeriodRuleDspDTO> getList(ProductQueryDTO productQueryDTO) {
		List<ValidPeriodRuleDspDTO> validPeriodRuleDspDTOs = (List<ValidPeriodRuleDspDTO>) getSqlMapClientTemplate()
				.queryForList("VALIDRULEDSP.selectList", productQueryDTO);
		return validPeriodRuleDspDTOs;
	}
}

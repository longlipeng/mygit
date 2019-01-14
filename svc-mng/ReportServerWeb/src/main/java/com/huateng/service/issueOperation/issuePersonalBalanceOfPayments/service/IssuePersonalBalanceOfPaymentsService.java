/**
 * Classname IssuePersonalBalanceOfPaymentsService.java
 *
 * Version information
 *
 * Licensed Property to HuaTeng Data Co., Ltd.
 * 
 * (C) Copyright of HuaTeng Data Co., Ltd. 2010
 *     All Rights Reserved.
 *
 * Project Info: HuaTeng Data Internet Acquiring  Project
 * 
 * Modification History:
 * =============================================================================
 *		Author		Date		Description
 *   ------------ ---------- ---------------------------------------------------
 *		zqs		2013-10-23
 * =============================================================================
 */


package com.huateng.service.issueOperation.issuePersonalBalanceOfPayments.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.allinfinance.service.issueOperation.issuePersonalBalanceOfPayments.dto.IssuePersonalBalanceOfPaymentsDTO;
import com.huateng.service.BizBaseService;
import com.huateng.service.BizService;

public class IssuePersonalBalanceOfPaymentsService extends BizBaseService implements
		BizService {

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getList(JSONObject jsonDto) {
		IssuePersonalBalanceOfPaymentsDTO issuePersonalBalanceOfPaymentsDTO = (IssuePersonalBalanceOfPaymentsDTO) JSONObject
				.toBean(jsonDto, IssuePersonalBalanceOfPaymentsDTO.class);
		return baseDao.queryForList("issue_personal_balanceOfPayments", "issue_personal_balanceOfPayments",
				issuePersonalBalanceOfPaymentsDTO);
	}

}
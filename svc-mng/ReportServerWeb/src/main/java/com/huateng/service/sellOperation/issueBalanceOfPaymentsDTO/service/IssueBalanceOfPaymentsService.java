package com.huateng.service.sellOperation.issueBalanceOfPaymentsDTO.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.allinfinance.service.issueOperation.issueBalanceOfPayments.dto.IssueBalanceOfPaymentsDTO;
import com.huateng.service.BizBaseService;
import com.huateng.service.BizService;

@SuppressWarnings("unchecked")
public class IssueBalanceOfPaymentsService extends BizBaseService implements
		BizService {

	public List<Object> getList(JSONObject jsonDto) {
		IssueBalanceOfPaymentsDTO ibop = (IssueBalanceOfPaymentsDTO) JSONObject.toBean(
				jsonDto, IssueBalanceOfPaymentsDTO.class);
		return baseDao.queryForList("issueBalanceOfPayments", "issueBalanceOfPayments", ibop);
	}

}

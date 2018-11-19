package com.huateng.univer.issuer.report;

import net.sf.json.JSONObject;

import com.allinfinance.univer.report.dto.IssueBalanceOfPaymentsDTO;
import com.huateng.univer.report.action.NewIreportAction;

public class IssueBalanceOfPaymentsAction extends NewIreportAction {
	private static final long serialVersionUID = -6596029261425930605L;
	/**
	 * 营销机构收支平衡报表
	 * 
	 */
	private IssueBalanceOfPaymentsDTO issueBalanceOfPaymentsDTO = new IssueBalanceOfPaymentsDTO();
	
	public String inQuery() throws Exception{
		issueBalanceOfPaymentsDTO.setReportName("seller_sell_balance_of_payments");
		issueBalanceOfPaymentsDTO.setReportType("xls");
		issueBalanceOfPaymentsDTO.setIssuerId(this.getUser().getEntityId());
		issueBalanceOfPaymentsDTO.setIssuerName(this.getUser().getIssuerName());
		issueBalanceOfPaymentsDTO.setReportFileName("营销机构收支平衡表");
		return "list";
	}
	
	public IssueBalanceOfPaymentsDTO getIssueBalanceOfPaymentsDTO() {
		return issueBalanceOfPaymentsDTO;
	}
	
	public void setIssueBalanceOfPaymentsDTO(
			IssueBalanceOfPaymentsDTO issueBalanceOfPaymentsDTO) {
		this.issueBalanceOfPaymentsDTO = issueBalanceOfPaymentsDTO;
	}
	
	protected JSONObject getJSONOBJect() {
		return JSONObject.fromObject(issueBalanceOfPaymentsDTO);
	}
}

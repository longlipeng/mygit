package com.huateng.univer.issuer.report;

import net.sf.json.JSONObject;



import com.allinfinance.univer.report.dto.IssuePersonalBalanceOfPaymentsDTO;
import com.huateng.univer.report.action.NewIreportAction;

/**
 * 个人收支平衡報表
 */
public class IssuePersonalBalanceOfPaymentsAction extends NewIreportAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6911509068002612132L;

	private IssuePersonalBalanceOfPaymentsDTO issuePersonalBalanceOfPaymentsDTO = new IssuePersonalBalanceOfPaymentsDTO();

	
	public String inQuery() throws Exception {
		issuePersonalBalanceOfPaymentsDTO.setReportName("issue_personal_balanceOfPayments");
		issuePersonalBalanceOfPaymentsDTO.setReportType("xls");
		issuePersonalBalanceOfPaymentsDTO.setUserId(getUser().getUserId());
		issuePersonalBalanceOfPaymentsDTO.setIssuerId(getUser().getEntityId());
		issuePersonalBalanceOfPaymentsDTO.setIssuerName(getUser().getSellerName());
		issuePersonalBalanceOfPaymentsDTO.setReportFileName("个人收支平衡表");
		return "list";
	}

	@Override
	protected JSONObject getJSONOBJect() {
		return JSONObject.fromObject(issuePersonalBalanceOfPaymentsDTO);
	}

	public IssuePersonalBalanceOfPaymentsDTO getIssuePersonalBalanceOfPaymentsDTO() {
		return issuePersonalBalanceOfPaymentsDTO;
	}

	public void setIssuePersonalBalanceOfPaymentsDTO(
			IssuePersonalBalanceOfPaymentsDTO issuePersonalBalanceOfPaymentsDTO) {
		this.issuePersonalBalanceOfPaymentsDTO = issuePersonalBalanceOfPaymentsDTO;
	}

	
	
	
	
}
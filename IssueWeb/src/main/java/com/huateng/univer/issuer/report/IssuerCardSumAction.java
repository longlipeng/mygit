package com.huateng.univer.issuer.report;


import net.sf.json.JSONObject;

import com.allinfinance.univer.report.dto.IssuerCardSumDTO;
import com.allinfinance.univer.report.dto.IssuerCardValSumDTO;
import com.huateng.univer.report.action.NewIreportAction;

/**
 * 发行机构的发卡汇总报表
 */
public class IssuerCardSumAction extends NewIreportAction {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 6911509068002612132L;

	private IssuerCardSumDTO issuerCardSumDTO = new IssuerCardSumDTO();




	

	public String inQuery() throws Exception {
		issuerCardSumDTO.setReportName("issuer_issue_sum");
		//ireportDTO.setReportName("seller_sell_summary");
		issuerCardSumDTO.setReportType("xls");
		issuerCardSumDTO.setIssuerId(getUser().getEntityId());
		issuerCardSumDTO.setIssuerName(getUser().getIssuerName());
		issuerCardSumDTO.setReportFileName("发卡情况汇总表");		
		return "list";
	}

	@Override
	protected JSONObject getJSONOBJect() {
		return JSONObject.fromObject(issuerCardSumDTO);
	}

	public IssuerCardSumDTO getIssuerCardSumDTO() {
		return issuerCardSumDTO;
	}

	public void setIssuerCardSumDTO(IssuerCardSumDTO issuerCardSumDTO) {
		this.issuerCardSumDTO = issuerCardSumDTO;
	}

	

}

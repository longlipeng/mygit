package com.huateng.univer.issuer.report;


import net.sf.json.JSONObject;

import com.allinfinance.univer.report.dto.IssuerCardValSumDTO;
import com.huateng.univer.report.action.NewIreportAction;

/**
 * 发行机构的卡余额汇总报表
 */
public class IssuerCardValSumAction extends NewIreportAction {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 6911509068002612132L;

	private IssuerCardValSumDTO issuerCardValSumDTO = new IssuerCardValSumDTO();




	public IssuerCardValSumDTO getIssuerCardValSumDTO() {
		return issuerCardValSumDTO;
	}

	public void setIssuerCardValSumDTO(IssuerCardValSumDTO issuerCardValSumDTO) {
		this.issuerCardValSumDTO = issuerCardValSumDTO;
	}

	public String execute() throws Exception {
		if (getUser().getEntityId().equals("0")) {
			this.addActionError("当前为一个发卡组，请选择一个发卡机构");
			return "error";
		} else {
			return "input";
		}

	}

	public String inQuery() throws Exception {
		issuerCardValSumDTO.setReportName("issuer_cardvalsum_ireport");
		//ireportDTO.setReportName("seller_sell_summary");
		
		issuerCardValSumDTO.setReportType("xls");
		issuerCardValSumDTO.setIssuerId(getUser().getEntityId());
		issuerCardValSumDTO.setIssuerName(getUser().getIssuerName());
		issuerCardValSumDTO.setReportFileName("卡余额汇总表");		
		return "list";
	}

	@Override
	protected JSONObject getJSONOBJect() {
		return JSONObject.fromObject(issuerCardValSumDTO);
	}

	

}

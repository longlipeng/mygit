package com.huateng.univer.issuer.report;


import net.sf.json.JSONObject;

import com.allinfinance.univer.report.dto.FundSubsideSumInputDTO;
import com.huateng.univer.report.action.NewIreportAction;




/**
 * 资金沉淀汇总報表
 */
public class FundSubsideSumAction extends NewIreportAction {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6911509068002612132L;



	private FundSubsideSumInputDTO fundSubsideSumInputDTO = new FundSubsideSumInputDTO();

	/**
	 * @param fundSubsideSumInputDTO the fundSubsideSumInputDTO to set
	 */ 
	public void setFundSubsideSumInputDTO(FundSubsideSumInputDTO fundSubsideSumInputDTO) {
		this.fundSubsideSumInputDTO = fundSubsideSumInputDTO;
	}

	/**
	 * @return the fundSubsideSumInputDTO
	 */
	public FundSubsideSumInputDTO getFundSubsideSumInputDTO() {
		return fundSubsideSumInputDTO;
	}
	
	

	public String inQuery() throws Exception {
		fundSubsideSumInputDTO.setReportName("sedimentation_funds");
		fundSubsideSumInputDTO.setReportType("xls");
		fundSubsideSumInputDTO.setIssuerId(getUser().getEntityId());
		fundSubsideSumInputDTO.setIssuerName(getUser().getIssuerName());
		fundSubsideSumInputDTO.setReportFileName("资金沉淀表");
		return "list";
	}

	@Override
	protected JSONObject getJSONOBJect() {
		return JSONObject.fromObject(fundSubsideSumInputDTO);
	}

}

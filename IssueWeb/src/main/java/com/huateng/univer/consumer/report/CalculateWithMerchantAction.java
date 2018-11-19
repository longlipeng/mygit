package com.huateng.univer.consumer.report;

import net.sf.json.JSONObject;

import com.allinfinance.univer.report.dto.CalculateWithMerchantDTO;
import com.huateng.univer.report.action.NewIreportAction;

public class CalculateWithMerchantAction extends NewIreportAction {
	

	private static final long serialVersionUID = 6911509068002612132L;

	private CalculateWithMerchantDTO calculateWithMerchantDTO = new CalculateWithMerchantDTO();
	
	public CalculateWithMerchantDTO getCalculateWithMerchantDTO() {
		return calculateWithMerchantDTO;
	}

	public void setCalculateWithMerchantDTO(
			CalculateWithMerchantDTO calculateWithMerchantDTO) {
		this.calculateWithMerchantDTO = calculateWithMerchantDTO;
	}
	
	public String inQuery() throws Exception{
		calculateWithMerchantDTO.setReportName("calculate_with_merchant");
		calculateWithMerchantDTO.setReportType("xls");
		calculateWithMerchantDTO.setIssuerId(getUser().getEntityId());
		calculateWithMerchantDTO.setIssuerName(getUser().getConsumerName());
		calculateWithMerchantDTO.setReportFileName("商户运营汇总表");
		return "list";
	}

	@Override
	protected JSONObject getJSONOBJect() {
		return JSONObject.fromObject(calculateWithMerchantDTO);
	}	
}

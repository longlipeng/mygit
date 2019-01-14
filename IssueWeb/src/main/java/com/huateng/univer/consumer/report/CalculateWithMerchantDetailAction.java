package com.huateng.univer.consumer.report;

import net.sf.json.JSONObject;

import com.allinfinance.univer.report.dto.CalculateWithMerchantDetailDTO;
import com.huateng.univer.report.action.NewIreportAction;

public class CalculateWithMerchantDetailAction extends NewIreportAction {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6911509068002612132L;




	
	
	CalculateWithMerchantDetailDTO calculateWithMerchantDetailDTO=new CalculateWithMerchantDetailDTO();
	

	


	public CalculateWithMerchantDetailDTO getCalculateWithMerchantDetailDTO() {
		return calculateWithMerchantDetailDTO;
	}
	public void setCalculateWithMerchantDetailDTO(
			CalculateWithMerchantDetailDTO calculateWithMerchantDetailDTO) {
		this.calculateWithMerchantDetailDTO = calculateWithMerchantDetailDTO;
	}
	public String execute() throws Exception{
		if(getUser().getEntityId().equals("0"))
		{
			this.addActionError("褰撳墠涓轰竴涓彂鍗＄粍锛岃閫夋嫨涓�涓彂鍗℃満鏋�");
			return "error";
		}
		else
		{
			return "input";
		}
		
	}
	public String inQuery() throws Exception{
		calculateWithMerchantDetailDTO.setReportName("CalculateWithMerchantDetailReprt");
		calculateWithMerchantDetailDTO.setReportType("xls");
		calculateWithMerchantDetailDTO.setIssuerId(getUser().getEntityId());
		calculateWithMerchantDetailDTO.setIssuerName(getUser().getConsumerName());
		calculateWithMerchantDetailDTO.setReportFileName("商户运营明细表");
		return "list";
	}
	
	@Override
	protected JSONObject getJSONOBJect() {
		return JSONObject.fromObject(calculateWithMerchantDetailDTO);
	}
	
}

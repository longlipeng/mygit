package com.huateng.univer.seller.report;

import net.sf.json.JSONObject;

import com.allinfinance.univer.report.IreportDTO;
import com.allinfinance.univer.report.dto.SellerSellCardDetailDTO;
import com.huateng.univer.report.action.NewIreportAction;

public class tradeDetailAction extends NewIreportAction {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6911509068002612132L;
	
	SellerSellCardDetailDTO sellerSellCardDetailDTO=new SellerSellCardDetailDTO();



	public SellerSellCardDetailDTO getSellerSellCardDetailDTO() {
		return sellerSellCardDetailDTO;
	}
	public void setSellerSellCardDetailDTO(
			SellerSellCardDetailDTO sellerSellCardDetailDTO) {
		this.sellerSellCardDetailDTO = sellerSellCardDetailDTO;
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
		sellerSellCardDetailDTO.setReportName("tradeDetail");
		sellerSellCardDetailDTO.setReportType("xls");
		sellerSellCardDetailDTO.setIssuerId(getUser().getEntityId());
		sellerSellCardDetailDTO.setIssuerName(getUser().getSellerName());
		sellerSellCardDetailDTO.setReportFileName("交易明细报表");
		return "list";
	}
	
	@Override
	protected JSONObject getJSONOBJect() {
		return JSONObject.fromObject(sellerSellCardDetailDTO);
	}
}

package com.huateng.univer.seller.report;

import net.sf.json.JSONObject;

import com.allinfinance.univer.report.dto.SellerSellCardDetailDTO;
import com.huateng.univer.report.action.NewIreportAction;

public class SellCardAction extends NewIreportAction {
	
	
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
			this.addActionError("机构号");
			return "error";
		}
		else
		{
			return "input";
		}
		
	}
	public String inQuery() throws Exception{
		sellerSellCardDetailDTO.setReportName("sell_card_order");
		sellerSellCardDetailDTO.setReportType("xls");
		sellerSellCardDetailDTO.setIssuerId(getUser().getEntityId());
		sellerSellCardDetailDTO.setIssuerName(getUser().getSellerName());
		sellerSellCardDetailDTO.setReportFileName("售卡订单明细报表");
		return "list";
	}
	
	public String inQuerySellCardInfo() throws Exception{
		sellerSellCardDetailDTO.setReportName("sell_card_info");
		sellerSellCardDetailDTO.setReportType("xls");
		sellerSellCardDetailDTO.setIssuerId(getUser().getEntityId());
		sellerSellCardDetailDTO.setIssuerName(getUser().getSellerName());
		sellerSellCardDetailDTO.setReportFileName("售卡信息明细报表");
		return "listInfo";
	}
	
	@Override
	protected JSONObject getJSONOBJect() {
		return JSONObject.fromObject(sellerSellCardDetailDTO);
	}
}

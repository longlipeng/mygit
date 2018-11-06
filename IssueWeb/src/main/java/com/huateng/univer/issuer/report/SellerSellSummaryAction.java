package com.huateng.univer.issuer.report;

import net.sf.json.JSONObject;

import com.allinfinance.univer.report.dto.SellerSellSummaryDTO;
import com.huateng.univer.report.action.NewIreportAction;

/**
 * 营销运营汇总報表
 */
public class SellerSellSummaryAction extends NewIreportAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6911509068002612132L;

	private SellerSellSummaryDTO sellerSellSummaryDTO = new SellerSellSummaryDTO();

	public SellerSellSummaryDTO getSellerSellSummaryDTO() {
		return sellerSellSummaryDTO;
	}

	public void setSellerSellSummaryDTO(
			SellerSellSummaryDTO sellerSellSummaryDTO) {
		this.sellerSellSummaryDTO = sellerSellSummaryDTO;
	}

	public String inQuery() throws Exception {
		sellerSellSummaryDTO.setReportName("seller_sell_summary");
		sellerSellSummaryDTO.setReportType("xls");
		sellerSellSummaryDTO.setIssuerId(getUser().getEntityId());
		sellerSellSummaryDTO.setIssuerName(getUser().getIssuerName());
		sellerSellSummaryDTO.setReportFileName("营销运营汇总表");
		return "list";
	}

	@Override
	protected JSONObject getJSONOBJect() {
		return JSONObject.fromObject(sellerSellSummaryDTO);
	}

}

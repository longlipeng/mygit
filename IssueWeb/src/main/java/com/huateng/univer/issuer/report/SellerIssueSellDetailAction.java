package com.huateng.univer.issuer.report;

import net.sf.json.JSONObject;

import com.allinfinance.univer.report.dto.SellerIssueSellDetailDTO;
import com.huateng.univer.report.action.NewIreportAction;

/**
 * 营销机构——发行运营明细表
 */
public class SellerIssueSellDetailAction extends NewIreportAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SellerIssueSellDetailDTO sellerIssueSellDetailDTO = new SellerIssueSellDetailDTO();

	public String inQuery() throws Exception {
		sellerIssueSellDetailDTO.setReportName("issue_sell_detail");
		sellerIssueSellDetailDTO.setReportType("xls");
		sellerIssueSellDetailDTO.setSellerId(getUser().getEntityId());
		sellerIssueSellDetailDTO.setSellerName(getUser().getSellerName());
		sellerIssueSellDetailDTO.setReportFileName("发行运营汇明细表");
		return "list";
	}

	protected JSONObject getJSONOBJect() {
		sellerIssueSellDetailDTO.setNsellerId(sellerIssueSellDetailDTO.getSellerId());
		sellerIssueSellDetailDTO.setNsellerName(sellerIssueSellDetailDTO.getSellerName());
		return JSONObject.fromObject(sellerIssueSellDetailDTO);
	}


	public SellerIssueSellDetailDTO getSellerIssueSellDetailDTO() {
		return sellerIssueSellDetailDTO;
	}

	public void setSellerIssueSellDetailDTO(
			SellerIssueSellDetailDTO sellerIssueSellDetailDTO) {
		this.sellerIssueSellDetailDTO = sellerIssueSellDetailDTO;
	}
	
}

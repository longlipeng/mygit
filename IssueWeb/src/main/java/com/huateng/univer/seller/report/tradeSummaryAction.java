package com.huateng.univer.seller.report;

import net.sf.json.JSONObject;

import com.allinfinance.univer.report.dto.TradeSummaryDTO;
import com.huateng.univer.report.action.NewIreportAction;

public class tradeSummaryAction extends NewIreportAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer totalRows = 0;

	private TradeSummaryDTO tradeSummaryDTO = new TradeSummaryDTO();

	public String inQuery() throws Exception {
		tradeSummaryDTO.setReportName("tradeSummary");
		tradeSummaryDTO.setReportType("xls");
		tradeSummaryDTO.setIssuerId(getUser().getEntityId());
		tradeSummaryDTO.setIssuerName(getUser().getSellerName());
		tradeSummaryDTO.setReportFileName("交易汇总表");
		return "list";
	}

	@Override
	protected JSONObject getJSONOBJect() {
		return JSONObject.fromObject(tradeSummaryDTO);
	}

	public Integer getTotalRows() {

		return totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}

	public TradeSummaryDTO getTradeSummaryDTO() {
		return tradeSummaryDTO;
	}

	public void setTradeSummaryDTO(TradeSummaryDTO tradeSummaryDTO) {
		this.tradeSummaryDTO = tradeSummaryDTO;
	}

}

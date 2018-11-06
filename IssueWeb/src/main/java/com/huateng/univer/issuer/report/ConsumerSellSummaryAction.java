package com.huateng.univer.issuer.report;

import net.sf.json.JSONObject;

import com.allinfinance.univer.report.dto.ConsumerSellSummaryDTO;
import com.huateng.univer.report.action.NewIreportAction;

/**
 * 收单运营汇总報表
 */
public class ConsumerSellSummaryAction extends NewIreportAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6911509068002612132L;

	private Integer totalRows = 0;
	private ConsumerSellSummaryDTO consumerSellSummaryDTO = new ConsumerSellSummaryDTO();

	public ConsumerSellSummaryDTO getConsumerSellSummaryDTO() {
		return consumerSellSummaryDTO;
	}

	public void setConsumerSellSummaryDTO(
			ConsumerSellSummaryDTO consumerSellSummaryDTO) {
		this.consumerSellSummaryDTO = consumerSellSummaryDTO;
	}

	public Integer getTotalRows() {

		return totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}

	public String inQuery() throws Exception {
		consumerSellSummaryDTO.setReportName("consumer_sell_sum");
		consumerSellSummaryDTO.setReportType("xls");
		consumerSellSummaryDTO.setIssuerId(getUser().getEntityId());
		consumerSellSummaryDTO.setIssuerName(getUser().getIssuerName());
		consumerSellSummaryDTO.setReportFileName("收单运营汇总表");
		return "list";
	}

	@Override
	protected JSONObject getJSONOBJect() {
		return JSONObject.fromObject(consumerSellSummaryDTO);
	}

}

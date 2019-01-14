package com.huateng.univer.consumer.report;

import net.sf.json.JSONObject;

import com.allinfinance.univer.report.dto.ConsumerSellSummaryDTO;
import com.huateng.univer.report.action.NewIreportAction;

public class MonthSettleAction extends NewIreportAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ConsumerSellSummaryDTO consumerSellSummaryDTO=new ConsumerSellSummaryDTO();
	

	
	


	public ConsumerSellSummaryDTO getConsumerSellSummaryDTO() {
		return consumerSellSummaryDTO;
	}






	public void setConsumerSellSummaryDTO(
			ConsumerSellSummaryDTO consumerSellSummaryDTO) {
		this.consumerSellSummaryDTO = consumerSellSummaryDTO;
	}






	public String inQuery() throws Exception{
		consumerSellSummaryDTO.setReportName("settle_month");
		consumerSellSummaryDTO.setReportType("xls");
		consumerSellSummaryDTO.setIssuerId(getUser().getEntityId());
		consumerSellSummaryDTO.setIssuerName(getUser().getConsumerName());
		consumerSellSummaryDTO.setReportFileName("结算汇总表");
		return "list";
	}
	
	@Override
	protected JSONObject getJSONOBJect() {
		return JSONObject.fromObject(consumerSellSummaryDTO);
	}
}

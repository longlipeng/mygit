package com.huateng.univer.consumer.report;

import net.sf.json.JSONObject;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.consumer.ConsumerQueryDTO;
import com.allinfinance.univer.report.dto.IssuerSellSummaryDTO;
import com.huateng.univer.report.action.NewIreportAction;

public class IssuerSellSummaryAction extends NewIreportAction {
	
	private static final long serialVersionUID = 6911509068002612132L;
	
	private IssuerSellSummaryDTO issuerSellSummaryDTO = new IssuerSellSummaryDTO();
		
	public IssuerSellSummaryDTO getIssuerSellSummaryDTO() {
		return issuerSellSummaryDTO;
	}

	public void setIssuerSellSummaryDTO(IssuerSellSummaryDTO issuerSellSummaryDTO) {
		this.issuerSellSummaryDTO = issuerSellSummaryDTO;
	}

	public String inQuery() throws Exception{
		issuerSellSummaryDTO.setReportName("issuer_sell_summary");
		issuerSellSummaryDTO.setReportType("xls");
//		issuerSellSummaryDTO.setIssuerId(getUser().getEntityId());
//		issuerSellSummaryDTO.setIssuerName(getUser().getConsumerName());
		issuerSellSummaryDTO.setReportFileName("发行运营汇总表");
		return "list";
	}
	
	@Override
	protected JSONObject getJSONOBJect() {
		return JSONObject.fromObject(issuerSellSummaryDTO);
	}		
	
	private PageDataDTO pageDataDTO=new PageDataDTO();
	private ConsumerQueryDTO consumerQueryDTO=new ConsumerQueryDTO();
	private Integer totalRows=0;	

	public PageDataDTO getPageDataDTO() {
		return pageDataDTO;
	}

	public void setPageDataDTO(PageDataDTO pageDataDTO) {
		this.pageDataDTO = pageDataDTO;
	}

	public ConsumerQueryDTO getConsumerQueryDTO() {
		return consumerQueryDTO;
	}

	public void setConsumerQueryDTO(ConsumerQueryDTO consumerQueryDTO) {
		this.consumerQueryDTO = consumerQueryDTO;
	}

	public Integer getTotalRows() {
		
		return totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}

	public String issuerChoose() throws Exception {
		ListPageInit(null, consumerQueryDTO);
		consumerQueryDTO.setEntityId(getUser().getEntityId());
		pageDataDTO = (PageDataDTO) sendService(ConstCode.CONSUMER_SELECT_ISSUER, consumerQueryDTO).getDetailvo();
		if (null != pageDataDTO && pageDataDTO.getTotalRecord() > 0) {
			totalRows = pageDataDTO.getTotalRecord();
		} else {
			totalRows = 0;
		}
		return "list";
	}
}

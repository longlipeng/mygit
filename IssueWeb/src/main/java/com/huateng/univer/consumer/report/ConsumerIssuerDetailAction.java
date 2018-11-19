package com.huateng.univer.consumer.report;

import net.sf.json.JSONObject;

import com.allinfinance.univer.report.dto.ComsumerIssuerDetailDTO;
import com.huateng.univer.report.action.NewIreportAction;

public class ConsumerIssuerDetailAction extends NewIreportAction {
	
	private static final long serialVersionUID = 6911509068002612132L;
	
	private ComsumerIssuerDetailDTO comsumerIssuerDetailDTO = new ComsumerIssuerDetailDTO();
	
	public ComsumerIssuerDetailDTO getComsumerIssuerDetailDTO() {
		return comsumerIssuerDetailDTO;
	}

	public void setComsumerIssuerDetailDTO(
			ComsumerIssuerDetailDTO comsumerIssuerDetailDTO) {
		this.comsumerIssuerDetailDTO = comsumerIssuerDetailDTO;
	}

	public String inQuery() throws Exception{
		comsumerIssuerDetailDTO.setReportName("consumer_issuer_detail");
		comsumerIssuerDetailDTO.setReportType("xls");
		comsumerIssuerDetailDTO.setConsumerId(getUser().getEntityId());
		comsumerIssuerDetailDTO.setConsumerName(getUser().getConsumerName());
		comsumerIssuerDetailDTO.setReportFileName("发行运营明细表");
		return "list";
	}
	
	@Override
	protected JSONObject getJSONOBJect() {
		return JSONObject.fromObject(comsumerIssuerDetailDTO);
	}		
	

}

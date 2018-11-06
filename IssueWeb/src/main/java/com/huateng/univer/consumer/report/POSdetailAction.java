package com.huateng.univer.consumer.report;

import net.sf.json.JSONObject;

import com.allinfinance.univer.report.dto.PosDetailDTO;
import com.huateng.univer.report.action.NewIreportAction;

public class POSdetailAction extends NewIreportAction {	

	private static final long serialVersionUID = 6911509068002612132L;

	private PosDetailDTO posDetailDTO = new PosDetailDTO();
	
	public PosDetailDTO getPosDetailDTO() {
		return posDetailDTO;
	}

	public void setPosDetailDTO(PosDetailDTO posDetailDTO) {
		this.posDetailDTO = posDetailDTO;
	}

	public String inQuery() throws Exception{
		posDetailDTO.setReportName("pos_detail");
		posDetailDTO.setReportType("xls");
		posDetailDTO.setIssuerId(getUser().getEntityId());
		posDetailDTO.setIssuerName(getUser().getConsumerName());
		posDetailDTO.setReportFileName("POS机明细表");
		return "list";
	}
	
	@Override
	protected JSONObject getJSONOBJect() {
		return JSONObject.fromObject(posDetailDTO);
	}	

}

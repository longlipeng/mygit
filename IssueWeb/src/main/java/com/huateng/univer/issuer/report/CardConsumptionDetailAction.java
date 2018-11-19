/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: CardConsumptionDetailAction.java
 * Author:   zqs
 * Date:     2013-4-17 
 * Description:     
 * History: 
 * <author>      <time>      <version>    <desc>
 * zqs             2013-4-17        
 */
package com.huateng.univer.issuer.report;

import net.sf.json.JSONObject;



import com.allinfinance.univer.report.dto.CardConsumptionDetailDTO;
import com.huateng.univer.report.action.NewIreportAction;


/**
 *  卡消费明细报表中的action<br> 
 *  
 *
 * @author zqs
 * @see 
 * @since 
 */
public class CardConsumptionDetailAction extends NewIreportAction {
	
	private static final long serialVersionUID = 6911509068002612132L;

	private CardConsumptionDetailDTO cardConsumptionDetailDTO = new CardConsumptionDetailDTO();

	
	public String inQuery() throws Exception {
		cardConsumptionDetailDTO.setReportName("card_consumption_detail");
		cardConsumptionDetailDTO.setReportType("xls");
		cardConsumptionDetailDTO.setIssuerId(getUser().getEntityId());
		cardConsumptionDetailDTO.setIssuerName(getUser().getIssuerName());
		cardConsumptionDetailDTO.setReportFileName("卡消费明细表");
		return "list";
	}

	@Override
	protected JSONObject getJSONOBJect() {
		return JSONObject.fromObject(cardConsumptionDetailDTO);
	}

	public CardConsumptionDetailDTO getCardConsumptionDetailDTO() {
		return cardConsumptionDetailDTO;
	}

	public void setCardConsumptionDetailDTO(
			CardConsumptionDetailDTO cardConsumptionDetailDTO) {
		this.cardConsumptionDetailDTO = cardConsumptionDetailDTO;
	}


}

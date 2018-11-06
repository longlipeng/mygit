/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: ProvisionsBanlanceAction.java
 * Author:   zqs
 * Date:     2013-7-18 
 * Description:     
 * History: 
 * <author>      <time>      <version>    <desc>
 * zqs             2013-7-18        
 */
package com.huateng.univer.issuer.report;

import net.sf.json.JSONObject;

import com.allinfinance.univer.report.dto.ProvisionsBanlanceDTO;
import com.huateng.univer.report.action.NewIreportAction;

/**
 *  备付金余额查询报表的Action<br> 
 *  
 *
 * @author zqs
 * @see 
 * @since 
 */
public class ProvisionsBanlanceAction extends NewIreportAction {
    
	private static final long serialVersionUID = 6911509068002612132L;
    /**
     * 备付金余额报表中的DTO
     */
	private ProvisionsBanlanceDTO provisionsBanlanceDTO = new ProvisionsBanlanceDTO();
	/**
	 * 设置报表信息
	 */
	public String inQuery() throws Exception {
		provisionsBanlanceDTO.setReportName("provisions_banlance");
		provisionsBanlanceDTO.setReportType("xls");
		provisionsBanlanceDTO.setIssuerId(getUser().getEntityId());
		provisionsBanlanceDTO.setIssuerName(getUser().getIssuerName());
		provisionsBanlanceDTO.setReportFileName("备付金余额查询报表");
		return "list";
	}
	/**
	 * 得到dto的json值
	 */
	@Override
	protected JSONObject getJSONOBJect() {
		return JSONObject.fromObject(provisionsBanlanceDTO);
	}
    /**
     * 获取DTO信息
     */
	public ProvisionsBanlanceDTO getProvisionsBanlanceDTO() {
        return provisionsBanlanceDTO;
    }
	/**
	 * 设置DTO信息
	 */
    public void setProvisionsBanlanceDTO(ProvisionsBanlanceDTO provisionsBanlanceDTO) {
        this.provisionsBanlanceDTO = provisionsBanlanceDTO;
    }
}

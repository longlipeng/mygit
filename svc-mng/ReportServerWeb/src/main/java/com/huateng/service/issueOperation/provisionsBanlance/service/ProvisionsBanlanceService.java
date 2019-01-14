/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: ProvisionsBanlanceService.java
 * Author:   zqs
 * Date:     2013-7-18 
 * Description:     
 * History: 
 * <author>      <time>      <version>    <desc>
 * zqs             2013-7-18        
 */
package com.huateng.service.issueOperation.provisionsBanlance.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.allinfinance.univer.report.dto.ProvisionsBanlanceDTO;
import com.huateng.service.BizBaseService;
import com.huateng.service.BizService;


/**
 *  备付金余额查询报表的service<br> 
 *  
 *
 * @author zqs
 * @see 
 * @since 
 */
@SuppressWarnings("unchecked")
public class ProvisionsBanlanceService extends BizBaseService implements
		BizService {
    /**
     * 备付金余额报表的List,填充数据
     */
	public List<Object> getList(JSONObject jsonDto) {
		ProvisionsBanlanceDTO provisionsBanlanceDTO = (ProvisionsBanlanceDTO) JSONObject.toBean(
				jsonDto, ProvisionsBanlanceDTO.class);
		return baseDao.queryForList("provisions_banlance", "provisions_banlance", provisionsBanlanceDTO);
	}

}

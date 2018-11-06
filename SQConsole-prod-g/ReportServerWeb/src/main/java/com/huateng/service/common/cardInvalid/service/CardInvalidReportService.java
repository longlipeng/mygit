/**
 * Classname ConsumerSellSummaryService.java
 *
 * Version information
 *
 * Licensed Property to HuaTeng Data Co., Ltd.
 * 
 * (C) Copyright of HuaTeng Data Co., Ltd. 2010
 *     All Rights Reserved.
 *
 * Project Info: HuaTeng Data Internet Acquiring  Project
 * 
 * Modification History:
 * =============================================================================
 *		Author		Date		Description
 *   ------------ ---------- ---------------------------------------------------
 *		administrator		2012-10-23
 * =============================================================================
 */

package com.huateng.service.common.cardInvalid.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

import com.allinfinance.univer.report.dto.CardInvalidReportDTO;
import com.huateng.service.BizBaseService;
import com.huateng.service.BizService;


/**
 * @author gouhao
 * 
 */
public class CardInvalidReportService extends BizBaseService implements BizService {
    
    private Logger logger = Logger.getLogger(this.getClass());
    @SuppressWarnings("unchecked")
    @Override
    public List<Object> getList(JSONObject jsonDto) {
        List<Object> list = new ArrayList<Object>();
        try{
            CardInvalidReportDTO cardInvalidReportDTO = (CardInvalidReportDTO) JSONObject.toBean(jsonDto,
                    CardInvalidReportDTO.class);
            // 页面传入机构号为空，设置成当前机构
            if (null == cardInvalidReportDTO.getEntityId() || "".equals(cardInvalidReportDTO.getEntityId())) {
                cardInvalidReportDTO.setEntityId(cardInvalidReportDTO.getIssuerId());
            }
            list = baseDao.queryForList("card_invalid", "card_invalid", cardInvalidReportDTO);
        }catch(Exception e){
            this.logger.error(e.getMessage());
        }
        return list;
    }

}

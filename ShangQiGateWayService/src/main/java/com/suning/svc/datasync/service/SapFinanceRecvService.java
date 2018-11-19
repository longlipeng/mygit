/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: SapFinanceRecvService.java
 * Author:   12073942
 * Date:     2013-4-26 下午2:27:13
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.datasync.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.suning.svc.datasync.xmlbean.sap.finance.ReceiptBean;
import com.suning.svc.service.sap.info.SapInfoReceiptService;
import com.suning.svc.service.sap.info.dto.ReceiptDto;
import com.suning.svc.service.sap.info.result.ReceiptResult;

/**
 * 财务记账回执处理
 * 
 * @author yangtao
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class SapFinanceRecvService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SapFinanceRecvService.class);

    /**
     * 与SAP协商的表示成功的状态值
     */
    private static final String STATUS_SUCCESS = "S";

    /**
     * SAP记账回执处理服务
     */
    private SapInfoReceiptService sapInfoReceiptService;

    /**
     * 
     * 处理已转好的BodyBean
     * 
     * @param bodyBean
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public void processBodyBean(ReceiptBean bodyBean) {
        String status = bodyBean.getStatus();
        String error = bodyBean.getError();
        String transSeq = bodyBean.getTransSeq();
        if (StringUtils.equals(status, STATUS_SUCCESS)) {
            ReceiptDto receiptDto = new ReceiptDto();
            receiptDto.setTransSeq(transSeq);
            ReceiptResult rlt = sapInfoReceiptService.receiptConfirm(receiptDto);
            if (rlt.isSuccess()) {
                LOGGER.info("记账回执[{}]的处理成功", transSeq);
            } else {
                LOGGER.error("记账回执[{}]的处理失败", transSeq);
            }
        } else {
            LOGGER.error("SAP报告说流水号为{}的记账记录处理失败：{}", transSeq, error);
        }
    }

    /**
     * @param sapInfoReceiptService the sapInfoReceiptService to set
     */
    public void setSapInfoReceiptService(SapInfoReceiptService sapInfoReceiptService) {
        this.sapInfoReceiptService = sapInfoReceiptService;
    }

}

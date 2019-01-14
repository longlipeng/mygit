/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: ReceiptResult.java
 * Author:   12073942
 * Date:     2013-4-27 下午1:12:16
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.service.sap.info.result;

import com.suning.framework.lang.Result;
import com.suning.svc.service.sap.info.dto.ReceiptDto;

/**
 * 回执处理结果
 * 
 * @author 12073942
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ReceiptResult extends Result {

    /**
     */
    private static final long serialVersionUID = -4047838058843233071L;

    /**
     * 本次请求的DTO
     */
    private ReceiptDto receiptDto;

    public ReceiptDto getReceiptDto() {
        return receiptDto;
    }

    public void setReceiptDto(ReceiptDto receiptDto) {
        this.receiptDto = receiptDto;
    }

}

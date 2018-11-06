/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: ReceiptDto.java
 * Author:   12073942
 * Date:     2013-4-27 下午1:08:59
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.service.sap.info.dto;

import java.io.Serializable;

/**
 * SAP财务记账回执DTO
 * 
 * @author 12073942
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ReceiptDto implements Serializable {

    /**
     */
    private static final long serialVersionUID = 1761448355215018970L;

    /**
     * TRANS_SEQ 交易流水号
     */
    private String transSeq;

    public String getTransSeq() {
        return transSeq;
    }

    public void setTransSeq(String transSeq) {
        this.transSeq = transSeq;
    }

}

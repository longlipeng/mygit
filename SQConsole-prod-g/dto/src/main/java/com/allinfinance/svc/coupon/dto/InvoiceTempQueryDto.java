/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: invoiceTempQueryDto.java
 * Author:   13040446
 * Date:     2013-11-6 下午05:42:29
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.allinfinance.svc.coupon.dto;

import com.allinfinance.framework.dto.PageQueryDTO;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author 13040446
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class InvoiceTempQueryDto extends PageQueryDTO{
    /**
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 结算单ID
     */
    private Long settlementId;

    /**
     * @return the settlementId
     */
    public Long getSettlementId() {
        return settlementId;
    }

    /**
     * @param settlementId the settlementId to set
     */
    public void setSettlementId(Long settlementId) {
        this.settlementId = settlementId;
    }
}

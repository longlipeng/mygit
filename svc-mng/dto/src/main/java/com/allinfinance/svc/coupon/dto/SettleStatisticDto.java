/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: SettleStatisticDto.java
 * Author:   孙超
 * Date:     2013-10-30 下午08:45:35
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.allinfinance.svc.coupon.dto;

import java.util.Date;

/**
 * 结算统计出来的dto
 * 
 * @author 孙超
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class SettleStatisticDto {

    private long settleMoney;
    private String mchtNo;

    public long getSettleMoney() {
        return settleMoney;
    }

    public void setSettleMoney(long settleMoney) {
        this.settleMoney = settleMoney;
    }

    public String getMchtNo() {
        return mchtNo;
    }

    public void setMchtNo(String mchtNo) {
        this.mchtNo = mchtNo;
    }

}

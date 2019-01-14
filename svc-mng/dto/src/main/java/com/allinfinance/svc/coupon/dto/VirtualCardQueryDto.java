/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: VirtualCardQueryDto.java
 * Author:   孙超
 * Date:     2013-10-29 下午05:41:22
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.allinfinance.svc.coupon.dto;

import com.allinfinance.framework.dto.PageQueryDTO;

/**
 * 虚拟卡查询dto
 * 
 * @author 孙超
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class VirtualCardQueryDto extends PageQueryDTO {
    /**
     */
    private static final long serialVersionUID = 1L;
    private String cardNo;
    private String couponNo;
    private String fatherEntityId;
    private String status;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCouponNo() {
        return couponNo;
    }

    public void setCouponNo(String couponNo) {
        this.couponNo = couponNo;
    }

    public String getFatherEntityId() {
        return fatherEntityId;
    }

    public void setFatherEntityId(String fatherEntityId) {
        this.fatherEntityId = fatherEntityId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: SettlementListDto.java
 * Author:   孙超
 * Date:     2013-10-31 下午07:20:51
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.allinfinance.svc.coupon.dto;

import java.util.Date;

/**
 * 结算单显示dto
 * 
 * @author 孙超
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class SettlementListDto {

    private String settleNo;
    private String mchtCode;
    private Long totalAmount;
    private Long commissionAmount;
    private Long receivedInvoinceAmount;
    private Long waitIvcAmount;
    private String status;
    private Date createdTime;
    private Date updatedTime;

    public String getSettleNo() {
        return settleNo;
    }

    public void setSettleNo(String settleNo) {
        this.settleNo = settleNo;
    }

    public String getMchtCode() {
        return mchtCode;
    }

    public void setMchtCode(String mchtCode) {
        this.mchtCode = mchtCode;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getCommissionAmount() {
        return commissionAmount;
    }

    public void setCommissionAmount(Long commissionAmount) {
        this.commissionAmount = commissionAmount;
    }

    public Long getReceivedInvoinceAmount() {
        return receivedInvoinceAmount;
    }

    public void setReceivedInvoinceAmount(Long receivedInvoinceAmount) {
        this.receivedInvoinceAmount = receivedInvoinceAmount;
    }

    public Long getWaitIvcAmount() {
        return waitIvcAmount;
    }

    public void setWaitIvcAmount(Long waitIvcAmount) {
        this.waitIvcAmount = waitIvcAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

}

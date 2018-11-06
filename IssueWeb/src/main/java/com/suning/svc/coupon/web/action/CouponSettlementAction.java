/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: CouponSettlementAction.java
 * Author:   孙超
 * Date:     2013-10-31 下午08:50:41
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.web.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.svc.coupon.dto.DoSettleDto;
import com.allinfinance.svc.coupon.dto.SettlementQueryDto;
import com.huateng.framework.action.BaseAction;

/**
 * 零元购结算单
 * 
 * @author 孙超
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CouponSettlementAction extends BaseAction {

    private static final Logger log = LoggerFactory.getLogger(CouponSettlementAction.class);
    private SettlementQueryDto settlementQueryDto = new SettlementQueryDto();
    private List<?> settlementList = new ArrayList();
    private static final long serialVersionUID = 1L;
    private int settlement_totalRows = 0;

    private Long[] settlementId;

    private DoSettleDto doSettleDto;

    public String list() {
        try {
            settlementQueryDto.setFatherEntityId(this.getUser().getEntityId());
            ListPageInit("settlement", settlementQueryDto);
            PageDataDTO result = (PageDataDTO) sendService(ConstCode.COUPON_SETTLE_DETAIL, settlementQueryDto)
                    .getDetailvo();
            settlement_totalRows = result.getTotalRecord();
            settlementList = result.getData();
            if (hasErrors()) {
                return "input";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "list";
    }

    /** 跳转到结算页面 */
    public String toSettlementPage() {
        return "list";
    }

    /** 进行结算 */
    public String doSettlement() {
        log.info("开始结算" + new Date());
        try {
            doSettleDto.setFatherEntityId(this.getUser().getEntityId());
            sendService(ConstCode.DO_COUPON_SETTLEMENT, doSettleDto);
        } catch (Exception e) {
            addActionError(e.getMessage());
        }
        this.addActionMessage("正在结算中");
        return toSettlementPage();
    }

    /** 发送结算单信息到开放平台 */
    public String sendCheckedToSOP() {
        try {
            settlementQueryDto.setSettlementIds(this.settlementId);
            sendService(ConstCode.CHECKED_SETTLEMENT_TO_SOP, settlementQueryDto);
        } catch (Exception e) {
            log.error("发送所有结算单到开放平台出错" + e.getMessage());
        }
        return list();
    }

    public String sendAllToSOP() {
        try {
            sendService(ConstCode.SETTLEMENT_TO_SOP, settlementQueryDto);
        } catch (Exception e) {
            log.error("发送结算单到开放平台出错" + e.getMessage());
        }
        return list();
    }

    public String sendInvoiceAllToSOP() {
        try {
            sendService(ConstCode.SETTLEMENT_INVOICE_TO_SOP, settlementQueryDto);
        } catch (Exception e) {
            log.error("发送结算单开票信息到开放平台出错" + e.getMessage());
        }
        return list();
    }

    public String sendCheckedInvoiceToSOP() {
        try {
            settlementQueryDto.setSettlementIds(this.settlementId);
            sendService(ConstCode.CHECKED_SETTLEMENT_INVOICE_TO_SOP, settlementQueryDto);
        } catch (Exception e) {
            log.error("发送结算单开票到开放平台出错" + e.getMessage());
        }
        return list();
    }

    public SettlementQueryDto getSettlementQueryDto() {
        return settlementQueryDto;
    }

    public void setSettlementQueryDto(SettlementQueryDto settlementQueryDto) {
        this.settlementQueryDto = settlementQueryDto;
    }

    public List<?> getSettlementList() {
        return settlementList;
    }

    public void setSettlementList(List<?> settlementList) {
        this.settlementList = settlementList;
    }

    public int getSettlement_totalRows() {
        return settlement_totalRows;
    }

    public void setSettlement_totalRows(int settlement_totalRows) {
        this.settlement_totalRows = settlement_totalRows;
    }

    public DoSettleDto getDoSettleDto() {
        return doSettleDto;
    }

    public void setDoSettleDto(DoSettleDto doSettleDto) {
        this.doSettleDto = doSettleDto;
    }

    public Long[] getSettlementId() {
        return settlementId;
    }

    public void setSettlementId(Long[] settlementId) {
        this.settlementId = settlementId;
    }

}

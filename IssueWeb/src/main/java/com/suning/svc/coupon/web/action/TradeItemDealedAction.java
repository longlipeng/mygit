/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: TradeItemDealedAction.java
 * Author:   13040446
 * Date:     2013-11-6 下午02:29:43
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.svc.coupon.dto.TradeItemDealedQueryDto;
import com.huateng.framework.action.BaseAction;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author 13040446
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class TradeItemDealedAction extends BaseAction {
    private static final Logger log = LoggerFactory.getLogger(TradeItemDealedAction.class);
    private TradeItemDealedQueryDto tradeItemDealedQueryDto = new TradeItemDealedQueryDto();
    private List<?> tradeItemDealedList = new ArrayList();
    private static final long serialVersionUID = 1L;
    private int totalRows = 0;

    private Map<String, String> refOrderTypeMap = new HashMap<String, String>();
    private Map<String, String> statusMap = new HashMap<String, String>();

    public String list() {
        try {
            ListPageInit(null, tradeItemDealedQueryDto);
            //initMap();
            PageDataDTO pageDataDTO = (PageDataDTO) sendService(ConstCode.TRADE_ITEM_DEALED_QUERY, tradeItemDealedQueryDto)
                    .getDetailvo();
            tradeItemDealedList = pageDataDTO.getData();
            totalRows = pageDataDTO.getTotalRecord();
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return "list";
    }

    private void initMap() {
        refOrderTypeMap.put("1", "充值");
        refOrderTypeMap.put("2", "充退");
        refOrderTypeMap.put("3", "消费");

        statusMap.put("0", "待处理");
        statusMap.put("1", "处理失败");
        statusMap.put("2", "处理中");
        statusMap.put("3", "已处理");
    }

    /**
     * @return the tradeItemDealedQueryDto
     */
    public TradeItemDealedQueryDto getTradeItemDealedQueryDto() {
        return tradeItemDealedQueryDto;
    }

    /**
     * @param tradeItemDealedQueryDto the tradeItemDealedQueryDto to set
     */
    public void setTradeItemDealedQueryDto(TradeItemDealedQueryDto tradeItemDealedQueryDto) {
        this.tradeItemDealedQueryDto = tradeItemDealedQueryDto;
    }

    /**
     * @return the tradeItemDealedList
     */
    public List<?> getTradeItemDealedList() {
        return tradeItemDealedList;
    }

    /**
     * @param tradeItemDealedList the tradeItemDealedList to set
     */
    public void setTradeItemDealedList(List<?> tradeItemDealedList) {
        this.tradeItemDealedList = tradeItemDealedList;
    }


    /**
     * @return the totalRows
     */
    public int getTotalRows() {
        return totalRows;
    }

    /**
     * @param totalRows the totalRows to set
     */
    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    /**
     * @return the refOrderTypeMap
     */
    public Map<String, String> getRefOrderTypeMap() {
        return refOrderTypeMap;
    }

    /**
     * @param refOrderTypeMap the refOrderTypeMap to set
     */
    public void setRefOrderTypeMap(Map<String, String> refOrderTypeMap) {
        this.refOrderTypeMap = refOrderTypeMap;
    }

    /**
     * @return the statusMap
     */
    public Map<String, String> getStatusMap() {
        return statusMap;
    }

    /**
     * @param statusMap the statusMap to set
     */
    public void setStatusMap(Map<String, String> statusMap) {
        this.statusMap = statusMap;
    }

}

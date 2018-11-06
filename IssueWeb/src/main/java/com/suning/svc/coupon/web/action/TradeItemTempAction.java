/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: TradeItemTempAction.java
 * Author:   12073942
 * Date:     2013-10-30 下午9:47:10
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.web.action;

import org.apache.commons.lang3.StringUtils;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.OperationResult;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.svc.coupon.dto.TradeItemTempQueryDTO;
import com.huateng.framework.action.BaseAction;

/**
 * 接收数据临时表查询
 * 
 * @author LEO
 */
public class TradeItemTempAction extends BaseAction {

    /**
     */
    private static final long serialVersionUID = 1L;

    /**
     * 查询条件
     */
    private TradeItemTempQueryDTO tradeItemTempQueryDTO = new TradeItemTempQueryDTO();

    /**
     * 分页查询结果
     */
    private PageDataDTO pageDataDTO = new PageDataDTO();

    /**
     * 总行数
     */
    private Integer totalRows;

    private long id;

    /**
     * 查询条件验证
     */
    public void validateList() throws Exception {
        if (!StringUtils.isEmpty(tradeItemTempQueryDTO.getId())
                && !StringUtils.isNumeric(tradeItemTempQueryDTO.getId())) {
            addFieldError("tradeItemTempQueryDTO.id", "流水号必须输入数字");
            tradeItemTempQueryDTO.setId("");
            list();
        }
    }

    /**
     * 列表查询
     */
    public String list() throws Exception {
        ListPageInit(null, tradeItemTempQueryDTO);
        OperationResult rlt = sendService(ConstCode.TRADE_ITEMTMP_QUERY, tradeItemTempQueryDTO);
        pageDataDTO = (PageDataDTO) rlt.getDetailvo();
        if (null != pageDataDTO && pageDataDTO.getData().size() > 0) {
            totalRows = pageDataDTO.getTotalRecord();
        } else {
            totalRows = 0;
        }
        return "list";
    }

    /**
     * 
     */
    public String delete() throws Exception {
        sendService(ConstCode.TRADE_ITEMTMP_DELETE, id);
        return list();
    }

    /**
     * @return the tradeItemTempQueryDTO
     */
    public TradeItemTempQueryDTO getTradeItemTempQueryDTO() {
        return tradeItemTempQueryDTO;
    }

    /**
     * @param tradeItemTempQueryDTO the tradeItemTempQueryDTO to set
     */
    public void setTradeItemTempQueryDTO(TradeItemTempQueryDTO tradeItemTempQueryDTO) {
        this.tradeItemTempQueryDTO = tradeItemTempQueryDTO;
    }

    /**
     * @return the pageDataDTO
     */
    public PageDataDTO getPageDataDTO() {
        return pageDataDTO;
    }

    /**
     * @param pageDataDTO the pageDataDTO to set
     */
    public void setPageDataDTO(PageDataDTO pageDataDTO) {
        this.pageDataDTO = pageDataDTO;
    }

    /**
     * @return the totalRows
     */
    public Integer getTotalRows() {
        return totalRows;
    }

    /**
     * @param totalRows the totalRows to set
     */
    public void setTotalRows(Integer totalRows) {
        this.totalRows = totalRows;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

}

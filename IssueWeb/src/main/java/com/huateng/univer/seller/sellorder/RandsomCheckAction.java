/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: RandsomCheckAction.java
 * Author:   13010154
 * Date:     2013-8-8 下午02:06:22
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.univer.seller.sellorder;

import org.apache.log4j.Logger;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderOrigCardListDTO;
import com.huateng.framework.action.BaseAction;

/**
 *  
 * 验卡对话框
 * 通过卡号查询到卡号的信息
 *
 * @author 13010154
 */
public class RandsomCheckAction extends BaseAction{
    private static final long serialVersionUID = 8135141420334024603L;
    /**
     * 日志
     */
    private Logger logger = Logger.getLogger(RandsomCheckAction.class);
    /**
     * 查询信息DTO
     */
    private PageDataDTO pageDataDTO = new PageDataDTO();
    /**
     * 原卡信息表的DTO
     */
    private SellOrderOrigCardListDTO sellOrderOrigCardListDTO; 
    /**
     * 分页总行数
     */
    private int totalRows;
    
    /**
     * 点击验卡按钮跳转到查询页面 <br>
     *
     */
    public String ransomCheckCardQuery() {
        return "ransomCheckCardQuery";
    }
    
    /**
     * 
     * 根据卡号查询赎回卡的信息 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String checkCardQueryList() {
        try {
            ListPageInit(null, sellOrderOrigCardListDTO);
            sellOrderOrigCardListDTO.setEntityId(getUser().getEntityId());
            sellOrderOrigCardListDTO.setOperator(getUser().getUserId());
            pageDataDTO = (PageDataDTO) sendService(
                    ConstCode.RANSOM_CHECK_CARD_QUQERY,
                    sellOrderOrigCardListDTO).getDetailvo();
            totalRows = pageDataDTO.getTotalRecord(); 
        } catch (Exception e) {
            this.logger.error(e.getMessage()+"根据卡号查询赎回卡的信息 失败！");
        }
        return "list";
    }

    public PageDataDTO getPageDataDTO() {
        return pageDataDTO;
    }

    public void setPageDataDTO(PageDataDTO pageDataDTO) {
        this.pageDataDTO = pageDataDTO;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public SellOrderOrigCardListDTO getSellOrderOrigCardListDTO() {
        return sellOrderOrigCardListDTO;
    }

    public void setSellOrderOrigCardListDTO(SellOrderOrigCardListDTO sellOrderOrigCardListDTO) {
        this.sellOrderOrigCardListDTO = sellOrderOrigCardListDTO;
    }
    
}

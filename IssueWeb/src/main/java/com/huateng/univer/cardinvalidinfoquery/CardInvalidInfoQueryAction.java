/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: cardInvalidInfoQuery.java
 * Author:   Administrator
 * Date:     2013-11-7 下午02:26:11
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 苟昊             20131107            版本号                  描述
 */
package com.huateng.univer.cardinvalidinfoquery;

/**
 * 〈卡作废查询〉<br> 
 * 〈功能详细描述〉
 *
 * @author Administrator
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderQueryDTO;
import com.allinfinance.univer.seller.seller.dto.SellerQueryDTO;
import com.huateng.framework.action.BaseAction;

public class CardInvalidInfoQueryAction extends BaseAction {
    /**
     * 日志类.
     */
    private Logger logger = Logger.getLogger(CardInvalidInfoQueryAction.class);
    /**
     * 版本标记.
     */
    private static final long serialVersionUID = 1L;
    /**
     * 营销机构查询DTO.
     */
    private SellerQueryDTO sellerQueryDTO = new SellerQueryDTO();
    /**
     * 机构下拉框List.
     */
    private List<SellerQueryDTO> sellerQueryDTOs = new ArrayList<SellerQueryDTO>();
    /**
     * 订单查询DTO.
     */
    SellOrderQueryDTO sellOrderQueryDTO = new SellOrderQueryDTO();
    /**
     * 订单DTO.
     */
    SellOrderDTO sellOrderDTO = new SellOrderDTO();
    /**
     * 作废单卡明细List.
     */
    protected List<?> cardList = new ArrayList<Object>();
    /**
     * 卡列表信息总数.
     */
    protected int cardListInfo_totalRows = 0;
    /**
     * 卡作废信息.
     */
    protected List<?> sellOrders = new ArrayList<Object>();
    /**
     * 卡作废信息总数.
     */
    protected int cardInvalidQueryInfo_totalRows = 0;

    /**
     * 查询初始跳转<br>
     * . 获取机构下拉框信息
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @SuppressWarnings("unchecked")
    public String queryInit() {
        sellerQueryDTO.setEntityId(getUser().getEntityId());
        sellerQueryDTOs = (List<SellerQueryDTO>) sendService(ConstCode.SELLER_LIST_QUERY, sellerQueryDTO).getDetailvo();
        return "init";
    }

    /**
     * 查询卡作废信息<br>
     * . 〈功能详细描述〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String query() {
        try {
            ListPageInit("cardInvalidQueryInfo", sellOrderQueryDTO);
            /***
             * 按订单ID的倒序排序
             */
            if (isEmpty(sellOrderQueryDTO.getSortFieldName())) {
                sellOrderQueryDTO.setSort("desc");
                sellOrderQueryDTO.setSortFieldName("orderId");
            }
            // 查询机构为空，设置成当前机构
            if (isEmpty(sellOrderQueryDTO.getEntityId())) {
                sellOrderQueryDTO.setEntityId(getUser().getEntityId());
            }
            PageDataDTO result = (PageDataDTO) sendService(ConstCode.CARD_INVOICE_ORDER_QUERY, sellOrderQueryDTO)
                    .getDetailvo();
            sellOrders = result.getData();
            cardInvalidQueryInfo_totalRows = result.getTotalRecord();
            queryInit();
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        return "list";
    }

    /**
     * 
     * 查询卡作废信息明细<br>
     * . 〈功能详细描述〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String view() {
        try {
            if (isNotEmpty(sellOrderQueryDTO.getOrderId())) {
                // 获取作废单明细
                sellOrderDTO = (SellOrderDTO) sendService(ConstCode.CARD_INVOICE_DETAIL, sellOrderQueryDTO)
                        .getDetailvo();
                // 获取卡明细
                ListPageInit("cardListInfo", sellOrderQueryDTO);
                PageDataDTO result = (PageDataDTO) sendService(ConstCode.CARD_LIST_DETALL, sellOrderQueryDTO)
                        .getDetailvo();
                cardList = result.getData();
                cardListInfo_totalRows = result.getTotalRecord();

            } else {
                this.logger.error("后台获取订单号失败!");
            }

        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        return "view";
    }

    /**
     * @return the sellerQueryDTOs
     */
    public List<SellerQueryDTO> getSellerQueryDTOs() {
        return sellerQueryDTOs;
    }

    /**
     * @param sellerQueryDTOs the sellerQueryDTOs to set
     */
    public void setSellerQueryDTOs(List<SellerQueryDTO> sellerQueryDTOs) {
        this.sellerQueryDTOs = sellerQueryDTOs;
    }

    /**
     * @return the sellerQueryDTO
     */
    public SellerQueryDTO getSellerQueryDTO() {
        return sellerQueryDTO;
    }

    /**
     * @param sellerQueryDTO the sellerQueryDTO to set
     */
    public void setSellerQueryDTO(SellerQueryDTO sellerQueryDTO) {
        this.sellerQueryDTO = sellerQueryDTO;
    }

    /**
     * @return the sellOrderQueryDTO
     */
    public SellOrderQueryDTO getSellOrderQueryDTO() {
        return sellOrderQueryDTO;
    }

    /**
     * @param sellOrderQueryDTO the sellOrderQueryDTO to set
     */
    public void setSellOrderQueryDTO(SellOrderQueryDTO sellOrderQueryDTO) {
        this.sellOrderQueryDTO = sellOrderQueryDTO;
    }

    /**
     * @return the sellOrders
     */
    public List<?> getSellOrders() {
        return sellOrders;
    }

    /**
     * @param sellOrders the sellOrders to set
     */
    public void setSellOrders(List<?> sellOrders) {
        this.sellOrders = sellOrders;
    }

    /**
     * @return the sellOrderDTO
     */
    public SellOrderDTO getSellOrderDTO() {
        return sellOrderDTO;
    }

    /**
     * @param sellOrderDTO the sellOrderDTO to set
     */
    public void setSellOrderDTO(SellOrderDTO sellOrderDTO) {
        this.sellOrderDTO = sellOrderDTO;
    }

    /**
     * @return the cardList
     */
    public List<?> getCardList() {
        return cardList;
    }

    /**
     * @param cardList the cardList to set
     */
    public void setCardList(List<?> cardList) {
        this.cardList = cardList;
    }

    /**
     * @return the cardListInfo_totalRows
     */
    public int getCardListInfo_totalRows() {
        return cardListInfo_totalRows;
    }

    /**
     * @param cardListInfoTotalRows the cardListInfo_totalRows to set
     */
    public void setCardListInfo_totalRows(int cardListInfoTotalRows) {
        cardListInfo_totalRows = cardListInfoTotalRows;
    }

    /**
     * @return the cardInvalidQueryInfo_totalRows
     */
    public int getCardInvalidQueryInfo_totalRows() {
        return cardInvalidQueryInfo_totalRows;
    }

    /**
     * @param cardInvalidQueryInfoTotalRows the cardInvalidQueryInfo_totalRows to set
     */
    public void setCardInvalidQueryInfo_totalRows(int cardInvalidQueryInfoTotalRows) {
        cardInvalidQueryInfo_totalRows = cardInvalidQueryInfoTotalRows;
    }

}

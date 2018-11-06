/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: StockTransferOrderBaseAction.java
 * Author:   13071598
 * Date:     2013-10-29 下午02:59:19
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.univer.seller.stockTransferOrder;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.univer.seller.order.dto.OrderReceiveCardListQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderCardListQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderCompositeDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderFlowQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderListQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderQueryDTO;
import com.huateng.framework.action.BaseAction;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.extremecomponents.table.core.TableConstants;

/**
 * 〈一句话功能简述〉<br>
 * 调拨订单基类
 * 
 * @author 13071598
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public abstract class StockTransferOrderBaseAction extends BaseAction {

    /**
     * 日志
     */
    private Logger logger = Logger.getLogger(StockTransferOrderBaseAction.class);
    private static final long serialVersionUID = -93953275889476786L;
    /**
     * action类名
     */
    private String actionName;
    /**
     * action方法名
     */
    private String actionMethodName;
    /**
     * 多个复选框数组
     */
    protected String[] ec_choose;
    /**
     * 订单查询DTO
     */
    protected SellOrderQueryDTO sellOrderQueryDTO = new SellOrderQueryDTO();
    /**
     * 调拨订单list
     */
    @SuppressWarnings("unchecked")
    protected List<?> stockTransferOrders = new ArrayList();
    /**
     * 调拨订单明细list
     */
    @SuppressWarnings("unchecked")
    protected List<?> stockTransferOrderList = new ArrayList();
    /**
     * 订单总记录数
     */
    protected int sellOrder_totalRows = 0;
    /**
     * 调拨订单未接收卡明细list
     */
    @SuppressWarnings("unchecked")
    private List<?> stockTransferOrderOriCardListDTOs = new ArrayList();
    /**
     * 调拨订单未接收卡明细总记录数
     */
    private int stockTransferOrderOriCardListRow_totalRows = 0;
    /**
     * 调拨订单已接收卡明细list
     */
    @SuppressWarnings("unchecked")
    private List<?> stockTransferOrderRaceCardListDTOs = new ArrayList();
    /**
     * 调拨订单已接收卡明细总记录数
     */
    private int stockTransferOrderRaceCardListRow_totalRows = 0;
    /**
     * 调拨订单明细总记录数
     */
    protected int stockTransferOrderList_totalRows = 0;
    /**
     * 订单DTO
     */
    protected SellOrderDTO sellOrderDTO = new SellOrderDTO();
    /**
     * 订单明细查询DTO
     */
    protected SellOrderListQueryDTO sellOrderListQueryDTO = new SellOrderListQueryDTO();
    /**
     * 订单流程查询dto
     */
    protected SellOrderFlowQueryDTO sellOrderFlowQueryDTO = new SellOrderFlowQueryDTO();
    /**
     * 订单卡明细查询DTO
     */
    protected SellOrderCardListQueryDTO sellOrderCardListQueryDTO = new SellOrderCardListQueryDTO();
    /**
     * 接收卡明细查询DTO
     */
    private OrderReceiveCardListQueryDTO orderReceiveCardListQueryDTO = new OrderReceiveCardListQueryDTO();
    /**
     * 订单录入DTO
     */
    protected SellOrderInputDTO sellOrderInputDTO = new SellOrderInputDTO();

    
    
    protected abstract void init();

    /**
     * 
     * 功能描述: <br>
     * 查询订单详细信息
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String view() {

        try {
            actionName = "stockTransferOrderInputAction";
            
            actionMethodName = "view";
            
            ListPageInit("stockTransferOrderList", sellOrderListQueryDTO);

            ListPageInit("stockTransferOrderOriCardListRow",sellOrderCardListQueryDTO);
            
            ListPageInit("stockTransferOrderRaceCardListRow", orderReceiveCardListQueryDTO);
            
            ListPageInit("orderFlow", sellOrderFlowQueryDTO);

            SellOrderCompositeDTO sellOrderCompositeDTO = new SellOrderCompositeDTO();

            // 订单
            sellOrderCompositeDTO.setSellOrderDTO(sellOrderDTO);

            // 订单明细
            sellOrderListQueryDTO.setOrderId(sellOrderDTO.getOrderId());
            sellOrderCompositeDTO.setSellOrderListQueryDTO(sellOrderListQueryDTO);

            // 订单未接收卡明细
            sellOrderCardListQueryDTO.setOrderId(sellOrderDTO.getOrderId());
            sellOrderCompositeDTO.setSellOrderCardListQueryDTO(sellOrderCardListQueryDTO);

            //已接收卡明细
            orderReceiveCardListQueryDTO.setOrderId(sellOrderDTO.getOrderId());
            sellOrderCompositeDTO.setOrderReceiveCardListQueryDTO(orderReceiveCardListQueryDTO);
            // 订单流程
            sellOrderFlowQueryDTO.setOrderId(sellOrderDTO.getOrderId());
            sellOrderCompositeDTO.setSellOrderFlowQueryDTO(sellOrderFlowQueryDTO);

            SellOrderCompositeDTO result = (SellOrderCompositeDTO) sendService(ConstCode.VIEW_STOCK_TRANSFER_ORDER,
                    sellOrderCompositeDTO).getDetailvo();

            //订单信息
            sellOrderDTO = result.getSellOrderDTO();

            // sellOrderDTO.setFirstEntityName(new
            // String(sellOrderDTO.getFirstEntityName().getBytes("iso8859-1"),"utf-8"));
            // sellOrderDTO.setProcessEntityName(new
            // String(sellOrderDTO.getProcessEntityName().getBytes("ISO8859-1"),"UTF-8"));

            //订单明细信息
            stockTransferOrderList = result.getSellOrderList().getData();
            stockTransferOrderList_totalRows = result.getSellOrderList().getTotalRecord();

            //订单未接收卡明细信息
            stockTransferOrderOriCardListDTOs = result.getSellOrderOrigCardList().getData();
            stockTransferOrderOriCardListRow_totalRows = result.getSellOrderOrigCardList().getTotalRecord();

            //订单已接收卡明细信息
            stockTransferOrderRaceCardListDTOs = result.getSellOrderReceCardList().getData();
            stockTransferOrderRaceCardListRow_totalRows = result.getSellOrderReceCardList().getTotalRecord();
            
            getRequest().setAttribute("orderflowList", result.getSellOrderFlowList().getData());
            getRequest().setAttribute("orderFlow_" + TableConstants.TOTAL_ROWS,
                    result.getSellOrderFlowList().getTotalRecord());

        } catch (Exception e) {
            this.logger.error(e);
            e.printStackTrace();
        }
        return "view";
    }

    
    
    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getActionMethodName() {
        return actionMethodName;
    }

    public void setActionMethodName(String actionMethodName) {
        this.actionMethodName = actionMethodName;
    }

    public SellOrderQueryDTO getSellOrderQueryDTO() {
        return sellOrderQueryDTO;
    }

    public void setSellOrderQueryDTO(SellOrderQueryDTO sellOrderQueryDTO) {
        this.sellOrderQueryDTO = sellOrderQueryDTO;
    }

    public List<?> getStockTransferOrders() {
        return stockTransferOrders;
    }

    public void setStockTransferOrders(List<?> stockTransferOrders) {
        this.stockTransferOrders = stockTransferOrders;
    }

    public int getSellOrder_totalRows() {
        return sellOrder_totalRows;
    }

    public void setSellOrder_totalRows(int sellOrderTotalRows) {
        sellOrder_totalRows = sellOrderTotalRows;
    }

    public SellOrderDTO getSellOrderDTO() {
        return sellOrderDTO;
    }

    public void setSellOrderDTO(SellOrderDTO sellOrderDTO) {
        this.sellOrderDTO = sellOrderDTO;
    }

    public List<?> getStockTransferOrderList() {
        return stockTransferOrderList;
    }

    public void setStockTransferOrderList(List<?> stockTransferOrderList) {
        this.stockTransferOrderList = stockTransferOrderList;
    }

    public int getStockTransferOrderList_totalRows() {
        return stockTransferOrderList_totalRows;
    }

    public void setStockTransferOrderList_totalRows(int stockTransferOrderListTotalRows) {
        stockTransferOrderList_totalRows = stockTransferOrderListTotalRows;
    }

    public SellOrderListQueryDTO getSellOrderListQueryDTO() {
        return sellOrderListQueryDTO;
    }

    public void setSellOrderListQueryDTO(SellOrderListQueryDTO sellOrderListQueryDTO) {
        this.sellOrderListQueryDTO = sellOrderListQueryDTO;
    }

    public SellOrderFlowQueryDTO getSellOrderFlowQueryDTO() {
        return sellOrderFlowQueryDTO;
    }

    public void setSellOrderFlowQueryDTO(SellOrderFlowQueryDTO sellOrderFlowQueryDTO) {
        this.sellOrderFlowQueryDTO = sellOrderFlowQueryDTO;
    }

    public String[] getEc_choose() {
        return ec_choose;
    }

    public void setEc_choose(String[] ecChoose) {
        ec_choose = ecChoose;
    }

    public SellOrderInputDTO getSellOrderInputDTO() {
        return sellOrderInputDTO;
    }

    public void setSellOrderInputDTO(SellOrderInputDTO sellOrderInputDTO) {
        this.sellOrderInputDTO = sellOrderInputDTO;
    }

    

    public List<?> getStockTransferOrderOriCardListDTOs() {
        return stockTransferOrderOriCardListDTOs;
    }

    public void setStockTransferOrderOriCardListDTOs(List<?> stockTransferOrderOriCardListDTOs) {
        this.stockTransferOrderOriCardListDTOs = stockTransferOrderOriCardListDTOs;
    }

    public int getStockTransferOrderOriCardListRow_totalRows() {
        return stockTransferOrderOriCardListRow_totalRows;
    }

    public void setStockTransferOrderOriCardListRow_totalRows(int stockTransferOrderOriCardListRowTotalRows) {
        stockTransferOrderOriCardListRow_totalRows = stockTransferOrderOriCardListRowTotalRows;
    }

    public List<?> getStockTransferOrderRaceCardListDTOs() {
        return stockTransferOrderRaceCardListDTOs;
    }

    public void setStockTransferOrderRaceCardListDTOs(List<?> stockTransferOrderRaceCardListDTOs) {
        this.stockTransferOrderRaceCardListDTOs = stockTransferOrderRaceCardListDTOs;
    }

    public int getStockTransferOrderRaceCardListRow_totalRows() {
        return stockTransferOrderRaceCardListRow_totalRows;
    }

    public void setStockTransferOrderRaceCardListRow_totalRows(int stockTransferOrderRaceCardListRowTotalRows) {
        stockTransferOrderRaceCardListRow_totalRows = stockTransferOrderRaceCardListRowTotalRows;
    }

    public SellOrderCardListQueryDTO getSellOrderCardListQueryDTO() {
        return sellOrderCardListQueryDTO;
    }

    public void setSellOrderCardListQueryDTO(SellOrderCardListQueryDTO sellOrderCardListQueryDTO) {
        this.sellOrderCardListQueryDTO = sellOrderCardListQueryDTO;
    }

}

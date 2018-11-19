/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: StockTransferOrderReadyAction.java
 * Author:   13071598
 * Date:     2013-10-30 下午03:13:34
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.univer.seller.stockTransferOrder;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.order.dto.OrderReadyDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderCardListQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderListQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderReadyQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderReadyResultDTO;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.constant.SystemInfoConstants;
import com.huateng.framework.util.SystemInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * 〈一句话功能简述〉<br>
 * 调拨订单出库action
 * 
 * @author 13071598
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class StockTransferOrderReadyAction extends StockTransferOrderBaseAction {

    /**
     */
    private static final long serialVersionUID = -4089213053481519584L;
    /**
     *日志 
     */
    private Logger logger = Logger.getLogger(StockTransferOrderReadyAction.class);
    /**
     * 订单明细查询DTO
     */
    private SellOrderListQueryDTO orderListQueryDTO = new SellOrderListQueryDTO();
    /**
     * 订单卡明细查询DTO
     */
    private SellOrderCardListQueryDTO orderCardListQueryDTO = new SellOrderCardListQueryDTO();
    /**
     * 订单准备查询DTO
     */
    private SellOrderReadyQueryDTO orderReadyQueryDTO = new SellOrderReadyQueryDTO();
    /**
     * 订单准备结果DTO
     */
    private SellOrderReadyResultDTO orderReadyResultDTO = new SellOrderReadyResultDTO();
    /**
     * 订单准备DTO
     */
    private OrderReadyDTO orderReadyDTO = new OrderReadyDTO();
    /**
     * 获取库存卡DTO
     */
    private PageDataDTO pageDataDTO;
    /**
     * 多个卡号数组
     */
    private String[] cardNoArray;
    /**
     * 订单明细list
     */
    @SuppressWarnings("unchecked")
    private List<?> orderListDTOs = new ArrayList();
    /**
     * 订单明细总记录数
     */
    private int orderListRow_totalRows = 0;
    /**
     * 订单卡明细list
     */
    @SuppressWarnings("unchecked")
    private List<?> orderCardListDTOs = new ArrayList();
    /**
     * 订单卡明细总记录数
     */
    private int orderCardListRow_totalRows = 0;

    /*
     * (non-Javadoc)
     * @see com.huateng.univer.seller.stockTransferOrder.StockTransferOrderBaseAction#init()
     */
    @Override
    protected void init() {

    }

    /**
     * 
     * 功能描述: <br>
     * 初始化主界面
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String list() {
        try {
            ListPageInit("sellOrder", sellOrderQueryDTO);
            // sellOrderQueryDTO.setOrderState(OrderConst.ORDER_STATE_DRAFT);
            sellOrderQueryDTO.setEntityId(getUser().getEntityId());
            sellOrderQueryDTO.setOrderState(OrderConst.STOCK_TRANSFER_ORDER_READY);
            /***
             * 按订单ID的倒序排序
             */
            if (isEmpty(sellOrderQueryDTO.getSortFieldName())) {
                sellOrderQueryDTO.setSort("desc");
                sellOrderQueryDTO.setSortFieldName("orderId");
            }
            PageDataDTO result = (PageDataDTO) sendService(ConstCode.STOCK_TRANSFER_ORDER_QUERY_AT_READY,
                    sellOrderQueryDTO).getDetailvo();
            stockTransferOrders = result.getData();
            sellOrder_totalRows = result.getTotalRecord();
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        return "list";
    }

    /**
     * 
     * 功能描述: <br>
     * 进入准备页面，查看订单，订单明细，订单卡明细
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String ready() {
        try {
            orderListQueryDTO.setOrderId(sellOrderDTO.getOrderId());
            ListPageInit(null, orderListQueryDTO);
            if (orderListQueryDTO.isQueryAll()) {
                orderListQueryDTO.setQueryAll(false);
                orderListQueryDTO.setRowsDisplayed(Integer.parseInt(SystemInfo
                        .getParameterValue(SystemInfoConstants.EXPORT_DATA_MAXIMUM)));
            }

            orderCardListQueryDTO.setOrderId(sellOrderDTO.getOrderId());
            ListPageInit("orderCardListRow", orderCardListQueryDTO);
            if (orderCardListQueryDTO.isQueryAll()) {
                orderCardListQueryDTO.setQueryAll(false);
                orderCardListQueryDTO.setRowsDisplayed(Integer.parseInt(SystemInfo
                        .getParameterValue(SystemInfoConstants.EXPORT_DATA_MAXIMUM)));
            }

            orderReadyQueryDTO.setOrderListQueryDTO(orderListQueryDTO);
            orderReadyQueryDTO.setOrderCardListQueryDTO(orderCardListQueryDTO);

            orderReadyResultDTO = (SellOrderReadyResultDTO) this.sendService(ConstCode.STOCK_TRANSFER_ORDER_READY,
                    orderReadyQueryDTO).getDetailvo();

            sellOrderDTO = orderReadyResultDTO.getSellOrderDTO();

            orderListDTOs = orderReadyResultDTO.getOrderLists().getData();
            orderListRow_totalRows = orderListDTOs.size();

            orderCardListDTOs = orderReadyResultDTO.getOrderCardLists().getData();
            orderCardListRow_totalRows = orderReadyResultDTO.getOrderCardLists().getTotalRecord();
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        return "ready";
    }

    /**
     * 
     * 功能描述: <br>
     * 开始准备卡
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @SuppressWarnings("unchecked")
    public String cardReady() {
        try {
            ListPageInit("cardStock", orderReadyDTO);
            pageDataDTO = (PageDataDTO) sendService(ConstCode.QUERY_CARD_FOR_READY, orderReadyDTO).getDetailvo();
            if (pageDataDTO != null && pageDataDTO.getData() != null && pageDataDTO.getData().size() > 0) {
                List<HashMap<String, String>> lstStockList = (List<HashMap<String, String>>) pageDataDTO.getData();
                orderReadyDTO.setStartCardNo(lstStockList.get(0).get("min"));
                orderReadyDTO.setEndCardNo(lstStockList.get(0).get("max"));
                getRequest().setAttribute("totalRows", pageDataDTO.getTotalRecord());
            } else {
                getRequest().setAttribute("totalRows", 0);
            }

        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        return "cardList";
    }

    /**
     * 
     * 功能描述: <br>
     * 准备选中的卡号
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String cardArrayReady() {
        try {

            orderReadyDTO.setCardNoArray(cardNoArray);
            orderReadyDTO.setFirstEntityId(getUser().getEntityId());
            String startCardNoString = orderReadyDTO.getStartCardNo();
            String endCardNoString = orderReadyDTO.getEndCardNo();
            orderReadyDTO.setStartCardNo("");
            orderReadyDTO.setEndCardNo("");
            sendService(ConstCode.STOCK_TRANSFER_ORDER_READY_BY_CARD, orderReadyDTO).getDetailvo();
            if (this.hasErrors()) {
                orderReadyDTO.setStartCardNo(startCardNoString);
                orderReadyDTO.setEndCardNo(endCardNoString);
                return cardReady();
            }

            if (!this.hasErrors()) {
                getRequest().setAttribute("orderId", orderReadyDTO.getOrderId());
                getRequest().setAttribute("sucessMessage", "准备成功!");
            }
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        return "readySuccess";
    }
    
    /**
     * 
     * 功能描述: <br>
     * 全部准备
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String cardAllReady() {
        try {
            cardNoArray = null;
            orderReadyDTO.setCardNoArray(cardNoArray);
            orderReadyDTO.setFirstEntityId(getUser().getEntityId());
            sendService(ConstCode.STOCK_TRANSFER_ORDER_READY_ALL_CARD, orderReadyDTO).getDetailvo();
            if (this.hasErrors()) {
                return cardReady();
            }

            if (!this.hasErrors()) {
                getRequest().setAttribute("orderId", orderReadyDTO.getOrderId());
                getRequest().setAttribute("sucessMessage", "准备成功!");
            }

        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        return "readySuccess";
    }

    /**
     * 
     * 功能描述: <br>
     * 删除一条卡明细
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String deleteCheckedRecord() {
        try {
            sendService(ConstCode.DELETE_CHECKED_READY_RECORD, orderReadyDTO).getDetailvo();
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        return ready();
    }

    /**
     * 
     * 功能描述: <br>
     * 删除所有卡明细
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String deleteAllRecord() {
        try {
            sendService(ConstCode.DELETE_ALL_READY_RECORD, sellOrderDTO).getDetailvo();
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        return ready();
    }

    /**
     * 
     * 功能描述: <br>
     * 提交订单
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String submitOrderReady() {
        try {
            sendService(ConstCode.SUBMIT_STOCK_TRANSFER_ORDER_AT_READY, sellOrderDTO).getDetailvo();
            if (!this.hasErrors()) {
                this.addActionMessage("提交订单成功!");
            }

        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        return list();
    }

    /**
     * 
     * 功能描述: <br>
     * 进入订单退回页面
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String sendBackOrder() {
        try {
            ready();
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        return "sendBack";
    }

    /**
     * 
     * 功能描述: <br>
     * 退回订单
     *
     * @return
     * @throws Exception
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String sendBackOrderSubmit() throws Exception {
        try {
            this.sendService(ConstCode.STOCK_TRANSFER_ORDER_SENDBACK, sellOrderDTO);
            if (!hasErrors()) {
                addActionMessage("调拨订单退回成功！");
            }
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        return list();
    }

    public SellOrderListQueryDTO getOrderListQueryDTO() {
        return orderListQueryDTO;
    }

    public void setOrderListQueryDTO(SellOrderListQueryDTO orderListQueryDTO) {
        this.orderListQueryDTO = orderListQueryDTO;
    }

    public SellOrderCardListQueryDTO getOrderCardListQueryDTO() {
        return orderCardListQueryDTO;
    }

    public void setOrderCardListQueryDTO(SellOrderCardListQueryDTO orderCardListQueryDTO) {
        this.orderCardListQueryDTO = orderCardListQueryDTO;
    }

    public SellOrderReadyQueryDTO getOrderReadyQueryDTO() {
        return orderReadyQueryDTO;
    }

    public void setOrderReadyQueryDTO(SellOrderReadyQueryDTO orderReadyQueryDTO) {
        this.orderReadyQueryDTO = orderReadyQueryDTO;
    }

    public SellOrderReadyResultDTO getOrderReadyResultDTO() {
        return orderReadyResultDTO;
    }

    public void setOrderReadyResultDTO(SellOrderReadyResultDTO orderReadyResultDTO) {
        this.orderReadyResultDTO = orderReadyResultDTO;
    }

    public List<?> getOrderListDTOs() {
        return orderListDTOs;
    }

    public void setOrderListDTOs(List<?> orderListDTOs) {
        this.orderListDTOs = orderListDTOs;
    }

    public int getOrderListRow_totalRows() {
        return orderListRow_totalRows;
    }

    public void setOrderListRow_totalRows(int orderListRowTotalRows) {
        orderListRow_totalRows = orderListRowTotalRows;
    }

    public List<?> getOrderCardListDTOs() {
        return orderCardListDTOs;
    }

    public void setOrderCardListDTOs(List<?> orderCardListDTOs) {
        this.orderCardListDTOs = orderCardListDTOs;
    }

    public int getOrderCardListRow_totalRows() {
        return orderCardListRow_totalRows;
    }

    public void setOrderCardListRow_totalRows(int orderCardListRowTotalRows) {
        orderCardListRow_totalRows = orderCardListRowTotalRows;
    }

    public OrderReadyDTO getOrderReadyDTO() {
        return orderReadyDTO;
    }

    public void setOrderReadyDTO(OrderReadyDTO orderReadyDTO) {
        this.orderReadyDTO = orderReadyDTO;
    }

    public PageDataDTO getPageDataDTO() {
        return pageDataDTO;
    }

    public void setPageDataDTO(PageDataDTO pageDataDTO) {
        this.pageDataDTO = pageDataDTO;
    }

    public String[] getCardNoArray() {
        return cardNoArray;
    }

    public void setCardNoArray(String[] cardNoArray) {
        this.cardNoArray = cardNoArray;
    }

}

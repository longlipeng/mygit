/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: StockTransferOrderReadyServiceImpl.java
 * Author:   13071598
 * Date:     2013-11-7 下午03:39:22
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.univer.seller.stockTransferOrder.service.impl;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.order.dto.OrderReadyDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderCardListQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderListQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderReadyQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderReadyResultDTO;
import com.huateng.framework.constant.Const;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.model.EntityStock;
import com.huateng.framework.ibatis.model.SellOrder;
import com.huateng.framework.ibatis.model.SellOrderCardList;
import com.huateng.framework.ibatis.model.SellOrderList;
import com.huateng.framework.ibatis.model.SellOrderListExample;
import com.huateng.framework.util.DateUtil;
import com.huateng.univer.entitystock.service.EntityStockService;
import com.huateng.univer.order.business.bo.OrderBO;
import com.huateng.univer.order.business.bo.OrderBaseQueryBO;
import com.huateng.univer.seller.stockTransferOrder.service.StockTransferOrderReadyService;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * 库存调拨订单出库service<br>
 * 
 * 
 * @author 13071598
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class StockTransferOrderReadyServiceImpl implements StockTransferOrderReadyService {

    Logger logger = Logger.getLogger(StockTransferOrderReadyServiceImpl.class);

    private PageQueryDAO pageQueryDAO;
    private BaseDAO baseDAO;
    private OrderBaseQueryBO orderBaseQueryBO;
    private OrderBO orderBO;

    private EntityStockService entityStockService;

    /**
     * 
     * 功能描述: <br>
     * 查询待出库状态下调拨订单
     *
     * @param sellOrderQueryDTO
     * @return
     * @throws BizServiceException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @Override
    public PageDataDTO queryStockTransferOrderAtReady(SellOrderQueryDTO sellOrderQueryDTO) throws BizServiceException {
        try {
            return pageQueryDAO.query("STOCKTRANSFERORDER.queryStockTransferOrderAtReady", sellOrderQueryDTO);
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("查询库存调拨订单失败~！");
        }
    }

    /**
     * 
     * 功能描述: <br>
     * 进入准备页面查询订单，订单明细，订单卡明细信息
     *
     * @param orderReadyQueryDTO
     * @return
     * @throws BizServiceException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public SellOrderReadyResultDTO queryForReady(SellOrderReadyQueryDTO orderReadyQueryDTO) throws BizServiceException {
        SellOrderReadyResultDTO orderReadyDTO = new SellOrderReadyResultDTO();
        try {
            SellOrderListQueryDTO orderListQueryDTO = orderReadyQueryDTO.getOrderListQueryDTO();
            SellOrderCardListQueryDTO orderCardListQueryDTO = orderReadyQueryDTO.getOrderCardListQueryDTO();
            SellOrderDTO sellOrderDTO = new SellOrderDTO();
            sellOrderDTO.setOrderId(orderListQueryDTO.getOrderId());
            sellOrderDTO = (SellOrderDTO) baseDAO.queryForObject("STOCKTRANSFERORDER", "getStockTransferOrderMsg",
                    sellOrderDTO);

            PageDataDTO orderLists = pageQueryDAO.query("STOCKTRANSFERORDER.getStockTransferOrderList",
                    orderListQueryDTO);
            PageDataDTO orderCardLists = pageQueryDAO.query("STOCKTRANSFERORDER.getStockTransferOrderCardList",
                    orderCardListQueryDTO);

            orderReadyDTO.setSellOrderDTO(sellOrderDTO);
            orderReadyDTO.setOrderLists(orderLists);
            orderReadyDTO.setOrderCardLists(orderCardLists);

        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("查询订单明细失败！");
        }
        return orderReadyDTO;
    }

    /**
     * 
     * 功能描述: <br>
     * 准备卡
     *
     * @param orderReadyDTO
     * @return
     * @throws BizServiceException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public PageDataDTO queryCardForReady(OrderReadyDTO orderReadyDTO) throws BizServiceException {
        try {
            orderReadyDTO.setLastRowNum("all");
            PageDataDTO pageDataDTO = pageQueryDAO.query("STOCKTRANSFERORDER.queryCardforOrderReady", orderReadyDTO);
            return pageDataDTO;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("获取卡信息失败!");
        }
    }

    /**
     * 
     * 功能描述: <br>
     * 准备选中的卡
     *
     * @param orderReadyDTO
     * @return
     * @throws BizServiceException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public void stockTransferOrderReadyByCard(OrderReadyDTO orderReadyDTO) throws BizServiceException {
        try {
            SellOrderList orderList = orderBaseQueryBO.getSellOrderListByPrimaryKey(orderReadyDTO.getOrderListId());
            if (orderList.getCardAmount() != null && orderList.getRealAmount() != null) {
                Integer reallyAmount = 0;
                reallyAmount = Integer.parseInt(orderList.getCardAmount())
                        - Integer.parseInt(orderList.getRealAmount());
                if (orderReadyDTO.cardNoArray.length > reallyAmount) {
                    throw new BizServiceException("所选卡片张数不应大于  " + reallyAmount + " 张,请重新准备! ");
                }
            }
            orderListReady(orderList, orderReadyDTO);

            /***
             * 更新订单的实际数量
             */
            // String cardQuantity = orderBaseQueryBO
            // .selectUnsignCardQuantity(orderReadyDTO.getOrderId());

            SellOrder sellOrder = new SellOrder();
            sellOrder.setOrderId(orderReadyDTO.getOrderId());

            // sellOrder.setRealCardQuantity(cardQuantity);

            sellOrder.setModifyTime(DateUtil.getCurrentTime());

            sellOrder.setModifyUser(orderReadyDTO.getLoginUserId());

            orderBO.updateByPrimaryKeySelective(sellOrder);
        } catch (BizServiceException e) {
            throw e;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("系统异常");
        }
    }

    /**
     * 
     * 功能描述: <br>
     * 全部准备
     *
     * @param orderReadyDTO
     * @throws BizServiceException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public void cardAllReady(OrderReadyDTO orderReadyDTO) throws BizServiceException {
        try {
            SellOrderList orderList = orderBaseQueryBO.getSellOrderListByPrimaryKey(orderReadyDTO.getOrderListId());
            if (orderList.getCardAmount() != null && orderList.getRealAmount() != null) {
                Integer reallyAmount = 0;
                reallyAmount = Integer.parseInt(orderList.getCardAmount())
                        - Integer.parseInt(orderList.getRealAmount());
                if (reallyAmount == 0) {
                    throw new BizServiceException("该明细已准备完成，请不要重复准备 ");
                }
            }
            orderListReady(orderList, orderReadyDTO);
            /***
             * 更新订单的实际数量
             */
            // String cardQuantity = orderBaseQueryBO
            // .selectUnsignCardQuantity(orderReadyDTO.getOrderId());

            SellOrder sellOrder = new SellOrder();
            sellOrder.setOrderId(orderReadyDTO.getOrderId());

            // sellOrder.setRealCardQuantity(cardQuantity);

            sellOrder.setModifyTime(DateUtil.getCurrentTime());

            sellOrder.setModifyUser(orderReadyDTO.getLoginUserId());

            orderBO.updateByPrimaryKeySelective(sellOrder);
        } catch (BizServiceException e) {
            throw e;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("系统异常");
        }
    }

    /**
     * 
     * 功能描述: <br>
     * 卡准备
     *
     * @param orderReadyDTO
     * @throws BizServiceException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @SuppressWarnings("unchecked")
    public void orderListReady(SellOrderList sellOrderList, OrderReadyDTO orderReadyDTO) throws Exception {
        List<SellOrderCardList> sellOrderCardList = new ArrayList<SellOrderCardList>();
        if (OrderConst.FACE_VALUE_TYPE_NOT_STATIC.equals(sellOrderList.getFaceValueType())) {
            orderReadyDTO.setOrderListId(sellOrderList.getOrderListId());
            sellOrderCardList = baseDAO.queryForList("STOCKTRANSFERORDER.selectCardDetailFororderReadyByNotFixDetail",
                    orderReadyDTO);
        } else if (OrderConst.FACE_VALUE_TYPE_STATIC.equals(sellOrderList.getFaceValueType())) {
            orderReadyDTO.setOrderListId(sellOrderList.getOrderListId());
            sellOrderCardList = baseDAO.queryForList("STOCKTRANSFERORDER.selectCardDetailFororderReadyByFixDetail",
                    orderReadyDTO);
        }
        Integer reallyAmount = 0;
        try {
            reallyAmount = Integer.parseInt(sellOrderList.getRealAmount());
        } catch (NumberFormatException e) {
            reallyAmount = 0;
        }
        List<String> cardNoList = new ArrayList<String>();
        List<EntityStock> entityStockList = new ArrayList<EntityStock>();
        for (SellOrderCardList sellOrderCardList_temp : sellOrderCardList) {
            reallyAmount++;
            EntityStock entityStock = new EntityStock();

            entityStock.setCardNo(sellOrderCardList_temp.getCardNo());
            cardNoList.add(sellOrderCardList_temp.getCardNo());
            entityStock.setEntityId(orderReadyDTO.getFirstEntityId());
            entityStock.setStockState(OrderConst.CARD_STOCK_OUT);
            sellOrderCardList_temp.setCreateTime(DateUtil.getCurrentTime());
            sellOrderCardList_temp.setCreateUser(orderReadyDTO.getLoginUserId());
            sellOrderCardList_temp.setOrderId(sellOrderList.getOrderId());
            sellOrderCardList_temp.setOrderListId(sellOrderList.getOrderListId());
            sellOrderCardList_temp.setOrderListId(sellOrderList.getOrderListId());
            sellOrderCardList_temp.setCardState(OrderConst.MAKE_CARD_STATE_SUCCESS);

            sellOrderCardList_temp.setModifyTime(DateUtil.getCurrentTime());
            sellOrderCardList_temp.setModifyUser(orderReadyDTO.getLoginUserId());
            sellOrderCardList_temp.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
            sellOrderCardList_temp.setLegCardNo(OrderConst.ORDER_CARD_STOCK_OUT);
            entityStockList.add(entityStock);
        }
        orderBO.batchInsertSellOrderCardList(sellOrderCardList);

        orderBO.updateEntityStockByPrimaryKey(entityStockList);

        // 记录库存操作日志
        entityStockService.addStockOperaterRecord(cardNoList, orderReadyDTO.getOrderId(), orderReadyDTO
                .getFirstEntityId(), Const.FUNCTION_ROLE_SELLER, OrderConst.ORDER_STATE_ORDER_READY,
                OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION, OrderConst.CARD_STOCK_OPERATE_TYPE_OUT, orderReadyDTO
                        .getLoginUserId());

        sellOrderList.setRealAmount(reallyAmount.toString());
        sellOrderList.setModifyTime(DateUtil.getCurrentTime());
        sellOrderList.setModifyUser(orderReadyDTO.getLoginUserId());
        orderBO.updateSellOrderList(sellOrderList);
    }

    /**
     * 
     * 功能描述: <br>
     * 删除一条准备好的卡号
     *
     * @param orderReadyDTO
     * @throws BizServiceException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public void deleteCheckedRecord(OrderReadyDTO orderReadyDTO) throws BizServiceException {
        try {
            SellOrderCardList sellOrderCardList = orderBO.getSellOrderCardListByPrimaryKey(orderReadyDTO
                    .getOrderCardListId());

            EntityStock entityStock = new EntityStock();

            entityStock.setCardNo(sellOrderCardList.getCardNo());

            entityStock.setEntityId(orderReadyDTO.getFirstEntityId());

            String orderListId = sellOrderCardList.getOrderListId();

            SellOrderList sellOrderList = orderBaseQueryBO.getSellOrderListByPrimaryKey(orderListId);

            Integer realAmont = new Integer(sellOrderList.getRealAmount());
            realAmont--;
            sellOrderList.setRealAmount(realAmont.toString());

            entityStock.setStockState(OrderConst.CARD_STOCK_IN);

            List<EntityStock> entityStockList = new ArrayList<EntityStock>();
            entityStockList.add(entityStock);

            orderBO.updateEntityStockByPrimaryKey(entityStockList);
            // 记录库存操作日志
            List<String> cardNoList = new ArrayList<String>();
            cardNoList.add(entityStock.getCardNo());
            entityStockService.addStockOperaterRecord(cardNoList, sellOrderList.getOrderId(), orderReadyDTO
                    .getFirstEntityId(), Const.FUNCTION_ROLE_SELLER, OrderConst.ORDER_STATE_ORDER_READY,
                    OrderConst.ORDER_FLOW_OPRATION_DELETE, OrderConst.CARD_STOCK_IN, orderReadyDTO.getLoginUserId());
            orderBO.deleteSellOrderCardListByPrimaryKey(orderReadyDTO.getOrderCardListId());

            orderBO.updateSellOrderList(sellOrderList);

        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("删除订单明细失败");
        }

    }

    /**
     * 
     * 功能描述: <br>
     * 删除所有准备好的卡号
     *
     * @param sellOrderDTO
     * @throws BizServiceException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public void deleteAllRecord(SellOrderDTO sellOrderDTO) throws BizServiceException {
        try {

            deleteAll(sellOrderDTO);

            /***
             * 更新订单的实际数量
             */

            SellOrder sellOrder = new SellOrder();
            sellOrder.setOrderId(sellOrderDTO.getOrderId());

            // sellOrder.setRealCardQuantity("0");

            sellOrder.setModifyTime(DateUtil.getCurrentTime());

            sellOrder.setModifyUser(sellOrderDTO.getLoginUserId());

            orderBO.updateByPrimaryKeySelective(sellOrder);

            SellOrderList sellOrderList = new SellOrderList();

            sellOrderList.setRealAmount("0");

            sellOrderList.setModifyTime(DateUtil.getCurrentTime());

            sellOrderList.setModifyUser(sellOrderDTO.getLoginUserId());

            SellOrderListExample sellOrderListExample = new SellOrderListExample();

            sellOrderListExample.createCriteria().andOrderIdEqualTo(sellOrderDTO.getOrderId());

            orderBO.updateSellOrderListByExample(sellOrderList, sellOrderListExample);

        } catch (BizServiceException e) {
            throw e;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("删除全部卡片失败");
        }

    }

    /**
     * 
     * 功能描述: <br>
     * 删除所有准备好的卡号
     *
     * @param sellOrderDTO
     * @throws BizServiceException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public void deleteAll(SellOrderDTO sellOrderDTO) throws BizServiceException {
        try {

            String cardQuantity = orderBaseQueryBO.selectUnsignCardQuantity(sellOrderDTO.getOrderId());
            if ("0".equals(cardQuantity)) {
                throw new BizServiceException("订单中无卡无须删除 ");
            }
            /**
             * 同时所有的明细中的卡实际数量至为0
             */
            List<SellOrderCardList> orderCardList_list = orderBaseQueryBO
                    .getSellOrderCardListDetailByOrderId(sellOrderDTO.getOrderId());

            List<EntityStock> entityStockList = new ArrayList<EntityStock>();
            List<String> cardNoList = new ArrayList<String>();
            for (SellOrderCardList detail : orderCardList_list) {
                EntityStock entityStcok = new EntityStock();
                entityStcok.setCardNo(detail.getCardNo());
                cardNoList.add(detail.getCardNo());
                entityStcok.setEntityId(sellOrderDTO.getFirstEntityId());
                entityStcok.setStockState(OrderConst.CARD_STOCK_IN);
                entityStockList.add(entityStcok);
            }
            orderBO.updateEntityStockByPrimaryKey(entityStockList);
            entityStockService.addStockOperaterRecord(cardNoList, sellOrderDTO.getOrderId(), sellOrderDTO
                    .getFirstEntityId(), Const.FUNCTION_ROLE_SELLER, OrderConst.ORDER_STATE_ORDER_READY,
                    OrderConst.ORDER_FLOW_OPRATION_DELETE, OrderConst.CARD_STOCK_IN, sellOrderDTO.getLoginUserId());

            orderBO.deleteSellOrderCardListByOrderId(sellOrderDTO.getOrderId());
        } catch (BizServiceException e) {
            throw e;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("删除全部卡片失败");
        }
    }

    /**
     * 
     * 功能描述: <br>
     * 提交订单
     *
     * @param sellOrderDTO
     * @throws BizServiceException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public void submitOrderReady(SellOrderDTO sellOrderDTO) throws BizServiceException {
        try {
            List<SellOrderList> sellOrderLists = orderBaseQueryBO.getSellOrderListByOrderId(sellOrderDTO.getOrderId());
            for (SellOrderList orderList : sellOrderLists) {
                Integer cardAmt = new Integer(orderList.getCardAmount());
                Integer realAmt = new Integer(orderList.getRealAmount());
                if (cardAmt > realAmt) {
                    throw new BizServiceException("订单：" + sellOrderDTO.getOrderId() + " 中有准备未完成的明细，不能提交!");
                }
            }
            orderBO.orderFlowNexNode(sellOrderDTO.getOrderId(), OrderConst.STOCK_TRANSFER_ORDER_OUT, sellOrderDTO
                    .getLoginUserId(), sellOrderDTO.getDefaultEntityId(), OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
                    "", OrderConst.ORDER_FLOW_READY);
        } catch (BizServiceException e) {
            throw e;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("提交订单失败!");
        }
    }

    /**
     * 
     * 功能描述: <br>
     * 待出库状态下退回订单
     *
     * @param sellOrderDTO
     * @throws BizServiceException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public void sendBackOrder(SellOrderDTO sellOrderDTO) throws BizServiceException {
        try {
            List<SellOrderCardList> orderCardList_list = orderBaseQueryBO
                    .getSellOrderCardListDetailByOrderId(sellOrderDTO.getOrderId());
            // 订单明细不能为空 and 订单明细中卡号不能为空
            if (null != orderCardList_list && orderCardList_list.size() > 0
                    && null != orderCardList_list.get(0).getCardNo()
                    && !"".equals(orderCardList_list.get(0).getCardNo().trim())) {
                List<EntityStock> entityStockList = new ArrayList<EntityStock>();
                List<String> cardNoList = new ArrayList<String>();
                for (SellOrderCardList detail : orderCardList_list) {
                    EntityStock entityStcok = new EntityStock();
                    entityStcok.setCardNo(detail.getCardNo());
                    cardNoList.add(detail.getCardNo());
                    entityStcok.setEntityId(sellOrderDTO.getProcessEntityId());
                    entityStcok.setStockState(OrderConst.CARD_STOCK_IN);
                    entityStockList.add(entityStcok);
                }
                orderBO.updateEntityStockByPrimaryKey(entityStockList);
                // 记录库存操作日志
                entityStockService.addStockOperaterRecord(cardNoList, sellOrderDTO.getOrderId(), sellOrderDTO
                        .getDefaultEntityId(), Const.FUNCTION_ROLE_SELLER, OrderConst.ORDER_STATE_ORDER_READY,
                        OrderConst.ORDER_FLOW_OPRATION_BACK, OrderConst.CARD_STOCK_IN, sellOrderDTO.getLoginUserId());

                orderBO.deleteSellOrderCardListByOrderId(sellOrderDTO.getOrderId());

                SellOrderList sellOrderList = new SellOrderList();

                sellOrderList.setRealAmount("0");

                sellOrderList.setModifyTime(DateUtil.getCurrentTime());

                sellOrderList.setModifyUser(sellOrderDTO.getLoginUserId());

                SellOrderListExample sellOrderListExample = new SellOrderListExample();

                sellOrderListExample.createCriteria().andOrderIdEqualTo(sellOrderDTO.getOrderId());

                orderBO.updateSellOrderListByExample(sellOrderList, sellOrderListExample);
            }

            orderBO.orderFlowNexNode(sellOrderDTO.getOrderId(), OrderConst.STOCK_TRANSFER_ORDER_INPUT, sellOrderDTO
                    .getLoginUserId(), sellOrderDTO.getDefaultEntityId(), OrderConst.ORDER_FLOW_OPRATION_BACK,
                    sellOrderDTO.getOperationMemo(), OrderConst.ORDER_FLOW_READY);
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("退回订单失败!");
        }

    }

    public PageQueryDAO getPageQueryDAO() {
        return pageQueryDAO;
    }

    public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
        this.pageQueryDAO = pageQueryDAO;
    }

    public BaseDAO getBaseDAO() {
        return baseDAO;
    }

    public void setBaseDAO(BaseDAO baseDAO) {
        this.baseDAO = baseDAO;
    }

    public OrderBaseQueryBO getOrderBaseQueryBO() {
        return orderBaseQueryBO;
    }

    public void setOrderBaseQueryBO(OrderBaseQueryBO orderBaseQueryBO) {
        this.orderBaseQueryBO = orderBaseQueryBO;
    }

    public OrderBO getOrderBO() {
        return orderBO;
    }

    public void setOrderBO(OrderBO orderBO) {
        this.orderBO = orderBO;
    }

    public EntityStockService getEntityStockService() {
        return entityStockService;
    }

    public void setEntityStockService(EntityStockService entityStockService) {
        this.entityStockService = entityStockService;
    }

}

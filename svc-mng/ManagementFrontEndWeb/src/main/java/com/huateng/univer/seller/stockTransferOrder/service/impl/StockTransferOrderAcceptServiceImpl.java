/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: StockTransferOrderAcceptServiceImpl.java
 * Author:   13071598
 * Date:     2013-11-10 上午09:30:59
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.univer.seller.stockTransferOrder.service.impl;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderCardListDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderCardListQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderQueryDTO;
import com.allinfinance.univer.seller.order.dto.StockTransferOrderAcceptDTO;
import com.huateng.framework.constant.Const;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.SellOrderCardListDAO;
import com.huateng.framework.ibatis.dao.SellOrderDAO;
import com.huateng.framework.ibatis.model.SellOrder;
import com.huateng.framework.ibatis.model.SellOrderCardList;
import com.huateng.framework.ibatis.model.SellOrderCardListExample;
import com.huateng.univer.entitystock.service.EntityStockService;
import com.huateng.univer.order.business.bo.OrderBO;
import com.huateng.univer.seller.stockTransferOrder.service.StockTransferOrderAcceptService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 〈一句话功能简述〉<br>
 * 调拨订单入库
 * 
 * @author 13071598
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class StockTransferOrderAcceptServiceImpl implements StockTransferOrderAcceptService {

    Logger logger = Logger.getLogger(StockTransferOrderAcceptServiceImpl.class);

    private CommonsDAO commonsDAO;
    private PageQueryDAO pageQueryDAO;
    private OrderBO orderBO;
    private SellOrderDAO sellOrderDAO;
    private EntityStockService entityStockService;
    private SellOrderCardListDAO sellOrderCardListDAO;

    /**
     * 
     * 功能描述: <br>
     * 查询已出库和部分入库状态下调拨订单
     *
     * @param sellOrderQueryDTO
     * @return
     * @throws BizServiceException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @Override
    public PageDataDTO queryStockTransferOrderAtAccept(SellOrderQueryDTO sellOrderQueryDTO) throws BizServiceException {
        try {
            return pageQueryDAO.query("STOCKTRANSFERORDER.queryStockTransferOrderAtAccept", sellOrderQueryDTO);
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("查询库存调拨订单失败~！");
        }
    }

    /**
     * 
     * 功能描述: <br>
     * 查询订单卡明细
     *
     * @param sellOrderDTO
     * @return
     * @throws BizServiceException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @SuppressWarnings("unchecked")
    public List<String> queryOrderCardList(SellOrderDTO sellOrderDTO) throws BizServiceException {
        try {
            List<String> result = (List<String>) commonsDAO.queryForList("STOCKTRANSFERORDER.getCardNos", sellOrderDTO);
            return result;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("获取卡明细失败~！");
        }
    }

    /**
     * 
     * 功能描述: <br>
     * 完成卡号段入库
     *
     * @param stockTransferOrderAcceptDTO
     * @throws BizServiceException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @SuppressWarnings("unchecked")
    public void submitAccept(StockTransferOrderAcceptDTO stockTransferOrderAcceptDTO) throws BizServiceException {
        try {
            List<SellOrderCardListQueryDTO> sellOrderCardQueryList = stockTransferOrderAcceptDTO
                    .getSellOrderCardQueryList();
            List<String> cardNos = new ArrayList<String>();

            SellOrderCardListDTO sellOrderCardListDTO = new SellOrderCardListDTO();
            sellOrderCardListDTO.setOrderId(stockTransferOrderAcceptDTO.getOrderId());
            for (int i = 0; i < sellOrderCardQueryList.size(); i++) {
                sellOrderCardListDTO.setStartCardNo(sellOrderCardQueryList.get(i).getStartCardNo());
                sellOrderCardListDTO.setEndCardNo(sellOrderCardQueryList.get(i).getEndCardNo());
                List<String> tempList = (List<String>) commonsDAO.queryForList("STOCKTRANSFERORDER.getAcceptCardNos",
                        sellOrderCardListDTO);
                cardNos.addAll(tempList);
            }
            for(String cardNo : cardNos){
                SellOrderCardList sellOrderCardList = new SellOrderCardList();
                sellOrderCardList.setLegCardNo(OrderConst.ORDER_CARD_STOCK_IN);
                SellOrderCardListExample example = new SellOrderCardListExample();
                example.createCriteria().andOrderIdEqualTo(stockTransferOrderAcceptDTO.getOrderId()).
                        andCardNoEqualTo(cardNo);
                //修改订单卡明细中的对应的库存状态
                sellOrderCardListDAO.updateByExampleSelective(sellOrderCardList, example);
            }
            // 修改库存状态
            entityStockService.modifyStockStateAndEntity(cardNos, stockTransferOrderAcceptDTO.getDefaultEntityId(),
                    OrderConst.CARD_STOCK_OUT, OrderConst.CARD_STOCK_IN);

            // 库存操作日志：入库
            entityStockService.addStockOperaterRecord(cardNos, stockTransferOrderAcceptDTO.getOrderId(),
                    stockTransferOrderAcceptDTO.getDefaultEntityId(), Const.FUNCTION_ROLE_SELLER,
                    OrderConst.ORDER_STATE_ORDER_ACCEPT, OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
                    OrderConst.CARD_STOCK_OPERATE_TYPE_IN, stockTransferOrderAcceptDTO.getLoginUserId());

        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("订单入库提交失败~！");
        }

    }

    /**
     * 
     * 功能描述: <br>
     * 判断部分入库或者完全入库
     *
     * @param sellOrderDTO
     * @throws BizServiceException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @SuppressWarnings("unchecked")
    public void checkAccept(SellOrderDTO sellOrderDTO) throws BizServiceException {
        try {
            sellOrderDTO.setOrderId(sellOrderDTO.getOrderId());
            List<String> result = (List<String>) commonsDAO.queryForList("STOCKTRANSFERORDER.getCardNos", sellOrderDTO);
            if (result.size() > 0) {
                orderBO.orderFlowNexNode(sellOrderDTO.getOrderId(), OrderConst.STOCK_TRANSFER_ORDER_NOTALL_IN,
                        sellOrderDTO.getLoginUserId(), sellOrderDTO.getDefaultEntityId(),
                        OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION, "", OrderConst.ORDER_FLOW_BRANCH_ACCEPT);
            } else {
                orderBO.orderFlowNexNode(sellOrderDTO.getOrderId(), OrderConst.STOCK_TRANSFER_ORDER_ALL_IN,
                        sellOrderDTO.getLoginUserId(), sellOrderDTO.getDefaultEntityId(),
                        OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION, "", OrderConst.ORDER_FLOW_BRANCH_ACCEPT);
            }
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("订单入库校验失败~！");
        }

    }

    /**
     * 
     * 功能描述: <br>
     * 退回订单
     *
     * @param sellOrderDTO
     * @throws BizServiceException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public void sendBackAtAccept(SellOrderDTO sellOrderDTO) throws BizServiceException {
        try {
            SellOrder sellOrder = sellOrderDAO.selectByPrimaryKey(sellOrderDTO.getOrderId());
            if (sellOrder.getOrderState().equals(OrderConst.STOCK_TRANSFER_ORDER_NOTALL_IN)) {
                throw new BizServiceException("部分入库状态不能退回");
            }
            orderBO.orderFlowNexNode(sellOrderDTO.getOrderId(), OrderConst.STOCK_TRANSFER_ORDER_READY, sellOrderDTO
                    .getLoginUserId(), sellOrderDTO.getDefaultEntityId(), OrderConst.ORDER_FLOW_OPRATION_BACK,
                    sellOrderDTO.getOperationMemo(), OrderConst.ORDER_FLOW_BRANCH_ACCEPT);
        } catch (BizServiceException bse) {
            throw bse;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("调拨订单退回失败~！");
        }
    }
    
    /**
     * 
     * 功能描述: <br>
     * 关闭订单
     *
     * @param sellOrderInputDTO
     * @throws BizServiceException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @SuppressWarnings("unchecked")
    public void closeAccept(SellOrderInputDTO sellOrderInputDTO) throws BizServiceException{
        try{
            for (String orderId : sellOrderInputDTO.getEc_choose()) {
                sellOrderInputDTO.setOrderId(orderId);
                List<Map<String,String>> list = (List<Map<String, String>>) commonsDAO
                        .queryForList("STOCKTRANSFERORDER.getAcceptCardNosForClose", sellOrderInputDTO);
                for(int i = 0;i < list.size();i++){
                    if(list.get(i).get("isInValid") == null){
                        throw new BizServiceException("未接收的卡含有未作废的卡，不能关闭");
                    }
                }
            }
            
            for(String orderId : sellOrderInputDTO.getEc_choose()){
                orderBO.orderFlowNexNode(orderId, OrderConst.STOCK_TRANSFER_ORDER_ALL_IN,
                        sellOrderInputDTO.getLoginUserId(), sellOrderInputDTO.getDefaultEntityId(),
                        OrderConst.ORDER_FLOW_OPERATION_CLOSE, "", OrderConst.ORDER_FLOW_BRANCH_ACCEPT);
            }
        }catch (BizServiceException bse) {
            throw bse;
        }catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("关闭订单失败");
        }
    }
    
    
    public PageQueryDAO getPageQueryDAO() {
        return pageQueryDAO;
    }

    public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
        this.pageQueryDAO = pageQueryDAO;
    }

    public CommonsDAO getCommonsDAO() {
        return commonsDAO;
    }

    public void setCommonsDAO(CommonsDAO commonsDAO) {
        this.commonsDAO = commonsDAO;
    }

    public EntityStockService getEntityStockService() {
        return entityStockService;
    }

    public void setEntityStockService(EntityStockService entityStockService) {
        this.entityStockService = entityStockService;
    }

    public OrderBO getOrderBO() {
        return orderBO;
    }

    public void setOrderBO(OrderBO orderBO) {
        this.orderBO = orderBO;
    }

    public SellOrderDAO getSellOrderDAO() {
        return sellOrderDAO;
    }

    public void setSellOrderDAO(SellOrderDAO sellOrderDAO) {
        this.sellOrderDAO = sellOrderDAO;
    }

    public SellOrderCardListDAO getSellOrderCardListDAO() {
        return sellOrderCardListDAO;
    }

    public void setSellOrderCardListDAO(SellOrderCardListDAO sellOrderCardListDAO) {
        this.sellOrderCardListDAO = sellOrderCardListDAO;
    }

    
}

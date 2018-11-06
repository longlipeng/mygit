/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: StockTransferOrderInputServiceImpl.java
 * Author:   13071598
 * Date:     2013-11-2 上午09:17:08
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.univer.seller.stockTransferOrder.service.impl;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.product.ProductQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderCardListQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderCompositeDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderListDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderListQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderQueryDTO;
import com.allinfinance.univer.system.sysparam.dto.SystemParameterDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.constant.SystemInfoConstants;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.SellOrderDAO;
import com.huateng.framework.ibatis.dao.SellOrderListDAO;
import com.huateng.framework.ibatis.model.SellOrder;
import com.huateng.framework.ibatis.model.SellOrderList;
import com.huateng.framework.ibatis.model.SellOrderListExample;
import com.huateng.framework.util.Amount;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.MathUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.framework.util.StringUtil;
import com.huateng.univer.issuer.order.biz.bo.StockOrderCommonService;
import com.huateng.univer.order.business.bo.OrderBO;
import com.huateng.univer.order.business.bo.OrderBaseQueryBO;
import com.huateng.univer.seller.stockTransferOrder.service.StockTransferOrderInputService;
import com.huateng.univer.system.sysparam.biz.service.SystemParameterService;

import java.util.List;

import org.apache.log4j.Logger;

/**
 * 〈一句话功能简述〉<br>
 * 调拨订单录入service实现类
 * 
 * @author 13071598
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class StockTransferOrderInputServiceImpl implements StockTransferOrderInputService {

    /**
     * 日志
     * */
    Logger logger = Logger.getLogger(StockTransferOrderInputServiceImpl.class);

    private OrderBO orderBO;
    private PageQueryDAO pageQueryDAO;
    private CommonsDAO commonsDAO;
    private BaseDAO baseDAO;
    private SellOrderDAO sellOrderDAO;
    private SellOrderListDAO sellOrderListDAO;

    private OrderBaseQueryBO orderBaseQueryBO;

    private StockOrderCommonService stockOrderCommonService;
    private SystemParameterService systemParameterService;

    /*
     * (non-Javadoc)
     * @see
     * com.huateng.univer.seller.stockTransferOrder.service.StockTransferOrderInputService#queryStockTransferOrderAtInput
     * (com.huateng.univer.seller.order.dto.SellOrderQueryDTO)
     */

    /**
     * 
     * 功能描述: <br>
     * 查询录入状态下调拨订单
     *
     * @param sellOrderQueryDTO
     * @return
     * @throws BizServiceException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @Override
    public PageDataDTO queryStockTransferOrderAtInput(SellOrderQueryDTO sellOrderQueryDTO) throws BizServiceException {
        try {
            return pageQueryDAO.query("STOCKTRANSFERORDER.queryStockTransferOrderAtInput", sellOrderQueryDTO);
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("查询库存调拨订单失败~！");
        }
    }

    /**
     * 
     * 功能描述: <br>
     * 查询调出机构和调入机构都允许销售的产品
     *
     * @param sellOrderDTO
     * @return
     * @throws BizServiceException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @SuppressWarnings("unchecked")
    public List<ProductQueryDTO> queryFirstProcessProducts(SellOrderDTO sellOrderDTO) throws BizServiceException {
        try {
            List<ProductQueryDTO> productList = (List<ProductQueryDTO>) commonsDAO.queryForList(
                    "STOCKTRANSFERORDER.queryFirstProcessProducts", sellOrderDTO);
            return productList;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("查询产品失败~！");
        }
    }

    /**
     * 
     * 功能描述: <br>
     * 查询调出机构产品的库存信息
     *
     * @param sellOrderQueryDTO
     * @return
     * @throws BizServiceException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @Override
    public PageDataDTO queryFirstEntityStock(SellOrderQueryDTO sellOrderQueryDTO) throws BizServiceException {
        try {
            return pageQueryDAO.query("STOCKTRANSFERORDER.getFirstEntityStock", sellOrderQueryDTO);
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("查询调出机构库存信息失败~！");
        }
    }

    /**
     * 
     * 功能描述: <br>
     * 新增一个调拨订单
     *
     * @param sellOrderDTO
     * @return
     * @throws BizServiceException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public SellOrderCompositeDTO insertStockTransferOrder(SellOrderDTO sellOrderDTO) throws BizServiceException {
        try {
            SellOrder sellOrder = new SellOrder();
            ReflectionUtil.copyProperties(sellOrderDTO, sellOrder);

            /**
             *获取订单ID
             */

            sellOrder.setOrderId(commonsDAO.getNextValueOfSequence("TB_SELL_ORDER"));

            sellOrder.setOrderDate(DateUtil.getFormatTime(sellOrder.getOrderDate()));
            sellOrder.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
            sellOrder.setCreateTime(DateUtil.getCurrentTime());
            sellOrder.setCreateUser(sellOrderDTO.getLoginUserId());
            sellOrder.setModifyTime(DateUtil.getCurrentTime());
            sellOrder.setModifyUser(sellOrderDTO.getLoginUserId());

            sellOrderDAO.insert(sellOrder);

            // 添加订单流程信息
            sellOrderDTO.setOrderId(sellOrder.getOrderId());
            stockOrderCommonService.addNewOrderFlow(sellOrderDTO, OrderConst.ORDER_FLOW_INPUT,
                    OrderConst.ORDER_FLOW_OPRATION_ADD);

            SellOrderListQueryDTO sellOrderListQueryDTO = new SellOrderListQueryDTO();
            sellOrderListQueryDTO.setOrderId(sellOrder.getOrderId());

            // SellOrderCompositeDTO sellOrderCompositeDTO = new SellOrderCompositeDTO();

            PageDataDTO sellOrderList = pageQueryDAO.query("STOCKTRANSFERORDER.getStockTransferOrderList",
                    sellOrderListQueryDTO);
            // sellOrder = sellOrderDAO.selectByPrimaryKey(sellOrder.getOrderId());

            SellOrderCompositeDTO sellOrderCompositeDTO = new SellOrderCompositeDTO();
            sellOrderCompositeDTO.setSellOrderList(sellOrderList);
            sellOrderCompositeDTO.setSellOrderDTO(sellOrderDTO);
            return sellOrderCompositeDTO;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("新增库存调拨订单失败~！");
        }
    }

    /**
     * 
     * 功能描述: <br>
     * 进入一个订单编辑页面，查询出页面显示的信息，订单信息，和订单明细信息
     *
     * @param sellOrderDTO
     * @return
     * @throws BizServiceException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public SellOrderCompositeDTO queryStockTransferOrderForEdit(SellOrderDTO sellOrderDTO) throws BizServiceException {
        try {
            SellOrderCompositeDTO sellOrderCompositeDTO = new SellOrderCompositeDTO();
            SellOrder sellOrder = new SellOrder();
            sellOrder = sellOrderDAO.selectByPrimaryKey(sellOrderDTO.getOrderId());
            sellOrder.setOrderDate(DateUtil.formatStringDate(sellOrder.getOrderDate()));
            ReflectionUtil.copyProperties(sellOrder, sellOrderDTO);
            sellOrderCompositeDTO.setSellOrderDTO(sellOrderDTO);

            SellOrderListQueryDTO sellOrderListQueryDTO = new SellOrderListQueryDTO();
            sellOrderListQueryDTO.setOrderId(sellOrder.getOrderId());

            PageDataDTO sellOrderList = pageQueryDAO.query("STOCKTRANSFERORDER.getStockTransferOrderList",
                    sellOrderListQueryDTO);
            sellOrderCompositeDTO.setSellOrderList(sellOrderList);
            return sellOrderCompositeDTO;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("查询订单失败~！");
        }
    }

    /**
     * 
     * 功能描述: <br>
     * 新增一条订单明细
     *
     * @param sellOrderListDTO
     * @throws BizServiceException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public void insertStockTransferOrderList(SellOrderListDTO sellOrderListDTO) throws BizServiceException {
        checkOrderListExit(sellOrderListDTO);
        checkOrderCardTotalThanMaximum(sellOrderListDTO);

        SellOrderList sellOrderList = new SellOrderList();

        ReflectionUtil.copyProperties(sellOrderListDTO, sellOrderList);

        sellOrderList.setOrderListId(commonsDAO.getNextValueOfSequence("TB_SELL_ORDER_LIST"));

        if (StringUtil.isNotEmpty(sellOrderListDTO.getFaceValue())) {
            sellOrderList.setFaceValue(Amount.getDataBaseAmount(sellOrderListDTO.getFaceValue()));
        }

        if (StringUtil.isNotEmpty(sellOrderListDTO.getValidityPeriod())) {
            sellOrderList.setValidityPeriod(DateUtil.getFormatTime(sellOrderListDTO.getValidityPeriod()));
        }

        sellOrderList.setCreateTime(DateUtil.getCurrentTime());

        sellOrderList.setCreateUser(sellOrderListDTO.getLoginUserId());

        sellOrderList.setModifyTime(DateUtil.getCurrentTime());

        sellOrderList.setModifyUser(sellOrderListDTO.getLoginUserId());

        sellOrderList.setDataState(DataBaseConstant.DATA_STATE_NORMAL);

        // 新增一条订单明细
        sellOrderListDAO.insert(sellOrderList);

        SellOrder sellOrder = sellOrderDAO.selectByPrimaryKey(sellOrderListDTO.getOrderId());

        String cardQuantity = sellOrder.getCardQuantity();

        cardQuantity = MathUtil.add(cardQuantity, sellOrderListDTO.getCardAmount()).toString();
        sellOrder.setCardQuantity(cardQuantity);
        // 更新订单表卡的总数
        sellOrderDAO.updateByPrimaryKeySelective(sellOrder);
    }

    /**
     * 
     * 功能描述: <br>
     * 检查明细是否已经存在
     *
     * @param sellOrderListDTO
     * @throws BizServiceException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public void checkOrderListExit(SellOrderListDTO sellOrderListDTO) throws BizServiceException {
        try {
            SellOrderListExample orderListExample = new SellOrderListExample();
            orderListExample.createCriteria().andOrderIdEqualTo(sellOrderListDTO.getOrderId()).andFaceValueTypeEqualTo(
                    sellOrderListDTO.getFaceValueType()).andFaceValueEqualTo(
                    Amount.getDataBaseAmount(sellOrderListDTO.getFaceValue())).andDataStateEqualTo(
                    DataBaseConstant.DATA_STATE_NORMAL);
            List<SellOrderList> lstResult = sellOrderListDAO.selectByExample(orderListExample);
            if (null != lstResult && lstResult.size() > 0) {
                for (SellOrderList sellOrderListTemp : lstResult) {
                    if (sellOrderListTemp.getProductId().equals(sellOrderListDTO.getProductId())) {
                        throw new BizServiceException("当前面额已经在本订单产生了明细,请不要重复添加");
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new BizServiceException("当前面额已经在本订单产生了明细,请不要重复添加");
        }
    }

    /**
     * 
     * 功能描述: <br>
     * 检查该订单下多有明细卡总量是否超出系统既定最大值
     *
     * @param sellOrderListDTO
     * @throws BizServiceException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public void checkOrderCardTotalThanMaximum(SellOrderListDTO sellOrderListDTO) throws BizServiceException {
        try {
            SellOrderListExample orderListExample = new SellOrderListExample();
            orderListExample.createCriteria().andOrderIdEqualTo(sellOrderListDTO.getOrderId()).andDataStateEqualTo(
                    DataBaseConstant.DATA_STATE_NORMAL);
            List<SellOrderList> orderLists = sellOrderListDAO.selectByExample(orderListExample);
            int existCount = 0;
            // 当前订单明细卡数量
            existCount += Integer.parseInt(sellOrderListDTO.getCardAmount());
            // 当前订单对应的所有明细卡数量之和
            for (SellOrderList tempList : orderLists) {
                existCount += Integer.parseInt(tempList.getCardAmount());
            }
            /* 取出系统既定的订单卡数量上限 */
            SystemParameterDTO systemParameterDTO = new SystemParameterDTO();
            systemParameterDTO.setParameterCode(SystemInfoConstants.ORDER_CARD_MAXIMUM);
            Integer maximum = Integer.parseInt(systemParameterService.viewSystemParamter(systemParameterDTO)
                    .getParameterValue());
            /* 当前订单明细卡数量+已存在的订单明细卡数量>系统既定订单卡数量上限 */
            if (existCount > maximum) {
                throw new BizServiceException("订单所有明细卡数量总和超过系统既定最大值" + maximum);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new BizServiceException("检测订单明细卡数量总和异常");
        }
    }

    /**
     * 
     * 功能描述: <br>
     * 编辑更新订单
     *
     * @param sellOrderDTO
     * @throws BizServiceException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public void updateStockTransferOrder(SellOrderDTO sellOrderDTO) throws BizServiceException {
        try {
            SellOrder sellOrder = new SellOrder();
            ReflectionUtil.copyProperties(sellOrderDTO, sellOrder);

            if (sellOrderDTO.getCardQuantity() == "") {
                sellOrder.setCardQuantity(null);
            }
            sellOrder.setOrderDate(DateUtil.getFormatTime(sellOrder.getOrderDate()));
            sellOrder.setProductId(null);
            sellOrder.setModifyTime(DateUtil.getCurrentTime());
            sellOrder.setModifyUser(sellOrderDTO.getLoginUserId());
            sellOrderDAO.updateByPrimaryKeySelective(sellOrder);
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("编辑订单失败!");
        }
    }

    /**
     * 
     * 功能描述: <br>
     * 查看订单、订单明细、订单流程信息
     *
     * @param sellOrderCompositeDTO
     * @throws BizServiceException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public SellOrderCompositeDTO viewStockTransferOrder(SellOrderCompositeDTO sellOrderCompositeDTO)
            throws BizServiceException {
        try {
            SellOrderDTO sellOrderDTO = (SellOrderDTO) baseDAO.queryForObject(
                    "STOCKTRANSFERORDER.getStockTransferOrderMsg", sellOrderCompositeDTO.getSellOrderDTO());
            if (sellOrderDTO.getCardQuantity() == null) {
                sellOrderDTO.setCardQuantity("0");
            }
            sellOrderDTO.setOrderDate(DateUtil.formatStringDate(sellOrderDTO.getOrderDate()));
            // 订单信息
            sellOrderCompositeDTO.setSellOrderDTO(sellOrderDTO);

            PageDataDTO sellOrderList = pageQueryDAO.query("STOCKTRANSFERORDER.getStockTransferOrderList",
                    sellOrderCompositeDTO.getSellOrderListQueryDTO());
            // 订单明细信息
            sellOrderCompositeDTO.setSellOrderList(sellOrderList);

            // 订单卡明细信息
            //未接收
            PageDataDTO sellOrderCardListOri = pageQueryDAO.query("STOCKTRANSFERORDER.getStockTransferOrderCardListOri",
                    sellOrderCompositeDTO.getSellOrderCardListQueryDTO());
            sellOrderCompositeDTO.setSellOrderOrigCardList(sellOrderCardListOri);
            //已接收
            PageDataDTO sellOrderCardListRace = pageQueryDAO.query("STOCKTRANSFERORDER.getStockTransferOrderCardListRace",
                    sellOrderCompositeDTO.getOrderReceiveCardListQueryDTO());
            sellOrderCompositeDTO.setSellOrderReceCardList(sellOrderCardListRace);
            // 查看流程记录
            sellOrderCompositeDTO.setSellOrderFlowList(orderBaseQueryBO.queryOrderFlowByOrderId(sellOrderCompositeDTO
                    .getSellOrderFlowQueryDTO()));
        } catch (BizServiceException b) {
            throw b;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("查看订单失败");
        }
        return sellOrderCompositeDTO;
    }

    /**
     * 
     * 功能描述: <br>
     * 提交订单
     *
     * @param sellOrderInputDTO
     * @throws BizServiceException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public void submitStockTransferOrderAtInput(SellOrderInputDTO sellOrderInputDTO) throws BizServiceException {
        try {
            for (String orderId : sellOrderInputDTO.getEc_choose()) {
                SellOrder currentOrder = sellOrderDAO.selectByPrimaryKey(orderId);

                if (StringUtil.isEmpty(currentOrder.getCardQuantity())
                        || Integer.parseInt(currentOrder.getCardQuantity()) <= 0) {
                    throw new BizServiceException("提交订单失败,订单：" + orderId + "卡数量为零,请添加卡明细");
                }
                // 下一状态：待出库
                orderBO.orderFlowNexNode(orderId, OrderConst.STOCK_TRANSFER_ORDER_READY, sellOrderInputDTO
                        .getLoginUserId(), sellOrderInputDTO.getDefaultEntityId(),
                        OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION, "", OrderConst.ORDER_FLOW_INPUT);

            }
        } catch (BizServiceException e) {
            throw e;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("提交订单失败");
        }
    }

    /**
     * 
     * 功能描述: <br>
     * 删除一条订单明细
     *
     * @param sellOrderListDTO
     * @throws BizServiceException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public void deleteRecord(SellOrderListDTO sellOrderListDTO) throws BizServiceException {
        try {
            SellOrderList sellOrderList = sellOrderListDAO.selectByPrimaryKey(sellOrderListDTO.getOrderListId());
            SellOrder sellOrder = sellOrderDAO.selectByPrimaryKey(sellOrderListDTO.getOrderId());

            // 删除订单明细
            sellOrderListDAO.deleteByPrimaryKey(sellOrderListDTO.getOrderListId());
            String cardQuantity = sellOrder.getCardQuantity();

            cardQuantity = MathUtil.subtract(cardQuantity, sellOrderList.getCardAmount()).toString();
            sellOrder.setCardQuantity(cardQuantity);
            // 更新订单表卡的总数
            sellOrderDAO.updateByPrimaryKeySelective(sellOrder);
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("删除订单明细失败");
        }

    }

    /**
     * 
     * 功能描述: <br>
     * 取消选中的订单
     *
     * @param sellOrderInputDTO
     * @throws BizServiceException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public void cancelStockTransferOrderAtInput(SellOrderInputDTO sellOrderInputDTO) throws BizServiceException {
        try {
            for (String orderId : sellOrderInputDTO.getEc_choose()) {
                orderBO.orderFlowNexNode(orderId, OrderConst.STOCK_TRANSFER_ORDER_CANCEL, sellOrderInputDTO
                        .getLoginUserId(), sellOrderInputDTO.getDefaultEntityId(),
                        OrderConst.ORDER_FLOW_OPRATION_CANCEL, "", OrderConst.ORDER_FLOW_INPUT);
            }
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("取消订单失败!");
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

    public SellOrderDAO getSellOrderDAO() {
        return sellOrderDAO;
    }

    public void setSellOrderDAO(SellOrderDAO sellOrderDAO) {
        this.sellOrderDAO = sellOrderDAO;
    }

    public StockOrderCommonService getStockOrderCommonService() {
        return stockOrderCommonService;
    }

    public void setStockOrderCommonService(StockOrderCommonService stockOrderCommonService) {
        this.stockOrderCommonService = stockOrderCommonService;
    }

    public SellOrderListDAO getSellOrderListDAO() {
        return sellOrderListDAO;
    }

    public void setSellOrderListDAO(SellOrderListDAO sellOrderListDAO) {
        this.sellOrderListDAO = sellOrderListDAO;
    }

    public SystemParameterService getSystemParameterService() {
        return systemParameterService;
    }

    public void setSystemParameterService(SystemParameterService systemParameterService) {
        this.systemParameterService = systemParameterService;
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

    public BaseDAO getBaseDAO() {
        return baseDAO;
    }

    public void setBaseDAO(BaseDAO baseDAO) {
        this.baseDAO = baseDAO;
    }

}

/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: OrderActUpdateTask.java
 * Author:   13091704
 * Date:     2013-11-14 下午03:08:06
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 张海曜                      13-11-25    2.0          代码格式修正
 */
package com.suning.svc.task;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.univer.order.business.bo.OrderBaseQueryBO;
import com.huateng.univer.order.business.service.SubmitOrderService;
import com.suning.svc.constants.OrderActConstants;
import com.suning.svc.core.template.CallBack;
import com.suning.svc.core.template.OneByOne;
import com.suning.svc.core.template.OneByOneTemplate;

/**
 * 
 * 〈一句话功能简述〉<br>
 * 订单自动激活
 * 
 * @author 13091704
 */
public class OrderAutoActTask {

    private static Logger logger = Logger.getLogger(OrderAutoActTask.class);
    /**
     * 防并发处理模板
     */
    private OneByOneTemplate oneByOneTemplate;
    /**
     * 后台订单处理类BO
     */
    private OrderBaseQueryBO orderBaseQueryBO;
    /**
     * 订单服务Service
     */
    private SubmitOrderService submitOrderService;

    public OneByOneTemplate getOneByOneTemplate() {
        return oneByOneTemplate;
    }

    public void setOneByOneTemplate(OneByOneTemplate oneByOneTemplate) {
        this.oneByOneTemplate = oneByOneTemplate;
    }

    public OrderBaseQueryBO getOrderBaseQueryBO() {
        return orderBaseQueryBO;
    }

    public void setOrderBaseQueryBO(OrderBaseQueryBO orderBaseQueryBO) {
        this.orderBaseQueryBO = orderBaseQueryBO;
    }

    public SubmitOrderService getSubmitOrderService() {
        return submitOrderService;
    }

    public void setSubmitOrderService(SubmitOrderService submitOrderService) {
        this.submitOrderService = submitOrderService;
    }

    /**
     * 
     * 功能描述: <br>
     * 开始自动激活任务,调度平台入口
     * 
     * @throws Exception
     */
    public void startOrderAutoActTask() throws BizServiceException {
        // 对整个自动激活任务进行防并发控制
        BizServiceException ex = oneByOneTemplate.execute(new OneByOne(OrderActConstants.ORDER_BIZ_TYPE_AUTOACT,
                OrderActConstants.ORDER_BIZ_ID_AUTOACT, OrderActConstants.ORDER_BIZ_METHOD_AUTOACT),
                new CallBack<BizServiceException>() {

                    // 将未处理异常作为结果返回
                    @Override
                    public BizServiceException invoke() {
                        try {
                            orderAutoAct();// 调用激活方法
                        } catch (BizServiceException e) {
                            logger.error(e.getErrorMessage());
                            return e;
                        }
                        return null;
                    }

                });
        // 将invoke时的异常（如果有）向外抛出（可由调度平台查看）
        if (ex != null) {
            throw ex;
        }

    }

    /**
     * 
     * 功能描述: <br>
     * 〈功能详细描述〉 实现自动激活那些满足条件的订单
     * 
     * @throws BizServiceException
     * 
     * @throws Exception
     */
    public void orderAutoAct() throws BizServiceException {
        logger.info("==进入orderAutoAct方法==");
        List<SellOrderDTO> sellOrderDTOList = new ArrayList<SellOrderDTO>();
        sellOrderDTOList = getAutoActOrders();
        for (final SellOrderDTO obj : sellOrderDTOList) {
            logger.info("==进入for循环==Bathno:::" + obj.getOrderId() + "---" + obj.getBatchNo());

            submitOrderService.updateOrderActState(obj);

            submitOrderService.orderAutoActive(obj);
        }
    }

    /**
     * 
     * 功能描述: <br>
     * 取得满足自动激活的订单
     * 
     * @return
     * @throws Exception
     */
    public List<SellOrderDTO> getAutoActOrders() throws BizServiceException {
        List<SellOrderDTO> sellOrderDTOs = new ArrayList<SellOrderDTO>();

        try {
            sellOrderDTOs = orderBaseQueryBO.queryAutoActOrder();
        } catch (BizServiceException e) {
            logger.error(e.getMessage());
            throw new BizServiceException("查询自动激活的订单失败!");
        }
        return sellOrderDTOs;
    }
}

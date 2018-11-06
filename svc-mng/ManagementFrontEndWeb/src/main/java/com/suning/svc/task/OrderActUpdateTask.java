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
import java.util.Map;

import org.apache.log4j.Logger;

import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.univer.businessbatch.BusinessBatchService;
import com.huateng.univer.order.business.bo.OrderBaseQueryBO;
import com.suning.svc.constants.OrderActConstants;
import com.suning.svc.core.template.CallBack;
import com.suning.svc.core.template.OneByOne;
import com.suning.svc.core.template.OneByOneTemplate;

/**
 * 〈一句话功能简述〉<br>
 * 订单激活状态更新
 * 
 * @author 13091704
 */
public class OrderActUpdateTask {

    private static Logger logger = Logger.getLogger(OrderActUpdateTask.class);
    /**
     * 防并发处理模板
     */
    private OneByOneTemplate oneByOneTemplate;
    /**
     * 后台订单处理类BO
     */
    private OrderBaseQueryBO orderBaseQueryBO;
    /**
     * 服务集合businessMap
     */
    private Map<String, BusinessBatchService> businessMap;

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

    public Map<String, BusinessBatchService> getBusinessMap() {
        return businessMap;
    }

    public void setBusinessMap(Map<String, BusinessBatchService> businessMap) {
        this.businessMap = businessMap;
    }

    /**
     * 
     * 功能描述: <br>
     * 开始订单激活状态更新,调度平台入口
     * 
     * @throws Exception
     */
    public void startOrderActUpdateTask() throws BizServiceException {
        // 对整个订单激活状态更新任务进行防并发控制
        BizServiceException ex = oneByOneTemplate.execute(new OneByOne(OrderActConstants.ORDER_BIZ_TYPE_ACTUPDATE,
                OrderActConstants.ORDER_BIZ_ID_ACTUPDATE, OrderActConstants.ORDER_BIZ_METHOD_ACTUPDATE),
                new CallBack<BizServiceException>() {

                    // 将未处理异常作为结果返回
                    @Override
                    public BizServiceException invoke() {
                        try {
                            orderActUpdate(OrderActConstants.SERVICE_NAME_BUSINESSACT);// 调用激活方法
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
     * 功能描述: <br>
     * 〈功能详细描述〉 订单激活状态更新
     * 
     * @throws BizServiceException
     */
    public void orderActUpdate(String serviceName) throws BizServiceException {
        logger.info("==进入orderActUpdate方法==");
        List<SellOrderDTO> sellOrderDTOList = new ArrayList<SellOrderDTO>();
        sellOrderDTOList = getOrderActUpdate();
        BusinessBatchService business = businessMap.get(serviceName);
        logger.info("==business=== " + business.getClass());
        for (final SellOrderDTO obj : sellOrderDTOList) {
            logger.info("==进入orderActUpdate方法的for循环==订单号:::" + obj.getOrderId() + "---批次号：" + obj.getBatchNo());
            if (null != obj.getBatchNo() || "".equals(obj.getBatchNo())) {
                business.checkBatchState(obj.getBatchNo());
            }
        }
    }

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉 取得等待修改激活状态的订单
     * 
     * @return
     * @throws BizServiceException
     */
    public List<SellOrderDTO> getOrderActUpdate() throws BizServiceException {
        List<SellOrderDTO> sellOrderDTOs = new ArrayList<SellOrderDTO>();

        try {
            sellOrderDTOs = orderBaseQueryBO.getOrderActUpdate();
        } catch (BizServiceException e) {
            logger.error(e.getMessage());
            throw new BizServiceException("查询等待修改激活状态的订单失败!");
        }
        return sellOrderDTOs;
    }
}

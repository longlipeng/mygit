/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: BatchDaySumTask.java
 * Author:   13010154
 * Date:     2013-11-4 上午11:16:01
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.task;

import org.apache.log4j.Logger;

import com.huateng.framework.constant.OrderDaySumBatchConstant;
import com.suning.svc.core.template.CallBack;
import com.suning.svc.core.template.OneByOne;
import com.suning.svc.core.template.OneByOneTemplate;
import com.suning.svc.coupon.service.OrderDaySumBatchService;
import com.suning.svc.coupon.service.SapInfoService;

/**
 * 汇总执行任务<br> 
 * 
 *
 * @author 13010154
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class BatchDaySumTask {
    /**
     * 日志
     */
    private static final Logger logger = Logger.getLogger(BatchDaySumTask.class);
    
    /**
     * 消费，充值，充值，充退汇总
     */
    private OrderDaySumBatchService orderDaySumBatchService;
    /**
     * 批次上传
     */
    private SapInfoService sapInfoService;

    /**
     * 防并发处理模板
     */
    private OneByOneTemplate oneByOneTemplate;
    /**
     * 
     * 批次执行任务 <br>
     * 〈功能详细描述〉
     *
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public void startSumOrderBatchTask() throws Exception {
        // 对整个批次任务进行防并发控制
       Exception ex = oneByOneTemplate.execute(new OneByOne(OrderDaySumBatchConstant.OBO_BIZ_TYPE_BATCH,
                OrderDaySumBatchConstant.OBO_BIZ_ID_ONCE, OrderDaySumBatchConstant.OBO_METHOD_RUNNING), new CallBack<Exception>() {

            // 将未处理异常作为结果返回
            @Override
            public Exception invoke() {
                try {
                    logger.info("开始执行任务汇总 批次开始");
                    //消费14
                    orderDaySumBatchService.sumCpConsumeOrder(OrderDaySumBatchConstant.CONSUME_ORDER, OrderDaySumBatchConstant.TXN_TYPE_CONSUME);
                    //退货15
                    orderDaySumBatchService.sumCpConsumeOrder(OrderDaySumBatchConstant.REFOUND_ORDER, OrderDaySumBatchConstant.TXN_TYPE_REFOUND);
                    //充值12
                    orderDaySumBatchService.sumCpDepositOrder(OrderDaySumBatchConstant.TXN_TYPE_DEPOSIT);
                    //充退13
                    orderDaySumBatchService.sumCpDepositRefundOrder(OrderDaySumBatchConstant.TXN_TYPE_DEPOSITREFOUND);
                    logger.info("开始执行任务汇总批次结束");
                    //插入记账信息表中
                    sapInfoService.operator();
                } catch (Exception e) {
                    logger.error("汇总批次失败！" + e.getMessage());
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
     * @return the orderDaySumBatchService
     */
    public OrderDaySumBatchService getOrderDaySumBatchService() {
        return orderDaySumBatchService;
    }

    /**
     * @param orderDaySumBatchService the orderDaySumBatchService to set
     */
    public void setOrderDaySumBatchService(OrderDaySumBatchService orderDaySumBatchService) {
        this.orderDaySumBatchService = orderDaySumBatchService;
    }

    /**
     * @return the sapInfoService
     */
    public SapInfoService getSapInfoService() {
        return sapInfoService;
    }

    /**
     * @param sapInfoService the sapInfoService to set
     */
    public void setSapInfoService(SapInfoService sapInfoService) {
        this.sapInfoService = sapInfoService;
    }

    /**
     * @return the oneByOneTemplate
     */
    public OneByOneTemplate getOneByOneTemplate() {
        return oneByOneTemplate;
    }

    /**
     * @param oneByOneTemplate the oneByOneTemplate to set
     */
    public void setOneByOneTemplate(OneByOneTemplate oneByOneTemplate) {
        this.oneByOneTemplate = oneByOneTemplate;
    }
    
    
    
    

}

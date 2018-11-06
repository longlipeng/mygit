/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: OrderDaySumBatchServiceImpl.java
 * Author:   13010154
 * Date:     2013-10-31 上午11:49:32
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.svc.coupon.dto.InsertOrderBatchDto;
import com.huateng.framework.constant.OrderDaySumBatchConstant;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.util.DateUtil;
import com.suning.svc.coupon.constants.SequenceContansts;
import com.suning.svc.coupon.service.OrderDaySumBatchService;
import com.suning.svc.coupon.util.SerialNumberUtil;
import com.suning.svc.ibatis.dao.ConsumeOrderDAO;
import com.suning.svc.ibatis.dao.DepositOrderDAO;
import com.suning.svc.ibatis.dao.DepositRefundOrderDAO;
import com.suning.svc.ibatis.dao.SumOrderBatchDAO;
import com.suning.svc.ibatis.dao.SumOrderResultDAO;
import com.suning.svc.ibatis.model.ConsumeOrder;
import com.suning.svc.ibatis.model.ConsumeOrderExample;
import com.suning.svc.ibatis.model.DepositOrder;
import com.suning.svc.ibatis.model.DepositOrderExample;
import com.suning.svc.ibatis.model.DepositRefundOrder;
import com.suning.svc.ibatis.model.DepositRefundOrderExample;
import com.suning.svc.ibatis.model.SumOrderBatch;
import com.suning.svc.ibatis.model.SumOrderBatchExample;

/**
 * 汇总消费订单，充值订单，充退订单<br> 
 * 〈功能详细描述〉
 *
 * @author 13010154
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class OrderDaySumBatchServiceImpl implements OrderDaySumBatchService {
    /**
     * 日志
     */
    private static final Logger logger = Logger.getLogger(OrderDaySumBatchServiceImpl.class);
    /**
     * 消费订单DAO
     */
    private ConsumeOrderDAO consumeOrderDAO;
   
    /**
     * 充值订单DAO
     */
    private DepositOrderDAO depositOrderDAO;
    
    /**
     * 充退订单DAO
     */
    
    private DepositRefundOrderDAO depositRefundOrderDAO;
    
    /**
     * 汇总批次DAO
     */
    private SumOrderBatchDAO sumOrderBatchDAO;
    
    /**
     * 汇总结果DAO
     */
    private SumOrderResultDAO sumOrderResultDAO;
    
    /**
     * 基类中DAO
     */
    private BaseDAO baseDAO;
    
    /**
     * 
     * 消费订单的汇总 <br>
     * 〈功能详细描述〉
     *
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @SuppressWarnings("unchecked")
    public void sumCpConsumeOrder(String txnType, String sumTxnTypeCode) {
        logger.info("消费订单汇总 开始"); 
        List<String> dateList = (List<String>) baseDAO.queryForList(
                "SUMAMOUNT_ORDER_BATCH.cpConsumeOrderSumDate", txnType);
        
        if(null != dateList && dateList.size() > 0) {
            for(String sumDate : dateList) {
                long batchNo = SerialNumberUtil.getSequence(SequenceContansts.SEQ_SUM_ORDER_BATCH);
                logger.info("批次号：" + batchNo);
                //更新消费订单表
                ConsumeOrder recordConsumeOrder = new ConsumeOrder();
                recordConsumeOrder.setSumBatchId(batchNo);
                recordConsumeOrder.setUpdatedTime(DateUtil.getCurrentDateAndTime());
                
                ConsumeOrderExample exampleConsumeOrder = new ConsumeOrderExample();
                
                exampleConsumeOrder.createCriteria().andTypeEqualTo(txnType)
                    .andTradeTimeGreaterThanOrEqualTo(sumDate + 
                        OrderDaySumBatchConstant.TRADE_TIME_PREFIX)
                    .andTradeTimeLessThanOrEqualTo(sumDate 
                            + OrderDaySumBatchConstant.TRADE_TIME_SUFFIX);
                
                consumeOrderDAO.updateByExampleSelective(recordConsumeOrder, exampleConsumeOrder);

                SumOrderBatch sumOrderBatch = new SumOrderBatch();
                
                sumOrderBatch.setId(batchNo);
                //1：正在处理  2：处理结束
                sumOrderBatch.setStatus(OrderDaySumBatchConstant.SUM_ORDER_BATCH_DOING_STATUS);
                //交易日期
                sumOrderBatch.setTradeDate(sumDate);
                Date currentTime = DateUtil.getCurrentDateAndTime();
                sumOrderBatch.setCreatedTime(currentTime);
                sumOrderBatch.setUpdatedTime(currentTime);
                sumOrderBatchDAO.insert(sumOrderBatch);
                
                InsertOrderBatchDto insertOrderBatchDto = new InsertOrderBatchDto();
                insertOrderBatchDto.setBatchNo(batchNo);
                insertOrderBatchDto.setTradeDate(sumDate);
                insertOrderBatchDto.setSumTxnTypeCode(sumTxnTypeCode);
                insertOrderBatchDto.setTxnType(txnType);
                baseDAO.insert("SUMAMOUNT_ORDER_BATCH.insertCpConsumeOrder", insertOrderBatchDto);  
                //更新处理状态为处理结束
                SumOrderBatch record = new SumOrderBatch();
                record.setStatus(OrderDaySumBatchConstant.SUM_ORDER_BATCH_DONE_STATUS);
                SumOrderBatchExample example = new SumOrderBatchExample();
                example.createCriteria().andIdEqualTo(batchNo);
                sumOrderBatchDAO.updateByExampleSelective(record, example);
                
            }
        
        } 
        logger.info("消费订单汇总 结束");
    }
    
    
    /**
     * 
     * 充值订单的汇总 <br>
     * 〈功能详细描述〉
     *
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @SuppressWarnings("unchecked")
    public void sumCpDepositOrder(String sumTxnTypeCode) {
        logger.info("充值订单汇总开始");
        List<String> dateList = (List<String>) baseDAO.queryForList(
                "SUMAMOUNT_ORDER_BATCH.cpDepositOrderDate", "");
        
        if(null != dateList && dateList.size() > 0) {
            for(String sumDate : dateList) {
                
                long batchNo = SerialNumberUtil.getSequence(SequenceContansts.SEQ_SUM_ORDER_BATCH);
                logger.info("批次号：" + batchNo);
                //更新充值订单表
                DepositOrder recordDepositOrder = new DepositOrder();
                recordDepositOrder.setSumBatchId(batchNo);
                recordDepositOrder.setUpdatedTime(DateUtil.getCurrentDateAndTime());
                
                DepositOrderExample depositOrderExample = new DepositOrderExample();
                
                depositOrderExample.createCriteria().andTradeTimeGreaterThanOrEqualTo(sumDate 
                        + OrderDaySumBatchConstant.TRADE_TIME_PREFIX)
                    .andTradeTimeLessThanOrEqualTo(sumDate 
                            + OrderDaySumBatchConstant.TRADE_TIME_SUFFIX);
                
                depositOrderDAO.updateByExampleSelective(recordDepositOrder, depositOrderExample);
                
                SumOrderBatch sumOrderBatch = new SumOrderBatch();
                
                sumOrderBatch.setId(batchNo);
                //1：正在处理  2：处理结束
                sumOrderBatch.setStatus(OrderDaySumBatchConstant.SUM_ORDER_BATCH_DOING_STATUS);
                //交易日期
                sumOrderBatch.setTradeDate(sumDate);
                Date currentTime = DateUtil.getCurrentDateAndTime();
                sumOrderBatch.setCreatedTime(currentTime);
                sumOrderBatch.setUpdatedTime(currentTime);
                sumOrderBatchDAO.insert(sumOrderBatch);
                    
                InsertOrderBatchDto insertOrderBatchDto = new InsertOrderBatchDto();
                insertOrderBatchDto.setBatchNo(batchNo);
                insertOrderBatchDto.setTradeDate(sumDate);
                insertOrderBatchDto.setSumTxnTypeCode(sumTxnTypeCode);
                baseDAO.insert("SUMAMOUNT_ORDER_BATCH.insertCpDepositOrder", insertOrderBatchDto); 
                
                //更新处理状态为处理结束
                SumOrderBatch record = new SumOrderBatch();
                record.setStatus(OrderDaySumBatchConstant.SUM_ORDER_BATCH_DONE_STATUS);
                SumOrderBatchExample example = new SumOrderBatchExample();
                example.createCriteria().andIdEqualTo(batchNo);
                sumOrderBatchDAO.updateByExampleSelective(record, example);
            }
        }
        logger.info("充值订单汇总结束");
    }
    
    /**
     * 
     * 充退订单的汇总 <br>
     * 〈功能详细描述〉
     *
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @SuppressWarnings("unchecked")
    public void sumCpDepositRefundOrder(String sumTxnTypeCode) {
        logger.info("充退订单汇总开始");
        List<String> dateList = (List<String>) baseDAO.queryForList(
                "SUMAMOUNT_ORDER_BATCH.cpDepositRefundOrderDate", "");
        
        if(null != dateList && dateList.size() > 0) {
            for(String sumDate : dateList) {
                
                long batchNo = SerialNumberUtil.getSequence(SequenceContansts.SEQ_SUM_ORDER_BATCH);
                logger.info("批次号：" + batchNo);
                
                //更新充退订单表
                DepositRefundOrder recordDepositRefundOrder = new DepositRefundOrder();
                recordDepositRefundOrder.setSumBatchId(batchNo);
                recordDepositRefundOrder.setUpdatedTime(DateUtil.getCurrentDateAndTime());
                
                DepositRefundOrderExample depositRefundOrderExample = new DepositRefundOrderExample();
                
                depositRefundOrderExample.createCriteria().andTradeTimeGreaterThanOrEqualTo(sumDate +
                        OrderDaySumBatchConstant.TRADE_TIME_PREFIX)
                    .andTradeTimeLessThanOrEqualTo(sumDate + OrderDaySumBatchConstant.TRADE_TIME_SUFFIX);
                
                depositRefundOrderDAO.updateByExampleSelective(recordDepositRefundOrder,
                        depositRefundOrderExample);
                
                SumOrderBatch sumOrderBatch = new SumOrderBatch();
                sumOrderBatch.setId(batchNo);
                //1：正在处理  2：处理结束
                sumOrderBatch.setStatus(OrderDaySumBatchConstant.SUM_ORDER_BATCH_DOING_STATUS);
                //交易日期
                sumOrderBatch.setTradeDate(sumDate);
                Date currentTime = DateUtil.getCurrentDateAndTime();
                sumOrderBatch.setCreatedTime(currentTime);
                sumOrderBatch.setUpdatedTime(currentTime);
                sumOrderBatchDAO.insert(sumOrderBatch);

                InsertOrderBatchDto insertOrderBatchDto = new InsertOrderBatchDto();
                insertOrderBatchDto.setBatchNo(batchNo);
                insertOrderBatchDto.setTradeDate(sumDate);
                insertOrderBatchDto.setSumTxnTypeCode(sumTxnTypeCode);
                baseDAO.insert("SUMAMOUNT_ORDER_BATCH.insertCpDepositRefundOrder", insertOrderBatchDto); 
                
                //更新处理状态为处理结束
                SumOrderBatch record = new SumOrderBatch();
                record.setStatus(OrderDaySumBatchConstant.SUM_ORDER_BATCH_DONE_STATUS);
                SumOrderBatchExample example = new SumOrderBatchExample();
                example.createCriteria().andIdEqualTo(batchNo);
                sumOrderBatchDAO.updateByExampleSelective(record, example);
            }
        }
        logger.info("充退订单的汇总结束");
        
    }


    /**
     * @return the consumeOrderDAO
     */
    public ConsumeOrderDAO getConsumeOrderDAO() {
        return consumeOrderDAO;
    }


    /**
     * @param consumeOrderDAO the consumeOrderDAO to set
     */
    public void setConsumeOrderDAO(ConsumeOrderDAO consumeOrderDAO) {
        this.consumeOrderDAO = consumeOrderDAO;
    }


    /**
     * @return the depositOrderDAO
     */
    public DepositOrderDAO getDepositOrderDAO() {
        return depositOrderDAO;
    }


    /**
     * @param depositOrderDAO the depositOrderDAO to set
     */
    public void setDepositOrderDAO(DepositOrderDAO depositOrderDAO) {
        this.depositOrderDAO = depositOrderDAO;
    }


    /**
     * @return the depositRefundOrderDAO
     */
    public DepositRefundOrderDAO getDepositRefundOrderDAO() {
        return depositRefundOrderDAO;
    }


    /**
     * @param depositRefundOrderDAO the depositRefundOrderDAO to set
     */
    public void setDepositRefundOrderDAO(DepositRefundOrderDAO depositRefundOrderDAO) {
        this.depositRefundOrderDAO = depositRefundOrderDAO;
    }

    /**
     * @return the sumOrderBatchDAO
     */
    public SumOrderBatchDAO getSumOrderBatchDAO() {
        return sumOrderBatchDAO;
    }

    /**
     * @param sumOrderBatchDAO the sumOrderBatchDAO to set
     */
    public void setSumOrderBatchDAO(SumOrderBatchDAO sumOrderBatchDAO) {
        this.sumOrderBatchDAO = sumOrderBatchDAO;
    }

    /**
     * @return the sumOrderResultDAO
     */
    public SumOrderResultDAO getSumOrderResultDAO() {
        return sumOrderResultDAO;
    }


    /**
     * @return the baseDAO
     */
    public BaseDAO getBaseDAO() {
        return baseDAO;
    }


    /**
     * @param baseDAO the baseDAO to set
     */
    public void setBaseDAO(BaseDAO baseDAO) {
        this.baseDAO = baseDAO;
    }


    /**
     * @param sumOrderResultDAO the sumOrderResultDAO to set
     */
    public void setSumOrderResultDAO(SumOrderResultDAO sumOrderResultDAO) {
        this.sumOrderResultDAO = sumOrderResultDAO;
    }
    
    

}

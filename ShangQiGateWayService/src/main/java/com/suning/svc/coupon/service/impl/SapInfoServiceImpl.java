/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: SapInfoServiceImpl.java
 * Author:   xuwei
 * Date:     2013-10-31 下午02:56:47
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huateng.framework.constant.OrderDaySumBatchConstant;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.util.DateUtil;
import com.suning.svc.constants.SapInfoConstants;
import com.suning.svc.coupon.constants.SequenceContansts;
import com.suning.svc.coupon.service.SapInfoService;
import com.suning.svc.coupon.util.SerialNumberUtil;
import com.suning.svc.ibatis.dao.SapInfoDAO;
import com.suning.svc.ibatis.dao.SumOrderResultDAO;
import com.suning.svc.ibatis.model.SapInfo;
import com.suning.svc.ibatis.model.SumOrderResult;
import com.suning.svc.ibatis.model.SumOrderResultExample;

/**
 * 结算信息
 * 
 * @author xuwei
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class SapInfoServiceImpl implements SapInfoService {
    private static final Logger logger = LoggerFactory.getLogger(SapInfoServiceImpl.class);

    /**
     * 基类中DAO
     */
    private BaseDAO baseDAO;
    /**
     * 记账信息Dao
     */
    private SapInfoDAO sapInfoDAO;
    /**
     * 批汇总Dao
     */
    private SumOrderResultDAO sumOrderResultDAO;

    /**
     * @return the sapInfoDAO
     */
    public SapInfoDAO getSapInfoDAO() {
        return sapInfoDAO;
    }

    /**
     * @param sapInfoDAO the sapInfoDAO to set
     */
    public void setSapInfoDAO(SapInfoDAO sapInfoDAO) {
        this.sapInfoDAO = sapInfoDAO;
    }

    /**
     * @return the sumOrderResultDAO
     */
    public SumOrderResultDAO getSumOrderResultDAO() {
        return sumOrderResultDAO;
    }

    /**
     * @param sumOrderResultDAO the sumOrderResultDAO to set
     */
    public void setSumOrderResultDAO(SumOrderResultDAO sumOrderResultDAO) {
        this.sumOrderResultDAO = sumOrderResultDAO;
    }

    /**
     * @param SumOrderResult record
     */
    @Override
    public void insert(SumOrderResult record) {
        if (record != null && !OrderDaySumBatchConstant.SUCCESS_STATUS.equals(record.getStatus().trim())) {
            SapInfo sapInfo = new SapInfo();
            // 由于Id有自增长属性，这里统一不赋值了
            // Long sId = (Long) baseDAO.queryForObject("SUMAMOUNT_ORDER_BATCH.nextMaxId", "");
            // sapInfo.setId((sId== null ? 0l : sId));
            sapInfo.setTransType(record.getTradeType());
            sapInfo.setTransSeq(record.getSerialNo());
            sapInfo.setTransDt(record.getTradeDate());
            // transCompany暂时为5101
            sapInfo.setTransCompany(record.getFatherEntityId());
            sapInfo.setPayment(OrderDaySumBatchConstant.PAYMENT);
            String amountMoneyS=String.valueOf(record.getAmount());
            Double amountMoneyD=Double.valueOf(amountMoneyS)/100;
            sapInfo.setAmount(String.valueOf(amountMoneyD));
            sapInfo.setNeedReceipt(OrderDaySumBatchConstant.NEED_RECEIPT);
            sapInfo.setFlag(OrderDaySumBatchConstant.FLAG);
            sapInfo.setVendor(record.getCustomerEntityId());
            Date date = DateUtil.getCurrentDateAndTime();
            sapInfo.setCreatedTime(date);
            sapInfo.setUpdatedTime(date);
            try {
                logger.info("开始插入SAPInfo数据！");
                sapInfoDAO.insert(sapInfo);
            } catch (Exception e) {
                logger.error("插入SAPInfo数据失败！：" + e.getMessage());
                // 如果失败，将状态置为上传失败
                SumOrderResult recordSumOrderResult1 = new SumOrderResult();
                recordSumOrderResult1.setStatus(OrderDaySumBatchConstant.FAIL_STATUE);
                SumOrderResultExample exampleSumOrderResult1 = new SumOrderResultExample();
                exampleSumOrderResult1.createCriteria().andIdEqualTo(record.getId());
                sumOrderResultDAO.updateByExampleSelective(recordSumOrderResult1, exampleSumOrderResult1);
                return;
            }
            logger.info("结束插入SAPInfo数据！");
            // 将该条记录置为已上传
            SumOrderResult recordSumOrderResult = new SumOrderResult();
            recordSumOrderResult.setStatus(OrderDaySumBatchConstant.SUCCESS_STATUS);
            SumOrderResultExample exampleSumOrderResult = new SumOrderResultExample();
            exampleSumOrderResult.createCriteria().andIdEqualTo(record.getId());
            sumOrderResultDAO.updateByExampleSelective(recordSumOrderResult, exampleSumOrderResult);
            return;
        } else {
            logger.debug("参数传递错误！");
            return;
        }
    }

    /*
     * (non-Javadoc)
     * @see com.suning.svc.coupon.service.SapInfoService#list()
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<SumOrderResult> list() {
        SumOrderResultExample example = new SumOrderResultExample();
        example.createCriteria().andStatusEqualTo(OrderDaySumBatchConstant.INIT_STATUS);
        List<SumOrderResult> sumOrderResultlist = sumOrderResultDAO.selectByExample(example);
        return sumOrderResultlist;
    }

    /*
     * (non-Javadoc)
     * @see com.suning.svc.coupon.service.SapInfoService#operator()
     */
    @Override
    public void operator() {
        try {
            SumOrderResultExample example = new SumOrderResultExample();
            example.createCriteria().andStatusNotEqualTo(OrderDaySumBatchConstant.SUCCESS_STATUS);
            // 查询记录数
            int totalNum = sumOrderResultDAO.countByExample(example);
            logger.info("共有" + totalNum + "条需要处理的记录");
            while (true) {
                List<SumOrderResult> sumOrderResultlist = sumOrderResultDAO.selectByExampleForPageList(example, 0,
                        SapInfoConstants.PAGE_SIZE);
                if (sumOrderResultlist.isEmpty()) {
                    // 查无记录则结束
                    break;
                }
                for (SumOrderResult record : sumOrderResultlist) {
                    // 逐条处理
                    insert(record);
                }
            }
            logger.info("处理完成！");
            return;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return;
        }
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

}

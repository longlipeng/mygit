/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: CommonServiceSupport.java
 * Author:   13040443
 * Date:     2013-10-29 下午02:33:04
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

import com.allinfinance.svc.coupon.dto.TradeItemTempDto;
import com.huateng.framework.util.DateUtil;
import com.suning.svc.coupon.constants.BatchConstants;
import com.suning.svc.coupon.constants.SequenceContansts;
import com.suning.svc.coupon.service.CommonSupportService;
import com.suning.svc.coupon.util.SerialNumberUtil;
import com.suning.svc.ibatis.dao.CommonSupportServiceDao;
import com.suning.svc.ibatis.dao.DealBatchDAO;
import com.suning.svc.ibatis.model.DealBatch;

/**
 * 公共的service 支撑类
 * 
 * @author yanbin
 */
public class CommonSupportServiceImpl implements CommonSupportService {

    private static final Logger log = LoggerFactory.getLogger(CommonSupportServiceImpl.class);

    private CommonSupportServiceDao commonSupportServiceDao;
    private DealBatchDAO dealBatchDAO;

    @Override
    public List<TradeItemTempDto> getGroupTradesByBatchNo(long batchNo) {
        return commonSupportServiceDao.selectGroupByBatchNo(batchNo);
    }

    @Override
    public List<TradeItemTempDto> getTradesByBatchNo(long batchNo) {
        return commonSupportServiceDao.selectByBatchNo(batchNo);
    }

    @Override
    public long getBatchNO(String tradeType, String direction) {
        log.debug("开始获取批次号。");

        Date spaceTime = DateUtil.addHours(new Date(), BatchConstants.REDEAL_TIME);

        // 获取minid 和count
        TradeItemTempDto tradeItemTempDto = commonSupportServiceDao.selectMinIdAndCount(tradeType, direction,
                spaceTime, BatchConstants.MAX_DEAL_COUNTER);
        long count = tradeItemTempDto.getCount();

        if (0 == count) {
            log.debug("已经没有需要处理的了");
            return -1;
        }

        // 生成批次
        Long batchNo = SerialNumberUtil.getSequence(SequenceContansts.SEQ_DEAL_BATCH);

        DealBatch dealBatch = new DealBatch();
        dealBatch.setId(batchNo);
        dealBatch.setBeginTime(new Date());
        // 类型设置
        dealBatch.setType(direction.replace(BatchConstants.ONE, BatchConstants.BLOCK) + tradeType);
        dealBatchDAO.insert(dealBatch);

        // 判断分页条数
        if (BatchConstants.DEAL_BATCH_SIZE >= count) {
            commonSupportServiceDao.updateBatch(batchNo, tradeType, direction, spaceTime,
                    BatchConstants.MAX_DEAL_COUNTER);
        } else {
            long minId = tradeItemTempDto.getMinId();
            long maxId = minId + BatchConstants.DEAL_BATCH_SIZE;
            commonSupportServiceDao.updateBatch(batchNo, minId, maxId, tradeType, direction, spaceTime,
                    BatchConstants.MAX_DEAL_COUNTER);
        }
        log.debug("返回到的批次号：" + batchNo);
        return batchNo;
    }

    /**
     * @return the commonSupportServiceDao
     */
    public CommonSupportServiceDao getCommonSupportServiceDao() {
        return commonSupportServiceDao;
    }

    /**
     * @param commonSupportServiceDao the commonSupportServiceDao to set
     */
    public void setCommonSupportServiceDao(CommonSupportServiceDao commonSupportServiceDao) {
        this.commonSupportServiceDao = commonSupportServiceDao;
    }

    /**
     * @return the dealBatchDAO
     */
    public DealBatchDAO getDealBatchDAO() {
        return dealBatchDAO;
    }

    /**
     * @param dealBatchDAO the dealBatchDAO to set
     */
    public void setDealBatchDAO(DealBatchDAO dealBatchDAO) {
        this.dealBatchDAO = dealBatchDAO;
    }

}

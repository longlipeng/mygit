/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: SapInfoQueryService.java
 * Author:   12073942
 * Date:     2013-10-30 下午7:46:26
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.service.sap.info;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.svc.coupon.dto.SapInfoQueryDTO;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.suning.svc.constants.SapInfoConstants;
import com.suning.svc.ibatis.model.SapInfo;

/**
 * SAP记账数据查询服务
 * 
 * @author LEO
 */
public class SapInfoQueryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SapInfoQueryService.class);

    /**
     * 分页查询DAO
     */
    private PageQueryDAO pageQueryDAO;

    /**
     * 基础DAO
     */
    private BaseDAO baseDAO;

    /**
     * 
     * 查询符合条件的列表
     * 
     * @param queryDTO 查询条件
     * @return 分页数据
     * @throws BizServiceException
     */
    public PageDataDTO inquirySapInfoList(SapInfoQueryDTO queryDTO) throws BizServiceException {
        try {
            return pageQueryDAO.query("SAP_INFO_QUERY.querySapInfoForPage", queryDTO);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new BizServiceException("查询记账表数据失败！");
        }
    }

    /**
     * 
     * 计数本次记账需要处理的数据
     * 
     * @return 记录数
     * @throws BizServiceException
     */
    public long countAccountingRecords() throws BizServiceException {
        try {
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("lastUpdatedTime", DateUtils.addMinutes(new Date(), SapInfoConstants.REDO_TIME_OFFSET));
            return (Long) baseDAO.queryForObject("SAP_INFO_QUERY.countAccountingRecords", params);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new BizServiceException("计数需要记账的数据失败！");
        }
    }

    /**
     * 
     * 分页获取本次记账需要处理的数据
     * 
     * @param pageNo 页码
     * @param pageSize 每页记录数
     * @return 分页数据
     * @throws BizServiceException
     */
    @SuppressWarnings("unchecked")
    public List<SapInfo> getAccountingRecordsList(int pageNo, int pageSize) throws BizServiceException {
        try {
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("lastUpdatedTime", DateUtils.addMinutes(new Date(), SapInfoConstants.REDO_TIME_OFFSET));
            params.put("firstCursorPosition", (pageNo - 1) * pageSize + 1);
            params.put("lastCursorPosition", pageNo * pageSize);
            return baseDAO.queryForList("SAP_INFO_QUERY.selectAccountingRecordsForPage", params);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new BizServiceException("分页获取需要记账的数据失败！");
        }
    }

    /**
     * @param pageQueryDAO the pageQueryDAO to set
     */
    public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
        this.pageQueryDAO = pageQueryDAO;
    }

    /**
     * @param baseDAO the baseDAO to set
     */
    public void setBaseDAO(BaseDAO baseDAO) {
        this.baseDAO = baseDAO;
    }

}

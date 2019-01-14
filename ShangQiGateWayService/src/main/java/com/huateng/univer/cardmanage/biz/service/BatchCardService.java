/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: BatchCardService.java
 * Author:   xuwei
 * Date:     2013-11-6 下午06:19:59
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.univer.cardmanage.biz.service;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.cardmanagement.dto.BatchCardActionDTO;
import com.huateng.framework.exception.BizServiceException;

/**
 * 发行机构卡批量作废
 *
 * @author xuwei
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface BatchCardService {
    public PageDataDTO query(BatchCardActionDTO batchCardDTO)throws BizServiceException;
    public long checkStatus(BatchCardActionDTO batchCardDTO)throws BizServiceException;
    public int checkProductId(BatchCardActionDTO batchCardDTO)throws BizServiceException;
    public void submitInvalid(BatchCardActionDTO batchCardDTO)throws BizServiceException;
}

/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: CustomerSynchService.java
 * Author:   12073942
 * Date:     2013-10-25 下午2:50:29
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.univer.synch.customer;

import com.huateng.framework.exception.BizServiceException;
import com.huateng.univer.synch.dto.CustomerSynchDTO;

/**
 * 客户下发同步服务
 * 
 * @author LEO
 */
public interface CustomerSynchService {

    /**
     * 
     * 为MDM供应商创建企业客户
     * 
     * @param dto 客户同步DTO
     * @throws BizServiceException 统一的业务异常
     */
    void customerInfoSynch(CustomerSynchDTO dto) throws BizServiceException;

}

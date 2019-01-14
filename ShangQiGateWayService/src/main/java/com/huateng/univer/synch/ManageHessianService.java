/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: ManageHessianService.java
 * Author:   lfr
 * Date:     1970-1-1 上午12:00:00
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.univer.synch;

import com.allinfinance.framework.dto.OperationResult;

/**
 * 
 * MDM同步统一服务入口
 * 
 * @author lfr
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface ManageHessianService {

    /**
     * 
     * 调用服务
     * 
     * @param txnCode 服务码
     * @param dto 传输DTO
     * @return 业务结果
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    OperationResult sendService(String txnCode, Object dto);

}

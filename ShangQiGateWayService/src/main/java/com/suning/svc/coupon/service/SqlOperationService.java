/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: SqlOperationService.java
 * Author:   秦伟
 * Date:     2013-12-16 下午5:05:05
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.service;

import com.allinfinance.svc.coupon.dto.SqlOperationDto;
import com.huateng.framework.exception.BizServiceException;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author 11051612
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface SqlOperationService {

    public boolean execute(SqlOperationDto dto) throws BizServiceException;
    
}

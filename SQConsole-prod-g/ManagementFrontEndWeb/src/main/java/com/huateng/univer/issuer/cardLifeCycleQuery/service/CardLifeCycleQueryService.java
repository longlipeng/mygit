/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: CardLifeCycleQueryService.java
 * Author:   zqs
 * Date:     2013-4-25 下午02:06:34
 * Description:      
 * History: 
 * <author>      <time>      <version>    <desc>
 * zqs             2013-4-25 下午02:06:34
 */
package com.huateng.univer.issuer.cardLifeCycleQuery.service;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.service.issueOperation.cardLifeCycleQuery.dto.CardLifeCycleQueryDTO;
import com.huateng.framework.exception.BizServiceException;

/**
 * 卡生命周期查询接口<br> 
 * 
 *
 * @author zqs
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface CardLifeCycleQueryService {
    
    /**
     * 查询卡生命周期
     * 
     * @param CardLifeCycleQueryDTO
     * @return PageDataDTO
     * @throws BizServiceException
     * */
    public PageDataDTO inqueryCardLifeCycle(CardLifeCycleQueryDTO cardLifeCycleQueryDTO)
        throws BizServiceException;

}

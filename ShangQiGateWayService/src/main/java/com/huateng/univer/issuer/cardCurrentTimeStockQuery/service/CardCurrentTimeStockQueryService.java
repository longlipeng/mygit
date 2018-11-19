/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: CardCurrentTimeStockQueryService.java
 * Author:   lbb
 * Date:     2013-7-22 下午11:14:34
 * Description:      
 * History: 
 * <author>      <time>      <version>    <desc>
 * lbb             2013-7-22 下午11:14:34
 */
package com.huateng.univer.issuer.cardCurrentTimeStockQuery.service;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.service.issueOperation.cardCurrentTimeStockQuery.dto.CardCurrentTimeStockQueryDTO;
import com.huateng.framework.exception.BizServiceException;

/**
 * 卡实时库存查询接口<br> 
 * 
 *
 * @author lbb
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface CardCurrentTimeStockQueryService {
    
    /**
     * 查询卡实时库存
     * 
     * @param CardCurrentTimeStockQueryDTO
     * @return PageDataDTO
     * @throws BizServiceException
     * */
    public PageDataDTO inqueryCardCurrentTimeStock(CardCurrentTimeStockQueryDTO cardCurrentTimeStockQueryDTO)
        throws BizServiceException;

}

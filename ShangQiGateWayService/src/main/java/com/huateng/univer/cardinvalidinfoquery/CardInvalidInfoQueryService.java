/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: CardInvalidInfoQueryService.java
 * Author:   Administrator
 * Date:     2013-11-7 下午03:05:43
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 苟昊             2013-11-7            版本号                  描述
 */
package com.huateng.univer.cardinvalidinfoquery;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderQueryDTO;
import com.huateng.framework.exception.BizServiceException;

/**
 * 卡作废查询接口<br>
 * 〈功能详细描述〉
 * 
 * @author Administrator
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface CardInvalidInfoQueryService {

    public PageDataDTO query(SellOrderQueryDTO sellOrderQueryDTO) throws BizServiceException;

    public SellOrderDTO view(SellOrderQueryDTO sellOrderQueryDTO) throws BizServiceException;

    public PageDataDTO viewCardList(SellOrderQueryDTO sellOrderQueryDTO) throws BizServiceException;
}

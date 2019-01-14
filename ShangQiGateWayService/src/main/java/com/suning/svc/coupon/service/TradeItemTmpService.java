/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: TradeDealResult.java
 * Author:   孙超
 * Date:     2013-11-5 下午10:30:01
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.service;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.svc.coupon.dto.TradeItemTempQueryDTO;

/**
 * 
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author 孙超
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface TradeItemTmpService {

    /**
     * 分页查询接收数据临时表
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    PageDataDTO getTradeItemTmpByPage(TradeItemTempQueryDTO tradeItemTempQueryDTO);

    /**
     * 删除ID
     * 
     * @param id
     */
    void delete(Long id);

}

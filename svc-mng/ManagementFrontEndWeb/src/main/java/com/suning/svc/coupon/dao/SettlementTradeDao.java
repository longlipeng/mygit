/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: TradeSettlementDao.java
 * Author:   13040443
 * Date:     2013-12-13 上午11:53:20
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.dao;

import java.util.List;
import java.util.Map;

/**
 * 自定义结算单查询dao
 * 
 * @author yanbin
 */
public interface SettlementTradeDao {

    /**
     * 获取结算反推的销售数据
     * 
     * @param id
     * @return
     */
    List<Map<String, String>> selectSettlementTrade(String mchtId, Long batchId);

}

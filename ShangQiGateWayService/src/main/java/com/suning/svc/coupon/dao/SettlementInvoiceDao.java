/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: SettlementInvoiceDao.java
 * Author:   Administrator
 * Date:     2013-11-4 下午12:58:02
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>gouhao      <time>2013-11-04      <version>    <desc>
 */
package com.suning.svc.coupon.dao;

import java.util.List;
import java.util.Map;

/**
 * 查询发票和结算单关联信息接口<br>
 * 〈功能详细描述〉
 * 
 * @author gouhao
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface SettlementInvoiceDao {
    /**
     * 通过发票号查询对应已匹配结算单
     */
    List selectSettlement(String statementName, Object parameterObject);

    /**
     * 检查结算单号所属发票是否都已开票
     */
    int checkInvoiceAll(String statementName, Object parameterObject);

    /**
     * 查询结算单下的发票信息
     */
    List<Map<String, String>> selectSettlementInvoice(long id);
}

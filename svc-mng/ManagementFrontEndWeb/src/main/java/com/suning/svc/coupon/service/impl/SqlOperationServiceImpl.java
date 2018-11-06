/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: SqlOperationServiceImpl.java
 * Author:   秦伟
 * Date:     2013-12-16 下午5:05:58
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.service.impl;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;

import com.allinfinance.svc.coupon.dto.SqlOperationDto;
import com.huateng.framework.exception.BizServiceException;
import com.suning.svc.coupon.dao.SqlOperationDao;
import com.suning.svc.coupon.service.SqlOperationService;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author 11051612
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class SqlOperationServiceImpl implements SqlOperationService {
    Logger logger = org.slf4j.LoggerFactory.getLogger(SqlOperationServiceImpl.class);

    SqlOperationDao dao;
    
    String[] permittedTable = new String[]{"CP_CARD_BATCH","CP_CONSUME_ORDER","CP_DEAL_BATCH","CP_DEPOSIT_ORDER","CP_DEPOSIT_REFUND_ORDER","CP_TRADE_ITEM_DEALED","CP_TRADE_ITEM_TEMP","CP_VIRTUAL_CARD","TB_CUSTOMER_INVOICE_INFO","TB_INVOICE","TB_INVOICE_REQUIREMENT","TB_INVOICE_REQUIREMENT_REF","TB_INVOICE_REQUIREMENT_TEMP","TB_INVOICE_TEMP","TB_PARTNER","TB_SETTLEMENT","TB_SETTLEMENT_INVOICE_REF","TB_SETTLE_BATCH","TB_SUM_ORDER_BATCH","TB_SUM_ORDER_RESULT" };
    
    /**
     * @return the dao
     */
    public SqlOperationDao getDao() {
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    public void setDao(SqlOperationDao dao) {
        this.dao = dao;
    }

    /* (non-Jsdoc)
     * @see com.suning.svc.coupon.service.SqlOperationService#execute()
     */
    @Override
    public boolean execute(SqlOperationDto dto) throws BizServiceException {
        String operation = dto.getOperation();
        String table = dto.getTable().toUpperCase();
        String others = dto.getOthers();
        if(!ArrayUtils.contains(permittedTable, table)){
            logger.warn("无操作表[{}]的权限", table);
            throw new BizServiceException("无操作表[" + table + "]的权限");
        }
        String sql = null;
        if("C".equalsIgnoreCase(operation)){
            sql = String.format("insert into %s %s", table, others);
        }else if("U".equalsIgnoreCase(operation)){
            sql = String.format("update %s %s", table, others);
        }else if("D".equalsIgnoreCase(operation)){
            sql = String.format("delete %s %s", table, others);
        }else{
            return false;
        }
        logger.warn("即将执行语句: [{}]", sql);
        try{
            dao.execute(sql);
        }catch(Exception e){
            throw new BizServiceException(e.getMessage(), e);
        }
        return true;
    }

}

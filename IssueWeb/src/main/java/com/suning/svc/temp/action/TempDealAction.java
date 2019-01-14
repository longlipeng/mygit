/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: TempDealAction.java
 * Author:   13040443
 * Date:     2013-11-20 下午03:27:43
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.temp.action;

import com.allinfinance.framework.constant.ConstCode;
import com.huateng.framework.action.BaseAction;

/**
 * 临时操作处理功能
 * 
 * @author yanbin
 */
public class TempDealAction extends BaseAction {

    private static final long serialVersionUID = 6461283438904261835L;

    /**
     * 删除实体参数数据临时功能
     */
    public void deleteEntitySystemParameter() {
        sendService(ConstCode.TEMP_DEAL_SYSTEM_PARAMTER, null);
    }

    /**
     * 删除发票需求所有开票金额为0的数据
     */
    public void deleteInvoiceRequirement() {
        sendService(ConstCode.TEMP_DEAL_DELETE_INVOIC_REQUIREMENT, null);
    }
    
}

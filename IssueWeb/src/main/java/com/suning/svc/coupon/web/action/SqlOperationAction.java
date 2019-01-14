/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: CouponSettlementAction.java
 * Author:   孙超
 * Date:     2013-10-31 下午08:50:41
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.web.action;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.OperationResult;
import com.allinfinance.svc.coupon.dto.SqlOperationDto;
import com.huateng.framework.action.BaseAction;

/**
 * 
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class SqlOperationAction extends BaseAction {

    /**
     */
    private static final long serialVersionUID = 1L;
    SqlOperationDto executor;
    
    /**
     * @return the executor
     */
    public SqlOperationDto getExecutor() {
        return executor;
    }

    /**
     * @param executor the executor to set
     */
    public void setExecutor(SqlOperationDto executor) {
        this.executor = executor;
    }

    public String toExecute(){
        return "list";
    }
    public String executeSql() {
        try {
            OperationResult result = sendService(ConstCode.SQL_EXECUTE_OPERATING, executor);
            if(result.getErrMessage() != null){
                addActionError(result.getErrMessage());
                return "list";
            }
            Boolean success = (Boolean) (result.getDetailvo());
            //ServletActionContext.getResponse().getWriter().println("执行结果:" + result);
            if(success){
                addActionMessage("操作成功");
            }else{
                addActionError("操作失败");
            }
        } catch (Exception e) { 
            e.printStackTrace();
            addActionError(e.getMessage());
        }
        return "list";
    }

}

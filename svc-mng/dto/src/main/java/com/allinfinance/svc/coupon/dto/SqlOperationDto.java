/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: SqlOperationDto.java
 * Author:   秦伟
 * Date:     2013-12-16 下午5:49:06
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.allinfinance.svc.coupon.dto;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author 11051612
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class SqlOperationDto {
    String operation;
    String table;
    String others = "";
    /**
     * @return the operation
     */
    public String getOperation() {
        return operation;
    }
    /**
     * @param operation the operation to set
     */
    public void setOperation(String operation) {
        this.operation = operation;
    }
    /**
     * @return the table
     */
    public String getTable() {
        return table;
    }
    /**
     * @param table the table to set
     */
    public void setTable(String table) {
        this.table = table;
    }
    /**
     * @return the others
     */
    public String getOthers() {
        return others;
    }
    /**
     * @param others the others to set
     */
    public void setOthers(String others) {
        this.others = others;
    }
}

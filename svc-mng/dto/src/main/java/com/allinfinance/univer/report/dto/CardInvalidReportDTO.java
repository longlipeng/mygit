/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: ctsDTO.java
 * Author:   13071598
 * Date:     2013-8-1 下午03:33:47
 * Description: //卡实时库存查询报表DTO      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 苟昊            2013-11-19                         
 */
package com.allinfinance.univer.report.dto;

import com.allinfinance.univer.report.IreportDTO;

/**
 * 〈卡作废报表DTO〉<br>
 * 〈卡作废报表DTO〉
 * 
 * @author gouhao
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CardInvalidReportDTO extends IreportDTO {

    private static final long serialVersionUID = 1L;
    /**
     * 输入条件：机构号
     * */
    private String entityId;
    /**
     * 输入条件: 开始时间
     * */
    private String startDate;
    /**
     * 输入条件： 结束时间
     */
    private String endDate;
    /**
     * 机构名称
     */
    private String entityName;
    /**
     * 作废时间
     */
    private String invalidDate;
    /**
     * 操作员
     */
    private String operator;
    /**
     * 卡号
     */
    private String cardNO;
    /**
     * 作废原因
     */
    private String memo;
    /**
     * @return the entityId
     */
    public String getEntityId() {
        return entityId;
    }
    /**
     * @param entityId the entityId to set
     */
    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }
    /**
     * @return the startDate
     */
    public String getStartDate() {
        return startDate;
    }
    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    /**
     * @return the endDate
     */
    public String getEndDate() {
        return endDate;
    }
    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    /**
     * @return the entityName
     */
    public String getEntityName() {
        return entityName;
    }
    /**
     * @param entityName the entityName to set
     */
    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }
    /**
     * @return the invalidDate
     */
    public String getInvalidDate() {
        return invalidDate;
    }
    /**
     * @param invalidDate the invalidDate to set
     */
    public void setInvalidDate(String invalidDate) {
        this.invalidDate = invalidDate;
    }
    /**
     * @return the operator
     */
    public String getOperator() {
        return operator;
    }
    /**
     * @param operator the operator to set
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }
    /**
     * @return the cardNO
     */
    public String getCardNO() {
        return cardNO;
    }
    /**
     * @param cardNO the cardNO to set
     */
    public void setCardNO(String cardNO) {
        this.cardNO = cardNO;
    }
    /**
     * @return the memo
     */
    public String getMemo() {
        return memo;
    }
    /**
     * @param memo the memo to set
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }
    
    
    
}

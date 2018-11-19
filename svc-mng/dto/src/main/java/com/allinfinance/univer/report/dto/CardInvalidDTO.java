/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: CardInvalidDTO.java
 * Author:   Administrator
 * Date:     2013-11-15 下午03:15:44
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author> gouhao     <time>2013-11-15      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.allinfinance.univer.report.dto;

import com.allinfinance.univer.report.IreportDTO;

/**
 * 卡作废报表DTO<br> 
 * 〈功能详细描述〉
 *
 * @author Administrator
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CardInvalidDTO extends IreportDTO{
    /**
     * 版本.
     */
    private static final long serialVersionUID = 1L;
    /**
     * 机构ID.
     */
    private String entityId;
    /**
     * 开始日期.
     */
    private String startDate;
    /**
     * 结束日期.
     */
    private String endDate;
    
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
    
    
}

/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: BatchCardActionDTO.java
 * Author:   xuwei
 * Date:     2013-11-6 下午05:49:45
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.allinfinance.univer.cardmanagement.dto;

import com.allinfinance.framework.dto.PageQueryDTO;

import java.io.Serializable;

/**
 * 卡批量作废查询DTO
 *
 * @author xuwei
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class BatchCardActionDTO extends PageQueryDTO implements Serializable {

    /**
     */
    private static final long serialVersionUID = 1L;
    /**
     * 起始号码
     */
    private String startCardNo;
    /**
     * 结束号码
     */
    private String endCardNo;
    /**
     * 处理机构
     */
    private String issuerId;
    /**
     * 接收卡号段
     */
    private String[] cardNoArray;
    /**
     * 操作人
     */
    private String user;
    /**
     * 备注
     */
    private String memo;
    /**
     *作废数量 
     */
    private String maxNum;
    /**
     * @return the startCardNo
     */
    public String getStartCardNo() {
        return startCardNo;
    }
    /**
     * @param startCardNo the startCardNo to set
     */
    public void setStartCardNo(String startCardNo) {
        this.startCardNo = startCardNo;
    }
    /**
     * @return the endCardNo
     */
    public String getEndCardNo() {
        return endCardNo;
    }
    /**
     * @param endCardNo the endCardNo to set
     */
    public void setEndCardNo(String endCardNo) {
        this.endCardNo = endCardNo;
    }
    /**
     * @return the issuerId
     */
    public String getIssuerId() {
        return issuerId;
    }
    /**
     * @param issuerId the issuerId to set
     */
    public void setIssuerId(String issuerId) {
        this.issuerId = issuerId;
    }
    /**
     * @return the cardNoArray
     */
    public String[] getCardNoArray() {
        return cardNoArray;
    }
    /**
     * @param cardNoArray the cardNoArray to set
     */
    public void setCardNoArray(String[] cardNoArray) {
        this.cardNoArray = cardNoArray;
    }
    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }
    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
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
    /**
     * @return the maxNum
     */
    public String getMaxNum() {
        return maxNum;
    }
    /**
     * @param maxNum the maxNum to set
     */
    public void setMaxNum(String maxNum) {
        this.maxNum = maxNum;
    }
   
    

}

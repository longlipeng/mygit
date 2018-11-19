/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: MerchantSynchDTO.java
 * Author:   lfr
 * Date:     1970-1-1 上午12:00:00
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.univer.synch.dto;

import java.io.Serializable;

/**
 * 
 * 商户同步DTO
 * 
 * @author lfr
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class MerchantSynchDTO implements Serializable {

    private static final long serialVersionUID = -5750099441228217546L;

    /**
     * 商户编号（供应商编码）
     */
    private String entityId;
    /**
     * 收单机构号(一个收单)
     */
    private String fatherEntityId;
    /**
     * 商户名（供应商名称
     */
    private String merchantName;
    /**
     * 商户号（搜索项1）
     */
    private String merchantCode;
    /**
     * 商户地址
     */
    private String merchantAddress;
    /**
     * 数据状态
     */
    private String dataState;
    /**
     * 商户类别：1-联营，0-默认
     */
    private String merchantType;
    /**
     * 20131024 同步标识
     */
    private String synchFlag;

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
     * @return the fatherEntityId
     */
    public String getFatherEntityId() {
        return fatherEntityId;
    }

    /**
     * @param fatherEntityId the fatherEntityId to set
     */
    public void setFatherEntityId(String fatherEntityId) {
        this.fatherEntityId = fatherEntityId;
    }

    /**
     * @return the merchantName
     */
    public String getMerchantName() {
        return merchantName;
    }

    /**
     * @param merchantName the merchantName to set
     */
    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    /**
     * @return the merchantCode
     */
    public String getMerchantCode() {
        return merchantCode;
    }

    /**
     * @param merchantCode the merchantCode to set
     */
    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    /**
     * @return the merchantAddress
     */
    public String getMerchantAddress() {
        return merchantAddress;
    }

    /**
     * @param merchantAddress the merchantAddress to set
     */
    public void setMerchantAddress(String merchantAddress) {
        this.merchantAddress = merchantAddress;
    }

    /**
     * @return the dataState
     */
    public String getDataState() {
        return dataState;
    }

    /**
     * @param dataState the dataState to set
     */
    public void setDataState(String dataState) {
        this.dataState = dataState;
    }

    /**
     * @return the merchantType
     */
    public String getMerchantType() {
        return merchantType;
    }

    /**
     * @param merchantType the merchantType to set
     */
    public void setMerchantType(String merchantType) {
        this.merchantType = merchantType;
    }

    /**
     * @return the synchFlag
     */
    public String getSynchFlag() {
        return synchFlag;
    }

    /**
     * @param synchFlag the synchFlag to set
     */
    public void setSynchFlag(String synchFlag) {
        this.synchFlag = synchFlag;
    }

}
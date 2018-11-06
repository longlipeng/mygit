/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: das.java
 * Author:   13071598
 * Date:     2013-8-1 下午04:12:40
 * Description: //卡实时库存查询页面      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 李斌斌             2013-08-01          
 */
package com.allinfinance.service.issueOperation.cardCurrentTimeStockQuery.dto;

import com.allinfinance.framework.dto.PageQueryDTO;

/**
 * 〈卡实时库存查询页面   〉<br> 
 * 〈卡实时库存查询页面  DTO〉
 *
 * @author 13071598
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CardCurrentTimeStockQueryDTO extends PageQueryDTO{

    private static final long serialVersionUID = 2235685692201572706L;
    
    /**
     * 输入条件：机构号
     * */
    private String issuerId;
    /**
     * 输入条件：产品名称
     * */
    private String productName;
    /**
     * 输入条件：实体类型
     * */
    private String functionRoleId;
    /**
     * 署名类型
     * */
    private String onymousStat;
    /**
     * 产品类别
     * */
    private String productType;
    /**
     * 面额类型
     * */
    private String faceValueType;
    /**
     * 面额值
     * */
    private String faceValue;
    /**
     * 实时库存量
     * */
    private String stockValue;
    
    
    public String getFunctionRoleId() {
        return functionRoleId;
    }
    public void setFunctionRoleId(String functionRoleId) {
        this.functionRoleId = functionRoleId;
    }
    public String getIssuerId() {
        return issuerId;
    }
    public void setIssuerId(String issuerId) {
        this.issuerId = issuerId;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getOnymousStat() {
        return onymousStat;
    }
    public void setOnymousStat(String onymousStat) {
        this.onymousStat = onymousStat;
    }
    public String getProductType() {
        return productType;
    }
    public void setProductType(String productType) {
        this.productType = productType;
    }
    public String getFaceValueType() {
        return faceValueType;
    }
    public void setFaceValueType(String faceValueType) {
        this.faceValueType = faceValueType;
    }
    public String getFaceValue() {
        return faceValue;
    }
    public void setFaceValue(String faceValue) {
        this.faceValue = faceValue;
    }
    public String getStockValue() {
        return stockValue;
    }
    public void setStockValue(String stockValue) {
        this.stockValue = stockValue;
    }
}

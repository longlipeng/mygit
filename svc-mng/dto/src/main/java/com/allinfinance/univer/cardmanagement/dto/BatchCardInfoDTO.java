/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: BatchCardInfoDTO.java
 * Author:   xuwei
 * Date:     2013-11-8 下午03:51:17
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.allinfinance.univer.cardmanagement.dto;

import java.io.Serializable;

/**
 * 卡信息输出DTO
 *
 * @author xuwei
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class BatchCardInfoDTO implements Serializable {
   /**
     */
   private static final long serialVersionUID = 1L;
   /**卡号*/
   private String cardNo;
   /**产品ID*/
   private String productId;
   /**发卡机构号*/
   private String issuerId;
   /**卡状态*/
   private String cardStat;
/**
 * @return the cardNo
 */
public String getCardNo() {
    return cardNo;
}
/**
 * @param cardNo the cardNo to set
 */
public void setCardNo(String cardNo) {
    this.cardNo = cardNo;
}
/**
 * @return the productId
 */
public String getProductId() {
    return productId;
}
/**
 * @param productId the productId to set
 */
public void setProductId(String productId) {
    this.productId = productId;
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
 * @return the cardStat
 */
public String getCardStat() {
    return cardStat;
}
/**
 * @param cardStat the cardStat to set
 */
public void setCardStat(String cardStat) {
    this.cardStat = cardStat;
}

   
}

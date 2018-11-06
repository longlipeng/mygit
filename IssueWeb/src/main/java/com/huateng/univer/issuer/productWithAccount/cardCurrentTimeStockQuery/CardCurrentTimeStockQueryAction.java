/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: qwe.java
 * Author:   13071598
 * Date:     2013-8-1 下午04:00:50
 * Description: //卡实时库存查询页面Action     
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 李斌斌             2013-08-01            
 */
package com.huateng.univer.issuer.productWithAccount.cardCurrentTimeStockQuery;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.service.issueOperation.cardCurrentTimeStockQuery.dto.CardCurrentTimeStockQueryDTO;
import com.allinfinance.univer.issuer.dto.product.ProductQueryDTO;
import com.allinfinance.univer.seller.seller.dto.SellerQueryDTO;
import com.huateng.framework.action.BaseAction;
import com.huateng.framework.util.TxnType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * 〈卡实时库存查询页面Action〉<br>
 * 〈功能详细描述〉
 * 
 * @author 13071598
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CardCurrentTimeStockQueryAction extends BaseAction {

    /**
     */
    private static final long serialVersionUID = 1L;

    private Logger logger = Logger.getLogger(CardCurrentTimeStockQueryAction.class);

    /**
     * 卡实时库存查询的DTO
     */
    private CardCurrentTimeStockQueryDTO cardCurrentTimeStockQueryDTO = new CardCurrentTimeStockQueryDTO();

    /**
     * 查询信息DTO
     */
    private PageDataDTO pageDataDTO = new PageDataDTO();
    /**
     * 获得营销机构DTO
     * */
    private SellerQueryDTO sellerQueryDTO = new SellerQueryDTO();

    /**
     * 分页总行数
     */
    private int totalRows;

    /**
     * 实体类型
     * */
    private String functionRoleId;
    
    /**
     * 所属营销机构
     */
    List<Map<String, String>> entityList = new ArrayList<Map<String, String>>();
    
    
    
    public List<Map<String, String>> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<Map<String, String>> entityList) {
        this.entityList = entityList;
    }

    public String getFunctionRoleId() {
        return functionRoleId;
    }

    public void setFunctionRoleId(String functionRoleId) {
        this.functionRoleId = functionRoleId;
    }

    /**
     * 
     * 卡实时库存查询页面 <br>
     * 
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String cardCurrentTimeStockQueryInit() {
        if(functionRoleId.equals("2")){
            return "issuerInput";
        }
        if(functionRoleId.equals("3")){
            sellerQueryDTO.setEntityId(getUser().getEntityId());
            entityList = getSellerList(sellerQueryDTO);
            return "sellerInput";
        }else{
            return "";
        }
    }

    /**
     * 卡实时库存查询
     */
    @SuppressWarnings("unchecked")
    public String queryCardCurrentTimeStock() {
        if (null == cardCurrentTimeStockQueryDTO) {
            cardCurrentTimeStockQueryDTO = new CardCurrentTimeStockQueryDTO();
        }
        if (StringUtils.isBlank(cardCurrentTimeStockQueryDTO.getProductName())) {
            cardCurrentTimeStockQueryInit();
        }
        //cardCurrentTimeStockQueryDTO.setIssuerId(getUser().getEntityId());
        if(functionRoleId.equals("2")){
            cardCurrentTimeStockQueryDTO.setIssuerId(getUser().getEntityId());
        }
        cardCurrentTimeStockQueryDTO.setFunctionRoleId(functionRoleId);
        try {
            ListPageInit("cardCurrentTimeStockQueryTable", cardCurrentTimeStockQueryDTO);
        } catch (Exception e) {
            logger.error("初始化错误:" + e.getMessage());
            addActionError("初始化错误");
            cardCurrentTimeStockQueryInit();
        }
        pageDataDTO = (PageDataDTO) sendService(ConstCode.CARDCURRENTTIMESTOCK_QUERY, cardCurrentTimeStockQueryDTO)
                .getDetailvo();
        if (null != pageDataDTO) {
            List<Map<String, Object>> list = (List<Map<String, Object>>) pageDataDTO.getData();
            int size = list.size();
            List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < size; i++) {
                Map<String, Object> newMap = new HashMap<String, Object>();
                Map<String, Object> map = list.get(i);
                Iterator it = map.entrySet().iterator();
                while (it.hasNext()) {
                    Entry entry = (Entry) it.next();
                    String key = (String) entry.getKey();
                    Object value = entry.getValue();
                    // 交易类型转换中文
                    if ("txnType".equals(key)) {
                        value = TxnType.getMap().get(value);
                    }
                    newMap.put(key, value);
                }
                newList.add(newMap);
            }
            pageDataDTO.setData(newList);
            totalRows = pageDataDTO.getTotalRecord();
        } else {
            logger.error("系统异常");
            addActionError("系统异常");
        }
       return  cardCurrentTimeStockQueryInit();
    }
    
    
    /**
     * 获得营销机构及其下属机构
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, String>> getSellerList(
            SellerQueryDTO sellerQueryDTO) {

        List<SellerQueryDTO> sDTO = (List<SellerQueryDTO>) sendService(
                ConstCode.SELLER_LIST_QUERY, sellerQueryDTO)
                .getDetailvo();
        List<Map<String, String>> sellerList = new ArrayList<Map<String, String>>();
        for (SellerQueryDTO serviceDto : sDTO) {
            Map<String, String> seller = new HashMap<String, String>();
            seller.put("entityId", serviceDto.getEntityId());
            seller.put("entityName", serviceDto.getSellerName());
            sellerList.add(seller);
        }
        return sellerList;
    }
    

    public CardCurrentTimeStockQueryDTO getCardCurrentTimeStockQueryDTO() {
        return cardCurrentTimeStockQueryDTO;
    }

    public void setCardCurrentTimeStockQueryDTO(CardCurrentTimeStockQueryDTO cardCurrentTimeStockQueryDTO) {
        this.cardCurrentTimeStockQueryDTO = cardCurrentTimeStockQueryDTO;
    }

    public PageDataDTO getPageDataDTO() {
        return pageDataDTO;
    }

    public void setPageDataDTO(PageDataDTO pageDataDTO) {
        this.pageDataDTO = pageDataDTO;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }
}

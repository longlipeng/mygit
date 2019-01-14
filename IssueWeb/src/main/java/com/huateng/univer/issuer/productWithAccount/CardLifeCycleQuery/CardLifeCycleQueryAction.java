/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: CardLifeCycleQueryAction.java
 * Author:   zqs
 * Date:     2013-4-25 上午09:51:08
 * Description:  
 * History: 
 * <author>      <time>      <version>    <desc>
 * zqs             2013-4-25 上午09:51:08                 
 */
package com.huateng.univer.issuer.productWithAccount.CardLifeCycleQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.service.issueOperation.cardLifeCycleQuery.dto.CardLifeCycleQueryDTO;
import com.huateng.framework.action.BaseAction;
import com.huateng.framework.util.TxnType;

/**
 * 卡生命周期查询  Action<br> 
 * 发行机构查询本机构下所发行的卡片的整个卡生命周期。只支持单张卡查询。
 *
 * @author zqs
 * @see 
 * @since 
 */
public class CardLifeCycleQueryAction extends BaseAction {
    
    private Logger logger = Logger.getLogger(CardLifeCycleQueryAction.class);
    
    private static final long serialVersionUID = 1242914610547362961L;
    
    /**
     * 卡生命周期查询的DTO
     */
    private CardLifeCycleQueryDTO cardLifeCycleQueryDTO = new CardLifeCycleQueryDTO();
    
    /**
     * 查询信息DTO
     */
    private PageDataDTO pageDataDTO = new PageDataDTO();
    
    /**
     * 分页总行数
     */
    private int totalRows;
 
    /**
     * 
     * 卡生命周期页面 <br>
     * 
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String cardLifeCycleQueryInit() {
        return INPUT;
      
    }
    
    /**
     * 卡生命周期查询
     */
    @SuppressWarnings("unchecked")
    public String queryCardLifeCycle() {
        if(null == cardLifeCycleQueryDTO) {
            cardLifeCycleQueryDTO = new CardLifeCycleQueryDTO();
        }
        if(StringUtils.isBlank(cardLifeCycleQueryDTO.getCardNo())) {
            return INPUT;
        }
        cardLifeCycleQueryDTO.setIssuerId(getUser().getEntityId());
        try {
            ListPageInit("cardCycleQueryTable", cardLifeCycleQueryDTO);
        } catch (Exception e) {
            logger.error("初始化错误:" + e.getMessage());
            addActionError("初始化错误");
            return INPUT;
        }
        pageDataDTO = (PageDataDTO) sendService(ConstCode.CARDLIFECYCLE_QUERY,
                cardLifeCycleQueryDTO).getDetailvo(); 
        if (null != pageDataDTO) {
            List<Map<String, Object>> list = (List<Map<String, Object>>) pageDataDTO.getData();
            int size = list.size();
            List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
            for(int i = 0; i < size; i++) {
                Map<String, Object> newMap = new HashMap<String, Object>();
                Map<String, Object> map = list.get(i);
                Iterator it = map.entrySet().iterator(); 
                while (it.hasNext()) { 
                    Entry entry = (Entry) it.next(); 
                    String key = (String) entry.getKey() ; 
                    Object value = entry.getValue();
                    //交易类型转换中文
                    if("txnType".equals(key)) {
                        value = TxnType.getMap().get(value);
                    }
                    newMap.put(key, value);
                }
                newList.add(newMap);  
            }
            pageDataDTO.setData(newList);
            totalRows = pageDataDTO.getTotalRecord();  
        }
        else {
            logger.error("系统异常");
            addActionError("系统异常");
        }
        return "list";
    }


    public CardLifeCycleQueryDTO getCardLifeCycleQueryDTO() {
        return cardLifeCycleQueryDTO;
    }

    public void setCardLifeCycleQueryDTO(CardLifeCycleQueryDTO cardLifeCycleQueryDTO) {
        this.cardLifeCycleQueryDTO = cardLifeCycleQueryDTO;
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

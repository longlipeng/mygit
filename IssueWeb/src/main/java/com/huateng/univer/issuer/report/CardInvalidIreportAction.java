/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: CardinvalidIreportAction.java
 * Author:   Administrator
 * Date:     2013-11-15 下午02:16:40
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author> gouhao <time>2013-11-15 <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.univer.issuer.report;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.univer.report.dto.CardInvalidDTO;
import com.allinfinance.univer.seller.seller.dto.SellerQueryDTO;
import com.huateng.framework.constant.CardInvalidConst;
import com.huateng.univer.report.action.NewIreportAction;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;



/**
 * 卡作废报表<br>
 * 〈功能详细描述〉
 * 
 * @author Administrator
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CardInvalidIreportAction extends NewIreportAction {
    /**
     * 版本标记.
     */
    private static final long serialVersionUID = 1L;
    /**
     * 营销机构查询DTO
     */
    private SellerQueryDTO sellerQueryDTO = new SellerQueryDTO();
    /**
     * 机构下拉框List
     */
    private List<SellerQueryDTO> sellerQueryDTOs = new ArrayList<SellerQueryDTO>();
    /**
     * 卡作废DTO
     */
    private CardInvalidDTO cardInvalidDTO = new CardInvalidDTO();
    /**
     * 标记位 0--发行机构卡作废报表;1--营销机构卡作废报表
     */
    private String flag;

    /**
     * 
     * 设置报表基本信息<br>
     * 〈功能详细描述〉
     * 
     * @return
     * @throws Exception
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String inQuery() throws Exception {
        queryInit();
        if (isNotEmpty(flag)) {

            if (CardInvalidConst.ISSUE_IREPORT.equals(flag)) {
                cardInvalidDTO.setReportName("issue_card_invalid");
                cardInvalidDTO.setReportFileName("发行机构卡作废报表");
            } else if (CardInvalidConst.SELL_IREPORT.equals(flag)) {
                cardInvalidDTO.setReportName("sell_card_invalid");
                cardInvalidDTO.setReportFileName("营销机构卡作废报表");
            }
        }
        cardInvalidDTO.setReportType("xls");
        cardInvalidDTO.setIssuerId(getUser().getEntityId());
        cardInvalidDTO.setIssuerName(getUser().getIssuerName());

        return "list";
    }

    @Override
    protected JSONObject getJSONOBJect() {
        return JSONObject.fromObject(cardInvalidDTO);
    }

    /**
     * 获取机构下拉框信息
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @SuppressWarnings("unchecked")
    public void queryInit() {
        sellerQueryDTO.setEntityId(getUser().getEntityId());
        sellerQueryDTOs = (List<SellerQueryDTO>) sendService(ConstCode.SELLER_LIST_QUERY, sellerQueryDTO).getDetailvo();
    }

    /**
     * @return the sellerQueryDTO
     */
    public SellerQueryDTO getSellerQueryDTO() {
        return sellerQueryDTO;
    }

    /**
     * @param sellerQueryDTO the sellerQueryDTO to set
     */
    public void setSellerQueryDTO(SellerQueryDTO sellerQueryDTO) {
        this.sellerQueryDTO = sellerQueryDTO;
    }

    /**
     * @return the sellerQueryDTOs
     */
    public List<SellerQueryDTO> getSellerQueryDTOs() {
        return sellerQueryDTOs;
    }

    /**
     * @param sellerQueryDTOs the sellerQueryDTOs to set
     */
    public void setSellerQueryDTOs(List<SellerQueryDTO> sellerQueryDTOs) {
        this.sellerQueryDTOs = sellerQueryDTOs;
    }

    /**
     * @return the cardInvalidDTO
     */
    public CardInvalidDTO getCardInvalidDTO() {
        return cardInvalidDTO;
    }

    /**
     * @param cardInvalidDTO the cardInvalidDTO to set
     */
    public void setCardInvalidDTO(CardInvalidDTO cardInvalidDTO) {
        this.cardInvalidDTO = cardInvalidDTO;
    }

    /**
     * @return the flag
     */
    public String getFlag() {
        return flag;
    }

    /**
     * @param flag the flag to set
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }

}

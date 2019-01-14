/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: BatchCardManageAction.java
 * Author:   xuwei
 * Date:     2013-11-6 下午03:27:51
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.univer.cardmanage;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.cardmanagement.dto.BatchCardActionDTO;
import com.allinfinance.univer.seller.seller.dto.SellerDTO;
import com.allinfinance.univer.system.sysparam.dto.EntitySystemParameterDTO;
import com.huateng.framework.action.BaseAction;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.constant.SystemInfoConstants;
import com.huateng.framework.util.SystemInfo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 发行机构--卡片管理--卡作废
 *
 * @author xuwei
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class BatchCardManageAction extends BaseAction{
    /**
     */
    private static final long serialVersionUID = 1L;
    private Logger logger = Logger.getLogger(CardManageAction.class);
    /**
     * DTO
     */
    private BatchCardActionDTO batchCardDTO;
    /**
     * 页面返回值DTO
     */
    private PageDataDTO pageDataDTO;
    /**
     * 返回值数量
     */
    private int totalRows=0;
    /**
     * 用来接收选择的数组
     */
    private String[] cardNoArray;
     /**
      * 用来存储作废原因
      */
    private String invalidReason;
    /**
     * 初始化
     */
    public String batchCardManageInit(){
        return "init";
    }
    /**
     * 卡号验证
     */
    public void checkCardNo(){
        String cardNoRule="^[0-9]{13,19}$";
        if(batchCardDTO.getStartCardNo()==null||!batchCardDTO.getStartCardNo().trim().matches(cardNoRule)||"".equals(batchCardDTO.getStartCardNo().trim())){
            addFieldError("batchCardDTO.startCardNo", "请输入格式正确的卡号(13-19位数字)");
        }
        if(batchCardDTO.getEndCardNo()==null||!batchCardDTO.getEndCardNo().trim().matches(cardNoRule)||"".equals(batchCardDTO.getEndCardNo().trim())){
            addFieldError("batchCardDTO.endCardNo", "请输入格式正确的卡号(13-19位数字)");
        }
    }
    /**
     * 卡号验证
     */
    public void check(){
        BigInteger startCardNo=new BigInteger(batchCardDTO.getStartCardNo().trim());
        BigInteger endCardNo=new BigInteger(batchCardDTO.getEndCardNo().trim());
        if(startCardNo.compareTo(endCardNo)==OrderConst.COMPARE_VALUE){
            addActionError("起始卡号不能大于结束卡号！");
        }
    }
    /**
     * 卡片查询
     * */
    public String queryCard(){
        try{
            checkCardNo();
            if(hasFieldErrors()){
                return "init";
            }
            check();
            if(hasActionErrors()){
                return "init";
            }
            ListPageInit("batchCardList",batchCardDTO);
            batchCardDTO.setIssuerId(getUser().getEntityId());
            pageDataDTO=(PageDataDTO)sendService(ConstCode.BATCH_CARD, batchCardDTO).getDetailvo();
            totalRows=pageDataDTO.getTotalRecord();
        }catch(Exception e){
            logger.error(e.getMessage());
        }
        return "list";
    }
    /**
     * 卡片查询
     * */
    public String queryCardDatail(){
        try{
            checkCardNo();
            if(hasFieldErrors()){
                return "input";
            }
            check();
            if(hasActionErrors()){
                return "input";
            }
            ListPageInit("batchCardList",batchCardDTO);
            batchCardDTO.setIssuerId(getUser().getEntityId());
            batchCardDTO.setUser(getUser().getUserId());
            pageDataDTO=(PageDataDTO)sendService(ConstCode.BATCH_CARD, batchCardDTO).getDetailvo();
            totalRows=pageDataDTO.getTotalRecord();
        }catch(Exception e){
            logger.error(e.getMessage());
        }
        return "list";
    }
    /**
     * 卡片作废
     * */
    public String invaild(){
        String cardTotalInteger=null;
        Map<String, List<EntitySystemParameterDTO>> map = SystemInfo.getEntityParameters();
        List<EntitySystemParameterDTO> list = map.get(getUser().getEntityId());
        if (null == list || list.size() == 0) {
            SellerDTO sellerDTO = new SellerDTO();
            sellerDTO.setEntityId(getUser().getEntityId());
            list = getEntitySystemParameter(sellerDTO, map);
        }
        for (EntitySystemParameterDTO entitySystemParameterDTO : list) {
            if (SystemInfoConstants.MAX_INVALID_NUM.equals(entitySystemParameterDTO.getParameterCode())) {
                cardTotalInteger = entitySystemParameterDTO.getParameterValue();
                break;
            }
        }
        batchCardDTO.setIssuerId(getUser().getEntityId());
        batchCardDTO.setUser(getUser().getUserId());
        batchCardDTO.setMemo(invalidReason);
        batchCardDTO.setCardNoArray(cardNoArray);
        batchCardDTO.setMaxNum(cardTotalInteger);
        sendService(ConstCode.INVALID,batchCardDTO);
        if(hasActionErrors()){
            return "input";
        }else{
            addActionMessage("卡片作废成功！");
        }
        return "list";
    }
    /**
     * 递归获取
     * 
     * @param sellerDTO
     * @param map
     * @return
     */
    private List<EntitySystemParameterDTO> getEntitySystemParameter(SellerDTO sellerDTO,
            Map<String, List<EntitySystemParameterDTO>> map) {
        sellerDTO = (SellerDTO) sendService(ConstCode.SELLER_SERVICE_INQUERY_ENTITY, sellerDTO).getDetailvo();
        List<EntitySystemParameterDTO> list = new ArrayList<EntitySystemParameterDTO>();
        if (null != sellerDTO) {
            list = map.get(sellerDTO.getFatherEntityId());
            if (null == list || list.size() == 0) {
                sellerDTO.setEntityId(sellerDTO.getFatherEntityId());
                list = getEntitySystemParameter(sellerDTO, map);
            }
        }
        return list;
    }
    /**
     * @return the batchCardDTO
     */
    public BatchCardActionDTO getBatchCardDTO() {
        return batchCardDTO;
    }
    /**
     * @param batchCardDTO the batchCardDTO to set
     */
    public void setBatchCardDTO(BatchCardActionDTO batchCardDTO) {
        this.batchCardDTO = batchCardDTO;
    }
    
    /**
     * @return the pageDataDTO
     */
    public PageDataDTO getPageDataDTO() {
        return pageDataDTO;
    }
    /**
     * @param pageDataDTO the pageDataDTO to set
     */
    public void setPageDataDTO(PageDataDTO pageDataDTO) {
        this.pageDataDTO = pageDataDTO;
    }
    /**
     * @return the totalRows
     */
    public int getTotalRows() {
        return totalRows;
    }
    /**
     * @param totalRows the totalRows to set
     */
    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
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
     * @return the invalidReason
     */
    public String getInvalidReason() {
        return invalidReason;
    }
    /**
     * @param invalidReason the invalidReason to set
     */
    public void setInvalidReason(String invalidReason) {
        this.invalidReason = invalidReason;
    }
   
    
}

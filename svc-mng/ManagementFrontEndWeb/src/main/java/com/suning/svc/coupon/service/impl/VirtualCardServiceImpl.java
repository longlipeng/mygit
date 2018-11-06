/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: CardServiceImpl.java
 * Author:   孙超
 * Date:     2013-10-28 下午08:31:25
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.svc.coupon.dto.VirtualCardQueryDto;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.ibatis.dao.EntitySystemParameterDAO;
import com.huateng.framework.ibatis.dao.ProductCardBinDAO;
import com.huateng.framework.ibatis.dao.ProductDAO;
import com.huateng.framework.ibatis.dao.SystemParameterDAO;
import com.huateng.framework.ibatis.model.EntitySystemParameter;
import com.huateng.framework.ibatis.model.EntitySystemParameterKey;
import com.huateng.framework.ibatis.model.ProductCardBin;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.LUHNGenerator;
import com.huateng.framework.util.StringUtil;
import com.suning.framework.lang.AppException;
import com.suning.svc.core.template.CallBack;
import com.suning.svc.core.template.OneByOne;
import com.suning.svc.core.template.OneByOneTemplate;
import com.suning.svc.coupon.constants.CardConstants;
import com.suning.svc.coupon.constants.OrgnizationConstants;
import com.suning.svc.coupon.constants.SequenceContansts;
import com.suning.svc.coupon.service.VirtualCardService;
import com.suning.svc.ibatis.dao.CardBatchDAO;
import com.suning.svc.ibatis.dao.VirtualCardDAO;
import com.suning.svc.ibatis.model.CardBatch;
import com.suning.svc.ibatis.model.VirtualCard;
import com.suning.svc.ibatis.model.VirtualCardExample;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author 孙超
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class VirtualCardServiceImpl implements VirtualCardService {

    private static final Logger log = LoggerFactory.getLogger(VirtualCardServiceImpl.class);
    private VirtualCardDAO virtualCardDAO;
    private CardBatchDAO cardBatchDAO;
    private OneByOneTemplate oneByOneTemplate;
    private SystemParameterDAO systemParameterDAO;
    private ProductDAO productDAO;
    private ProductCardBinDAO productCardBinDAO;
    private BaseDAO baseDAO;
    private CommonsDAO commonsDAO;
    private PageQueryDAO pageQueryDAO;
    private EntitySystemParameterDAO entitySystemParameterDAO;
    private TransactionTemplate cardManageTransactionTemplate;

    /*
     * (non-Javadoc)
     * @see com.suning.svc.coupon.service.VirtualCardService#getCardToUse()
     */
    @Override
    public List<VirtualCard> getCardToUse(long productId) throws Exception {
        List<VirtualCard> downCards = canCardCanUsed(productId);
        if (downCards != null && downCards.size() > 0) {
            return downCards;
        }
        // 为产品productId制1000张卡
        makeCards(productId, CardConstants.MAKE_NUM);
        return canCardCanUsed(productId);
    }

    /*
     * (non-Javadoc)
     * @see com.suning.svc.coupon.service.CardService#countCardCanUsed()
     */
    @Override
    public List<VirtualCard> canCardCanUsed(final long productId) throws Exception {
        return oneByOneTemplate.execute(new OneByOne(CardConstants.CARD_BIZ_TYPE, CardConstants.CARD_BIZ_ID, ""),
                new CallBack<List<VirtualCard>>() {
                    public List<VirtualCard> invoke() {
                        Map<String, Object> queryMap = new HashMap<String, Object>();
                        queryMap.put("cardState", CardConstants.CARD_LOADED);
                        queryMap.put("productId", productId);
                        queryMap.put("compareTime", DateUtil.addHours(new Date(), CardConstants.CARD_REUSE_TIME));
                        Integer count = (Integer) baseDAO.queryForObject("VIRTUALCARD.countCardCanBeUsed", queryMap);
                        log.info("超过24小时且状态为内存中的卡张数为{}", count);
                        if (count >= CardConstants.MAKE_NUM) {
                            @SuppressWarnings("unchecked")
                            List<VirtualCard> virtualCardList = (List<VirtualCard>) baseDAO.queryForList(
                                    "VIRTUALCARD.queryCardCanNoUsed", queryMap);
                            for (VirtualCard card : virtualCardList) {
                                card.setUpdatedTime(new Date());
                            }
                            int updateCount = commonsDAO.batchUpdate(
                                    "CP_VIRTUAL_CARD.abatorgenerated_updateByPrimaryKeySelective", virtualCardList);
                            log.info("成功更新超过24小时且状态为内存中卡{}条卡时间记录", updateCount);
                            return virtualCardList;
                        } else {
                            // 获取初始状态的卡数量
                            Map<String, Object> initCardMap = new HashMap<String, Object>();
                            initCardMap.put("productId", productId);
                            initCardMap.put("cardState", CardConstants.CARD_INIT);
                            Integer cardNum = (Integer) baseDAO
                                    .queryForObject("VIRTUALCARD.countInitCard", initCardMap);
                            if (cardNum >= CardConstants.MAKE_NUM) {
                                @SuppressWarnings("unchecked")
                                List<VirtualCard> virtualCardList = baseDAO.queryForList("VIRTUALCARD.queryInitCard",
                                        initCardMap);
                                for (VirtualCard card : virtualCardList) {
                                    card.setStatus(CardConstants.CARD_LOADED);
                                    card.setUpdatedTime(new Date());
                                }
                                int updateCount = commonsDAO.batchUpdate(
                                        "CP_VIRTUAL_CARD.abatorgenerated_updateByPrimaryKeySelective", virtualCardList);
                                log.info("成功更新未使用{}条卡时间记录", updateCount);
                                return virtualCardList;
                            }
                        }
                        return new ArrayList<VirtualCard>();
                    }
                });

    }

    /*
     * 不会发生一台机器同时制卡 (non-Javadoc)
     * @see com.suning.svc.coupon.service.CardService#makeCards()
     */
    @Override
    public void makeCards(long productId, int num) throws Exception {
        // 可以同时制卡 生成批次
        String batchId = commonsDAO.getNextValueOfSequenceBySequence(SequenceContansts.SEQ_CARD_BATCH);
        if (batchId == null) {
            log.error("批次号不存在,请确认sequence SEQ_CARD_BATCH是否创建");
            throw new AppException("批次号不存在,请确认sequence  SEQ_CARD_BATCH是否创建");
        }
        log.info("开始制卡,卡批次{},卡张数{}", batchId, num);
        CardBatch cardBatch = new CardBatch();
        cardBatch.setId(Long.valueOf(batchId));
        cardBatch.setCardNum(CardConstants.MAKE_NUM);
        cardBatch.setStatus(CardConstants.MAKING_CARD);
        cardBatch.setProductId(productId);
        cardBatch.setCreatedTime(new Date());
        cardBatch.setUpdatedTime(new Date());
        cardBatchDAO.insert(cardBatch);
        List<VirtualCard> cards = new ArrayList<VirtualCard>();
        List<String> cardNoList = readyCardNo(String.valueOf(productId), num);
        for (int i = 0; i < num; i++) {
            VirtualCard virtualCard = new VirtualCard();
            virtualCard.setId(Long.valueOf(commonsDAO
                    .getNextValueOfSequenceBySequence(SequenceContansts.SEQ_CP_VIRTUAL_CARD)));
            virtualCard.setCardNo(cardNoList.get(i));
            virtualCard.setBatchId(Long.valueOf(batchId));
            virtualCard.setProductId(productId);
            virtualCard.setCreatedTime(new Date());
            virtualCard.setStatus(CardConstants.CARD_INIT);
            virtualCard.setUpdatedTime(new Date());
            virtualCard.setAvailableBalance(0L);
            cards.add(virtualCard);
        }
        // commonsDAO.batchInsert("VIRTUALCARD.virtual_batch_insert", cards);
        commonsDAO.batchInsert("CP_VIRTUAL_CARD.abatorgenerated_insert", cards);
        // //更新批次状态
        CardBatch c = new CardBatch();
        c.setId(Long.valueOf(batchId));
        c.setStatus(CardConstants.MAKE_CARD_OVER);
        c.setUpdatedTime(new Date());
        cardBatchDAO.updateByPrimaryKeySelective(c);
        log.info("批次{}制卡成功", batchId);
    }

    /**
     * 为产品productID创建num张卡
     * 
     * @param productId
     * @param num
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private List<String> readyCardNo(final String productId, final int num) {
        ProductCardBin pbin = (ProductCardBin) cardManageTransactionTemplate.execute(new TransactionCallback() {
            @Override
            public Object doInTransaction(TransactionStatus arg0) {
                ProductCardBin productCardBin = (ProductCardBin) baseDAO.queryForObject(
                        "VIRTUALCARD.getProductCardBinForLock", productId);
                if (productCardBin == null) {
                    log.error("产品id为:[ " + productId + " ]还没有对应的卡BIN");
                    throw new AppException("产品id为:[ " + productId + " ]还没有对应的卡BIN");
                }
                String serialNumber = productCardBin.getSerialNumber();
                if (StringUtil.isEmpty(serialNumber)) {
                    serialNumber = "0";
                }
                Integer initSerialNumber = Integer.valueOf(serialNumber);
                productCardBin.setSerialNumber(String.valueOf(initSerialNumber + num));
                productCardBinDAO.updateByPrimaryKeySelective(productCardBin);
                return productCardBin;
            }
        });

        String cardBin = pbin.getCardBin();
        String serialNumber = pbin.getSerialNumber();
        Integer initSer = Integer.valueOf(serialNumber) - num;
        List<String> cardNoList = new ArrayList<String>();
        for (int i = 0; i < num; i++) {
            initSer += 1;
            cardNoList.add(cardNoRegulation(cardBin, initSer.toString()));
        }

        return cardNoList;
    }

    /**
     * 
     * 生成卡号的规则
     * 
     * @param cardBin
     * @param serialNumber
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private String cardNoRegulation(String cardBin, String serialNumber) {
        int randomLength = CardConstants.CARD_NO_LENGTH - 1 - cardBin.length();
        String result = cardBin + getFormatStr(serialNumber, "0", randomLength, true);
        return LUHNGenerator.generate(result);
    }

    // public static void main(String[] args) {
    // VirtualCardServiceImpl a = new VirtualCardServiceImpl();
    // System.out.println(a.cardNoRegulation("222", "2"));
    // ;
    // }

    /**
     * 获取符合规则的字符串
     * 
     * @param str
     * @param appendStr
     * @param length
     * @param appendBefore
     * @return
     * @throws Exception
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private String getFormatStr(String str, String appendStr, int length, boolean appendBefore) {
        if (null == str) {
            str = "";
        }
        if (null == appendStr) {
            appendStr = "0";
        }
        int circleNum = length - str.length();
        StringBuffer buffer = new StringBuffer(str);
        if (circleNum < 0) {
            throw new AppException("流水号长度不够");
        } else {
            for (int i = 0; i < circleNum; i++) {
                if (appendBefore) {
                    buffer.insert(0, appendStr);
                } else {
                    buffer.append(appendStr);
                }
            }
        }
        return buffer.toString();

    }

    /*
     * (non-Javadoc)
     * @see com.suning.svc.coupon.service.VirtualCardService#updateVirtualCardState(long, java.lang.String)
     */
    @Override
    public void updateVirtualCardState(long id, String cardState) {
        VirtualCard card = new VirtualCard();
        card.setId(id);
        card.setStatus(cardState);
        card.setUpdatedTime(new Date());
        virtualCardDAO.updateByPrimaryKeySelective(card);
    }

    /*
     * (non-Javadoc)
     * @see com.suning.svc.coupon.service.VirtualCardService#batchUpdateCardStatus(java.util.List)
     */
    @Override
    public int batchUpdateCardStatus(List<VirtualCard> virtualCardList) {
        for (VirtualCard card : virtualCardList) {
            card.setStatus(CardConstants.CARD_USED);
            card.setUpdatedTime(new Date());
        }
        return commonsDAO.batchUpdate("CP_VIRTUAL_CARD.abatorgenerated_updateByPrimaryKeySelective", virtualCardList);
    }

    @SuppressWarnings("unchecked")
    @Override
    public VirtualCard queryCardByCouponNo(String couponNo, String orgCode) {
        VirtualCardExample virtualCardExample = new VirtualCardExample();
        VirtualCardExample.Criteria criteria = virtualCardExample.createCriteria();
        criteria.andCouponNoEqualTo(couponNo).andFatherEntityIdEqualTo(orgCode);
        List<VirtualCard> virtualCards = virtualCardDAO.selectByExample(virtualCardExample);
        if (null == virtualCards || virtualCards.size() == 0) {
            return null;
        }
        return virtualCards.get(0);

    }

    /*
     * (non-Javadoc)
     * @see
     * com.suning.svc.coupon.service.VirtualCardService#queryVirtualCardByPage(com.suning.svc.coupon.dto.VirtualCardQueryDto
     * )
     */
    @Override
    public PageDataDTO queryVirtualCardByPage(VirtualCardQueryDto dto) {
        PageDataDTO p = pageQueryDAO.query("VIRTUALCARD.queryVirtualCardByPage", dto);
        return p;
    }

    public VirtualCardDAO getVirtualCardDAO() {
        return virtualCardDAO;
    }

    public void setVirtualCardDAO(VirtualCardDAO virtualCardDAO) {
        this.virtualCardDAO = virtualCardDAO;
    }

    public CardBatchDAO getCardBatchDAO() {
        return cardBatchDAO;
    }

    public void setCardBatchDAO(CardBatchDAO cardBatchDAO) {
        this.cardBatchDAO = cardBatchDAO;
    }

    public OneByOneTemplate getOneByOneTemplate() {
        return oneByOneTemplate;
    }

    public void setOneByOneTemplate(OneByOneTemplate oneByOneTemplate) {
        this.oneByOneTemplate = oneByOneTemplate;
    }

    public SystemParameterDAO getSystemParameterDAO() {
        return systemParameterDAO;
    }

    public void setSystemParameterDAO(SystemParameterDAO systemParameterDAO) {
        this.systemParameterDAO = systemParameterDAO;
    }

    public ProductDAO getProductDAO() {
        return productDAO;
    }

    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public ProductCardBinDAO getProductCardBinDAO() {
        return productCardBinDAO;
    }

    public void setProductCardBinDAO(ProductCardBinDAO productCardBinDAO) {
        this.productCardBinDAO = productCardBinDAO;
    }

    public BaseDAO getBaseDAO() {
        return baseDAO;
    }

    public void setBaseDAO(BaseDAO baseDAO) {
        this.baseDAO = baseDAO;
    }

    public CommonsDAO getCommonsDAO() {
        return commonsDAO;
    }

    public void setCommonsDAO(CommonsDAO commonsDAO) {
        this.commonsDAO = commonsDAO;
    }

    public PageQueryDAO getPageQueryDAO() {
        return pageQueryDAO;
    }

    public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
        this.pageQueryDAO = pageQueryDAO;
    }

    public EntitySystemParameterDAO getEntitySystemParameterDAO() {
        return entitySystemParameterDAO;
    }

    public void setEntitySystemParameterDAO(EntitySystemParameterDAO entitySystemParameterDAO) {
        this.entitySystemParameterDAO = entitySystemParameterDAO;
    }

    /*
     * (non-Javadoc)
     * @see com.suning.svc.coupon.service.VirtualCardService#queryByCardKey()
     */
    @Override
    public EntitySystemParameter queryByCardKey() {
        EntitySystemParameterKey key = new EntitySystemParameterKey();
        key.setEntityId(OrgnizationConstants.HXT_ENTITY_ID);
        key.setFatherEntityId(OrgnizationConstants.FATHER_ENTITY_ID);
        key.setParameterCode(CardConstants.PRODUCT_KEY);
        EntitySystemParameter entitySystemParameter = entitySystemParameterDAO.selectByPrimaryKey(key);
        if (entitySystemParameter == null) {
            log.error("请在系统参数表中设置产品id");
            throw new AppException("请在系统参数表中设置产品id");
        }
        return entitySystemParameter;
    }

    public TransactionTemplate getCardManageTransactionTemplate() {
        return cardManageTransactionTemplate;
    }

    public void setCardManageTransactionTemplate(TransactionTemplate cardManageTransactionTemplate) {
        this.cardManageTransactionTemplate = cardManageTransactionTemplate;
    }

    @SuppressWarnings("unchecked")
    @Override
    public VirtualCard queryHXTCardByCouponNo(String couponNo) {
        VirtualCardExample virtualCardExample = new VirtualCardExample();
        VirtualCardExample.Criteria criteria = virtualCardExample.createCriteria();
        criteria.andCouponNoEqualTo(couponNo).andFatherEntityIdEqualTo(OrgnizationConstants.HXT_ENTITY_ID);
        List<VirtualCard> virtualCards = virtualCardDAO.selectByExample(virtualCardExample);
        if (null == virtualCards || virtualCards.size() == 0) {
            return null;
        }
        return virtualCards.get(0);
    }

}

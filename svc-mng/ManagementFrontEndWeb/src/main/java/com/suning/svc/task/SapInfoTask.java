/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: SapInfoTask.java
 * Author:   12073942
 * Date:     2013-4-22 下午4:55:01
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.IssuerDAO;
import com.huateng.framework.ibatis.dao.MerchantDAO;
import com.huateng.framework.ibatis.model.Issuer;
import com.huateng.framework.ibatis.model.Merchant;
import com.huateng.framework.ibatis.model.MerchantKey;
import com.suning.rsc.mqservice.ei.annotation.EsbEIWired;
import com.suning.svc.constants.SapInfoConstants;
import com.suning.svc.core.template.CallBack;
import com.suning.svc.core.template.OneByOne;
import com.suning.svc.core.template.OneByOneTemplate;
import com.suning.svc.datasync.service.SapFinanceSendService;
import com.suning.svc.datasync.xmlbean.sap.finance.FinanceBean;
import com.suning.svc.ibatis.dao.SapInfoDAO;
import com.suning.svc.ibatis.model.SapInfo;
import com.suning.svc.ibatis.model.SapInfoExample;
import com.suning.svc.ibatis.model.SapInfoExample.Criteria;
import com.suning.svc.service.sap.info.SapInfoQueryService;

/**
 * 上传记账数据到SAP任务
 * 
 * @author LEO
 */
public class SapInfoTask implements InitializingBean {

    private static final Logger LOGGER = Logger.getLogger(SapInfoTask.class);

    /**
     * 发卡机构-卡产品 键值对
     */
    private static final Map<String, String> ISSUER_MAPPER = new HashMap<String, String>();

    /**
     * 发卡机构表DAO
     */
    private IssuerDAO issuerDAO;

    /**
     * 记账表DAO
     */
    private SapInfoDAO sapInfoDAO;

    /**
     * 商户表DAO
     */
    private MerchantDAO merchantDAO;

    /**
     * ESB消息发送服务
     */
    private SapFinanceSendService sapFinanceSendService;

    /**
     * 防并发处理模板
     */
    private OneByOneTemplate oneByOneTemplate;

    /**
     * 记账数据查询DAO服务
     */
    private SapInfoQueryService sapInfoQueryService;

    /**
     * 
     * 开始记账任务
     * 
     * @throws Exception 关注异常：1.OneByOne并发 2.商户不存在 3.DB访问异常
     * 
     */
    public void startFinanceTask() throws Exception {
        // 对整个记账任务进行防并发控制
        Exception ex = oneByOneTemplate.execute(new OneByOne(SapInfoConstants.OBO_BIZ_TYPE_ACCOUNT,
                SapInfoConstants.OBO_BIZ_ID_ONCE, SapInfoConstants.OBO_METHOD_RUNNING), new CallBack<Exception>() {

            // 将未处理异常作为结果返回
            @Override
            public Exception invoke() {
                try {
                    doTask();
                } catch (Exception e) {
                    return e;
                }
                return null;
            }

        });
        // 将invoke时的异常（如果有）向外抛出（可由调度平台查看）
        if (ex != null) {
            throw ex;
        }
    }

    /**
     * 
     * 执行任务
     * 
     * @throws BizServiceException
     * 
     */
    protected void doTask() throws BizServiceException {
        LOGGER.info("记账任务开始");
        // 查询记录数
        long totalNum = sapInfoQueryService.countAccountingRecords();
        LOGGER.info("共有" + totalNum + "条需要处理的记录");
        while (true) {
            // 始终查询首页
            List<SapInfo> records = sapInfoQueryService.getAccountingRecordsList(1, SapInfoConstants.PAGE_SIZE);
            if (records == null || records.isEmpty()) {
                // 查无记录则结束
                break;
            }
            for (SapInfo record : records) {
                // 逐条处理
                processSingleRecord(record);
            }
        }
        LOGGER.info("记账任务结束");
    }

    /**
     * 
     * 单条记录处理
     * 
     * @param record 记账表的一条记录
     */
    private void processSingleRecord(SapInfo record) {
        LOGGER.info("正在处理流水号为" + record.getTransSeq() + "的记录");

        // 满足忽略条件的记录不上传
        if (checkAmount(record)) {
            // 创建更新字段
            SapInfo newValues = new SapInfo();
            newValues.setId(record.getId());
            newValues.setFlag(SapInfoConstants.FLAG_AMOUNT_ZERO);
            newValues.setUpdatedTime(new Date());
            // 按主键选择性更新
            sapInfoDAO.updateByPrimaryKeySelective(newValues);
            LOGGER.info("流水号为" + record.getTransSeq() + "的记录不需要上传：金额为0");
            return;
        }

        // 满足忽略条件的记录不上传
        if (checkMerchant(record)) {
            // 创建更新字段
            SapInfo newValues = new SapInfo();
            newValues.setId(record.getId());
            newValues.setFlag(SapInfoConstants.FLAG_MERCHANT_JOINT);
            newValues.setUpdatedTime(new Date());
            // 按主键选择性更新
            sapInfoDAO.updateByPrimaryKeySelective(newValues);
            LOGGER.info("流水号为" + record.getTransSeq() + "的记录不需要上传：商户为联营");
            return;
        }

        // 调用发送服务传ESB
        sapFinanceSendService.asynSend(convertRecord2Bean(record));
        // 创建更新字段
        SapInfo newValues = new SapInfo();
        if (StringUtils.equals(record.getNeedReceipt(), SapInfoConstants.NEED_RECEIPT)) {
            newValues.setFlag(SapInfoConstants.FLAG_SENT);
        } else {
            newValues.setFlag(SapInfoConstants.FLAG_DONE);
        }
        newValues.setUpdatedTime(new Date());
        // 创建更新条件
        SapInfoExample example = new SapInfoExample();
        Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(record.getId());
        // 测试发现存在0先被更新为9，再被更新为1的异常，此条件解决此问题
        criteria.andFlagNotEqualTo(SapInfoConstants.FLAG_DONE);
        // 按条件选择性更新
        // Preparing Statement: update TBL_SAP_INFO set FLAG = ? , UPDATED_TIME = ? where ( ID = ? and FLAG <> ? )
        sapInfoDAO.updateByExampleSelective(newValues, example);
        LOGGER.info("流水号为" + record.getTransSeq() + "的记录上传成功");

    }

    /**
     * 
     * 检查金额<BR>
     * 金额为0的所有记账记录不上传
     * 
     * @param record SAP_INFO表记录
     * @return 是否满足条件
     */
    private boolean checkAmount(SapInfo record) {
        // 规则1：20130717忽略所有金额为0的记录，无视交易类型
        if (StringUtils.equals(record.getAmount(), SapInfoConstants.AMOUNT_ZERO)) {
            return true;
        }
        return false;
    }

    /**
     * 
     * 检查商户<BR>
     * 商户性质为联营的记录不上传
     * 
     * @param record SAP_INFO表记录
     * @return 是否满足条件
     */
    private boolean checkMerchant(SapInfo record) {
        // 规则2：20130802广场卡联营商户的结算单不记账
        if (StringUtils.equals(record.getTransType(), SapInfoConstants.TRANS_TYPE_SETTLEMENT)) {
            MerchantKey merchantKey = new MerchantKey();
            // VENDOR为商户编码（entityId）
            merchantKey.setEntityId(record.getVendor());
            // TRANS_COMPANY为收单结构编码（fatherEntityId）
            merchantKey.setFatherEntityId(record.getTransCompany());
            // 按主键查询商户表（entityId+fatherEntityId联合主键）
            Merchant merchant = merchantDAO.selectByPrimaryKey(merchantKey);
            // 判断merchant为空（尽管生产环境不应出现为空）
            if (merchant == null) {
                String errMsg = "商户信息不存在：" + record.getVendor() + "在" + record.getTransCompany();
                LOGGER.error(errMsg);
                throw new RuntimeException(errMsg);
            }
            if (StringUtils.equals(merchant.getMerchantType(), SapInfoConstants.MERCHANT_TYPE_JOINT)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 
     * 将SAP_INFO表中的记录转成报文Bean
     * 
     * @param record SapInfo类型的表记录
     * @return 报文Bean
     */
    private FinanceBean convertRecord2Bean(SapInfo record) {
        FinanceBean bean = new FinanceBean();
        bean.setTransType(record.getTransType());
        bean.setTransSeq(record.getTransSeq());
        bean.setTransDt(record.getTransDt());
        bean.setTransCompany(record.getTransCompany());
        bean.setPayment(record.getPayment());
        bean.setAmount(record.getAmount());
        bean.setRateAmount(record.getRateAmount());
        bean.setReAmount(record.getReAmount());
        // 20130929 外部供应商编码去00
        // dto.setVendor(record.getVendor());
        String vendor = record.getVendor();
        if (vendor != null && vendor.length() >= 10 && vendor.startsWith("00")) {
            bean.setVendor(vendor.substring(2));
        } else {
            bean.setVendor(vendor);
        }
        bean.setSaleCompany(record.getSaleCompany());

        // 20130717启用DOC_NO1字段
        bean.setDocNo1(ISSUER_MAPPER.get(record.getTransCompany()));
        // dto.setDocNo2(null);
        // dto.setSupplyOrderNo(null);
        // dto.setCostcenterCode(null);
        // dto.setExpenseAcct(null);
        // dto.setBankSubject(null);
        // dto.setCmmdtyGrp(null);
        // dto.setCmmdtyHeirarchy(null);
        // dto.setCmmdtyCode(null);
        // dto.setCmmdtyDesc(null);
        // dto.setStore(null);
        // dto.setInvoiceNo(null);
        // dto.setPymntNo(null);

        // 以下为20130524新增用于资金打款的字段
        bean.setServiceType(record.getServiceType());
        bean.setServiceSubtype(record.getServiceSubtype());
        bean.setPubPvtIntFlag(record.getPubPvtIntFlag());
        bean.setDocNo(record.getDocNo());
        bean.setCurrencyCode(record.getCurrencyCode());
        bean.setPaymtType(record.getPaymtType());
        bean.setPaymtFlag(record.getPaymtFlag());
        return bean;
    }

    @Override
    public void afterPropertiesSet() {
        initIssuerMapper();
    }

    /**
     * 
     * 初始化ISSUER_MAPPER
     * 
     */
    private void initIssuerMapper() {
        // 查询发卡机构表并将映射关系载入内存
        List<Issuer> allIssuers = issuerDAO.selectByExample(null);
        for (Issuer issuer : allIssuers) {
            // 建立 5101-->HXT_CARD 等键值对
            ISSUER_MAPPER.put(issuer.getEntityId(), issuer.getIssuerComment());
        }
        LOGGER.info("初始化完成：从发卡机构表读取并装载了" + ISSUER_MAPPER.size() + "组键值对");
    }

    /**
     * 由EsbEI注入
     */
    @EsbEIWired
    public void setSapFinanceSendService(SapFinanceSendService sapFinanceSendService) {
        this.sapFinanceSendService = sapFinanceSendService;
    }

    /**
     * @param oneByOneTemplate the oneByOneTemplate to set
     */
    public void setOneByOneTemplate(OneByOneTemplate oneByOneTemplate) {
        this.oneByOneTemplate = oneByOneTemplate;
    }

    /**
     * @param issuerDAO the issuerDAO to set
     */
    public void setIssuerDAO(IssuerDAO issuerDAO) {
        this.issuerDAO = issuerDAO;
    }

    /**
     * @param sapInfoDAO the sapInfoDAO to set
     */
    public void setSapInfoDAO(SapInfoDAO sapInfoDAO) {
        this.sapInfoDAO = sapInfoDAO;
    }

    /**
     * @param merchantDAO the merchantDAO to set
     */
    public void setMerchantDAO(MerchantDAO merchantDAO) {
        this.merchantDAO = merchantDAO;
    }

    /**
     * @param sapInfoQueryService the sapInfoQueryService to set
     */
    public void setSapInfoQueryService(SapInfoQueryService sapInfoQueryService) {
        this.sapInfoQueryService = sapInfoQueryService;
    }

}

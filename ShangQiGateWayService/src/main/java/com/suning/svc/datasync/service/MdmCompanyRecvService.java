/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: MdmCompanyRecvService.java
 * Author:   12073942
 * Date:     2013-7-30 下午6:30:38
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.datasync.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.allinfinance.framework.dto.OperationResult;
import com.huateng.framework.ibatis.dao.IssuerDAO;
import com.huateng.framework.ibatis.model.Issuer;
import com.huateng.univer.synch.ManageHessianService;
import com.huateng.univer.synch.constants.MDMConstCode;
import com.huateng.univer.synch.dto.MerchantSynchDTO;
import com.suning.rsc.mqservice.ei.annotation.EsbEIWired;
import com.suning.svc.datasync.constants.MdmConstants;
import com.suning.svc.datasync.xmlbean.mdm.company.CompanyFeedbackInfo;
import com.suning.svc.datasync.xmlbean.mdm.company.CompanyLayerInfo;
import com.suning.svc.datasync.xmlbean.mdm.company.FeedbackBodyBean;
import com.suning.svc.datasync.xmlbean.mdm.company.ReceivedBodyBean;

/**
 * 公司层主数据处理
 * 
 * @author LEO
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class MdmCompanyRecvService implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(MdmCompanyRecvService.class);

    /**
     * 发卡机构（公司代码）集合
     */
    private static final List<String> ISSUER_LIST = new ArrayList<String>();

    /**
     * 发卡机构表DAO
     */
    private IssuerDAO issuerDAO;

    /**
     * MNG业务处理服务
     */
    private ManageHessianService manageHessianService;

    /**
     * ESB报文发送服务
     */
    private MdmCompanySendService companySendService;

    /**
     * 
     * 处理MDM下发的公司层主数据
     * 
     * @param bodyBean 接收到的报文主体
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public void processBodyBean(ReceivedBodyBean bodyBean) {
        ArrayList<CompanyFeedbackInfo> companyFeedbackInfoList = new ArrayList<CompanyFeedbackInfo>();
        // 只取公司层信息部分
        List<CompanyLayerInfo> companyLayerInfoList = bodyBean.getCompanyLayerInfoList();
        // 遍历每条公司层信息，逐条处理
        for (CompanyLayerInfo info : companyLayerInfoList) {
            if (processSingleInfo(info)) {
                // 插入成功状态返回
                companyFeedbackInfoList.add(generateFeedbackInfo(info, true));
            } else {
                // 插入失败状态返回
                companyFeedbackInfoList.add(generateFeedbackInfo(info, false));
            }
        }
        // 向MDM发状态返回信息
        FeedbackBodyBean feedbackBodyBean = new FeedbackBodyBean();
        feedbackBodyBean.setDistributeSys(MdmConstants.DISTRIBUTE_SYS_SVC);
        feedbackBodyBean.setCompanyFeedbackInfoList(companyFeedbackInfoList);
        companySendService.asynSend(feedbackBodyBean);
    }

    /**
     * 
     * 处理单条公司层信息
     * 
     * @param info 一条公司层信息
     * @return 是否成功
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private boolean processSingleInfo(CompanyLayerInfo info) {

        // 筛选数据
        if (isAbandoned(info)) {
            // 不需要的信息忽略并返回成功
            LOGGER.info("公司层信息[{};{}]已丢弃", info.getSupplierCode(), info.getCoCode());
            return true;
        }

        MerchantSynchDTO dto = new MerchantSynchDTO();
        // 设置供应商编码
        dto.setEntityId(info.getSupplierCode());
        // 设置收单结构号
        dto.setFatherEntityId(info.getCoCode());
        // 业务处理
        OperationResult result = manageHessianService.sendService(MDMConstCode.MDM_MERCHANT_INFO_SYNCH, dto);
        if (result.getErrMessage() == null) {
            LOGGER.info("公司层信息[{};{}]处理成功", info.getSupplierCode(), info.getCoCode());
            return true;
        } else {
            LOGGER.error("公司层信息[{};{}]处理失败：{}",
                    new Object[] { info.getSupplierCode(), info.getCoCode(), result.getErrMessage() });
            return false;
        }
    }

    /**
     * 
     * 检查信息是否忽略
     * 
     * @param info 公司层信息
     * @return 是否忽略
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private boolean isAbandoned(CompanyLayerInfo info) {
        for (String coCode : ISSUER_LIST) {
            if (StringUtils.equals(info.getCoCode(), coCode)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 
     * 生成对应的返回信息
     * 
     * @param companyLayerInfo 公司层信息
     * @param isSuccess 是否成功
     * @return 返回MDM的状态信息
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private CompanyFeedbackInfo generateFeedbackInfo(CompanyLayerInfo companyLayerInfo, boolean isSuccess) {
        CompanyFeedbackInfo feedbackInfo = new CompanyFeedbackInfo();
        // 同名字段取值
        feedbackInfo.setSupplierCode(companyLayerInfo.getSupplierCode());
        feedbackInfo.setCoCode(companyLayerInfo.getCoCode());
        feedbackInfo.setVersionNo(companyLayerInfo.getVersionNo());
        if (isSuccess) {
            // 成功状态取值
            feedbackInfo.setProcessStat(MdmConstants.PROCESS_STAT_SUCCESS);
            feedbackInfo.setNotes(MdmConstants.NOTES_SUCCESS);
        } else {
            // 失败状态取值
            feedbackInfo.setProcessStat(MdmConstants.PROCESS_STAT_FAIL);
            feedbackInfo.setNotes(MdmConstants.NOTES_FAIL);
        }
        return feedbackInfo;
    }

    /**
     * @param manageHessianService the manageHessianService to set
     */
    public void setManageHessianService(ManageHessianService manageHessianService) {
        this.manageHessianService = manageHessianService;
    }

    /**
     * 由EsbEI注入
     */
    @EsbEIWired
    public void setCompanySendService(MdmCompanySendService companySendService) {
        this.companySendService = companySendService;
    }

    /**
     * @param issuerDAO the issuerDAO to set
     */
    public void setIssuerDAO(IssuerDAO issuerDAO) {
        this.issuerDAO = issuerDAO;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        initIssuerList();
    }

    /**
     * 
     * 初始化ISSUER_LIST
     * 
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private void initIssuerList() {
        // 查询发卡机构表并将映射关系载入内存
        List<Issuer> allIssuers = issuerDAO.selectByExample(null);
        for (Issuer issuer : allIssuers) {
            // 加入5101等公司代码
            ISSUER_LIST.add(issuer.getEntityId());
        }
        LOGGER.info("初始化完成：从发卡机构表读取并装载了" + ISSUER_LIST.size() + "个发卡公司代码");
    }

}

/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: MdmSupplierRecvService.java
 * Author:   12073942
 * Date:     2013-10-16 下午4:15:28
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

import com.allinfinance.framework.dto.OperationResult;
import com.huateng.univer.synch.ManageHessianService;
import com.huateng.univer.synch.constants.MDMConstCode;
import com.huateng.univer.synch.dto.BankInfoDTO;
import com.huateng.univer.synch.dto.ContactInfoDTO;
import com.huateng.univer.synch.dto.CustomerSynchDTO;
import com.huateng.univer.synch.dto.MerchantSynchDTO;
import com.suning.rsc.mqservice.ei.annotation.EsbEIWired;
import com.suning.svc.datasync.constants.MdmConstants;
import com.suning.svc.datasync.xmlbean.mdm.merchant.BankInfo;
import com.suning.svc.datasync.xmlbean.mdm.merchant.ContactInfo;
import com.suning.svc.datasync.xmlbean.mdm.merchant.FeedbackBodyBean;
import com.suning.svc.datasync.xmlbean.mdm.merchant.FeedbackStatusInfo;
import com.suning.svc.datasync.xmlbean.mdm.merchant.Merchant;
import com.suning.svc.datasync.xmlbean.mdm.merchant.ReceivedBodyBean;

/**
 * MDM供应商基本层数据下发接收处理服务
 * 
 * @author LEO
 */
public class MdmSupplierRecvService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MdmSupplierRecvService.class);

    /**
     * 返回MDM的报文发送服务
     */
    private MdmSupplierSendService supplierSendService;

    /**
     * MNG管理后台同步服务
     */
    private ManageHessianService manageHessianService;

    /**
     * 
     * 处理MDM下发的供应商基本层数据
     * 
     * @param bodyBean 接收到的报文主体
     */
    public void processBodyBean(ReceivedBodyBean bodyBean) {
        ArrayList<FeedbackStatusInfo> feedbackStatusInfoList = new ArrayList<FeedbackStatusInfo>();
        List<Merchant> suppliers = bodyBean.getMerchant();
        for (Merchant supplier : suppliers) {
            if (processSingleInfo(supplier, bodyBean)) {
                // 插入成功状态返回
                feedbackStatusInfoList.add(generateFeedbackInfo(supplier, true));
            } else {
                // 插入失败状态返回
                feedbackStatusInfoList.add(generateFeedbackInfo(supplier, false));
            }
        }
        // 向MDM发状态返回信息
        FeedbackBodyBean feedbackBodyBean = new FeedbackBodyBean();
        feedbackBodyBean.setDistributeSys(MdmConstants.DISTRIBUTE_SYS_SVC);
        feedbackBodyBean.setFeedbackStatusInfo(feedbackStatusInfoList);
        supplierSendService.asynSend(feedbackBodyBean);
    }

    /**
     * 
     * 处理单条供应商数据
     * 
     * @param supplier 供应商数据
     * @param bodyBean 报文
     * @return 是否处理成功
     */
    private boolean processSingleInfo(Merchant supplier, ReceivedBodyBean bodyBean) {
        LOGGER.info("供应商[{}]基本层信息处理开始", supplier.getSupplierCode());
        boolean isSuccess = true;

        // 系统原生商户所需供应商数据处理
        if (isNativeMcht(supplier)) {
            LOGGER.info("供应商[{}]为原生供应商", supplier.getSupplierCode());

            // 原生商户，同步标识为同步到临时表
            MerchantSynchDTO dto = bean2MchtDto(supplier, MdmConstants.SYNCH_FLAG_TEMP);
            OperationResult rlt = manageHessianService.sendService(MDMConstCode.MDM_MERCHANT_INFO_SYNCH, dto);
            if (rlt.getErrMessage() == null) {
                LOGGER.info("供应商[{}]同步到临时表成功", supplier.getSupplierCode());
            } else {
                isSuccess = false;
                LOGGER.error("供应商[{}]同步到临时表失败：{}", supplier.getSupplierCode(), rlt.getErrMessage());
            }

        }

        // 券活动商户/客户所需供应商数据处理
        if (isCouponMcht(supplier)) {
            LOGGER.info("供应商[{}]为券活动供应商", supplier.getSupplierCode());

            // 券活动商户，同步标识为立即为华夏通同步
            MerchantSynchDTO mdto = bean2MchtDto(supplier, MdmConstants.SYNCH_FLAG_HXT);
            OperationResult mrlt = manageHessianService.sendService(MDMConstCode.MDM_MERCHANT_INFO_SYNCH, mdto);
            if (mrlt.getErrMessage() == null) {
                LOGGER.info("供应商[{}]同步到华夏通商户成功", supplier.getSupplierCode());
            } else {
                isSuccess = false;
                LOGGER.error("供应商[{}]同步到华夏通商户失败：{}", supplier.getSupplierCode(), mrlt.getErrMessage());
            }

            // 找到该供应商对应的银行信息
            List<BankInfo> bankInfoList = getBankInfoList(bodyBean, supplier);
            LOGGER.info("供应商[{}]有{}条银行信息", supplier.getSupplierCode(), bankInfoList.size());
            // 找到该供应商对应的联系人信息
            List<ContactInfo> contactInfoList = getContactInfoList(bodyBean, supplier);
            LOGGER.info("供应商[{}]有{}条联系人信息", supplier.getSupplierCode(), contactInfoList.size());
            // 券活动客户，同步为客户
            CustomerSynchDTO cdto = bean2CstmrDto(supplier, bankInfoList, contactInfoList);
            OperationResult crlt = manageHessianService.sendService(MDMConstCode.MDM_CUSTOMER_INFO_SYNCH, cdto);
            if (crlt.getErrMessage() == null) {
                LOGGER.info("供应商[{}]同步到华夏通客户成功", supplier.getSupplierCode());
            } else {
                isSuccess = false;
                LOGGER.error("供应商[{}]同步到华夏通客户失败：{}", supplier.getSupplierCode(), crlt.getErrMessage());
            }

        }
        // 其它类型供应商不处理
        LOGGER.info("供应商[{}]基本层信息处理结束", supplier.getSupplierCode());
        return isSuccess;
    }

    /**
     * 
     * 判断是否为系统原生商户所需的供应商数据
     * 
     * @param supplier 供应商数据
     * @return 是/否
     */
    private boolean isNativeMcht(Merchant supplier) {
        // 判断业务类型字段，若为"920预付卡"，即为满足预付卡系统的外部供应商
        // 20130802 增加接收广场卡租赁、广场卡联营
        if (StringUtils.equals(supplier.getBizTypeCode(), MdmConstants.BIZ_TYPE_HXT)
                || StringUtils.equals(supplier.getBizTypeCode(), MdmConstants.BIZ_TYPE_GCK_RENT)
                || StringUtils.equals(supplier.getBizTypeCode(), MdmConstants.BIZ_TYPE_GCK_JOINT)) {
            return true;
        }
        // RE开头的内部供应商
        if (StringUtils.startsWith(supplier.getSupplierCode(), MdmConstants.SUPPLIER_CODE_PREFIX_INNER)) {
            return true;
        }
        return false;
    }

    /**
     * 
     * 判断是否为券活动所需的供应商数据
     * 
     * @param supplier 供应商数据
     * @return 是/否
     */
    private boolean isCouponMcht(Merchant supplier) {
        // 类型为921C店的外部供应商
        if (StringUtils.equals(supplier.getBizTypeCode(), MdmConstants.BIZ_TYPE_C_SHOP)) {
            return true;
        }
        // RE开头的内部供应商
        if (StringUtils.startsWith(supplier.getSupplierCode(), MdmConstants.SUPPLIER_CODE_PREFIX_INNER)) {
            return true;
        }
        return false;
    }

    /**
     * 
     * 从报文中查询供应商对应的多条银行信息
     * 
     * @param bodyBean 报文
     * @param supplier 供应商
     * @return 列表（不会是null）
     */
    private List<BankInfo> getBankInfoList(ReceivedBodyBean bodyBean, Merchant supplier) {
        List<BankInfo> bankInfoList = new ArrayList<BankInfo>();
        if (bodyBean.getBankInfo() != null) {
            for (BankInfo bankInfo : bodyBean.getBankInfo()) {
                if (StringUtils.equals(bankInfo.getSupplierCode(), supplier.getSupplierCode())) {
                    bankInfoList.add(bankInfo);
                }
            }
        }
        return bankInfoList;
    }

    /**
     * 
     * 从报文中查询供应商对应的多条联系人信息
     * 
     * @param bodyBean 报文
     * @param supplier 供应商
     * @return 列表（不会是null）
     */
    private List<ContactInfo> getContactInfoList(ReceivedBodyBean bodyBean, Merchant supplier) {
        List<ContactInfo> contactInfoList = new ArrayList<ContactInfo>();
        if (bodyBean.getContactInfo() != null) {
            for (ContactInfo contactInfo : bodyBean.getContactInfo()) {
                if (StringUtils.equals(contactInfo.getSupplierCode(), supplier.getSupplierCode())) {
                    contactInfoList.add(contactInfo);
                }
            }
        }
        return contactInfoList;
    }

    /**
     * 
     * 从报文bean构建商户同步DTO
     * 
     * @param supplier 供应商基本层信息
     * @param synchFlag 同步标识
     * @return 商户同步DTO
     */
    private MerchantSynchDTO bean2MchtDto(Merchant supplier, String synchFlag) {
        MerchantSynchDTO dto = new MerchantSynchDTO();
        dto.setEntityId(supplier.getSupplierCode());
        dto.setMerchantName(supplier.getSupplierName());
        // 201310 由SEAM处理搜索项1：将15位纯数字作为商户编码，其它视作无商户编码
        if (StringUtils.isNumeric(supplier.getSearchItem1())
                && StringUtils.length(supplier.getSearchItem1()) == MdmConstants.MERCHANT_CODE_LENGTH) {
            dto.setMerchantCode(supplier.getSearchItem1());
        }
        dto.setMerchantAddress(supplier.getRegAddress());
        // 20130917 搜索项2处理：除了0，均视作有效
        if (StringUtils.equals(supplier.getSearchItem2(), MdmConstants.DATA_STATE_DISABLED)) {
            dto.setDataState(MdmConstants.DATA_STATE_DISABLED);
        } else {
            dto.setDataState(MdmConstants.DATA_STATE_ENABLED);
        }
        // 20130802 增加根据bizTypeCode判断联营商户
        if (StringUtils.equals(supplier.getBizTypeCode(), MdmConstants.BIZ_TYPE_GCK_JOINT)) {
            dto.setMerchantType(MdmConstants.MERCHANT_TYPE_JOINT);
        } else {
            dto.setMerchantType(MdmConstants.MERCHANT_TYPE_DEFAULT);
        }
        // 20131024 增加同步标识
        dto.setSynchFlag(synchFlag);
        return dto;
    }

    /**
     * 
     * 从报文bean构建客户同步DTO
     * 
     * @param supplier 供应商基本层信息
     * @param contactInfoList
     * @param bankInfoList
     * @return 客户同步DTO
     */
    private CustomerSynchDTO bean2CstmrDto(Merchant supplier, List<BankInfo> bankInfoList,
            List<ContactInfo> contactInfoList) {
        CustomerSynchDTO dto = new CustomerSynchDTO();

        // 供应商编码作为客户实体ID
        dto.setEntityId(supplier.getSupplierCode());
        // 供应商名称作为客户名称
        dto.setCustomerName(supplier.getSupplierName());
        // 对应OFFICE_ADDRESS
        dto.setCustomerAddress(supplier.getOfficeAddress());
        // 对应OFFICE_POST_CODE
        dto.setCustomerPostcode(supplier.getOfficePostCode());
        // 对应TEL_NO
        dto.setCustomerTelephone(supplier.getTelNo());
        // 对应FAX_NO
        dto.setCustomerFax(supplier.getFaxNo());
        // 对应BIZ_REG_NO 工商注册号
        dto.setLicenseId(supplier.getBizRegNo());
        // 对应REG_CAPITAL 注册资本
        dto.setRegiCapital(supplier.getRegCapital());
        // 对应BIZ_LICENSE_REG_DATE 营业执照注册日期
        dto.setLicenseStaValidity(supplier.getBizLicenseRegDate());
        // 对应BIZ_LICENSE_EXPIRE_DATE 营业执照到期日期
        dto.setLicenseEndValidity(supplier.getBizLicenseExpireDate());
        // 对应LEGAL_PERSON_NAME 法人代表姓名
        dto.setCorpName(supplier.getLegalPersonName());
        // 对应LEGAL_PERSON_ID_CARD 法人代表身份证号
        dto.setCorpCredId(supplier.getLegalPersonIdCard());

        // 税类型
        dto.setTaxCode(supplier.getTaxCode());
        // 税类型描述
        dto.setTaxTypeDesc(supplier.getTaxTypeDesc());
        // 税号
        dto.setTaxCode(supplier.getTaxCode());
        // 税号类型
        dto.setTaxNoType(supplier.getTaxNoType());
        // 公司注册地址
        dto.setRegAddress(supplier.getRegAddress());
        // 付款方式描述
        dto.setPaymentTypeDesc(supplier.getPaymentTypeDesc());
        // 城市邮编
        dto.setCityPostCode(supplier.getCityPostCode());
        // 邮政信箱
        dto.setMailBox(supplier.getMallBox());
        // 公司类型
        dto.setCoTypeCode(supplier.getCoTypeCode());

        // 复制银行列表到DTO
        List<BankInfoDTO> bankInfoDTOs = dto.getBankInfoList();
        for (BankInfo bankInfo : bankInfoList) {
            BankInfoDTO bankInfoDTO = new BankInfoDTO();
            // 取银行名称和银行账户2个字段
            bankInfoDTO.setBranchBankName(bankInfo.getBranchBankName());
            bankInfoDTO.setBankAcctCode(bankInfo.getBankAcctCode());
            bankInfoDTOs.add(bankInfoDTO);
        }

        // 复制联系人列表到DTO
        List<ContactInfoDTO> contactInfoDTOs = dto.getContactInfoList();
        for (ContactInfo contactInfo : contactInfoList) {
            ContactInfoDTO contactInfoDTO = new ContactInfoDTO();
            // 取联系人名称、联系人电话、联系人手机3个字段
            contactInfoDTO.setContactName(contactInfo.getContactPersonName());
            contactInfoDTO.setContactMobilePhone(contactInfo.getContactPersonMobile());
            contactInfoDTO.setContactTelephone(contactInfo.getContactPersonTel());
            contactInfoDTOs.add(contactInfoDTO);
        }

        return dto;
    }

    /**
     * 
     * 生成返回MDM的信息
     * 
     * @param supplier 供应商基本层信息
     * @param isSuccess 是否成功
     * @return 返回状态信息
     */
    private FeedbackStatusInfo generateFeedbackInfo(Merchant supplier, boolean isSuccess) {
        // 返回MDM
        FeedbackStatusInfo feedbackStatusInfo = new FeedbackStatusInfo();
        feedbackStatusInfo.setSupplierCode(supplier.getSupplierCode());
        feedbackStatusInfo.setVersionNo(supplier.getVersionNo());
        if (isSuccess) {
            // 成功状态取值
            feedbackStatusInfo.setProcessStat(MdmConstants.PROCESS_STAT_SUCCESS);
            feedbackStatusInfo.setNotes(MdmConstants.NOTES_SUCCESS);
        } else {
            // 失败状态取值
            feedbackStatusInfo.setProcessStat(MdmConstants.PROCESS_STAT_FAIL);
            feedbackStatusInfo.setNotes(MdmConstants.NOTES_FAIL);
        }
        return feedbackStatusInfo;
    }

    /**
     * 由EsbEI注入
     */
    @EsbEIWired
    public void setSupplierSendService(MdmSupplierSendService supplierSendService) {
        this.supplierSendService = supplierSendService;
    }

    /**
     * @param manageHessianService the manageHessianService to set
     */
    public void setManageHessianService(ManageHessianService manageHessianService) {
        this.manageHessianService = manageHessianService;
    }

}

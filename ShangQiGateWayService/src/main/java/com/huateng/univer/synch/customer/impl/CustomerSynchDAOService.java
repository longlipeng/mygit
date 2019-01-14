/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: CustomerSynchDAOService.java
 * Author:   12073942
 * Date:     2013-10-30 上午10:04:19
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.univer.synch.customer.impl;

import java.util.Date;

import org.apache.log4j.Logger;

import com.allinfinance.univer.entity.dto.ContactDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.BankDAO;
import com.huateng.framework.ibatis.dao.CustomerDAO;
import com.huateng.framework.ibatis.model.Bank;
import com.huateng.framework.ibatis.model.Customer;
import com.huateng.framework.util.DateUtil;
import com.huateng.univer.entitybaseinfo.contact.biz.service.ContactService;
import com.huateng.univer.synch.dto.BankInfoDTO;
import com.huateng.univer.synch.dto.ContactInfoDTO;
import com.huateng.univer.synch.dto.CustomerSynchDTO;
import com.suning.svc.ibatis.dao.CustomerInvoiceInfoDAO;
import com.suning.svc.ibatis.model.CustomerInvoiceInfo;

/**
 * 客户事务操作
 * 
 * @author LEO
 */
public class CustomerSynchDAOService {

    Logger logger = Logger.getLogger(this.getClass());

    /**
     * 通用DAO
     */
    private CommonsDAO commonsDAO;

    /**
     * 客户表DAO
     */
    private CustomerDAO customerDAO;

    /**
     * 客户发票信息表DAO
     */
    private CustomerInvoiceInfoDAO customerInvoiceInfoDAO;

    /**
     * 银行信息
     */
    private BankDAO bankDAO;

    /**
     * 联系人信息
     */
    private ContactService contactService;

    /**
     * 新增客户信息
     * 
     * @param customerSynchDTO 客户同步DTO
     * @throws BizServiceException 业务异常
     */
    public CustomerSynchDTO insertCustomer(CustomerSynchDTO customerSynchDTO) throws BizServiceException {
        try {

            String entityId = customerSynchDTO.getEntityId();
            // 增加客户信息
            addCustomer(customerSynchDTO);
            for (BankInfoDTO bankInfoDTO : customerSynchDTO.getBankInfoList()) {
                // 增加银行信息
                addBankInfo(bankInfoDTO, entityId);
            }
            for (ContactInfoDTO contactInfoDTO : customerSynchDTO.getContactInfoList()) {
                // 增加联系人信息
                addContactInfo(contactInfoDTO, entityId);
            }
            // 新增客户发票信息
            addInvoiceInfo(customerSynchDTO);

            return customerSynchDTO;
        } catch (BizServiceException b) {
            throw b;
        } catch (Exception e) {
            logger.error("供应商:" + customerSynchDTO.getEntityId() + "新增客户信息失败：" + e.getMessage());
            throw new BizServiceException("供应商:" + customerSynchDTO.getEntityId() + "新增客户信息失败。");
        }
    }

    /**
     * 
     * 增加客户信息
     * 
     * @param customerSynchDTO
     * @throws BizServiceException
     */
    private void addCustomer(CustomerSynchDTO customerSynchDTO) throws BizServiceException {
        try {
            Customer customer = new Customer();
            // 主键取自DTO
            customer.setEntityId(customerSynchDTO.getEntityId());
            customer.setFatherEntityId(customerSynchDTO.getFatherEntityId());
            // customerSynchDTO字段x2：客户名称 客户编码
            customer.setCustomerName(customerSynchDTO.getCustomerName());
            customer.setCustomerCode(customerSynchDTO.getEntityId());
            // customerSynchDTO字段x4：单位所在地址 公司邮编 公司电话 公司传真
            customer.setCustomerAddress(customerSynchDTO.getCustomerAddress());
            customer.setCustomerPostcode(customerSynchDTO.getCustomerPostcode());
            customer.setCustomerTelephone(customerSynchDTO.getCustomerTelephone());
            customer.setCustomerFax(customerSynchDTO.getCustomerFax());
            // customerSynchDTO字段x6：工商注册号 注册资本 营业执照注册日期 营业执照到期日期 法人代表姓名 法人代表身份证号
            customer.setLicenseId(customerSynchDTO.getLicenseId());
            customer.setRegiCapital(customerSynchDTO.getRegiCapital());
            customer.setLicenseStaValidity(customerSynchDTO.getLicenseStaValidity());
            customer.setLicenseEndValidity(customerSynchDTO.getLicenseEndValidity());
            customer.setCorpName(customerSynchDTO.getCorpName());
            customer.setCorpCredId(customerSynchDTO.getCorpCredId());
            // 客户类型为企业客户
            customer.setCustomerType("0");
            // 从页面POST获取的默认参数值传入
            customer.setChannel("1");
            customer.setSalesRegionId("1");
            customer.setPaymentTerm("2");
            customer.setBusinessCity("1");
            customer.setBusinessAreaId("13");
            customer.setSalesmanId("0");
            customer.setActivitySector("2");
            customer.setCusState("2");
            customer.setHasDm("1");
            customer.setCorpCredType("1");
            customer.setAwareness("0");
            customer.setFiveCertificate("2");
            customer.setSurvey("2");
            customer.setCreditInformation("2");
            customer.setIdentityInspect("2");
            customer.setBadnessInspect("2");
            customer.setPictureSave("2");
            customer.setActionAtLaw("2");
            customer.setCorpActionAtLaw("2");
            customer.setCreditStatus("0");
            customer.setPunishRecordFlag("2");
            customer.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
            customer.setCreateTime(DateUtil.getCurrentTime());
            customer.setCreateUser("0");
            customer.setModifyTime(DateUtil.getCurrentTime());
            customer.setModifyUser("0");
            customerDAO.insert(customer);
        } catch (Exception e) {
            logger.error("新增客户" + customerSynchDTO.getEntityId() + "失败：" + e.getMessage());
            throw new BizServiceException("新增客户" + customerSynchDTO.getEntityId() + "失败。");
        }
    }

    /**
     * 
     * 增加银行信息
     * 
     * @param bankInfoDTO
     * @param entityId
     * @throws BizServiceException
     */
    private void addBankInfo(BankInfoDTO bankInfoDTO, String entityId) throws BizServiceException {
        try {
            Bank bank = new Bank();
            String bankId = commonsDAO.getNextValueOfSequence("TB_Bank");
            bank.setBankId(bankId);
            bank.setEntityId(entityId);
            // customerSynchDTO字段x2-8/18：银行名称 银行账户
            if (bankInfoDTO.getBranchBankName() == null) {
                // 该字段限制为非空
                bank.setBankName("");
            } else {
                bank.setBankName(bankInfoDTO.getBranchBankName());
            }
            if (bankInfoDTO.getBankAcctCode() == null) {
                // 该字段限制为非空
                bank.setBankAccount("");
            } else {
                bank.setBankAccount(bankInfoDTO.getBankAcctCode());
            }
            bank.setBankAccountName("");
            bank.setBankType("1");
            // 设置非默认状态
            bank.setAccountFlag(DataBaseConstant.DEFAULT_FLAG_NO);
            bank.setCreateUser("0");
            bank.setCreateTime(DateUtil.getCurrentTime());
            bank.setModifyUser("0");
            bank.setModifyTime(DateUtil.getCurrentTime());
            bank.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
            bankDAO.insert(bank);
        } catch (Exception e) {
            logger.error("新增客户" + entityId + "的银行信息失败：" + e.getMessage());
            throw new BizServiceException("新增客户" + entityId + "的银行信息失败。");
        }

    }

    /**
     * 
     * 增加联系人信息
     * 
     * @param contactInfoDTO
     * @param entityId
     * @throws BizServiceException
     */
    private void addContactInfo(ContactInfoDTO contactInfoDTO, String entityId) throws BizServiceException {
        try {
            ContactDTO entityContact = new ContactDTO();
            entityContact.setEntityId(entityId);
            // customerSynchDTO字段x3-11/18：联系人名称 联系人电话 联系人手机
            entityContact.setContactName(contactInfoDTO.getContactName());
            entityContact.setContactTelephone(contactInfoDTO.getContactTelephone());
            entityContact.setContactMobilePhone(contactInfoDTO.getContactMobilePhone());
            // 设置非默认状态
            entityContact.setDefaultFlag(DataBaseConstant.DEFAULT_FLAG_NO);
            entityContact.setContactType("1");
            entityContact.setContactGender("1");
            entityContact.setCreateUser("0");
            entityContact.setModifyUser("0");
            entityContact.setValidityFlag("1");
            entityContact.setPapersNo("1");
            entityContact.setPapersType("1");
            contactService.insert(entityContact);
        } catch (Exception e) {
            logger.error("新增客户" + entityId + "的联系人信息失败：" + e.getMessage());
            throw new BizServiceException("新增客户" + entityId + "的联系人信息失败。");
        }
    }

    /**
     * 
     * 增加发票信息
     * 
     * @param customerSynchDTO
     * @throws BizServiceException
     */
    private void addInvoiceInfo(CustomerSynchDTO customerSynchDTO) throws BizServiceException {
        try {
            CustomerInvoiceInfo customerInvoiceInfo = new CustomerInvoiceInfo();
            customerInvoiceInfo.setCustomerEntityId(customerSynchDTO.getEntityId());
            customerInvoiceInfo.setFatherEntityId(customerSynchDTO.getFatherEntityId());
            // customerSynchDTO字段x1：公司注册地址
            customerInvoiceInfo.setRegAddress(customerSynchDTO.getRegAddress());
            // customerSynchDTO字段x4：税类型 税类型描述 税号 税号类型
            customerInvoiceInfo.setTaxTypeCode(customerSynchDTO.getTaxTypeCode());
            customerInvoiceInfo.setTaxTypeDesc(customerSynchDTO.getTaxTypeDesc());
            customerInvoiceInfo.setTaxCode(customerSynchDTO.getTaxCode());
            customerInvoiceInfo.setTaxNoType(customerSynchDTO.getTaxNoType());
            // customerSynchDTO字段x4：付款方式描述 城市邮编 邮政信箱 公司类型
            customerInvoiceInfo.setPaymentTypeDesc(customerSynchDTO.getPaymentTypeDesc());
            customerInvoiceInfo.setCityPostCode(customerSynchDTO.getCityPostCode());
            customerInvoiceInfo.setMailBox(customerSynchDTO.getMailBox());
            customerInvoiceInfo.setCoTypeCode(customerSynchDTO.getCoTypeCode());
            // 取第一条银行信息
            if (customerSynchDTO.getBankInfoList().size() > 0) {
                BankInfoDTO bankInfoDTO = customerSynchDTO.getBankInfoList().get(0);
                // customerSynchDTO字段x2：银行名称 银行账户
                customerInvoiceInfo.setBranchBankName(bankInfoDTO.getBranchBankName());
                customerInvoiceInfo.setBankAcctCode(bankInfoDTO.getBankAcctCode());
            }
            customerInvoiceInfo.setCreatedTime(new Date());
            customerInvoiceInfo.setUpdatedTime(new Date());
            // 剩余8字段：发票种类 收票人地址 收票人 收票人电话 公司名称 开票项目 地址电话 收票部门 需要页面编辑
            customerInvoiceInfoDAO.insert(customerInvoiceInfo);
        } catch (Exception e) {
            logger.error("新增客户" + customerSynchDTO.getEntityId() + "的发票信息失败：" + e.getMessage());
            throw new BizServiceException("新增客户" + customerSynchDTO.getEntityId() + "的发票信息失败。");
        }
    }

    /**
     * 更新客户信息
     * 
     * @param customerSynchDTO 客户同步DTO
     * @throws BizServiceException 业务异常
     */
    public CustomerSynchDTO updateCustomer(CustomerSynchDTO customerSynchDTO) throws BizServiceException {
        try {

            // 更新客户表
            Customer customer = new Customer();
            // 被更新记录的主键
            customer.setEntityId(customerSynchDTO.getEntityId());
            customer.setFatherEntityId(customerSynchDTO.getFatherEntityId());
            // 更新操作共使用DTO中12个字段
            customer.setCustomerName(customerSynchDTO.getCustomerName());
            customer.setCustomerCode(customerSynchDTO.getEntityId());
            customer.setCustomerAddress(customerSynchDTO.getCustomerAddress());
            customer.setCustomerPostcode(customerSynchDTO.getCustomerPostcode());
            customer.setCustomerTelephone(customerSynchDTO.getCustomerTelephone());
            customer.setCustomerFax(customerSynchDTO.getCustomerFax());
            customer.setLicenseId(customerSynchDTO.getLicenseId());
            customer.setRegiCapital(customerSynchDTO.getRegiCapital());
            customer.setLicenseStaValidity(customerSynchDTO.getLicenseStaValidity());
            customer.setLicenseEndValidity(customerSynchDTO.getLicenseEndValidity());
            customer.setCorpName(customerSynchDTO.getCorpName());
            customer.setCorpCredId(customerSynchDTO.getCorpCredId());
            // 未注销
            customer.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
            customer.setModifyUser("0");
            customer.setModifyTime(DateUtil.getCurrentTime());
            customerDAO.updateByPrimaryKeySelective(customer);

            return customerSynchDTO;
        } catch (Exception e) {
            logger.error("供应商:" + customerSynchDTO.getEntityId() + "更新客户信息失败：" + e.getMessage());
            throw new BizServiceException("供应商:" + customerSynchDTO.getEntityId() + "更新客户信息失败。");
        }
    }

    /**
     * @param commonsDAO the commonsDAO to set
     */
    public void setCommonsDAO(CommonsDAO commonsDAO) {
        this.commonsDAO = commonsDAO;
    }

    /**
     * @param customerDAO the customerDAO to set
     */
    public void setCustomerDAO(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    /**
     * @param customerInvoiceInfoDAO the customerInvoiceInfoDAO to set
     */
    public void setCustomerInvoiceInfoDAO(CustomerInvoiceInfoDAO customerInvoiceInfoDAO) {
        this.customerInvoiceInfoDAO = customerInvoiceInfoDAO;
    }

    /**
     * @param contactService the contactService to set
     */
    public void setContactService(ContactService contactService) {
        this.contactService = contactService;
    }

    /**
     * @param bankDAO the bankDAO to set
     */
    public void setBankDAO(BankDAO bankDAO) {
        this.bankDAO = bankDAO;
    }

}

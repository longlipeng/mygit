/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: CommonServiceSupport.java
 * Author:   13040443
 * Date:     2013-10-29 下午02:33:04
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.svc.coupon.dto.InvoiceRequirementsDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.CustomerDAO;
import com.huateng.framework.ibatis.model.Customer;
import com.huateng.framework.ibatis.model.CustomerKey;
import com.huateng.framework.util.StringUtil;
import com.suning.svc.core.common.BaseException;
import com.suning.svc.coupon.constants.DepositConstants;
import com.suning.svc.coupon.constants.OrgnizationConstants;
import com.suning.svc.coupon.constants.SequenceContansts;
import com.suning.svc.coupon.dao.InvoiceMatchingDao;
import com.suning.svc.coupon.service.InvoiceRequirementService;
import com.suning.svc.coupon.util.SerialNumberUtil;
import com.suning.svc.ibatis.dao.DepositProcessorDAO;
import com.suning.svc.ibatis.dao.InvoiceDAO;
import com.suning.svc.ibatis.dao.InvoiceRequirementDAO;
import com.suning.svc.ibatis.model.DepositOrder;
import com.suning.svc.ibatis.model.DepositRefundOrder;
import com.suning.svc.ibatis.model.Invoice;
import com.suning.svc.ibatis.model.InvoiceRequirement;
import com.suning.svc.ibatis.model.InvoiceRequirementExample;

/**
 * 发票需求实现类
 * 
 * @author yanbin
 */
public class InvoiceRequirementServiceImpl implements InvoiceRequirementService {

    private Logger logger = Logger.getLogger(this.getClass());

    private InvoiceRequirementDAO invoiceRequirementDAO;
    private CustomerDAO customerDAO;
    private DepositProcessorDAO depositProcessorDAO;
    private InvoiceDAO invoiceDAO;
    private InvoiceMatchingDao invoiceMatchingDao;
    private PageQueryDAO pageQueryDAO;

    @Override
    public PageDataDTO initInvoiceRequirement(InvoiceRequirementsDTO invoiceRequirementDTO) throws BizServiceException {
        try {
            invoiceRequirementDTO.getLoginUserId();
            return pageQueryDAO.query("INVOICE_REQUIREMENT_SERVICE.abatorgenerated_selectInvoiceRequirement",
                    invoiceRequirementDTO);
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("查询开票需求失败!");
        }
    }

    @Override
    public void modifyInvoice(InvoiceRequirementsDTO invoiceRequirementsDTO) throws BizServiceException {

        String invoiceIds = invoiceRequirementsDTO.getInvoiceIds();
        if (StringUtil.isEmpty(invoiceIds)) {
            return;
        }
        String[] invoiceIdArray = invoiceIds.substring(1).split(",");
        InvoiceRequirement firstRequirement = null;
        InvoiceRequirement otherRequirement = null;
        long invoiceId = SerialNumberUtil.getSequence(SequenceContansts.SEQ_INVOICETEMP_SN);
        long invoiceAmount = 0L;
        long reqWaitAmount = 0L;
        int length = invoiceIdArray.length;
        for (int i = 0; i < length; i++) {
            otherRequirement = invoiceRequirementDAO.selectByPrimaryKey(Long.valueOf(invoiceIdArray[i]));
            if (null == otherRequirement) {
                throw new BizServiceException("开票需求记录不存在,ID:" + invoiceIdArray[i]);
            }
            if (DepositConstants.INVOICE_REQUIRE_STATUS_YES.equals(otherRequirement.getStatus())) {
                throw new BizServiceException("选中记录中有已开票");
            }
            if (i == 0) {
                firstRequirement = otherRequirement;
            }
            if (!StringUtils.equals(firstRequirement.getCustomerEntityId(), otherRequirement.getCustomerEntityId())) {
                throw new BizServiceException("一次开票信息商户必须一致");
            }
            reqWaitAmount = otherRequirement.getWaitAmount();
            invoiceAmount += reqWaitAmount;
            // 向关联表中插入数据
            invoiceMatchingDao.insertInvoiceMatching(otherRequirement.getId(), invoiceId, reqWaitAmount);
            // 修改发票需求表中已开金额和未开金额，调整状态为已完成
            otherRequirement.setYetAmount(otherRequirement.getYetAmount() + reqWaitAmount);
            otherRequirement.setWaitAmount(0L);
            // 调整状态为已完成
            otherRequirement.setStatus(DepositConstants.INVOICE_REQUIRE_STATUS_YES);
            invoiceRequirementDAO.updateByPrimaryKey(otherRequirement);

        }

        Date now = new Date();
        String loginUser = invoiceRequirementsDTO.getLoginUserId();
        Invoice invoice = new Invoice();
        invoice.setId(invoiceId);
        invoice.setMchtEntityId(OrgnizationConstants.HXT_HEFEI_ENTITY_ID);
        invoice.setCustomerEntityId(firstRequirement.getCustomerEntityId());
        invoice.setAssigner(loginUser);
        invoice.setStatus(DepositConstants.INVOICE_REQUIRE_STATUS_NO);
        // 向发票表中插入数据
        invoice.setName(firstRequirement.getInvoiceName());
        invoice.setAmount(invoiceAmount);
        invoice.setFatherEntityId(firstRequirement.getFatherEntityId());
        invoice.setHandInvoicer(loginUser);
        invoice.setHandedTime(now);
        invoice.setCheckInvoicer(loginUser);
        invoice.setCheckedTime(now);
        invoice.setType(DataBaseConstant.INVOICE_TYPE_TWO);
        invoice.setAssignedTime(now);
        invoice.setCreatedTime(now);
        invoice.setUpdatedTime(now);
        invoice.setStatus(DataBaseConstant.INVOICE_ALREADY_BAND);
        invoice.setInvoiceProject(DataBaseConstant.INVOICE_PROJECT_SERVICE);
        invoice.setInvoiceNo(invoiceRequirementsDTO.getInvoiceNO());
        invoiceDAO.insert(invoice);

    }

    @Override
    public PageDataDTO sumMchtInvoiceRequirement(InvoiceRequirementsDTO invoiceRequirementDTO)
            throws BizServiceException {
        try {
            invoiceRequirementDTO.getLoginUserId();
            return pageQueryDAO.query("INVOICE_REQUIREMENT_SERVICE.querySumMchtInvoice",
                    invoiceRequirementDTO);
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("查询汇总开票需求失败!");
        }
    }

    public InvoiceRequirementDAO getInvoiceRequirementDAO() {
        return invoiceRequirementDAO;
    }

    public void setInvoiceRequirementDAO(InvoiceRequirementDAO invoiceRequirementDAO) {
        this.invoiceRequirementDAO = invoiceRequirementDAO;
    }

    public InvoiceDAO getInvoiceDAO() {
        return invoiceDAO;
    }

    public void setInvoiceDAO(InvoiceDAO invoiceDAO) {
        this.invoiceDAO = invoiceDAO;
    }

    public InvoiceMatchingDao getInvoiceMatchingDao() {
        return invoiceMatchingDao;
    }

    public void setInvoiceMatchingDao(InvoiceMatchingDao invoiceMatchingDao) {
        this.invoiceMatchingDao = invoiceMatchingDao;
    }

    @Override
    public void addInvoiceRequirement(DepositOrder depositOrder) {
        InvoiceRequirement invoiceRequirement = new InvoiceRequirement();
        invoiceRequirement.setId(SerialNumberUtil.getSequence(SequenceContansts.SEQ_INVOICE_REQUIREMENT));
        invoiceRequirement.setRefOrderId(depositOrder.getId());
        invoiceRequirement.setRefOrderType(DepositConstants.INVOICE_REF_TYPE_DEPOSIT);
        invoiceRequirement.setYetAmount(0L);
        invoiceRequirement.setWaitAmount(depositOrder.getAmount());

        CustomerKey customerKey = new CustomerKey();
        customerKey.setEntityId(depositOrder.getCustomerEntityId());
        customerKey.setFatherEntityId(depositOrder.getFatherEntityId());
        Customer customer = customerDAO.selectByPrimaryKey(customerKey);
        // 客户没有客户名称
        if (null == customer) {
            invoiceRequirement.setInvoiceName(depositOrder.getFatherEntityId() + "-"
                    + depositOrder.getCustomerEntityId());
        } else {
            invoiceRequirement.setInvoiceName(customer.getCustomerName());
        }
        invoiceRequirement.setCustomerEntityId(depositOrder.getCustomerEntityId());
        invoiceRequirement.setFatherEntityId(depositOrder.getFatherEntityId());
        invoiceRequirement.setStatus(DepositConstants.INVOICE_REQUIRE_STATUS_NO);
        invoiceRequirement.setInvoiceProject(DataBaseConstant.INVOICE_PROJECT_SERVICE);
        invoiceRequirement.setAspect(DepositConstants.INVOICE_REQUIRE_ASPECT_NORMAL);
        invoiceRequirement.setCreatedTime(new Date());
        invoiceRequirement.setUpdatedTime(new Date());
        invoiceRequirementDAO.insert(invoiceRequirement);
    }

    @Override
    public void addInvoiceRequirement(DepositRefundOrder depositRefundOrder, Long depositOrderId) throws BaseException {

        InvoiceRequirementExample example = new InvoiceRequirementExample();
        example.createCriteria().andRefOrderIdEqualTo(depositOrderId)
                .andRefOrderTypeEqualTo(DepositConstants.INVOICE_REF_TYPE_DEPOSIT);
        @SuppressWarnings("unchecked")
        List<InvoiceRequirement> invoiceRequirements = invoiceRequirementDAO.selectByExample(example);
        if (null == invoiceRequirements || invoiceRequirements.size() == 0) {
            throw new BaseException("找不到原开票记录");
        }
        InvoiceRequirement invoiceRequirement = invoiceRequirements.get(0);
        Long waitAmount = invoiceRequirement.getWaitAmount();
        Long refundAmount = depositRefundOrder.getAmount();

        // 如果发票需求状态为已完成或者待开票金额为0 则直接开红票
        if (DepositConstants.INVOICE_REQUIRE_STATUS_YES.equals(invoiceRequirement.getStatus()) || waitAmount == 0) {
            addRedInvoice(depositRefundOrder, null);
        } else {
            Long minusAmount = waitAmount - refundAmount;
            // 可开票金额相减大于则更新金额和更新时间
            if (minusAmount > 0) {
                depositProcessorDAO.updateMinusInvoiceMoney(invoiceRequirement.getId(), refundAmount);
            }
            // =0 则扣除可开票金额 并且设置状态
            else if (minusAmount == 0) {
                depositProcessorDAO.updateMinusInvoiceMoney(invoiceRequirement.getId(), refundAmount,
                        DepositConstants.INVOICE_REQUIRE_STATUS_YES);
            }
            // <0 则设置未开票金额为0 并增加负金额的红票
            else {
                depositProcessorDAO.updateMinusInvoiceMoney(invoiceRequirement.getId(), waitAmount,
                        DepositConstants.INVOICE_REQUIRE_STATUS_YES);
                addRedInvoice(depositRefundOrder, minusAmount * -1);
            }
        }
    }

    /**
     * 开红票，如果anmout为null 则为充退全开红票。不然按amount金额开红票
     * 
     * @param depositRefundOrder
     * @param amount
     */
    private void addRedInvoice(DepositRefundOrder depositRefundOrder, Long amount) {
        InvoiceRequirement invoiceRequirement = new InvoiceRequirement();
        invoiceRequirement.setId(SerialNumberUtil.getSequence(SequenceContansts.SEQ_INVOICE_REQUIREMENT));
        invoiceRequirement.setRefOrderId(depositRefundOrder.getId());
        invoiceRequirement.setRefOrderType(DepositConstants.INVOICE_REF_TYPE_REFUND);
        invoiceRequirement.setYetAmount(0L);
        if (null == amount) {
            invoiceRequirement.setWaitAmount(depositRefundOrder.getAmount());
        } else {
            invoiceRequirement.setWaitAmount(amount);
        }

        CustomerKey customerKey = new CustomerKey();
        customerKey.setEntityId(depositRefundOrder.getCustomerEntityId());
        customerKey.setFatherEntityId(depositRefundOrder.getFatherEntityId());
        Customer customer = customerDAO.selectByPrimaryKey(customerKey);

        if (null == customer) {
            invoiceRequirement.setInvoiceName(depositRefundOrder.getFatherEntityId() + "-"
                    + depositRefundOrder.getCustomerEntityId());
        } else {
            invoiceRequirement.setInvoiceName(customer.getCustomerName());
        }
        invoiceRequirement.setCustomerEntityId(depositRefundOrder.getCustomerEntityId());
        invoiceRequirement.setFatherEntityId(depositRefundOrder.getFatherEntityId());
        invoiceRequirement.setStatus(DepositConstants.INVOICE_REQUIRE_STATUS_NO);
        invoiceRequirement.setInvoiceProject(DataBaseConstant.INVOICE_PROJECT_SERVICE);
        invoiceRequirement.setAspect(DepositConstants.INVOICE_REQUIRE_ASPECT_RED);
        invoiceRequirement.setCreatedTime(new Date());
        invoiceRequirement.setUpdatedTime(new Date());
        invoiceRequirementDAO.insert(invoiceRequirement);
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public PageQueryDAO getPageQueryDAO() {
        return pageQueryDAO;
    }

    public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
        this.pageQueryDAO = pageQueryDAO;
    }

    /**
     * @return the customerDAO
     */
    public CustomerDAO getCustomerDAO() {
        return customerDAO;
    }

    /**
     * @param customerDAO the customerDAO to set
     */
    public void setCustomerDAO(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    /**
     * @return the depositProcessorDAO
     */
    public DepositProcessorDAO getDepositProcessorDAO() {
        return depositProcessorDAO;
    }

    /**
     * @param depositProcessorDAO the depositProcessorDAO to set
     */
    public void setDepositProcessorDAO(DepositProcessorDAO depositProcessorDAO) {
        this.depositProcessorDAO = depositProcessorDAO;
    }

}
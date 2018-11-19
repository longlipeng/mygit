/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: InvoiceMatchingServiceImpl.java
 * Author:   13040446
 * Date:     2013-10-30 下午03:13:07
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.svc.coupon.dto.InvoiceRequireMentDto;
import com.allinfinance.svc.coupon.dto.InvoiceRequireMentQueryDto;
import com.allinfinance.svc.coupon.dto.InvoiceRequireTempQueryDto;
import com.allinfinance.svc.coupon.dto.InvoiceTempQueryDto;
import com.allinfinance.svc.coupon.dto.SettlementInfoDto;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.CustomerDAO;
import com.huateng.framework.ibatis.dao.EntitySystemParameterDAO;
import com.huateng.framework.ibatis.model.EntitySystemParameter;
import com.huateng.framework.ibatis.model.EntitySystemParameterKey;
import com.huateng.framework.util.ReflectionUtil;
import com.suning.svc.coupon.constants.OrgnizationConstants;
import com.suning.svc.coupon.constants.SequenceContansts;
import com.suning.svc.coupon.constants.SettlementConstants;
import com.suning.svc.coupon.dao.InvoiceMatchingDao;
import com.suning.svc.coupon.service.InvoiceMatchingService;
import com.suning.svc.ibatis.dao.CustomerInvoiceInfoDAO;
import com.suning.svc.ibatis.dao.InvoiceDAO;
import com.suning.svc.ibatis.dao.InvoiceRequirementTempDAO;
import com.suning.svc.ibatis.dao.InvoiceTempDAO;
import com.suning.svc.ibatis.dao.SettlementDAO;
import com.suning.svc.ibatis.model.CustomerInvoiceInfo;
import com.suning.svc.ibatis.model.CustomerInvoiceInfoExample;
import com.suning.svc.ibatis.model.Invoice;
import com.suning.svc.ibatis.model.InvoiceRequirementTemp;
import com.suning.svc.ibatis.model.InvoiceTemp;
import com.suning.svc.ibatis.model.InvoiceTempExample;
import com.suning.svc.ibatis.model.Settlement;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author 13040446
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class InvoiceMatchingServiceImpl implements InvoiceMatchingService {
    /**
     * 日志
     */
    private Logger logger = Logger.getLogger(InvoiceMatchingServiceImpl.class);
    /**
     * 分页查询DAO
     */
    private PageQueryDAO pageQueryDAO;
    /**
     * 发票需求DAO
     */
    private InvoiceRequirementTempDAO invoiceRequirementTempDAO;
    /**
     * 发票临时表DAO
     */
    private InvoiceTempDAO invoiceTempDAO;
    /**
     * 公用DAO
     */
    private BaseDAO baseDAO;
    /**
     * 发票DAO
     */
    private InvoiceDAO invoiceDAO;
    /**
     * 结算单DAO
     */
    private SettlementDAO settlementDAO;
    /**
     * 匹配发票DAO
     */
    private InvoiceMatchingDao invoiceMatchingDao;
    /**
     * 客户DAO
     */
    private CustomerDAO customerDAO;

    private EntitySystemParameterDAO entitySystemParameterDAO;

    private CustomerInvoiceInfoDAO customerInvoiceInfoDAO;

    /**
     * 根据结算单查询可开发票需求
     * 
     * @param dto
     * @return PageDataDTO
     */
    public PageDataDTO queryInvoiceRequire(InvoiceRequireMentQueryDto dto) throws BizServiceException {
        try {
            return pageQueryDAO.query("INVOICE_MATCHING.queryInvoiceRequire", dto);
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("查询发票需求失败！");
        }
    }

    /**
     * 查询发票需求临时表
     * 
     * @param dto
     * @return PageDataDTO
     */
    public PageDataDTO queryInvoiceRequireTemp(InvoiceRequireTempQueryDto dto) throws BizServiceException {
        try {
            return pageQueryDAO.query("INVOICE_MATCHING.queryInvoiceRequireTemp", dto);
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("查询发票需求临时表失败！");
        }
    }

    /**
     * 查询发票临时表
     * 
     * @param dto
     * @return PageDataDTO
     */
    public PageDataDTO queryInvoiceTemp(InvoiceTempQueryDto dto) throws BizServiceException {
        try {
            return pageQueryDAO.query("INVOICE_MATCHING.queryInvoiceTemp", dto);
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("查询发票临时表失败！");
        }
    }

    /**
     * 发票临时表入库
     * 
     * @param dto
     * @return
     */
    public void insertTemp(InvoiceRequireMentQueryDto dto) throws BizServiceException {
        this.logger.info("插入发票临时表");
        String[] ids = dto.getInvoiceRequireIds();
        if (ids == null || ids.length == 0) {
            return;
        }
        long amount = 0;
        for (String s : ids) {
            amount += Long.valueOf(s.split(",")[4]);
        }
        Settlement record = settlementDAO.selectByPrimaryKey(dto.getSettlementId());
        if (amount > record.getWaitIvcAmount()) {
            throw new BizServiceException("申请金额大于结算单可开票金额！");
        }
        InvoiceTemp invoiceTemp = new InvoiceTemp();
        // 根据sequence建id
        invoiceTemp.setId(Long.valueOf(baseDAO.getNextValueOfSequence(SequenceContansts.SEQ_INVOICETEMP_SN)));
        invoiceTemp.setSettlementId(dto.getSettlementId());
        String fatherEntityId = ids[0].split(",")[1];
        String customerEntityId = ids[0].split(",")[2];
        String customerName = ids[0].split(",")[3];
        invoiceTemp.setFatherEntityId(fatherEntityId);
        invoiceTemp.setCustomerEntityId(customerEntityId);
        invoiceTemp.setCustomerName(customerName);
        invoiceTemp.setInvoiceName(customerName);
        invoiceTemp.setInvoiceProject(dto.getInvoiceProject());

        for (String s : ids) {
            InvoiceRequirementTemp invoiceRequirementTemp = new InvoiceRequirementTemp();
            invoiceRequirementTemp.setInvoiceRequireId(Long.valueOf(s.split(",")[0]));
            invoiceRequirementTemp.setFatherEntityId(fatherEntityId);
            invoiceRequirementTemp.setCustomerEntityId(customerEntityId);
            invoiceRequirementTemp.setCustomerName(customerName);
            invoiceRequirementTemp.setAmount(Long.valueOf(s.split(",")[4]));
            invoiceRequirementTemp.setInvoicetmpId(invoiceTemp.getId());
            invoiceRequirementTempDAO.insert(invoiceRequirementTemp);

        }
        invoiceTemp.setAmount(amount);
        invoiceTempDAO.insert(invoiceTemp);

    }

    /**
     * 发票入库、插入关联表
     * 
     * @param dto
     * @return
     */
    @SuppressWarnings("unchecked")
    public void insertInvoice(InvoiceRequireMentQueryDto dto) throws BizServiceException {
        this.logger.info("插入发票正式表");

        InvoiceTempExample invoiceTempExample = new InvoiceTempExample();
        invoiceTempExample.createCriteria().andSettlementIdEqualTo(dto.getSettlementId());
        List<InvoiceTemp> list = invoiceTempDAO.selectByExample(invoiceTempExample);
        Settlement record = settlementDAO.selectByPrimaryKey(dto.getSettlementId());
        long amount = 0;
        for (InvoiceTemp invoiceTemp : list) {
            amount += invoiceTemp.getAmount();
            if (invoiceTemp.getFatherEntityId() == record.getFatherEntityId()
                    && invoiceTemp.getCustomerEntityId() == record.getMchtEntityId()) {
                throw new BizServiceException("商户不能给自己开票！");
            }
        }

        this.logger.info("判断申请金额否大于结算单可开票金额");
        if (amount > record.getWaitIvcAmount()) {
            throw new BizServiceException("申请金额大于结算单可开票金额！");
        }
        List<InvoiceRequireMentDto> invoiceReqDto = baseDAO.queryForList("INVOICE_MATCHING",
                "invoiceRequire_selectBySettlementId", dto);
        this.logger.info("判断申请金额是否大于需求未开票金额");
        for (InvoiceRequireMentDto invoiceRequireMentDto : invoiceReqDto) {
            if (invoiceRequireMentDto.getAmount() > invoiceRequireMentDto.getWaitAmount()) {
                throw new BizServiceException("申请金额大于需求未开票金额！");
            }
        }
        this.logger.info("根据发票临时表入正式表");
        for (InvoiceTemp invoiceTemp : list) {
            Invoice invoice = new Invoice();

            invoice.setId(invoiceTemp.getId());
            invoice.setAmount(invoiceTemp.getAmount());
            invoice.setCreatedTime(new Date());
            invoice.setAssignedTime(new Date());
            invoice.setName(invoiceTemp.getInvoiceName());
            invoice.setStatus(DataBaseConstant.INVOICE_ALREADY_DISTRI);// 0-已匹配、1-已收票、2-已交票
            invoice.setType(DataBaseConstant.INVOICE_TYPE_ONE);
            invoice.setFatherEntityId(invoiceTemp.getFatherEntityId());
            // 客户号
            invoice.setCustomerEntityId(invoiceTemp.getCustomerEntityId());
            // 商户号
            invoice.setMchtEntityId(record.getMchtEntityId());
            invoice.setAssigner(dto.getUserName());// 分配人
            invoice.setInvoiceProject(invoiceTemp.getInvoiceProject());// 开票项目

            // 插入发票正式表
            invoiceDAO.insert(invoice);

        }
        invoiceMatchingDao.insert("INVOICE_MATCHING.invoiceRequirementref_insert", dto);
        invoiceMatchingDao.insert("INVOICE_MATCHING.settlementInvoiceRef_insert", dto);
        dto.setNewDate(new Date());
        invoiceMatchingDao.update("INVOICE_MATCHING.invoiceRequirement_updateBysettlementId", dto);
        invoiceMatchingDao.update("INVOICE_MATCHING.invoiceRequire_updateStatus", dto);
        long yetAmount = record.getWaitIvcAmount() - amount;
        Settlement newValues = new Settlement();
        newValues.setId(record.getId());
        newValues.setWaitIvcAmount(yetAmount);
        newValues.setReceivedInvoinceAmount(record.getReceivedInvoinceAmount() + amount);
        newValues.setUpdatedTime(new Date());
        if (yetAmount == 0) {
            newValues.setStatus(SettlementConstants.SETTLEMENT_STATUS_ONE);// 结算单状态改为已匹配发票
        }
        // 修改结算单待开票金额 和状态
        settlementDAO.updateByPrimaryKeySelective(newValues);

        dto.setInvoiceTmpIds(null);
        deleteTemp(dto);

    }

    /**
     * 删除发票临时表数据
     * 
     * @param dto
     * @return
     */
    public void deleteTemp(InvoiceRequireMentQueryDto dto) throws BizServiceException {
        this.logger.info("删除发票临时表");
        try {
            invoiceMatchingDao.delete("INVOICE_MATCHING.invoiceRequireTemp_deleteByPrimaryKey", dto);
            invoiceMatchingDao.delete("INVOICE_MATCHING.invoiceTemp_deleteByPrimaryKey", dto);
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("删除发票临时表失败！");
        }
    }

    /**
     * 根据结算单ID 查询
     * 
     * @param dto
     * @return SettlementInfoDto
     */
    public SettlementInfoDto querySettlementById(SettlementInfoDto dto) throws BizServiceException {
        this.logger.info("根据结算单ID查询");
        try {
            Settlement record = settlementDAO.selectByPrimaryKey(dto.getId());
            SettlementInfoDto rlt = new SettlementInfoDto();
            ReflectionUtil.copyProperties(record, rlt);
            return rlt;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("查询结算单信息失败!");
        }
    }

    /**
     * 增加普通发票
     * 
     * @param dto
     * @return
     */
    public void insertCommonInvoice(InvoiceRequireMentQueryDto dto) throws BizServiceException {
        this.logger.info("结算单开普通发票");
        Settlement record = settlementDAO.selectByPrimaryKey(dto.getSettlementId());
        long amount = record.getWaitIvcAmount() - dto.getCommonAmount();
        if (amount < 0) {
            throw new BizServiceException("开票金额大于结算单可开票金额！");
        }

        Invoice invoice = new Invoice();

        invoice.setId(Long.valueOf(baseDAO.getNextValueOfSequence(SequenceContansts.SEQ_INVOICETEMP_SN)));
        invoice.setAmount(dto.getCommonAmount());
        invoice.setCreatedTime(new Date());
        invoice.setAssignedTime(new Date());
        // 0-已匹配、1-已收票、2-已交票
        invoice.setStatus(DataBaseConstant.INVOICE_NOT_MAKE);
        // 2-普票、1-增票
        invoice.setType(DataBaseConstant.INVOICE_TYPE_TWO);
        // 华夏通-5101
        invoice.setFatherEntityId(OrgnizationConstants.HXT_ENTITY_ID);
        // 开给华夏通-合肥分公司
        invoice.setCustomerEntityId(OrgnizationConstants.HXT_HEFEI_ENTITY_ID);
        invoice.setInvoiceProject(dto.getInvoiceProject());
        // 结算单商户号
        invoice.setMchtEntityId(record.getMchtEntityId());
        invoice.setAssigner(dto.getUserName());
        EntitySystemParameterKey key = new EntitySystemParameterKey();
        key.setFatherEntityId(OrgnizationConstants.FATHER_ENTITY_ID);
        key.setEntityId(OrgnizationConstants.HXT_ENTITY_ID);
        key.setParameterCode(OrgnizationConstants.HXT_INVOICE_NAME);
        EntitySystemParameter entitySystemParameter = entitySystemParameterDAO.selectByPrimaryKey(key);

        // 华夏通没有在系统表维护发票名称
        if (null == entitySystemParameter) {
            invoice.setName(OrgnizationConstants.HXT_HEFEI_ENTITY_ID);
        } else {
            invoice.setName(entitySystemParameter.getParameterValue());
        }

        // 插入发票正式表
        this.logger.info("普通发票入正式表");
        invoiceDAO.insert(invoice);
        dto.setInvoiceId(invoice.getId());
        this.logger.info("插入结算发票关系表");
        invoiceMatchingDao.insert("INVOICE_MATCHING.settlementInvRef_insert", dto);
        Settlement newValues = new Settlement();
        newValues.setId(record.getId());
        newValues.setWaitIvcAmount(amount);
        newValues.setReceivedInvoinceAmount(record.getReceivedInvoinceAmount() + dto.getCommonAmount());
        newValues.setUpdatedTime(new Date());
        if (amount == 0) {
            newValues.setStatus(SettlementConstants.SETTLEMENT_STATUS_ONE);// 结算单状态改为已匹配发票
        }
        // 修改结算单待开票金额 和状态
        settlementDAO.updateByPrimaryKeySelective(newValues);
    }

    /**
     * 根据结算单查询结算单发票详情
     * 
     * @param dto
     * @return SettlementInfoDto
     */
    public PageDataDTO queryInvoiceSettleView(InvoiceRequireMentQueryDto dto) throws BizServiceException {
        try {
            return pageQueryDAO.query("INVOICE_MATCHING.queryInvoiceBySettleId", dto);
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("查询结算单发票详情失败！");
        }
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, String>> queryInvoiceProject(InvoiceRequireMentDto dto) throws BizServiceException {
        CustomerInvoiceInfoExample customerInvoiceInfoExample = new CustomerInvoiceInfoExample();
        customerInvoiceInfoExample.createCriteria().andFatherEntityIdEqualTo(OrgnizationConstants.HXT_ENTITY_ID)
                .andCustomerEntityIdEqualTo(dto.getCustomerCode());
        CustomerInvoiceInfo customerInvoiceInfo;
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        try {
            List<CustomerInvoiceInfo> listCustomer = customerInvoiceInfoDAO.selectByExample(customerInvoiceInfoExample);
            if (listCustomer != null && listCustomer.size() > 0) {
                customerInvoiceInfo = listCustomer.get(0);
                if (null == customerInvoiceInfo) {
                    return list;
                }
                String invoiceItem = customerInvoiceInfo.getInvoiceItem();
                if (null != invoiceItem && !"".equals(invoiceItem)) {
                    String[] items = customerInvoiceInfo.getInvoiceItem().split(",");
                    dto.setItems(items);
                    dto.setDictTypeCode(DataBaseConstant.DICT_TYPE_CODE);
                    return list = baseDAO.queryForList("INVOICE_MATCHING", "queryInvoiceProject", dto);
                }
            }

        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("查询客户发票信息失败！");
        }
        return list;
    }

    public long querySumAmount(InvoiceRequireMentQueryDto dto) throws BizServiceException {
        Long sumAmount = (Long) baseDAO.queryForObject("INVOICE_MATCHING", "querySumAmount", dto);
        if (null == sumAmount) {
            return 0;
        }
        return sumAmount;
    }

    /**
     * @return the pageQueryDAO
     */
    public PageQueryDAO getPageQueryDAO() {
        return pageQueryDAO;
    }

    /**
     * @param pageQueryDAO the pageQueryDAO to set
     */
    public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
        this.pageQueryDAO = pageQueryDAO;
    }

    /**
     * @return the invoiceRequirementTempDAO
     */
    public InvoiceRequirementTempDAO getInvoiceRequirementTempDAO() {
        return invoiceRequirementTempDAO;
    }

    /**
     * @param invoiceRequirementTempDAO the invoiceRequirementTempDAO to set
     */
    public void setInvoiceRequirementTempDAO(InvoiceRequirementTempDAO invoiceRequirementTempDAO) {
        this.invoiceRequirementTempDAO = invoiceRequirementTempDAO;
    }

    /**
     * @return the invoiceTempDAO
     */
    public InvoiceTempDAO getInvoiceTempDAO() {
        return invoiceTempDAO;
    }

    /**
     * @param invoiceTempDAO the invoiceTempDAO to set
     */
    public void setInvoiceTempDAO(InvoiceTempDAO invoiceTempDAO) {
        this.invoiceTempDAO = invoiceTempDAO;
    }

    /**
     * @return the baseDAO
     */
    public BaseDAO getBaseDAO() {
        return baseDAO;
    }

    /**
     * @param baseDAO the baseDAO to set
     */
    public void setBaseDAO(BaseDAO baseDAO) {
        this.baseDAO = baseDAO;
    }

    /**
     * @param settlementDAO the settlementDAO to set
     */
    public void setSettlementDAO(SettlementDAO settlementDAO) {
        this.settlementDAO = settlementDAO;
    }

    /**
     * @return the invoiceDAO
     */
    public InvoiceDAO getInvoiceDAO() {
        return invoiceDAO;
    }

    /**
     * @param invoiceDAO the invoiceDAO to set
     */
    public void setInvoiceDAO(InvoiceDAO invoiceDAO) {
        this.invoiceDAO = invoiceDAO;
    }

    /**
     * @return the invoiceMatchingDao
     */
    public InvoiceMatchingDao getInvoiceMatchingDao() {
        return invoiceMatchingDao;
    }

    /**
     * @param invoiceMatchingDao the invoiceMatchingDao to set
     */
    public void setInvoiceMatchingDao(InvoiceMatchingDao invoiceMatchingDao) {
        this.invoiceMatchingDao = invoiceMatchingDao;
    }

    /**
     * @return the settlementDAO
     */
    public SettlementDAO getSettlementDAO() {
        return settlementDAO;
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
     * @return the entitySystemParameterDAO
     */
    public EntitySystemParameterDAO getEntitySystemParameterDAO() {
        return entitySystemParameterDAO;
    }

    /**
     * @param entitySystemParameterDAO the entitySystemParameterDAO to set
     */
    public void setEntitySystemParameterDAO(EntitySystemParameterDAO entitySystemParameterDAO) {
        this.entitySystemParameterDAO = entitySystemParameterDAO;
    }

    /**
     * @return the customerInvoiceInfoDAO
     */
    public CustomerInvoiceInfoDAO getCustomerInvoiceInfoDAO() {
        return customerInvoiceInfoDAO;
    }

    /**
     * @param customerInvoiceInfoDAO the customerInvoiceInfoDAO to set
     */
    public void setCustomerInvoiceInfoDAO(CustomerInvoiceInfoDAO customerInvoiceInfoDAO) {
        this.customerInvoiceInfoDAO = customerInvoiceInfoDAO;
    }

}

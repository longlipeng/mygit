/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: SettlementBizImpl.java
 * Author:   11051612
 * Date:     2013-10-29 上午10:55:08
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionTemplate;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.svc.coupon.dto.DoSettleDto;
import com.allinfinance.svc.coupon.dto.SettleStatisticDto;
import com.allinfinance.svc.coupon.dto.SettlementQueryDto;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.EntitySystemParameterDAO;
import com.huateng.framework.ibatis.dao.SystemParameterDAO;
import com.huateng.framework.ibatis.model.EntitySystemParameter;
import com.huateng.framework.ibatis.model.EntitySystemParameterKey;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.FtpUtil;
import com.suning.svc.coupon.constants.OrgnizationConstants;
import com.suning.svc.coupon.constants.SequenceContansts;
import com.suning.svc.coupon.constants.SettlementConstants;
import com.suning.svc.coupon.dao.SettlementInvoiceDao;
import com.suning.svc.coupon.dao.SettlementTradeDao;
import com.suning.svc.coupon.service.SettlementBiz;
import com.suning.svc.coupon.util.ExcelBean;
import com.suning.svc.coupon.util.ExcelUtil;
import com.suning.svc.coupon.util.SerialNumberUtil;
import com.suning.svc.ibatis.dao.SettleBatchDAO;
import com.suning.svc.ibatis.dao.SettlementDAO;
import com.suning.svc.ibatis.model.SettleBatch;
import com.suning.svc.ibatis.model.Settlement;
import com.suning.svc.ibatis.model.SettlementExample;

/**
 * 结算
 * 
 * @author 孙超
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class SettlementBizImpl implements SettlementBiz {

    private static final Logger log = LoggerFactory.getLogger(SettlementBizImpl.class);
    private CommonsDAO commonsDAO;
    private BaseDAO baseDAO;
    private SettleBatchDAO settleBatchDAO;
    private SystemParameterDAO systemParameterDAO;
    private PageQueryDAO pageQueryDAO;
    private EntitySystemParameterDAO entitySystemParameterDAO;
    private TransactionTemplate transactionTemplate;
    private SettlementDAO settlementDAO;
    private SettlementTradeDao settlementTradeDao;

    private SettlementInvoiceDao settlementInvoiceDao;

    /** FTP IP */
    private String sendSopIp;

    /** FTP PORT */
    private int sendSopPort;

    /** FTP USR */
    private String sendSopUsername;

    /** FTP PWD */
    private String sendSopPassword;

    /** FTP PATH */
    private String sendSopPath;

    /*
     * (non-Jsdoc)
     * @see com.suning.svc.coupon.service.SettlementBiz#settle()
     */
    @Override
    public void settle(final DoSettleDto doSettleDto) throws BizServiceException {
        Date startDate = strToDate(doSettleDto.getStartDateStr());
        Date endDate = strToDate(doSettleDto.getEndDateStr());
        // 判断账期内是否有结算在执行
        if (isSettleWorking(startDate, endDate)) {
            throw new BizServiceException("账期内有结算任务在执行，请稍后再试");
        }
        doSettle(doSettleDto);

    }

    private void doSettle(DoSettleDto doSettleDto) throws BizServiceException {
        Date startDate = strToDate(doSettleDto.getStartDateStr());
        Date endDate = strToDate(doSettleDto.getEndDateStr());
        // 生成结算批次,正在处理中
        SettleBatch batch = new SettleBatch();
        Long batchId = null;
        try {
            batchId = Long.valueOf(commonsDAO.getNextValueOfSequenceBySequence(SequenceContansts.SEQ_SETTLE_BATCH));
        } catch (BizServiceException e) {
            log.error("结算批次产生失败");
            throw new BizServiceException("结算批次号产生失败 ");
        }
        batch.setId(batchId);
        batch.setStartDate(startDate);
        batch.setEndDate(endDate);
        batch.setCreatedTime(new Date());
        batch.setStatus(SettlementConstants.SETTLEMENT_INING);
        batch.setUpdatedTime(new Date());
        settleBatchDAO.insert(batch);
        // 将结算批次号更新到消费明细表
        Map<String, Object> updateConBatchMap = new HashMap<String, Object>();
        updateConBatchMap.put("settleBatchId", batchId);
        updateConBatchMap.put("updatedTime", new Date());
        String startDateStr;
        String endDateStr;
        startDateStr = formatDate(startDate);
        endDateStr = formatDate(endDate);
        updateConBatchMap.put("beginDate", startDateStr);
        updateConBatchMap.put("endDate", endDateStr);
        updateConBatchMap.put("fatherEntityId", doSettleDto.getFatherEntityId());
        int updateCount = baseDAO.update("COUPONSETTLEMENT.batchSettlement", updateConBatchMap);
        if (updateCount == 0) {
            endBatch(batchId);
            throw new BizServiceException("账期内没有消费或退货记录");
        }
        // 获取费率
        EntitySystemParameterKey key = new EntitySystemParameterKey();
        key.setEntityId(doSettleDto.getFatherEntityId());
        key.setFatherEntityId(OrgnizationConstants.FATHER_ENTITY_ID);
        key.setParameterCode(SettlementConstants.SETTLEMENT_COMMISSION_KEY);
        EntitySystemParameter entitySystemParameter = entitySystemParameterDAO.selectByPrimaryKey(key);
        // SystemParameter systemParamter = systemParameterDAO
        // .selectByPrimaryKey(SettlementConstants.SETTLEMENT_COMMISSION_KEY);
        if (entitySystemParameter == null) {
            endBatch(batchId);
            log.error("还没设置费率,请先设置");
            throw new BizServiceException("还没设置费率,请先设置");
        }
        Double rate = Double.valueOf(entitySystemParameter.getParameterValue());
        // 根据批次商户号，对明细表进行汇总生成结算单
        List<SettleStatisticDto> settleDtoList = baseDAO.queryForList("COUPONSETTLEMENT.settleByMcht", batchId);
        List<Settlement> settlementList = new ArrayList<Settlement>();
        // 插入结算单表
        for (SettleStatisticDto dto : settleDtoList) {
            Settlement settlement = new Settlement();
            long id = SerialNumberUtil.getSequence(SequenceContansts.SEQ_SETTLEMENT);
            settlement.setId(id);
            settlement.setSettleNo(SerialNumberUtil.getSerialNumberOf16(id));
            settlement.setBeginDate(startDate);
            settlement.setCommissionAmount(calCommission(dto.getSettleMoney(), rate));
            settlement.setCreatedTime(new Date());
            settlement.setEndDate(endDate);
            // settlement.setMchtCode(dto.getMchtNo());
            settlement.setMchtEntityId(dto.getMchtNo());
            settlement.setFatherEntityId(OrgnizationConstants.HXT_ENTITY_ID);
            settlement.setReceivedInvoinceAmount(0L);
            settlement.setSettleBatchId(batchId);
            // 结算好不用了 结算单状态
            settlement.setStatus(SettlementConstants.SETTLEMENT_INIT_STATE);
            settlement.setTotalAmount(dto.getSettleMoney());
            settlement.setWaitIvcAmount(dto.getSettleMoney() - settlement.getCommissionAmount());
            settlementList.add(settlement);
        }
        commonsDAO.batchInsert("TB_SETTLEMENT.abatorgenerated_insert", settlementList);
        // 更新批次状态
        endBatch(batchId);
    }

    /**
     * 结束当前批次
     * 
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private void endBatch(Long batchId) {
        SettleBatch batch = new SettleBatch();
        batch.setId(batchId);
        batch.setStatus(SettlementConstants.SETTLEMENT_OVER);
        settleBatchDAO.updateByPrimaryKeySelective(batch);
    }

    /**
     * 计算佣金，精确到分
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private long calCommission(long settleMoney, double rate) {
        Double commission = settleMoney * rate / 100;
        return commission.longValue();
    }

    private String formatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(date);
    }

    public Date strToDate(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = format.parse(dateStr);
        } catch (ParseException e) {
        }
        return d;
    }

    // public static void main(String[] args) {
    // System.out.println(-1L < 0);
    // System.out.println(Math.abs(-1L));
    // Double dd = Double.valueOf("2.5");
    // System.out.println(dd);
    // System.out.println(dd.longValue());
    // }

    /**
     * 查询是否有重复的任务在执行,
     * 
     * @param beginDate
     * @param endDate
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private boolean isSettleWorking(Date startDate, Date endDate) {
        Map<String, Object> conditions = new HashMap<String, Object>();
        conditions.put("settleState", SettlementConstants.SETTLEMENT_INING);
        conditions.put("startDate", startDate);
        conditions.put("endDate", endDate);
        Integer count = (Integer) baseDAO.queryForObject("COUPONSETTLEMENT.judgeWetherSettleIng", conditions);
        if (count > 0) {
            return true;
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * @see com.suning.svc.coupon.service.SettlementBiz#querySettlementByPage(com
     * .suning.svc.coupon.dto.VirtualCardQueryDto)
     */
    @Override
    public PageDataDTO querySettlementByPage(SettlementQueryDto dto) {
        PageDataDTO p = pageQueryDAO.query("COUPONSETTLEMENT.querySettlementByPage", dto);
        return p;
    }

    public void sendCheckToSOP(SettlementQueryDto settlementQueryDto) {
        for (Long settlementId : settlementQueryDto.getSettlementIds()) {
            Settlement settlement = settlementDAO.selectByPrimaryKey(settlementId);
            String mchtNo = settlement.getMchtEntityId();
            // 判断是否自营
            if (mchtNo.startsWith("RE")) {
                continue;
            }
            try {
                // 掉接口
                createSettlementDetailToSop(settlement);
            } catch (Exception e) {
                log.error("发送结算单号为{}到开放平台失败,原因{}", settlement.getSettleNo(), e.getMessage());
                continue;
            }
            settlement.setSendStatus("1");
            settlementDAO.updateByPrimaryKeySelective(settlement);
        }
    }

    public void sendAllToSOP(SettlementQueryDto settlementQueryDto) {
        SettlementExample example = new SettlementExample();
        SettlementExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(settlementQueryDto.getMchtCode())) {
            criteria.andMchtEntityIdEqualTo(settlementQueryDto.getMchtCode());
        }
        if (StringUtils.isNotBlank(settlementQueryDto.getSendStatus())) {
            criteria.andSendStatusEqualTo(settlementQueryDto.getSendStatus());
        }
        if (StringUtils.isNotBlank(settlementQueryDto.getInvoiceSendStatus())) {
            criteria.andInvoiceSendStatusEqualTo(settlementQueryDto.getInvoiceSendStatus());
        }
        List<Settlement> settlements = settlementDAO.selectByExample(example);
        for (Settlement settlement : settlements) {
            String mchtNo = settlement.getMchtEntityId();
            if (mchtNo.startsWith("RE")) {
                continue;
            }
            try {
                // 掉接口
                // settlementToTradeItem(settlement);
                createSettlementDetailToSop(settlement);
            } catch (Exception e) {
                log.error("发送结算单号为{}到开放平台失败,原因{}", settlement.getSettleNo(), e.getMessage());
                continue;
            }
            settlement.setSendStatus("1");
            settlementDAO.updateByPrimaryKeySelective(settlement);
        }
    }

    public void sendInvoiceAllToSOP(SettlementQueryDto settlementQueryDto) {
        SettlementExample example = new SettlementExample();
        SettlementExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(settlementQueryDto.getMchtCode())) {
            criteria.andMchtEntityIdEqualTo(settlementQueryDto.getMchtCode());
        }
        if (StringUtils.isNotBlank(settlementQueryDto.getSendStatus())) {
            criteria.andSendStatusEqualTo(settlementQueryDto.getSendStatus());
        }
        if (StringUtils.isNotBlank(settlementQueryDto.getInvoiceSendStatus())) {
            criteria.andInvoiceSendStatusEqualTo(settlementQueryDto.getInvoiceSendStatus());
        }
        List<Settlement> settlements = settlementDAO.selectByExample(example);
        for (Settlement settlement : settlements) {
            String mchtNo = settlement.getMchtEntityId();
            if (mchtNo.startsWith("RE")) {
                continue;
            }
            try {
                // 掉接口
                createInvoiceDetailToSop(settlement);
            } catch (Exception e) {
                log.error("发送结算单号为{}到开放平台失败,原因{}", settlement.getSettleNo(), e.getMessage());
                continue;
            }
            settlement.setInvoiceSendStatus("1");
            settlementDAO.updateByPrimaryKeySelective(settlement);
        }
    }

    public void sendCheckedInvoiceToSOP(SettlementQueryDto settlementQueryDto) {
        for (Long settlementId : settlementQueryDto.getSettlementIds()) {
            Settlement settlement = settlementDAO.selectByPrimaryKey(settlementId);
            String mchtNo = settlement.getMchtEntityId();
            // 判断是否自营
            if (mchtNo.startsWith("RE")) {
                continue;
            }
            try {
                // 掉接口
                createInvoiceDetailToSop(settlement);
            } catch (Exception e) {
                log.error("发送结算单号为{}到开放平台失败,原因{}", settlement.getSettleNo(), e.getMessage());
                continue;
            }
            settlement.setInvoiceSendStatus("1");
            settlementDAO.updateByPrimaryKeySelective(settlement);
        }
    }

    /**
     * 生成 结算明细excel 发送给 sop
     * 
     * @param id
     */
    private void createSettlementDetailToSop(Settlement settlement) {

        // 根据id查询结算数据
        List<Map<String, String>> lists = settlementTradeDao.selectSettlementTrade(settlement.getMchtEntityId(),
                settlement.getSettleBatchId());

        if (null == lists || lists.size() == 0) {
            log.error(settlement.getId() + "这个结算单无关联的交易信息！");
            return;
        }

        // 字段名
        String[] fields = { "MCHT_ENTITY_ID", "CUSTOMER_NAME", "ITEM_ORDER_NO", "AMOUNT", "TRADE_TIME", "DIRECTION" };
        // 标题台头
        String[] titles = { "商户号", "商户名称", "B2C行项号", "承担金额", "交易时间", "交易类型" };

        ExcelBean excelBean = new ExcelBean();
        excelBean.setDataList(lists);
        excelBean.setFields(fields);
        excelBean.setTitles(titles);
        excelBean.setReturnStream(true);
        // 生成结算明细excel
        ExcelBean resultExcelBean = ExcelUtil.generateExcel(excelBean);
        try {
            FtpUtil.uploadFile(
                    sendSopIp,
                    sendSopPort,
                    sendSopUsername,
                    sendSopPassword,
                    sendSopPath + "/" + settlement.getMchtEntityId(),
                    settlement.getMchtEntityId() + SettlementConstants.SEND_SOP_EXCEL_SHEET_SETTLMENT
                            + DateUtil.date2String(settlement.getBeginDate()) + "~"
                            + DateUtil.date2String(settlement.getEndDate()) + ".xls", resultExcelBean.getInputStream());
        } catch (Exception e) {
            log.error("发送结算明细给开放平台ftp出错：" + e.toString());
        }
    }

    /**
     * 生成发票明细 excel 发送给 sop
     * 
     * @param id
     */
    private void createInvoiceDetailToSop(Settlement settlement) {
        List<Map<String, String>> lists = settlementInvoiceDao.selectSettlementInvoice(settlement.getId());

        if (null == lists || lists.size() == 0) {
            log.error(settlement.getId() + "这个结算单无关联的交易信息！");
            return;
        }

        // 字段名
        String[] invoiceFields = { "MCHTENTITYID", "MERCHANTNAME", "NAME", "AMOUNT", "DEVELOPER", "RECEIVER",
                "TAXCODE", "REGADDRESS", "ADDRESSTEL", "BRANCHBANKNAME", "BANKACCTCODE", "STATUS" };
        // 标题台头
        String[] invoiceTitles = { "商户号", "商户名称", "发票名称", "发票金额", "开票方", "收票方", "纳税人识别号", "地址", "电话", "开户行", "账户", "状态" };

        ExcelBean excelBean = new ExcelBean();
        excelBean.setDataList(lists);
        excelBean.setFields(invoiceFields);
        excelBean.setTitles(invoiceTitles);
        excelBean.setReturnStream(true);
        // 生成发票明细excel
        ExcelBean resultExcelBean = ExcelUtil.generateExcel(excelBean);
        try {
            FtpUtil.uploadFile(
                    sendSopIp,
                    sendSopPort,
                    sendSopUsername,
                    sendSopPassword,
                    sendSopPath + "/" + settlement.getMchtEntityId(),
                    settlement.getMchtEntityId() + SettlementConstants.SEND_SOP_EXCEL_SHEET_INVOICE
                            + DateUtil.date2String(settlement.getBeginDate()) + "~"
                            + DateUtil.date2String(settlement.getEndDate()) + ".xls", resultExcelBean.getInputStream());
        } catch (Exception e) {
            log.error("发送结算发票给开放平台ftp出错：" + e.toString());
        }
    }

    public CommonsDAO getCommonsDAO() {
        return commonsDAO;
    }

    public void setCommonsDAO(CommonsDAO commonsDAO) {
        this.commonsDAO = commonsDAO;
    }

    public BaseDAO getBaseDAO() {
        return baseDAO;
    }

    public void setBaseDAO(BaseDAO baseDAO) {
        this.baseDAO = baseDAO;
    }

    public SettleBatchDAO getSettleBatchDAO() {
        return settleBatchDAO;
    }

    /**
     * @param sendSopIp the sendSopIp to set
     */
    public void setSendSopIp(String sendSopIp) {
        this.sendSopIp = sendSopIp;
    }

    /**
     * @param sendSopPort the sendSopPort to set
     */
    public void setSendSopPort(int sendSopPort) {
        this.sendSopPort = sendSopPort;
    }

    /**
     * @param sendSopUsername the sendSopUsername to set
     */
    public void setSendSopUsername(String sendSopUsername) {
        this.sendSopUsername = sendSopUsername;
    }

    /**
     * @param sendSopPassword the sendSopPassword to set
     */
    public void setSendSopPassword(String sendSopPassword) {
        this.sendSopPassword = sendSopPassword;
    }

    /**
     * @param sendSopPath the sendSopPath to set
     */
    public void setSendSopPath(String sendSopPath) {
        this.sendSopPath = sendSopPath;
    }

    public void setSettleBatchDAO(SettleBatchDAO settleBatchDAO) {
        this.settleBatchDAO = settleBatchDAO;
    }

    public SystemParameterDAO getSystemParameterDAO() {
        return systemParameterDAO;
    }

    public void setSystemParameterDAO(SystemParameterDAO systemParameterDAO) {
        this.systemParameterDAO = systemParameterDAO;
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

    public TransactionTemplate getTransactionTemplate() {
        return transactionTemplate;
    }

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    /**
     * @return the settlementTradeDao
     */
    public SettlementTradeDao getSettlementTradeDao() {
        return settlementTradeDao;
    }

    /**
     * @param settlementTradeDao the settlementTradeDao to set
     */
    public void setSettlementTradeDao(SettlementTradeDao settlementTradeDao) {
        this.settlementTradeDao = settlementTradeDao;
    }

    /**
     * @return the settlementInvoiceDao
     */
    public SettlementInvoiceDao getSettlementInvoiceDao() {
        return settlementInvoiceDao;
    }

    /**
     * @param settlementInvoiceDao the settlementInvoiceDao to set
     */
    public void setSettlementInvoiceDao(SettlementInvoiceDao settlementInvoiceDao) {
        this.settlementInvoiceDao = settlementInvoiceDao;
    }

    public SettlementDAO getSettlementDAO() {
        return settlementDAO;
    }

    public void setSettlementDAO(SettlementDAO settlementDAO) {
        this.settlementDAO = settlementDAO;
    }

}

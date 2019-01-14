/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: SettleMentAction.java
 * Author:   13040446
 * Date:     2013-10-30 下午03:30:06
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.web.action;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.svc.coupon.dto.InvoiceRequireMentDto;
import com.allinfinance.svc.coupon.dto.InvoiceRequireMentQueryDto;
import com.allinfinance.svc.coupon.dto.InvoiceRequireTempQueryDto;
import com.allinfinance.svc.coupon.dto.InvoiceTempQueryDto;
import com.allinfinance.svc.coupon.dto.SettlementInfoDto;
import com.huateng.framework.action.BaseAction;
import com.huateng.framework.constant.DataBaseConstant;

/**
 * 结算单管理
 * 
 * @author 13040446
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class SettleMentAction extends BaseAction {

    private static final long serialVersionUID = 1L;
    /**
     * 日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(SettleMentAction.class);
    /**
     * 查询条件
     */
    private InvoiceRequireMentQueryDto invoiceRequireMentQueryDto = new InvoiceRequireMentQueryDto();

    private InvoiceRequireTempQueryDto invoiceRequireTempQueryDto = new InvoiceRequireTempQueryDto();

    private InvoiceTempQueryDto invoiceTempQueryDto = new InvoiceTempQueryDto();

    private InvoiceRequireMentDto invoiceRequireMentDto = new InvoiceRequireMentDto();
    // private int totalRows = 0;
    /**
     * 发票需求列表
     */
    private PageDataDTO queryInvoiceRequire;
    /**
     * 发票需求临时表列表
     */
    private PageDataDTO queryInvoiceRequireTemp;
    /**
     * 发票临时表列表
     */
    private PageDataDTO queryInvoiceTemp;
    /**
     * 发票需求数组
     */
    private String[] invoiceRequireIds;

    /**
     * 发票临时表ID
     */

    private long[] invoiceTmpIds;
    /**
     * 结算单ID
     */
    private String settleMentId;
    /**
     * 普票金额
     */
    private long commonAmount;

    /**
     * 增票金额
     */
    // private long cutdown;
    private SettlementInfoDto settlementInfoDto = new SettlementInfoDto();
    /**
     * 发票需求列表
     */
    private List<?> result1;
    /**
     * 发票需求列表总数
     */
    private int table1_totalRows = 0;
    /**
     * 发票需求临时表列表
     */
    private List<?> result2;
    /**
     * 发票需求临时表列表总数
     */
    private int table2_totalRows = 0;
    /**
     * 发票临时表列表
     */
    private List<?> result3;
    /**
     * 发票临时表列表总数
     */
    private int table3_totalRows = 0;
    /**
     * 发票列表
     */
    private List<?> invoiceList;
    /**
     * 发票列表总数
     */
    private int invoice_totalRows = 0;

    private List<Map<String, String>> listProject;

    private long sumAmount = 0;

    /**
     * 
     * 结算单匹配发票
     */
    public String toMachingInvoice() {
        try {
            ListPageInit("table1", invoiceRequireMentQueryDto);
            ListPageInit("table2", invoiceRequireTempQueryDto);
            ListPageInit("table3", invoiceTempQueryDto);
            settlementInfoDto.setId(Long.valueOf(settleMentId));
            settlementInfoDto = (SettlementInfoDto) sendService(ConstCode.QUERY_SETTLEMENT_BY_ID, settlementInfoDto)
                    .getDetailvo();
            invoiceRequireMentQueryDto.setSettlementId(Long.valueOf(settleMentId));
            invoiceRequireTempQueryDto.setSettlementId(Long.valueOf(settleMentId));
            invoiceTempQueryDto.setSettlementId(Long.valueOf(settleMentId));
            PageDataDTO dto1 = ((PageDataDTO) sendService(ConstCode.QUERY_INVOICE_REQUIRE, invoiceRequireMentQueryDto)
                    .getDetailvo());
            result1 = dto1.getData();
            table1_totalRows = dto1.getTotalRecord();
            PageDataDTO dto2 = (PageDataDTO) sendService(ConstCode.QUERY_INVOICE_REQUIRE_TEMP,
                    invoiceRequireTempQueryDto).getDetailvo();
            result2 = dto2.getData();
            table2_totalRows = dto2.getTotalRecord();
            PageDataDTO dto3 = ((PageDataDTO) sendService(ConstCode.QUERY_INVOICE_TEMP, invoiceTempQueryDto)
                    .getDetailvo());
            result3 = dto3.getData();
            table3_totalRows = dto3.getTotalRecord();

            sumAmount = (Long) sendService(ConstCode.QUERY_SUM_AMOUNT, invoiceRequireMentQueryDto).getDetailvo();

        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return "maching";
    }

    /**
     * 
     * 新增匹配发票，入发票临时表、发票需求临时表
     */
    public String addMachingInvoice() {
        try {
            invoiceRequireMentQueryDto.setInvoiceRequireIds(invoiceRequireIds);
            invoiceRequireMentQueryDto.setSettlementId(Long.valueOf(settleMentId));
            this.sendService(ConstCode.MACHING_INVOICE_ADD, invoiceRequireMentQueryDto);
            if (!hasActionErrors()) {
                addActionMessage("操作成功！");
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return toMachingInvoice();
    }

    /**
     * 
     * 入发票正式表
     */
    public String addInvoice() {
        try {
            invoiceRequireMentQueryDto.setSettlementId(Long.valueOf(settleMentId));
            if (null != this.getUser()) {
                invoiceRequireMentQueryDto.setUserName(this.getUser().getUsername());
            }
            this.sendService(ConstCode.INVOICE_ADD, invoiceRequireMentQueryDto);
            if (!hasActionErrors()) {
                addActionMessage("保存发票成功！");
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        // return "list";
        return toMachingInvoice();
    }

    /**
     * 
     * 删除发票临时表
     */
    public String delInvoiceTemp() {
        try {
            invoiceRequireMentQueryDto.setInvoiceTmpIds(invoiceTmpIds);
            invoiceRequireMentQueryDto.setSettlementId(Long.valueOf(settleMentId));
            this.sendService(ConstCode.INVOICETEMP_DEL, invoiceRequireMentQueryDto);
            if (!hasActionErrors()) {
                addActionMessage("删除成功！");
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return toMachingInvoice();
    }

    /**
     * 
     * 结算单开普通发票
     */
    @SuppressWarnings("unchecked")
    public String toCommonInvoice() {
        try {
            settlementInfoDto.setId(Long.valueOf(settleMentId));
            settlementInfoDto = (SettlementInfoDto) sendService(ConstCode.QUERY_SETTLEMENT_BY_ID, settlementInfoDto)
                    .getDetailvo();
            invoiceRequireMentDto.setCustomerCode(DataBaseConstant.HXT_ENTITY_ID);
            listProject = (List<Map<String, String>>) sendService(ConstCode.QUERY_INVOICE_PROJECT,
                    invoiceRequireMentDto).getDetailvo();
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return "toCommonInvoice";
    }

    /**
     * 
     * 结算单开普通发票
     */
    public String addCommonInvoice() {
        try {
            invoiceRequireMentQueryDto.setCommonAmount(commonAmount);
            invoiceRequireMentQueryDto.setSettlementId(Long.valueOf(settleMentId));
            if (null != this.getUser()) {
                invoiceRequireMentQueryDto.setUserName(this.getUser().getUsername());
            }
            this.sendService(ConstCode.COMMON_INVOICE_ADD, invoiceRequireMentQueryDto);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return "";
    }

    /**
     * 
     * 结算单发票详情
     */
    public String settleInvoiceView() {
        try {
            ListPageInit("invoice", invoiceRequireMentQueryDto);
            settlementInfoDto.setId(Long.valueOf(settleMentId));
            settlementInfoDto = (SettlementInfoDto) sendService(ConstCode.QUERY_SETTLEMENT_BY_ID, settlementInfoDto)
                    .getDetailvo();
            invoiceRequireMentQueryDto.setSettlementId(Long.valueOf(settleMentId));
            PageDataDTO result = (PageDataDTO) sendService(ConstCode.INVOICE_SETTLE_DETAIL, invoiceRequireMentQueryDto)
                    .getDetailvo();
            invoice_totalRows = result.getTotalRecord();
            invoiceList = result.getData();
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return "view";

    }

    /**
     * @return the invoiceRequireMentDto
     */
    public InvoiceRequireMentDto getInvoiceRequireMentDto() {
        return invoiceRequireMentDto;
    }

    /**
     * @param invoiceRequireMentDto the invoiceRequireMentDto to set
     */
    public void setInvoiceRequireMentDto(InvoiceRequireMentDto invoiceRequireMentDto) {
        this.invoiceRequireMentDto = invoiceRequireMentDto;
    }

    /**
     * @return the listProject
     */
    public List<Map<String, String>> getListProject() {
        return listProject;
    }

    /**
     * @param listProject the listProject to set
     */
    public void setListProject(List<Map<String, String>> listProject) {
        this.listProject = listProject;
    }

    /**
     * 
     * 选择开票项目
     */
    @SuppressWarnings("unchecked")
    public String invoiceProject() {
        listProject = (List<Map<String, String>>) sendService(ConstCode.QUERY_INVOICE_PROJECT, invoiceRequireMentDto)
                .getDetailvo();
        return "choosePro";
    }

    /**
     * @return the invoiceRequireMentQueryDto
     */
    public InvoiceRequireMentQueryDto getInvoiceRequireMentQueryDto() {
        return invoiceRequireMentQueryDto;
    }

    /**
     * @param invoiceRequireMentQueryDto the invoiceRequireMentQueryDto to set
     */
    public void setInvoiceRequireMentQueryDto(InvoiceRequireMentQueryDto invoiceRequireMentQueryDto) {
        this.invoiceRequireMentQueryDto = invoiceRequireMentQueryDto;
    }

    /**
     * @return the queryInvoiceRequire
     */
    public PageDataDTO getQueryInvoiceRequire() {
        return queryInvoiceRequire;
    }

    /**
     * @param queryInvoiceRequire the queryInvoiceRequire to set
     */
    public void setQueryInvoiceRequire(PageDataDTO queryInvoiceRequire) {
        this.queryInvoiceRequire = queryInvoiceRequire;
    }

    /**
     * @return the queryInvoiceRequireTemp
     */
    public PageDataDTO getQueryInvoiceRequireTemp() {
        return queryInvoiceRequireTemp;
    }

    /**
     * @param queryInvoiceRequireTemp the queryInvoiceRequireTemp to set
     */
    public void setQueryInvoiceRequireTemp(PageDataDTO queryInvoiceRequireTemp) {
        this.queryInvoiceRequireTemp = queryInvoiceRequireTemp;
    }

    /**
     * @return the queryInvoiceTemp
     */
    public PageDataDTO getQueryInvoiceTemp() {
        return queryInvoiceTemp;
    }

    /**
     * @param queryInvoiceTemp the queryInvoiceTemp to set
     */
    public void setQueryInvoiceTemp(PageDataDTO queryInvoiceTemp) {
        this.queryInvoiceTemp = queryInvoiceTemp;
    }

    /**
     * @return the invoiceRequireIds
     */
    public String[] getInvoiceRequireIds() {
        return invoiceRequireIds;
    }

    /**
     * @param invoiceRequireIds the invoiceRequireIds to set
     */
    public void setInvoiceRequireIds(String[] invoiceRequireIds) {
        this.invoiceRequireIds = invoiceRequireIds;
    }

    /**
     * @return the settleMentId
     */
    public String getSettleMentId() {
        return settleMentId;
    }

    /**
     * @param settleMentId the settleMentId to set
     */
    public void setSettleMentId(String settleMentId) {
        this.settleMentId = settleMentId;
    }

    /**
     * @return the settlementInfoDto
     */
    public SettlementInfoDto getSettlementInfoDto() {
        return settlementInfoDto;
    }

    /**
     * @param settlementInfoDto the settlementInfoDto to set
     */
    public void setSettlementInfoDto(SettlementInfoDto settlementInfoDto) {
        this.settlementInfoDto = settlementInfoDto;
    }

    /**
     * @return the result1
     */
    public List<?> getResult1() {
        return result1;
    }

    /**
     * @param result1 the result1 to set
     */
    public void setResult1(List<?> result1) {
        this.result1 = result1;
    }

    /**
     * @return the table1_totalRows
     */
    public int getTable1_totalRows() {
        return table1_totalRows;
    }

    /**
     * @param table1_totalRows the table1_totalRows to set
     */
    public void setTable1_totalRows(int table1_totalRows) {
        this.table1_totalRows = table1_totalRows;
    }

    /**
     * @return the result2
     */
    public List<?> getResult2() {
        return result2;
    }

    /**
     * @param result2 the result2 to set
     */
    public void setResult2(List<?> result2) {
        this.result2 = result2;
    }

    /**
     * @return the table2_totalRows
     */
    public int getTable2_totalRows() {
        return table2_totalRows;
    }

    /**
     * @param table2_totalRows the table2_totalRows to set
     */
    public void setTable2_totalRows(int table2_totalRows) {
        this.table2_totalRows = table2_totalRows;
    }

    /**
     * @return the result3
     */
    public List<?> getResult3() {
        return result3;
    }

    /**
     * @param result3 the result3 to set
     */
    public void setResult3(List<?> result3) {
        this.result3 = result3;
    }

    /**
     * @return the table3_totalRows
     */
    public int getTable3_totalRows() {
        return table3_totalRows;
    }

    /**
     * @param table3_totalRows the table3_totalRows to set
     */
    public void setTable3_totalRows(int table3_totalRows) {
        this.table3_totalRows = table3_totalRows;
    }

    /**
     * @return the invoiceTmpIds
     */
    public long[] getInvoiceTmpIds() {
        return invoiceTmpIds;
    }

    /**
     * @param invoiceTmpIds the invoiceTmpIds to set
     */
    public void setInvoiceTmpIds(long[] invoiceTmpIds) {
        this.invoiceTmpIds = invoiceTmpIds;
    }

    /**
     * @return the commonAmount
     */
    public long getCommonAmount() {
        return commonAmount;
    }

    /**
     * @param commonAmount the commonAmount to set
     */
    public void setCommonAmount(long commonAmount) {
        this.commonAmount = commonAmount;
    }

    /**
     * @return the invoiceList
     */
    public List<?> getInvoiceList() {
        return invoiceList;
    }

    /**
     * @param invoiceList the invoiceList to set
     */
    public void setInvoiceList(List<?> invoiceList) {
        this.invoiceList = invoiceList;
    }

    /**
     * @return the invoice_totalRows
     */
    public int getInvoice_totalRows() {
        return invoice_totalRows;
    }

    /**
     * @param invoice_totalRows the invoice_totalRows to set
     */
    public void setInvoice_totalRows(int invoice_totalRows) {
        this.invoice_totalRows = invoice_totalRows;
    }

    /**
     * @return the invoiceRequireTempQueryDto
     */
    public InvoiceRequireTempQueryDto getInvoiceRequireTempQueryDto() {
        return invoiceRequireTempQueryDto;
    }

    /**
     * @param invoiceRequireTempQueryDto the invoiceRequireTempQueryDto to set
     */
    public void setInvoiceRequireTempQueryDto(InvoiceRequireTempQueryDto invoiceRequireTempQueryDto) {
        this.invoiceRequireTempQueryDto = invoiceRequireTempQueryDto;
    }

    /**
     * @return the invoiceTempQueryDto
     */
    public InvoiceTempQueryDto getInvoiceTempQueryDto() {
        return invoiceTempQueryDto;
    }

    /**
     * @param invoiceTempQueryDto the invoiceTempQueryDto to set
     */
    public void setInvoiceTempQueryDto(InvoiceTempQueryDto invoiceTempQueryDto) {
        this.invoiceTempQueryDto = invoiceTempQueryDto;
    }

    /**
     * @return the sumAmount
     */
    public long getSumAmount() {
        return sumAmount;
    }

    /**
     * @param sumAmount the sumAmount to set
     */
    public void setSumAmount(long sumAmount) {
        this.sumAmount = sumAmount;
    }

}

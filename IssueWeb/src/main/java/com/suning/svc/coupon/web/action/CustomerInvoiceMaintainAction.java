/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: CustomerInvoiceMaintainAction.java
 * Author:   12073942
 * Date:     2013-10-30 下午9:47:10
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.web.action;

import java.util.List;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.OperationResult;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.svc.coupon.dto.CustomerInvoiceInfoDTO;
import com.allinfinance.svc.coupon.dto.CustomerInvoiceInfoQueryDTO;
import com.allinfinance.univer.system.dictinfo.dto.EntityDictInfoDTO;
import com.huateng.framework.action.BaseAction;
import com.huateng.framework.util.SystemInfo;

/**
 * 客户发票信息维护
 * 
 * @author LEO
 */
public class CustomerInvoiceMaintainAction extends BaseAction {

    /**
     */
    private static final long serialVersionUID = 1L;

    /**
     * 查询条件
     */
    private CustomerInvoiceInfoQueryDTO customerInvoiceInfoQueryDTO = new CustomerInvoiceInfoQueryDTO();

    /**
     * 对象DTO
     */
    private CustomerInvoiceInfoDTO customerInvoiceInfoDTO = new CustomerInvoiceInfoDTO();

    /**
     * 分页查询结果
     */
    private PageDataDTO pageDataDTO = new PageDataDTO();

    /**
     * 总行数
     */
    private Integer totalRows;

    /**
     * 勾选的记录行ID
     */
    private String[] customerInvoiceIds;

    /**
     * 开票项目
     */
    private String invoiceItem;

    /**
     * 列表查询
     */
    public String list() throws Exception {
        ListPageInit(null, customerInvoiceInfoQueryDTO);
        OperationResult rlt = sendService(ConstCode.CUSTOMER_INVOICE_INQUIRY, customerInvoiceInfoQueryDTO);
        pageDataDTO = (PageDataDTO) rlt.getDetailvo();
        if (null != pageDataDTO && pageDataDTO.getData().size() > 0) {
            totalRows = pageDataDTO.getTotalRecord();
        } else {
            totalRows = 0;
        }
        return "list";
    }

    /**
     * 单条查询
     */
    public String query() throws Exception {
        if (customerInvoiceIds != null && customerInvoiceIds.length == 1) {
            Long id = Long.valueOf(customerInvoiceIds[0]);
            OperationResult rlt = sendService(ConstCode.CUSTOMER_INVOICE_GET, id);
            customerInvoiceInfoDTO = (CustomerInvoiceInfoDTO) rlt.getDetailvo();
        }
        return "input";
    }

    /**
     * 编辑
     */
    public String edit() throws Exception {
        sendService(ConstCode.CUSTOMER_INVOICE_UPDATE, customerInvoiceInfoDTO);
        if (!hasActionErrors()) {
            addActionMessage("编辑客户发票信息成功！");
        }
        return list();
    }

    /**
     * 选择（开票项目）
     */
    public String choose() throws Exception {
        // 取当前用户所属机构
        String entityId = getUser().getEntityId();
        // 查询该机构字典表
        List<EntityDictInfoDTO> list = SystemInfo.getEntityDictInfo().get(entityId).get("120");
        pageDataDTO.setData(list);
        totalRows = list.size();
        return "choice";
    }

    /**
     * @return the customerInvoiceInfoQueryDTO
     */
    public CustomerInvoiceInfoQueryDTO getCustomerInvoiceInfoQueryDTO() {
        return customerInvoiceInfoQueryDTO;
    }

    /**
     * @param customerInvoiceInfoQueryDTO the customerInvoiceInfoQueryDTO to set
     */
    public void setCustomerInvoiceInfoQueryDTO(CustomerInvoiceInfoQueryDTO customerInvoiceInfoQueryDTO) {
        this.customerInvoiceInfoQueryDTO = customerInvoiceInfoQueryDTO;
    }

    /**
     * @return the customerInvoiceInfoDTO
     */
    public CustomerInvoiceInfoDTO getCustomerInvoiceInfoDTO() {
        return customerInvoiceInfoDTO;
    }

    /**
     * @param customerInvoiceInfoDTO the customerInvoiceInfoDTO to set
     */
    public void setCustomerInvoiceInfoDTO(CustomerInvoiceInfoDTO customerInvoiceInfoDTO) {
        this.customerInvoiceInfoDTO = customerInvoiceInfoDTO;
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
    public Integer getTotalRows() {
        return totalRows;
    }

    /**
     * @param totalRows the totalRows to set
     */
    public void setTotalRows(Integer totalRows) {
        this.totalRows = totalRows;
    }

    /**
     * @return the customerInvoiceIds
     */
    public String[] getCustomerInvoiceIds() {
        return customerInvoiceIds;
    }

    /**
     * @param customerInvoiceIds the customerInvoiceIds to set
     */
    public void setCustomerInvoiceIds(String[] customerInvoiceIds) {
        this.customerInvoiceIds = customerInvoiceIds;
    }

    /**
     * @return the invoiceItem
     */
    public String getInvoiceItem() {
        return invoiceItem;
    }

    /**
     * @param invoiceItem the invoiceItem to set
     */
    public void setInvoiceItem(String invoiceItem) {
        this.invoiceItem = invoiceItem;
    }

}

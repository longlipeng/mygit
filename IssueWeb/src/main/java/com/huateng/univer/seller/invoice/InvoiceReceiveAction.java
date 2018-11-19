/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: sss.java
 * Author:   Administrator
 * Date:     2013-11-1 下午04:12:00
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author> gouhao     <time> 2013-11-1 下午04:12:00     <version> 1.0    <desc> 发票管理页面的Action类
 */

package com.huateng.univer.seller.invoice;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.invoice.dto.InvoiceReceiveDTO;
import com.allinfinance.univer.seller.invoice.dto.InvoiceReceiveQueryDTO;
import com.allinfinance.univer.seller.invoice.dto.InvoiceReceiveUpdateDTO;
import com.huateng.framework.action.BaseAction;
import com.huateng.framework.security.model.User;
import com.huateng.framework.util.ReflectionUtil;

/**
 * 发票管理Action<br>
 * 
 * @author gouhao
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@SuppressWarnings("unchecked")
public class InvoiceReceiveAction extends BaseAction {

    /**
     * 日志类.
     */
    private Logger logger = Logger.getLogger(InvoiceReceiveAction.class);
    /**
     * 版本.
     */
    private static final long serialVersionUID = 1L;
    /**
     * 发票查询DTO.
     */
    private InvoiceReceiveQueryDTO invoiceReceiveQueryDTO = new InvoiceReceiveQueryDTO();
    /**
     * 发票信息DTO.
     */
    private InvoiceReceiveDTO invoiceReceiveDTO = new InvoiceReceiveDTO();
    /**
     * 发票更新信息DTO.
     */
    private InvoiceReceiveUpdateDTO invoiceReceiveUpdateDTO = new InvoiceReceiveUpdateDTO();
    /**
     * 页面展示.
     */
    protected List<?> invoiceInfos = new ArrayList();
    /**
     * 页面展示标记位.
     */
    protected int invoiceInfo_totalRows = 0;
    /**
     * 复选框List.
     */
    private List<?> invoiceIdList = new ArrayList();
    /**
     * 发票id
     */
    private String invoiceId = null;

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    /**
     * 发票号
     */
    private String invoiceNO = null;

    public String getInvoiceNO() {
        return invoiceNO;
    }

    public void setInvoiceNO(String invoiceNO) {
        this.invoiceNO = invoiceNO;
    }

    /**
     * 
     * 功能描述:发票管理查询 <br>
     * 发票的收票和交票操作前通过此方法查询发票信息并展示
     * 
     * @return String
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String list() {
        logger.info("InvoiceReceive!list");
        try {

            ListPageInit("invoiceInfo", invoiceReceiveQueryDTO);
            ReflectionUtil.copyProperties(invoiceReceiveQueryDTO, invoiceReceiveDTO);
            invoiceReceiveQueryDTO.setDefaultEntityId(this.getUser().getEntityId());
            invoiceReceiveDTO.setDefaultEntityId(this.getUser().getEntityId());

            PageDataDTO result = (PageDataDTO) sendService(ConstCode.INVOICE_RECEIVE, invoiceReceiveQueryDTO)
                    .getDetailvo();

            invoiceInfos = result.getData();
            invoiceInfo_totalRows = result.getTotalRecord();
            if (hasErrors()) {
                return "input";
            }
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        return "list";
    }

    /**
     * 
     * 功能描述: 收票操作<br>
     * 执行发票的收票或者交票操作
     * 
     * @return String
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String receiveInvoice() {
        try {
            logger.info("InvoiceReceive!receiveInvoice");
            Long id = null;
            if (invoiceId != null) {
                id = Long.parseLong(invoiceId);
            }
            Long[] idss = new Long[1];
            idss[0] = id;
            invoiceReceiveUpdateDTO.setIdss(idss);
            invoiceReceiveUpdateDTO.setInvoiceNO(invoiceNO);
            // 获取登陆操作员信息
            User user = this.getUser();
            if (user != null) {
                invoiceReceiveUpdateDTO.setUserId(user.getUserId());
            }
            this.sendService(ConstCode.INVOICE_RECEIVE_OPERATING, invoiceReceiveUpdateDTO);
            if (!hasErrors()) {
                addActionMessage("收票操作成功！");
            }
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        return list();
    }

    /**
     * 
     * 功能描述: 交票操作<br>
     * 执行发票的收票或者交票操作
     * 
     * @return String
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String handInvoice() {
        try {
            logger.info("InvoiceReceive!bandInvoice");
            ArrayList<Long> ids = new ArrayList<Long>();
            Long id = null;
            if (invoiceId != null) {
                id = Long.parseLong(invoiceId);
            }
            Long[] idss = new Long[1];
            idss[0] = id;
            invoiceReceiveUpdateDTO.setIdss(idss);
            // 获取登陆操作员信息
            User user = this.getUser();
            if (user != null) {
                invoiceReceiveUpdateDTO.setUserId(user.getUserId());
            }
            this.sendService(ConstCode.INVOICE_BAND_OPERATING, invoiceReceiveUpdateDTO);
            if (!hasErrors()) {
                addActionMessage("交票操作成功！");
            }
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        return list();
    }
    
    public InvoiceReceiveUpdateDTO getInvoiceReceiveUpdateDTO() {
        return invoiceReceiveUpdateDTO;
    }

    public void setInvoiceReceiveUpdateDTO(InvoiceReceiveUpdateDTO invoiceReceiveUpdateDTO) {
        this.invoiceReceiveUpdateDTO = invoiceReceiveUpdateDTO;
    }

    public List<?> getInvoiceInfos() {
        return invoiceInfos;
    }

    public void setInvoiceInfos(List<?> invoiceInfos) {
        this.invoiceInfos = invoiceInfos;
    }

    public int getInvoiceInfo_totalRows() {
        return invoiceInfo_totalRows;
    }

    public void setInvoiceInfo_totalRows(int invoiceInfoTotalRows) {
        invoiceInfo_totalRows = invoiceInfoTotalRows;
    }

    public String dateFormat(String date) {
        return date.substring(0, 10);
    }

    public List<?> getInvoiceIdList() {
        return invoiceIdList;
    }

    public void setInvoiceIdList(List<?> invoiceIdList) {
        this.invoiceIdList = invoiceIdList;
    }

    public InvoiceReceiveQueryDTO getInvoiceReceiveQueryDTO() {
        return invoiceReceiveQueryDTO;
    }

    public void setInvoiceReceiveQueryDTO(InvoiceReceiveQueryDTO invoiceReceiveQueryDTO) {
        this.invoiceReceiveQueryDTO = invoiceReceiveQueryDTO;
    }

    public InvoiceReceiveDTO getInvoiceReceiveDTO() {
        return invoiceReceiveDTO;
    }

    public void setInvoiceReceiveDTO(InvoiceReceiveDTO invoiceReceiveDTO) {
        this.invoiceReceiveDTO = invoiceReceiveDTO;
    }

}

package com.suning.svc.coupon.web.action;

/**
 * 发票需求
 * 
 * @author wp
 * 
 */
import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.svc.coupon.dto.InvoiceRequirementsDTO;
import com.huateng.framework.action.BaseAction;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class InvoiceRequirementAction extends BaseAction {

    private InvoiceRequirementsDTO invoiceRequirementsDTO = new InvoiceRequirementsDTO();
    private Logger logger = Logger.getLogger(this.getClass());
    private static final long serialVersionUID = 1L;
    protected List<?> invoiceList = new ArrayList();
    private String invoiceId;
    private String invoiceNO;

    private int totalRows;

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public InvoiceRequirementsDTO getInvoiceRequirementsDTO() {
        return invoiceRequirementsDTO;
    }

    public void setInvoiceRequirementsDTO(InvoiceRequirementsDTO invoiceRequirementsDTO) {
        this.invoiceRequirementsDTO = invoiceRequirementsDTO;
    }

    public String list() {
        try {
            ListPageInit("invoiceList", invoiceRequirementsDTO);
            PageDataDTO pageDataDTO = (PageDataDTO) sendService(ConstCode.FRTCODE_SERVICE_INVOICE_REQUIREMENT_LIST,
                    invoiceRequirementsDTO).getDetailvo();
            invoiceList = pageDataDTO.getData();
            totalRows = pageDataDTO.getTotalRecord();
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }

        return "list";

    }

    public String mchtSumList() {
        try {
            ListPageInit("invoiceList", invoiceRequirementsDTO);
            PageDataDTO pageDataDTO = (PageDataDTO) sendService(ConstCode.SUM_MCHT_INVOICE_REQUIREMENT,
                    invoiceRequirementsDTO).getDetailvo();
            invoiceList = pageDataDTO.getData();
            totalRows = pageDataDTO.getTotalRecord();
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        return "list";
    }

    /**
     * 开票
     * 
     * @return
     */
    public String doInvoice() {
        invoiceRequirementsDTO.setLoginUserId(getUser().getLoginUserId());
        invoiceRequirementsDTO.setInvoiceIds(getInvoiceId());
        invoiceRequirementsDTO.setInvoiceNO(getInvoiceNO());
        sendService(ConstCode.FRTCODE_SERVICE_INVOICE_Matching_REQUIREMENT, invoiceRequirementsDTO);
        addActionMessage("完成开票");
        return list();

    }

    public List<?> getInvoiceList() {
        return invoiceList;
    }

    public void setInvoiceList(List<?> invoiceList) {
        this.invoiceList = invoiceList;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    /**
     * @return the invoiceNO
     */
    public String getInvoiceNO() {
        return invoiceNO;
    }

    /**
     * @param invoiceNO the invoiceNO to set
     */
    public void setInvoiceNO(String invoiceNO) {
        this.invoiceNO = invoiceNO;
    }

}

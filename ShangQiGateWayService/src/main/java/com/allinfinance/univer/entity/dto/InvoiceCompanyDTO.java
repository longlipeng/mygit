package com.allinfinance.univer.entity.dto;

import java.util.List;

import com.allinfinance.framework.dto.BaseDTO;

/**
 * 发票公司信息DTO
 * @author dawn
 *
 */
public class InvoiceCompanyDTO extends BaseDTO {

    private static final long serialVersionUID = -855878892981808509L;

    private String invoiceCompanyId;

    private String customerId;

    private String invoiceCompanyName;

    private String defaultFlag;
    
    private String entityId;
    
    private String createUser;
    
    private String modifyUser;
    
    private String modifyTime;
    
    private String dataState;
    private List<InvoiceCompanyDTO> invoiceCompanyDTO;
    
    public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getDataState() {
		return dataState;
	}

	public void setDataState(String dataState) {
		this.dataState = dataState;
	}

	public List<InvoiceCompanyDTO> getInvoiceCompanyDTO() {
		return invoiceCompanyDTO;
	}

	public void setInvoiceCompanyDTO(List<InvoiceCompanyDTO> invoiceCompanyDTO) {
		this.invoiceCompanyDTO = invoiceCompanyDTO;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getInvoiceCompanyId() {
        return invoiceCompanyId;
    }

    public void setInvoiceCompanyId(String invoiceCompanyId) {
        this.invoiceCompanyId = invoiceCompanyId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getInvoiceCompanyName() {
        return invoiceCompanyName;
    }

    public void setInvoiceCompanyName(String invoiceCompanyName) {
        this.invoiceCompanyName = invoiceCompanyName;
    }

    public String getDefaultFlag() {
        return defaultFlag;
    }

    public void setDefaultFlag(String defaultFlag) {
        this.defaultFlag = defaultFlag;
    }
}

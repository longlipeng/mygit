package com.allinfinance.univer.seller.invoice.dto;

import java.util.Date;

import com.allinfinance.framework.dto.PageQueryDTO;

public class OrderInvoiceInfoQueryDTO extends PageQueryDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String dataStat;
	private Date recUpdTs;
	private Date recCrtTs;
	private String recUpdUsrId;
	
	private Date startTime;
	private Date stopTime;
	
	/**
	 * 
	 */
	private String invoiceId;

    private String orderId;
    //发票抬头
    private String invoiceTitle;
    //发票金额
    private String invoiceAmount;
    //发票类型
    private String invoiceType;
    //发票项目
    private String invoiceProj;
    
    /**
     * 客户预计取票日期
     */
    private String customerExpectedDate;
    //发票日期
    private String invoiceDate;
    //发票代码
    private String invoiceCode;
    //订送方式
    private String sendoutWay;

    /**
     * 客户地址
     */
    private String invoiceCustomerAddress;

    private String userId;

    private String comment;

    private String invoiceState;

    private String createUser;

    private String createTime;

    private String modifyUser;
    
    private String saleManName;

    private String modifyTime;

    private String dataState;
    //订单总金额
    private String totalPrice;
    //客户名称
    private String customerName;
    //订单发起者
    private String firstEntityName;
    //开始时间
    private String beginDate;
    //结束时间
    private String endDate;
    
    /**
     * 开票主体
     */
    private String billingSubject;
    
    /**
     * 开票日期
     */
    private String billingDate;
    
    /**
     * 发票号码
     */
    private String invoiceNumber;
    
    /**
     * 销售日期
     */
    private String saleDate;
    
	private OrderInvoiceInfoDTO orderInvoiceInfoDTO = new OrderInvoiceInfoDTO();
	
	

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getSaleManName() {
		return saleManName;
	}

	public void setSaleManName(String saleManName) {
		this.saleManName = saleManName;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	public String getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(String invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getInvoiceProj() {
		return invoiceProj;
	}

	public void setInvoiceProj(String invoiceProj) {
		this.invoiceProj = invoiceProj;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public String getSendoutWay() {
		return sendoutWay;
	}

	public void setSendoutWay(String sendoutWay) {
		this.sendoutWay = sendoutWay;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getInvoiceState() {
		return invoiceState;
	}

	public void setInvoiceState(String invoiceState) {
		this.invoiceState = invoiceState;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

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

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getFirstEntityName() {
		return firstEntityName;
	}

	public void setFirstEntityName(String firstEntityName) {
		this.firstEntityName = firstEntityName;
	}

	public String getDataStat() {
		return dataStat;
	}

	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}

	public Date getRecUpdTs() {
		return recUpdTs;
	}

	public void setRecUpdTs(Date recUpdTs) {
		this.recUpdTs = recUpdTs;
	}

	public Date getRecCrtTs() {
		return recCrtTs;
	}

	public void setRecCrtTs(Date recCrtTs) {
		this.recCrtTs = recCrtTs;
	}

	public String getRecUpdUsrId() {
		return recUpdUsrId;
	}

	public void setRecUpdUsrId(String recUpdUsrId) {
		this.recUpdUsrId = recUpdUsrId;
	}
	
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getStopTime() {
		return stopTime;
	}

	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
	}

	public OrderInvoiceInfoDTO getOrderInvoiceInfoDTO() {
		return orderInvoiceInfoDTO;
	}

	public void setOrderInvoiceInfoDTO(OrderInvoiceInfoDTO orderInvoiceInfoDTO) {
		this.orderInvoiceInfoDTO = orderInvoiceInfoDTO;
	}

    public String getCustomerExpectedDate() {
        return customerExpectedDate;
    }

    public void setCustomerExpectedDate(String customerExpectedDate) {
        this.customerExpectedDate = customerExpectedDate;
    }

    public String getInvoiceCustomerAddress() {
        return invoiceCustomerAddress;
    }

    public void setInvoiceCustomerAddress(String invoiceCustomerAddress) {
        this.invoiceCustomerAddress = invoiceCustomerAddress;
    }

    public String getBillingSubject() {
        return billingSubject;
    }

    public void setBillingSubject(String billingSubject) {
        this.billingSubject = billingSubject;
    }

    public String getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(String billingDate) {
        this.billingDate = billingDate;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }
    
	
	
}
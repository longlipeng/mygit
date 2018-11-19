package com.allinfinance.univer.seller.order.dto;

import java.util.ArrayList;
import java.util.List;

import com.allinfinance.framework.dto.BaseDTO;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.service.uploadFile.dto.UploadCardFileDTO;
import com.allinfinance.univer.entity.dto.BankDTO;
import com.allinfinance.univer.entity.dto.ContactDTO;
import com.allinfinance.univer.entity.dto.DeliveryPointDTO;
import com.allinfinance.univer.entity.dto.InvoiceAddressDTO;
import com.allinfinance.univer.entity.dto.InvoiceCompanyDTO;

/***
 * 
 * @author dawn 订单DTO
 */
public class SellOrderDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2235685692201572706L;

	private String orderId;

	private String orderType;

	private String orderDate;

	private String firstEntityId;

	private String firstEntityName;

	private String processEntityId;

	private String processEntityName;

	private String orderState;

	private String productId;

	private String validityPeriod;

	private String cardLayoutId;

	private String cardLayoutName;
	
	private String changeCardFee;

	private String saleMan;

	private String saleManName;
	
	private String cardIssueFee;

	private String annualFee;

	private String deliveryMeans;

	private String deliveryFee;

	private String deliveryPoint;

	private String orderContact;

	private String orderPriority;
	private String faceValueId;
	private String invoiceCompanyName;

	private String invoiceAddresses;

	private String invoiceItemId;

	private String invoiceItem;

	private String invoiceDate;

	private String paymentTerm;

	private String paymentDelay;

	private String paymentState;

	private String paymentDate;

	private String discountFee;

	private String additionalFee;

	private String totalPrice;

	private String faceValueType;

	private String faceValue;
	
	private String customerType;

	private String serviceId;

	private String serviceName;
	
	private String serviceFee;

	private String forecastCreditDate;

	private String realCreditDate;

	private String memo;

	private String makeCardReason;

	private String isInnerDeduct;

	private String orderSource;

	private String refOrder;

	private String externalId;

	private String cardQuantity;

	private String realCardQuantity;

	private String cardCompanyId;

	private String cardType;

	private String isImportCardFile;

	private String isCreateCardFile;

	private String isCreatePinFile;

	private String orderBarcode;

	private String createUser;

	private String createUserName;
	
	private String createTime;

	
	
	private String modifyUser;

	private String modifyTime;

	private String dataState;

	private String loyaltyContractId;
	
	private String packageId;

	private String packageName;
	
	private String packageFee;

	private String productName;
	
	private String onymousStat;
	
	private String initActStat;
	
	private String cardNo;
	
	private List<InvoiceAddressDTO> invoiceAddressList = new ArrayList<InvoiceAddressDTO>();

	private List<InvoiceCompanyDTO> invoiceCompanyList = new ArrayList<InvoiceCompanyDTO>();

	private List<DeliveryPointDTO> deliveryPointList = new ArrayList<DeliveryPointDTO>();

	private PageDataDTO orderCardList = new PageDataDTO();

	private PageDataDTO orderList = new PageDataDTO();
	
	private PageDataDTO origCardList = new PageDataDTO();
	
	private String dynamicFirstEntityTable;
	
	
	private String dynamicProcessEntityTable;
	
	private String dynamicFirstEntitycolumn;
	
	private String operationMemo;
	
	private String dynamicprocessEntitycolumn;
	
	private String payChannel;
	
	private List<BankDTO> lstBankDTO;
	
	private String fromBankId;
	
	private String intoBankId;
	
	private BankDTO bankDTO;
	
	private String payDetails;
	
	private String origCardTotalAmt;
	
	private String newCardTotalAmt;
	
	private String origCardQuantity;
	
	private String contactId;
	
	private ContactDTO contactDTO;
	
	private String perFlag;
	
	private String origProdName;
	
	private List<String> cardNoSections;
	
	private List<String> origCardNoSections;
	
	private String cusContactWay;
	
	private String buyStatement;
	
	private String creditTotalAmount;
	
	private String paymentAmount;
	private String isCheckCard;
	
	private PageDataDTO paymentList= new PageDataDTO();

	private String orderIds[];

	private String paymentId;
	
     //订单编辑之前的支付方式
	private String oldPaymentTerm;
	
	private String batchNo;
	private String batchType;
	private List<String> orderStates;
	
	private String maxRansomFee;
	private String cvn2;//卡片cvv2(用于一个订单中只有一张卡的激活)
	private UploadCardFileDTO uploadCardFileDTO;
	
	
	public UploadCardFileDTO getUploadCardFileDTO() {
		return uploadCardFileDTO;
	}

	public void setUploadCardFileDTO(UploadCardFileDTO uploadCardFileDTO) {
		this.uploadCardFileDTO = uploadCardFileDTO;
	}

	public String getCvn2() {
		return cvn2;
	}

	public void setCvn2(String cvn2) {
		this.cvn2 = cvn2;
	}

	public String getOldPaymentTerm() {
		return oldPaymentTerm;
	}

	public void setOldPaymentTerm(String oldPaymentTerm) {
		this.oldPaymentTerm = oldPaymentTerm;
	}

	public String getIsCheckCard() {
		return isCheckCard;
	}

	public void setIsCheckCard(String isCheckCard) {
		this.isCheckCard = isCheckCard;
	}
	
	/**
	 * 设置调用方法为提交或退回
	 */
	private String operation;
	
	public String getOperation() {
		return operation;
	}
	
	public void setOperation(String operation) {
		this.operation = operation;
	}

	private List<SellOrderPaymentDTO> orderPaymentDTOList;
	
	private List<CardNoSectionDTO> cardNoSectionDTOs=new ArrayList<CardNoSectionDTO>();
	
	private String printDate;
	
	private List<SellOrderCardListDTO> orderCardListDTOList;

	private List<CardNoSectionDTO> cardNoSectionDTOList;
	
	private SellOrderFlowDTO orderFlowDTO;

	private String stockCertFlag;
	
	public String getStockCertFlag() {
		return stockCertFlag;
	}

	public void setStockCertFlag(String stockCertFlag) {
		this.stockCertFlag = stockCertFlag;
	}
	
	public SellOrderFlowDTO getOrderFlowDTO() {
		return orderFlowDTO;
	}

	public void setOrderFlowDTO(SellOrderFlowDTO orderFlowDTO) {
		this.orderFlowDTO = orderFlowDTO;
	}

	public List<SellOrderCardListDTO> getOrderCardListDTOList() {
		return orderCardListDTOList;
	}

	public void setOrderCardListDTOList(
			List<SellOrderCardListDTO> orderCardListDTOList) {
		this.orderCardListDTOList = orderCardListDTOList;
	}

	public List<CardNoSectionDTO> getCardNoSectionDTOList() {
		return cardNoSectionDTOList;
	}

	public void setCardNoSectionDTOList(List<CardNoSectionDTO> cardNoSectionDTOList) {
		this.cardNoSectionDTOList = cardNoSectionDTOList;
	}

	public String getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public List<SellOrderPaymentDTO> getOrderPaymentDTOList() {
		return orderPaymentDTOList;
	}

	public void setOrderPaymentDTOList(List<SellOrderPaymentDTO> orderPaymentDTOList) {
		this.orderPaymentDTOList = orderPaymentDTOList;
	}

	public String getCreditTotalAmount() {
		return creditTotalAmount;
	}

	public void setCreditTotalAmount(String creditTotalAmount) {
		this.creditTotalAmount = creditTotalAmount;
	}

	public String getBuyStatement() {
		return buyStatement;
	}

	public void setBuyStatement(String buyStatement) {
		this.buyStatement = buyStatement;
	}

	public String getCusContactWay() {
		return cusContactWay;
	}

	public void setCusContactWay(String cusContactWay) {
		this.cusContactWay = cusContactWay;
	}

	public List<String> getCardNoSections() {
		return cardNoSections;
	}

	public void setCardNoSections(List<String> cardNoSections) {
		this.cardNoSections = cardNoSections;
	}

	public List<String> getOrigCardNoSections() {
		return origCardNoSections;
	}

	public void setOrigCardNoSections(List<String> origCardNoSections) {
		this.origCardNoSections = origCardNoSections;
	}

	public String getOrigProdName() {
		return origProdName;
	}

	public void setOrigProdName(String origProdName) {
		this.origProdName = origProdName;
	}

	public String getPerFlag() {
		return perFlag;
	}

	public void setPerFlag(String perFlag) {
		this.perFlag = perFlag;
	}

	public ContactDTO getContactDTO() {
		return contactDTO;
	}

	public void setContactDTO(ContactDTO contactDTO) {
		this.contactDTO = contactDTO;
	}

	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	public PageDataDTO getOrigCardList() {
		return origCardList;
	}

	public void setOrigCardList(PageDataDTO origCardList) {
		this.origCardList = origCardList;
	}

	public String getOrigCardTotalAmt() {
		return origCardTotalAmt;
	}

	public void setOrigCardTotalAmt(String origCardTotalAmt) {
		this.origCardTotalAmt = origCardTotalAmt;
	}

	public String getNewCardTotalAmt() {
		return newCardTotalAmt;
	}

	public void setNewCardTotalAmt(String newCardTotalAmt) {
		this.newCardTotalAmt = newCardTotalAmt;
	}

	public String getOrigCardQuantity() {
		return origCardQuantity;
	}

	public void setOrigCardQuantity(String origCardQuantity) {
		this.origCardQuantity = origCardQuantity;
	}

	public String getPayDetails() {
		return payDetails;
	}

	public void setPayDetails(String payDetails) {
		this.payDetails = payDetails;
	}

	public BankDTO getBankDTO() {
		return bankDTO;
	}

	public void setBankDTO(BankDTO bankDTO) {
		this.bankDTO = bankDTO;
	}

	public String getFromBankId() {
		return fromBankId;
	}

	public void setFromBankId(String fromBankId) {
		this.fromBankId = fromBankId;
	}

	public String getIntoBankId() {
		return intoBankId;
	}

	public void setIntoBankId(String intoBankId) {
		this.intoBankId = intoBankId;
	}

	public List<BankDTO> getLstBankDTO() {
		return lstBankDTO;
	}

	public void setLstBankDTO(List<BankDTO> lstBankDTO) {
		this.lstBankDTO = lstBankDTO;
	}

	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getFirstEntityId() {
		return firstEntityId;
	}

	public void setFirstEntityId(String firstEntityId) {
		this.firstEntityId = firstEntityId;
	}

	public String getProcessEntityId() {
		return processEntityId;
	}

	public void setProcessEntityId(String processEntityId) {
		this.processEntityId = processEntityId;
	}

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	public String getInitActStat() {
		return initActStat;
	}

	public void setInitActStat(String initActStat) {
		this.initActStat = initActStat;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getValidityPeriod() {
		return validityPeriod;
	}

	public void setValidityPeriod(String validityPeriod) {
		this.validityPeriod = validityPeriod;
	}

	public String getCardLayoutId() {
		return cardLayoutId;
	}

	public void setCardLayoutId(String cardLayoutId) {
		this.cardLayoutId = cardLayoutId;
	}

	public String getChangeCardFee() {
		return changeCardFee;
	}

	public void setChangeCardFee(String changeCardFee) {
		this.changeCardFee = changeCardFee;
	}

	public String getSaleMan() {
		return saleMan;
	}

	public void setSaleMan(String saleMan) {
		this.saleMan = saleMan;
	}

	public String getCardIssueFee() {
		return cardIssueFee;
	}

	public void setCardIssueFee(String cardIssueFee) {
		this.cardIssueFee = cardIssueFee;
	}

	public String getAnnualFee() {
		return annualFee;
	}

	public void setAnnualFee(String annualFee) {
		this.annualFee = annualFee;
	}

	public String getDeliveryMeans() {
		return deliveryMeans;
	}

	public void setDeliveryMeans(String deliveryMeans) {
		this.deliveryMeans = deliveryMeans;
	}

	public String getDeliveryFee() {
		return deliveryFee;
	}

	public void setDeliveryFee(String deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	public String getDeliveryPoint() {
		return deliveryPoint;
	}

	public void setDeliveryPoint(String deliveryPoint) {
		this.deliveryPoint = deliveryPoint;
	}

	public String getOrderContact() {
		return orderContact;
	}

	public void setOrderContact(String orderContact) {
		this.orderContact = orderContact;
	}

	public String getOrderPriority() {
		return orderPriority;
	}

	public void setOrderPriority(String orderPriority) {
		this.orderPriority = orderPriority;
	}

	public String getInvoiceCompanyName() {
		return invoiceCompanyName;
	}

	public void setInvoiceCompanyName(String invoiceCompanyName) {
		this.invoiceCompanyName = invoiceCompanyName;
	}

	public String getInvoiceAddresses() {
		return invoiceAddresses;
	}

	public void setInvoiceAddresses(String invoiceAddresses) {
		this.invoiceAddresses = invoiceAddresses;
	}

	public String getInvoiceItemId() {
		return invoiceItemId;
	}

	public void setInvoiceItemId(String invoiceItemId) {
		this.invoiceItemId = invoiceItemId;
	}

	public String getInvoiceItem() {
		return invoiceItem;
	}

	public void setInvoiceItem(String invoiceItem) {
		this.invoiceItem = invoiceItem;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getPaymentTerm() {
		return paymentTerm;
	}

	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}

	public String getPaymentDelay() {
		return paymentDelay;
	}

	public void setPaymentDelay(String paymentDelay) {
		this.paymentDelay = paymentDelay;
	}

	public String getPaymentState() {
		return paymentState;
	}

	public void setPaymentState(String paymentState) {
		this.paymentState = paymentState;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getDiscountFee() {
		return discountFee;
	}

	public void setDiscountFee(String discountFee) {
		this.discountFee = discountFee;
	}

	public String getAdditionalFee() {
		return additionalFee;
	}

	public void setAdditionalFee(String additionalFee) {
		this.additionalFee = additionalFee;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getFaceValueType() {
		return faceValueType;
	}

	public void setFaceValueType(String faceValueType) {
		this.faceValueType = faceValueType;
	}

	public String getFaceValue() {
		return faceValue;
	}

	public void setFaceValue(String faceValue) {
		this.faceValue = faceValue;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(String serviceFee) {
		this.serviceFee = serviceFee;
	}

	public String getForecastCreditDate() {
		return forecastCreditDate;
	}

	public void setForecastCreditDate(String forecastCreditDate) {
		this.forecastCreditDate = forecastCreditDate;
	}

	public String getRealCreditDate() {
		return realCreditDate;
	}

	public void setRealCreditDate(String realCreditDate) {
		this.realCreditDate = realCreditDate;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getMakeCardReason() {
		return makeCardReason;
	}

	public void setMakeCardReason(String makeCardReason) {
		this.makeCardReason = makeCardReason;
	}

	public String getIsInnerDeduct() {
		return isInnerDeduct;
	}

	public void setIsInnerDeduct(String isInnerDeduct) {
		this.isInnerDeduct = isInnerDeduct;
	}

	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

	public String getRefOrder() {
		return refOrder;
	}

	public void setRefOrder(String refOrder) {
		this.refOrder = refOrder;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getCardQuantity() {
		return cardQuantity;
	}

	public void setCardQuantity(String cardQuantity) {
		this.cardQuantity = cardQuantity;
	}

	public String getRealCardQuantity() {
		return realCardQuantity;
	}

	public void setRealCardQuantity(String realCardQuantity) {
		this.realCardQuantity = realCardQuantity;
	}

	public String getCardCompanyId() {
		return cardCompanyId;
	}

	public void setCardCompanyId(String cardCompanyId) {
		this.cardCompanyId = cardCompanyId;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getIsImportCardFile() {
		return isImportCardFile;
	}

	public void setIsImportCardFile(String isImportCardFile) {
		this.isImportCardFile = isImportCardFile;
	}

	public String getIsCreateCardFile() {
		return isCreateCardFile;
	}

	public void setIsCreateCardFile(String isCreateCardFile) {
		this.isCreateCardFile = isCreateCardFile;
	}

	public String getIsCreatePinFile() {
		return isCreatePinFile;
	}

	public void setIsCreatePinFile(String isCreatePinFile) {
		this.isCreatePinFile = isCreatePinFile;
	}

	public String getOrderBarcode() {
		return orderBarcode;
	}

	public void setOrderBarcode(String orderBarcode) {
		this.orderBarcode = orderBarcode;
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

	public List<InvoiceAddressDTO> getInvoiceAddressList() {
		return invoiceAddressList;
	}

	public void setInvoiceAddressList(List<InvoiceAddressDTO> invoiceAddressList) {
		this.invoiceAddressList = invoiceAddressList;
	}

	public List<InvoiceCompanyDTO> getInvoiceCompanyList() {
		return invoiceCompanyList;
	}

	public void setInvoiceCompanyList(List<InvoiceCompanyDTO> invoiceCompanyList) {
		this.invoiceCompanyList = invoiceCompanyList;
	}

	public List<DeliveryPointDTO> getDeliveryPointList() {
		return deliveryPointList;
	}

	public void setDeliveryPointList(List<DeliveryPointDTO> deliveryPointList) {
		this.deliveryPointList = deliveryPointList;
	}

	
	public String getProcessEntityName() {
		return processEntityName;
	}

	public void setProcessEntityName(String processEntityName) {
		this.processEntityName = processEntityName;
	}

	
	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getPackageFee() {
		return packageFee;
	}

	public void setPackageFee(String packageFee) {
		this.packageFee = packageFee;
	}

	public PageDataDTO getOrderCardList() {
		return orderCardList;
	}

	public void setOrderCardList(PageDataDTO orderCardList) {
		this.orderCardList = orderCardList;
	}

	public PageDataDTO getOrderList() {
		return orderList;
	}

	public void setOrderList(PageDataDTO orderList) {
		this.orderList = orderList;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String[] getOrderIds() {
		return orderIds;
	}

	public void setOrderIds(String[] orderIds) {
		this.orderIds = orderIds;
	}

	public String getCardLayoutName() {
		return cardLayoutName;
	}

	public void setCardLayoutName(String cardLayoutName) {
		this.cardLayoutName = cardLayoutName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getFirstEntityName() {
		return firstEntityName;
	}

	public void setFirstEntityName(String firstEntityName) {
		this.firstEntityName = firstEntityName;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getDynamicFirstEntityTable() {
		return dynamicFirstEntityTable;
	}

	public void setDynamicFirstEntityTable(String dynamicFirstEntityTable) {
		this.dynamicFirstEntityTable = dynamicFirstEntityTable;
	}

	public String getDynamicProcessEntityTable() {
		return dynamicProcessEntityTable;
	}

	public void setDynamicProcessEntityTable(String dynamicProcessEntityTable) {
		this.dynamicProcessEntityTable = dynamicProcessEntityTable;
	}

	public String getDynamicFirstEntitycolumn() {
		return dynamicFirstEntitycolumn;
	}

	public void setDynamicFirstEntitycolumn(String dynamicFirstEntitycolumn) {
		this.dynamicFirstEntitycolumn = dynamicFirstEntitycolumn;
	}

	public String getDynamicprocessEntitycolumn() {
		return dynamicprocessEntitycolumn;
	}

	public void setDynamicprocessEntitycolumn(String dynamicprocessEntitycolumn) {
		this.dynamicprocessEntitycolumn = dynamicprocessEntitycolumn;
	}

	public String getSaleManName() {
		return saleManName;
	}

	public void setSaleManName(String saleManName) {
		this.saleManName = saleManName;
	}

	public String getOperationMemo() {
		return operationMemo;
	}

	public void setOperationMemo(String operationMemo) {
		this.operationMemo = operationMemo;
	}
	public String getLoyaltyContractId() {
		return loyaltyContractId;
	}

	public void setLoyaltyContractId(String loyaltyContractId) {
		this.loyaltyContractId = loyaltyContractId;
	}
	public String getOnymousStat() {
		return onymousStat;
	}

	public void setOnymousStat(String onymousStat) {
		this.onymousStat = onymousStat;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public List<CardNoSectionDTO> getCardNoSectionDTOs() {
		return cardNoSectionDTOs;
	}

	public void setCardNoSectionDTOs(List<CardNoSectionDTO> cardNoSectionDTOs) {
		this.cardNoSectionDTOs = cardNoSectionDTOs;
	}

	public String getPrintDate() {
		return printDate;
	}

	public void setPrintDate(String printDate) {
		this.printDate = printDate;
	}

	public PageDataDTO getPaymentList() {
		return paymentList;
	}

	public void setPaymentList(PageDataDTO paymentList) {
		this.paymentList = paymentList;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getFaceValueId() {
		return faceValueId;
	}

	public void setFaceValueId(String faceValueId) {
		this.faceValueId = faceValueId;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getBatchType() {
		return batchType;
	}

	public void setBatchType(String batchType) {
		this.batchType = batchType;
	}

	public List<String> getOrderStates() {
		return orderStates;
	}

	public void setOrderStates(List<String> orderStates) {
		this.orderStates = orderStates;
	}

	public String getMaxRansomFee() {
		return maxRansomFee;
	}

	public void setMaxRansomFee(String maxRansomFee) {
		this.maxRansomFee = maxRansomFee;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	
}

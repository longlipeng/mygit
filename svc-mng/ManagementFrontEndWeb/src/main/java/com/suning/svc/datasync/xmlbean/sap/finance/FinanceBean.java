/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: FinanceBean.java
 * Author:   12073942
 * Date:     2013-7-1 下午8:02:51
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.datasync.xmlbean.sap.finance;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * 财务记账DTO
 * 
 * @author yangtao
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@XStreamAlias("IT_IN")
public class FinanceBean {

    /** Field1 交易类型 transType TRANS_TYPE */
    @XStreamAlias("TRANS_TYPE")
    private String transType;

    /** Field2 交易流水号 transSeq TRANS_SEQ */
    @XStreamAlias("TRANS_SEQ")
    private String transSeq;

    /** Filed3 交易日期 transDt TRANS_DT */
    @XStreamAlias("TRANS_DT")
    private String transDt;

    /** Filed4 交易公司 transCompany TRANS_COMPANY */
    @XStreamAlias("TRANS_COMPANY")
    private String transCompany;

    /** Filed5 支付方式 payment PAYMENT */
    @XStreamAlias("PAYMENT")
    private String payment;

    /** Filed6 金额 amount AMOUNT */
    @XStreamAlias("AMOUNT")
    private String amount;

    /** Filed7 手续费金额 rateAmount RATE_AMOUNT */
    @XStreamAlias("RATE_AMOUNT")
    private String rateAmount;

    /** Filed8 原卡金额 reAmount RE_AMOUNT */
    @XStreamAlias("RE_AMOUNT")
    private String reAmount;

    /** Filed9 商户编码 vendor VENDOR */
    @XStreamAlias("VENDOR")
    private String vendor;

    /** Filed10 销售机构 saleCompany SALE_COMPANY */
    @XStreamAlias("SALE_COMPANY")
    private String saleCompany;

    /** Filed11 公文号1 docNo1 DOC_NO1 */
    @XStreamAlias("DOC_NO1")
    private String docNo1;

    /** Filed12 公文号2 docNo2 DOC_NO2 */
    @XStreamAlias("DOC_NO2")
    private String docNo2;

    /** Filed13 供应商订单号 supplyOrderNo SUPPLY_ORDER_NO */
    @XStreamAlias("SUPPLY_ORDER_NO")
    private String supplyOrderNo;

    /** Filed14 成本中心 costcenterCode COSTCENTER_CODE */
    @XStreamAlias("COSTCENTER_CODE")
    private String costcenterCode;

    /** Filed15 费用科目 expenseAcct EXPENSE_ACCT */
    @XStreamAlias("EXPENSE_ACCT")
    private String expenseAcct;

    /** Filed16 银行存款科目 bankSubject BANK_SUBJECT */
    @XStreamAlias("BANK_SUBJECT")
    private String bankSubject;

    /** Filed17 商品组 cmmdtyGrp CMMDTY_GRP */
    @XStreamAlias("CMMDTY_GRP")
    private String cmmdtyGrp;

    /** Filed18 产品层次 cmmdtyHeirarchy CMMDTY_HEIRARCHY */
    @XStreamAlias("CMMDTY_HEIRARCHY")
    private String cmmdtyHeirarchy;

    /** Filed19 商品编码 cmmdtyCode CMMDTY_CODE */
    @XStreamAlias("CMMDTY_CODE")
    private String cmmdtyCode;

    /** Filed20 商品描述 cmmdtyDesc CMMDTY_DESC */
    @XStreamAlias("CMMDTY_DESC")
    private String cmmdtyDesc;

    /** Filed21 门店代码 store STORE */
    @XStreamAlias("STORE")
    private String store;

    /** Filed22 发票号码 invoiceNo INVOICE_NO */
    @XStreamAlias("INVOICE_NO")
    private String invoiceNo;

    /** Filed23 支付单号 pymntNo PYMNT_NO */
    @XStreamAlias("PYMNT_NO")
    private String pymntNo;

    /** Filed24 业务类型 serviceType SERVICE_TYPE */
    @XStreamAlias("SERVICE_TYPE")
    private String serviceType;

    /** Filed25 业务子类 serviceSubtype SERVICE_SUBTYPE */
    @XStreamAlias("SERVICE_SUBTYPE")
    private String serviceSubtype;

    /** Filed26 对公对私对内标志 pubPvtIntFlag PUB_PVT_INT_FLAG */
    @XStreamAlias("PUB_PVT_INT_FLAG")
    private String pubPvtIntFlag;

    /** Filed27 公文号 docNo DOC_NO */
    @XStreamAlias("DOC_NO")
    private String docNo;

    /** Filed28 货币码 currencyCode CURRENCY_CODE */
    @XStreamAlias("CURRENCY_CODE")
    private String currencyCode;

    /** Filed29 付款方式 paymtType PAYMT_TYPE */
    @XStreamAlias("PAYMT_TYPE")
    private String paymtType;

    /** Filed30 是否打款标志 paymtFlag PAYMT_FLAG */
    @XStreamAlias("PAYMT_FLAG")
    private String paymtFlag;

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getTransSeq() {
        return transSeq;
    }

    public void setTransSeq(String transSeq) {
        this.transSeq = transSeq;
    }

    public String getTransDt() {
        return transDt;
    }

    public void setTransDt(String transDt) {
        this.transDt = transDt;
    }

    public String getTransCompany() {
        return transCompany;
    }

    public void setTransCompany(String transCompany) {
        this.transCompany = transCompany;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRateAmount() {
        return rateAmount;
    }

    public void setRateAmount(String rateAmount) {
        this.rateAmount = rateAmount;
    }

    public String getReAmount() {
        return reAmount;
    }

    public void setReAmount(String reAmount) {
        this.reAmount = reAmount;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getSaleCompany() {
        return saleCompany;
    }

    public void setSaleCompany(String saleCompany) {
        this.saleCompany = saleCompany;
    }

    public String getDocNo1() {
        return docNo1;
    }

    public void setDocNo1(String docNo1) {
        this.docNo1 = docNo1;
    }

    public String getDocNo2() {
        return docNo2;
    }

    public void setDocNo2(String docNo2) {
        this.docNo2 = docNo2;
    }

    public String getSupplyOrderNo() {
        return supplyOrderNo;
    }

    public void setSupplyOrderNo(String supplyOrderNo) {
        this.supplyOrderNo = supplyOrderNo;
    }

    public String getCostcenterCode() {
        return costcenterCode;
    }

    public void setCostcenterCode(String costcenterCode) {
        this.costcenterCode = costcenterCode;
    }

    public String getExpenseAcct() {
        return expenseAcct;
    }

    public void setExpenseAcct(String expenseAcct) {
        this.expenseAcct = expenseAcct;
    }

    public String getBankSubject() {
        return bankSubject;
    }

    public void setBankSubject(String bankSubject) {
        this.bankSubject = bankSubject;
    }

    public String getCmmdtyGrp() {
        return cmmdtyGrp;
    }

    public void setCmmdtyGrp(String cmmdtyGrp) {
        this.cmmdtyGrp = cmmdtyGrp;
    }

    public String getCmmdtyHeirarchy() {
        return cmmdtyHeirarchy;
    }

    public void setCmmdtyHeirarchy(String cmmdtyHeirarchy) {
        this.cmmdtyHeirarchy = cmmdtyHeirarchy;
    }

    public String getCmmdtyCode() {
        return cmmdtyCode;
    }

    public void setCmmdtyCode(String cmmdtyCode) {
        this.cmmdtyCode = cmmdtyCode;
    }

    public String getCmmdtyDesc() {
        return cmmdtyDesc;
    }

    public void setCmmdtyDesc(String cmmdtyDesc) {
        this.cmmdtyDesc = cmmdtyDesc;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getPymntNo() {
        return pymntNo;
    }

    public void setPymntNo(String pymntNo) {
        this.pymntNo = pymntNo;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceSubtype() {
        return serviceSubtype;
    }

    public void setServiceSubtype(String serviceSubtype) {
        this.serviceSubtype = serviceSubtype;
    }

    public String getPubPvtIntFlag() {
        return pubPvtIntFlag;
    }

    public void setPubPvtIntFlag(String pubPvtIntFlag) {
        this.pubPvtIntFlag = pubPvtIntFlag;
    }

    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getPaymtType() {
        return paymtType;
    }

    public void setPaymtType(String paymtType) {
        this.paymtType = paymtType;
    }

    public String getPaymtFlag() {
        return paymtFlag;
    }

    public void setPaymtFlag(String paymtFlag) {
        this.paymtFlag = paymtFlag;
    }

}

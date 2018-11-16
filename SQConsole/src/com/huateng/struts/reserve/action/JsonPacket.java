package com.huateng.struts.reserve.action;

public class JsonPacket {

	private String version;
	private String encoding;
	private String certId;
	private String signature;
	private String signMethod;
	private String txnType;
	private String backUrl;
	private String txnNo;
	private String acqInsCode;
	private String txnDate;
	private String sndTime;
	private String insSeq;
	private String payeeAcctNo;
	private String payeeAcctName;
	private String acctNo;
	private String acctName;
	private String currencyCode;
	private String txnAmt;
	private String remark;
	private String reqReserved;
	private String reserved;
	private String acctBal;//银联虚拟记账余额
	private String avibBal;
	private String signPubKeyCert;
	private String payerAcctBal;//付款方虚拟记账余额
	private String bankNo;
	private String proAcctBal;
	private String proAvlbBal;
	private String acsAcctBal;
	private String acsAcctName;
	private String avlbQuotaAmt;
	
	private String respCode;
	private String respMsg;
	
	
	
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public String getProAcctBal() {
		return proAcctBal;
	}
	public void setProAcctBal(String proAcctBal) {
		this.proAcctBal = proAcctBal;
	}
	public String getProAvlbBal() {
		return proAvlbBal;
	}
	public void setProAvlbBal(String proAvlbBal) {
		this.proAvlbBal = proAvlbBal;
	}
	public String getAcsAcctBal() {
		return acsAcctBal;
	}
	public void setAcsAcctBal(String acsAcctBal) {
		this.acsAcctBal = acsAcctBal;
	}
	public String getAcsAcctName() {
		return acsAcctName;
	}
	public void setAcsAcctName(String acsAcctName) {
		this.acsAcctName = acsAcctName;
	}
	public String getAvlbQuotaAmt() {
		return avlbQuotaAmt;
	}
	public void setAvlbQuotaAmt(String avlbQuotaAmt) {
		this.avlbQuotaAmt = avlbQuotaAmt;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public String getCertId() {
		return certId;
	}
	public void setCertId(String certId) {
		this.certId = certId;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getSignMethod() {
		return signMethod;
	}
	public void setSignMethod(String signMethod) {
		this.signMethod = signMethod;
	}
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	public String getBackUrl() {
		return backUrl;
	}
	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}
	public String getTxnNo() {
		return txnNo;
	}
	public void setTxnNo(String txnNo) {
		this.txnNo = txnNo;
	}
	public String getAcqInsCode() {
		return acqInsCode;
	}
	public void setAcqInsCode(String acqInsCode) {
		this.acqInsCode = acqInsCode;
	}
	public String getTxnDate() {
		return txnDate;
	}
	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}
	public String getSndTime() {
		return sndTime;
	}
	public void setSndTime(String sndTime) {
		this.sndTime = sndTime;
	}
	public String getInsSeq() {
		return insSeq;
	}
	public void setInsSeq(String insSeq) {
		this.insSeq = insSeq;
	}
	public String getPayeeAcctNo() {
		return payeeAcctNo;
	}
	public void setPayeeAcctNo(String payeeAcctNo) {
		this.payeeAcctNo = payeeAcctNo;
	}
	public String getPayeeAcctName() {
		return payeeAcctName;
	}
	public void setPayeeAcctName(String payeeAcctName) {
		this.payeeAcctName = payeeAcctName;
	}
	public String getAcctNo() {
		return acctNo;
	}
	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}
	public String getAcctName() {
		return acctName;
	}
	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getTxnAmt() {
		return txnAmt;
	}
	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getReqReserved() {
		return reqReserved;
	}
	public void setReqReserved(String reqReserved) {
		this.reqReserved = reqReserved;
	}
	public String getReserved() {
		return reserved;
	}
	public void setReserved(String reserved) {
		this.reserved = reserved;
	}
	public String getAcctBal() {
		return acctBal;
	}
	public void setAcctBal(String acctBal) {
		this.acctBal = acctBal;
	}
	public String getAvibBal() {
		return avibBal;
	}
	public void setAvibBal(String avibBal) {
		this.avibBal = avibBal;
	}
	public String getSignPubKeyCert() {
		return signPubKeyCert;
	}
	public void setSignPubKeyCert(String signPubKeyCert) {
		this.signPubKeyCert = signPubKeyCert;
	}
	public String getPayerAcctBal() {
		return payerAcctBal;
	}
	public void setPayerAcctBal(String payerAcctBal) {
		this.payerAcctBal = payerAcctBal;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespMsg() {
		return respMsg;
	}
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	
	
}

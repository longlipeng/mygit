package com.huateng.framework.msg;

public class CardMakeBean {
	/* 交易类型 */
	private String txnType;

	/* 发送日期 */
	private String sndDate;

	/* 发送时刻 */
	private String sndTime;

	/* 公钥信息 */
	private String pubKey;

	// /* 待发数据条数 */
	// private String txnNo;

	/* 卡号 */
	private String cardID;

	/* 失效日期 */
	private String expDate;

	/* 服务代码 */
	private String svrID;

	/* 卡面样式 */
	private String cardSur;

	/* 产品标识 */
	private String productID;

	/* 卡金额 */
	private String cardAmount;

	/* 账户类型 */
	private String acctype;

	/* 卡号打印 */
	private String cardIDPrint;

	/* 有效期打印 */
	private String expDatePrint;

	/* 金额打印 */
	private String cardAmountPrint;

	/* 公司名打印 */
	private String companyPrint;

	/* 持卡人姓名 */
	private String cardholderPrint;

	/* 部门打印 */
	private String departPrint;

	/* 员工编号 */
	private String empID;

	/* 应答码 */
	private String rspCode;

	/* 加密方式 */
	private String cipherType;

	/* 解密密钥 */
	private String cipherKey;

	/* 密文 */
	private String cipherText;

	// 密钥索引
	private String keyIndex;
	// 密钥数据
	private String keyData;
	// 密文数据
	private String cryptData;
	// 解密数据
	private String text;

	public String getKeyIndex() {
		return keyIndex;
	}

	public void setKeyIndex(String keyIndex) {
		this.keyIndex = keyIndex;
	}

	public String getKeyData() {
		return keyData;
	}

	public void setKeyData(String keyData) {
		this.keyData = keyData;
	}

	public String getCryptData() {
		return cryptData;
	}

	public void setCryptData(String cryptData) {
		this.cryptData = cryptData;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the txnType
	 */
	public String getTxnType() {
		return txnType;
	}

	/**
	 * @param txnType
	 *            the txnType to set
	 */
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	/**
	 * @return the sndDate
	 */
	public String getSndDate() {
		return sndDate;
	}

	/**
	 * @param sndDate
	 *            the sndDate to set
	 */
	public void setSndDate(String sndDate) {
		this.sndDate = sndDate;
	}

	/**
	 * @return the sndTime
	 */
	public String getSndTime() {
		return sndTime;
	}

	/**
	 * @param sndTime
	 *            the sndTime to set
	 */
	public void setSndTime(String sndTime) {
		this.sndTime = sndTime;
	}

	/**
	 * @return the pubKey
	 */
	public String getPubKey() {
		return pubKey;
	}

	/**
	 * @param pubKey
	 *            the pubKey to set
	 */
	public void setPubKey(String pubKey) {
		this.pubKey = pubKey;
	}

	/**
	 * @return the cardID
	 */
	public String getCardID() {
		return cardID;
	}

	/**
	 * @param cardID
	 *            the cardID to set
	 */
	public void setCardID(String cardID) {
		this.cardID = cardID;
	}

	/**
	 * @return the expDate
	 */
	public String getExpDate() {
		return expDate;
	}

	/**
	 * @param expDate
	 *            the expDate to set
	 */
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	/**
	 * @return the svrID
	 */
	public String getSvrID() {
		return svrID;
	}

	/**
	 * @param svrID
	 *            the svrID to set
	 */
	public void setSvrID(String svrID) {
		this.svrID = svrID;
	}

	/**
	 * @return the cardSur
	 */
	public String getCardSur() {
		return cardSur;
	}

	/**
	 * @param cardSur
	 *            the cardSur to set
	 */
	public void setCardSur(String cardSur) {
		this.cardSur = cardSur;
	}

	/**
	 * @return the productID
	 */
	public String getProductID() {
		return productID;
	}

	/**
	 * @param productID
	 *            the productID to set
	 */
	public void setProductID(String productID) {
		this.productID = productID;
	}

	/**
	 * @return the cardAmount
	 */
	public String getCardAmount() {
		return cardAmount;
	}

	/**
	 * @param cardAmount
	 *            the cardAmount to set
	 */
	public void setCardAmount(String cardAmount) {
		this.cardAmount = cardAmount;
	}

	/**
	 * @return the acctype
	 */
	public String getAcctype() {
		return acctype;
	}

	/**
	 * @param acctype
	 *            the acctype to set
	 */
	public void setAcctype(String acctype) {
		this.acctype = acctype;
	}

	/**
	 * @return the cardIDPrint
	 */
	public String getCardIDPrint() {
		return cardIDPrint;
	}

	/**
	 * @param cardIDPrint
	 *            the cardIDPrint to set
	 */
	public void setCardIDPrint(String cardIDPrint) {
		this.cardIDPrint = cardIDPrint;
	}

	/**
	 * @return the expDatePrint
	 */
	public String getExpDatePrint() {
		return expDatePrint;
	}

	/**
	 * @param expDatePrint
	 *            the expDatePrint to set
	 */
	public void setExpDatePrint(String expDatePrint) {
		this.expDatePrint = expDatePrint;
	}

	/**
	 * @return the cardAmountPrint
	 */
	public String getCardAmountPrint() {
		return cardAmountPrint;
	}

	/**
	 * @param cardAmountPrint
	 *            the cardAmountPrint to set
	 */
	public void setCardAmountPrint(String cardAmountPrint) {
		this.cardAmountPrint = cardAmountPrint;
	}

	/**
	 * @return the companyPrint
	 */
	public String getCompanyPrint() {
		return companyPrint;
	}

	/**
	 * @param companyPrint
	 *            the companyPrint to set
	 */
	public void setCompanyPrint(String companyPrint) {
		this.companyPrint = companyPrint;
	}

	/**
	 * @return the cardholderPrint
	 */
	public String getCardholderPrint() {
		return cardholderPrint;
	}

	/**
	 * @param cardholderPrint
	 *            the cardholderPrint to set
	 */
	public void setCardholderPrint(String cardholderPrint) {
		this.cardholderPrint = cardholderPrint;
	}

	/**
	 * @return the departPrint
	 */
	public String getDepartPrint() {
		return departPrint;
	}

	/**
	 * @param departPrint
	 *            the departPrint to set
	 */
	public void setDepartPrint(String departPrint) {
		this.departPrint = departPrint;
	}

	/**
	 * @return the empID
	 */
	public String getEmpID() {
		return empID;
	}

	/**
	 * @param empID
	 *            the empID to set
	 */
	public void setEmpID(String empID) {
		this.empID = empID;
	}

	/**
	 * @return the rspCode
	 */
	public String getRspCode() {
		return rspCode;
	}

	/**
	 * @param rspCode
	 *            the rspCode to set
	 */
	public void setRspCode(String rspCode) {
		this.rspCode = rspCode;
	}

	/**
	 * @return the cipherType
	 */
	public String getCipherType() {
		return cipherType;
	}

	/**
	 * @param cipherType
	 *            the cipherType to set
	 */
	public void setCipherType(String cipherType) {
		this.cipherType = cipherType;
	}

	/**
	 * @return the cipherKey
	 */
	public String getCipherKey() {
		return cipherKey;
	}

	/**
	 * @param cipherKey
	 *            the cipherKey to set
	 */
	public void setCipherKey(String cipherKey) {
		this.cipherKey = cipherKey;
	}

	/**
	 * @return the cipherText
	 */
	public String getCipherText() {
		return cipherText;
	}

	/**
	 * @param cipherText
	 *            the cipherText to set
	 */
	public void setCipherText(String cipherText) {
		this.cipherText = cipherText;
	}

	public String toString() {
		String sep = "\r\n";
		StringBuffer sb = new StringBuffer();
		sb.append(this.getClass().getName()).append(sep);
		sb.append("[").append("txnType").append(" = ").append(txnType).append(
				"]").append(sep);
		sb.append("[").append("sndDate").append(" = ").append(sndDate).append(
				"]").append(sep);
		sb.append("[").append("sndTime").append(" = ").append(sndTime).append(
				"]").append(sep);
		// sb.append("[").append("txnNo").append(" = ").append(txnNo).append(
		// "]").append(sep);
		sb.append("[").append("cardID").append(" = ").append(cardID)
				.append("]").append(sep);
		sb.append("[").append("expDate").append(" = ").append(expDate).append(
				"]").append(sep);
		sb.append("[").append("svrID").append(" = ").append(svrID).append("]")
				.append(sep);
		sb.append("[").append("cardSur").append(" = ").append(cardSur).append(
				"]").append(sep);
		sb.append("[").append("rspCode").append(" = ").append(rspCode).append(
				"]").append(sep);
		sb.append("[").append("cipherType").append(" = ").append(cipherType)
				.append("]").append(sep);
		sb.append("[").append("cipherKey").append(" = ").append(cipherKey)
				.append("]").append(sep);
		sb.append("[").append("cipherText").append(" = ").append(cipherText)
				.append("]").append(sep);
		return sb.toString();
	}
}

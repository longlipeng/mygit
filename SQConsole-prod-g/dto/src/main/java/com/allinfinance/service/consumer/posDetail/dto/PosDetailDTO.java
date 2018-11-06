package com.allinfinance.service.consumer.posDetail.dto;

import com.allinfinance.univer.report.IreportDTO;

/**
 * Classname PosDetailDTO.java
 *
 * Version information
 *
 * Licensed Property to HuaTeng Data Co., Ltd.
 * 
 * (C) Copyright of HuaTeng Data Co., Ltd. 2010
 *     All Rights Reserved.
 *
 * Project Info: HuaTeng Data Internet Acquiring  Project
 * 
 * Modification History:
 * =============================================================================
 *		Author		Date		Description
 *   ------------ ---------- ---------------------------------------------------
 *		administrator		2012-10-25
 * =============================================================================
 */



/**
 * @author administrator
 * 
 */
public class PosDetailDTO extends IreportDTO {
	
	private static final long serialVersionUID = 1L;

	/**商户号*/
	private String mchntId;	
	/**商户名称 */
	private String mchntName;
	/**门店号 */
	private String shopId;
	/**门店名称 */
	private String shopName;
	/**POS号 */
	private String termId;
	/**厂商名称 */
	private String brandName;
	
	/**POS当前流水号 */
	private String sysSeqNum;
	/**POS当前批次号 */
	private String termBatchNo;
	/**型号 */
	private String termModel;
	/**门店电话 */
	private String telephone;
	/**门店地址 */
	private String address;
	/**POS连接方式 */
	private String termTrans;
	
	public String getMchntId() {
		return mchntId;
	}
	public void setMchntId(String mchntId) {
		this.mchntId = mchntId;
	}
	public String getMchntName() {
		return mchntName;
	}
	public void setMchntName(String mchntName) {
		this.mchntName = mchntName;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getTermId() {
		return termId;
	}
	public void setTermId(String termId) {
		this.termId = termId;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getSysSeqNum() {
		return sysSeqNum;
	}
	public void setSysSeqNum(String sysSeqNum) {
		this.sysSeqNum = sysSeqNum;
	}
	public String getTermBatchNo() {
		return termBatchNo;
	}
	public void setTermBatchNo(String termBatchNo) {
		this.termBatchNo = termBatchNo;
	}
	public String getTermModel() {
		return termModel;
	}
	public void setTermModel(String termModel) {
		this.termModel = termModel;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTermTrans() {
		return termTrans;
	}
	public void setTermTrans(String termTrans) {
		this.termTrans = termTrans;
	}
	
	

}

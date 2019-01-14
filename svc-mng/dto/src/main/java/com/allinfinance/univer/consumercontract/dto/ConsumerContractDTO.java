package com.allinfinance.univer.consumercontract.dto;

import java.util.Date;
import java.util.List;

import com.allinfinance.framework.dto.BaseDTO;
import com.allinfinance.univer.servicefeerule.dto.CaclDspDTO;

public class ConsumerContractDTO extends BaseDTO implements
		java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1113704481148648521L;
	private String consumerContractId;
	private String ruleNo;
	private String contractBuyer;
	private String contractSeller;
	/**
	 * contractType1为收单机构 2为商户
	 */
	private String contractType;
	private String contractCreateDate;
	private String contractStartDate;
	private String contractEndDate;
	private Date contractStartDateDate;
	private Date contractEndDateDate;
	private String dataState;
	private String merchantId;
	private String merchantName;
	private String merchantCode;
	private String ruleName;
	private String settleDateNext;
	private List<String> transId;
	
	private String handleSettleDate;
	private String settlDtPre;
	private String settlDtNext;
	private String settlDtFirst;
	/*修改周期规则标志*/
	private String ruleEditFlag;
	public List<String> getTransId() {
		return transId;
	}

	public void setTransId(List<String> transId) {
		this.transId = transId;
	}

	private List<AccTypeContractDTO> accTypeContractDTOList;
	private List<CaclDspDTO> CaclDspDTOs;

	public List<CaclDspDTO> getCaclDspDTOs() {
		return CaclDspDTOs;
	}

	public void setCaclDspDTOs(List<CaclDspDTO> caclDspDTOs) {
		CaclDspDTOs = caclDspDTOs;
	}

	private String clearTp;

	public String getClearTp() {
		return clearTp;
	}

	public void setClearTp(String clearTp) {
		this.clearTp = clearTp;
	}

	public String getConsumerContractId() {
		return consumerContractId;
	}

	public void setConsumerContractId(String consumerContractId) {
		this.consumerContractId = consumerContractId;
	}

	public String getRuleNo() {
		return ruleNo;
	}

	public void setRuleNo(String ruleNo) {
		this.ruleNo = ruleNo;
	}

	public String getContractBuyer() {
		return contractBuyer;
	}

	public void setContractBuyer(String contractBuyer) {
		this.contractBuyer = contractBuyer;
	}

	public String getContractSeller() {
		return contractSeller;
	}

	public void setContractSeller(String contractSeller) {
		this.contractSeller = contractSeller;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public String getContractCreateDate() {
		return contractCreateDate;
	}

	public void setContractCreateDate(String contractCreateDate) {
		this.contractCreateDate = contractCreateDate;
	}

	public String getContractStartDate() {
		return contractStartDate;
	}

	public void setContractStartDate(String contractStartDate) {
		this.contractStartDate = contractStartDate;
	}

	public String getContractEndDate() {
		return contractEndDate;
	}

	public void setContractEndDate(String contractEndDate) {
		this.contractEndDate = contractEndDate;
	}

	public String getDataState() {
		return dataState;
	}

	public void setDataState(String dataState) {
		this.dataState = dataState;
	}

	public Date getContractStartDateDate() {
		return contractStartDateDate;
	}

	public void setContractStartDateDate(Date contractStartDateDate) {
		this.contractStartDateDate = contractStartDateDate;
	}

	public Date getContractEndDateDate() {
		return contractEndDateDate;
	}

	public void setContractEndDateDate(Date contractEndDateDate) {
		this.contractEndDateDate = contractEndDateDate;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public List<AccTypeContractDTO> getAccTypeContractDTOList() {
		return accTypeContractDTOList;
	}

	public void setAccTypeContractDTOList(
			List<AccTypeContractDTO> accTypeContractDTOList) {
		this.accTypeContractDTOList = accTypeContractDTOList;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public String getSettleDateNext() {
		return settleDateNext;
	}

	public void setSettleDateNext(String settleDateNext) {
		this.settleDateNext = settleDateNext;
	}

	public String getHandleSettleDate() {
		return handleSettleDate;
	}

	public void setHandleSettleDate(String handleSettleDate) {
		this.handleSettleDate = handleSettleDate;
	}

	public String getSettlDtPre() {
		return settlDtPre;
	}

	public void setSettlDtPre(String settlDtPre) {
		this.settlDtPre = settlDtPre;
	}

	public String getSettlDtNext() {
		return settlDtNext;
	}

	public void setSettlDtNext(String settlDtNext) {
		this.settlDtNext = settlDtNext;
	}
	public String getSettlDtFirst() {
		return settlDtFirst;
	}

	public void setSettlDtFirst(String settlDtFirst) {
		this.settlDtFirst = settlDtFirst;
	}

	public String getRuleEditFlag() {
		return ruleEditFlag;
	}

	public void setRuleEditFlag(String ruleEditFlag) {
		this.ruleEditFlag = ruleEditFlag;
	}
	
}

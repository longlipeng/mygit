package com.allinfinance.univer.seller.order.dto;

import java.util.Map;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.framework.dto.PageQueryDTO;
import com.allinfinance.service.BatchParamInterface;

public class SellOrderOrigCardListDTO extends PageQueryDTO implements
		BatchParamInterface {

	private static final long serialVersionUID = -2516700649408148043L;
	private String origcardListId;
	private String orderId;
	private String cardNo;
	private String cardholderId;
	private String firstName;
	private String amount;
	private String amountStr;
	private String validityPeriod;
	private String validityPeriodStr;
	private String productId;
	private String productName;
	private String cardSate;
	private String createUser;
	private String createTime;
	private String modifyUser;
	private String modifyTime;
	private String dataState;
	private String[] origcardListIds;
	private String callBack;
	
	/**
	 * 订单发起方
	 */
	private String firstEntityId;
	private String firstEntityName;
	private String cvv;
	private Map<String, String> checkedOneCard;
	

	private PageDataDTO orderList;
	
	/**
	 * 操作人
	 */
	private String operator;
	/**
	 * 锁定状态
	 */
	private String lockState;
	/**
     * 注销状态
     */
    private String cancelSate;
	/**
	 * 挂失状态
	 */
	private String lostState;
	/**
	 * 本机构号
	 */
	private String entityId;

	@Override
	public String calcAmt() {
		return getAmount();
	}

	/**
	 * 用来存放业务流水号
	 */
	private String txnSeq;

	@Override
	public String fetchSeq() {

		return this.txnSeq;
	}
	
	

	public String getCallBack() {
		return callBack;
	}



	public void setCallBack(String callBack) {
		this.callBack = callBack;
	}



	@Override
	public void setSeq(String seq) {
		this.txnSeq = seq;
	}

	public String getTxnSeq() {
		return txnSeq;
	}

	public void setTxnSeq(String txnSeq) {
		this.txnSeq = txnSeq;
	}

	public String getFirstEntityId() {
		return firstEntityId;
	}

	public void setFirstEntityId(String firstEntityId) {
		this.firstEntityId = firstEntityId;
	}

	public String getFirstEntityName() {
		return firstEntityName;
	}

	public void setFirstEntityName(String firstEntityName) {
		this.firstEntityName = firstEntityName;
	}

	public PageDataDTO getOrderList() {
		return orderList;
	}

	public void setOrderList(PageDataDTO orderList) {
		this.orderList = orderList;
	}

	public String getOrigcardListId() {
		return origcardListId;
	}

	public void setOrigcardListId(String origcardListId) {
		this.origcardListId = origcardListId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardholderId() {
		return cardholderId;
	}

	public void setCardholderId(String cardholderId) {
		this.cardholderId = cardholderId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getValidityPeriod() {
		return validityPeriod;
	}

	public void setValidityPeriod(String validityPeriod) {
		this.validityPeriod = validityPeriod;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCardSate() {
		return cardSate;
	}

	public void setCardSate(String cardSate) {
		this.cardSate = cardSate;
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

	public String[] getOrigcardListIds() {
		return origcardListIds;
	}

	public void setOrigcardListIds(String[] origcardListIds) {
		this.origcardListIds = origcardListIds;
	}

	public Map<String, String> getCheckedOneCard() {
		return checkedOneCard;
	}

	public void setCheckedOneCard(Map<String, String> checkedOneCard) {
		this.checkedOneCard = checkedOneCard;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

    public String getAmountStr() {
        return amountStr;
    }

    public void setAmountStr(String amountStr) {
        this.amountStr = amountStr;
    }

    public String getValidityPeriodStr() {
        return validityPeriodStr;
    }

    public void setValidityPeriodStr(String validityPeriodStr) {
        this.validityPeriodStr = validityPeriodStr;
    }

    public String getCancelSate() {
        return cancelSate;
    }

    public void setCancelSate(String cancelSate) {
        this.cancelSate = cancelSate;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getLockState() {
        return lockState;
    }

    public void setLockState(String lockState) {
        this.lockState = lockState;
    }

    public String getLostState() {
        return lostState;
    }

    public void setLostState(String lostState) {
        this.lostState = lostState;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }
	
	
}

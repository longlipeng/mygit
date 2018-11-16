package com.huateng.po.risk;

import java.io.Serializable;

public class TblRiskMchtTranLimit implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String mchtNo;//商户号
	private String saState;//状态
	private String oprID;//操作员编号
	private String updateTime;//修改时间
	private String txnNum;//交易代码
	private String bussType;//业务类型
	private String channel;//交易渠道
	private String cardType;//卡类型
	private String limit;//权限
	private String channelOld;//修改前的
	private String bussTypeOld;//修改前的
	private String txnNumOld;//修改前的
	private String cardTypeOld;//修改前的
	private String limitOld;//修改前的
	private String creOprId;
	private String OprId;
	private String creTime;
	public String getOprId() {
		return OprId;
	}
	public void setOprId(String oprId) {
		OprId = oprId;
	}
	public String getCreOprId() {
		return creOprId;
	}
	public void setCreOprId(String creOprId) {
		this.creOprId = creOprId;
	}
	public String getCreTime() {
		return creTime;
	}
	public void setCreTime(String creTime) {
		this.creTime = creTime;
	}
	public String getLimitOld() {
		return limitOld;
	}
	public void setLimitOld(String limitOld) {
		this.limitOld = limitOld;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMchtNo() {
		return mchtNo;
	}
	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}
	public String getSaState() {
		return saState;
	}
	public void setSaState(String saState) {
		this.saState = saState;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getTxnNum() {
		return txnNum;
	}
	public void setTxnNum(String txnNum) {
		this.txnNum = txnNum;
	}
	public String getBussType() {
		return bussType;
	}
	public void setBussType(String bussType) {
		this.bussType = bussType;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getChannelOld() {
		return channelOld;
	}
	public void setChannelOld(String channelOld) {
		this.channelOld = channelOld;
	}
	public String getBussTypeOld() {
		return bussTypeOld;
	}
	public void setBussTypeOld(String bussTypeOld) {
		this.bussTypeOld = bussTypeOld;
	}
	public String getTxnNumOld() {
		return txnNumOld;
	}
	public void setTxnNumOld(String txnNumOld) {
		this.txnNumOld = txnNumOld;
	}
	public String getCardTypeOld() {
		return cardTypeOld;
	}
	public void setCardTypeOld(String cardTypeOld) {
		this.cardTypeOld = cardTypeOld;
	}
	
	public String getLimit() {
		return limit;
	}
	public void setLimit(String limit) {
		this.limit = limit;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bussType == null) ? 0 : bussType.hashCode());
		result = prime * result
				+ ((bussTypeOld == null) ? 0 : bussTypeOld.hashCode());
		result = prime * result
				+ ((cardType == null) ? 0 : cardType.hashCode());
		result = prime * result
				+ ((cardTypeOld == null) ? 0 : cardTypeOld.hashCode());
		result = prime * result + ((channel == null) ? 0 : channel.hashCode());
		result = prime * result
				+ ((channelOld == null) ? 0 : channelOld.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((mchtNo == null) ? 0 : mchtNo.hashCode());
		result = prime * result + ((oprID == null) ? 0 : oprID.hashCode());
		result = prime * result + ((saState == null) ? 0 : saState.hashCode());
		result = prime * result + ((txnNum == null) ? 0 : txnNum.hashCode());
		result = prime * result
				+ ((txnNumOld == null) ? 0 : txnNumOld.hashCode());
		result = prime * result
				+ ((updateTime == null) ? 0 : updateTime.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TblRiskMchtTranLimit other = (TblRiskMchtTranLimit) obj;
		if (bussType == null) {
			if (other.bussType != null)
				return false;
		} else if (!bussType.equals(other.bussType))
			return false;
		if (bussTypeOld == null) {
			if (other.bussTypeOld != null)
				return false;
		} else if (!bussTypeOld.equals(other.bussTypeOld))
			return false;
		if (cardType == null) {
			if (other.cardType != null)
				return false;
		} else if (!cardType.equals(other.cardType))
			return false;
		if (cardTypeOld == null) {
			if (other.cardTypeOld != null)
				return false;
		} else if (!cardTypeOld.equals(other.cardTypeOld))
			return false;
		if (channel == null) {
			if (other.channel != null)
				return false;
		} else if (!channel.equals(other.channel))
			return false;
		if (channelOld == null) {
			if (other.channelOld != null)
				return false;
		} else if (!channelOld.equals(other.channelOld))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (mchtNo == null) {
			if (other.mchtNo != null)
				return false;
		} else if (!mchtNo.equals(other.mchtNo))
			return false;
		if (oprID == null) {
			if (other.oprID != null)
				return false;
		} else if (!oprID.equals(other.oprID))
			return false;
		if (saState == null) {
			if (other.saState != null)
				return false;
		} else if (!saState.equals(other.saState))
			return false;
		if (txnNum == null) {
			if (other.txnNum != null)
				return false;
		} else if (!txnNum.equals(other.txnNum))
			return false;
		if (txnNumOld == null) {
			if (other.txnNumOld != null)
				return false;
		} else if (!txnNumOld.equals(other.txnNumOld))
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		return true;
	}
	
}

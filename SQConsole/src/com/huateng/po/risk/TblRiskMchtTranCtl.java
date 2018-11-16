package com.huateng.po.risk;

import java.io.Serializable;

public class TblRiskMchtTranCtl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String mchtNo;//商户号
	private String saState;//状态
	private String oprID;//操作员编号
	private String updateTime;//修改时间
	private String txnNum;//交易代码
	private String bussType;//业务类型
	private String channel;//交易渠道
	private String channelOld;//修改前的
	private String bussTypeOld;//修改前的
	private String txnNumOld;//修改前的
	private String reserved1;//审核人编号
	
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

	public String getTxnNum() {
		return txnNum;
	}

	public void setTxnNum(String txnNum) {
		this.txnNum = txnNum;
	}

	public String getSaState() {
		return saState;
	}

	public void setSaState(String saState) {
		this.saState = saState;
	}

	public String getOprID() {
		return oprID;
	}

	public void setOprID(String oprID) {
		this.oprID = oprID;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
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

	public String toString(){
		return super.toString();
	}
	
	public String getReserved1() {
		return reserved1;
	}

	public void setReserved1(String reserved1) {
		this.reserved1 = reserved1;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bussType == null) ? 0 : bussType.hashCode());
		result = prime * result
				+ ((bussTypeOld == null) ? 0 : bussTypeOld.hashCode());
		result = prime * result + ((channel == null) ? 0 : channel.hashCode());
		result = prime * result
				+ ((channelOld == null) ? 0 : channelOld.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((mchtNo == null) ? 0 : mchtNo.hashCode());
		result = prime * result + ((oprID == null) ? 0 : oprID.hashCode());
		result = prime * result
				+ ((reserved1 == null) ? 0 : reserved1.hashCode());
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
		TblRiskMchtTranCtl other = (TblRiskMchtTranCtl) obj;
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
		if (reserved1 == null) {
			if (other.reserved1 != null)
				return false;
		} else if (!reserved1.equals(other.reserved1))
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

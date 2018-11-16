package com.huateng.po.rout;

import java.io.Serializable;

public class TblRouteInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String cardBin;//卡BIN
	private String bussType;//业务类型
	private String txnNum;//交易类型
	private String channel;//渠道
	private String areaNo;//地区代码
	private String mchntId;//受理商户号
	private String reserved;//保留域primary key
	private String destInstId;//目的机构
	private String saState;//审核状态
	private String creatorId;//创建人编号——存储的是修改人
	private String checkId;//审核人编号
	private String updateTime;//更新时间
	private String checkTime;//审核时间
	private String creOprId;//创建人编号
	private String creTime;//创建时间
	private String cardType;//卡类型
	private long minAmt;  //最低金额
	private long maxAmt;  //最高金额
	private String mchtMcc;
	
	public TblRouteInfo(){}
	
	public String getReserved() {
		return reserved;
	}
	public void setReserved(String reserved) {
		this.reserved = reserved;
	}
	public String getDestInstId() {
		return destInstId;
	}
	public void setDestInstId(String destInstId) {
		this.destInstId = destInstId;
	}
	public String getSaState() {
		return saState;
	}
	public void setSaState(String saState) {
		this.saState = saState;
	}
	public String getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
	public String getCheckId() {
		return checkId;
	}
	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}

	public String getCardBin() {
		return cardBin.split("-")[0];
	}

	public void setCardBin(String cardBin) {
		this.cardBin = cardBin;
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

	public String getBussType() {
		return bussType;
	}

	public void setBussType(String bussType) {
		this.bussType = bussType;
	}

	public String getTxnNum() {
		return txnNum;
	}

	public void setTxnNum(String txnNum) {
		this.txnNum = txnNum;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getAreaNo() {
		return areaNo;
	}

	public void setAreaNo(String areaNo) {
		this.areaNo = areaNo;
	}

	public String getMchntId() {
		return mchntId;
	}

	public void setMchntId(String mchntId) {
		this.mchntId = mchntId;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	
	public long getMinAmt() {
		return minAmt;
	}

	public void setMinAmt(long minAmt) {
		this.minAmt = minAmt;
	}

	public long getMaxAmt() {
		return maxAmt;
	}

	public void setMaxAmt(long maxAmt) {
		this.maxAmt = maxAmt;
	}

	public String getMchtMcc() {
		return mchtMcc;
	}

	public void setMchtMcc(String mchtMcc) {
		this.mchtMcc = mchtMcc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((areaNo == null) ? 0 : areaNo.hashCode());
		result = prime * result
				+ ((bussType == null) ? 0 : bussType.hashCode());
		result = prime * result + ((cardBin == null) ? 0 : cardBin.hashCode());
		result = prime * result + ((channel == null) ? 0 : channel.hashCode());
		result = prime * result + ((checkId == null) ? 0 : checkId.hashCode());
		result = prime * result
				+ ((checkTime == null) ? 0 : checkTime.hashCode());
		result = prime * result
				+ ((creatorId == null) ? 0 : creatorId.hashCode());
		result = prime * result
				+ ((destInstId == null) ? 0 : destInstId.hashCode());
		result = prime * result + ((mchntId == null) ? 0 : mchntId.hashCode());
		result = prime * result
				+ ((reserved == null) ? 0 : reserved.hashCode());
		result = prime * result + ((saState == null) ? 0 : saState.hashCode());
		result = prime * result + ((txnNum == null) ? 0 : txnNum.hashCode());
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
		TblRouteInfo other = (TblRouteInfo) obj;
		if (areaNo == null) {
			if (other.areaNo != null)
				return false;
		} else if (!areaNo.equals(other.areaNo))
			return false;
		if (bussType == null) {
			if (other.bussType != null)
				return false;
		} else if (!bussType.equals(other.bussType))
			return false;
		if (cardBin == null) {
			if (other.cardBin != null)
				return false;
		} else if (!cardBin.equals(other.cardBin))
			return false;
		if (channel == null) {
			if (other.channel != null)
				return false;
		} else if (!channel.equals(other.channel))
			return false;
		if (checkId == null) {
			if (other.checkId != null)
				return false;
		} else if (!checkId.equals(other.checkId))
			return false;
		if (checkTime == null) {
			if (other.checkTime != null)
				return false;
		} else if (!checkTime.equals(other.checkTime))
			return false;
		if (creatorId == null) {
			if (other.creatorId != null)
				return false;
		} else if (!creatorId.equals(other.creatorId))
			return false;
		if (destInstId == null) {
			if (other.destInstId != null)
				return false;
		} else if (!destInstId.equals(other.destInstId))
			return false;
		if (mchntId == null) {
			if (other.mchntId != null)
				return false;
		} else if (!mchntId.equals(other.mchntId))
			return false;
		if (reserved == null) {
			if (other.reserved != null)
				return false;
		} else if (!reserved.equals(other.reserved))
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
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		return true;
	}

	public Serializable clone(TblRouteInfoTemp tblRouteInfoTemp) {
		tblRouteInfoTemp.setAreaNo(this.areaNo);
		tblRouteInfoTemp.setBussType(this.bussType);
		tblRouteInfoTemp.setCardBin(this.cardBin);
		tblRouteInfoTemp.setChannel(this.channel);
		tblRouteInfoTemp.setCheckId(this.checkId);
		tblRouteInfoTemp.setCheckTime(this.checkTime);
		tblRouteInfoTemp.setCreatorId(this.creatorId);
		tblRouteInfoTemp.setDestInstId(this.destInstId);
		tblRouteInfoTemp.setMchntId(this.mchntId);
		tblRouteInfoTemp.setReserved(this.reserved);
		tblRouteInfoTemp.setSaState(this.saState);
		tblRouteInfoTemp.setUpdateTime(this.updateTime);
		tblRouteInfoTemp.setTxnNum(this.txnNum);
		tblRouteInfoTemp.setCreTime(this.creTime);
		tblRouteInfoTemp.setCreOprId(this.creOprId);
		tblRouteInfoTemp.setCardType(this.cardType);
		tblRouteInfoTemp.setMinAmt(this.minAmt);
		tblRouteInfoTemp.setMaxAmt(this.maxAmt);
		tblRouteInfoTemp.setMchtMcc(this.mchtMcc);
		
		return tblRouteInfoTemp;
	}
}

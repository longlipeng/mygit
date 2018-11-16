/**
 * 
 */
package com.huateng.po.error;

import java.io.Serializable;

/**
 * @author zoujunqing
 *
 */
public class ManualReturn implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((amtReturn == null) ? 0 : amtReturn.hashCode());
		result = prime * result
				+ ((amtTrans == null) ? 0 : amtTrans.hashCode());
		result = prime * result
				+ ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((instDate == null) ? 0 : instDate.hashCode());
		result = prime * result + ((mchtNo == null) ? 0 : mchtNo.hashCode());
		result = prime * result + ((oprId == null) ? 0 : oprId.hashCode());
		result = prime * result + ((pan == null) ? 0 : pan.hashCode());
		result = prime * result
				+ ((retrivlRef == null) ? 0 : retrivlRef.hashCode());
		result = prime * result + ((saState == null) ? 0 : saState.hashCode());
		result = prime * result + ((termId == null) ? 0 : termId.hashCode());
		result = prime * result + ((updDate == null) ? 0 : updDate.hashCode());
		result = prime * result + ((updId == null) ? 0 : updId.hashCode());
		result = prime * result + ((returnFee == null) ? 0 : returnFee.hashCode());
		result = prime * result + ((instCode == null) ? 0 : instCode.hashCode());
		result = prime * result + ((oprFlag == null) ? 0 : oprFlag.hashCode());
		result = prime * result + ((srcId == null) ? 0 : srcId.hashCode());
		result = prime * result + ((potalSsn == null) ? 0 : potalSsn.hashCode());
		result = prime * result + ((rspCode == null) ? 0 : rspCode.hashCode());
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
		ManualReturn other = (ManualReturn) obj;
		if (amtReturn == null) {
			if (other.amtReturn != null)
				return false;
		} else if (!amtReturn.equals(other.amtReturn))
			return false;
		if (amtTrans == null) {
			if (other.amtTrans != null)
				return false;
		} else if (!amtTrans.equals(other.amtTrans))
			return false;
		if (createDate == null) {
			if (other.createDate != null)
				return false;
		} else if (!createDate.equals(other.createDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (instDate == null) {
			if (other.instDate != null)
				return false;
		} else if (!instDate.equals(other.instDate))
			return false;
		if (mchtNo == null) {
			if (other.mchtNo != null)
				return false;
		} else if (!mchtNo.equals(other.mchtNo))
			return false;
		if (oprId == null) {
			if (other.oprId != null)
				return false;
		} else if (!oprId.equals(other.oprId))
			return false;
		if (pan == null) {
			if (other.pan != null)
				return false;
		} else if (!pan.equals(other.pan))
			return false;
		if (retrivlRef == null) {
			if (other.retrivlRef != null)
				return false;
		} else if (!retrivlRef.equals(other.retrivlRef))
			return false;
		if (saState == null) {
			if (other.saState != null)
				return false;
		} else if (!saState.equals(other.saState))
			return false;
		if (termId == null) {
			if (other.termId != null)
				return false;
		} else if (!termId.equals(other.termId))
			return false;
		if (updDate == null) {
			if (other.updDate != null)
				return false;
		} else if (!updDate.equals(other.updDate))
			return false;
		if (updId == null) {
			if (other.updId != null)
				return false;
		} else if (!updId.equals(other.updId))
			return false;
		if (returnFee == null) {
			if (other.returnFee != null)
				return false;
		} else if (!returnFee.equals(other.returnFee))
			return false;
		if (instCode == null) {
			if (other.instCode != null)
				return false;
		} else if (!instCode.equals(other.instCode))
			return false;
		if (oprFlag == null) {
			if (other.oprFlag != null)
				return false;
		} else if (!oprFlag.equals(other.oprFlag))
			return false;
		if (srcId == null) {
			if (other.srcId != null)
				return false;
		} else if (!srcId.equals(other.srcId))
			return false;
		if (potalSsn == null) {
			if (other.potalSsn != null)
				return false;
		} else if (!potalSsn.equals(other.potalSsn))
			return false;
		if (rspCode == null) {
			if (other.rspCode != null)
				return false;
		} else if (!rspCode.equals(other.rspCode))
			return false;
		
		
		return true;
	}
	private String id;
	private String mchtNo;
	private String termId;
	private String pan;
	private String amtTrans;
	private String retrivlRef;
	private String amtReturn;
	private String instDate;
	private String txnNum;
	private String saState;
	private String oprId;
	private String updId;
	private String createDate;
	private String updDate;
	private String sysSsn;
	private String termSsn;
	private String txnInfo;
	private String returnFee;
	private String instCode;
	private String oprFlag;
	private String srcId;
	private String potalSsn;
	private String rspCode;
	private String destId;
	
	
	public String getDestId() {
		return destId;
	}
	public void setDestId(String destId) {
		this.destId = destId;
	}
	public String getReturnFee() {
		return returnFee;
	}
	public void setReturnFee(String returnFee) {
		this.returnFee = returnFee;
	}
	public String getSrcId() {
		return srcId;
	}
	public void setSrcId(String srcId) {
		this.srcId = srcId;
	}
	public String getPotalSsn() {
		return potalSsn;
	}
	public void setPotalSsn(String potalSsn) {
		this.potalSsn = potalSsn;
	}
	public String getRspCode() {
		return rspCode;
	}
	public void setRspCode(String rspCode) {
		this.rspCode = rspCode;
	}
	public String getOprFlag() {
		return oprFlag;
	}
	public void setOprFlag(String oprFlag) {
		this.oprFlag = oprFlag;
	}
	public String getInstCode() {
		return instCode;
	}
	public void setInstCode(String instCode) {
		this.instCode = instCode;
	}
	public String getreturnFee() {
		return returnFee;
	}
	public void setreturnFee(String returnFee) {
		this.returnFee = returnFee;
	}
	
	public String getTxnInfo() {
		return txnInfo;
	}
	public void setTxnInfo(String txnInfo) {
		this.txnInfo = txnInfo;
	}
	public String getTermSsn() {
		return termSsn;
	}
	public void setTermSsn(String termSsn) {
		this.termSsn = termSsn;
	}
 
	public String getSysSsn() {
		return sysSsn;
	}
	public void setSysSsn(String sysSsn) {
		this.sysSsn = sysSsn;
	}
	public String getTxnNum() {
		return txnNum;
	}
	public void setTxnNum(String txnNum) {
		this.txnNum = txnNum;
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
	public String getTermId() {
		return termId;
	}
	public void setTermId(String termId) {
		this.termId = termId;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public String getAmtTrans() {
		return amtTrans;
	}
	public void setAmtTrans(String amtTrans) {
		this.amtTrans = amtTrans;
	}
	public String getRetrivlRef() {
		return retrivlRef;
	}
	public void setRetrivlRef(String retrivlRef) {
		this.retrivlRef = retrivlRef;
	}
	public String getAmtReturn() {
		return amtReturn;
	}
	public void setAmtReturn(String amtReturn) {
		this.amtReturn = amtReturn;
	}
	public String getInstDate() {
		return instDate;
	}
	public void setInstDate(String instDate) {
		this.instDate = instDate;
	}
	public String getSaState() {
		return saState;
	}
	public void setSaState(String saState) {
		this.saState = saState;
	}
	public String getOprId() {
		return oprId;
	}
	public void setOprId(String oprId) {
		this.oprId = oprId;
	}
	public String getUpdId() {
		return updId;
	}
	public void setUpdId(String updId) {
		this.updId = updId;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getUpdDate() {
		return updDate;
	}
	public void setUpdDate(String updDate) {
		this.updDate = updDate;
	}
	
	
	

}

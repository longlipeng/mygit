package com.huateng.po.rout;

import java.io.Serializable;

/**
 * mapping table TBL_TERM_CHANNEL_INF2交行终端通道配置信息表
 * @author crystal
 *
 */
public class TblTermChannelInf2 implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;//key
	private String termIns;//所属机构号
	private String mchtMcc;//商户MCC码
	private String mchtId;//商户号
	private String mchtTermId;//商户终端号
	private String insMcc;//机构MCC码
	private String insMcht;//机构商户号
	private String insTerm;//机构终端号
	private String cardType;//卡类型
	private String insMcht2;//机构核心商户号
	private String insTerm2;//机构核心终端号
	private String stat;//状态
	private String creTime;//创建时间
	private String creOprId;//创建人
	private String modiTime;//修改时间
	private String modiOprId;//修改人
	private String checkTime;//审核时间
	private String checkOprId;//审核人
	private String termInsOld;//修改前的所属机构号
	private String mchtMccOld;//修改前的商户MCC码
	private String mchtIdOld;//修改前的商户号
	private String mchtTermIdOld;//修改前的商户终端号
	private String insMccOld;//修改前的机构MCC码
	private String insMchtOld;//修改前的机构核心商户号
	private String insTermOld;//修改前的机构核心终端号
	private String cardTypeOld;//修改前的卡类型
	private String insMcht2Old;//修改前的机构交易商户号
	private String insTerm2Old;//修改前的机构交易终端号
	private String reserve01;//预留域，修改前的索引
	private String reserve02;//预留域，修改前的主密钥
	private String lmk;//终端主密钥
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTermIns() {
		return termIns;
	}
	public void setTermIns(String termIns) {
		this.termIns = termIns;
	}
	public String getMchtMcc() {
		return mchtMcc;
	}
	public void setMchtMcc(String mchtMcc) {
		this.mchtMcc = mchtMcc;
	}
	public String getMchtId() {
		return mchtId;
	}
	public void setMchtId(String mchtId) {
		this.mchtId = mchtId;
	}
	public String getMchtTermId() {
		return mchtTermId;
	}
	public void setMchtTermId(String mchtTermId) {
		this.mchtTermId = mchtTermId;
	}
	public String getInsMcc() {
		return insMcc;
	}
	public void setInsMcc(String insMcc) {
		this.insMcc = insMcc;
	}
	public String getInsMcht() {
		return insMcht;
	}
	public void setInsMcht(String insMcht) {
		this.insMcht = insMcht;
	}
	public String getInsTerm() {
		return insTerm;
	}
	public void setInsTerm(String insTerm) {
		this.insTerm = insTerm;
	}
	public String getStat() {
		return stat;
	}
	public void setStat(String stat) {
		this.stat = stat;
	}
	public String getCreTime() {
		return creTime;
	}
	public void setCreTime(String creTime) {
		this.creTime = creTime;
	}
	public String getCreOprId() {
		return creOprId;
	}
	public void setCreOprId(String creOprId) {
		this.creOprId = creOprId;
	}
	public String getModiTime() {
		return modiTime;
	}
	public void setModiTime(String modiTime) {
		this.modiTime = modiTime;
	}
	public String getModiOprId() {
		return modiOprId;
	}
	public void setModiOprId(String modiOprId) {
		this.modiOprId = modiOprId;
	}
	public String getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}
	public String getCheckOprId() {
		return checkOprId;
	}
	public void setCheckOprId(String checkOprId) {
		this.checkOprId = checkOprId;
	}
	public String getTermInsOld() {
		return termInsOld;
	}
	public void setTermInsOld(String termInsOld) {
		this.termInsOld = termInsOld;
	}
	public String getMchtMccOld() {
		return mchtMccOld;
	}
	public void setMchtMccOld(String mchtMccOld) {
		this.mchtMccOld = mchtMccOld;
	}
	public String getMchtIdOld() {
		return mchtIdOld;
	}
	public void setMchtIdOld(String mchtIdOld) {
		this.mchtIdOld = mchtIdOld;
	}
	public String getMchtTermIdOld() {
		return mchtTermIdOld;
	}
	public void setMchtTermIdOld(String mchtTermIdOld) {
		this.mchtTermIdOld = mchtTermIdOld;
	}
	public String getInsMccOld() {
		return insMccOld;
	}
	public void setInsMccOld(String insMccOld) {
		this.insMccOld = insMccOld;
	}
	public String getInsMchtOld() {
		return insMchtOld;
	}
	public void setInsMchtOld(String insMchtOld) {
		this.insMchtOld = insMchtOld;
	}
	public String getInsTermOld() {
		return insTermOld;
	}
	public void setInsTermOld(String insTermOld) {
		this.insTermOld = insTermOld;
	}
	public String getReserve01() {
		return reserve01;
	}
	public void setReserve01(String reserve01) {
		this.reserve01 = reserve01;
	}
	public String getReserve02() {
		return reserve02;
	}
	public void setReserve02(String reserve02) {
		this.reserve02 = reserve02;
	}
	
	public String getLmk() {
		return lmk;
	}
	public void setLmk(String lmk) {
		this.lmk = lmk;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getInsMcht2() {
		return insMcht2;
	}
	public void setInsMcht2(String insMcht2) {
		this.insMcht2 = insMcht2;
	}
	public String getInsTerm2() {
		return insTerm2;
	}
	public void setInsTerm2(String insTerm2) {
		this.insTerm2 = insTerm2;
	}
	public String getCardTypeOld() {
		return cardTypeOld;
	}
	public void setCardTypeOld(String cardTypeOld) {
		this.cardTypeOld = cardTypeOld;
	}
	public String getInsMcht2Old() {
		return insMcht2Old;
	}
	public void setInsMcht2Old(String insMcht2Old) {
		this.insMcht2Old = insMcht2Old;
	}
	public String getInsTerm2Old() {
		return insTerm2Old;
	}
	public void setInsTerm2Old(String insTerm2Old) {
		this.insTerm2Old = insTerm2Old;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((checkOprId == null) ? 0 : checkOprId.hashCode());
		result = prime * result
				+ ((checkTime == null) ? 0 : checkTime.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((insMcc == null) ? 0 : insMcc.hashCode());
		result = prime * result
				+ ((insMccOld == null) ? 0 : insMccOld.hashCode());
		result = prime * result + ((insMcht == null) ? 0 : insMcht.hashCode());
		result = prime * result
				+ ((insMchtOld == null) ? 0 : insMchtOld.hashCode());
		result = prime * result + ((insTerm == null) ? 0 : insTerm.hashCode());
		result = prime * result
				+ ((insTermOld == null) ? 0 : insTermOld.hashCode());
		result = prime * result + ((lmk == null) ? 0 : lmk.hashCode());
		result = prime * result + ((mchtId == null) ? 0 : mchtId.hashCode());
		result = prime * result
				+ ((mchtIdOld == null) ? 0 : mchtIdOld.hashCode());
		result = prime * result + ((mchtMcc == null) ? 0 : mchtMcc.hashCode());
		result = prime * result
				+ ((mchtMccOld == null) ? 0 : mchtMccOld.hashCode());
		result = prime * result
				+ ((mchtTermId == null) ? 0 : mchtTermId.hashCode());
		result = prime * result
				+ ((mchtTermIdOld == null) ? 0 : mchtTermIdOld.hashCode());
		result = prime * result
				+ ((modiOprId == null) ? 0 : modiOprId.hashCode());
		result = prime * result
				+ ((modiTime == null) ? 0 : modiTime.hashCode());
		result = prime * result
				+ ((reserve01 == null) ? 0 : reserve01.hashCode());
		result = prime * result
				+ ((reserve02 == null) ? 0 : reserve02.hashCode());
		result = prime * result + ((stat == null) ? 0 : stat.hashCode());
		result = prime * result + ((termIns == null) ? 0 : termIns.hashCode());
		result = prime * result
				+ ((termInsOld == null) ? 0 : termInsOld.hashCode());
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
		TblTermChannelInf2 other = (TblTermChannelInf2) obj;
		if (checkOprId == null) {
			if (other.checkOprId != null)
				return false;
		} else if (!checkOprId.equals(other.checkOprId))
			return false;
		if (checkTime == null) {
			if (other.checkTime != null)
				return false;
		} else if (!checkTime.equals(other.checkTime))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (insMcc == null) {
			if (other.insMcc != null)
				return false;
		} else if (!insMcc.equals(other.insMcc))
			return false;
		if (insMccOld == null) {
			if (other.insMccOld != null)
				return false;
		} else if (!insMccOld.equals(other.insMccOld))
			return false;
		if (insMcht == null) {
			if (other.insMcht != null)
				return false;
		} else if (!insMcht.equals(other.insMcht))
			return false;
		if (insMchtOld == null) {
			if (other.insMchtOld != null)
				return false;
		} else if (!insMchtOld.equals(other.insMchtOld))
			return false;
		if (insTerm == null) {
			if (other.insTerm != null)
				return false;
		} else if (!insTerm.equals(other.insTerm))
			return false;
		if (insTermOld == null) {
			if (other.insTermOld != null)
				return false;
		} else if (!insTermOld.equals(other.insTermOld))
			return false;
		if (lmk == null) {
			if (other.lmk != null)
				return false;
		} else if (!lmk.equals(other.lmk))
			return false;
		if (mchtId == null) {
			if (other.mchtId != null)
				return false;
		} else if (!mchtId.equals(other.mchtId))
			return false;
		if (mchtIdOld == null) {
			if (other.mchtIdOld != null)
				return false;
		} else if (!mchtIdOld.equals(other.mchtIdOld))
			return false;
		if (mchtMcc == null) {
			if (other.mchtMcc != null)
				return false;
		} else if (!mchtMcc.equals(other.mchtMcc))
			return false;
		if (mchtMccOld == null) {
			if (other.mchtMccOld != null)
				return false;
		} else if (!mchtMccOld.equals(other.mchtMccOld))
			return false;
		if (mchtTermId == null) {
			if (other.mchtTermId != null)
				return false;
		} else if (!mchtTermId.equals(other.mchtTermId))
			return false;
		if (mchtTermIdOld == null) {
			if (other.mchtTermIdOld != null)
				return false;
		} else if (!mchtTermIdOld.equals(other.mchtTermIdOld))
			return false;
		if (modiOprId == null) {
			if (other.modiOprId != null)
				return false;
		} else if (!modiOprId.equals(other.modiOprId))
			return false;
		if (modiTime == null) {
			if (other.modiTime != null)
				return false;
		} else if (!modiTime.equals(other.modiTime))
			return false;
		if (reserve01 == null) {
			if (other.reserve01 != null)
				return false;
		} else if (!reserve01.equals(other.reserve01))
			return false;
		if (reserve02 == null) {
			if (other.reserve02 != null)
				return false;
		} else if (!reserve02.equals(other.reserve02))
			return false;
		if (stat == null) {
			if (other.stat != null)
				return false;
		} else if (!stat.equals(other.stat))
			return false;
		if (termIns == null) {
			if (other.termIns != null)
				return false;
		} else if (!termIns.equals(other.termIns))
			return false;
		if (termInsOld == null) {
			if (other.termInsOld != null)
				return false;
		} else if (!termInsOld.equals(other.termInsOld))
			return false;
		return true;
	}
	
}

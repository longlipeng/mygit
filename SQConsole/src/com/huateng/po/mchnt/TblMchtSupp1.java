/**
 * 
 */
package com.huateng.po.mchnt;

import java.io.Serializable;

/**
 * 环讯商户补充信息表
 * @author jinlong
 * 2012-5-14
 *
 */
@SuppressWarnings("serial")
public class TblMchtSupp1 implements Serializable{

	/**
	 * 15位商户编号
	 */
	private String mchtNo;
	/**
	 * 省代码
	 */
	private String province;
	/**
	 * 市代码
	 */
	private String city;
	/**
	 * 区/县
	 */
	private String area;
	/**
	 * 所属部门
	 */
	private String depart;
	/**
	 * 套用商户类型原因
	 */
	private String mchtTypeCause;
	/**
	 * 国家代码
	 */
	private String countryCode;
	/**
	 * 经营范围
	 */
	 private String busScope;
	 /**
	  * 主营业务
	  */
	 private String busMain;
	 /**
	  * 联系人座机号
	  */
	 private String linkTel;
	 /**
	  * 财务负责人
	  */
	 private String finManager;
	 /**
	  * 财务人手机号
	  */
	 private String finPhone;
	 /**
	  * 财务座机号
	  */
	 private String finTel;
	 /**
	  * 财务传真
	  */
	 private String finFax;
	 /**
	  * 财务电子邮箱
	  */
	 private String finEmail;
	 /**
	  * 直营连锁店数量
	  */
	 private String shopNum;
	 /**
	  * 经营地段
	  */
	 private String busArea;
	 /**
	  * 经营区域
	  */
	 private String busZone;
	 /**
	  * 营业面积
	  */
	 private String acreage;
	 /**
	  * 营业用地性质
	  */
	 private String areaType;
	 /**
	  * 员工人数
	  */
	 private String empNum;
	 /**
	  * 收银员数量
	  */
	 private String cashierNum;
	 /**
	  * 收银台数量
	  */
	 private String cashierDeskNum;
	 /**
	  * 前一年营业额
	  */
	 private String turnoverBefore;
	 /**
	  * 年营业额
	  */
	 private String turnover;
	 /**
	  * 开业日期
	  */
	 private String openDate;
	 /**
	  * 是否为洒店行业
	  */
	 private String isWineshop; 
	 /**
	  * 酒店等级
	  */
	 private String wineshopLvl;
	 /**
	  * 是否多个收单行
	  */
	 private String isMoreAcq;
	 /**
	  * 是否申请外卡
	  */
	 private String isAppOutSide; 
	 /**
	  * 是否有内卡受理经验
	  */
	 private String hasInnerPosExp;
	 /**
	  * 是否有POS外卡受理经验
	  */
	 private String hasOurPosExp;
	 /**
	  * 服务模式
	  */
	 private String serType;
	 /**
	  * 服务级别
	  */
	 private String serLvl;
	 /**
	  * 商户拓展渠道
	  */
	 private String src;
	 /**
	  * 拓展人
	  */
	 private String expander;
	 /**
	  * 推荐人
	  */
	 private String referrer; 
	 /**
	  * 原商户号
	  */
	 private String mchtNoOld;
	 /**
	  * mcc2
	  */
	 private String mcc2;
	 /**
	  * 代理方名称
	  */
	 private String proxy;
	 /**
	  * 代理方联系方式
	  */
	 private String proxyTel;
	 /**
	  * 国籍
	  */
	 private String nationality;
	 
	 private String singleAmt;    //单笔消费金额
	private String monthTotalAmt;  //月消费总额
	
	
	 
	public String getSingleAmt() {
		return singleAmt;
	}
	public void setSingleAmt(String singleAmt) {
		this.singleAmt = singleAmt;
	}
	public String getMonthTotalAmt() {
		return monthTotalAmt;
	}
	public void setMonthTotalAmt(String monthTotalAmt) {
		this.monthTotalAmt = monthTotalAmt;
	}
	/**
	 * @return the nationality
	 */
	public String getNationality() {
		return nationality;
	}
	/**
	 * @param nationality the nationality to set
	 */
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	/**
	 * @return the hasOurPosExp
	 */
	public String getHasOurPosExp() {
		return hasOurPosExp;
	}
	/**
	 * @param hasOurPosExp the hasOurPosExp to set
	 */
	public void setHasOurPosExp(String hasOurPosExp) {
		this.hasOurPosExp = hasOurPosExp;
	}
	/**
	 * @return the expander
	 */
	public String getExpander() {
		return expander;
	}
	/**
	 * @param expander the expander to set
	 */
	public void setExpander(String expander) {
		this.expander = expander;
	}
	/**
	 * @return the serLvl
	 */
	public String getSerLvl() {
		return serLvl;
	}
	/**
	 * @param serLvl the serLvl to set
	 */
	public void setSerLvl(String serLvl) {
		this.serLvl = serLvl;
	}
	/**
	 * @return the mchtNo
	 */
	public String getMchtNo() {
		return mchtNo;
	}
	/**
	 * @param mchtNo the mchtNo to set
	 */
	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}
	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}
	/**
	 * @param province the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	
	
	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}
	/**
	 * @param area the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}
	/**
	 * @return the depart
	 */
	public String getDepart() {
		return depart;
	}
	/**
	 * @param depart the depart to set
	 */
	public void setDepart(String depart) {
		this.depart = depart;
	}
	/**
	 * @return the mchtTypeCause
	 */
	public String getMchtTypeCause() {
		return mchtTypeCause;
	}
	/**
	 * @param mchtTypeCause the mchtTypeCause to set
	 */
	public void setMchtTypeCause(String mchtTypeCause) {
		this.mchtTypeCause = mchtTypeCause;
	}
	/**
	 * @return the countryCode
	 */
	public String getCountryCode() {
		return countryCode;
	}
	/**
	 * @param countryCode the countryCode to set
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	/**
	 * @return the busScope
	 */
	public String getBusScope() {
		return busScope;
	}
	/**
	 * @param busScope the busScope to set
	 */
	public void setBusScope(String busScope) {
		this.busScope = busScope;
	}
	/**
	 * @return the busMain
	 */
	public String getBusMain() {
		return busMain;
	}
	/**
	 * @param busMain the busMain to set
	 */
	public void setBusMain(String busMain) {
		this.busMain = busMain;
	}
	/**
	 * @return the linkTel
	 */
	public String getLinkTel() {
		return linkTel;
	}
	/**
	 * @param linkTel the linkTel to set
	 */
	public void setLinkTel(String linkTel) {
		this.linkTel = linkTel;
	}
	/**
	 * @return the finManager
	 */
	public String getFinManager() {
		return finManager;
	}
	/**
	 * @param finManager the finManager to set
	 */
	public void setFinManager(String finManager) {
		this.finManager = finManager;
	}
	/**
	 * @return the finPhone
	 */
	public String getFinPhone() {
		return finPhone;
	}
	/**
	 * @param finPhone the finPhone to set
	 */
	public void setFinPhone(String finPhone) {
		this.finPhone = finPhone;
	}
	/**
	 * @return the finTel
	 */
	public String getFinTel() {
		return finTel;
	}
	/**
	 * @param finTel the finTel to set
	 */
	public void setFinTel(String finTel) {
		this.finTel = finTel;
	}
	/**
	 * @return the finFax
	 */
	public String getFinFax() {
		return finFax;
	}
	/**
	 * @param finFax the finFax to set
	 */
	public void setFinFax(String finFax) {
		this.finFax = finFax;
	}
	/**
	 * @return the finEmail
	 */
	public String getFinEmail() {
		return finEmail;
	}
	/**
	 * @param finEmail the finEmail to set
	 */
	public void setFinEmail(String finEmail) {
		this.finEmail = finEmail;
	}
	/**
	 * @return the shopNum
	 */
	public String getShopNum() {
		return shopNum;
	}
	/**
	 * @param shopNum the shopNum to set
	 */
	public void setShopNum(String shopNum) {
		this.shopNum = shopNum;
	}
	/**
	 * @return the busArea
	 */
	public String getBusArea() {
		return busArea;
	}
	/**
	 * @param busArea the busArea to set
	 */
	public void setBusArea(String busArea) {
		this.busArea = busArea;
	}
	/**
	 * @return the busZone
	 */
	public String getBusZone() {
		return busZone;
	}
	/**
	 * @param busZone the busZone to set
	 */
	public void setBusZone(String busZone) {
		this.busZone = busZone;
	}
	/**
	 * @return the acreage
	 */
	public String getAcreage() {
		return acreage;
	}
	/**
	 * @param acreage the acreage to set
	 */
	public void setAcreage(String acreage) {
		this.acreage = acreage;
	}
	/**
	 * @return the areaType
	 */
	public String getAreaType() {
		return areaType;
	}
	/**
	 * @param areaType the areaType to set
	 */
	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}
	/**
	 * @return the empNum
	 */
	public String getEmpNum() {
		return empNum;
	}
	/**
	 * @param empNum the empNum to set
	 */
	public void setEmpNum(String empNum) {
		this.empNum = empNum;
	}
	/**
	 * @return the cashierNum
	 */
	public String getCashierNum() {
		return cashierNum;
	}
	/**
	 * @param cashierNum the cashierNum to set
	 */
	public void setCashierNum(String cashierNum) {
		this.cashierNum = cashierNum;
	}
	/**
	 * @return the cashierDeskNum
	 */
	public String getCashierDeskNum() {
		return cashierDeskNum;
	}
	/**
	 * @param cashierDeskNum the cashierDeskNum to set
	 */
	public void setCashierDeskNum(String cashierDeskNum) {
		this.cashierDeskNum = cashierDeskNum;
	}
	/**
	 * @return the turnoverBefore
	 */
	public String getTurnoverBefore() {
		return turnoverBefore;
	}
	/**
	 * @param turnoverBefore the turnoverBefore to set
	 */
	public void setTurnoverBefore(String turnoverBefore) {
		this.turnoverBefore = turnoverBefore;
	}
	/**
	 * @return the turnover
	 */
	public String getTurnover() {
		return turnover;
	}
	/**
	 * @param turnover the turnover to set
	 */
	public void setTurnover(String turnover) {
		this.turnover = turnover;
	}
	/**
	 * @return the openDate
	 */
	public String getOpenDate() {
		return openDate;
	}
	/**
	 * @param openDate the openDate to set
	 */
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}
	/**
	 * @return the isWineshop
	 */
	public String getIsWineshop() {
		return isWineshop;
	}
	/**
	 * @param isWineshop the isWineshop to set
	 */
	public void setIsWineshop(String isWineshop) {
		this.isWineshop = isWineshop;
	}
	/**
	 * @return the wineshopLvl
	 */
	public String getWineshopLvl() {
		return wineshopLvl;
	}
	/**
	 * @param wineshopLvl the wineshopLvl to set
	 */
	public void setWineshopLvl(String wineshopLvl) {
		this.wineshopLvl = wineshopLvl;
	}
	/**
	 * @return the isMoreAcq
	 */
	public String getIsMoreAcq() {
		return isMoreAcq;
	}
	/**
	 * @param isMoreAcq the isMoreAcq to set
	 */
	public void setIsMoreAcq(String isMoreAcq) {
		this.isMoreAcq = isMoreAcq;
	}
	/**
	 * @return the isAppOutSide
	 */
	public String getIsAppOutSide() {
		return isAppOutSide;
	}
	/**
	 * @param isAppOutSide the isAppOutSide to set
	 */
	public void setIsAppOutSide(String isAppOutSide) {
		this.isAppOutSide = isAppOutSide;
	}
	/**
	 * @return the hasInnerPosExp
	 */
	public String getHasInnerPosExp() {
		return hasInnerPosExp;
	}
	/**
	 * @param hasInnerPosExp the hasInnerPosExp to set
	 */
	public void setHasInnerPosExp(String hasInnerPosExp) {
		this.hasInnerPosExp = hasInnerPosExp;
	}
	/**
	 * @return the serType
	 */
	public String getSerType() {
		return serType;
	}
	/**
	 * @param serType the serType to set
	 */
	public void setSerType(String serType) {
		this.serType = serType;
	}
	/**
	 * @return the src
	 */
	public String getSrc() {
		return src;
	}
	/**
	 * @param src the src to set
	 */
	public void setSrc(String src) {
		this.src = src;
	}
	/**
	 * @return the referrer
	 */
	public String getReferrer() {
		return referrer;
	}
	/**
	 * @param referrer the referrer to set
	 */
	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}
	/**
	 * @return the mchtNoOld
	 */
	public String getMchtNoOld() {
		return mchtNoOld;
	}
	/**
	 * @param mchtNoOld the mchtNoOld to set
	 */
	public void setMchtNoOld(String mchtNoOld) {
		this.mchtNoOld = mchtNoOld;
	}
	/**
	 * @return the mcc2
	 */
	public String getMcc2() {
		return mcc2;
	}
	/**
	 * @param mcc2 the mcc2 to set
	 */
	public void setMcc2(String mcc2) {
		this.mcc2 = mcc2;
	}
	/**
	 * @return the proxy
	 */
	public String getProxy() {
		return proxy;
	}
	/**
	 * @param proxy the proxy to set
	 */
	public void setProxy(String proxy) {
		this.proxy = proxy;
	}
	/**
	 * @return the proxyTel
	 */
	public String getProxyTel() {
		return proxyTel;
	}
	/**
	 * @param proxyTel the proxyTel to set
	 */
	public void setProxyTel(String proxyTel) {
		this.proxyTel = proxyTel;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((acreage == null) ? 0 : acreage.hashCode());
		result = prime * result
				+ ((areaType == null) ? 0 : areaType.hashCode());
		result = prime * result + ((busArea == null) ? 0 : busArea.hashCode());
		result = prime * result + ((busMain == null) ? 0 : busMain.hashCode());
		result = prime * result
				+ ((busScope == null) ? 0 : busScope.hashCode());
		result = prime * result + ((busZone == null) ? 0 : busZone.hashCode());
		result = prime * result
				+ ((cashierDeskNum == null) ? 0 : cashierDeskNum.hashCode());
		result = prime * result
				+ ((cashierNum == null) ? 0 : cashierNum.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result
				+ ((countryCode == null) ? 0 : countryCode.hashCode());
		result = prime * result + ((depart == null) ? 0 : depart.hashCode());
		result = prime * result + ((empNum == null) ? 0 : empNum.hashCode());
		result = prime * result
				+ ((finEmail == null) ? 0 : finEmail.hashCode());
		result = prime * result + ((finFax == null) ? 0 : finFax.hashCode());
		result = prime * result
				+ ((finManager == null) ? 0 : finManager.hashCode());
		result = prime * result
				+ ((finPhone == null) ? 0 : finPhone.hashCode());
		result = prime * result + ((finTel == null) ? 0 : finTel.hashCode());
		result = prime * result
				+ ((hasInnerPosExp == null) ? 0 : hasInnerPosExp.hashCode());
		result = prime * result
				+ ((isAppOutSide == null) ? 0 : isAppOutSide.hashCode());
		result = prime * result
				+ ((isMoreAcq == null) ? 0 : isMoreAcq.hashCode());
		result = prime * result
				+ ((isWineshop == null) ? 0 : isWineshop.hashCode());
		result = prime * result + ((linkTel == null) ? 0 : linkTel.hashCode());
		result = prime * result + ((mcc2 == null) ? 0 : mcc2.hashCode());
		result = prime * result + ((mchtNo == null) ? 0 : mchtNo.hashCode());
		result = prime * result
				+ ((mchtNoOld == null) ? 0 : mchtNoOld.hashCode());
		result = prime * result
				+ ((mchtTypeCause == null) ? 0 : mchtTypeCause.hashCode());
		result = prime * result
				+ ((openDate == null) ? 0 : openDate.hashCode());
		result = prime * result
				+ ((province == null) ? 0 : province.hashCode());
		result = prime * result + ((proxy == null) ? 0 : proxy.hashCode());
		result = prime * result
				+ ((proxyTel == null) ? 0 : proxyTel.hashCode());
		result = prime * result
				+ ((referrer == null) ? 0 : referrer.hashCode());
		result = prime * result + ((serType == null) ? 0 : serType.hashCode());
		result = prime * result + ((shopNum == null) ? 0 : shopNum.hashCode());
		result = prime * result + ((src == null) ? 0 : src.hashCode());
		result = prime * result
				+ ((turnover == null) ? 0 : turnover.hashCode());
		result = prime * result
				+ ((turnoverBefore == null) ? 0 : turnoverBefore.hashCode());
		result = prime * result
				+ ((wineshopLvl == null) ? 0 : wineshopLvl.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TblMchtSupp1 other = (TblMchtSupp1) obj;
		if (acreage == null) {
			if (other.acreage != null)
				return false;
		} else if (!acreage.equals(other.acreage))
			return false;
		if (areaType == null) {
			if (other.areaType != null)
				return false;
		} else if (!areaType.equals(other.areaType))
			return false;
		if (busArea == null) {
			if (other.busArea != null)
				return false;
		} else if (!busArea.equals(other.busArea))
			return false;
		if (busMain == null) {
			if (other.busMain != null)
				return false;
		} else if (!busMain.equals(other.busMain))
			return false;
		if (busScope == null) {
			if (other.busScope != null)
				return false;
		} else if (!busScope.equals(other.busScope))
			return false;
		if (busZone == null) {
			if (other.busZone != null)
				return false;
		} else if (!busZone.equals(other.busZone))
			return false;
		if (cashierDeskNum == null) {
			if (other.cashierDeskNum != null)
				return false;
		} else if (!cashierDeskNum.equals(other.cashierDeskNum))
			return false;
		if (cashierNum == null) {
			if (other.cashierNum != null)
				return false;
		} else if (!cashierNum.equals(other.cashierNum))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (countryCode == null) {
			if (other.countryCode != null)
				return false;
		} else if (!countryCode.equals(other.countryCode))
			return false;
		if (depart == null) {
			if (other.depart != null)
				return false;
		} else if (!depart.equals(other.depart))
			return false;
		if (empNum == null) {
			if (other.empNum != null)
				return false;
		} else if (!empNum.equals(other.empNum))
			return false;
		if (finEmail == null) {
			if (other.finEmail != null)
				return false;
		} else if (!finEmail.equals(other.finEmail))
			return false;
		if (finFax == null) {
			if (other.finFax != null)
				return false;
		} else if (!finFax.equals(other.finFax))
			return false;
		if (finManager == null) {
			if (other.finManager != null)
				return false;
		} else if (!finManager.equals(other.finManager))
			return false;
		if (finPhone == null) {
			if (other.finPhone != null)
				return false;
		} else if (!finPhone.equals(other.finPhone))
			return false;
		if (finTel == null) {
			if (other.finTel != null)
				return false;
		} else if (!finTel.equals(other.finTel))
			return false;
		if (hasInnerPosExp == null) {
			if (other.hasInnerPosExp != null)
				return false;
		} else if (!hasInnerPosExp.equals(other.hasInnerPosExp))
			return false;
		if (isAppOutSide == null) {
			if (other.isAppOutSide != null)
				return false;
		} else if (!isAppOutSide.equals(other.isAppOutSide))
			return false;
		if (isMoreAcq == null) {
			if (other.isMoreAcq != null)
				return false;
		} else if (!isMoreAcq.equals(other.isMoreAcq))
			return false;
		if (isWineshop == null) {
			if (other.isWineshop != null)
				return false;
		} else if (!isWineshop.equals(other.isWineshop))
			return false;
		if (linkTel == null) {
			if (other.linkTel != null)
				return false;
		} else if (!linkTel.equals(other.linkTel))
			return false;
		if (mcc2 == null) {
			if (other.mcc2 != null)
				return false;
		} else if (!mcc2.equals(other.mcc2))
			return false;
		if (mchtNo == null) {
			if (other.mchtNo != null)
				return false;
		} else if (!mchtNo.equals(other.mchtNo))
			return false;
		if (mchtNoOld == null) {
			if (other.mchtNoOld != null)
				return false;
		} else if (!mchtNoOld.equals(other.mchtNoOld))
			return false;
		if (mchtTypeCause == null) {
			if (other.mchtTypeCause != null)
				return false;
		} else if (!mchtTypeCause.equals(other.mchtTypeCause))
			return false;
		if (openDate == null) {
			if (other.openDate != null)
				return false;
		} else if (!openDate.equals(other.openDate))
			return false;
		if (province == null) {
			if (other.province != null)
				return false;
		} else if (!province.equals(other.province))
			return false;
		if (proxy == null) {
			if (other.proxy != null)
				return false;
		} else if (!proxy.equals(other.proxy))
			return false;
		if (proxyTel == null) {
			if (other.proxyTel != null)
				return false;
		} else if (!proxyTel.equals(other.proxyTel))
			return false;
		if (referrer == null) {
			if (other.referrer != null)
				return false;
		} else if (!referrer.equals(other.referrer))
			return false;
		if (serType == null) {
			if (other.serType != null)
				return false;
		} else if (!serType.equals(other.serType))
			return false;
		if (shopNum == null) {
			if (other.shopNum != null)
				return false;
		} else if (!shopNum.equals(other.shopNum))
			return false;
		if (src == null) {
			if (other.src != null)
				return false;
		} else if (!src.equals(other.src))
			return false;
		if (turnover == null) {
			if (other.turnover != null)
				return false;
		} else if (!turnover.equals(other.turnover))
			return false;
		if (turnoverBefore == null) {
			if (other.turnoverBefore != null)
				return false;
		} else if (!turnoverBefore.equals(other.turnoverBefore))
			return false;
		if (wineshopLvl == null) {
			if (other.wineshopLvl != null)
				return false;
		} else if (!wineshopLvl.equals(other.wineshopLvl))
			return false;
		return true;
	} 
	 
	 
	/***********************************************************************************************************/
	//添加字段BY张骏恺 20140730
	private java.lang.String preAuthor;
	private java.lang.String returnFunc;



	public java.lang.String getPreAuthor() {
		return preAuthor;
	}
	public void setPreAuthor(java.lang.String preAuthor) {
		this.preAuthor = preAuthor;
	}
	public java.lang.String getReturnFunc() {
		return returnFunc;
	}
	public void setReturnFunc(java.lang.String returnFunc) {
		this.returnFunc = returnFunc;
	}
	
	
	
	
	
	
	
	
}

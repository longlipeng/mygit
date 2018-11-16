package com.huateng.struts.pos.action;

import com.huateng.bo.term.T3010BO;
import com.huateng.common.Constants;
import com.huateng.po.TblTermInfTmp;
import com.huateng.struts.pos.TblTermInfConstants;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;

public class T3010102Action extends BaseAction {

	private static final long serialVersionUID = 8817290734735660318L;
	private T3010BO t3010BO;
	private String provinceUpd;
	private String cityUpd;
	private String areaUpd;
	private String cityCodeUpd;
	private String termIdUpd;
	private String mchnNoUpd;
	private String brhIdUpd;
	private String termSignStaUpd;
	private String termIdIdUpd;
	private String termFactoryUpd;
	private String termMachTpUpd;
	private String termMccUpd;
	private String termVerUpd;
	private String termTpUpd;
	private String paramDownSignUpd;
	private String icDownSignUpd;
 	private String contTelUpd;
 	private String termPara2Upd;	//保存联系人
	private String propTpUpd;
	private String termPara1Upd;
	private String propInsNmUpd;
 	private String termBatchNmUpd;
	private String termStlmDtUpd;
	private String termParaUpd;
	private String connectModeUpd;
	private String equipInvIdUpd;
	private String equipInvNmUpd;
	private String bindTel1Upd;
	private String bindTel2Upd;
	private String bindTel3Upd;
	private String termAddrUpd;
	private String termPlaceUpd;
	private String oprNmUpd;
	private String reserveFlag1Upd;
	private String keyDownSignUpd;
	private String param1Upd;
	private String param2Upd;
	private String param3Upd;
	private String param4Upd;
	private String param5Upd;
	private String param6Upd;
	private String param7Upd;
	private String param8Upd;
	private String param9Upd;
	private String param10Upd;
	private String param11Upd;
	private String param12Upd;
	private String param13Upd;
	private String param14Upd;
	private String param15Upd;
	private String param16Upd;
	private String param17Upd;
	private String param18Upd;
	private String param19Upd;
	private String param20Upd;
	private String param21Upd;
	private String param22Upd;
	private String param23Upd;
	private String param24Upd;
	private String param25Upd;
	private String param26Upd;
	private String param27Upd;
	private String param28Upd;
	private String param29Upd;
	private String param30Upd;
	private String param31Upd;
	private String param32Upd;
	private String txn22Upd;
	private String txn27Upd;
	private String financeCard1Upd;
	private String financeCard2Upd;
	private String financeCard3Upd;
	private String txn14Upd;
	private String txn15Upd;
	private String txn16Upd;
	private String txn35Upd;
	private String txn36Upd;
	private String txn37Upd;
	private String txn38Upd;
	private String txn39Upd;
	private String txn40Upd;
	private String recCrtTs;

	private String termFactoryU; 
	private String runMainId1U;
	 
	private String othSvrIdU;
	private String runMainId2U;
	private String misc3U;
	private String misc2U;
	private String misc1U;
	
	private String productCdUpd;//中行终端主密钥
	//终端参数
	private String txn11NewU;
	private String txn12NewU;
	private String txn13NewU;
	private String txn20NewU;
	private String txn21NewU;
	private String txn23NewU;
	private String txn25New1U;
	private String txn25New2U;
	private String txn25New3U;
	private String txn25New4U;
	private String txn25New5U;
	
	//20140815添加begin
	private String txn25New6U;
	private String txn25New7U;
	private String txn25New8U;
	//20140815添加end
	
	private String txn27NewU;
	private String txn28NewU;
	private String txn29NewU;
	private String txn30NewU;
	
	private String txn17NewU;
	private String txn18NewU;
	private String txn19NewU;

	
	
	public String getTxn25New6U() {
		return txn25New6U;
	}

	public void setTxn25New6U(String txn25New6U) {
		this.txn25New6U = txn25New6U;
	}

	public String getTxn25New7U() {
		return txn25New7U;
	}

	public void setTxn25New7U(String txn25New7U) {
		this.txn25New7U = txn25New7U;
	}

	public String getTxn25New8U() {
		return txn25New8U;
	}

	public void setTxn25New8U(String txn25New8U) {
		this.txn25New8U = txn25New8U;
	}

	/**
	 * @return the productCdUpd
	 */
	public String getProductCdUpd() {
		return productCdUpd;
	}

	/**
	 * @param productCdUpd the productCdUpd to set
	 */
	public void setProductCdUpd(String productCdUpd) {
		this.productCdUpd = productCdUpd;
	}

	public String getProvinceUpd() {
		return provinceUpd;
	}

	public void setProvinceUpd(String provinceUpd) {
		this.provinceUpd = provinceUpd;
	}

	public String getCityUpd() {
		return cityUpd;
	}

	public void setCityUpd(String cityUpd) {
		this.cityUpd = cityUpd;
	}

	public String getAreaUpd() {
		return areaUpd;
	}

	public void setAreaUpd(String areaUpd) {
		this.areaUpd = areaUpd;
	}

	public String getCityCodeUpd() {
		return cityCodeUpd;
	}

	public void setCityCodeUpd(String cityCodeUpd) {
		this.cityCodeUpd = cityCodeUpd;
	}

	public String getTermFactoryU() {
		return termFactoryU;
	}

	public void setTermFactoryU(String termFactoryU) {
		this.termFactoryU = termFactoryU;
	}

	public String getRunMainId1U() {
		return runMainId1U;
	}

	public void setRunMainId1U(String runMainId1U) {
		this.runMainId1U = runMainId1U;
	}

	public String getOthSvrIdU() {
		return othSvrIdU;
	}

	public void setOthSvrIdU(String othSvrIdU) {
		this.othSvrIdU = othSvrIdU;
	}

	public String getRunMainId2U() {
		return runMainId2U;
	}

	public void setRunMainId2U(String runMainId2U) {
		this.runMainId2U = runMainId2U;
	}

	public String getMisc3U() {
		return misc3U;
	}

	public void setMisc3U(String misc3u) {
		misc3U = misc3u;
	}

	public String getMisc2U() {
		return misc2U;
	}

	public void setMisc2U(String misc2u) {
		misc2U = misc2u;
	}

	public String getMisc1U() {
		return misc1U;
	}

	public void setMisc1U(String misc1u) {
		misc1U = misc1u;
	}

	public String getTxn11NewU() {
		return txn11NewU;
	}

	public void setTxn11NewU(String txn11NewU) {
		this.txn11NewU = txn11NewU;
	}

	public String getTxn12NewU() {
		return txn12NewU;
	}

	public void setTxn12NewU(String txn12NewU) {
		this.txn12NewU = txn12NewU;
	}

	public String getTxn13NewU() {
		return txn13NewU;
	}

	public void setTxn13NewU(String txn13NewU) {
		this.txn13NewU = txn13NewU;
	}

	public String getTxn20NewU() {
		return txn20NewU;
	}

	public void setTxn20NewU(String txn20NewU) {
		this.txn20NewU = txn20NewU;
	}

	public String getTxn21NewU() {
		return txn21NewU;
	}

	public void setTxn21NewU(String txn21NewU) {
		this.txn21NewU = txn21NewU;
	}

	public String getTxn23NewU() {
		return txn23NewU;
	}

	public void setTxn23NewU(String txn23NewU) {
		this.txn23NewU = txn23NewU;
	}

	public String getTxn25New1U() {
		return txn25New1U;
	}

	public void setTxn25New1U(String txn25New1U) {
		this.txn25New1U = txn25New1U;
	}

	public String getTxn25New2U() {
		return txn25New2U;
	}

	public void setTxn25New2U(String txn25New2U) {
		this.txn25New2U = txn25New2U;
	}

	public String getTxn25New3U() {
		return txn25New3U;
	}

	public void setTxn25New3U(String txn25New3U) {
		this.txn25New3U = txn25New3U;
	}

	public String getTxn25New4U() {
		return txn25New4U;
	}

	public void setTxn25New4U(String txn25New4U) {
		this.txn25New4U = txn25New4U;
	}

	public String getTxn25New5U() {
		return txn25New5U;
	}

	public void setTxn25New5U(String txn25New5U) {
		this.txn25New5U = txn25New5U;
	}

	public String getTxn27NewU() {
		return txn27NewU;
	}

	public void setTxn27NewU(String txn27NewU) {
		this.txn27NewU = txn27NewU;
	}

	public String getTxn28NewU() {
		return txn28NewU;
	}

	public void setTxn28NewU(String txn28NewU) {
		this.txn28NewU = txn28NewU;
	}

	public String getTxn29NewU() {
		return txn29NewU;
	}

	public void setTxn29NewU(String txn29NewU) {
		this.txn29NewU = txn29NewU;
	}

	public String getTxn30NewU() {
		return txn30NewU;
	}

	public void setTxn30NewU(String txn30NewU) {
		this.txn30NewU = txn30NewU;
	}

	public String getTxn17NewU() {
		return txn17NewU;
	}

	public void setTxn17NewU(String txn17NewU) {
		this.txn17NewU = txn17NewU;
	}

	public String getTxn18NewU() {
		return txn18NewU;
	}

	public void setTxn18NewU(String txn18NewU) {
		this.txn18NewU = txn18NewU;
	}

	public String getTxn19NewU() {
		return txn19NewU;
	}

	public void setTxn19NewU(String txn19NewU) {
		this.txn19NewU = txn19NewU;
	}

	public T3010BO getT3010BO() {
		return t3010BO;
	}

	/**
	 * @return the termIdUpd
	 */
	public String getTermIdUpd() {
		return termIdUpd;
	}

	/**
	 * @param termIdUpd the termIdUpd to set
	 */
	public void setTermIdUpd(String termIdUpd) {
		this.termIdUpd = termIdUpd;
	}

	/**
	 * @return the mchnNoUpd
	 */
	public String getMchnNoUpd() {
		return mchnNoUpd;
	}

	/**
	 * @param mchnNoUpd the mchnNoUpd to set
	 */
	public void setMchnNoUpd(String mchnNoUpd) {
		this.mchnNoUpd = mchnNoUpd;
	}

	/**
	 * @return the brhIdUpd
	 */
	public String getBrhIdUpd() {
		return brhIdUpd;
	}

	/**
	 * @param brhIdUpd the brhIdUpd to set
	 */
	public void setBrhIdUpd(String brhIdUpd) {
		this.brhIdUpd = brhIdUpd;
	}

	/**
	 * @return the termSignStaUpd
	 */
	public String getTermSignStaUpd() {
		return termSignStaUpd;
	}

	/**
	 * @param termSignStaUpd the termSignStaUpd to set
	 */
	public void setTermSignStaUpd(String termSignStaUpd) {
		this.termSignStaUpd = termSignStaUpd;
	}

	/**
	 * @return the termIdIdUpd
	 */
	public String getTermIdIdUpd() {
		return termIdIdUpd;
	}

	/**
	 * @param termIdIdUpd the termIdIdUpd to set
	 */
	public void setTermIdIdUpd(String termIdIdUpd) {
		this.termIdIdUpd = termIdIdUpd;
	}

	/**
	 * @return the termFactoryUpd
	 */
	public String getTermFactoryUpd() {
		return termFactoryUpd;
	}

	/**
	 * @param termFactoryUpd the termFactoryUpd to set
	 */
	public void setTermFactoryUpd(String termFactoryUpd) {
		this.termFactoryUpd = termFactoryUpd;
	}

	/**
	 * @return the termMachTpUpd
	 */
	public String getTermMachTpUpd() {
		return termMachTpUpd;
	}

	/**
	 * @param termMachTpUpd the termMachTpUpd to set
	 */
	public void setTermMachTpUpd(String termMachTpUpd) {
		this.termMachTpUpd = termMachTpUpd;
	}

	/**
	 * @return the termMccUpd
	 */
	public String getTermMccUpd() {
		return termMccUpd;
	}

	/**
	 * @param termMccUpd the termMccUpd to set
	 */
	public void setTermMccUpd(String termMccUpd) {
		this.termMccUpd = termMccUpd;
	}

	/**
	 * @return the termVerUpd
	 */
	public String getTermVerUpd() {
		return termVerUpd;
	}

	/**
	 * @param termVerUpd the termVerUpd to set
	 */
	public void setTermVerUpd(String termVerUpd) {
		this.termVerUpd = termVerUpd;
	}

	/**
	 * @return the termTpUpd
	 */
	public String getTermTpUpd() {
		return termTpUpd;
	}

	/**
	 * @param termTpUpd the termTpUpd to set
	 */
	public void setTermTpUpd(String termTpUpd) {
		this.termTpUpd = termTpUpd;
	}

	/**
	 * @return the paramDownSignUpd
	 */
	public String getParamDownSignUpd() {
		return paramDownSignUpd;
	}

	/**
	 * @param paramDownSignUpd the paramDownSignUpd to set
	 */
	public void setParamDownSignUpd(String paramDownSignUpd) {
		this.paramDownSignUpd = paramDownSignUpd;
	}

	/**
	 * @return the icDownSignUpd
	 */
	public String getIcDownSignUpd() {
		return icDownSignUpd;
	}

	/**
	 * @param icDownSignUpd the icDownSignUpd to set
	 */
	public void setIcDownSignUpd(String icDownSignUpd) {
		this.icDownSignUpd = icDownSignUpd;
	}

	/**
	 * @return the contTelUpd
	 */
	public String getContTelUpd() {
		return contTelUpd;
	}

	/**
	 * @param contTelUpd the contTelUpd to set
	 */
	public void setContTelUpd(String contTelUpd) {
		this.contTelUpd = contTelUpd;
	}


	public String getTermPara2Upd() {
		return termPara2Upd;
	}

	public void setTermPara2Upd(String termPara2Upd) {
		this.termPara2Upd = termPara2Upd;
	}

	/**
	 * @return the propTpUpd
	 */
	public String getPropTpUpd() {
		return propTpUpd;
	}

	/**
	 * @param propTpUpd the propTpUpd to set
	 */
	public void setPropTpUpd(String propTpUpd) {
		this.propTpUpd = propTpUpd;
	}

	/**
	 * @return the propInsNmUpd
	 */
	public String getPropInsNmUpd() {
		return propInsNmUpd;
	}

	/**
	 * @param propInsNmUpd the propInsNmUpd to set
	 */
	public void setPropInsNmUpd(String propInsNmUpd) {
		this.propInsNmUpd = propInsNmUpd;
	}

	/**
	 * @return the reserveFlag1Upd
	 */
	public String getTermPara1Upd() {
		return termPara1Upd;
	}

	/**
	 * @param reserveFlag1Upd the reserveFlag1Upd to set
	 */
	public void setTermPara1Upd(String termPara1Upd) {
		this.termPara1Upd = termPara1Upd;
	}

	/**
	 * @return the termBatchNmUpd
	 */
	public String getTermBatchNmUpd() {
		return termBatchNmUpd;
	}

	/**
	 * @param termBatchNmUpd the termBatchNmUpd to set
	 */
	public void setTermBatchNmUpd(String termBatchNmUpd) {
		this.termBatchNmUpd = termBatchNmUpd;
	}

	/**
	 * @return the termStlmDtUpd
	 */
	public String getTermStlmDtUpd() {
		return termStlmDtUpd;
	}

	/**
	 * @param termStlmDtUpd the termStlmDtUpd to set
	 */
	public void setTermStlmDtUpd(String termStlmDtUpd) {
		this.termStlmDtUpd = termStlmDtUpd;
	}

	/**
	 * @return the connectModeUpd
	 */
	public String getConnectModeUpd() {
		return connectModeUpd;
	}

	/**
	 * @param connectModeUpd the connectModeUpd to set
	 */
	public void setConnectModeUpd(String connectModeUpd) {
		this.connectModeUpd = connectModeUpd;
	}

	/**
	 * @return the equipInvIdUpd
	 */
	public String getEquipInvIdUpd() {
		return equipInvIdUpd;
	}

	/**
	 * @param equipInvIdUpd the equipInvIdUpd to set
	 */
	public void setEquipInvIdUpd(String equipInvIdUpd) {
		this.equipInvIdUpd = equipInvIdUpd;
	}

	/**
	 * @return the equipInvNmUpd
	 */
	public String getEquipInvNmUpd() {
		return equipInvNmUpd;
	}

	/**
	 * @param equipInvNmUpd the equipInvNmUpd to set
	 */
	public void setEquipInvNmUpd(String equipInvNmUpd) {
		this.equipInvNmUpd = equipInvNmUpd;
	}

	/**
	 * @return the bindTel1Upd
	 */
	public String getBindTel1Upd() {
		return bindTel1Upd;
	}

	/**
	 * @param bindTel1Upd the bindTel1Upd to set
	 */
	public void setBindTel1Upd(String bindTel1Upd) {
		this.bindTel1Upd = bindTel1Upd;
	}

	/**
	 * @return the bindTel2Upd
	 */
	public String getBindTel2Upd() {
		return bindTel2Upd;
	}

	/**
	 * @param bindTel2Upd the bindTel2Upd to set
	 */
	public void setBindTel2Upd(String bindTel2Upd) {
		this.bindTel2Upd = bindTel2Upd;
	}

	/**
	 * @return the bindTel3Upd
	 */
	public String getBindTel3Upd() {
		return bindTel3Upd;
	}

	/**
	 * @param bindTel3Upd the bindTel3Upd to set
	 */
	public void setBindTel3Upd(String bindTel3Upd) {
		this.bindTel3Upd = bindTel3Upd;
	}

	/**
	 * @return the termAddrUpd
	 */
	public String getTermAddrUpd() {
		return termAddrUpd;
	}

	/**
	 * @param termAddrUpd the termAddrUpd to set
	 */
	public void setTermAddrUpd(String termAddrUpd) {
		this.termAddrUpd = termAddrUpd;
	}

	/**
	 * @return the termPlaceUpd
	 */
	public String getTermPlaceUpd() {
		return termPlaceUpd;
	}

	/**
	 * @param termPlaceUpd the termPlaceUpd to set
	 */
	public void setTermPlaceUpd(String termPlaceUpd) {
		this.termPlaceUpd = termPlaceUpd;
	}

	/**
	 * @return the oprNmUpd
	 */
	public String getOprNmUpd() {
		return oprNmUpd;
	}

	/**
	 * @param oprNmUpd the oprNmUpd to set
	 */
	public void setOprNmUpd(String oprNmUpd) {
		this.oprNmUpd = oprNmUpd;
	}

	/**
	 * @return the keyDownSignUpd
	 */
	public String getKeyDownSignUpd() {
		return keyDownSignUpd;
	}

	/**
	 * @param keyDownSignUpd the keyDownSignUpd to set
	 */
	public void setKeyDownSignUpd(String keyDownSignUpd) {
		this.keyDownSignUpd = keyDownSignUpd;
	}

	/**
	 * @return the param1Upd
	 */
	public String getParam1Upd() {
		return param1Upd;
	}

	/**
	 * @param param1Upd the param1Upd to set
	 */
	public void setParam1Upd(String param1Upd) {
		this.param1Upd = param1Upd;
	}

	/**
	 * @return the param2Upd
	 */
	public String getParam2Upd() {
		return param2Upd;
	}

	/**
	 * @param param2Upd the param2Upd to set
	 */
	public void setParam2Upd(String param2Upd) {
		this.param2Upd = param2Upd;
	}

	/**
	 * @return the param3Upd
	 */
	public String getParam3Upd() {
		return param3Upd;
	}

	/**
	 * @param param3Upd the param3Upd to set
	 */
	public void setParam3Upd(String param3Upd) {
		this.param3Upd = param3Upd;
	}

	/**
	 * @return the param4Upd
	 */
	public String getParam4Upd() {
		return param4Upd;
	}

	/**
	 * @param param4Upd the param4Upd to set
	 */
	public void setParam4Upd(String param4Upd) {
		this.param4Upd = param4Upd;
	}

	/**
	 * @return the param5Upd
	 */
	public String getParam5Upd() {
		return param5Upd;
	}

	/**
	 * @param param5Upd the param5Upd to set
	 */
	public void setParam5Upd(String param5Upd) {
		this.param5Upd = param5Upd;
	}

	/**
	 * @return the param6Upd
	 */
	public String getParam6Upd() {
		return param6Upd;
	}

	/**
	 * @param param6Upd the param6Upd to set
	 */
	public void setParam6Upd(String param6Upd) {
		this.param6Upd = param6Upd;
	}

	/**
	 * @return the param7Upd
	 */
	public String getParam7Upd() {
		return param7Upd;
	}

	/**
	 * @param param7Upd the param7Upd to set
	 */
	public void setParam7Upd(String param7Upd) {
		this.param7Upd = param7Upd;
	}

	/**
	 * @return the param8Upd
	 */
	public String getParam8Upd() {
		return param8Upd;
	}

	/**
	 * @param param8Upd the param8Upd to set
	 */
	public void setParam8Upd(String param8Upd) {
		this.param8Upd = param8Upd;
	}

	/**
	 * @return the param9Upd
	 */
	public String getParam9Upd() {
		return param9Upd;
	}

	/**
	 * @param param9Upd the param9Upd to set
	 */
	public void setParam9Upd(String param9Upd) {
		this.param9Upd = param9Upd;
	}

	/**
	 * @return the param10Upd
	 */
	public String getParam10Upd() {
		return param10Upd;
	}

	/**
	 * @param param10Upd the param10Upd to set
	 */
	public void setParam10Upd(String param10Upd) {
		this.param10Upd = param10Upd;
	}

	/**
	 * @return the param11Upd
	 */
	public String getParam11Upd() {
		return param11Upd;
	}

	/**
	 * @param param11Upd the param11Upd to set
	 */
	public void setParam11Upd(String param11Upd) {
		this.param11Upd = param11Upd;
	}

	/**
	 * @return the param12Upd
	 */
	public String getParam12Upd() {
		return param12Upd;
	}

	/**
	 * @param param12Upd the param12Upd to set
	 */
	public void setParam12Upd(String param12Upd) {
		this.param12Upd = param12Upd;
	}

	/**
	 * @return the param13Upd
	 */
	public String getParam13Upd() {
		return param13Upd;
	}

	/**
	 * @param param13Upd the param13Upd to set
	 */
	public void setParam13Upd(String param13Upd) {
		this.param13Upd = param13Upd;
	}

	/**
	 * @return the param14Upd
	 */
	public String getParam14Upd() {
		return param14Upd;
	}

	/**
	 * @param param14Upd the param14Upd to set
	 */
	public void setParam14Upd(String param14Upd) {
		this.param14Upd = param14Upd;
	}

	/**
	 * @return the param15Upd
	 */
	public String getParam15Upd() {
		return param15Upd;
	}

	/**
	 * @param param15Upd the param15Upd to set
	 */
	public void setParam15Upd(String param15Upd) {
		this.param15Upd = param15Upd;
	}

	/**
	 * @return the param16Upd
	 */
	public String getParam16Upd() {
		return param16Upd;
	}

	/**
	 * @param param16Upd the param16Upd to set
	 */
	public void setParam16Upd(String param16Upd) {
		this.param16Upd = param16Upd;
	}

	/**
	 * @return the param17Upd
	 */
	public String getParam17Upd() {
		return param17Upd;
	}

	/**
	 * @param param17Upd the param17Upd to set
	 */
	public void setParam17Upd(String param17Upd) {
		this.param17Upd = param17Upd;
	}

	/**
	 * @return the param18Upd
	 */
	public String getParam18Upd() {
		return param18Upd;
	}

	/**
	 * @param param18Upd the param18Upd to set
	 */
	public void setParam18Upd(String param18Upd) {
		this.param18Upd = param18Upd;
	}

	/**
	 * @return the param19Upd
	 */
	public String getParam19Upd() {
		return param19Upd;
	}

	/**
	 * @param param19Upd the param19Upd to set
	 */
	public void setParam19Upd(String param19Upd) {
		this.param19Upd = param19Upd;
	}

	/**
	 * @return the param20Upd
	 */
	public String getParam20Upd() {
		return param20Upd;
	}

	/**
	 * @param param20Upd the param20Upd to set
	 */
	public void setParam20Upd(String param20Upd) {
		this.param20Upd = param20Upd;
	}

	/**
	 * @return the param21Upd
	 */
	public String getParam21Upd() {
		return param21Upd;
	}

	/**
	 * @param param21Upd the param21Upd to set
	 */
	public void setParam21Upd(String param21Upd) {
		this.param21Upd = param21Upd;
	}

	/**
	 * @return the param22Upd
	 */
	public String getParam22Upd() {
		return param22Upd;
	}

	/**
	 * @param param22Upd the param22Upd to set
	 */
	public void setParam22Upd(String param22Upd) {
		this.param22Upd = param22Upd;
	}

	/**
	 * @return the param23Upd
	 */
	public String getParam23Upd() {
		return param23Upd;
	}

	/**
	 * @param param23Upd the param23Upd to set
	 */
	public void setParam23Upd(String param23Upd) {
		this.param23Upd = param23Upd;
	}

	/**
	 * @return the param24Upd
	 */
	public String getParam24Upd() {
		return param24Upd;
	}

	/**
	 * @param param24Upd the param24Upd to set
	 */
	public void setParam24Upd(String param24Upd) {
		this.param24Upd = param24Upd;
	}

	/**
	 * @return the param25Upd
	 */
	public String getParam25Upd() {
		return param25Upd;
	}

	/**
	 * @param param25Upd the param25Upd to set
	 */
	public void setParam25Upd(String param25Upd) {
		this.param25Upd = param25Upd;
	}

	/**
	 * @return the param26Upd
	 */
	public String getParam26Upd() {
		return param26Upd;
	}

	/**
	 * @param param26Upd the param26Upd to set
	 */
	public void setParam26Upd(String param26Upd) {
		this.param26Upd = param26Upd;
	}

	/**
	 * @return the param27Upd
	 */
	public String getParam27Upd() {
		return param27Upd;
	}

	/**
	 * @param param27Upd the param27Upd to set
	 */
	public void setParam27Upd(String param27Upd) {
		this.param27Upd = param27Upd;
	}

	/**
	 * @return the param28Upd
	 */
	public String getParam28Upd() {
		return param28Upd;
	}

	/**
	 * @param param28Upd the param28Upd to set
	 */
	public void setParam28Upd(String param28Upd) {
		this.param28Upd = param28Upd;
	}

	/**
	 * @return the param29Upd
	 */
	public String getParam29Upd() {
		return param29Upd;
	}

	/**
	 * @param param29Upd the param29Upd to set
	 */
	public void setParam29Upd(String param29Upd) {
		this.param29Upd = param29Upd;
	}

	/**
	 * @return the param30Upd
	 */
	public String getParam30Upd() {
		return param30Upd;
	}

	/**
	 * @param param30Upd the param30Upd to set
	 */
	public void setParam30Upd(String param30Upd) {
		this.param30Upd = param30Upd;
	}

	/**
	 * @return the param31Upd
	 */
	public String getParam31Upd() {
		return param31Upd;
	}

	/**
	 * @param param31Upd the param31Upd to set
	 */
	public void setParam31Upd(String param31Upd) {
		this.param31Upd = param31Upd;
	}

	/**
	 * @return the param32Upd
	 */
	public String getParam32Upd() {
		return param32Upd;
	}

	/**
	 * @param param32Upd the param32Upd to set
	 */
	public void setParam32Upd(String param32Upd) {
		this.param32Upd = param32Upd;
	}

	/**
	 * @return the txn22Upd
	 */
	public String getTxn22Upd() {
		return txn22Upd;
	}

	/**
	 * @param txn22Upd the txn22Upd to set
	 */
	public void setTxn22Upd(String txn22Upd) {
		this.txn22Upd = txn22Upd;
	}

	/**
	 * @return the txn27Upd
	 */
	public String getTxn27Upd() {
		return txn27Upd;
	}

	/**
	 * @param txn27Upd the txn27Upd to set
	 */
	public void setTxn27Upd(String txn27Upd) {
		this.txn27Upd = txn27Upd;
	}

	/**
	 * @return the financeCard1Upd
	 */
	public String getFinanceCard1Upd() {
		return financeCard1Upd;
	}

	/**
	 * @param financeCard1Upd the financeCard1Upd to set
	 */
	public void setFinanceCard1Upd(String financeCard1Upd) {
		this.financeCard1Upd = financeCard1Upd;
	}

	/**
	 * @return the financeCard2Upd
	 */
	public String getFinanceCard2Upd() {
		return financeCard2Upd;
	}

	/**
	 * @param financeCard2Upd the financeCard2Upd to set
	 */
	public void setFinanceCard2Upd(String financeCard2Upd) {
		this.financeCard2Upd = financeCard2Upd;
	}

	/**
	 * @return the financeCard3Upd
	 */
	public String getFinanceCard3Upd() {
		return financeCard3Upd;
	}

	/**
	 * @param financeCard3Upd the financeCard3Upd to set
	 */
	public void setFinanceCard3Upd(String financeCard3Upd) {
		this.financeCard3Upd = financeCard3Upd;
	}

	
	/**
	 * @return the txn14Upd
	 */
	public String getTxn14Upd() {
		return txn14Upd;
	}

	/**
	 * @param txn14Upd the txn14Upd to set
	 */
	public void setTxn14Upd(String txn14Upd) {
		this.txn14Upd = txn14Upd;
	}

	/**
	 * @return the txn15Upd
	 */
	public String getTxn15Upd() {
		return txn15Upd;
	}

	/**
	 * @param txn15Upd the txn15Upd to set
	 */
	public void setTxn15Upd(String txn15Upd) {
		this.txn15Upd = txn15Upd;
	}

	/**
	 * @return the txn16Upd
	 */
	public String getTxn16Upd() {
		return txn16Upd;
	}

	/**
	 * @param txn16Upd the txn16Upd to set
	 */
	public void setTxn16Upd(String txn16Upd) {
		this.txn16Upd = txn16Upd;
	}

	/**
	 * @return the txn35Upd
	 */
	public String getTxn35Upd() {
		return txn35Upd;
	}

	/**
	 * @param txn35Upd the txn35Upd to set
	 */
	public void setTxn35Upd(String txn35Upd) {
		this.txn35Upd = txn35Upd;
	}

	/**
	 * @return the txn36Upd
	 */
	public String getTxn36Upd() {
		return txn36Upd;
	}

	/**
	 * @param txn36Upd the txn36Upd to set
	 */
	public void setTxn36Upd(String txn36Upd) {
		this.txn36Upd = txn36Upd;
	}

	/**
	 * @return the txn37Upd
	 */
	public String getTxn37Upd() {
		return txn37Upd;
	}

	/**
	 * @param txn37Upd the txn37Upd to set
	 */
	public void setTxn37Upd(String txn37Upd) {
		this.txn37Upd = txn37Upd;
	}

	/**
	 * @return the txn38Upd
	 */
	public String getTxn38Upd() {
		return txn38Upd;
	}

	/**
	 * @param txn38Upd the txn38Upd to set
	 */
	public void setTxn38Upd(String txn38Upd) {
		this.txn38Upd = txn38Upd;
	}

	/**
	 * @return the txn39Upd
	 */
	public String getTxn39Upd() {
		return txn39Upd;
	}

	/**
	 * @param txn39Upd the txn39Upd to set
	 */
	public void setTxn39Upd(String txn39Upd) {
		this.txn39Upd = txn39Upd;
	}

	/**
	 * @return the txn40Upd
	 */
	public String getTxn40Upd() {
		return txn40Upd;
	}

	/**
	 * @param txn40Upd the txn40Upd to set
	 */
	public void setTxn40Upd(String txn40Upd) {
		this.txn40Upd = txn40Upd;
	}

	/**
	 * @return the termParaUpd
	 */
	public String getTermParaUpd() {
		return termParaUpd;
	}

	/**
	 * @param termParaUpd the termParaUpd to set
	 */
	public void setTermParaUpd(String termParaUpd) {
		this.termParaUpd = termParaUpd;
	}

	/**
	 * @return the recCrtTs
	 */
	public String getRecCrtTs() {
		return recCrtTs;
	}

	/**
	 * @param recCrtTs the recCrtTs to set
	 */
	public void setRecCrtTs(String recCrtTs) {
		this.recCrtTs = recCrtTs;
	}

	public String getReserveFlag1Upd() {
		return reserveFlag1Upd;
	}

	public void setReserveFlag1Upd(String reserveFlag1Upd) {
		this.reserveFlag1Upd = reserveFlag1Upd;
	}

	/**
	 * @param t3010bo the t3010BO to set
	 */
	public void setT3010BO(T3010BO t3010bo) {
		t3010BO = t3010bo;
	}

	@Override
	protected String subExecute() throws Exception {
		TblTermInfTmp tblTermInf = t3010BO.get(CommonFunction.fillString(termIdUpd, ' ', 12, true), recCrtTs);
		if(tblTermInf == null)
			return TblTermInfConstants.T30101_03;
		
		//添加对商户状态的判断begin

		//添加对商户状态的判断end
		
		tblTermInf.setTermBranch(brhIdUpd);
		tblTermInf.setTermAddr(termAddrUpd);
		tblTermInf.setTermPlace(termPlaceUpd);
		tblTermInf.setMchtCd(mchnNoUpd);
		tblTermInf.setTermSignSta(termSignStaUpd);
		tblTermInf.setTermMcc(termMccUpd);
		tblTermInf.setTermTp(termTpUpd);
//		supportIC这个为默认项，必填不可见。IC卡参数下载标志由IcDownSign标志位控制
		tblTermInf.setKeyDownSign(isChecked(keyDownSignUpd));
		
//		System.out.println("paramDownSignUpd : " + paramDownSignUpd);
//		tblTermInf.setParamDownSign(isChecked(paramDownSignUpd));
		
//		tblTermInf.setSupportIc(isChecked(supportICUpd));
//		tblTermInf.setKeyDownSign("1");
		tblTermInf.setParamDownSign("1");
		tblTermInf.setIcDownSign(isChecked(icDownSignUpd));
		tblTermInf.setReserveFlag1(isChecked(reserveFlag1Upd));
		
		tblTermInf.setConnectMode(connectModeUpd);
		tblTermInf.setContTel(contTelUpd);
		tblTermInf.setTermPara2(termPara2Upd);
		tblTermInf.setOprNm(oprNmUpd);
		tblTermInf.setEquipInvId(equipInvIdUpd);
		tblTermInf.setEquipInvNm(equipInvNmUpd);
		tblTermInf.setPropTp(propTpUpd);
		tblTermInf.setPropInsNm(propInsNmUpd);
		tblTermInf.setTermPara1(termPara1Upd);
		tblTermInf.setBindTel1(bindTel1Upd);
		tblTermInf.setTermMachTp(termMachTpUpd);
		
		//修改安装维护员和安装维护机构
		tblTermInf.setMisc2(this.getMisc2U());
		tblTermInf.setMisc3(this.getMisc3U());
		//修改终端厂商
		tblTermInf.setTermFactory(this.getTermFactoryUpd());
		//修改运行维护代码
		tblTermInf.setRunMainId1(this.getRunMainId1U());
		tblTermInf.setRunMainId2(this.getRunMainId2U());
		tblTermInf.setOthSvrId(this.getOthSvrIdU());
		
		if(bindTel2Upd!=null)
			tblTermInf.setBindTel2(bindTel2Upd);
		else
			bindTel2Upd = " ";
		if(bindTel3Upd!=null)
			tblTermInf.setBindTel3(bindTel3Upd);
		else
			bindTel3Upd = " ";
		
		if(txn15Upd == null)
			txn15Upd = " ";
		if(txn16Upd == null)
			txn16Upd = " ";
		
		if(termTpUpd.equals("1")){
			tblTermInf.setFinanceCard1(financeCard1Upd);
			if(financeCard2Upd!=null)
				tblTermInf.setFinanceCard2(financeCard2Upd);
			else
				financeCard2Upd = " ";
			if(financeCard3Upd!=null)
				tblTermInf.setFinanceCard3(financeCard3Upd);
			else
				financeCard3Upd = " ";
		}else{
			financeCard1Upd = " ";
			financeCard2Upd = " ";
			financeCard3Upd = " ";
		}
//		tblTermInf.setRecUpdOpr(operator.getOprId());页面中审核人字段，非审核操作不更新此字段值20121018
		String termPara = parseTermPara(tblTermInf.getId().getTermId().trim());
		tblTermInf.setTermPara(termPara);
		

		//判断是否勾选交易
		int value = checkTxn(param1Upd)+checkTxn(param2Upd)+checkTxn(param3Upd)+checkTxn(param4Upd)
			+checkTxn(param5Upd)+checkTxn(param6Upd)+checkTxn(param7Upd)+checkTxn(param8Upd)
			/*+checkTxn(param9Upd)+checkTxn(param10Upd)+checkTxn(param11Upd)+checkTxn(param12Upd)
			+checkTxn(param13Upd)*/+checkTxn(param14Upd)+checkTxn(param15Upd)+checkTxn(param16Upd)
			+checkTxn(param17Upd)+checkTxn(param18Upd)+checkTxn(param19Upd)+checkTxn(param20Upd)
			+checkTxn(param21Upd)+checkTxn(param22Upd)+checkTxn(param23Upd)+checkTxn(param24Upd);
		if(value == 0)
			return TblTermInfConstants.T30101_02;
		//判断财务POS交易勾选
/*		int value0 = checkTxn(param1Upd)+checkTxn(param11Upd)+checkTxn(param12Upd)+checkTxn(param13Upd)
		+checkTxn(param14Upd)+checkTxn(param15Upd);
		if((value0 != 6||value > 6)&&termTpUpd.equals("1"))
			return TblTermInfConstants.T30101_04;
		
		//判断是否支持IC卡
		if(checkTxn(icDownSignUpd) != 1)
		{
			int value1 = checkTxn(param20Upd)+checkTxn(param21Upd)+checkTxn(param22Upd)
					+checkTxn(param23Upd)+checkTxn(param24Upd);
			if(value1 > 0)
				return TblTermInfConstants.T30101_05;
		}
		
		//判断是否电子现金是否勾选
		if(checkTxn(param20Upd) != 1)
		{
			int value2 = checkTxn(param21Upd)+checkTxn(param22Upd)
					+checkTxn(param23Upd)+checkTxn(param24Upd);
			if(value2 > 0)
				return TblTermInfConstants.T30101_06;
		}*/
		tblTermInf.setProvince(this.getProvinceUpd());
		tblTermInf.setCity(this.getCityUpd());
		tblTermInf.setArea(this.getAreaUpd());
		tblTermInf.setCityCode(this.getCityCodeUpd());
		
		tblTermInf.setProductCd(productCdUpd);
		
		if(tblTermInf.getTermSta().equals(TblTermInfConstants.TERM_STA_INIT)||tblTermInf.getTermSta().equals(TblTermInfConstants.TERM_STA_ADD_REFUSE)){
			tblTermInf.setRecCrtOpr(operator.getOprId());//新增未审核和新增拒绝状态的终端记录被修改，也应该更新修改人信息20121018
			tblTermInf.setRecDelTs(CommonFunction.getCurrentDateTime());
			t3010BO.update(tblTermInf, TblTermInfConstants.TERM_STA_INIT);
		}else{
			tblTermInf.setRecCrtOpr(operator.getOprId());
			tblTermInf.setRecDelTs(CommonFunction.getCurrentDateTime());
			t3010BO.update(tblTermInf, TblTermInfConstants.TERM_STA_MOD_UNCHK);
		}
		return Constants.SUCCESS_CODE_CUSTOMIZE+"终端信息修改成功,商户号：["+mchnNoUpd+"],终端号：["+termIdUpd.trim()+"]";
	}
	
	public String parseTermPara(String termId){
		StringBuffer result = new StringBuffer();
		result.append("11").append(CommonFunction.fillString(txn11NewU, ' ', 2, true))
			  .append("12").append(CommonFunction.fillString(txn12NewU, ' ', 2, true))
			  .append("13").append(CommonFunction.fillString(txn13NewU, ' ', 1, true))
			  .append("14").append(CommonFunction.fillStringByDB(txn14Upd.trim(), ' ', 14, true))
			  .append("15").append(CommonFunction.fillStringByDB(txn15Upd.trim(), ' ', 14, true))
			  .append("16").append(CommonFunction.fillStringByDB(txn16Upd.trim(), ' ', 14, true))
			  .append("17").append(CommonFunction.fillString(txn17NewU,' ',14,true))
			  .append("18").append(CommonFunction.fillString(txn18NewU, ' ', 1, true))
			  .append("19").append(CommonFunction.fillString(txn19NewU, ' ', 2, false))
		  	  .append("20").append(isChecked(txn20NewU))
			  .append("21").append(isChecked(txn21NewU))
			  .append("22").append(CommonFunction.fillStringByDB(txn22Upd.trim(), ' ', 40, true))
			  .append("23").append(CommonFunction.fillString(txn23NewU, '0', 1, true))
			  .append("24").append(" ")
			  .append("25");
		
		
		StringBuffer sb=new StringBuffer();
		sb.append(isChecked(txn25New1U)).append(isChecked(txn25New2U)).append(isChecked(txn25New3U)).append(isChecked(txn25New4U))
		   .append(isChecked(txn25New5U)).append(isChecked(txn25New6U))
		   .append(isChecked(txn25New7U)).append(isChecked(txn25New8U))/*.append("000")*/;
		result.append(CommonFunction.fillString(Integer.toHexString(Integer.valueOf(sb.toString(), 2)),'0',2,false));//将sb中保存的2进制值转换为16进制保存
		
		result.append("26");		
		StringBuffer txnCode = new StringBuffer();
		String txnCode1=isChecked(param1Upd)+isChecked(param2Upd)+isChecked(param3Upd)+isChecked(param4Upd);
		String txnCode2=isChecked(param5Upd)+isChecked(param6Upd)+isChecked(param7Upd)+isChecked(param8Upd);
		String txnCode3=isChecked(param9Upd)+isChecked(param10Upd)+isChecked(param11Upd)+isChecked(param12Upd);
		String txnCode4=isChecked(param13Upd)+isChecked(param14Upd)+isChecked(param15Upd)+isChecked(param16Upd);
		String txnCode5=isChecked(param17Upd)+isChecked(param18Upd)+isChecked(param19Upd)+isChecked(param20Upd);
		String txnCode6=isChecked(param21Upd)+isChecked(param22Upd)+isChecked(param23Upd)+isChecked(param24Upd);
		String txnCode7=isChecked(param25Upd)+isChecked(param26Upd)+isChecked(param27Upd)+isChecked(param28Upd);
		String txnCode8=isChecked(param29Upd)+isChecked(param30Upd)+isChecked(param31Upd)+isChecked(param32Upd);
		txnCode.append(Integer.toHexString(Integer.valueOf(txnCode1,2)))
		.append(Integer.toHexString(Integer.valueOf(txnCode2,2)))
		.append(Integer.toHexString(Integer.valueOf(txnCode3,2)))
		.append(Integer.toHexString(Integer.valueOf(txnCode4,2)))
		.append(Integer.toHexString(Integer.valueOf(txnCode5,2)))
		.append(Integer.toHexString(Integer.valueOf(txnCode6,2)))
		.append(Integer.toHexString(Integer.valueOf(txnCode7,2)))
		.append(Integer.toHexString(Integer.valueOf(txnCode8,2)));
		result.append(CommonFunction.fillString(txnCode.toString(),'0',8,false));
		
		result.append("27").append(CommonFunction.fillString(txn27NewU, ' ', 1, true))
			.append("28").append(isChecked(txn28NewU))
			.append("29").append(isChecked(txn29NewU))
			.append("30").append(CommonFunction.fillString(txn30NewU, ' ', 2, true));
			/*.append("31").append("000001")
			.append("32").append(CommonFunction.fillString(financeCard1New, ' ', 19, true))
			.append("33").append(CommonFunction.fillString(financeCard2New, ' ', 19, true))
			.append("34").append(CommonFunction.fillString(financeCard3New, ' ', 19, true))
			.append("35").append(CommonFunction.fillString(txn35New==null?" ":txn35New, ' ', 2, true))
			.append("36").append(txn36New==null?CommonFunction.fillString(txn36New, ' ', 12, true):translate(txn36New))
			.append("37").append(txn37New==null?CommonFunction.fillString(txn37New, ' ', 12, true):translate(txn37New))
			.append("38").append(txn38New==null?CommonFunction.fillString(txn38New, ' ', 12, true):translate(txn38New))
			.append("39").append(txn39New==null?CommonFunction.fillString(txn39New, ' ', 12, true):translate(txn39New))
			.append("40").append(CommonFunction.fillString(txn40New==null?" ":txn40New, ' ', 2, true));*/
		return result.toString();
	
		/*
		StringBuffer result = new StringBuffer();
		
		result.append("14").append(CommonFunction.fillStringByDB(txn14Upd.trim(), ' ', 14, true)).append("|")
			.append("15").append(CommonFunction.fillStringByDB(txn15Upd.trim(), ' ', 14, true)).append("|")
			.append("16").append(CommonFunction.fillStringByDB(txn16Upd.trim(), ' ', 14, true)).append("|")
			.append("22").append(CommonFunction.fillStringByDB(txn22Upd.trim(), ' ', 40, true)).append("|").append("26");
		
		StringBuffer txnCode = new StringBuffer();
		String txnCode1=isChecked(param1Upd)+isChecked(param2Upd)+isChecked(param3Upd)+isChecked(param4Upd);
		String txnCode2=isChecked(param5Upd)+isChecked(param6Upd)+isChecked(param7Upd)+isChecked(param8Upd);
		String txnCode3=isChecked(param9Upd)+isChecked(param10Upd)+isChecked(param11Upd)+isChecked(param12Upd);
		String txnCode4=isChecked(param13Upd)+isChecked(param14Upd)+isChecked(param15Upd)+isChecked(param16Upd);
		String txnCode5=isChecked(param17Upd)+isChecked(param18Upd)+isChecked(param19Upd)+isChecked(param20Upd);
		String txnCode6=isChecked(param21Upd)+isChecked(param22Upd)+isChecked(param23Upd)+isChecked(param24Upd);
		String txnCode7=isChecked(param25Upd)+isChecked(param26Upd)+isChecked(param27Upd)+isChecked(param28Upd);
		String txnCode8=isChecked(param29Upd)+isChecked(param30Upd)+isChecked(param31Upd)+isChecked(param32Upd);
		txnCode.append(Integer.toHexString(Integer.valueOf(txnCode1,2)))
		.append(Integer.toHexString(Integer.valueOf(txnCode2,2)))
		.append(Integer.toHexString(Integer.valueOf(txnCode3,2)))
		.append(Integer.toHexString(Integer.valueOf(txnCode4,2)))
		.append(Integer.toHexString(Integer.valueOf(txnCode5,2)))
		.append(Integer.toHexString(Integer.valueOf(txnCode6,2)))
		.append(Integer.toHexString(Integer.valueOf(txnCode7,2)))
		.append(Integer.toHexString(Integer.valueOf(txnCode8,2)));
		result.append(CommonFunction.fillString(txnCode.toString(), '0', 16, true));
		
		result.append("|").append("27").append(CommonFunction.fillString(txn27Upd==null?"":txn27Upd.trim(), ' ', 40, true)).append("|")
			.append("28").append(CommonFunction.fillString(mchnNoUpd, ' ', 15, true)).append("|")
			.append("29").append(CommonFunction.fillString(termId, ' ', 8, true)).append("|")
			.append("31").append("000001").append("|")
			.append("32").append(CommonFunction.fillString(financeCard1Upd.trim(), ' ', 19, true)).append("|")
			.append("33").append(CommonFunction.fillString(financeCard2Upd.trim(), ' ', 19, true)).append("|")
			.append("34").append(CommonFunction.fillString(financeCard3Upd.trim(), ' ', 19, true)).append("|")
			.append("35").append(CommonFunction.fillString(txn35Upd, ' ', 2, true)).append("|")
			.append("36").append(txn36Upd==null?CommonFunction.fillString(txn36Upd, ' ', 12, true):translate(txn36Upd)).append("|")
			.append("37").append(txn37Upd==null?CommonFunction.fillString(txn37Upd, ' ', 12, true):translate(txn37Upd)).append("|")
			.append("38").append(txn38Upd==null?CommonFunction.fillString(txn38Upd, ' ', 12, true):translate(txn38Upd)).append("|")
			.append("39").append(txn39Upd==null?CommonFunction.fillString(txn39Upd, ' ', 12, true):translate(txn39Upd)).append("|")
			.append("40").append(CommonFunction.fillString(txn40Upd, ' ', 2, true));
		return result.toString();
	*/}
	
	public String isChecked(String param){
		return param==null?"0":"1";
	}
	public int checkTxn(String param){
		return param==null?0:1;
	}
	public String translate(String money){
		if(money.contains("."))
			return CommonFunction.fillString(money.replaceAll("\\.", ""), ' ', 12, true);
		else
			money = CommonFunction.fillString(money, '0', money.length()+2, true);
		return CommonFunction.fillString(money, ' ', 12, true);
	}
}

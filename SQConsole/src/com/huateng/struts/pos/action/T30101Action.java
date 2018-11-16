package com.huateng.struts.pos.action;

import groovy.ui.SystemOutputInterceptor;
import sqlj.framework.ClassResolver.SystemClassResolver;

import com.huateng.bo.term.T3010BO;
import com.huateng.common.Constants;
import com.huateng.po.TblTermInfTmp;
import com.huateng.po.TblTermInfTmpPK;
import com.huateng.struts.pos.TblTermInfConstants;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;

/**
 * Title:终端维护
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-17
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @version 1.0
 */
public class T30101Action extends BaseAction {

	private static final long serialVersionUID = -4511548416739218414L;
	private T3010BO t3010BO;
	private String province;
	private String city;
	private String area;
	private String cityCode;
	private String termIdNew;
	private String mchnNoNew;
	private String brhIdNew;
	private String termIdIdNew;
	private String termFactoryNew;
	private String termMachTpNew;
	private String termMccNew;
	private String termVerNew;
	private String termTpNew;
	private String paramDownSignNew;
	private String icDownSignNew;
 	private String contTelNew;
 	private String termPara2New;	//保存联系人
	private String propTpNew;
	private String propInsNmNew;
	private String termPara1New;
 	private String termBatchNmNew;
	private String termStlmDtNew;
	private String connectModeNew;
	private String equipInvIdNew;
	private String equipInvNmNew;
	private String bindTel1New;
	private String bindTel2New;
	private String bindTel3New;
	private String termAddrNew;
	private String termPlaceNew;
	private String oprNmNew;
	private String keyDownSignNew;
	private String productCdNew;
	private String reserveFlag1New;
	
	private String info3Sign;//是否跳转到权限选择界面标志
	
	private String param1New;
	private String param2New;
	private String param3New;
	private String param4New;
	private String param5New;
	private String param6New;
	private String param7New;
	private String param8New;
	private String param9New;
	private String param10New;
	private String param11New;
	private String param12New;
	private String param13New;
	private String param14New;
	private String param15New;
	private String param16New;
	private String param17New;
	private String param18New;
	private String param19New;
	private String param20New;
	private String param21New;
	private String param22New;
	private String param23New;
	private String param24New;
	private String param25New;
	private String param26New;
	private String param27New;
	private String param28New;
	private String param29New;
	private String param30New;
	private String param31New;
	private String param32New;
	private String txn14New;
	private String txn15New;
	private String txn16New;
	private String txn22New;
	//private String txn27New;
	private String financeCard1New;
	private String financeCard2New;
	private String financeCard3New;
	private String txn35New;
	private String txn36New;
	private String txn37New;
	private String txn38New;
	private String txn39New;
	private String txn40New;
	private String termIdAdd;
	
	private String termFactory; 
	private String runMainId1;
	 
	private String othSvrId;
	private String runMainId2;
	private String misc3;
	private String misc2;
	private String misc1;
	
	//终端参数
	private String txn11New;
	private String txn12New;
	private String txn13New;
	private String txn20New;
	private String txn21New;
	private String txn23New;
	private String txn25New1;
	private String txn25New2;
	private String txn25New3;
	private String txn25New4;
	private String txn25New5;
	
	//20140815添加begin
	private String txn25New6;
	private String txn25New7;
	private String txn25New8;
	//20140815添加end
	
	private String txn27New;
	private String txn28New;
	private String txn29New;
	private String txn30New;
	
	private String txn17New;
	private String txn18New;
	private String txn19New;
	

	
	
	public String getTxn25New6() {
		return txn25New6;
	}

	public void setTxn25New6(String txn25New6) {
		this.txn25New6 = txn25New6;
	}

	public String getTxn25New7() {
		return txn25New7;
	}

	public void setTxn25New7(String txn25New7) {
		this.txn25New7 = txn25New7;
	}

	public String getTxn25New8() {
		return txn25New8;
	}

	public void setTxn25New8(String txn25New8) {
		this.txn25New8 = txn25New8;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getTxn17New() {
		return txn17New;
	}

	public void setTxn17New(String txn17New) {
		this.txn17New = txn17New;
	}

	public String getTxn18New() {
		return txn18New;
	}

	public void setTxn18New(String txn18New) {
		this.txn18New = txn18New;
	}

	public String getTxn19New() {
		return txn19New;
	}

	public void setTxn19New(String txn19New) {
		this.txn19New = txn19New;
	}

	public String getTxn11New() {
		return txn11New;
	}

	public void setTxn11New(String txn11New) {
		this.txn11New = txn11New;
	}

	public String getTxn12New() {
		return txn12New;
	}

	public void setTxn12New(String txn12New) {
		this.txn12New = txn12New;
	}

	public String getTxn13New() {
		return txn13New;
	}

	public void setTxn13New(String txn13New) {
		this.txn13New = txn13New;
	}

	public String getTxn20New() {
		return txn20New;
	}

	public void setTxn20New(String txn20New) {
		this.txn20New = txn20New;
	}

	public String getTxn21New() {
		return txn21New;
	}

	public void setTxn21New(String txn21New) {
		this.txn21New = txn21New;
	}

	public String getTxn23New() {
		return txn23New;
	}

	public void setTxn23New(String txn23New) {
		this.txn23New = txn23New;
	}

	public String getTxn25New1() {
		return txn25New1;
	}

	public void setTxn25New1(String txn25New1) {
		this.txn25New1 = txn25New1;
	}

	public String getTxn25New2() {
		return txn25New2;
	}

	public void setTxn25New2(String txn25New2) {
		this.txn25New2 = txn25New2;
	}

	public String getTxn25New3() {
		return txn25New3;
	}

	public void setTxn25New3(String txn25New3) {
		this.txn25New3 = txn25New3;
	}

	public String getTxn25New4() {
		return txn25New4;
	}

	public void setTxn25New4(String txn25New4) {
		this.txn25New4 = txn25New4;
	}

	public String getTxn25New5() {
		return txn25New5;
	}

	public void setTxn25New5(String txn25New5) {
		this.txn25New5 = txn25New5;
	}

	public String getTxn28New() {
		return txn28New;
	}

	public void setTxn28New(String txn28New) {
		this.txn28New = txn28New;
	}

	public String getTxn29New() {
		return txn29New;
	}

	public void setTxn29New(String txn29New) {
		this.txn29New = txn29New;
	}

	public String getTxn30New() {
		return txn30New;
	}

	public void setTxn30New(String txn30New) {
		this.txn30New = txn30New;
	}

	public String getMisc2() {
		return misc2;
	}

	public void setMisc2(String misc2) {
		this.misc2 = misc2;
	}

	public String getRunMainId2() {
		return runMainId2;
	}

	public void setRunMainId2(String runMainId2) {
		this.runMainId2 = runMainId2;
	}

	public String getMisc3() {
		return misc3;
	}

	public void setMisc3(String misc3) {
		this.misc3 = misc3;
	}

	public String getRunMainId1() {
		return runMainId1;
	}

	public void setRunMainId1(String runMainId1) {
		this.runMainId1 = runMainId1;
	}

	public String getMisc1() {
		return misc1;
	}

	public void setMisc1(String misc1) {
		this.misc1 = misc1;
	}

	public String getOthSvrId() {
		return othSvrId;
	}

	public void setOthSvrId(String othSvrId) {
		this.othSvrId = othSvrId;
	}

	public String getTermFactory() {
		return termFactory;
	}

	public void setTermFactory(String termFactory) {
		this.termFactory = termFactory;
	}

	public String getTermIdAdd() {
		return termIdAdd;
	}

	public void setTermIdAdd(String termIdAdd) {
		this.termIdAdd = termIdAdd;
	}

	/**
	 * @param t3010bo the t3010BO to set
	 */
	public void setT3010BO(T3010BO t3010bo) {
		t3010BO = t3010bo;
	}

	/**
	 * @return the termIdNew
	 */
	public String getTermIdNew() {
		return termIdNew;
	}

	/**
	 * @param termIdNew the termIdNew to set
	 */
	public void setTermIdNew(String termIdNew) {
		this.termIdNew = termIdNew;
	}

	/**
	 * @return the mchnNoNew
	 */
	public String getMchnNoNew() {
		return mchnNoNew;
	}

	/**
	 * @param mchnNoNew the mchnNoNew to set
	 */
	public void setMchnNoNew(String mchnNoNew) {
		this.mchnNoNew = mchnNoNew;
	}

	/**
	 * @return the brhIdNew
	 */
	public String getBrhIdNew() {
		return brhIdNew;
	}

	/**
	 * @param brhIdNew the brhIdNew to set
	 */
	public void setBrhIdNew(String brhIdNew) {
		this.brhIdNew = brhIdNew;
	}

	/**
	 * @return the termIdIdNew
	 */
	public String getTermIdIdNew() {
		return termIdIdNew;
	}

	/**
	 * @param termIdIdNew the termIdIdNew to set
	 */
	public void setTermIdIdNew(String termIdIdNew) {
		this.termIdIdNew = termIdIdNew;
	}

	/**
	 * @return the termFactoryNew
	 */
	public String getTermFactoryNew() {
		return termFactoryNew;
	}

	/**
	 * @param termFactoryNew the termFactoryNew to set
	 */
	public void setTermFactoryNew(String termFactoryNew) {
		this.termFactoryNew = termFactoryNew;
	}

	/**
	 * @return the termMachTpNew
	 */
	public String getTermMachTpNew() {
		return termMachTpNew;
	}

	/**
	 * @param termMachTpNew the termMachTpNew to set
	 */
	public void setTermMachTpNew(String termMachTpNew) {
		this.termMachTpNew = termMachTpNew;
	}

	/**
	 * @return the termMccNew
	 */
	public String getTermMccNew() {
		return termMccNew;
	}

	/**
	 * @param termMccNew the termMccNew to set
	 */
	public void setTermMccNew(String termMccNew) {
		this.termMccNew = termMccNew;
	}

	/**
	 * @return the termVerNew
	 */
	public String getTermVerNew() {
		return termVerNew;
	}

	/**
	 * @param termVerNew the termVerNew to set
	 */
	public void setTermVerNew(String termVerNew) {
		this.termVerNew = termVerNew;
	}

	/**
	 * @return the paramDownSignNew
	 */
	public String getParamDownSignNew() {
		return paramDownSignNew;
	}

	/**
	 * @param paramDownSignNew the paramDownSignNew to set
	 */
	public void setParamDownSignNew(String paramDownSignNew) {
		this.paramDownSignNew = paramDownSignNew;
	}

	public String getIcDownSignNew() {
		return icDownSignNew;
	}

	public void setIcDownSignNew(String icDownSignNew) {
		this.icDownSignNew = icDownSignNew;
	}

	/**
	 * @return the contTelNew
	 */
	public String getContTelNew() {
		return contTelNew;
	}

	/**
	 * @param contTelNew the contTelNew to set
	 */
	public void setContTelNew(String contTelNew) {
		this.contTelNew = contTelNew;
	}


	public String getTermPara2New() {
		return termPara2New;
	}

	public void setTermPara2New(String termPara2New) {
		this.termPara2New = termPara2New;
	}

	/**
	 * @return the propTpNew
	 */
	public String getPropTpNew() {
		return propTpNew;
	}

	/**
	 * @param propTpNew the propTpNew to set
	 */
	public void setPropTpNew(String propTpNew) {
		this.propTpNew = propTpNew;
	}

	/**
	 * @return the reserveFlag1New
	 */
	public String getTermPara1New() {
		return termPara1New;
	}

	/**
	 * @param reserveFlag1New the reserveFlag1New to set
	 */
	public void setTermPara1New(String termPara1New) {
		this.termPara1New = termPara1New;
	}

	/**
	 * @return the propInsNmNew
	 */
	public String getPropInsNmNew() {
		return propInsNmNew;
	}

	/**
	 * @param propInsNmNew the propInsNmNew to set
	 */
	public void setPropInsNmNew(String propInsNmNew) {
		this.propInsNmNew = propInsNmNew;
	}

	/**
	 * @return the termBatchNmNew
	 */
	public String getTermBatchNmNew() {
		return termBatchNmNew;
	}

	/**
	 * @param termBatchNmNew the termBatchNmNew to set
	 */
	public void setTermBatchNmNew(String termBatchNmNew) {
		this.termBatchNmNew = termBatchNmNew;
	}

	/**
	 * @return the termStlmDtNew
	 */
	public String getTermStlmDtNew() {
		return termStlmDtNew;
	}

	/**
	 * @return the termTpNew
	 */
	public String getTermTpNew() {
		return termTpNew;
	}

	/**
	 * @param termTpNew the termTpNew to set
	 */
	public void setTermTpNew(String termTpNew) {
		this.termTpNew = termTpNew;
	}

	/**
	 * @param termStlmDtNew the termStlmDtNew to set
	 */
	public void setTermStlmDtNew(String termStlmDtNew) {
		this.termStlmDtNew = termStlmDtNew;
	}

	/**
	 * @return the connectModeNew
	 */
	public String getConnectModeNew() {
		return connectModeNew;
	}

	/**
	 * @param connectModeNew the connectModeNew to set
	 */
	public void setConnectModeNew(String connectModeNew) {
		this.connectModeNew = connectModeNew;
	}

	/**
	 * @return the equipInvIdNew
	 */
	public String getEquipInvIdNew() {
		return equipInvIdNew;
	}

	/**
	 * @param equipInvIdNew the equipInvIdNew to set
	 */
	public void setEquipInvIdNew(String equipInvIdNew) {
		this.equipInvIdNew = equipInvIdNew;
	}

	/**
	 * @return the equipInvNmNew
	 */
	public String getEquipInvNmNew() {
		return equipInvNmNew;
	}

	/**
	 * @param equipInvNmNew the equipInvNmNew to set
	 */
	public void setEquipInvNmNew(String equipInvNmNew) {
		this.equipInvNmNew = equipInvNmNew;
	}

	/**
	 * @return the bindTel1New
	 */
	public String getBindTel1New() {
		return bindTel1New;
	}

	/**
	 * @param bindTel1New the bindTel1New to set
	 */
	public void setBindTel1New(String bindTel1New) {
		this.bindTel1New = bindTel1New;
	}

	/**
	 * @return the bindTel2New
	 */
	public String getBindTel2New() {
		return bindTel2New;
	}

	/**
	 * @param bindTel2New the bindTel2New to set
	 */
	public void setBindTel2New(String bindTel2New) {
		this.bindTel2New = bindTel2New;
	}

	/**
	 * @return the bindTel3New
	 */
	public String getBindTel3New() {
		return bindTel3New;
	}

	/**
	 * @param bindTel3New the bindTel3New to set
	 */
	public void setBindTel3New(String bindTel3New) {
		this.bindTel3New = bindTel3New;
	}

	/**
	 * @return the termAddrNew
	 */
	public String getTermAddrNew() {
		return termAddrNew;
	}

	/**
	 * @param termAddrNew the termAddrNew to set
	 */
	public void setTermAddrNew(String termAddrNew) {
		this.termAddrNew = termAddrNew;
	}

	/**
	 * @return the termPlaceNew
	 */
	public String getTermPlaceNew() {
		return termPlaceNew;
	}

	/**
	 * @param termPlaceNew the termPlaceNew to set
	 */
	public void setTermPlaceNew(String termPlaceNew) {
		this.termPlaceNew = termPlaceNew;
	}

	/**
	 * @return the oprNmNew
	 */
	public String getOprNmNew() {
		return oprNmNew;
	}

	public String getReserveFlag1New() {
		return reserveFlag1New;
	}

	public void setReserveFlag1New(String reserveFlag1New) {
		this.reserveFlag1New = reserveFlag1New;
	}

	/**
	 * @param oprNmNew the oprNmNew to set
	 */
	public void setOprNmNew(String oprNmNew) {
		this.oprNmNew = oprNmNew;
	}

	/**
	 * @return the keyDownSignNew
	 */
	public String getKeyDownSignNew() {
		return keyDownSignNew;
	}

	/**
	 * @param keyDownSignNew the keyDownSignNew to set
	 */
	public void setKeyDownSignNew(String keyDownSignNew) {
		this.keyDownSignNew = keyDownSignNew;
	}


	public String getInfo3Sign() {
		return info3Sign;
	}

	public void setInfo3Sign(String info3Sign) {
		this.info3Sign = info3Sign;
	}

	/**
	 * @return the param1New
	 */
	public String getParam1New() {
		return param1New;
	}

	/**
	 * @param param1New the param1New to set
	 */
	public void setParam1New(String param1New) {
		this.param1New = param1New;
	}

	/**
	 * @return the param2New
	 */
	public String getParam2New() {
		return param2New;
	}

	/**
	 * @param param2New the param2New to set
	 */
	public void setParam2New(String param2New) {
		this.param2New = param2New;
	}

	/**
	 * @return the param3New
	 */
	public String getParam3New() {
		return param3New;
	}

	/**
	 * @param param3New the param3New to set
	 */
	public void setParam3New(String param3New) {
		this.param3New = param3New;
	}

	/**
	 * @return the param4New
	 */
	public String getParam4New() {
		return param4New;
	}

	/**
	 * @param param4New the param4New to set
	 */
	public void setParam4New(String param4New) {
		this.param4New = param4New;
	}

	/**
	 * @return the param5New
	 */
	public String getParam5New() {
		return param5New;
	}

	/**
	 * @param param5New the param5New to set
	 */
	public void setParam5New(String param5New) {
		this.param5New = param5New;
	}

	/**
	 * @return the param6New
	 */
	public String getParam6New() {
		return param6New;
	}

	/**
	 * @param param6New the param6New to set
	 */
	public void setParam6New(String param6New) {
		this.param6New = param6New;
	}

	/**
	 * @return the param7New
	 */
	public String getParam7New() {
		return param7New;
	}

	/**
	 * @param param7New the param7New to set
	 */
	public void setParam7New(String param7New) {
		this.param7New = param7New;
	}

	/**
	 * @return the param8New
	 */
	public String getParam8New() {
		return param8New;
	}

	/**
	 * @param param8New the param8New to set
	 */
	public void setParam8New(String param8New) {
		this.param8New = param8New;
	}

	/**
	 * @return the param9New
	 */
	public String getParam9New() {
		return param9New;
	}

	/**
	 * @param param9New the param9New to set
	 */
	public void setParam9New(String param9New) {
		this.param9New = param9New;
	}

	/**
	 * @return the param10New
	 */
	public String getParam10New() {
		return param10New;
	}

	/**
	 * @param param10New the param10New to set
	 */
	public void setParam10New(String param10New) {
		this.param10New = param10New;
	}

	/**
	 * @return the param11New
	 */
	public String getParam11New() {
		return param11New;
	}

	/**
	 * @param param11New the param11New to set
	 */
	public void setParam11New(String param11New) {
		this.param11New = param11New;
	}

	/**
	 * @return the param12New
	 */
	public String getParam12New() {
		return param12New;
	}

	/**
	 * @param param12New the param12New to set
	 */
	public void setParam12New(String param12New) {
		this.param12New = param12New;
	}

	/**
	 * @return the param13New
	 */
	public String getParam13New() {
		return param13New;
	}

	/**
	 * @param param13New the param13New to set
	 */
	public void setParam13New(String param13New) {
		this.param13New = param13New;
	}

	/**
	 * @return the param14New
	 */
	public String getParam14New() {
		return param14New;
	}

	/**
	 * @param param14New the param14New to set
	 */
	public void setParam14New(String param14New) {
		this.param14New = param14New;
	}

	/**
	 * @return the param15New
	 */
	public String getParam15New() {
		return param15New;
	}

	/**
	 * @param param15New the param15New to set
	 */
	public void setParam15New(String param15New) {
		this.param15New = param15New;
	}

	/**
	 * @return the param16New
	 */
	public String getParam16New() {
		return param16New;
	}

	/**
	 * @param param16New the param16New to set
	 */
	public void setParam16New(String param16New) {
		this.param16New = param16New;
	}

	/**
	 * @return the param17New
	 */
	public String getParam17New() {
		return param17New;
	}

	/**
	 * @param param17New the param17New to set
	 */
	public void setParam17New(String param17New) {
		this.param17New = param17New;
	}

	/**
	 * @return the param18New
	 */
	public String getParam18New() {
		return param18New;
	}

	/**
	 * @param param18New the param18New to set
	 */
	public void setParam18New(String param18New) {
		this.param18New = param18New;
	}

	/**
	 * @return the param19New
	 */
	public String getParam19New() {
		return param19New;
	}

	/**
	 * @param param19New the param19New to set
	 */
	public void setParam19New(String param19New) {
		this.param19New = param19New;
	}

	/**
	 * @return the param20New
	 */
	public String getParam20New() {
		return param20New;
	}

	/**
	 * @param param20New the param20New to set
	 */
	public void setParam20New(String param20New) {
		this.param20New = param20New;
	}

	/**
	 * @return the param21New
	 */
	public String getParam21New() {
		return param21New;
	}

	/**
	 * @param param21New the param21New to set
	 */
	public void setParam21New(String param21New) {
		this.param21New = param21New;
	}

	/**
	 * @return the param22New
	 */
	public String getParam22New() {
		return param22New;
	}

	/**
	 * @param param22New the param22New to set
	 */
	public void setParam22New(String param22New) {
		this.param22New = param22New;
	}

	/**
	 * @return the param23New
	 */
	public String getParam23New() {
		return param23New;
	}

	/**
	 * @param param23New the param23New to set
	 */
	public void setParam23New(String param23New) {
		this.param23New = param23New;
	}

	/**
	 * @return the param24New
	 */
	public String getParam24New() {
		return param24New;
	}

	/**
	 * @param param24New the param24New to set
	 */
	public void setParam24New(String param24New) {
		this.param24New = param24New;
	}

	/**
	 * @return the param25New
	 */
	public String getParam25New() {
		return param25New;
	}

	/**
	 * @param param25New the param25New to set
	 */
	public void setParam25New(String param25New) {
		this.param25New = param25New;
	}

	/**
	 * @return the param26New
	 */
	public String getParam26New() {
		return param26New;
	}

	/**
	 * @param param26New the param26New to set
	 */
	public void setParam26New(String param26New) {
		this.param26New = param26New;
	}

	/**
	 * @return the param27New
	 */
	public String getParam27New() {
		return param27New;
	}

	/**
	 * @param param27New the param27New to set
	 */
	public void setParam27New(String param27New) {
		this.param27New = param27New;
	}

	/**
	 * @return the param28New
	 */
	public String getParam28New() {
		return param28New;
	}

	/**
	 * @param param28New the param28New to set
	 */
	public void setParam28New(String param28New) {
		this.param28New = param28New;
	}

	/**
	 * @return the param29New
	 */
	public String getParam29New() {
		return param29New;
	}

	/**
	 * @param param29New the param29New to set
	 */
	public void setParam29New(String param29New) {
		this.param29New = param29New;
	}

	/**
	 * @return the param30New
	 */
	public String getParam30New() {
		return param30New;
	}

	/**
	 * @param param30New the param30New to set
	 */
	public void setParam30New(String param30New) {
		this.param30New = param30New;
	}

	/**
	 * @return the param31New
	 */
	public String getParam31New() {
		return param31New;
	}

	/**
	 * @param param31New the param31New to set
	 */
	public void setParam31New(String param31New) {
		this.param31New = param31New;
	}

	/**
	 * @return the param32New
	 */
	public String getParam32New() {
		return param32New;
	}

	/**
	 * @param param32New the param32New to set
	 */
	public void setParam32New(String param32New) {
		this.param32New = param32New;
	}

	/**
	 * @return the txn22New
	 */
	public String getTxn22New() {
		return txn22New;
	}

	/**
	 * @param txn22New the txn22New to set
	 */
	public void setTxn22New(String txn22New) {
		this.txn22New = txn22New;
	}

	/**
	 * @return the txn27New
	 */
	public String getTxn27New() {
		return txn27New;
	}

	/**
	 * @param txn27New the txn27New to set
	 */
	public void setTxn27New(String txn27New) {
		this.txn27New = txn27New;
	}
	
	/**
	 * @return the financeCard1New
	 */
	public String getFinanceCard1New() {
		return financeCard1New;
	}

	/**
	 * @param financeCard1New the financeCard1New to set
	 */
	public void setFinanceCard1New(String financeCard1New) {
		this.financeCard1New = financeCard1New;
	}

	/**
	 * @return the financeCard2New
	 */
	public String getFinanceCard2New() {
		return financeCard2New;
	}

	/**
	 * @param financeCard2New the financeCard2New to set
	 */
	public void setFinanceCard2New(String financeCard2New) {
		this.financeCard2New = financeCard2New;
	}

	/**
	 * @return the financeCard3New
	 */
	public String getFinanceCard3New() {
		return financeCard3New;
	}

	/**
	 * @param financeCard3New the financeCard3New to set
	 */
	public void setFinanceCard3New(String financeCard3New) {
		this.financeCard3New = financeCard3New;
	}

	/**
	 * @return the txn14New
	 */
	public String getTxn14New() {
		return txn14New;
	}

	/**
	 * @param txn14New the txn14New to set
	 */
	public void setTxn14New(String txn14New) {
		this.txn14New = txn14New;
	}

	/**
	 * @return the txn15New
	 */
	public String getTxn15New() {
		return txn15New;
	}

	/**
	 * @param txn15New the txn15New to set
	 */
	public void setTxn15New(String txn15New) {
		this.txn15New = txn15New;
	}

	/**
	 * @return the txn16New
	 */
	public String getTxn16New() {
		return txn16New;
	}

	/**
	 * @param txn16New the txn16New to set
	 */
	public void setTxn16New(String txn16New) {
		this.txn16New = txn16New;
	}

	/**
	 * @return the txn35New
	 */
	public String getTxn35New() {
		return txn35New;
	}

	/**
	 * @param txn35New the txn35New to set
	 */
	public void setTxn35New(String txn35New) {
		this.txn35New = txn35New;
	}

	/**
	 * @return the txn36New
	 */
	public String getTxn36New() {
		return txn36New;
	}

	/**
	 * @param txn36New the txn36New to set
	 */
	public void setTxn36New(String txn36New) {
		this.txn36New = txn36New;
	}

	/**
	 * @return the txn37New
	 */
	public String getTxn37New() {
		return txn37New;
	}

	/**
	 * @param txn37New the txn37New to set
	 */
	public void setTxn37New(String txn37New) {
		this.txn37New = txn37New;
	}

	/**
	 * @return the txn38New
	 */
	public String getTxn38New() {
		return txn38New;
	}

	/**
	 * @param txn38New the txn38New to set
	 */
	public void setTxn38New(String txn38New) {
		this.txn38New = txn38New;
	}

	/**
	 * @return the txn39New
	 */
	public String getTxn39New() {
		return txn39New;
	}

	/**
	 * @param txn39New the txn39New to set
	 */
	public void setTxn39New(String txn39New) {
		this.txn39New = txn39New;
	}
	
	/**
	 * @return the txn40New
	 */
	public String getTxn40New() {
		return txn40New;
	}

	/**
	 * @param txn40New the txn40New to set
	 */
	public void setTxn40New(String txn40New) {
		this.txn40New = txn40New;
	}

	/**
	 * @return the productCdNew
	 */
	public String getProductCdNew() {
		return productCdNew;
	}

	/**
	 * @param productCdNew the productCdNew to set
	 */
	public void setProductCdNew(String productCdNew) {
		this.productCdNew = productCdNew;
	}

	@Override
	protected String subExecute() throws Exception {
		//生成终端号
		String no = t3010BO.getTermNo();
		if("-1".equals(no)){
			return "终端号已达到上限！";
		}
		termIdAdd = no;
		System.out.println("终端号："+ termIdAdd);
		
		//验证终端号是否已经存在
		String termIdSql="select count(*) from tbl_term_inf_tmp where term_id='"+termIdAdd+"' ";
		String result= CommonFunction.getCommQueryDAO().findCountBySQLQuery(termIdSql);
		System.out.println("result+"+result);
		if(!result.equals("0")){
			return "30101.07";
		}
		//验证输入的商户号对应商户是否为注销状态
		String mchtNoSql = "select count(*) from tbl_mcht_base_inf_tmp where trim(mcht_no)='" + this.mchnNoNew + "' and mcht_status='8' ";
		String result2 = CommonFunction.getCommQueryDAO().findCountBySQLQuery(mchtNoSql);
		if(!"0".equals(result2)){
			return "该商户已经注销，不能开设终端，请重新输入。";
		}
		
		TblTermInfTmp tblTermInf = new TblTermInfTmp();
		TblTermInfTmpPK pk = new TblTermInfTmpPK();
		BeanUtils.copyProperties(tblTermInf, this);
		tblTermInf.setId(pk);
		/*if(brhIdNew != null && !brhIdNew.trim().equals(""))
			pk.setTermId(t3010BO.getTermId(brhIdNew));
		else
			return TblTermInfConstants.T30101_02;*/
		pk.setTermId(termIdAdd);
		
		tblTermInf.setTermFactory(termFactoryNew);//终端厂商
		tblTermInf.setTermMachTp(termMachTpNew);//终端型号
		tblTermInf.setTermBranch(brhIdNew);
		tblTermInf.setTermAddr(termAddrNew);
		tblTermInf.setMchtCd(mchnNoNew);
		tblTermInf.setTermSta(TblTermInfConstants.TERM_STA_INIT);
		tblTermInf.setTermSignSta(TblTermInfConstants.TERM_SIGN_DEFAULT);
		tblTermInf.setTermMcc(termMccNew);
//		supportIC这个为默认项，必填不可见。IC卡参数下载标志由IcDownSign标志位控制
		tblTermInf.setKeyDownSign(isChecked(keyDownSignNew));
		tblTermInf.setParamDownSign(isChecked(paramDownSignNew));
//		tblTermInf.setSupportIc(isChecked(supportICNew));
//		tblTermInf.setKeyDownSign("1");
//		tblTermInf.setParamDownSign("1");
		tblTermInf.setSupportIc("1");
		tblTermInf.setIcDownSign(isChecked(icDownSignNew));
		tblTermInf.setReserveFlag1(isChecked(reserveFlag1New));
		
		tblTermInf.setConnectMode(connectModeNew);
		tblTermInf.setContTel(contTelNew);
		tblTermInf.setTermPara2(termPara2New);
		tblTermInf.setOprNm(oprNmNew);
		tblTermInf.setEquipInvId(equipInvIdNew);
		tblTermInf.setEquipInvNm(equipInvNmNew);
		tblTermInf.setPropTp(propTpNew);
		tblTermInf.setPropInsNm(propInsNmNew);
		tblTermInf.setTermPara1(termPara1New);
		tblTermInf.setTermBatchNm("000001");
		tblTermInf.setTermTp(termTpNew);
		tblTermInf.setBindTel1(bindTel1New);
		tblTermInf.setProductCd(productCdNew);
		if(bindTel2New!=null)
			tblTermInf.setBindTel2(bindTel2New);
		else
			bindTel2New = " ";
		
		if(bindTel3New!=null)
			tblTermInf.setBindTel3(bindTel3New);
		else
			bindTel3New = " ";
		
		if(txn15New == null)
			txn15New = " ";
		if(txn16New == null)
			txn16New = " ";
		//环讯没有财务pos
		/*if(termTpNew.equals("1"))
		{
			tblTermInf.setFinanceCard1(financeCard1New);
			if(financeCard2New!=null)
				tblTermInf.setFinanceCard2(financeCard2New);
			else
				financeCard2New = " ";
			if(financeCard2New!=null)
				tblTermInf.setFinanceCard3(financeCard3New);
			else
				financeCard3New = " ";
		}
		else
		{
			financeCard1New = " ";
			financeCard2New = " ";
			financeCard3New = " ";
		}*/
		//tblTermInf.setRecUpdOpr(operator.getOprId());
		tblTermInf.setRecCrtOpr(operator.getOprId());//修改人
		tblTermInf.setRecCheOpr(operator.getOprId());//创建人
		tblTermInf.setTermStlmDt(CommonFunction.getCurrentDate());
		
		//判断是否勾选交易
		int value = checkTxn(param1New)+checkTxn(param2New)+checkTxn(param3New)+checkTxn(param4New)
			+checkTxn(param5New)+checkTxn(param6New)+checkTxn(param7New)+checkTxn(param8New)
			/*+checkTxn(param9New)+checkTxn(param10New)+checkTxn(param11New)+checkTxn(param12New)
			+checkTxn(param13New)*/+checkTxn(param14New)+checkTxn(param15New)+checkTxn(param16New)
			+checkTxn(param17New)+checkTxn(param18New)+checkTxn(param19New)+checkTxn(param20New)
			+checkTxn(param21New)+checkTxn(param22New)+checkTxn(param23New)+checkTxn(param24New);
		System.out.println("预授权"+param2New+"退货"+param8New+"消费"+param6New+"消费撤销"+param7New+"查询"+param1New+"是否屏蔽卡号"+txn28New+"CA公钥下载标志"+keyDownSignNew+"IC卡参数下载标志"+icDownSignNew);
	//	System.out.println(info3Sign);
		if(value == 0){
			if(info3Sign != null && info3Sign.equals("other")){
				param1New = "1";
				param6New = "1";
				param7New = "1";
			}else{
				return TblTermInfConstants.T30101_02;
			}
		}
		
		String termPara = parseTermPara(tblTermInf.getId().getTermId());
		System.err.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.err.println(termPara);
		System.err.println("================================================================================================");
		tblTermInf.setTermPara(termPara);
		//判断财务POS交易勾选
		/*int value0 = checkTxn(param1New)+checkTxn(param11New)+checkTxn(param12New)+checkTxn(param13New)
		+checkTxn(param14New)+checkTxn(param15New);
		if((value0 != 6||value > 6) && termTpNew.equals("1"))
			return TblTermInfConstants.T30101_04;*/
		
		//判断是否支持IC卡
		/*if(checkTxn(icDownSignNew) != 1)
		{
			int value1 = checkTxn(param20New)+checkTxn(param21New)+checkTxn(param22New)
					+checkTxn(param23New)+checkTxn(param24New);
			if(value1 > 0)
				return TblTermInfConstants.T30101_05;
		}*/
		//判断是否电子现金是否勾选
		/*if(checkTxn(param20New) != 1)
		{
			int value2 = checkTxn(param21New)+checkTxn(param22New)
					+checkTxn(param23New)+checkTxn(param24New);
			if(value2 > 0)
				return TblTermInfConstants.T30101_06;
		}*/
			
		tblTermInf.setProvince(this.getProvince());
		tblTermInf.setCity(this.getCity()!=null?this.getCity():" ");
		tblTermInf.setArea(this.getArea()!=null?this.getArea():" ");
		tblTermInf.setCityCode(this.getCityCode());
		tblTermInf.setRecDelTs(CommonFunction.getCurrentDateTime());//修改时间
		String termId = t3010BO.add(tblTermInf);
		if(termId != null)
			return Constants.SUCCESS_CODE_CUSTOMIZE+"终端信息新增成功,商户号：["+mchnNoNew+"],终端号：["+termId.trim()+"]";
		
		return Constants.FAILURE_CODE;
	}
	
	public String parseTermPara(String termId)	{
		StringBuffer result = new StringBuffer();
		result.append("11").append(CommonFunction.fillString(txn11New, ' ', 2, true))//POS终端应用类型
			  .append("12").append(CommonFunction.fillString(txn12New, ' ', 2, true))//超时时间
			  .append("13").append(CommonFunction.fillString(txn13New, ' ', 1, true))//拨号重拨次数
			  .append("14").append(CommonFunction.fillStringByDB(txn14New.trim(), ' ', 14, true))//绑定电话1
			  .append("15").append(CommonFunction.fillStringByDB(txn15New.trim(), ' ', 14, true))//绑定电话2
			  .append("16").append(CommonFunction.fillStringByDB(txn16New.trim(), ' ', 14, true))//绑定电话3
			  .append("17").append(CommonFunction.fillString(txn17New,' ',14,true))//无
			  .append("18").append(CommonFunction.fillString(txn18New, ' ', 1, true))//是否支持消费
			  .append("19").append(CommonFunction.fillString(txn19New, ' ', 2, false))//小费百分比
			  .append("20").append(isChecked(txn20New))//支持手工输入卡号
			  .append("21").append(isChecked(txn21New))//是否自动签退
			  .append("22").append(CommonFunction.fillStringByDB(txn22New.trim(), ' ', 40, true))//商户名
			  .append("23").append(CommonFunction.fillString(txn23New, '0', 1, true))//冲正重发次数
			  .append("24").append(" ")
			  .append("25");
		
	//	System.out.println(txn25New6);//预授权撤销交易是否需要输入密码
	//	System.out.println(txn25New8);//预授权完成是否需输入密码
		StringBuffer sb=new StringBuffer();
		sb.append(isChecked(txn25New1)).append(isChecked(txn25New2)).append(isChecked(txn25New3)).append(isChecked(txn25New4))
		   .append(isChecked(txn25New5)).append(isChecked(txn25New6))
		   .append(isChecked(txn25New7)).append(isChecked(txn25New8))/*.append("000")*/;
		result.append(CommonFunction.fillString(Integer.toHexString(Integer.valueOf(sb.toString(), 2)),'0',2,false));//将sb中保存的2进制值转换为16进制保存
		
		result.append("26");		
		StringBuffer txnCode = new StringBuffer();
		String txnCode1=isChecked(param1New)+isChecked(param2New)+isChecked(param3New)+isChecked(param4New);
		String txnCode2=isChecked(param5New)+isChecked(param6New)+isChecked(param7New)+isChecked(param8New);
		String txnCode3=isChecked(param9New)+isChecked(param10New)+isChecked(param11New)+isChecked(param12New);
		String txnCode4=isChecked(param13New)+isChecked(param14New)+isChecked(param15New)+isChecked(param16New);
		String txnCode5=isChecked(param17New)+isChecked(param18New)+isChecked(param19New)+isChecked(param20New);
		String txnCode6=isChecked(param21New)+isChecked(param22New)+isChecked(param23New)+isChecked(param24New);
		String txnCode7=isChecked(param25New)+isChecked(param26New)+isChecked(param27New)+isChecked(param28New);
		String txnCode8=isChecked(param29New)+isChecked(param30New)+isChecked(param31New)+isChecked(param32New);
		txnCode.append(Integer.toHexString(Integer.valueOf(txnCode1,2)))
				.append(Integer.toHexString(Integer.valueOf(txnCode2,2)))
				.append(Integer.toHexString(Integer.valueOf(txnCode3,2)))
				.append(Integer.toHexString(Integer.valueOf(txnCode4,2)))
				.append(Integer.toHexString(Integer.valueOf(txnCode5,2)))
				.append(Integer.toHexString(Integer.valueOf(txnCode6,2)))
				.append(Integer.toHexString(Integer.valueOf(txnCode7,2)))
				.append(Integer.toHexString(Integer.valueOf(txnCode8,2)));
		result.append(CommonFunction.fillString(txnCode.toString(),'0',8,false));
		
		result.append("27").append(CommonFunction.fillString(txn27New, ' ', 1, true))//打印联数
			.append("28").append(isChecked(txn28New))//是否屏蔽卡号
			.append("29").append(isChecked(txn29New))//是否显示LOGO
			.append("30").append(CommonFunction.fillString(txn30New, ' ', 2, true));//快捷交易类型
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
	}
	public String isChecked(String param){
		return param==null?"0":"1";
	}
	public int checkTxn(String param) {
		return param==null?0:1;
	}
	public String translate(String money) {
		if(money.contains("."))
			return CommonFunction.fillString(money.replaceAll("\\.", ""), ' ', 12, true);
		else
			money = CommonFunction.fillString(money, '0', money.length()+2, true);
		return CommonFunction.fillString(money, ' ', 12, true);
	}

}

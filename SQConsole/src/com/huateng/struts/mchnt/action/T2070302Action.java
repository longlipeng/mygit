package com.huateng.struts.mchnt.action;



import com.huateng.bo.mchnt.T2070302BO;
import com.huateng.bo.mchnt.T20703BO;
import com.huateng.common.Constants;
import com.huateng.po.mchnt.TblHisDiscAlgo1;
import com.huateng.po.mchnt.TblHisDiscAlgo1Tmp;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.GenerateNextId;

public class T2070302Action extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String DISC_ID;
	private String TERM_ID;
    private String MCHT_NO;
    private String CITY_CODE;
    private String TO_BRCH_NO;
    private String FK_BRCH_NO;
    private String CARD_TYPE;
    private String CHANNEL_NO;
    private String BUSINESS_TYPE;
    private String TXN_NUM;
    private String MIN_FEE;
    private String MAX_FEE;
    private String FLOOR_AMOUNT;
    private String FLAG;
    private String FEE_VALUE;
    private String MAX_FEE1;
    private String MIN_FEE1;
    private String FEE_VALUE1;
    private String FLOOR_AMOUNT1;
    private String refuseInfo;
    public String getRefuseInfo() {
		return refuseInfo;
	}
	public void setRefuseInfo(String refuseInfo) {
		this.refuseInfo = refuseInfo;
	}
	
    public String getMAX_FEE1() {
		return MAX_FEE1;
	}
	public void setMAX_FEE1(String mAXFEE1) {
		MAX_FEE1 = mAXFEE1;
	}
	public String getMIN_FEE1() {
		return MIN_FEE1;
	}
	public void setMIN_FEE1(String mINFEE1) {
		MIN_FEE1 = mINFEE1;
	}
	public String getFEE_VALUE1() {
		return FEE_VALUE1;
	}
	public void setFEE_VALUE1(String fEEVALUE1) {
		FEE_VALUE1 = fEEVALUE1;
	}
	public String getFLOOR_AMOUNT1() {
		return FLOOR_AMOUNT1;
	}
	public void setFLOOR_AMOUNT1(String fLOORAMOUNT1) {
		FLOOR_AMOUNT1 = fLOORAMOUNT1;
	}
	
    public String getDISC_ID() {
		return DISC_ID;
	}
	public void setDISC_ID(String dISCID) {
		DISC_ID = dISCID;
	}
    public String getTERM_ID() {
		return TERM_ID;
	}
	public void setTERM_ID(String tERMID) {
		TERM_ID = tERMID;
	}
	public String getMCHT_NO() {
		return MCHT_NO;
	}
	public void setMCHT_NO(String mCHTNO) {
		MCHT_NO = mCHTNO;
	}
	public String getCITY_CODE() {
		return CITY_CODE;
	}
	public void setCITY_CODE(String cITYCODE) {
		CITY_CODE = cITYCODE;
	}
	public String getTO_BRCH_NO() {
		return TO_BRCH_NO;
	}
	public void setTO_BRCH_NO(String tOBRCHNO) {
		TO_BRCH_NO = tOBRCHNO;
	}
	public String getFK_BRCH_NO() {
		return FK_BRCH_NO;
	}
	public void setFK_BRCH_NO(String fKBRCHNO) {
		FK_BRCH_NO = fKBRCHNO;
	}
	public String getCARD_TYPE() {
		return CARD_TYPE;
	}
	public void setCARD_TYPE(String cARDTYPE) {
		CARD_TYPE = cARDTYPE;
	}
	public String getCHANNEL_NO() {
		return CHANNEL_NO;
	}
	public void setCHANNEL_NO(String cHANNELNO) {
		CHANNEL_NO = cHANNELNO;
	}
	public String getBUSINESS_TYPE() {
		return BUSINESS_TYPE;
	}
	public void setBUSINESS_TYPE(String bUSINESSTYPE) {
		BUSINESS_TYPE = bUSINESSTYPE;
	}
	public String getTXN_NUM() {
		return TXN_NUM;
	}
	public void setTXN_NUM(String tXNNUM) {
		TXN_NUM = tXNNUM;
	}
	public String getMIN_FEE() {
		return MIN_FEE;
	}
	public void setMIN_FEE(String mINFEE) {
		MIN_FEE = mINFEE;
	}
	public String getMAX_FEE() {
		return MAX_FEE;
	}
	public void setMAX_FEE(String mAXFEE) {
		MAX_FEE = mAXFEE;
	}
	public String getFLOOR_AMOUNT() {
		return FLOOR_AMOUNT;
	}
	public void setFLOOR_AMOUNT(String fLOORAMOUNT) {
		FLOOR_AMOUNT = fLOORAMOUNT;
	}
	public String getFLAG() {
		return FLAG;
	}
	public void setFLAG(String fLAG) {
		FLAG = fLAG;
	}
	public String getFEE_VALUE() {
		return FEE_VALUE;
	}
	public void setFEE_VALUE(String fEEVALUE) {
		FEE_VALUE = fEEVALUE;
	}
	private T20703BO t20703BO=(T20703BO) ContextUtil.getBean("T20703BO");
	private T2070302BO t2070302BO=(T2070302BO) ContextUtil.getBean("T2070302BO");
	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub
		try {
			if("add".equals(method)) {
				rspCode = add();
			} else if("delete".equals(method)) {
				rspCode = delete();
			} else if("update".equals(method)) {
				rspCode = update();
			} 
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，商户手续费规则" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	
	private String add() {
		// TODO Auto-generated method stub
		TblHisDiscAlgo1Tmp tblHisDiscAlgo1Tmp=new TblHisDiscAlgo1Tmp(); 
		String DISC_ID=GenerateNextId.getMchtFeeId();
		tblHisDiscAlgo1Tmp.setDISC_ID(DISC_ID);
		tblHisDiscAlgo1Tmp.setINDEX_NUM("0");
		tblHisDiscAlgo1Tmp.setTERM_ID(TERM_ID);
		tblHisDiscAlgo1Tmp.setMCHT_NO(MCHT_NO);
		tblHisDiscAlgo1Tmp.setCITY_CODE(CITY_CODE);
		tblHisDiscAlgo1Tmp.setTO_BRCH_NO(TO_BRCH_NO);
		tblHisDiscAlgo1Tmp.setFK_BRCH_NO(FK_BRCH_NO);
		tblHisDiscAlgo1Tmp.setCARD_TYPE(CARD_TYPE);
		tblHisDiscAlgo1Tmp.setCHANNEL_NO(CHANNEL_NO);
		tblHisDiscAlgo1Tmp.setBUSINESS_TYPE(BUSINESS_TYPE);
		tblHisDiscAlgo1Tmp.setTXN_NUM(TXN_NUM);
		tblHisDiscAlgo1Tmp.setMIN_FEE(Double.parseDouble(MIN_FEE));
		tblHisDiscAlgo1Tmp.setMAX_FEE(Double.parseDouble(MAX_FEE));
		tblHisDiscAlgo1Tmp.setFLOOR_AMOUNT(Double.parseDouble(FLOOR_AMOUNT));
		tblHisDiscAlgo1Tmp.setFLAG(FLAG);
		tblHisDiscAlgo1Tmp.setFEE_VALUE(Double.parseDouble(FEE_VALUE));
		tblHisDiscAlgo1Tmp.setREC_UPD_USR_ID(operator.getOprId());
		tblHisDiscAlgo1Tmp.setREC_UPD_TS(CommonFunction.getCurrentDateTime());
		tblHisDiscAlgo1Tmp.setREC_CRT_TS(CommonFunction.getCurrentDateTime());
		tblHisDiscAlgo1Tmp.setSA_SATUTE(CommonFunction.ADD_TO_CHECK);
		t2070302BO.add(tblHisDiscAlgo1Tmp);
		log("添加商户手续费规则成功。操作员编号："+operator.getOprId()) ;
		return Constants.SUCCESS_CODE;
	}
	private String update() {
		// TODO Auto-generated method stub
		TblHisDiscAlgo1Tmp tblHisDiscAlgo1Tmp=t2070302BO.get(DISC_ID);
		tblHisDiscAlgo1Tmp.setINDEX_NUM("0");
		tblHisDiscAlgo1Tmp.setTERM_ID(TERM_ID);
		tblHisDiscAlgo1Tmp.setMCHT_NO(MCHT_NO);
		tblHisDiscAlgo1Tmp.setCITY_CODE(CITY_CODE);
		tblHisDiscAlgo1Tmp.setTO_BRCH_NO(TO_BRCH_NO);
		tblHisDiscAlgo1Tmp.setFK_BRCH_NO(FK_BRCH_NO);
		tblHisDiscAlgo1Tmp.setCARD_TYPE(CARD_TYPE);
		tblHisDiscAlgo1Tmp.setCHANNEL_NO(CHANNEL_NO);
		tblHisDiscAlgo1Tmp.setBUSINESS_TYPE(BUSINESS_TYPE);
		tblHisDiscAlgo1Tmp.setTXN_NUM(TXN_NUM);
		tblHisDiscAlgo1Tmp.setMIN_FEE(Double.parseDouble(MIN_FEE1));
		tblHisDiscAlgo1Tmp.setMAX_FEE(Double.parseDouble(MAX_FEE1));
		tblHisDiscAlgo1Tmp.setFLOOR_AMOUNT(Double.parseDouble(FLOOR_AMOUNT1));
		tblHisDiscAlgo1Tmp.setFLAG(FLAG);
		tblHisDiscAlgo1Tmp.setFEE_VALUE(Double.parseDouble(FEE_VALUE1));
		tblHisDiscAlgo1Tmp.setREC_UPD_USR_ID(operator.getOprId());
		tblHisDiscAlgo1Tmp.setREC_UPD_TS(CommonFunction.getCurrentDateTime());
		tblHisDiscAlgo1Tmp.setREC_CRT_TS(CommonFunction.getCurrentDateTime());
		String state=tblHisDiscAlgo1Tmp.getSA_SATUTE();
		if(("2").equals(state)){
			tblHisDiscAlgo1Tmp.setREC_UPD_USR_ID(operator.getOprId());
			tblHisDiscAlgo1Tmp.setREC_UPD_TS(CommonFunction.getCurrentDateTime());
			tblHisDiscAlgo1Tmp.setSA_SATUTE(CommonFunction.MODIFY_TO_CHECK);
		}
		t2070302BO.update(tblHisDiscAlgo1Tmp);
		log("更改商户手续费规则成功。操作员编号："+operator.getOprId()) ;
		return Constants.SUCCESS_CODE;
	}
	private String delete() throws IllegalAccessException, Exception {
		// TODO Auto-generated method stub
		TblHisDiscAlgo1Tmp tblHisDiscAlgo1Tmp=t2070302BO.get(DISC_ID);
		String state=tblHisDiscAlgo1Tmp.getSA_SATUTE();
		if("0".equals(state)){
			t2070302BO.delete(DISC_ID);
		}
		tblHisDiscAlgo1Tmp.setSA_SATUTE(CommonFunction.DELETE_TO_CHECK);
		t2070302BO.update(tblHisDiscAlgo1Tmp);
		TblHisDiscAlgo1 tblHisDiscAlgo1=t20703BO.get(DISC_ID);
		BeanUtils.copyProperties(tblHisDiscAlgo1Tmp,tblHisDiscAlgo1);
		t20703BO.update(tblHisDiscAlgo1);
		
		return Constants.SUCCESS_CODE;
	}
	
}

package com.huateng.struts.risk.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.huateng.bo.risk.T40402BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.po.CstSysParam;
import com.huateng.po.risk.RiskBefore;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

public class T40402Action extends BaseAction {
	private static final long serialVersionUID = 1L;
	
	private String MCHT_NM;
	private String LICENSE_NO;
	private String ORG_CODE;
	private String IDENTITY;
	private String MCHT_TYPE;
	private String MAIN_BUS_NUM;
	private String RISK_INDUSTRY;
	private String PREMISES;
	private String PARAM1;
	private String REG_FUND;
	private String PARAM2;
	private String PARAM3;
	private String PARAM4;
	private String PARAM5;
	private String PARAM6;
	private String PARAM7;
	private String PARAM8;
	private String PARAM9;
	private String PARAM10;
	private String PARAM11;
	private String PARAM12;
	private String PARAM13;
	private String rbauto15;
	private String rbauto16;
	private String rbauto17;
	private String rbauto18;
	//
	private String PARAM27;//广东
	private String PARAM15;//湖北
	private String PARAM16;//河南
	private String PARAM17;//海南
	
	private String PARAM18;//山东
	private String PARAM21;//浙江
	private String PARAM19;//湖南
	
	private String PARAM20;//河北
	private String PARAM22;//山西
	private String PARAM23;//江西
	private String PARAM24;//安徽
	
	private String PARAM25;//其他地区
	/**
	 * @return the pARAM27
	 */
	public String getPARAM27() {
		return PARAM27;
	}

	/**
	 * @param pARAM27 the pARAM27 to set
	 */
	public void setPARAM27(String pARAM27) {
		PARAM27 = pARAM27;
	}

	/**
	 * @return the pARAM16
	 */
	public String getPARAM16() {
		return PARAM16;
	}

	/**
	 * @param pARAM16 the pARAM16 to set
	 */
	public void setPARAM16(String pARAM16) {
		PARAM16 = pARAM16;
	}

	/**
	 * @return the pARAM17
	 */
	public String getPARAM17() {
		return PARAM17;
	}

	/**
	 * @param pARAM17 the pARAM17 to set
	 */
	public void setPARAM17(String pARAM17) {
		PARAM17 = pARAM17;
	}

	/**
	 * @return the pARAM21
	 */
	public String getPARAM21() {
		return PARAM21;
	}

	/**
	 * @param pARAM21 the pARAM21 to set
	 */
	public void setPARAM21(String pARAM21) {
		PARAM21 = pARAM21;
	}

	/**
	 * @return the pARAM19
	 */
	public String getPARAM19() {
		return PARAM19;
	}

	/**
	 * @param pARAM19 the pARAM19 to set
	 */
	public void setPARAM19(String pARAM19) {
		PARAM19 = pARAM19;
	}

	/**
	 * @return the pARAM20
	 */
	public String getPARAM20() {
		return PARAM20;
	}

	/**
	 * @param pARAM20 the pARAM20 to set
	 */
	public void setPARAM20(String pARAM20) {
		PARAM20 = pARAM20;
	}

	/**
	 * @return the pARAM22
	 */
	public String getPARAM22() {
		return PARAM22;
	}

	/**
	 * @param pARAM22 the pARAM22 to set
	 */
	public void setPARAM22(String pARAM22) {
		PARAM22 = pARAM22;
	}

	/**
	 * @return the pARAM23
	 */
	public String getPARAM23() {
		return PARAM23;
	}

	/**
	 * @param pARAM23 the pARAM23 to set
	 */
	public void setPARAM23(String pARAM23) {
		PARAM23 = pARAM23;
	}

	/**
	 * @return the pARAM24
	 */
	public String getPARAM24() {
		return PARAM24;
	}

	/**
	 * @param pARAM24 the pARAM24 to set
	 */
	public void setPARAM24(String pARAM24) {
		PARAM24 = pARAM24;
	}

	/**
	 * @return the pARAM25
	 */
	public String getPARAM25() {
		return PARAM25;
	}

	/**
	 * @param pARAM25 the pARAM25 to set
	 */
	public void setPARAM25(String pARAM25) {
		PARAM25 = pARAM25;
	}


	
    /**
	 * @return the pARAM18
	 */
	public String getPARAM18() {
		return PARAM18;
	}

	/**
	 * @param pARAM18 the pARAM18 to set
	 */
	public void setPARAM18(String pARAM18) {
		PARAM18 = pARAM18;
	}

	/**
	 * @return the pARAM15
	 */
	public String getPARAM15() {
		return PARAM15;
	}

	/**
	 * @param pARAM15 the pARAM15 to set
	 */
	public void setPARAM15(String pARAM15) {
		PARAM15 = pARAM15;
	}


	/**
	 * @return the rbauto15
	 */
	public String getRbauto15() {
		return rbauto15;
	}

	/**
	 * @param rbauto15
	 *            the rbauto15 to set
	 */
	public void setRbauto15(String rbauto15) {
		this.rbauto15 = rbauto15;
	}

	/**
	 * @return the rbauto16
	 */
	public String getRbauto16() {
		return rbauto16;
	}

	/**
	 * @param rbauto16
	 *            the rbauto16 to set
	 */
	public void setRbauto16(String rbauto16) {
		this.rbauto16 = rbauto16;
	}

	/**
	 * @return the rbauto17
	 */
	public String getRbauto17() {
		return rbauto17;
	}

	/**
	 * @param rbauto17
	 *            the rbauto17 to set
	 */
	public void setRbauto17(String rbauto17) {
		this.rbauto17 = rbauto17;
	}

	/**
	 * @return the rbauto18
	 */
	public String getRbauto18() {
		return rbauto18;
	}

	/**
	 * @param rbauto18
	 *            the rbauto18 to set
	 */
	public void setRbauto18(String rbauto18) {
		this.rbauto18 = rbauto18;
	}

	public String getLICENSE_NO() {
		return LICENSE_NO;
	}

	public void setLICENSE_NO(String lICENSENO) {
		LICENSE_NO = lICENSENO;
	}

	public String getORG_CODE() {
		return ORG_CODE;
	}

	public void setORG_CODE(String oRGCODE) {
		ORG_CODE = oRGCODE;
	}

	public String getIDENTITY() {
		return IDENTITY;
	}

	public void setIDENTITY(String iDENTITY) {
		IDENTITY = iDENTITY;
	}

	public String getMCHT_NM() {
		return MCHT_NM;
	}

	public void setMCHT_NM(String mCHTNM) {
		MCHT_NM = mCHTNM;
	}

	public String getMCHT_TYPE() {
		return MCHT_TYPE;
	}

	public void setMCHT_TYPE(String mCHTTYPE) {
		MCHT_TYPE = mCHTTYPE;
	}

	public String getMAIN_BUS_NUM() {
		return MAIN_BUS_NUM;
	}

	public void setMAIN_BUS_NUM(String mAINBUSNUM) {
		MAIN_BUS_NUM = mAINBUSNUM;
	}

	public String getRISK_INDUSTRY() {
		return RISK_INDUSTRY;
	}

	public void setRISK_INDUSTRY(String rISKINDUSTRY) {
		RISK_INDUSTRY = rISKINDUSTRY;
	}

	public String getPREMISES() {
		return PREMISES;
	}

	public void setPREMISES(String pREMISES) {
		PREMISES = pREMISES;
	}

	public String getPARAM1() {
		return PARAM1;
	}

	public void setPARAM1(String pARAM1) {
		PARAM1 = pARAM1;
	}

	public String getREG_FUND() {
		return REG_FUND;
	}

	public void setREG_FUND(String rEGFUND) {
		REG_FUND = rEGFUND;
	}

	public String getPARAM2() {
		return PARAM2;
	}

	public void setPARAM2(String pARAM2) {
		PARAM2 = pARAM2;
	}

	public String getPARAM3() {
		return PARAM3;
	}

	public void setPARAM3(String pARAM3) {
		PARAM3 = pARAM3;
	}

	public String getPARAM4() {
		return PARAM4;
	}

	public void setPARAM4(String pARAM4) {
		PARAM4 = pARAM4;
	}

	public String getPARAM5() {
		return PARAM5;
	}

	public void setPARAM5(String pARAM5) {
		PARAM5 = pARAM5;
	}

	public String getPARAM6() {
		return PARAM6;
	}

	public void setPARAM6(String pARAM6) {
		PARAM6 = pARAM6;
	}

	public String getPARAM7() {
		return PARAM7;
	}

	public void setPARAM7(String pARAM7) {
		PARAM7 = pARAM7;
	}

	public String getPARAM8() {
		return PARAM8;
	}

	public void setPARAM8(String pARAM8) {
		PARAM8 = pARAM8;
	}

	public String getPARAM9() {
		return PARAM9;
	}

	public void setPARAM9(String pARAM9) {
		PARAM9 = pARAM9;
	}

	public String getPARAM10() {
		return PARAM10;
	}

	public void setPARAM10(String pARAM10) {
		PARAM10 = pARAM10;
	}

	public String getPARAM11() {
		return PARAM11;
	}

	public void setPARAM11(String pARAM11) {
		PARAM11 = pARAM11;
	}

	public String getPARAM12() {
		return PARAM12;
	}

	public void setPARAM12(String pARAM12) {
		PARAM12 = pARAM12;
	}

	public String getPARAM13() {
		return PARAM13;
	}

	public void setPARAM13(String pARAM13) {
		PARAM13 = pARAM13;
	}

	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub
		try {
			if ("add".equals(getMethod())) {
				rspCode = add();
			} else if ("delete".equals(getMethod())) {
				rspCode = delete();
			}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId() + "，对事前风险维护操作" + getMethod()
					+ "失败，失败原因为：" + e.getMessage());
		}
		return rspCode;
	}

	private T40402BO t40402BO = (T40402BO) ContextUtil.getBean("T40402BO");

	private String add() throws IOException {
		//判断申请安装地区是否有选中内容
		if(!PARAM27.contains("true")&&!PARAM15.contains("true")&&!PARAM16.contains("true")
				&&!PARAM17.contains("true")&&!PARAM18.contains("true")&&!PARAM21.contains("true")
				&&!PARAM19.contains("true")&&!PARAM20.contains("true")&&!PARAM22.contains("true")
				&&!PARAM23.contains("true")&&!PARAM24.contains("true")&&!PARAM25.contains("true")){
			writeErrorMsg("必选项：申请安装地区，请选择");
			return Constants.FAILURE_CODE;
		}
			
		RiskBefore riskBefore = new RiskBefore();
		if (t40402BO.get(StringUtil.trim(MCHT_NM)) != null) {
			writeErrorMsg("该事前风险控制已经存在");
			return Constants.FAILURE_CODE;
		}
		String sql2 = "select * from RISK_BEFORE where LICENSE_NO = '"
				+ StringUtil.trim(LICENSE_NO) + "' and IDENTITY='" + StringUtil.trim(IDENTITY) + "'";
		//20120727组织结构代码改为可选，为空时不作为判断条件
		if(ORG_CODE!=null || !"".equals(ORG_CODE))
			sql2 += " and ORG_CODE='"+ StringUtil.trim(ORG_CODE)+ "'";
		
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql2);
		// 判断是否有相同的法人机构号一类的在表里面
		if (dataList.size() > 0) {
			writeErrorMsg("已存在相同的营业执照号组织机构代码法人身份证");
			return Constants.FAILURE_CODE;
		}
		riskBefore.setMCHT_NM(StringUtil.trim(MCHT_NM));
		riskBefore.setLICENSE_NO(StringUtil.trim(LICENSE_NO));
		riskBefore.setORG_CODE(StringUtil.trim(ORG_CODE));
		riskBefore.setIDENTITY(StringUtil.trim(IDENTITY));
		riskBefore.setMCHT_TYPE(MCHT_TYPE);
		riskBefore.setMAIN_BUS_NUM(MAIN_BUS_NUM);
		riskBefore.setREG_FUND(REG_FUND);
		riskBefore.setPREMISES(PREMISES);
		riskBefore.setRISK_INDUSTRY(RISK_INDUSTRY);
		riskBefore.setPARAM1(PARAM1);
		//riskBefore.setPARAM2(PARAM2);
		//riskBefore.setPARAM3(PARAM3);
		riskBefore.setPARAM4(PARAM4);
		riskBefore.setPARAM5(PARAM5);
		riskBefore.setPARAM6(PARAM6);
		//riskBefore.setPARAM7(PARAM7);
		//riskBefore.setPARAM8(PARAM8);
		//riskBefore.setPARAM9(PARAM9);
		//riskBefore.setPARAM10(PARAM10);
		//riskBefore.setPARAM11(PARAM11);
		riskBefore.setOPR_ID(operator.getOprId());
		riskBefore.setCREATE_TIME(CommonFunction.getCurrentDateTime());
//业务人员是否上门
		if (!StringUtil.isNull(PARAM12)) {
			riskBefore.setPARAM12("1");
		} else {
			riskBefore.setPARAM12("0");
		}
//其它的set
		StringBuffer str1=new StringBuffer();
		if (!StringUtil.isNull(rbauto15)) {
			str1.append(rbauto15);
		}else{
			str1.append("0");
		}
		if (!StringUtil.isNull(rbauto16)) {
			str1.append(rbauto16);
		}else{
			str1.append("0");
		}
		if (!StringUtil.isNull(rbauto17)) {
			str1.append(rbauto17);
		}else{
			str1.append("0");
		}
		if (!StringUtil.isNull(rbauto18)) {
			str1.append(rbauto18);
		}else{
			str1.append("0");
		}
		riskBefore.setPARAM13(str1.toString());
//申请安装地区
		StringBuffer str=new StringBuffer();
		if(PARAM27.contains("true")){
			str.append("1");
		}else{
			str.append("0");
		}
		if(PARAM15.contains("true")){
			str.append("1");
		}else{
			str.append("0");
		}
		if(PARAM16.contains("true")){
			str.append("1");
		}else{
			str.append("0");
		}
		if(PARAM17.contains("true")){
			str.append("1");
		}else{
			str.append("0");
		}
		if(PARAM18.contains("true")){
			str.append("1");
		}else{
			str.append("0");
		}
		if(PARAM21.contains("true")){
			str.append("1");
		}else{
			str.append("0");
		}
		if(PARAM19.contains("true")){
			str.append("1");
		}else{
			str.append("0");
		}
		if(PARAM20.contains("true")){
			str.append("1");
		}else{
			str.append("0");
		}
		if(PARAM22.contains("true")){
			str.append("1");
		}else{
			str.append("0");
		}
		if(PARAM23.contains("true")){
			str.append("1");
		}else{
			str.append("0");
		}
		if(PARAM24.contains("true")){
			str.append("1");
		}else{
			str.append("0");
		}
		if(PARAM25.contains("true")){
			str.append("1");
		}else{
			str.append("0");
		}
		riskBefore.setPARAM11(str.toString());
		int count = sum(MCHT_TYPE,MAIN_BUS_NUM,RISK_INDUSTRY,REG_FUND,PREMISES,PARAM1,PARAM4,PARAM5,PARAM6,riskBefore.getPARAM11(),riskBefore.getPARAM12(),riskBefore.getPARAM13());
		
		if (count >= 56) {
			riskBefore.setGRADE("1");// 优质商户
		}
		if (count >= 41 && count <= 55) {
			riskBefore.setGRADE("2");// 一般商户
		}
		if (count >= 31 && count <= 40) {
			riskBefore.setGRADE("3");// 有一定风险
		}
		if (count >= 21 && count <= 30) {
			riskBefore.setGRADE("4");// 风险倾向商户
		}
		if (count <= 20 ){//20120920修改，20分临界值的处理（全选第一项无法添加成功，报index：0的错）
			riskBefore.setGRADE("5");// 高风险商户
		}
		riskBefore.setSCORE(count + "");
		String grade = riskBefore.getGRADE();
		String sql = "select * from cst_sys_param a where a.KEY=" + grade
				+ " and OWNER='RISKLEVEL'";
		ICommQueryDAO dao = CommonFunction.getCommQueryDAO();
		List<CstSysParam> CstSysParamList = (List<CstSysParam>) dao.findBySQLQuery6(sql);
		CstSysParam cstSysParam = CstSysParamList.get(0);
		String gradename = cstSysParam.getValue();
		String des = cstSysParam.getReserve();
		riskBefore.setGradename(gradename);
		riskBefore.setDesc(des);
		riskBefore.setSastatue("0");
		t40402BO.add(riskBefore);
		log("添加事前风险控制信息成功。操作员编号：" + operator.getOprId() + "，事前风险控制："+ getMCHT_NM());
		writeSuccessMsg("分数:" + riskBefore.getSCORE() + "       风险等级："+ gradename + "        备注：" + des);
		return Constants.SUCCESS_CODE;
	}

	private int sum(String MCHT_TYPE,String MAIN_BUS_NUM,String RISK_INDUSTRY,String REG_FUND,String PREMISES,String  PARAM1,String PARAM4,String PARAM5,String PARAM6,String PARAM11,String PARAM12,String PARAM13) {
		int count = 0;
		if(MCHT_TYPE.equals("1")){
			count+=5;
		}else if(MCHT_TYPE.equals("2")){
			count+=3;
		}
		
		if(MAIN_BUS_NUM.equals("2")){
			count+=2;
		}else if(MAIN_BUS_NUM.equals("3")){
			count+=3;
		}else if(MAIN_BUS_NUM.equals("4")){
			count+=4;
		}else if(MAIN_BUS_NUM.equals("5")){
			count+=5;
		}else{
			count+=0;
		}
		
		if(RISK_INDUSTRY.equals("1")){
			count+=0;
		}else if(RISK_INDUSTRY.equals("2")){
			count+=2;
		}else if(RISK_INDUSTRY.equals("3")){
			count+=3;
		}else if(RISK_INDUSTRY.equals("4")){
			count+=4;
		}else if(RISK_INDUSTRY.equals("5")){
			count+=5;
		}else if(RISK_INDUSTRY.equals("6")){
			count+=3;
		}else{
			count+=3;
		}
		//商户注册资本
		if(REG_FUND.equals("1") || REG_FUND.equals("7")){
			count+=0;
		}else if(REG_FUND.equals("2")){
			count+=1;
		}else if(REG_FUND.equals("3")){
			count+=2;
		}else if(REG_FUND.equals("4")){
			count+=3;
		}else if(REG_FUND.equals("5")){
			count+=4;
		}else if(REG_FUND.equals("6")){
			count+=5;
		}
		//企业性质
		if(PREMISES.equals("03")){//国有企业
			count+=5;
		}else if(PREMISES.equals("05")){//股份有限公司
			count+=5;
		}else if(PREMISES.equals("07")){//集体所有企业
			count+=4;
		}else if(PREMISES.equals("08")){//中外合资、合作企业
			count+=3;
		}else if(PREMISES.equals("09")){//外商独资企业
			count+=3;
		}else if(PREMISES.equals("04")){//私营合伙企业
			count+=2;
		}else if(PREMISES.equals("02")){//私营独资企业
			count+=1;
		}else if(PREMISES.equals("01")){//个体工商户
			count+=1;
		}else
//		if(PREMISES.equals("10")){//个人
//			count=count+0;
//		}
		if(PREMISES.equals("10")){//事业单位
			count+=5;
		}else{//其他
			count+=0;
		}
	//主营业务数量范围
		if(PARAM1.equals("1")){
			count+=5;
		}else if(PARAM1.equals("2")){
			count+=3;
		}else if(PARAM1.equals("3")){
			count+=2;
		}else if(PARAM1.equals("4")){
			count+=1;
		}else if(PARAM1.equals("5")){
			count+=0;
		}
		
	//商户来源
		if(PARAM4.equals("1")){
			count+=5;
		}else if(PARAM4.equals("2")){
			count+=4;
		}else
		if(PARAM4.equals("3")){
			count+=2;
		}else if(PARAM4.equals("4")){
			count+=0;
		}
		
		//商户服务类别码
		if(PARAM5.equals("9")){
			count+=5;
		}else if(PARAM5.equals("10")){
			count+=5;
		}else if(PARAM5.equals("11")){
			count+=5;
		}else if(PARAM5.equals("12")){
			count+=5;
		}else if(PARAM5.equals("13")){
			count+=5;
		}else if(PARAM5.equals("14")){
			count+=5;
		}else if(PARAM5.equals("15")){
			count+=5;
		}else if(PARAM5.equals("16")){
			count+=5;
		}else if(PARAM5.equals("17")){
			count+=10;
		}else{
			count+=0;
		}
		//商户交易类型
		if(PARAM6.equals("1")){
			count+=5;
		}else if(PARAM6.equals("2")){
			count+=2;
		}else if(PARAM6.equals("3")){
			count+=2;
		}else if(PARAM6.equals("4")){
			count+=2;
		}
	
	//申请安装地区
		if(PARAM11.substring(4, 7).contains("1")){
			count+=3;
		}else if(PARAM11.substring(7, 11).contains("1")){
			count+=4;
		}else if(PARAM11.substring(11, 12).contains("1")){
			count+=5;
		}else{
			count+=0;
		}
		
	//业务人员是否上门
		if(PARAM12.equals("0")){
			count+=0;
		}else if(PARAM12.equals("1")){
			count+=5;
		}
	//其它
		if(PARAM13.substring(0, 1).equals("1")){
			count+=2;
		}
		if(PARAM13.substring(1, 2).equals("1")){
			count+=1;
		}
		if(PARAM13.substring(2, 3).equals("1")){
			count+=1;
		}
		if(PARAM13.substring(3, 4).equals("1")){
			count+=1;
		}
		return count;
	}

	private String delete() {
		RiskBefore riskBefore = t40402BO.get(MCHT_NM);
		String state = riskBefore.getSastatue();
		if (CommonFunction.ADD_TO_CHECK.equals(state)) {
			t40402BO.delete(riskBefore);
		} else {
			riskBefore.setSastatue(CommonFunction.DELETE_TO_CHECK);
			List<RiskBefore> riskBeforeList = new ArrayList<RiskBefore>();
			riskBeforeList.add(riskBefore);
			t40402BO.update(riskBeforeList);
		}
		return Constants.SUCCESS_CODE;

	}
}

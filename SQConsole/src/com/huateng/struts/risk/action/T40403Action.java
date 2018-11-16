package com.huateng.struts.risk.action;

import java.util.ArrayList;
import java.util.List;

import com.huateng.bo.risk.T40206BO;
import com.huateng.bo.risk.T40402BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.po.risk.RiskBefore;
import com.huateng.po.risk.TblRiskRefuse;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

public class T40403Action extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private String mcht_Nm;
    private String refuseInfo;
	/**
	 * @return the refuseInfo
	 */
	public String getRefuseInfo() {
		return refuseInfo;
	}

	/**
	 * @param refuseInfo the refuseInfo to set
	 */
	public void setRefuseInfo(String refuseInfo) {
		this.refuseInfo = refuseInfo;
	}

	/**
	 * @return the mcht_Nm
	 */
	public String getMcht_Nm() {
		return mcht_Nm;
	}

	/**
	 * @param mchtNm the mcht_Nm to set
	 */
	public void setMcht_Nm(String mchtNm) {
		mcht_Nm = mchtNm;
	}
	private T40402BO t40402BO = (T40402BO) ContextUtil.getBean("T40402BO");
	private T40206BO t40206bo = (T40206BO) ContextUtil.getBean("T40206BO");
	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub
		//在新增、修改、冻结、恢复和注销时，修改人和操作人不能使同一人
		String sql = "SELECT OPR_ID FROM RISK_BEFORE WHERE MCHT_NM= '" + mcht_Nm + "'";
		List<String> list = commQueryDAO.findBySQLQuery(sql);
		if (null != list && !list.isEmpty()) {
			if (!StringUtil.isNull(list.get(0))) {
				if(list.get(0).equals(operator.getOprId())){
					return "同一操作员不能审核！";
				}
			}
		}
		try {
			if("accept".equals(getMethod())) {			
					rspCode = accept();			
			} else if("refuse".equals(getMethod())) {
				rspCode = refuse();
			}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，对事前风险控制的审核操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	private String accept() {
		// TODO Auto-generated method stub
		RiskBefore riskBefore=t40402BO.get(mcht_Nm);
		String state=riskBefore.getSastatue();
		if(CommonFunction.ADD_TO_CHECK.equals(state)){
			riskBefore.setSastatue(CommonFunction.NORMAL);
		}
		if(CommonFunction.DELETE_TO_CHECK.equals(state)){
			riskBefore.setSastatue(CommonFunction.DELETE);
		}
		riskBefore.setUPD_OPR_ID(operator.getOprId());
		riskBefore.setUPD_TIME(CommonFunction.getCurrentDateTime());
		List<RiskBefore> riskBeforeList=new ArrayList<RiskBefore>();
		riskBeforeList.add(riskBefore);
		t40402BO.update(riskBeforeList);
		return Constants.SUCCESS_CODE;
	}
	private String refuse() throws Exception {
		// TODO Auto-generated method stub
		TblRiskRefuse tblRiskRefuse=new TblRiskRefuse();
		RiskBefore riskBefore=t40402BO.get(mcht_Nm);
		String state=riskBefore.getSastatue();
		if(CommonFunction.ADD_TO_CHECK.equals(state)){
			t40402BO.delete(riskBefore);
		}
		if(CommonFunction.DELETE_TO_CHECK.equals(state)){
			riskBefore.setSastatue(CommonFunction.NORMAL);
		}
		riskBefore.setUPD_OPR_ID(operator.getOprId());
		riskBefore.setUPD_TIME(CommonFunction.getCurrentDateTime());
		List<RiskBefore> riskBeforeList=new ArrayList<RiskBefore>();
		riskBeforeList.add(riskBefore);
		tblRiskRefuse.setRefuseType(state);
		tblRiskRefuse.setRefuseInfo(getRefuseInfo());
		tblRiskRefuse.setOprId(operator.getOprId());
		tblRiskRefuse.setBrhId(operator.getOprBrhId());
		tblRiskRefuse.setParam1(riskBefore.getMCHT_NM());
		tblRiskRefuse.setParam2(riskBefore.getMccName());
		tblRiskRefuse.setParam3(riskBefore.getMCHT_TYPE());
		tblRiskRefuse.setParam4(riskBefore.getGRADE());
		tblRiskRefuse.setParam5(riskBefore.getSCORE());
		tblRiskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
		tblRiskRefuse.setFlag("91");
		t40206bo.saveRefuseInfo(tblRiskRefuse);
		t40402BO.update(riskBeforeList);
		return Constants.SUCCESS_CODE;
	}

	

}

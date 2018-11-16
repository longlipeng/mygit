package com.huateng.struts.rout.action;

import com.huateng.bo.risk.T40206BO;
import com.huateng.bo.rout.T11011BO;
import com.huateng.bo.rout.T11012BO;
import com.huateng.common.Constants;
import com.huateng.po.risk.TblRiskRefuse;
import com.huateng.po.rout.TblRouteInfo;
import com.huateng.po.rout.TblRouteInfoTemp;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

public class T11012Action extends BaseAction {

	private static final long serialVersionUID = 1L;

	private T11011BO t11011BO = (T11011BO)ContextUtil.getBean("T11011BO");
	private T11012BO t11012BO = (T11012BO)ContextUtil.getBean("T11012BO");
	T40206BO t40206bo = (T40206BO) ContextUtil.getBean("T40206BO");
	
	public static String ADD_TO_CHECK = "0";
	public static String DELETE = "1";
	public static String NORMAL = "2";
	public static String MODIFY_TO_CHECK = "3";
	public static String DELETE_TO_CHECK = "4";
	
	//primary key
	private String reserved;
	//拒绝原因
	private String refuseInfo;
	
	public String getReserved() {
		return reserved;
	}

	public void setReserved(String reserved) {
		this.reserved = reserved;
	}

	public String getRefuseInfo() {
		return refuseInfo;
	}

	public void setRefuseInfo(String refuseInfo) {
		this.refuseInfo = refuseInfo;
	}

	@Override
	protected String subExecute() throws Exception {
		log("操作员：" + operator.getOprId());
		for(int i=0 ; i<this.getReserved().split(";").length ; i++){
			TblRouteInfoTemp tblRouteInfoTemp = t11011BO.get(this.getReserved().split(";")[i]);
			if(this.checkOperator(tblRouteInfoTemp.getCreatorId()))
				return "存在提交人和当前审核人是同一人的记录，请重新选择！";
		}
		
		for(int i=0 ; i<this.getReserved().split(";").length ; i++){
			String id = this.getReserved().split(";")[i];
			TblRouteInfoTemp tblRouteInfoTemp = t11011BO.get(id);
			String state = tblRouteInfoTemp.getSaState();
			if("accept".equals(method)) {
				if(state.equals(ADD_TO_CHECK)){
					rspCode = acceptAdd(id);
				}
				if(state.equals(DELETE_TO_CHECK)){
					rspCode = acceptDelete(id);
				}
				if(state.equals(MODIFY_TO_CHECK)){
					rspCode = acceptModify(id);
				}
			} else if("refuse".equals(method)) {
				if(state.equals(ADD_TO_CHECK)){
					rspCode = refuseAdd(id);
				}
				if(state.equals(DELETE_TO_CHECK)){
					rspCode = refuseDelete(id);
				}
				if(state.equals(MODIFY_TO_CHECK)){
					rspCode = refuseModify(id);
				}
			}
		}
		return rspCode;
	}
	
	/**
	 * 审核通过新增待审核的路由信息
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String acceptAdd(String id) throws Exception {
		TblRouteInfoTemp tblRouteInfoTemp = t11011BO.get(id);
		tblRouteInfoTemp.setCheckTime(CommonFunction.getCurrentDateTime());
		tblRouteInfoTemp.setCheckId(operator.getOprId());
		tblRouteInfoTemp.setSaState(NORMAL);
		t11011BO.update(tblRouteInfoTemp);
		
		TblRouteInfo tblRouteInfo = new TblRouteInfo();
		tblRouteInfo = (TblRouteInfo) tblRouteInfoTemp.clone(tblRouteInfo);
		rspCode = t11012BO.add(tblRouteInfo);
		log("审核通过新增待审核的路由信息");
		return rspCode;
	}
	
	/**
	 * 审核拒绝新增待审核的路由信息
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String refuseAdd(String id) throws Exception {
		TblRouteInfoTemp tblRouteInfoTemp = new TblRouteInfoTemp();
		tblRouteInfoTemp =  t11011BO.get(id);
		t11011BO.delete(id);
		
		//保存拒绝原因
		TblRiskRefuse riskRefuse = new TblRiskRefuse();
		riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
		riskRefuse.setParam1(tblRouteInfoTemp.getCardBin());
		riskRefuse.setParam2(tblRouteInfoTemp.getBussType());
		riskRefuse.setParam3(tblRouteInfoTemp.getChannel() +";"+ tblRouteInfoTemp.getDestInstId());
		riskRefuse.setParam4(tblRouteInfoTemp.getAreaNo());
		riskRefuse.setReserve1(tblRouteInfoTemp.getCardType());	//卡类型
		riskRefuse.setParam5(tblRouteInfoTemp.getMchntId());
		riskRefuse.setParam6(tblRouteInfoTemp.getTxnNum());
		riskRefuse.setBrhId(operator.getOprBrhId());
		riskRefuse.setOprId(operator.getOprId());
		riskRefuse.setRefuseType(ADD_TO_CHECK);
		riskRefuse.setRefuseInfo(refuseInfo);
		riskRefuse.setFlag("13");
		
		t40206bo.saveRefuseInfo(riskRefuse);
		log("审核拒绝新增待审核的路由信息");
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 审核通过删除待审核的路由信息
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String acceptDelete(String id) throws Exception {
		TblRouteInfoTemp tblRouteInfoTemp = t11011BO.get(id);
		tblRouteInfoTemp.setCheckTime(CommonFunction.getCurrentDateTime());
		tblRouteInfoTemp.setCheckId(operator.getOprId());
		tblRouteInfoTemp.setSaState(DELETE);
		t11011BO.update(tblRouteInfoTemp);
		
		TblRouteInfo tblRouteInfo = new TblRouteInfo();
		tblRouteInfo = (TblRouteInfo) tblRouteInfoTemp.clone(tblRouteInfo);
		rspCode = t11012BO.update(tblRouteInfo);
		log("审核通过删除待审核的路由信息");
		return rspCode;
	}
	
	/**
	 * 审核拒绝删除待审核的路由信息
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String refuseDelete(String id) throws Exception {
		TblRouteInfoTemp tblRouteInfoTemp = new TblRouteInfoTemp();
		tblRouteInfoTemp = t11011BO.get(id);
		tblRouteInfoTemp.setCheckTime(CommonFunction.getCurrentDateTime());
		tblRouteInfoTemp.setCheckId(operator.getOprId());
		tblRouteInfoTemp.setSaState(NORMAL);
		t11011BO.update(tblRouteInfoTemp);
		
		TblRouteInfo tblRouteInfo = new TblRouteInfo();
		tblRouteInfo = (TblRouteInfo) tblRouteInfoTemp.clone(tblRouteInfo);
		rspCode = t11012BO.update(tblRouteInfo);
		
		TblRiskRefuse riskRefuse = new TblRiskRefuse();
		riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
		riskRefuse.setParam1(tblRouteInfoTemp.getCardBin());
		riskRefuse.setParam2(tblRouteInfoTemp.getBussType());
		riskRefuse.setParam3(tblRouteInfoTemp.getChannel()+";"+ tblRouteInfoTemp.getDestInstId());
		riskRefuse.setParam4(tblRouteInfoTemp.getAreaNo());
		riskRefuse.setReserve1(tblRouteInfoTemp.getCardType());	//卡类型
		riskRefuse.setParam5(tblRouteInfoTemp.getMchntId());
		riskRefuse.setParam6(tblRouteInfoTemp.getTxnNum());
		riskRefuse.setBrhId(operator.getOprBrhId());
		riskRefuse.setOprId(operator.getOprId());
		riskRefuse.setRefuseType(DELETE_TO_CHECK);
		riskRefuse.setRefuseInfo(refuseInfo);
		riskRefuse.setFlag("13");
		
		t40206bo.saveRefuseInfo(riskRefuse);
		log("审核拒绝删除待审核的路由信息");
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 审核通过修改待审核的路由信息
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String acceptModify(String id) throws Exception {
		TblRouteInfoTemp tblRouteInfoTemp = t11011BO.get(id);
		tblRouteInfoTemp.setCheckTime(CommonFunction.getCurrentDateTime());
		tblRouteInfoTemp.setCheckId(operator.getOprId());
		tblRouteInfoTemp.setSaState(NORMAL);
		t11011BO.update(tblRouteInfoTemp);
		
		//将修改后的字段覆盖原字段
		TblRouteInfo tblRouteInfo = new TblRouteInfo();
		tblRouteInfo = (TblRouteInfo) tblRouteInfoTemp.clone(tblRouteInfo);
		rspCode = t11012BO.update(tblRouteInfo);
		log("审核通过修改待审核的路由信息");
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 审核拒绝修改待审核的路由信息
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String refuseModify(String id) throws Exception {
		TblRouteInfoTemp tblRouteInfoTemp = new TblRouteInfoTemp();
		TblRouteInfo tblRouteInfo = new TblRouteInfo();
		
		//用原字段值覆盖修改后的值
		tblRouteInfo = t11012BO.get(id);
		tblRouteInfo.setCheckTime(CommonFunction.getCurrentDateTime());
		tblRouteInfo.setCheckId(operator.getOprId());
		tblRouteInfo.setSaState(NORMAL);
		rspCode = t11012BO.update(tblRouteInfo);
		
		tblRouteInfoTemp = (TblRouteInfoTemp) tblRouteInfo.clone(tblRouteInfoTemp);
		tblRouteInfoTemp.setCheckTime(CommonFunction.getCurrentDateTime());
		tblRouteInfoTemp.setCheckId(operator.getOprId());
		tblRouteInfoTemp.setSaState(NORMAL);
		t11011BO.update(tblRouteInfoTemp);
		
		TblRiskRefuse riskRefuse = new TblRiskRefuse();
		riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
		riskRefuse.setParam1(tblRouteInfo.getCardBin());
		riskRefuse.setParam2(tblRouteInfo.getBussType());
		riskRefuse.setParam3(tblRouteInfo.getChannel()+";"+ tblRouteInfoTemp.getDestInstId());
		riskRefuse.setParam4(tblRouteInfo.getAreaNo());
		riskRefuse.setParam5(tblRouteInfo.getMchntId());
		riskRefuse.setParam6(tblRouteInfo.getTxnNum());
		riskRefuse.setReserve1(tblRouteInfo.getCardType());	//卡类型
		riskRefuse.setBrhId(operator.getOprBrhId());
		riskRefuse.setOprId(operator.getOprId());
		riskRefuse.setRefuseType(MODIFY_TO_CHECK);
		riskRefuse.setRefuseInfo(refuseInfo);
		riskRefuse.setFlag("13");
		
		t40206bo.saveRefuseInfo(riskRefuse);
		log("审核拒绝修改待审核的路由信息");
		return Constants.SUCCESS_CODE;
	}
	
	//判断操作人和审核人是否相同
	private boolean checkOperator(String id)throws Exception {
		if(id==null || "".equals(id)){
			return true;
		}
		if(id.equals(operator.getOprId()))
			return true;//相同
		else
			return false;//不同
	}
}

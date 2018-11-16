package com.huateng.struts.risk.action;

import com.huateng.bo.risk.T40206BO;
import com.huateng.bo.risk.T40216BO;
import com.huateng.common.Constants;
import com.huateng.log.Log;
import com.huateng.po.risk.TblRiskTermTranLimit;
import com.huateng.po.risk.TblRiskRefuse;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

public class T40217Action extends BaseAction {

	private static final long serialVersionUID = 1L;

	T40216BO t40216bo = (T40216BO) ContextUtil.getBean("T40216BO");
	T40206BO t40206bo = (T40206BO) ContextUtil.getBean("T40206BO");
	
	public static String ADD_TO_CHECK = "0";
	public static String DELETE = "1";
	public static String NORMAL = "2";
	public static String MODIFY_TO_CHECK = "3";
	public static String DELETE_TO_CHECK = "4";
	
	@Override
	protected String subExecute() throws Exception {
		log("操作员：" + operator.getOprId());
		
		TblRiskTermTranLimit tblRiskTermTranLimit = t40216bo.get(id);
		String state = tblRiskTermTranLimit.getSaState();
		if("accept".equals(method)) {
			if(state.equals(ADD_TO_CHECK)){
				log("审核通过新增待审核的终端交易权限");
				rspCode = acceptAdd();
			}
			if(state.equals(DELETE_TO_CHECK)){
				log("审核通过删除待审核的终端交易权限");
				rspCode = acceptDelete();
			}
			if(state.equals(MODIFY_TO_CHECK)){
				log("审核通过修改待审核的终端交易权限");
				rspCode = acceptModify();
			}
		} else if("refuse".equals(method)) {
			if(state.equals(ADD_TO_CHECK)){
				log("审核拒绝新增待审核的终端交易权限");
				rspCode = refuseAdd();
			}
			if(state.equals(DELETE_TO_CHECK)){
				log("审核拒绝删除待审核的终端交易权限");
				rspCode = refuseDelete();
			}
			if(state.equals(MODIFY_TO_CHECK)){
				log("审核拒绝修改待审核的终端交易权限");
				rspCode = refuseModify();
			}
		}
		return rspCode;
	}

	/**
	 * 审核通过新增待审核的终端交易权限
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String acceptAdd() throws Exception {
		if(this.checkOperator())
			return "提交人与审核人不能是同一个人";
		else{
			TblRiskTermTranLimit tblRiskTermTranLimit = t40216bo.get(id);
			tblRiskTermTranLimit.setUpdateTime(CommonFunction.getCurrentDateTime());
			tblRiskTermTranLimit.setOprID(operator.getOprId());
			tblRiskTermTranLimit.setSaState(NORMAL);
			//修改后的修改前的BussType修改前的
			tblRiskTermTranLimit.setBussType(tblRiskTermTranLimit.getBussTypeOld());
			tblRiskTermTranLimit.setTxnNum(tblRiskTermTranLimit.getTxnNumOld());
			tblRiskTermTranLimit.setCardType(tblRiskTermTranLimit.getCardTypeOld());
			tblRiskTermTranLimit.setChannel(tblRiskTermTranLimit.getChannelOld());
			rspCode = t40216bo.update(tblRiskTermTranLimit);
		}
		return rspCode;
	}
	
	/**
	 * 审核拒绝新增待审核的终端交易权限
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String refuseAdd() throws Exception {
		TblRiskTermTranLimit tblRiskTermTranLimit = new TblRiskTermTranLimit();
		if(this.checkOperator())
			return "提交人与审核人不能是同一个人";
		else{
			tblRiskTermTranLimit = t40216bo.get(id);
			t40216bo.delete(id);
		}
		//保存拒绝原因
		TblRiskRefuse riskRefuse = new TblRiskRefuse();
		riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
		riskRefuse.setParam1(tblRiskTermTranLimit.getTermID());
		riskRefuse.setParam2(tblRiskTermTranLimit.getChannel());
		riskRefuse.setParam3(tblRiskTermTranLimit.getBussType());
		riskRefuse.setParam4(tblRiskTermTranLimit.getTxnNum());
		riskRefuse.setParam5(tblRiskTermTranLimit.getCardType());
		riskRefuse.setParam6(tblRiskTermTranLimit.getLimit());
		riskRefuse.setBrhId(operator.getOprBrhId());
		riskRefuse.setOprId(operator.getOprId());
		riskRefuse.setRefuseType(ADD_TO_CHECK);
		riskRefuse.setRefuseInfo(refuseInfo);
		riskRefuse.setFlag("5");
		
		t40206bo.saveRefuseInfo(riskRefuse);
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 审核通过删除待审核的终端交易权限
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String acceptDelete() throws Exception {
		if(this.checkOperator())
			return "提交人与审核人不能是同一个人";
		else{
			TblRiskTermTranLimit tblRiskTermTranLimit = t40216bo.get(id);
			tblRiskTermTranLimit.setUpdateTime(CommonFunction.getCurrentDateTime());
			tblRiskTermTranLimit.setOprID(operator.getOprId());
			tblRiskTermTranLimit.setSaState(DELETE);
			//修改后的修改前的BussType修改前的
			tblRiskTermTranLimit.setBussType(tblRiskTermTranLimit.getBussTypeOld());
			tblRiskTermTranLimit.setTxnNum(tblRiskTermTranLimit.getTxnNumOld());
			tblRiskTermTranLimit.setCardType(tblRiskTermTranLimit.getCardTypeOld());
			tblRiskTermTranLimit.setChannel(tblRiskTermTranLimit.getChannelOld());
			rspCode = t40216bo.update(tblRiskTermTranLimit);
		}
		return rspCode;
	}
	
	/**
	 * 审核拒绝删除待审核的终端交易权限
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String refuseDelete() throws Exception {
		TblRiskTermTranLimit tblRiskTermTranLimit = new TblRiskTermTranLimit();
		String termID= "";
		String channel ="";
		String bussType ="";
		String txnNum = "";
		String cardType ="";
		String limit ="";
		if(this.checkOperator())
			return "提交人与审核人不能是同一个人";
		else{
			tblRiskTermTranLimit = t40216bo.get(id);
			termID = tblRiskTermTranLimit.getTermID();
			channel = tblRiskTermTranLimit.getChannel();
			bussType = tblRiskTermTranLimit.getBussType();
			txnNum = tblRiskTermTranLimit.getTxnNum();
			cardType = tblRiskTermTranLimit.getCardType();
			limit = tblRiskTermTranLimit.getLimit();
			tblRiskTermTranLimit.setUpdateTime(CommonFunction.getCurrentDateTime());
			tblRiskTermTranLimit.setOprID(operator.getOprId());
			tblRiskTermTranLimit.setSaState(NORMAL);
			//修改前的给修改后的TxnNumOld是修改后的
			tblRiskTermTranLimit.setTxnNumOld(tblRiskTermTranLimit.getTxnNum());
			tblRiskTermTranLimit.setBussTypeOld(tblRiskTermTranLimit.getBussType());
			tblRiskTermTranLimit.setCardTypeOld(tblRiskTermTranLimit.getCardType());
			tblRiskTermTranLimit.setChannelOld(tblRiskTermTranLimit.getChannel());
			rspCode = t40216bo.update(tblRiskTermTranLimit);
		}
		
		TblRiskRefuse riskRefuse = new TblRiskRefuse();
		riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
		riskRefuse.setParam1(termID);
		riskRefuse.setParam2(channel);
		riskRefuse.setParam3(bussType);
		riskRefuse.setParam4(txnNum);
		riskRefuse.setParam5(cardType);
		riskRefuse.setParam6(limit);
		riskRefuse.setBrhId(operator.getOprBrhId());
		riskRefuse.setOprId(operator.getOprId());
		riskRefuse.setRefuseType(DELETE_TO_CHECK);
		riskRefuse.setRefuseInfo(refuseInfo);
		riskRefuse.setFlag("5");//商户交易权限审核
		
		t40206bo.saveRefuseInfo(riskRefuse);
		return rspCode;
	}
	
	/**
	 * 审核通过修改待审核的终端交易权限
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String acceptModify() throws Exception {
		if(this.checkOperator())
			return "提交人与审核人不能是同一个人";
		else{
			TblRiskTermTranLimit tblRiskTermTranLimit = t40216bo.get(id);
			tblRiskTermTranLimit.setUpdateTime(CommonFunction.getCurrentDateTime());
			tblRiskTermTranLimit.setOprID(operator.getOprId());
			tblRiskTermTranLimit.setSaState(NORMAL);
			//修改后的修改前的BussType修改前的
			tblRiskTermTranLimit.setBussType(tblRiskTermTranLimit.getBussTypeOld());
			tblRiskTermTranLimit.setTxnNum(tblRiskTermTranLimit.getTxnNumOld());
			tblRiskTermTranLimit.setCardType(tblRiskTermTranLimit.getCardTypeOld());
			tblRiskTermTranLimit.setChannel(tblRiskTermTranLimit.getChannelOld());
			rspCode = t40216bo.update(tblRiskTermTranLimit);
		}
		return rspCode;
	}
	
	/**
	 * 审核拒绝修改待审核的终端交易权限
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String refuseModify() throws Exception {
		TblRiskTermTranLimit tblRiskTermTranLimit = new TblRiskTermTranLimit();
		String termID= "";
		String channel ="";
		String bussType ="";
		String txnNum = "";
		String cardType ="";
		String limit ="";
		if(this.checkOperator())
			return "提交人与审核人不能是同一个人";
		else{
			tblRiskTermTranLimit = t40216bo.get(id);
			tblRiskTermTranLimit.setUpdateTime(CommonFunction.getCurrentDateTime());
			tblRiskTermTranLimit.setOprID(operator.getOprId());
			tblRiskTermTranLimit.setSaState(NORMAL);
			termID = tblRiskTermTranLimit.getTermID();
			channel = tblRiskTermTranLimit.getChannel();
			bussType = tblRiskTermTranLimit.getBussType();
			txnNum = tblRiskTermTranLimit.getTxnNum();
			cardType = tblRiskTermTranLimit.getCardType();
			limit = tblRiskTermTranLimit.getLimit();
			
			//修改前的给修改后的TxnNumOld是修改后的
			tblRiskTermTranLimit.setTxnNumOld(tblRiskTermTranLimit.getTxnNum());
			tblRiskTermTranLimit.setBussTypeOld(tblRiskTermTranLimit.getBussType());
			tblRiskTermTranLimit.setCardTypeOld(tblRiskTermTranLimit.getCardType());
			tblRiskTermTranLimit.setChannelOld(tblRiskTermTranLimit.getChannel());
			rspCode = t40216bo.update(tblRiskTermTranLimit);
		}
		
		TblRiskRefuse riskRefuse = new TblRiskRefuse();
		riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
		riskRefuse.setParam1(termID);
		riskRefuse.setParam2(channel);
		riskRefuse.setParam3(bussType);
		riskRefuse.setParam4(txnNum);
		riskRefuse.setParam5(cardType);
		riskRefuse.setParam6(limit);
		riskRefuse.setBrhId(operator.getOprBrhId());
		riskRefuse.setOprId(operator.getOprId());
		riskRefuse.setRefuseType(MODIFY_TO_CHECK);
		riskRefuse.setRefuseInfo(refuseInfo);
		riskRefuse.setFlag("5");
		
		t40206bo.saveRefuseInfo(riskRefuse);
		return rspCode;
	}
	
	//判断终端交易权限的操作人和审核人是否相同
	private boolean checkOperator()throws Exception {
		TblRiskTermTranLimit tblRiskTermTranLimit = t40216bo.get(id);
		String oprID = tblRiskTermTranLimit.getCreid();
		Log.log("修改人："+oprID+" 审核人"+operator.getOprId());
		if(operator.getOprId().equals(oprID))
			return true;//相同
		else
			return false;//不同
	}
	
	// 
	private String id;
	//拒绝原因
	private String refuseInfo;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRefuseInfo() {
		return refuseInfo;
	}
	public void setRefuseInfo(String refuseInfo) {
		this.refuseInfo = refuseInfo;
	}
}

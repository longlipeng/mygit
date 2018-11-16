package com.huateng.struts.risk.action;

import com.huateng.bo.risk.T40206BO;
import com.huateng.bo.risk.T40214BO;
import com.huateng.common.Constants;
import com.huateng.log.Log;
import com.huateng.po.risk.TblRiskMchtTranLimit;
import com.huateng.po.risk.TblRiskRefuse;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

public class T40215Action extends BaseAction {

	private static final long serialVersionUID = 1L;

	T40214BO t40214bo = (T40214BO) ContextUtil.getBean("T40214BO");
	T40206BO t40206bo = (T40206BO) ContextUtil.getBean("T40206BO");
	
	public static String ADD_TO_CHECK = "0";
	public static String DELETE = "1";
	public static String NORMAL = "2";
	public static String MODIFY_TO_CHECK = "3";
	public static String DELETE_TO_CHECK = "4";
	
	@Override
	protected String subExecute() throws Exception {
		log("操作员：" + operator.getOprId());
		
		TblRiskMchtTranLimit tblRiskMchtTranLimit = t40214bo.get(id);
		String state = tblRiskMchtTranLimit.getSaState();
		if("accept".equals(method)) {
			if(state.equals(ADD_TO_CHECK)){
				log("审核通过新增待审核的商户交易权限");
				rspCode = acceptAdd();
			}
			if(state.equals(DELETE_TO_CHECK)){
				log("审核通过删除待审核的商户交易权限");
				rspCode = acceptDelete();
			}
			if(state.equals(MODIFY_TO_CHECK)){
				log("审核通过修改待审核的商户交易权限");
				rspCode = acceptModify();
			}
		} else if("refuse".equals(method)) {
			if(state.equals(ADD_TO_CHECK)){
				log("审核拒绝新增待审核的商户交易权限");
				rspCode = refuseAdd();
			}
			if(state.equals(DELETE_TO_CHECK)){
				log("审核拒绝删除待审核的商户交易权限");
				rspCode = refuseDelete();
			}
			if(state.equals(MODIFY_TO_CHECK)){
				log("审核拒绝修改待审核的商户交易权限");
				rspCode = refuseModify();
			}
		}
		return rspCode;
	}

	/**
	 * 审核通过新增待审核的商户交易权限
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String acceptAdd() throws Exception {
		if(this.checkOperator())
			return "提交人与审核人不能是同一个人";
		else{
			TblRiskMchtTranLimit tblRiskMchtTranCtl = t40214bo.get(id);
			tblRiskMchtTranCtl.setUpdateTime(CommonFunction.getCurrentDateTime());
			tblRiskMchtTranCtl.setOprId(operator.getOprId());
			tblRiskMchtTranCtl.setSaState(NORMAL);
			//将通过的数据存到原始的数据中BussType是修改前的
			tblRiskMchtTranCtl.setBussType(tblRiskMchtTranCtl.getBussTypeOld());
			tblRiskMchtTranCtl.setTxnNum(tblRiskMchtTranCtl.getTxnNumOld());
			tblRiskMchtTranCtl.setChannel(tblRiskMchtTranCtl.getChannelOld());
			tblRiskMchtTranCtl.setCardType(tblRiskMchtTranCtl.getCardTypeOld());
			rspCode = t40214bo.update(tblRiskMchtTranCtl);
		}
		return rspCode;
	}
	
	/**
	 * 审核拒绝新增待审核的商户交易权限
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String refuseAdd() throws Exception {
		TblRiskMchtTranLimit tblRiskMchtTranLimit = new TblRiskMchtTranLimit();
		if(this.checkOperator())
			return "提交人与审核人不能是同一个人";
		else{
			tblRiskMchtTranLimit = t40214bo.get(id);
			t40214bo.delete(id);
		}
		//保存拒绝原因
		TblRiskRefuse riskRefuse = new TblRiskRefuse();
		riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
		riskRefuse.setParam1(tblRiskMchtTranLimit.getMchtNo());
		riskRefuse.setParam2(tblRiskMchtTranLimit.getChannel());
		riskRefuse.setParam3(tblRiskMchtTranLimit.getBussType());
		riskRefuse.setParam4(tblRiskMchtTranLimit.getTxnNum());
		riskRefuse.setParam5(tblRiskMchtTranLimit.getCardType());
		riskRefuse.setParam6(tblRiskMchtTranLimit.getLimit());
		riskRefuse.setBrhId(operator.getOprBrhId());
		riskRefuse.setOprId(operator.getOprId());
		riskRefuse.setRefuseType(ADD_TO_CHECK);
		riskRefuse.setRefuseInfo(refuseInfo);
		riskRefuse.setFlag("4");
		
		t40206bo.saveRefuseInfo(riskRefuse);
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 审核通过删除待审核的商户交易权限
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String acceptDelete() throws Exception {
		if(this.checkOperator())
			return "提交人与审核人不能是同一个人";
		else{
			TblRiskMchtTranLimit tblRiskMchtTranLimit = t40214bo.get(id);
			tblRiskMchtTranLimit.setUpdateTime(CommonFunction.getCurrentDateTime());
			tblRiskMchtTranLimit.setOprId(operator.getOprId());
			tblRiskMchtTranLimit.setSaState(DELETE);
			//将通过的数据存到原始的数据中BussType是修改前的
			tblRiskMchtTranLimit.setBussType(tblRiskMchtTranLimit.getBussTypeOld());
			tblRiskMchtTranLimit.setTxnNum(tblRiskMchtTranLimit.getTxnNumOld());
			tblRiskMchtTranLimit.setChannel(tblRiskMchtTranLimit.getChannelOld());
			tblRiskMchtTranLimit.setCardType(tblRiskMchtTranLimit.getCardTypeOld());
			rspCode = t40214bo.update(tblRiskMchtTranLimit);
		}
		return rspCode;
	}
	
	/**
	 * 审核拒绝删除待审核的商户交易权限
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String refuseDelete() throws Exception {
		TblRiskMchtTranLimit tblRiskMchtTranLimit = new TblRiskMchtTranLimit();
		String mchtNo= "";
		String channel ="";
		String bussType ="";
		String txnNum = "";
		String cardType ="";
		String limit ="";
		if(this.checkOperator())
			return "提交人与审核人不能是同一个人";
		else{
			tblRiskMchtTranLimit = t40214bo.get(id);
			mchtNo = tblRiskMchtTranLimit.getMchtNo();
			channel = tblRiskMchtTranLimit.getChannel();
			bussType = tblRiskMchtTranLimit.getBussType();
			txnNum = tblRiskMchtTranLimit.getTxnNum();
			cardType = tblRiskMchtTranLimit.getCardType();
			limit = tblRiskMchtTranLimit.getLimit();
			tblRiskMchtTranLimit.setUpdateTime(CommonFunction.getCurrentDateTime());
			tblRiskMchtTranLimit.setOprId(operator.getOprId());
			tblRiskMchtTranLimit.setSaState(NORMAL);
			
			rspCode = t40214bo.update(tblRiskMchtTranLimit);
		}
		
		TblRiskRefuse riskRefuse = new TblRiskRefuse();
		riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
		riskRefuse.setParam1(mchtNo);
		riskRefuse.setParam2(channel);
		riskRefuse.setParam3(bussType);
		riskRefuse.setParam4(txnNum);
		riskRefuse.setParam5(cardType);
		riskRefuse.setParam6(limit);
		riskRefuse.setBrhId(operator.getOprBrhId());
		riskRefuse.setOprId(operator.getOprId());
		riskRefuse.setRefuseType(DELETE_TO_CHECK);
		riskRefuse.setRefuseInfo(refuseInfo);
		riskRefuse.setFlag("4");//商户交易权限审核
		
		t40206bo.saveRefuseInfo(riskRefuse);
		return rspCode;
	}
	
	/**
	 * 审核通过修改待审核的商户交易权限
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String acceptModify() throws Exception {
		if(this.checkOperator())
			return "提交人与审核人不能是同一个人";
		else{
			TblRiskMchtTranLimit tblRiskMchtTranLimit = t40214bo.get(id);
			tblRiskMchtTranLimit.setUpdateTime(CommonFunction.getCurrentDateTime());
			tblRiskMchtTranLimit.setOprId(operator.getOprId());
			tblRiskMchtTranLimit.setSaState(NORMAL);
			tblRiskMchtTranLimit.setBussType(tblRiskMchtTranLimit.getBussTypeOld());
			tblRiskMchtTranLimit.setTxnNum(tblRiskMchtTranLimit.getTxnNumOld());
			tblRiskMchtTranLimit.setChannel(tblRiskMchtTranLimit.getChannelOld());
			tblRiskMchtTranLimit.setCardType(tblRiskMchtTranLimit.getCardTypeOld());
			rspCode = t40214bo.update(tblRiskMchtTranLimit);
		}
		return rspCode;
	}
	
	/**
	 * 审核拒绝修改待审核的商户交易权限
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String refuseModify() throws Exception {
		TblRiskMchtTranLimit tblRiskMchtTranLimit = new TblRiskMchtTranLimit();
		String mchtNo= "";
		String channel ="";
		String bussType ="";
		String txnNum = "";
		String cardType ="";
		String limit ="";
		if(this.checkOperator())
			return "提交人与审核人不能是同一个人";
		else{
			tblRiskMchtTranLimit = t40214bo.get(id);
			tblRiskMchtTranLimit.setUpdateTime(CommonFunction.getCurrentDateTime());
			tblRiskMchtTranLimit.setOprId(operator.getOprId());
			tblRiskMchtTranLimit.setSaState(NORMAL);
			mchtNo = tblRiskMchtTranLimit.getMchtNo();
			channel = tblRiskMchtTranLimit.getChannel();
			bussType = tblRiskMchtTranLimit.getBussType();
			txnNum = tblRiskMchtTranLimit.getTxnNum();
			cardType = tblRiskMchtTranLimit.getCardType();
			limit = tblRiskMchtTranLimit.getLimit();
			
			//把修改后的数据恢复到修改前的时候BussTypeOld是修改后的数据
			tblRiskMchtTranLimit.setBussTypeOld(tblRiskMchtTranLimit.getBussType());
			tblRiskMchtTranLimit.setCardTypeOld(tblRiskMchtTranLimit.getCardType());
			tblRiskMchtTranLimit.setChannelOld(tblRiskMchtTranLimit.getChannel());
			tblRiskMchtTranLimit.setTxnNum(tblRiskMchtTranLimit.getTxnNumOld());
			//tblRiskMchtTranLimit.setLimit(tblRiskMchtTranLimit.getLimitOld());
			rspCode = t40214bo.update(tblRiskMchtTranLimit);
		}
		
		TblRiskRefuse riskRefuse = new TblRiskRefuse();
		riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
		riskRefuse.setParam1(mchtNo);
		riskRefuse.setParam2(channel);
		riskRefuse.setParam3(bussType);
		riskRefuse.setParam4(txnNum);
		riskRefuse.setParam5(cardType);
		riskRefuse.setParam6(limit);
		riskRefuse.setBrhId(operator.getOprBrhId());
		riskRefuse.setOprId(operator.getOprId());
		riskRefuse.setRefuseType(MODIFY_TO_CHECK);
		riskRefuse.setRefuseInfo(refuseInfo);
		riskRefuse.setFlag("4");
		
		t40206bo.saveRefuseInfo(riskRefuse);
		return rspCode;
	}
	
	//判断卡黑名单的操作人和审核人是否相同
	private boolean checkOperator()throws Exception {
		TblRiskMchtTranLimit tblRiskMchtTranLimit = t40214bo.get(id);
		String oprID = tblRiskMchtTranLimit.getCreOprId();
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

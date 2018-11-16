package com.huateng.struts.base.action;

import com.huateng.bo.base.T20109BO;
import com.huateng.bo.risk.T40206BO;
import com.huateng.common.Constants;
import com.huateng.log.Log;
import com.huateng.po.base.TblRspCodeMap;
import com.huateng.po.base.TblRspCodeMapPK;
import com.huateng.po.risk.TblRiskRefuse;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

public class T20110Action extends BaseAction {

	private static final long serialVersionUID = 1L;

	private T20109BO t20109BO = (T20109BO)ContextUtil.getBean("T20109BO");
	T40206BO t40206bo = (T40206BO) ContextUtil.getBean("T40206BO");
	
	public static String ADD_TO_CHECK = "0";
	public static String DELETE = "1";
	public static String NORMAL = "2";
	public static String MODIFY_TO_CHECK = "3";
	public static String DELETE_TO_CHECK = "4";
	
	@Override
	protected String subExecute() throws Exception {
		log("操作员：" + operator.getOprId());
		
		TblRspCodeMapPK key=new TblRspCodeMapPK();
		key.setDestId(getDestId());
		key.setDestRspCode(getDestRspCode());
		key.setSrcId(getSrcId());
		key.setSrcRspCode(getSrcRspCode());
		
		String state = t20109BO.get(key).getStatusId();
		if("accept".equals(method)) {
			if(state.equals(ADD_TO_CHECK)){
				log("审核通过新增待审核的应答码");
				rspCode = acceptAdd();
			}
			if(state.equals(DELETE_TO_CHECK)){
				log("审核通过删除待审核的应答码");
				rspCode = acceptDelete();
			}
			if(state.equals(MODIFY_TO_CHECK)){
				log("审核通过修改待审核的应答码");
				rspCode = acceptModify();
			}
		} else if("refuse".equals(method)) {
			if(state.equals(ADD_TO_CHECK)){
				log("审核拒绝新增待审核的应答码");
				rspCode = refuseAdd();
			}
			if(state.equals(DELETE_TO_CHECK)){
				log("审核拒绝删除待审核的应答码");
				rspCode = refuseDelete();
			}
			if(state.equals(MODIFY_TO_CHECK)){
				log("审核拒绝修改待审核的应答码");
				rspCode = refuseModify();
			}
		}
		return rspCode;
	}

	/**
	 * 审核通过新增待审核的应答码
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String acceptAdd() throws Exception {
		if(this.checkOperator())
			return "提交人与审核人不能是同一个人";
		else{
			TblRspCodeMapPK key=new TblRspCodeMapPK();
			key.setDestId(getDestId());
			key.setDestRspCode(getDestRspCode());
			key.setSrcId(getSrcId());
			key.setSrcRspCode(getSrcRspCode());
			
			TblRspCodeMap tblRspCodeMap = t20109BO.get(key);
			
			tblRspCodeMap.setCheckTime(CommonFunction.getCurrentDateTime());
			tblRspCodeMap.setCheckId(operator.getOprId());
			tblRspCodeMap.setStatusId(NORMAL);
			//将审核通过的数据存到原始的字段中
			tblRspCodeMap.setSrcRspCodeL(Integer.valueOf(tblRspCodeMap.getSrcRspCodeLOld().trim()));
			tblRspCodeMap.setDestRspCodeL(Integer.valueOf(tblRspCodeMap.getDestRspCodeLOld().trim()));
			tblRspCodeMap.setRspCodeDsp(tblRspCodeMap.getRspCodeDspOld());
			rspCode = t20109BO.update(tblRspCodeMap);
		}
		return rspCode;
	}
	
	/**
	 * 审核拒绝新增待审核的应答码
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String refuseAdd() throws Exception {
		TblRspCodeMap tblRspCodeMap  = new TblRspCodeMap();
		if(this.checkOperator())
			return "提交人与审核人不能是同一个人";
		else{
			TblRspCodeMapPK key=new TblRspCodeMapPK();
			key.setDestId(getDestId());
			key.setDestRspCode(getDestRspCode());
			key.setSrcId(getSrcId());
			key.setSrcRspCode(getSrcRspCode());
			
			tblRspCodeMap = t20109BO.get(key);
			t20109BO.delete(key);
		}
		//保存拒绝原因
		TblRiskRefuse riskRefuse = new TblRiskRefuse();
		riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
		riskRefuse.setParam1(tblRspCodeMap.getId().getSrcRspCode());
		riskRefuse.setParam2(tblRspCodeMap.getId().getDestRspCode());
		riskRefuse.setParam3(String.valueOf(tblRspCodeMap.getSrcRspCodeL()));
		riskRefuse.setParam4(String.valueOf(tblRspCodeMap.getDestRspCodeL()));
		riskRefuse.setParam5(tblRspCodeMap.getId().getSrcId());
		riskRefuse.setParam6(tblRspCodeMap.getId().getDestId());
		riskRefuse.setReserve1(tblRspCodeMap.getRspCodeDsp());//20121109
		riskRefuse.setBrhId(operator.getOprBrhId());
		riskRefuse.setOprId(operator.getOprId());
		riskRefuse.setRefuseType(ADD_TO_CHECK);
		riskRefuse.setRefuseInfo(refuseInfo);
		riskRefuse.setFlag("14");
		
		t40206bo.saveRefuseInfo(riskRefuse);
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 审核通过删除待审核的应答码
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String acceptDelete() throws Exception {
		if(this.checkOperator())
			return "提交人与审核人不能是同一个人";
		else{
			TblRspCodeMapPK key=new TblRspCodeMapPK();
			key.setDestId(getDestId());
			key.setDestRspCode(getDestRspCode());
			key.setSrcId(getSrcId());
			key.setSrcRspCode(getSrcRspCode());
			
			TblRspCodeMap tblRspCodeMap = t20109BO.get(key);
			tblRspCodeMap.setCheckTime(CommonFunction.getCurrentDateTime());
			tblRspCodeMap.setCheckId(operator.getOprId());
			tblRspCodeMap.setStatusId(DELETE);
			//将通过的数据存到原始的字段中
			tblRspCodeMap.setSrcRspCodeL(Integer.valueOf(tblRspCodeMap.getSrcRspCodeLOld().trim()));
			tblRspCodeMap.setDestRspCodeL(Integer.valueOf(tblRspCodeMap.getDestRspCodeLOld().trim()));
			tblRspCodeMap.setRspCodeDsp(tblRspCodeMap.getRspCodeDspOld());
			rspCode = t20109BO.update(tblRspCodeMap);
		}
		return rspCode;
	}
	
	/**
	 * 审核拒绝删除待审核的应答码
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String refuseDelete() throws Exception {
		String srcRspCode= "";
		String destRspCode ="";
		String srcRspCodeL ="";
		String destRspCodeL = "";
		String srcId ="";
		String destId ="";
		String reserve1 = "";
		if(this.checkOperator())
			return "提交人与审核人不能是同一个人";
		else{
			TblRspCodeMapPK key=new TblRspCodeMapPK();
			key.setDestId(getDestId());
			key.setDestRspCode(getDestRspCode());
			key.setSrcId(getSrcId());
			key.setSrcRspCode(getSrcRspCode());
			
			TblRspCodeMap tblRspCodeMap = t20109BO.get(key);
			srcRspCode = tblRspCodeMap.getId().getSrcRspCode();
			destRspCode = tblRspCodeMap.getId().getDestRspCode();
			srcRspCodeL = String.valueOf(tblRspCodeMap.getSrcRspCodeL());
			destRspCodeL = String.valueOf(tblRspCodeMap.getDestRspCodeL());
			srcId = tblRspCodeMap.getId().getSrcId();
			destId = tblRspCodeMap.getId().getDestId();
			reserve1 = tblRspCodeMap.getRspCodeDsp();//20121109
			
			tblRspCodeMap.setCheckTime(CommonFunction.getCurrentDateTime());
			tblRspCodeMap.setCheckId(operator.getOprId());
			tblRspCodeMap.setStatusId(NORMAL);
			
			rspCode = t20109BO.update(tblRspCodeMap);
		}
		
		TblRiskRefuse riskRefuse = new TblRiskRefuse();
		riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
		riskRefuse.setParam1(srcRspCode);
		riskRefuse.setParam2(destRspCode);
		riskRefuse.setParam3(srcRspCodeL);
		riskRefuse.setParam4(destRspCodeL);
		riskRefuse.setParam5(srcId);
		riskRefuse.setParam6(destId);
		riskRefuse.setReserve1(reserve1);//20121109
		riskRefuse.setBrhId(operator.getOprBrhId());
		riskRefuse.setOprId(operator.getOprId());
		riskRefuse.setRefuseType(DELETE_TO_CHECK);
		riskRefuse.setRefuseInfo(refuseInfo);
		riskRefuse.setFlag("14");//应答码审核
		
		t40206bo.saveRefuseInfo(riskRefuse);
		return rspCode;
	}
	
	/**
	 * 审核通过修改待审核的商户应答码
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String acceptModify() throws Exception {
		if(this.checkOperator())
			return "提交人与审核人不能是同一个人";
		else{
			TblRspCodeMapPK key=new TblRspCodeMapPK();
			key.setDestId(getDestId());
			key.setDestRspCode(getDestRspCode());
			key.setSrcId(getSrcId());
			key.setSrcRspCode(getSrcRspCode());
			
			TblRspCodeMap tblRspCodeMap = t20109BO.get(key);
			tblRspCodeMap.setCheckTime(CommonFunction.getCurrentDateTime());
			tblRspCodeMap.setCheckId(operator.getOprId());
			tblRspCodeMap.setStatusId(NORMAL);
			tblRspCodeMap.setSrcRspCodeL(Integer.valueOf(tblRspCodeMap.getSrcRspCodeLOld()));
			tblRspCodeMap.setDestRspCodeL(Integer.valueOf(tblRspCodeMap.getDestRspCodeLOld()));
			tblRspCodeMap.setRspCodeDsp(tblRspCodeMap.getRspCodeDspOld());
			rspCode = t20109BO.update(tblRspCodeMap);
		}
		return rspCode;
	}
	
	/**
	 * 审核拒绝修改待审核的应答码
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String refuseModify() throws Exception {
		String srcRspCode= "";
		String destRspCode ="";
		String srcRspCodeL ="";
		String destRspCodeL = "";
		String srcId ="";
		String destId ="";
		String reserve1 = "";
		if(this.checkOperator())
			return "提交人与审核人不能是同一个人";
		else{
			TblRspCodeMapPK key=new TblRspCodeMapPK();
			key.setDestId(getDestId());
			key.setDestRspCode(getDestRspCode());
			key.setSrcId(getSrcId());
			key.setSrcRspCode(getSrcRspCode());
			
			TblRspCodeMap tblRspCodeMap = t20109BO.get(key);
			tblRspCodeMap.setCheckTime(CommonFunction.getCurrentDateTime());
			tblRspCodeMap.setCheckId(operator.getOprId());
			tblRspCodeMap.setStatusId(NORMAL);
			srcRspCode = tblRspCodeMap.getId().getSrcRspCode();
			destRspCode = tblRspCodeMap.getId().getDestRspCode();
			srcRspCodeL = String.valueOf(tblRspCodeMap.getSrcRspCodeL());
			destRspCodeL = String.valueOf(tblRspCodeMap.getDestRspCodeL());
			srcId = tblRspCodeMap.getId().getSrcId();
			destId = tblRspCodeMap.getId().getDestId();
			reserve1 = tblRspCodeMap.getRspCodeDsp();//20121109
			
			//把修改后的数据恢复到修改前的字段
			tblRspCodeMap.setSrcRspCodeLOld(String.valueOf(tblRspCodeMap.getSrcRspCodeL()));
			tblRspCodeMap.setDestRspCodeLOld(String.valueOf(tblRspCodeMap.getDestRspCodeL()));
			tblRspCodeMap.setRspCodeDspOld(tblRspCodeMap.getRspCodeDsp());
			rspCode = t20109BO.update(tblRspCodeMap);
		}
		
		TblRiskRefuse riskRefuse = new TblRiskRefuse();
		riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
		riskRefuse.setParam1(srcRspCode);
		riskRefuse.setParam2(destRspCode);
		riskRefuse.setParam3(srcRspCodeL);
		riskRefuse.setParam4(destRspCodeL);
		riskRefuse.setParam5(srcId);
		riskRefuse.setParam6(destId);
		riskRefuse.setReserve1(reserve1);//20121109
		riskRefuse.setBrhId(operator.getOprBrhId());
		riskRefuse.setOprId(operator.getOprId());
		riskRefuse.setRefuseType(MODIFY_TO_CHECK);
		riskRefuse.setRefuseInfo(refuseInfo);
		riskRefuse.setFlag("14");
		
		t40206bo.saveRefuseInfo(riskRefuse);
		return rspCode;
	}
	
	//判断应答码的操作人和审核人是否相同
	private boolean checkOperator()throws Exception {
		TblRspCodeMapPK key=new TblRspCodeMapPK();
		key.setDestId(getDestId());
		key.setDestRspCode(getDestRspCode());
		key.setSrcId(getSrcId());
		key.setSrcRspCode(getSrcRspCode());
		
		TblRspCodeMap tblRspCodeMap = t20109BO.get(key);
		String oprID = tblRspCodeMap.getOprId();
		Log.log("修改人："+oprID+" 审核人"+operator.getOprId());
		if(operator.getOprId().equals(oprID))
			return true;//相同
		else
			return false;//不同
	}
	
	//key
	private String srcId;
    private String destId;
    private String srcRspCode;
    private String destRspCode;
	//拒绝原因
	private String refuseInfo;

	public String getSrcId() {
		return srcId;
	}

	public void setSrcId(String srcId) {
		this.srcId = srcId;
	}

	public String getDestId() {
		return destId;
	}

	public void setDestId(String destId) {
		this.destId = destId;
	}

	public String getSrcRspCode() {
		return srcRspCode;
	}

	public void setSrcRspCode(String srcRspCode) {
		this.srcRspCode = srcRspCode;
	}

	public String getDestRspCode() {
		return destRspCode;
	}

	public void setDestRspCode(String destRspCode) {
		this.destRspCode = destRspCode;
	}

	public String getRefuseInfo() {
		return refuseInfo;
	}
	public void setRefuseInfo(String refuseInfo) {
		this.refuseInfo = refuseInfo;
	}
}

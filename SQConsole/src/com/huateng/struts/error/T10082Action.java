package com.huateng.struts.error;

import com.huateng.bo.risk.T40206BO;
import com.huateng.bo.error.T10081BO;
import com.huateng.bo.error.T10082BO;
import com.huateng.common.Constants;
import com.huateng.po.risk.TblRiskRefuse;
import com.huateng.po.error.TblElecCashInf;
import com.huateng.po.error.TblElecCashInfTmp;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

public class T10082Action extends BaseAction {

	private static final long serialVersionUID = 1L;

	private T10081BO t10081BO = (T10081BO)ContextUtil.getBean("T10081BO");
	private T10082BO t10082BO = (T10082BO)ContextUtil.getBean("T10082BO");
	T40206BO t40206bo = (T40206BO) ContextUtil.getBean("T40206BO");
	
	public static String ADD_TO_CHECK = "0";		//新增待审核
	public static String DELETE = "1";				//已删除
	public static String NORMAL = "2";				//正常
	public static String MODIFY_TO_CHECK = "3";		//修改待审核
	public static String DELETE_TO_CHECK = "4";		//删除待审核
	
	//primary key
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

	@Override
	protected String subExecute() throws Exception {
		log("操作员：" + operator.getOprId());
		TblElecCashInfTmp tblElecCashInfTmp = t10081BO.get(this.getId());
		if(this.checkOperator(tblElecCashInfTmp.getRecUpdUsr()))
			return "存在提交人和当前审核人是同一人的记录，请重新选择！";
		
		String state = tblElecCashInfTmp.getSaState();
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
		return rspCode;
	}
	
	/**
	 * 审核通过新增待审核的补电子现金消费信息
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String acceptAdd(String id) throws Exception {
		TblElecCashInfTmp tblElecCashInfTmp = t10081BO.get(id);
		tblElecCashInfTmp.setRecCheTs(CommonFunction.getCurrentDateTime());
		tblElecCashInfTmp.setRecCheUsr(operator.getOprId());
		tblElecCashInfTmp.setSaState(NORMAL);
		t10081BO.update(tblElecCashInfTmp);
		
		TblElecCashInf tblElecCashInf = new TblElecCashInf();
		BeanUtils.copyProperties(tblElecCashInf, tblElecCashInfTmp);
		rspCode = t10082BO.add(tblElecCashInf);
		log("审核通过新增待审核的补电子现金消费信息");
		return rspCode;
	}
	
	/**
	 * 审核拒绝新增待审核的补电子现金消费信息
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String refuseAdd(String id) throws Exception {
		TblElecCashInfTmp tblElecCashInfTmp = new TblElecCashInfTmp();
		tblElecCashInfTmp =  t10081BO.get(id);
		t10081BO.delete(id);
		
		//保存拒绝原因
		TblRiskRefuse riskRefuse = new TblRiskRefuse();
		riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());	//拒绝时间
		riskRefuse.setOprId(operator.getOprId());					//拒绝人
		riskRefuse.setBrhId(operator.getOprBrhId());				//操作员所属机构
		riskRefuse.setRefuseInfo(refuseInfo);						//拒绝原因
		riskRefuse.setRefuseType(ADD_TO_CHECK);						//新增审核拒绝
		riskRefuse.setParam1(tblElecCashInfTmp.getMchtCd());		//商户号
		riskRefuse.setParam2(tblElecCashInfTmp.getTermId());		//终端号
		riskRefuse.setParam3(tblElecCashInfTmp.getTxnBatchNo());	//交易批次号
		riskRefuse.setParam4(tblElecCashInfTmp.getTxnCerNo());		//交易凭证号
		riskRefuse.setParam5(tblElecCashInfTmp.getTransDate());		//交易日期
		riskRefuse.setParam6(tblElecCashInfTmp.getTransAmt());		//交易金额
		riskRefuse.setReserve1(tblElecCashInfTmp.getPan());			//卡号
		riskRefuse.setFlag("17");		//补电子现金消费审核拒绝标识
		
		t40206bo.saveRefuseInfo(riskRefuse);
		log("审核拒绝新增待审核的补电子现金消费信息");
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 审核通过删除待审核的补电子现金消费信息
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String acceptDelete(String id) throws Exception {
		TblElecCashInfTmp tblElecCashInfTmp = t10081BO.get(id);
		tblElecCashInfTmp.setRecCheTs(CommonFunction.getCurrentDateTime());
		tblElecCashInfTmp.setRecCheUsr(operator.getOprId());
		tblElecCashInfTmp.setSaState(DELETE);
		t10081BO.update(tblElecCashInfTmp);
		
		TblElecCashInf tblElecCashInf = new TblElecCashInf();
		BeanUtils.copyProperties(tblElecCashInf, tblElecCashInfTmp);
		rspCode = t10082BO.update(tblElecCashInf);
		log("审核通过删除待审核的补电子现金消费信息");
		return rspCode;
	}
	
	/**
	 * 审核拒绝删除待审核的补电子现金消费信息
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String refuseDelete(String id) throws Exception {
		TblElecCashInfTmp tblElecCashInfTmp = new TblElecCashInfTmp();
		tblElecCashInfTmp = t10081BO.get(id);
		tblElecCashInfTmp.setRecCheTs(CommonFunction.getCurrentDateTime());
		tblElecCashInfTmp.setRecCheUsr(operator.getOprId());
		tblElecCashInfTmp.setSaState(NORMAL);
		t10081BO.update(tblElecCashInfTmp);
		
		TblElecCashInf tblElecCashInf = new TblElecCashInf();
		BeanUtils.copyProperties(tblElecCashInf,tblElecCashInfTmp);
		rspCode = t10082BO.update(tblElecCashInf);
		
		TblRiskRefuse riskRefuse = new TblRiskRefuse();
		riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());	//拒绝时间
		riskRefuse.setOprId(operator.getOprId());					//拒绝人
		riskRefuse.setBrhId(operator.getOprBrhId());				//操作员所属机构
		riskRefuse.setRefuseInfo(refuseInfo);						//拒绝原因
		riskRefuse.setRefuseType(DELETE_TO_CHECK);					//删除审核拒绝
		riskRefuse.setParam1(tblElecCashInfTmp.getMchtCd());		//商户号
		riskRefuse.setParam2(tblElecCashInfTmp.getTermId());		//终端号
		riskRefuse.setParam3(tblElecCashInfTmp.getTxnBatchNo());	//交易批次号
		riskRefuse.setParam4(tblElecCashInfTmp.getTxnCerNo());		//交易凭证号
		riskRefuse.setParam5(tblElecCashInfTmp.getTransDate());		//交易日期
		riskRefuse.setParam6(tblElecCashInfTmp.getTransAmt());		//交易金额
		riskRefuse.setReserve1(tblElecCashInfTmp.getPan());			//卡号
		riskRefuse.setFlag("17");		//补电子现金消费审核拒绝标识
		
		t40206bo.saveRefuseInfo(riskRefuse);
		log("审核拒绝删除待审核的补电子现金消费信息");
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 审核通过修改待审核的补电子现金消费信息
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String acceptModify(String id) throws Exception {
		TblElecCashInfTmp tblElecCashInfTmp = t10081BO.get(id);
		tblElecCashInfTmp.setRecCheTs(CommonFunction.getCurrentDateTime());
		tblElecCashInfTmp.setRecCheUsr(operator.getOprId());
		tblElecCashInfTmp.setSaState(NORMAL);
		t10081BO.update(tblElecCashInfTmp);
		
		//将修改后的字段覆盖原字段
		TblElecCashInf tblElecCashInf = new TblElecCashInf();
		BeanUtils.copyProperties(tblElecCashInf, tblElecCashInfTmp);
		rspCode = t10082BO.update(tblElecCashInf);
		log("审核通过修改待审核的补电子现金消费信息");
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 审核拒绝修改待审核的补电子现金消费信息
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String refuseModify(String id) throws Exception {
		TblElecCashInfTmp tblElecCashInfTmp = new TblElecCashInfTmp();
		TblElecCashInf tblElecCashInf = new TblElecCashInf();
		//用原字段值覆盖修改后的值
		tblElecCashInf = t10082BO.get(id);
		tblElecCashInf.setRecCheTs(CommonFunction.getCurrentDateTime());
		tblElecCashInf.setRecCheUsr(operator.getOprId());
		tblElecCashInf.setSaState(NORMAL);
		rspCode = t10082BO.update(tblElecCashInf);
		
		BeanUtils.copyProperties(tblElecCashInfTmp, tblElecCashInf);
		t10081BO.update(tblElecCashInfTmp);
		
		TblRiskRefuse riskRefuse = new TblRiskRefuse();
		riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());	//拒绝时间
		riskRefuse.setOprId(operator.getOprId());					//拒绝人
		riskRefuse.setBrhId(operator.getOprBrhId());				//操作员所属机构
		riskRefuse.setRefuseInfo(refuseInfo);						//拒绝原因
		riskRefuse.setRefuseType(MODIFY_TO_CHECK);					//修改审核拒绝
		riskRefuse.setParam1(tblElecCashInfTmp.getMchtCd());		//商户号
		riskRefuse.setParam2(tblElecCashInfTmp.getTermId());		//终端号
		riskRefuse.setParam3(tblElecCashInfTmp.getTxnBatchNo());	//交易批次号
		riskRefuse.setParam4(tblElecCashInfTmp.getTxnCerNo());		//交易凭证号
		riskRefuse.setParam5(tblElecCashInfTmp.getTransDate());		//交易日期
		riskRefuse.setParam6(tblElecCashInfTmp.getTransAmt());		//交易金额
		riskRefuse.setReserve1(tblElecCashInfTmp.getPan());			//卡号
		riskRefuse.setFlag("17");		//补电子现金消费审核拒绝标识
		
		t40206bo.saveRefuseInfo(riskRefuse);
		log("审核拒绝修改待审核的补电子现金消费信息");
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

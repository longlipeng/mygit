package com.huateng.struts.risk.action;

import com.huateng.bo.impl.mchnt.TblMchntService;
import com.huateng.bo.risk.T40202BO;
import com.huateng.bo.risk.T40206BO;
import com.huateng.common.StringUtil;
import com.huateng.po.TblCtlMchtInf;
import com.huateng.po.mchnt.TblMchtBaseInfTmp;
import com.huateng.po.risk.TblRiskRefuse;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

public class T40207Action extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	T40202BO t40202BO = (T40202BO) ContextUtil.getBean("T40202BO");
	T40206BO t40206bo = (T40206BO) ContextUtil.getBean("T40206BO");
	TblMchntService service = (TblMchntService) ContextUtil
	.getBean("TblMchntService");
	public static String ADD_TO_CHECK = "0";
	public static String DELETE = "1";
	public static String NORMAL = "2";
	public static String MODIFY_TO_CHECK = "3";
	public static String DELETE_TO_CHECK = "4";
	
	@Override
	protected String subExecute() throws Exception {
		log("操作员：" + operator.getOprId());
		TblCtlMchtInf tblCtlMchtInf = t40202BO.get(datePk);
		String state = tblCtlMchtInf.getSaState();
		if("accept".equals(method)) {
			if(state.equals(ADD_TO_CHECK)){
				log("审核通过新增待审核的商户黑名单");
				rspCode = acceptAdd();
			}
			if(state.equals(DELETE_TO_CHECK)){
				log("审核通过删除待审核的商户黑名单");
				rspCode = acceptDelete();
			}
		} else if("refuse".equals(method)) {
			if(state.equals(ADD_TO_CHECK)){
				log("审核拒绝新增待审核的商户黑名单");
				rspCode = refuseAdd();
			}
			if(state.equals(DELETE_TO_CHECK)){
				log("审核拒绝删除待审核的商户黑名单");
				rspCode = refuseDelete();
			}
		}
		return rspCode;
	}

	/**
	 * 审核通过  新增待审核的商户黑名单
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String acceptAdd() throws Exception {
		
		TblCtlMchtInf tblCtlMchtInf = t40202BO.get(datePk);
		
		if(this.checkOperator())
			return "操作人与审核人不能是同一人。";
		else{
			
			tblCtlMchtInf.setSaModiOprId(operator.getOprId());
			tblCtlMchtInf.setSaModiTime(CommonFunction.getCurrentDateTime());
			tblCtlMchtInf.setSaState(NORMAL);
			//商户加入到黑名单后需要将商户临时表中的状态置为黑名单状态，并将商户临时表中的原状态保存在商户黑名单表中的sa_conn_or 字段中
			/*TblMchtBaseInfTmp mcht=service.getBaseInfTmp(tblCtlMchtInf.getId());
			if(mcht==null){
				return "该商户不存在或已删除";
			}
			tblCtlMchtInf.setSaConnOr(mcht.getMchtStatus());
			mcht.setMchtStatus("H");//将商户状态置为黑名单
			service.updateBaseInfTmp(mcht);*/
			
			rspCode = t40202BO.update(tblCtlMchtInf);
		}
		//商户黑名单审核信息表
		TblRiskRefuse riskRefuse = new TblRiskRefuse();
	//	riskRefuse.setReserve1(datePk);    //主键
		riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());  //审核时间
		riskRefuse.setParam1(tblCtlMchtInf.getSaMerChName().trim());             //商户名
		riskRefuse.setOprId(operator.getOprId());  //审核人
		riskRefuse.setRefuseType("5");             //操作类型
		riskRefuse.setRefuseInfo(refuseInfo.trim());      //审核备注
		riskRefuse.setFlag("2"); //标志
		riskRefuse.setParam2(tblCtlMchtInf.getAppRemark());   //申请备注
		riskRefuse.setParam3(tblCtlMchtInf.getSaInitOprId());   //申请人
		riskRefuse.setParam4(tblCtlMchtInf.getSaInitTime());    //申请时间
		riskRefuse.setParam5(tblCtlMchtInf.getSaState());    //当前状态
		riskRefuse.setParam6(tblCtlMchtInf.getAddType());  //添加方式
		
		t40206bo.saveRefuseInfo(riskRefuse);
		
		//更新所有该卡号的状态
		String sqlUpdate = "update  tbl_risk_refuse set PARAM5 = '"+tblCtlMchtInf.getSaState()+"' where FLAG='2' and PARAM1 ='"+tblCtlMchtInf.getSaMerChName().trim()+"'";
		CommonFunction.getCommQueryDAO().excute(sqlUpdate);
		
		return rspCode;
		

	}
	
	/**
	 * 审核拒绝  新增待审核的商户黑名单
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String refuseAdd() throws Exception {
		
		
		TblCtlMchtInf tblCtlMchtInf = t40202BO.get(datePk);

		if(this.checkOperator())
			return "操作人与审核人不能是同一人";
		else{
			tblCtlMchtInf.setSaModiOprId(operator.getOprId());   //审核人
			tblCtlMchtInf.setSaModiTime(CommonFunction.getCurrentDateTime());   //审核时间
			tblCtlMchtInf.setSaState(DELETE);   //状态
			
			rspCode = t40202BO.update(tblCtlMchtInf);
		}
		
		//商户黑名单审核信息表
		TblRiskRefuse riskRefuse = new TblRiskRefuse();
//		riskRefuse.setReserve1(datePk);    //主键
		riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());  //审核时间
		riskRefuse.setParam1(tblCtlMchtInf.getSaMerChName().trim());             //商户名
		riskRefuse.setOprId(operator.getOprId());  //审核人
		riskRefuse.setRefuseType("0");             //操作类型
		riskRefuse.setRefuseInfo(refuseInfo.trim());      //审核备注
		riskRefuse.setFlag("2"); //标志
		riskRefuse.setParam2(tblCtlMchtInf.getAppRemark());   //申请备注
		riskRefuse.setParam3(tblCtlMchtInf.getSaInitOprId());   //申请人
		riskRefuse.setParam4(tblCtlMchtInf.getSaInitTime());    //申请时间
		riskRefuse.setParam5(tblCtlMchtInf.getSaState());    //当前状态
		riskRefuse.setParam6(tblCtlMchtInf.getAddType());  //添加方式
		
		t40206bo.saveRefuseInfo(riskRefuse);
		
		//更新所有该卡号的状态
		String sqlUpdate = "update  tbl_risk_refuse set PARAM5 = '"+tblCtlMchtInf.getSaState()+"' where FLAG='2' and PARAM1 ='"+tblCtlMchtInf.getSaMerChName().trim()+"'";
		CommonFunction.getCommQueryDAO().excute(sqlUpdate);
				
		return rspCode;
	}
	
	/**
	 * 审核通过  删除待审核的商户黑名单
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String acceptDelete() throws Exception {
		
		TblCtlMchtInf tblCtlMchtInf = t40202BO.get(datePk);
		if(this.checkOperator())
			return "操作人与审核人不能是同一人";
		else{
			
			tblCtlMchtInf.setSaModiOprId(operator.getOprId());
			tblCtlMchtInf.setSaModiTime(CommonFunction.getCurrentDateTime());
			tblCtlMchtInf.setSaState(DELETE);
			//删除商户黑名单后需要恢复商户临时表中的原状态
			/*TblMchtBaseInfTmp mcht=service.getBaseInfTmp(tblCtlMchtInf.getId());
			if(mcht!=null&&!StringUtil.isEmpty(tblCtlMchtInf.getSaConnOr().trim())){
				 mcht.setMchtStatus(tblCtlMchtInf.getSaConnOr().trim());
				 service.updateBaseInfTmp(mcht);
			}*/
			
		rspCode = t40202BO.update(tblCtlMchtInf);
		}
		
		//商户黑名单审核信息表
		TblRiskRefuse riskRefuse = new TblRiskRefuse();
//		riskRefuse.setReserve1(datePk);    //主键
		riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());  //审核时间
		riskRefuse.setParam1(tblCtlMchtInf.getSaMerChName().trim());             //商户名
		riskRefuse.setOprId(operator.getOprId());  //审核人
		riskRefuse.setRefuseType("6");             //操作类型
		riskRefuse.setRefuseInfo(refuseInfo.trim());      //审核备注
		riskRefuse.setFlag("2"); //标志
		riskRefuse.setParam2(tblCtlMchtInf.getAppRemark());   //申请备注
		riskRefuse.setParam3(tblCtlMchtInf.getSaInitOprId());   //申请人
		riskRefuse.setParam4(tblCtlMchtInf.getSaInitTime());    //申请时间
		riskRefuse.setParam5(tblCtlMchtInf.getSaState());    //当前状态
		riskRefuse.setParam6(tblCtlMchtInf.getAddType());  //添加方式
		
		t40206bo.saveRefuseInfo(riskRefuse);
		
		//更新所有该卡号的状态
		String sqlUpdate = "update  tbl_risk_refuse set PARAM5 = '"+tblCtlMchtInf.getSaState()+"' where FLAG='2' and PARAM1 ='"+tblCtlMchtInf.getSaMerChName().trim()+"'";
		CommonFunction.getCommQueryDAO().excute(sqlUpdate);
				
				
		return rspCode;
	}
	
	/**
	 * 审核拒绝  删除待审核的商户黑名单
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String refuseDelete() throws Exception {
		
		TblCtlMchtInf tblCtlMchtInf = t40202BO.get(datePk);
		if(this.checkOperator())
			return "操作人与审核人不能是同一人";
		else{
		
			tblCtlMchtInf.setSaModiTime(CommonFunction.getCurrentDateTime());
			tblCtlMchtInf.setSaModiOprId(operator.getOprId());
			tblCtlMchtInf.setSaState(NORMAL);
			rspCode = t40202BO.update(tblCtlMchtInf);
		}
		
		//商户黑名单审核信息表
		TblRiskRefuse riskRefuse = new TblRiskRefuse();
	//	riskRefuse.setReserve1(datePk);    //主键
		riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());  //审核时间
		riskRefuse.setParam1(tblCtlMchtInf.getSaMerChName().trim());             //商户名
		riskRefuse.setOprId(operator.getOprId());  //审核人
		riskRefuse.setRefuseType("4");             //操作类型
		riskRefuse.setRefuseInfo(refuseInfo.trim());      //审核备注
		riskRefuse.setFlag("2"); //标志
		riskRefuse.setParam2(tblCtlMchtInf.getAppRemark());   //申请备注
		riskRefuse.setParam3(tblCtlMchtInf.getSaInitOprId());   //申请人
		riskRefuse.setParam4(tblCtlMchtInf.getSaInitTime());    //申请时间
		riskRefuse.setParam5(tblCtlMchtInf.getSaState());    //当前状态
		riskRefuse.setParam6(tblCtlMchtInf.getAddType());  //添加方式
		
		t40206bo.saveRefuseInfo(riskRefuse);
		
		//更新所有该卡号的状态
		String sqlUpdate = "update  tbl_risk_refuse set PARAM5 = '"+tblCtlMchtInf.getSaState()+"' where FLAG='2' and PARAM1 ='"+tblCtlMchtInf.getSaMerChName().trim()+"'";
		CommonFunction.getCommQueryDAO().excute(sqlUpdate);
				
		return rspCode;
	}
	
	//判断商户黑名单的操作人和审核人是否相同
	private boolean checkOperator()throws Exception {
		TblCtlMchtInf tblCtlMchtInf = t40202BO.get(datePk);
		String oprID = tblCtlMchtInf.getSaInitOprId();//操作人
//		String oprID = tblCtlMchtInf.getSaModiOprId();
		if(operator.getOprId().equals(oprID))
			return true;//相同
		else
			return false;//不同
	}
	
	private String saMerNo;// 受控商户号
	private String saMerChName;// 商户中文名称
	private String saMerEnName;// 商户英文名称
	private String saConnOr;// 联系人
	private String saConnTel;// 联系电话
	private String saZoneNo;// 分号
	private String saAdmiBranNo;// 主管分公司号
	private String datePk;   //时间 主键
	//拒绝原因
	private String refuseInfo;
	
	public String getRefuseInfo() {
		return refuseInfo;
	}

	public void setRefuseInfo(String refuseInfo) {
		this.refuseInfo = refuseInfo;
	}

	public String getSaMerNo() {
		return saMerNo;
	}
	public void setSaMerNo(String saMerNo) {
		this.saMerNo = saMerNo;
	}
	public String getSaMerChName() {
		return saMerChName;
	}
	public void setSaMerChName(String saMerChName) {
		this.saMerChName = saMerChName;
	}
	public String getSaMerEnName() {
		return saMerEnName;
	}
	public void setSaMerEnName(String saMerEnName) {
		this.saMerEnName = saMerEnName;
	}
	public String getSaConnOr() {
		return saConnOr;
	}
	public void setSaConnOr(String saConnOr) {
		this.saConnOr = saConnOr;
	}
	public String getSaConnTel() {
		return saConnTel;
	}
	public void setSaConnTel(String saConnTel) {
		this.saConnTel = saConnTel;
	}
	public String getSaZoneNo() {
		return saZoneNo;
	}
	public void setSaZoneNo(String saZoneNo) {
		this.saZoneNo = saZoneNo;
	}
	public String getSaAdmiBranNo() {
		return saAdmiBranNo;
	}
	public void setSaAdmiBranNo(String saAdmiBranNo) {
		this.saAdmiBranNo = saAdmiBranNo;
	}

	public String getDatePk() {
		return datePk;
	}

	public void setDatePk(String datePk) {
		this.datePk = datePk;
	}
	
}

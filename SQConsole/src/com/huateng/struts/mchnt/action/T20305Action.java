/**
 * 
 */
package com.huateng.struts.mchnt.action;

import com.huateng.bo.mchnt.T20304BO;
import com.huateng.bo.risk.T40206BO;
import com.huateng.po.mchnt.CstMchtFeeInf;
import com.huateng.po.mchnt.CstMchtFeeInfPK;
import com.huateng.po.mchnt.CstMchtFeeInfTmp;
import com.huateng.po.risk.TblRiskRefuse;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

/**
 * @author jinlong
 *
 */
public class T20305Action extends BaseAction{
	private static final long serialVersionUID = 1L;

	private T20304BO t20304BO = (T20304BO) ContextUtil.getBean("T20304BO");
	private T40206BO t40206bo = (T40206BO) ContextUtil.getBean("T40206BO");
	@Override
	protected String subExecute() throws Exception {
		log("操作员：" + operator.getOprId());
		
		CstMchtFeeInfPK cstMchtFeeInfPK = new CstMchtFeeInfPK();
		
		cstMchtFeeInfPK.setCardType(cardType);
		
		cstMchtFeeInfPK.setMchtCd(mchtCd);
		
//		cstMchtFeeInfPK.setTxnNum("1101");
		
		CstMchtFeeInfTmp old=t20304BO.getMchtLimitTmp(cstMchtFeeInfPK);
		if(old!=null){
			String state=old.getSaState();
			if("accept".equals(method)) {
				if(state.equals(CommonFunction.ADD_TO_CHECK)){
					log("审核通过新增待审核的商户交易权限");
					rspCode = acceptAdd(old);
				}
				if(state.equals(CommonFunction.DELETE_TO_CHECK)){
					log("审核通过删除待审核的商户交易权限");
					rspCode = acceptDelete(old);
				}
				if(state.equals(CommonFunction.MODIFY_TO_CHECK)){
					log("审核通过修改待审核的商户交易权限");
					rspCode = acceptModify(old);
				}
			    if("00".equals(rspCode)){
			    	//限额操作记录
					TblRiskRefuse riskRefuse = new TblRiskRefuse();
					riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
					riskRefuse.setParam1(old.getId().getMchtCd());
					riskRefuse.setParam2(old.getId().getCardType());
					riskRefuse.setBrhId(operator.getOprBrhId());
					riskRefuse.setOprId(operator.getOprId());
//					riskRefuse.setRefuseType("0");
					riskRefuse.setRefuseInfo(refuseInfo);
					riskRefuse.setFlag("9");
					riskRefuse.setParam6("商户交易限额审核");
					riskRefuse.setReserve1("通过");
					t40206bo.saveRefuseInfo(riskRefuse);
			    }
			} else if("refuse".equals(method)) {
				if(state.equals(CommonFunction.ADD_TO_CHECK)){
					log("审核拒绝新增待审核的商户交易权限");
					rspCode = refuseAdd(old);
				}
				if(state.equals(CommonFunction.DELETE_TO_CHECK)){
					log("审核拒绝删除待审核的商户交易权限");
					rspCode = refuseDelete(old);
				}
				if(state.equals(CommonFunction.MODIFY_TO_CHECK)){
					log("审核拒绝修改待审核的商户交易权限");
					rspCode = refuseModify(old);
				}
				 if("00".equals(rspCode)){
				    	//限额操作记录
						TblRiskRefuse riskRefuse = new TblRiskRefuse();
						riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
						riskRefuse.setParam1(old.getId().getMchtCd());
						riskRefuse.setParam2(old.getId().getCardType());
						riskRefuse.setBrhId(operator.getOprBrhId());
						riskRefuse.setOprId(operator.getOprId());
//						riskRefuse.setRefuseType("0");
						riskRefuse.setRefuseInfo(refuseInfo);
						riskRefuse.setFlag("9");
						riskRefuse.setParam6("商户交易限额审核");
						riskRefuse.setReserve1("拒绝");
						t40206bo.saveRefuseInfo(riskRefuse);
				    }
			}
		}else{
			return "错误";
		}
		return rspCode;
	}

	/**
	 * 审核通过新增待审核的商户交易权限
	 * @param old 
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String acceptAdd(CstMchtFeeInfTmp old) throws Exception {
		if(this.checkOperator(old))
			return "提交人与审核人不能是同一个人";
		else{
			old.setSaState(CommonFunction.NORMAL);
			old.setRecUpdTs(CommonFunction.getCurrentDateTime());
			old.setOprID(operator.getOprId());
			CstMchtFeeInf cst=new CstMchtFeeInf();
			BeanUtils.copyProperties(cst, old);
			t20304BO.saveOrUpdate(old);
			t20304BO.saveOrUpdate(cst);
		}
		return "00";
	}
	
	/**
	 * 审核拒绝新增待审核的商户交易权限
	 * @param old 
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String refuseAdd(CstMchtFeeInfTmp old) throws Exception {
		if(this.checkOperator(old))
			return "提交人与审核人不能是同一个人";
		else{
			//检查正是表是否有数据，如果有，则恢复到原状态（已删除），若没有，临时表状态更新为新增审核拒绝
			CstMchtFeeInf cst=t20304BO.getMchtLimit(old.getId());
			if(null!=cst){
				BeanUtils.copyProperties(old, cst);
				
				old.setRecUpdTs(CommonFunction.getCurrentDateTime());
				old.setOprID(operator.getOprId());
				
			}else{
				old.setRecUpdTs(CommonFunction.getCurrentDateTime());
				old.setOprID(operator.getOprId());
				old.setSaState("5");
			}
			rspCode = t20304BO.saveOrUpdate(old);
		}
	/*	//保存拒绝原因
		TblRiskRefuse riskRefuse = new TblRiskRefuse();
		riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
		riskRefuse.setParam1(old.getId().getMchtCd());
		riskRefuse.setParam2(old.getId().getCardType());
		riskRefuse.setBrhId(operator.getOprBrhId());
		riskRefuse.setOprId(operator.getOprId());
		riskRefuse.setRefuseType("0");
		riskRefuse.setRefuseInfo(refuseInfo);
		riskRefuse.setFlag("9");
		riskRefuse.setParam3(old.getDaySingle().toString());
		riskRefuse.setParam4(old.getDayAmt().toString());
		riskRefuse.setParam5(old.getMonAmt().toString());
		t40206bo.saveRefuseInfo(riskRefuse);*/
		return "00";
	}
	
	/**
	 * 审核通过删除待审核的商户交易权限
	 * @param old 
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String acceptDelete(CstMchtFeeInfTmp old) throws Exception {
		if(this.checkOperator(old))
			return "提交人与审核人不能是同一个人";
		else{
			 
			old.setRecUpdTs(CommonFunction.getCurrentDateTime());
			old.setOprID(operator.getOprId());
			old.setSaState(CommonFunction.DELETE);
			CstMchtFeeInf cst=t20304BO.getMchtLimit(old.getId());
			cst.setRecUpdTs(CommonFunction.getCurrentDateTime());
			cst.setOprID(operator.getOprId());
			cst.setSaState(CommonFunction.DELETE);
			t20304BO.saveOrUpdate(cst);
			t20304BO.saveOrUpdate(old);
		}
		return "00";
	}
	
	/**
	 * 审核拒绝删除待审核的商户交易权限
	 * @param old 
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String refuseDelete(CstMchtFeeInfTmp old) throws Exception {
		if(this.checkOperator(old))
			return "提交人与审核人不能是同一个人";
		else{
			old.setRecUpdTs(CommonFunction.getCurrentDateTime());
			old.setOprID(operator.getOprId());
			old.setSaState(CommonFunction.NORMAL);
			rspCode =t20304BO.saveOrUpdate(old);//update(tblRiskMchtTranCtl);
		}
		
		/*TblRiskRefuse riskRefuse = new TblRiskRefuse();
		riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
		riskRefuse.setParam1(old.getId().getMchtCd());
		riskRefuse.setParam2(old.getId().getCardType());
		riskRefuse.setBrhId(operator.getOprBrhId());
		riskRefuse.setOprId(operator.getOprId());
		riskRefuse.setRefuseType("4");
		riskRefuse.setRefuseInfo(refuseInfo);
		riskRefuse.setFlag("9");
		riskRefuse.setParam3(old.getDaySingle().toString());
		riskRefuse.setParam4(old.getDayAmt().toString());
		riskRefuse.setParam5(old.getMonAmt().toString());
		t40206bo.saveRefuseInfo(riskRefuse);*/
		
		return rspCode;
	}
	
	/**
	 * 审核通过修改待审核的商户交易权限
	 * @param old 
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String acceptModify(CstMchtFeeInfTmp old) throws Exception {
		if(this.checkOperator(old)){
			return "提交人与审核人不能是同一个人";
		}
		else{
			old.setRecUpdTs(CommonFunction.getCurrentDateTime());
			old.setOprID(operator.getOprId());
			old.setSaState(CommonFunction.NORMAL);
			CstMchtFeeInf cst =t20304BO.getMchtLimit(old.getId());
			BeanUtils.copyProperties(cst, old);
			t20304BO.saveOrUpdate(cst);
			t20304BO.saveOrUpdate(old);
		}
		return "00";
	}
	
	/**
	 * 审核拒绝修改待审核的商户交易权限
	 * @param old 
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String refuseModify(CstMchtFeeInfTmp old) throws Exception {
		if(this.checkOperator(old))
			return "提交人与审核人不能是同一个人";
		else{
			CstMchtFeeInf cst=t20304BO.getMchtLimit(old.getId());
			BeanUtils.copyProperties(old, cst);
			
			old.setRecUpdTs(CommonFunction.getCurrentDateTime());
			old.setOprID(operator.getOprId());
			old.setSaState(CommonFunction.NORMAL);
			rspCode = t20304BO.saveOrUpdate(old);
		}
		
		/*TblRiskRefuse riskRefuse = new TblRiskRefuse();
		riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
		riskRefuse.setParam1(old.getId().getMchtCd());
		riskRefuse.setParam2(old.getId().getCardType());
		riskRefuse.setBrhId(operator.getOprBrhId());
		riskRefuse.setOprId(operator.getOprId());
		riskRefuse.setRefuseType("3");
		riskRefuse.setRefuseInfo(refuseInfo);
		riskRefuse.setFlag("9");
		riskRefuse.setParam3(old.getDaySingle().toString());
		riskRefuse.setParam4(old.getDayAmt().toString());
		riskRefuse.setParam5(old.getMonAmt().toString());
		t40206bo.saveRefuseInfo(riskRefuse);*/
		return rspCode;
	}
	
	//判断操作人和审核人是否相同
	private boolean checkOperator(CstMchtFeeInfTmp old)throws Exception {
		log("修改人："+old.getOprID()+" || 审核人："+operator.getOprId());
		String oprID = old.getOprID();
		if(operator.getOprId().equals(oprID))
			return true;//相同
		else
			return false;//不同
	}
	
	// 
	private String mchtCd;
	//拒绝原因
	private String refuseInfo;
	
	private String cardType;
	
	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getMchtCd() {
		return mchtCd;
	}
	public void setMchtCd(String mchtCd) {
		this.mchtCd = mchtCd;
	}
	public String getRefuseInfo() {
		return refuseInfo;
	}
	public void setRefuseInfo(String refuseInfo) {
		this.refuseInfo = refuseInfo;
	}
}

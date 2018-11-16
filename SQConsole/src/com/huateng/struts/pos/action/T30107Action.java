 package com.huateng.struts.pos.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.huateng.bo.pos.T30106BO;
import com.huateng.bo.pos.T30107BO;
import com.huateng.bo.risk.T40206BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.po.pos.CstTermFeeInTrue;
import com.huateng.po.pos.CstTermFeeIn;
import com.huateng.po.pos.CstTermFeePK;
import com.huateng.po.risk.TblRiskRefuse;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

public class T30107Action extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String termid;
	private String mchtcd;
	private String cardType;
	public String getRefuseInfo() {
		return refuseInfo;
	}
	public void setRefuseInfo(String refuseInfo) {
		this.refuseInfo = refuseInfo;
	}
	private String refuseInfo;
	public String getTermid() {
		return termid;
	}
	public void setTermid(String termid) {
		this.termid = termid;
	}
	public String getMchtcd() {
		return mchtcd;
	}
	public void setMchtcd(String mchtcd) {
		this.mchtcd = mchtcd;
	}
	
	
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	private T30106BO t30106BO = (T30106BO) ContextUtil.getBean("T30106BO");
	private T30107BO t30107BO = (T30107BO) ContextUtil.getBean("T30107BO");
	private T40206BO t40206bo = (T40206BO) ContextUtil.getBean("T40206BO");
	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub
		//在新增、修改、冻结、恢复和注销时，修改人和操作人不能使同一人
		String sql = "SELECT CRE_ID FROM CST_TERM_FEE_INF WHERE MCHT_CD= '" + mchtcd + "' and TERM_ID='"+termid+ "' and CARD_TYPE='"+cardType+"'";
		List<String> list = commQueryDAO.findBySQLQuery(sql);
		if (null != list && !list.isEmpty()) {
			if (!StringUtil.isNull(list.get(0))) {
				if(list.get(0).equals(operator.getOprId())){
					log("修改人："+list.get(0)+"  | 审核人："+operator.getOprId());
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
			log("操作员编号：" + operator.getOprId()+ "，对终端交易限额的审核操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	
	
	
	private String accept() {
		// TODO Auto-generated method stub
		//新增
		//获得临时表的这条数据
		CstTermFeePK cstTermFeePK=new CstTermFeePK();
		cstTermFeePK.setMchtcd(mchtcd);
		cstTermFeePK.setTermid(termid);
		cstTermFeePK.setCardtype(cardType);
		CstTermFeeIn cstTermFeeIn=t30106BO.get(cstTermFeePK);
		//创建正式表
		CstTermFeeInTrue cstTermFeeInTrue=new CstTermFeeInTrue();
		//得到该条记录的状态
		String state=cstTermFeeIn.getSastatue();
		//更新审核人和审核时间
		cstTermFeeIn.setUpid(operator.getOprId());
		cstTermFeeIn.setRecupdts(CommonFunction.getCurrentDateTime());
		
	//新增审核
		if(("0").equals(state)){
			cstTermFeeIn.setSastatue(CommonFunction.NORMAL);
			//修改状态改到临时表
			List<CstTermFeeIn> cstTermFeeInList=new ArrayList<CstTermFeeIn>();
			cstTermFeeInList.add(cstTermFeeIn);
			t30106BO.update(cstTermFeeInList);
			//从临时表中获得数据到实际表
			// 复制新的信息
			BeanUtils.copyProperties(cstTermFeeIn,cstTermFeeInTrue);	
			t30107BO.add(cstTermFeeInTrue);
		}
		//修改审核
		if(("3").equals(state)){
			cstTermFeeIn.setSastatue(CommonFunction.NORMAL);
			cstTermFeeInTrue=t30107BO.get(cstTermFeePK);
			//修改状态改到临时表
			List<CstTermFeeIn> cstTermFeeInList=new ArrayList<CstTermFeeIn>();
			cstTermFeeInList.add(cstTermFeeIn);
			t30106BO.update(cstTermFeeInList);
			//修改状态到真实表
			BeanUtils.copyProperties(cstTermFeeIn,cstTermFeeInTrue);	
			t30107BO.update(cstTermFeeInTrue);
		}
		//注销审核
		if(("4").equals(state)){
			cstTermFeeIn.setSastatue(CommonFunction.DELETE);
			//修改状态到临时表
			List<CstTermFeeIn> cstTermFeeInList=new ArrayList<CstTermFeeIn>();
			cstTermFeeInList.add(cstTermFeeIn);
			t30106BO.update(cstTermFeeInList);
			//修改状态到真实表
			BeanUtils.copyProperties(cstTermFeeIn,cstTermFeeInTrue);	
			t30107BO.update(cstTermFeeInTrue);
		}
		return Constants.SUCCESS_CODE;
	}

	
	private String refuse() throws Exception {
		// TODO Auto-generated method stub
		TblRiskRefuse tblRiskRefuse=new TblRiskRefuse();
		CstTermFeePK cstTermFeePK=new CstTermFeePK();
		cstTermFeePK.setMchtcd(mchtcd);
		cstTermFeePK.setTermid(termid);
		cstTermFeePK.setCardtype(cardType);
		CstTermFeeIn cstTermFeeIn=t30106BO.get(cstTermFeePK);
		CstTermFeeInTrue cstTermFeeInTrue=new CstTermFeeInTrue();
		String state=cstTermFeeIn.getSastatue();
		cstTermFeeIn.setUpid(operator.getOprId());
		cstTermFeeIn.setRecupdts(CommonFunction.getCurrentDateTime());
		//新增拒绝，就直接删除
		if(("0").equals(state)){
			t30106BO.delete(cstTermFeePK);
		}
		//修改拒绝临时表变成正常状态
		if(("3").equals(state)){
			cstTermFeeInTrue=t30107BO.get(cstTermFeePK);
			//恢复原来的数据
			BeanUtils.copyProperties(cstTermFeeInTrue,cstTermFeeIn);	
			t30106BO.update(cstTermFeeIn);
			cstTermFeeIn.setSastatue(CommonFunction.NORMAL);
		}
		//注销拒绝
		if(("4").equals(state)){
			cstTermFeeInTrue=t30107BO.get(cstTermFeePK);
			//恢复原来的数据
			BeanUtils.copyProperties(cstTermFeeInTrue,cstTermFeeIn);	
			t30106BO.update(cstTermFeeIn);
			cstTermFeeIn.setSastatue(CommonFunction.NORMAL);
		}
		tblRiskRefuse.setRefuseType(state);
		tblRiskRefuse.setRefuseInfo(getRefuseInfo());
		tblRiskRefuse.setOprId(operator.getOprId());
		tblRiskRefuse.setBrhId(operator.getOprBrhId());
		tblRiskRefuse.setParam1(termid);
		tblRiskRefuse.setParam2(mchtcd);
		tblRiskRefuse.setParam3(cardType);
		tblRiskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
		tblRiskRefuse.setFlag("8");
		t40206bo.saveRefuseInfo(tblRiskRefuse);
		return Constants.SUCCESS_CODE;
	}
	

}

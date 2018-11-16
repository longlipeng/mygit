package com.huateng.struts.mchnt.action;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import com.huateng.bo.mchnt.T2070302BO;
import com.huateng.bo.mchnt.T20703BO;
import com.huateng.bo.risk.T40206BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.po.mchnt.TblHisDiscAlgo1;
import com.huateng.po.mchnt.TblHisDiscAlgo1Tmp;
import com.huateng.po.risk.TblRiskRefuse;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

public class T20705Action extends BaseAction{
  
	private static final long serialVersionUID = 1L;
	private String DISC_ID;
	private String refuseInfo;
    public String getDISC_ID() {
		return DISC_ID;
	}
	public void setDISC_ID(String dISCID) {
		DISC_ID = dISCID;
	}
    public String getRefuseInfo() {
	return refuseInfo;
    }
    public void setRefuseInfo(String refuseInfo) {
 	 this.refuseInfo = refuseInfo;
    }
	private T20703BO t20703BO=(T20703BO) ContextUtil.getBean("T20703BO");
	private T2070302BO t2070302BO=(T2070302BO) ContextUtil.getBean("T2070302BO");
	private T40206BO t40206bo = (T40206BO) ContextUtil.getBean("T40206BO");
	
	@SuppressWarnings("unchecked")
	@Override
	protected String subExecute() throws Exception {
		String sql = "SELECT REC_UPD_USR_ID FROM tbl_his_disc_algo1_tmp WHERE DISC_ID= '" + DISC_ID + "'";
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
			log("操作员编号：" + operator.getOprId()+ "，对商户手续费规则审核操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	
	private String accept() {
		//新增
		//获得临时表的这条数据
		TblHisDiscAlgo1Tmp tblHisDiscAlgo1Tmp=t2070302BO.get(DISC_ID);
		TblHisDiscAlgo1 tblHisDiscAlgo1=new TblHisDiscAlgo1();
		String state=tblHisDiscAlgo1Tmp.getSA_SATUTE();
	//新增审核
		if(("0").equals(state)){
			tblHisDiscAlgo1Tmp.setSA_SATUTE(CommonFunction.NORMAL);
			//修改状态改到临时表
			List<TblHisDiscAlgo1Tmp> tblHisDiscAlgo1List=new ArrayList<TblHisDiscAlgo1Tmp>();
			tblHisDiscAlgo1List.add(tblHisDiscAlgo1Tmp);
			t2070302BO.update(tblHisDiscAlgo1List);
			//从临时表中获得数据到实际表
			// 复制新的信息
			BeanUtils.copyProperties(tblHisDiscAlgo1Tmp,tblHisDiscAlgo1);	
			t20703BO.add(tblHisDiscAlgo1);
		}
		//修改审核
		if(("3").equals(state)){
			tblHisDiscAlgo1Tmp.setSA_SATUTE(CommonFunction.NORMAL);
			tblHisDiscAlgo1=t20703BO.get(DISC_ID);
			//修改状态改到临时表
			List<TblHisDiscAlgo1Tmp> tblHisDiscAlgo1TmpList=new ArrayList<TblHisDiscAlgo1Tmp>();
			tblHisDiscAlgo1TmpList.add(tblHisDiscAlgo1Tmp);
			t2070302BO.update(tblHisDiscAlgo1TmpList);
			//修改数据到真实表
			BeanUtils.copyProperties(tblHisDiscAlgo1Tmp,tblHisDiscAlgo1);	
			t20703BO.update(tblHisDiscAlgo1);
		}
		//注销审核
		if(("4").equals(state)){
			tblHisDiscAlgo1Tmp.setSA_SATUTE(CommonFunction.DELETE);
			//修改状态到临时表
			List<TblHisDiscAlgo1Tmp> tblHisDiscAlgo1TmpList=new ArrayList<TblHisDiscAlgo1Tmp>();
			tblHisDiscAlgo1TmpList.add(tblHisDiscAlgo1Tmp);
			t2070302BO.update(tblHisDiscAlgo1TmpList);
			//修改状态到真实表
			BeanUtils.copyProperties(tblHisDiscAlgo1Tmp,tblHisDiscAlgo1);	
			t20703BO.update(tblHisDiscAlgo1);
		}
		return Constants.SUCCESS_CODE;
	}
	
	private String refuse() throws Exception {
		// TODO Auto-generated method stub
		TblRiskRefuse tblRiskRefuse=new TblRiskRefuse();
		TblHisDiscAlgo1Tmp tblHisDiscAlgo1Tmp=t2070302BO.get(DISC_ID);
		TblHisDiscAlgo1 tblHisDiscAlgo1=new TblHisDiscAlgo1();
		String state=tblHisDiscAlgo1Tmp.getSA_SATUTE();
		//新增拒绝，就直接删除
		if(("0").equals(state)){
			t2070302BO.delete(DISC_ID);
		}
		//修改拒绝临时表变成正常状态
		if(("3").equals(state)){
			tblHisDiscAlgo1=t20703BO.get(DISC_ID);
			//恢复原来的数据
			BeanUtils.copyProperties(tblHisDiscAlgo1,tblHisDiscAlgo1Tmp);	
			t2070302BO.update(tblHisDiscAlgo1Tmp);
			tblHisDiscAlgo1Tmp.setSA_SATUTE(CommonFunction.NORMAL);
		}
		//注销拒绝
		if(("4").equals(state)){
			tblHisDiscAlgo1=t20703BO.get(DISC_ID);
			//恢复原来的数据
			BeanUtils.copyProperties(tblHisDiscAlgo1,tblHisDiscAlgo1Tmp);	
			t2070302BO.update(tblHisDiscAlgo1Tmp);
			tblHisDiscAlgo1Tmp.setSA_SATUTE(CommonFunction.NORMAL);
		}
		tblRiskRefuse.setRefuseType(state);
		tblRiskRefuse.setRefuseInfo(getRefuseInfo());
		tblRiskRefuse.setOprId(operator.getOprId());
		tblRiskRefuse.setBrhId(operator.getOprBrhId());
		tblRiskRefuse.setParam1(tblHisDiscAlgo1Tmp.getMCHT_NO());
		tblRiskRefuse.setParam2(tblHisDiscAlgo1Tmp.getTERM_ID());
		tblRiskRefuse.setParam3(tblHisDiscAlgo1Tmp.getCITY_CODE());
		tblRiskRefuse.setParam4(tblHisDiscAlgo1Tmp.getTO_BRCH_NO());
		tblRiskRefuse.setParam5(tblHisDiscAlgo1Tmp.getFK_BRCH_NO());
		tblRiskRefuse.setParam6(tblHisDiscAlgo1Tmp.getCARD_TYPE());
		tblRiskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
		tblRiskRefuse.setFlag("8");
		t40206bo.saveRefuseInfo(tblRiskRefuse);
		return Constants.SUCCESS_CODE;
	}
}

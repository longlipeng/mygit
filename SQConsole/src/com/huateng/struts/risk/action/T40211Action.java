package com.huateng.struts.risk.action;

import java.util.ArrayList;
import java.util.List;
import com.huateng.bo.risk.T40206BO;
import com.huateng.bo.risk.T40210BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.po.risk.TblRiskChlTranCtl;
import com.huateng.po.risk.TblRiskRefuse;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

public class T40211Action extends BaseAction{
	private String id;
	private String refuseInfo;
	
	public String getRefuseInfo() {
		return refuseInfo;
	}
	public void setRefuseInfo(String refuseInfo) {
		this.refuseInfo = refuseInfo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	private static final long serialVersionUID = 1L;
	private T40210BO t40210BO = (T40210BO) ContextUtil.getBean("T40210BO");
	private T40206BO t40206bo = (T40206BO) ContextUtil.getBean("T40206BO");
	
	@Override
	protected String subExecute() throws Exception {
		//在新增、修改、冻结、恢复和注销时，修改人和操作人不能使同一人
		String sql = "SELECT CRE_ID FROM TBL_RISK_CHL_TRAN_CTL WHERE ID= '" + id + "'";
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
			log("操作员编号：" + operator.getOprId()+ "，对渠道交易黑名单的审核操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	
	private String accept() {
		//获得临时表的这条数据
		TblRiskChlTranCtl tblRiskChlTranCtl=t40210BO.get(getId());
		String state=tblRiskChlTranCtl.getSastatue();
		
	    //新增审核
		if(("0").equals(state)){
			tblRiskChlTranCtl.setSastatue(CommonFunction.NORMAL);
			//把修改后的值赋值给修改前的值Cardbinold是修改后的值
			tblRiskChlTranCtl.setCardbin(tblRiskChlTranCtl.getCardbinold());
			tblRiskChlTranCtl.setTxnnum(tblRiskChlTranCtl.getTxnnumold());
			tblRiskChlTranCtl.setUpid(operator.getOprId());
			tblRiskChlTranCtl.setUptime(CommonFunction.getCurrentDateTime());
			//修改状态改到临时表
			List<TblRiskChlTranCtl> tblRiskChlTranCtlList=new ArrayList<TblRiskChlTranCtl>();
			tblRiskChlTranCtlList.add(tblRiskChlTranCtl);
			t40210BO.update(tblRiskChlTranCtlList);
		}
		//修改审核
		if(("3").equals(state)){
			tblRiskChlTranCtl.setSastatue(CommonFunction.NORMAL);
			//修改状态改到临时表
			//把修改后的值赋值给修改前的值Cardbinold是修改后的值
			tblRiskChlTranCtl.setUpid(operator.getOprId());
			tblRiskChlTranCtl.setUptime(CommonFunction.getCurrentDateTime());
			tblRiskChlTranCtl.setCardbin(tblRiskChlTranCtl.getCardbinold());
			tblRiskChlTranCtl.setTxnnum(tblRiskChlTranCtl.getTxnnumold());
			List<TblRiskChlTranCtl> tblRiskChlTranCtlList=new ArrayList<TblRiskChlTranCtl>();
			tblRiskChlTranCtlList.add(tblRiskChlTranCtl);
			t40210BO.update(tblRiskChlTranCtlList);
		}
		//注销审核
		if(("4").equals(state)){
			tblRiskChlTranCtl.setSastatue(CommonFunction.DELETE);
			//修改状态到临时表
			//把修改后的值赋值给修改前的值Cardbinold是修改后的值
			tblRiskChlTranCtl.setUpid(operator.getOprId());
			tblRiskChlTranCtl.setUptime(CommonFunction.getCurrentDateTime());
			tblRiskChlTranCtl.setCardbin(tblRiskChlTranCtl.getCardbinold());
			tblRiskChlTranCtl.setTxnnum(tblRiskChlTranCtl.getTxnnumold());
			List<TblRiskChlTranCtl> tblRiskChlTranCtlList=new ArrayList<TblRiskChlTranCtl>();
			tblRiskChlTranCtlList.add(tblRiskChlTranCtl);
			t40210BO.update(tblRiskChlTranCtlList);
		}
		return Constants.SUCCESS_CODE;
	}
	
	private String refuse() throws Exception {
		TblRiskRefuse tblRiskRefuse=new TblRiskRefuse();
		TblRiskChlTranCtl tblRiskChlTranCtl=t40210BO.get(getId());
		String state=tblRiskChlTranCtl.getSastatue();
		//新增拒绝，就直接删除
		if(("0").equals(state)){
			t40210BO.delete(getId());
		}
		//修改拒绝临时表变成正常状态
		if(("3").equals(state)){

			//恢复原来的数据
			//修改前的数据给修改后Cardbinold是修改后
			tblRiskChlTranCtl.setUpid(operator.getOprId());
			tblRiskChlTranCtl.setUptime(CommonFunction.getCurrentDateTime());
			tblRiskChlTranCtl.setCardbinold(tblRiskChlTranCtl.getCardbin());
			tblRiskChlTranCtl.setTxnnumold(tblRiskChlTranCtl.getTxnnum());
			tblRiskChlTranCtl.setSastatue(CommonFunction.NORMAL);
			t40210BO.update(tblRiskChlTranCtl);
		}
		//注销拒绝
		if(("4").equals(state)){
			tblRiskChlTranCtl.setUpid(operator.getOprId());
			tblRiskChlTranCtl.setUptime(CommonFunction.getCurrentDateTime());
			tblRiskChlTranCtl.setCardbinold(tblRiskChlTranCtl.getCardbin());
			tblRiskChlTranCtl.setTxnnumold(tblRiskChlTranCtl.getTxnnum());
			tblRiskChlTranCtl.setSastatue(CommonFunction.NORMAL);
			t40210BO.update(tblRiskChlTranCtl);
		}
		
		tblRiskRefuse.setRefuseType(state);
		tblRiskRefuse.setRefuseInfo(getRefuseInfo());
		tblRiskRefuse.setOprId(operator.getOprId());
		tblRiskRefuse.setBrhId(operator.getOprBrhId());
		tblRiskRefuse.setParam1(tblRiskChlTranCtl.getChannel());
		if(tblRiskChlTranCtl.getCardbin().equals("*")){
			tblRiskRefuse.setParam2("所有卡bin");
		}else{
			tblRiskRefuse.setParam2(tblRiskChlTranCtl.getCardbin());
		}
		tblRiskRefuse.setParam3(tblRiskChlTranCtl.getTxnnum());
		tblRiskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
		tblRiskRefuse.setFlag("6");
		t40206bo.saveRefuseInfo(tblRiskRefuse);
		return Constants.SUCCESS_CODE;
	}
}

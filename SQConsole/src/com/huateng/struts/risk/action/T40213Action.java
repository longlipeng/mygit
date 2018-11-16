package com.huateng.struts.risk.action;

import java.util.ArrayList;
import java.util.List;
import com.huateng.bo.risk.T40206BO;
import com.huateng.bo.risk.T40212BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.log.Log;
import com.huateng.po.risk.TblRiskChlTranLimit;
import com.huateng.po.risk.TblRiskRefuse;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

public class T40213Action extends BaseAction{
	
	private String id;
	public String getRefuseInfo() {
		return refuseInfo;
	}
	public void setRefuseInfo(String refuseInfo) {
		this.refuseInfo = refuseInfo;
	}
	private String refuseInfo;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	private static final long serialVersionUID = 1L;
	
	@Override
	protected String subExecute() throws Exception {
		//在新增、修改、冻结、恢复和注销时，修改人和操作人不能使同一人
		String sql = "SELECT CRE_ID FROM TBL_RISK_CHL_TRAN_LIMIT WHERE ID= '" + id + "'";
		List<String> list = commQueryDAO.findBySQLQuery(sql);
		if (null != list && !list.isEmpty()) {
			if (!StringUtil.isNull(list.get(0))) {
				Log.log("修改人："+list.get(0)+" 审核人"+operator.getOprId());
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
			log("操作员编号：" + operator.getOprId()+ "，对渠道交易权限的审核操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	private T40212BO t40212BO = (T40212BO) ContextUtil.getBean("T40212BO");
	private T40206BO t40206bo = (T40206BO) ContextUtil.getBean("T40206BO");
	
	private String accept() {
		//新增机构的审核
		//获得表的这条数据
		TblRiskChlTranLimit tblRiskChlTranLimit=t40212BO.get(this.getId());

		String state=tblRiskChlTranLimit.getStatue();
		//新增审核通过
		//删除待审核的修改为删除状态，其余都设为正常状态
		if(CommonFunction.DELETE_TO_CHECK.equals(state)){
			tblRiskChlTranLimit.setStatue(CommonFunction.DELETE);
		}else{
			tblRiskChlTranLimit.setStatue(CommonFunction.NORMAL);
		}
		tblRiskChlTranLimit.setCardbin(tblRiskChlTranLimit.getCardbinold());
		tblRiskChlTranLimit.setTxnnum(tblRiskChlTranLimit.getTxnnumold());
		tblRiskChlTranLimit.setUpid(operator.getOprId());
		tblRiskChlTranLimit.setUptime(CommonFunction.getCurrentDateTime());
		List<TblRiskChlTranLimit> tblRiskChlTranLimitList=new ArrayList<TblRiskChlTranLimit>();
		tblRiskChlTranLimitList.add(tblRiskChlTranLimit);
		t40212BO.update(tblRiskChlTranLimitList);
		return Constants.SUCCESS_CODE;
	}
	
	private String refuse() throws Exception {
		// TODO Auto-generated method stub
		TblRiskRefuse tblRiskRefuse=new TblRiskRefuse();
		TblRiskChlTranLimit tblRiskChlTranLimit=t40212BO.get(this.getId());
		String state=tblRiskChlTranLimit.getStatue();
		//新增拒绝，直接删除
		if(CommonFunction.ADD_TO_CHECK.equals(state)){
			t40212BO.delete(this.getId());	 
		}
		tblRiskChlTranLimit.setCardbinold(tblRiskChlTranLimit.getCardbin());
		tblRiskChlTranLimit.setTxnnumold(tblRiskChlTranLimit.getTxnnum());
		tblRiskChlTranLimit.setUpid(operator.getOprId());
		tblRiskChlTranLimit.setUptime(CommonFunction.getCurrentDateTime());
		//修改拒绝临时表变成正常状态
		if(("3").equals(state)){
			//恢复原来的数据
			//修改前的数据给修改后Cardbinold是修改后
			tblRiskChlTranLimit.setCardbinold(tblRiskChlTranLimit.getCardbin());
			tblRiskChlTranLimit.setTxnnumold(tblRiskChlTranLimit.getTxnnum());
			tblRiskChlTranLimit.setStatue(CommonFunction.NORMAL);
			t40212BO.update(tblRiskChlTranLimit);
		}
		//注销拒绝
		if(("4").equals(state)){
			tblRiskChlTranLimit.setCardbinold(tblRiskChlTranLimit.getCardbin());
			tblRiskChlTranLimit.setTxnnumold(tblRiskChlTranLimit.getTxnnum());
			tblRiskChlTranLimit.setStatue(CommonFunction.NORMAL);
			t40212BO.update(tblRiskChlTranLimit);
		}			
		tblRiskRefuse.setRefuseType(state);
		tblRiskRefuse.setRefuseInfo(getRefuseInfo());
		tblRiskRefuse.setOprId(operator.getOprId());
		tblRiskRefuse.setBrhId(operator.getOprBrhId());
		tblRiskRefuse.setParam1(tblRiskChlTranLimit.getChannel());
		tblRiskRefuse.setParam2(tblRiskChlTranLimit.getCardbinold());
		tblRiskRefuse.setParam3(tblRiskChlTranLimit.getTxnnumold());
		tblRiskRefuse.setParam4(tblRiskChlTranLimit.getLimit());
		tblRiskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
		tblRiskRefuse.setFlag("7");
		t40206bo.saveRefuseInfo(tblRiskRefuse);
		
		return Constants.SUCCESS_CODE;
	}
}

package com.huateng.struts.base.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import com.huateng.bo.base.T10110BO;
import com.huateng.bo.risk.T40206BO;
import com.huateng.common.Constants;
import com.huateng.po.base.AgencyFeeLub;
import com.huateng.po.base.AgencyFeeLubTmp;
import com.huateng.po.risk.TblRiskRefuse;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

public class T10112Action extends BaseAction{
	
	private static final long serialVersionUID = 1L;
	private T10110BO t10110BO = (T10110BO) ContextUtil.getBean("T10110BO");
	T40206BO t40206bo = (T40206BO) ContextUtil.getBean("T40206BO");
	
	private String id;
	private String refuseInfo;//拒绝原因
	private String idList; //批量审核
	
	
	public String getIdList() {
		return idList;
	}

	public void setIdList(String idList) {
		this.idList = idList;
	}

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
		//在审核新增、修改、注销时，审核人和操作人不能是同一人
//		String sql = "SELECT UP_OPR_ID FROM tbl_agency_fee_lub WHERE FEE_ID='" + this.id + "'";
//		List<String> list = commQueryDAO.findBySQLQuery(sql);
//		if (null != list && !list.isEmpty()) {
//			if (!StringUtil.isNull(list.get(0))) {
//				if(list.get(0).equals(operator.getOprId())){
//					return "同一操作员不能审核！";
//				}
//			}
//		}
		
		/*
		 * 批量审核
		 * by shiyiwen 20141020
		 */
		
		jsonBean.parseJSONArrayData(getIdList());
		int len = jsonBean.getArray().size();
		List<AgencyFeeLubTmp> list = new ArrayList<AgencyFeeLubTmp>();
		
		for(int i = 0;i<len;i++){
			String feeId = jsonBean.getJSONDataAt(i).getString("feeid");			
		//	AgencyFeeLubTmp agencyFeeLub = t10110BO.getTmp(this.getId());
			AgencyFeeLubTmp agencyFeeLub = t10110BO.getTmp(feeId);
			String checkID = agencyFeeLub.getCRE_OPR_ID();
			if(operator.getOprId().equals(checkID)){
				return "费率编号"+agencyFeeLub.getFEE_ID()+"：同一操作员不能审核！";
			}
			try {
				if("accept".equals(getMethod())) {			
					rspCode = accept(feeId);			
				} else if("refuse".equals(getMethod())) {
					rspCode = refuse(feeId);
				}
			} catch (Exception e) {
				e.printStackTrace();
				log("操作员编号：" + operator.getOprId()+ "，对机构费率为"+agencyFeeLub.getFEE_ID()+"的审核操作" + getMethod() + "失败，失败原因为："+e.getMessage());
			}
		
		}
		return rspCode;
	}
	
	private String accept(String id) {//新增机构分润的审核
		//获得表的这条数据
		AgencyFeeLub  feeLub = t10110BO.get(id);
		if(feeLub == null)
			feeLub = new AgencyFeeLub();
		AgencyFeeLubTmp agencyFeeLub = t10110BO.getTmp(id);
		String state = agencyFeeLub.getSTATUE();
	    
		if((Constants.ADD_TO_CHECK).equals(state)){//通过新增待审核
			agencyFeeLub.setSTATUE(Constants.NORMAL);
		}else if((Constants.MODIFY_TO_CHECK).equals(state)){//通过修改待审核
			agencyFeeLub.setSTATUE(Constants.NORMAL);
		}else if((Constants.LOGOUT_TO_CHECK).equals(state)){//通过注销待审核
			agencyFeeLub.setSTATUE(Constants.LOGOUT);
		}
		//20120806，更新审核人和审核时间字段
		agencyFeeLub.setUP_OPR_ID(operator.getOprId());
		agencyFeeLub.setUP_OPR_DATE(CommonFunction.getCurrentDateTime());
		
		//修改状态改dao表
		List<AgencyFeeLubTmp> tblAgencyFeeLubList=new ArrayList<AgencyFeeLubTmp>();
		List<AgencyFeeLub> feeLubList=new ArrayList<AgencyFeeLub>();
	
		try {
			BeanUtils.copyProperties(feeLub,agencyFeeLub);
			tblAgencyFeeLubList.add(agencyFeeLub);
			t10110BO.updateTmp(tblAgencyFeeLubList);
			feeLubList.add(feeLub);
			t10110BO.update(feeLubList);
			return Constants.SUCCESS_CODE;
		}  catch (Exception e) {
			e.printStackTrace();
			return "审核失败！";
		}
	}
	
	private String refuse(String id) throws IllegalAccessException, InvocationTargetException {//审核拒绝，只需处理临时表，不需改动正式表
		AgencyFeeLubTmp agencyFeeLub = t10110BO.getTmp(id);
		AgencyFeeLub  feeLub = t10110BO.get(id);
		String state = agencyFeeLub.getSTATUE();
		//保存拒绝原因
		TblRiskRefuse riskRefuse = new TblRiskRefuse();
		
		//新增拒绝，只要修改状态就可以了
		if((Constants.ADD_TO_CHECK).equals(state)){
			agencyFeeLub.setSTATUE(Constants.ADD_REFUSE);
			riskRefuse.setRefuseType("0");
		}
		//修改拒绝临时表不变，真实表也不变
		if((Constants.MODIFY_TO_CHECK).equals(state)){
			BeanUtils.copyProperties(agencyFeeLub,feeLub);
			riskRefuse.setRefuseType("3");
		//	agencyFeeLub.setSTATUE(Constants.NORMAL);
		}
		//注销拒绝
		if((Constants.LOGOUT_TO_CHECK).equals(state)){
			BeanUtils.copyProperties(agencyFeeLub,feeLub);
			agencyFeeLub.setSTATUE(Constants.NORMAL);
			riskRefuse.setRefuseType("4");
		}
		agencyFeeLub.setUP_OPR_ID(operator.getOprId());
		agencyFeeLub.setUP_OPR_DATE(CommonFunction.getCurrentDateTime());
		//修改状态改到临时表
		List<AgencyFeeLubTmp> agencyFeeInfoList = new ArrayList<AgencyFeeLubTmp>();
		agencyFeeInfoList.add(agencyFeeLub);
		t10110BO.updateTmp(agencyFeeInfoList);
		//20120807添加保存审核拒绝原因
		riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
		riskRefuse.setParam1(agencyFeeLub.getFEE_ID());//分润编号
		riskRefuse.setParam2(agencyFeeLub.getAGEN_ID());//机构代码
		riskRefuse.setParam3(agencyFeeLub.getTERM_ID());//终端号
		riskRefuse.setParam4(agencyFeeLub.getMTCH_NO());//商户号
		riskRefuse.setParam5(agencyFeeLub.getMCC_CODE());//MCC码
		riskRefuse.setParam6(agencyFeeLub.getMCHT_LUB_PARAM());//机构分润参数
		riskRefuse.setBrhId(operator.getOprBrhId());
		riskRefuse.setOprId(operator.getOprId());
		riskRefuse.setRefuseInfo(refuseInfo);
		riskRefuse.setFlag("16");		
		try {
			t40206bo.saveRefuseInfo(riskRefuse);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constants.SUCCESS_CODE;
	}
}

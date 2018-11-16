package com.huateng.struts.base.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.huateng.bo.base.T1010601BO;
import com.huateng.bo.base.T1010602BO;
import com.huateng.bo.base.T10106BO;
import com.huateng.bo.base.T1010802BO;
import com.huateng.bo.risk.T40206BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.po.base.AgencyInfo;
import com.huateng.po.base.AgencyInfoPK;
import com.huateng.po.base.AgencyInfoTrue;
import com.huateng.po.base.AgencyInfoTruePK;
import com.huateng.po.base.TblInstBdbBankCode;
import com.huateng.po.base.TblInstBdbBankCodeTmp;
import com.huateng.po.risk.TblRiskRefuse;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

public class T1010802Action extends BaseAction{

	private static final long serialVersionUID = 1L;
	private String Id;
	private String refuseInfo;
	private String tranType;
	
	/**
	 * @return the refuseInfo
	 */
	public String getRefuseInfo() {
		return refuseInfo;
	}
	/**
	 * @param refuseInfo the refuseInfo to set
	 */
	public void setRefuseInfo(String refuseInfo) {
		this.refuseInfo = refuseInfo;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	
	public String getTranType() {
		return tranType;
	}
	public void setTranType(String tranType) {
		this.tranType = tranType;
	}
	@SuppressWarnings("unchecked")
	@Override
	protected String subExecute() throws Exception {
		//在新增、修改、冻结、恢复和注销时，操作人和操作人不能使同一人
		String sql = "SELECT CRE_OPR_ID FROM TBL_AGENCY_INFO WHERE AGEN_ID= '" + Id + "'";
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
			log("操作员编号：" + operator.getOprId()+ "，对机构的审核操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	//真实表
	private T1010802BO t1010802BO = (T1010802BO) ContextUtil.getBean("T1010802BO");
	private T40206BO t40206bo = (T40206BO) ContextUtil.getBean("T40206BO");
	//临时表
	private T10106BO t10106BO = (T10106BO) ContextUtil.getBean("T10106BO");
	
	//开户行临时表
	private T1010601BO t1010601BO = (T1010601BO) ContextUtil.getBean("T1010601BO");
	
	//开户行真实表
	private T1010602BO t1010602BO = (T1010602BO) ContextUtil.getBean("T1010602BO");
	
	private String accept() throws IllegalAccessException,InvocationTargetException{
		//新增机构的审核
		//获得临时表的这条数据
		AgencyInfoPK agencyInfoPK = new AgencyInfoPK(Id,tranType);
		AgencyInfo agencyInfo=t10106BO.get(agencyInfoPK);
		agencyInfo.setUP_OPR_ID(operator.getOprId());
		agencyInfo.setUP_OPR_DATE(CommonFunction.getCurrentDateTime());
				
		AgencyInfoTruePK agencyInfoTruePK = new AgencyInfoTruePK(Id,tranType);
		
		String state = agencyInfo.getSTATUE();
		//新增审核通过
		if("0".equals(state)){
			agencyInfo.setSTATUE("1");
			//修改状态改到临时表
			List<AgencyInfo> tblAgencyInfoList = new ArrayList<AgencyInfo>();
			tblAgencyInfoList.add(agencyInfo);
			t10106BO.update(tblAgencyInfoList);
			
			//从临时表中获得数据到实际表
			// 复制新的信息,把临时表的甘冈修改的正常的数据放到真实表
			AgencyInfoTrue agencyInfotrue = new AgencyInfoTrue();
			agencyInfotrue.setAgencyInfoTruePK(agencyInfoTruePK);
			
			BeanUtils.copyProperties(agencyInfoPK,agencyInfoTruePK);
			BeanUtils.copyProperties(agencyInfo,agencyInfotrue);
			
			
			if(t1010802BO.get(agencyInfoTruePK) == null){//20120821修改，判断真实表中是否有该机构号的记录
				t1010802BO.add(agencyInfotrue);
			}else{
				t1010802BO.update(agencyInfotrue);
			}
			
		}
		//修改审核
		if("2".equals(state)){
			agencyInfo.setSTATUE("1");
			AgencyInfoTrue agencyInfotrue=t1010802BO.get(agencyInfoTruePK);
			//修改状态改到临时表
			List<AgencyInfo> agencyInfoList=new ArrayList<AgencyInfo>();
			agencyInfoList.add(agencyInfo);
			t10106BO.update(agencyInfoList);
			//修改状态到真实表
			BeanUtils.copyProperties(agencyInfo,agencyInfotrue);	
			t1010802BO.update(agencyInfotrue);
		}
		//注销审核
		if("8".equals(state)){
			agencyInfo.setSTATUE("7");
			AgencyInfoTrue agencyInfotrue=t1010802BO.get(agencyInfoTruePK);
			//修改状态到临时表
			List<AgencyInfo> agencyInfoList=new ArrayList<AgencyInfo>();
			agencyInfoList.add(agencyInfo);
			t10106BO.update(agencyInfoList);
			//修改状态和数据到真实表
			BeanUtils.copyProperties(agencyInfo,agencyInfotrue);	
			t1010802BO.update(agencyInfotrue);
		}
		
		//对开户行进行操作
		TblInstBdbBankCodeTmp tblInstBdbBankCodeTmp = null;
		TblInstBdbBankCode tblInstBdbBankCode = null;
		String stateBank = "";
//		List<TblInstBdbBankCode> tblInstBdbBankCodeList = new ArrayList<TblInstBdbBankCode>();
		
		String bankSql = "select id,tmp_no,inst_code,bank_code,state from TBL_INST_BDB_BANK_CODE_TMP where inst_code = '"+Id.trim()+"'";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(bankSql);
		if(dataList.size() != 0){
			for(int i = 0;i<dataList.size();i++){
				tblInstBdbBankCodeTmp = t1010601BO.query(dataList.get(i)[0].toString());
				stateBank = tblInstBdbBankCodeTmp.getState();
				//如果是新增待审核
				if("0".equals(stateBank)){  
					tblInstBdbBankCode = new TblInstBdbBankCode();
					tblInstBdbBankCodeTmp.setState("2");//临时表状态变为正常
					t1010601BO.update(tblInstBdbBankCodeTmp);
					BeanUtils.copyProperties(tblInstBdbBankCodeTmp,tblInstBdbBankCode);	
					t1010602BO.save(tblInstBdbBankCode);
				//	tblInstBdbBankCodeList.add(tblInstBdbBankCodeTmp);
				 //如果是删除待审核  
				}else if("4".equals(stateBank)){    
					t1010601BO.delete(tblInstBdbBankCodeTmp.getId().toString());
					t1010602BO.delete(tblInstBdbBankCodeTmp.getId().toString());
				}
				
			}		
		}
		
		
		
		
		
		return Constants.SUCCESS_CODE;
	}
	
	private String refuse() throws Exception {
		TblRiskRefuse tblRiskRefuse=new TblRiskRefuse();
		AgencyInfoPK agencyInfoPK = new AgencyInfoPK(Id,tranType);
		AgencyInfo agencyInfo=t10106BO.get(agencyInfoPK);
	//	AgencyInfoTrue agencyInfoTrue=t1010802BO.get(Id); 
		String state=agencyInfo.getSTATUE();
		//新增拒绝，只要修稿临时表的状态就可以了
		if(("0").equals(state)){
			agencyInfo.setSTATUE("3");	
			agencyInfo.setUP_OPR_ID(operator.getOprId());
			agencyInfo.setUP_OPR_DATE(CommonFunction.getCurrentDateTime());
		}
		//修改拒绝将正式表中的数据覆盖临时表中的数据
		if(("2").equals(state)){
			AgencyInfoTruePK agencyInfoTruePK = new AgencyInfoTruePK(Id,tranType);
			AgencyInfoTrue agencyInfoTrue=t1010802BO.get(agencyInfoTruePK);
			BeanUtils.copyProperties(agencyInfoTrue, agencyInfo);
			
			agencyInfo.setUP_OPR_ID(operator.getOprId());
			agencyInfo.setUP_OPR_DATE(CommonFunction.getCurrentDateTime());
			//agencyInfo.setSTATUE("4");
			agencyInfo.setSTATUE("1");//20120817修改
		}
		//注销拒绝
		if(("8").equals(state)){
			agencyInfo.setSTATUE("1");
			agencyInfo.setUP_OPR_ID(operator.getOprId());
			agencyInfo.setUP_OPR_DATE(CommonFunction.getCurrentDateTime());
		}
		//修改状态改到临时表
		List<AgencyInfo> agencyInfoList=new ArrayList<AgencyInfo>();
		agencyInfoList.add(agencyInfo);
		t10106BO.update(agencyInfoList);
		
		//对开户行进行操作
		TblInstBdbBankCodeTmp tblInstBdbBankCodeTmp = null;
		TblInstBdbBankCode tblInstBdbBankCode = null;
		String stateBank = "";
		
		String bankSql = "select id,tmp_no,inst_code,bank_code,state from TBL_INST_BDB_BANK_CODE_TMP where inst_code = '"+Id.trim()+"'";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(bankSql);
		if(dataList.size() != 0){
			for(int i=0;i<dataList.size();i++){
				tblInstBdbBankCodeTmp = t1010601BO.query(dataList.get(i)[0].toString());
				stateBank = dataList.get(i)[4].toString();
				String id = tblInstBdbBankCodeTmp.getId().toString();
				//如果是新增待审核 被拒绝
				if("0".equals(stateBank)){
					t1010601BO.delete(id);
				}else if("4".equals(stateBank)){     //如果是删除待审核 被拒绝
					tblInstBdbBankCode = t1010602BO.query(id);
					BeanUtils.copyProperties(tblInstBdbBankCode,tblInstBdbBankCodeTmp);
					t1010601BO.update(tblInstBdbBankCodeTmp);
				}
				
			}
		}
		
		tblRiskRefuse.setRefuseType(state);     //拒绝类型2
		tblRiskRefuse.setRefuseInfo(getRefuseInfo());  //拒绝原因
		tblRiskRefuse.setOprId(operator.getOprId());    //拒绝人00001111
		tblRiskRefuse.setBrhId(operator.getOprBrhId());   //拒绝人所属分公司9900
		tblRiskRefuse.setParam1(agencyInfo.getAgencyInfoPK().getAGEN_ID());   //机构号00000001
		tblRiskRefuse.setParam2(agencyInfo.getAGEN_NAME());  //机构名称 11
		tblRiskRefuse.setParam3(agencyInfo.getAGEN_TYPE());   // 机构类型01
		tblRiskRefuse.setParam4(agencyInfo.getAgencyInfoPK().getTRAN_TYPE());   //交易联接类型02
		tblRiskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());   //20140901165954
		tblRiskRefuse.setFlag("92");    //
		t40206bo.saveRefuseInfo(tblRiskRefuse);
		
		return Constants.SUCCESS_CODE;
	}
}

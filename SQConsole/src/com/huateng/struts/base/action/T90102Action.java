package com.huateng.struts.base.action;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import com.huateng.bo.base.T10108BO;
import com.huateng.bo.base.T90102BO;
import com.huateng.bo.risk.T40206BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.po.base.TblBranchManage;
import com.huateng.po.base.TblBranchManageTrue;
import com.huateng.po.risk.TblRiskRefuse;
public class T90102Action extends BaseAction {

	private static final long serialVersionUID = 9155983083422330118L;

	T40206BO t40206bo = (T40206BO) ContextUtil.getBean("T40206BO");
	@Override
	protected String subExecute() throws Exception {
		//在新增、修改和注销时，修改人和操作人不能使同一人
		String sql = "SELECT UP_OPR_ID FROM tbl_branch_manage WHERE BRANCH_NO= '" + Id + "'";
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
				if(("8").equals(this.getState())){
					if( ifParent(this.getId()) ){
						return "该分公司有下级分公司，不能删除。";
					}
				}
				rspCode = accept();			
			} else if("refuse".equals(getMethod())) {
				rspCode = refuse();
			}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，对分公司的审核操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}

	private T10108BO t10108BO = (T10108BO) ContextUtil.getBean("T10108BO");
	private T90102BO t90102BO = (T90102BO) ContextUtil.getBean("T90102BO");
	
	private String accept() throws Exception {//通过
		TblBranchManage tblBranchManage = t10108BO.get(this.getId());//获得临时表的数据
		TblBranchManageTrue tblBranchManageTrue = new TblBranchManageTrue();//真实表
		
		String state=tblBranchManage.getState();
		//审核新增
		if(("0").equals(state)){
			tblBranchManage.setBranchCheckOpr(operator.getOprId());
			tblBranchManage.setBranchCheckTime(CommonFunction.getCurrentDateTime());
			tblBranchManage.setState("1");//normal
			//修改状态改到临时表
			List<TblBranchManage> tblBranchManageList=new ArrayList<TblBranchManage>();
			tblBranchManageList.add(tblBranchManage);
			t10108BO.update(tblBranchManageList);
			
			BeanUtils.copyProperties(tblBranchManage,tblBranchManageTrue);	//保存数据到实际表
			t90102BO.add(tblBranchManageTrue);
		}
		//审核修改
		if(("2").equals(state)){
			tblBranchManage.setState("1");//normal
			tblBranchManageTrue = t90102BO.get(this.getId());
			//修改状态改到临时表
			List<TblBranchManage> tblBranchManageList = new ArrayList<TblBranchManage>();
			tblBranchManageList.add(tblBranchManage);
			t10108BO.update(tblBranchManageList);
			//修改状态到真实表
			BeanUtils.copyProperties(tblBranchManage,tblBranchManageTrue);	
			t90102BO.update(tblBranchManageTrue);
		}
		//审核删除
		if(("8").equals(state)){
			tblBranchManage.setState("7");
			//修改状态到临时表
			List<TblBranchManage> tblBranchManageList=new ArrayList<TblBranchManage>();
			tblBranchManageList.add(tblBranchManage);
			t10108BO.update(tblBranchManageList);
			//修改状态到真实表
			BeanUtils.copyProperties(tblBranchManage,tblBranchManageTrue);	
			t90102BO.update(tblBranchManageTrue);
		}
		return Constants.SUCCESS_CODE;
	}
	
	@SuppressWarnings("unchecked")
	private boolean ifParent(String id) throws Exception{
		List<Object[]> dataList = null;// 存放查询结果集
		StringBuffer sql = new StringBuffer();
		sql.append( "select PARENT_BRANCH_ID from TBL_BRANCH_MANAGE ")
				.append("where BRANCH_NO!=PARENT_BRANCH_ID and PARENT_BRANCH_ID= '").append(id).append("'");
		
		dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql.toString());
		if(dataList.isEmpty() || dataList.size() == 0)
			return  false;
		else
			return true;
	}
	
	private String refuse() throws Exception{//拒绝
		TblBranchManage tblBranchManage = t10108BO.get(this.getId());
//		TblBranchManageTrue tblBranchManageTrue=new TblBranchManageTrue();
		String state=tblBranchManage.getState();
		//新增拒绝，直接删除临时表中信息即可
		if(("0").equals(state)){
			t10108BO.delete(this.getId());
			
			//保存拒绝原因
			TblRiskRefuse riskRefuse = new TblRiskRefuse();
			riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
			riskRefuse.setParam1(tblBranchManage.getId());
			riskRefuse.setParam2(tblBranchManage.getBranchname());
			riskRefuse.setParam3(tblBranchManage.getBranchaddr());
			riskRefuse.setParam4(tblBranchManage.getBrancharea());
			riskRefuse.setParam5(tblBranchManage.getBranchpos());
			riskRefuse.setParam6(tblBranchManage.getBranchdes());
			riskRefuse.setBrhId(operator.getOprBrhId());
			riskRefuse.setOprId(operator.getOprId());
			riskRefuse.setRefuseType("0");
			riskRefuse.setRefuseInfo(refuseInfo);
			riskRefuse.setFlag("12");
			
			t40206bo.saveRefuseInfo(riskRefuse);
			return Constants.SUCCESS_CODE;
		}
		//修改拒绝,临时表不变，真实表也不变
		if(("2").equals(state)){
			tblBranchManage.setState("1");
			//保存拒绝原因
			TblRiskRefuse riskRefuse = new TblRiskRefuse();
			riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
			riskRefuse.setParam1(tblBranchManage.getId());
			riskRefuse.setParam2(tblBranchManage.getBranchname());
			riskRefuse.setParam3(tblBranchManage.getBranchaddr());
			riskRefuse.setParam4(tblBranchManage.getBrancharea());
			riskRefuse.setParam5(tblBranchManage.getBranchpos());
			riskRefuse.setParam6(tblBranchManage.getBranchdes());
			riskRefuse.setBrhId(operator.getOprBrhId());
			riskRefuse.setOprId(operator.getOprId());
			riskRefuse.setRefuseType("3");
			riskRefuse.setRefuseInfo(refuseInfo);
			riskRefuse.setFlag("12");
			
			t40206bo.saveRefuseInfo(riskRefuse);
		}
		//删除拒绝,临时表更新，真实表不变
		if(("8").equals(state)){
			tblBranchManage.setState("1");
			//保存拒绝原因
			TblRiskRefuse riskRefuse = new TblRiskRefuse();
			riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
			riskRefuse.setParam1(tblBranchManage.getId());
			riskRefuse.setParam2(tblBranchManage.getBranchname());
			riskRefuse.setParam3(tblBranchManage.getBranchaddr());
			riskRefuse.setParam4(tblBranchManage.getBrancharea());
			riskRefuse.setParam5(tblBranchManage.getBranchpos());
			riskRefuse.setParam6(tblBranchManage.getBranchdes());
			riskRefuse.setBrhId(operator.getOprBrhId());
			riskRefuse.setOprId(operator.getOprId());
			riskRefuse.setRefuseType("4");
			riskRefuse.setRefuseInfo(refuseInfo);
			riskRefuse.setFlag("12");
			
			t40206bo.saveRefuseInfo(riskRefuse);
		}
		//修改状态改到临时表
		List<TblBranchManage> tblBranchManageList=new ArrayList<TblBranchManage>();
		tblBranchManageList.add(tblBranchManage);
		t10108BO.update(tblBranchManageList);
		
		
		return Constants.SUCCESS_CODE;
	}
	
	private String Id;
	private String branchLevel;
	private String parentBranchId;
	private String brancharea;
	private String phonenum;
	private String branchpos;
	private String branchaddr;
	private String linkname;
	private String linkmanpho;
	private String linkmanmail;
	private String branchfax;
	private String branchname;
	private String branchdes;
	private String branchDataList;
	private String state;//分公司状态
	private String refuseInfo;//拒绝原因
	
	public String getRefuseInfo() {
		return refuseInfo;
	}

	public void setRefuseInfo(String refuseInfo) {
		this.refuseInfo = refuseInfo;
	}

	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	
	public String getBranchLevel() {
		return branchLevel;
	}

	public void setBranchLevel(String branchLevel) {
		this.branchLevel = branchLevel;
	}

	public String getParentBranchId() {
		return parentBranchId;
	}

	public void setParentBranchId(String parentBranchId) {
		this.parentBranchId = parentBranchId;
	}

	public String getBrancharea() {
		return brancharea;
	}
	public void setBrancharea(String brancharea) {
		this.brancharea = brancharea;
	}
	public String getPhonenum() {
		return phonenum;
	}
	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}
	public String getBranchpos() {
		return branchpos;
	}
	public void setBranchpos(String branchpos) {
		this.branchpos = branchpos;
	}
	public String getBranchaddr() {
		return branchaddr;
	}
	public void setBranchaddr(String branchaddr) {
		this.branchaddr = branchaddr;
	}
	public String getLinkname() {
		return linkname;
	}
	public void setLinkname(String linkname) {
		this.linkname = linkname;
	}
	public String getLinkmanpho() {
		return linkmanpho;
	}
	public void setLinkmanpho(String linkmanpho) {
		this.linkmanpho = linkmanpho;
	}
	public String getLinkmanmail() {
		return linkmanmail;
	}
	public void setLinkmanmail(String linkmanmail) {
		this.linkmanmail = linkmanmail;
	}
	public String getBranchfax() {
		return branchfax;
	}
	public void setBranchfax(String branchfax) {
		this.branchfax = branchfax;
	}
	public String getBranchname() {
		return branchname;
	}
	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}
	public String getBranchdes() {
		return branchdes;
	}
	public void setBranchdes(String branchdes) {
		this.branchdes = branchdes;
	}
	public String getBranchDataList() {
		return branchDataList;
	}
	public void setBranchDataList(String branchDataList) {
		this.branchDataList = branchDataList;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
